package com.hk.array;

import java.lang.reflect.Array;

/**
 * <p>Concat class.</p>
 *
 * @author theKayani
 */
public class Concat
{
	/**
	 * <p>concat.</p>
	 *
	 * @param arrs an array of T[] objects
	 * @param <T> a T class
	 * @return an array of T[] objects
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] concat(T[]... arrs)
	{
		if (arrs.length == 0) return null;
		int len = 0;
		for (T[] arr : arrs)
		{
			len += arr.length;
		}

		T[] arr = (T[]) Array.newInstance(arrs[0].getClass().getComponentType(), len);
		int index = 0;
		for (T[] ts : arrs) {
			System.arraycopy(ts, 0, arr, index, ts.length);
			index += ts.length;
		}
		return arr;
	}

	/**
	 * <p>concat.</p>
	 *
	 * @param arrs an array of {@link int} objects
	 * @return an array of {@link int} objects
	 */
	public static int[] concat(int[]... arrs)
	{
		int len = 0;
		for (int[] arr : arrs)
		{
			len += arr.length;
		}

		int[] arr = new int[len];
		int index = 0;
		for (int[] ints : arrs) {
			System.arraycopy(ints, 0, arr, index, ints.length);
			index += ints.length;
		}
		return arr;
	}

	/**
	 * <p>concat.</p>
	 *
	 * @param arrs an array of {@link double} objects
	 * @return an array of {@link double} objects
	 */
	public static double[] concat(double[]... arrs)
	{
		int len = 0;
		for (double[] arr : arrs)
		{
			len += arr.length;
		}

		double[] arr = new double[len];
		int index = 0;
		for (double[] doubles : arrs) {
			System.arraycopy(doubles, 0, arr, index, doubles.length);
			index += doubles.length;
		}
		return arr;
	}

	/**
	 * <p>concat.</p>
	 *
	 * @param arrs an array of {@link float} objects
	 * @return an array of {@link float} objects
	 */
	public static float[] concat(float[]... arrs)
	{
		int len = 0;
		for (float[] arr : arrs)
		{
			len += arr.length;
		}

		float[] arr = new float[len];
		int index = 0;
		for (float[] floats : arrs) {
			System.arraycopy(floats, 0, arr, index, floats.length);
			index += floats.length;
		}
		return arr;
	}

	/**
	 * <p>concat.</p>
	 *
	 * @param arrs an array of {@link byte} objects
	 * @return an array of {@link byte} objects
	 */
	public static byte[] concat(byte[]... arrs)
	{
		int len = 0;
		for (byte[] arr : arrs)
		{
			len += arr.length;
		}

		byte[] arr = new byte[len];
		int index = 0;
		for (byte[] bytes : arrs) {
			System.arraycopy(bytes, 0, arr, index, bytes.length);
			index += bytes.length;
		}
		return arr;
	}

	/**
	 * <p>concat.</p>
	 *
	 * @param arrs an array of {@link short} objects
	 * @return an array of {@link short} objects
	 */
	public static short[] concat(short[]... arrs)
	{
		int len = 0;
		for (short[] arr : arrs)
		{
			len += arr.length;
		}

		short[] arr = new short[len];
		int index = 0;
		for (short[] shorts : arrs) {
			System.arraycopy(shorts, 0, arr, index, shorts.length);
			index += shorts.length;
		}
		return arr;
	}

	/**
	 * <p>concat.</p>
	 *
	 * @param arrs an array of {@link long} objects
	 * @return an array of {@link long} objects
	 */
	public static long[] concat(long[]... arrs)
	{
		int len = 0;
		for (long[] arr : arrs)
		{
			len += arr.length;
		}

		long[] arr = new long[len];
		int index = 0;
		for (long[] longs : arrs) {
			System.arraycopy(longs, 0, arr, index, longs.length);
			index += longs.length;
		}
		return arr;
	}

	/**
	 * <p>concat.</p>
	 *
	 * @param arrs an array of {@link char} objects
	 * @return an array of {@link char} objects
	 */
	public static char[] concat(char[]... arrs)
	{
		int len = 0;
		for (char[] arr : arrs)
		{
			len += arr.length;
		}

		char[] arr = new char[len];
		int index = 0;
		for (char[] chars : arrs) {
			System.arraycopy(chars, 0, arr, index, chars.length);
			index += chars.length;
		}
		return arr;
	}

	/**
	 * <p>concat.</p>
	 *
	 * @param arrs an array of {@link boolean} objects
	 * @return an array of {@link boolean} objects
	 */
	public static boolean[] concat(boolean[]... arrs)
	{
		int len = 0;
		for (boolean[] arr : arrs)
		{
			len += arr.length;
		}

		boolean[] arr = new boolean[len];
		int index = 0;
		for (boolean[] booleans : arrs) {
			System.arraycopy(booleans, 0, arr, index, booleans.length);
			index += booleans.length;
		}
		return arr;
	}

	/**
	 * <p>concatStrings.</p>
	 *
	 * @param arrs an array of {@link java.lang.String} objects
	 * @return a {@link java.lang.String} object
	 */
	public static String concatStrings(String[]... arrs)
	{
		int len = 0;
		for (String[] arr : arrs)
		{
			for (String str : arr)
			{
				if(str != null)
					len += str.length();
			}
		}

		char[] chs = new char[len];
		len = 0;
		for (String[] arr : arrs)
		{
			for (String str : arr)
			{
				if(str != null)
				{
					System.arraycopy(str.toCharArray(), 0, chs, len, str.length());
					len += str.length();
				}
			}
		}
		return new String(chs);
	}

	/**
	 * <p>sumOfInts.</p>
	 *
	 * @param arrs an array of {@link int} objects
	 * @return a int
	 */
	public static int sumOfInts(int[]... arrs)
	{
		int tot = 0;
		for (int[] arr : arrs)
		{
			for (int i : arr)
			{
				tot += i;
			}
		}
		return tot;
	}

	/**
	 * <p>sumOfLongs.</p>
	 *
	 * @param arrs an array of {@link long} objects
	 * @return a long
	 */
	public static long sumOfLongs(long[]... arrs)
	{
		long tot = 0;
		for (long[] arr : arrs)
		{
			for (long i : arr)
			{
				tot += i;
			}
		}
		return tot;
	}

	/**
	 * <p>sumOfShorts.</p>
	 *
	 * @param arrs an array of {@link short} objects
	 * @return a short
	 */
	public static short sumOfShorts(short[]... arrs)
	{
		short tot = 0;
		for (short[] arr : arrs)
		{
			for (short i : arr)
			{
				tot += i;
			}
		}
		return tot;
	}

	/**
	 * <p>sumOfBytes.</p>
	 *
	 * @param arrs an array of {@link byte} objects
	 * @return a byte
	 */
	public static byte sumOfBytes(byte[]... arrs)
	{
		byte tot = 0;
		for (byte[] arr : arrs)
		{
			for (byte i : arr)
			{
				tot += i;
			}
		}
		return tot;
	}

	/**
	 * <p>sumOfFloats.</p>
	 *
	 * @param arrs an array of {@link float} objects
	 * @return a float
	 */
	public static float sumOfFloats(float[]... arrs)
	{
		float tot = 0;
		for (float[] arr : arrs)
		{
			for (float i : arr)
			{
				tot += i;
			}
		}
		return tot;
	}

	/**
	 * <p>sumOfDoubles.</p>
	 *
	 * @param arrs an array of {@link double} objects
	 * @return a double
	 */
	public static double sumOfDoubles(double[]... arrs)
	{
		double tot = 0;
		for (double[] arr : arrs)
		{
			for (double i : arr)
			{
				tot += i;
			}
		}
		return tot;
	}

	/**
	 * <p>sumOfChars.</p>
	 *
	 * @param arrs an array of {@link char} objects
	 * @return a char
	 */
	public static char sumOfChars(char[]... arrs)
	{
		char tot = 0;
		for (char[] arr : arrs)
		{
			for (char i : arr)
			{
				tot += i;
			}
		}
		return tot;
	}

	private Concat()
	{}
}