package com.hk.lua;

import java.util.Arrays;

class LuaBlock implements Tokens
{
	final String source;
	protected final LuaStatement[] sts;

	LuaBlock(LuaStatement[] sts, String source)
	{
		this.sts = sts;
		this.source = source;
	}

	Object execute(LuaInterpreter interp)
	{
		Environment env = interp.env;
		interp.env = new Environment(interp, interp.env, false);
		Object res = run(interp);
		interp.env = env;
		return res;
	}

	/**
	 * <p>run.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @return a {@link java.lang.Object} object
	 */
	protected Object run(LuaInterpreter interp)
	{
		Object res = null;
		LuaExpressions exps;
		String[] names;
		LuaObject[] arr;
		stlbl:
		for (LuaStatement st : sts)
		{
			try
			{
				switch (st.code())
				{
					case T_DO:
						res = st.getBlock().execute(interp);
						if (res != null)
							break stlbl;
						else
							break;
					case T_NUMERIC_FOR:
						LuaObject index = st.getValue1().evaluate(interp);
						LuaObject end = st.getValue2().evaluate(interp);
						LuaObject step = st.getValue3().evaluate(interp);
						boolean neg = step.getLong() < 0;
						names = new String[]{st.getIndex()};
						arr = new LuaObject[1];
						index = index.doSub(interp, step);
						while (true)
						{
							index = index.doAdd(interp, step);

							if (neg && index.doLT(interp, end).getBoolean())
								break;
							if (!neg && end.doLT(interp, index).getBoolean())
								break;

							arr[0] = index;
							res = st.getBody().execute(interp, interp.env, names, arr);
							if (res instanceof Integer && (int) res == T_BREAK)
							{
								res = null;
								break;
							}
							if (res != null)
								break stlbl;
						}
						break;
					case T_GENERIC_FOR:
						names = st.getVars();
						arr = st.getExpressions().collect(interp);
						LuaObject func = arr.length < 1 ? LuaNil.NIL : arr[0];
						LuaObject state = arr.length < 2 ? LuaNil.NIL : arr[1];
						LuaObject var = arr.length < 3 ? LuaNil.NIL : arr[2];
						while (true)
						{
							LuaObject result = func.doCall(interp, new LuaObject[] { state, var });
							if (result instanceof LuaArgs)
								arr = ((LuaArgs) result).objs;
							else
								arr = new LuaObject[]{result};

							if (arr.length == 0 || arr[0].isNil())
								break;
							var = arr[0];
							res = st.getBody().execute(interp, interp.env, names, arr);

							if (res instanceof Integer && (int) res == T_BREAK)
							{
								res = null;
								break;
							}
							if (res != null)
								break stlbl;
						}
						break;
					case T_WHILE:
						while (st.getExpression().evaluate(interp).getBoolean())
						{
							res = st.getBlock().execute(interp);
							if (res instanceof Integer && (int) res == T_BREAK)
							{
								res = null;
								break;
							}
							if (res != null)
								break stlbl;
						}
						break;
					case T_REPEAT:
						do
						{
							res = st.getBlock().execute(interp);
							if (res instanceof Integer && (int) res == T_BREAK)
							{
								res = null;
								break;
							}
							if (res != null)
								break stlbl;
						} while (!st.getExpression().evaluate(interp).getBoolean());
						break;
					case T_IF:
						while (true)
						{
							if (st.isElseStatement() || st.getExpression().evaluate(interp).getBoolean())
							{
								res = st.getBlock().execute(interp);
								if (res != null)
									break stlbl;
								else
									break;
							}

							if (!st.hasNextElseStatement())
								break;
							st = st.nextElseStatement();
						}
						break;
					case T_IDENTIFIER:
						LuaLocation[] vars = st.getLocations();
						exps = st.getExpressions();
						LuaObject[] values;
						if (exps == null)
						{
							values = new LuaObject[vars.length];
							Arrays.fill(values, LuaNil.NIL);
						}
						else
							values = exps.collect(interp);

						for (int j = 0; j < vars.length; j++)
							vars[j].give(interp, null, j < values.length ? values[j] : LuaNil.NIL);
						break;
					case T_BREAK:
						res = T_BREAK;
						break stlbl;
					case T_RETURN:
						exps = st.getExpressions();
						values = exps == null ? null : exps.collect(interp);
						if (values == null)
							return LuaNil.NIL;
						else if (values.length == 1)
							res = values[0];
						else
							res = values;
						break stlbl;
					case T_FUNCTION:
						st.getLocation().evaluate(interp);
						break;
					default:
						throw new Error();
				}
			}
			catch (LuaException e)
			{
//				System.out.println(e.getLocalizedMessage() + ", (" + st.line + "), " + e.internal + ", " + e.stacktrace);
				throw exception(st, e);
			}
		}
		return res;
	}

	/**
	 * <p>exception.</p>
	 *
	 * @param st a {@link com.hk.lua.LuaStatement} object
	 * @param e a {@link com.hk.lua.LuaException} object
	 * @return a {@link com.hk.lua.LuaException} object
	 */
	@SuppressWarnings("ThrowableNotThrown")
	protected LuaException exception(LuaStatement st, LuaException e)
	{
		if(!e.internal)
		{
			e.internal();
			new LuaException(source, st.line, "in main chunk", e);
		}

		return e;
	}
}