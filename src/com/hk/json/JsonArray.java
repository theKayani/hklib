package com.hk.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class JsonArray extends JsonValue implements Iterable<JsonValue>
{
	final List<JsonValue> values;

	public JsonArray()
	{
		values = new ArrayList<>();
	}
	
	public JsonType getType()
	{
		return JsonType.ARRAY;
	}
	
	public JsonArray add(Object obj)
	{
		return add(Json.toJson(obj));
	}

	public JsonArray add(JsonValue value)
	{
		if (this == value)
			throw new IllegalArgumentException("Can't add this to this");
		
		values.add(value == null ? JsonNull.NULL : value);
		return this;
	}

	public JsonArray add(String value)
	{
		return add(new JsonString(value));
	}

	public JsonArray add(double value)
	{
		return add(new JsonNumber(value));
	}

	public JsonArray add(long value)
	{
		return add(new JsonNumber(value));
	}

	public JsonArray add(boolean value)
	{
		return add(JsonBoolean.valueOf(value));
	}
	
	public JsonArray addAll(Iterable<JsonValue> vals)
	{
		for(JsonValue val : vals)
			add(val);
		return this;
	}
	
	public JsonArray addAll(JsonValue... vals)
	{
		for(JsonValue val : vals)
			add(val);
		return this;
	}

	public int size()
	{
		return values.size();
	}

	public JsonValue get(int i)
	{
		return values.get(i);
	}

	public JsonValue remove(int i)
	{
		return values.remove(i);
	}

	public boolean isEmpty()
	{
		return values.isEmpty();
	}

	public boolean contains(JsonValue value)
	{
		return values.contains(value);
	}
	
	public boolean isArray()
	{
		return true;
	}
	
	public JsonArray getArray()
	{
		return this;
	}
	
	public ListIterator<JsonValue> listIterator()
	{
		return values.listIterator();
	}

	@Override
	public Iterator<JsonValue> iterator()
	{
		return values.iterator();
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonArray && Objects.equals(values, ((JsonArray) o).values);
	}

	@Override
	public int hashCode()
	{
		return 53 + values.hashCode();
	}
}
