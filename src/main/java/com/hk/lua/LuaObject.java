package com.hk.lua;

import java.util.Map;
import java.util.Set;

/**
 * <p>Every Lua value at runtime (and before) is represented by a
 * LuaObject. This represents Lua strings, numbers, booleans, tables
 * and other types of values. To implement your own concept of a
 * LuaObject, you can extend the {@link com.hk.lua.LuaUserdata}
 * and define your own functionality for a Lua object.</p>
 *
 * @author theKayani
 */
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

	/**
	 * <p>Return whether another LuaObject is of equal value to this one.</p>
	 *
	 * @param o a {@link com.hk.lua.LuaObject} object
	 * @return a boolean dictating whether these objects are of equal value.
	 */
	public abstract boolean rawEqual(LuaObject o);

	/**
	 * <p>Return the length of this Lua object. This only applies to
	 * Lua strings and Lua tables.</p>
	 *
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public abstract LuaObject rawLen();

	/**
	 * <p>Get the raw value using a Lua object as a key to map the val.</p>
	 *
	 * @param key a {@link com.hk.lua.LuaObject} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public abstract LuaObject rawGet(LuaObject key);
	/**
	 * <p>Set the raw value using a Lua object as a key to map the val.</p>
	 *
	 * @param key a {@link com.hk.lua.LuaObject} object
	 * @param value a {@link com.hk.lua.LuaObject} object
	 */
	public abstract void rawSet(LuaObject key, LuaObject value);
	
	/**
	 * <p>Get true or false defining whether this object 'exists' and
	 * is not nil or false</p>
	 *
	 * @return a boolean
	 */
	public abstract boolean getBoolean();

	/**
	 * <p>Get the string value of this Lua object. If an interpreter
	 * is provided and this object is a table, this method would call
	 * the "__tostring" method. If this object is a string, this
	 * parameter is ignored.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @return a {@link java.lang.String} representation of this object
	 */
	public abstract String getString(LuaInterpreter interp);

	/**
	 * <p>Get the double value of this Lua object if it's a number.</p>
	 *
	 * @return a double
	 * @deprecated use #getDouble()
	 */
	@Deprecated
	public final double getFloat()
	{
		return getDouble();
	}

	/**
	 * <p>Get the double value of this Lua object if it's a number.</p>
	 *
	 * @return a double
	 */
	public abstract double getDouble();

	/**
	 * <p>Get the float value of this Lua object if it's a number.</p>
	 *
	 * @return a float
	 */
	public float getFlt()
	{
		return (float) getDouble();
	}

	/**
	 * <p>Get the long value of this Lua object if it's a number.</p>
	 *
	 * @return a long
	 * @deprecated use #getLong()
	 */
	@Deprecated
	public final long getInteger()
	{
		return getLong();
	}

	/**
	 * <p>Get the long value of this Lua object if it's a number.</p>
	 *
	 * @return a long
	 */
	public abstract long getLong();

	/**
	 * <p>Get the integer value of this Lua object if it's a number.</p>
	 *
	 * @return an integer
	 */
	public int getInt()
	{
		return (int) getLong();
	}

	/**
	 * <p>isNil.</p>
	 *
	 * @return a boolean
	 */
	public abstract boolean isNil();
	/**
	 * <p>isBoolean.</p>
	 *
	 * @return a boolean
	 */
	public abstract boolean isBoolean();
	/**
	 * <p>isString.</p>
	 *
	 * @return a boolean
	 */
	public abstract boolean isString();
	/**
	 * <p>isNumber.</p>
	 *
	 * @return a boolean
	 */
	public abstract boolean isNumber();
	/**
	 * <p>isInteger.</p>
	 *
	 * @return a boolean
	 */
	public abstract boolean isInteger();
	/**
	 * <p>isTable.</p>
	 *
	 * @return a boolean
	 */
	public abstract boolean isTable();
	/**
	 * <p>isFunction.</p>
	 *
	 * @return a boolean
	 */
	public abstract boolean isFunction();
	/**
	 * <p>isUserdata.</p>
	 *
	 * @return a boolean
	 */
	public abstract boolean isUserdata();
	/**
	 * <p>isThread.</p>
	 *
	 * @return a boolean
	 */
	public abstract boolean isThread();
	
	/**
	 * <p>type.</p>
	 *
	 * @return a {@link com.hk.lua.LuaType} object
	 */
	public abstract LuaType type();
	
	/**
	 * <p>name.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String name()
	{
		return type().luaName;
	}
	
	/**
	 * <p>setMetatable.</p>
	 *
	 * @param metatable a {@link com.hk.lua.LuaObject} object
	 */
	public void setMetatable(LuaObject metatable)
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * <p>getMetatable.</p>
	 *
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject getMetatable()
	{
		return LuaNil.NIL;
	}

	@Override
	LuaObject evaluate(LuaInterpreter interps)
	{
		return this;
	}
	
	/**
	 * <p>getIndicies.</p>
	 *
	 * @return a {@link java.util.Set} object
	 */
	public Set<LuaObject> getIndicies()
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * <p>getEntries.</p>
	 *
	 * @return a {@link java.util.Set} object
	 */
	public Set<Map.Entry<LuaObject, LuaObject>> getEntries()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * <p>getIndex.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param key a long
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject getIndex(LuaInterpreter interp, long key)
	{
		return getIndex(interp, LuaInteger.valueOf(key));
	}

	/**
	 * <p>getIndex.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param key a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject getIndex(LuaInterpreter interp, String key)
	{
		return getIndex(interp, new LuaString(key));
	}

	/**
	 * <p>getIndex.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param key a {@link com.hk.lua.LuaObject} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject getIndex(LuaInterpreter interp, LuaObject key)
	{
		return doIndex(interp, key);
	}
	
	/**
	 * <p>setIndex.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param key a long
	 * @param value a {@link java.lang.Object} object
	 */
	public void setIndex(LuaInterpreter interp, long key, Object value)
	{
		setIndex(interp, LuaInteger.valueOf(key), Lua.newLuaObject(value));
	}
	
	/**
	 * <p>setIndex.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param key a {@link java.lang.String} object
	 * @param value a {@link java.lang.Object} object
	 */
	public void setIndex(LuaInterpreter interp, String key, Object value)
	{
		setIndex(interp, new LuaString(key), Lua.newLuaObject(value));
	}
	
	/**
	 * <p>setIndex.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param key a {@link com.hk.lua.LuaObject} object
	 * @param value a {@link java.lang.Object} object
	 */
	public void setIndex(LuaInterpreter interp, LuaObject key, Object value)
	{
		setIndex(interp, key, Lua.newLuaObject(value));
	}
	
	/**
	 * <p>setIndex.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param key a long
	 * @param value a {@link com.hk.lua.LuaObject} object
	 */
	public void setIndex(LuaInterpreter interp, long key, LuaObject value)
	{
		setIndex(interp, LuaInteger.valueOf(key), value);
	}
	
	/**
	 * <p>setIndex.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param key a {@link java.lang.String} object
	 * @param value a {@link com.hk.lua.LuaObject} object
	 */
	public void setIndex(LuaInterpreter interp, String key, LuaObject value)
	{
		setIndex(interp, new LuaString(key), value);
	}
	
	/**
	 * <p>setIndex.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param key a {@link com.hk.lua.LuaObject} object
	 * @param value a {@link com.hk.lua.LuaObject} object
	 */
	public void setIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
	{
		doNewIndex(interp, key, value);
	}

	/**
	 * <p>rawGet.</p>
	 *
	 * @param key a long
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject rawGet(long key)
	{
		return rawGet(LuaInteger.valueOf(key));
	}

	/**
	 * <p>rawGet.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject rawGet(String key)
	{
		return rawGet(new LuaString(key));
	}
	
	/**
	 * <p>rawSet.</p>
	 *
	 * @param key a long
	 * @param value a {@link java.lang.Object} object
	 */
	public void rawSet(long key, Object value)
	{
		rawSet(LuaInteger.valueOf(key), Lua.newLuaObject(value));
	}
	
	/**
	 * <p>rawSet.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param value a {@link java.lang.Object} object
	 */
	public void rawSet(String key, Object value)
	{
		rawSet(new LuaString(key), Lua.newLuaObject(value));
	}
	
	/**
	 * <p>rawSet.</p>
	 *
	 * @param key a {@link com.hk.lua.LuaObject} object
	 * @param value a {@link java.lang.Object} object
	 */
	public void rawSet(LuaObject key, Object value)
	{
		rawSet(key, Lua.newLuaObject(value));
	}
	
	/**
	 * <p>rawSet.</p>
	 *
	 * @param key a long
	 * @param value a {@link com.hk.lua.LuaObject} object
	 */
	public void rawSet(long key, LuaObject value)
	{
		rawSet(LuaInteger.valueOf(key), value);
	}
	
	/**
	 * <p>rawSet.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param value a {@link com.hk.lua.LuaObject} object
	 */
	public void rawSet(String key, LuaObject value)
	{
		rawSet(new LuaString(key), value);
	}

	/**
	 * <p>getString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public final String getString()
	{
		return getString(null);
	}
	
	/**
	 * <p>getLength.</p>
	 *
	 * @return a long
	 */
	public final long getLength()
	{
		return rawLen().getLong();
	}
	
	/**
	 * <p>call.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param args a {@link com.hk.lua.LuaObject} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject call(LuaInterpreter interp, LuaObject... args)
	{
		return doCall(interp, args == null ? new LuaObject[0] : args);
	}

	/**
	 * <p>callFunction.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param args a {@link java.lang.Object} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
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
	
	/**
	 * <p>getAsThread.</p>
	 *
	 * @return a {@link com.hk.lua.LuaThread} object
	 */
	public LuaThread getAsThread()
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * <p>getUserdata.</p>
	 *
	 * @return a {@link java.lang.Object} object
	 */
	public Object getUserdata()
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * <p>getUserdata.</p>
	 *
	 * @param cls a {@link java.lang.Class} object
	 * @param <T> a T class
	 * @return a T object
	 */
	public <T> T getUserdata(Class<T> cls)
	{
		return cls.cast(getUserdata());
	}
		
	/** {@inheritDoc} */
	public boolean equals(Object o)
	{
		return o instanceof LuaObject && rawEqual((LuaObject) o);
	}
	
	/**
	 * <p>hashCode.</p>
	 *
	 * @return a int
	 */
	public abstract int hashCode();
	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString()
	{
		return getString();
	}
}
