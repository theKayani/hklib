package com.hk.io.mqtt;

public class ClientOptions
{
	// using SSLSocket or just Socket
	boolean useSSL;

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

	public ClientOptions setUseSSL(boolean useSSL)
	{
		this.useSSL = useSSL;
		return this;
	}

	public ClientOptions set(ClientOptions options)
	{
		useSSL = options.useSSL;
		return this;
	}
}
