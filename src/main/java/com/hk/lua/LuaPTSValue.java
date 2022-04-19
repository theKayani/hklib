package com.hk.lua;

class LuaPTSValue extends LuaLocation
{
	private final LuaExpression exp;

	LuaPTSValue(int line, LuaExpression exp)
	{
		super(line);
		this.exp = exp;
	}

	@Override
	void give(LuaInterpreter interp, LuaObject parent, LuaObject obj)
	{
		if(next == null)
			throw new Error();

		next.give(interp, exp.evaluate(interp), obj);
	}

	LuaObject evaluate(LuaInterpreter interp)
	{
		LuaObject s = exp.evaluate(interp);
		if(next == null)
			return s.evaluate(interp);
		else
			return next.grab(interp, s);
	}

	boolean isCall()
	{
		return next != null && next.isCall();
	}
}