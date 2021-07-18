package com.hk.lua;

import com.hk.str.StringUtil;

import junit.framework.TestCase;

public class LuaStatementOGTest extends TestCase
{
	private LuaInterpreter interp;
	
	@Override
	protected void setUp()
	{
		interp = Lua.interpreter();
		interp.importLib(LuaLibrary.BASIC);
		interp.importLib(LuaLibrary.COROUTINE);
//		interp.importLib(LuaLibrary.PACKAGE);
		interp.importLib(LuaLibrary.STRING);
		interp.importLib(LuaLibrary.TABLE);
		interp.importLib(LuaLibrary.MATH);
		interp.importLib(LuaLibrary.IO);
		interp.importLib(LuaLibrary.OS);
//		interp.importLib(LuaLibrary.DEBUG);

		interp.importLib(LuaLibrary.JSON);
		interp.importLib(LuaLibrary.HASH);
	}
	
	public void testDo()
	{
		String[] tests = {
				"return -1",
				"return 0",
				"return 1",
				"return true",
				"return false",
				"return 3.14159265358979323846",
				"return \"myString\"",
				"return \"\"",
				"return '123'"
		};
		
		for(String test : tests)
			assertEquals(interp.require("do " + test + " end"), interp.require(test));
		
		for(String test : tests)
			assertEquals(interp.require(StringUtil.repeat("do ", 100) + test + StringUtil.repeat(" end", 100)), interp.require(test));
	}
	
	public void testNumericFor()
	{
		
	}
	
	protected void tearDown()
	{
		interp = null;
	}
}
