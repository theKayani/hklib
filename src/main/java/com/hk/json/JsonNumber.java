package com.hk.json;

public class JsonNumber extends JsonValue
{
	private final Number value;

	public JsonNumber(long value)
	{
		this.value = value;
	}

	public JsonNumber(double value)
	{
		this.value = value;
	}
	
	public JsonType getType()
	{
		return JsonType.NUMBER;
	}

	public boolean isNumber()
	{
		return true;
	}
	
	public Number getNumber()
	{
		return value;
	}

	@Override
	public int hashCode()
	{
		return value instanceof Long ? Long.hashCode(value.longValue()) : Double.hashCode(value.doubleValue());
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonNumber && value.equals(((JsonNumber) o).value);
	}

	@Override
	public String toString()
	{
		return value.toString();
	}
}
