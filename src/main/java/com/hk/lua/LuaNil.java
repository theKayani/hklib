package com.hk.lua;

class LuaNil extends LuaObject
{
	static final LuaNil NIL = new LuaNil();

	private LuaNil()
	{}

	/** {@inheritDoc} */
	@Override
	public boolean rawEqual(LuaObject o)
	{
		return o.isNil();
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
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getString(LuaInterpreter interp)
	{
		return "nil";
	}

	/** {@inheritDoc} */
	@Override
	public double getDouble()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public long getLong()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNil()
	{
		return true;
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
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInteger()
	{
		return false;
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
		if (o.code() == T_NIL)
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		else
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
	}

	@Override
	LuaBoolean doLT(LuaInterpreter interp, LuaObject o)
	{
		if (o.code() == T_NIL)
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		else
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
	}

	@Override
	LuaBoolean doEQ(LuaInterpreter interp, LuaObject o)
	{
		return LuaBoolean.valueOf(o.isNil());
	}

	@Override
	LuaObject doConcat(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_CONCATENATE.create(name());
	}

	@Override
	LuaObject doAdd(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSub(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMul(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doDiv(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doIDiv(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMod(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doPow(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBAND(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBOR(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBXOR(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHL(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHR(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBNOT(LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doUnm(LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doLen(LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	@Override
	LuaObject doIndex(LuaInterpreter interp, LuaObject key)
	{
		return rawGet(key);
	}

	@Override
	void doNewIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
	{
		rawSet(key, value);
	}

	@Override
	LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
	{
		throw LuaErrors.INVALID_CALL.create(name());
	}

	@Override
	int code()
	{
		return T_NIL;
	}

	/** {@inheritDoc} */
	@Override
	public LuaType type()
	{
		return LuaType.NIL;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return 0;
	}
}