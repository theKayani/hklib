package com.hk.lua;

import com.hk.Assets;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;

public class LuaLibraryPackageTest extends TestCase
{
	public void test() throws FileNotFoundException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/library_package.lua"));

		interp.setExtra(LuaLibraryIO.EXKEY_CWD, "./src/test/resources/assets/lua");

		Lua.importStandard(interp);
		interp.importLib(LuaLibrary.PACKAGE);

		Object obj = interp.execute();

		assertTrue(obj instanceof LuaObject);
		assertTrue(((LuaObject) obj).getBoolean());
	}
}