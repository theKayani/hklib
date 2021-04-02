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
	
	public boolean getBoolean()
	{
		return true;
	}
	
	public String getString()
	{
		return Double.toString(value);
	}

	public double getFloat()
	{
		return value;
	}
	
	public long getInteger()
	{
		return (long) value;
	}

	public boolean isInteger()
	{
		return false;
	}

	public boolean isNil()
	{
		return false;
	}
	
	public boolean isBoolean()
	{
		return false;
	}
	
	public boolean isString()
	{
		return false;
	}
	
	public boolean isNumber()
	{
		return true;
	}
	
	public boolean isTable()
	{
		return false;
	}
	
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

	LuaBoolean doLE(LuaObject o)
	{
		switch(o.code())
		{
		case T_NUMBER:
			return LuaBoolean.valueOf(value <= o.getFloat());
		default:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	LuaBoolean doLT(LuaObject o)
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
	LuaBoolean doEQ(LuaObject o)
	{
		return (LuaBoolean) rawEqual(o);
	}

	LuaObject doConcat(LuaObject o)
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
	
	LuaObject doAdd(LuaObject o)
	{
		return new LuaFloat(value + o.getFloat());
	}

	LuaObject doSub(LuaObject o)
	{
		return new LuaFloat(value - o.getFloat());
	}

	LuaObject doMul(LuaObject o)
	{
		return new LuaFloat(value * o.getFloat());
	}

	LuaObject doDiv(LuaObject o)
	{
		return new LuaFloat(value / o.getFloat());
	}

	LuaObject doIDiv(LuaObject o)
	{
		return new LuaFloat(Math.floor(value / o.getFloat()));
	}

	@Override
	LuaObject doMod(LuaObject o)
	{
		return new LuaFloat(value % o.getFloat());
	}

	LuaObject doPow(LuaObject o)
	{
		return new LuaFloat(Math.pow(value, o.getFloat()));
	}

	@Override
	LuaObject doBAND(LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() & o.getInteger());
	}

	@Override
	LuaObject doBOR(LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() | o.getInteger());
	}

	@Override
	LuaObject doBXOR(LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() ^ o.getInteger());
	}

	@Override
	LuaObject doSHL(LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() << o.getInteger());
	}

	@Override
	LuaObject doSHR(LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() >> o.getInteger());
	}

	@Override
	LuaObject doBNOT()
	{
		return LuaInteger.valueOf(~getInteger());
	}

	LuaObject doUnm()
	{
		return new LuaFloat(-value);
	}

	@Override
	LuaObject doLen()
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	@Override
	LuaObject doIndex(LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	void doNewIndex(LuaObject key, LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	LuaObject doCall(LuaObject[] args)
	{
		throw LuaErrors.INVALID_CALL.create(name());
	}

	@Override
	int code()
	{
		return T_NUMBER;
	}
	
	public LuaType type()
	{
		return LuaType.FLOAT;
	}
	
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
