package com.hk.lua;

import java.util.Stack;

class LuaField extends LuaLocation
{
	private final Lua.LuaValue value;
	private final String source;
	String name;
	
	LuaField(LuaInterpreter interp, int line, Lua.LuaValue value)
	{
		super(interp, line);
		this.value = value;
		name = value instanceof LuaString ? ((LuaString) value).getString() : null;
		
		source = interp.currSource;
	}

	@Override
	void give(LuaObject parent, LuaObject obj)
	{
		LuaObject val = value.evaluate();
		if(next == null)
		{
			parent.doNewIndex(val, obj);
		}
		else
		{
			try
			{
				next.give(parent.doIndex(val), obj);
			}
			catch(LuaException e)
			{
				if(e.primary)
				{
					Stack<LuaException> stacktrace = e.stacktrace;
					e = new LuaException(source, line, e.getLocalizedMessage() + " (field '" + val + "')");
					e.stacktrace = stacktrace;
				}
				throw e.internal();
			}
		}
	}

	@Override
	LuaObject grab(LuaObject parent)
	{
		LuaObject val = value.evaluate();
		if(next == null)
		{
			return parent.doIndex(val);
		}
		else
		{
			try
			{
				return next.grab(parent.doIndex(val));
			}
			catch(LuaException e)
			{
				if(e.primary)
				{
					Stack<LuaException> stacktrace = e.stacktrace;
					e = new LuaException(source, line, e.getLocalizedMessage() + " (field '" + val + "')");
					e.stacktrace = stacktrace;
				}
				throw e.internal();
			}
		}
	}
}
