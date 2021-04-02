package com.hk.lua;

abstract class LuaLocation extends Lua.LuaValue
{
	final LuaInterpreter interp;
	final int line;
	LuaLocation next;
	
	LuaLocation(LuaInterpreter interp, int line)
	{
		this.interp = interp;
		this.line = line;
	}
	
	abstract void give(LuaObject parent, LuaObject obj);

	LuaObject grab(LuaObject parent)
	{
		return evaluate();
	}

	@Override
	LuaObject evaluate()
	{
		throw new UnsupportedOperationException();
	}

	boolean isCall()
	{
		return next == null ? false : next.isCall();
	}
}