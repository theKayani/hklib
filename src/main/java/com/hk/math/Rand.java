package com.hk.math;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

/**
 * <p>Rand class.</p>
 *
 * @author theKayani
 */
public class Rand
{
	private static final Random rand = new Random();

	/**
	 * <p>nextInt.</p>
	 *
	 * @return a int
	 */
	public static int nextInt()
	{
		return rand.nextInt();
	}

	/**
	 * <p>nextInt.</p>
	 *
	 * @param bound a int
	 * @return a int
	 */
	public static int nextInt(int bound)
	{
		return rand.nextInt(bound);
	}

	/**
	 * <p>nextIntWithNeg.</p>
	 *
	 * @param bound a int
	 * @return a int
	 */
	public static int nextIntWithNeg(int bound)
	{
		return rand.nextInt() % bound;
	}

	/**
	 * <p>nextInt.</p>
	 *
	 * @param min a int
	 * @param max a int
	 * @return a int
	 */
	public static int nextInt(int min, int max)
	{
		return rand.nextInt(max - min) + min;
	}

	/**
	 * <p>nextFloat.</p>
	 *
	 * @return a float
	 */
	public static float nextFloat()
	{
		return rand.nextFloat();
	}

	/**
	 * <p>nextFloat.</p>
	 *
	 * @param multiplier a float
	 * @return a float
	 */
	public static float nextFloat(float multiplier)
	{
		return rand.nextFloat() * multiplier;
	}

	/**
	 * <p>nextFloat.</p>
	 *
	 * @param min a float
	 * @param max a float
	 * @return a float
	 */
	public static float nextFloat(float min, float max)
	{
		return nextFloat(max - min) + min;
	}

	/**
	 * <p>nextFloatPrecision.</p>
	 *
	 * @param precision a int
	 * @return a float
	 */
	public static float nextFloatPrecision(int precision)
	{
		if (precision <= 0)
		{
			throw new IllegalArgumentException("precision must be positive");
		}
		float e = FloatMath.pow(10F, precision);
		return FloatMath.floor(nextFloat() * e) % e / e;
	}

	/**
	 * <p>nextDouble.</p>
	 *
	 * @return a double
	 */
	public static double nextDouble()
	{
		return rand.nextDouble();
	}

	/**
	 * <p>nextDouble.</p>
	 *
	 * @param multiplier a double
	 * @return a double
	 */
	public static double nextDouble(double multiplier)
	{
		return rand.nextDouble() * multiplier;
	}

	/**
	 * <p>nextDouble.</p>
	 *
	 * @param min a double
	 * @param max a double
	 * @return a double
	 */
	public static double nextDouble(double min, double max)
	{
		return nextDouble(max - min) + min;
	}

	/**
	 * <p>nextDoublePrecision.</p>
	 *
	 * @param precision a int
	 * @return a double
	 */
	public static double nextDoublePrecision(int precision)
	{
		if (precision < 0)
		{
			throw new IllegalArgumentException("precision must be positive");
		}
		double e = Math.pow(10D, precision);
		return Math.floor(nextDouble() * e) % e / e;
	}

	/**
	 * <p>nextGaussian.</p>
	 *
	 * @return a double
	 */
	public static synchronized double nextGaussian()
	{
		return rand.nextGaussian();
	}

	/**
	 * <p>nextBoolean.</p>
	 *
	 * @return a boolean
	 */
	public static boolean nextBoolean()
	{
		return rand.nextBoolean();
	}

	/**
	 * <p>nextLong.</p>
	 *
	 * @return a long
	 */
	public static long nextLong()
	{
		return rand.nextLong();
	}

	/**
	 * <p>nextShort.</p>
	 *
	 * @return a short
	 */
	public static short nextShort()
	{
		return (short) (rand.nextInt() >>> 16 & 0xFFFF);
	}

	/**
	 * <p>nextBytes.</p>
	 *
	 * @param length a int
	 * @return an array of {@link byte} objects
	 */
	public static byte[] nextBytes(int length)
	{
		byte[] arr = new byte[length];
		rand.nextBytes(arr);
		return arr;
	}

	/**
	 * <p>nextBytes.</p>
	 *
	 * @param arr an array of {@link byte} objects
	 * @return an array of {@link byte} objects
	 */
	public static byte[] nextBytes(byte[] arr)
	{
		rand.nextBytes(arr);
		return arr;
	}

	/**
	 * <p>nextByte.</p>
	 *
	 * @return a byte
	 */
	public static byte nextByte()
	{
		return nextBytes(1)[0];
	}

	/**
	 * <p>nextChar.</p>
	 *
	 * @return a char
	 */
	public static char nextChar()
	{
		return Character.forDigit(nextInt(Character.MAX_RADIX), Character.MAX_RADIX);
	}

	/**
	 * <p>nextString.</p>
	 *
	 * @param length a int
	 * @return a {@link java.lang.String} object
	 */
	public static String nextString(int length)
	{
		char[] cs = new char[length];
		for (int i = 0; i < length; i++)
			cs[i] = nextChar();

		return new String(cs);
	}

	/**
	 * <p>nextInts.</p>
	 *
	 * @param length a int
	 * @return an array of {@link int} objects
	 */
	public static int[] nextInts(int length)
	{
		int[] arr = new int[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextInt();
		}
		return arr;
	}

	/**
	 * <p>nextInts.</p>
	 *
	 * @param length a int
	 * @param bound a int
	 * @return an array of {@link int} objects
	 */
	public static int[] nextInts(int length, int bound)
	{
		int[] arr = new int[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextInt(bound);
		}
		return arr;
	}

	/**
	 * <p>nextInts.</p>
	 *
	 * @param length a int
	 * @param min a int
	 * @param max a int
	 * @return an array of {@link int} objects
	 */
	public static int[] nextInts(int length, int min, int max)
	{
		int[] arr = new int[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextInt(min, max);
		}
		return arr;
	}

	/**
	 * <p>nextFloats.</p>
	 *
	 * @param length a int
	 * @return an array of {@link float} objects
	 */
	public static float[] nextFloats(int length)
	{
		float[] arr = new float[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextFloat();
		}
		return arr;
	}

	/**
	 * <p>nextFloats.</p>
	 *
	 * @param length a int
	 * @param multiplier a float
	 * @return an array of {@link float} objects
	 */
	public static float[] nextFloats(int length, float multiplier)
	{
		float[] arr = new float[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextFloat(multiplier);
		}
		return arr;
	}

	/**
	 * <p>nextDoubles.</p>
	 *
	 * @param length a int
	 * @return an array of {@link double} objects
	 */
	public static double[] nextDoubles(int length)
	{
		double[] arr = new double[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextDouble();
		}
		return arr;
	}

	/**
	 * <p>nextDoubles.</p>
	 *
	 * @param length a int
	 * @param multiplier a double
	 * @return an array of {@link double} objects
	 */
	public static double[] nextDoubles(int length, double multiplier)
	{
		double[] arr = new double[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextDouble(multiplier);
		}
		return arr;
	}

	/**
	 * <p>nextLongs.</p>
	 *
	 * @param length a int
	 * @return an array of {@link long} objects
	 */
	public static long[] nextLongs(int length)
	{
		long[] arr = new long[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextLong();
		}
		return arr;
	}

	/**
	 * <p>nextShorts.</p>
	 *
	 * @param length a int
	 * @return an array of {@link short} objects
	 */
	public static short[] nextShorts(int length)
	{
		short[] arr = new short[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextShort();
		}
		return arr;
	}

	/**
	 * <p>nextBooleans.</p>
	 *
	 * @param length a int
	 * @return an array of {@link boolean} objects
	 */
	public static boolean[] nextBooleans(int length)
	{
		boolean[] arr = new boolean[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = nextBoolean();
		}
		return arr;
	}

	/**
	 * <p>nextFrom.</p>
	 *
	 * @param array a {@link java.lang.Object} object
	 * @param <T> a T class
	 * @return a T object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T nextFrom(Object array)
	{
		return (T) Array.get(array, nextInt(Array.getLength(array)));
	}

	/**
	 * <p>nextFrom.</p>
	 *
	 * @param list a {@link java.util.List} object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T nextFrom(List<T> list)
	{
		return list.get(nextInt(list.size()));
	}

	/**
	 * Generates an array of random integers that add up to the target
	 * value provided.
	 *
	 * @param target the target sum that the result integers should
	 *                  add up to
	 * @param amount the amount of integers to generate, the same size
	 *                  as the array returned
	 * @return an array of integers that add up to the target amount
	 */
	public static int[] nextIntsSumTo(int target, int amount)
	{
		return nextIntsSumTo(rand, target, amount);
	}

	/**
	 * Generates an array of random longs that add up to the target
	 * value provided.
	 *
	 * @param target the target sum that the result longs should
	 *                  add up to
	 * @param amount the amount of longs to generate, the same size
	 *                  as the array returned
	 * @return an array of longs that add up to the target amount
	 */
	public static long[] nextLongsSumTo(long target, int amount)
	{
		return nextLongsSumTo(rand, target, amount);
	}

	/**
	 * Generates an array of random floats that add up to the target
	 * value provided.
	 *
	 * @param target the target sum that the result floats should
	 *                  add up to
	 * @param amount the amount of floats to generate, the same size
	 *                  as the array returned
	 * @return an array of floats that add up to the target amount
	 */
	public static float[] nextFloatsSumTo(float target, int amount)
	{
		return nextFloatsSumTo(rand, target, amount);
	}

	/**
	 * Generates an array of random doubles that add up to the target
	 * value provided.
	 *
	 * @param target the target sum that the result doubles should
	 *                  add up to
	 * @param amount the amount of doubles to generate, the same size
	 *                  as the array returned
	 * @return an array of doubles that add up to the target amount
	 */
	public static double[] nextDoublesSumTo(double target, int amount)
	{
		return nextDoublesSumTo(rand, target, amount);
	}

	/**
	 * Generates an array of random integers that add up to the target
	 * value provided.
	 *
	 * @param rng the random object to use to generate the random
	 *               values
	 * @param target the target sum that the result integers should
	 *                  add up to
	 * @param amount the amount of integers to generate, the same size
	 *                  as the array returned
	 * @return an array of integers that add up to the target amount
	 */
	public static int[] nextIntsSumTo(Random rng, int target, int amount)
	{
		float[] nums = nextFloatsSumTo(rng, target, amount);
		int[] intNums = new int[amount];
		int intSum = 0;
		for (int i = 0; i < amount - 1; i++)
			intSum += intNums[i] = (i % 2 == 0 ? (int) Math.floor(nums[i]) : (int) Math.ceil(nums[i]));
		intNums[amount - 1] = target - intSum;

		return intNums;
	}

	/**
	 * Generates an array of random longs that add up to the target
	 * value provided.
	 *
	 * @param rng the random object to use to generate the random
	 *               values
	 * @param target the target sum that the result longs should
	 *                  add up to
	 * @param amount the amount of longs to generate, the same size
	 *                  as the array returned
	 * @return an array of longs that add up to the target amount
	 */
	public static long[] nextLongsSumTo(Random rng, long target, int amount)
	{
		double[] nums = nextDoublesSumTo(rng, target, amount);
		long[] intNums = new long[amount];
		long intSum = 0;
		for (int i = 0; i < amount - 1; i++)
			intSum += intNums[i] = (i % 2 == 0 ? (long) Math.floor(nums[i]) : (long) Math.ceil(nums[i]));
		intNums[amount - 1] = target - intSum;

		return intNums;
	}

	/**
	 * Generates an array of random doubles that add up to the target
	 * value provided.
	 *
	 * @param rng the random object to use to generate the random
	 *               values
	 * @param target the target sum that the result doubles should
	 *                  add up to
	 * @param amount the amount of doubles to generate, the same size
	 *                  as the array returned
	 * @return an array of doubles that add up to the target amount
	 */
	public static double[] nextDoublesSumTo(Random rng, double target, int amount)
	{
		double[] nums = new double[amount];
		double sum = 0D;
		for (int i = 0; i < amount; i++)
			sum += nums[i] = rng.nextDouble();
		for (int i = 0; i < amount - 1; i++)
			nums[i] = nums[i] * target * (1D / sum);

		return nums;
	}

	/**
	 * Generates an array of random floats that add up to the target
	 * value provided.
	 *
	 * @param rng the random object to use to generate the random
	 *               values
	 * @param target the target sum that the result floats should
	 *                  add up to
	 * @param amount the amount of floats to generate, the same size
	 *                  as the array returned
	 * @return an array of floats that add up to the target amount
	 */
	public static float[] nextFloatsSumTo(Random rng, float target, int amount)
	{
		float[] nums = new float[amount];
		float sum = 0F;
		for (int i = 0; i < amount; i++)
			sum += nums[i] = rng.nextFloat();
		for (int i = 0; i < amount - 1; i++)
			nums[i] = nums[i] * target * (1F / sum);

		return nums;
	}

	private Rand()
	{}
}
