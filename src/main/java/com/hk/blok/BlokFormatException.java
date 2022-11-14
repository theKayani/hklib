package com.hk.blok;

public class BlokFormatException extends RuntimeException
{
	public BlokFormatException()
	{
	}

	public BlokFormatException(String message)
	{
		super(message);
	}

	public BlokFormatException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BlokFormatException(Throwable cause)
	{
		super(cause);
	}
}