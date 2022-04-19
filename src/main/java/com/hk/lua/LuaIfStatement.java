package com.hk.lua;

class LuaIfStatement extends LuaStatement
{
	private final LuaExpression exp;
	private final LuaBlock block;
	boolean hasNext = false;
	LuaIfStatement elseStatement;

	LuaIfStatement(int line, LuaExpression exp, LuaBlock block)
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

	boolean isElseStatement()
	{
		return exp == null;
	}

	boolean hasNextElseStatement()
	{
		return hasNext;
	}

	LuaIfStatement nextElseStatement()
	{
		return elseStatement;
	}

	@Override
	int code()
	{
		return T_IF;
	}
}