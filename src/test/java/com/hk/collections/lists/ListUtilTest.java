package com.hk.collections.lists;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;

public class ListUtilTest extends TestCase
{
	public void testNewArrayList()
	{
		ArrayList<Object> expectedObj, actualObj;
		ArrayList<String> expectedStr, actualStr;
		ArrayList<Double> expectedDbl, actualDbl;
		Object[] objectArr = { new File("does/not/exist"), "woo", new ByteArrayOutputStream() };
		String[] stringArr = { "blame", "it", "on", "my", "add", ",", "baby" };
		Double[] doubleArr = { -1D, 0D, Math.sqrt(2), 0.1D, 0.01D, 0.001D, Math.E, 0.002D, 0.1 + 0.2, Math.PI };

		actualObj = ListUtil.newArrayList();
		expectedObj = new ArrayList<>();
		assertEquals(expectedObj, actualObj);

		actualStr = ListUtil.newArrayList();
		expectedStr = new ArrayList<>();
		assertEquals(expectedStr, actualStr);

		actualDbl = ListUtil.newArrayList();
		expectedDbl = new ArrayList<>();
		assertEquals(expectedDbl, actualDbl);

		actualObj = ListUtil.newArrayList(objectArr);
		expectedObj = new ArrayList<>();
		Collections.addAll(expectedObj, objectArr);
		assertEquals(expectedObj, actualObj);

		actualStr = ListUtil.newArrayList(stringArr);
		expectedStr = new ArrayList<>();
		Collections.addAll(expectedStr, stringArr);
		assertEquals(expectedStr, actualStr);

		actualDbl = ListUtil.newArrayList(doubleArr);
		expectedDbl = new ArrayList<>();
		Collections.addAll(expectedDbl, doubleArr);
		assertEquals(expectedDbl, actualDbl);

		actualObj = ListUtil.newArrayListWith(objectArr);
		expectedObj = new ArrayList<>();
		Collections.addAll(expectedObj, objectArr);
		assertEquals(expectedObj, actualObj);

		actualStr = ListUtil.newArrayListWith(stringArr);
		expectedStr = new ArrayList<>();
		Collections.addAll(expectedStr, stringArr);
		assertEquals(expectedStr, actualStr);

		actualDbl = ListUtil.newArrayListWith(doubleArr);
		expectedDbl = new ArrayList<>();
		Collections.addAll(expectedDbl, doubleArr);
		assertEquals(expectedDbl, actualDbl);

		Collection<Object> objectCollection = Arrays.asList(objectArr);
		actualObj = ListUtil.newArrayList(objectCollection);
		expectedObj = new ArrayList<>(objectCollection);
		assertEquals(expectedObj, actualObj);

		Collection<String> stringCollection = Arrays.asList(stringArr);
		actualStr = ListUtil.newArrayList(stringCollection);
		expectedStr = new ArrayList<>(stringCollection);
		assertEquals(expectedStr, actualStr);

		Collection<Double> doubleCollection = Arrays.asList(doubleArr);
		actualDbl = ListUtil.newArrayList();
		expectedDbl = new ArrayList<>();
		assertEquals(expectedDbl, actualDbl);
	}

	public void testAddAll()
	{
		List<Object> expectedObj, actualObj;
		List<String> expectedStr, actualStr;
		List<Double> expectedDbl, actualDbl;

		Object[] objectArr = { new File("does/not/exist"), "woo", new ByteArrayOutputStream() };
		String[] stringArr = { "blame", "it", "on", "my", "ADD", ",", "baby" };
		Double[] doubleArr = { -1D, 0D, Math.sqrt(2), 0.1D, 0.01D, 0.001D, Math.E, 0.002D, 0.1 + 0.2, Math.PI };

		actualObj = new ArrayList<>();
		ListUtil.addAll(actualObj, objectArr);
		expectedObj = new ArrayList<>();
		Collections.addAll(expectedObj, objectArr);
		assertEquals(expectedObj, actualObj);

		actualStr = new ArrayList<>();
		ListUtil.addAll(actualStr, stringArr);
		expectedStr = new ArrayList<>();
		Collections.addAll(expectedStr, stringArr);
		assertEquals(expectedStr, actualStr);

		actualDbl = new ArrayList<>();
		ListUtil.addAll(actualDbl, doubleArr);
		expectedDbl = new ArrayList<>();
		Collections.addAll(expectedDbl, doubleArr);
		assertEquals(expectedDbl, actualDbl);

		actualObj = new ArrayList<>();
		ListUtil.addAll(actualObj, objectArr);
		expectedObj = new ArrayList<>();
		Collections.addAll(expectedObj, objectArr);
		assertEquals(expectedObj, actualObj);

		actualStr = new ArrayList<>();
		ListUtil.addAll(actualStr, stringArr);
		expectedStr = new ArrayList<>();
		Collections.addAll(expectedStr, stringArr);
		assertEquals(expectedStr, actualStr);

		actualDbl = new ArrayList<>();
		ListUtil.addAll(actualDbl, doubleArr);
		expectedDbl = new ArrayList<>();
		Collections.addAll(expectedDbl, doubleArr);
		assertEquals(expectedDbl, actualDbl);
	}

	public void testSwap()
	{
		Object obj1 = new Object(), obj2 = new Object(), obj3 = new Object();
		List<Object> expectedObj, actualObj;
		List<String> expectedStr, actualStr;
		List<Double> expectedDbl, actualDbl;

		expectedObj = Arrays.asList(obj1, obj3, obj2);
		assertSame(expectedObj, ListUtil.swap(expectedObj, 1, 2));
		actualObj = Arrays.asList(obj1, obj2, obj3);
		assertEquals(expectedObj, actualObj);

		assertSame(expectedObj, ListUtil.swap(expectedObj, 0, 1));
		actualObj = Arrays.asList(obj2, obj1, obj3);
		assertEquals(expectedObj, actualObj);

		assertSame(expectedObj, ListUtil.swap(expectedObj, 2, 0));
		actualObj = Arrays.asList(obj3, obj1, obj2);
		assertEquals(expectedObj, actualObj);

		expectedStr = Arrays.asList("you", "are", "how");
		assertSame(expectedStr, ListUtil.swap(expectedStr, 0, 2));
		actualStr = Arrays.asList("how", "are", "you");
		assertEquals(expectedStr, actualStr);

		assertSame(expectedStr, ListUtil.swap(expectedStr, 2, 1));
		actualStr = Arrays.asList("how", "you", "are");
		assertEquals(expectedStr, actualStr);

		assertSame(expectedStr, ListUtil.swap(expectedStr, 2, 0));
		actualStr = Arrays.asList("are", "you", "how");
		assertEquals(expectedStr, actualStr);

		expectedDbl = Arrays.asList(10D, 1D, 100D);
		assertSame(expectedDbl, ListUtil.swap(expectedDbl, 0, 2));
		actualDbl = Arrays.asList(100D, 1D, 10D);
		assertEquals(expectedDbl, actualDbl);

		assertSame(expectedDbl, ListUtil.swap(expectedDbl, 1, 0));
		actualDbl = Arrays.asList(1D, 100D, 10D);
		assertEquals(expectedDbl, actualDbl);

		assertSame(expectedDbl, ListUtil.swap(expectedDbl, 2, 1));
		actualDbl = Arrays.asList(1D, 10D, 100D);
		assertEquals(expectedDbl, actualDbl);
	}
}