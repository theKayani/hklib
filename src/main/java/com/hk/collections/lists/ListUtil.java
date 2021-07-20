package com.hk.collections.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>ListUtil class.</p>
 *
 * @author theKayani
 */
public class ListUtil
{
	/**
	 * <p>newArrayList.</p>
	 *
	 * @param <T> a T class
	 * @return a {@link java.util.ArrayList} object
	 */
	public static <T> ArrayList<T> newArrayList()
	{
		return new ArrayList<>();
	}

	/**
	 * <p>newArrayList.</p>
	 *
	 * @param elements a T object
	 * @param <T> a T class
	 * @return a {@link java.util.ArrayList} object
	 */
	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> newArrayList(T... elements)
	{
		ArrayList<T> list = newArrayList();
		addElements(list, elements);
		return list;
	}

	/**
	 * <p>newArrayListWith.</p>
	 *
	 * @param elements an array of T[] objects
	 * @param <T> a T class
	 * @return a {@link java.util.ArrayList} object
	 */
	public static <T> ArrayList<T> newArrayListWith(T[] elements)
	{
		ArrayList<T> list = newArrayList();
		addElements(list, elements);
		return list;
	}

	/**
	 * <p>newArrayList.</p>
	 *
	 * @param capacity a int
	 * @param <T> a T class
	 * @return a {@link java.util.ArrayList} object
	 */
	public static <T> ArrayList<T> newArrayList(int capacity)
	{
		return new ArrayList<>(capacity);
	}

	/**
	 * <p>newArrayList.</p>
	 *
	 * @param c a {@link java.util.Collection} object
	 * @param <T> a T class
	 * @return a {@link java.util.ArrayList} object
	 */
	public static <T> ArrayList<T> newArrayList(Collection<? extends T> c)
	{
		return new ArrayList<>(c);
	}

	/**
	 * <p>newLinkedList.</p>
	 *
	 * @param <T> a T class
	 * @return a {@link java.util.LinkedList} object
	 */
	public static <T> LinkedList<T> newLinkedList()
	{
		return new LinkedList<>();
	}

	/**
	 * <p>newLinkedList.</p>
	 *
	 * @param c a {@link java.util.Collection} object
	 * @param <T> a T class
	 * @return a {@link java.util.LinkedList} object
	 */
	public static <T> LinkedList<T> newLinkedList(Collection<? extends T> c)
	{
		return new LinkedList<>(c);
	}

	/**
	 * <p>newImmutableList.</p>
	 *
	 * @param c a {@link java.util.List} object
	 * @param <T> a T class
	 * @return a {@link com.hk.collections.lists.ImmutableList} object
	 */
	public static <T> ImmutableList<T> newImmutableList(List<? extends T> c)
	{
		return new ImmutableList<>(c);
	}

	/**
	 * <p>newImmutableListBuilder.</p>
	 *
	 * @param <T> a T class
	 * @return a {@link com.hk.collections.lists.ImmutableList.Builder} object
	 */
	public static <T> ImmutableList.Builder<T> newImmutableListBuilder()
	{
		return new ImmutableList.Builder<>();
	}

	/**
	 * <p>newSortedList.</p>
	 *
	 * @param <T> a T class
	 * @return a {@link com.hk.collections.lists.SortedList} object
	 */
	public static <T> SortedList<T> newSortedList()
	{
		return new SortedList<>();
	}

	/**
	 * <p>newSortedList.</p>
	 *
	 * @param comparator a {@link java.util.Comparator} object
	 * @param <T> a T class
	 * @return a {@link com.hk.collections.lists.SortedList} object
	 */
	public static <T> SortedList<T> newSortedList(Comparator<T> comparator)
	{
		return new SortedList<>(comparator);
	}

	/**
	 * <p>newSortedList.</p>
	 *
	 * @param reverseSorting a boolean
	 * @param <T> a T class
	 * @return a {@link com.hk.collections.lists.SortedList} object
	 */
	public static <T> SortedList<T> newSortedList(boolean reverseSorting)
	{
		return new SortedList<>(reverseSorting);
	}

	/**
	 * <p>newSortedList.</p>
	 *
	 * @param comparator a {@link java.util.Comparator} object
	 * @param reverseSorting a boolean
	 * @param <T> a T class
	 * @return a {@link com.hk.collections.lists.SortedList} object
	 */
	public static <T> SortedList<T> newSortedList(Comparator<T> comparator, boolean reverseSorting)
	{
		return new SortedList<>(comparator, reverseSorting);
	}

	/**
	 * <p>swap.</p>
	 *
	 * @param list a {@link java.util.List} object
	 * @param index1 a int
	 * @param index2 a int
	 * @param <T> a T class
	 * @return a {@link java.util.List} object
	 */
	public static <T> List<T> swap(List<T> list, int index1, int index2)
	{
		list.set(index1, list.set(index2, list.get(index1)));
		return list;
	}

	/**
	 * <p>addElements.</p>
	 *
	 * @param list a {@link java.util.List} object
	 * @param array an array of T[] objects
	 * @param <T> a T class
	 * @return a boolean
	 */
	public static <T> boolean addElements(List<T> list, T[] array)
	{
		boolean good = true;
		for (T obj : array)
		{
			good &= list.add(obj);
		}
		return good;
	}

	/**
	 * <p>addAll.</p>
	 *
	 * @param list a {@link java.util.List} object
	 * @param array a T object
	 * @param <T> a T class
	 * @return a boolean
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean addAll(List<T> list, T... array)
	{
		return addElements(list, array);
	}

	private ListUtil()
	{}
}
