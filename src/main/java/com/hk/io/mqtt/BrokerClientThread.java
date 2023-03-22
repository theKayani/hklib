package com.hk.io.mqtt;

import com.hk.math.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

class BrokerClientThread extends Thread
{
	private final Broker broker;
	private final Socket socket;
//	private final AtomicBoolean globalStop, localStop, sentClose, gotConnect;
	private final AtomicBoolean localStop, sentClose, gotConnect;
	private final AtomicInteger keepAlive;
	private final AtomicLong connectTime;
	private final SocketAddress address;
	private final int internalID;
	private final Object writeLock = new Object();
	// pretty much thread local anyway
	private Message will;
	private final AtomicReference<Session> currentSession;
	private int packetID;

	BrokerClientThread(Broker broker, Socket client, int internalID)
	{
		this.broker = broker;
		this.socket = client;
		this.internalID = internalID;
		localStop = new AtomicBoolean(false);
		sentClose = new AtomicBoolean(false);
		gotConnect = new AtomicBoolean(false);
		keepAlive = new AtomicInteger(-1);
		connectTime = new AtomicLong(System.nanoTime() / 1000000L);
		currentSession = new AtomicReference<>(null);
		address = client.getRemoteSocketAddress();
		packetID = -1;
	}

	@Override
	public void run()
	{
		try
		{
			InputStream in = socket.getInputStream();
			byte b;
			while(!localStop.get() && socket.isConnected())
			{
				// fixed header
				b = Common.read(in);
				PacketType type = PacketType.getFromHeader(b);

				if(type == null)
				{
					if(broker.getLogger().isLoggable(Level.WARNING))
						broker.getLogger().warning("[" + address + "] Unknown packet header: " + MathUtil.byteBin(b & 0xFF));
					localStop.set(true);
					break;
				}

				AtomicInteger remLen = new AtomicInteger(Common.readRemainingField(in));
				if(broker.getLogger().isLoggable(Level.FINER))
					broker.getLogger().finer("[" + address + "] Received packet: " + type + ", remaining length: " + remLen);

				if(type != PacketType.CONNECT && sess() == null)
				{
					broker.getLogger().fine("[" + address + "] unexpected publish before auth, disconnecting");
					localStop.set(true);
					break;
				}

				switch (type)
				{
					case CONNECT:
						handleConnectPacket(in, remLen);
						break;
					case PUBLISH:
						handlePublishPacket(in, remLen, b & 0xF);
						break;
					case PUBACK:
						handlePubackPacket(in, remLen);
						break;
					case PUBREC:
						handlePubrecPacket(in, remLen);
						break;
					case PUBREL:
						handlePubrelPacket(in, remLen);
						break;
					case PUBCOMP:
						handlePubcompPacket(in, remLen);
						break;
					case SUBSCRIBE:
						handleSubscribePacket(in, remLen);
						break;
					case UNSUBSCRIBE:
						handleUnsubscribePacket(in, remLen);
						break;
					case PINGREQ:
						OutputStream out = socket.getOutputStream();

						synchronized (writeLock)
						{
							broker.getLogger().fine("[" + address + "] Sending packet: PINGRESP");
							out.write(0xD0);
							out.write(0x0);
							out.flush();
						}
						break;
					case DISCONNECT:
						localStop.set(true);
						break;
					case CONNACK:
					case SUBACK:
					case UNSUBACK:
					case PINGRESP:
						throw new IOException("unexpected packet type: " + type);
				}
				connectTime.set(System.nanoTime() / 1000000L);
				if(!localStop.get() && remLen.get() != 0)
				{
					broker.getLogger().fine("[" + address + "] remaining length overflow, disconnecting");
					localStop.set(true);
				}
			}
		}
		catch (IOException e)
		{
			if(!localStop.get())
			{
				if (broker.exceptionHandler != null)
					broker.exceptionHandler.accept(e);
			}
		}
		finally
		{
			// must not be a planned disconnect
			if(!localStop.get() && will != null)
				broker.beginForward(will);

			close(true);
		}
	}

	private void handleConnectPacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		if(gotConnect.getAndSet(true))
		{
			broker.getLogger().fine("[" + address + "] duplicate connect, disconnecting");
			localStop.set(true);
			return;
		}
		// variable header
		byte[] bs = new byte[7];
		if(in.read(bs) != 7 || remLen.addAndGet(-7) < 0)
			throw new IOException(Common.EOF);
		byte[] expected = { 0x0, 0x4, 0x4D, 0x51, 0x54, 0x54, 0x4 };
		for (int i = 0; i < 7; i++)
		{
			if(bs[i] != expected[i])
			{
				broker.getLogger().fine("[" + address + "] Incorrect protocol or version, disconnecting with ack");
				sendConnackPacket(false, Common.ConnectReturn.UNACCEPTABLE_PROTOCOL_VERSION);
				localStop.set(true);
				return;
			}
		}
		boolean breach = false;
		int connectFlags = Common.read(in, remLen) & 0xFF;
		boolean hasUsername = ((connectFlags >> 7) & 1) != 0;
		boolean hasPassword = ((connectFlags >> 6) & 1) != 0;
		if(hasPassword && !hasUsername)
			breach = true;
		boolean hasWill = ((connectFlags >> 2) & 1) != 0;
		boolean willRetain = ((connectFlags >> 5) & 1) != 0;
		if(!hasWill && willRetain)
			breach = true;
		byte willQos = (byte) ((connectFlags >> 3) & 3);
		if(!hasWill && willQos != 0)
			breach = true;
		boolean cleanSession = ((connectFlags >> 1) & 1) != 0;
		if((connectFlags & 1) != 0)
			breach = true;

		if(breach)
		{
			broker.getLogger().fine("[" + address + "] Unexpected connect flags: " + MathUtil.byteBin(connectFlags));
			localStop.set(true);
			return;
		}
		int keepAlive = Common.readShort(in, remLen);

		// payload
		String clientID = Common.readUTFString(in, remLen);
		if(!Broker.isValidClientID(clientID) || !broker.engine.tryClientID(clientID))
		{
			broker.getLogger().fine("[" + address + "] invalid client identifier, disconnecting with ack");
			sendConnackPacket(false, Common.ConnectReturn.IDENTIFIER_REJECTED);
			localStop.set(true);
			return;
		}

		if(!hasUsername && broker.engine.requireUsername(clientID) || !hasPassword && broker.engine.requirePassword(clientID))
		{
			broker.getLogger().fine("[" + address + "] no username/password provided, disconnecting with ack");
			sendConnackPacket(false, Common.ConnectReturn.BAD_USERNAME_OR_PASSWORD);
			localStop.set(true);
			return;
		}

		String willTopic = null;
		byte[] willMessage = null;
		if(hasWill)
		{
			willTopic = Common.readUTFString(in, remLen);
			willMessage = Common.readBytes(in, remLen);

			if(!Session.isValidTopic(willTopic))
			{
				broker.getLogger().fine("[" + address + "] invalid will topic, disconnecting");
				localStop.set(true);
				return;
			}
		}

		String username = null;
		byte[] password = null;
		if(hasUsername)
		{
			username = Common.readUTFString(in, remLen);
			if(hasPassword)
				password = Common.readBytes(in, remLen);
		}

		if(remLen.get() != 0)
		{
			broker.getLogger().fine("[" + address + "] remaining length overflow, disconnecting");
			localStop.set(true);
			return;
		}

		if(broker.getLogger().isLoggable(Level.FINE))
		{
			broker.getLogger().fine("[" + address + "] Attempting with username: " + username + ", with" + (password == null ? " no" : "")
					+ " password, " + keepAlive + "s keep alive, and " + (!hasWill ? "no" : "with a") + " will");
		}

		if(!broker.getEngine().attemptAuthenticate(clientID, username, password))
		{
			broker.getLogger().fine("[" + address + "] failed authentication, disconnecting with ack");
			sendConnackPacket(false, Common.ConnectReturn.UNAUTHORIZED);
			localStop.set(true);
			return;
		}

		broker.getLogger().fine("[" + address + "] successfully authorized!");
		BrokerClientThread prevThread = broker.clientIDThreadMap.get(clientID);
		if(prevThread != null && clientID.equals(prevThread.sess().clientID))
		{
			broker.getLogger().info("[" + address + "] Client #" + prevThread.internalID + " has similar id, disconnecting");
			prevThread.close(false);
		}
		Session session = broker.getEngine().getSession(clientID);
		if(cleanSession || (session != null && session.hasExpired()))
			session = null;
		sendConnackPacket(session != null, Common.ConnectReturn.ACCEPTED);
		broker.getLogger().info("[" + address + "] Client #" + internalID + " connected with id: " + clientID + (username != null ? " and username: " + username : ""));

		this.keepAlive.set(keepAlive);
		if(session == null)
			session = broker.getEngine().createSession(clientID);

		if(hasWill)
			this.will = new Message(willTopic, willMessage, willQos, willRetain);

		currentSession.set(session);
		broker.clientIDThreadMap.put(clientID, this);
	}

	private void handlePublishPacket(InputStream in, AtomicInteger remLen, int flags) throws IOException
	{
		boolean dup = (flags & 8) != 0;
		int qos = (flags & 6) >> 1;
		boolean retain = (flags & 1) != 0;

		String topic = Common.readUTFString(in, remLen);

		if(!Session.isValidTopic(topic))
		{
			broker.getLogger().fine("[" + address + "] invalid publish topic, disconnecting");
			localStop.set(true);
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
				int len = Math.min(1024, size);
				Common.readBytes(in, arr, len);
				size -= len;
				out.write(arr, 0, len);
			}
			out.close();
			msg = new Message(topic, file, -1, qos, retain);
		}
		else
		{
			// read to memory, link to message
			byte[] arr = new byte[size];
			Common.readBytes(in, arr);

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
		Session sess = sess();

		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		PublishPacket.Transaction transaction = sess.unfinishedSend.remove(pid);
		if(transaction == null || transaction.qos != 1 || transaction.lastPacket != PacketType.PUBLISH)
		{
			broker.getLogger().warning("[" + address + "] unexpected PUBACK, disconnecting");
			localStop.set(true);
		}
	}

	private void handlePubrecPacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		Session sess = sess();

		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		PublishPacket.Transaction transaction = sess.unfinishedSend.get(pid);
		if(transaction == null || transaction.qos != 2 || transaction.lastPacket != PacketType.PUBLISH)
		{
			broker.getLogger().warning("[" + address + "] unexpected PUBREC, disconnecting");
			localStop.set(true);
		}
		else
		{
			sess.unfinishedSend.put(pid, transaction.setLastPacket(PacketType.PUBREL));

			OutputStream out = socket.getOutputStream();
			synchronized (writeLock)
			{
				if(broker.getLogger().isLoggable(Level.FINE))
					broker.getLogger().fine("[" + address + "] Sending packet: PUBREL (pid: " + pid + ")");
				Common.sendPubrel(out, pid);
			}
		}
	}

	private void handlePubrelPacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		Session sess = sess();

		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		PublishPacket.Transaction transaction = sess.unfinishedRecv.remove(pid);
		if(transaction == null || transaction.qos != 2 || transaction.lastPacket != PacketType.PUBREC)
		{
			broker.getLogger().warning("[" + address + "] unexpected PUBREL, disconnecting");
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

	private void handlePubcompPacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		Session sess = sess();

		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		PublishPacket.Transaction transaction = sess.unfinishedSend.remove(pid);
		if(transaction == null || transaction.qos != 2 || transaction.lastPacket != PacketType.PUBREL)
		{
			broker.getLogger().warning("unexpected PUBCOMP, disconnecting");
			localStop.set(true);
		}
	}

	private void handleSubscribePacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		Session sess = sess();

		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		if(remLen.get() == 0)
		{
			broker.getLogger().warning("[" + address + "] empty subscribe packet not allowed, disconnecting");
			localStop.set(true);
			return;
		}

		ByteArrayOutputStream returnCodes = new ByteArrayOutputStream();
		List<Message> retained = new ArrayList<>();
		do {
			String topicFilter = Common.readUTFString(in, remLen);
			if(!Session.isValidTopicFilter(topicFilter))
			{
				broker.getLogger().warning("[" + address + "] invalid topic filter, disconnecting");
				localStop.set(true);
				return;
			}
			int desiredQos = Common.read(in, remLen);
			if(desiredQos < 0 || desiredQos > 2)
			{
				broker.getLogger().warning("[" + address + "] qos for subscribe is out of bounds: " + desiredQos + ", disconnecting");
				localStop.set(true);
				return;
			}
			if(broker.getEngine().canSubscribe(sess, topicFilter))
			{
				int maxQos = broker.getEngine().transformDesiredQos(sess, topicFilter, desiredQos);
				switch (maxQos)
				{
					case 0:
					case 1:
					case 2:
					case 128:
						desiredQos = maxQos;
						break;
					default:
						desiredQos = 0x80;
						broker.getLogger().severe("[" + address + "] engine should only return 0x0, 0x1, 0x2, or 0x80, defaulting to 0x80");
				}

				returnCodes.write(desiredQos);
				if(desiredQos != 0x80)
				{
					sess.subscribe(topicFilter, desiredQos);
					broker.matchRetained(retained, topicFilter);
				}
			}
			else
				returnCodes.write(0x80);
		} while(remLen.get() > 0);

		OutputStream out = socket.getOutputStream();

		synchronized (writeLock)
		{
			if(broker.getLogger().isLoggable(Level.FINE))
				broker.getLogger().fine("[" + address + "] Sending packet: SUBACK for " + returnCodes.size() + " topic(s)");

			out.write(0x90);
			Common.writeRemainingField(out, 2 + returnCodes.size());
			Common.writeShort(out, pid.get());
			returnCodes.writeTo(out);
			out.flush();
		}

		for (Message message : retained)
			publish(message, message.getQos(), true);
	}

	private void handleUnsubscribePacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		Session sess = sess();

		Common.PacketID pid = new Common.PacketID(Common.readShort(in, remLen));
		if(remLen.get() == 0)
		{
			broker.getLogger().warning("[" + address + "] empty unsubscribe packet not allowed, disconnecting");
			localStop.set(true);
			return;
		}

		String topicFilter;
		do {
			topicFilter = Common.readUTFString(in, remLen);
			if(!Session.isValidTopicFilter(topicFilter))
			{
				broker.getLogger().warning("[" + address + "] invalid topic filter, disconnecting");
				localStop.set(true);
				return;
			}
			sess.unsubscribe(topicFilter);
		} while(remLen.get() > 0);

		OutputStream out = socket.getOutputStream();

		synchronized (writeLock)
		{
			if(broker.getLogger().isLoggable(Level.FINE))
				broker.getLogger().fine("[" + address + "] Sending packet: UNSUBACK");

			out.write(0xB0);
			Common.writeRemainingField(out, 2);
			Common.writeShort(out, pid.get());
			out.flush();
		}
	}

	private void sendConnackPacket(boolean sessionPresent, @NotNull Common.ConnectReturn result) throws IOException
	{
		OutputStream out = socket.getOutputStream();

		synchronized (writeLock)
		{
			broker.getLogger().fine("[" + address + "] Sending packet: CONNACK " + result + (result == Common.ConnectReturn.ACCEPTED ? ", SP: " + sessionPresent : ""));
			out.write(0x20);
			out.write(0x2);

			if(result == Common.ConnectReturn.ACCEPTED)
			{
				out.write(sessionPresent ? 0x1 : 0x0);
				out.write(0x0);
			}
			else
			{
				out.write(0x0);
				out.write(result.ordinal());
			}
			out.flush();
		}
	}

	void publish(Message message, int qos, boolean retain)
	{
		try
		{
			Common.PacketID pid = nextPid();
			Session sess = sess();

			PublishPacket packet = new PublishPacket(message, pid, broker.getLogger(), socket.getOutputStream(), writeLock);
			packet.setPrefix("[" + address + "] ");
			packet.setExceptionHandler(broker.exceptionHandler);
			packet.setDesiredQos(qos);
			packet.setDesiredRetain(retain);
			packet.setOnComplete(transaction -> {
				if (qos > 0)
					sess.unfinishedSend.put(pid, transaction);
			});
			broker.executorService.submit(packet);
		}
		catch (IOException e)
		{
			if(broker.exceptionHandler != null)
				broker.exceptionHandler.accept(e);
		}
	}

	public void checkGotConnect()
	{
		long elapsed = System.nanoTime() / 1000000L - connectTime.get();
		if(gotConnect.get() && elapsed > keepAlive.get() * 1500L)
		{
			broker.getLogger().fine("[" + address + "] exceeded keep-alive by %150, disconnecting");
			close(false);
		}
		else if(!gotConnect.get() && elapsed > broker.options.connectWaitTimeout)
		{
			broker.getLogger().fine("[" + address + "] did not receive CONNECT in time, disconnecting");
			close(false);
		}

		Session sess = sess();
		if(sess != null)
		{
			synchronized (sess.unfinishedSend)
			{
				for (Map.Entry<Common.PacketID, PublishPacket.Transaction> entry : sess.unfinishedSend.entrySet())
				{
					Common.PacketID key = entry.getKey();
					PublishPacket.Transaction value = entry.getValue();
					if (value.isPast(broker.options.qosAckTimeout))
					{
						broker.getLogger().warning("[" + address + "] did not receive ACK in time, disconnecting");
						close(false);
						return;
					}
				}
			}
			synchronized (sess.unfinishedRecv)
			{
				for (Map.Entry<Common.PacketID, PublishPacket.Transaction> entry : sess.unfinishedRecv.entrySet())
				{
					Common.PacketID key = entry.getKey();
					PublishPacket.Transaction value = entry.getValue();
					if (value.isPast(broker.options.qosAckTimeout))
					{
						broker.getLogger().warning("[" + address + "] did not receive ACK in time, disconnecting");
						close(false);
						return;
					}
				}
			}
		}
	}

	Session sess()
	{
		return currentSession.get();
	}

	private synchronized Common.PacketID nextPid()
	{
		if(sess() == null)
			throw new IllegalStateException("no need for a pid before a session has be established");

		Common.PacketID a;
		do
		{
			packetID = packetID == 65535 ? 0 : packetID + 1;
			a = new Common.PacketID(packetID);
		} while(sess().unfinishedSend.containsKey(a));
		return a;
	}

	void close(boolean now)
	{
		localStop.set(true);
		if(socket.isClosed() || sentClose.get())
		{
			sentClose.set(true);
			return;
		}

		sentClose.set(true);
		Runnable closer = () -> {
			try
			{
				if (!socket.isClosed())
					socket.close();
			}
			catch (IOException e)
			{
				if(broker.exceptionHandler != null)
					broker.exceptionHandler.accept(e);
			}
			finally
			{
				if(broker.removeClient(this))
					broker.getLogger().info("[" + address + "] Client disconnected: #" + internalID);
			}
		};

		if(now)
			closer.run();
		else
			broker.executorService.submit(closer);
	}
}
