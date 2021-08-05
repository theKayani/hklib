package com.hk.ex;

/**
 * <p>OutOfBoundsException class.</p>
 *
 * @author theKayani
 */
public class OutOfBoundsException extends RuntimeException
{
	/**
	 * <p>Constructor for OutOfBoundsException.</p>
	 */
	public OutOfBoundsException()
	{
	}

	/**
	 * <p>Constructor for OutOfBoundsException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
	public OutOfBoundsException(String message)
	{
		super(message);
	}

	/**
	 * <p>Constructor for OutOfBoundsException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object
	 */
	public OutOfBoundsException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * <p>Constructor for OutOfBoundsException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @param cause a {@link java.lang.Throwable} object
	 */
	public OutOfBoundsException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * <p>Constructor for OutOfBoundsException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @param cause a {@link java.lang.Throwable} object
	 * @param enableSuppression a boolean
	 * @param writableStackTrace a boolean
	 */
	protected OutOfBoundsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	private static final long serialVersionUID = -8863697427281645767L;
}
