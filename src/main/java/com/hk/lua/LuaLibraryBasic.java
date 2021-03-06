package com.hk.lua;

import com.hk.lua.Lua.LuaMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * <p>LuaLibraryBasic class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryBasic implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	_assert() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.ANY);
			if(!args[0].getBoolean())
			{
				if(args.length > 1 && args[1].isString())
					throw new LuaException(args[1].getString());
				else
					throw new LuaException("assertion failed!");
			}

			return new LuaArgs(args);
		}

		@Override
		public String toString() { return "assert"; }
	},
	collectgarbage() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			System.gc();
			return LuaNil.NIL;
//			throw new Error();
		}
	},
	dofile() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			try
			{
				File file = new File(args[0].getString());
				Object res = LuaInterpreter.readLua(new FileReader(file), file.getPath(), interp.global, true).execute(interp);
				return res == null ? LuaNil.NIL : (LuaObject) res;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new LuaException("IOException: " + e.getLocalizedMessage());
			}
		}
	},
	error() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			throw new LuaException(args[0].getString());
		}
	},
	getmetatable() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			return args.length == 1 ? args[0].getMetatable() : LuaNil.NIL;
		}
	},
	ipairs() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.TABLE);
			final LuaTable tbl = (LuaTable) args[0];
			LuaFunction met = new LuaFunction() {
				long count = 1;

				@Override
				LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
				{
					LuaInteger i = LuaInteger.valueOf(count++);
					LuaObject next = tbl.getIndex(interp, i);
					if(next.getBoolean())
						return new LuaArgs(i, next);
					else
						return LuaNil.NIL;
				}
			};
			return new LuaArgs(met, args[0], LuaInteger.ZERO);
		}
	},
	load() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			try
			{
				final LuaChunk chunk;
				try
				{
					chunk = LuaInterpreter.readLua(new StringReader(args[0].getString()), Lua.STDIN, interp.global, true);
				}
				catch (LuaException ex)
				{
					return Lua.wrapErr(ex);
				}

				return new LuaFunction() {
					@Override
					LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
					{
						Object res = chunk.execute(interp, (Object[]) args);
						if(res instanceof LuaObject[])
							return new LuaArgs((LuaObject[]) res);
						else
							return res == null ? LuaNil.NIL : (LuaObject) res;
					}
				};
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new LuaException("IOException: " + e.getLocalizedMessage());
			}
		}
	},
	loadfile() {
		private LuaObject apply(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			try
			{
				File file = new File(args[0].getString());
				final LuaChunk chunk = LuaInterpreter.readLua(new FileReader(file), file.getPath(), interp.global, true);

				return new LuaFunction()
				{
					@Override
					LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
					{
						Object res = chunk.execute(interp, (Object[]) args);
						if(res instanceof LuaObject[])
							return new LuaArgs((LuaObject[]) res);
						else
							return res == null ? LuaNil.NIL : (LuaObject) res;
					}
				};
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new LuaException("IOException: " + e.getLocalizedMessage());
			}
		}

		@Override
		public void accept(final Environment env, LuaObject table)
		{
			String name = toString();
			if(name != null && !name.trim().isEmpty())
			{
				table.rawSet(new LuaString(name), Lua.newMethod((interp, args) -> apply(env.interp, args)));
			}
		}
	},
	next() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.TABLE);
			LuaTable tbl = (LuaTable) args[0];
			LuaObject arg2 = args.length > 1 ? args[1] : LuaNil.NIL;
			if(!tbl.map.isEmpty())
			{
				Set<Map.Entry<LuaObject, LuaObject>> set = tbl.map.entrySet();
				Map.Entry<LuaObject, LuaObject> ent;
				if(arg2.isNil())
				{
					ent = set.iterator().next();
					return new LuaArgs(ent.getKey(), ent.getValue());
				}
				else
				{
					Iterator<Map.Entry<LuaObject, LuaObject>> itr = set.iterator();
					while(itr.hasNext())
					{
						ent = itr.next();

						if(!ent.getKey().doEQ(interp, arg2).getBoolean())
							continue;

						if(itr.hasNext())
						{							
							ent = itr.next();
							return new LuaArgs(ent.getKey(), ent.getValue());
						}
						else
							break;
					}
				}
			}
			return LuaNil.NIL;
		}
	},
	pairs() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.TABLE);
			LuaTable tbl = (LuaTable) args[0];
			LuaFunction met;
			if(tbl.map.isEmpty())
			{
				met = new LuaFunction() {
					@Override
					LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
					{
						return LuaNil.NIL;
					}
				};
			}
			else
			{
				final Iterator<Map.Entry<LuaObject, LuaObject>> itr = tbl.map.entrySet().iterator();
				met = new LuaFunction() {
					@Override
					LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
					{
						if(itr.hasNext())
						{
							Map.Entry<LuaObject, LuaObject> ent = itr.next();
							return new LuaArgs(ent.getKey(), ent.getValue());
						}
						else
							return LuaNil.NIL;
					}
				};
			}
			return new LuaArgs(met, args[0], LuaNil.NIL);
		}
	},
	pcall() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.FUNCTION);

			LuaObject[] tmp = new LuaObject[args.length - 1];
			System.arraycopy(args, 1, tmp, 0, tmp.length);
			Environment tmpEnv = interp.env;
			try
			{
				LuaObject res = args[0].doCall(interp, tmp);
				if(res instanceof LuaArgs)
				{
					tmp = ((LuaArgs) res).objs;
					LuaObject[] arr = new LuaObject[tmp.length + 1];
					arr[0] = LuaBoolean.TRUE;
					System.arraycopy(tmp, 0, arr, 1, tmp.length);
					return new LuaArgs(arr);
				}
				else
					return new LuaArgs(LuaBoolean.TRUE, res);
			}
			catch(LuaException e)
			{
				return Lua.wrapErr(e);
			}
			finally
			{
				interp.env = tmpEnv;
			}
		}
	},
	print() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			PrintStream out = interp.getExtra(EXKEY_OUTPUT , PrintStream.class, System.out);

			for(int i = 0; i < args.length; i++)
			{
				out.print(args[i].getString(interp));

				if(i < args.length - 1)
					out.print('\t');
			}
			out.println();
			return LuaNil.NIL;
		}
	},
	rawequal() {
		@Override
		public LuaBoolean call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY, LuaType.ANY);

			return LuaBoolean.valueOf(args[0].rawEqual(args[1]));
		}
	},
	rawget() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY, LuaType.ANY);
			if(args[0].isTable() || args[0].isUserdata())
				return args[0].rawGet(args[1]);
			else
				throw Lua.badArgument(0, name(), "expected table or userdata");
		}
	},
	rawlen() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			return args[0].rawLen();
		}
	},
	rawset() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY, LuaType.ANY, LuaType.ANY);

			if(args[0].isTable() || args[0].isUserdata())
				args[0].rawSet(args[1], args[2]);
			else
				throw Lua.badArgument(0, name(), "expected table or userdata");

			return args[0];
		}
	},
	select() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			if(args[0].isNumber())
			{
				int l = args.length - 1;
				int index = args[0].getInt();
				while(index <= 0)
					index += args.length;

				if(index > l)
					return LuaNil.NIL;

				index--;
				LuaObject[] arr = new LuaObject[l - index];
				for(int i = 0; i < arr.length; i++)
					arr[i] = args[1 + i + index];

				return new LuaArgs(arr);
			}
			else if(args[0].isString() && "#".equals(args[0].getString()))
			{
				return LuaInteger.valueOf(args.length - 1);
			}
			throw new LuaException("expected number or '#' to select(...)");
//			throw new Error();
		}
	},
	setmetatable() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			if(args.length == 2)
			{
				if(!args[0].isTable())
					throw Lua.badArgument(0, name(), "table expected", "got " + args[0].name());
				if(!args[1].isTable() && !args[1].isNil())
					throw Lua.badArgument(1, name(), "nil or table expected");
				args[0].setMetatable(args[1]);
				return args[0];
			}
			throw new Error();
		}
	},
	tonumber() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			LuaObject num = args[0];
			int base = args.length > 1 ? args[1].getInt() : 10;

			if(base < 2 || base > 36)
				throw Lua.badArgument(1, name(), "base out of range [2-36]");

			if(base == 10)
			{
				if(num.isString())
				{
					Number n = LuaString.getNumber(num.getString());
					if(n instanceof Long)
						return LuaInteger.valueOf(n.longValue());
					else if(n instanceof Double)
						return new LuaFloat(n.doubleValue());
				}
				else if(num.isNumber())
					return num;
			}
			else
			{
				if(!num.isString())
					throw Lua.badArgument(0, name(), "expected string with base");

				return LuaInteger.valueOf(Long.parseLong(num.getString(), base));
			}
			return LuaNil.NIL;
		}
	},
	tostring() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			return new LuaString(args[0].getString(interp));
		}
	},
	type() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			switch(args[0].type())
			{
			case FLOAT:
			case INTEGER:
				return new LuaString("number");
			default:
				return new LuaString(args[0].name());
			}
		}
	},
	_VERSION() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			table.rawSet(new LuaString("_VERSION"), new LuaString("Lua 5.3 (JVM)"));
		}
	},
	xpcall() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.FUNCTION, LuaType.FUNCTION);

			LuaObject[] tmp = new LuaObject[args.length - 1];
			System.arraycopy(args, 1, tmp, 0, tmp.length);
			tmp[0] = args[0];
			LuaArgs res = (LuaArgs) pcall.call(interp, tmp);

			if(!res.objs[0].getBoolean())
				return new LuaArgs(LuaBoolean.FALSE, args[1].call(interp, res.objs[1]));
			else
				return res;
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
			table.rawSet(new LuaString(name), Lua.newMethod(this));
	}

	/** Constant <code>EXKEY_OUTPUT="system.out"</code> */
	public static final String EXKEY_OUTPUT = "system.out";
}