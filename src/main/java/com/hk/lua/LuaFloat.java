package com.hk.lua;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class LuaFloat extends LuaObject
{	
	private final double value;

	LuaFloat(double value)
	{
		this.value = value == -0 ? 0 : value;
	}

	/** {@inheritDoc}
	 * @param o*/
	@Override
	public boolean rawEqual(@NotNull LuaObject o)
	{
		return o.isNumber() && value == o.getDouble();
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
		return Double.toString(value);
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
		return (long) value;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInteger()
	{
		return false;
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

	@Override
	LuaBoolean doLE(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.code() == T_NUMBER)
		{
			return LuaBoolean.valueOf(value <= o.getDouble());
		}
		throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doLT(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.code() == T_NUMBER)
		{
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
		case T_FUNCTION:
			throw LuaErrors.INVALID_CONCATENATE.create(o.name());
		default:
			throw new Error();
		}
	}

	@Override
	LuaObject doAdd(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return new LuaFloat(value + o.getDouble());
	}

	@Override
	LuaObject doSub(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return new LuaFloat(value - o.getDouble());
	}

	@Override
	LuaObject doMul(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
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
		return new LuaFloat(Math.floor(value / o.getDouble()));
	}

	@Override
	LuaObject doMod(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
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
		return LuaInteger.valueOf(getLong() & o.getLong());
	}

	@Override
	LuaObject doBOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(getLong() | o.getLong());
	}

	@Override
	LuaObject doBXOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(getLong() ^ o.getLong());
	}

	@Override
	LuaObject doSHL(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(getLong() << o.getLong());
	}

	@Override
	LuaObject doSHR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(getLong() >> o.getLong());
	}

	@Override
	LuaObject doBNOT(@Nullable LuaInterpreter interp)
	{
		return LuaInteger.valueOf(~getLong());
	}

	@Override
	LuaObject doUnm(@Nullable LuaInterpreter interp)
	{
		return new LuaFloat(-value);
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
		return LuaType.FLOAT;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		long bits = Double.doubleToLongBits(value);
		return (int) (bits ^ (bits >>> 32));
	}

	static final LuaFloat NaN = new LuaFloat(Double.NaN);
	static final LuaFloat POSITIVE_INFINITY = new LuaFloat(Double.POSITIVE_INFINITY);
	static final LuaFloat NEGATIVE_INFINITY = new LuaFloat(Double.NEGATIVE_INFINITY);
	static final LuaFloat PI = new LuaFloat(Math.PI);
	static final LuaFloat HUGE = new LuaFloat(Double.MAX_VALUE);
}