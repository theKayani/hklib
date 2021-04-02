package com.hk.lua;

class LuaInteger extends LuaObject
{
	private final long value;
	
	private LuaInteger(long value)
	{
		this.value = value;
	}

	@Override
	public LuaBoolean rawEqual(LuaObject o)
	{
		return LuaBoolean.valueOf(o.isNumber() && getFloat() == o.getFloat());
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
		return Long.toString(value);
	}

	@Override
	public double getFloat()
	{
		return value;
	}

	@Override
	public long getInteger()
	{
		return value;
	}

	@Override
	public boolean isInteger()
	{
		return true;
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
			if(o.isInteger())
				return LuaBoolean.valueOf(value <= o.getInteger());
			else
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
			if(o.isInteger())
				return LuaBoolean.valueOf(value < o.getInteger());
			else
				return LuaBoolean.valueOf(value < o.getFloat());
		default:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	@Override
	LuaBoolean doEQ(LuaInterpreter interp, LuaObject o)
	{
		return rawEqual(o);
	}

	@Override
	LuaObject doConcat(LuaInterpreter interp, LuaObject o)
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
	LuaObject doAdd(LuaInterpreter interp, LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value + o.getInteger());
		else
			return new LuaFloat(value + o.getFloat());
	}

	@Override
	LuaObject doSub(LuaInterpreter interp, LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value - o.getInteger());
		else
			return new LuaFloat(value - o.getFloat());
	}

	@Override
	LuaObject doMul(LuaInterpreter interp, LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value * o.getInteger());
		else
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
		try
		{
			if(o.isInteger())
				return valueOf(value / o.getInteger());
			else
				return new LuaFloat(Math.floor(value / o.getFloat()));
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
	LuaObject doMod(LuaInterpreter interp, LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value % o.getInteger());
		else
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
		return LuaInteger.valueOf(value & o.getInteger());
	}

	@Override
	LuaObject doBOR(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(value | o.getInteger());
	}

	@Override
	LuaObject doBXOR(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(value ^ o.getInteger());
	}

	@Override
	LuaObject doSHL(LuaInterpreter interp, LuaObject o)
	{
		switch((int) o.getInteger())
		{
		case 64:
			return LuaInteger.ZERO;
		default:
			return LuaInteger.valueOf(value << o.getInteger());
		}
	}

	@Override
	LuaObject doSHR(LuaInterpreter interp, LuaObject o)
	{
		switch((int) o.getInteger())
		{
		case 64:
			return LuaInteger.ZERO;
		default:
			return LuaInteger.valueOf(value >> o.getInteger());
		}
	}

	@Override
	LuaObject doBNOT(LuaInterpreter interp)
	{
		return LuaInteger.valueOf(~value);
	}

	@Override
	LuaObject doUnm(LuaInterpreter interp)
	{
		return valueOf(-value);
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
		return LuaType.INTEGER;
	}

	@Override
	public int hashCode()
	{
		return Double.hashCode(value);
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
}
