package com.hk.array;

import junit.framework.TestCase;

public class ImmutableArrayTest extends TestCase
{
	@Override
	public void setUp()
	{
		// TODO: create or delete
	}

	public void testImmutableArray()
	{
		ImmutableArray<String> strArr = new ImmutableArray<>(new String[0]);
		assertEquals(0, strArr.length);
	}

	public void testIterator()
	{
		// TODO: ImmutableArray.iterator()
	}

	public void testGet()
	{
		// TODO: ImmutableArray.get(int)
	}

	public void testToArray()
	{
		// TODO: ImmutableArray.toArray()
	}

	@Override
	public void tearDown()
	{
		// TODO: create or delete
	}
}