package com.hk.io.mqtt.engine;

import com.hk.io.mqtt.Message;
import com.hk.io.mqtt.Session;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class BasicMQTTEngine implements MQTTEngine
{
	private final Map<String, Session> sessionMap;
	/**
	 * Creates a new session given a client id
	 */
	private Function<String, Session> sessionFactory;
	private Authentication authentication;

	public BasicMQTTEngine()
	{
		sessionMap = Collections.synchronizedMap(new HashMap<>());
		setSessionFactory(null);
	}

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

	@NotNull
	public Function<String, Session> getSessionFactory()
	{
		return sessionFactory;
	}

	@Contract("_ -> this")
	public BasicMQTTEngine setSessionFactory(@Nullable Function<String, Session> sessionFactory)
	{
		this.sessionFactory = sessionFactory == null ? SimpleSession::new : sessionFactory;
		return this;
	}

	@Override
	public boolean tryClientID(@NotNull String clientID)
	{
		return true;
	}

	@Override
	public boolean requireUsername(@NotNull String clientID)
	{
		return auth().requireUsername();
	}

	@Override
	public boolean requirePassword(@NotNull String clientID)
	{
		return auth().requirePassword();
	}

	@Override
	public boolean attemptAuthenticate(@NotNull String clientID, @Nullable String username, byte @Nullable [] password)
	{
		return auth().attemptAuthenticate(clientID, username, password);
	}

	@Override
	@Nullable
	public Session getSession(@NotNull String clientID)
	{
		return sessionMap.get(clientID);
	}

	@Override
	@NotNull
	public Session createSession(@NotNull String clientID)
	{
		Session session = sessionFactory.apply(clientID);
		sessionMap.put(clientID, Objects.requireNonNull(session));
		return session;
	}

	@Override
	public boolean canSendTo(@NotNull Message message, @NotNull String clientID)
	{
		return true;
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
		public boolean attemptAuthenticate(@NotNull String clientID, @Nullable String username, byte @Nullable [] password)
		{
			return true;
		}
	}

	public interface Authentication
	{
		boolean requireUsername();

		boolean requirePassword();

		boolean attemptAuthenticate(String clientID, @Nullable String username, byte @Nullable [] password);
	}
}
