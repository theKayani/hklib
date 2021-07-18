package com.hk.json;

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
