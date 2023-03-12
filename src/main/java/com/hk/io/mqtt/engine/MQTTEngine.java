package com.hk.io.mqtt.engine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * SHOULD BE THREAD-SAFE
 * Handles all session data!
 */
public interface MQTTEngine
{
	/**
	 * Test to see if the provided client ID is accepted or rejected
	 * to connect.
	 *
	 * There's already some basic checking to see if an ID conforms
	 * with MQTT specification but here you can target specific IDs
	 *
	 * @param clientID the id to test for authorization
	 * @return false if any client connecting with this ID will be rejected
	 */
	boolean tryClientID(@NotNull String clientID);

	/**
	 * Get the session attached to the provided client identifier. Can
	 * be null if no prior session found.
	 *
	 * This can either be server-provided or provided from a previous
	 * connection where the clean-session flag was set to false.
	 *
	 * @param clientID The client identifier to check for
	 * @return a session if is present for the client ID provided
	 */
	@Nullable
	Session getSession(@NotNull String clientID);

	/**
	 * Creates a new empty session instantiated with the given client
	 * identifier and store it itself. The created session can be
	 * retrieved using {@link #getSession(String)} and the client
	 * identifier.
	 *
	 * @param clientID to pair the session with
	 * @return a new session matching with the client ID
	 */
	@NotNull
	Session createSession(@NotNull String clientID);

	/**
	 * If connecting clients should have a username, otherwise the
	 * connecting client will be disconnected with <i>ACK</i>.
	 *
	 * @param clientID the id associated with the connecting client
	 * @return true to automatically disconnect incoming clients with
	 * 			no usernames
	 */
	boolean requireUsername(@NotNull String clientID);

	/**
	 * If connecting clients should have a password with each username,
	 * otherwise the connecting client will be disconnected with <i>ACK</i>.
	 * If true, this MUST mean {@link #requireUsername} returns true as well.
	 *
	 * @param clientID the id associated with the connecting client
	 * @return true to automatically disconnect incoming clients with
	 * 			no usernames or passwords
	 */
	boolean requirePassword(@NotNull String clientID);

	/**
	 * Authenticate a client attempting to connect with the given
	 * username and password. The values be null if the engine doesn't
	 * require a username or password.
	 *
	 * @param clientID the id associated with the connecting client
	 * @param username the username the client is attempting to authenticate with
	 * @param password the password the client is attempting to authenticate with
	 * @return true if the given username and password combination is
	 * 				authorized to connect to the given broker.
	 */
	boolean attemptAuthenticate(@NotNull String clientID, @Nullable String username, byte @Nullable [] password);
}
