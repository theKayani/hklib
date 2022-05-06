package com.hk.array;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * <p>ImmutableArray class.</p>
 *
 * @author theKayani
 */
public final class ImmutableArray<T> implements Iterable<T>
{
	private final Object array;
	private final Class<?> type;
	public final int length;

	/**
	 * <p>Constructor for ImmutableArray.</p>
	 *
	 * @param array an array of {@link boolean} objects
	 */
	public ImmutableArray(boolean[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = boolean.class;
	}

	/**
	 * <p>Constructor for ImmutableArray.</p>
	 *
	 * @param array an array of {@link byte} objects
	 */
	public ImmutableArray(byte[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = byte.class;
	}

	/**
	 * <p>Constructor for ImmutableArray.</p>
	 *
	 * @param array an array of {@link short} objects
	 */
	public ImmutableArray(short[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Short.class;
	}

	/**
	 * <p>Constructor for ImmutableArray.</p>
	 *
	 * @param array an array of {@link char} objects
	 */
	public ImmutableArray(char[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Character.class;
	}

	/**
	 * <p>Constructor for ImmutableArray.</p>
	 *
	 * @param array an array of {@link int} objects
	 */
	public ImmutableArray(int[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Integer.class;
	}

	/**
	 * <p>Constructor for ImmutableArray.</p>
	 *
	 * @param array an array of {@link float} objects
	 */
	public ImmutableArray(float[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Float.class;
	}

	/**
	 * <p>Constructor for ImmutableArray.</p>
	 *
	 * @param array an array of {@link long} objects
	 */
	public ImmutableArray(long[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Long.class;
	}

	/**
	 * <p>Constructor for ImmutableArray.</p>
	 *
	 * @param array an array of {@link double} objects
	 */
	public ImmutableArray(double[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Double.class;
	}

	/**
	 * <p>Constructor for ImmutableArray.</p>
	 *
	 * @param array an array of {@link java.lang.Object} objects
	 */
	public ImmutableArray(@NotNull Object[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = array.getClass().getComponentType();
	}

	/**
	 * <p>get.</p>
	 *
	 * @param index a int
	 * @return a T object
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public T get(int index)
	{
		return (T) Array.get(array, index);
	}

	/**
	 * <p>toArray.</p>
	 *
	 * @return an array of T[] objects
	 */
	@SuppressWarnings("unchecked")
	@NotNull
	public T[] toArray()
	{
		T[] arr = (T[]) Array.newInstance(type, length);
		for (int i = 0; i < length; i++)
			arr[i] = get(i);

		return arr;
	}

	/** {@inheritDoc} */
	@Override
	@NotNull
	public Iterator<T> iterator()
	{
		return new Itr();
	}

	private class Itr implements Iterator<T>
	{
		private int index;

		@Override
		public boolean hasNext()
		{
			return index < length;
		}

		@Override
		public T next()
		{
			return get(index++);
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException("This array cannot be modified.");
		}
	}
}
