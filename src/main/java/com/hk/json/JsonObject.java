package com.hk.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class JsonObject extends JsonValue implements Iterable<Map.Entry<String, JsonValue>>
{
	public final Map<String, JsonValue> map;

	public JsonObject()
	{
		map = new LinkedHashMap<>();
	}

	public JsonObject(int initialCapacity)
	{
		map = new LinkedHashMap<>(initialCapacity);
	}

	public JsonObject(Map<String, JsonValue> map)
	{
		this.map = new LinkedHashMap<>(map);
	}

	public JsonObject(JsonObject copy)
	{
		map = new LinkedHashMap<>(copy.map);
	}
	
	public JsonType getType()
	{
		return JsonType.OBJECT;
	}
	
	public boolean contains(String name)
	{
		return map.containsKey(name);
	}
	
	public JsonValue get(String name)
	{
		return map.get(name);
	}
	
	public boolean isObject(String name)
	{
		return map.containsKey(name) && map.get(name).isObject();
	}
	
	public JsonObject getObject(String name)
	{
		return get(name).getObject();
	}
	
	public boolean isArray(String name)
	{
		return map.containsKey(name) && get(name).isArray();
	}
	
	public JsonArray getArray(String name)
	{
		return get(name).getArray();
	}
	
	public boolean isString(String name)
	{
		return map.containsKey(name) && get(name).isString();
	}
	
	public String getString(String name)
	{
		return get(name).getString();
	}
	
	public boolean isNumber(String name)
	{
		return map.containsKey(name) && get(name).isNumber();
	}
	
	public Number getNumber(String name)
	{
		return get(name).getNumber();
	}
	
	public byte getByte(String name)
	{
		return get(name).getNumber().byteValue();
	}
	
	public short getShort(String name)
	{
		return get(name).getNumber().shortValue();
	}
	
	public int getInt(String name)
	{
		return get(name).getNumber().intValue();
	}
	
	public long getLong(String name)
	{
		return get(name).getNumber().longValue();
	}
	
	public float getFloat(String name)
	{
		return get(name).getNumber().floatValue();
	}
	
	public double getDouble(String name)
	{
		return get(name).getNumber().doubleValue();
	}
	
	public boolean isBoolean(String name)
	{
		return map.containsKey(name) && get(name).isBoolean();
	}
	
	public boolean getBoolean(String name)
	{
		return get(name).getBoolean();
	}
	
	public boolean isNull(String name)
	{
		return map.containsKey(name) && get(name).isNull();
	}
	
	public JsonObject put(String name, Object obj)
	{
		return put(name, Json.toJson(obj));
	}

	public JsonObject put(String name, JsonValue value)
	{
		if (this == value)
			throw new IllegalArgumentException("Can't add this to this");
		if(name == null)
			throw new IllegalArgumentException("Cannot put null keys");

		map.put(name, value == null ? JsonNull.NULL : value);
		return this;
	}

	public JsonObject put(String name, String value)
	{
		if(value == null)
			return put(name, JsonNull.NULL);
		else
			return put(name, new JsonString(value));
	}

	public JsonObject put(String name, long value)
	{
		return put(name, new JsonNumber(value));
	}

	public JsonObject put(String name, double value)
	{
		return put(name, new JsonNumber(value));
	}

	public JsonObject put(String name, boolean value)
	{
		return put(name, JsonBoolean.valueOf(value));
	}
	
	public JsonValue remove(String name)
	{
		return map.remove(name);
	}
	
	public JsonObject removeAll()
	{
		map.clear();
		return this;
	}
	
	public boolean isEmpty()
	{
		return map.isEmpty();
	}
	
	public int size()
	{
		return map.size();
	}
	
	public <T> Map<String, T> toMap(JsonAdapter<T> adapter)
	{
		Map<String, T> map = new HashMap<>();
		
		for(Map.Entry<String, JsonValue> ent : this)
			map.put(ent.getKey(), adapter.fromJson(ent.getValue()));
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> toMap(Class<T> cls)
	{
		for(JsonAdapter<?> adapter : Json.globalAdapters)
		{
			if(adapter.getObjClass().isAssignableFrom(cls))
			{
				Map<String, T> map = new HashMap<>();
				
				for(Map.Entry<String, JsonValue> ent : this)
					map.put(ent.getKey(), (T) adapter.fromJson(ent.getValue()));
				
				return map;
			}
		}

		throw new JsonAdaptationException("No adapter for " + cls.getName());
	}
	
	public boolean isObject()
	{
		return true;
	}
	
	public JsonObject getObject()
	{
		return this;
	}
	
	public Iterator<String> keys()
	{
		return map.keySet().iterator();
	}
	
	public Iterator<Map.Entry<String, JsonValue>> iterator()
	{
		return map.entrySet().iterator();
	}
	
	public Iterator<JsonValue> values()
	{
		return map.values().iterator();
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonObject && Objects.equals(map, ((JsonObject) o).map);
	}

	@Override
	public int hashCode()
	{
		return map.hashCode() + 34;
	}
}
