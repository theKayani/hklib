package com.hk.lua;

class LuaAssignStatement extends LuaStatement
{
	private final LuaLocation[] locations;
	private final LuaExpressions exps;

	LuaAssignStatement(int line, LuaLocation[] locations, LuaExpressions exps)
	{
		super(line);
		this.locations = locations;
		this.exps = exps;
	}

	LuaLocation[] getLocations()
	{
		return locations;
	}

	LuaExpressions getExpressions()
	{
		return exps;
	}

	@Override
	int code()
	{
		return T_IDENTIFIER;
	}
}