package com.hk.lua;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.BiConsumer;

import com.hk.lua.Lua.LuaMethod;
import com.hk.math.Rand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>LuaLibraryOS class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryOS implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	clock() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			final long nanos = System.nanoTime();
			String name = toString();
			if(name != null && !name.trim().isEmpty())
			{
				table.rawSet(new LuaString(name), new LuaFunction() {
					@Override
					LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args) {
						return new LuaFloat((System.nanoTime() - nanos) / 1E9D);
					}
				});
			}
		}
	},
	date() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
			return Lua.newString(fmt.format(new Date()));
		}
	},
	difftime() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.INTEGER, LuaType.INTEGER);
			return args[0].doSub(interp, args[1]);
		}
	},
	execute() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			String cmd = args[0].getString();
			try
			{
				Process proc = Runtime.getRuntime().exec(cmd);
				return new LuaArgs(LuaBoolean.TRUE, LuaInteger.valueOf(proc.waitFor()));
			}
			catch (IOException | InterruptedException e)
			{
				return Lua.wrapErr(e);
			}
		}
	},
	exit() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			int status = 0;
			if(args.length >= 1)
			{
				if(args[0].isNumber())
					status = args[0].getInt();
				else if(args[0].isBoolean())
					status = args[0].getBoolean() ? 0 : -1;
			}
			throw new LuaException.LuaExitException(status);
		}
	},
	getenv() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			String str = System.getenv(args[0].getString());
			if(str == null)
				return LuaNil.NIL;
			else
				return new LuaString(str);
		}
	},
	getprop() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			String str = System.getProperty(args[0].getString());
			if(str == null)
				return LuaNil.NIL;
			else
				return new LuaString(str);
		}
	},
	millis() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			return LuaInteger.valueOf(System.currentTimeMillis());
		}
	},
	nanos() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			return LuaInteger.valueOf(System.nanoTime());
		}
	},
	remove() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			String fileName = args[0].getString();

			try
			{
				Files.delete(Paths.get(fileName));
				return LuaBoolean.TRUE;
			}
			catch (IOException e)
			{
				return Lua.wrapErr(e);
			}
		}
	},
	rename() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING, LuaType.STRING);
			String oldName = args[0].getString();
			String newName = args[1].getString();

			try
			{
				Files.move(Paths.get(oldName), Paths.get(newName));
				return LuaBoolean.TRUE;
			}
			catch (IOException e)
			{
				return Lua.wrapErr(e);
			}
		}
	},
	setlocale() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new UnsupportedOperationException();
		}
	},
	time() {
		@SuppressWarnings("MagicConstant")
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			long time;
			if(args.length >= 1)
			{
				Lua.checkArgs(toString(), args, LuaType.TABLE);
				LuaObject tbl = args[0];
				GregorianCalendar cal = new GregorianCalendar();
				LuaObject day = tbl.rawGet(new LuaString("day"));
				LuaObject month = tbl.rawGet(new LuaString("month"));
				LuaObject year = tbl.rawGet(new LuaString("year"));
				if(!day.isInteger())
					throw new LuaException("field 'day' is not an integer");
				if(!month.isInteger())
					throw new LuaException("field 'month' is not an integer");
				if(!year.isInteger())
					throw new LuaException("field 'year' is not an integer");

				cal.set(year.getInt(), month.getInt() - 1, day.getInt());
				time = cal.getTimeInMillis();

				LuaObject ex = tbl.rawGet(new LuaString("hour"));
				if(ex.isInteger())
					time += ex.getLong() * 60 * 60 * 1000;

				ex = tbl.rawGet(new LuaString("min"));
				if(ex.isInteger())
					time += ex.getLong() * 60 * 1000;

				ex = tbl.rawGet(new LuaString("sec"));				
				if(ex.isInteger())
					time += ex.getLong() * 1000;
			}
			else
				time = System.currentTimeMillis();

			return LuaInteger.valueOf(time / 1000L);
		}
	},
	tmpname() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			return new LuaString(System.getProperty("java.io.tmpdir") + "luajvmtmp_" + Math.abs(Rand.nextInt()) + System.currentTimeMillis() + ".tmp");
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
}