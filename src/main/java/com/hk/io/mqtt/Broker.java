package com.hk.io.mqtt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.logging.*;

/**
 * Creates a MQTT Protocol Broker that blocks upon calling the {@link #run()}
 * method.
 */
public class Broker implements Runnable
{
	private final InetSocketAddress host;
	private final List<BrokerClientThread> currentClients;
	public final BrokerOptions options;
	private final AtomicReference<Status> status;
	private final Logger logger;
	Consumer<IOException> exceptionHandler;
	private ScheduledExecutorService executorService;
	private ServerSocket socket;
	private Consumer<ServerSocket> handler;
	private AtomicBoolean hardStop;
	AtomicBoolean globalStop;

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
		currentClients = new ArrayList<>();
		status = new AtomicReference<>(Status.NOT_BOUND);
		logger = Logger.getLogger("MQTT-Broker");
		logger.setLevel(Level.INFO);
		logger.setUseParentHandlers(false);
		logger.addHandler(new ConsoleHandler());
	}

	@Contract("_ -> this")
	public Broker setSocketHandler(@Nullable Consumer<ServerSocket> handler)
	{
		this.handler = handler;
		return this;
	}

	@Contract("-> this")
	public Broker setDefaultExceptionHandler()
	{
		return setExceptionHandler(Common.DefaultExceptionHandler.INSTANCE);
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

	@NotNull
	public Status getStatus()
	{
		return status.get();
	}

	public void run()
	{
		if(status.get() != Status.NOT_BOUND)
			throw new IllegalStateException("Broker already tried on socket");

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
					logger.info("Client connected: " + currentClients.size() + "/" + options.maxClients);

					BrokerClientThread clientThread = new BrokerClientThread(this, client);
					clientThread.setDaemon(true);
					currentClients.add(clientThread);
					clientThread.start();
					logger.fine("Starting thread for new client " + client);
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

	public void stop(boolean soft)
	{
		if(!isRunning())
			throw new IllegalStateException("broker is not running");

		if(logger.isLoggable(Level.FINE))
			logger.fine("Signaling broker to stop");
		globalStop.set(true);
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