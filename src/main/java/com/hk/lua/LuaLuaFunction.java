package com.hk.lua;

class LuaLuaFunction extends LuaFunction
{
	private final Environment fenv;
	private final String[] args;
	private final LuaBody body;
	
	LuaLuaFunction(Environment fenv, String[] args, LuaBody body)
	{
		this.fenv = fenv;
		this.args = args;
		this.body = body;
	}
	
	@Override
	public String getString(LuaInterpreter interp)
	{
		return "function: 0x" + Integer.toHexString(hashCode()) + Integer.toHexString(System.identityHashCode(body));
	}

	@Override
	LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
	{
		Object res = body.execute(interp, fenv, this.args, args);
		if(res instanceof LuaObject[])
			return new LuaArgs((LuaObject[]) res);
		else
			return res == null ? LuaNil.NIL : (LuaObject) res;
	}
}
