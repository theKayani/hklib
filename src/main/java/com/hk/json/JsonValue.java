package com.hk.json;

import org.jetbrains.annotations.NotNull;

/**
 * <p>Abstract JsonValue class.</p>
 *
 * @author theKayani
 */
public abstract class JsonValue
{
	JsonValue()
	{}

	/**
	 * <p>isObject.</p>
	 *
	 * @return a boolean
	 */
	public boolean isObject()
	{
		return false;
	}

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link com.hk.json.JsonType} object
	 */
	@NotNull
	public abstract JsonType getType();

	/**
	 * <p>getObject.</p>
	 *
	 * @return a {@link com.hk.json.JsonObject} object
	 */
	@NotNull
	public JsonObject getObject()
	{
		throw new IllegalStateException("not an object");
	}

	/**
	 * <p>isArray.</p>
	 *
	 * @return a boolean
	 */
	public boolean isArray()
	{
		return false;
	}

	/**
	 * <p>getArray.</p>
	 *
	 * @return a {@link com.hk.json.JsonArray} object
	 */
	@NotNull
	public JsonArray getArray()
	{
		throw new IllegalStateException("not an array");
	}

	/**
	 * <p>isString.</p>
	 *
	 * @return a boolean
	 */
	public boolean isString()
	{
		return false;
	}

	/**
	 * <p>getString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	@NotNull
	public String getString()
	{
		throw new IllegalStateException("not a string");
	}

	/**
	 * <p>isNumber.</p>
	 *
	 * @return a boolean
	 */
	public boolean isNumber()
	{
		return false;
	}

	/**
	 * <p>getNumber.</p>
	 *
	 * @return a {@link java.lang.Number} object
	 */
	@NotNull
	public Number getNumber()
	{
		throw new IllegalStateException("not a number");
	}

	/**
	 * <p>isBoolean.</p>
	 *
	 * @return a boolean
	 */
	public boolean isBoolean()
	{
		return false;
	}

	/**
	 * <p>getBoolean.</p>
	 *
	 * @return a boolean
	 */
	public boolean getBoolean()
	{
		throw new IllegalStateException("not a boolean");
	}

	/**
	 * <p>is.</p>
	 *
	 * @param cls a {@link java.lang.Class} object
	 * @param adapter a {@link com.hk.json.JsonAdapter} object
	 * @param <T> a T class
	 * @return a boolean
	 */
	public <T> boolean is(@NotNull Class<T> cls, @NotNull JsonAdapter<?> adapter)
	{
		return adapter.getObjClass().isAssignableFrom(cls);
	}

	/**
	 * <p>is.</p>
	 *
	 * @param cls a {@link java.lang.Class} object
	 * @param <T> a T class
	 * @return a boolean
	 */
	public <T> boolean is(@NotNull Class<T> cls)
	{
		for(JsonAdapter<?> adapter : Json.globalAdapters)
		{
			if(is(cls, adapter))
				return true;
		}
		return false;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param adapter a {@link com.hk.json.JsonAdapter} object
	 * @param <T> a T class
	 * @return a T object
	 * @throws com.hk.json.JsonAdaptationException if any.
	 */
	public <T> T get(@NotNull JsonAdapter<T> adapter) throws JsonAdaptationException
	{
		return adapter.fromJson(this);
	}

	/**
	 * <p>get.</p>
	 *
	 * @param cls a {@link java.lang.Class} object
	 * @param <T> a T class
	 * @return a T object
	 * @throws com.hk.json.JsonAdaptationException if any.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(@NotNull Class<T> cls) throws JsonAdaptationException
	{
		for(JsonAdapter<?> adapter : Json.globalAdapters)
		{
			if(is(cls, adapter))
				return (T) adapter.fromJson(this);
		}
		throw new JsonAdaptationException("No adapter for " + cls.getName());
	}

	/**
	 * <p>isNull.</p>
	 *
	 * @return a boolean
	 */
	public boolean isNull()
	{
		return false;
	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	@NotNull
	public String toString()
	{
		return Json.writePretty(this);
	}
}