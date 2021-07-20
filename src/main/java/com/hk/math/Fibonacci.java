package com.hk.math;

import com.hk.array.ArrayUtil;
import com.hk.array.ImmutableArray;

/**
 * <p>Fibonacci class.</p>
 *
 * @author theKayani
 */
public class Fibonacci
{
	/** Constant <code>fibonacciInt</code> */
	public static final ImmutableArray<Integer> fibonacciInt = ArrayUtil.immutableArrayOf(getIntFibonnaciArray(47));
	/** Constant <code>fibonacciLong</code> */
	public static final ImmutableArray<Long> fibonacciLong = ArrayUtil.immutableArrayOf(getLongFibonnaciArray(93));

	/**
	 * <p>getIntFibonnaciArray.</p>
	 *
	 * @param length a int
	 * @return an array of {@link int} objects
	 */
	public static int[] getIntFibonnaciArray(int length)
	{
		int[] fibb = new int[length];
		fibb[0] = 0;
		fibb[1] = 1;
		for (int i = 2; i < fibb.length; i++)
		{
			fibb[i] = fibb[i - 1] + fibb[i - 2];
		}
		return fibb;
	}

	/**
	 * <p>getLongFibonnaciArray.</p>
	 *
	 * @param length a int
	 * @return an array of {@link long} objects
	 */
	public static long[] getLongFibonnaciArray(int length)
	{
		long[] fibb = new long[length];
		fibb[0] = 0;
		fibb[1] = 1;
		for (int i = 2; i < fibb.length; i++)
		{
			fibb[i] = fibb[i - 1] + fibb[i - 2];
		}
		return fibb;
	}

	/**
	 * <p>Getter for the field <code>fibonacciInt</code>.</p>
	 *
	 * @param index a int
	 * @return a int
	 */
	public static int getFibonacciInt(int index)
	{
		return fibonacciLong.get(index).intValue();
	}

	/**
	 * <p>Getter for the field <code>fibonacciLong</code>.</p>
	 *
	 * @param index a int
	 * @return a long
	 */
	public static long getFibonacciLong(int index)
	{
		return fibonacciLong.get(index);
	}

	private Fibonacci()
	{}
}
