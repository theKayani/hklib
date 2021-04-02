package com.hk.lua;

class LuaNil extends LuaObject
{
	static final LuaNil NIL = new LuaNil();
	
	private LuaNil()
	{}

	@Override
	public LuaBoolean rawEqual(LuaObject o)
	{
		return LuaBoolean.valueOf(o.isNil());
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
		return false;
	}

	public String getString()
	{
		return "nil";
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
		return true;
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
		case T_NIL:
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
		case T_NIL:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		default:
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		}
	}

	@Override
	LuaBoolean doEQ(LuaObject o)
	{
		return LuaBoolean.valueOf(o.isNil());
	}

	@Override
	LuaObject doConcat(LuaObject o)
	{
		throw LuaErrors.INVALID_CONCATENATE.create(name());
	}

	LuaObject doAdd(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	LuaObject doSub(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	LuaObject doMul(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

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
		return rawGet(key);
	}

	@Override
	void doNewIndex(LuaObject key, LuaObject value)
	{
		rawSet(key, value);
	}

	@Override
	LuaObject doCall(LuaObject[] args)
	{
		throw LuaErrors.INVALID_CALL.create(name());
	}

	@Override
	int code()
	{
		return T_NIL;
	}
	
	public LuaType type()
	{
		return LuaType.NIL;
	}
	
	public int hashCode()
	{
		return 0;
	}
}
