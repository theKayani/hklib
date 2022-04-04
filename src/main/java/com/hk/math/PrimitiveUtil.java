package com.hk.math;

/**
 * <p>PrimitiveUtil class.</p>
 *
 * @author theKayani
 */
public class PrimitiveUtil
{
	/**
	 * <p>bytesToShort.</p>
	 *
	 * @param off a int
	 * @param arr a byte
	 * @return a short
	 */
	public static short bytesToShort(int off, byte... arr)
	{
		short n = 0;
		for (int i = 0; i < 2; i++)
		{
			n |= (arr[i + off] & 0xFF) << 8 * i;
		}
		return n;
	}

	/**
	 * <p>bytesToInt.</p>
	 *
	 * @param off a int
	 * @param arr a byte
	 * @return a int
	 */
	public static int bytesToInt(int off, byte... arr)
	{
		int n = 0;
		for (int i = 0; i < 4; i++)
		{
			n |= (arr[i + off] & 0xFF) << 8 * i;
		}
		return n;
	}

	/**
	 * <p>bytesToLong.</p>
	 *
	 * @param off a int
	 * @param arr a byte
	 * @return a long
	 */
	public static long bytesToLong(int off, byte... arr)
	{
		long n = 0;
		for (int i = 0; i < 8; i++)
		{
			n |= (arr[i + off] & 0xFFL) << 8 * i;
		}
		return n;
	}

	/**
	 * <p>shortToBytes.</p>
	 *
	 * @param n a short
	 * @return an array of {@link byte} objects
	 */
	public static byte[] shortToBytes(short n)
	{
		return shortToBytes(n, new byte[2], 0);
	}

	/**
	 * <p>shortToBytes.</p>
	 *
	 * @param n a short
	 * @param bs an array of {@link byte} objects
	 * @param off a int
	 * @return an array of {@link byte} objects
	 */
	public static byte[] shortToBytes(short n, byte[] bs, int off)
	{
		for (int i = 0; i < 2; i++)
		{
			bs[i + off] = (byte) (n >> 8 * i & 0xFF);
		}

		return bs;
	}

	/**
	 * <p>shortsToInt.</p>
	 *
	 * @param off a int
	 * @param arr a short
	 * @return a int
	 */
	public static int shortsToInt(int off, short... arr)
	{
		int n = 0;
		for (int i = 0; i < 2; i++)
		{
			n |= (arr[i + off] & 0xFFFF) << 16 * i;
		}
		return n;
	}

	/**
	 * <p>shortsToLong.</p>
	 *
	 * @param off a int
	 * @param arr a short
	 * @return a long
	 */
	public static long shortsToLong(int off, short... arr)
	{
		long n = 0;
		for (int i = 0; i < 4; i++)
		{
			n |= (arr[i + off] & 0xFFFFL) << 16 * i;
		}
		return n;
	}

	/**
	 * <p>intToBytes.</p>
	 *
	 * @param n a int
	 * @return an array of {@link byte} objects
	 */
	public static byte[] intToBytes(int n)
	{
		return intToBytes(n, new byte[4], 0);
	}

	/**
	 * <p>intToShorts.</p>
	 *
	 * @param n a int
	 * @return an array of {@link short} objects
	 */
	public static short[] intToShorts(int n)
	{
		return intToShorts(n, new short[2], 0);
	}

	/**
	 * <p>intToBytes.</p>
	 *
	 * @param n a int
	 * @param bs an array of {@link byte} objects
	 * @param off a int
	 * @return an array of {@link byte} objects
	 */
	public static byte[] intToBytes(int n, byte[] bs, int off)
	{
		for (int i = 0; i < 4; i++)
		{
			bs[i + off] = (byte) (n >> 8 * i & 0xFF);
		}

		return bs;
	}

	/**
	 * <p>intToShorts.</p>
	 *
	 * @param n a int
	 * @param bs an array of {@link short} objects
	 * @param off a int
	 * @return an array of {@link short} objects
	 */
	public static short[] intToShorts(int n, short[] bs, int off)
	{
		for (int i = 0; i < 2; i++)
		{
			bs[i + off] = (short) (n >> 16 * i & 0xFFFF);
		}

		return bs;
	}

	/**
	 * <p>intsToLong.</p>
	 *
	 * @param off a int
	 * @param arr a int
	 * @return a long
	 */
	public static long intsToLong(int off, int... arr)
	{
		long n = 0;
		for (int i = 0; i < 2; i++)
		{
			n |= (arr[i + off] & 0xFFFFFFFFL) << 32 * i;
		}
		return n;
	}

	/**
	 * <p>longToBytes.</p>
	 *
	 * @param n a long
	 * @return an array of {@link byte} objects
	 */
	public static byte[] longToBytes(long n)
	{
		return longToBytes(n, new byte[8], 0);
	}

	/**
	 * <p>longToShorts.</p>
	 *
	 * @param n a long
	 * @return an array of {@link short} objects
	 */
	public static short[] longToShorts(long n)
	{
		return longToShorts(n, new short[4], 0);
	}

	/**
	 * <p>longToInts.</p>
	 *
	 * @param n a long
	 * @return an array of {@link int} objects
	 */
	public static int[] longToInts(long n)
	{
		return longToInts(n, new int[2], 0);
	}

	/**
	 * <p>longToBytes.</p>
	 *
	 * @param n a long
	 * @param arr an array of {@link byte} objects
	 * @param off a int
	 * @return an array of {@link byte} objects
	 */
	public static byte[] longToBytes(long n, byte[] arr, int off)
	{
		for (int i = 0; i < 8; i++)
		{
			arr[i + off] = (byte) (n >> 8 * i & 0xFF);
		}

		return arr;
	}

	/**
	 * <p>longToShorts.</p>
	 *
	 * @param n a long
	 * @param arr an array of {@link short} objects
	 * @param off a int
	 * @return an array of {@link short} objects
	 */
	public static short[] longToShorts(long n, short[] arr, int off)
	{
		for (int i = 0; i < 4; i++)
		{
			arr[i + off] = (short) (n >> 16 * i & 0xFFFF);
		}

		return arr;
	}

	/**
	 * <p>longToInts.</p>
	 *
	 * @param n a long
	 * @param arr an array of {@link int} objects
	 * @param off a int
	 * @return an array of {@link int} objects
	 */
	public static int[] longToInts(long n, int[] arr, int off)
	{
		for (int i = 0; i < 2; i++)
		{
			arr[i + off] = (int) (n >> 32 * i);
		}

		return arr;
	}

	private PrimitiveUtil()
	{}
}
