package com.hk.json;

/**
 * <p>JsonAdaptationException class.</p>
 *
 * @author theKayani
 */
public class JsonAdaptationException extends RuntimeException
{
	/**
	 * <p>Constructor for JsonAdaptationException.</p>
	 */
	public JsonAdaptationException()
	{
		super();
	}

	/**
	 * <p>Constructor for JsonAdaptationException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
	public JsonAdaptationException(String message)
	{
		super(message);
	}

	/**
	 * <p>Constructor for JsonAdaptationException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object
	 */
	public JsonAdaptationException(Throwable cause)
	{
		super(cause);
	}

	private static final long serialVersionUID = 7195553027489100229L;
}
