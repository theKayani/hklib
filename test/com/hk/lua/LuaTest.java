package com.hk.lua;

import junit.framework.TestCase;

public class LuaTest extends TestCase
{
	@Override
	public void setUp()
	{

//		public static void main(String[] args) throws Exception
//		{
//			long start;
//			final LuaInterpreter interp = Lua.reader(new File("src/com/hk/lua/main.lua"));
	//
//			start = System.nanoTime();
//			interp.compile();
//			double compileTime = (System.nanoTime() - start) / 1E6D;
	//
//			interp.importLib(LuaLibrary.BASIC);
//			interp.importLib(LuaLibrary.COROUTINE);
////			interp.importLib(LuaLibrary.PACKAGE);
//			interp.importLib(LuaLibrary.STRING);
//			interp.importLib(LuaLibrary.TABLE);
//			interp.importLib(LuaLibrary.MATH);
//			interp.importLib(LuaLibrary.IO);
//			interp.importLib(LuaLibrary.OS);
////			interp.importLib(LuaLibrary.DEBUG);
	//
//			interp.importLib(LuaLibrary.JSON);
//			interp.importLib(LuaLibrary.HASH);
	//
//			Object obj;
//			try
//			{
//				start = System.nanoTime();
//				obj = interp.execute();
//				double executeTime = (System.nanoTime() - start) / 1E6D;
//				System.out.println("(time) " + compileTime + ", " + executeTime);
//			}
//			catch(LuaException e)
//			{
//				System.out.println("(time) " + compileTime);
//				throw e;
//			}
//			if(obj instanceof Object[])
//				System.out.println(Arrays.deepToString((Object[]) obj));
//			else
//				System.out.println(obj);
//		}
		
//		public static void main(String[] args) throws IOException
//		{		
//			LuaInterpreter interp;
//			LuaFactory factory = Lua.factory(new File("src/com/hk/lua/main.lua"));
	//
//			factory.compile();
//			factory.addLibrary(LuaLibrary.BASIC);
//			factory.addLibrary(LuaLibrary.COROUTINE);
////			factory.addLibrary(LuaLibrary.PACKAGE);
//			factory.addLibrary(LuaLibrary.STRING);
//			factory.addLibrary(LuaLibrary.TABLE);
//			factory.addLibrary(LuaLibrary.MATH);
//			factory.addLibrary(LuaLibrary.IO);
//			factory.addLibrary(LuaLibrary.OS);
////			factory.addLibrary(LuaLibrary.DEBUG);
	//
//			factory.addLibrary(LuaLibrary.JSON);
//			factory.addLibrary(LuaLibrary.HASH);
	//
//			interp = factory.build();
//			interp.getGlobals().setVar("arr", Lua.newTable(Arrays.asList(Lua.newNumber(3), Lua.newNumber(2), Lua.newNumber(1))));
//			time(interp);
	//
////			interp = factory.build();
////			interp.getGlobals().setVar("arr", Lua.newTable(Arrays.asList(Lua.newNumber(4), Lua.newNumber(32), Lua.newNumber(-1), Lua.newNumber(3), Lua.newNumber(2), Lua.newNumber(1))));
////			time(interp);
////			
////			time(factory.build());
//		}
	//	
//		private static void time(LuaInterpreter interp)
//		{
//			long start = System.nanoTime();
//			Object obj = interp.execute();
//			double executeTime = (System.nanoTime() - start) / 1E6D;
	//
//			if(obj instanceof Object[])
//				System.out.println(Arrays.deepToString((Object[]) obj));
//			else
//				System.out.println(obj);
//			System.out.println(executeTime + "ms");
//		}
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