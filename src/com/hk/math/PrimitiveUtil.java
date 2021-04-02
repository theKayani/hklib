package com.hk.math;

class PrimitiveUtil
{
	public static short bytesToShort(int off, byte... arr)
	{
		short n = 0;
		for (int i = 0; i < 2; i++)
		{
			n |= (arr[i + off] & 0xFF) << 8 * i;
		}
		return n;
	}

	public static int bytesToInt(int off, byte... arr)
	{
		int n = 0;
		for (int i = 0; i < 4; i++)
		{
			n |= (arr[i + off] & 0xFF) << 8 * i;
		}
		return n;
	}

	public static long bytesToLong(int off, byte... arr)
	{
		long n = 0;
		for (int i = 0; i < 8; i++)
		{
			n |= (arr[i + off] & 0xFFL) << 8 * i;
		}
		return n;
	}

	public static byte[] shortToBytes(short n)
	{
		return shortToBytes(n, new byte[2], 0);
	}

	public static byte[] shortToBytes(short n, byte[] bs, int off)
	{
		for (int i = 0; i < 2; i++)
		{
			bs[i + off] = (byte) (n >> 8 * i & 0xFF);
		}

		return bs;
	}

	public static int shortsToInt(int off, short... arr)
	{
		int n = 0;
		for (int i = 0; i < 2; i++)
		{
			n |= (arr[i + off] & 0xFFFF) << 16 * i;
		}
		return n;
	}

	public static long shortsToLong(int off, short... arr)
	{
		long n = 0;
		for (int i = 0; i < 4; i++)
		{
			n |= (arr[i + off] & 0xFFFFL) << 16 * i;
		}
		return n;
	}

	public static byte[] intToBytes(int n)
	{
		return intToBytes(n, new byte[4], 0);
	}

	public static short[] intToShorts(int n)
	{
		return intToShorts(n, new short[2], 0);
	}

	public static byte[] intToBytes(int n, byte[] bs, int off)
	{
		for (int i = 0; i < 4; i++)
		{
			bs[i + off] = (byte) (n >> 8 * i & 0xFF);
		}

		return bs;
	}

	public static short[] intToShorts(int n, short[] bs, int off)
	{
		for (int i = 0; i < 2; i++)
		{
			bs[i + off] = (short) (n >> 16 * i & 0xFFFF);
		}

		return bs;
	}

	public static long intsToLong(int off, int... arr)
	{
		long n = 0;
		for (int i = 0; i < 2; i++)
		{
			n |= (arr[i + off] & 0xFFFFFFFFL) << 32 * i;
		}
		return n;
	}

	public static byte[] longToBytes(long n)
	{
		return longToBytes(n, new byte[8], 0);
	}

	public static short[] longToShorts(long n)
	{
		return longToShorts(n, new short[4], 0);
	}

	public static int[] longToInts(long n)
	{
		return longToInts(n, new int[2], 0);
	}

	public static byte[] longToBytes(long n, byte[] arr, int off)
	{
		for (int i = 0; i < 8; i++)
		{
			arr[i + off] = (byte) (n >> 8 * i & 0xFF);
		}

		return arr;
	}

	public static short[] longToShorts(long n, short[] arr, int off)
	{
		for (int i = 0; i < 4; i++)
		{
			arr[i + off] = (short) (n >> 16 * i & 0xFFFF);
		}

		return arr;
	}

	public static int[] longToInts(long n, int[] arr, int off)
	{
		for (int i = 0; i < 2; i++)
		{
			arr[i + off] = (int) (n >> 32 * i & 0xFFFFFFFF);
		}

		return arr;
	}

	PrimitiveUtil()
	{}
}
