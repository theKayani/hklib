package com.hk.io.mqtt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

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

	static String readUTFString(InputStream in) throws IOException
	{
		// TODO: MQTT-1.5.3-1
		// TODO: MQTT-1.5.3-2
		// TODO: MQTT-1.5.3-3

		return new String(readBytes(in), StandardCharsets.UTF_8);
	}

	static void writeBytes(OutputStream out, byte[] bs) throws IOException
	{
		if(bs.length > 65535)
			throw new IllegalArgumentException("byte array is too long to write");

		writeShort(out, bs.length);
		out.write(bs);
	}

	static byte[] readBytes(InputStream in) throws IOException
	{
		int len = readShort(in);
		if(len == 0)
			return new byte[0];

		byte[] bs = new byte[len];
		if(in.read(bs) != len)
			throw new IOException(EOF);
		return bs;
	}

	static void writeShort(OutputStream out, int i) throws IOException
	{
		out.write((i >> 8) & 0xFF);
		out.write(i & 0xFF);
	}

	static int readShort(InputStream in) throws IOException
	{
		byte[] i = new byte[2];
		if(in.read(i) != 2)
			throw new IOException(EOF);

		return ((i[0] << 8) | i[1]) & 0xFFFF;
	}

	static byte read(InputStream in) throws IOException
	{
		int i = in.read();
		if(i == -1)
			throw new IOException(EOF);
		return (byte) i;
	}

	static final String EOF = "end of stream reached";

	static final class DefaultExceptionHandler implements Consumer<IOException>
	{
		static final DefaultExceptionHandler INSTANCE = new DefaultExceptionHandler();

		private DefaultExceptionHandler()
		{}

		@Override
		public void accept(IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}
}
