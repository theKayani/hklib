package com.hk.ex;

public class OutOfBoundsException extends RuntimeException
{
	public OutOfBoundsException()
	{
	}

	public OutOfBoundsException(String message)
	{
		super(message);
	}

	public OutOfBoundsException(Throwable cause)
	{
		super(cause);
	}

	public OutOfBoundsException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public OutOfBoundsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	private static final long serialVersionUID = -8863697427281645767L;
}
