package com.hk.lua;

import com.hk.Assets;
import com.hk.math.Rand;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class LuaTest extends TestCase
{
	@Override
	public void setUp() throws IOException
	{
	}

	public void testFactories() throws IOException
	{
		LuaInterpreter interp;
		LuaFactory factory = Lua.factory(Assets.get("lua/test_factories.lua"));

		factory.compile();

		Lua.importStandard(factory);

		Object res;

		interp = factory.build();
		interp.getGlobals().setVar("arr", Lua.newTable(Collections.singletonList(Lua.newNumber(0))));
		res = interp.execute(Lua.newNumber(1L), Lua.newNumber(0L));
		assertTrue(res instanceof LuaObject);
		assertTrue(((LuaObject) res).getBoolean());

		interp = factory.build();
		interp.getGlobals().setVar("arr", Lua.newTable(Arrays.asList(Lua.newNumber(3), Lua.newNumber(2), Lua.newNumber(1))));
		res = interp.execute(Lua.newNumber(3L), Lua.newNumber(6L));
		assertTrue(res instanceof LuaObject);
		assertTrue(((LuaObject) res).getBoolean());

		Random rng = new Random(8593640562223858172L);
		for (int i = 0; i < 10; i++)
		{
			interp = factory.build();
			LuaObject tbl = Lua.newTable();
			int len = rng.nextInt(46) + 5; // 5 - 50
			int total = 0;
			for (int j = 1; j <= len; j++)
			{
				int v = rng.nextInt(100);
				total += v;
				tbl.rawSet(j, Lua.newNumber(v));
			}

			interp.getGlobals().setVar("arr", tbl);
			res = interp.execute(Lua.newNumber(len), Lua.newNumber(total));
			assertTrue(res instanceof LuaObject);
			assertTrue(((LuaObject) res).getBoolean());
		}

		for (int i = 5; i <= 25; i++)
		{
			interp = factory.build();
			res = interp.execute(Lua.newNumber(i));
			assertTrue(res instanceof LuaObject);
			assertTrue(((LuaObject) res).getBoolean());
		}
	}

	public void testMain() throws FileNotFoundException
	{
		File mainFile = Assets.get("lua/main.lua");

		if(!mainFile.exists())
			return;

		long start;
		final LuaInterpreter interp = Lua.reader(mainFile);

		start = System.nanoTime();
		interp.compile();
		double compileTime = (System.nanoTime() - start) / 1E6D;

		Lua.importStandard(interp);

		Object obj;
		try
		{
			start = System.nanoTime();
			obj = interp.execute();
			double executeTime = (System.nanoTime() - start) / 1E6D;
			System.out.println("(time) " + compileTime + ", " + executeTime);
		}
		catch(LuaException e)
		{
			System.out.println("(time) " + compileTime);
			throw e;
		}

		System.out.println(obj);
	}

	public void testInheritance() throws FileNotFoundException
	{
		final LuaInterpreter interp = Lua.reader(Assets.get("lua/test_inheritance.lua"));

		Lua.importStandard(interp);

		Object obj = interp.execute();

		assertTrue(obj instanceof LuaObject);
		assertTrue(((LuaObject) obj).getBoolean());
	}

	public void testFactory()
	{
		// TODO: Lua.factory(java.io.InputStream, java.nio.charset.Charset)

		// TODO: Lua.factory(java.io.InputStream)

		// TODO: Lua.factory(java.net.URL)

		// TODO: Lua.factory(java.io.Reader)

		// TODO: Lua.factory(String)

		// TODO: Lua.factory(char[])

		// TODO: Lua.factory(java.io.File)

		// TODO: Lua.factory(java.io.File, java.nio.charset.Charset)
	}

	public void testReader()
	{
		// TODO: Lua.reader(java.net.URL)

		// TODO: Lua.reader(String)

		// TODO: Lua.reader(java.io.Reader)

		// TODO: Lua.reader(java.io.InputStream)

		// TODO: Lua.reader(java.io.File)

		// TODO: Lua.reader(java.io.File, java.nio.charset.Charset)

		// TODO: Lua.reader(char[])

		// TODO: Lua.reader(java.io.InputStream, java.nio.charset.Charset)
	}

	public void testNewBoolean()
	{
		// TODO: Lua.newBoolean(boolean)
	}

	public void testNewVarargs()
	{
		// TODO: Lua.newVarargs(com.hk.lua.LuaObject[])
	}

	public void testNewNumber()
	{
		// TODO: Lua.newNumber(double)

		// TODO: Lua.newNumber(long)
	}

	public void testInterpreter()
	{
		// TODO: Lua.interpreter()
	}

	public void testCheck()
	{
		// TODO: Lua.check(String, com.hk.lua.LuaObject[], com.hk.lua.LuaType[])
	}

	public void testNewString()
	{
		// TODO: Lua.newString(CharSequence)

		// TODO: Lua.newString(String)

		// TODO: Lua.newString(char)

		// TODO: Lua.newString(char[])
	}

	public void testNil()
	{
		// TODO: Lua.nil()
	}

	public void testNewFunc()
	{
		// TODO: Lua.newFunc(com.hk.lua.Lua$LuaMethod)
	}

	public void testNewJavaFunc()
	{
		// TODO: Lua.newJavaFunc(Object, String)

		// TODO: Lua.newJavaFunc(Class, String)
	}

	public void testNewLuaObject()
	{
		// TODO: Lua.newLuaObject(Object)
	}

	public void testNewTable()
	{
		// TODO: Lua.newTable(Iterable)

		// TODO: Lua.newTable(java.util.Map)

		// TODO: Lua.newTable()
	}

	public void testCheckArgs()
	{
		// TODO: Lua.checkArgs(String, com.hk.lua.LuaObject[], com.hk.lua.LuaType[])

		// TODO: Lua.checkArgs(String, com.hk.lua.LuaType[], com.hk.lua.LuaObject[])
	}

	@Override
	public void tearDown()
	{
		// TODO: create or delete
	}
}