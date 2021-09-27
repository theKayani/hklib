package com.hk.lua;

import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>Lua class.</p>
 *
 * @author theKayani
 */
public class Lua
{
	static final String STDIN = "<stdin>";

	/**
	 * <p>factory.</p>
	 *
	 * @param rdr a {@link java.io.Reader} object
	 * @param source a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(Reader rdr, String source)
	{
		return new LuaFactory(Objects.requireNonNull(rdr), source);
	}

	/**
	 * <p>factory.</p>
	 *
	 * @param rdr a {@link java.io.Reader} object
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(Reader rdr)
	{
		return new LuaFactory(Objects.requireNonNull(rdr), STDIN);
	}

	/**
	 * <p>factory.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(String str)
	{
		return new LuaFactory(new StringReader(str), STDIN);
	}

	/**
	 * <p>factory.</p>
	 *
	 * @param array an array of {@link char} objects
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(char[] array)
	{
		return new LuaFactory(new CharArrayReader(array), STDIN);
	}

	/**
	 * <p>factory.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link com.hk.lua.LuaFactory} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static LuaFactory factory(File file) throws FileNotFoundException
	{
		return new LuaFactory(new FileReader(file), file.getName());
	}

	/**
	 * <p>factory.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.lua.LuaFactory} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static LuaFactory factory(File file, Charset charset) throws FileNotFoundException
	{
		return new LuaFactory(new InputStreamReader(new FileInputStream(file), charset), file.getName());
	}

	/**
	 * <p>factory.</p>
	 *
	 * @param input a {@link java.io.InputStream} object
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(InputStream input)
	{
		return new LuaFactory(new InputStreamReader(input), STDIN);
	}

	/**
	 * <p>factory.</p>
	 *
	 * @param input a {@link java.io.InputStream} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(InputStream input, Charset charset)
	{
		return new LuaFactory(new InputStreamReader(input, charset), STDIN);
	}

	/**
	 * <p>factory.</p>
	 *
	 * @param url a {@link java.net.URL} object
	 * @return a {@link com.hk.lua.LuaFactory} object
	 * @throws java.io.IOException if any.
	 */
	public static LuaFactory factory(URL url) throws IOException
	{
		return new LuaFactory(new InputStreamReader(url.openStream()), url.getFile());
	}

	/**
	 * <p>interpreter.</p>
	 *
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter interpreter()
	{
		return new LuaInterpreter((Reader) null, STDIN);
	}

	/**
	 * <p>interpreter.</p>
	 *
	 * @param source a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter interpreter(String source)
	{
		return new LuaInterpreter((Reader) null, source);
	}
	
	/**
	 * <p>reader.</p>
	 *
	 * @param rdr a {@link java.io.Reader} object
	 * @param source a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(Reader rdr, String source)
	{
		return new LuaInterpreter(Objects.requireNonNull(rdr), source);
	}
	
	/**
	 * <p>reader.</p>
	 *
	 * @param rdr a {@link java.io.Reader} object
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(Reader rdr)
	{
		return new LuaInterpreter(Objects.requireNonNull(rdr), STDIN);
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(String str)
	{
		return new LuaInterpreter(new StringReader(str), STDIN);
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param array an array of {@link char} objects
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(char[] array)
	{
		return new LuaInterpreter(new CharArrayReader(array), STDIN);
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static LuaInterpreter reader(File file) throws FileNotFoundException
	{
		return new LuaInterpreter(new FileReader(file), file.getName());
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static LuaInterpreter reader(File file, Charset charset) throws FileNotFoundException
	{
		return new LuaInterpreter(new InputStreamReader(new FileInputStream(file), charset), file.getName());
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param input a {@link java.io.InputStream} object
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(InputStream input)
	{
		return new LuaInterpreter(new InputStreamReader(input), STDIN);
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param input a {@link java.io.InputStream} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(InputStream input, Charset charset)
	{
		return new LuaInterpreter(new InputStreamReader(input, charset), STDIN);
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param url a {@link java.net.URL} object
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 * @throws java.io.IOException if any.
	 */
	public static LuaInterpreter reader(URL url) throws IOException
	{
		return new LuaInterpreter(new InputStreamReader(url.openStream()), url.getFile());
	}
	
	/**
	 * <p>newLuaObject.</p>
	 *
	 * @param o a {@link java.lang.Object} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newLuaObject(Object o)
	{
		if(o == null)
			return LuaNil.NIL;
		else if(o instanceof LuaObject)
			return (LuaObject) o;
		else if(o instanceof CharSequence)
			return new LuaString(((CharSequence) o).toString());
		else if(o instanceof Character)
			return new LuaString(String.valueOf((Character) o));
		else if(o instanceof Byte || o instanceof Short || o instanceof Integer || o instanceof Long)
			return newNumber(((Number) o).longValue());
		else if(o instanceof Number)
			return newNumber(((Number) o).doubleValue());
		else if(o instanceof Boolean)
			return LuaBoolean.valueOf((Boolean) o);
		else if(o instanceof LuaObject[])
			return ((LuaObject[]) o).length == 0 ? LuaNil.NIL : new LuaArgs((LuaObject[]) o);
		else if(o instanceof LuaMethod)
			return Lua.newFunc((LuaMethod) o);
		else if(o.getClass().isArray())
		{
			int len = Array.getLength(o);
			if(len == 0)
				return LuaNil.NIL;
			
			LuaObject[] objs = new LuaObject[len];
			for(int i = 0; i < len; i++)
				objs[i] = Lua.newLuaObject(Array.get(o, i));
			
			return new LuaArgs(objs);
		}
		else if(o instanceof Map<?, ?>)
		{
			Map<?, ?> mp = (Map<?, ?>) o;
			LuaTable tbl = new LuaTable();
			for(Map.Entry<?, ?> ent : mp.entrySet())
				tbl.rawSet(newLuaObject(ent.getKey()), newLuaObject(ent.getValue()));

			return tbl;
		}
		else if(o instanceof Iterable<?>)
		{
			Iterable<?> itr = (Iterable<?>) o;
			Iterator<?> iterator = itr.iterator();

			LuaTable tbl = new LuaTable();
			int i = 1;
			while(iterator.hasNext())
				tbl.rawSet(LuaInteger.valueOf(i++), newLuaObject(iterator.next()));

			return tbl;
		}
		else
			throw new UnsupportedOperationException();
	}
	
	/**
	 * <p>newBoolean.</p>
	 *
	 * @param value a boolean
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newBoolean(boolean value)
	{
		return LuaBoolean.valueOf(value);
	}
	
	/**
	 * <p>newString.</p>
	 *
	 * @param cs a {@link java.lang.CharSequence} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newString(CharSequence cs)
	{
		return cs == null ? LuaNil.NIL : newString(cs.toString());
	}
	
	/**
	 * <p>newString.</p>
	 *
	 * @param c a char
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newString(char c)
	{
		return newString(String.valueOf(c));
	}
	
	/**
	 * <p>newString.</p>
	 *
	 * @param cs an array of {@link char} objects
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newString(char[] cs)
	{
		return newString(new String(cs));
	}
	
	/**
	 * <p>newString.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newString(String str)
	{
		return str == null ? LuaNil.NIL : new LuaString(str);
	}
	
	/**
	 * <p>newNumber.</p>
	 *
	 * @param value a double
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newNumber(double value)
	{
		return new LuaFloat(value);
	}
	
	/**
	 * <p>newNumber.</p>
	 *
	 * @param value a long
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newNumber(long value)
	{
		return LuaInteger.valueOf(value);
	}
	
	/**
	 * <p>newVarargs.</p>
	 *
	 * @param args a {@link com.hk.lua.LuaObject} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newVarargs(LuaObject... args)
	{
		return new LuaArgs(args);
	}
	
	/**
	 * <p>getVarargs.</p>
	 *
	 * @param o a {@link com.hk.lua.LuaObject} object
	 * @return an array of {@link com.hk.lua.LuaObject} objects
	 */
	public static LuaObject[] getVarargs(LuaObject o)
	{
		if(o instanceof LuaArgs)
			return ((LuaArgs) o).objs;
		else
			return new LuaObject[] { o };
	}
	
	/**
	 * <p>newTable.</p>
	 *
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newTable()
	{
		return new LuaTable();
	}
	
	/**
	 * <p>newTable.</p>
	 *
	 * @param map a {@link java.util.Map} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newTable(Map<LuaObject, LuaObject> map)
	{
		return new LuaTable(new LinkedHashMap<>(map));
	}
	
	/**
	 * <p>newTable.</p>
	 *
	 * @param iterable a {@link java.lang.Iterable} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newTable(Iterable<LuaObject> iterable)
	{
		LuaObject tbl = new LuaTable();
		LuaObject o;
		long i = 1;
		
		Iterator<LuaObject> itr = iterable.iterator();
		while(itr.hasNext())
			tbl.rawSet(i++, (o = itr.next()) == null ? LuaNil.NIL : o);
		
		return tbl;
	}
	
	/**
	 * <p>newJavaFunc.</p>
	 *
	 * @param cls a {@link java.lang.Class} object
	 * @param methodName a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newJavaFunc(Class<?> cls, String methodName)
	{
		return newJavaFunc(null, cls, methodName);
	}
	
	/**
	 * <p>newJavaFunc.</p>
	 *
	 * @param o a {@link java.lang.Object} object
	 * @param methodName a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newJavaFunc(Object o, String methodName)
	{
		return newJavaFunc(o, o.getClass(), methodName);
	}
	
	private static LuaObject newJavaFunc(Object o, Class<?> cls, String methodName)
	{
		Method[] ms = cls.getDeclaredMethods();
		for(int i = 0; i < ms.length; i++)
		{
			if(ms[i].getName().equals(methodName))
			{
				if(ms[i].isAnnotationPresent(LuaParameters.class))
					return new LuaJavaFunction.LuaJavaArgFunction(ms[i], o);
				else
					return new LuaJavaFunction(ms[i], o);
			}
		}
		return LuaNil.NIL;
	}
	
	/**
	 * <p>newFunc.</p>
	 *
	 * @param method a {@link com.hk.lua.Lua.LuaMethod} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(final LuaMethod method)
	{
		return new LuaFunction()
		{
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{
				LuaObject o = method.call(interp, args);
				return o == null ? LuaNil.NIL : o;
			}
		};
	}
	
	/**
	 * <p>nil.</p>
	 *
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject nil()
	{
		return LuaNil.NIL;
	}
	
	/**
	 * <p>check.</p>
	 *
	 * @param method a {@link java.lang.String} object
	 * @param args an array of {@link com.hk.lua.LuaObject} objects
	 * @param types an array of {@link com.hk.lua.LuaType} objects
	 * @return an array of {@link com.hk.lua.LuaObject} objects
	 */
	public static LuaObject[] check(String method, LuaObject[] args, LuaType[] types)
	{
		for(int i = 0; i < types.length; i++)
		{
			if(i == args.length || !types[i].applies(args[i].type()))
				throw new LuaException("bad argument #" + (i + 1) + " to '" + method + "' (" + types[i].luaName + " expected)");
		}
		
		return args;
	}
	
	/**
	 * <p>checkArgs.</p>
	 *
	 * @param method a {@link java.lang.String} object
	 * @param args an array of {@link com.hk.lua.LuaObject} objects
	 * @param types a {@link com.hk.lua.LuaType} object
	 */
	public static void checkArgs(String method, LuaObject[] args, LuaType... types)
	{
		check(method, args, types);
	}
	
	/**
	 * <p>checkArgs.</p>
	 *
	 * @param method a {@link java.lang.String} object
	 * @param types an array of {@link com.hk.lua.LuaType} objects
	 * @param args a {@link com.hk.lua.LuaObject} object
	 */
	public static void checkArgs(String method, LuaType[] types, LuaObject... args)
	{
		check(method, args, types);
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface LuaParameters
	{
		LuaType[] value();
	}
	
	public interface LuaMethod
	{
		abstract LuaObject call(LuaInterpreter interp, LuaObject[] args);
	}

	static abstract class LuaValue implements Tokens
	{
		abstract LuaObject evaluate(LuaInterpreter interp);
	}
	
	private Lua()
	{}

	public static final String EXIT_CODE = "interpreter.exit";
}
