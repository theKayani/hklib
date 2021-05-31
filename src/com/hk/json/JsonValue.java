package com.hk.json;

public abstract class JsonValue
{
	JsonValue()
	{}
	
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
	
	public <T> boolean is(Class<T> cls, JsonAdapter<?> adapter)
	{
		return adapter.getObjClass().isAssignableFrom(cls);
	}
	
	public <T> boolean is(Class<T> cls)
	{
		for(JsonAdapter<?> adapter : Json.globalAdapters)
		{
			if(is(cls, adapter))
				return true;
		}
		return false;
	}
	
	public <T> T get(JsonAdapter<T> adapter) throws JsonAdaptationException
	{
		return adapter.fromJson(this);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> cls) throws JsonAdaptationException
	{
		for(JsonAdapter<?> adapter : Json.globalAdapters)
		{
			if(is(cls, adapter))
				return (T) adapter.fromJson(this);
		}
		throw new JsonAdaptationException("No adapter for " + cls.getName());
	}

	public boolean isNull()
	{
		return false;
	}
	
	public String toString()
	{
		return Json.writePretty(this);
	}
}
