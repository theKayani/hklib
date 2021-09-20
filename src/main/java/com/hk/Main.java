package com.hk;

import java.util.List;

import com.hk.abs.Lockable;
import com.hk.collections.lists.LockableList;
import com.hk.lua.Lua;
import com.hk.lua.LuaInterpreter;
import com.hk.lua.LuaLibrary;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		LuaInterpreter interp = Lua.reader("print [[] }]]");

		interp.importLib(LuaLibrary.BASIC);

		interp.execute();
	}
}