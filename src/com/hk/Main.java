package com.hk;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.hk.lua.Lua;
import com.hk.lua.LuaFactory;
import com.hk.lua.LuaInterpreter;
import com.hk.lua.LuaLibrary;

public class Main
{
//	public static void main(String[] args) throws Exception
//	{
//		long start;
//		final LuaInterpreter interp = Lua.reader(new File("src/com/hk/lua/main.lua"));
//
//		start = System.nanoTime();
//		interp.compile();
//		double compileTime = (System.nanoTime() - start) / 1E6D;
//
//		interp.importLib(LuaLibrary.BASIC);
//		interp.importLib(LuaLibrary.COROUTINE);
////		interp.importLib(LuaLibrary.PACKAGE);
//		interp.importLib(LuaLibrary.STRING);
//		interp.importLib(LuaLibrary.TABLE);
//		interp.importLib(LuaLibrary.MATH);
//		interp.importLib(LuaLibrary.IO);
//		interp.importLib(LuaLibrary.OS);
////		interp.importLib(LuaLibrary.DEBUG);
//
//		interp.importLib(LuaLibrary.JSON);
//		interp.importLib(LuaLibrary.HASH);
//
//		Object obj;
//		try
//		{
//			start = System.nanoTime();
//			obj = interp.execute();
//			double executeTime = (System.nanoTime() - start) / 1E6D;
//			System.out.println("(time) " + compileTime + ", " + executeTime);
//		}
//		catch(LuaException e)
//		{
//			System.out.println("(time) " + compileTime);
//			throw e;
//		}
//		if(obj instanceof Object[])
//			System.out.println(Arrays.deepToString((Object[]) obj));
//		else
//			System.out.println(obj);
//	}
	
	public static void main(String[] args) throws IOException
	{		
		LuaInterpreter interp;
		LuaFactory factory = Lua.factory(new File("src/com/hk/lua/main.lua"));

		factory.compile();
		factory.addLibrary(LuaLibrary.BASIC);
		factory.addLibrary(LuaLibrary.COROUTINE);
//		factory.addLibrary(LuaLibrary.PACKAGE);
		factory.addLibrary(LuaLibrary.STRING);
		factory.addLibrary(LuaLibrary.TABLE);
		factory.addLibrary(LuaLibrary.MATH);
		factory.addLibrary(LuaLibrary.IO);
		factory.addLibrary(LuaLibrary.OS);
//		factory.addLibrary(LuaLibrary.DEBUG);

		factory.addLibrary(LuaLibrary.JSON);
		factory.addLibrary(LuaLibrary.HASH);

		interp = factory.build();
		interp.getGlobals().setVar("arr", Lua.newTable(Arrays.asList(Lua.newNumber(3), Lua.newNumber(2), Lua.newNumber(1))));
		time(interp);

//		interp = factory.build();
//		interp.getGlobals().setVar("arr", Lua.newTable(Arrays.asList(Lua.newNumber(4), Lua.newNumber(32), Lua.newNumber(-1), Lua.newNumber(3), Lua.newNumber(2), Lua.newNumber(1))));
//		time(interp);
//		
//		time(factory.build());
	}
	
	private static void time(LuaInterpreter interp)
	{
		long start = System.nanoTime();
		Object obj = interp.execute();
		double executeTime = (System.nanoTime() - start) / 1E6D;

		if(obj instanceof Object[])
			System.out.println(Arrays.deepToString((Object[]) obj));
		else
			System.out.println(obj);
		System.out.println(executeTime + "ms");
	}
}