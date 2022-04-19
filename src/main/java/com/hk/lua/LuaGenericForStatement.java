package com.hk.lua;

class LuaGenericForStatement extends LuaForStatement
{
	private final String[] vars;
	private final LuaExpressions exps;

	LuaGenericForStatement(int line, String[] vars, LuaExpressions exps)
	{
		super(line);
		this.vars = vars;
		this.exps = exps;
	}

	@Override
	String[] getVars()
	{
		return vars;
	}

	@Override
	LuaExpressions getExpressions()
	{
		return exps;
	}

	@Override
	int code()
	{
		return T_GENERIC_FOR;
	}
}