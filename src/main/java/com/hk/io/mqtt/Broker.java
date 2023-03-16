package com.hk.io.mqtt;

import com.hk.io.mqtt.engine.BasicMQTTEngine;
import com.hk.io.mqtt.engine.MQTTEngine;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates a MQTT Protocol Broker that blocks upon calling the {@link #run()}
 * method.
 */
public class Broker implements Runnable
{
	private final InetSocketAddress host;
	final List<BrokerClientThread> currentClients;
	public final BrokerOptions options;
	private final AtomicReference<Status> status;
	private final Logger logger;
	Consumer<IOException> exceptionHandler;
	ScheduledExecutorService executorService;
	MQTTEngine engine;
	private ServerSocket socket;
	private Consumer<ServerSocket> handler;
	private AtomicBoolean hardStop;
	AtomicBoolean globalStop;
	private int nextID;

	public Broker(int port)
	{
		this(new InetSocketAddress(port));
	}

	public Broker(String host, int port)
	{
		this(new InetSocketAddress(host, port));
	}

	public Broker(InetSocketAddress host)
	{
		this.host = host;
		this.options = new BrokerOptions();
		currentClients = Collections.synchronizedList(new ArrayList<>());
		status = new AtomicReference<>(Status.NOT_BOUND);
		logger = Logger.getLogger("MQTT-Broker");
		logger.setLevel(Level.INFO);
		logger.setUseParentHandlers(false);
		logger.addHandler(new ConsoleHandler());
	}

	public MQTTEngine getEngine()
	{
		return engine;
	}

	@Contract("_ -> this")
	public Broker setEngine(MQTTEngine engine)
	{
		if(status.get() != Status.NOT_BOUND)
			throw new IllegalStateException("Broker already started!");

		this.engine = engine;
		return this;
	}

	@Contract("_ -> this")
	public Broker setSocketHandler(@Nullable Consumer<ServerSocket> handler)
	{
		this.handler = handler;
		return this;
	}

	@Nullable
	public Consumer<IOException> getExceptionHandler()
	{
		return exceptionHandler;
	}

	@Contract("_ -> this")
	public Broker setDefaultExceptionHandler(String message)
	{
		return setExceptionHandler(new Common.DefaultExceptionHandler(message, logger));
	}

	@Contract("-> this")
	public Broker setDefaultExceptionHandler()
	{
		return setExceptionHandler(new Common.DefaultExceptionHandler("MQTT Broker", logger));
	}

	@Contract("_ -> this")
	public Broker setExceptionHandler(@Nullable Consumer<IOException> exceptionHandler)
	{
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	@Contract("_ -> this")
	public Broker setLogLevel(@NotNull Level level)
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

	public ScheduledExecutorService getScheduler()
	{
		return executorService;
	}

	@NotNull
	public Status getStatus()
	{
		return status.get();
	}

	public void run()
	{
		if(status.get() != Status.NOT_BOUND)
			throw new IllegalStateException("Broker already tried on socket");

		if(engine == null)
			engine = new BasicMQTTEngine();

		try
		{
			logger.info("Creating" + (options.useSSL ? " SSL" : "") + " socket");

			if(options.useSSL)
				socket = SSLServerSocketFactory.getDefault().createServerSocket();
			else
				socket = new ServerSocket();

			if(handler != null)
			{
				if(logger.isLoggable(Level.FINE))
					logger.fine("Calling handler with socket: " + handler);
				handler.accept(socket);
			}

			status.set(Status.BINDING);

			if(socket.isBound())
			{
				socket = null;
				throw new IllegalStateException("socket bound before starting broker");
			}

			globalStop = new AtomicBoolean(false);
			executorService = Executors.newScheduledThreadPool(options.threadPoolSize);
			executorService.scheduleWithFixedDelay(() -> forAll(BrokerClientThread::checkGotConnect), 0, 1, TimeUnit.SECONDS);
			logger.fine("Creating thread pool: " + options.threadPoolSize);
			logger.warning("Attempting to bind to host: " + host);
			socket.bind(host, options.socketBacklog);
			logger.fine("Socket: " + socket);
		}
		catch (IOException e)
		{
			if(exceptionHandler != null)
				exceptionHandler.accept(e);
		}

		status.set(Status.BOUND);
		logger.info("Successfully bound and listening!");
		while(!globalStop.get())
		{
			SocketAddress clientAddr = null;
			try
			{
				Socket client = socket.accept();
				clientAddr = client.getRemoteSocketAddress();

				status.set(Status.CLIENT_CONNECTING);
				logger.fine("New connection from " + clientAddr);

				if(currentClients.size() < options.maxClients)
				{
					BrokerClientThread clientThread = new BrokerClientThread(this, client, ++nextID);
					clientThread.setDaemon(true);
					currentClients.add(clientThread);
					clientThread.start();
					logger.fine("Client connected: #" + nextID + " " + currentClients.size() + "/" + options.maxClients);
					logger.finer("Starting thread for new client " + client);
				}
				else
				{
					logger.warning("Client connected and refused due to capacity");
					client.close();
				}
			}
			catch (IOException e)
			{
				logger.warning("Issue occurred connecting client: " + clientAddr);
				if(exceptionHandler != null)
					exceptionHandler.accept(e);
			}
			finally
			{
				status.set(Status.BOUND);
			}
		}

		try
		{
			status.set(Status.STOPPING);
			logger.warning("Stopping broker");
			logger.fine("Closing connected clients");
			if(hardStop == null || !hardStop.get())
			{
				// TODO: Cleanly close connected clients?
			}
			logger.fine("Closing executor service, rejecting new tasks");
			executorService.shutdown();
			try
			{
				if(!executorService.awaitTermination(10, TimeUnit.SECONDS))
					logger.warning("Tasks remaining in executor service, closing anyway");
			}
			catch (InterruptedException e)
			{
				logger.log(Level.WARNING, "Exception during closing executor service", e);
			}

			socket.close();
			logger.fine("Closed socket");
		}
		catch (IOException e)
		{
			if(exceptionHandler != null)
				exceptionHandler.accept(e);
		}
		finally
		{
			status.set(Status.STOPPED);
			logger.info("Stopped broker");
		}
	}

	public boolean isRunning()
	{
		return socket != null && !socket.isClosed() && globalStop != null && !globalStop.get();
	}

	public void runAsync()
	{
		runAsync(null);
	}

	public void runAsync(@Nullable Consumer<Thread> handler)
	{
		if(logger.isLoggable(Level.INFO))
			logger.info("Running broker async");
		Thread thread = new Thread(this);
		if(handler != null)
			handler.accept(thread);
		thread.start();
	}

	void forAll(Consumer<BrokerClientThread> consumer)
	{
		synchronized (currentClients)
		{
			currentClients.forEach(consumer);
		}
	}

	public void stop(boolean soft)
	{
		if(!isRunning())
			throw new IllegalStateException("broker is not running");

		if(logger.isLoggable(Level.FINE))
			logger.fine("Signaling broker to stop");
		globalStop.set(true);
		forAll(thread -> thread.close(!soft));
		hardStop = new AtomicBoolean(!soft);
	}

	static boolean isValidClientID(String id)
	{
		if(id.isEmpty() || id.length() > 64)
			return false;

		for (int i = 0; i < id.length(); i++)
		{
			switch (Character.toLowerCase(id.charAt(i)))
			{
				case '0': case '1': case '2': case '3': case '4':
				case '5': case '6': case '7': case '8': case '9':
				case 'a': case 'b': case 'c': case 'd': case 'e':
				case 'f': case 'g': case 'h': case 'i': case 'j':
				case 'k': case 'l': case 'm': case 'n': case 'o':
				case 'p': case 'q': case 'r': case 's': case 't':
				case 'u': case 'v': case 'w': case 'x': case 'y':
				case 'z': case '-': case '_':
					continue;
				default:
					return false;
			}
		}
		return true;
	}

	public enum Status
	{
		BINDING, BOUND, CLIENT_CONNECTING, NOT_BOUND, STOPPED, STOPPING
	}
}
