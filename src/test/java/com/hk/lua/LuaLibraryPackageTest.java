package com.hk.lua;

import com.hk.Assets;
import com.hk.math.Rand;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.function.BiConsumer;

public class LuaLibraryPackageTest extends TestCase
{
	public void test() throws FileNotFoundException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/library_package.lua"));

		interp.setExtra(LuaLibraryIO.EXKEY_CWD, "./src/test/resources/assets/lua");

		Lua.importStandard(interp);
		interp.importLib(LuaLibrary.PACKAGE);

		LuaObject obj = interp.execute();

		assertNotNull(obj);
		assertTrue(obj.getBoolean());
	}

	public void testPreloaded() throws IOException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/test_preloaded.lua"));

		interp.setExtra(LuaLibraryIO.EXKEY_CWD, "./src/test/resources/assets/lua");

		// compile Lua from string
		interp.preload("1767/3350/6427/1525189", new StringReader("return { a=1,b=2,c=3 }"));

		// compile Lua from file/reader
		interp.preload("2312/8616/2516/5097625", Assets.get("lua/colors.lua").toPath());

		// import Lua from library
		interp.preload("7392/4355/5670/3202062", new LuaLibrary<>(null, LuaTestLibrary.class));

		// import Lua from factory
		LuaFactory factory = Lua.factory("local tbl = { 10, 20, 30, 40, 50 }\nreturn tbl");
		factory.compile();
		interp.preload("6203/1920/3565/6150394", factory);


		Lua.importStandard(interp);
		interp.importLib(LuaLibrary.PACKAGE);

		LuaObject obj = interp.execute();

		// confirm that it's been loaded by the 'require' function
		assertTrue(interp.hasModule("1767/3350/6427/1525189"));

		assertNotNull(obj);
		assertTrue(obj.getBoolean());
	}

	private enum LuaTestLibrary implements BiConsumer<Environment, LuaObject>
	{
		ABC_123, DEF_456, GHI_789;

		@Override
		public void accept(Environment environment, LuaObject table)
		{
			String[] sp = name().split("_");
			table.rawSet(sp[0], Integer.parseInt(sp[1]));
		}
	}
}