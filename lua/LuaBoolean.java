package com.hk.lua;

class LuaBoolean extends LuaObject
{
	static final LuaBoolean TRUE = new LuaBoolean(true);
	static final LuaBoolean FALSE = new LuaBoolean(false);
	
	private final boolean value;
	
	private LuaBoolean(boolean value)
	{
		this.value = value;
	}
	
	LuaBoolean not()
	{
		return value ? FALSE : TRUE;
	}

	@Override
	public LuaBoolean rawEqual(LuaObject o)
	{
		return LuaBoolean.valueOf(o.isBoolean() && value == o.getBoolean());
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
	
	public String getString()
	{
		return value ? "true" : "false";
	}
	
	public boolean getBoolean()
	{
		return value;
	}
	
	public double getFloat()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}
	
	public long getInteger()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}
	
	public boolean isNil()
	{
		return false;
	}
	
	public boolean isBoolean()
	{
		return true;
	}
	
	public boolean isString()
	{
		return false;
	}
	
	public boolean isNumber()
	{
		return false;
	}
	
	public boolean isInteger()
	{
		return false;
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

	@Override
	LuaBoolean doLE(LuaObject o)
	{
		switch(o.code())
		{
		case T_FUNCTION:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		default:
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		}
	}

	@Override
	LuaBoolean doLT(LuaObject o)
	{
		switch(o.code())
		{
		case T_FUNCTION:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		default:
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		}
	}

	@Override
	LuaBoolean doEQ(LuaObject o)
	{
		return LuaBoolean.valueOf(o.isBoolean() && value == o.getBoolean());
	}

	@Override
	LuaObject doConcat(LuaObject o)
	{
		throw LuaErrors.INVALID_CONCATENATE.create(name());
	}

	@Override
	LuaObject doAdd(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSub(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMul(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doDiv(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doIDiv(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMod(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doPow(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBAND(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBOR(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBXOR(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHL(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHR(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBNOT()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	LuaObject doUnm()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
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
		return T_BOOLEAN;
	}
	
	public LuaType type()
	{
		return LuaType.BOOLEAN;
	}
	
	public int hashCode()
	{
		return Boolean.hashCode(value);
	}
	
	static LuaBoolean valueOf(boolean value)
	{
		return value ? TRUE : FALSE;
	}
}
