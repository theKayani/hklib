package com.hk.lua;

class LuaReturnStatement extends LuaStatement
{
	private final LuaExpressions exps;

	LuaReturnStatement(int line, LuaExpressions exps)
	{
		super(line);
		this.exps = exps;
	}

	@Override
	LuaExpressions getExpressions()
	{
		return exps;
	}

	@Override
	int code()
	{
		return T_RETURN;
	}
}