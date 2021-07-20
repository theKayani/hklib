package com.hk.math;

import java.util.Objects;

/**
 * <p>Tuple4 class.</p>
 *
 * @author theKayani
 */
public class Tuple4<T> extends Tuple3<T>
{
	T obj4;

	/**
	 * <p>Constructor for Tuple4.</p>
	 */
	public Tuple4()
	{
		super();
		obj4 = null;
	}

	/**
	 * <p>Constructor for Tuple4.</p>
	 *
	 * @param arr an array of T[] objects
	 */
	public Tuple4(T[] arr)
	{
		super(arr);
		obj4 = arr[3];
	}

	/**
	 * <p>Constructor for Tuple4.</p>
	 *
	 * @param copy a {@link com.hk.math.Tuple4} object
	 */
	public Tuple4(Tuple4<T> copy)
	{
		super(copy);
		this.obj4 = copy.obj4;
	}

	/**
	 * <p>Constructor for Tuple4.</p>
	 *
	 * @param obj1 a T object
	 * @param obj2 a T object
	 * @param obj3 a T object
	 * @param obj4 a T object
	 */
	public Tuple4(T obj1, T obj2, T obj3, T obj4)
	{
		super(obj1, obj2, obj3);
		this.obj4 = obj4;
	}

	/**
	 * <p>Constructor for Tuple4.</p>
	 *
	 * @param obj a T object
	 */
	public Tuple4(T obj)
	{
		super(obj);
		obj4 = obj;
	}

	/**
	 * <p>Setter for the field <code>obj4</code>.</p>
	 *
	 * @param obj4 a T object
	 * @return a {@link com.hk.math.Tuple1} object
	 */
	public Tuple1<T> setObj4(T obj4)
	{
		this.obj4 = obj4;
		return this;
	}

	/**
	 * <p>Getter for the field <code>obj4</code>.</p>
	 *
	 * @return a T object
	 */
	public T getObj4()
	{
		return obj4;
	}

	/**
	 * <p>getObj4Or.</p>
	 *
	 * @param def a T object
	 * @return a T object
	 */
	public T getObj4Or(T def)
	{
		return obj4 == null ? def : obj4;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return "{" + Objects.toString(obj1) + ", " + Objects.toString(obj2) + ", " + Objects.toString(obj3) + ", " + Objects.toString(obj4) + "}";
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj)
	{
		return obj != null && Tuple4.class.equals(obj.getClass()) && Objects.deepEquals(((Tuple4<?>) obj).obj1, obj1) && Objects.deepEquals(((Tuple4<?>) obj).obj2, obj2) && Objects.deepEquals(((Tuple4<?>) obj).obj3, obj3) && Objects.deepEquals(((Tuple4<?>) obj).obj4, obj4);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return Objects.hash(obj1, obj2, obj3, obj4);
	}

	/** {@inheritDoc} */
	@Override
	public Tuple4<T> copy()
	{
		return new Tuple4<T>(this);
	}
}
