package com.hk.io.mqtt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.*;

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
	 * Test whether this topic does not contain wildcards and can be
	 * used to publish messages and wills. Returns true if the provided
	 * topic is pure.
	 *
	 * @param topic the topic to test
	 * @return true if this topic contains no wildcards and is pure
	 */
	public static boolean isValidTopic(String topic)
	{
		if(topic.isEmpty())
			return false;

		char c;
		for (int i = 0; i < topic.length(); i++)
		{
			c = topic.charAt(i);
			if(c == '+' || c == '#')
				return false;
		}
		return true;
	}

	/**
	 * Test whether this topic filter is valid and could be
	 * subscribed to.
	 *
	 * @param topicFilter the topic filter to test
	 * @return false if this topic filter is valid
	 */
	public static boolean isValidTopicFilter(String topicFilter)
	{
		return split(topicFilter) != null;
	}

	/**
	 * Split the topic or filter into the levels
	 *
	 * @param topicFilter the topic filter to test
	 * @return false if this topic filter is valid
	 */
	public static String[] split(String topicFilter)
	{
		if(topicFilter.isEmpty())
			return null; // invalid

		List<String> lst = new ArrayList<>();
		StringBuilder level = new StringBuilder();
		char c;
		boolean has = false;
		for (int i = 0; i < topicFilter.length(); i++)
		{
			has = false;
			c = topicFilter.charAt(i);

			if (c == '/')
			{
				lst.add(level.toString());
				level.setLength(0);
				has = true;
			}
			else
			{
				if((c == '+' || c == '#') && level.length() > 0)
					return null; // invalid
				if(c == '#' && i < topicFilter.length() - 1)
					return null; // invalid
				level.append(c);
			}
		}
		if(has || level.length() > 0)
			lst.add(level.toString());
		return lst.toArray(new String[0]);
	}

	/**
	 * Check whether the subscription matches the topic provided given
	 * the MQTT spec: http://docs.oasis-open.org/mqtt/mqtt/v3.1.1/os/mqtt-v3.1.1-os.html#_Toc398718106
	 *
	 * @param topic the pure topic to test
	 * @param topicFilter the topic filter to check against
	 * @return true if the topic filter matches the topic.
	 */
	public static boolean matches(String topic, String topicFilter)
	{
		if(!isValidTopic(topic))
			throw new IllegalArgumentException("invalid topic: " + topic);
		if(topic.equals(topicFilter))
			return true;

		boolean tpcHasSysChar = topic.charAt(0) == '$';
		boolean fltHasSysChar = topicFilter.charAt(0) == '$';

		if(tpcHasSysChar != fltHasSysChar)
			return false;
		String[] fltLvls = split(topicFilter);
		if(fltLvls == null)
			throw new IllegalArgumentException("invalid topic filter: " + topicFilter);

		String[] tpcLvls = split(topic);
		if(tpcLvls == null)
			throw new Error("not possibru"); // all conditions covered in isValidTopic(topic)

		int i;
		for (i = 0; i < tpcLvls.length; i++)
		{
			if(i == fltLvls.length)
			{
				// have remaining levels that haven't been matched
				return false;
			}
			if(fltLvls[i].equals("+"))
			{
				// match single-level wildcard for level
				continue;
			}
			if(fltLvls[i].equals("#"))
			{
				// match multi-level wildcard for rest of topic
				return true;
			}
			if(tpcLvls[i].equals(fltLvls[i]))
			{
				// match level as case-sensitive string
				continue;
			}
			// match multi-level wildcard for parent topic too
			return i < fltLvls.length - 1 && fltLvls[i + 1].equals("#");
		}
		return true;
	}
}
