package com.hk.lua;

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
 * <p>This class contains various utility methods for handling
 * different types of Lua data and environments.</p>
 *
 * @author theKayani
 */
public class Lua
{
	static final String STDIN = "<stdin>";
	/**
	 * Lua nil
	 */
	public static final LuaObject NIL = LuaNil.NIL;
	/**
	 * Lua boolean: true
	 */
	public static final LuaObject TRUE = LuaBoolean.TRUE;
	/**
	 * Lua boolean: false
	 */
	public static final LuaObject FALSE = LuaBoolean.FALSE;
	/**
	 * Lua integer: 0
	 */
	public static final LuaObject ZERO = LuaInteger.ZERO;
	/**
	 * Lua integer: 1
	 */
	public static final LuaObject ONE = LuaInteger.ONE;
	/**
	 * Lua integer: -1
	 */
	public static final LuaObject NEG_ONE = LuaInteger.NEG_ONE;
	/**
	 * Lua float: NaN
	 */
	public static final LuaObject NaN = LuaFloat.NaN;
	/**
	 * Lua float: max value
	 */
	public static final LuaObject HUGE = LuaFloat.HUGE;
	/**
	 * Lua float: pi
	 */
	public static final LuaObject PI = LuaFloat.PI;
	/**
	 * Lua float: inf
	 */
	public static final LuaObject POSITIVE_INFINITY = LuaFloat.POSITIVE_INFINITY;
	/**
	 * Lua float: -inf
	 */
	public static final LuaObject NEGATIVE_INFINITY = LuaFloat.NEGATIVE_INFINITY;

	/**
	 * <p>Construct a {@link LuaFactory} from a reader using the
	 * specified parameter as the source.</p>
	 *
	 * <p>Lua factories are pre-compiled chunks of Lua code. Instead
	 * of having to wrap a reader with a {@link LuaInterpreter}
	 * everytime, you can create a factory with the reader and use
	 * the factory to provide new {@link LuaInterpreter} objects.</p>
	 *
	 * <p>Take this example, lets say we have a factory:</p>
	 * <code>
	 *     LuaFactory factory = Lua.factory("return testing()");<br>
	 *     factory.addLibrary(LuaLibrary.BASIC);<br>
	 * </code>
	 * <p>We can then create two separate interpreters with different
	 * functionalities upon calling the <code>testing</code> method.</p>
	 *
	 * @param rdr Lua code
	 * @param source Source of code for error messages
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(Reader rdr, String source)
	{
		return new LuaFactory(Objects.requireNonNull(rdr), source);
	}

	/**
	 * <p>Construct a {@link LuaFactory} from a reader using STDIN as
	 * the source.</p>
	 *
	 * @param rdr Lua code
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(Reader rdr)
	{
		return new LuaFactory(Objects.requireNonNull(rdr), STDIN);
	}

	/**
	 * <p>Construct a {@link LuaFactory} from a string using STDIN as
	 * the source.</p>
	 *
	 * @param str Lua code
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(String str)
	{
		return new LuaFactory(new StringReader(str), STDIN);
	}

	/**
	 * <p>Construct a {@link LuaFactory} from a file using the file
	 * name as the source. Uses the default charset to read the file.</p>
	 *
	 * @param file Lua code source file
	 * @return a {@link com.hk.lua.LuaFactory} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static LuaFactory factory(File file) throws FileNotFoundException
	{
		return new LuaFactory(new FileReader(file), file.getName());
	}

	/**
	 * <p>Construct a {@link LuaFactory} from a file using the file
	 * name as the source. Specify a charset to use when reading
	 * characters from the file.</p>
	 *
	 * @param file Lua code source file
	 * @param charset to use when reading the file
	 * @return a {@link com.hk.lua.LuaFactory} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static LuaFactory factory(File file, Charset charset) throws FileNotFoundException
	{
		return new LuaFactory(new InputStreamReader(new FileInputStream(file), charset), file.getName());
	}

	/**
	 * <p>Construct a {@link LuaFactory} from an input stream using
	 * STDIN as the source. The input stream is read using the
	 * default charset.</p>
	 *
	 * @param input Lua code
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(InputStream input)
	{
		return new LuaFactory(new InputStreamReader(input), STDIN);
	}

	/**
	 * <p>Construct a {@link LuaFactory} from an input stream using
	 * STDIN as the source. Specify a charset to use when reading
	 * characters from the stream.</p>
	 *
	 * @param input Lua code
	 * @param charset to use when reading the stream
	 * @return a {@link com.hk.lua.LuaFactory} object
	 */
	public static LuaFactory factory(InputStream input, Charset charset)
	{
		return new LuaFactory(new InputStreamReader(input, charset), STDIN);
	}

	/**
	 * <p>Construct a {@link LuaFactory} from a URL as the source for
	 * the Lua code.</p>
	 *
	 * @param url containing Lua code
	 * @return a {@link com.hk.lua.LuaFactory} object
	 * @throws java.io.IOException if any.
	 */
	public static LuaFactory factory(URL url) throws IOException
	{
		return new LuaFactory(new InputStreamReader(url.openStream()), url.getFile());
	}

	/**
	 * <p>Construct a {@link LuaInterpreter} using STDIN as the source.</p>
	 * <p>This interpreter can be used to read chunks of Lua code in
	 * realtime.</p>
	 *
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter interpreter()
	{
		return new LuaInterpreter((Reader) null, STDIN);
	}

	/**
	 * <p>Construct a {@link LuaInterpreter} using the specified
	 * parameter.</p>
	 * <p>This interpreter can be used to read chunks of Lua code in
	 * realtime.</p>
	 *
	 * @param source Source of code for error messages
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter interpreter(String source)
	{
		return new LuaInterpreter((Reader) null, source);
	}
	
	/**
	 * <p>Construct a {@link LuaInterpreter} from a reader using the
	 * specified parameter as the source.</p>
	 *
	 * <p>Lua interpreter objects can be used to execute arbitrary
	 * Lua code within an enclosed environment. Let's say we have a
	 * file called <code>main.lua</code> with the following contents:</p>
	 * <code>
	 *     print("Hello World!")<br>
	 *     local a = 1<br>
	 *     local b = a + a<br>
	 *     local c = a + b * b<br>
	 *     return globalVariable + b + c * c<br>
	 * </code>
	 * <br>
	 * <p>Take the following code as an example:</p>
	 * <code>
	 *     LuaInterpreter interp = Lua.reader(new File("main.lua"));<br>
	 *     interp.compile();<br>
	 *     interp.importLibrary(LuaLibrary.BASIC);<br>
	 * </code>
	 * <p>The first line opens a new interpreter using the given file.
	 * The second line parses the file into an executable Lua chunk.
	 * The third line allows us to import various methods, fields,
	 * and functions into our Lua environment. {@link LuaLibraryBasic}
	 * contains the code revolving around the standard library in Lua
	 * 5.3. By importing it, we have access to the <code>print</code>
	 * method which prints its arguments to the console.</p>
	 *
	 * <p>Before we can run the lua code without errors, we have to
	 * supply <code>globalVariable</code>, or else it will remain
	 * as <code>nil</code>. We can achieve this using the following:</p>
	 * <code>
	 *     Environment env = interp.getGlobals();<br>
	 *     env.setVar("globalVariable", Lua.newNumber(2));<br>
	 * </code>
	 *
	 * <p>This is how we can inject variables and data into our Lua
	 * environment for our code to manipulate.</p>
	 *
	 * <p>To execute the following code and collect the result:</p>
	 * <code>
	 *     Object result = interp.execute(...);<br>
	 * </code>
	 * <p>The <code>...</code> can be Lua objects which will be
	 * passed into the <code>...</code> variable in the lua code.</p>
	 *
	 * <p>This covers the basics of how the {@link LuaInterpreter}
	 * can be used to execute arbitrary Lua code which can, in turn,
	 * be wired into Java methods, functions, and data.</p>
	 *
	 * @param rdr Lua code
	 * @param source Source of code for error messages
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(Reader rdr, String source)
	{
		return new LuaInterpreter(Objects.requireNonNull(rdr), source);
	}
	
	/**
	 * <p>Construct a {@link LuaInterpreter} from a reader using
	 * STDIN as the source.</p>
	 *
	 * @param rdr Lua code
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(Reader rdr)
	{
		return new LuaInterpreter(Objects.requireNonNull(rdr), STDIN);
	}

	/**
	 * <p>Construct a {@link LuaInterpreter} from a string using
	 * STDIN as the source.</p>
	 *
	 * @param str Lua code
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(String str)
	{
		return new LuaInterpreter(new StringReader(str), STDIN);
	}

	/**
	 * <p>Construct a {@link LuaInterpreter} from a file using the
	 * file name as the source. Uses the default charset to read the
	 * file.</p>
	 *
	 * @param file Lua code source file
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static LuaInterpreter reader(File file) throws FileNotFoundException
	{
		return new LuaInterpreter(new FileReader(file), file.getName());
	}

	/**
	 * <p>Construct a {@link LuaInterpreter} from a file using the
	 * file name as the source. Specify a charset to use when reading
	 * characters from the file.</p>
	 *
	 * @param file Lua code source file
	 * @param charset to use when reading the file
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static LuaInterpreter reader(File file, Charset charset) throws FileNotFoundException
	{
		return new LuaInterpreter(new InputStreamReader(new FileInputStream(file), charset), file.getName());
	}

	/**
	 * <p>Construct a {@link LuaInterpreter} from a stream using the
	 * STDIN as the source. Uses the default charset to read the
	 * stream.</p>
	 *
	 * @param input Lua code
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(InputStream input)
	{
		return new LuaInterpreter(new InputStreamReader(input), STDIN);
	}

	/**
	 * <p>Construct a {@link LuaInterpreter} from a stream using the
	 * STDIN as the source. Specify a charset to use when reading
	 * characters from the stream.</p>
	 *
	 * @param input Lua code
	 * @param charset to use when reading the stream
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public static LuaInterpreter reader(InputStream input, Charset charset)
	{
		return new LuaInterpreter(new InputStreamReader(input, charset), STDIN);
	}

	/**
	 * <p>Construct a {@link LuaInterpreter} from a URL as the source for
	 * the Lua code.</p>
	 *
	 * @param url containing Lua code
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 * @throws java.io.IOException if any.
	 */
	public static LuaInterpreter reader(URL url) throws IOException
	{
		return new LuaInterpreter(new InputStreamReader(url.openStream()), url.getFile());
	}

	/**
	 * Import the standard <code>Lua 5.3</code> libraries as well as
	 * three additional libraries regarding hashing, JSON, and dates.
	 * @param interp The environment to import the libraries into
	 */
	public static void importStandard(LuaInterpreter interp)
	{
		interp.importLib(LuaLibrary.BASIC);
		interp.importLib(LuaLibrary.COROUTINE);
//		interp.importLib(LuaLibrary.PACKAGE);
		interp.importLib(LuaLibrary.STRING);
//		interp.importLib(LuaLibrary.UTF8);
		interp.importLib(LuaLibrary.TABLE);
		interp.importLib(LuaLibrary.MATH);
		interp.importLib(LuaLibrary.IO);
		interp.importLib(LuaLibrary.OS);
//		interp.importLib(LuaLibrary.DEGUB);

		interp.importLib(LuaLibrary.JSON);
		interp.importLib(LuaLibrary.HASH);
		interp.importLib(LuaLibrary.DATE);
	}

	/**
	 * <p>Import the standard <code>Lua 5.3</code> libraries as well as
	 * three additional libraries regarding hashing, JSON, and dates.</p>
	 * <p>Once these are imported into the factory, they don't need to
	 * be imported into the retrieved interpreter object.</p>
	 *
	 * @param factory The environment to import the libraries into
	 */
	public static void importStandard(LuaFactory factory)
	{
		factory.addLibrary(LuaLibrary.BASIC);
		factory.addLibrary(LuaLibrary.COROUTINE);
//		factory.addLibrary(LuaLibrary.PACKAGE);
		factory.addLibrary(LuaLibrary.STRING);
//		factory.addLibrary(LuaLibrary.UTF8);
		factory.addLibrary(LuaLibrary.TABLE);
		factory.addLibrary(LuaLibrary.MATH);
		factory.addLibrary(LuaLibrary.IO);
		factory.addLibrary(LuaLibrary.OS);
//		factory.addLibrary(LuaLibrary.DEGUB);

		factory.addLibrary(LuaLibrary.JSON);
		factory.addLibrary(LuaLibrary.HASH);
		factory.addLibrary(LuaLibrary.DATE);
	}
	
	/**
	 * <p>Convert, or attempt to convert, the given object into the Lua object
	 * equivalent. Here's a quick overview of how conversions happen:</p>
	 * <ul>
	 *   <li>
	 *     <code>null</code> returns Lua <code>nil</code>
	 *   </li>
	 *   <li>
	 *     <code>LuaObject</code> returns itself
	 *   </li>
	 *   <li>
	 *       <code>{@link String}</code>, <code>{@link CharSequence}</code>,
	 *       and <code>{@link Character}</code> return a Lua string
	 *   </li>
	 *   <li>
	 *       <code>{@link Byte}</code>, <code>{@link Short}</code>,
	 *       <code>{@link Integer}</code>, and <code>{@link Long}</code>
	 *       return a Lua integer
	 *   </li>
	 *   <li>
	 *       <code>{@link Float}</code> and <code>{@link Double}</code>
	 *       return a Lua float
	 *   </li>
	 *   <li>
	 *       <code>Boolean</code> return a Lua boolean
	 *   </li>
	 *   <li>
	 *       <code>Object[]</code> return a lua varargs (see {@link #newVarargs})
	 *   </li>
	 *   <li>
	 *       empty Java array return Lua <code>nil</code>
	 *   </li>
	 *   <li>
	 *       {@link java.util.Map} returns a Lua table
	 *   </li>
	 *   <li>
	 *       {@link java.lang.Iterable} return a Lua sequence
	 *   </li>
	 *   <li>
	 *       otherwise <i>exception</i>
	 *   </li>
	 * </ul>
	 * <p>Array and collection values are converted into Lua objects
	 * using the same method.</p>
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
		else if(o instanceof CharSequence || o instanceof Character)
			return new LuaString(String.valueOf(o));
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
	 * <p>Convert the specified parameter into a Lua boolean.</p>
	 * <p>Calling {@link LuaObject#isBoolean()} on the result will
	 * return true.</p>
	 *
	 * @param value a boolean
	 * @return a {@link com.hk.lua.LuaObject} boolean object
	 */
	public static LuaObject newBoolean(boolean value)
	{
		return LuaBoolean.valueOf(value);
	}
	
	/**
	 * <p>Convert the specified parameter into a Lua string.</p>
	 * <p>Calling {@link LuaObject#isString()} on the result will
	 * return true. Unless the parameter is null, then the result is
	 * <code>nil</code>.</p>
	 *
	 * @param cs a {@link java.lang.CharSequence} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newString(CharSequence cs)
	{
		return cs == null ? LuaNil.NIL : newString(cs.toString());
	}
	
	/**
	 * <p>Convert the specified parameter into a Lua string.</p>
	 * <p>Calling {@link LuaObject#isString()} on the result will
	 * return true.</p>
	 *
	 * @param c a char
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newString(char c)
	{
		return newString(String.valueOf(c));
	}
	
	/**
	 * <p>Convert the specified parameter into a Lua string.</p>
	 * <p>Calling {@link LuaObject#isString()} on the result will
	 * return true. Unless the parameter is null, then the result is
	 * <code>nil</code>.</p>
	 *
	 * @param cs an array of {@link char} objects
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newString(char[] cs)
	{
		return cs == null ? LuaNil.NIL : new LuaString(new String(cs));
	}
	
	/**
	 * <p>Convert the specified parameter into a Lua string.</p>
	 * <p>Calling {@link LuaObject#isString()} on the result will
	 * return true. Unless the parameter is null, then the result is
	 * <code>nil</code>.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newString(String str)
	{
		return str == null ? LuaNil.NIL : new LuaString(str);
	}
	
	/**
	 * <p>Convert the specified parameter into a Lua float.</p>
	 * <p>Calling {@link LuaObject#isNumber()} on the result will
	 * return true but {@link LuaObject#isInteger()} will return
	 * false.</p>
	 *
	 * @param value a double
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newNumber(double value)
	{
		return new LuaFloat(value);
	}
	
	/**
	 * <p>Convert the specified parameter into a Lua integer.</p>
	 * <p>Calling {@link LuaObject#isNumber()} or
	 * {@link LuaObject#isInteger()} on the result will return true.</p>
	 *
	 * @param value a long
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newNumber(long value)
	{
		return LuaInteger.valueOf(value);
	}
	
	/**
	 * <p>Convert the specified parameters into a Lua args object.</p>
	 * <p>This is similar to returning multiple values from a lua
	 * function. If directly used, only the first value matters, but
	 * this object can be expanded to include the rest of the
	 * arguments. See Lua manual.</p>
	 *
	 * @see <a href="https://www.lua.org/pil/5.2.html">https://www.lua.org/pil/5.2.html</a>
	 * @param args a {@link com.hk.lua.LuaObject} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newVarargs(LuaObject... args)
	{
		return new LuaArgs(args);
	}
	
	/**
	 * <p>Convert this potential LuaObject into an array of objects.</p>
	 * <p>If the parameter is a Lua varargs object, then the result
	 * array is the objects stored in this variable argument object.</p>
	 * <p>If the parameter is NOT a Lua varargs object, then it is
	 * returned in a one-length/single-object array.</p>
	 * @see <a href="https://www.lua.org/pil/5.2.html">https://www.lua.org/pil/5.2.html</a>
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
	 * <p>Convert an empty map into a new Lua table. The internal map
	 * is a {@link java.util.LinkedHashMap}.</p>
	 * <p>Using the {@link LuaObject#rawSet} and {@link LuaObject#rawGet}
	 * methods, you can manipulate the data in the map, using the Lua
	 * object. On the other hand, the {@link LuaObject#getIndex} and
	 * {@link LuaObject#setIndex} methods respect the metatable and
	 * require the interpreter.</p>
	 * <p>Calling {@link LuaObject#isTable()} on the result will
	 * return true.</p>
	 *
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newTable()
	{
		return new LuaTable();
	}
	
	/**
	 * <p>Convert a given map into a new Lua table. The map is
	 * wrapped in a {@link java.util.LinkedHashMap} first.</p>
	 * <p>Calling {@link LuaObject#isTable()} on the result will
	 * return true.</p>
	 *
	 * @param map a {@link java.util.Map} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newTable(Map<LuaObject, LuaObject> map)
	{
		return new LuaTable(new LinkedHashMap<>(map));
	}
	
	/**
	 * <p>Convert a given iterable into a new Lua sequence. The
	 * indices are incremented by one since arrays start at 1 in Lua.</p>
	 * <p>Calling {@link LuaObject#isTable()} on the result will
	 * return true.</p>
	 *
	 * @param iterable a {@link java.lang.Iterable} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newTable(Iterable<LuaObject> iterable)
	{
		LuaObject tbl = new LuaTable();
		LuaObject o;
		long i = 1;

		for (LuaObject luaObject : iterable)
			tbl.rawSet(i++, (o = luaObject) == null ? LuaNil.NIL : o);
		
		return tbl;
	}
	
	/**
	 * <p>Convert a Java function into a Lua function. This operation
	 * uses Java Reflection. Take a look at the following example:</p>
	 * <code>
	 *     public class MathHelp<br>
	 *     {<br>
	 *     &nbsp;&nbsp;public static double random(LuaObject[] args)<br>
	 *     &nbsp;&nbsp;{<br>
	 *     &nbsp;&nbsp;&nbsp;&nbsp;return Math.random();<br>
	 *     &nbsp;&nbsp;}<br>
	 *     }<br>
	 * </code>
	 * <p>We can create a Lua function wrapping 'random(args)' using
	 * this method. Here's what that would look like:</p>
	 * <code>
	 *     LuaObject function = Lua.newJavaFunc(MathHelp.class, "random");<br>
	 * </code>
	 * <p>This isn't generally recommended since it can be quite slow
	 * because of Java Reflection but if necessary, it is available.</p>
	 *
	 * @param cls that contains the method
	 * @param methodName to wrap in a Lua function
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newJavaFunc(Class<?> cls, String methodName)
	{
		return newJavaFunc(null, cls, methodName);
	}
	
	/**
	 * <p>Convert a Java function into a Lua function. This operation
	 * uses Java Reflection.</p>
	 *
	 * @see #newJavaFunc(Class, String)
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
		for (Method m : ms)
		{
			if (m.getName().equals(methodName))
			{
				if (m.isAnnotationPresent(LuaParameters.class))
					return new LuaJavaFunction.LuaJavaArgFunction(m, o);
				else
					return new LuaJavaFunction(m, o);
			}
		}
		return LuaNil.NIL;
	}
	
	/**
	 * <p>Convert a {@link LuaMethod} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
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
	 * <p>Lua <code>nil</code></p>
	 * <p>Calling {@link LuaObject#isNil()} on the result will
	 * return true.</p>
	 *
	 * @return a {@link com.hk.lua.LuaObject} object
	 * @deprecated see #NIL
	 */
	@Deprecated
	public static LuaObject nil()
	{
		return LuaNil.NIL;
	}

	/**
	 * <p>This method checks the arguments array against the types
	 * array to assure they match up. Otherwise, a "bad argument" lua
	 * exception is thrown.</p>
	 *
	 * @param method a {@link java.lang.String} object
	 * @param args an array of {@link com.hk.lua.LuaObject} objects
	 * @param types a {@link com.hk.lua.LuaType} object
	 */
	public static void checkArgs(String method, LuaObject[] args, LuaType... types)
	{
		for(int i = 0; i < types.length; i++)
		{
			if(i == args.length || !types[i].applies(args[i].type()))
				throw new LuaException("bad argument #" + (i + 1) + " to '" + method + "' (" + types[i].luaName + " expected)");
		}
	}

	/**
	 * @deprecated see {@link #checkArgs(String, LuaObject[], LuaType...)}
	 */
	@Deprecated
	public static void checkArgs(String method, LuaType[] types, LuaObject... args)
	{
		checkArgs(method, args, types);
	}

	/**
	 * <p>Annotate a Java method with this class to automatically
	 * assure parameters being passed to the annotated method. Take
	 * a look at the following example:</p>
	 * <code>
	 *     public class MathHelp<br>
	 *     {<br>
	 *     &nbsp;&nbsp;{@literal @}LuaParameter(LuaType.NUMBER, LuaType.NUMBER)<br>
	 *     &nbsp;&nbsp;public static double atan2(LuaObject y, LuaObject x)<br>
	 *     &nbsp;&nbsp;{<br>
	 *     &nbsp;&nbsp;&nbsp;&nbsp;return Math.atan2(y.getFloat(), x.getFloat());<br>
	 *     &nbsp;&nbsp;}<br>
	 *     }<br>
	 * </code>
	 * <p>This allows us to have a set number of arguments and to
	 * ensure the type of object being passed. Using the
	 * {@link Lua#newJavaFunc} method, we can retrieve a Lua function
	 * that wraps the method above!</p>
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface LuaParameters
	{
		/**
		 * @return The types of arguments that should be accepted
		 */
		LuaType[] value();
	}

	/**
	 * This interface can be passes into {@link #newFunc} to return
	 * a {@link LuaObject} as an executable Lua function.
	 */
	public interface LuaMethod
	{
		/**
		 * <p>Run a Java method, or function, wrapped as a LuaObject</p>
		 *
		 * @param interp the interpreter executing this code
		 * @param args the arguments passed during execution
		 * @return the result of the execution, if is null, will be
		 * converted into Lua nil.
		 */
		LuaObject call(LuaInterpreter interp, LuaObject[] args);
	}

	static abstract class LuaValue implements Tokens
	{
		abstract LuaObject evaluate(LuaInterpreter interp);
	}
	
	private Lua()
	{}

	/**
	 * <p>If the {@link LuaInterpreter} was exited using the <code>exit</code>
	 * method, then the resulting exit code will be placed in the
	 * extra data of the interpreter. The exit code can be retrieved
	 * using this line:</p>
	 * <code>
	 *     interpreter.getExtra(Lua.EXIT_CODE);
	 * </code>
	 */
	public static final String EXIT_CODE = "interpreter.exit";
}
