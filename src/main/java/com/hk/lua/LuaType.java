package com.hk.lua;

/**
 * <p>LuaType class.</p>
 *
 * @author theKayani
 */
public enum LuaType
{
	NIL,
	BOOLEAN,
	STRING,
	NUMBER,
	FLOAT,
	INTEGER,
	TABLE,
	FUNCTION,
	USERDATA,
	THREAD,
	ANY;

	public final String luaName;

	LuaType()
	{
		luaName = name().toLowerCase();
	}

	/**
	 * <p>applies.</p>
	 *
	 * @param type a {@link com.hk.lua.LuaType} object
	 * @return a boolean
	 */
	public boolean applies(LuaType type)
	{
		switch(this)
		{
		case ANY:
			return type != null;
		case BOOLEAN:
			return type == BOOLEAN || type == NIL;
		case FLOAT:
		case NUMBER:
			return type == NUMBER || type == FLOAT || type == INTEGER;
		default:
			return type == this;
		}
	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString()
	{
		return luaName;
	}

	/**
	 * <p>size.</p>
	 *
	 * @return a int
	 */
	public static int size()
	{
		return values().length;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param index a int
	 * @return a {@link com.hk.lua.LuaType} object
	 */
	public static LuaType get(int index)
	{
		return values()[index];
	}
}