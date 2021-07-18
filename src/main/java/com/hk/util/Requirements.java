package com.hk.util;

import java.lang.reflect.Array;

import com.hk.ex.OutOfBoundsException;

public class Requirements
{
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
	
	public static <T> T requireCondition(T orig, boolean condition)
	{
		if(!condition)
		{
			throw new IllegalArgumentException();
		}
		return orig;
	}
	
	public static <T> T requireCondition(T orig, boolean condition, String message)
	{
		if(!condition)
		{
			throw new IllegalArgumentException(message);
		}
		return orig;
	}
	
	public static <T> T requireNotCondition(T orig, boolean condition)
	{
		if(condition)
		{
			throw new IllegalArgumentException();
		}
		return orig;
	}
	
	public static <T> T requireNotCondition(T orig, boolean condition, String message)
	{
		if(condition)
		{
			throw new IllegalArgumentException(message);
		}
		return orig;
	}
	
	public static <T> T requireNotNull(T orig)
	{
		if(orig == null)
		{
			throw new NullPointerException();
		}
		return orig;
	}
	
	public static <T> T requireNotNull(T orig, String message)
	{
		if(orig == null)
		{
			throw new NullPointerException(message);
		}
		return orig;
	}
	
	public static <T> T requireAtLeastSize(T array, int atLeast)
	{
		if(Array.getLength(array) < atLeast)
		{
			throw new IllegalArgumentException();
		}
		return array;
	}
	
	public static <T> T requireAtLeastSize(T array, int atLeast, String message)
	{
		if(Array.getLength(array) < atLeast)
		{
			throw new IllegalArgumentException(message);
		}
		return array;
	}
	
	public static <T> T requireAtMostSize(T array, int atMost)
	{
		if(Array.getLength(array) > atMost)
		{
			throw new IllegalArgumentException();
		}
		return array;
	}
	
	public static <T> T requireAtMostSize(T array, int atMost, String message)
	{
		if(Array.getLength(array) > atMost)
		{
			throw new IllegalArgumentException(message);
		}
		return array;
	}
	
	public static <T> T requireSize(T array, int length)
	{
		if(Array.getLength(array) != length)
		{
			throw new IllegalArgumentException();
		}
		return array;
	}
	
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
