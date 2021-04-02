package com.hk.lua;

import java.util.Map;
import java.util.Set;

public abstract class LuaObject extends Lua.LuaValue
{
	LuaObject()
	{
	}

	abstract LuaBoolean doLE(LuaObject o);
	abstract LuaBoolean doLT(LuaObject o);
	abstract LuaBoolean doEQ(LuaObject o);
	
	abstract LuaObject doConcat(LuaObject o);
	abstract LuaObject doAdd(LuaObject o);
	abstract LuaObject doSub(LuaObject o);
	abstract LuaObject doMul(LuaObject o);
	abstract LuaObject doDiv(LuaObject o);
	abstract LuaObject doIDiv(LuaObject o);
	abstract LuaObject doMod(LuaObject o);
	abstract LuaObject doPow(LuaObject o);
	abstract LuaObject doBAND(LuaObject o);
	abstract LuaObject doBOR(LuaObject o);
	abstract LuaObject doBXOR(LuaObject o);
	abstract LuaObject doSHL(LuaObject o);
	abstract LuaObject doSHR(LuaObject o);
	abstract LuaObject doBNOT();
	abstract LuaObject doUnm();
	abstract LuaObject doLen();
	abstract LuaObject doIndex(LuaObject key);
	abstract void doNewIndex(LuaObject key, LuaObject value);
	abstract LuaObject doCall(LuaObject[] args);

	abstract int code();

	public abstract LuaBoolean rawEqual(LuaObject o);
	public abstract LuaObject rawLen();
	public abstract LuaObject rawGet(LuaObject key);
	public abstract void rawSet(LuaObject key, LuaObject value);
	
	public abstract boolean getBoolean();
	public abstract String getString();
	public abstract double getFloat();
	public abstract long getInteger();

	public abstract boolean isNil();
	public abstract boolean isBoolean();
	public abstract boolean isString();
	public abstract boolean isNumber();
	public abstract boolean isInteger();
	public abstract boolean isTable();
	public abstract boolean isFunction();
	public abstract boolean isUserdata();
	public abstract boolean isThread();
	
	public abstract LuaType type();
	
	public String name()
	{
		return type().luaName;
	}
	
	public void setMetatable(LuaObject metatable)
	{
		throw new UnsupportedOperationException();
	}
	
	public LuaObject getMetatable()
	{
		return LuaNil.NIL;
	}

	@Override
	LuaObject evaluate()
	{
		return this;
	}
	
	public Set<LuaObject> getIndicies()
	{
		throw new UnsupportedOperationException();
	}
	
	public Set<Map.Entry<LuaObject, LuaObject>> getEntries()
	{
		throw new UnsupportedOperationException();
	}

	public LuaObject getIndex(long key)
	{
		return getIndex(LuaInteger.valueOf(key));
	}

	public LuaObject getIndex(String key)
	{
		return getIndex(new LuaString(key));
	}

	public LuaObject getIndex(LuaObject key)
	{
		return doIndex(key);
	}
	
	public void setIndex(long key, Object value)
	{
		setIndex(LuaInteger.valueOf(key), Lua.newLuaObject(value));
	}
	
	public void setIndex(String key, Object value)
	{
		setIndex(new LuaString(key), Lua.newLuaObject(value));
	}
	
	public void setIndex(LuaObject key, Object value)
	{
		setIndex(key, Lua.newLuaObject(value));
	}
	
	public void setIndex(long key, LuaObject value)
	{
		setIndex(LuaInteger.valueOf(key), value);
	}
	
	public void setIndex(String key, LuaObject value)
	{
		setIndex(new LuaString(key), value);
	}
	
	public void setIndex(LuaObject key, LuaObject value)
	{
		doNewIndex(key, value);
	}
	
	public long getLength()
	{
		return rawLen().getInteger();
	}

	public LuaObject callFunction(Object... args)
	{
		LuaObject[] as;
		if(args != null)
		{
			as = new LuaObject[args.length];
			for(int i = 0; i < as.length; i++)
				as[i] = Lua.newLuaObject(args[i]);
		}
		else
		{
			as = new LuaObject[0];
		}
		
		return doCall(as);
	}
	
	public LuaThread getAsThread()
	{
		throw new UnsupportedOperationException();
	}
	
	public Object getUserdata()
	{
		throw new UnsupportedOperationException();
	}
	
	public <T> T getUserdata(Class<T> cls)
	{
		return cls.cast(getUserdata());
	}
		
	public boolean equals(Object o)
	{
		if(o instanceof LuaObject)
			return rawEqual((LuaObject) o).getBoolean();
		return false;
	}
	
	public abstract int hashCode();
	
	public String toString()
	{
		return getString();
	}
}
