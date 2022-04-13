package com.hk.lua;

import com.hk.func.BiConsumer;
import com.hk.lua.Lua.LuaMethod;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * <p>LuaLibraryReflect class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryReflect implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	_class() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);

			String key = EXKEY_CLASS_PREFIX + args[0].getString();
			if(interp.hasExtra(key))
				return interp.getExtraLua(key);

			try
			{
				return getJavaClass(interp, args[0]);
			}
			catch (ClassNotFoundException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString("class not found: " + e.getLocalizedMessage()));
			}
		}

		private LuaObject getJavaClass(LuaInterpreter interp, LuaObject arg) throws ClassNotFoundException
		{
			String key = EXKEY_CLASS_PREFIX + arg.getString();
			Class<?> cls = Class.forName(arg.getString(), false, getClass().getClassLoader());
			LuaJavaClass luaCls = new LuaJavaClass(cls);
			interp.setExtra(key, luaCls);
			return luaCls;
		}

		@Override
		public String toString()
		{
			return "class";
		}
	};
	
	/** {@inheritDoc} */
	@Override
	public LuaObject call(LuaInterpreter interp, LuaObject[] args)
	{
		throw new Error();
	}

	/** {@inheritDoc} */
	@Override
	public void accept(Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), Lua.newFunc(this));
	}

	private static Object[] attemptConvert(String method, Class<?>[] cls, LuaObject[] args, boolean strict)
	{
		if(cls.length == args.length)
		{
			Object[] result = new Object[cls.length];
			for (int i = 0; i < cls.length; i++)
			{
				try
				{
					result[i] = attempt(method, i, cls[i], args[i]);
				}
				catch (IllegalArgumentException e)
				{
					if(strict)
						throw Lua.badArgument(i, method, "expected '" + cls[i].getName() + "'");

					result = null;
					break;
				}
			}

			if(result != null)
				return result;
		}
		if(strict)
			throw new LuaException("cannot pass args: " + Arrays.toString(args));
		else
			return null;
	}

	private static Object attempt(String method, int param, Class<?> cls, LuaObject arg)
	{
		String e = "got " + arg.name();
		if(!cls.isPrimitive() && arg.isNil())
		{
			return null;
		}
		else if(cls.equals(byte.class) || cls.equals(Byte.class))
		{
			if(arg.isNumber())
				return (byte) arg.getLong();
			else
				throw Lua.badArgument(param, method, "expected 'byte'", e);
		}
		else if(cls.equals(short.class) || cls.equals(Short.class))
		{
			if(arg.isNumber())
				return (short) arg.getLong();
			else
				throw Lua.badArgument(param, method, "expected 'short'", e);
		}
		else if(cls.equals(int.class) || cls.equals(Integer.class))
		{
			if(arg.isNumber())
				return arg.getInt();
			else
				throw Lua.badArgument(param, method, "expected 'int'", e);
		}
		else if(cls.equals(long.class) || cls.equals(Long.class))
		{
			if(arg.isNumber())
				return arg.getLong();
			else
				throw Lua.badArgument(param, method, "expected 'long'", e);
		}
		else if(cls.equals(float.class) || cls.equals(Float.class))
		{
			if(arg.isNumber())
				return arg.getFlt();
			else
				throw Lua.badArgument(param, method, "expected 'float'", e);
		}
		else if(cls.equals(double.class) || cls.equals(Double.class))
		{
			if(arg.isNumber())
				return arg.getDouble();
			else
				throw Lua.badArgument(param, method, "expected 'double'", e);
		}
		else if(cls.equals(char.class) || cls.equals(Character.class))
		{
			if(arg.isString() && arg.getString().length() != 1)
					return arg.getString().charAt(0);
			else
				throw Lua.badArgument(param, method, "expected 'char'", e);
		}
		else if(cls.equals(String.class) || cls.equals(CharSequence.class))
		{
			if(arg.isString())
				return arg.getString();
			else
				throw Lua.badArgument(param, method, "expected 'string'", e);
		}
		else if(cls.equals(boolean.class) || cls.equals(Boolean.class))
			return arg.getBoolean();
		else if(arg.getUserdata() != null && cls.isAssignableFrom(arg.getUserdata().getClass()))
			return arg.getUserdata();

		throw new IllegalArgumentException();
	}

	private static class LuaJava extends LuaUserdata
	{
		protected Class<?> cls;
		protected Object obj;

		@Override
		public LuaObject rawGet(LuaObject key)
		{
			if(key.isString())
			{
				try
				{
					return Lua.newLuaObject(cls.getField(key.getString()).get(obj));
				}
				catch (NoSuchFieldException | IllegalAccessException e)
				{
					throw new LuaException(e.getLocalizedMessage());
				}
			}
			else
				return super.rawGet(key);
		}

		@Override
		public void rawSet(LuaObject key, LuaObject value)
		{
			if(key.isString())
			{
				try
				{
					Field field = cls.getField(key.getString());

					field.set(obj, attempt("rawset", 2, field.getType(), value));
				}
				catch (NoSuchFieldException | IllegalAccessException e)
				{
					throw new LuaException(e.getLocalizedMessage());
				}
			}
			else
				super.rawSet(key, value);
		}

		@Override
		public boolean rawEqual(LuaObject o)
		{
			return o != null && o.getClass() == getClass() && ((LuaJava) o).cls.equals(cls) && Objects.equals(((LuaJava) o).obj, obj);
		}

		@Override
		public String name()
		{
			return (obj == null ? "class " : "") + cls.getName() + "*";
		}

		@Override
		public Object getUserdata()
		{
			return obj == null ? cls : obj;
		}

		@Override
		public String getString(LuaInterpreter interp)
		{
			return obj == null ? cls.toString() : obj.toString();
		}
	}

	static class LuaJavaClass extends LuaJava
	{
		LuaJavaClass(Class<?> cls)
		{
			this.cls = cls;

			metatable = new LuaTable();
			metatable.rawSet("__name", new LuaString(cls.getName() + "*"));
			metatable.rawSet("__index", metatable);

			Constructor<?>[] constructors = cls.getConstructors();
			if(constructors.length > 0)
				setConstructor(constructors);
		}

		private void setConstructor(final Constructor<?>[] constructors)
		{
			metatable.rawSet("new", new LuaFunction() {
				@Override
				LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
				{
					for (Constructor<?> constructor : constructors)
					{
						Class<?>[] params = constructor.getParameterTypes();
						Object[] result = attemptConvert("new", params, args, false);

						if(result != null)
						{
							try
							{
								return new LuaJavaInstance(LuaJavaClass.this, constructor.newInstance(result));
							}
							catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
							{
								throw new LuaException(e.getLocalizedMessage());
							}
						}
					}
					return null;
				}
			});
		}
	}

	static class LuaJavaInstance extends LuaJava
	{
		private LuaJavaInstance(LuaJavaClass cls, Object obj)
		{
			this.cls = cls.cls;
			this.obj = obj;
		}
	}

	/** Constant <code>EXKEY_CLASS_PREFIX="java.class."</code> */
	public static final String EXKEY_CLASS_PREFIX = "java.class.";
}