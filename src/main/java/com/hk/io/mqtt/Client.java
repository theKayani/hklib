package com.hk.io.mqtt;

import com.hk.math.MathUtil;
import com.hk.math.Rand;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client
{
	private final InetSocketAddress host;
	public final ClientOptions options;
	private final Logger logger;
	ScheduledExecutorService executorService;
	final AtomicReference<Status> status;
	final Map<Common.PacketID, PublishPacket.Transaction> unfinishedSend;
	final Map<Common.PacketID, PublishPacket.Transaction> unfinishedRecv;
	int packetID;
	Consumer<IOException> exceptionHandler;
	@NotNull
	String clientID;
	@Nullable
	String username;
	byte @Nullable [] password;
	@Nullable
	Message lastWill;
	boolean cleanSession;
	int keepAlive;
	private Socket socket;
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
		this.options = new ClientOptions();

		clientID = randomClientID();
		cleanSession = true;
		keepAlive = 60;
		status = new AtomicReference<>(Status.NOT_CONNECTED);
		globalStop = new AtomicBoolean(false);
		packetID = -1;
		logger = Logger.getLogger("MQTT-Client");
		logger.setLevel(Level.INFO);
		logger.setUseParentHandlers(false);
		logger.addHandler(new ConsoleHandler());
		unfinishedSend = Collections.synchronizedMap(new HashMap<>());
		unfinishedRecv = Collections.synchronizedMap(new HashMap<>());
	}

	public String getID()
	{
		return clientID;
	}

	@Contract("-> this")
	public Client setDefaultID()
	{
		return setID(randomClientID());
	}

	@Contract("_ -> this")
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

	@Contract("-> this")
	public Client setNoUsername()
	{
		return setUsername(null);
	}

	@Contract("_ -> this")
	public Client setUsername(@Nullable String username)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change username after attempting to connect");

		this.username = username;
		return this;
	}

	@Contract("-> this")
	public Client setNoPassword()
	{
		this.password = null;
		return this;
	}

	@Contract("_ -> this")
	public Client setPassword(@Nullable String password)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change password after attempting to connect");

		if(password == null)
			return setNoPassword();

		this.password = password.getBytes(StandardCharsets.UTF_8);
		return this;
	}

	@Contract("_ -> this")
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
	public Message getLastWill()
	{
		if(status.get() == Status.NOT_CONNECTED || lastWill == null)
			return lastWill;
		else
			return lastWill.clone();
	}

	@Contract("_ -> this")
	public Client setLastWill(@Nullable Message lastWill)
	{
		if (lastWill != null)
		{
			if (lastWill.getSize() == -1)
				throw new IllegalStateException("last will message cannot be unknown");
			else if (lastWill.getSize() > 65535)
				throw new IllegalStateException("last will message is too large");
		}
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change last will after attempting to connect");

		this.lastWill = lastWill;
		return this;
	}

	public boolean getCleanSession()
	{
		return cleanSession;
	}

	@Contract("-> this")
	public Client setToCleanSession()
	{
		return setCleanSession(true);
	}

	@Contract("_ -> this")
	public Client setCleanSession(boolean cleanSession)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change clean session flag after attempting to connect");

		this.cleanSession = cleanSession;
		return this;
	}

	@Range(from=1, to=65535)
	public int getKeepAlive()
	{
		return keepAlive;
	}

	@Contract("_ -> this")
	public Client setKeepAlive(@Range(from=1, to=65535) int keepAlive)
	{
		if(status.get() != Status.NOT_CONNECTED)
			throw new IllegalStateException("cannot change keep alive timeout after attempting to connect");

		if(keepAlive < 1 || keepAlive > 65535)
			throw new IllegalArgumentException("keep alive timeout should be 1 to 65535");
		this.keepAlive = keepAlive;
		return this;
	}

	@Nullable
	public Consumer<IOException> getExceptionHandler()
	{
		return exceptionHandler;
	}

	@Contract("_ -> this")
	public Client setDefaultExceptionHandler(@NotNull String message)
	{
		return setExceptionHandler(new Common.DefaultExceptionHandler(message, logger));
	}

	@Contract("-> this")
	public Client setDefaultExceptionHandler()
	{
		return setExceptionHandler(new Common.DefaultExceptionHandler("MQTT Client", logger));
	}

	@Contract("_ -> this")
	public Client setExceptionHandler(@Nullable Consumer<IOException> exceptionHandler)
	{
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	@Contract("_ -> this")
	public Client setLogLevel(@NotNull Level level)
	{
		logger.setLevel(Objects.requireNonNull(level));
		for (Handler handler : logger.getHandlers())
			handler.setLevel(level);
		return this;
	}

	@NotNull
	public Logger getLogger()
	{
		return logger;
	}

	@NotNull
	public Status getStatus()
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

			logger.info("Creating" + (options.useSSL ? " SSL" : "") + " socket");
			if(options.isUsingSSL())
				socket = SSLSocketFactory.getDefault().createSocket();
			else
				socket = new Socket();

			status.set(Status.CONNECTING);
			logger.warning("Attempting to connect to host: " + host);
			socket.connect(host);
			logger.fine("Socket: " + socket);

			status.set(Status.CONNECTED);
			executorService = Executors.newSingleThreadScheduledExecutor();
			clientThread = new ClientThread(this, socket, keepAlive);
			clientThread.setDaemon(true);
			clientThread.start();
			logger.fine("Starting thread for socket");

			executorService.submit(clientThread::sendConnectPacket);
			executorService.scheduleWithFixedDelay(clientThread::checkGotConnect, 0, 1, TimeUnit.SECONDS);
		}
		catch (IOException e)
		{
			if(exceptionHandler != null)
				exceptionHandler.accept(e);
		}
	}

	/**
	 * <p>Time how long it takes to send a PINGREQ packet and receive a
	 * PINGRESP packet back.</p>
	 *
	 * <p>The result is the total transaction time in milliseconds.</p>
	 *
	 * <p>THIS METHOD BLOCKS UNTIL A RESPONSE IS RECEIVED</p>
	 *
	 * @return the time in milliseconds or -1 if PINGRESP wasn't received
	 */
	public double ping()
	{
		if(status.get() != Status.AUTHORIZED)
			return -1D;

		long start = System.nanoTime();
		synchronized (clientThread.lastPacket)
		{
			clientThread.sendPingRequestPacket();

			long waitEnd = System.nanoTime() / 1000000L + 10000;
			do {
				try
				{
					clientThread.lastPacket.wait(Math.max(100, waitEnd - System.nanoTime() / 1000000L));
					break;
				}
				catch (InterruptedException e)
				{
					logger.log(Level.WARNING, "interrupted waiting for ping response", e);
				}
			} while (waitEnd > System.nanoTime() / 1000000L);
		}

		if(clientThread.lastPacket.get() == PacketType.PINGRESP)
			return (System.nanoTime() - start) / 1E6D;
		else
			return -1D;
	}

	public boolean publish(@NotNull String topic, @NotNull String message)
	{
		return publish(new Message(topic, message, options.defaultQos.get(), options.defaultRetain.get()));
	}

	public boolean publish(@NotNull String topic, byte @NotNull [] message)
	{
		return publish(new Message(topic, message, options.defaultQos.get(), options.defaultRetain.get()));
	}

	public boolean publish(@NotNull String topic, @NotNull String message, @Range(from=0, to=2) int qos, boolean retain)
	{
		return publish(new Message(topic, message, qos, retain));
	}

	public boolean publish(@NotNull String topic, byte @NotNull [] message, @Range(from=0, to=2) int qos, boolean retain)
	{
		return publish(new Message(topic, message, qos, retain));
	}

	@SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
	public boolean publish(@NotNull Message message)
	{
		if(!options.exploitFreedom && !isAuthorized())
			throw new IllegalStateException("client hasn't authenticated with broker yet");
		if(!isConnected())
			throw new IllegalStateException("client hasn't connected with broker yet");

		Objects.requireNonNull(message);

		if (options.waitForPubAck)
		{
			Common.PacketID pid = nextPid();
			synchronized (pid)
			{
				clientThread.publish(message, pid);
				long waitEnd = System.nanoTime() / 1000000L + options.qosAckTimeout;

				do {
					try
					{
						pid.wait(Math.max(100, waitEnd - System.nanoTime() / 1000000L));
						break;
					}
					catch (InterruptedException e)
					{
						logger.log(Level.WARNING, "interrupted waiting for publish response", e);
					}
				} while (waitEnd > System.nanoTime() / 1000000L);
				return !unfinishedSend.containsKey(pid);
			}
		}
		else
		{
			clientThread.publish(message, message.getQos() == 0 ? null : nextPid());
			return true;
		}
	}

	private synchronized Common.PacketID nextPid()
	{
		Common.PacketID a;
		do
		{
			packetID = packetID == 65535 ? 0 : packetID + 1;
			a = new Common.PacketID(packetID);
		} while(unfinishedSend.containsKey(a));
		return a;
	}

	public void disconnect(boolean hard)
	{
		if(clientThread != null)
			clientThread.close(hard);
	}

	public boolean isConnected()
	{
		return clientThread != null && socket.isConnected();
	}

	public boolean isAuthorized()
	{
		return clientThread != null && clientThread.gotConnAck.get() && status.get() == Status.AUTHORIZED;
	}

	public boolean hasPriorSession()
	{
		if(clientThread.gotConnAck.get())
			return clientThread.hasPriorSession;
		else
			throw new IllegalStateException("Client has not received ack (CONNACK)");
	}

	public Common.ConnectReturn getConnectReturn()
	{
		if(clientThread.gotConnAck.get())
			return clientThread.connectReturn;
		else
			throw new IllegalStateException("Client has not received ack (CONNACK)");
	}

	@NotNull
	static String randomClientID()
	{
		return "j" + MathUtil.shortHex(Rand.nextShort()) + MathUtil.longHex(System.currentTimeMillis());
	}

	public enum Status
	{
		AUTHORIZED, CONNECTED, CONNECTING, DISCONNECTED, DISCONNECTING, NOT_CONNECTED
	}
}
