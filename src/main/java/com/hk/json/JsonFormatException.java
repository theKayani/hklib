package com.hk.json;

/**
 * <p>JsonFormatException class.</p>
 *
 * @author theKayani
 */
public class JsonFormatException extends RuntimeException
{	
	JsonFormatException(String message)
	{
		super(message);
	}

	JsonFormatException(Throwable cause)
	{
		super(cause);
	}

	private static final long serialVersionUID = 4461709233785103096L;
}
