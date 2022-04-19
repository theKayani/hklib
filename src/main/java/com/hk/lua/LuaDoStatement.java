package com.hk.lua;

class LuaDoStatement extends LuaStatement
{
	private final LuaBlock block;

	LuaDoStatement(int line, LuaBlock block)
	{
		super(line);
		this.block = block;
	}

	@Override
	LuaBlock getBlock()
	{
		return block;
	}

	@Override
	int code()
	{
		return T_DO;
	}
}