package com.hk.io.mqtt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

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

	/**
	 * Add or modify a subscription with the provided topic filter and
	 * desired QoS.
	 *
	 * @param topicFilter the filter that was subscribed to
	 * @param desiredQos the desired QoS that messages should be sent at
	 */
	public abstract void subscribe(@NotNull String topicFilter, @Range(from=0, to=2) int desiredQos);

	/**
	 * Remove a subscription matching the provided topic filter.
	 *
	 * @param topicFilter the filter that was unsubscribed to
	 */
	public abstract void unsubscribe(@NotNull String topicFilter);

	/**
	 * Check whether the subscription matches the topic provided given
	 * the MQTT spec.
	 *
	 * @param topic the pure topic to test
	 * @param topicFilter the topic filter to check against
	 * @return true if the topic filter matches the topic.
	 */
	public static boolean matches(String topic, String topicFilter)
	{
		System.out.println("topic = '" + topic + "'");
		System.out.println("topicFilter = '" + topicFilter + "'");
		if(Common.isInvalidTopic(topicFilter))
		{
			// TODO: wildcard check
			throw new Error("FINISH HIM");
		}
		else
		{
			return topic.equals(topicFilter);
		}
	}
}
