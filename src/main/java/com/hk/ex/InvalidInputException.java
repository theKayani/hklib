package com.hk.ex;

/**
 * <p>InvalidInputException class.</p>
 *
 * @author theKayani
 */
public class InvalidInputException extends Exception
{	
	/**
	 * <p>Constructor for InvalidInputException.</p>
	 */
	public InvalidInputException()
	{
	}

	/**
	 * <p>Constructor for InvalidInputException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
	public InvalidInputException(String message)
	{
		super(message);
	}

	/**
	 * <p>Constructor for InvalidInputException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object
	 */
	public InvalidInputException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * <p>Constructor for InvalidInputException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @param cause a {@link java.lang.Throwable} object
	 */
	public InvalidInputException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * <p>Constructor for InvalidInputException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @param cause a {@link java.lang.Throwable} object
	 * @param enableSuppression a boolean
	 * @param writableStackTrace a boolean
	 */
	protected InvalidInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	private static final long serialVersionUID = 8793656911329713609L;
}