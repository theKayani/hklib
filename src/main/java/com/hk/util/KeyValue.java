package com.hk.util;

import java.util.Objects;

/**
 * <p>KeyValue class.</p>
 *
 * @author theKayani
 */
public class KeyValue<T> implements Comparable<KeyValue<T>>
{
	public final String key;
	public T value;
	
	/**
	 * <p>Constructor for KeyValue.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 */
	public KeyValue(String key)
	{
		this(key, null);
	}
	
	/**
	 * <p>Constructor for KeyValue.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param value a T object
	 */
	public KeyValue(String key, T value)
	{
		this.key = key;
		this.value = value;
	}
	
	/**
	 * <p>hasKey.</p>
	 *
	 * @return a boolean
	 */
	public boolean hasKey()
	{
		return key != null;
	}
	
	/**
	 * <p>Getter for the field <code>key</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getKey()
	{
		return key;
	}
	
	/**
	 * <p>setNull.</p>
	 *
	 * @return a {@link com.hk.util.KeyValue} object
	 */
	public KeyValue<T> setNull()
	{
		value = null;
		return this;
	}
	
	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a T object
	 * @return a {@link com.hk.util.KeyValue} object
	 */
	public KeyValue<T> setValue(T value)
	{
		this.value = value;
		return this;
	}
	
	/**
	 * <p>hasValue.</p>
	 *
	 * @return a boolean
	 */
	public boolean hasValue()
	{
		return value != null;
	}
	
	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a T object
	 */
	public T getValue()
	{
		return value;
	}
	
	/** {@inheritDoc} */
	@Override
	public int compareTo(KeyValue<T> o)
	{
		if(o == null)
			return 1;
		else if(Objects.equals(key, o.key))
			return 0;
		else
			return key == null ? -1 : key.compareTo(o.key);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = hash * 31 + (key == null ? 0 : key.hashCode());
		hash = hash * 31 + (value == null ? 0 : value.hashCode());
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof KeyValue)
		{
			KeyValue<?> kv = (KeyValue<?>) obj;
			return Objects.equals(key, kv.key) && Objects.equals(value, kv.value);
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return key + "=" + value;
	}
}
