package com.hk.ex;

import java.io.IOException;

/**
 * <p>UncheckedIOException class.</p>
 *
 * @author theKayani
 */
public class UncheckedIOException extends RuntimeException
{
	/**
	 * <p>Constructor for UncheckedIOException.</p>
	 *
	 * @param cause a {@link IOException} object
	 */
	public UncheckedIOException(IOException cause)
	{
		super(cause);
	}

	/**
	 * <p>Constructor for UncheckedIOException.</p>
	 *
	 * @param message a {@link String} object
	 * @param cause a {@link IOException} object
	 */
	public UncheckedIOException(String message, IOException cause)
	{
		super(message, cause);
	}

	private static final long serialVersionUID = -3033750061207003033L;
}
