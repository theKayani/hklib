package com.hk.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>JsonObject class.</p>
 *
 * @author theKayani
 */
public class JsonObject extends JsonValue implements Iterable<Map.Entry<String, JsonValue>>
{
	public final Map<String, JsonValue> map;

	/**
	 * <p>Constructor for JsonObject.</p>
	 */
	public JsonObject()
	{
		map = new LinkedHashMap<>();
	}

	/**
	 * <p>Constructor for JsonObject.</p>
	 *
	 * @param initialCapacity a int
	 */
	public JsonObject(int initialCapacity)
	{
		map = new LinkedHashMap<>(initialCapacity);
	}

	/**
	 * <p>Constructor for JsonObject.</p>
	 *
	 * @param map a {@link java.util.Map} object
	 */
	public JsonObject(Map<String, JsonValue> map)
	{
		this.map = new LinkedHashMap<>(map);
	}

	/**
	 * <p>Constructor for JsonObject.</p>
	 *
	 * @param copy a {@link com.hk.json.JsonObject} object
	 */
	public JsonObject(JsonObject copy)
	{
		map = new LinkedHashMap<>(copy.map);
	}
	
	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link com.hk.json.JsonType} object
	 */
	public JsonType getType()
	{
		return JsonType.OBJECT;
	}
	
	/**
	 * <p>contains.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean contains(String name)
	{
		return map.containsKey(name);
	}
	
	/**
	 * <p>get.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public JsonValue get(String name)
	{
		return map.get(name);
	}
	
	/**
	 * <p>isObject.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isObject(String name)
	{
		return map.containsKey(name) && map.get(name).isObject();
	}
	
	/**
	 * <p>getObject.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a {@link com.hk.json.JsonObject} object
	 */
	public JsonObject getObject(String name)
	{
		return get(name).getObject();
	}
	
	/**
	 * <p>isArray.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isArray(String name)
	{
		return map.containsKey(name) && get(name).isArray();
	}
	
	/**
	 * <p>getArray.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray getArray(String name)
	{
		return get(name).getArray();
	}
	
	/**
	 * <p>isString.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isString(String name)
	{
		return map.containsKey(name) && get(name).isString();
	}
	
	/**
	 * <p>getString.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public String getString(String name)
	{
		return get(name).getString();
	}
	
	/**
	 * <p>isNumber.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isNumber(String name)
	{
		return map.containsKey(name) && get(name).isNumber();
	}
	
	/**
	 * <p>getNumber.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a {@link java.lang.Number} object
	 */
	public Number getNumber(String name)
	{
		return get(name).getNumber();
	}
	
	/**
	 * <p>getByte.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a byte
	 */
	public byte getByte(String name)
	{
		return get(name).getNumber().byteValue();
	}
	
	/**
	 * <p>getShort.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a short
	 */
	public short getShort(String name)
	{
		return get(name).getNumber().shortValue();
	}
	
	/**
	 * <p>getInt.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a int
	 */
	public int getInt(String name)
	{
		return get(name).getNumber().intValue();
	}
	
	/**
	 * <p>getLong.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a long
	 */
	public long getLong(String name)
	{
		return get(name).getNumber().longValue();
	}
	
	/**
	 * <p>getFloat.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a float
	 */
	public float getFloat(String name)
	{
		return get(name).getNumber().floatValue();
	}
	
	/**
	 * <p>getDouble.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a double
	 */
	public double getDouble(String name)
	{
		return get(name).getNumber().doubleValue();
	}
	
	/**
	 * <p>isBoolean.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isBoolean(String name)
	{
		return map.containsKey(name) && get(name).isBoolean();
	}
	
	/**
	 * <p>getBoolean.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean getBoolean(String name)
	{
		return get(name).getBoolean();
	}
	
	/**
	 * <p>isNull.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isNull(String name)
	{
		return map.containsKey(name) && get(name).isNull();
	}
	
	/**
	 * <p>put.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @param obj a {@link java.lang.Object} object
	 * @return a {@link com.hk.json.JsonObject} object
	 */
	public JsonObject put(String name, Object obj)
	{
		return put(name, Json.toJson(obj));
	}

	/**
	 * <p>put.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @return a {@link com.hk.json.JsonObject} object
	 */
	public JsonObject put(String name, JsonValue value)
	{
		if (this == value)
			throw new IllegalArgumentException("Can't add this to this");
		if(name == null)
			throw new IllegalArgumentException("Cannot put null keys");

		map.put(name, value == null ? JsonNull.NULL : value);
		return this;
	}

	/**
	 * <p>put.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @param value a {@link java.lang.String} object
	 * @return a {@link com.hk.json.JsonObject} object
	 */
	public JsonObject put(String name, String value)
	{
		if(value == null)
			return put(name, JsonNull.NULL);
		else
			return put(name, new JsonString(value));
	}

	/**
	 * <p>put.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @param value a long
	 * @return a {@link com.hk.json.JsonObject} object
	 */
	public JsonObject put(String name, long value)
	{
		return put(name, new JsonNumber(value));
	}

	/**
	 * <p>put.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @param value a double
	 * @return a {@link com.hk.json.JsonObject} object
	 */
	public JsonObject put(String name, double value)
	{
		return put(name, new JsonNumber(value));
	}

	/**
	 * <p>put.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @param value a boolean
	 * @return a {@link com.hk.json.JsonObject} object
	 */
	public JsonObject put(String name, boolean value)
	{
		return put(name, JsonBoolean.valueOf(value));
	}
	
	/**
	 * <p>remove.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public JsonValue remove(String name)
	{
		return map.remove(name);
	}
	
	/**
	 * <p>removeAll.</p>
	 *
	 * @return a {@link com.hk.json.JsonObject} object
	 */
	public JsonObject removeAll()
	{
		map.clear();
		return this;
	}
	
	/**
	 * <p>isEmpty.</p>
	 *
	 * @return a boolean
	 */
	public boolean isEmpty()
	{
		return map.isEmpty();
	}
	
	/**
	 * <p>size.</p>
	 *
	 * @return a int
	 */
	public int size()
	{
		return map.size();
	}
	
	/**
	 * <p>toMap.</p>
	 *
	 * @param adapter a {@link com.hk.json.JsonAdapter} object
	 * @param <T> a T class
	 * @return a {@link java.util.Map} object
	 */
	public <T> Map<String, T> toMap(JsonAdapter<T> adapter)
	{
		Map<String, T> map = new HashMap<>();
		
		for(Map.Entry<String, JsonValue> ent : this)
			map.put(ent.getKey(), adapter.fromJson(ent.getValue()));
		
		return map;
	}
	
	/**
	 * <p>toMap.</p>
	 *
	 * @param cls a {@link java.lang.Class} object
	 * @param <T> a T class
	 * @return a {@link java.util.Map} object
	 */
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
	
	/**
	 * <p>isObject.</p>
	 *
	 * @return a boolean
	 */
	public boolean isObject()
	{
		return true;
	}
	
	/**
	 * <p>getObject.</p>
	 *
	 * @return a {@link com.hk.json.JsonObject} object
	 */
	public JsonObject getObject()
	{
		return this;
	}
	
	/**
	 * <p>keys.</p>
	 *
	 * @return a {@link java.util.Iterator} object
	 */
	public Iterator<String> keys()
	{
		return map.keySet().iterator();
	}
	
	/**
	 * <p>iterator.</p>
	 *
	 * @return a {@link java.util.Iterator} object
	 */
	public Iterator<Map.Entry<String, JsonValue>> iterator()
	{
		return map.entrySet().iterator();
	}
	
	/**
	 * <p>values.</p>
	 *
	 * @return a {@link java.util.Iterator} object
	 */
	public Iterator<JsonValue> values()
	{
		return map.values().iterator();
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonObject && Objects.equals(map, ((JsonObject) o).map);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return map.hashCode() + 34;
	}
}
