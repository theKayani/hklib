package com.hk.lua;

class LuaRepeatStatement extends LuaStatement
{
	private final LuaBlock block;
	private final LuaExpression exp;

	LuaRepeatStatement(int line, LuaBlock block, LuaExpression exp)
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
		return T_REPEAT;
	}
}