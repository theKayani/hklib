package com.hk.io.mqtt;

import com.hk.math.MathUtil;
import com.hk.util.KeyValue;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

class ClientThread extends Thread
{
	private final Client client;
	private final Socket socket;
	private final AtomicBoolean globalStop, sentClose;
	final AtomicReference<PacketType> lastPacket = new AtomicReference<>();
	final AtomicBoolean gotConnAck;
	boolean hasPriorSession;
	Common.ConnectReturn connectReturn;
	private final AtomicLong connectTime;
	private final int keepAlive;

	ClientThread(Client client, Socket socket, int keepAlive)
	{
		this.client = client;
		this.socket = socket;
		this.keepAlive = keepAlive;
		globalStop = client.globalStop;
		sentClose = new AtomicBoolean(false);
		gotConnAck = new AtomicBoolean(false);
		connectTime = new AtomicLong(-1L);
	}

	@Override
	public void run()
	{
		try
		{
			InputStream in = socket.getInputStream();
			byte b;
			while(!globalStop.get())
			{
				// fixed header
				b = Common.read(in);
				PacketType type;
				synchronized (lastPacket)
				{
					type = PacketType.getFromHeader(b);
					if(type == null)
					{
						client.getLogger().warning("Unknown packet header: " + MathUtil.byteBin(b & 0xFF));
						socket.close();
						break;
					}

					lastPacket.set(type);
					lastPacket.notifyAll();
				}

				AtomicInteger remLen = new AtomicInteger(Common.readRemainingField(in));
				if(client.getLogger().isLoggable(Level.FINER))
					client.getLogger().finer("Received packet: " + type + ", remaining length: " + remLen);
				switch (type)
				{
					case CONNACK:
						handleConnackPacket(in, remLen);
						break;
					case PUBLISH:
						throw new Error("TODO PUBLISH");
					case PUBACK:
						handlePubackPacket(in, remLen);
						break;
					case PUBREC:
						handlePubrecPacket(in, remLen);
						break;
					case PUBREL:
						throw new Error("TODO PUBREL");
					case PUBCOMP:
						handlePubcomp(in, remLen);
						break;
					case SUBACK:
						handleSuback(in, remLen);
						break;
					case UNSUBACK:
						throw new Error("TODO UNSUBACK");
					case PINGRESP:
						// handled by lastPacket
						break;
					case DISCONNECT:
						globalStop.set(true);
						break;
					case CONNECT:
					case SUBSCRIBE:
					case UNSUBSCRIBE:
					case PINGREQ:
						throw new IOException("unexpected packet type: " + type);
				}
				if(!globalStop.get() && remLen.get() != 0)
				{
					client.getLogger().fine("remaining length overflow, disconnecting");
					globalStop.set(true);
				}
			}
		}
		catch (IOException e)
		{
			if(!globalStop.get())
			{
				if (client.exceptionHandler != null)
					client.exceptionHandler.accept(e);
			}
		}
		finally
		{
			globalStop.set(true);
			close(true);
		}
	}

	private void handleConnackPacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		// variable header
		hasPriorSession = (Common.read(in, remLen) & 1) != 0;
		connectReturn = Common.ConnectReturn.values()[Common.read(in, remLen)];

		if(connectReturn == Common.ConnectReturn.ACCEPTED)
			client.status.set(Client.Status.AUTHORIZED);

		if(remLen.get() != 0)
		{
			client.getLogger().fine("remaining length overflow, disconnecting");
			globalStop.set(true);
		}
		else
			gotConnAck.set(true);

		client.getLogger().info("Connect Acknowledged with connect return: " + connectReturn);
	}

	private void handlePublishPacket(InputStream in, AtomicInteger remLen, int flags) throws IOException
	{
		boolean dup = (flags & 8) != 0;
		int qos = (flags & 6) >> 1;
		boolean retain = (flags & 1) != 0;

		String topic = Common.readUTFString(in, remLen);

		if(Common.isInvalidTopic(topic))
		{
			client.getLogger().fine("invalid publish topic, disconnecting");
			globalStop.set(true);
			return;
		}

		Common.PacketID pid = null;
		if(qos > 0)
			pid = new Common.PacketID(Common.readShort(in, remLen));

		int size = remLen.get();
		Message msg;
		if(broker.options.maxVolatileMessageSize >= 0 && size > broker.options.maxVolatileMessageSize)
		{
			// write to file, link to message
			File file = File.createTempFile("mqtt_message_", Long.toHexString(System.nanoTime()) + ".dat");
			if(!file.exists() && !file.createNewFile())
				throw new FileNotFoundException("cannot create temp file: " + file);

			OutputStream out = Files.newOutputStream(file.toPath());
			byte[] arr = new byte[1024];
			while(size > 0)
			{
				file.deleteOnExit();
				int len = Math.min(1024, size);
				if(in.read(arr, 0, len) != len)
					throw new IOException(Common.EOF);
				size -= len;
				out.write(arr, 0, len);
			}
			out.close();
			msg = new Message(topic, file, qos, retain);
		}
		else
		{
			// read to memory, link to message
			byte[] arr = new byte[size];
			if(in.read(arr) != size)
				throw new IOException(Common.EOF);

			msg = new Message(topic, arr, qos, retain);
		}
		remLen.set(0);

		if(broker.getLogger().isLoggable(Level.FINEST))
			broker.getLogger().finest("[" + address + "] received: " + msg);

		if(qos == 0 || qos == 1)
		{
			// forward to subscribers
			broker.beginForward(msg);

			if(qos == 1)
			{
				OutputStream out = socket.getOutputStream();
				synchronized (writeLock)
				{
					if(broker.getLogger().isLoggable(Level.FINE))
						broker.getLogger().fine("[" + address + "] Sending packet: PUBACK (pid: " + pid + ")");
					Common.sendPuback(out, pid);
				}
			}
		}
		else
		{
			// await pubrel before forwarding
			sess().unfinishedRecv.put(pid, new PublishPacket.Transaction(msg, pid, 2).setLastPacket(PacketType.PUBREC));

			OutputStream out = socket.getOutputStream();
			synchronized (writeLock)
			{
				if(broker.getLogger().isLoggable(Level.FINE))
					broker.getLogger().fine("[" + address + "] Sending packet: PUBREC (pid: " + pid + ")");
				Common.sendPubrec(out, pid);
			}
		}
	}

	private void handlePubackPacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		PublishPacket.Transaction transaction = client.unfinishedSend.remove(pid);
		if(transaction == null || transaction.qos != 1 || transaction.lastPacket != PacketType.PUBLISH)
		{
			client.getLogger().warning("unexpected PUBACK, disconnecting");
			globalStop.set(true);
		}
		else
		{
			synchronized (transaction.pid)
			{
				transaction.pid.notifyAll();
			}
		}
	}

	private void handlePubrecPacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		PublishPacket.Transaction transaction = client.unfinishedSend.get(pid);
		if(transaction == null || transaction.qos != 2 || transaction.lastPacket != PacketType.PUBLISH)
		{
			client.getLogger().warning("unexpected PUBREC, disconnecting");
			globalStop.set(true);
		}
		else
		{
			client.unfinishedSend.put(pid, transaction.setLastPacket(PacketType.PUBREL));

			OutputStream out = socket.getOutputStream();
			client.executorService.submit(() -> {
				try
				{
					client.getLogger().fine("Sending packet: PUBREL (pid: " + pid + ")");
					Common.sendPubrel(out, pid);
				}
				catch (IOException e)
				{
					if(client.exceptionHandler != null)
						client.exceptionHandler.accept(e);
				}
			});
		}
	}

	private void handlePubrelPacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		Session sess = sess();

		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		PublishPacket.Transaction transaction = sess.unfinishedRecv.remove(pid);
		if(transaction == null || transaction.qos != 2 || transaction.lastPacket != PacketType.PUBREC)
		{
			broker.getLogger().warning("unexpected PUBREL, disconnecting");
			localStop.set(true);
		}
		else
		{
			broker.beginForward((Message) transaction.message);

			OutputStream out = socket.getOutputStream();
			synchronized (writeLock)
			{
				if(broker.getLogger().isLoggable(Level.FINE))
					broker.getLogger().fine("[" + address + "] Sending packet: PUBCOMP (pid: " + pid + ")");
				Common.sendPubcomp(out, pid);
			}
		}
	}

	private void handlePubcomp(InputStream in, AtomicInteger remLen) throws IOException
	{
		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		PublishPacket.Transaction transaction = client.unfinishedSend.remove(pid);
		if(transaction == null || transaction.qos != 2 || transaction.lastPacket != PacketType.PUBREL)
		{
			client.getLogger().warning("unexpected PUBCOMP, disconnecting");
			globalStop.set(true);
		}
		else
		{
			synchronized (transaction.pid)
			{
				transaction.pid.notifyAll();
			}
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private void handleSuback(InputStream in, AtomicInteger remLen) throws IOException
	{
		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		PublishPacket.Transaction transaction = client.unfinishedSend.remove(pid);
		if(transaction == null || transaction.qos != 1 || transaction.lastPacket != PacketType.SUBSCRIBE)
		{
			client.getLogger().warning("unexpected SUBACK, disconnecting");
			globalStop.set(true);
		}
		else
		{
			synchronized (transaction.pid)
			{
				KeyValue<Integer>[] topicFilters = (KeyValue[]) transaction.message;
				int diff = remLen.get() - topicFilters.length;
				if(diff != 0)
				{
					client.getLogger().warning("incomplete packet (invalid remaining length), disconnecting");
					globalStop.set(true);
					return;
				}

				synchronized (client.subscribedTopicFilters)
				{
					outerLabel:
					for (KeyValue<Integer> topicFilter : topicFilters)
					{
						int b = Common.read(in, remLen) & 0xFF;
						switch (b)
						{
							case 0:
							case 1:
							case 2:
								client.subscribedTopicFilters.put(topicFilter.key, b);
								break;
							case 128:
								break;
							default:
								client.getLogger().warning("unexpected subscribe return: " + MathUtil.byteHex(b) + ", disconnecting");
								globalStop.set(true);
								break outerLabel;
						}
					}
				}
				transaction.pid.notifyAll();
			}
		}
	}

	void sendConnectPacket()
	{
		try
		{
			OutputStream out = socket.getOutputStream();
			if(client.getLogger().isLoggable(Level.FINE))
				client.getLogger().fine("Sending packet: CONNECT (id: " + client.clientID + ")");

			// fixed header
			out.write(0x10);
			int remlen = 10;
			remlen += Common.utfBytesLength(client.clientID) + 2;
			if(client.lastWill != null)
			{
				remlen += Common.utfBytesLength(client.lastWill.getTopic()) + 2;
				int size = client.lastWill.getSize();
				if(size > 65535)
					throw new UnsupportedOperationException("last will message size cannot be greater than 65535");
				if(size < 0)
					throw new UnsupportedOperationException("last will message size cannot be unknown");
				remlen += size + 2;
			}
			if(client.username != null)
			{
				remlen += Common.utfBytesLength(client.username) + 2;
				if(client.password != null)
					remlen += client.password.length + 2;
			}
			// remaining field
			Common.writeRemainingField(out, remlen);

			// variable header

			// protocol name and level
			out.write(0x0); // string
			out.write(0x4); // length of 4
			out.write(0x4D); // M
			out.write(0x51); // Q
			out.write(0x54); // T
			out.write(0x54); // T
			out.write(0x4); // v3.1.1

			// connect flags
			int connectFlags = 0;
			if(client.cleanSession)
				connectFlags |= 2;

			if(client.lastWill != null)
			{
				connectFlags |= 4;
				connectFlags |= (client.lastWill.getQos() << 3);
				if(client.lastWill.isRetain())
					connectFlags |= 32;
			}
			if(client.username != null)
			{
				connectFlags |= 128;
				if (client.password != null)
					connectFlags |= 64;
			}

			out.write(connectFlags & 0xFF);

			// keep-alive
			Common.writeShort(out, keepAlive);

			// payload
			Common.writeUTFString(out, client.clientID);

			if(client.lastWill != null)
			{
				Common.writeUTFString(out, client.lastWill.getTopic());
				Common.writeShort(out, client.lastWill.getSize());
				client.lastWill.writeTo(out);
			}

			if(client.username != null)
			{
				Common.writeUTFString(out, client.username);
				if(client.password != null)
					Common.writeBytes(out, client.password);
			}

			out.flush();
			connectTime.set(System.nanoTime() / 1000000);

			if(client.getLogger().isLoggable(Level.FINE))
			{
				client.getLogger().fine("Connecting with username: " + client.username + ", with" + (client.password == null ? " no" : "")
						+ " password, " + keepAlive + "s keep alive, and " + (client.lastWill == null ? "no" : "with a") + " will");
			}
		}
		catch (IOException e)
		{
			if(client.exceptionHandler != null)
				client.exceptionHandler.accept(e);
		}
	}

	void sendPingRequestPacket()
	{
		client.executorService.submit(() -> {
			try
			{
				client.getLogger().fine("Sending packet: PINGREQ");

				OutputStream out = socket.getOutputStream();
				out.write(0xC0);
				out.write(0x0);
				out.flush();
				connectTime.set(System.nanoTime() / 1000000);
			}
			catch (IOException ex)
			{
				if(client.exceptionHandler != null)
					client.exceptionHandler.accept(ex);
			}
		});
	}

	void publish(Message message, Common.PacketID pid)
	{
		try
		{
			PublishPacket packet = new PublishPacket(message, pid, client.getLogger(), socket.getOutputStream(), null);
			packet.setExceptionHandler(client.exceptionHandler);
			packet.setOnComplete(transaction -> {
				connectTime.set(System.nanoTime() / 1000000);
				synchronized (pid)
				{
					if (message.getQos() > 0)
						client.unfinishedSend.put(pid, transaction);
					else
						pid.notifyAll();
				}
			});
			client.executorService.submit(packet);
		}
		catch (IOException e)
		{
			if(client.exceptionHandler != null)
				client.exceptionHandler.accept(e);
		}
	}

	@SuppressWarnings("ConstantConditions")
	void subscribe(KeyValue<Integer>[] topicFilters, Common.PacketID pid)
	{
		client.executorService.submit(() -> {
			try
			{
				client.getLogger().fine("Sending packet: SUBSCRIBE with " + topicFilters.length + " topic(s)");

				OutputStream out = socket.getOutputStream();
				out.write(0x82);
				int remlen = 0;
				for (KeyValue<Integer> topicFilter : topicFilters)
					remlen += Common.utfBytesLength(topicFilter.key) + 5;
				Common.writeRemainingField(out, remlen);
				Common.writeShort(out, pid.get());

				for (KeyValue<Integer> topicFilter : topicFilters)
				{
					Common.writeUTFString(out, topicFilter.key);
					out.write(topicFilter.value & 3);
				}

				client.unfinishedSend.put(pid, new PublishPacket.Transaction(topicFilters, pid, 1).setLastPacket(PacketType.SUBSCRIBE));

				out.flush();
				connectTime.set(System.nanoTime() / 1000000);
			}
			catch (IOException ex)
			{
				if(client.exceptionHandler != null)
					client.exceptionHandler.accept(ex);
			}
		});
	}

	void checkGotConnect()
	{
		long elapsed = System.nanoTime() / 1000000 - connectTime.get();
		if(gotConnAck.get() && elapsed > keepAlive * 1000L)
		{
			client.getLogger().fine("exceeding keep-alive, sending PINGREQ");
			sendPingRequestPacket();
		}
		else if(connectTime.get() != -1 && !gotConnAck.get() && elapsed > client.options.connectWaitTimeout)
		{
			client.getLogger().warning("did not receive CONNACK in time, disconnecting");
			close(false);
			return;
		}

		synchronized (client.unfinishedSend)
		{
			for (Map.Entry<Common.PacketID, PublishPacket.Transaction> entry : client.unfinishedSend.entrySet())
			{
				Common.PacketID key = entry.getKey();
				PublishPacket.Transaction value = entry.getValue();
				if (value.isPast(client.options.qosAckTimeout))
				{
					client.getLogger().warning("did not receive ACK in time, disconnecting");
					close(false);
					return;
				}
			}
		}
		synchronized (client.unfinishedRecv)
		{
			for (Map.Entry<Common.PacketID, PublishPacket.Transaction> entry : client.unfinishedRecv.entrySet())
			{
				Common.PacketID key = entry.getKey();
				PublishPacket.Transaction value = entry.getValue();
				if (value.isPast(client.options.qosAckTimeout))
				{
					client.getLogger().warning("did not receive ACK in time, disconnecting");
					close(false);
					return;
				}
			}
		}
	}

	void close(boolean now)
	{
		if(socket.isClosed() || sentClose.get())
			return;

		globalStop.set(true);
		sentClose.set(true);
		Runnable closer = () -> {
			try
			{
				if (!socket.isClosed())
					socket.close();
			}
			catch (IOException e)
			{
				if(client.exceptionHandler != null)
					client.exceptionHandler.accept(e);
			}
			finally
			{
				client.status.set(Client.Status.DISCONNECTED);
				client.getLogger().info("Client disconnected");
			}
		};

		if (!now)
		{
			client.executorService.submit(() -> {
				try
				{
					OutputStream out = socket.getOutputStream();
					out.write(0xE0);
					out.write(0x0);
					out.flush();
				}
				catch (IOException e)
				{
					if(client.exceptionHandler != null)
						client.exceptionHandler.accept(e);
				}
			});
			client.executorService.submit(closer);
			client.executorService.shutdown();
		}
		else
		{
			client.executorService.shutdown();
			try
			{
				if(!client.executorService.awaitTermination(10, TimeUnit.SECONDS))
					client.getLogger().warning("executor service did not finish in 10 seconds");
			}
			catch (InterruptedException e)
			{
				client.getLogger().log(Level.WARNING, "interrupted while waiting for executor service to close");
			}
			closer.run();
		}
	}
}
