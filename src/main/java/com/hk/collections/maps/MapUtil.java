package com.hk.collections.maps;

import java.util.*;

/**
 * <p>MapUtil class.</p>
 *
 * @author theKayani
 */
public class MapUtil
{
	/**
	 * <p>newIndexMap.</p>
	 *
	 * @param <T> a T class
	 * @return a {@link com.hk.collections.maps.IndexMap} object
	 */
	@Deprecated
	public static <T> IndexMap<T> newIndexMap()
	{
		return new IndexMap<>();
	}

	/**
	 * <p>newHashMap.</p>
	 *
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.HashMap} object
	 */
	public static <T, E> HashMap<T, E> newHashMap()
	{
		return new HashMap<>();
	}

	/**
	 * <p>newHashMap.</p>
	 *
	 * @param initialCapacity a int
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.HashMap} object
	 */
	public static <T, E> HashMap<T, E> newHashMap(int initialCapacity)
	{
		return new HashMap<>(initialCapacity);
	}

	/**
	 * <p>newHashMap.</p>
	 *
	 * @param initialCapacity a int
	 * @param loadFactor a float
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.HashMap} object
	 */
	public static <T, E> HashMap<T, E> newHashMap(int initialCapacity, float loadFactor)
	{
		return new HashMap<>(initialCapacity, loadFactor);
	}

	/**
	 * <p>newHashMap.</p>
	 *
	 * @param map a {@link java.util.Map} object
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.HashMap} object
	 */
	public static <T, E> HashMap<T, E> newHashMap(Map<? extends T, ? extends E> map)
	{
		return new HashMap<>(map);
	}

	/**
	 * <p>newHashTable.</p>
	 *
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.Hashtable} object
	 */
	public static <T, E> Hashtable<T, E> newHashTable()
	{
		return new Hashtable<>();
	}

	/**
	 * <p>newHashTable.</p>
	 *
	 * @param initialCapacity a int
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.Hashtable} object
	 */
	public static <T, E> Hashtable<T, E> newHashTable(int initialCapacity)
	{
		return new Hashtable<>(initialCapacity);
	}

	/**
	 * <p>newHashTable.</p>
	 *
	 * @param initialCapacity a int
	 * @param loadFactor a float
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.Hashtable} object
	 */
	public static <T, E> Hashtable<T, E> newHashTable(int initialCapacity, float loadFactor)
	{
		return new Hashtable<>(initialCapacity, loadFactor);
	}

	/**
	 * <p>newHashTable.</p>
	 *
	 * @param map a {@link java.util.Map} object
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.Hashtable} object
	 */
	public static <T, E> Hashtable<T, E> newHashTable(Map<? extends T, ? extends E> map)
	{
		return new Hashtable<>(map);
	}

	/**
	 * <p>newTreeMap.</p>
	 *
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.TreeMap} object
	 */
	public static <T, E> TreeMap<T, E> newTreeMap()
	{
		return new TreeMap<>();
	}

	/**
	 * <p>newTreeMap.</p>
	 *
	 * @param map a {@link java.util.SortedMap} object
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.TreeMap} object
	 */
	public static <T, E> TreeMap<T, E> newTreeMap(SortedMap<T, ? extends E> map)
	{
		return new TreeMap<>(map);
	}

	/**
	 * <p>newTreeMap.</p>
	 *
	 * @param map a {@link java.util.Map} object
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.TreeMap} object
	 */
	public static <T, E> TreeMap<T, E> newTreeMap(Map<? extends T, ? extends E> map)
	{
		return new TreeMap<>(map);
	}

	/**
	 * <p>newTreeMap.</p>
	 *
	 * @param comp a {@link java.util.Comparator} object
	 * @param <T> a T class
	 * @param <E> a E class
	 * @return a {@link java.util.TreeMap} object
	 */
	public static <T, E> TreeMap<T, E> newTreeMap(Comparator<? super T> comp)
	{
		return new TreeMap<>(comp);
	}

	private MapUtil()
	{}
}
