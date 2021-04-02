package com.hk.lua;

import java.util.Stack;

class LuaVariable extends LuaLocation
{
	final String variable;
	private final String source;
	private final boolean local;
	
	LuaVariable(LuaInterpreter interp, int line, String variable, boolean local)
	{
		super(interp, line);
		this.variable = variable;
		this.local = local;
		
		source = interp.currSource;
	}

	void give(LuaObject obj)
	{
		give(null, obj);
	}
	
	void give(LuaObject parent, LuaObject obj)
	{
		if(next == null)
		{
			if(local)
				interp.env.setLocal(variable, obj);
			else
				interp.env.setVar(variable, obj);
		}
		else
		{
			boolean local = interp.env.isLocal(variable);
			try
			{
				next.give(interp.env.getVar(variable), obj);
			}
			catch(LuaException e)
			{
				if(e.primary)
				{
					Stack<LuaException> stacktrace = e.stacktrace;
					e = new LuaException(source, line, e.getLocalizedMessage() + " (" + (local ? "local" : "global") + " '" + variable + "')");
					e.internal();
					e.stacktrace = stacktrace;
				}
				throw e;
			}
		}
	}
	
	@Override
	LuaObject evaluate()
	{
		LuaObject obj = interp.env.getVar(variable);
		if(next == null)
		{
			return obj;
		}
		else
		{
			boolean local = interp.env.isLocal(variable);
			try
			{
				return next.grab(obj);
			}
			catch(LuaException e)
			{
				if(e.primary)
				{
					Stack<LuaException> stacktrace = e.stacktrace;
					e = new LuaException(source, line, e.getLocalizedMessage() + " (" + (local ? "local" : "global") + " '" + variable + "')");
					e.internal();
					e.stacktrace = stacktrace;
				}
				throw e;
			}
		}
	}
}
