package com.hk.lua;

import java.util.Arrays;

class LuaBlock implements Tokens
{
	protected final LuaInterpreter interp;
	private final String source;
	protected final LuaStatement[] sts;
	
	LuaBlock(LuaInterpreter interp, LuaStatement[] sts)
	{
		this.interp = interp;
		this.sts = sts;
		
		source = interp.currSource;
	}

	Object execute()
	{
		Environment env = interp.env;
		interp.env = new Environment(interp, interp.env, false);
		Object res = run();
		interp.env = env;
		return res;
	}
	
	protected Object run()
	{
		Object res = null;
		LuaStatement st;
		LuaExpressions exps;
		String[] names;
		LuaObject[] arr;
		stlbl:
		for(int i = 0; i < sts.length; i++)
		{
			st = sts[i];
			try
			{
				switch(st.code())
				{
				case T_DO:
					res = st.getBlock().execute();
					if(res != null)
						break stlbl;
					else
						break;
				case T_NUMERIC_FOR:
					LuaObject index = st.getValue1().evaluate();
					LuaObject end = st.getValue2().evaluate();
					LuaObject step = st.getValue3().evaluate();
					boolean neg = step.getInteger() < 0;
					names = new String[] { st.getIndex() };
					arr = new LuaObject[1];
					index = index.doSub(step);
					while(true)
					{
						index = index.doAdd(step);

						if(neg && index.doLT(end).getBoolean())
							break;
						if(!neg && end.doLT(index).getBoolean())
							break;
						
						arr[0] = index;
						res = st.getBody().execute(interp.env, names, arr);
						if(res instanceof Integer && (int) res == T_BREAK)
						{
							res = null;
							break;
						}
						if(res != null)
							break stlbl;
					}
					break;
				case T_GENERIC_FOR:
					names = st.getVars();
					arr = st.getExpressions().collect();
					LuaObject func = arr.length < 1 ? LuaNil.NIL : arr[0];
					LuaObject state = arr.length < 2 ? LuaNil.NIL : arr[1];
					LuaObject var = arr.length < 3 ? LuaNil.NIL : arr[2];
					while(true)
					{
						LuaObject result = func.doCall(new LuaObject[] { state, var });
						if(result instanceof LuaArgs)
							arr = ((LuaArgs) result).objs;
						else
							arr = new LuaObject[] { (LuaObject) result };
						
						if(arr.length == 0 || arr[0].isNil())
							break;
						var = arr[0];
						res = st.getBody().execute(interp.env, names, arr);
						
						if(res instanceof Integer && (int) res == T_BREAK)
						{
							res = null;
							break;
						}
						if(res != null)
							break stlbl;
					}
					break;
				case T_WHILE:
					while(st.getExpression().evaluate().getBoolean())
					{
						res = st.getBlock().execute();
						if(res instanceof Integer && (int) res == T_BREAK)
						{
							res = null;
							break;
						}
						if(res != null)
							break stlbl;
					}
					break;
				case T_REPEAT:
					do
					{
						res = st.getBlock().execute();
						if(res instanceof Integer && (int) res == T_BREAK)
						{
							res = null;
							break;
						}
						if(res != null)
							break stlbl;
					} while(!st.getExpression().evaluate().getBoolean());
					break;
				case T_IF:
					while(true)
					{		
						if(st.isElseStatement() || st.getExpression().evaluate().getBoolean())
						{
							res = st.getBlock().execute();
							if(res != null)
								break stlbl;
							else
								break;
						}
						
						if(!st.hasNextElseStatement())
							break;
						st = st.nextElseStatement();
					}
					break;
				case T_IDENTIFIER:
					LuaLocation[] vars = st.getLocations();
					exps = st.getExpressions();
					LuaObject[] values;
					if(exps == null)
					{
						values = new LuaObject[vars.length];
						Arrays.fill(values, LuaNil.NIL);
					}
					else
						values = exps.collect();
					
					for(int j = 0; j < Math.min(vars.length, values.length); j++)
						vars[j].give(null,  values[j]);
					break;
				case T_BREAK:
					res = T_BREAK;
					break stlbl;
				case T_RETURN:
					exps = st.getExpressions();
					values = exps == null ? null : exps.collect();
					if(values == null)
						return LuaNil.NIL;
					else if(values.length == 1)
						res = values[0];
					else
						res = values;
					break stlbl;
				case T_FUNCTION:
					st.getLocation().evaluate();
					break;
				default:
					throw new Error();
				}
			}
			catch(LuaException e)
			{
//				System.out.println(e.getLocalizedMessage() + ", (" + st.line + "), " + e.internal + ", " + e.stacktrace);
				throw exception(st, e);
			}
		}
		return res;
	}

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
