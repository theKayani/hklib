package com.hk.lua;

abstract class LuaFunction extends LuaObject
{
	LuaFunction()
	{
	}

	@Override
	public LuaBoolean rawEqual(LuaObject o)
	{
		return LuaBoolean.valueOf(o == this);
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
	public String getString()
	{
		return "function: 0x" + Long.toHexString((long) Math.pow(hashCode(), 2));
	}

	@Override
	public double getFloat()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public long getInteger()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
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
		return false;
	}
	
	public boolean isInteger()
	{
		return false;
	}

	@Override
	public boolean isTable()
	{
		return false;
	}
	
	public boolean isFunction()
	{
		return true;
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
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		default:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	@Override
	LuaBoolean doLT(LuaObject o)
	{
		switch(o.code())
		{
		case T_FUNCTION:
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		default:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	@Override
	LuaBoolean doEQ(LuaObject o)
	{
		return LuaBoolean.valueOf(o == this);
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

	@Override
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
	abstract LuaObject doCall(LuaObject[] args);

	@Override
	int code()
	{
		return T_FUNCTION;
	}
	
	public LuaType type()
	{
		return LuaType.FUNCTION;
	}

	@Override
	public int hashCode()
	{
		return System.identityHashCode(this);
	}
}
