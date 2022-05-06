package com.hk.lua;

import com.hk.Assets;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class LuaLibraryIOTest extends TestCase
{
	public void testCWD() throws FileNotFoundException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/test_cwd.lua"));
		Lua.importStandard(interp);
		interp.execute();

		LuaObject obj;

		String dir = System.getProperty("user.dir");

		interp.setExtra(LuaLibraryIO.EXKEY_CWD, dir);
		obj = interp.getGlobals().getVar("butNotTheDeputy").call(interp);
		assertTrue(obj.getBoolean());

		interp.setExtra(LuaLibraryIO.EXKEY_CWD, dir + "/src/test/resources");
		obj = interp.getGlobals().getVar("butISwearItWasSelfDefense").call(interp);
		assertTrue(obj.getBoolean());

		interp.setExtra(LuaLibraryIO.EXKEY_CWD, dir + "/src/test/resources/assets/lua");
		obj = interp.getGlobals().getVar("andTheySayItIsACapitalOffense").call(interp);
		assertTrue(obj.getBoolean());
	}
}