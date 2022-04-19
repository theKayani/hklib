package com.hk.lua;

class LuaCallStatement extends LuaStatement
{
	private final LuaLocation location;

	LuaCallStatement(int line, LuaLocation location)
	{
		super(line);
		this.location = location;
	}

	LuaLocation getLocation()
	{
		return location;
	}

	@Override
	int code()
	{
		return T_FUNCTION;
	}
}