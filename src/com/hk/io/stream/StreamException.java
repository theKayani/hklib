package com.hk.io.stream;

import java.io.IOException;

public class StreamException extends IOException
{
	public StreamException()
	{
		super();
	}

	public StreamException(String message)
	{
		super(message);
	}

	public StreamException(Throwable e)
	{
		super(e);
	}

	public StreamException(String message, Throwable e)
	{
		super(message, e);
	}

	private static final long serialVersionUID = -449520458559123989L;
}
