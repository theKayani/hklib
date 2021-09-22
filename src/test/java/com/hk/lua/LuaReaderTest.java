package com.hk.lua;

import com.hk.Assets;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class LuaReaderTest extends TestCase
{
	public void test() throws FileNotFoundException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/test.lua"));

		LuaLibrary.importStandard(interp);

		Object res = interp.execute();

		if(res instanceof Object[])
			System.out.println("res = " + Arrays.deepToString((Object[]) res));
		else
			System.out.println("res = " + res);
	}

	public void testIsClosed()
	{
	}

	public void testClose()
	{
	}

	public void testRead()
	{
	}

	public void testLines()
	{
	}

	public void testSeek()
	{
	}
}