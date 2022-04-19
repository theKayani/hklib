package com.hk.lua;

class LuaWhileStatement extends LuaStatement
{
	private final LuaExpression exp;
	private final LuaBlock block;

	LuaWhileStatement(int line, LuaExpression exp, LuaBlock block)
	{
		super(line);
		this.exp = exp;
		this.block = block;
	}

	@Override
	LuaBlock getBlock()
	{
		return block;
	}

	@Override
	LuaExpression getExpression()
	{
		return exp;
	}

	@Override
	int code()
	{
		return T_WHILE;
	}
}