package com.hk.io;

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
	 * <p>copyTo.</p>
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @param out a {@link java.io.OutputStream} object
	 */
	public static void copyTo(InputStream in, OutputStream out)
	{
		copyTo(in, out, 1024);
	}
	
	/**
	 * <p>copyTo.</p>
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @param out a {@link java.io.OutputStream} object
	 * @param buffer a int
	 */
	public static void copyTo(InputStream in, OutputStream out, int buffer)
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
	 * <p>readAll.</p>
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @return an array of {@link byte} objects
	 */
	public static byte[] readAll(InputStream in)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copyTo(in, baos);
		return baos.toByteArray();
	}

	/**
	 * <p>readString.</p>
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @return a {@link java.lang.String} object
	 */
	public static String readString(InputStream in)
	{
		return new String(readAll(in));
	}

	/**
	 * <p>readString.</p>
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @param charset a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String readString(InputStream in, String charset)
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
	 * <p>readString.</p>
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link java.lang.String} object
	 */
	public static String readString(InputStream in, Charset charset)
	{
		return new String(readAll(in), charset);
	}

	/**
	 * <p>closeQuietly.</p>
	 *
	 * @param c a {@link java.io.Closeable} object
	 */
	public static void closeQuietly(Closeable c)
	{
		try
		{
			c.close();
		}
		catch (IOException e)
		{}
	}
	
	@Deprecated
	/**
	 * renamed to closeWithRE
	 *
	 * @param c a {@link java.io.Closeable} object
	 */
	public static void closeWithRuntimeException(Closeable c)
	{
		closeWithRE(c);
	}

	/**
	 * <p>closeWithRE.</p>
	 *
	 * @param c a {@link java.io.Closeable} object
	 */
	public static void closeWithRE(Closeable c)
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
	 * <p>Returns an empty reader. Which doesn't support marking.</p>
	 *
	 * @return an empty reader
	 */
	public static Reader emptyReader()
	{
		return emptyReader;
	}

	/**
	 * <p></p>
	 *
	 * @return
	 */
	public static Writer nowhereWriter()
	{
		return nowhereWriter;
	}

	private static final Reader emptyReader = new Reader()
	{
		@Override
		public int read() throws IOException
		{
			return -1;
		}

		@Override
		public int read(char[] cbuf, int off, int len)
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
		public void write(String str, int off, int len) {}

		@Override
		public void write(char[] cbuf, int off, int len) {}

		@Override
		public void flush() {}

		@Override
		public void close() {}
	};

	private IOUtil()
	{}
}
