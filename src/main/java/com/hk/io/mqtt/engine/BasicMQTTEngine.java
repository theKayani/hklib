package com.hk.io.mqtt.engine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicMQTTEngine implements MQTTEngine
{
	private Authentication authentication;

	public BasicMQTTEngine()
	{}

	@Nullable
	public Authentication getAuthentication()
	{
		return authentication;
	}

	public BasicMQTTEngine setAuthentication(@Nullable Authentication authentication)
	{
		this.authentication = authentication;
		return this;
	}

	@Override
	public boolean tryClientID(String clientID)
	{
		return auth().tryClientID(clientID);
	}

	@Override
	public boolean requireUsername(String clientID)
	{
		return auth().requireUsername();
	}

	@Override
	public boolean requirePassword(String clientID)
	{
		return auth().requirePassword();
	}

	@Override
	public boolean attemptAuthenticate(@NotNull String clientID, @Nullable String username, byte @Nullable [] password)
	{
		return auth().attemptAuthenticate(username, password);
	}

	private Authentication auth()
	{
		if(authentication == null)
			return YesAuthentication.INSTANCE;
		else
			return authentication;
	}

	/**
	 * Authorize and authenticate all clients attempting to connect
	 * with the given credentials.
	 *
	 * This is the default when {@link #authentication} is null
	 */
	public final static class YesAuthentication implements Authentication
	{
		static final YesAuthentication INSTANCE = new YesAuthentication();

		private YesAuthentication()
		{}

		@Override
		public boolean tryClientID(String clientID)
		{
			return true;
		}

		@Override
		public boolean requireUsername()
		{
			return false;
		}

		@Override
		public boolean requirePassword()
		{
			return false;
		}

		@Override
		public boolean attemptAuthenticate(@Nullable String username, byte @Nullable [] password)
		{
			return true;
		}
	}

	public interface Authentication
	{
		boolean tryClientID(String clientID);

		boolean requireUsername();

		boolean requirePassword();

		boolean attemptAuthenticate(@Nullable String username, byte @Nullable [] password);
	}
}
