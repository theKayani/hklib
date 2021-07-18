package com.hk.util;

import java.util.Objects;

public class KeyValue<T> implements Comparable<KeyValue<T>>
{
	public final String key;
	public T value;
	
	public KeyValue(String key)
	{
		this(key, null);
	}
	
	public KeyValue(String key, T value)
	{
		this.key = key;
		this.value = value;
	}
	
	public boolean hasKey()
	{
		return key != null;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public KeyValue<T> setNull()
	{
		value = null;
		return this;
	}
	
	public KeyValue<T> setValue(T value)
	{
		this.value = value;
		return this;
	}
	
	public boolean hasValue()
	{
		return value != null;
	}
	
	public T getValue()
	{
		return value;
	}
	
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

	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = hash * 31 + (key == null ? 0 : key.hashCode());
		hash = hash * 31 + (value == null ? 0 : value.hashCode());
		return hash;
	}

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

	@Override
	public String toString()
	{
		return key + "=" + value;
	}
}