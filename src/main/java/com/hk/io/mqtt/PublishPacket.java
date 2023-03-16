package com.hk.io.mqtt;

import com.hk.math.MathUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

final class PublishPacket implements Runnable
{
	private final Message message;
	private final AtomicInteger pid;
	private final Logger logger;
	private final OutputStream out;
	private final Object writeLock;
	private Consumer<IOException> exceptionHandler;
	private Consumer<Transaction> onComplete;
	private boolean duplicate = false;

	PublishPacket(Message message, AtomicInteger pid, Logger logger, OutputStream out, Object writeLock)
	{
		this.message = Objects.requireNonNull(message);
		this.pid = pid;
		this.logger = Objects.requireNonNull(logger);
		this.out = Objects.requireNonNull(out);
		this.writeLock = writeLock == null ? new Object() : writeLock;

		if (pid == null && message.getQos() != 0)
			throw new IllegalArgumentException("pid should NOT be null with a non-zero QoS message");
	}

	public PublishPacket setExceptionHandler(Consumer<IOException> exceptionHandler)
	{
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	public PublishPacket setOnComplete(Consumer<Transaction> onComplete)
	{
		this.onComplete = Objects.requireNonNull(onComplete);
		return this;
	}

	public PublishPacket setDuplicate()
	{
		this.duplicate = true;
		return this;
	}

	@Override
	public void run()
	{
		try
		{
			synchronized (writeLock)
			{
				int flags = 0;
				if (message.isRetain())
					flags |= 0x1;
				flags |= (message.getQos() & 3) << 1;
				if (duplicate)
					flags |= 0x8;
				out.write(0x30 | flags);
				byte[] topicBytes = message.getTopic().getBytes(StandardCharsets.UTF_8);
				int payloadLength = message.getSize();
				ByteArrayOutputStream bout = null;
				if (payloadLength < 0)
				{
					bout = new ByteArrayOutputStream();
					message.writeTo(bout);
					payloadLength = bout.size();
				}
				int total = payloadLength + topicBytes.length + 2;
				if (message.getQos() > 0)
					total += 2;
				if (total > 268435455)
					throw new IOException("packet size overflow: " + total + " > 268435455 (max packet size)");

				logger.fine("Sending packet: PUBLISH (QoS: " + message.getQos() + ", " + (pid == null ? "no pid" : MathUtil.shortHex(pid.get())) + ", " + total + " b)");
				Common.writeRemainingField(out, total);
				Common.writeBytes(out, topicBytes);
				if (message.getQos() > 0)
					Common.writeShort(out, Objects.requireNonNull(pid).get());

				if (bout != null)
					bout.writeTo(out);
				else
					message.writeTo(out);

				if (onComplete != null)
					onComplete.accept(new Transaction(message, pid));

				out.flush();

				if (logger.isLoggable(Level.FINEST))
					logger.finest("published: " + message);
			}
		}
		catch (IOException ex)
		{
			if (exceptionHandler != null)
				exceptionHandler.accept(ex);
		}
	}

	static class Transaction
	{
		final Message message;
		final AtomicInteger pid;
		PacketType lastPacket;
		long timestamp;

		Transaction(Message message, AtomicInteger pid)
		{
			this.message = message;
			this.pid = pid;
			updateTimestamp();
		}

		public Transaction setLastPacket(PacketType lastPacket)
		{
			this.lastPacket = lastPacket;
			return updateTimestamp();
		}

		private Transaction updateTimestamp()
		{
			timestamp = System.nanoTime() / 1000000L;
			return this;
		}

		boolean isPast(long timeout)
		{
			return System.nanoTime() / 1000000L - timestamp > timeout;
		}
	}
}
