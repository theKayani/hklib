package com.hk.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class JsonArray extends JsonValue implements Iterable<JsonValue>
{
	public final List<JsonValue> list;

	public JsonArray()
	{
		list = new ArrayList<>();
	}

	public JsonArray(int initialCapacity)
	{
		list = new ArrayList<>(initialCapacity);
	}
	
	public JsonArray(List<JsonValue> values)
	{
		this.list = new ArrayList<>(values);
	}
	
	public JsonArray(JsonArray copy)
	{
		list = new ArrayList<>(copy.list);
	}
	
	public JsonType getType()
	{
		return JsonType.ARRAY;
	}
	
	public <T> JsonArray add(T obj, JsonAdapter<T> adapter)
	{
		return add(adapter.toJson(obj));
	}
	
	public JsonArray add(Object obj)
	{
		return add(Json.toJson(obj));
	}

	public JsonArray add(JsonValue value)
	{
		if (this == value)
			throw new IllegalArgumentException("Can't add this to this");
		
		list.add(value == null ? JsonNull.NULL : value);
		return this;
	}

	public JsonArray add(String value)
	{
		return add(value == null ? null : new JsonString(value));
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
	
	public <T> JsonArray addAll(Iterable<T> vals, JsonAdapter<T> adapter)
	{
		for(T val : vals)
			add(adapter.toJson(val));
		return this;
	}
	
	public <T> JsonArray addAll(Iterable<T> vals, Class<T> cls)
	{		
		for(JsonAdapter<?> adapter : Json.globalAdapters)
		{
			if(adapter.getObjClass().isAssignableFrom(cls))
			{
				for(T val : vals)
					add(adapter.tryTo(val));
				return this;
			}
		}

		throw new JsonAdaptationException("No adapter for " + cls.getName());
	}
	
	public JsonArray addAll(JsonValue... vals)
	{
		for(JsonValue val : vals)
			add(val);
		return this;
	}
	
	public JsonArray addAll(Object... vals)
	{
		for(Object val : vals)
			add(Json.toJson(val));

		return this;
	}

	public int size()
	{
		return list.size();
	}

	public JsonValue get(int i)
	{
		return list.get(i);
	}

	public JsonArray add(int i, JsonValue value)
	{
		list.add(i, value == null ? JsonNull.NULL : value);
		return this;
	}

	public <T> JsonArray add(int i, T value, JsonAdapter<T> adapter)
	{
		return add(i, adapter.toJson(value));
	}

	public JsonArray add(int i, Object value)
	{
		return add(i, Json.toJson(value));
	}

	public JsonArray add(int i, String value)
	{
		return add(i, new JsonString(value));
	}

	public JsonArray add(int i, double value)
	{
		return add(i, new JsonNumber(value));
	}

	public JsonArray add(int i, long value)
	{
		return add(i, new JsonNumber(value));
	}

	public JsonArray add(int i, boolean value)
	{
		return add(i, JsonBoolean.valueOf(value));
	}

	public JsonValue set(int i, JsonValue value)
	{
		return list.set(i, value == null ? JsonNull.NULL : value);
	}

	public <T> JsonValue set(int i, T value, JsonAdapter<T> adapter)
	{
		return set(i, adapter.toJson(value));
	}

	public JsonValue set(int i, Object value)
	{
		return set(i, Json.toJson(value));
	}

	public JsonValue set(int i, String value)
	{
		return set(i, new JsonString(value));
	}

	public JsonValue set(int i, double value)
	{
		return set(i, new JsonNumber(value));
	}

	public JsonValue set(int i, long value)
	{
		return set(i, new JsonNumber(value));
	}

	public JsonValue set(int i, boolean value)
	{
		return set(i, JsonBoolean.valueOf(value));
	}

	public JsonValue remove(int i)
	{
		return list.remove(i);
	}
	
	public JsonArray removeAll()
	{
		list.clear();
		return this;
	}

	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public boolean contains(JsonValue value)
	{
		return list.contains(value);
	}
	
	public <T> List<T> toList(JsonAdapter<T> adapter)
	{
		List<T> list = new ArrayList<>(size());
		
		for(JsonValue value : this.list)
			list.add(adapter.fromJson(value));
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> toList(Class<T> cls)
	{
		for(JsonAdapter<?> adapter : Json.globalAdapters)
		{
			if(adapter.getObjClass().isAssignableFrom(cls))
			{
				List<T> list = new ArrayList<>(size());
				
				for(JsonValue value : this.list)
					list.add((T) adapter.fromJson(value));
				
				return list;
			}
		}
		
		throw new JsonAdaptationException("No adapter for " + cls.getName());
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
		return list.listIterator();
	}

	@Override
	public Iterator<JsonValue> iterator()
	{
		return list.iterator();
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonArray && Objects.equals(list, ((JsonArray) o).list);
	}

	@Override
	public int hashCode()
	{
		return 53 + list.hashCode();
	}
}
