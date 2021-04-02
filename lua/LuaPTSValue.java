package com.hk.lua;

class LuaPTSValue extends LuaLocation
{
	private final LuaExpression exp;
	
	LuaPTSValue(LuaInterpreter interp, int line, LuaExpression exp)
	{
		super(interp, line);
		this.exp = exp;
	}
	
	@Override
	void give(LuaObject parent, LuaObject obj)
	{
		if(next == null)
			throw new Error();
		
		next.give(exp.evaluate(), obj);
	}
	
	LuaObject evaluate()
	{
		LuaObject s = exp.evaluate();
		if(next == null)
			return s;
		else
			return next.grab(s);
	}

	boolean isCall()
	{
		return next == null || next.isCall();
	}
}
