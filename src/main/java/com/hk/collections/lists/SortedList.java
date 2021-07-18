package com.hk.collections.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class SortedList<E> extends ArrayList<E>
{
	private Comparator<E> comparator;
	private boolean reverseSorting;
	private static final long serialVersionUID = 1L;

	public SortedList()
	{
		this(null, false);
	}

	public SortedList(Comparator<E> comparator)
	{
		this(comparator, false);
	}

	public SortedList(boolean reverseSorting)
	{
		this(null, reverseSorting);
	}

	public SortedList(Comparator<E> comparator, boolean reverseSorting)
	{
		this.comparator = comparator;
		this.reverseSorting = reverseSorting;
	}

	@SuppressWarnings("unchecked")
	public SortedList<E> setReverseSorting(boolean reverseSorting)
	{
		if (this.reverseSorting != reverseSorting)
		{
			this.reverseSorting = reverseSorting;
			Object[] os = toArray();
			clear();
			for (Object o : os)
			{
				add((E) o);
			}
		}
		return this;
	}

	public SortedList<E> setComparator(Comparator<E> comparator)
	{
		this.comparator = comparator;
		return this;
	}

	public Comparator<E> getComparator()
	{
		return comparator;
	}

	public boolean isReverseSorting()
	{
		return reverseSorting;
	}

	@Override
	public boolean add(E mt)
	{
		int index = binarySearch(mt);
		super.add(index < 0 ? ~index : index, mt);
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		boolean good = true;
		Iterator<? extends E> itr = c.iterator();
		while (itr.hasNext())
		{
			good = good && add(itr.next());
		}
		return good;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		throw new UnsupportedOperationException("Sorted Lists cannot be manually set");
	}

	@Override
	public E set(int index, E element)
	{
		throw new UnsupportedOperationException("Sorted Lists cannot be manually set");
	}

	@Override
	public void add(int index, E element)
	{
		throw new UnsupportedOperationException("Sorted Lists cannot be manually set");
	}

	@SuppressWarnings("unchecked")
	private int binarySearch(E key)
	{
		int low = 0;
		int high = size() - 1;

		while (low <= high)
		{
			int mid = low + high >>> 1;
			E midVal = get(mid);
			int cmp = comparator == null ? (reverseSorting ? ((Comparable<E>) key).compareTo(midVal) : ((Comparable<E>) midVal).compareTo(key)) : (reverseSorting ? comparator.compare(key, midVal) : comparator.compare(midVal, key));

			if (cmp < 0)
			{
				low = mid + 1;
			}
			else if (cmp > 0)
			{
				high = mid - 1;
			}
			else return mid; // key found
		}
		return -(low + 1); // key not found
	}
}
