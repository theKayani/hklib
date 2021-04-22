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
		assertEquals(Lua.nil(), interp.require(base));
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testBoolean()
	{
		String base = "return true";
		assertEquals(Lua.newBoolean(true), interp.require(base));
		assertEquals(Lua.newBoolean(false), interp.require("return false"));
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testString()
	{
		String base = "return ''";
		assertEquals(Lua.newString(""), interp.require(base));
		assertEquals(Lua.newString("Hello world!"), interp.require("return 'Hello world!'"));
		assertEquals(Lua.newString("123"), interp.require("return '123'"));
		assertEquals(Lua.newString("quotes"), interp.require("return \"quotes\""));
		assertEquals(Lua.newString('6'), interp.require("return '6'"));
		assertEquals(Lua.newString((String) null), interp.require("return nil"));
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testNumber()
	{
		String base = "return 0";

		assertEquals(Lua.newNumber(-2), interp.require("return -2"));
		assertEquals(Lua.newNumber(-1), interp.require("return -1"));
		assertEquals(Lua.newNumber(0), interp.require(base));
		assertEquals(Lua.newNumber(1), interp.require("return 1"));
		assertEquals(Lua.newNumber(2), interp.require("return 2"));
		assertEquals(Lua.newNumber(5), interp.require("return 5"));

		assertEquals(Lua.newNumber(-2.0), interp.require("return -2"));
		assertEquals(Lua.newNumber(-1.0), interp.require("return -1"));
		assertEquals(Lua.newNumber(0.0), interp.require(base));
		assertEquals(Lua.newNumber(1.0), interp.require("return 1"));
		assertEquals(Lua.newNumber(2.0), interp.require("return 2"));
		assertEquals(Lua.newNumber(5.0), interp.require("return 5"));

		assertEquals(Lua.newNumber(-2), interp.require("return -2.0"));
		assertEquals(Lua.newNumber(-1), interp.require("return -1.0"));
		assertEquals(Lua.newNumber(0), interp.require("return 0.0"));
		assertEquals(Lua.newNumber(1), interp.require("return 1.0"));
		assertEquals(Lua.newNumber(2), interp.require("return 2.0"));
		assertEquals(Lua.newNumber(5), interp.require("return 5.0"));

		assertEquals(Lua.newNumber(-2.0), interp.require("return -2.0"));
		assertEquals(Lua.newNumber(-1.0), interp.require("return -1.0"));
		assertEquals(Lua.newNumber(0.0), interp.require("return 0.0"));
		assertEquals(Lua.newNumber(1.0), interp.require("return 1.0"));
		assertEquals(Lua.newNumber(2.0), interp.require("return 2.0"));
		assertEquals(Lua.newNumber(5.0), interp.require("return 5.0"));
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testInteger()
	{
		String base = "return 9223372036854775807";
		assertEquals(Lua.newNumber(2147483647L), interp.require("return 2147483647"));
		assertEquals(Lua.newNumber(-2147483647L), interp.require("return -2147483647"));
		assertEquals(Lua.newNumber(2147483648L), interp.require("return 2147483648"));
		assertEquals(Lua.newNumber(-2147483648L), interp.require("return -2147483648"));
		assertEquals(Lua.newNumber(9223372036854775807L), interp.require(base));
		assertEquals(Lua.newNumber(-9223372036854775807L), interp.require("return -9223372036854775807"));

		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testFloat()
	{
		String base = "return 1E99";
		assertEquals(Lua.newNumber(1E99D), interp.require(base));
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	public void testTable()
	{
		String base = "return {}";
		
		assertEquals(Collections.<LuaObject, LuaObject>emptyMap(), ((LuaTable) interp.require(base)).map);

		assertEquals(((LuaTable) interp.require(base)).map, ((LuaTable) interp.require(base)).map);
	}
	
	public void testFunction()
	{
		String base = "return string.byte";
		assertEquals(LuaLibraryString._byte.func, interp.require(base));
		
		assertEquals(interp.require(base), interp.require(base));
	}
	
	protected void tearDown()
	{
		interp = null;
	}
}
