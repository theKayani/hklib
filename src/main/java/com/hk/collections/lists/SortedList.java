package com.hk.collections.lists;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * <p>SortedList class.</p>
 *
 * @author theKayani
 */
public class SortedList<E> extends ArrayList<E>
{
	private Comparator<E> comparator;
	private boolean reverseSorting;
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for SortedList.</p>
	 */
	public SortedList()
	{
		this(null, false);
	}

	/**
	 * <p>Constructor for SortedList.</p>
	 *
	 * @param comparator a {@link java.util.Comparator} object
	 */
	public SortedList(@NotNull Comparator<E> comparator)
	{
		this(comparator, false);
	}

	/**
	 * <p>Constructor for SortedList.</p>
	 *
	 * @param reverseSorting a boolean
	 */
	public SortedList(boolean reverseSorting)
	{
		this(null, reverseSorting);
	}

	/**
	 * <p>Constructor for SortedList.</p>
	 *
	 * @param comparator a {@link java.util.Comparator} object
	 * @param reverseSorting a boolean
	 */
	public SortedList(@NotNull Comparator<E> comparator, boolean reverseSorting)
	{
		this.comparator = comparator;
		this.reverseSorting = reverseSorting;
	}

	/**
	 * <p>Setter for the field <code>reverseSorting</code>.</p>
	 *
	 * @param reverseSorting a boolean
	 * @return a {@link com.hk.collections.lists.SortedList} object
	 */
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

	/**
	 * <p>Setter for the field <code>comparator</code>.</p>
	 *
	 * @param comparator a {@link java.util.Comparator} object
	 * @return a {@link com.hk.collections.lists.SortedList} object
	 */
	public SortedList<E> setComparator(Comparator<E> comparator)
	{
		this.comparator = comparator;
		return this;
	}

	/**
	 * <p>Getter for the field <code>comparator</code>.</p>
	 *
	 * @return a {@link java.util.Comparator} object
	 */
	public Comparator<E> getComparator()
	{
		return comparator;
	}

	/**
	 * <p>isReverseSorting.</p>
	 *
	 * @return a boolean
	 */
	public boolean isReverseSorting()
	{
		return reverseSorting;
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(E mt)
	{
		int index = binarySearch(mt);
		super.add(index < 0 ? ~index : index, mt);
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		boolean good = true;
		for (E e : c)
			good = good && add(e);

		return good;
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		throw new UnsupportedOperationException("Sorted Lists cannot be manually set");
	}

	/** {@inheritDoc} */
	@Override
	public E set(int index, E element)
	{
		throw new UnsupportedOperationException("Sorted Lists cannot be manually set");
	}

	/** {@inheritDoc} */
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
