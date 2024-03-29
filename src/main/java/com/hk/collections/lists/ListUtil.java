package com.hk.collections.lists;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * This class contains helper methods that create and manipulate various List classes. Such as...
 * <ul>
 *     <li>{@link java.util.ArrayList}</li>
 *     <li>{@link java.util.LinkedList}</li>
 *     <li>{@link com.hk.collections.lists.SortedList}</li>
 *     <li>{@link com.hk.collections.lists.ImmutableList}</li>
 *     <li>{@link com.hk.collections.lists.LockableList}</li>
 * </ul>
 *
 * @author theKayani
 */
public class ListUtil
{
	/**
	 * Creates a new array list. Similar to the constructor
	 *
	 * @param <T> a T class
	 * @return a new {@link java.util.ArrayList} object
	 */
	@NotNull
	public static <T> ArrayList<T> newArrayList()
	{
		return new ArrayList<>();
	}

	/**
	 * Creates a new array list with a list of objects in it.
	 *
	 * @param elements a list of T objects
	 * @param <T> a T class
	 * @return a new {@link java.util.ArrayList} object
	 */
	@SuppressWarnings("unchecked")
	@NotNull
	public static <T> ArrayList<T> newArrayList(T... elements)
	{
		ArrayList<T> list = newArrayList();
		addAll(list, elements);
		return list;
	}

	/**
	 * Creates a new array list with the array of objects in it.
	 *
	 * @param elements an array of T[] objects
	 * @param <T> a T class
	 * @return a new {@link java.util.ArrayList} object
	 */
	@NotNull
	public static <T> ArrayList<T> newArrayListWith(@NotNull T[] elements)
	{
		ArrayList<T> list = newArrayList();
		addAll(list, elements);
		return list;
	}

	/**
	 * Creates a new, empty, array list with an initial specified capacity.
	 *
	 * @param initialCapacity initial capacity for array list
	 * @param <T> a T class
	 * @return a new {@link java.util.ArrayList} object
	 */
	@NotNull
	public static <T> ArrayList<T> newArrayList(int initialCapacity)
	{
		return new ArrayList<>(initialCapacity);
	}

	/**
	 * Creates a new array list with the given collection added to it.
	 *
	 * @param c a {@link java.util.Collection} of T objects to add
	 * @param <T> a T class
	 * @return a new {@link java.util.ArrayList} object
	 */
	@NotNull
	public static <T> ArrayList<T> newArrayList(@NotNull Collection<? extends T> c)
	{
		return new ArrayList<>(c);
	}

	/**
	 * <p>newLinkedList.</p>
	 *
	 * @param <T> a T class
	 * @return a {@link java.util.LinkedList} object
	 */
	@NotNull
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
	@NotNull
	public static <T> LinkedList<T> newLinkedList(@NotNull Collection<? extends T> c)
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
	@NotNull
	public static <T> ImmutableList<T> newImmutableList(@NotNull List<? extends T> c)
	{
		return new ImmutableList<>(c);
	}

	/**
	 * <p>newImmutableListBuilder.</p>
	 *
	 * @param <T> a T class
	 * @return a {@link com.hk.collections.lists.ImmutableList.Builder} object
	 */
	@NotNull
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
	@NotNull
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
	@NotNull
	public static <T> SortedList<T> newSortedList(@NotNull Comparator<T> comparator)
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
	@NotNull
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
	@NotNull
	public static <T> SortedList<T> newSortedList(@NotNull Comparator<T> comparator, boolean reverseSorting)
	{
		return new SortedList<>(comparator, reverseSorting);
	}

	/**
	 * Swap two different indices in a list.
	 *
	 * @param list the list to perform the swap with
	 * @param index1 the index of the first object to swap with the second
	 * @param index2 the destination to put the first object
	 * @param <T> a T class
	 * @return the same list
	 */
	@NotNull
	public static <T> List<T> swap(@NotNull List<T> list, int index1, int index2)
	{
		list.set(index1, list.set(index2, list.get(index1)));
		return list;
	}

	/**
	 * @deprecated use {@link Collections#addAll}
	 */
	@Deprecated
	public static <T> void addElements(@NotNull List<T> list, @NotNull T[] array)
	{
		Collections.addAll(list, array);
	}

	/**
	 * @deprecated use {@link Collections#addAll}
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public static <T> void addAll(@NotNull List<T> list, T... array)
	{
		Collections.addAll(list, array);
	}

	/**
	 * Adds the array of elements provided to the given collection in the
	 * <b>reverse</b> order. Similar to
	 * {@link Collections#addAll(Collection, Object[])} but in the
	 * reverse order.
	 *
	 * @param coll the collection to add the elements to
	 * @param arr the array of elements to add to the given collection
	 * @param <T> the type of element
	 */
	@SuppressWarnings("unchecked")
	public static <T> void reverseAddAll(@NotNull Collection<T> coll, T... arr)
	{
		for (int i = 0; i < arr.length; i++)
			coll.add(arr[arr.length - i - 1]);
	}


	/**
	 * Adds the collection of elements provided to the given collection
	 * in the <b>reverse</b> order. Similar to
	 * {@link Collections#addAll(Collection, Object[])} but in the
	 * reverse order.
	 *
	 * @param coll the collection to add the elements to
	 * @param collection the collection to add to the given collection
	 * @param <T> the type of element
	 */
	@SuppressWarnings("unchecked")
	public static <T> void reverseAddAll(@NotNull Collection<T> coll, @NotNull Collection<? extends T> collection)
	{
		Object[] arr = collection.toArray();
		for (int i = 0; i < arr.length; i++)
			coll.add((T) arr[arr.length - i - 1]);
	}

	private ListUtil()
	{}
}
