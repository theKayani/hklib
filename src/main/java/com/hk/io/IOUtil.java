package com.hk.io;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;

/**
 * <p>IOUtil class.</p>
 *
 * @author theKayani
 */
public final class IOUtil
{
	/**
	 * Copy all the data from an input stream to an output stream
	 * using a default byte buffer size of 1024.
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @param out a {@link java.io.OutputStream} object
	 */
	public static void copyTo(@NotNull InputStream in, @NotNull OutputStream out)
	{
		copyTo(in, out, 1024);
	}

	/**
	 * Copy all the data from an input stream to an output stream
	 * using the specified size for the byte buffer.
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @param out a {@link java.io.OutputStream} object
	 * @param buffer a int
	 */
	public static void copyTo(@NotNull InputStream in, @NotNull OutputStream out, int buffer)
	{
		try
		{
			byte[] arr = new byte[buffer];
			int read;

			while ((read = in.read(arr)) != -1)
			{
				out.write(arr, 0, read);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Copy all the data from a reader to a writer using a default
	 * character buffer size of 512.
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @param out a {@link java.io.OutputStream} object
	 */
	public static void copyTo(@NotNull Reader in, @NotNull Writer out)
	{
		copyTo(in, out, 512);
	}

	/**
	 * Copy all the data from a reader to a writer using the specified
	 * size for the character buffer.
	 *
	 * @param in a {@link java.io.Reader} object
	 * @param out a {@link java.io.Writer} object
	 * @param buffer a int
	 */
	public static void copyTo(@NotNull Reader in, @NotNull Writer out, int buffer)
	{
		try
		{
			char[] arr = new char[buffer];
			int read;

			while ((read = in.read(arr)) != -1)
				out.write(arr, 0, read);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Collect all the contents of an input stream to a byte array.
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @return an array of {@link byte} objects
	 */
	public static byte[] readAll(@NotNull InputStream in)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copyTo(in, baos);
		return baos.toByteArray();
	}

	/**
	 * Collect all the contents of a reader to a character array.
	 *
	 * @param in a {@link java.io.Reader} object
	 * @return an array of {@link char} objects
	 */
	public static char[] readAll(@NotNull Reader in)
	{
		CharArrayWriter caw = new CharArrayWriter();
		copyTo(in, caw);
		return caw.toCharArray();
	}

	/**
	 * Fully read an input stream in the default charset into a
	 * string.
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @return a {@link java.lang.String} object
	 */
	@NotNull
	public static String readString(@NotNull InputStream in)
	{
		return new String(readAll(in));
	}

	/**
	 * Fully read an input stream in the specified charset into a
	 * string.
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @param charset a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	@NotNull
	public static String readString(@NotNull InputStream in, @NotNull String charset)
	{
		try
		{
			return new String(readAll(in), charset);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Fully read an input stream in the specified charset into a
	 * string.
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link java.lang.String} object
	 */
	@NotNull
	public static String readString(@NotNull InputStream in, @NotNull Charset charset)
	{
		return new String(readAll(in), charset);
	}

	/**
	 * Fully read a reader into a string.
	 *
	 * @param in a {@link java.io.Reader} object
	 * @return a {@link java.lang.String} object
	 */
	@NotNull
	public static String readString(@NotNull Reader in)
	{
		return new String(readAll(in));
	}

	/**
	 * <p>closeQuietly.</p>
	 *
	 * @param c a {@link java.io.Closeable} object
	 */
	public static void closeQuietly(@NotNull Closeable c)
	{
		try
		{
			c.close();
		}
		catch (IOException ignored)
		{}
	}

	@Deprecated
	/*
	  renamed to closeWithRE
	 */
	public static void closeWithRuntimeException(@NotNull Closeable c)
	{
		closeWithRE(c);
	}

	/**
	 * <p>closeWithRE.</p>
	 *
	 * @param c a {@link java.io.Closeable} object
	 */
	public static void closeWithRE(@NotNull Closeable c)
	{
		try
		{
			c.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>Returns an empty reader that can't be closed. Which doesn't
	 * support marking.</p>
	 *
	 * @return an empty reader
	 */
	@NotNull
	public static Reader emptyReader()
	{
		return emptyReader;
	}

	/**
	 * <p>Returns an empty input stream that can't be closed. Which doesn't
	 * support marking.</p>
	 *
	 * @return an empty stream
	 */
	@NotNull
	public static InputStream emptyStream()
	{
		return emptyStream;
	}

	/**
	 * <p>Returns a writer that can't be closed which also goes nowhere.</p>
	 *
	 * @return a writer that goes nowhere
	 */
	@NotNull
	public static Writer nowhereWriter()
	{
		return nowhereWriter;
	}

	/**
	 * <p>Returns an output stream that can't be closed which also goes nowhere.</p>
	 *
	 * @return an output stream that goes nowhere
	 */
	@NotNull
	public static OutputStream nowhereStream()
	{
		return nowhereStream;
	}

	private static final Reader emptyReader = new Reader() {
		@Override
		public int read()
		{
			return -1;
		}

		@Override
		public int read(char @NotNull [] cbuf, int off, int len)
		{
			return -1;
		}

		@Override
		public long skip(long n)
		{
			return 0;
		}

		@Override
		public void close() {}
	};

	private static final Writer nowhereWriter = new Writer() {
		@Override
		public void write(int c) {}

		@Override
		public void write(@NotNull String str, int off, int len) {}

		@Override
		public void write(char @NotNull [] cbuf, int off, int len) {}

		@Override
		public void flush() {}

		@Override
		public void close() {}
	};

	private static final InputStream emptyStream = new InputStream() {
		@Override
		public int read()
		{
			return -1;
		}

		@Override
		public int read(byte @NotNull [] b, int off, int len)
		{
			return -1;
		}

		@Override
		public long skip(long n)
		{
			return 0;
		}
	};

	private static final OutputStream nowhereStream = new OutputStream() {
		@Override
		public void write(int b) {}

		@Override
		public void write(byte @NotNull [] b, int off, int len) {}
	};

	private IOUtil()
	{}
}