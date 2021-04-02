package com.hk.math;

import java.util.Comparator;
import java.util.Objects;

public class ComparatorUtil
{
	public static final Comparator<Integer> compInteger = getComparator();
	public static final Comparator<Float> compFloat = getComparator();
	public static final Comparator<Double> compDouble = getComparator();
	public static final Comparator<Long> compLong = getComparator();
	public static final Comparator<Short> compShort = getComparator();
	public static final Comparator<Character> compCharacter = getComparator();
	public static final Comparator<Byte> compByte = getComparator();
	public static final Comparator<Boolean> compBoolean = getComparator();
	public static final Comparator<String> compString = getComparator();

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
