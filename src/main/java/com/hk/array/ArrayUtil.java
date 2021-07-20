package com.hk.array;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class gives simple tools and utilities for working with arrays.
 * From copying them to extending them to primitive arrays to object arrays.
 *
 * <p> All the utilities are here.
 *
 * @author theKayani
 */
public class ArrayUtil
{
	/**
	 * Returns an {@link com.hk.array.ImmutableArray} instance of the given array.
	 *
	 * @param array The array that should be given to the ImmutableArray
	 * @return The {@link com.hk.array.ImmutableArray} instance.
	 * @param <T> a T class
	 */
	public static <T> ImmutableArray<T> immutableArrayOf(Object array)
	{
		if(array instanceof boolean[])
			return new ImmutableArray<>((boolean[]) array);
		else if(array instanceof byte[])
			return new ImmutableArray<>((byte[]) array);
		else if(array instanceof short[])
			return new ImmutableArray<>((short[]) array);
		else if(array instanceof char[])
			return new ImmutableArray<>((char[]) array);
		else if(array instanceof int[])
			return new ImmutableArray<>((int[]) array);
		else if(array instanceof float[])
			return new ImmutableArray<>((float[]) array);
		else if(array instanceof long[])
			return new ImmutableArray<>((long[]) array);
		else if(array instanceof double[])
			return new ImmutableArray<>((double[]) array);
		else
			return new ImmutableArray<>((Object[]) array);
	}

	/**
	 * Swaps the two objects in the array. This can also be given primitive arrays as well.
	 * Also returns the array object given to the method.
	 *
	 * @param arr The array object. Will give an exception if arr isn't an Array
	 * @param index1 The index of the first object to swap.
	 * @param index2 The index of the second object to swap.
	 * @return The array back.
	 * @param <T> a T class
	 */
	public static <T> Object swap(Object arr, int index1, int index2)
	{
		Object t = Array.get(arr, index1);
		Array.set(arr, index1, Array.get(arr, index2));
		Array.set(arr, index2, t);
		return arr;
	}

	/**
	 * <p>shuffleArray.</p>
	 *
	 * @param arr a T object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T shuffleArray(T arr)
	{
		return shuffleArray(arr, ThreadLocalRandom.current());
	}

	/**
	 * <p>shuffleArray.</p>
	 *
	 * @param arr a T object
	 * @param rand a {@link java.util.Random} object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T> T shuffleArray(T arr, Random rand)
	{
		int l = Array.getLength(arr);
		for (int i = l - 1; i >= 0; i--)
		{
			swap(arr, i, rand.nextInt(i + 1));
		}
		return arr;
	}

	/**
	 * This converts a list to it's primitive state.
	 * In this case, it converts an {@link java.lang.Integer} list to a
	 * int array.
	 *
	 * @param list The list to convert
	 * @return The primitive array instance of the list's contents.
	 */
	public static int[] toIntArray(List<Integer> list)
	{
		int[] array = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This converts a list to it's primitive state.
	 * In this case, it converts a {@link java.lang.Float} list to a
	 * float array.
	 *
	 * @param list The list to convert
	 * @return The primitive array instance of the list's contents.
	 */
	public static float[] toFloatArray(List<Float> list)
	{
		float[] array = new float[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This converts a list to it's primitive state.
	 * In this case, it converts a {@link java.lang.Double} list to a
	 * double array.
	 *
	 * @param list The list to convert
	 * @return The primitive array instance of the list's contents.
	 */
	public static double[] toDoubleArray(List<Double> list)
	{
		double[] array = new double[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This converts a list to it's primitive state.
	 * In this case, it converts a {@link java.lang.Character} list to a
	 * char array.
	 *
	 * @param list The list to convert
	 * @return The primitive array instance of the list's contents.
	 */
	public static char[] toCharArray(List<Character> list)
	{
		char[] array = new char[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This converts a list to it's primitive state.
	 * In this case, it converts a {@link java.lang.Long} list to a
	 * long array.
	 *
	 * @param list The list to convert
	 * @return The primitive array instance of the list's contents.
	 */
	public static long[] toLongArray(List<Long> list)
	{
		long[] array = new long[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This converts a list to it's primitive state.
	 * In this case, it converts a {@link java.lang.Short} list to a
	 * short array.
	 *
	 * @param list The list to convert
	 * @return The primitive array instance of the list's contents.
	 */
	public static short[] toShortArray(List<Short> list)
	{
		short[] array = new short[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This converts a list to it's primitive state.
	 * In this case, it converts a {@link java.lang.Byte} list to a
	 * byte array.
	 *
	 * @param list The list to convert
	 * @return The primitive array instance of the list's contents.
	 */
	public static byte[] toByteArray(List<Byte> list)
	{
		byte[] array = new byte[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This converts a list to it's primitive state.
	 * In this case, it converts a {@link java.lang.Boolean} list to a
	 * boolean array.
	 *
	 * @param list The list to convert
	 * @return The primitive array instance of the list's contents.
	 */
	public static boolean[] toBooleanArray(List<Boolean> list)
	{
		boolean[] array = new boolean[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This increases the size of the given object array by one.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but a size higher.
	 * @param <T> a T class
	 */
	public static <T> T[] growArray(T[] array)
	{
		return Arrays.copyOf(array, array.length + 1);
	}

	/**
	 * This increases the size of the given int array by one.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but a size higher.
	 */
	public static int[] growArray(int[] array)
	{
		return Arrays.copyOf(array, array.length + 1);
	}

	/**
	 * This increases the size of the given double array by one.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but a size higher.
	 */
	public static double[] growArray(double[] array)
	{
		return Arrays.copyOf(array, array.length + 1);
	}

	/**
	 * This increases the size of the given float array by one.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but a size higher.
	 */
	public static float[] growArray(float[] array)
	{
		return Arrays.copyOf(array, array.length + 1);
	}

	/**
	 * This increases the size of the given short array by one.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but a size higher.
	 */
	public static short[] growArray(short[] array)
	{
		return Arrays.copyOf(array, array.length + 1);
	}

	/**
	 * This increases the size of the given byte array by one.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but a size higher.
	 */
	public static byte[] growArray(byte[] array)
	{
		return Arrays.copyOf(array, array.length + 1);
	}

	/**
	 * This increases the size of the given long array by one.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but a size higher.
	 */
	public static long[] growArray(long[] array)
	{
		return Arrays.copyOf(array, array.length + 1);
	}

	/**
	 * This increases the size of the given boolean array by one.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but a size higher.
	 */
	public static boolean[] growArray(boolean[] array)
	{
		return Arrays.copyOf(array, array.length + 1);
	}

	/**
	 * This increases the size of the given char array by one.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but a size higher.
	 */
	public static char[] growArray(char[] array)
	{
		return Arrays.copyOf(array, array.length + 1);
	}

	/**
	 * This increases the size of the given object array by the amount to grow.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but amtToGrow more length.
	 * @param amtToGrow a int
	 * @param <T> a T class
	 */
	public static <T> T[] growArrayBy(T[] array, int amtToGrow)
	{
		return Arrays.copyOf(array, array.length + amtToGrow);
	}

	/**
	 * This increases the size of the given int array by the amount to grow.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but amtToGrow more length.
	 * @param amtToGrow a int
	 */
	public static int[] growArrayBy(int[] array, int amtToGrow)
	{
		return Arrays.copyOf(array, array.length + amtToGrow);
	}

	/**
	 * This increases the size of the given double array by the amount to grow.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but amtToGrow more length.
	 * @param amtToGrow a int
	 */
	public static double[] growArrayBy(double[] array, int amtToGrow)
	{
		return Arrays.copyOf(array, array.length + amtToGrow);
	}

	/**
	 * This increases the size of the given float array by the amount to grow.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but amtToGrow more length.
	 * @param amtToGrow a int
	 */
	public static float[] growArrayBy(float[] array, int amtToGrow)
	{
		return Arrays.copyOf(array, array.length + amtToGrow);
	}

	/**
	 * This increases the size of the given short array by the amount to grow.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but amtToGrow more length.
	 * @param amtToGrow a int
	 */
	public static short[] growArrayBy(short[] array, int amtToGrow)
	{
		return Arrays.copyOf(array, array.length + amtToGrow);
	}

	/**
	 * This increases the size of the given byte array by the amount to grow.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but amtToGrow more length.
	 * @param amtToGrow a int
	 */
	public static byte[] growArrayBy(byte[] array, int amtToGrow)
	{
		return Arrays.copyOf(array, array.length + amtToGrow);
	}

	/**
	 * This increases the size of the given long array by the amount to grow.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but amtToGrow more length.
	 * @param amtToGrow a int
	 */
	public static long[] growArrayBy(long[] array, int amtToGrow)
	{
		return Arrays.copyOf(array, array.length + amtToGrow);
	}

	/**
	 * This increases the size of the given boolean array by the amount to grow.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but amtToGrow more length.
	 * @param amtToGrow a int
	 */
	public static boolean[] growArrayBy(boolean[] array, int amtToGrow)
	{
		return Arrays.copyOf(array, array.length + amtToGrow);
	}

	/**
	 * This increases the size of the given char array by the amount to grow.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but amtToGrow more length.
	 * @param amtToGrow a int
	 */
	public static char[] growArrayBy(char[] array, int amtToGrow)
	{
		return Arrays.copyOf(array, array.length + amtToGrow);
	}

	/**
	 * This increases the size of the given int array at the specified index by one.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @param indexToGrow The index to grow the array at.
	 * @return A new array with the same contents but one more length at the given index.
	 */
	public static int[] growArrayAt(int[] array, int indexToGrow)
	{
		int[] arr1 = Arrays.copyOfRange(array, 0, indexToGrow);
		int[] arr2 = new int[1];
		int[] arr3 = Arrays.copyOfRange(array, indexToGrow, array.length);
		return Concat.concat(arr1, arr2, arr3);
	}

	/**
	 * This increases the size of the given object array by the amount to grow.
	 * <p><i>NOTE: This doesn't alter the original array given!</i>
	 *
	 * @param array The array to grow.
	 * @return A new array with the same contents but amtToGrow more length.
	 * @param indexToGrow a int
	 * @param <T> a T class
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] growArrayAt(T[] array, int indexToGrow)
	{
		T[] arr1 = Arrays.copyOfRange(array, 0, indexToGrow);
		T[] arr2 = (T[]) Array.newInstance(array.getClass().getComponentType(), 1);
		T[] arr3 = Arrays.copyOfRange(array, indexToGrow, array.length);
		return Concat.concat(arr1, arr2, arr3);
	}

	/**
	 * <p>toFloatArray.</p>
	 *
	 * @param arr a float
	 * @return an array of {@link float} objects
	 */
	public static float[] toFloatArray(float... arr)
	{
		return arr;
	}

	/**
	 * <p>toDoubleArray.</p>
	 *
	 * @param arr a double
	 * @return an array of {@link double} objects
	 */
	public static double[] toDoubleArray(double... arr)
	{
		return arr;
	}

	/**
	 * <p>toIntArray.</p>
	 *
	 * @param arr a int
	 * @return an array of {@link int} objects
	 */
	public static int[] toIntArray(int... arr)
	{
		return arr;
	}

	/**
	 * <p>toShortArray.</p>
	 *
	 * @param arr a short
	 * @return an array of {@link short} objects
	 */
	public static short[] toShortArray(short... arr)
	{
		return arr;
	}

	/**
	 * <p>toByteArray.</p>
	 *
	 * @param arr a byte
	 * @return an array of {@link byte} objects
	 */
	public static byte[] toByteArray(byte... arr)
	{
		return arr;
	}

	/**
	 * <p>toLongArray.</p>
	 *
	 * @param arr a long
	 * @return an array of {@link long} objects
	 */
	public static long[] toLongArray(long... arr)
	{
		return arr;
	}

	/**
	 * <p>toCharArray.</p>
	 *
	 * @param arr a char
	 * @return an array of {@link char} objects
	 */
	public static char[] toCharArray(char... arr)
	{
		return arr;
	}

	/**
	 * <p>toBooleanArray.</p>
	 *
	 * @param arr a boolean
	 * @return an array of {@link boolean} objects
	 */
	public static boolean[] toBooleanArray(boolean... arr)
	{
		return arr;
	}

	/**
	 * <p>toArray.</p>
	 *
	 * @param arr a T object
	 * @param <T> a T class
	 * @return an array of T[] objects
	 */
	@SafeVarargs
	public static <T> T[] toArray(T... arr)
	{
		return arr;
	}

	/**
	 * <p>toObjIntegerArray.</p>
	 *
	 * @param arr an array of {@link int} objects
	 * @return an array of {@link java.lang.Integer} objects
	 */
	public static Integer[] toObjIntegerArray(int[] arr)
	{
		Integer[] arr1 = new Integer[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			arr1[i] = arr[i];
		}
		return arr1;
	}

	/**
	 * <p>toObjFloatArray.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return an array of {@link java.lang.Float} objects
	 */
	public static Float[] toObjFloatArray(float[] arr)
	{
		Float[] arr1 = new Float[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			arr1[i] = arr[i];
		}
		return arr1;
	}

	/**
	 * <p>toObjDoubleArray.</p>
	 *
	 * @param arr an array of {@link double} objects
	 * @return an array of {@link java.lang.Double} objects
	 */
	public static Double[] toObjDoubleArray(double[] arr)
	{
		Double[] arr1 = new Double[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			arr1[i] = arr[i];
		}
		return arr1;
	}

	/**
	 * <p>toObjLongArray.</p>
	 *
	 * @param arr an array of {@link long} objects
	 * @return an array of {@link java.lang.Long} objects
	 */
	public static Long[] toObjLongArray(long[] arr)
	{
		Long[] arr1 = new Long[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			arr1[i] = arr[i];
		}
		return arr1;
	}

	/**
	 * <p>toObjShortArray.</p>
	 *
	 * @param arr an array of {@link short} objects
	 * @return an array of {@link java.lang.Short} objects
	 */
	public static Short[] toObjShortArray(short[] arr)
	{
		Short[] arr1 = new Short[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			arr1[i] = arr[i];
		}
		return arr1;
	}

	/**
	 * <p>toObjByteArray.</p>
	 *
	 * @param arr an array of {@link byte} objects
	 * @return an array of {@link java.lang.Byte} objects
	 */
	public static Byte[] toObjByteArray(byte[] arr)
	{
		Byte[] arr1 = new Byte[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			arr1[i] = arr[i];
		}
		return arr1;
	}

	/**
	 * <p>toObjCharacterArray.</p>
	 *
	 * @param arr an array of {@link char} objects
	 * @return an array of {@link java.lang.Character} objects
	 */
	public static Character[] toObjCharacterArray(char[] arr)
	{
		Character[] arr1 = new Character[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			arr1[i] = arr[i];
		}
		return arr1;
	}

	/**
	 * <p>toObjBooleanArray.</p>
	 *
	 * @param arr an array of {@link boolean} objects
	 * @return an array of {@link java.lang.Boolean} objects
	 */
	public static Boolean[] toObjBooleanArray(boolean[] arr)
	{
		Boolean[] arr1 = new Boolean[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			arr1[i] = arr[i];
		}
		return arr1;
	}

	private ArrayUtil()
	{}
}
