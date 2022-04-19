package com.hk.collections.lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;

import junit.framework.TestCase;

public class ImmutableListTest extends TestCase
{
	public void testImmutableList()
	{
		ImmutableList<String> strLst = new ImmutableList<>(Collections.<String>emptyList());
		assertEquals(strLst.size(), 0);
		assertEquals(strLst, Collections.<String>emptyList());

		strLst = new ImmutableList<>(Collections.singletonList("abc"));
		assertEquals(strLst.size(), 1);
		assertEquals(strLst, Collections.singletonList("abc"));

		strLst = new ImmutableList<>(Arrays.asList("a", "b", "c"));
		assertEquals(strLst.size(), 3);
		assertEquals(strLst, Arrays.asList("a", "b", "c"));

		ImmutableList<Object> objLst = new ImmutableList<>(Collections.emptyList());
		assertEquals(objLst.size(), 0);
		assertEquals(objLst, Collections.<String>emptyList());

		Object o = new Object();
		objLst = new ImmutableList<>(Collections.singletonList(o));
		assertEquals(objLst.size(), 1);
		assertEquals(objLst, Collections.singletonList(o));

		Object o1 = new Object();
		Object o2 = new Object();
		Object o3 = new Object();
		objLst = new ImmutableList<>(Arrays.asList(o1, o2, o3));
		assertEquals(objLst.size(), 3);
		assertEquals(objLst, Arrays.asList(o1, o2, o3));

		ImmutableList<Double> dblLst = new ImmutableList<>(Collections.<Double>emptyList());
		assertEquals(dblLst.size(), 0);
		assertEquals(dblLst, Collections.<Double>emptyList());

		dblLst = new ImmutableList<>(Collections.singletonList(2.0));
		assertEquals(dblLst.size(), 1);
		assertEquals(dblLst, Collections.singletonList(2.0));

		dblLst = new ImmutableList<>(Arrays.asList(-1D, -10D, -100D));
		assertEquals(dblLst.size(), 3);
		assertEquals(dblLst, Arrays.asList(-1D, -10D, -100D));
	}

	public void testIterator()
	{
		ImmutableList<String> strLst = new ImmutableList<>(Collections.<String>emptyList());
		Iterator<String> strItr = strLst.iterator();
		assertFalse(strItr.hasNext());

		strLst = new ImmutableList<>(Collections.singletonList("abc"));
		strItr = strLst.iterator();
		assertTrue(strItr.hasNext());
		assertEquals(strItr.next(), "abc");
		assertFalse(strItr.hasNext());

		strLst = new ImmutableList<>(Arrays.asList("a", "b", "c"));
		strItr = strLst.iterator();
		assertTrue(strItr.hasNext());
		assertEquals(strItr.next(), "a");
		assertTrue(strItr.hasNext());
		assertEquals(strItr.next(), "b");
		assertTrue(strItr.hasNext());
		assertEquals(strItr.next(), "c");
		assertFalse(strItr.hasNext());

		ImmutableList<Object> objLst = new ImmutableList<>(Collections.emptyList());
		Iterator<Object> objItr = objLst.iterator();
		assertFalse(objItr.hasNext());

		Object o = new Object();
		objLst = new ImmutableList<>(Collections.singletonList(o));
		objItr = objLst.iterator();
		assertTrue(objItr.hasNext());
		assertEquals(objItr.next(), o);
		assertFalse(objItr.hasNext());

		Object o1 = new Object();
		Object o2 = new Object();
		Object o3 = new Object();
		objLst = new ImmutableList<>(Arrays.asList(o1, o2, o3));
		objItr = objLst.iterator();
		assertTrue(objItr.hasNext());
		assertEquals(objItr.next(), o1);
		assertTrue(objItr.hasNext());
		assertEquals(objItr.next(), o2);
		assertTrue(objItr.hasNext());
		assertEquals(objItr.next(), o3);
		assertFalse(objItr.hasNext());

		ImmutableList<Double> dblLst = new ImmutableList<>(Collections.<Double>emptyList());
		Iterator<Double> dblItr = dblLst.iterator();
		assertFalse(dblItr.hasNext());

		dblLst = new ImmutableList<>(Collections.singletonList(2.0));
		dblItr = dblLst.iterator();
		assertTrue(dblItr.hasNext());
		assertEquals(dblItr.next(), 2.0);
		assertFalse(dblItr.hasNext());

		dblLst = new ImmutableList<>(Arrays.asList(-1D, -10D, -100D));
		dblItr = dblLst.iterator();
		assertTrue(dblItr.hasNext());
		assertEquals(dblItr.next(), -1D);
		assertTrue(dblItr.hasNext());
		assertEquals(dblItr.next(), -10D);
		assertTrue(dblItr.hasNext());
		assertEquals(dblItr.next(), -100D);
		assertFalse(dblItr.hasNext());
	}

	public void testSubList()
	{
		ImmutableList<String> strLst = new ImmutableList<>(Collections.<String>emptyList());
		assertEquals(strLst.subList(0, 0).size(), 0);
		assertEquals(strLst.subList(0, 0), Collections.<String>emptyList());

		strLst = new ImmutableList<>(Collections.singletonList("abc"));
		assertEquals(strLst.subList(0, 1).size(), 1);
		assertEquals(strLst.subList(0, 1), Collections.singletonList("abc"));

		strLst = new ImmutableList<>(Arrays.asList("a", "b", "c"));
		assertEquals(strLst.subList(0, 3).size(), 3);
		assertEquals(strLst.subList(0, 3), Arrays.asList("a", "b", "c"));
		assertEquals(strLst.subList(0, 1), Collections.singletonList("a"));
		assertEquals(strLst.subList(1, 2), Collections.singletonList("b"));
		assertEquals(strLst.subList(2, 3), Collections.singletonList("c"));
		assertEquals(strLst.subList(1, 3), Arrays.asList("b", "c"));
		assertEquals(strLst.subList(0, 2), Arrays.asList("a", "b"));

		ImmutableList<Object> objLst = new ImmutableList<>(Collections.emptyList());
		assertEquals(objLst.subList(0, 0).size(), 0);
		assertEquals(objLst.subList(0, 0), Collections.<String>emptyList());

		Object o = new Object();
		objLst = new ImmutableList<>(Collections.singletonList(o));
		assertEquals(objLst.subList(0, 1).size(), 1);
		assertEquals(objLst.subList(0, 1), Collections.singletonList(o));

		Object o1 = new Object();
		Object o2 = new Object();
		Object o3 = new Object();
		objLst = new ImmutableList<>(Arrays.asList(o1, o2, o3));
		assertEquals(objLst.subList(0, 3).size(), 3);
		assertEquals(objLst.subList(0, 3), Arrays.asList(o1, o2, o3));
		assertEquals(objLst.subList(0, 1), Collections.singletonList(o1));
		assertEquals(objLst.subList(1, 2), Collections.singletonList(o2));
		assertEquals(objLst.subList(2, 3), Collections.singletonList(o3));
		assertEquals(objLst.subList(1, 3), Arrays.asList(o2, o3));
		assertEquals(objLst.subList(0, 2), Arrays.asList(o1, o2));

		ImmutableList<Double> dblLst = new ImmutableList<>(Collections.<Double>emptyList());
		assertEquals(dblLst.subList(0, 0).size(), 0);
		assertEquals(dblLst.subList(0, 0), Collections.<Double>emptyList());

		dblLst = new ImmutableList<>(Collections.singletonList(2.0));
		assertEquals(dblLst.subList(0, 1).size(), 1);
		assertEquals(dblLst.subList(0, 1), Collections.singletonList(2.0));

		dblLst = new ImmutableList<>(Arrays.asList(-1D, -10D, -100D));
		assertEquals(dblLst.subList(0, 3).size(), 3);
		assertEquals(dblLst.subList(0, 3), Arrays.asList(-1D, -10D, -100D));
		assertEquals(dblLst.subList(0, 1), Collections.singletonList(-1D));
		assertEquals(dblLst.subList(1, 2), Collections.singletonList(-10D));
		assertEquals(dblLst.subList(2, 3), Collections.singletonList(-100D));
		assertEquals(dblLst.subList(1, 3), Arrays.asList(-10D, -100D));
		assertEquals(dblLst.subList(0, 2), Arrays.asList(-1D, -10D));
	}

	public void testClone()
	{
		ImmutableList<String> strLst = new ImmutableList<>(Collections.<String>emptyList());
		assertEquals(strLst.clone(), strLst);

		strLst = new ImmutableList<>(Collections.singletonList("abc"));
		assertEquals(strLst.clone(), strLst);

		strLst = new ImmutableList<>(Arrays.asList("a", "b", "c"));
		assertEquals(strLst.clone(), strLst);

		ImmutableList<Object> objLst = new ImmutableList<>(Collections.emptyList());
		assertEquals(objLst.clone(), objLst);

		Object o = new Object();
		objLst = new ImmutableList<>(Collections.singletonList(o));
		assertEquals(objLst.clone(), objLst);

		Object o1 = new Object();
		Object o2 = new Object();
		Object o3 = new Object();
		objLst = new ImmutableList<>(Arrays.asList(o1, o2, o3));
		assertEquals(objLst.clone(), objLst);

		ImmutableList<Double> dblLst = new ImmutableList<>(Collections.<Double>emptyList());
		assertEquals(dblLst.clone(), dblLst);

		dblLst = new ImmutableList<>(Collections.singletonList(2.0));
		assertEquals(dblLst.clone(), dblLst);

		dblLst = new ImmutableList<>(Arrays.asList(-1D, -10D, -100D));
		assertEquals(dblLst.clone(), dblLst);
	}

	public void testListIterator()
	{
		ImmutableList<String> strLst = new ImmutableList<>(Collections.<String>emptyList());
		ListIterator<String> strItr = strLst.listIterator();
		assertFalse(strItr.hasNext());
		assertEquals(strItr.nextIndex(), strLst.size());

		strLst = new ImmutableList<>(Collections.singletonList("abc"));
		strItr = strLst.listIterator();
		assertFalse(strItr.hasPrevious());
		assertTrue(strItr.hasNext());
		assertEquals(strItr.nextIndex(), 0);
		assertEquals(strItr.next(), "abc");
		assertEquals(strItr.previousIndex(), 0);
		assertFalse(strItr.hasNext());

		strLst = new ImmutableList<>(Arrays.asList("a", "b", "c"));
		strItr = strLst.listIterator();
		assertFalse(strItr.hasPrevious());
		assertTrue(strItr.hasNext());
		assertEquals(strItr.nextIndex(), 0);
		assertEquals(strItr.next(), "a");
		assertEquals(strItr.previousIndex(), 0);
		assertTrue(strItr.hasNext());
		assertEquals(strItr.nextIndex(), 1);
		assertEquals(strItr.next(), "b");
		assertEquals(strItr.previousIndex(), 1);
		assertTrue(strItr.hasPrevious());
		assertEquals(strItr.nextIndex(), 2);
		assertEquals(strItr.previous(), "b");
		assertEquals(strItr.previousIndex(), 0);
		assertTrue(strItr.hasPrevious());
		assertEquals(strItr.nextIndex(), 1);
		assertEquals(strItr.previous(), "a");
		assertEquals(strItr.previousIndex(), -1);
		assertTrue(strItr.hasNext());
		assertEquals(strItr.nextIndex(), 0);
		assertEquals(strItr.next(), "a");
		assertEquals(strItr.previousIndex(), 0);
		assertTrue(strItr.hasNext());
		assertEquals(strItr.nextIndex(), 1);
		assertEquals(strItr.next(), "b");
		assertEquals(strItr.previousIndex(), 1);		
		assertTrue(strItr.hasNext());
		assertEquals(strItr.nextIndex(), 2);
		assertEquals(strItr.next(), "c");
		assertEquals(strItr.previousIndex(), 2);
		assertFalse(strItr.hasNext());

		ImmutableList<Object> objLst = new ImmutableList<>(Collections.emptyList());
		ListIterator<Object> objItr = objLst.listIterator();
		assertFalse(objItr.hasNext());

		Object o = new Object();
		objLst = new ImmutableList<>(Collections.singletonList(o));
		objItr = objLst.listIterator();
		assertFalse(objItr.hasPrevious());
		assertTrue(objItr.hasNext());
		assertEquals(objItr.nextIndex(), 0);
		assertEquals(objItr.next(), o);
		assertEquals(objItr.previousIndex(), 0);
		assertFalse(objItr.hasNext());

		Object o1 = new Object();
		Object o2 = new Object();
		Object o3 = new Object();
		objLst = new ImmutableList<>(Arrays.asList(o1, o2, o3));
		objItr = objLst.listIterator();
		assertFalse(objItr.hasPrevious());
		assertTrue(objItr.hasNext());
		assertEquals(objItr.nextIndex(), 0);
		assertEquals(objItr.next(), o1);
		assertEquals(objItr.previousIndex(), 0);
		assertTrue(objItr.hasNext());
		assertEquals(objItr.nextIndex(), 1);
		assertEquals(objItr.next(), o2);
		assertEquals(objItr.previousIndex(), 1);
		assertTrue(objItr.hasPrevious());
		assertEquals(objItr.nextIndex(), 2);
		assertEquals(objItr.previous(), o2);
		assertEquals(objItr.previousIndex(), 0);
		assertTrue(objItr.hasPrevious());
		assertEquals(objItr.nextIndex(), 1);
		assertEquals(objItr.previous(), o1);
		assertEquals(objItr.previousIndex(), -1);
		assertTrue(objItr.hasNext());
		assertEquals(objItr.nextIndex(), 0);
		assertEquals(objItr.next(), o1);
		assertEquals(objItr.previousIndex(), 0);
		assertTrue(objItr.hasNext());
		assertEquals(objItr.nextIndex(), 1);
		assertEquals(objItr.next(), o2);
		assertEquals(objItr.previousIndex(), 1);		
		assertTrue(objItr.hasNext());
		assertEquals(objItr.nextIndex(), 2);
		assertEquals(objItr.next(), o3);
		assertEquals(objItr.previousIndex(), 2);
		assertFalse(objItr.hasNext());

		ImmutableList<Double> dblLst = new ImmutableList<>(Collections.<Double>emptyList());
		ListIterator<Double> dblItr = dblLst.listIterator();
		assertFalse(dblItr.hasNext());

		dblLst = new ImmutableList<>(Collections.singletonList(2.0));
		dblItr = dblLst.listIterator();
		assertFalse(dblItr.hasPrevious());
		assertTrue(dblItr.hasNext());
		assertEquals(dblItr.nextIndex(), 0);
		assertEquals(dblItr.next(), 2.0);
		assertEquals(dblItr.previousIndex(), 0);
		assertFalse(dblItr.hasNext());

		dblLst = new ImmutableList<>(Arrays.asList(-1D, -10D, -100D));
		dblItr = dblLst.listIterator();
		assertFalse(dblItr.hasPrevious());
		assertTrue(dblItr.hasNext());
		assertEquals(dblItr.nextIndex(), 0);
		assertEquals(dblItr.next(), -1D);
		assertEquals(dblItr.previousIndex(), 0);
		assertTrue(dblItr.hasNext());
		assertEquals(dblItr.nextIndex(), 1);
		assertEquals(dblItr.next(), -10D);
		assertEquals(dblItr.previousIndex(), 1);
		assertTrue(dblItr.hasPrevious());
		assertEquals(dblItr.nextIndex(), 2);
		assertEquals(dblItr.previous(), -10D);
		assertEquals(dblItr.previousIndex(), 0);
		assertTrue(dblItr.hasPrevious());
		assertEquals(dblItr.nextIndex(), 1);
		assertEquals(dblItr.previous(), -1D);
		assertEquals(dblItr.previousIndex(), -1);
		assertTrue(dblItr.hasNext());
		assertEquals(dblItr.nextIndex(), 0);
		assertEquals(dblItr.next(), -1D);
		assertEquals(dblItr.previousIndex(), 0);
		assertTrue(dblItr.hasNext());
		assertEquals(dblItr.nextIndex(), 1);
		assertEquals(dblItr.next(), -10D);
		assertEquals(dblItr.previousIndex(), 1);		
		assertTrue(dblItr.hasNext());
		assertEquals(dblItr.nextIndex(), 2);
		assertEquals(dblItr.next(), -100D);
		assertEquals(dblItr.previousIndex(), 2);
		assertFalse(dblItr.hasNext());
	}

	public void testFails()
	{
		ImmutableList<String> strLst = new ImmutableList<>(Collections.<String>emptyList());
		assertEquals(strLst.size(), 0);
		failAdd(strLst, "ERROR");
		assertEquals(strLst.size(), 0);

		ImmutableList<Object> objLst = new ImmutableList<>(Collections.emptyList());
		assertEquals(objLst.size(), 0);
		failAdd(objLst, new Object());
		assertEquals(objLst.size(), 0);

		ImmutableList<Double> dblLst = new ImmutableList<>(Collections.<Double>emptyList());
		assertEquals(dblLst.size(), 0);
		failAdd(dblLst, 0D);
		assertEquals(dblLst.size(), 0);
	}

	private <E> void failAdd(ImmutableList<E> lst, E val)
	{
		try
		{
			lst.add(val);			
			fail();
		}
		catch(Exception e)
		{
			assertTrue(e.getLocalizedMessage().contains("immutable"));
			assertTrue(UnsupportedOperationException.class.isAssignableFrom(e.getClass()));
		}
	}
}