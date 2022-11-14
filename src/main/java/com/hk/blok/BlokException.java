package com.hk.blok;

public class BlokException extends Exception
{
	public BlokException()
	{
	}

	public BlokException(String message)
	{
		super(message);
	}

	public BlokException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BlokException(Throwable cause)
	{
		super(cause);
	}
}
