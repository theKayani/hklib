package com.hk.json;

import com.hk.util.Requirements;

public class JsonString extends JsonValue
{
	private String value;
	
	public JsonString(String value)
	{
		this.value = Requirements.requireNotNull(value);
	}
	
	public JsonType getType()
	{
		return JsonType.STRING;
	}

	public boolean isString()
	{
		return true;
	}
	
	public String getString()
	{
		return value;
	}

	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonString && value.equals(((JsonString) o).value);
	}

	@Override
	public String toString()
	{
		return Json.write(this);
	}
}
