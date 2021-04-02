package com.hk.math;

import java.util.Objects;

public class Tuple4<T> extends Tuple3<T>
{
	T obj4;

	public Tuple4()
	{
		super();
		obj4 = null;
	}

	public Tuple4(T[] arr)
	{
		super(arr);
		obj4 = arr[3];
	}

	public Tuple4(Tuple4<T> copy)
	{
		super(copy);
		this.obj4 = copy.obj4;
	}

	public Tuple4(T obj1, T obj2, T obj3, T obj4)
	{
		super(obj1, obj2, obj3);
		this.obj4 = obj4;
	}

	public Tuple4(T obj)
	{
		super(obj);
		obj4 = obj;
	}

	public Tuple1<T> setObj4(T obj4)
	{
		this.obj4 = obj4;
		return this;
	}

	public T getObj4()
	{
		return obj4;
	}

	public T getObj4Or(T def)
	{
		return obj4 == null ? def : obj4;
	}

	@Override
	public String toString()
	{
		return "{" + Objects.toString(obj1) + ", " + Objects.toString(obj2) + ", " + Objects.toString(obj3) + ", " + Objects.toString(obj4) + "}";
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj != null && Tuple4.class.equals(obj.getClass()) && Objects.deepEquals(((Tuple4<?>) obj).obj1, obj1) && Objects.deepEquals(((Tuple4<?>) obj).obj2, obj2) && Objects.deepEquals(((Tuple4<?>) obj).obj3, obj3) && Objects.deepEquals(((Tuple4<?>) obj).obj4, obj4);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(obj1, obj2, obj3, obj4);
	}

	@Override
	public Tuple4<T> copy()
	{
		return new Tuple4<T>(this);
	}
}
