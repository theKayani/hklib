package com.hk.lua;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.function.BiConsumer;

import com.hk.lua.Lua.LuaMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>LuaLibraryDate class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryDate implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	parse() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			try
			{
				DateFormat df;
				if(args.length > 1)
				{
					Lua.checkArgs(toString(), args, LuaType.STRING, LuaType.STRING);
					df = new SimpleDateFormat(args[1].getString());
					if(args.length >= 3)
					{
						if(args[2].isString())
							df.setTimeZone(TimeZone.getTimeZone(args[2].getString()));
						else
							throw Lua.badArgument(2, name(), "string expected");
					}
				}
				else
				{
					Lua.checkArgs(toString(), args, LuaType.STRING);
					df = interp.getExtra(EXKEY_DATE_FORMAT, DateFormat.class, defaultFormat);
				}
				return new LuaDateUserdata(df.parse(args[0].getString()));
			}
			catch (ParseException e)
			{
				return Lua.wrapErr(e);
			}
		}
	},
	fromtime() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.INTEGER);
			return new LuaDateUserdata(new Date(args[0].getLong()));
		}
	},
	now() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			return new LuaDateUserdata(new Date());
		}
	},
	setformat() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			String tz = null;
			SimpleDateFormat df;
			if(args.length > 2)
			{
				Lua.checkArgs(toString(), args, LuaType.STRING, LuaType.STRING);
				tz = args[1].getString();
			}
			else
				Lua.checkArgs(toString(), args, LuaType.STRING);

			df = new SimpleDateFormat(args[0].getString());

			if(tz != null)
				df.setTimeZone(TimeZone.getTimeZone(tz));

			interp.setExtra(EXKEY_DATE_FORMAT, df);
			return LuaNil.NIL;
		}
	},
	getformat() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			DateFormat df = interp.getExtra(EXKEY_DATE_FORMAT, DateFormat.class);
			if(df instanceof SimpleDateFormat)
				return new LuaString(((SimpleDateFormat) df).toPattern());
			else
				return LuaNil.NIL;
		}
	};

	/** {@inheritDoc} */
	@Override
	public void accept(Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), Lua.newMethod(this));
	}

	static final LuaObject dateMetatable = new LuaTable();

	static
	{
		dateMetatable.rawSet("__name", new LuaString("DATE*"));
		dateMetatable.rawSet("__index", dateMetatable);

		dateMetatable.rawSet("year", new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				if(args.length < 1 || !(args[0] instanceof LuaDateUserdata))
					throw Lua.badArgument(0, "year", "DATE* expected");
				else
				{
					GregorianCalendar cal = ((LuaDateUserdata) args[0]).calendar;
					if(args.length > 1)
					{
						if(!args[1].isInteger())
							throw Lua.badArgument(1, "year", "integer expected");
						try
						{
							cal.set(Calendar.YEAR, args[1].getInt());
						}
						catch(ArrayIndexOutOfBoundsException ex)
						{
							throw new LuaException(ex.getLocalizedMessage());
						}
						return args[0];
					}
					return LuaInteger.valueOf(cal.get(Calendar.YEAR));
				}
			}
		});
		dateMetatable.rawSet("month", new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				if(args.length < 1 || !(args[0] instanceof LuaDateUserdata))
					throw Lua.badArgument(0, "month", "DATE* expected");
				else
				{
					GregorianCalendar cal = ((LuaDateUserdata) args[0]).calendar;
					if(args.length > 1)
					{
						if(!args[1].isInteger())
							throw Lua.badArgument(1, "month", "integer expected");
						try
						{
							cal.set(Calendar.MONTH, args[1].getInt() - 1);
						}
						catch(ArrayIndexOutOfBoundsException ex)
						{
							throw new LuaException(ex.getLocalizedMessage());
						}
						return args[0];
					}
					return LuaInteger.valueOf(cal.get(Calendar.MONTH) + 1);
				}
			}
		});
		dateMetatable.rawSet("day", new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				if(args.length < 1 || !(args[0] instanceof LuaDateUserdata))
					throw Lua.badArgument(0, "day", "DATE* expected");
				else
				{
					GregorianCalendar cal = ((LuaDateUserdata) args[0]).calendar;
					if(args.length > 1)
					{
						if(!args[1].isInteger())
							throw Lua.badArgument(1, "day", "integer expected");
						try
						{
							cal.set(Calendar.DAY_OF_MONTH, args[1].getInt());
						}
						catch(ArrayIndexOutOfBoundsException ex)
						{
							throw new LuaException(ex.getLocalizedMessage());
						}
						return args[0];
					}
					return LuaInteger.valueOf(cal.get(Calendar.DAY_OF_MONTH));
				}
			}
		});
		dateMetatable.rawSet("hour", new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				if(args.length < 1 || !(args[0] instanceof LuaDateUserdata))
					throw Lua.badArgument(0, "hour", "DATE* expected");
				else
				{
					GregorianCalendar cal = ((LuaDateUserdata) args[0]).calendar;
					if(args.length > 1)
					{
						if(!args[1].isInteger())
							throw Lua.badArgument(1, "hour", "integer expected");
						try
						{
							cal.set(Calendar.HOUR_OF_DAY, args[1].getInt());
						}
						catch(ArrayIndexOutOfBoundsException ex)
						{
							throw new LuaException(ex.getLocalizedMessage());
						}
						return args[0];
					}
					return LuaInteger.valueOf(cal.get(Calendar.HOUR_OF_DAY));
				}
			}
		});
		dateMetatable.rawSet("minute", new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				if(args.length < 1 || !(args[0] instanceof LuaDateUserdata))
					throw Lua.badArgument(0, "minute", "DATE* expected");
				else
				{
					GregorianCalendar cal = ((LuaDateUserdata) args[0]).calendar;
					if(args.length > 1)
					{
						if(!args[1].isInteger())
							throw Lua.badArgument(1, "minute", "integer expected");
						try
						{
							cal.set(Calendar.MINUTE, args[1].getInt());
						}
						catch(ArrayIndexOutOfBoundsException ex)
						{
							throw new LuaException(ex.getLocalizedMessage());
						}
						return args[0];
					}
					return LuaInteger.valueOf(cal.get(Calendar.MINUTE));
				}
			}
		});
		dateMetatable.rawSet("second", new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				if(args.length < 1 || !(args[0] instanceof LuaDateUserdata))
					throw Lua.badArgument(0, "second", "DATE* expected");
				else
				{
					GregorianCalendar cal = ((LuaDateUserdata) args[0]).calendar;
					if(args.length > 1)
					{
						if(!args[1].isInteger())
							throw Lua.badArgument(1, "second", "integer expected");
						try
						{
							cal.set(Calendar.SECOND, args[1].getInt());
						}
						catch(ArrayIndexOutOfBoundsException ex)
						{
							throw new LuaException(ex.getLocalizedMessage());
						}
						return args[0];
					}
					return LuaInteger.valueOf(cal.get(Calendar.SECOND));
				}
			}
		});
		dateMetatable.rawSet("millis", new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				if(args.length < 1 || !(args[0] instanceof LuaDateUserdata))
					throw Lua.badArgument(0, "millis", "DATE* expected");
				else
				{
					GregorianCalendar cal = ((LuaDateUserdata) args[0]).calendar;
					if(args.length > 1)
					{
						if(!args[1].isInteger())
							throw Lua.badArgument(1, "millis", "integer expected");
						try
						{
							cal.set(Calendar.MILLISECOND, args[1].getInt());
						}
						catch(ArrayIndexOutOfBoundsException ex)
						{
							throw new LuaException(ex.getLocalizedMessage());
						}
						return args[0];
					}
					return LuaInteger.valueOf(cal.get(Calendar.MILLISECOND));
				}
			}
		});
		dateMetatable.rawSet("time", new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				if(args.length < 1 || !(args[0] instanceof LuaDateUserdata))
					throw Lua.badArgument(0, "time", "DATE* expected");
				else
					return LuaInteger.valueOf(((LuaDateUserdata) args[0]).calendar.getTimeInMillis());
			}
		});
		dateMetatable.rawSet("format", new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				Lua.checkArgs(toString(), args, LuaType.USERDATA, LuaType.STRING);
				if(args.length < 1 || !(args[0] instanceof LuaDateUserdata))
					throw Lua.badArgument(0, "format", "DATE* expected");
				else
				{
					DateFormat df = new SimpleDateFormat(args[1].getString());
					if(args.length >= 3)
					{
						if(args[2].isString())
							df.setTimeZone(TimeZone.getTimeZone(args[2].getString()));
						else
							throw Lua.badArgument(2, "format", "string expected");
					}
					return new LuaString(df.format(((LuaDateUserdata) args[0]).calendar.getTime()));
				}
			}
		});
		dateMetatable.rawSet("clone", new LuaFunction() {
			@Override
			LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
			{
				Lua.checkArgs(toString(), args, LuaType.USERDATA);
				if(args.length < 1 || !(args[0] instanceof LuaDateUserdata))
					throw Lua.badArgument(0, "clone", "DATE* expected");
				else
					return new LuaDateUserdata(((LuaDateUserdata) args[0]).calendar.getTime());
			}
		});
	}

	public static class LuaDateUserdata extends LuaUserdata
	{
		private final GregorianCalendar calendar;

		public LuaDateUserdata(Date date)
		{			
			calendar = new GregorianCalendar();
			calendar.setTime(date);

			metatable = dateMetatable;
		}

		@Override
		public boolean rawEqual(@NotNull LuaObject o)
		{
			if(o == this)
				return true;
			if(o.getUserdata() instanceof Date)
			{
				Date date = o.getUserdata(Date.class);
				return calendar.getTime().equals(date);
			}
			return false;
		}

		@Override
		public LuaBoolean doEQ(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
		{
			return LuaBoolean.valueOf(rawEqual(o));
		}

		@Override
		public LuaBoolean doLE(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
		{
			if(o instanceof LuaDateUserdata)
			{
				GregorianCalendar cal = ((LuaDateUserdata) o).calendar;
				return LuaBoolean.valueOf(calendar.equals(cal) || calendar.before(cal));
			}
			else
				return super.doLE(interp, o);
		}

		@Override
		public LuaBoolean doLT(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
		{
			if(o instanceof LuaDateUserdata)
				return LuaBoolean.valueOf(calendar.before(((LuaDateUserdata) o).calendar));
			else
				return super.doLT(interp, o);
		}

		@Override
		public @NotNull String name()
		{
			return "DATE*";
		}

		@Override
		public Date getUserdata()
		{
			return calendar.getTime();
		}

		@Override
		public @NotNull String getString(@Nullable LuaInterpreter interp)
		{
			DateFormat df;
			if(interp == null)
				df = defaultFormat;
			else
				df = interp.getExtra(EXKEY_DATE_FORMAT, DateFormat.class, defaultFormat);

			return df.format(calendar.getTime());
		}
	}

	/** Constant <code>EXKEY_DATE_FORMAT="date.format"</code> */
	public static final String EXKEY_DATE_FORMAT = "date.format";
	private static final DateFormat defaultFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
}