package com.hk.lua;

class LuaFunctionExpression extends LuaExpression
{
	private final String[] args;
	private final LuaBody body;

	LuaFunctionExpression(String source, String[] args, LuaBody body)
	{
		super(source);
		this.args = args;
		this.body = body;
	}

	@Override
	LuaFunctionExpression collect(Object[] array)
	{
		throw new UnsupportedOperationException();
	}

	LuaObject evaluate(LuaInterpreter interp)
	{
		return new LuaLuaFunction(interp.env, args, body);
	}

	boolean isCall()
	{
		return false;
	}
}