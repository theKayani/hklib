package com.hk.io;

import java.io.Writer;

import com.hk.util.Requirements;

public class StringBuilderWriter extends Writer
{
	private final StringBuilder sb;
	
	public StringBuilderWriter()
	{
		this.sb = new StringBuilder();
	}
	
	public StringBuilderWriter(int initialCapacity)
	{
		this.sb = new StringBuilder(initialCapacity);
	}
	
	public StringBuilderWriter(StringBuilder sb)
	{
		this.sb = Requirements.requireNotNull(sb);
	}

	@Override
	public void write(char[] cbuf, int off, int len)
	{
		sb.append(cbuf, off, len);
	}

	@Override
	public void flush()
	{
		// tada
	}

	@Override
	public void close()
	{
		// tada
	}
	
	public StringBuilder getBuilder()
	{
		return sb;
	}
	
	public String toString()
	{
		return sb.toString();
	}
}
