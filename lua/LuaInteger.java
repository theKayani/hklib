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
	
	public boolean getBoolean()
	{
		return true;
	}
	
	public String getString()
	{
		return Long.toString(value);
	}
	
	public double getFloat()
	{
		return value;
	}

	public long getInteger()
	{
		return value;
	}

	public boolean isInteger()
	{
		return true;
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
			if(o.isInteger())
				return LuaBoolean.valueOf(value <= o.getInteger());
			else
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
			if(o.isInteger())
				return LuaBoolean.valueOf(value < o.getInteger());
			else
				return LuaBoolean.valueOf(value < o.getFloat());
		default:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	@Override
	LuaBoolean doEQ(LuaObject o)
	{
		return rawEqual(o);
	}

	LuaObject doConcat(LuaObject o)
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
	
	LuaObject doAdd(LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value + o.getInteger());
		else
			return new LuaFloat(value + o.getFloat());
	}

	LuaObject doSub(LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value - o.getInteger());
		else
			return new LuaFloat(value - o.getFloat());
	}

	LuaObject doMul(LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value * o.getInteger());
		else
			return new LuaFloat(value * o.getFloat());
	}

	LuaObject doDiv(LuaObject o)
	{
		return new LuaFloat(value / o.getFloat());
	}

	LuaObject doIDiv(LuaObject o)
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
	LuaObject doMod(LuaObject o)
	{
		if(o.isInteger())
			return valueOf(value % o.getInteger());
		else
			return new LuaFloat(value % o.getFloat());
	}

	LuaObject doPow(LuaObject o)
	{
		return new LuaFloat(Math.pow(value, o.getFloat()));
	}

	@Override
	LuaObject doBAND(LuaObject o)
	{
		return LuaInteger.valueOf(value & o.getInteger());
	}

	@Override
	LuaObject doBOR(LuaObject o)
	{
		return LuaInteger.valueOf(value | o.getInteger());
	}

	@Override
	LuaObject doBXOR(LuaObject o)
	{
		return LuaInteger.valueOf(value ^ o.getInteger());
	}

	@Override
	LuaObject doSHL(LuaObject o)
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
	LuaObject doSHR(LuaObject o)
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
	LuaObject doBNOT()
	{
		return LuaInteger.valueOf(~value);
	}

	LuaObject doUnm()
	{
		return valueOf(-value);
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
		return LuaType.INTEGER;
	}
	
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
