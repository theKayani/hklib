package com.hk.lua;

import com.hk.Assets;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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

		LuaObject res;

		interp = factory.build();
		interp.getGlobals().setVar("arr", Lua.newTable(Collections.singletonList(Lua.newNumber(0))));
		res = interp.execute(Lua.newNumber(1L), Lua.newNumber(0L));
		assertNotNull(res);
		assertTrue(res.getBoolean());

		interp = factory.build();
		interp.getGlobals().setVar("arr", Lua.newTable(Arrays.asList(Lua.newNumber(3), Lua.newNumber(2), Lua.newNumber(1))));
		res = interp.execute(Lua.newNumber(3L), Lua.newNumber(6L));
		assertNotNull(res);
		assertTrue(res.getBoolean());

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
			assertNotNull(res);
			assertTrue(res.getBoolean());
		}

		for (int i = 5; i <= 25; i++)
		{
			interp = factory.build();
			res = interp.execute(Lua.newNumber(i));
			assertNotNull(res);
			assertTrue(res.getBoolean());
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

		System.setProperty(LuaLibraryPackage.EXKEY_PATH, "./src/test/resources/assets/lua");

		Lua.importStandard(interp);
		interp.importLib(LuaLibrary.PACKAGE);
		interp.importLib(LuaLibrary.REFLECT);

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

		LuaObject obj = interp.execute();

		assertNotNull(obj);
		assertTrue(obj.getBoolean());
	}

	public void testCode() throws FileNotFoundException
	{
		// ALMOST verbatim lua 5.3.4 'code.lua' test suite
		final LuaInterpreter interp = Lua.reader(Assets.get("lua/code.lua"));

		Lua.importStandard(interp);

		LuaObject obj = interp.execute();

		assertNotNull(obj);
		assertTrue(obj.getBoolean());
	}

	public void testCalls() throws FileNotFoundException
	{
		// ALMOST verbatim lua 5.3.4 'calls.lua' test suite
		final LuaInterpreter interp = Lua.reader(Assets.get("lua/calls.lua"));

		Lua.importStandard(interp);

		LuaObject obj = interp.execute();

		assertNotNull(obj);
		assertTrue(obj.getBoolean());
	}

	public void testClosures() throws FileNotFoundException
	{
		// ALMOST verbatim lua 5.3.4 'closures.lua' test suite
		final LuaInterpreter interp = Lua.reader(Assets.get("lua/closures.lua"));

		Lua.importStandard(interp);

		LuaObject obj = interp.execute();

		assertNotNull(obj);
		assertTrue(obj.getBoolean());
	}

	public void testEnvironments() throws FileNotFoundException
	{
		final LuaInterpreter interp = Lua.reader(Assets.get("lua/test_env.lua"));

		Lua.importStandard(interp);

		LuaObject obj = interp.execute();

		assertNotNull(obj);
		assertTrue(obj.getBoolean());
	}

	public void testLargeRecords() throws FileNotFoundException
	{
		final LuaInterpreter interp = Lua.reader(Assets.get("lua/large_records.lua"));

		Lua.importStandard(interp);

		Object obj = interp.execute();

		assertEquals(Lua.newNumber(880), obj);
	}

	public void testLunity() throws FileNotFoundException
	{
		final LuaInterpreter interp = Lua.reader(Assets.get("lua/test_lunity.lua"));

		interp.setExtra(LuaLibraryPackage.EXKEY_PATH, "./src/test/resources/assets/lua/?.lua;./src/test/resources/assets/lua/?/init.lua");

		Lua.importStandard(interp);
		interp.importLib(LuaLibrary.PACKAGE);

		LuaObject obj = interp.execute();

		assertNotNull(obj);
		assertTrue(obj.getBoolean());
	}

	public void testFunctions() throws FileNotFoundException
	{
		final LuaInterpreter interp = Lua.reader(Assets.get("lua/test_functions.lua"));

		Environment globals = interp.getGlobals();
		Set<String> calls = new HashSet<>();
		String name = "my function";

		// Predicates
		globals.setVar("doublePredicate", Lua.newFuncFrom(name, (double d) -> {
			calls.add("doublePredicate");
			return false;
		}));
		globals.setVar("intPredicate", Lua.newFuncFrom(name, (int i) -> {
			calls.add("intPredicate");
			return true;
		}));
		globals.setVar("longPredicate", Lua.newFuncFrom(name, (long l) -> {
			calls.add("longPredicate");
			return false;
		}));
		globals.setVar("stringPredicate", Lua.newFuncFrom(name, (String s) -> {
			calls.add("stringPredicate");
			return true;
		}));

		// Consumers
		globals.setVar("doubleConsumer", Lua.newFuncFrom("doubleConsumer", (double d) -> { calls.add("doubleConsumer"); }));
		globals.setVar("intConsumer", Lua.newFuncFrom("intConsumer", (int i) -> { calls.add("intConsumer"); }));
		globals.setVar("longConsumer", Lua.newFuncFrom("longConsumer", (long l) -> { calls.add("longConsumer"); }));
		globals.setVar("stringConsumer", Lua.newFuncFrom("stringConsumer", (String s) -> { calls.add("stringConsumer"); }));

		// Suppliers

		// Operators

		// Functions

		Lua.importStandard(interp);

		LuaObject obj = interp.execute();

		assertNotNull(obj);
		assertTrue(obj.getBoolean());
	}

	@Override
	public void tearDown()
	{
		// TODO: create or delete
	}
}