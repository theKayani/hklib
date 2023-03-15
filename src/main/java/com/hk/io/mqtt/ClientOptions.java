package com.hk.io.mqtt;

public class ClientOptions
{
	// using SSLSocket or just Socket
	boolean useSSL;
	// "reasonable" amount of millis to wait before disconnecting a
	// client that doesn't receive a CONNACK packet
	int connectWaitTimeout;

	public ClientOptions()
	{
		useSSL = false;
	}

	public ClientOptions(ClientOptions options)
	{
		set(options);
	}

	public boolean isUsingSSL()
	{
		return useSSL;
	}

	public int getConnectWaitTimeout()
	{
		return connectWaitTimeout;
	}

	public ClientOptions setUseSSL(boolean useSSL)
	{
		this.useSSL = useSSL;
		return this;
	}

	public ClientOptions setConnectWaitTimeout(int connectWaitTimeout)
	{
		this.connectWaitTimeout = connectWaitTimeout;
		return this;
	}

	public ClientOptions set(ClientOptions options)
	{
		useSSL = options.useSSL;
		connectWaitTimeout = options.connectWaitTimeout;
		return this;
	}
}
