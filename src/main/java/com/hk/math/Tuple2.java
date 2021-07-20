package com.hk.math;

import java.util.Objects;

/**
 * <p>Tuple2 class.</p>
 *
 * @author theKayani
 */
public class Tuple2<T> extends Tuple1<T>
{
	T obj2;

	/**
	 * <p>Constructor for Tuple2.</p>
	 */
	public Tuple2()
	{
		super();
		obj2 = null;
	}

	/**
	 * <p>Constructor for Tuple2.</p>
	 *
	 * @param arr an array of T[] objects
	 */
	public Tuple2(T[] arr)
	{
		super(arr);
		obj2 = arr[1];
	}

	/**
	 * <p>Constructor for Tuple2.</p>
	 *
	 * @param copy a {@link com.hk.math.Tuple2} object
	 */
	public Tuple2(Tuple2<T> copy)
	{
		super(copy);
		this.obj2 = copy.obj2;
	}

	/**
	 * <p>Constructor for Tuple2.</p>
	 *
	 * @param obj1 a T object
	 * @param obj2 a T object
	 */
	public Tuple2(T obj1, T obj2)
	{
		super(obj1);
		this.obj2 = obj2;
	}

	/**
	 * <p>Constructor for Tuple2.</p>
	 *
	 * @param obj a T object
	 */
	public Tuple2(T obj)
	{
		super(obj);
		obj2 = obj;
	}

	/**
	 * <p>Setter for the field <code>obj2</code>.</p>
	 *
	 * @param obj2 a T object
	 * @return a {@link com.hk.math.Tuple1} object
	 */
	public Tuple1<T> setObj2(T obj2)
	{
		this.obj2 = obj2;
		return this;
	}

	/**
	 * <p>Getter for the field <code>obj2</code>.</p>
	 *
	 * @return a T object
	 */
	public T getObj2()
	{
		return obj2;
	}

	/**
	 * <p>getObj2Or.</p>
	 *
	 * @param def a T object
	 * @return a T object
	 */
	public T getObj2Or(T def)
	{
		return obj2 == null ? def : obj2;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return "{" + Objects.toString(obj1) + ", " + Objects.toString(obj2) + "}";
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj)
	{
		return obj != null && Tuple2.class.equals(obj.getClass()) && Objects.deepEquals(((Tuple2<?>) obj).obj1, obj1) && Objects.deepEquals(((Tuple2<?>) obj).obj2, obj2);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return Objects.hash(obj1, obj2);
	}

	/** {@inheritDoc} */
	@Override
	public Tuple2<T> copy()
	{
		return new Tuple2<T>(obj1, obj2);
	}
}
