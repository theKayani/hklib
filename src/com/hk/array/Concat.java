package com.hk.array;

import java.lang.reflect.Array;

public class Concat
{
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
		for (int i = 0; i < arrs.length; i++)
		{
			System.arraycopy(arrs[i], 0, arr, index, arrs[i].length);
			index += arrs[i].length;
		}
		return arr;
	}

	public static int[] concat(int[]... arrs)
	{
		int len = 0;
		for (int[] arr : arrs)
		{
			len += arr.length;
		}

		int[] arr = new int[len];
		int index = 0;
		for (int i = 0; i < arrs.length; i++)
		{
			System.arraycopy(arrs[i], 0, arr, index, arrs[i].length);
			index += arrs[i].length;
		}
		return arr;
	}

	public static double[] concat(double[]... arrs)
	{
		int len = 0;
		for (double[] arr : arrs)
		{
			len += arr.length;
		}

		double[] arr = new double[len];
		int index = 0;
		for (int i = 0; i < arrs.length; i++)
		{
			System.arraycopy(arrs[i], 0, arr, index, arrs[i].length);
			index += arrs[i].length;
		}
		return arr;
	}

	public static float[] concat(float[]... arrs)
	{
		int len = 0;
		for (float[] arr : arrs)
		{
			len += arr.length;
		}

		float[] arr = new float[len];
		int index = 0;
		for (int i = 0; i < arrs.length; i++)
		{
			System.arraycopy(arrs[i], 0, arr, index, arrs[i].length);
			index += arrs[i].length;
		}
		return arr;
	}

	public static byte[] concat(byte[]... arrs)
	{
		int len = 0;
		for (byte[] arr : arrs)
		{
			len += arr.length;
		}

		byte[] arr = new byte[len];
		int index = 0;
		for (int i = 0; i < arrs.length; i++)
		{
			System.arraycopy(arrs[i], 0, arr, index, arrs[i].length);
			index += arrs[i].length;
		}
		return arr;
	}

	public static short[] concat(short[]... arrs)
	{
		int len = 0;
		for (short[] arr : arrs)
		{
			len += arr.length;
		}

		short[] arr = new short[len];
		int index = 0;
		for (int i = 0; i < arrs.length; i++)
		{
			System.arraycopy(arrs[i], 0, arr, index, arrs[i].length);
			index += arrs[i].length;
		}
		return arr;
	}

	public static long[] concat(long[]... arrs)
	{
		int len = 0;
		for (long[] arr : arrs)
		{
			len += arr.length;
		}

		long[] arr = new long[len];
		int index = 0;
		for (int i = 0; i < arrs.length; i++)
		{
			System.arraycopy(arrs[i], 0, arr, index, arrs[i].length);
			index += arrs[i].length;
		}
		return arr;
	}

	public static char[] concat(char[]... arrs)
	{
		int len = 0;
		for (char[] arr : arrs)
		{
			len += arr.length;
		}

		char[] arr = new char[len];
		int index = 0;
		for (int i = 0; i < arrs.length; i++)
		{
			System.arraycopy(arrs[i], 0, arr, index, arrs[i].length);
			index += arrs[i].length;
		}
		return arr;
	}

	public static boolean[] concat(boolean[]... arrs)
	{
		int len = 0;
		for (boolean[] arr : arrs)
		{
			len += arr.length;
		}

		boolean[] arr = new boolean[len];
		int index = 0;
		for (int i = 0; i < arrs.length; i++)
		{
			System.arraycopy(arrs[i], 0, arr, index, arrs[i].length);
			index += arrs[i].length;
		}
		return arr;
	}

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
