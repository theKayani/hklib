package com.hk.lua;

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
	
	private LuaType()
	{
		luaName = name().toLowerCase();
	}
	
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
	
	public String toString()
	{
		return luaName;
	}
	
	public static int size()
	{
		return values().length;
	}
	
	public static LuaType get(int index)
	{
		return values()[index];
	}
}
