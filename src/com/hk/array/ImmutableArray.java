package com.hk.array;

import java.lang.reflect.Array;
import java.util.Iterator;

public final class ImmutableArray<T> implements Iterable<T>
{
	private final Object array;
	public final int length;

	public ImmutableArray(Object array)
	{
		if (array.getClass().getComponentType() != null)
		{
			this.array = array;
			this.length = Array.getLength(array);
		}
		else
		{
			throw new IllegalArgumentException("array must actually be an array, " + array);
		}
	}

	@SuppressWarnings("unchecked")
	public T get(int index)
	{
		return (T) Array.get(array, index);
	}

	@SuppressWarnings("unchecked")
	public T[] toArray()
	{
		T[] arr = (T[]) Array.newInstance(array.getClass().getComponentType(), length);
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
