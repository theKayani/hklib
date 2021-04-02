package com.hk.lua;

class LuaCall extends LuaLocation
{
	private final LuaExpressions args;
	
	LuaCall(LuaInterpreter interp, int line, LuaExpressions args)
	{
		super(interp, line);
		this.args = args;
	}
	
	@Override
	void give(LuaObject parent, LuaObject obj)
	{
		if(next == null)
			throw new Error();
		
		next.give(parent.doCall(args == null ? new LuaObject[0] : args.collect()).evaluate(), obj);
	}
	
	LuaObject grab(LuaObject parent)
	{
		LuaObject res = parent.doCall(args == null ? new LuaObject[0] : args.collect());
		if(next == null)
			return res;
		else
			return next.grab(res.evaluate());
	}

	boolean isCall()
	{
		return next == null || next.isCall();
	}
}
