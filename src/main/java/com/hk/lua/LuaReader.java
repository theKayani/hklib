package com.hk.lua;

import java.io.BufferedReader;
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
		this.reader = reader.markSupported() ? reader : new BufferedReader(reader);
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
	public LuaObject[] read(int[] formats) throws IOException
	{
		String numchars = "+-.0123456789xXpPaAbBcCdDeEfF";
		LuaObject[] result = new LuaObject[formats.length];

		int j;
		StringBuilder sb;
		LuaObject obj;
		for(int i = 0; i < formats.length; i++)
		{
			obj = null;

			switch (formats[i]) {
				case LuaLibraryIO.READMODE_N:
					sb = new StringBuilder();

					while(true)
					{
						reader.mark(1);
						if((j = reader.read()) == -1)
							break;

						if(sb.length() == 0 && Character.isWhitespace(j))
							continue;

						if(numchars.indexOf(j) == -1)
						{
							reader.reset();
							break;
						}

						sb.append((char) j);
					}

					if(sb.length() > 0)
					{
						Number number = LuaString.getNumber(sb.toString());

						if (number instanceof Long)
							obj = LuaInteger.valueOf(number.longValue());
						else if (number instanceof Double)
							obj = new LuaFloat(number.doubleValue());
					}
					break;
				case LuaLibraryIO.READMODE_A:
					sb = new StringBuilder();

					while((j = reader.read()) != -1)
						sb.append((char) j);

					obj = new LuaString(sb);
					break;
				case LuaLibraryIO.READMODE_L:
				case LuaLibraryIO.READMODE_LOW_L:
					sb = new StringBuilder();
					while((j = reader.read()) != -1)
					{
						if((char) j == '\n')
						{
							if(formats[i] == LuaLibraryIO.READMODE_L)
								sb.append('\n');
							break;
						}

						sb.append((char) j);
					}

					if (j != -1 && sb.length() >= 0)
						obj = new LuaString(sb);
					break;
				case 0:
					reader.mark(1);

					j = reader.read();

					if(j != -1)
					{
						reader.reset();
						obj = new LuaString("");
					}
					break;
				default:
					char[] cs = new char[formats[i]];

					int k;
					for(k = 0; k < cs.length; k++)
					{
						j = reader.read();

						if(j == -1)
							break;

						cs[k] = (char) j;
					}

					if ((k != 0) ^ (cs.length == 0))
						obj = new LuaString(new String(cs, 0, k));
			}

			result[i] = obj == null ? LuaNil.NIL : obj;
		}
		return result;
	}

	@Override
	public Iterator<LuaObject> lines(int[] formats) throws IOException
	{
		System.out.println("LuaReader.lines");
		System.out.println("formats = " + Arrays.toString(formats));
		return null;
	}

	@Override
	public long seek(int mode, long offset) throws IOException
	{
		throw new LuaException("UnsupportedOperationException in this version of Lua (JVM)");
	}

	@Override
	public Reader getUserdata()
	{
		return reader;
	}
}
