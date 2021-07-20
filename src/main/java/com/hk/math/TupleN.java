package com.hk.math;

import java.util.Arrays;
import java.util.Objects;
import com.hk.array.ArrayUtil;

/**
 * <p>TupleN class.</p>
 *
 * @author theKayani
 */
public class TupleN<T>
{
	T[] arr;

	/**
	 * <p>Constructor for TupleN.</p>
	 */
	public TupleN()
	{
		arr = null;
	}

	/**
	 * <p>Constructor for TupleN.</p>
	 *
	 * @param size a int
	 */
	public TupleN(int size)
	{
		arr = ArrayUtil.toArray((T) null);
		arr = ArrayUtil.growArrayBy(arr, size - 1);
	}

	/**
	 * <p>Constructor for TupleN.</p>
	 *
	 * @param arr an array of T[] objects
	 */
	public TupleN(T[] arr)
	{
		this.arr = arr;
	}

	@SuppressWarnings("unchecked")
	/**
	 * <p>Constructor for TupleN.</p>
	 *
	 * @param obj1 a T object
	 * @param arr a T object
	 */
	public TupleN(T obj1, T... arr)
	{
		this(arr.length + 1);
		for (int i = 0; i <= arr.length; i++)
		{
			if (i == 0) this.arr[0] = obj1;
			else
			{
				this.arr[i] = arr[i - 1];
			}
		}
	}

	/**
	 * <p>size.</p>
	 *
	 * @return a int
	 */
	public int size()
	{
		return arr.length;
	}

	/**
	 * <p>getObj.</p>
	 *
	 * @param index a int
	 * @return a T object
	 */
	public T getObj(int index)
	{
		return arr[index];
	}

	/**
	 * <p>getObjOr.</p>
	 *
	 * @param index a int
	 * @param def a T object
	 * @return a T object
	 */
	public T getObjOr(int index, T def)
	{
		return getObj(index) == null ? def : getObj(index);
	}

	/**
	 * <p>setObj.</p>
	 *
	 * @param index a int
	 * @param obj a T object
	 * @return a {@link com.hk.math.TupleN} object
	 */
	public TupleN<T> setObj(int index, T obj)
	{
		arr[index] = obj;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		String str = Arrays.toString(arr);
		return "{" + str.substring(1, str.length() - 2) + "}";
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return Arrays.hashCode(arr);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj)
	{
		return obj != null && TupleN.class.equals(obj.getClass()) && Objects.deepEquals(this.arr, ((TupleN<?>) obj).arr);
	}
}
