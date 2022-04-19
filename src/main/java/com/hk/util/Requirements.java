package com.hk.util;

import java.lang.reflect.Array;

import com.hk.ex.OutOfBoundsException;

/**
 * <p>Requirements class.</p>
 *
 * @author theKayani
 */
public class Requirements
{
	/**
	 * <p>requireInBounds.</p>
	 *
	 * @param min a int
	 * @param val a int
	 * @param max a int
	 * @return a int
	 */
	public static int requireInBounds(int min, int val, int max)
	{
		if(val < min)
		{
			throw new OutOfBoundsException(val + " is less than the minimum, " + min);
		}
		if(val > max)
		{
			throw new OutOfBoundsException(val + " is more than the maximum, " + max);
		}
		return val;
	}

	/**
	 * <p>requireInBounds.</p>
	 *
	 * @param min a int
	 * @param val a int
	 * @param max a int
	 * @param message a {@link java.lang.String} object
	 * @return a int
	 */
	public static int requireInBounds(int min, int val, int max, String message)
	{
		if(val < min)
		{
			throw new OutOfBoundsException(message);
		}
		if(val > max)
		{
			throw new OutOfBoundsException(message);
		}
		return val;
	}

	/**
	 * <p>requireInBounds.</p>
	 *
	 * @param min a float
	 * @param val a float
	 * @param max a float
	 * @return a float
	 */
	public static float requireInBounds(float min, float val, float max)
	{
		if(val < min)
		{
			throw new OutOfBoundsException(val + " is less than the minimum, " + min);
		}
		if(val > max)
		{
			throw new OutOfBoundsException(val + " is more than the maximum, " + max);
		}
		return val;
	}

	/**
	 * <p>requireInBounds.</p>
	 *
	 * @param min a float
	 * @param val a float
	 * @param max a float
	 * @param message a {@link java.lang.String} object
	 * @return a float
	 */
	public static float requireInBounds(float min, float val, float max, String message)
	{
		if(val < min)
		{
			throw new OutOfBoundsException(message);
		}
		if(val > max)
		{
			throw new OutOfBoundsException(message);
		}
		return val;
	}

	/**
	 * <p>requireInBounds.</p>
	 *
	 * @param min a double
	 * @param val a double
	 * @param max a double
	 * @return a double
	 */
	public static double requireInBounds(double min, double val, double max)
	{
		if(val < min)
		{
			throw new OutOfBoundsException(val + " is less than the minimum, " + min);
		}
		if(val > max)
		{
			throw new OutOfBoundsException(val + " is more than the maximum, " + max);
		}
		return val;
	}

	/**
	 * <p>requireInBounds.</p>
	 *
	 * @param min a double
	 * @param val a double
	 * @param max a double
	 * @param message a {@link java.lang.String} object
	 * @return a double
	 */
	public static double requireInBounds(double min, double val, double max, String message)
	{
		if(val < min)
		{
			throw new OutOfBoundsException(message);
		}
		if(val > max)
		{
			throw new OutOfBoundsException(message);
		}
		return val;
	}

	/**
	 * <p>requireInBounds.</p>
	 *
	 * @param min a short
	 * @param val a short
	 * @param max a short
	 * @return a short
	 */
	public static short requireInBounds(short min, short val, short max)
	{
		if(val < min)
		{
			throw new OutOfBoundsException(val + " is less than the minimum, " + min);
		}
		if(val > max)
		{
			throw new OutOfBoundsException(val + " is more than the maximum, " + max);
		}
		return val;
	}

	/**
	 * <p>requireInBounds.</p>
	 *
	 * @param min a short
	 * @param val a short
	 * @param max a short
	 * @param message a {@link java.lang.String} object
	 * @return a short
	 */
	public static short requireInBounds(short min, short val, short max, String message)
	{
		if(val < min)
		{
			throw new OutOfBoundsException(message);
		}
		if(val > max)
		{
			throw new OutOfBoundsException(message);
		}
		return val;
	}

	/**
	 * <p>requireInBounds.</p>
	 *
	 * @param min a byte
	 * @param val a byte
	 * @param max a byte
	 * @return a byte
	 */
	public static byte requireInBounds(byte min, byte val, byte max)
	{
		if(val < min)
		{
			throw new OutOfBoundsException(val + " is less than the minimum, " + min);
		}
		if(val > max)
		{
			throw new OutOfBoundsException(val + " is more than the maximum, " + max);
		}
		return val;
	}

	/**
	 * <p>requireInBounds.</p>
	 *
	 * @param min a byte
	 * @param val a byte
	 * @param max a byte
	 * @param message a {@link java.lang.String} object
	 * @return a byte
	 */
	public static byte requireInBounds(byte min, byte val, byte max, String message)
	{
		if(val < min)
		{
			throw new OutOfBoundsException(message);
		}
		if(val > max)
		{
			throw new OutOfBoundsException(message);
		}
		return val;
	}

	/**
	 * <p>requireCondition.</p>
	 *
	 * @param orig a T object
	 * @param condition a boolean
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireCondition(T orig, boolean condition)
	{
		if(!condition)
		{
			throw new IllegalArgumentException();
		}
		return orig;
	}

	/**
	 * <p>requireCondition.</p>
	 *
	 * @param orig a T object
	 * @param condition a boolean
	 * @param message a {@link java.lang.String} object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireCondition(T orig, boolean condition, String message)
	{
		if(!condition)
		{
			throw new IllegalArgumentException(message);
		}
		return orig;
	}

	/**
	 * <p>requireNotCondition.</p>
	 *
	 * @param orig a T object
	 * @param condition a boolean
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireNotCondition(T orig, boolean condition)
	{
		if(condition)
		{
			throw new IllegalArgumentException();
		}
		return orig;
	}

	/**
	 * <p>requireNotCondition.</p>
	 *
	 * @param orig a T object
	 * @param condition a boolean
	 * @param message a {@link java.lang.String} object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireNotCondition(T orig, boolean condition, String message)
	{
		if(condition)
		{
			throw new IllegalArgumentException(message);
		}
		return orig;
	}

	/**
	 * <p>requireNotNull.</p>
	 *
	 * @param orig a T object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireNotNull(T orig)
	{
		if(orig == null)
		{
			throw new NullPointerException();
		}
		return orig;
	}

	/**
	 * <p>requireNotNull.</p>
	 *
	 * @param orig a T object
	 * @param message a {@link java.lang.String} object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireNotNull(T orig, String message)
	{
		if(orig == null)
		{
			throw new NullPointerException(message);
		}
		return orig;
	}

	/**
	 * <p>requireAtLeastSize.</p>
	 *
	 * @param array a T object
	 * @param atLeast a int
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireAtLeastSize(T array, int atLeast)
	{
		if(Array.getLength(array) < atLeast)
		{
			throw new IllegalArgumentException();
		}
		return array;
	}

	/**
	 * <p>requireAtLeastSize.</p>
	 *
	 * @param array a T object
	 * @param atLeast a int
	 * @param message a {@link java.lang.String} object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireAtLeastSize(T array, int atLeast, String message)
	{
		if(Array.getLength(array) < atLeast)
		{
			throw new IllegalArgumentException(message);
		}
		return array;
	}

	/**
	 * <p>requireAtMostSize.</p>
	 *
	 * @param array a T object
	 * @param atMost a int
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireAtMostSize(T array, int atMost)
	{
		if(Array.getLength(array) > atMost)
		{
			throw new IllegalArgumentException();
		}
		return array;
	}

	/**
	 * <p>requireAtMostSize.</p>
	 *
	 * @param array a T object
	 * @param atMost a int
	 * @param message a {@link java.lang.String} object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireAtMostSize(T array, int atMost, String message)
	{
		if(Array.getLength(array) > atMost)
		{
			throw new IllegalArgumentException(message);
		}
		return array;
	}

	/**
	 * <p>requireSize.</p>
	 *
	 * @param array a T object
	 * @param length a int
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireSize(T array, int length)
	{
		if(Array.getLength(array) != length)
		{
			throw new IllegalArgumentException();
		}
		return array;
	}

	/**
	 * <p>requireSize.</p>
	 *
	 * @param array a T object
	 * @param length a int
	 * @param message a {@link java.lang.String} object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T requireSize(T array, int length, String message)
	{
		if(Array.getLength(array) != length)
		{
			throw new IllegalArgumentException(message);
		}
		return array;
	}

	private Requirements()
	{}
}