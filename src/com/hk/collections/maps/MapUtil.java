package com.hk.collections.maps;

import java.util.*;

public class MapUtil
{
	@Deprecated
	public static <T> IndexMap<T> newIndexMap()
	{
		return new IndexMap<>();
	}

	public static <T, E> HashMap<T, E> newHashMap()
	{
		return new HashMap<>();
	}

	public static <T, E> HashMap<T, E> newHashMap(int initialCapacity)
	{
		return new HashMap<>(initialCapacity);
	}

	public static <T, E> HashMap<T, E> newHashMap(int initialCapacity, float loadFactor)
	{
		return new HashMap<>(initialCapacity, loadFactor);
	}

	public static <T, E> HashMap<T, E> newHashMap(Map<? extends T, ? extends E> map)
	{
		return new HashMap<>(map);
	}

	public static <T, E> Hashtable<T, E> newHashTable()
	{
		return new Hashtable<>();
	}

	public static <T, E> Hashtable<T, E> newHashTable(int initialCapacity)
	{
		return new Hashtable<>(initialCapacity);
	}

	public static <T, E> Hashtable<T, E> newHashTable(int initialCapacity, float loadFactor)
	{
		return new Hashtable<>(initialCapacity, loadFactor);
	}

	public static <T, E> Hashtable<T, E> newHashTable(Map<? extends T, ? extends E> map)
	{
		return new Hashtable<>(map);
	}

	public static <T, E> TreeMap<T, E> newTreeMap()
	{
		return new TreeMap<>();
	}

	public static <T, E> TreeMap<T, E> newTreeMap(SortedMap<T, ? extends E> map)
	{
		return new TreeMap<>(map);
	}

	public static <T, E> TreeMap<T, E> newTreeMap(Map<? extends T, ? extends E> map)
	{
		return new TreeMap<>(map);
	}

	public static <T, E> TreeMap<T, E> newTreeMap(Comparator<? super T> comp)
	{
		return new TreeMap<>(comp);
	}

	private MapUtil()
	{}
}
