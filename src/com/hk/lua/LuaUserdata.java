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
	public String getString(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "tostring", LuaNil.NIL);
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
	public LuaBoolean doLE(LuaInterpreter interp, LuaObject o)
	{
		if(name().equals(o.name()))
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	public LuaBoolean doLT(LuaInterpreter interp, LuaObject o)
	{
		if(name().equals(o.name()))
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	public LuaObject doConcat(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "concat", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_CONCATENATE.create(name(), o.name());
	}

	@Override
	public LuaObject doAdd(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "add", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doSub(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "sub", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doMul(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "mul", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doDiv(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "div", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doIDiv(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "idiv", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doMod(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "mod", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doPow(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "pow", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBAND(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "band", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBOR(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "bor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBXOR(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "bxor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doSHL(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "shl", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doSHR(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "shr", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBNOT(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "bnot", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doUnm(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "unm", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doLen(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "len", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_LENGTH.create(name());
	}

	@Override
	public LuaObject doIndex(LuaInterpreter interp, LuaObject key)
	{
		LuaObject func = getHandler("index", LuaNil.NIL);
		if(func != null)
		{
			if(func.isFunction())
				return func.doCall(interp, new LuaObject[] { this, key }).evaluate(interp);
			else
				return func.doIndex(interp, key);
		}
		else
			throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	public void doNewIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
	{
		LuaObject func = getHandler("newindex", LuaNil.NIL);
		if(func != null)
		{
			if(func.isFunction())
				func.doCall(interp, new LuaObject[] { this, key, value }).evaluate(interp);
			else
				func.doNewIndex(interp, key, value);
		}
		else
			throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	public LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
	{
		LuaObject func = getHandler("call", LuaNil.NIL);
		if(func != null)
		{
			LuaObject[] tmp = new LuaObject[args.length + 1];
			tmp[0] = this;
			System.arraycopy(args, 0, tmp, 1, args.length);
			return func.doCall(interp, tmp).evaluate(interp);
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
