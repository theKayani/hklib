package com.hk.io;

import java.io.Writer;

import com.hk.util.Requirements;
import org.jetbrains.annotations.NotNull;

/**
 * <p>StringBuilderWriter class.</p>
 *
 * @author theKayani
 */
public class StringBuilderWriter extends Writer
{
	private final StringBuilder sb;

	/**
	 * <p>Constructor for StringBuilderWriter.</p>
	 */
	public StringBuilderWriter()
	{
		this.sb = new StringBuilder();
	}

	/**
	 * <p>Constructor for StringBuilderWriter.</p>
	 *
	 * @param initialCapacity a int
	 */
	public StringBuilderWriter(int initialCapacity)
	{
		this.sb = new StringBuilder(initialCapacity);
	}

	/**
	 * <p>Constructor for StringBuilderWriter.</p>
	 *
	 * @param sb a {@link java.lang.StringBuilder} object
	 */
	public StringBuilderWriter(@NotNull StringBuilder sb)
	{
		this.sb = Requirements.requireNotNull(sb);
	}

	/** {@inheritDoc} */
	@Override
	public void write(char @NotNull [] cbuf, int off, int len)
	{
		sb.append(cbuf, off, len);
	}

	/** {@inheritDoc} */
	@Override
	public void flush()
	{
		// tada
	}

	/** {@inheritDoc} */
	@Override
	public void close()
	{
		// tada
	}

	/**
	 * <p>getBuilder.</p>
	 *
	 * @return a {@link java.lang.StringBuilder} object
	 */
	@NotNull
	public StringBuilder getBuilder()
	{
		return sb;
	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	@NotNull
	public String toString()
	{
		return sb.toString();
	}
}