package com.hk.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * <p>JsonArray class.</p>
 *
 * @author theKayani
 */
public class JsonArray extends JsonValue implements Iterable<JsonValue>
{
	public final List<JsonValue> list;

	/**
	 * <p>Constructor for JsonArray.</p>
	 */
	public JsonArray()
	{
		list = new ArrayList<>();
	}

	/**
	 * <p>Constructor for JsonArray.</p>
	 *
	 * @param initialCapacity a int
	 */
	public JsonArray(int initialCapacity)
	{
		list = new ArrayList<>(initialCapacity);
	}

	/**
	 * <p>Constructor for JsonArray.</p>
	 *
	 * @param values a {@link java.util.List} object
	 */
	public JsonArray(List<JsonValue> values)
	{
		this.list = new ArrayList<>(values);
	}

	/**
	 * <p>Constructor for JsonArray.</p>
	 *
	 * @param copy a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray(JsonArray copy)
	{
		list = new ArrayList<>(copy.list);
	}

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link com.hk.json.JsonType} object
	 */
	public JsonType getType()
	{
		return JsonType.ARRAY;
	}

	/**
	 * <p>add.</p>
	 *
	 * @param obj a T object
	 * @param adapter a {@link com.hk.json.JsonAdapter} object
	 * @param <T> a T class
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public <T> JsonArray add(T obj, JsonAdapter<T> adapter)
	{
		return add(adapter.toJson(obj));
	}

	/**
	 * <p>add.</p>
	 *
	 * @param obj a {@link java.lang.Object} object
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(Object obj)
	{
		return add(Json.toJson(obj));
	}

	/**
	 * <p>add.</p>
	 *
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(JsonValue value)
	{
		if (this == value)
			throw new IllegalArgumentException("Can't add this to this");

		list.add(value == null ? JsonNull.NULL : value);
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(String value)
	{
		return add(value == null ? null : new JsonString(value));
	}

	/**
	 * <p>add.</p>
	 *
	 * @param value a double
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(double value)
	{
		return add(new JsonNumber(value));
	}

	/**
	 * <p>add.</p>
	 *
	 * @param value a long
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(long value)
	{
		return add(new JsonNumber(value));
	}

	/**
	 * <p>add.</p>
	 *
	 * @param value a boolean
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(boolean value)
	{
		return add(JsonBoolean.valueOf(value));
	}

	/**
	 * <p>addAll.</p>
	 *
	 * @param vals a {@link java.lang.Iterable} object
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray addAll(Iterable<JsonValue> vals)
	{
		for(JsonValue val : vals)
			add(val);
		return this;
	}

	/**
	 * <p>addAll.</p>
	 *
	 * @param vals a {@link java.lang.Iterable} object
	 * @param adapter a {@link com.hk.json.JsonAdapter} object
	 * @param <T> a T class
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public <T> JsonArray addAll(Iterable<T> vals, JsonAdapter<T> adapter)
	{
		for(T val : vals)
			add(adapter.toJson(val));
		return this;
	}

	/**
	 * <p>addAll.</p>
	 *
	 * @param vals a {@link java.lang.Iterable} object
	 * @param cls a {@link java.lang.Class} object
	 * @param <T> a T class
	 * @return a {@link com.hk.json.JsonArray} object
	 */
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

	/**
	 * <p>addAll.</p>
	 *
	 * @param vals a {@link com.hk.json.JsonValue} object
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray addAll(JsonValue... vals)
	{
		for(JsonValue val : vals)
			add(val);
		return this;
	}

	/**
	 * <p>addAll.</p>
	 *
	 * @param vals a {@link java.lang.Object} object
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray addAll(Object... vals)
	{
		for(Object val : vals)
			add(Json.toJson(val));

		return this;
	}

	/**
	 * <p>size.</p>
	 *
	 * @return a int
	 */
	public int size()
	{
		return list.size();
	}

	/**
	 * <p>get.</p>
	 *
	 * @param i a int
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public JsonValue get(int i)
	{
		return list.get(i);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param i a int
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(int i, JsonValue value)
	{
		list.add(i, value == null ? JsonNull.NULL : value);
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @param i a int
	 * @param value a T object
	 * @param adapter a {@link com.hk.json.JsonAdapter} object
	 * @param <T> a T class
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public <T> JsonArray add(int i, T value, JsonAdapter<T> adapter)
	{
		return add(i, adapter.toJson(value));
	}

	/**
	 * <p>add.</p>
	 *
	 * @param i a int
	 * @param value a {@link java.lang.Object} object
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(int i, Object value)
	{
		return add(i, Json.toJson(value));
	}

	/**
	 * <p>add.</p>
	 *
	 * @param i a int
	 * @param value a {@link java.lang.String} object
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(int i, String value)
	{
		return add(i, new JsonString(value));
	}

	/**
	 * <p>add.</p>
	 *
	 * @param i a int
	 * @param value a double
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(int i, double value)
	{
		return add(i, new JsonNumber(value));
	}

	/**
	 * <p>add.</p>
	 *
	 * @param i a int
	 * @param value a long
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(int i, long value)
	{
		return add(i, new JsonNumber(value));
	}

	/**
	 * <p>add.</p>
	 *
	 * @param i a int
	 * @param value a boolean
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray add(int i, boolean value)
	{
		return add(i, JsonBoolean.valueOf(value));
	}

	/**
	 * <p>set.</p>
	 *
	 * @param i a int
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public JsonValue set(int i, JsonValue value)
	{
		return list.set(i, value == null ? JsonNull.NULL : value);
	}

	/**
	 * <p>set.</p>
	 *
	 * @param i a int
	 * @param value a T object
	 * @param adapter a {@link com.hk.json.JsonAdapter} object
	 * @param <T> a T class
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public <T> JsonValue set(int i, T value, JsonAdapter<T> adapter)
	{
		return set(i, adapter.toJson(value));
	}

	/**
	 * <p>set.</p>
	 *
	 * @param i a int
	 * @param value a {@link java.lang.Object} object
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public JsonValue set(int i, Object value)
	{
		return set(i, Json.toJson(value));
	}

	/**
	 * <p>set.</p>
	 *
	 * @param i a int
	 * @param value a {@link java.lang.String} object
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public JsonValue set(int i, String value)
	{
		return set(i, new JsonString(value));
	}

	/**
	 * <p>set.</p>
	 *
	 * @param i a int
	 * @param value a double
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public JsonValue set(int i, double value)
	{
		return set(i, new JsonNumber(value));
	}

	/**
	 * <p>set.</p>
	 *
	 * @param i a int
	 * @param value a long
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public JsonValue set(int i, long value)
	{
		return set(i, new JsonNumber(value));
	}

	/**
	 * <p>set.</p>
	 *
	 * @param i a int
	 * @param value a boolean
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public JsonValue set(int i, boolean value)
	{
		return set(i, JsonBoolean.valueOf(value));
	}

	/**
	 * <p>remove.</p>
	 *
	 * @param i a int
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	public JsonValue remove(int i)
	{
		return list.remove(i);
	}

	/**
	 * <p>removeAll.</p>
	 *
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray removeAll()
	{
		list.clear();
		return this;
	}

	/**
	 * <p>isEmpty.</p>
	 *
	 * @return a boolean
	 */
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	/**
	 * <p>contains.</p>
	 *
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @return a boolean
	 */
	public boolean contains(JsonValue value)
	{
		return list.contains(value);
	}

	/**
	 * <p>toList.</p>
	 *
	 * @param adapter a {@link com.hk.json.JsonAdapter} object
	 * @param <T> a T class
	 * @return a {@link java.util.List} object
	 */
	public <T> List<T> toList(JsonAdapter<T> adapter)
	{
		List<T> list = new ArrayList<>(size());

		for(JsonValue value : this.list)
			list.add(adapter.fromJson(value));

		return list;
	}

	/**
	 * <p>toList.</p>
	 *
	 * @param cls a {@link java.lang.Class} object
	 * @param <T> a T class
	 * @return a {@link java.util.List} object
	 */
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

	/**
	 * <p>isArray.</p>
	 *
	 * @return a boolean
	 */
	public boolean isArray()
	{
		return true;
	}

	/**
	 * <p>getArray.</p>
	 *
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	public JsonArray getArray()
	{
		return this;
	}

	/**
	 * <p>listIterator.</p>
	 *
	 * @return a {@link java.util.ListIterator} object
	 */
	public ListIterator<JsonValue> listIterator()
	{
		return list.listIterator();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<JsonValue> iterator()
	{
		return list.iterator();
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonArray && Objects.equals(list, ((JsonArray) o).list);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return 53 + list.hashCode();
	}
}