package com.hk.math;

import java.util.Objects;

/**
 * <p>Tuple3 class.</p>
 *
 * @author theKayani
 */
public class Tuple3<T> extends Tuple2<T>
{
	T obj3;

	/**
	 * <p>Constructor for Tuple3.</p>
	 */
	public Tuple3()
	{
		super();
		obj3 = null;
	}

	/**
	 * <p>Constructor for Tuple3.</p>
	 *
	 * @param arr an array of T[] objects
	 */
	public Tuple3(T[] arr)
	{
		super(arr);
		obj3 = arr[2];
	}

	/**
	 * <p>Constructor for Tuple3.</p>
	 *
	 * @param copy a {@link com.hk.math.Tuple3} object
	 */
	public Tuple3(Tuple3<T> copy)
	{
		super(copy);
		this.obj3 = copy.obj3;
	}

	/**
	 * <p>Constructor for Tuple3.</p>
	 *
	 * @param obj1 a T object
	 * @param obj2 a T object
	 * @param obj3 a T object
	 */
	public Tuple3(T obj1, T obj2, T obj3)
	{
		super(obj1, obj2);
		this.obj3 = obj3;
	}

	/**
	 * <p>Constructor for Tuple3.</p>
	 *
	 * @param obj a T object
	 */
	public Tuple3(T obj)
	{
		super(obj);
		obj3 = obj;
	}

	/**
	 * <p>Setter for the field <code>obj3</code>.</p>
	 *
	 * @param obj3 a T object
	 * @return a {@link com.hk.math.Tuple1} object
	 */
	public Tuple1<T> setObj3(T obj3)
	{
		this.obj3 = obj3;
		return this;
	}

	/**
	 * <p>Getter for the field <code>obj3</code>.</p>
	 *
	 * @return a T object
	 */
	public T getObj3()
	{
		return obj3;
	}

	/**
	 * <p>getObj3Or.</p>
	 *
	 * @param def a T object
	 * @return a T object
	 */
	public T getObj3Or(T def)
	{
		return obj3 == null ? def : obj3;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return "{" + Objects.toString(obj1) + ", " + Objects.toString(obj2) + ", " + Objects.toString(obj3) + "}";
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj)
	{
		return obj != null && Tuple3.class.equals(obj.getClass()) && Objects.deepEquals(((Tuple3<?>) obj).obj1, obj1) && Objects.deepEquals(((Tuple3<?>) obj).obj2, obj2) && Objects.deepEquals(((Tuple3<?>) obj).obj3, obj3);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return Objects.hash(obj1, obj2, obj3);
	}

	/** {@inheritDoc} */
	@Override
	public Tuple3<T> copy()
	{
		return new Tuple3<T>(this);
	}
}
