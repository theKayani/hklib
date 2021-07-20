package com.hk.io.stream;

import java.io.IOException;

/**
 * <p>StreamException class.</p>
 *
 * @author theKayani
 */
public class StreamException extends IOException
{
	/**
	 * <p>Constructor for StreamException.</p>
	 */
	public StreamException()
	{
		super();
	}

	/**
	 * <p>Constructor for StreamException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
	public StreamException(String message)
	{
		super(message);
	}

	/**
	 * <p>Constructor for StreamException.</p>
	 *
	 * @param e a {@link java.lang.Throwable} object
	 */
	public StreamException(Throwable e)
	{
		super(e);
	}

	/**
	 * <p>Constructor for StreamException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @param e a {@link java.lang.Throwable} object
	 */
	public StreamException(String message, Throwable e)
	{
		super(message, e);
	}

	private static final long serialVersionUID = -449520458559123989L;
}
