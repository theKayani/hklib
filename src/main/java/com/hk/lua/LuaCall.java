package com.hk.lua;

class LuaCall extends LuaLocation
{
	private final LuaExpressions args;

	LuaCall(int line, LuaExpressions args)
	{
		super(line);
		this.args = args;
	}

	@Override
	void give(LuaInterpreter interp, LuaObject parent, LuaObject obj)
	{
		if(next == null)
			throw new Error();

		next.give(interp, parent.doCall(interp, args == null ? new LuaObject[0] : args.collect(interp)).evaluate(interp), obj);
	}

	LuaObject grab(LuaInterpreter interp, LuaObject parent)
	{
		LuaObject res = parent.doCall(interp, args == null ? new LuaObject[0] : args.collect(interp));
		if(next == null)
			return res;
		else
			return next.grab(interp, res.evaluate(interp));
	}

	boolean isCall()
	{
		return next == null || next.isCall();
	}
}