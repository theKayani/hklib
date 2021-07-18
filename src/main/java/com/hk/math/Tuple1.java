package com.hk.math;

import java.util.Objects;

public class Tuple1<T>
{
	T obj1;

	public Tuple1()
	{
		obj1 = null;
	}

	public Tuple1(T[] arr)
	{
		obj1 = arr[0];
	}

	public Tuple1(Tuple1<T> copy)
	{
		this.obj1 = copy.obj1;
	}

	public Tuple1(T obj1)
	{
		this.obj1 = obj1;
	}

	public Tuple1<T> setObj1(T obj1)
	{
		this.obj1 = obj1;
		return this;
	}

	public T getObj1()
	{
		return obj1;
	}

	public T getObj1Or(T def)
	{
		return obj1 == null ? def : obj1;
	}

	@Override
	public String toString()
	{
		return "{" + Objects.toString(obj1) + "}";
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj != null && Tuple1.class.equals(obj.getClass()) && Objects.deepEquals(((Tuple1<?>) obj).obj1, obj1);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.obj1);
	}

	public Tuple1<T> copy()
	{
		return new Tuple1<T>(obj1);
	}
}
