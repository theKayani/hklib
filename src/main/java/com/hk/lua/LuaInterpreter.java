package com.hk.lua;

import com.hk.ex.UncheckedIOException;
import com.hk.func.BiConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/**
 * This utility class encapsulates an entire Lua environment within
 * a single Java class/package. This class is instantiated using the
 * {@link Lua} class, which allows you to construct a Lua
 * interpreter using a {@link java.io.Reader}. The reader is fully
 * read and interpreted as Lua code. Which can then be executed in a
 * myriad of fashions. There can be various types of variables and
 * objects that can be directly tied to Java methods, fields, or code.
 *
 * @see Lua#interpreter()
 * @see Lua#reader
 * @see LuaFactory#build()
 * @see <a href="https://www.lua.org/manual/5.3/">https://www.lua.org/manual/5.3/</a>
 *
 * @author theKayani
 */
public class LuaInterpreter implements Tokens
{
	private final Map<String, Object> extraData;
	private final String mainSrc;
	private LuaChunk mainChunk;
	private Reader reader;
	final Map<LuaObject, LuaObject> required;
	final Stack<LuaThread> threads;
	Environment global, env;
	String currSource;

	LuaInterpreter(@NotNull LuaStatement[] sts, @NotNull String source)
	{
		this.reader = null;
		this.mainSrc = source;
		global = new Environment(this, null, false);
		extraData = new HashMap<>();
		threads = new Stack<>();
		required = new HashMap<>();

		mainChunk = new LuaChunk(sts, source, global, true);
	}

	LuaInterpreter(@NotNull Reader reader, @NotNull String source)
	{
		this.reader = reader;
		this.mainSrc = source;
		global = new Environment(this, null, false);
		extraData = new HashMap<>();
		threads = new Stack<>();
		required = new HashMap<>();

		if(reader == null)
			mainChunk = new LuaChunk(new LuaStatement[0], source, global, false);
	}

	/**
	 * <p>Check whether a certain key has a value under it, whether null or not.</p>
	 *
	 * <p>Extra data it data that is tied to this specific interpreter.
	 * Completely optional and has no effect on execution of Lua.
	 * Just for utility and data consistency sake.</p>
	 *
	 * @param key a {@link java.lang.String} to match to a key
	 * @return if this certain key has data under it
	 */
	public boolean hasExtra(@NotNull String key)
	{
		return extraData.containsKey(key);
	}

	/**
	 * <p>Get the extra data under a certain key.</p>
	 *
	 * <p>Extra data it data that is tied to this specific interpreter.
	 * Completely optional and has no effect on execution of Lua.
	 * Just for utility and data consistency sake.</p>
	 *
	 * @param key a {@link java.lang.String} to match to a key
	 * @return a {@link java.lang.Object} or null if there is no match.
	 */
	@Nullable
	public Object getExtra(@NotNull String key)
	{
		return extraData.get(key);
	}

	/**
	 * <p>Get the extra data under a certain key. The data returned
	 * should be able to be cast to the <code>cls</code> specified.
	 * If so, it is boxed and returned.</p>
	 *
	 * <p>Extra data it data that is tied to this specific interpreter.
	 * Completely optional and has no effect on execution of Lua.
	 * Just for utility and data consistency sake.</p>
	 *
	 * @param key a {@link java.lang.String} to match to a key
	 * @param cls a {@link java.lang.Class} to cast the returned
	 *            value to
	 * @param <T> a T class
	 * @return a T object, or null if none was found.
	 */
	@Nullable
	public <T> T getExtra(@NotNull String key, @NotNull Class<T> cls)
	{
		return getExtra(key, cls, null);
	}

	/**
	 * <p>Get the extra data under a certain key, or a default if there
	 * data found under the key was null or not found.</p>
	 *
	 * <p>Extra data it data that is tied to this specific interpreter.
	 * Completely optional and has no effect on execution of Lua.
	 * Just for utility and data consistency sake.</p>
	 *
	 * @param key a {@link java.lang.String} to match to a key
	 * @param cls a {@link java.lang.Class} to cast the returned
	 *               value to
	 * @param def a T object or the default if there
	 *               was one provided
	 * @param <T> a T class
	 * @return a T object, or the def value if the data was null or
	 * there was no found data.
	 */
	@Nullable
	public <T> T getExtra(@NotNull String key, @NotNull Class<T> cls, @Nullable T def)
	{
		try
		{
			Object o = getExtra(key);
			if(o != null)
				return cls.cast(o);
		}
		catch(ClassCastException ignored)
		{}
		return def;
	}

	/**
	 * <p>Get the extra data under a certain key, cast as a {@link LuaObject}.
	 * This is useful because it will not return null, it will
	 * instead return {@link Lua#NIL} if there was no valid data found.</p>
	 *
	 * <p>Extra data it data that is tied to this specific interpreter.
	 * Completely optional and has no effect on execution of Lua.
	 * Just for utility and data consistency sake.</p>
	 *
	 * @param key a {@link java.lang.String} to match to a key
	 * @return a {@link com.hk.lua.LuaObject} object, or {@link Lua#NIL}
	 * if there was no valid data found under the specified key.
	 */
	@NotNull
	public LuaObject getExtraLua(@NotNull String key)
	{
		return getExtra(key, LuaObject.class, LuaNil.NIL);
	}

	/**
	 * <p>Get the extra data under a certain key, cast as a {@link String}.
	 * This will return null if there was no valid data found.</p>
	 *
	 * <p>Extra data it data that is tied to this specific interpreter.
	 * Completely optional and has no effect on execution of Lua.
	 * Just for utility and data consistency sake.</p>
	 *
	 * @param key a {@link java.lang.String} to match to a key
	 * @return a {@link java.lang.String} object, or null
	 * if there was no valid data found under the specified key.
	 */
	@Nullable
	public String getExtraProp(@NotNull String key)
	{
		return getExtra(key, String.class);
	}

	/**
	 * <p>Set the data under a certain key. This value can then later be
	 * retrieved using the {@link #getExtra} methods. These have
	 * various overloaded helper functions to return the value cast
	 * as another type.</p>
	 *
	 * <p>Extra data it data that is tied to this specific interpreter.
	 * Completely optional and has no effect on execution of Lua.
	 * Just for utility and data consistency sake.</p>
	 *
	 * @param key a {@link java.lang.String} to match to a key
	 * @param value a {@link java.lang.Object} object, or null if desired
	 * @return this
	 */
	@NotNull
	public LuaInterpreter setExtra(@NotNull String key, @Nullable Object value)
	{
		extraData.put(key, value);
		return this;
	}

	/**
	 * <p>Remove a certain key from the extra data. This will result in
	 * the default value being used (if passed) or a null value when
	 * using the {@link #getExtra} methods.</p>
	 *
	 * <p>Extra data it data that is tied to this specific interpreter.
	 * Completely optional and has no effect on execution of Lua.
	 * Just for utility and data consistency sake.</p>
	 *
	 * @param key a {@link java.lang.String} to match to a key
	 * @return this
	 */
	@NotNull
	public LuaInterpreter removeExtra(@NotNull String key)
	{
		extraData.remove(key);
		return this;
	}

	/**
	 * <p>Get the global Lua environment with global variables and
	 * functions. Conventionally, the developer would use this method
	 * to set the various global variables for this specific
	 * environment before calling the {@link #execute} method, or
	 * calling a specific method by name.</p>
	 *
	 * <p>Calling this before <code>execute</code> will only contain the
	 * library imported methods and data. After calling the <code>execute</code>
	 * method, will the environment be populated by the variables
	 * from the Lua code interpreted.</p>
	 *
	 * @return the global Lua {@link com.hk.lua.Environment}
	 */
	@NotNull
	public Environment getGlobals()
	{
		return global;
	}

	/**
	 * Immediately execute a string of Lua code using this global
	 * environment under a new Lua chunk.
	 *
	 * @param cs the Lua code to interpret
	 * @return the result from this execution. If this code had a
	 * return statement.
	 */
	@NotNull
	public LuaObject require(@NotNull CharSequence cs)
	{
		return require(null, new StringReader(cs.toString()));
	}

	/**
	 * Immediately execute a reader of Lua code using this global
	 * environment under a new Lua chunk.
	 *
	 * @param reader a {@link java.io.Reader} to provide the Lua code
	 *               to interpret
	 * @return the result from this execution. If this code had a
	 * return statement.
	 */
	@NotNull
	public LuaObject require(@NotNull Reader reader)
	{
		return require(null, reader);
	}

	/**
	 * Immediately execute a reader of Lua code using this global
	 * environment under a new Lua chunk. This function executes the
	 * Lua code under a certain module, the result is then stored in
	 * the case that this function is called again with the same
	 * module. If so, the reader is ignored and the saved value is
	 * returned instead.
	 *
	 * @param module a key to match the result object to
	 * @param reader a {@link java.io.Reader} to provide the Lua code
	 *               to interpret
	 * @return the result from this execution. If this code had a
	 * return statement.
	 */
	@NotNull
	public LuaObject require(@Nullable String module, @NotNull Reader reader)
	{
		LuaObject result = module == null ? null : required.get(new LuaString(module));
		if(result == null)
		{
			LuaChunk chunk;
			try
			{
				chunk = readLua(reader, module == null ? Lua.STDIN : module, new Environment(this, global, true), true);
			}
			catch(IOException e)
			{
				throw new LuaException(e.getLocalizedMessage());
			}

			result = (LuaObject) chunk.execute(this);
			if(module != null)
				required.put(new LuaString(module), result);
		}
		return result;
	}

	public boolean hasModule(@NotNull String module)
	{
		return required.containsKey(new LuaString(module));
	}

	/**
	 * <p>Import the individual elements of a Lua Library directly into
	 * the global environment. Whether the elements are placed in the
	 * global space, or under a certain table is defined by the
	 * constructor when instantiating the library object.</p>
	 *
	 * <p>To use any of the default libraries, you don't need to define
	 * any functions yourself. All you need to do is import the
	 * various standard libraries under {@link LuaLibrary}.</p>
	 *
	 * @see LuaLibrary#LuaLibrary(String, Class)
	 *
	 * @param lib a {@link com.hk.lua.LuaLibrary} object
	 * @param <T> a T class
	 */
	public <T extends Enum<T> & BiConsumer<Environment, LuaObject>> void importLib(@NotNull LuaLibrary<T> lib)
	{
		LuaObject tbl;
		if(lib.table == null || lib.table.trim().isEmpty())
			tbl = global.lua_G;
		else
			global.setVar(lib.table, tbl = new LuaTable());

		for(BiConsumer<Environment, LuaObject> consumer : lib.consumers)
			consumer.accept(global, tbl);
	}

	/**
	 * <p>Execute the interpreter using the previously provided
	 * reader. If the {@link #compile} was not called first, the code
	 * is first interpreted and compiled before executed.</p>
	 *
	 * <p>The <code>args</code> parameter is for providing a variable
	 * amount of arguments to the Lua code. According to
	 * <a href="https://www.lua.org/manual/5.3/manual.html#3.4.11">The Lua 5.3 Manual</a>,
	 * the provided arguments are converted into their LuaObject
	 * equivalents and stored in the <code>...</code> varargs
	 * variable in the Lua code.</p>
	 *
	 * @param args any vararg parameters to pass to the Lua code.
	 * @return a result of the execution.
	 * @throws com.hk.lua.LuaException if any.
	 * @throws com.hk.ex.UncheckedIOException if any.
	 */
	@SuppressWarnings("ThrowableNotThrown")
	@Nullable
	public Object execute(Object... args) throws UncheckedIOException
	{
		try
		{
			compile();
			return mainChunk.execute(this, args);
		}
		catch(LuaException ex)
		{
			new LuaException(ex);
			throw ex;
		}
	}

	/**
	 * <p>Attempt to fully read the provided {@link java.io.Reader} and
	 * interpret it as Lua code. If there are any Lua syntax errors,
	 * this function will throw a {@link LuaException}.</p>
	 *
	 * <p>If not, the code is saved to be executed using the
	 * {@link #execute} command. If this is not called beforehand,
	 * it is called by default by the <code>execute</code> function.</p>
	 *
	 * @throws com.hk.lua.LuaException if any Lua syntax errors.
	 * @throws com.hk.ex.UncheckedIOException if any.
	 */
	public void compile() throws UncheckedIOException
	{
		if(mainChunk != null)
			return;

		try
		{
			mainChunk = readLua(reader, mainSrc, global, false);
			reader = null;
		}
		catch(IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	static LuaChunk readLua(Reader rdr, String source, Environment env, boolean secondary) throws IOException
	{
		Tokenizer tkz = new Tokenizer(rdr);
		try
		{
			LuaChunk chunk = new LuaChunk(readStatements(tkz, source, 0), source, env, secondary);
			if(tkz.next())
				throw unexpected(tkz, source);
			return chunk;
		}
		catch(LuaException e)
		{
			if(!e.primary)
				throw e;
			else
				throw new LuaException(source, tkz.line(), e.getLocalizedMessage());
		}
		finally
		{
			rdr.close();
		}
	}

	private static LuaBlock readBlock(Tokenizer tkz, String source, int flags) throws IOException
	{
		return new LuaBlock(readStatements(tkz, source, flags), source);
	}

	static LuaStatement[] readStatements(Tokenizer tkz, String source, int flags) throws IOException
	{
		List<LuaStatement> lst = new ArrayList<>();
		int len;
		while(tkz.next())
		{
			len = lst.size();
			if(!readStatement(tkz, source, lst, flags))
			{
				if(len == lst.size())
					tkz.prev();
				break;
			}
		}
		return lst.toArray(new LuaStatement[0]);
	}

	private static boolean readStatement(Tokenizer tkz, String source, List<LuaStatement> lst, int flags) throws IOException
	{
		boolean res = false;
		int line = tkz.line(), type = tkz.type();
		LuaExpression exp;
		LuaBlock block;

		switch(type)
		{
		case T_IF:
			exp = readExpression(tkz, source, true);
			if(tkz.next() && tkz.type() == T_THEN)
			{
				block = readBlock(tkz, source, flags | F_IF);

				LuaIfStatement ifSt = new LuaIfStatement(line, exp, block);
				res = true;
				lst.add(ifSt);
				iflbl:
				do
				{
					if(tkz.next())
					{
						line = tkz.line();
						switch(tkz.type())
						{
						case T_ELSEIF:
							exp = readExpression(tkz, source, true);
							if(tkz.next() && tkz.type() == T_THEN)
							{
								block = readBlock(tkz, source, flags | F_ELSEIF);
								ifSt.elseStatement = new LuaIfStatement(line, exp, block);
								ifSt.hasNext = true;
								ifSt = ifSt.elseStatement;
								break;
							}
							else
								throw unexpected(tkz, source, "else if, expected 'then'");
						case T_ELSE:
							block = readBlock(tkz, source, flags | F_ELSE);
							ifSt.elseStatement = new LuaIfStatement(line, null, block);
							ifSt.hasNext = true;
							if(tkz.next() && tkz.type() == T_END)
								break iflbl;
							else
								throw unexpected(tkz, source, "else, expected 'end'");
						case T_END:
							break iflbl;
						default:
							throw unexpected(tkz, source);
						}
					}
					else
						throw unexpected(tkz, source, "if, expected 'end'");
				} while(true);
				break;
			}
			else
				throw unexpected(tkz, source);
		case T_WHILE:
			exp = readExpression(tkz, source, true);
			if(tkz.next() && tkz.type() == T_DO)
			{
				res = true;
				lst.add(new LuaWhileStatement(line, exp, readBlock(tkz, source, flags | F_WHILE)));
				if(tkz.next() && tkz.type() == T_END)
					break;
				else
					throw unexpected(tkz, source, "while, expected 'end'");
			}
			else
				throw unexpected(tkz, source, "while, expected 'do'");
		case T_REPEAT:
			block = readBlock(tkz, source, flags | F_REPEAT);
			if(tkz.next() && tkz.type() == T_UNTIL)
			{
				exp = readExpression(tkz, source, true);
				res = true;
				lst.add(new LuaRepeatStatement(line, block, exp));
				break;
			}
			else
				throw unexpected(tkz, source, "repeat, expected 'until'");
		case T_FOR:
			if(tkz.next() && tkz.type() == T_IDENTIFIER)
			{
				String id = (String) tkz.value();
				List<String> ids = new ArrayList<>();
				ids.add(id);
				if(!tkz.next())
					throw unexpected(tkz, source, "for");

				LuaForStatement fst;
				switch(tkz.type())
				{
				case T_EQUALS:
					Lua.LuaValue exp1 = readExpression(tkz, source, true);

					if(!tkz.next() || tkz.type() != T_COMMA)
						throw unexpected(tkz, source, "numeric for, expected ','");

					Lua.LuaValue exp2 = readExpression(tkz, source, true);

					Lua.LuaValue exp3 = LuaInteger.ONE;
					if(tkz.next())
					{
						if(tkz.type() == T_COMMA)
							exp3 = readExpression(tkz, source, true);
						else
							tkz.prev();
					}

					fst = new LuaNumericForStatement(line, id, exp1, exp2, exp3);
					break;
				case T_COMMA:
				case T_IN:
					if(tkz.type() == T_COMMA)
					{
						do
						{
							if(!tkz.next() || tkz.type() != T_IDENTIFIER)
								throw unexpected(tkz, source, "generic for, expected name");

							ids.add((String) tkz.value());

							if(tkz.next())
							{
								if(tkz.type() == T_COMMA)
									continue;
								else if(tkz.type() == T_IN)
									break;
							}
							throw unexpected(tkz, source, "generic for, expected 'in'");
						} while(true);
					}
					String[] vars = ids.toArray(new String[0]);
					fst = new LuaGenericForStatement(line, vars, readExpressions(tkz, source, true));
					break;
				default:
					throw unexpected(tkz, source, "for");
				}

				if(!tkz.next() || tkz.type() != T_DO)
					throw unexpected(tkz, source, "for, expected 'do'");

				fst.body = new LuaBody(readStatements(tkz, source, flags | F_FOR), source, null);

				if(!tkz.next() || tkz.type() != T_END)
					throw unexpected(tkz, source, "for, expected 'end'");

				res = true;
				lst.add(fst);
				break;
			}
			else
				throw unexpected(tkz, source, "for, expected name");
		case T_DO:
			block = readBlock(tkz, source, flags | F_DO);
			if(tkz.next() && tkz.type() == T_END)
			{
				res = true;
				lst.add(new LuaDoStatement(line, block));
				break;
			}
			else
				throw unexpected(tkz, source, "do block, expected 'end'");
		case T_GOTO:
		case T_DOUBLECOLON:
			throw new LuaException("goto statements and labels are not supported in this Lua interpreter.");
		case T_SEMIC:
			res = true;
			break;
		case T_BREAK:
			if((flags & F_LOOP) != 0)
			{
				lst.add(new LuaBreakStatement(line));
				break;
			}
			else
				return false;
		case T_RETURN:
			lst.add(new LuaReturnStatement(line, readExpressions(tkz, source, false)));
			break;
		case T_LOCAL:
			if(tkz.next())
			{
				switch(tkz.type())
				{
				case T_IDENTIFIER:
					List<LuaVariable> vars = new ArrayList<>();
					do
					{
						vars.add(new LuaVariable(source, tkz.line(), (String) tkz.value(), true));

						if(!tkz.next() || tkz.type() != T_COMMA)
							break;

						tkz.next();
					} while(true);

					if(tkz.type() == T_EQUALS)
					{
						LuaExpressions exps = readExpressions(tkz, source, true);
						res = true;
						lst.add(new LuaAssignStatement(line, vars.toArray(new LuaVariable[0]), exps));	
					}
					else
					{
						tkz.prev();
						res = true;
						lst.add(new LuaAssignStatement(line, vars.toArray(new LuaVariable[0]), null));	
					}
					break;
				case T_FUNCTION:
					if(!tkz.next() || tkz.type() != T_IDENTIFIER)
						throw unexpected(tkz, source, "name");

					String name = (String) tkz.value();
					LuaVariable var = new LuaVariable(source, tkz.line(), name, true);

					res = true;
					lst.add(new LuaAssignStatement(line, new LuaVariable[] { var }, new LuaExpressions(readFunction(tkz, source, false, name))));
					break;
				default:
					throw unexpected(tkz, source, "name");
				}
				break;
			}
			else
				throw unexpected(tkz, source, "local variable or function");
		case T_FUNCTION:
			if(tkz.next() && tkz.type() == T_IDENTIFIER)
			{
				LuaLocation fname = readLocation(tkz, source, false);
				LuaLocation last = fname;
				while(last.next != null)
					last = last.next;
				String name;

				LuaFunctionExpression fexp;
				if(tkz.next() && tkz.type() == T_COLON)
				{
					if(!tkz.next() || tkz.type() != T_IDENTIFIER)
						throw unexpected(tkz, source, "name");

					last.next = new LuaField(source, tkz.line(), new LuaString((String) tkz.value()));
					name = ((LuaField) last.next).name;

					fexp = readFunction(tkz, source, true, name);
				}
				else
				{
					if(last instanceof LuaField)
						name = ((LuaField) last).name;
					else
						name = ((LuaVariable) last).variable;

					tkz.prev();
					fexp = readFunction(tkz, source, false, name);
				}
				res = true;
				lst.add(new LuaAssignStatement(line, new LuaLocation[] { fname }, new LuaExpressions(fexp)));
				break;
			}
			else
				throw unexpected(tkz, source, "name");
		case T_OPEN_PTS:
		case T_IDENTIFIER:
			LuaLocation[] locs = readLocations(tkz, source);

			res = true;
			if(locs.length == 1 && locs[0].isCall())
			{				
				lst.add(new LuaCallStatement(line, locs[0]));
				break;
			}
			else if(tkz.next() && tkz.type() == T_EQUALS)
			{
				lst.add(new LuaAssignStatement(line, locs, readExpressions(tkz, source, true)));
				break;
			}
			else
				throw unexpected(tkz, source, "statement");
		default:
			return false;
		}

		if(tkz.next() && tkz.type() != T_SEMIC)
			tkz.prev();

		return res;
	}

	private static LuaLocation[] readLocations(Tokenizer tkz, String source) throws IOException
	{
		List<LuaLocation> lst = new ArrayList<>();
		do
		{
			lst.add(readLocation(tkz, source, true));

			if(tkz.next())
			{
				if(tkz.type() != T_COMMA)
				{
					tkz.prev();
					break;
				}
				else
					tkz.next();
			}
			else
				break;
		} while(true);

		return lst.toArray(new LuaLocation[0]);
	}

	private static LuaExpressions readExpressions(Tokenizer tkz, String source, boolean strict) throws IOException
	{
		List<LuaExpression> lst = new ArrayList<>();
		LuaExpression exp;
		do
		{
			exp = readExpression(tkz, source, strict);
			if(exp == null)
				return null;
			strict = true;
			lst.add(exp);

			if(tkz.next())
			{
				if(tkz.type() != T_COMMA)
				{
					tkz.prev();
					break;
				}
			}
			else
				break;
		} while(true);

		return new LuaExpressions(lst.toArray(new LuaExpression[0]));
	}

	private static LuaLocation readLocation(Tokenizer tkz, String source, boolean wm) throws IOException
	{		
		int line;
		LuaLocation root;

		switch(tkz.type())
		{
		case T_IDENTIFIER:
			root = new LuaVariable(source, tkz.line(), (String) tkz.value(), false);
			break;
		case T_OPEN_PTS:
			line = tkz.line();
			LuaExpression exp;
			if(tkz.next() && tkz.type() == T_CLSE_PTS)
			{
				exp = null;
				tkz.prev();
			}
			else
			{
				tkz.prev();
				exp = readExpression(tkz, source, true);
			}
			root = new LuaPTSValue(line, exp);
			if(tkz.next() && tkz.type() == T_CLSE_PTS)
				break;
			else
				throw unexpected(tkz, source, "value, expected ')'");
		default:
			throw unexpected(tkz, source, "variable");
		}

		LuaLocation curr = root;
		tklbl:
		while(tkz.next())
		{
			switch(tkz.type())
			{
			case T_PERIOD:
				if(tkz.next() && tkz.type() == T_IDENTIFIER)
				{
					curr.next = new LuaField(source, tkz.line(), new LuaString((String) tkz.value()));
					curr = curr.next;
					break;
				}
				else
				{
					tkz.prev();
					break tklbl;
				}
			case T_COLON:
				if(wm)
				{
					if(tkz.next() && tkz.type() == T_IDENTIFIER)
					{
						String name = (String) tkz.value();
						line = tkz.line();
						LuaExpressions exps = null;
						if(!tkz.next())
							throw unexpected(tkz, source, "call, expected '(', string, or table");

						switch(tkz.type())
						{
							case T_OPEN_PTS:
								if (!tkz.next() || tkz.type() != T_CLSE_PTS)
								{
									tkz.prev();
									exps = readExpressions(tkz, source, true);
								}
								else
									tkz.prev();

								if (!tkz.next() || tkz.type() != T_CLSE_PTS)
									throw unexpected(tkz, source, "call, expected ')'");
								break;
							case T_STRING:
								exps = new LuaExpressions(new LuaExpression(source, new LuaString((String) tkz.value())));
								break;
							case T_OPEN_BRC:
								exps = new LuaExpressions(readTable(tkz, source));
								break;
							default:
								throw unexpected(tkz, source, "call, expected '(', string, or table");
						}
						curr.next = new LuaSelfCall(line, name, exps);
						curr = curr.next;
						break;
					}
					else
						throw unexpected(tkz, source, "call, expected name");
				}
			case T_OPEN_BKT:
				if(wm)
				{
					curr.next = new LuaField(source, tkz.line(), readExpression(tkz, source, true));
					curr = curr.next;
					if(tkz.next() && tkz.type() == T_CLSE_BKT)
						break;
					else
						throw unexpected(tkz, source, "field, expected ']'");
				}
			case T_STRING:
				if(wm)
				{
					line = tkz.line();
					LuaExpressions exps = new LuaExpressions(new LuaExpression(source, new LuaString((String) tkz.value())));
					curr.next = new LuaCall(line, exps);
					curr = curr.next;
					break;
				}
			case T_OPEN_BRC:
				if(wm)
				{
					line = tkz.line();
					LuaExpressions exps = new LuaExpressions(readTable(tkz, source));
					curr.next = new LuaCall(line, exps);
					curr = curr.next;
					break;
				}
			case T_OPEN_PTS:
				if(wm)
				{
					line = tkz.line();
					LuaExpressions exps;
					if(tkz.next() && tkz.type() == T_CLSE_PTS)
					{
						exps = null;
						tkz.prev();
					}
					else
					{
						tkz.prev();
						exps = readExpressions(tkz, source, true);
					}
					curr.next = new LuaCall(line, exps);
					curr = curr.next;
					if(tkz.next() && tkz.type() == T_CLSE_PTS)
						break;
					else
						throw unexpected(tkz, source, "call, expected ')'");
				}
			default:
				tkz.prev();
				break tklbl;
			}
		}
		return root;
	}

	private static LuaExpression readExpression(Tokenizer tkz, String source, boolean strict) throws IOException
	{
		Stack<Integer> ops = new Stack<>();
		List<Object> res = new ArrayList<>();
		boolean hasValue = false, ra;
		int type, line;

		tklbl:
		while(tkz.next())
		{
			ra = false;
			type = tkz.type();
			if(hasValue)
			{
				hasValue = false;
				switch(type)
				{
				case T_CONCAT:
				case T_POW:
					ra = true;
				case T_OR:
				case T_AND:
				case T_GREQ_THAN:
				case T_LSEQ_THAN:
				case T_GRTR_THAN:
				case T_LESS_THAN:
				case T_NEQUALS:
				case T_EEQUALS:
				case T_MINUS:
				case T_BAND:
				case T_BOR:
				case T_BXOR:
				case T_SHL:
				case T_SHR:
				case T_PLUS:
				case T_TIMES:
				case T_DIVIDE:
				case T_FLR_DIVIDE:
				case T_MODULO:
					while(!ops.isEmpty() && (Tokenizer.prec(type) < Tokenizer.prec(ops.peek()) || !ra && Tokenizer.prec(type) == Tokenizer.prec(ops.peek())))
					{
						res.add(ops.pop());
						res.add(ops.pop());
					}

					ops.push(tkz.line());
					ops.push(type);
					break;
				default:
					tkz.prev();
					break tklbl;
				}
			}
			else
			{
				hasValue = true;
				switch(type)
				{
				case T_MINUS:
					type = T_NEGATE;
				case T_BXOR:
					if(type == T_BXOR)
						type = T_UBNOT;
				case T_POUND:
				case T_NOT:
					while(!ops.isEmpty())
					{
						int n = ops.peek();
						ra = n == T_CONCAT || n == T_POW || n == T_NEGATE || n == T_UBNOT || n == T_POUND || n == T_NOT;
						if(Tokenizer.prec(type) < Tokenizer.prec(n) || !ra && Tokenizer.prec(type) == Tokenizer.prec(n))
						{
							res.add(ops.pop());
							res.add(ops.pop());
						}
						else
							break;
					}

					ops.push(tkz.line());
					ops.push(type);
					hasValue = false;
					break;
				case T_BOOLEAN:
					res.add(LuaBoolean.valueOf((Boolean) tkz.value()));
					res.add(tkz.line());
					break;
				case T_NIL:
					res.add(LuaNil.NIL);
					res.add(tkz.line());
					break;
				case T_STRING:
					res.add(new LuaString((String) tkz.value()));
					res.add(tkz.line());
					break;
				case T_NUMBER:
					if(tkz.value() instanceof Long)
						res.add(LuaInteger.valueOf((long) tkz.value()));
					else
						res.add(new LuaFloat((double) tkz.value()));
					res.add(tkz.line());
					break;
				case T_OPEN_BRC:
					line = tkz.line();
					res.add(readTable(tkz, source));
					res.add(line);
					break;
				case T_OPEN_PTS:
				case T_IDENTIFIER:
					line = tkz.line();
					res.add(readLocation(tkz, source, true));
					res.add(line);
					break;
				case T_FUNCTION:
					line = tkz.line();
					res.add(readFunction(tkz, source, false, "<" + source + ":" + line + ">"));
					res.add(line);
					break;
				case T_VARARGS:
					res.add(T_VARARGS);
					res.add(tkz.line());
					break;
				default:
					if(res.isEmpty())
					{
						if(strict)
							throw unexpected(tkz, source);
						else
						{
							tkz.prev();
							return null;
						}
					}
					else
					{
						tkz.prev();
						break tklbl;
					}
				}
			}
		}

		if(res.size() == 0)
		{
			if(strict)
				throw unexpected(tkz, source, "expression");
			else
				return null;
		}

		while(!ops.isEmpty())
			res.add(ops.pop());

		Object[] arr = res.toArray();
		if(arr.length == 1 && arr[0] instanceof LuaExpression)
			return (LuaExpression) arr[0];
		else
			return new LuaExpression(source).collect(arr);
	}

	private static LuaFunctionExpression readFunction(Tokenizer tkz, String source, boolean self, String name) throws IOException
	{
		if(tkz.next() && tkz.type() == T_OPEN_PTS)
		{
			tkz.next();
			List<String> args = new ArrayList<>();
			if(self)
				args.add("self");

			if(tkz.type() != T_CLSE_PTS)
			{
				do
				{
					if(tkz.type() == T_VARARGS)
					{
						args.add(null);
						tkz.next();
						break;
					}

					if(tkz.type() != T_IDENTIFIER)
						throw unexpected(tkz, source, "function, expected name");

					args.add((String) tkz.value());

					if(!tkz.next() || tkz.type() != T_COMMA)
						break;

					tkz.next();
				} while(true);

				if(tkz.type() != T_CLSE_PTS)
					throw unexpected(tkz, source, "function, expected ')'");
			}

			LuaStatement[] sts = readStatements(tkz, source, F_FUNCTION);
			if(tkz.next() && tkz.type() == T_END)
				return new LuaFunctionExpression(source, args.toArray(new String[0]), new LuaBody(sts, source, name));
			else
				throw unexpected(tkz, source);
		}
		else
			throw unexpected(tkz, source, "function, expected '('");
	}

	private static LuaTableExpression readTable(Tokenizer tkz, String source) throws IOException
	{
		if(tkz.type() != T_OPEN_BRC)
			throw unexpected(tkz, source, "table, expected '{'");

		List<Object> res = new ArrayList<>();

		tklbl:
		while(tkz.next())
		{
			switch(tkz.type())
			{
			case T_CLSE_BRC:
				tkz.prev();
				break tklbl;
			case T_OPEN_BKT:
				res.add(readExpression(tkz, source, true));
				if(tkz.next() && tkz.type() == T_CLSE_BKT)
				{
					if(tkz.next() && tkz.type() == T_EQUALS)
						res.add(readExpression(tkz, source, true));
					else
						throw unexpected(tkz, source, "table field, expected '='");
				}
				else
					throw unexpected(tkz, source, "table field, expected ']'");
				break;
			case T_IDENTIFIER:
				String name = (String) tkz.value();
				if(tkz.next() && tkz.type() == T_EQUALS)
				{
					res.add(new LuaString(name));
					res.add(readExpression(tkz, source, true));
					break;
				}
				else
					tkz.prev();
			default:
				tkz.prev();
				res.add(null);
				res.add(readExpression(tkz, source, true));
				break;
			}

			if(tkz.next())
			{
				switch(tkz.type())
				{
				case T_COMMA:
				case T_SEMIC:
					break;
				default:
					tkz.prev();
					break tklbl;
				}
			}
		}

		if(tkz.next() && tkz.type() == T_CLSE_BRC)
			return new LuaTableExpression(source).collect(res.toArray());

		throw unexpected(tkz, source);
	}

	static LuaException unexpected(Tokenizer tkz, String source)
	{
		return unexpected(tkz, source, null);
	}

	static LuaException unexpected(Tokenizer tkz, String source, String section)
	{
		String s = "unexpected ";
		if(tkz.token() == null)
			s += "<eof>";
		else
			s += "symbol '" + tkz.token() + "'";
		if(section != null)
			s += " reading " + section;

		return new LuaException(source, tkz.line(), s);
	}
}