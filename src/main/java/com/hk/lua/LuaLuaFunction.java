package com.hk.lua;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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

	/** {@inheritDoc}
	 * @param interp
	 * @return*/
	@Override
	public @NotNull String getString(@Nullable LuaInterpreter interp)
	{
		return "function: 0x" + Integer.toHexString(hashCode()) + Integer.toHexString(System.identityHashCode(body));
	}

	@Override
	LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
	{
		Objects.requireNonNull(interp);
		Object res = body.execute(interp, fenv, this.args, args);
		if(res instanceof LuaObject[])
			return new LuaArgs((LuaObject[]) res);
		else
			return res == null ? LuaNil.NIL : (LuaObject) res;
	}
}