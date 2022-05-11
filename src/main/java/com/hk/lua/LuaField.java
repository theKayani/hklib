package com.hk.lua;

import java.util.Stack;

class LuaField extends LuaLocation
{
	private final Lua.LuaValue value;
	private final String source;
	final String name;

	LuaField(String source, int line, Lua.LuaValue value)
	{
		super(line);
		this.value = value;
		name = value instanceof LuaString ? ((LuaString) value).getString() : null;
		this.source = source;
	}

	@Override
	void give(LuaInterpreter interp, LuaObject parent, LuaObject obj)
	{
		LuaObject val = value.evaluate(interp);
		if(next == null)
		{
			parent.doNewIndex(interp, val, obj);
		}
		else
		{
			try
			{
				next.give(interp, parent.doIndex(interp, val), obj);
			}
			catch(LuaException e)
			{
				LuaException e2 = e;
				if(e2.primary)
				{
					Stack<LuaException> stacktrace = e2.stacktrace;
					e2 = new LuaException(source, line, e2.getLocalizedMessage() + " (field '" + val + "')");
					e2.stacktrace = stacktrace;
				}
				throw e2.internal();
			}
		}
	}

	@Override
	LuaObject grab(LuaInterpreter interp, LuaObject parent)
	{
		LuaObject val = value.evaluate(interp);
		if(next == null)
		{
			return parent.doIndex(interp, val);
		}
		else
		{
			try
			{
				return next.grab(interp, parent.doIndex(interp, val));
			}
			catch(LuaException e)
			{
				LuaException e2 = e;
				if(e2.primary)
				{
					Stack<LuaException> stacktrace = e2.stacktrace;
					e2 = new LuaException(source, line, e2.getLocalizedMessage() + " (field '" + val + "')");
					e2.stacktrace = stacktrace;
				}
				throw e2.internal();
			}
		}
	}
}