package com.hk.lua;

abstract class LuaForStatement extends LuaStatement
{
	LuaBody body;

	LuaForStatement(int line)
	{
		super(line);
	}

	LuaBody getBody()
	{
		return body;
	}

	@Override
	abstract int code();
}