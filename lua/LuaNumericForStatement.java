package com.hk.lua;

class LuaNumericForStatement extends LuaForStatement
{
	private final String index;
	private final Lua.LuaValue exp1, exp2, exp3;

	LuaNumericForStatement(int line, String index, Lua.LuaValue exp1, Lua.LuaValue exp2, Lua.LuaValue exp3)
	{
		super(line);
		this.index = index;
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
	}

	@Override
	String getIndex()
	{
		return index;
	}

	@Override
	Lua.LuaValue getValue1()
	{
		return exp1;
	}

	@Override
	Lua.LuaValue getValue2()
	{
		return exp2;
	}

	@Override
	Lua.LuaValue getValue3()
	{
		return exp3;
	}

	@Override
	int code()
	{
		return T_NUMERIC_FOR;
	}
}
