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

	@Override
	public LuaBoolean rawEqual(LuaObject o)
	{
		return LuaBoolean.valueOf(o == this);
	}

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
	
	public LuaObject rawGet(LuaObject key)
	{
		LuaObject obj = map.get(key);
		return obj == null ? LuaNil.NIL : obj;
	}
	
	public void rawSet(LuaObject key, LuaObject value)
	{
		if(key.isNil() || (key.isNumber() && Double.isNaN(key.getFloat())))
			return;
		
		if(value.isNil())
			map.remove(key);
		else
			map.put(key, value);
	}
	
	public double getFloat()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}
	
	public long getInteger()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}
	
	public boolean getBoolean()
	{
		return true;
	}
	
	public String getString()
	{
//		return toString();
		
		LuaObject res = event("tostring", LuaNil.NIL);
		if(res != null)
			return res.getString();
		else
			return "table: 0x" + Integer.toHexString(hashCode()) + Integer.toHexString(System.identityHashCode(map));
	}
	
	public boolean isNil()
	{
		return false;
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
		return true;
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
		LuaObject res = event("le", o);
		if(res != null)
			return LuaBoolean.valueOf(res.getBoolean());
		else
		{
			res = event("lt", o, o, this);
			if(res != null)
				return LuaBoolean.valueOf(!res.getBoolean());
			else
				throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	@Override
	LuaBoolean doLT(LuaObject o)
	{
		LuaObject res = event("lt", o);
		if(res != null)
			return LuaBoolean.valueOf(res.getBoolean());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaObject doConcat(LuaObject o)
	{
		LuaObject res = event("concat", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_CONCATENATE.create(name());
	}

	@Override
	LuaObject doAdd(LuaObject o)
	{
		LuaObject res = event("add", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSub(LuaObject o)
	{
		LuaObject res = event("sub", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMul(LuaObject o)
	{
		LuaObject res = event("mul", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doDiv(LuaObject o)
	{
		LuaObject res = event("div", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doIDiv(LuaObject o)
	{
		LuaObject res = event("idiv", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMod(LuaObject o)
	{
		LuaObject res = event("mod", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doPow(LuaObject o)
	{
		LuaObject res = event("pow", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBAND(LuaObject o)
	{
		LuaObject res = event("band", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBOR(LuaObject o)
	{
		LuaObject res = event("bor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBXOR(LuaObject o)
	{
		LuaObject res = event("bxor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHL(LuaObject o)
	{
		LuaObject res = event("shl", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHR(LuaObject o)
	{
		LuaObject res = event("shr", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBNOT()
	{
		LuaObject res = event("bnot", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doUnm()
	{
		LuaObject res = event("unm", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doLen()
	{
		LuaObject res = event("len", LuaNil.NIL);
		if(res != null)
			return res;
		else
			return rawLen();
	}
	
	LuaObject doIndex(LuaObject key)
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
				return func.doCall(new LuaObject[] { this, key }).evaluate();
			else
				return func.doIndex(key);
		}
		else
			return LuaNil.NIL;
	}
	
	void doNewIndex(LuaObject key, LuaObject value)
	{
		LuaObject v = map.get(key);
		if(v == null || v.isNil())
		{
			LuaObject func = getHandler("newindex", LuaNil.NIL);
			if(func != null)
			{
				if(func.isFunction())
					func.doCall(new LuaObject[] { this, key, value });
				else
					func.doNewIndex(key, value);
			}
		}
		map.put(key, value);
	}

	@Override
	LuaObject doCall(LuaObject[] args)
	{
		LuaObject[] tmp = new LuaObject[args.length + 1];
		tmp[0] = this;
		System.arraycopy(args, 0, tmp, 1, args.length);
		
		LuaObject res = event("call", LuaNil.NIL, tmp);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_CALL.create(name());
	}
	
	public Set<LuaObject> getIndicies()
	{
		return map.keySet();
	}
	
	public Set<Map.Entry<LuaObject, LuaObject>> getEntries()
	{
		return map.entrySet();
	}
	
	@Override
	int code()
	{
		return T_TABLE;
	}
	
	public LuaType type()
	{
		return LuaType.TABLE;
	}
	
	public String toString()
	{
		return map.toString();
	}

	@Override
	public int hashCode()
	{
		return System.identityHashCode(this);
	}
}
