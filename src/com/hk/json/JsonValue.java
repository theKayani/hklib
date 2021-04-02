package com.hk.json;

public abstract class JsonValue
{
	public boolean isObject()
	{
		return false;
	}
	
	public abstract JsonType getType();
	
	public JsonObject getObject()
	{
		throw new IllegalStateException("not an object");
	}

	public boolean isArray()
	{
		return false;
	}
	
	public JsonArray getArray()
	{
		throw new IllegalStateException("not an array");
	}

	public boolean isString()
	{
		return false;
	}
	
	public String getString()
	{
		throw new IllegalStateException("not a string");
	}

	public boolean isNumber()
	{
		return false;
	}
	
	public Number getNumber()
	{
		throw new IllegalStateException("not a number");
	}

	public boolean isBoolean()
	{
		return false;
	}
	
	public boolean getBoolean()
	{
		throw new IllegalStateException("not a boolean");
	}

	public boolean isNull()
	{
		return false;
	}
	
	public String toString()
	{
		return Json.write(this);
	}
}
