package com.hk.json;

public class JsonBoolean extends JsonValue
{
	private final boolean value;

	private JsonBoolean(boolean value)
	{
		this.value = value;
	}
	
	public JsonType getType()
	{
		return JsonType.BOOLEAN;
	}

	public boolean isBoolean()
	{
		return true;
	}
	
	public boolean getBoolean()
	{
		return value;
	}

	@Override
	public int hashCode()
	{
		return value ? 707 : ~707;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonBoolean && value == ((JsonBoolean) o).value;
	}
	
	public String toString()
	{
		return Boolean.toString(value);
	}
	
	public static JsonBoolean valueOf(boolean b)
	{
		return b ? TRUE : FALSE;
	}

	public static final JsonBoolean TRUE = new JsonBoolean(true);
	public static final JsonBoolean FALSE = new JsonBoolean(false);
}
