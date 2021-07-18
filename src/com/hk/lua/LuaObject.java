package com.hk.lua;

import java.util.Map;
import java.util.Set;

public abstract class LuaObject extends Lua.LuaValue
{
	LuaObject()
	{
	}

	abstract LuaBoolean doLE(LuaInterpreter interp, LuaObject o);
	abstract LuaBoolean doLT(LuaInterpreter interp, LuaObject o);
	abstract LuaBoolean doEQ(LuaInterpreter interp, LuaObject o);
	
	abstract LuaObject doConcat(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doAdd(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doSub(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doMul(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doDiv(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doIDiv(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doMod(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doPow(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doBAND(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doBOR(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doBXOR(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doSHL(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doSHR(LuaInterpreter interp, LuaObject o);
	abstract LuaObject doBNOT(LuaInterpreter interp);
	abstract LuaObject doUnm(LuaInterpreter interp);
	abstract LuaObject doLen(LuaInterpreter interp);
	abstract LuaObject doIndex(LuaInterpreter interp, LuaObject key);
	abstract void doNewIndex(LuaInterpreter interp, LuaObject key, LuaObject value);
	abstract LuaObject doCall(LuaInterpreter interp, LuaObject[] args);

	abstract int code();

	public abstract LuaBoolean rawEqual(LuaObject o);
	public abstract LuaObject rawLen();
	public abstract LuaObject rawGet(LuaObject key);
	public abstract void rawSet(LuaObject key, LuaObject value);
	
	public abstract boolean getBoolean();
	public abstract String getString(LuaInterpreter interp);
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
	LuaObject evaluate(LuaInterpreter interps)
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

	public LuaObject getIndex(LuaInterpreter interp, long key)
	{
		return getIndex(interp, LuaInteger.valueOf(key));
	}

	public LuaObject getIndex(LuaInterpreter interp, String key)
	{
		return getIndex(interp, new LuaString(key));
	}

	public LuaObject getIndex(LuaInterpreter interp, LuaObject key)
	{
		return doIndex(interp, key);
	}
	
	public void setIndex(LuaInterpreter interp, long key, Object value)
	{
		setIndex(interp, LuaInteger.valueOf(key), Lua.newLuaObject(value));
	}
	
	public void setIndex(LuaInterpreter interp, String key, Object value)
	{
		setIndex(interp, new LuaString(key), Lua.newLuaObject(value));
	}
	
	public void setIndex(LuaInterpreter interp, LuaObject key, Object value)
	{
		setIndex(interp, key, Lua.newLuaObject(value));
	}
	
	public void setIndex(LuaInterpreter interp, long key, LuaObject value)
	{
		setIndex(interp, LuaInteger.valueOf(key), value);
	}
	
	public void setIndex(LuaInterpreter interp, String key, LuaObject value)
	{
		setIndex(interp, new LuaString(key), value);
	}
	
	public void setIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
	{
		doNewIndex(interp, key, value);
	}

	public LuaObject rawGet(long key)
	{
		return rawGet(LuaInteger.valueOf(key));
	}

	public LuaObject rawGet(String key)
	{
		return rawGet(new LuaString(key));
	}
	
	public void rawSet(long key, Object value)
	{
		rawSet(LuaInteger.valueOf(key), Lua.newLuaObject(value));
	}
	
	public void rawSet(String key, Object value)
	{
		rawSet(new LuaString(key), Lua.newLuaObject(value));
	}
	
	public void rawSet(LuaObject key, Object value)
	{
		rawSet(key, Lua.newLuaObject(value));
	}
	
	public void rawSet(long key, LuaObject value)
	{
		rawSet(LuaInteger.valueOf(key), value);
	}
	
	public void rawSet(String key, LuaObject value)
	{
		rawSet(new LuaString(key), value);
	}

	public final String getString()
	{
		return getString(null);
	}
	
	public final long getLength()
	{
		return rawLen().getInteger();
	}
	
	public LuaObject call(LuaInterpreter interp, LuaObject... args)
	{
		return doCall(interp, args == null ? new LuaObject[0] : args);
	}

	public LuaObject callFunction(LuaInterpreter interp, Object... args)
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
		
		return doCall(interp, as);
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
