package com.hk.json;


public class JsonNull extends JsonValue
{
	public static final JsonNull NULL = new JsonNull();

	private JsonNull()
	{}
	
	public JsonType getType()
	{
		return JsonType.NULL;
	}

	public boolean isNull()
	{
		return true;
	}

	@Override
	public int hashCode()
	{
		return 6532;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonNull;
	}
	
	public String toString()
	{
		return "null";
	}
}
