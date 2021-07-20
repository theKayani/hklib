package com.hk.math;

import java.util.Comparator;
import java.util.Objects;

/**
 * <p>ComparatorUtil class.</p>
 *
 * @author theKayani
 */
public class ComparatorUtil
{
	/** Constant <code>compInteger</code> */
	public static final Comparator<Integer> compInteger = getComparator();
	/** Constant <code>compFloat</code> */
	public static final Comparator<Float> compFloat = getComparator();
	/** Constant <code>compDouble</code> */
	public static final Comparator<Double> compDouble = getComparator();
	/** Constant <code>compLong</code> */
	public static final Comparator<Long> compLong = getComparator();
	/** Constant <code>compShort</code> */
	public static final Comparator<Short> compShort = getComparator();
	/** Constant <code>compCharacter</code> */
	public static final Comparator<Character> compCharacter = getComparator();
	/** Constant <code>compByte</code> */
	public static final Comparator<Byte> compByte = getComparator();
	/** Constant <code>compBoolean</code> */
	public static final Comparator<Boolean> compBoolean = getComparator();
	/** Constant <code>compString</code> */
	public static final Comparator<String> compString = getComparator();

	/**
	 * <p>reversed.</p>
	 *
	 * @param original a {@link java.util.Comparator} object
	 * @param <T> a T class
	 * @return a {@link java.util.Comparator} object
	 */
	public static <T> Comparator<T> reversed(final Comparator<T> original)
	{
		return new Comparator<T>()
		{
			@Override
			public int compare(T o1, T o2)
			{
				return original.compare(o2, o1);
			}
		};
	}

	/**
	 * <p>getComparator.</p>
	 *
	 * @param <T> a T class
	 * @return a {@link java.util.Comparator} object
	 */
	public static <T extends Comparable<T>> Comparator<T> getComparator()
	{
		return new Comparator<T>()
		{
			@Override
			public int compare(T o1, T o2)
			{
				if (Objects.equals(o1, o2))
				{
					return 0;
				}
				if (o1 == null)
				{
					return -1;
				}
				if (o2 == null)
				{
					return 1;
				}
				return o1.compareTo(o2);
			}
		};
	}

	/**
	 * <p>toStringComparator.</p>
	 *
	 * @param <T> a T class
	 * @return a {@link java.util.Comparator} object
	 */
	public static <T> Comparator<T> toStringComparator()
	{
		return new Comparator<T>()
		{
			@Override
			public int compare(T o1, T o2)
			{
				if (Objects.equals(o1, o2))
				{
					return 0;
				}
				if (o1 == null)
				{
					return -1;
				}
				if (o2 == null)
				{
					return 1;
				}
				return o1.toString().compareTo(o2.toString());
			}
		};
	}

	/**
	 * <p>hashCodeComparator.</p>
	 *
	 * @param <T> a T class
	 * @return a {@link java.util.Comparator} object
	 */
	public static <T> Comparator<T> hashCodeComparator()
	{
		return new Comparator<T>()
		{
			@Override
			public int compare(T o1, T o2)
			{
				if (Objects.equals(o1, o2))
				{
					return 0;
				}
				if (o1 == null)
				{
					return -1;
				}
				if (o2 == null)
				{
					return 1;
				}
				return Integer.compare(o1.hashCode(), o2.hashCode());
			}
		};
	}

	private ComparatorUtil()
	{}
}
