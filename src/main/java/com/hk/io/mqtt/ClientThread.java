package com.hk.io.mqtt;

import com.hk.math.MathUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
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
						handlePuback(in, remLen);
						break;
					case PUBREC:
						handlePubrec(in, remLen);
						break;
					case PUBREL:
						throw new Error("TODO PUBREL");
					case PUBCOMP:
						throw new Error("TODO PUBCOMP");
					case SUBACK:
						throw new Error("TODO SUBACK");
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
				if(remLen.get() != 0)
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

	private void handlePuback(InputStream in, AtomicInteger remLen) throws IOException
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

	private void handlePubrec(InputStream in, AtomicInteger remLen) throws IOException
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
					client.getLogger().fine("Sending packet: PUBREC (pid: " + pid + ")");
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

	void sendConnectPacket()
	{
		try
		{
			OutputStream out = socket.getOutputStream();
			if(client.getLogger().isLoggable(Level.FINE))
				client.getLogger().fine("Sending packet: CONNECT (id: " + client.clientID + ")");

			// fixed header
			out.write(0x10);
			ByteArrayOutputStream bout = new ByteArrayOutputStream(256);

			// variable header

			// protocol name and level
			bout.write(0x0); // string
			bout.write(0x4); // length of 4
			bout.write(0x4D); // M
			bout.write(0x51); // Q
			bout.write(0x54); // T
			bout.write(0x54); // T
			bout.write(0x4); // v3.1.1

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

			bout.write(connectFlags & 0xFF);

			// keep-alive
			Common.writeShort(bout, keepAlive);

			// payload
			Common.writeUTFString(bout, client.clientID);

			if(client.lastWill != null)
			{
				Common.writeUTFString(bout, client.lastWill.getTopic());
				int size = client.lastWill.getSize();
				if(size > 65535)
					throw new UnsupportedOperationException("last will message size cannot be greater than 65535");

				Common.writeShort(bout, size);
				client.lastWill.writeTo(bout);
			}

			if(client.username != null)
			{
				Common.writeUTFString(bout, client.username);
				if(client.password != null)
					Common.writeBytes(bout, client.password);
			}

			// remaining field
			Common.writeRemainingField(out, bout.size());
			bout.writeTo(out);
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
