package com.hk.lua;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import com.hk.Assets;

import junit.framework.TestCase;

public class LuaLibraryDateTest extends TestCase
{
	public void test() throws FileNotFoundException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/library_date.lua"));

		interp.setExtra(LuaLibraryDate.EXKEY_DATE_FORMAT, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		Lua.importStandard(interp);

		Object obj = interp.execute();

		assertTrue(obj instanceof LuaObject);
		assertTrue(((LuaObject) obj).getBoolean());
	}
}