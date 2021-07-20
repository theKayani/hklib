package com.hk.math;

import java.util.Objects;

/**
 * <p>Tuple1 class.</p>
 *
 * @author theKayani
 */
public class Tuple1<T>
{
	T obj1;

	/**
	 * <p>Constructor for Tuple1.</p>
	 */
	public Tuple1()
	{
		obj1 = null;
	}

	/**
	 * <p>Constructor for Tuple1.</p>
	 *
	 * @param arr an array of T[] objects
	 */
	public Tuple1(T[] arr)
	{
		obj1 = arr[0];
	}

	/**
	 * <p>Constructor for Tuple1.</p>
	 *
	 * @param copy a {@link com.hk.math.Tuple1} object
	 */
	public Tuple1(Tuple1<T> copy)
	{
		this.obj1 = copy.obj1;
	}

	/**
	 * <p>Constructor for Tuple1.</p>
	 *
	 * @param obj1 a T object
	 */
	public Tuple1(T obj1)
	{
		this.obj1 = obj1;
	}

	/**
	 * <p>Setter for the field <code>obj1</code>.</p>
	 *
	 * @param obj1 a T object
	 * @return a {@link com.hk.math.Tuple1} object
	 */
	public Tuple1<T> setObj1(T obj1)
	{
		this.obj1 = obj1;
		return this;
	}

	/**
	 * <p>Getter for the field <code>obj1</code>.</p>
	 *
	 * @return a T object
	 */
	public T getObj1()
	{
		return obj1;
	}

	/**
	 * <p>getObj1Or.</p>
	 *
	 * @param def a T object
	 * @return a T object
	 */
	public T getObj1Or(T def)
	{
		return obj1 == null ? def : obj1;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return "{" + Objects.toString(obj1) + "}";
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj)
	{
		return obj != null && Tuple1.class.equals(obj.getClass()) && Objects.deepEquals(((Tuple1<?>) obj).obj1, obj1);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return Objects.hash(this.obj1);
	}

	/**
	 * <p>copy.</p>
	 *
	 * @return a {@link com.hk.math.Tuple1} object
	 */
	public Tuple1<T> copy()
	{
		return new Tuple1<T>(obj1);
	}
}
