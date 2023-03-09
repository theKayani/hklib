package com.hk.io.mqtt;

import org.jetbrains.annotations.Nullable;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Creates a MQTT Protocol Broker that blocks upon calling the {@link #run()}
 * method.
 */
public class Broker implements Runnable
{
	private final InetSocketAddress host;
	private final List<BrokerClientThread> currentClients;
	public final BrokerOptions options;
	private final AtomicReference<Status> status = new AtomicReference<>();
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
		status.set(Status.NOT_BOUND);
	}

	public Broker setSocketHandler(@Nullable Consumer<ServerSocket> handler)
	{
		this.handler = handler;
		return this;
	}

	public Broker setDefaultExceptionHandler()
	{
		return setExceptionHandler(Common.DefaultExceptionHandler.INSTANCE);
	}

	public Broker setExceptionHandler(@Nullable Consumer<IOException> exceptionHandler)
	{
		this.exceptionHandler = exceptionHandler;
		return this;
	}

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
			if(options.useSSL)
				socket = SSLServerSocketFactory.getDefault().createServerSocket();
			else
				socket = new ServerSocket();

			if(handler != null)
				handler.accept(socket);

			status.set(Status.BINDING);

			if(socket.isBound())
			{
				socket = null;
				throw new IllegalStateException("socket bound before starting broker");
			}

			globalStop = new AtomicBoolean(false);
			executorService = Executors.newScheduledThreadPool(options.threadPoolSize);
			socket.bind(host, options.socketBacklog);
		}
		catch (IOException e)
		{
			if(exceptionHandler != null)
				exceptionHandler.accept(e);
		}

		status.set(Status.BOUND);
		while(!globalStop.get())
		{
			try
			{
				Socket client = socket.accept();

				status.set(Status.CLIENT_CONNECTING);
				if(currentClients.size() < options.maxClients)
				{
					BrokerClientThread clientThread = new BrokerClientThread(this, client);
					clientThread.setDaemon(true);
					currentClients.add(clientThread);
					clientThread.start();
				}
				else
					client.close();
			}
			catch (IOException e)
			{
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
			status.set(Status.DISCONNECTING);
			if(hardStop == null || !hardStop.get())
			{
				// TODO: Cleanly close connected clients?
			}

			socket.close();
		}
		catch (IOException e)
		{
			if(exceptionHandler != null)
				exceptionHandler.accept(e);
		}
		finally
		{
			status.set(Status.STOPPED);
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
		Thread thread = new Thread(this);
		if(handler != null)
			handler.accept(thread);
		thread.start();
	}

	public void stop(boolean soft)
	{
		if(!isRunning())
			throw new IllegalStateException("broker already not running");

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
		BINDING, BOUND, CLIENT_CONNECTING, DISCONNECTING, NOT_BOUND, STOPPED
	}
}
