package com.hk.lua;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import com.hk.Assets;
import com.hk.io.IOUtil;
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

	public void testCalls() throws FileNotFoundException
	{
		final LinkedList<LuaObject[]> expected = new LinkedList<>();
		expected.add(new LuaObject[] {
				Lua.newNumber(1),
				Lua.newNumber(2),
				Lua.newNumber(4),
		});
		expected.add(new LuaObject[] {
				Lua.newString("hello"),
				Lua.newString("world!"),
		});
		expected.add(new LuaObject[] {
				Lua.newString("a boolean"),
				Lua.newBoolean(true),
				Lua.newString("a false boolean"),
				Lua.newBoolean(false),
		});

		String[] strs = ("these are my values. there are many " +
				"like it but these are mine").split(" ");
		LuaObject[] objs = new LuaObject[strs.length];
		for(int i = 0; i < objs.length; i++)
			objs[i] = Lua.newString(strs[i]);
		expected.add(objs);

		expected.add(new LuaObject[] {
				Lua.newString("double quotes"),
				Lua.newString("single quotes"),
		});
		expected.add(new LuaObject[] {
				Lua.newString("w parenthesis"),
		});
		expected.add(new LuaObject[] {
				Lua.newString("wo parenthesis"),
		});
		expected.add(new LuaObject[] {
				Lua.newTable(Collections.singletonList(Lua.newString("in a table")))
		});
		expected.add(new LuaObject[0]);
		expected.add(new LuaObject[] {
				Lua.newString("using stdout"),
		});
		expected.add(new LuaObject[] {
				Lua.newString("using stdout wo parenthesis"),
		});
		expected.add(new LuaObject[] {
				Lua.newTable(Arrays.asList(Lua.newString("using stdout"), Lua.newString("in a table")))
		});
		expected.add(new LuaObject[0]);

		final int tests = expected.size();
		LuaWriter wtr = new LuaWriter(IOUtil.nowhereWriter())
		{
			@Override
			public LuaObject write(LuaObject[] values)
			{
				String msg = "subtest <" + (tests - expected.size() + 1) + ">";
				assertFalse(msg, expected.isEmpty());

				LuaObject[] objects = expected.removeFirst();

				assertEquals(msg, objects.length, values.length);

				for(int i = 0; i < objects.length; i++)
				{
					if(objects[i].isTable())
					{
						assertTrue(msg, values[i] instanceof LuaTable);
						assertEquals(msg, ((LuaTable) objects[i]).map, ((LuaTable) values[i]).map);
					}
					else
						assertEquals(msg, objects[i], values[i]);
				}

				return Lua.newBoolean(true);
			}
		};

		LuaInterpreter interp = Lua.reader(Assets.get("lua/calls.lua"));

		interp.setExtra(LuaLibraryIO.EXKEY_STDOUT, wtr);

		LuaLibrary.importStandard(interp);

		Object res = interp.execute();

		assertTrue(res instanceof LuaObject);
		assertTrue(((LuaObject) res).isBoolean());
		assertTrue(((LuaObject) res).getBoolean());
		assertTrue(wtr.isClosed());
		assertTrue(expected.isEmpty());
	}
	
	protected void tearDown()
	{
		interp = null;
	}
}
