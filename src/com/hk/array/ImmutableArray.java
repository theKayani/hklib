package com.hk.array;

import java.lang.reflect.Array;
import java.util.Iterator;

public final class ImmutableArray<T> implements Iterable<T>
{
	private final Object array;
	private final Class<?> type;
	public final int length;

	public ImmutableArray(boolean[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = boolean.class;
	}

	public ImmutableArray(byte[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = byte.class;
	}

	public ImmutableArray(short[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Short.class;
	}

	public ImmutableArray(char[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Character.class;
	}

	public ImmutableArray(int[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Integer.class;
	}

	public ImmutableArray(float[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Float.class;
	}

	public ImmutableArray(long[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Long.class;
	}

	public ImmutableArray(double[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = Double.class;
	}

	public ImmutableArray(Object[] array)
	{
		this.array = array;
		this.length = array.length;
		this.type = array.getClass().getComponentType();
	}

	@SuppressWarnings("unchecked")
	public T get(int index)
	{
		return (T) Array.get(array, index);
	}

	@SuppressWarnings("unchecked")
	public T[] toArray()
	{
		T[] arr = (T[]) Array.newInstance(type, length);
		for (int i = 0; i < length; i++)
		{
			arr[i] = get(i);
		}
		return arr;
	}

	@Override
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
