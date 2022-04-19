package com.hk.lua;

class LuaSelfCall extends LuaLocation
{
	private final String name;
	private final LuaExpressions args;

	LuaSelfCall(int line, String name, LuaExpressions args)
	{
		super(line);
		this.name = name;
		this.args = args;
	}

	@Override
	void give(LuaInterpreter interp, LuaObject parent, LuaObject obj)
	{
		if(next == null)
			throw new Error();

		LuaObject field = parent.doIndex(interp, new LuaString(name));

		LuaObject[] arr;
		if(args == null)
			arr = new LuaObject[] { parent };
		else
			arr = args.collect(interp, parent);

		next.give(interp, field.doCall(interp, arr).evaluate(interp), obj);
	}

	LuaObject grab(LuaInterpreter interp, LuaObject parent)
	{
		LuaObject field = parent.doIndex(interp, new LuaString(name));

		LuaObject[] arr;
		if(args == null)
			arr = new LuaObject[] { parent };
		else
			arr = args.collect(interp, parent);

		LuaObject res = field.doCall(interp, arr);
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