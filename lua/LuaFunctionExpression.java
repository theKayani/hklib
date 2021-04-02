package com.hk.lua;

public class LuaFunctionExpression extends LuaExpression
{
	private final String[] args;
	private final LuaBody body;
	
	LuaFunctionExpression(LuaInterpreter interp, String[] args, LuaBody body)
	{
		super(interp);
		this.args = args;
		this.body = body;
	}

	@Override
	LuaFunctionExpression collect(Object[] array)
	{
		throw new UnsupportedOperationException();
	}
	
	LuaObject evaluate()
	{
		return new LuaLuaFunction(interp.env, args, body);
	}
	
	boolean isCall()
	{
		return false;
	}
}
