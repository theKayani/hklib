package com.hk.lua;

class LuaFloat extends LuaObject
{	
	private final double value;

	LuaFloat(double value)
	{
		this.value = value == -0 ? 0 : value;
	}

	/** {@inheritDoc} */
	@Override
	public boolean rawEqual(LuaObject o)
	{
		return o.isNumber() && value == o.getDouble();
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject rawLen()
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject rawGet(LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public void rawSet(LuaObject key, LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public boolean getBoolean()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public String getString(LuaInterpreter interp)
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
	LuaBoolean doLE(LuaInterpreter interp, LuaObject o)
	{
		if (o.code() == T_NUMBER)
		{
			return LuaBoolean.valueOf(value <= o.getDouble());
		}
		throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doLT(LuaInterpreter interp, LuaObject o)
	{
		if (o.code() == T_NUMBER)
		{
			return LuaBoolean.valueOf(value < o.getDouble());
		}
		throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doEQ(LuaInterpreter interp, LuaObject o)
	{
		return LuaBoolean.valueOf(rawEqual(o));
	}

	@Override
	LuaObject doConcat(LuaInterpreter interp, LuaObject o)
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
	LuaObject doAdd(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(value + o.getDouble());
	}

	@Override
	LuaObject doSub(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(value - o.getDouble());
	}

	@Override
	LuaObject doMul(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(value * o.getDouble());
	}

	@Override
	LuaObject doDiv(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(value / o.getDouble());
	}

	@Override
	LuaObject doIDiv(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(Math.floor(value / o.getDouble()));
	}

	@Override
	LuaObject doMod(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(value % o.getDouble());
	}

	@Override
	LuaObject doPow(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(Math.pow(value, o.getDouble()));
	}

	@Override
	LuaObject doBAND(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getLong() & o.getLong());
	}

	@Override
	LuaObject doBOR(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getLong() | o.getLong());
	}

	@Override
	LuaObject doBXOR(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getLong() ^ o.getLong());
	}

	@Override
	LuaObject doSHL(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getLong() << o.getLong());
	}

	@Override
	LuaObject doSHR(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getLong() >> o.getLong());
	}

	@Override
	LuaObject doBNOT(LuaInterpreter interp)
	{
		return LuaInteger.valueOf(~getLong());
	}

	@Override
	LuaObject doUnm(LuaInterpreter interp)
	{
		return new LuaFloat(-value);
	}

	@Override
	LuaObject doLen(LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	@Override
	LuaObject doIndex(LuaInterpreter interp, LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	void doNewIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
	{
		throw LuaErrors.INVALID_CALL.create(name());
	}

	@Override
	int code()
	{
		return T_NUMBER;
	}

	/** {@inheritDoc} */
	@Override
	public LuaType type()
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