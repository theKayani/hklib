package com.hk.lua;

import com.hk.Assets;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Arrays;

public class LuaReaderTest extends TestCase
{
	public void testIsClosed()
	{
	}

	public void testClose()
	{
	}

	public void testRead() throws FileNotFoundException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/test_stdin.lua"));

		LuaLibrary.importStandard(interp);

		String str = "\"álo\"{a}\nsecond line\nthird line \nçfourth_line\n\n\t\t  3450\n";
		interp.setExtra(LuaLibraryIO.EXKEY_STDIN, new LuaReader(new StringReader(str)));

		Object obj = interp.execute();
		assertTrue(obj instanceof LuaObject);
		assertTrue(((LuaObject) obj).getBoolean());
	}

	public void testLines()
	{
	}

	public void testSeek()
	{
	}
}