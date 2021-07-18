package com.hk.math;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

public class Rand
{
	private static final Random rand = new Random();

	public static int nextInt()
	{
		return rand.nextInt();
	}

	public static int nextInt(int bound)
	{
		return rand.nextInt(bound);
	}

	public static int nextIntWithNeg(int bound)
	{
		return rand.nextInt() % bound;
	}

	public static int nextInt(int min, int max)
	{
		return rand.nextInt(max - min) + min;
	}

	public static float nextFloat()
	{
		return rand.nextFloat();
	}

	public static float nextFloat(float multiplier)
	{
		return rand.nextFloat() * multiplier;
	}

	public static float nextFloat(float min, float max)
	{
		return nextFloat(max - min) + min;
	}

	public static float nextFloatPrecision(int precision)
	{
		if (precision <= 0)
		{
			throw new IllegalArgumentException("precision must be positive");
		}
		float e = FloatMath.pow(10F, precision);
		return FloatMath.floor(nextFloat() * e) % e / e;
	}

	public static double nextDouble()
	{
		return rand.nextDouble();
	}

	public static double nextDouble(double multiplier)
	{
		return rand.nextDouble() * multiplier;
	}

	public static double nextDouble(double min, double max)
	{
		return nextDouble(max - min) + min;
	}

	public static double nextDoublePrecision(int precision)
	{
		if (precision < 0)
		{
			throw new IllegalArgumentException("precision must be positive");
		}
		double e = Math.pow(10D, precision);
		return Math.floor(nextDouble() * e) % e / e;
	}

	public static synchronized double nextGaussian()
	{
		return rand.nextGaussian();
	}

	public static boolean nextBoolean()
	{
		return rand.nextBoolean();
	}

	public static long nextLong()
	{
		return rand.nextLong();
	}

	public static short nextShort()
	{
		return (short) (rand.nextInt() >>> 16 & 0xFFFF);
	}

	public static byte[] nextBytes(int length)
	{
		byte[] arr = new byte[length];
		rand.nextBytes(arr);
		return arr;
	}

	public static byte[] nextBytes(byte[] arr)
	{
		rand.nextBytes(arr);
		return arr;
	}

	public static byte nextByte()
	{
		return nextBytes(1)[0];
	}

	public static char nextChar()
	{
		return Character.forDigit(nextInt(Character.MAX_RADIX), Character.MAX_RADIX);
	}

	public static String nextString(int length)
	{
		String s = "";
		for (int i = 0; i < length; i++)
		{
			s += nextChar();
		}
		return s;
	}

	public static int[] nextInts(int length)
	{
		int[] arr = new int[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextInt();
		}
		return arr;
	}

	public static int[] nextInts(int length, int bound)
	{
		int[] arr = new int[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextInt(bound);
		}
		return arr;
	}

	public static int[] nextInts(int length, int min, int max)
	{
		int[] arr = new int[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextInt(min, max);
		}
		return arr;
	}

	public static float[] nextFloats(int length)
	{
		float[] arr = new float[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextFloat();
		}
		return arr;
	}

	public static float[] nextFloats(int length, float multiplier)
	{
		float[] arr = new float[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextFloat(multiplier);
		}
		return arr;
	}

	public static double[] nextDoubles(int length)
	{
		double[] arr = new double[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextDouble();
		}
		return arr;
	}

	public static double[] nextDoubles(int length, double multiplier)
	{
		double[] arr = new double[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextDouble(multiplier);
		}
		return arr;
	}

	public static long[] nextLongs(int length)
	{
		long[] arr = new long[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextLong();
		}
		return arr;
	}

	public static short[] nextShorts(int length)
	{
		short[] arr = new short[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextShort();
		}
		return arr;
	}

	public static boolean[] nextBooleans(int length)
	{
		boolean[] arr = new boolean[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextBoolean();
		}
		return arr;
	}

	@SuppressWarnings("unchecked")
	public static <T> T nextFrom(Object array)
	{
		return (T) Array.get(array, nextInt(Array.getLength(array)));
	}

	public static <T> T nextFrom(List<T> list)
	{
		return list.get(nextInt(list.size()));
	}

	private Rand()
	{}
}
