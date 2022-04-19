package com.hk.lua;

class LuaBody extends LuaBlock
{
	private final String func;

	LuaBody(LuaStatement[] sts, String source, String func)
	{
		super(sts, source);
		this.func = func;
	}

	Object execute(LuaInterpreter interp, Environment fenv, String[] argNames, LuaObject[] args)
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
				System.arraycopy(args, i, tmp, 0, tmp.length);

				interp.env.varargs = new LuaArgs(tmp);
			}
			else
				interp.env.varargs = LuaNil.NIL;
		}
		else
		{
			for(int i = 0; i < argNames.length; i++)
				interp.env.setLocal(argNames[i], i < args.length ? args[i] : LuaNil.NIL);
		}

		Object res = run(interp);
		interp.env = env;
		return res;
	}

	/** {@inheritDoc} */
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