package com.hk.lua;

public class LuaChunk extends LuaBlock
{
	private final Environment myEnv;
	private final boolean secondary;

	LuaChunk(LuaInterpreter interp, LuaStatement[] luaStatements, Environment myEnv, boolean secondary)
	{
		super(interp, luaStatements);
		this.myEnv = myEnv;
		this.secondary = secondary;
	}

	Object execute(Object... args)
	{
		LuaObject varargs;
		if(args == null || args.length == 0)
			varargs = LuaNil.NIL;
		else if(args instanceof LuaObject[])
			varargs = new LuaArgs((LuaObject[]) args);
		else
		{
			LuaObject[] objs = new LuaObject[args.length];
			for(int i = 0; i < args.length; i++)
				objs[i] = Lua.newLuaObject(args[i]);
			
			varargs = new LuaArgs(objs);
		}
		
		myEnv.varargs = varargs;
		Environment env = interp.env;
		interp.env = myEnv;
		Object res;
		try
		{
			res = run();
		}
		catch(LuaException.LuaExitException ex)
		{
			if(secondary)
				throw ex;
			else
				return new LuaObject[] { LuaInteger.valueOf(ex.code) };			
		}
		interp.env = env;
		return res;
	}
	
	Object execute()
	{
		return execute((Object[]) null);
	}

//	protected LuaException exception(LuaStatement st, LuaException e)
//	{
//		new LuaException(interp.getSource(), e.internal ? e.lineNumber : st.line, "in main chunk", e);
//		return e;
//	}
}
