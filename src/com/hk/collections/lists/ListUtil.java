package com.hk.collections.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ListUtil
{
	public static <T> ArrayList<T> newArrayList()
	{
		return new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> newArrayList(T... elements)
	{
		ArrayList<T> list = newArrayList();
		addElements(list, elements);
		return list;
	}

	public static <T> ArrayList<T> newArrayListWith(T[] elements)
	{
		ArrayList<T> list = newArrayList();
		addElements(list, elements);
		return list;
	}

	public static <T> ArrayList<T> newArrayList(int capacity)
	{
		return new ArrayList<>(capacity);
	}

	public static <T> ArrayList<T> newArrayList(Collection<? extends T> c)
	{
		return new ArrayList<>(c);
	}

	public static <T> LinkedList<T> newLinkedList()
	{
		return new LinkedList<>();
	}

	public static <T> LinkedList<T> newLinkedList(Collection<? extends T> c)
	{
		return new LinkedList<>(c);
	}

	public static <T> ImmutableList<T> newImmutableList(List<? extends T> c)
	{
		return new ImmutableList<>(c);
	}

	public static <T> ImmutableList.Builder<T> newImmutableListBuilder()
	{
		return new ImmutableList.Builder<>();
	}

	public static <T> SortedList<T> newSortedList()
	{
		return new SortedList<>();
	}

	public static <T> SortedList<T> newSortedList(Comparator<T> comparator)
	{
		return new SortedList<>(comparator);
	}

	public static <T> SortedList<T> newSortedList(boolean reverseSorting)
	{
		return new SortedList<>(reverseSorting);
	}

	public static <T> SortedList<T> newSortedList(Comparator<T> comparator, boolean reverseSorting)
	{
		return new SortedList<>(comparator, reverseSorting);
	}

	public static <T> List<T> swap(List<T> list, int index1, int index2)
	{
		list.set(index1, list.set(index2, list.get(index1)));
		return list;
	}

	public static <T> boolean addElements(List<T> list, T[] array)
	{
		boolean good = true;
		for (T obj : array)
		{
			good &= list.add(obj);
		}
		return good;
	}

	@SuppressWarnings("unchecked")
	public static <T> boolean addAll(List<T> list, T... array)
	{
		return addElements(list, array);
	}

	private ListUtil()
	{}
}
