package com.hk.lua;

import com.hk.func.BiConsumer;
import com.hk.lua.Lua.LuaMethod;

import java.awt.*;
import java.io.*;
import java.lang.ref.WeakReference;
import java.lang.reflect.*;
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
			return getJavaClass(interp, args[0].getString());
		}

		@Override
		public String toString()
		{
			return "class";
		}
	},
	reflect() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.ANY);
			return LuaNil.NIL;
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

	private static LuaObject getJavaClass(LuaInterpreter interp, String name)
	{
		try
		{
			return getJavaClass(interp, Class.forName(name, false, ClassLoader.getSystemClassLoader()));
		}
		catch (ClassNotFoundException e)
		{
			return new LuaArgs(LuaNil.NIL, new LuaString("class not found: " + e.getLocalizedMessage()));
		}
	}

	private static LuaJavaClass getJavaClass(LuaInterpreter interp, Class<?> cls)
	{
		String key = EXKEY_CLASS_PREFIX + cls.getName();
		if(interp.hasExtra(key))
			return interp.getExtra(key, LuaJavaClass.class);

		LuaJavaClass luaCls = new LuaJavaClass(interp, cls);
		interp.setExtra(key, luaCls);
		return luaCls;
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
		else if(arg.getUserdata() != null && cls.isInstance(arg.getUserdata()))
			return arg.getUserdata();

		throw new IllegalArgumentException();
	}

	private static class LuaJava extends LuaUserdata
	{
		protected final LuaInterpreter interp;
		protected final Class<?> cls;
		protected final Object obj;

		LuaJava(LuaInterpreter interp, Class<?> cls, Object obj)
		{
			this.interp = interp;
			this.cls = cls;
			this.obj = obj;

			metatable = new LuaTable();
			metatable.rawSet("__name", new LuaString(name()));
			metatable.rawSet("__index", metatable);
		}

		@Override
		public LuaObject rawGet(LuaObject key)
		{
			if(key.isString())
			{
				Object res = null;
				try
				{
					Field field = cls.getField(key.getString());
					res = field.get(obj);
					return Lua.newLuaObject(res);
				}
				catch (UnsupportedOperationException e)
				{
					Objects.requireNonNull(res);
					if(res instanceof Class<?>)
						return getJavaClass(interp, (Class<?>) res);
					else
						return new LuaJavaInstance(interp, res.getClass(), res);
				}
				catch (NoSuchFieldException | IllegalAccessException e)
				{
					throw new LuaException("no such field: " + e.getLocalizedMessage());
				}
			}
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

					if(Modifier.isFinal(field.getModifiers()))
						throw new LuaException("cannot alter field " + field.getDeclaringClass().getName() + "." + field.getName() + " which is final");
					else
						field.set(obj, attempt("rawset", 2, field.getType(), value));
				}
				catch (NoSuchFieldException | IllegalAccessException e)
				{
					throw new LuaException("no such field: " + e.getLocalizedMessage());
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
		public LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(cls.getName(), args, LuaType.STRING);
			String name = args[0].getString();
			Class<?>[] params = new Class<?>[args.length - 1];
			for (int i = 1; i < args.length; i++)
			{
				if(args[i].isString())
				{
					String s = args[i].getString();
					switch(s)
					{
						case "byte":
							params[i - 1] = byte.class;
							break;
						case "short":
							params[i - 1] = short.class;
							break;
						case "integer":
						case "int":
							params[i - 1] = int.class;
							break;
						case "long":
							params[i - 1] = long.class;
							break;
						case "float":
							params[i - 1] = float.class;
							break;
						case "double":
							params[i - 1] = double.class;
							break;
						case "char":
							params[i - 1] = char.class;
							break;
						case "boolean":
							params[i - 1] = boolean.class;
							break;
						case "string":
							params[i - 1] = String.class;
							break;
						default:
							try
							{
								params[i - 1] = Class.forName(s, false, ClassLoader.getSystemClassLoader());
							}
							catch (ClassNotFoundException e)
							{
								throw new LuaException("class not found: " + e.getLocalizedMessage());
							}
					}
				}
				else if(args[i] instanceof LuaJavaClass)
					params[i - 1] = ((LuaJavaClass) args[i]).cls;
				else
					throw Lua.badArgument(i, cls.getName(), "expected string", "got " + args[i].name());
			}

			try
			{
				return createLuaFunc(cls.getMethod(name, params));
			}
			catch (NoSuchMethodException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString("method not found: " + e.getLocalizedMessage()));
			}
		}

		protected LuaObject createLuaFunc(final Method method)
		{
			return new LuaFunction() {
				@Override
				LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
				{
					Object[] objects = attemptConvert(method.getName(), method.getParameterTypes(), args, true);

					try
					{
						Object res = method.invoke(obj, objects);
						try
						{
							return Lua.newLuaObject(res);
						}
						catch (UnsupportedOperationException e)
						{
							Objects.requireNonNull(res);
							if(res instanceof Class<?>)
								return getJavaClass(interp, (Class<?>) res);
							else
								return new LuaJavaInstance(interp, res.getClass(), res);
						}
					}
					catch (IllegalAccessException | InvocationTargetException e)
					{
						throw new LuaException("cannot execute: " + e.getLocalizedMessage());
					}
				}
			};
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

	public static class LuaJavaClass extends LuaJava
	{
		LuaJavaClass(LuaInterpreter interp, Class<?> cls)
		{
			super(interp, cls, null);

			Constructor<?>[] constructors = cls.getConstructors();
			if(constructors.length > 0)
				setConstructor(constructors);

			for (final Method method : cls.getMethods())
			{
				if(Modifier.isStatic(method.getModifiers()))
					metatable.rawSet(method.getName(), createLuaFunc(method));
			}
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
								return new LuaJavaInstance(interp, LuaJavaClass.this.cls, constructor.newInstance(result));
							}
							catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
							{
								throw new LuaException(e.getLocalizedMessage());
							}
						}
					}
					return LuaNil.NIL;
				}
			});
		}
	}

	public static class LuaJavaInstance extends LuaJava
	{
		private LuaJavaInstance(LuaInterpreter interp, Class<?> cls, Object obj)
		{
			super(interp, cls, Objects.requireNonNull(obj));
			if(!cls.isInstance(obj))
				throw new IllegalArgumentException("cannot cast " + obj + " to " + cls);

			for (final Method method : cls.getMethods())
			{
				if(!Modifier.isStatic(method.getModifiers()))
					metatable.rawSet(method.getName(), createLuaFunc(method));
			}
		}
	}

	/** Constant <code>EXKEY_CLASS_PREFIX="java.class."</code> */
	public static final String EXKEY_CLASS_PREFIX = "java.class.";
}