package com.hk.lua;

class LuaArgs extends LuaObject
{
	final LuaObject obj;
	final LuaObject[] objs;

	LuaArgs(LuaObject... res)
	{
		objs = res;
		obj = objs[0];
	}

	/** {@inheritDoc} */
	@Override
	public boolean rawEqual(LuaObject o)
	{
		return obj.rawEqual(o);
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject rawLen()
	{
		return obj.rawLen();
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject rawGet(LuaObject key)
	{
		return obj.rawGet(key);
	}

	/** {@inheritDoc} */
	@Override
	public void rawSet(LuaObject key, LuaObject value)
	{
		obj.rawSet(key, value);
	}

	/** {@inheritDoc} */
	@Override
	public boolean getBoolean()
	{
		return obj.getBoolean();
	}

	/** {@inheritDoc} */
	@Override
	public String getString(LuaInterpreter interp)
	{
		return obj.getString();
	}

	/** {@inheritDoc} */
	@Override
	public double getDouble()
	{
		return obj.getDouble();
	}

	/** {@inheritDoc} */
	@Override
	public long getLong()
	{
		return obj.getLong();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNil()
	{
		return obj.isNil();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isBoolean()
	{
		return obj.isBoolean();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isString()
	{
		return obj.isString();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNumber()
	{
		return obj.isNumber();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInteger()
	{
		return obj.isInteger();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isTable()
	{
		return obj.isTable();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isFunction()
	{
		return obj.isFunction();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isUserdata()
	{
		return obj.isUserdata();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isThread()
	{
		return obj.isThread();
	}

	@Override
	LuaBoolean doLE(LuaInterpreter interp, LuaObject o)
	{
		return obj.doLE(interp, o);
	}

	@Override
	LuaBoolean doLT(LuaInterpreter interp, LuaObject o)
	{
		return obj.doLT(interp, o);
	}

	@Override
	LuaBoolean doEQ(LuaInterpreter interp, LuaObject o)
	{
		return obj.doEQ(interp, o);
	}

	@Override
	LuaObject doConcat(LuaInterpreter interp, LuaObject o)
	{
		return obj.doConcat(interp, o);
	}

	@Override
	LuaObject doAdd(LuaInterpreter interp, LuaObject o)
	{
		return obj.doAdd(interp, o);
	}

	@Override
	LuaObject doSub(LuaInterpreter interp, LuaObject o)
	{
		return obj.doSub(interp, o);
	}

	@Override
	LuaObject doMul(LuaInterpreter interp, LuaObject o)
	{
		return obj.doMul(interp, o);
	}

	@Override
	LuaObject doDiv(LuaInterpreter interp, LuaObject o)
	{
		return obj.doDiv(interp, o);
	}

	@Override
	LuaObject doIDiv(LuaInterpreter interp, LuaObject o)
	{
		return obj.doIDiv(interp, o);
	}

	@Override
	LuaObject doMod(LuaInterpreter interp, LuaObject o)
	{
		return obj.doMod(interp, o);
	}

	@Override
	LuaObject doPow(LuaInterpreter interp, LuaObject o)
	{
		return obj.doPow(interp, o);
	}

	@Override
	LuaObject doBAND(LuaInterpreter interp, LuaObject o)
	{
		return obj.doBAND(interp, o);
	}

	@Override
	LuaObject doBOR(LuaInterpreter interp, LuaObject o)
	{
		return obj.doBOR(interp, o);
	}

	@Override
	LuaObject doBXOR(LuaInterpreter interp, LuaObject o)
	{
		return obj.doBXOR(interp, o);
	}

	@Override
	LuaObject doSHL(LuaInterpreter interp, LuaObject o)
	{
		return obj.doSHL(interp, o);
	}

	@Override
	LuaObject doSHR(LuaInterpreter interp, LuaObject o)
	{
		return obj.doSHR(interp, o);
	}

	@Override
	LuaObject doBNOT(LuaInterpreter interp)
	{
		return obj.doBNOT(interp);
	}

	@Override
	LuaObject doUnm(LuaInterpreter interp)
	{
		return obj.doUnm(interp);
	}

	@Override
	LuaObject doLen(LuaInterpreter interp)
	{
		return obj.doLen(interp);
	}

	@Override
	LuaObject doIndex(LuaInterpreter interp, LuaObject key)
	{
		return obj.doIndex(interp, key);
	}

	@Override
	void doNewIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
	{
		obj.doNewIndex(interp, key, value);
	}

	@Override
	LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
	{
		return obj.doCall(interp, args);
	}

	@Override
	LuaObject evaluate(LuaInterpreter interp)
	{
		return obj;
	}

	@Override
	int code()
	{
		return obj.code();
	}

	/** {@inheritDoc} */
	@Override
	public LuaType type()
	{
		return obj.type();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return obj.hashCode();
	}
}