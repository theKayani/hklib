package com.hk.io.mqtt.engine;

import com.hk.io.mqtt.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
		return -1;
	}
}
