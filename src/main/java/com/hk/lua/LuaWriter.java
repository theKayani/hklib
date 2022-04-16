package com.hk.lua;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class LuaWriter extends LuaLibraryIO.LuaIOUserdata
{
	private final Writer writer;
	private BufferedWriter bufferedWriter;
	private boolean isClosed;

	public LuaWriter(Writer writer)
	{
		this.writer = writer;
	}

	@Override
	public boolean isClosed()
	{
		return isClosed;
	}

	@Override
	public LuaObject close() throws IOException
	{
		if(isClosed)
			throw new LuaException("output is already closed");

		writer.close();
		isClosed = true;

		return LuaBoolean.TRUE;
	}

	@Override
	public LuaObject flush() throws IOException
	{
		checkClosed();
		writer.flush();
		return LuaBoolean.TRUE;
	}

	@Override
	public LuaObject[] read(int[] formats)
	{
		return new LuaObject[] {
				LuaNil.NIL, new LuaString("invalid call to writable")
		};
	}

	@Override
	public long seek(int mode, long offset)
	{
		checkClosed();
		throw new LuaException("UnsupportedOperationException in this version of Lua (JVM)");
	}

	@Override
	public LuaObject setvbuf(int mode, int size)
	{
		checkClosed();
		throw new LuaException("UnsupportedOperationException in this version of Lua (JVM)");
	}

	@Override
	public LuaObject write(LuaObject[] values) throws IOException
	{
		checkClosed();

		int param = 1;
		for(LuaObject value : values)
		{
			if(value.isNumber() || value.isString())
			{
				writer.write(value.getString());
			}
			else
				return Lua.wrapErr("bad argument #" + param + " to 'write' (expected string or number)");

			param++;
		}

		return LuaBoolean.TRUE;
	}

	private void checkClosed()
	{
		if(isClosed)
			throw new LuaException("output is closed");
	}

	@Override
	public Writer getUserdata()
	{
		return writer;
	}
}
