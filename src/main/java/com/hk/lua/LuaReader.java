package com.hk.lua;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class LuaReader extends LuaLibraryIO.LuaInput
{
	private final Reader reader;
	private boolean isClosed;

	public LuaReader(Reader reader)
	{
		this.reader = reader;
	}

	@Override
	public boolean isClosed()
	{
		return isClosed;
	}

	@Override
	public Integer close() throws IOException
	{
		if(isClosed)
			throw new LuaException("reader already closed");

		reader.close();
		isClosed = true;

		return null;
	}

	@Override
	public LuaObject[] read(int[] formats) throws IOException
	{
		return new LuaObject[0];
	}

	@Override
	public Iterator<LuaObject> lines(int[] formats) throws IOException
	{
		return null;
	}

	@Override
	public long seek(int mode, long offset) throws IOException
	{
		return 0;
	}

	@Override
	public Object getUserdata()
	{
		return null;
	}
}
