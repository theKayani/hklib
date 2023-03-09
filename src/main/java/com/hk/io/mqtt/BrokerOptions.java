package com.hk.io.mqtt;

import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class BrokerOptions
{
	// amount of connections to backlog before accepting
	int socketBacklog;
	// using SSLServerSocket or just ServerSocket
	boolean useSSL;
	// "reasonable" amount of millis to wait before disconnecting a
	// client that doesn't send a CONNECT packet
	int connectWaitTimeout;
	// executor service pool size
	int threadPoolSize;
	// maximum amount of connections to hold at once
	int maxClients;
	// checks incoming client IDs waiting to connect, called from various threads
	// should return true to accept and false to reject incoming client IDs
	@Nullable
	Predicate<String> clientIDAgent;

	public BrokerOptions()
	{
		socketBacklog = 5;
		useSSL = false;
		connectWaitTimeout = 10000;
		threadPoolSize = 4;
		maxClients = 128;
		clientIDAgent = null;
	}

	public BrokerOptions(BrokerOptions options)
	{
		set(options);
	}

	public int getSocketBacklog()
	{
		return socketBacklog;
	}

	public boolean isUsingSSL()
	{
		return useSSL;
	}

	public int getConnectWaitTimeout()
	{
		return connectWaitTimeout;
	}

	public int getThreadPoolSize()
	{
		return threadPoolSize;
	}

	public int getMaxClients()
	{
		return maxClients;
	}

	public BrokerOptions setSocketBacklog(int socketBacklog)
	{
		this.socketBacklog = socketBacklog;
		return this;
	}

	public BrokerOptions setUseSSL(boolean useSSL)
	{
		this.useSSL = useSSL;
		return this;
	}

	public BrokerOptions setConnectWaitTimeout(int connectWaitTimeout)
	{
		this.connectWaitTimeout = connectWaitTimeout;
		return this;
	}

	public BrokerOptions setThreadPoolSize(int threadPoolSize)
	{
		this.threadPoolSize = threadPoolSize;
		return this;
	}

	public BrokerOptions setMaxClients(int maxClients)
	{
		this.maxClients = maxClients;
		return this;
	}

	public BrokerOptions set(BrokerOptions options)
	{
		socketBacklog = options.socketBacklog;
		useSSL = options.useSSL;
		connectWaitTimeout = options.connectWaitTimeout;
		threadPoolSize = options.threadPoolSize;
		maxClients = options.maxClients;
		clientIDAgent = options.clientIDAgent;
		return this;
	}
}
