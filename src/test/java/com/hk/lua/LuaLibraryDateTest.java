package com.hk.lua;

import java.io.FileNotFoundException;

import com.hk.Assets;

import junit.framework.TestCase;

public class LuaLibraryDateTest extends TestCase
{
	private LuaInterpreter interp;
	
	@Override
	public void setUp() throws FileNotFoundException
	{
		interp = Lua.reader(Assets.get("lua/library_date.lua"));
		
		Lua.importStandard(interp);
	}
	
	public void test()
	{
		interp.execute();
	}

	@Override
	public void tearDown()
	{
		interp = null;
	}
}