package com.hk.io.mqtt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Session
{
	/**
	 * The client identifier that was used to create this session.
	 */
	public final String clientID;
	final Map<Common.PacketID, PublishPacket.Transaction> unfinishedSend;
	final Map<Common.PacketID, PublishPacket.Transaction> unfinishedRecv;

	public Session(String clientID)
	{
		this.clientID = clientID;
		// is linked so that packed stay
		// ordered or something idk
		unfinishedSend = Collections.synchronizedMap(new LinkedHashMap<>());
		unfinishedRecv = Collections.synchronizedMap(new LinkedHashMap<>());
	}

	/**
	 * Returns true if the session has expired due to unexpected
	 * circumstances
	 *
	 * @return true if this session will not be used anymore
	 */
	public abstract boolean hasExpired();

	/**
	 * Get the desired QoS for receiving messages when the client
	 * subscribed to this topic. Returns -1 if there is no matching
	 * subscription for the topic.
	 *
	 * @param topic the topic to get the desired QoS for
	 * @return the desired QoS when subscribing to the topic or -1 if
	 * 		the subscription doesn't exist.
	 */
	@Range(from=-1, to=2)
	public abstract int getDesiredQoS(@NotNull String topic);

	public static boolean matches(String topic, String subscription)
	{
		System.out.println("topic = " + topic);
		System.out.println("subscription = " + subscription);
		// TODO: vvv
		throw new Error("FINISH HIM");
	}
}
