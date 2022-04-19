package com.hk.lua;

abstract class LuaStatement implements Tokens
{
	final int line;

	LuaStatement(int line)
	{
		this.line = line;
	}

	LuaBody getBody()
	{
		throw new UnsupportedOperationException();
	}

	LuaBlock getBlock()
	{
		throw new UnsupportedOperationException();
	}

	LuaLocation[] getLocations()
	{
		throw new UnsupportedOperationException();
	}

	String[] getVars()
	{
		throw new UnsupportedOperationException();
	}

	LuaExpressions getExpressions()
	{
		throw new UnsupportedOperationException();
	}

	LuaExpression getExpression()
	{
		throw new UnsupportedOperationException();
	}

	LuaLocation getLocation()
	{
		throw new UnsupportedOperationException();
	}

	String getIndex()
	{
		throw new UnsupportedOperationException();
	}

	Lua.LuaValue getValue1()
	{
		throw new UnsupportedOperationException();
	}

	Lua.LuaValue getValue2()
	{
		throw new UnsupportedOperationException();
	}

	Lua.LuaValue getValue3()
	{
		throw new UnsupportedOperationException();
	}

	boolean isLocal()
	{
		throw new UnsupportedOperationException();
	}

	boolean isElseStatement()
	{
		throw new UnsupportedOperationException();
	}

	boolean hasNextElseStatement()
	{
		throw new UnsupportedOperationException();
	}

	LuaIfStatement nextElseStatement()
	{
		throw new UnsupportedOperationException();
	}

	abstract int code();
}