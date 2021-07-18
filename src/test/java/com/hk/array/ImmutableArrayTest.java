package com.hk.array;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import junit.framework.TestCase;

public class ImmutableArrayTest extends TestCase
{
	
	@Override
	public void setUp()
	{}

	public void testImmutableArray()
	{
		int[] lens = { 0, 1, 10, 100 };
		
		for(int len : lens)
		{
			assertEquals(len, new ImmutableArray<String>(new String[len]).length);
			assertEquals(len, new ImmutableArray<Object>(new Object[len]).length);
			assertEquals(len, new ImmutableArray<Double>(new Double[len]).length);
			assertEquals(len, new ImmutableArray<Double>(new double[len]).length);
		}
	}

	public void testGet()
	{
		String[] stringArr = new String[] {
				"a", "b", "c",
				"1", "2", "3",
				"x", "y", "z"
		};
		ImmutableArray<String> imStringArr = new ImmutableArray<>(stringArr);
		
		for(int i = 0; i < stringArr.length; i++)
			assertSame(stringArr[i], imStringArr.get(i));

		File[] fileArr = File.listRoots();
		ImmutableArray<File> imFileArr = new ImmutableArray<>(fileArr);
		
		for(int i = 0; i < fileArr.length; i++)
			assertSame(fileArr[i], imFileArr.get(i));

		Object[] objArr = new Object[1_000_000];
		for(int i = 0; i < objArr.length; i++)
			objArr[i] = new Object();
		ImmutableArray<Object> imObjArr = new ImmutableArray<>(objArr);
		
		for(int i = 0; i < objArr.length; i++)
			assertSame(objArr[i], imObjArr.get(i));

		Double[] doubleArr = new Double[20];
		for(int i = 0; i < 18; i++)
			doubleArr[i] = Math.pow(2, -i);
		doubleArr[18] = Double.MAX_VALUE;
		doubleArr[19] = Double.MIN_VALUE;
		ImmutableArray<Double> imDoubleArr = new ImmutableArray<>(doubleArr);
		
		for(int i = 0; i < doubleArr.length; i++)
			assertSame(doubleArr[i], imDoubleArr.get(i));

		double[] pDoubleArr = new double[] {
			-1D, 0D, 1D,
			Math.PI, Math.E, Math.sqrt(2D),
			0.5D, 0.25D, 0.125D
		};
		imDoubleArr = new ImmutableArray<>(pDoubleArr);
		
		for(int i = 0; i < pDoubleArr.length; i++)
			assertEquals(pDoubleArr[i], imDoubleArr.get(i));
	}

	public void testIterator()
	{
		int i = 0;

		String[] stringArr = new String[] {
				"a", "b", "c",
				"1", "2", "3",
				"x", "y", "z"
		};
		ImmutableArray<String> imStringArr = new ImmutableArray<>(stringArr);
		Iterator<String> stringItr = imStringArr.iterator();
		
		i = 0;
		while(stringItr.hasNext())
			assertSame(stringArr[i++], stringItr.next());
		assertEquals(i, stringArr.length);

		File[] fileArr = File.listRoots();
		ImmutableArray<File> imFileArr = new ImmutableArray<>(fileArr);
		Iterator<File> fileItr = imFileArr.iterator();

		i = 0;
		while(fileItr.hasNext())
			assertSame(fileArr[i++], fileItr.next());
		assertEquals(i, fileArr.length);

		Object[] objArr = new Object[1_000_000];
		for(i = 0; i < objArr.length; i++)
			objArr[i] = new Object();
		ImmutableArray<Object> imObjArr = new ImmutableArray<>(objArr);
		Iterator<Object> objItr = imObjArr.iterator();

		i = 0;
		while(objItr.hasNext())
			assertSame(objArr[i++], objItr.next());
		assertEquals(i, objArr.length);

		Double[] doubleArr = new Double[20];
		for(i = 0; i < 18; i++)
			doubleArr[i] = Math.pow(2, -i);
		doubleArr[18] = Double.MAX_VALUE;
		doubleArr[19] = Double.MIN_VALUE;
		ImmutableArray<Double> imDoubleArr = new ImmutableArray<>(doubleArr);
		Iterator<Double> doubleItr = imDoubleArr.iterator();

		i = 0;
		while(doubleItr.hasNext())
			assertSame(doubleArr[i++], doubleItr.next());
		assertEquals(i, doubleArr.length);

		double[] pDoubleArr = new double[] {
			-1D, 0D, 1D,
			Math.PI, Math.E, Math.sqrt(2D),
			0.5D, 0.25D, 0.125D
		};
		imDoubleArr = new ImmutableArray<>(pDoubleArr);
		doubleItr = imDoubleArr.iterator();

		i = 0;
		while(doubleItr.hasNext())
			assertEquals(pDoubleArr[i++], doubleItr.next());
		assertEquals(i, pDoubleArr.length);
	}

	public void testToArray()
	{
		String[] stringArr = new String[] {
				"a", "b", "c",
				"1", "2", "3",
				"x", "y", "z"
		};
		
		assertTrue("arrays not equal", Arrays.equals(stringArr, new ImmutableArray<String>(stringArr).toArray()));

		File[] fileArr = File.listRoots();
		
		assertTrue("arrays not equal", Arrays.equals(fileArr, new ImmutableArray<File>(fileArr).toArray()));

		Object[] objArr = new Object[1_000_000];
		for(int i = 0; i < objArr.length; i++)
			objArr[i] = new Object();
		
		assertTrue("arrays not equal", Arrays.equals(objArr, new ImmutableArray<Object>(objArr).toArray()));

		Double[] doubleArr = new Double[20];
		for(int i = 0; i < 18; i++)
			doubleArr[i] = Math.pow(2, -i);
		doubleArr[18] = Double.MAX_VALUE;
		doubleArr[19] = Double.MIN_VALUE;
		
		assertTrue("arrays not equal", Arrays.equals(doubleArr, new ImmutableArray<Double>(doubleArr).toArray()));

		double[] pDoubleArr = new double[] {
			-1D, 0D, 1D,
			Math.PI, Math.E, Math.sqrt(2D),
			0.5D, 0.25D, 0.125D
		};
		
		assertTrue("arrays not equal", Arrays.equals(pDoubleArr, ArrayUtil.toDoubleArray(Arrays.asList(new ImmutableArray<Double>(pDoubleArr).toArray()))));
	}

	@Override
	public void tearDown()
	{
	}
}