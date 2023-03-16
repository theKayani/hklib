package com.hk.io.mqtt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

class Common
{
	static void writeRemainingField(OutputStream out, int x) throws IOException
	{
		int b;
		do {
			b = x & 0x7F;
			x >>= 7;
			if(x > 0)
				b |= 0x80;
			out.write(b);
		} while(x > 0);
	}

	static int readRemainingField(InputStream in) throws IOException
	{
		int x = 0;
		int b;
		int shift = 0;
		do {
			b = read(in);
			x |= (b & 0x7F) << shift;
			shift += 7;
			if(shift > 28)
				throw new IOException("malformed remaining length");
		} while((b & 0x80) != 0);
		return x;
	}

	static void writeUTFString(OutputStream out, String s) throws IOException
	{
		writeBytes(out, s.getBytes(StandardCharsets.UTF_8));
	}

	static String readUTFString(InputStream in, AtomicInteger remLen) throws IOException
	{
		// TODO: MQTT-1.5.3-1
		// TODO: MQTT-1.5.3-2
		// TODO: MQTT-1.5.3-3

		return new String(readBytes(in, remLen), StandardCharsets.UTF_8);
	}

	static void writeBytes(OutputStream out, byte[] bs) throws IOException
	{
		if(bs.length > 65535)
			throw new IllegalArgumentException("byte array is too long to write");

		writeShort(out, bs.length);
		out.write(bs);
	}

	static byte[] readBytes(InputStream in, AtomicInteger remLen) throws IOException
	{
		int len = readShort(in, remLen);
		if(len == 0)
			return new byte[0];

		byte[] bs = new byte[len];
		if(in.read(bs) != len)
			throw new IOException(EOF);
		if(remLen != null && remLen.addAndGet(-len) < 0)
			throw new IOException(EOF);
		return bs;
	}

	static void writeShort(OutputStream out, int i) throws IOException
	{
		out.write((i >> 8) & 0xFF);
		out.write(i & 0xFF);
	}

	static int readShort(InputStream in, AtomicInteger remLen) throws IOException
	{
		byte[] i = new byte[2];
		if(in.read(i) != 2)
			throw new IOException(EOF);
		if(remLen != null && remLen.addAndGet(-2) < 0)
			throw new IOException(EOF);

		return ((i[0] << 8) | i[1]) & 0xFFFF;
	}

	static byte read(InputStream in) throws IOException
	{
		return read(in, null);
	}

	static byte read(InputStream in, AtomicInteger remLen) throws IOException
	{
		int i = in.read();
		if(i == -1)
			throw new IOException(EOF);
		if(remLen != null && remLen.decrementAndGet() < 0)
			throw new IOException(EOF);
		return (byte) i;
	}

	static final String EOF = "end of stream reached";

	enum ConnectReturn
	{
		ACCEPTED,
		UNACCEPTABLE_PROTOCOL_VERSION,
		IDENTIFIER_REJECTED,
		SERVER_UNAVAILABLE,
		BAD_USERNAME_OR_PASSWORD,
		UNAUTHORIZED
	}

	static final class DefaultExceptionHandler implements Consumer<IOException>
	{
		private final String message;
		private final Logger logger;

		DefaultExceptionHandler(String message, Logger logger)
		{
			this.message = message;
			this.logger = logger;
		}

		@Override
		public void accept(IOException e)
		{
			logger.log(Level.WARNING, message, e);
//			throw new UncheckedIOException(e);
		}
	}

	static final class PublishPacket implements Runnable
	{
		private final Message message;
		private final AtomicInteger pid;
		private final Logger logger;
		private final OutputStream out;
		private Consumer<IOException> exceptionHandler;
		private Runnable onComplete;
		private boolean duplicate = false;

		PublishPacket(Message message, AtomicInteger pid, Logger logger, OutputStream out)
		{
			this.message = Objects.requireNonNull(message);
			this.pid = pid;
			this.logger = Objects.requireNonNull(logger);
			this.out = Objects.requireNonNull(out);

			if(pid != null && message.getQos() == 0)
				throw new IllegalArgumentException("pid should be null with a 0 QoS message");
			if(pid == null && message.getQos() != 0)
				throw new IllegalArgumentException("pid should NOT be null with a non-zero QoS message");
		}

		public PublishPacket setExceptionHandler(Consumer<IOException> exceptionHandler)
		{
			this.exceptionHandler = exceptionHandler;
			return this;
		}

		public PublishPacket setOnComplete(Runnable onComplete)
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

				int flags = 0;
				if(message.isRetain())
					flags |= 0x1;
				flags |= message.getQos() << 1;
				if(duplicate)
					flags |= 0x8;
				out.write(0x30 | (flags & 0xF));
				byte[] topicBytes = message.getTopic().getBytes(StandardCharsets.UTF_8);
				int payloadLength = message.getSize();
				ByteArrayOutputStream bout = null;
				if(payloadLength < 0)
				{
					bout = new ByteArrayOutputStream();
					message.writeTo(bout);
					payloadLength = bout.size();
				}
				int total = payloadLength + topicBytes.length;
				if(pid != null)
					total += 2;
				if(total > 268435455)
					throw new IOException("packet size overflow: " + total + " > 268435455 (max packet size)");

				logger.fine("Sending packet: PUBLISH (" + total + " byte" + (total == 1 ? "" : "s") + ")");
				Common.writeRemainingField(out, total);
				Common.writeBytes(out, topicBytes);
				if(pid != null)
					Common.writeShort(out, pid.get());

				if(bout != null)
					bout.writeTo(out);
				else
					message.writeTo(out);
				out.flush();

				if(onComplete != null)
					onComplete.run();
			}
			catch (IOException ex)
			{
				if(exceptionHandler != null)
					exceptionHandler.accept(ex);
			}
		}
	}
}
