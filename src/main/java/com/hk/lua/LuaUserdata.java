package com.hk.lua;

/**
 * <p>Abstract LuaUserdata class.</p>
 *
 * @author theKayani
 */
public abstract class LuaUserdata extends LuaMetatable
{	
	/**
	 * <p>Constructor for LuaUserdata.</p>
	 */
	public LuaUserdata()
	{
	}

	/** {@inheritDoc} */
	@Override
	public boolean rawEqual(LuaObject o)
	{
		return o == this;
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
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public String getString(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "tostring", LuaNil.NIL);
		if(res != null)
			return res.getString();
		else
			return "userdata: 0x" + Integer.toHexString(hashCode());
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
	
	/**
	 * <p>name.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public abstract String name();

	/**
	 * <p>getUserdata.</p>
	 *
	 * @return a {@link java.lang.Object} object
	 */
	public abstract Object getUserdata();

	/** {@inheritDoc} */
	@Override
	public boolean isNil()
	{
		return false;
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
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isThread()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public final LuaType type()
	{
		return LuaType.USERDATA;
	}

	/** {@inheritDoc} */
	@Override
	public LuaBoolean doEQ(LuaInterpreter interp, LuaObject o)
	{
		return super.doEQ(interp, o);
	}

	/** {@inheritDoc} */
	@Override
	public LuaBoolean doLE(LuaInterpreter interp, LuaObject o)
	{
		if(name().equals(o.name()))
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaBoolean doLT(LuaInterpreter interp, LuaObject o)
	{
		if(name().equals(o.name()))
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doConcat(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "concat", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_CONCATENATE.create(name(), o.name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doAdd(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "add", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doSub(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "sub", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doMul(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "mul", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doDiv(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "div", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doIDiv(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "idiv", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doMod(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "mod", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doPow(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "pow", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doBAND(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "band", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doBOR(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "bor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doBXOR(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "bxor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doSHL(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "shl", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doSHR(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "shr", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doBNOT(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "bnot", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doUnm(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "unm", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doLen(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "len", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_LENGTH.create(name());
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return System.identityHashCode(this);
	}
}
