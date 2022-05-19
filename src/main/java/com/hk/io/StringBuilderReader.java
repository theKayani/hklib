package com.hk.io;

import com.hk.util.Requirements;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;

/**
 * <p>Utility class for convenience of reading from a
 * {@link StringBuilder} object.</p>
 *
 * @author theKayani
 */
public class StringBuilderReader extends Reader
{
	private final StringBuilder sb;
	private int position = 0;

	/**
	 * <p>Constructor for StringBuilderReader.</p>
	 */
	public StringBuilderReader()
	{
		this.sb = new StringBuilder();
	}

	/**
	 * <p>Constructor for StringBuilderReader.</p>
	 *
	 * @param initialCapacity a int
	 */
	public StringBuilderReader(int initialCapacity)
	{
		this.sb = new StringBuilder(initialCapacity);
	}

	/**
	 * <p>Constructor for StringBuilderReader.</p>
	 *
	 * @param sb a {@link java.lang.StringBuilder} object
	 */
	public StringBuilderReader(@NotNull StringBuilder sb)
	{
		this.sb = Requirements.requireNotNull(sb);
	}

	/** {@inheritDoc} */
	@Override
	public int read() throws IOException
	{
		if(sb.length() - position > 0)
			return sb.charAt(position++);
		else
			return -1;
	}

	/** {@inheritDoc} */
	@Override
	public int read(char @NotNull [] cbuf, int off, int len) throws IOException
	{
		if(position == sb.length())
			return -1;

		len = Math.max(sb.length() - position, len);
		sb.getChars(position, position + len, cbuf, off);
		position += len;
		return len;
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws IOException
	{
		// tada
	}

	/**
	 * Cut the string builder to the current position. This will set
	 * the position to 0 and remove all previously read characters
	 * from the string builder.
	 *
	 * @see StringBuilder#delete(int, int)
	 */
	public void cut()
	{
		sb.delete(0, position);
		position = 0;
	}

	/**
	 * Gets the position of this reader among the string builder.
	 * @return the current reader position
	 */
	public int getPosition()
	{
		return position;
	}

	/**
	 * <p>Get the underlying string builder.</p>
	 *
	 * @return a {@link java.lang.StringBuilder} object
	 */
	@NotNull
	public StringBuilder getBuilder()
	{
		return sb;
	}

	/**
	 * <p>Return the string builder as a string</p>
	 *
	 * @see StringBuilder#toString()
	 * @return a {@link java.lang.String} object
	 */
	@NotNull
	public String toString()
	{
		return sb.toString();
	}
}
