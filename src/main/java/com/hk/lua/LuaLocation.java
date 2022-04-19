package com.hk.lua;

abstract class LuaLocation extends Lua.LuaValue
{
	final int line;
	LuaLocation next;

	LuaLocation(int line)
	{
		this.line = line;
	}

	abstract void give(LuaInterpreter interp, LuaObject parent, LuaObject obj);

	LuaObject grab(LuaInterpreter interp, LuaObject parent)
	{
		return evaluate(interp);
	}

	@Override
	LuaObject evaluate(LuaInterpreter interp)
	{
		throw new UnsupportedOperationException();
	}

	boolean isCall()
	{
		return next != null && next.isCall();
	}
}