package com.hk.lua;

class LuaFloat extends LuaObject
{	
	private final double value;
	
	LuaFloat(double value)
	{
		this.value = value == -0 ? 0 : value;
	}

	@Override
	public LuaBoolean rawEqual(LuaObject o)
	{
		return LuaBoolean.valueOf(o.isNumber() && value == o.getFloat());
	}

	@Override
	public LuaObject rawLen()
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	@Override
	public LuaObject rawGet(LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	public void rawSet(LuaObject key, LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}
	
	@Override
	public boolean getBoolean()
	{
		return true;
	}
	
	@Override
	public String getString(LuaInterpreter interp)
	{
		return Double.toString(value);
	}

	@Override
	public double getFloat()
	{
		return value;
	}
	
	@Override
	public long getInteger()
	{
		return (long) value;
	}

	@Override
	public boolean isInteger()
	{
		return false;
	}

	@Override
	public boolean isNil()
	{
		return false;
	}

	@Override
	public boolean isBoolean()
	{
		return false;
	}

	@Override
	public boolean isString()
	{
		return false;
	}

	@Override
	public boolean isNumber()
	{
		return true;
	}

	@Override
	public boolean isTable()
	{
		return false;
	}

	@Override
	public boolean isFunction()
	{
		return false;
	}

	@Override
	public boolean isUserdata()
	{
		return false;
	}

	@Override
	public boolean isThread()
	{
		return false;
	}

	@Override
	LuaBoolean doLE(LuaInterpreter interp, LuaObject o)
	{
		switch(o.code())
		{
		case T_NUMBER:
			return LuaBoolean.valueOf(value <= o.getFloat());
		default:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	@Override
	LuaBoolean doLT(LuaInterpreter interp, LuaObject o)
	{
		switch(o.code())
		{
		case T_NUMBER:
			return LuaBoolean.valueOf(value < o.getFloat());
		default:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	@Override
	LuaBoolean doEQ(LuaInterpreter interp, LuaObject o)
	{
		return (LuaBoolean) rawEqual(o);
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
		return new LuaFloat(value + o.getFloat());
	}

	@Override
	LuaObject doSub(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(value - o.getFloat());
	}

	@Override
	LuaObject doMul(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(value * o.getFloat());
	}

	@Override
	LuaObject doDiv(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(value / o.getFloat());
	}

	@Override
	LuaObject doIDiv(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(Math.floor(value / o.getFloat()));
	}

	@Override
	LuaObject doMod(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(value % o.getFloat());
	}

	@Override
	LuaObject doPow(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(Math.pow(value, o.getFloat()));
	}

	@Override
	LuaObject doBAND(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() & o.getInteger());
	}

	@Override
	LuaObject doBOR(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() | o.getInteger());
	}

	@Override
	LuaObject doBXOR(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() ^ o.getInteger());
	}

	@Override
	LuaObject doSHL(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() << o.getInteger());
	}

	@Override
	LuaObject doSHR(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() >> o.getInteger());
	}

	@Override
	LuaObject doBNOT(LuaInterpreter interp)
	{
		return LuaInteger.valueOf(~getInteger());
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

	@Override
	public LuaType type()
	{
		return LuaType.FLOAT;
	}

	@Override
	public int hashCode()
	{
		return Double.hashCode(value);
	}
	
	static final LuaFloat NaN = new LuaFloat(Double.NaN);
	static final LuaFloat POSITIVE_INFINITY = new LuaFloat(Double.POSITIVE_INFINITY);
	static final LuaFloat NEGATIVE_INFINITY = new LuaFloat(Double.NEGATIVE_INFINITY);
	static final LuaFloat PI = new LuaFloat(Math.PI);
	static final LuaFloat HUGE = new LuaFloat(Double.MAX_VALUE);
}
