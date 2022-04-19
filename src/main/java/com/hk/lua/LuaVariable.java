package com.hk.lua;

import java.util.Stack;

class LuaVariable extends LuaLocation
{
	final String variable;
	private final String source;
	private final boolean local;

	LuaVariable(String source, int line, String variable, boolean local)
	{
		super(line);
		this.variable = variable;
		this.local = local;
		this.source = source;
	}

	void give(LuaInterpreter interp, LuaObject obj)
	{
		give(interp, null, obj);
	}

	void give(LuaInterpreter interp, LuaObject parent, LuaObject obj)
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
				next.give(interp, interp.env.getVar(variable), obj);
			}
			catch(LuaException e)
			{
				LuaException e2 = e;
				if(e2.primary)
				{
					Stack<LuaException> stacktrace = e2.stacktrace;
					e2 = new LuaException(source, line, e2.getLocalizedMessage() + " (" + (local ? "local" : "global") + " '" + variable + "')");
					e2.internal();
					e2.stacktrace = stacktrace;
				}
				throw e2;
			}
		}
	}

	@Override
	LuaObject evaluate(LuaInterpreter interp)
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
				return next.grab(interp, obj);
			}
			catch(LuaException e)
			{
				LuaException e2 = e;
				if(e2.primary)
				{
					Stack<LuaException> stacktrace = e2.stacktrace;
					e2 = new LuaException(source, line, e2.getLocalizedMessage() + " (" + (local ? "local" : "global") + " '" + variable + "')");
					e2.internal();
					e2.stacktrace = stacktrace;
				}
				throw e2;
			}
		}
	}
}