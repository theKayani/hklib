package com.hk.lua;

import java.util.Map;
import java.util.Set;

class LuaTable extends LuaMetatable
{
	protected final Map<LuaObject, LuaObject> map;

	LuaTable()
	{
//		this(new java.util.TreeMap<LuaObject, LuaObject>());
//		this(new java.util.HashMap<LuaObject, LuaObject>());
		this(new java.util.LinkedHashMap<LuaObject, LuaObject>());
	}

	LuaTable(Map<LuaObject, LuaObject> map)
	{
		this.map = map;
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
		LuaObject a, b;
		int count = 1;
		while(map.containsKey(LuaInteger.valueOf(count)))
		{
			if(count > 1)
			{
				a = map.get(LuaInteger.valueOf(count));
				b = map.get(LuaInteger.valueOf(count - 1));
				if((a == null || a.isNil()) && (b == null || b.isNil()))
					break;
			}
			count++;
		}

		return LuaInteger.valueOf(count - 1);
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject rawGet(LuaObject key)
	{
		LuaObject obj = map.get(key);
		return obj == null ? LuaNil.NIL : obj;
	}

	/** {@inheritDoc} */
	@Override
	public void rawSet(LuaObject key, LuaObject value)
	{
		if(key.isNil() || (key.isNumber() && Double.isNaN(key.getDouble())))
			return;

		if(value.isNil())
			map.remove(key);
		else
			map.put(key, value);
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
	public boolean getBoolean()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public String getString(LuaInterpreter interp)
	{
//		return toString();

		LuaObject res = event(interp, "tostring", LuaNil.NIL);
		if(res != null)
			return res.getString();
		else
			return "table: 0x" + Integer.toHexString(hashCode()) + Integer.toHexString(System.identityHashCode(map));
	}

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
		return true;
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
		LuaObject res = event(interp, "le", o);
		if(res != null)
			return LuaBoolean.valueOf(res.getBoolean());
		else
		{
			res = event(interp, "lt", o, o, this);
			if(res != null)
				return LuaBoolean.valueOf(!res.getBoolean());
			else
				throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	@Override
	LuaBoolean doLT(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "lt", o);
		if(res != null)
			return LuaBoolean.valueOf(res.getBoolean());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaObject doConcat(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "concat", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_CONCATENATE.create(name());
	}

	@Override
	LuaObject doAdd(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "add", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSub(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "sub", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMul(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "mul", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doDiv(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "div", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doIDiv(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "idiv", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMod(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "mod", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doPow(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "pow", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBAND(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "band", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBOR(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "bor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBXOR(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "bxor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHL(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "shl", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHR(LuaInterpreter interp, LuaObject o)
	{
		LuaObject res = event(interp, "shr", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBNOT(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "bnot", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doUnm(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "unm", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doLen(LuaInterpreter interp)
	{
		LuaObject res = event(interp, "len", LuaNil.NIL);
		if(res != null)
			return res;
		else
			return rawLen();
	}

	@Override
	LuaObject doIndex(LuaInterpreter interp, LuaObject key)
	{
		if(map.containsKey(key))
		{
			key = map.get(key);
			if(key != null && !key.isNil())
				return key;
		}

		LuaObject func = getHandler("index", LuaNil.NIL);
		if(func != null)
		{
			if(func.isFunction())
				return func.doCall(interp, new LuaObject[] { this, key }).evaluate(interp);
			else
				return func.doIndex(interp, key);
		}
		else
			return LuaNil.NIL;
	}

	@Override
	void doNewIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
	{
		LuaObject v = map.get(key);
		if(v == null || v.isNil())
		{
			LuaObject func = getHandler("newindex", LuaNil.NIL);
			if(func != null)
			{
				if(func.isFunction())
					func.doCall(interp, new LuaObject[] { this, key, value });
				else
					func.doNewIndex(interp, key, value);
			}
		}
		map.put(key, value);
	}

	@Override
	LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
	{
		LuaObject[] tmp = new LuaObject[args.length + 1];
		tmp[0] = this;
		System.arraycopy(args, 0, tmp, 1, args.length);

		LuaObject res = event(interp, "call", LuaNil.NIL, tmp);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_CALL.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public Set<LuaObject> getIndicies()
	{
		return map.keySet();
	}

	/** {@inheritDoc} */
	@Override
	public Set<Map.Entry<LuaObject, LuaObject>> getEntries()
	{
		return map.entrySet();
	}

	@Override
	int code()
	{
		return T_TABLE;
	}

	/** {@inheritDoc} */
	@Override
	public LuaType type()
	{
		return LuaType.TABLE;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return map.toString();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return System.identityHashCode(this);
	}
}