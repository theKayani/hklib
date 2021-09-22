package com.hk.lua;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
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
	public LuaObject close() throws IOException
	{
		System.out.println("LuaReader.close");
		if(isClosed)
			throw new LuaException("reader already closed");

		reader.close();
		isClosed = true;

		return LuaNil.NIL;
	}

	@Override
	public LuaObject[] read(long[] formats) throws IOException
	{
		System.out.println("LuaReader.read");
		System.out.println("formats = " + Arrays.toString(formats));
		return new LuaObject[0];
	}

	@Override
	public Iterator<LuaObject> lines(long[] formats) throws IOException
	{
		System.out.println("LuaReader.lines");
		System.out.println("formats = " + Arrays.toString(formats));
		return null;
	}

	@Override
	public long seek(int mode, long offset) throws IOException
	{
		System.out.println("LuaReader.seek");
		System.out.println("mode = " + mode + ", offset = " + offset);
		return 0;
	}

	@Override
	public Object getUserdata()
	{
		return null;
	}
}
