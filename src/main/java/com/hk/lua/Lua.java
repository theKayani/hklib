package com.hk.lua;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import java.util.function.*;

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
	@NotNull
	public static LuaFactory factory(@NotNull Reader rdr, @NotNull String source)
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
	@NotNull
	public static LuaFactory factory(@NotNull Reader rdr)
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
	@NotNull
	public static LuaFactory factory(@NotNull String str)
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
	@NotNull
	public static LuaFactory factory(@NotNull File file) throws FileNotFoundException
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
	@NotNull
	public static LuaFactory factory(@NotNull File file, @NotNull Charset charset) throws FileNotFoundException
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
	@NotNull
	public static LuaFactory factory(@NotNull InputStream input)
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
	@NotNull
	public static LuaFactory factory(@NotNull InputStream input, @NotNull Charset charset)
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
	@NotNull
	public static LuaFactory factory(@NotNull URL url) throws IOException
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
	@NotNull
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
	@NotNull
	public static LuaInterpreter interpreter(@NotNull String source)
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
	@NotNull
	public static LuaInterpreter reader(@NotNull Reader rdr, @NotNull String source)
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
	@NotNull
	public static LuaInterpreter reader(@NotNull Reader rdr)
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
	@NotNull
	public static LuaInterpreter reader(@NotNull String str)
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
	@NotNull
	public static LuaInterpreter reader(@NotNull File file) throws FileNotFoundException
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
	@NotNull
	public static LuaInterpreter reader(@NotNull File file, @NotNull Charset charset) throws FileNotFoundException
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
	@NotNull
	public static LuaInterpreter reader(@NotNull InputStream input)
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
	@NotNull
	public static LuaInterpreter reader(@NotNull InputStream input, @NotNull Charset charset)
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
	@NotNull
	public static LuaInterpreter reader(@NotNull URL url) throws IOException
	{
		return new LuaInterpreter(new InputStreamReader(url.openStream()), url.getFile());
	}

	/**
	 * Import the standard <code>Lua 5.3</code> libraries as well as
	 * three additional libraries regarding hashing, JSON, and dates.
	 * @param interp The environment to import the libraries into
	 */
	public static void importStandard(@NotNull LuaInterpreter interp)
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
	public static void importStandard(@NotNull LuaFactory factory)
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
	@NotNull
	public static LuaObject newLuaObject(@Nullable Object o)
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
			return Lua.newMethod((LuaMethod) o);
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
	 * @deprecated use {@link #newBool} or {@link #TRUE} or {@link #FALSE}
	 */
	@Deprecated
	@NotNull
	public static LuaObject newBoolean(boolean value)
	{
		return newBool(value);
	}

	/**
	 * <p>Convert the specified parameter into a Lua boolean.</p>
	 * <p>Calling {@link LuaObject#isBoolean()} on the result will
	 * return true.</p>
	 *
	 * @param value a boolean
	 * @return a {@link com.hk.lua.LuaObject} boolean object
	 */
	@NotNull
	public static LuaObject newBool(boolean value)
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
	@NotNull
	public static LuaObject newString(@Nullable CharSequence cs)
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
	@NotNull
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
	@NotNull
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
	@NotNull
	public static LuaObject newString(@Nullable String str)
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
	@NotNull
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
	@NotNull
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
	@NotNull
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
	@NotNull
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
	@NotNull
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
	@NotNull
	public static LuaObject newTable(@NotNull Map<LuaObject, LuaObject> map)
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
	@NotNull
	public static LuaObject newTable(@NotNull Iterable<LuaObject> iterable)
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
	@NotNull
	public static LuaObject newJavaFunc(@NotNull Class<?> cls, @NotNull String methodName)
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
	@NotNull
	public static LuaObject newJavaFunc(@NotNull Object o, @NotNull String methodName)
	{
		return newJavaFunc(o, o.getClass(), methodName);
	}

	private static LuaObject newJavaFunc(@Nullable Object o, @NotNull Class<?> cls, @NotNull String methodName)
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
	 * @see #newMethod(LuaMethod)
	 *
	 * @deprecated
	 */
	@Deprecated
	@NotNull
	public static LuaObject newFunc(@NotNull final LuaMethod method)
	{
		return newMethod(method);
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
	@NotNull
	public static LuaObject newMethod(@NotNull final LuaMethod method)
	{
		return new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				LuaObject o = method.call(interp, args);
				return o == null ? LuaNil.NIL : o;
			}
		};
	}

	/**
	 * <p>Convert a {@link java.util.function.DoublePredicate} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.BiPredicate} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final DoublePredicate method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.NUMBER);
			return Lua.newBool(method.test(args[0].getDouble()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.IntPredicate} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.IntPredicate} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final IntPredicate method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			return Lua.newBool(method.test(args[0].getInt()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.LongPredicate} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.LongPredicate} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final LongPredicate method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			return Lua.newBool(method.test(args[0].getLong()));
		});
	}

	/**
	 * <p>Convert a String {@link java.util.function.Predicate} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a String {@link java.util.function.Predicate} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final Predicate<String> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.STRING);
			return Lua.newBool(method.test(args[0].getString()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.DoubleConsumer} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.DoubleConsumer} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final DoubleConsumer method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.NUMBER);
			method.accept(args[0].getDouble());
			return null;
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.IntConsumer} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.IntConsumer} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final IntConsumer method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			method.accept(args[0].getInt());
			return null;
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.LongConsumer} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.LongConsumer} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final LongConsumer method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			method.accept(args[0].getLong());
			return null;
		});
	}

	/**
	 * <p>Convert a String {@link java.util.function.Consumer} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a String {@link java.util.function.Consumer} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final Consumer<String> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.STRING);
			method.accept(args[0].getString());
			return null;
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.ObjDoubleConsumer} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.ObjDoubleConsumer} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final ObjDoubleConsumer<LuaInterpreter> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.NUMBER);
			method.accept(interp, args[0].getDouble());
			return null;
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.ObjIntConsumer} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.ObjIntConsumer} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final ObjIntConsumer<LuaInterpreter> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			method.accept(interp, args[0].getInt());
			return null;
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.ObjLongConsumer} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.ObjLongConsumer} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final ObjLongConsumer<LuaInterpreter> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			method.accept(interp, args[0].getLong());
			return null;
		});
	}

	/**
	 * <p>Convert a String {@link java.util.function.BiConsumer} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a String {@link java.util.function.BiConsumer} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final BiConsumer<LuaInterpreter, String> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.STRING);
			method.accept(interp, args[0].getString());
			return null;
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.BooleanSupplier} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.BooleanSupplier} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final BooleanSupplier method)
	{
		return newMethod((interp, args) -> Lua.newBool(method.getAsBoolean()));
	}

	/**
	 * <p>Convert a {@link java.util.function.DoubleSupplier} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.DoubleSupplier} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final DoubleSupplier method)
	{
		return newMethod((interp, args) -> Lua.newNumber(method.getAsDouble()));
	}

	/**
	 * <p>Convert a {@link java.util.function.IntSupplier} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.IntSupplier} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final IntSupplier method)
	{
		return newMethod((interp, args) -> Lua.newNumber(method.getAsInt()));
	}

	/**
	 * <p>Convert a {@link java.util.function.LongSupplier} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.LongSupplier} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final LongSupplier method)
	{
		return newMethod((interp, args) -> Lua.newNumber(method.getAsLong()));
	}

	/**
	 * <p>Convert a String {@link java.util.function.Supplier} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a String {@link java.util.function.Supplier} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final Supplier<String> method)
	{
		return newMethod((interp, args) -> Lua.newString(method.get()));
	}

	/**
	 * <p>Convert a {@link java.util.function.DoubleFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.DoubleFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final DoubleFunction<LuaObject> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.NUMBER);
			return method.apply(args[0].getDouble());
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.DoubleToIntFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.DoubleToIntFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final DoubleToIntFunction method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.NUMBER);
			return Lua.newNumber(method.applyAsInt(args[0].getDouble()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.DoubleToLongFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.DoubleToLongFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final DoubleToLongFunction method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.NUMBER);
			return Lua.newNumber(method.applyAsLong(args[0].getDouble()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.ToDoubleFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.ToDoubleFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final ToDoubleFunction<LuaObject[]> method)
	{
		return newMethod((interp, args) -> Lua.newNumber(method.applyAsDouble(args)));
	}

	/**
	 * <p>Convert a {@link java.util.function.ToDoubleBiFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.ToDoubleBiFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final ToDoubleBiFunction<LuaInterpreter, LuaObject[]> method)
	{
		return newMethod((interp, args) -> Lua.newNumber(method.applyAsDouble(interp, args)));
	}

	/**
	 * <p>Convert a {@link java.util.function.IntFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.IntFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final IntFunction<LuaObject> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			return method.apply(args[0].getInt());
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.IntToDoubleFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.IntToDoubleFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final IntToDoubleFunction method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			return Lua.newNumber(method.applyAsDouble(args[0].getInt()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.IntToLongFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.IntToLongFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final IntToLongFunction method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			return Lua.newNumber(method.applyAsLong(args[0].getInt()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.ToIntFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.ToIntFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final ToIntFunction<LuaObject[]> method)
	{
		return newMethod((interp, args) -> Lua.newNumber(method.applyAsInt(args)));
	}

	/**
	 * <p>Convert a {@link java.util.function.ToIntBiFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.ToIntBiFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final ToIntBiFunction<LuaInterpreter, LuaObject[]> method)
	{
		return newMethod((interp, args) -> Lua.newNumber(method.applyAsInt(interp, args)));
	}

	/**
	 * <p>Convert a {@link java.util.function.LongFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.LongFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final LongFunction<LuaObject> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			return method.apply(args[0].getLong());
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.LongToDoubleFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.LongToDoubleFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final LongToDoubleFunction method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			return Lua.newNumber(method.applyAsDouble(args[0].getInt()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.LongToIntFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.LongToIntFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final LongToIntFunction method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			return Lua.newNumber(method.applyAsInt(args[0].getInt()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.ToLongFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.ToLongFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final ToLongFunction<LuaObject[]> method)
	{
		return newMethod((interp, args) -> Lua.newNumber(method.applyAsLong(args)));
	}

	/**
	 * <p>Convert a {@link java.util.function.ToLongBiFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.ToLongBiFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final ToLongBiFunction<LuaInterpreter, LuaObject[]> method)
	{
		return newMethod((interp, args) -> Lua.newNumber(method.applyAsLong(interp, args)));
	}

	/**
	 * <p>Convert a String {@link java.util.function.Function} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a String {@link java.util.function.Function} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final String name, @NotNull final Function<String, LuaObject> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.STRING);
			return method.apply(args[0].getString());
		});
	}

	/**
	 * <p>Convert a String {@link java.util.function.Function} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a String {@link java.util.function.Function} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final Function<LuaObject[], String> method)
	{
		return newMethod((interp, args) -> Lua.newString(method.apply(args)));
	}

	/**
	 * <p>Convert a {@link java.util.function.BiFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.BiFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFuncFrom(@NotNull final BiFunction<LuaInterpreter, LuaObject[], String> method)
	{
		return newMethod((interp, args) -> Lua.newString(method.apply(interp, args)));
	}

	/**
	 * <p>Convert a {@link java.util.function.Supplier} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.Supplier} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final Supplier<LuaObject> method)
	{
		return newMethod((interp, args) -> method.get());
	}

	/**
	 * <p>Convert a {@link java.util.function.UnaryOperator} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.UnaryOperator} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final String name, @NotNull final UnaryOperator<LuaObject> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.ANY);
			return method.apply(args[0]);
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.BinaryOperator} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.BinaryOperator} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final String name, @NotNull final BinaryOperator<LuaObject> method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.ANY, LuaType.ANY);
			return method.apply(args[0], args[1]);
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.DoubleBinaryOperator} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.DoubleBinaryOperator} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final String name, @NotNull final DoubleBinaryOperator method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.NUMBER, LuaType.NUMBER);
			return Lua.newNumber(method.applyAsDouble(args[0].getDouble(), args[1].getDouble()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.DoubleUnaryOperator} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.DoubleUnaryOperator} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final String name, @NotNull final DoubleUnaryOperator method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.NUMBER);
			return Lua.newNumber(method.applyAsDouble(args[0].getDouble()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.IntBinaryOperator} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.IntBinaryOperator} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final String name, @NotNull final IntBinaryOperator method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER, LuaType.INTEGER);
			return Lua.newNumber(method.applyAsInt(args[0].getInt(), args[1].getInt()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.IntUnaryOperator} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.IntUnaryOperator} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final String name, @NotNull final IntUnaryOperator method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			return Lua.newNumber(method.applyAsInt(args[0].getInt()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.LongBinaryOperator} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.LongBinaryOperator} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final String name, @NotNull final LongBinaryOperator method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER, LuaType.INTEGER);
			return Lua.newNumber(method.applyAsLong(args[0].getInt(), args[1].getInt()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.LongUnaryOperator} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param name name of the method for errors
	 * @param method a {@link java.util.function.LongUnaryOperator} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final String name, @NotNull final LongUnaryOperator method)
	{
		return newMethod((interp, args) -> {
			Lua.checkArgs(name, args, LuaType.INTEGER);
			return Lua.newNumber(method.applyAsLong(args[0].getLong()));
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.Consumer} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.Consumer} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final Consumer<LuaObject[]> method)
	{
		return newMethod((interp, args) -> {
			method.accept(args);
			return null;
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.BiConsumer} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.BiConsumer} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final BiConsumer<LuaInterpreter, LuaObject[]> method)
	{
		return newMethod((interp, args) -> {
			method.accept(interp, args);
			return null;
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.Predicate} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.Predicate} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final Predicate<LuaObject[]> method)
	{
		return newMethod((interp, args) -> Lua.newBool(method.test(args)));
	}

	/**
	 * <p>Convert a {@link java.util.function.BiPredicate} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.BiPredicate} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final BiPredicate<LuaInterpreter, LuaObject[]> method)
	{
		return newMethod((interp, args) -> Lua.newBool(method.test(interp, args)));
	}

	/**
	 * <p>Convert a {@link java.util.function.Function} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.Function} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final Function<LuaObject[], LuaObject> method)
	{
		return newMethod((interp, args) -> method.apply(args));
	}

	/**
	 * <p>Convert a {@link java.lang.Runnable} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.lang.Runnable} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final Runnable method)
	{
		return newMethod((interp, args) -> {
			method.run();
			return LuaNil.NIL;
		});
	}

	/**
	 * <p>Convert a {@link java.util.function.BiFunction} into a Lua function.</p>
	 * <p>This returns a Lua object that can be executed.</p>
	 * <p>Calling {@link LuaObject#isFunction()} on the result will
	 * return true.</p>
	 *
	 * @param method a {@link java.util.function.BiFunction} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject newFunc(@NotNull final BiFunction<LuaInterpreter, LuaObject[], LuaObject> method)
	{
		return newMethod(method::apply);
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
	@NotNull
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
	public static void checkArgs(@NotNull String method, @NotNull LuaObject[] args, LuaType... types)
	{
		for(int i = 0; i < types.length; i++)
		{
			if(i == args.length || !types[i].applies(args[i].type()))
				throw badArgument(i, method, types[i].luaName + " expected", i < args.length ? "got " + args[i].name() : null);
		}
	}

	/**
	 * <p>This method throws a 'bad argument' Lua exception. This is
	 * usually used when a method gets an invalid parameter. With a
	 * <code>null</code> extra parameter.</p>
	 *
	 * <p>Here are some examples:</p>
	 *
	 * <code>badArgument(0, "tostring", "expected string", "got nil")</code>
	 * <p>Will produce: <code>bad argument #1 to 'tostring' (expected string, got nil)</code></p>
	 *
	 * <code>badArgument(3, "openAccount", "email cannot be empty")</code>
	 * <p>Will produce: <code>bad argument #4 to 'openAccount' (email cannot be empty)</code></p>
	 *
	 * @param param the parameter index that threw the exception
	 * @param method the name of the method to which the argument was
	 *                  supplied
	 * @param message the issue message, ex: 'expected table or nil'
	 * @return an exception to be thrown with the appropriate message
	 */
	@NotNull
	public static LuaException badArgument(int param, @NotNull String method, @NotNull String message)
	{
		throw badArgument(param, method, message, null);
	}

	/**
	 * <p>This method throws a 'bad argument' Lua exception. This is
	 * usually used when a method gets an invalid parameter.</p>
	 *
	 * <p>Here are some examples:</p>
	 *
	 * <code>badArgument(0, "tostring", "expected string", "got nil")</code>
	 * <p>Will produce: <code>bad argument #1 to 'tostring' (expected string, got nil)</code></p>
	 *
	 * <code>badArgument(3, "openAccount", "email cannot be empty")</code>
	 * <p>Will produce: <code>bad argument #4 to 'openAccount' (email cannot be empty)</code></p>
	 *
	 * @param param the parameter index that threw the exception
	 * @param method the name of the method to which the argument was
	 *                  supplied
	 * @param message the issue message, ex: 'expected table or nil'
	 * @param extra an extra message that is concatenated to the
	 *                 message
	 * @return an exception to be thrown with the appropriate message
	 */
	@NotNull
	public static LuaException badArgument(int param, @NotNull String method, @NotNull String message, @Nullable String extra)
	{
		return new LuaException("bad argument #" + (param + 1) + " to '" + method + "' (" + message + (extra == null ? "" : ", " + extra) + ")");
	}

	@NotNull
	public static LuaObject wrapErr(@NotNull Exception ex)
	{
		return wrapErr(ex.getLocalizedMessage());
	}

	@NotNull
	public static LuaObject wrapErr(@NotNull String message)
	{
		return new LuaArgs(NIL, new LuaString(message));
	}

	/**
	 * @deprecated see {@link #checkArgs(String, LuaObject[], LuaType...)}
	 */
	@Deprecated
	public static void checkArgs(@NotNull String method, @NotNull LuaType[] types, LuaObject... args)
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
	 * This interface can be passes into {@link #newMethod} to return
	 * a {@link LuaObject} as an executable Lua function.
	 */
	@FunctionalInterface
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