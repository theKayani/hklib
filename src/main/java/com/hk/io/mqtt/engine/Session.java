package com.hk.io.mqtt.engine;

public interface Session
{
	/**
	 * Get the client identifier that was used to create this session.
	 *
	 * @return the client id
	 */
	String getClientID();

	/**
	 * Returns true if the session has expired due to unexpected
	 * circumstances
	 *
	 * @return true if this session will not be used anymore
	 */
	boolean hasExpired();
}
