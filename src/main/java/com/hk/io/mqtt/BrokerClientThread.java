package com.hk.io.mqtt;

import com.hk.math.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.file.Files;
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
	final AtomicReference<Session> currentSession;
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
			while(!localStop.get())
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

				int remLen = Common.readRemainingField(in);
				if(broker.getLogger().isLoggable(Level.FINER))
					broker.getLogger().finer("[" + address + "] Received packet: " + type + ", remaining length: " + remLen);

				if(type != PacketType.CONNECT && currentSession == null)
				{
					broker.getLogger().fine("[" + address + "] unexpected publish before auth, disconnecting");
					localStop.set(true);
					break;
				}

				switch (type)
				{
					case CONNECT:
						handleConnectPacket(in, new AtomicInteger(remLen));
						break;
					case PUBLISH:
						handlePublishPacket(in, new AtomicInteger(remLen), b & 0xF);
						break;
					case PUBACK:
						throw new Error("TODO PUBACK");
					case PUBREC:
						throw new Error("TODO PUBREC");
					case PUBREL:
						throw new Error("TODO PUBREL");
					case PUBCOMP:
						throw new Error("TODO PUBCOMP");
					case SUBSCRIBE:
						throw new Error("TODO SUBSCRIBE");
					case UNSUBSCRIBE:
						throw new Error("TODO UNSUBSCRIBE");
					case PINGREQ:
						broker.getLogger().fine("[" + address + "] Sending packet: PINGRESP");
						OutputStream out = socket.getOutputStream();

						synchronized (writeLock)
						{
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
			}
		}
		catch (IOException e)
		{
			if(!localStop.get())
			{
				broker.beginForward(will);

				if (broker.exceptionHandler != null)
					broker.exceptionHandler.accept(e);
			}
			// otherwise, must be a planned disconnect
		}
		finally
		{
			localStop.set(true);
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
		byte[] expected = { 0x0, 0x4, 0x4D, 0x51, 0x55, 0x55, 0x4 };
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
		AtomicInteger pid = null;
		if(qos > 0)
			pid = new AtomicInteger(Common.readShort(in, remLen));

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

		if(broker.getLogger().isLoggable(Level.FINEST))
			broker.getLogger().finest("[" + address + "] received: " + msg);

		if(qos == 1)
		{
			broker.getLogger().fine("[" + address + "] Sending packet: PUBACK (pid: " + MathUtil.shortHex(pid.get()) + ")");
			OutputStream out = socket.getOutputStream();

			// forward to subscribers
			broker.beginForward(msg);

			synchronized (writeLock)
			{
				Common.sendPuback(out, pid);
			}
		}
		else if(qos == 2)
		{
			broker.getLogger().fine("[" + address + "] Sending packet: PUBREC (pid: " + MathUtil.shortHex(pid.get()) + ")");
			OutputStream out = socket.getOutputStream();

			// await pubrel before forwarding

			synchronized (writeLock)
			{
				Common.sendPubrec(out, pid);
				sess().unfinishedRecv.put(pid, new PublishPacket.Transaction(msg, pid).setLastPacket(PacketType.PUBREC));
			}
		}
		else
			throw new AssertionError("should not be possible");
	}

	private void sendConnackPacket(boolean sessionPresent, @NotNull Common.ConnectReturn result) throws IOException
	{
		broker.getLogger().fine("[" + address + "] Sending packet: CONNACK " + result + (result == Common.ConnectReturn.ACCEPTED ? ", SP: " + sessionPresent : ""));
		OutputStream out = socket.getOutputStream();

		synchronized (writeLock)
		{
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
	}

	Session sess()
	{
		return currentSession.get();
	}

	private synchronized AtomicInteger nextPid()
	{
		if(sess() == null)
			throw new IllegalStateException("no need for a pid before a session has be established");

		do
		{
			packetID = packetID == 65535 ? 0 : packetID + 1;
		} while(sess().unfinishedSend.containsKey(new AtomicInteger(packetID)));
		return new AtomicInteger(packetID);
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
