package com.hk.lua;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class LuaInteger extends LuaObject
{
	private final long value;

	private LuaInteger(long value)
	{
		this.value = value;
	}

	/** {@inheritDoc}
	 * @param o*/
	@Override
	public boolean rawEqual(@NotNull LuaObject o)
	{
		if(o.isInteger())
			return value == o.getLong();
		else
			return o.isNumber() && getDouble() == o.getDouble();
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaObject rawLen()
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaObject rawGet(@NotNull LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	/** {@inheritDoc}
	 * @param key
	 * @param value*/
	@Override
	public void rawSet(@NotNull LuaObject key, @NotNull LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public boolean getBoolean()
	{
		return true;
	}

	/** {@inheritDoc}
	 * @param interp
	 * @return*/
	@Override
	public @NotNull String getString(@Nullable LuaInterpreter interp)
	{
		return Long.toString(value);
	}

	/** {@inheritDoc} */
	@Override
	public double getDouble()
	{
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public long getLong()
	{
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInteger()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNil()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isBoolean()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isString()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNumber()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isTable()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isFunction()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isUserdata()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isThread()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVarargs()
	{
		return false;
	}

	@Override
	LuaBoolean doLE(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.code() == T_NUMBER)
		{
			if (o.isInteger())
				return LuaBoolean.valueOf(value <= o.getLong());
			else
				return LuaBoolean.valueOf(value <= o.getDouble());
		}
		throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doLT(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.code() == T_NUMBER)
		{
			if (o.isInteger())
				return LuaBoolean.valueOf(value < o.getLong());
			else
				return LuaBoolean.valueOf(value < o.getDouble());
		}
		throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doEQ(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaBoolean.valueOf(rawEqual(o));
	}

	@Override
	LuaObject doConcat(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		switch(o.code())
		{
		case T_STRING:
		case T_NUMBER:
			return new LuaString(getString() + o.getString());
		default:
			throw LuaErrors.INVALID_CONCATENATE.create(o.name());
		}
	}

	@Override
	LuaObject doAdd(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value + o.getLong());
		else
			return new LuaFloat(value + o.getDouble());
	}

	@Override
	LuaObject doSub(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value - o.getLong());
		else
			return new LuaFloat(value - o.getDouble());
	}

	@Override
	LuaObject doMul(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value * o.getLong());
		else
			return new LuaFloat(value * o.getDouble());
	}

	@Override
	LuaObject doDiv(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return new LuaFloat(value / o.getDouble());
	}

	@Override
	LuaObject doIDiv(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		try
		{
			if(o.isInteger())
				return valueOf(value / o.getLong());
			else
				return new LuaFloat(Math.floor(value / o.getDouble()));
		}
		catch(ArithmeticException e)
		{
			if(e.getLocalizedMessage().equals("/ by zero"))
				throw LuaErrors.DIVIDE_BY_ZERO.create();
			else
				throw new LuaException(e.getLocalizedMessage());
		}
	}

	@Override
	LuaObject doMod(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value % o.getLong());
		else
			return new LuaFloat(value % o.getDouble());
	}

	@Override
	LuaObject doPow(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return new LuaFloat(Math.pow(value, o.getDouble()));
	}

	@Override
	LuaObject doBAND(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(value & o.getLong());
	}

	@Override
	LuaObject doBOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(value | o.getLong());
	}

	@Override
	LuaObject doBXOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(value ^ o.getLong());
	}

	@Override
	LuaObject doSHL(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.getInt() == 64)
		{
			return LuaInteger.ZERO;
		}
		return LuaInteger.valueOf(value << o.getLong());
	}

	@Override
	LuaObject doSHR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.getInt() == 64)
		{
			return LuaInteger.ZERO;
		}
		return LuaInteger.valueOf(value >> o.getLong());
	}

	@Override
	LuaObject doBNOT(@Nullable LuaInterpreter interp)
	{
		return LuaInteger.valueOf(~value);
	}

	@Override
	LuaObject doUnm(@Nullable LuaInterpreter interp)
	{
		return valueOf(-value);
	}

	@Override
	LuaObject doLen(@Nullable LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	@Override
	LuaObject doIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	void doNewIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key, @NotNull LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
	{
		throw LuaErrors.INVALID_CALL.create(name());
	}

	@Override
	int code()
	{
		return T_NUMBER;
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaType type()
	{
		return LuaType.INTEGER;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		long bits = Double.doubleToLongBits(value);
		return (int) (bits ^ (bits >>> 32));
	}

	private static final LuaInteger[] map;

	static
	{
		long l = -2048;
		map = new LuaInteger[4096];
		for(int i = 0; i < 4096; i++)
			map[i] = new LuaInteger(l++);
	}

	static LuaInteger valueOf(long l)
	{
		if(l >= -2048 && l < 2048)
			return map[(int) (l + 2048)];
		else
			return new LuaInteger(l);
	}

	static final LuaInteger ZERO = valueOf(0);
	static final LuaInteger ONE = valueOf(1);
	static final LuaInteger NEG_ONE = valueOf(-1);
}