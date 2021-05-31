package com.hk.json;

public class JsonAdaptationException extends RuntimeException
{
	public JsonAdaptationException()
	{
		super();
	}

	public JsonAdaptationException(String message)
	{
		super(message);
	}

	public JsonAdaptationException(Throwable cause)
	{
		super(cause);
	}

	private static final long serialVersionUID = 7195553027489100229L;
}
