package com.hk.math;

import java.util.Objects;

public class Tuple3<T> extends Tuple2<T>
{
	T obj3;

	public Tuple3()
	{
		super();
		obj3 = null;
	}

	public Tuple3(T[] arr)
	{
		super(arr);
		obj3 = arr[2];
	}

	public Tuple3(Tuple3<T> copy)
	{
		super(copy);
		this.obj3 = copy.obj3;
	}

	public Tuple3(T obj1, T obj2, T obj3)
	{
		super(obj1, obj2);
		this.obj3 = obj3;
	}

	public Tuple3(T obj)
	{
		super(obj);
		obj3 = obj;
	}

	public Tuple1<T> setObj3(T obj3)
	{
		this.obj3 = obj3;
		return this;
	}

	public T getObj3()
	{
		return obj3;
	}

	public T getObj3Or(T def)
	{
		return obj3 == null ? def : obj3;
	}

	@Override
	public String toString()
	{
		return "{" + Objects.toString(obj1) + ", " + Objects.toString(obj2) + ", " + Objects.toString(obj3) + "}";
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj != null && Tuple3.class.equals(obj.getClass()) && Objects.deepEquals(((Tuple3<?>) obj).obj1, obj1) && Objects.deepEquals(((Tuple3<?>) obj).obj2, obj2) && Objects.deepEquals(((Tuple3<?>) obj).obj3, obj3);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(obj1, obj2, obj3);
	}

	@Override
	public Tuple3<T> copy()
	{
		return new Tuple3<T>(this);
	}
}
