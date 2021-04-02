package com.hk.lua;

class LuaBody extends LuaBlock
{
	private final String func;
	private final String source;
	
	LuaBody(LuaInterpreter interp, LuaStatement[] sts, String func)
	{
		super(interp, sts);
		this.func = func;
		
		source = interp.currSource;
	}
	
	Object execute(Environment fenv, String[] argNames, LuaObject[] args)
	{
		Environment env = interp.env;
		interp.env = new Environment(interp, fenv, false);//func != null);
		if(argNames.length > 0 && argNames[argNames.length - 1] == null)
		{
			int i;
			for(i = 0; i < argNames.length - 1; i++)
				interp.env.setLocal(argNames[i], i < args.length ? args[i] : LuaNil.NIL);

			if(args.length >= argNames.length)
			{
				LuaObject[] tmp = new LuaObject[args.length - argNames.length + 1];
				for(int j = 0; j < tmp.length; j++)
					tmp[j] = args[i + j];
	
				interp.env.varargs = new LuaArgs(tmp);
			}
			else
				interp.env.varargs = LuaNil.NIL;
		}
		else
		{
			for(int i = 0; i < Math.min(args.length, argNames.length); i++)
				interp.env.setLocal(argNames[i], args[i]);
		}

		Object res = run();
		interp.env = env;
		return res;
	}

	protected LuaException exception(LuaStatement st, LuaException e)
	{
		if(func != null)
		{
			new LuaException(source, st.line, "in function '" + func + "'", e);
			e.internal = false;
			return e;
		}
		else
			return super.exception(st, e);
	}
}
