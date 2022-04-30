package com.hk.lua;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hk.lua.Lua.LuaParameters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class LuaJavaFunction extends LuaFunction
{
	protected final Method method;
	protected final Object obj;

	LuaJavaFunction(Method method, Object obj)
	{
		this.method = method;
		this.obj = obj;
	}

	/** {@inheritDoc}
	 * @param interp
	 * @return*/
	@Override
	public @NotNull String getString(@Nullable LuaInterpreter interp)
	{
		return "function:" + method.getName() + ": 0x" + Integer.toHexString(System.identityHashCode(method));
	}

	@Override
	LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
	{
		try
		{
			return Lua.newLuaObject(method.invoke(obj, new Object[] { args }));
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}

	static class LuaJavaArgFunction extends LuaJavaFunction
	{
		private final LuaType[] argTypes;

		LuaJavaArgFunction(Method method, Object obj)
		{
			super(method, obj);
			argTypes = method.getAnnotation(LuaParameters.class).value();
		}

		@Override
		LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
		{
			Object[] newArgs = new Object[argTypes.length];
			for(int i = 0; i < argTypes.length; i++)
			{
				if(i == args.length || !argTypes[i].applies(args[i].type()))
					throw Lua.badArgument(i, method.getName(), argTypes[i].luaName + " expected");

				newArgs[i] = args[i];
			}

			try
			{
				return Lua.newLuaObject(method.invoke(obj, newArgs));
			}
			catch (IllegalAccessException | IllegalArgumentException e)
			{
				throw new RuntimeException(e);
			}
			catch (InvocationTargetException e)
			{
				throw new RuntimeException(e.getCause());
			}
		}
	}
}