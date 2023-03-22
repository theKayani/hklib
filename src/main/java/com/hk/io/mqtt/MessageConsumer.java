package com.hk.io.mqtt;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Consumer;

@FunctionalInterface
public interface MessageConsumer extends Consumer<Message>
{
	void consume(Message message) throws IOException;

	@Override
	default void accept(Message message)
	{
		try
		{
			consume(message);
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}
}
