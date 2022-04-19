package com.hk.lua;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Stack;

/**
 * <p>LuaException class.</p>
 *
 * @author theKayani
 */
public class LuaException extends RuntimeException
{
	final String source;
	final int lineNumber;
	final boolean primary;
	boolean internal;
	Stack<LuaException> stacktrace;

	@SuppressWarnings("CopyConstructorMissesField")
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

	/**
	 * <p>Constructor for LuaException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
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

    /**
     * <p>fillInStackTrace.</p>
     *
     * @return a {@link java.lang.Throwable} object
     */
    public synchronized Throwable fillInStackTrace()
    {
//    	return super.fillInStackTrace();
    	return null;
    }

    /**
     * <p>getCause.</p>
     *
     * @return a {@link com.hk.lua.LuaException} object
     */
    public synchronized LuaException getCause()
    {
    	return (LuaException) super.getCause();
    }

    /**
     * <p>printStackTrace.</p>
     */
    public void printStackTrace()
    {
        printStackTrace(System.out);
    }

    /**
     * <p>printStackTrace.</p>
     *
     * @param s a {@link java.io.PrintStream} object
     */
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

    /**
     * <p>printStackTrace.</p>
     *
     * @param s a {@link java.io.PrintWriter} object
     */
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

	private interface WrappedPrinter
	{
		void print(Object o);

		void println(Object o);
	}

	private static final long serialVersionUID = 1691728901684388319L;
}