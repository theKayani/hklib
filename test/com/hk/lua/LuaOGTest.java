package com.hk.lua;

import java.util.Collections;

import junit.framework.TestCase;

public class LuaOGTest extends TestCase
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
	
	public void testInterpreter()
	{
		assertNotNull(interp);
	}
	
	public void testNil()
	{
		String base = "return nil";
		assertEquals(interp.require(base), Lua.nil());
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testBoolean()
	{
		String base = "return true";
		assertEquals(interp.require(base), Lua.newBoolean(true));
		assertEquals(interp.require("return false"), Lua.newBoolean(false));
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testString()
	{
		String base = "return ''";
		assertEquals(interp.require(base), Lua.newString(""));
		assertEquals(interp.require("return 'Hello world!'"), Lua.newString("Hello world!"));
		assertEquals(interp.require("return '123'"), Lua.newString("123"));
		assertEquals(interp.require("return \"quotes\""), Lua.newString("quotes"));
		assertEquals(interp.require("return '6'"), Lua.newString('6'));
		assertEquals(interp.require("return nil"), Lua.newString((String) null));
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testNumber()
	{
		String base = "return 0";

		assertEquals(interp.require("return -2"), Lua.newNumber(-2));
		assertEquals(interp.require("return -1"), Lua.newNumber(-1));
		assertEquals(interp.require(base), Lua.newNumber(0));
		assertEquals(interp.require("return 1"), Lua.newNumber(1));
		assertEquals(interp.require("return 2"), Lua.newNumber(2));
		assertEquals(interp.require("return 5"), Lua.newNumber(5));

		assertEquals(interp.require("return -2"), Lua.newNumber(-2.0));
		assertEquals(interp.require("return -1"), Lua.newNumber(-1.0));
		assertEquals(interp.require(base), Lua.newNumber(0.0));
		assertEquals(interp.require("return 1"), Lua.newNumber(1.0));
		assertEquals(interp.require("return 2"), Lua.newNumber(2.0));
		assertEquals(interp.require("return 5"), Lua.newNumber(5.0));

		assertEquals(interp.require("return -2.0"), Lua.newNumber(-2));
		assertEquals(interp.require("return -1.0"), Lua.newNumber(-1));
		assertEquals(interp.require("return 0.0"), Lua.newNumber(0));
		assertEquals(interp.require("return 1.0"), Lua.newNumber(1));
		assertEquals(interp.require("return 2.0"), Lua.newNumber(2));
		assertEquals(interp.require("return 5.0"), Lua.newNumber(5));

		assertEquals(interp.require("return -2.0"), Lua.newNumber(-2.0));
		assertEquals(interp.require("return -1.0"), Lua.newNumber(-1.0));
		assertEquals(interp.require("return 0.0"), Lua.newNumber(0.0));
		assertEquals(interp.require("return 1.0"), Lua.newNumber(1.0));
		assertEquals(interp.require("return 2.0"), Lua.newNumber(2.0));
		assertEquals(interp.require("return 5.0"), Lua.newNumber(5.0));
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testInteger()
	{
		String base = "return 9223372036854775807";
		assertEquals(interp.require("return 2147483647"), Lua.newNumber(2147483647L));
		assertEquals(interp.require("return -2147483647"), Lua.newNumber(-2147483647L));
		assertEquals(interp.require("return 2147483648"), Lua.newNumber(2147483648L));
		assertEquals(interp.require("return -2147483648"), Lua.newNumber(-2147483648L));
		assertEquals(interp.require(base), Lua.newNumber(9223372036854775807L));
		assertEquals(interp.require("return -9223372036854775807"), Lua.newNumber(-9223372036854775807L));

		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testFloat()
	{
		String base = "return 1E99";
		assertEquals(interp.require(base), Lua.newNumber(1E99D));
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testTable()
	{
		String base = "return {}";
		
		assertEquals(((LuaTable) interp.require(base)).map, Collections.<LuaObject, LuaObject>emptyMap());

		assertEquals(((LuaTable) interp.require(base)).map, ((LuaTable) interp.require(base)).map);
	}
	
	public void testFunction()
	{
		String base = "return string.byte";
		assertEquals(interp.require(base), LuaLibraryString._byte.func);
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	protected void tearDown()
	{
		interp = null;
	}
}
