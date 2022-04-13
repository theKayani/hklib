package com.hk.lua;

import com.hk.Assets;
import junit.framework.TestCase;

import java.io.FileNotFoundException;

public class LuaLibraryReflectTest extends TestCase
{
	@Override
	public void setUp()
	{
		// TODO: create or delete
	}

	public void test() throws FileNotFoundException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/library_reflect.lua"));

		Lua.importStandard(interp);
		interp.importLib(LuaLibrary.REFLECT);

		Object obj = interp.execute();

		assertTrue(obj instanceof LuaObject);
		assertTrue(((LuaObject) obj).getBoolean());
	}

	@Override
	public void tearDown()
	{
		// TODO: create or delete
	}
}