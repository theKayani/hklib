package com.hk.io.mqtt.engine;

public class SimpleSession implements Session
{
	public final String clientID;

	public SimpleSession(String clientID)
	{
		this.clientID = clientID;
	}

	@Override
	public String getClientID()
	{
		return clientID;
	}

	@Override
	public boolean hasExpired()
	{
		return false;
	}
}
