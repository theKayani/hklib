package com.hk.lua;

public abstract class LuaUserdata extends LuaMetatable
{	
	public LuaUserdata()
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
		LuaObject res = event("tostring", LuaNil.NIL);
		if(res != null)
			return res.getString();
		else
			return "userdata: 0x" + Integer.toHexString(hashCode());
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
	
	public abstract String name();

	public abstract Object getUserdata();

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

	@Override
	public boolean isInteger()
	{
		return false;
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
		return true;
	}

	@Override
	public boolean isThread()
	{
		return false;
	}

	@Override
	public final LuaType type()
	{
		return LuaType.USERDATA;
	}

	@Override
	public LuaBoolean doLE(LuaObject o)
	{
		if(name().equals(o.name()))
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	public LuaBoolean doLT(LuaObject o)
	{
		if(name().equals(o.name()))
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	public LuaObject doConcat(LuaObject o)
	{
		LuaObject res = event("concat", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_CONCATENATE.create(name(), o.name());
	}

	@Override
	public LuaObject doAdd(LuaObject o)
	{
		LuaObject res = event("add", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doSub(LuaObject o)
	{
		LuaObject res = event("sub", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doMul(LuaObject o)
	{
		LuaObject res = event("mul", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doDiv(LuaObject o)
	{
		LuaObject res = event("div", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doIDiv(LuaObject o)
	{
		LuaObject res = event("idiv", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doMod(LuaObject o)
	{
		LuaObject res = event("mod", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doPow(LuaObject o)
	{
		LuaObject res = event("pow", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBAND(LuaObject o)
	{
		LuaObject res = event("band", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBOR(LuaObject o)
	{
		LuaObject res = event("bor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBXOR(LuaObject o)
	{
		LuaObject res = event("bxor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doSHL(LuaObject o)
	{
		LuaObject res = event("shl", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doSHR(LuaObject o)
	{
		LuaObject res = event("shr", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBNOT()
	{
		LuaObject res = event("bnot", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doUnm()
	{
		LuaObject res = event("unm", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doLen()
	{
		LuaObject res = event("len", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_LENGTH.create(name());
	}

	@Override
	public LuaObject doIndex(LuaObject key)
	{
		LuaObject func = getHandler("index", LuaNil.NIL);
		if(func != null)
		{
			if(func.isFunction())
				return func.doCall(new LuaObject[] { this, key }).evaluate();
			else
				return func.doIndex(key);
		}
		else
			throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	public void doNewIndex(LuaObject key, LuaObject value)
	{
		LuaObject func = getHandler("newindex", LuaNil.NIL);
		if(func != null)
		{
			if(func.isFunction())
				func.doCall(new LuaObject[] { this, key, value }).evaluate();
			else
				func.doNewIndex(key, value);
		}
		else
			throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	public LuaObject doCall(LuaObject[] args)
	{
		LuaObject func = getHandler("call", LuaNil.NIL);
		if(func != null)
		{
			LuaObject[] tmp = new LuaObject[args.length + 1];
			tmp[0] = this;
			System.arraycopy(args, 0, tmp, 1, args.length);
			return func.doCall(tmp).evaluate();
		}
		else
			throw LuaErrors.INVALID_CALL.create(name());
	}

	@Override
	int code()
	{
		return T_USERDATA;
	}

	@Override
	public int hashCode()
	{
		return System.identityHashCode(this);
	}
}
