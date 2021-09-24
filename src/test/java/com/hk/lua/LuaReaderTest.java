package com.hk.lua;

import com.hk.Assets;
import com.hk.io.IOUtil;
import junit.framework.TestCase;

import javax.naming.RefAddr;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.channels.SeekableByteChannel;
import java.util.Arrays;

public class LuaReaderTest extends TestCase
{
	private LuaInterpreter interp;

	@Override
	protected void setUp() throws FileNotFoundException
	{
		interp = Lua.reader(Assets.get("lua/test_stdin.lua"));

		LuaLibrary.importStandard(interp);

		interp.execute();
	}

	public void testClose()
	{
		interp.setExtra(LuaLibraryIO.EXKEY_STDIN, new LuaReader(IOUtil.emptyReader()));

		LuaObject obj = interp.getGlobals().getVar("testClose").call(interp);
		assertTrue(obj.getBoolean());
	}

	public void testRead() throws IOException
	{
		String str;
		LuaObject obj;

		str = "\"\u00E1lo\"{a}\n" +
				"second line\n" +
				"third line \n" +
				"\u00E7fourth_line\n" +
				"\n" +
				"\t\t  3450\n";
		interp.setExtra(LuaLibraryIO.EXKEY_STDIN, new LuaReader(new StringReader(str)));

		obj = interp.getGlobals().getVar("testRead").call(interp);
		assertTrue(obj.getBoolean());

		str = "\n\nline\nother";
		Reader rdr = new StringReader(str);
		interp.setExtra(LuaLibraryIO.EXKEY_STDIN, new LuaReader(rdr));

		obj = interp.getGlobals().getVar("testReadLineFormatIO").call(interp);
		assertTrue(obj.getBoolean());

		rdr.reset();

		obj = interp.getGlobals().getVar("testReadLineFormatInput").call(interp);
		assertTrue(obj.getBoolean());

		rdr.reset();

		obj = interp.getGlobals().getVar("testReadLowLineFormat").call(interp);
		assertTrue(obj.getBoolean());

		str = " 123.4\t-56e-2  not a number\n" +
				"second line\n" +
				"third line\n" +
				"\n" +
				"and the rest of the file\n";
		interp.setExtra(LuaLibraryIO.EXKEY_STDIN, new LuaReader(new StringReader(str)));

		obj = interp.getGlobals().getVar("testReadMultipleFormats").call(interp);
		assertTrue(obj.getBoolean());
	}

	public void testLines() throws IOException
	{
		String str;
		LuaObject obj;

		str = "1\n2\n3\n4\n5\n6";
		Reader rdr = new StringReader(str);
		interp.setExtra(LuaLibraryIO.EXKEY_STDIN, new LuaReader(rdr));

		obj = interp.getGlobals().getVar("testLinesCount").call(interp);
		assertTrue(obj.isNumber());
		assertEquals(6, obj.getInteger());

		rdr.reset();

		obj = interp.getGlobals().getVar("testLinesNumbers").call(interp);
		assertTrue(obj.isNumber());
		assertEquals(6, obj.getInteger());

		StringBuilder sb = new StringBuilder(3892);
		for(int i = 0; i < 999; i++)
			sb.append(i + 1).append('\n');

		sb.append("1000");

		rdr = new StringReader(sb.toString());
		interp.setExtra(LuaLibraryIO.EXKEY_STDIN, new LuaReader(rdr));

		obj = interp.getGlobals().getVar("testLinesCount").call(interp);
		assertTrue(obj.isNumber());
		assertEquals(1000, obj.getInteger());

		rdr.reset();

		obj = interp.getGlobals().getVar("testLinesNumbers").call(interp);
		assertTrue(obj.isNumber());
		assertEquals(1000, obj.getInteger());

		str = "0123456789\n";
		rdr = new StringReader(str);
		interp.setExtra(LuaLibraryIO.EXKEY_STDIN, new LuaReader(rdr));

		for (int i = 1; i <= 3; i++)
		{
			obj = interp.getGlobals().getVar("testLinesMultipleFormats" + i).call(interp);
			assertTrue(obj.getBoolean());

			rdr.reset();
		}

		str = "00\n10\n20\n30\n40\n";
		rdr = new StringReader(str);
		interp.setExtra(LuaLibraryIO.EXKEY_STDIN, new LuaReader(rdr));

		obj = interp.getGlobals().getVar("testLinesMultipleNumberFormats").call(interp);
		assertTrue(obj.getBoolean());
	}

	public void testSeek()
	{
	}
}