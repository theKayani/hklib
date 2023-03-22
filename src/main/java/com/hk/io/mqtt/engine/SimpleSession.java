package com.hk.io.mqtt.engine;

import com.hk.io.mqtt.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimpleSession extends Session
{
	private final Map<String, Integer> desiredTopics;

	public SimpleSession(String clientID)
	{
		super(clientID);
		desiredTopics = Collections.synchronizedMap(new HashMap<>());
	}

	@Override
	public boolean hasExpired()
	{
		return false;
	}

	@Override
	@Range(from=-1, to=2)
	public int getDesiredQoS(@NotNull String topic)
	{
		int max = -1;
		synchronized (desiredTopics)
		{
			for (Map.Entry<String, Integer> entry : desiredTopics.entrySet())
			{
				if (matches(topic, entry.getKey()))
					max = Math.max(max, entry.getValue());
			}
		}
		return max;
	}

	@Override
	public void subscribe(@NotNull String topicFilter, @Range(from=0, to=2) int desiredQos)
	{
		Objects.requireNonNull(topicFilter);
		if(desiredQos < 0 || desiredQos > 2)
			throw new IllegalArgumentException("expected qos between 0 and 2, not " + desiredQos);

		desiredTopics.put(topicFilter, desiredQos);
	}

	@Override
	public void unsubscribe(@NotNull String topicFilter)
	{
		Objects.requireNonNull(topicFilter);
		desiredTopics.remove(topicFilter);
	}
}
