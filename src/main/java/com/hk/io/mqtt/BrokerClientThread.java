package com.hk.io.mqtt;

import com.hk.math.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

class BrokerClientThread extends Thread
{
	private final Broker broker;
	private final Socket client;
	private final AtomicBoolean globalStop, localStop, sentClose;
	private final SocketAddress address;
	private final int internalID;

	BrokerClientThread(Broker broker, Socket client, int internalID)
	{
		this.broker = broker;
		this.client = client;
		globalStop = broker.globalStop;
		localStop = new AtomicBoolean(false);
		sentClose = new AtomicBoolean(false);
		address = client.getRemoteSocketAddress();
		this.internalID = internalID;
	}

	@Override
	public void run()
	{
		try
		{
			InputStream in = client.getInputStream();
			byte b;
			while(!globalStop.get() && !localStop.get())
			{
				// fixed header
				b = Common.read(in);
				PacketType type = PacketType.getFromHeader(b);

				if(type == null)
				{
					if(broker.getLogger().isLoggable(Level.WARNING))
						broker.getLogger().warning("[" + address + "] Unknown packet header: " + MathUtil.byteBin(b & 0xFF));
					client.close();
					break;
				}

				int remLen = Common.readRemainingField(in);
				if(broker.getLogger().isLoggable(Level.FINER))
					broker.getLogger().finer("[" + address + "] Received packet: " + type + ", remaining length: " + remLen);
				switch (type)
				{
					case CONNECT:
						handleConnectPacket(in, new AtomicInteger(remLen));
						break;
					case PUBLISH:
//						handlePublishPacket(in, remLen, b);
						throw new Error("TODO PUBLISH");
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
						throw new Error("TODO PINGREQ");
					case DISCONNECT:
						throw new Error("TODO DISCONNECT");
					case CONNACK:
					case SUBACK:
					case UNSUBACK:
					case PINGRESP:
						throw new IOException("unexpected packet type: " + type);
				}
			}
		}
		catch (IOException e)
		{
			// TODO: publish last will testament
			if(broker.exceptionHandler != null)
				broker.exceptionHandler.accept(e);
		}
		finally
		{
			localStop.set(true);
			close(true);
		}
	}

	private void handleConnectPacket(InputStream in, AtomicInteger remLen) throws IOException
	{
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

		System.out.println("hasWill = " + hasWill);
		System.out.println("willQos = " + willQos);
		System.out.println("willRetain = " + willRetain);
		System.out.println("keepAlive = " + keepAlive);

		// payload
		String clientID = Common.readUTFString(in, remLen);
		if(!Broker.isValidClientID(clientID) || !broker.engine.tryClientID(clientID))
		{
			broker.getLogger().fine("[" + address + "] invalid client identifier, disconnecting with ack");
			sendConnackPacket(false, Common.ConnectReturn.IDENTIFIER_REJECTED);
			localStop.set(true);
			return;
		}
		System.out.println("clientID = " + clientID);

		if(!hasUsername && broker.engine.requireUsername(clientID) || !hasPassword && broker.engine.requirePassword(clientID))
		{
			broker.getLogger().fine("[" + address + "] no username/password provided, disconnecting with ack");
			sendConnackPacket(false, Common.ConnectReturn.BAD_USERNAME_OR_PASSWORD);
			localStop.set(true);
			return;
		}

		String willTopic;
		byte[] willMessage;
		if(hasWill)
		{
			willTopic = Common.readUTFString(in, remLen);
			willMessage = Common.readBytes(in, remLen);

			System.out.println("willTopic = " + willTopic);
			System.out.println("willMessage = " + Arrays.toString(willMessage));
		}

		String username = null;
		byte[] password = null;
		if(hasUsername)
		{
			username = Common.readUTFString(in, remLen);
			System.out.println("username = " + username);
			if(hasPassword)
			{
				password = Common.readBytes(in, remLen);
				System.out.println("password = " + Arrays.toString(password));
			}
		}

		if(remLen.get() != 0)
		{
			broker.getLogger().fine("[" + address + "] remaining length overflow, disconnecting");
			localStop.set(true);
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
		}
	}

	void sendConnackPacket(boolean sessionPresent, @NotNull Common.ConnectReturn result) throws IOException
	{
		broker.getLogger().fine("[" + address + "] Sending packet CONNACK: " + result + (result == Common.ConnectReturn.ACCEPTED ? ", SP: " + sessionPresent : ""));
		OutputStream out = client.getOutputStream();

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

	void close(boolean now)
	{
		if(client.isClosed() || sentClose.get())
			return;

		localStop.set(true);
		sentClose.set(true);
		Runnable closer = () -> {
			try
			{
				if (!client.isClosed())
					client.close();
			}
			catch (IOException e)
			{
				if(broker.exceptionHandler != null)
					broker.exceptionHandler.accept(e);
			}
			finally
			{
				if(broker.currentClients.remove(this) && broker.getLogger().isLoggable(Level.FINE))
					broker.getLogger().fine("[" + address + "] Client disconnected: #" + internalID);
			}
		};

		if(now)
			closer.run();
		else
			broker.executorService.submit(closer);
	}
}
