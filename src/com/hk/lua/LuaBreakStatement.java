package com.hk.lua;

class LuaBreakStatement extends LuaStatement
{
	LuaBreakStatement(int line)
	{
		super(line);
	}

	@Override
	int code()
	{
		return T_BREAK;
	}
}
