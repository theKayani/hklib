package com.hk.lua;

import com.hk.Assets;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Arrays;

public class LuaReaderTest extends TestCase
{
	public void test() throws FileNotFoundException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/library_io.lua"));

		LuaLibrary.importStandard(interp);
		interp.getGlobals().setVar("require", Lua.newFunc(new Lua.LuaMethod()
		{
			@Override
			public LuaObject call(LuaInterpreter interp, LuaObject[] args)
			{
				System.out.println(Arrays.toString(args));
				return null;
			}
		}));

//		LuaObject luaUnit = interp.require("luaunit.lua", new FileReader(Assets.get("lua/luaunit.lua")));
//		interp.getGlobals().setVar("luaunit", luaUnit);

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