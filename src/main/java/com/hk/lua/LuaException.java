package com.hk.lua;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Stack;

public class LuaException extends RuntimeException
{
	final String source;
	final int lineNumber;
	final boolean primary;
	boolean internal;
	Stack<LuaException> stacktrace;
	
	LuaException(LuaException parent)
	{
		super("[J]: in ?");
		primary = false;
		source = null;
		lineNumber = -1;
		stacktrace = parent.stacktrace;
		stacktrace.push(this);
	}
	
	LuaException(String source, int lineNumber, String message)
	{
		this(source, lineNumber, message, null);
	}
	
	LuaException(String source, int lineNumber, String message, LuaException cause)
	{
		super(source + ":" + (lineNumber + 1) + ": " + message);
		this.source = source;
		this.lineNumber = lineNumber;
		primary = false;

		if(cause != null)
		{
			stacktrace = cause.stacktrace;
			stacktrace.push(this);
		}
		else
		{
			stacktrace = new Stack<>();
		}
	}
	
	public LuaException(String message)
	{
		super(message);
		primary = true;
		source = null;
		lineNumber = -1;
		stacktrace = new Stack<>();
	}
	
	LuaException internal()
	{
		internal = true;
		return this;
	}

    public synchronized Throwable fillInStackTrace()
    {
//    	return super.fillInStackTrace();
    	return null;
    }

    public synchronized LuaException getCause()
    {
    	return (LuaException) super.getCause();
    }
    
    public void printStackTrace()
    {
        printStackTrace(System.out);
    }

    public void printStackTrace(final PrintStream s)
    {
        printStackTrace(new WrappedPrinter() {
			@Override
			public void print(Object o)
			{
				s.print(o);
			}

			@Override
			public void println(Object o)
			{
				s.println(o);
			}
		});
    }

    public void printStackTrace(final PrintWriter s)
    {
        printStackTrace(new WrappedPrinter() {
			@Override
			public void print(Object o)
			{
				s.print(o);
			}

			@Override
			public void println(Object o)
			{
				s.println(o);
			}
		});
    }

	private void printStackTrace(WrappedPrinter out)
	{
		out.println("lua: " + getLocalizedMessage());
		if(!stacktrace.isEmpty())
		{
			out.println("stack traceback:");
			for(LuaException ex : stacktrace)
			{
				out.print('\t');
				out.println(ex.getLocalizedMessage());
			}
		}
	}

	static class LuaExitException extends RuntimeException
	{
		final int code;
		
		LuaExitException(int code)
		{
			this.code = code;
		}
	
		private static final long serialVersionUID = -5800760852156641968L;
	}
	
	private static interface WrappedPrinter
	{
		void print(Object o);

		void println(Object o);
	}

	private static final long serialVersionUID = 1691728901684388319L;
}
