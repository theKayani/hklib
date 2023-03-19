package com.hk.io.mqtt;

import com.hk.math.MathUtil;
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
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

	static void sendPuback(OutputStream out, Common.PacketID pid) throws IOException
	{
		Objects.requireNonNull(pid);
		out.write(0x40);
		out.write(0x2);
		writeShort(out, pid.get());
		out.flush();
	}

	static void sendPubrec(OutputStream out, Common.PacketID pid) throws IOException
	{
		Objects.requireNonNull(pid);
		out.write(0x50);
		out.write(0x2);
		writeShort(out, pid.get());
		out.flush();
	}

	static void sendPubrel(OutputStream out, Common.PacketID pid) throws IOException
	{
		Objects.requireNonNull(pid);
		out.write(0x62);
		out.write(0x2);
		writeShort(out, pid.get());
		out.flush();
	}

	static void sendPubcomp(OutputStream out, Common.PacketID pid) throws IOException
	{
		Objects.requireNonNull(pid);
		out.write(0x70);
		out.write(0x2);
		writeShort(out, pid.get());
		out.flush();
	}

	/**
	 * Test whether this topic contains wildcards and can not be used
	 * to publish messages and wills. Returns false if the provided
	 * topic is pure.
	 *
	 * @param topic the topic to test
	 * @return false if this topic contains no wildcards and is pure
	 */
	static boolean isInvalidTopic(String topic)
	{
		char c;
		for (int i = 0; i < topic.length(); i++)
		{
			c = topic.charAt(i);
			if(c == '+' || c == '#')
				return true;
		}
		return false;
	}

	static int utfBytesLength(String s)
	{
		// absolutely definitely not taken from ObjectOutputStream :D
		char[] cbuf = new char[256];
		int len = s.length();
		int utflen = 0;
		for (int off = 0; off < len; )
		{
			int csize = Math.min(len - off, cbuf.length);
			s.getChars(off, off + csize, cbuf, 0);
			for (int cpos = 0; cpos < csize; cpos++) {
				char c = cbuf[cpos];
				if (c >= 0x0001 && c <= 0x007F) {
					utflen++;
				} else if (c > 0x07FF) {
					utflen += 3;
				} else {
					utflen += 2;
				}

				if(utflen > 65535)
					return 65536;
			}
			off += csize;
		}
		return utflen;
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

	/**
	 * custom short integer wrapper because {@link AtomicInteger#hashCode} doesn't work right breaking hashmaps
	 */
	static class PacketID implements Supplier<Integer>
	{
		final int id;

		PacketID(int id)
		{
			this.id = id;

			if((id & 0xFFFF0000) != 0)
				throw new IllegalArgumentException("expected pid between 0x0000 and 0xFFFF");
		}

		@Range(from=0, to=65535)
		@Override
		public Integer get()
		{
			return id;
		}

		@Override
		public boolean equals(Object o)
		{
			return o instanceof PacketID && id == ((PacketID) o).id;
		}

		@Override
		public int hashCode()
		{
			return id;
		}

		@Override
		public String toString()
		{
			return MathUtil.shortHex(id);
		}
	}
}
