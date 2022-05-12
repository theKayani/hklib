package com.hk.csv;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;

public class CsvWriter implements Closeable
{
	private final Writer wtr;
	private int line = 0;

	/**
	 * <p>Create a new CSV writer from a file. This doesn't write
	 * anything until the methods are called.</p>
	 *
	 * @param f a {@link java.io.File} object
	 */
	public CsvWriter(@NotNull File f) throws IOException
	{
		this(new FileWriter(f));
	}

	/**
	 * <p>Create a new CSV writer from a file using the given charset.
	 * This doesn't write anything until the methods are called.</p>
	 *
	 * @param f a {@link java.io.File} object
	 * @param charset charset to use
	 */
	public CsvWriter(@NotNull File f, @NotNull Charset charset) throws IOException
	{
		this(new FileOutputStream(f), charset);
	}

	/**
	 * <p>Create a new CSV writer from a Java writer. This doesn't
	 * write anything until the methods are called.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object
	 */
	public CsvWriter(@NotNull OutputStream out)
	{
		this(new OutputStreamWriter(out));
	}

	/**
	 * <p>Create a new CSV writer from a Java writer using the given
	 * charset. This doesn't write anything until the methods are
	 * called.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object
	 * @param charset charset to use
	 */
	public CsvWriter(@NotNull OutputStream out, @NotNull Charset charset)
	{
		this(new OutputStreamWriter(out, charset));
	}

	/**
	 * <p>Create a new CSV writer from a Java writer. This doesn't
	 * write anything until the methods are called.</p>
	 *
	 * @param wtr a {@link java.io.Writer} object
	 */
	public CsvWriter(@NotNull Writer wtr)
	{
		this.wtr = Objects.requireNonNull(wtr);
	}

	public int getLineNumber()
	{
		return line;
	}

	public void writeLine(Object... values) throws IOException
	{
		for (int i = 0; i < values.length; i++)
		{
			String value = String.valueOf(values[i]);


			if(i < values.length - 1)
				wtr.write(',');
		}
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws IOException
	{
		wtr.close();
	}
}
