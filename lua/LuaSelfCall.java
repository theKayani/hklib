package com.hk.lua;

class LuaSelfCall extends LuaLocation
{
	private final String name;
	private final LuaExpressions args;
	
	LuaSelfCall(LuaInterpreter interp, int line, String name, LuaExpressions args)
	{
		super(interp, line);
		this.name = name;
		this.args = args;
	}
	
	@Override
	void give(LuaObject parent, LuaObject obj)
	{
		if(next == null)
			throw new Error();
		
		LuaObject field = parent.doIndex(new LuaString(name));
		
		LuaObject[] arr = null;
		if(args == null)
			arr = new LuaObject[] { parent };
		else
			arr = args.collect(parent);
		
		next.give(field.doCall(arr).evaluate(), obj);
	}
	
	LuaObject grab(LuaObject parent)
	{
		LuaObject field = parent.doIndex(new LuaString(name));

		LuaObject[] arr = null;
		if(args == null)
			arr = new LuaObject[] { parent };
		else
			arr = args.collect(parent);
		
		LuaObject res = field.doCall(arr);
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
