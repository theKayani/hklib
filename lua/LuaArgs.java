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

	@Override
	public LuaBoolean rawEqual(LuaObject o)
	{
		return obj.rawEqual(o);
	}

	@Override
	public LuaObject rawLen()
	{
		return obj.rawLen();
	}

	@Override
	public LuaObject rawGet(LuaObject key)
	{
		return obj.rawGet(key);
	}

	@Override
	public void rawSet(LuaObject key, LuaObject value)
	{
		obj.rawSet(key, value);
	}

	@Override
	public boolean getBoolean()
	{
		return obj.getBoolean();
	}

	@Override
	public String getString()
	{
		return obj.getString();
	}

	@Override
	public double getFloat()
	{
		return obj.getFloat();
	}

	@Override
	public long getInteger()
	{
		return obj.getInteger();
	}

	@Override
	public boolean isNil()
	{
		return obj.isNil();
	}

	@Override
	public boolean isBoolean()
	{
		return obj.isBoolean();
	}

	@Override
	public boolean isString()
	{
		return obj.isString();
	}

	@Override
	public boolean isNumber()
	{
		return obj.isNumber();
	}
	
	public boolean isInteger()
	{
		return obj.isInteger();
	}

	@Override
	public boolean isTable()
	{
		return obj.isTable();
	}

	@Override
	public boolean isFunction()
	{
		return obj.isFunction();
	}

	@Override
	public boolean isUserdata()
	{
		return obj.isUserdata();
	}

	@Override
	public boolean isThread()
	{
		return obj.isThread();
	}

	@Override
	LuaBoolean doLE(LuaObject o)
	{
		return obj.doLE(o);
	}

	@Override
	LuaBoolean doLT(LuaObject o)
	{
		return obj.doLT(o);
	}

	@Override
	LuaBoolean doEQ(LuaObject o)
	{
		return obj.doEQ(o);
	}

	@Override
	LuaObject doConcat(LuaObject o)
	{
		return obj.doConcat(o);
	}

	@Override
	LuaObject doAdd(LuaObject o)
	{
		return obj.doAdd(o);
	}

	@Override
	LuaObject doSub(LuaObject o)
	{
		return obj.doSub(o);
	}

	@Override
	LuaObject doMul(LuaObject o)
	{
		return obj.doMul(o);
	}

	@Override
	LuaObject doDiv(LuaObject o)
	{
		return obj.doDiv(o);
	}

	@Override
	LuaObject doIDiv(LuaObject o)
	{
		return obj.doIDiv(o);
	}

	@Override
	LuaObject doMod(LuaObject o)
	{
		return obj.doMod(o);
	}

	@Override
	LuaObject doPow(LuaObject o)
	{
		return obj.doPow(o);
	}

	@Override
	LuaObject doBAND(LuaObject o)
	{
		return obj.doBAND(o);
	}

	@Override
	LuaObject doBOR(LuaObject o)
	{
		return obj.doBOR(o);
	}

	@Override
	LuaObject doBXOR(LuaObject o)
	{
		return obj.doBXOR(o);
	}

	@Override
	LuaObject doSHL(LuaObject o)
	{
		return obj.doSHL(o);
	}

	@Override
	LuaObject doSHR(LuaObject o)
	{
		return obj.doSHR(o);
	}

	@Override
	LuaObject doBNOT()
	{
		return obj.doBNOT();
	}

	@Override
	LuaObject doUnm()
	{
		return obj.doUnm();
	}

	@Override
	LuaObject doLen()
	{
		return obj.doLen();
	}

	@Override
	LuaObject doIndex(LuaObject key)
	{
		return obj.doIndex(key);
	}

	@Override
	void doNewIndex(LuaObject key, LuaObject value)
	{
		obj.doNewIndex(key, value);
	}

	@Override
	LuaObject doCall(LuaObject[] args)
	{
		return obj.doCall(args);
	}
	
	LuaObject evaluate()
	{
		return obj;
	}

	@Override
	int code()
	{
		return obj.code();
	}
	
	public LuaType type()
	{
		return obj.type();
	}

	@Override
	public int hashCode()
	{
		return obj.hashCode();
	}
}
