package com.hk.io.mqtt;

import com.hk.math.MathUtil;
import com.hk.math.Rand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class Client
{
	private final InetSocketAddress host;
	private final ScheduledExecutorService executorService;
	private final AtomicReference<Status> status;
	Consumer<IOException> exceptionHandler;
	@NotNull
	private String clientID;
	@Nullable
	private String username;
	private byte @Nullable [] password;
	@Nullable
	private Will lastWill;
	private boolean cleanSession;
	private int keepAlive;
	private Socket client;
	private ClientThread clientThread;
	private AtomicBoolean hardStop;
	AtomicBoolean globalStop;

	public Client(String host, int port)
	{
		this(new InetSocketAddress(host, port));
	}

	public Client(InetSocketAddress host)
	{
		this.host = host;
		executorService = Executors.newSingleThreadScheduledExecutor();

		clientID = randomClientID();
		cleanSession = true;
		keepAlive = 60;
		status = new AtomicReference<>(Status.NOT_CONNECTED);
		globalStop = new AtomicBoolean(false);
	}

	public String getID()
	{
		return clientID;
	}

	public Client setDefaultID()
	{
		return setID(randomClientID());
	}

	public Client setID(@NotNull String clientID)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change client ID after attempting to connect");

		this.clientID = clientID;
		return this;
	}

	@Nullable
	public String getUsername()
	{
		return username;
	}

	public Client setNoUsername()
	{
		return setUsername(null);
	}

	public Client setUsername(@Nullable String username)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change username after attempting to connect");

		this.username = username;
		return this;
	}

	public Client setNoPassword()
	{
		this.password = null;
		return this;
	}

	public Client setPassword(@Nullable String password)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change password after attempting to connect");

		if(password == null)
			return setNoPassword();

		this.password = password.getBytes(StandardCharsets.UTF_8);
		return this;
	}

	public Client setRawPassword(byte @Nullable [] password)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change password after attempting to connect");

		if(password == null)
			return setNoPassword();

		this.password = Arrays.copyOf(password, password.length);
		return this;
	}

	@Nullable
	public Will getLastWill()
	{
		return lastWill;
	}

	public Client setLastWill(@Nullable Will lastWill)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change last will after attempting to connect");

		this.lastWill = lastWill;
		return this;
	}

	public boolean getCleanSession()
	{
		return cleanSession;
	}

	public Client setToCleanSession()
	{
		return setCleanSession(true);
	}

	public Client setCleanSession(boolean cleanSession)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change clean session flag after attempting to connect");

		this.cleanSession = cleanSession;
		return this;
	}

	public int getKeepAlive()
	{
		return keepAlive;
	}

	public void setKeepAlive(int keepAlive)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change keep alive timeout after attempting to connect");

		if(keepAlive < 1 || keepAlive > 65535)
			throw new IllegalArgumentException("keep alive timeout should be 1 to 65535");
		this.keepAlive = keepAlive;
	}

	public Client setDefaultExceptionHandler()
	{
		return setExceptionHandler(Common.DefaultExceptionHandler.INSTANCE);
	}

	public Client setExceptionHandler(@Nullable Consumer<IOException> exceptionHandler)
	{
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	public Client.Status getStatus()
	{
		return status.get();
	}

	public void connect()
	{
		if(password != null && username == null)
			throw new IllegalStateException("client username missing but has password?");

		try
		{
			if(status.get() != Status.NOT_CONNECTED && status.get() != Status.DISCONNECTED)
				throw new IllegalStateException("client already tried on socket");

			client = new Socket();

			status.set(Status.CONNECTING);
			client.connect(host);
			System.out.println("\nclient.getRemoteSocketAddress() = " + client.getRemoteSocketAddress());

			status.set(Status.CONNECTED);
			clientThread = new ClientThread(this, client);
			clientThread.setDaemon(true);
			clientThread.start();

			executorService.submit(this::sendConnectPacket);
		}
		catch (IOException e)
		{
			if(exceptionHandler != null)
				exceptionHandler.accept(e);
		}
	}

	private void sendConnectPacket()
	{
		try
		{
			OutputStream out = client.getOutputStream();

			// fixed header
			out.write(0x10);
			ByteArrayOutputStream bout = new ByteArrayOutputStream(256);
			byte[] bs;

			// variable header

			// protocol name and level
			bout.write(0x0);
			bout.write(0x4);
			bout.write(0x4D); // M
			bout.write(0x51); // Q
			bout.write(0x55); // T
			bout.write(0x55); // T
			bout.write(0x4); // v3.1.1

			// connect flags
			int connectFlags = 0;
			if(cleanSession)
				connectFlags |= 2;

			if(lastWill != null)
			{
				connectFlags |= 4;
				connectFlags |= (lastWill.qos << 3);
				if(lastWill.retain)
					connectFlags |= 32;
			}
			if(username != null)
			{
				connectFlags |= 64;
				if (password != null)
					connectFlags |= 128;
			}

			bout.write(connectFlags & 0xFF);

			// keep-alive
			Common.writeShort(bout, keepAlive);

			// payload
			Common.writeUTFString(bout, clientID);

			if(lastWill != null)
			{
				Common.writeUTFString(bout, lastWill.topic);
				Common.writeBytes(bout, lastWill.message);
			}

			if(username != null)
			{
				Common.writeUTFString(bout, username);

				if(password != null)
					Common.writeBytes(bout, password);
			}

			// remaining field
			Common.writeRemainingField(out, bout.size());
			bout.writeTo(out);
		}
		catch (IOException e)
		{
			if(exceptionHandler != null)
				exceptionHandler.accept(e);
		}
	}

	public void disconnect(boolean soft)
	{
		globalStop.set(true);
		hardStop = new AtomicBoolean(!soft);
		if(!soft)
		{
			try
			{
				client.close();
			}
			catch (IOException e)
			{
				if(exceptionHandler != null)
					exceptionHandler.accept(e);
			}
		}
	}

	public boolean isConnected()
	{
		return client != null && client.isConnected();
	}

	@NotNull
	static String randomClientID()
	{
		return "j" + MathUtil.shortHex(Rand.nextShort()) + MathUtil.longHex(System.currentTimeMillis());
	}

	public static class Will
	{
		@NotNull
		private final String topic;
		private final byte @NotNull [] message;
		private byte qos;
		private boolean retain;

		/**
		 * default will with a QOS of 1 and to retain flag off
		 *
		 * @param topic the will topic to publish
		 * @param message the will message to publish
		 */
		public Will(@NotNull String topic, @NotNull String message)
		{
			this(topic, message, 1, false);
		}

		/**
		 * default will with a QOS of 1 and to retain flag off
		 *
		 * @param topic the will topic to publish
		 * @param message the will message to publish
		 */
		public Will(@NotNull String topic, byte @NotNull [] message)
		{
			this(topic, message, 1, false);
		}

		public Will(@NotNull String topic, @NotNull String message, int qos, boolean retain)
		{
			this.topic = Objects.requireNonNull(topic);
			this.message = message.getBytes(StandardCharsets.UTF_8);
			setQos(qos).setRetain(retain);
		}

		public Will(@NotNull String topic, byte @NotNull [] message, int qos, boolean retain)
		{
			this.topic = Objects.requireNonNull(topic);
			this.message = Arrays.copyOf(message, message.length);
			setQos(qos).setRetain(retain);
		}

		public int getQos()
		{
			return qos;
		}

		public Will setNoQos()
		{
			return setQos(0);
		}

		public Will setQos(int qos)
		{
			if(qos < 0 || qos > 2)
				throw new IllegalArgumentException("qos must be between 0 and 2");
			this.qos = (byte) qos;
			return this;
		}

		public boolean isRetain()
		{
			return retain;
		}

		public Will setToRetain()
		{
			return setRetain(true);
		}

		public Will setNotRetain()
		{
			return setRetain(false);
		}

		public Will setRetain(boolean retain)
		{
			this.retain = retain;
			return this;
		}

		@NotNull
		public String getTopic()
		{
			return topic;
		}

		public byte @NotNull [] getRawMessage()
		{
			return message;
		}

		@NotNull
		public String getMessage()
		{
			return new String(message, StandardCharsets.UTF_8);
		}
	}

	public enum Status
	{
		AUTHORIZED, CONNECTED, CONNECTING, DISCONNECTED, DISCONNECTING, NOT_CONNECTED
	}
}
