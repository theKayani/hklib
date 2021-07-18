package com.hk.ex;

public class InvalidInputException extends Exception
{	
	public InvalidInputException()
	{
	}

	public InvalidInputException(String message)
	{
		super(message);
	}

	public InvalidInputException(Throwable cause)
	{
		super(cause);
	}

	public InvalidInputException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public InvalidInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	private static final long serialVersionUID = 8793656911329713609L;
}
