package com.hk.lua;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hk.lua.Lua.LuaParameters;

class LuaJavaFunction extends LuaFunction
{
	protected final Method method;
	protected final Object obj;
	
	LuaJavaFunction(Method method, Object obj)
	{
		this.method = method;
		this.obj = obj;
	}
	
	@Override
	public String getString(LuaInterpreter interp)
	{
		return "function:" + method.getName() + ": 0x" + Integer.toHexString(System.identityHashCode(method));
	}

	@Override
	LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
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
		LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
		{
			Object[] newArgs = new Object[argTypes.length];
			for(int i = 0; i < argTypes.length; i++)
			{
				if(i == args.length || !argTypes[i].applies(args[i].type()))
					throw new LuaException("bad argument #" + (i + 1) + " to '" + method.getName() + "' (" + argTypes[i].luaName + " expected)");

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
