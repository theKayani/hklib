package com.hk.array;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

public class ArrayUtilTest extends TestCase
{
	public void testToBooleanArray()
	{
		boolean[] bs;

		bs = ArrayUtil.toBooleanArray(Collections.<Boolean>emptyList());

		assertEquals(0, bs.length);

		bs = ArrayUtil.toBooleanArray(Collections.singletonList(true));

		assertEquals(1, bs.length);
		assertTrue(bs[0]);

		bs = ArrayUtil.toBooleanArray(Collections.singletonList(false));

		assertEquals(1, bs.length);
		assertFalse(bs[0]);

		bs = ArrayUtil.toBooleanArray(Arrays.asList(true, true));

		assertEquals(2, bs.length);
		assertTrue(bs[0]);
		assertTrue(bs[1]);

		bs = ArrayUtil.toBooleanArray(Arrays.asList(false, true));

		assertEquals(2, bs.length);
		assertFalse(bs[0]);
		assertTrue(bs[1]);

		bs = ArrayUtil.toBooleanArray(Arrays.asList(true, false));

		assertEquals(2, bs.length);
		assertTrue(bs[0]);
		assertFalse(bs[1]);

		bs = ArrayUtil.toBooleanArray(Arrays.asList(false, false));

		assertEquals(2, bs.length);
		assertFalse(bs[0]);
		assertFalse(bs[1]);

		bs = ArrayUtil.toBooleanArray();

		assertEquals(0, bs.length);

		bs = ArrayUtil.toBooleanArray(true);

		assertEquals(1, bs.length);
		assertTrue(bs[0]);

		bs = ArrayUtil.toBooleanArray(false);

		assertEquals(1, bs.length);
		assertFalse(bs[0]);

		bs = ArrayUtil.toBooleanArray(true, true);

		assertEquals(2, bs.length);
		assertTrue(bs[0]);
		assertTrue(bs[1]);

		bs = ArrayUtil.toBooleanArray(false, true);

		assertEquals(2, bs.length);
		assertFalse(bs[0]);
		assertTrue(bs[1]);

		bs = ArrayUtil.toBooleanArray(true, false);

		assertEquals(2, bs.length);
		assertTrue(bs[0]);
		assertFalse(bs[1]);

		bs = ArrayUtil.toBooleanArray(false, false);

		assertEquals(2, bs.length);
		assertFalse(bs[0]);
		assertFalse(bs[1]);
	}

	public void testToCharArray()
	{
		char[] cs;

		cs = ArrayUtil.toCharArray(Collections.<Character>emptyList());

		assertEquals(0, cs.length);

		cs = ArrayUtil.toCharArray(Collections.singletonList('a'));

		assertEquals(1, cs.length);
		assertEquals('a', cs[0]);

		cs = ArrayUtil.toCharArray(Collections.singletonList('b'));

		assertEquals(1, cs.length);
		assertEquals('b', cs[0]);

		cs = ArrayUtil.toCharArray(Arrays.asList('a', 'a'));

		assertEquals(2, cs.length);
		assertEquals('a', cs[0]);
		assertEquals('a', cs[1]);

		cs = ArrayUtil.toCharArray(Arrays.asList('b', 'a'));

		assertEquals(2, cs.length);
		assertEquals('b', cs[0]);
		assertEquals('a', cs[1]);

		cs = ArrayUtil.toCharArray(Arrays.asList('a', 'b'));

		assertEquals(2, cs.length);
		assertEquals('a', cs[0]);
		assertEquals('b', cs[1]);

		cs = ArrayUtil.toCharArray(Arrays.asList('b', 'b'));

		assertEquals(2, cs.length);
		assertEquals('b', cs[0]);
		assertEquals('b', cs[1]);

		cs = ArrayUtil.toCharArray();

		assertEquals(0, cs.length);

		cs = ArrayUtil.toCharArray('a');

		assertEquals(1, cs.length);
		assertEquals('a', cs[0]);

		cs = ArrayUtil.toCharArray('b');

		assertEquals(1, cs.length);
		assertEquals('b', cs[0]);

		cs = ArrayUtil.toCharArray('a', 'a');

		assertEquals(2, cs.length);
		assertEquals('a', cs[0]);
		assertEquals('a', cs[1]);

		cs = ArrayUtil.toCharArray('b', 'a');

		assertEquals(2, cs.length);
		assertEquals('b', cs[0]);
		assertEquals('a', cs[1]);

		cs = ArrayUtil.toCharArray('a', 'b');

		assertEquals(2, cs.length);
		assertEquals('a', cs[0]);
		assertEquals('b', cs[1]);

		cs = ArrayUtil.toCharArray('b', 'b');

		assertEquals(2, cs.length);
		assertEquals('b', cs[0]);
		assertEquals('b', cs[1]);
	}

	public void testToObjShortArray()
	{
		Short[] ss;

		ss = ArrayUtil.toObjShortArray(new short[0]);

		assertEquals(0, ss.length);

		ss = ArrayUtil.toObjShortArray(new short[] { 1 });

		assertEquals(1, ss.length);
		assertEquals(Short.valueOf((short) 1), ss[0]);

		ss = ArrayUtil.toObjShortArray(new short[] { -1 });

		assertEquals(1, ss.length);
		assertEquals(Short.valueOf((short) -1), ss[0]);

		ss = ArrayUtil.toObjShortArray(new short[] { 1, 1 });

		assertEquals(2, ss.length);
		assertEquals(Short.valueOf((short) 1), ss[0]);
		assertEquals(Short.valueOf((short) 1), ss[1]);

		ss = ArrayUtil.toObjShortArray(new short[] { -1, 1 });

		assertEquals(2, ss.length);
		assertEquals(Short.valueOf((short) -1), ss[0]);
		assertEquals(Short.valueOf((short) 1), ss[1]);

		ss = ArrayUtil.toObjShortArray(new short[] { 1, -1 });

		assertEquals(2, ss.length);
		assertEquals(Short.valueOf((short) 1), ss[0]);
		assertEquals(Short.valueOf((short) -1), ss[1]);

		ss = ArrayUtil.toObjShortArray(new short[] { -1, -1 });

		assertEquals(2, ss.length);
		assertEquals(Short.valueOf((short) -1), ss[0]);
		assertEquals(Short.valueOf((short) -1), ss[1]);
	}

	public void testToLongArray()
	{
		long[] ls;

		ls = ArrayUtil.toLongArray(Collections.<Long>emptyList());

		assertEquals(0, ls.length);

		ls = ArrayUtil.toLongArray(Collections.singletonList(1L));

		assertEquals(1, ls.length);
		assertEquals(1L, ls[0]);

		ls = ArrayUtil.toLongArray(Collections.singletonList(-1L));

		assertEquals(1, ls.length);
		assertEquals(-1L, ls[0]);

		ls = ArrayUtil.toLongArray(Arrays.asList(1L, 1L));

		assertEquals(2, ls.length);
		assertEquals(1L, ls[0]);
		assertEquals(1L, ls[1]);

		ls = ArrayUtil.toLongArray(Arrays.asList(-1L, 1L));

		assertEquals(2, ls.length);
		assertEquals(-1L, ls[0]);
		assertEquals(1L, ls[1]);

		ls = ArrayUtil.toLongArray(Arrays.asList(1L, -1L));

		assertEquals(2, ls.length);
		assertEquals(1L, ls[0]);
		assertEquals(-1L, ls[1]);

		ls = ArrayUtil.toLongArray(Arrays.asList(-1L, -1L));

		assertEquals(2, ls.length);
		assertEquals(-1L, ls[0]);
		assertEquals(-1L, ls[1]);

		ls = ArrayUtil.toLongArray();

		assertEquals(0, ls.length);

		ls = ArrayUtil.toLongArray(1L);

		assertEquals(1, ls.length);
		assertEquals(1L, ls[0]);

		ls = ArrayUtil.toLongArray(-1L);

		assertEquals(1, ls.length);
		assertEquals(-1L, ls[0]);

		ls = ArrayUtil.toLongArray(1L, 1L);

		assertEquals(2, ls.length);
		assertEquals(1L, ls[0]);
		assertEquals(1L, ls[1]);

		ls = ArrayUtil.toLongArray(-1L, 1L);

		assertEquals(2, ls.length);
		assertEquals(-1L, ls[0]);
		assertEquals(1L, ls[1]);

		ls = ArrayUtil.toLongArray(1L, -1L);

		assertEquals(2, ls.length);
		assertEquals(1L, ls[0]);
		assertEquals(-1L, ls[1]);

		ls = ArrayUtil.toLongArray(-1L, -1L);

		assertEquals(2, ls.length);
		assertEquals(-1L, ls[0]);
		assertEquals(-1L, ls[1]);
	}

	public void testSwap()
	{
		String[] strings1 = { "single" };
		ArrayUtil.swap(strings1, 0, 0);
		assertEquals("single", strings1[0]);

		String[] strings2 = { "my", "string" };
		ArrayUtil.swap(strings2, 0, 1);
		assertEquals("string", strings2[0]);
		assertEquals("my", strings2[1]);
		ArrayUtil.swap(strings2, 1, 0);
		assertEquals("my", strings2[0]);
		assertEquals("string", strings2[1]);

		String[] strings3 = { "long", "john", "silver" };
		ArrayUtil.swap(strings3, 0, 1);
		assertEquals("john", strings3[0]);
		assertEquals("long", strings3[1]);
		assertEquals("silver", strings3[2]);
		ArrayUtil.swap(strings3, 1, 2);
		assertEquals("john", strings3[0]);
		assertEquals("silver", strings3[1]);
		assertEquals("long", strings3[2]);
		ArrayUtil.swap(strings3, 2, 0);
		assertEquals("long", strings3[0]);
		assertEquals("silver", strings3[1]);
		assertEquals("john", strings3[2]);
		ArrayUtil.swap(strings3, 2, 1);
		assertEquals("long", strings3[0]);
		assertEquals("john", strings3[1]);
		assertEquals("silver", strings3[2]);

		String[] strings4 = { "my", "name", "is", "erica" };
		ArrayUtil.swap(strings4, 0, 1);
		assertEquals("name", strings4[0]);
		assertEquals("my", strings4[1]);
		assertEquals("is", strings4[2]);
		assertEquals("erica", strings4[3]);
		ArrayUtil.swap(strings4, 1, 2);
		assertEquals("name", strings4[0]);
		assertEquals("is", strings4[1]);
		assertEquals("my", strings4[2]);
		assertEquals("erica", strings4[3]);
		ArrayUtil.swap(strings4, 2, 3);
		assertEquals("name", strings4[0]);
		assertEquals("is", strings4[1]);
		assertEquals("erica", strings4[2]);
		assertEquals("my", strings4[3]);
		ArrayUtil.swap(strings4, 3, 0);
		assertEquals("my", strings4[0]);
		assertEquals("is", strings4[1]);
		assertEquals("erica", strings4[2]);
		assertEquals("name", strings4[3]);
		ArrayUtil.swap(strings4, 3, 1);
		assertEquals("my", strings4[0]);
		assertEquals("name", strings4[1]);
		assertEquals("erica", strings4[2]);
		assertEquals("is", strings4[3]);
		ArrayUtil.swap(strings4, 2, 0);
		assertEquals("erica", strings4[0]);
		assertEquals("name", strings4[1]);
		assertEquals("my", strings4[2]);
		assertEquals("is", strings4[3]);
		ArrayUtil.swap(strings4, 3, 2);
		assertEquals("erica", strings4[0]);
		assertEquals("name", strings4[1]);
		assertEquals("is", strings4[2]);
		assertEquals("my", strings4[3]);
		ArrayUtil.swap(strings4, 0, 3);
		assertEquals("my", strings4[0]);
		assertEquals("name", strings4[1]);
		assertEquals("is", strings4[2]);
		assertEquals("erica", strings4[3]);

		boolean[] bools1 = { true };
		ArrayUtil.swap(bools1, 0, 0);
		assertTrue(bools1[0]);

		boolean[] bools2 = { true, false };
		ArrayUtil.swap(bools2, 0, 1);
		assertFalse(bools2[0]);
		assertTrue(bools2[1]);
		ArrayUtil.swap(bools2, 1, 0);
		assertTrue(bools2[0]);
		assertFalse(bools2[1]);

		boolean[] bools3 = { false, true, false };
		ArrayUtil.swap(bools3, 0, 1);
		assertTrue(bools3[0]);
		assertFalse(bools3[1]);
		assertFalse(bools3[2]);
		ArrayUtil.swap(bools3, 1, 2);
		assertTrue(bools3[0]);
		assertFalse(bools3[1]);
		assertFalse(bools3[2]);
		ArrayUtil.swap(bools3, 2, 0);
		assertFalse(bools3[0]);
		assertFalse(bools3[1]);
		assertTrue(bools3[2]);
		ArrayUtil.swap(bools3, 2, 1);
		assertFalse(bools3[0]);
		assertTrue(bools3[1]);
		assertFalse(bools3[2]);

		boolean[] bools4 = { true, false, true, false };
		ArrayUtil.swap(bools4, 0, 1);
		assertFalse(bools4[0]);
		assertTrue(bools4[1]);
		assertTrue(bools4[2]);
		assertFalse(bools4[3]);
		ArrayUtil.swap(bools4, 1, 2);
		assertFalse(bools4[0]);
		assertTrue(bools4[1]);
		assertTrue(bools4[2]);
		assertFalse(bools4[3]);
		ArrayUtil.swap(bools4, 2, 3);
		assertFalse(bools4[0]);
		assertTrue(bools4[1]);
		assertFalse(bools4[2]);
		assertTrue(bools4[3]);
		ArrayUtil.swap(bools4, 3, 0);
		assertTrue(bools4[0]);
		assertTrue(bools4[1]);
		assertFalse(bools4[2]);
		assertFalse(bools4[3]);
		ArrayUtil.swap(bools4, 3, 1);
		assertTrue(bools4[0]);
		assertFalse(bools4[1]);
		assertFalse(bools4[2]);
		assertTrue(bools4[3]);
		ArrayUtil.swap(bools4, 2, 0);
		assertFalse(bools4[0]);
		assertFalse(bools4[1]);
		assertTrue(bools4[2]);
		assertTrue(bools4[3]);
		ArrayUtil.swap(bools4, 3, 2);
		assertFalse(bools4[0]);
		assertFalse(bools4[1]);
		assertTrue(bools4[2]);
		assertTrue(bools4[3]);
		ArrayUtil.swap(bools4, 0, 3);
		assertTrue(bools4[0]);
		assertFalse(bools4[1]);
		assertTrue(bools4[2]);
		assertFalse(bools4[3]);

		long[] longs1 = { 12L };
		ArrayUtil.swap(longs1, 0, 0);
		assertEquals(12L, longs1[0]);

		long[] longs2 = { 23L, -34L };
		ArrayUtil.swap(longs2, 0, 1);
		assertEquals(-34L, longs2[0]);
		assertEquals(23L, longs2[1]);
		ArrayUtil.swap(longs2, 1, 0);
		assertEquals(23L, longs2[0]);
		assertEquals(-34L, longs2[1]);

		long[] longs3 = { 45L, -54L, 67L };
		ArrayUtil.swap(longs3, 0, 1);
		assertEquals(-54L, longs3[0]);
		assertEquals(45L, longs3[1]);
		assertEquals(67L, longs3[2]);
		ArrayUtil.swap(longs3, 1, 2);
		assertEquals(-54L, longs3[0]);
		assertEquals(67L, longs3[1]);
		assertEquals(45L, longs3[2]);
		ArrayUtil.swap(longs3, 2, 0);
		assertEquals(45L, longs3[0]);
		assertEquals(67L, longs3[1]);
		assertEquals(-54L, longs3[2]);
		ArrayUtil.swap(longs3, 2, 1);
		assertEquals(45L, longs3[0]);
		assertEquals(-54L, longs3[1]);
		assertEquals(67L, longs3[2]);

		long[] longs4 = { 23L, -100L, Long.MAX_VALUE, 0L };
		ArrayUtil.swap(longs4, 0, 1);
		assertEquals(-100L, longs4[0]);
		assertEquals(23L, longs4[1]);
		assertEquals(Long.MAX_VALUE, longs4[2]);
		assertEquals(0L, longs4[3]);
		ArrayUtil.swap(longs4, 1, 2);
		assertEquals(-100L, longs4[0]);
		assertEquals(Long.MAX_VALUE, longs4[1]);
		assertEquals(23L, longs4[2]);
		assertEquals(0L, longs4[3]);
		ArrayUtil.swap(longs4, 2, 3);
		assertEquals(-100L, longs4[0]);
		assertEquals(Long.MAX_VALUE, longs4[1]);
		assertEquals(0L, longs4[2]);
		assertEquals(23L, longs4[3]);
		ArrayUtil.swap(longs4, 3, 0);
		assertEquals(23L, longs4[0]);
		assertEquals(Long.MAX_VALUE, longs4[1]);
		assertEquals(0L, longs4[2]);
		assertEquals(-100L, longs4[3]);
		ArrayUtil.swap(longs4, 3, 1);
		assertEquals(23L, longs4[0]);
		assertEquals(-100L, longs4[1]);
		assertEquals(0L, longs4[2]);
		assertEquals(Long.MAX_VALUE, longs4[3]);
		ArrayUtil.swap(longs4, 2, 0);
		assertEquals(0L, longs4[0]);
		assertEquals(-100L, longs4[1]);
		assertEquals(23L, longs4[2]);
		assertEquals(Long.MAX_VALUE, longs4[3]);
		ArrayUtil.swap(longs4, 3, 2);
		assertEquals(0L, longs4[0]);
		assertEquals(-100L, longs4[1]);
		assertEquals(Long.MAX_VALUE, longs4[2]);
		assertEquals(23L, longs4[3]);
		ArrayUtil.swap(longs4, 0, 3);
		assertEquals(23L, longs4[0]);
		assertEquals(-100L, longs4[1]);
		assertEquals(Long.MAX_VALUE, longs4[2]);
		assertEquals(0L, longs4[3]);

		short[] shorts1 = { (short) -1 };
		ArrayUtil.swap(shorts1, 0, 0);
		assertEquals((short) -1, shorts1[0]);

		short[] shorts2 = { (short) -100, (short) 100 };
		ArrayUtil.swap(shorts2, 0, 1);
		assertEquals((short) 100, shorts2[0]);
		assertEquals((short) -100, shorts2[1]);
		ArrayUtil.swap(shorts2, 1, 0);
		assertEquals((short) -100, shorts2[0]);
		assertEquals((short) 100, shorts2[1]);

		short[] shorts3 = { (short) 525, (short) 256, (short) -16365 };
		ArrayUtil.swap(shorts3, 0, 1);
		assertEquals((short) 256, shorts3[0]);
		assertEquals((short) 525, shorts3[1]);
		assertEquals((short) -16365, shorts3[2]);
		ArrayUtil.swap(shorts3, 1, 2);
		assertEquals((short) 256, shorts3[0]);
		assertEquals((short) -16365, shorts3[1]);
		assertEquals((short) 525, shorts3[2]);
		ArrayUtil.swap(shorts3, 2, 0);
		assertEquals((short) 525, shorts3[0]);
		assertEquals((short) -16365, shorts3[1]);
		assertEquals((short) 256, shorts3[2]);
		ArrayUtil.swap(shorts3, 2, 1);
		assertEquals((short) 525, shorts3[0]);
		assertEquals((short) 256, shorts3[1]);
		assertEquals((short) -16365, shorts3[2]);

		short[] shorts4 = { (short) -100, (short) -1, (short) 1, (short) 100 };
		ArrayUtil.swap(shorts4, 0, 1);
		assertEquals((short) -1, shorts4[0]);
		assertEquals((short) -100, shorts4[1]);
		assertEquals((short) 1, shorts4[2]);
		assertEquals((short) 100, shorts4[3]);
		ArrayUtil.swap(shorts4, 1, 2);
		assertEquals((short) -1, shorts4[0]);
		assertEquals((short) 1, shorts4[1]);
		assertEquals((short) -100, shorts4[2]);
		assertEquals((short) 100, shorts4[3]);
		ArrayUtil.swap(shorts4, 2, 3);
		assertEquals((short) -1, shorts4[0]);
		assertEquals((short) 1, shorts4[1]);
		assertEquals((short) 100, shorts4[2]);
		assertEquals((short) -100, shorts4[3]);
		ArrayUtil.swap(shorts4, 3, 0);
		assertEquals((short) -100, shorts4[0]);
		assertEquals((short) 1, shorts4[1]);
		assertEquals((short) 100, shorts4[2]);
		assertEquals((short) -1, shorts4[3]);
		ArrayUtil.swap(shorts4, 3, 1);
		assertEquals((short) -100, shorts4[0]);
		assertEquals((short) -1, shorts4[1]);
		assertEquals((short) 100, shorts4[2]);
		assertEquals((short) 1, shorts4[3]);
		ArrayUtil.swap(shorts4, 2, 0);
		assertEquals((short) 100, shorts4[0]);
		assertEquals((short) -1, shorts4[1]);
		assertEquals((short) -100, shorts4[2]);
		assertEquals((short) 1, shorts4[3]);
		ArrayUtil.swap(shorts4, 3, 2);
		assertEquals((short) 100, shorts4[0]);
		assertEquals((short) -1, shorts4[1]);
		assertEquals((short) 1, shorts4[2]);
		assertEquals((short) -100, shorts4[3]);
		ArrayUtil.swap(shorts4, 0, 3);
		assertEquals((short) -100, shorts4[0]);
		assertEquals((short) -1, shorts4[1]);
		assertEquals((short) 1, shorts4[2]);
		assertEquals((short) 100, shorts4[3]);

		byte[] bytes1 = { (byte) 0 };
		ArrayUtil.swap(bytes1, 0, 0);
		assertEquals((byte) 0, bytes1[0]);

		byte[] bytes2 = { (byte) 127, (byte) -128 };
		ArrayUtil.swap(bytes2, 0, 1);
		assertEquals((byte) -128, bytes2[0]);
		assertEquals((byte) 127, bytes2[1]);
		ArrayUtil.swap(bytes2, 1, 0);
		assertEquals((byte) 127, bytes2[0]);
		assertEquals((byte) -128, bytes2[1]);

		byte[] bytes3 = { (byte) 1, (byte) 10, (byte) 100 };
		ArrayUtil.swap(bytes3, 0, 1);
		assertEquals((byte) 10, bytes3[0]);
		assertEquals((byte) 1, bytes3[1]);
		assertEquals((byte) 100, bytes3[2]);
		ArrayUtil.swap(bytes3, 1, 2);
		assertEquals((byte) 10, bytes3[0]);
		assertEquals((byte) 100, bytes3[1]);
		assertEquals((byte) 1, bytes3[2]);
		ArrayUtil.swap(bytes3, 2, 0);
		assertEquals((byte) 1, bytes3[0]);
		assertEquals((byte) 100, bytes3[1]);
		assertEquals((byte) 10, bytes3[2]);
		ArrayUtil.swap(bytes3, 2, 1);
		assertEquals((byte) 1, bytes3[0]);
		assertEquals((byte) 10, bytes3[1]);
		assertEquals((byte) 100, bytes3[2]);

		byte[] bytes4 = { (byte) 127, (byte) 63, (byte) 31, (byte) 15 };
		ArrayUtil.swap(bytes4, 0, 1);
		assertEquals((byte) 63, bytes4[0]);
		assertEquals((byte) 127, bytes4[1]);
		assertEquals((byte) 31, bytes4[2]);
		assertEquals((byte) 15, bytes4[3]);
		ArrayUtil.swap(bytes4, 1, 2);
		assertEquals((byte) 63, bytes4[0]);
		assertEquals((byte) 31, bytes4[1]);
		assertEquals((byte) 127, bytes4[2]);
		assertEquals((byte) 15, bytes4[3]);
		ArrayUtil.swap(bytes4, 2, 3);
		assertEquals((byte) 63, bytes4[0]);
		assertEquals((byte) 31, bytes4[1]);
		assertEquals((byte) 15, bytes4[2]);
		assertEquals((byte) 127, bytes4[3]);
		ArrayUtil.swap(bytes4, 3, 0);
		assertEquals((byte) 127, bytes4[0]);
		assertEquals((byte) 31, bytes4[1]);
		assertEquals((byte) 15, bytes4[2]);
		assertEquals((byte) 63, bytes4[3]);
		ArrayUtil.swap(bytes4, 3, 1);
		assertEquals((byte) 127, bytes4[0]);
		assertEquals((byte) 63, bytes4[1]);
		assertEquals((byte) 15, bytes4[2]);
		assertEquals((byte) 31, bytes4[3]);
		ArrayUtil.swap(bytes4, 2, 0);
		assertEquals((byte) 15, bytes4[0]);
		assertEquals((byte) 63, bytes4[1]);
		assertEquals((byte) 127, bytes4[2]);
		assertEquals((byte) 31, bytes4[3]);
		ArrayUtil.swap(bytes4, 3, 2);
		assertEquals((byte) 15, bytes4[0]);
		assertEquals((byte) 63, bytes4[1]);
		assertEquals((byte) 31, bytes4[2]);
		assertEquals((byte) 127, bytes4[3]);
		ArrayUtil.swap(bytes4, 0, 3);
		assertEquals((byte) 127, bytes4[0]);
		assertEquals((byte) 63, bytes4[1]);
		assertEquals((byte) 31, bytes4[2]);
		assertEquals((byte) 15, bytes4[3]);

		char[] chars1 = { '0' };
		ArrayUtil.swap(chars1, 0, 0);
		assertEquals('0', chars1[0]);

		char[] chars2 = { 'a', 'A' };
		ArrayUtil.swap(chars2, 0, 1);
		assertEquals('A', chars2[0]);
		assertEquals('a', chars2[1]);
		ArrayUtil.swap(chars2, 1, 0);
		assertEquals('a', chars2[0]);
		assertEquals('A', chars2[1]);

		char[] chars3 = { 'x', 'y', 'z' };
		ArrayUtil.swap(chars3, 0, 1);
		assertEquals('y', chars3[0]);
		assertEquals('x', chars3[1]);
		assertEquals('z', chars3[2]);
		ArrayUtil.swap(chars3, 1, 2);
		assertEquals('y', chars3[0]);
		assertEquals('z', chars3[1]);
		assertEquals('x', chars3[2]);
		ArrayUtil.swap(chars3, 2, 0);
		assertEquals('x', chars3[0]);
		assertEquals('z', chars3[1]);
		assertEquals('y', chars3[2]);
		ArrayUtil.swap(chars3, 2, 1);
		assertEquals('x', chars3[0]);
		assertEquals('y', chars3[1]);
		assertEquals('z', chars3[2]);

		char[] chars4 = { 'a', 'b', 'c', 'd' };
		ArrayUtil.swap(chars4, 0, 1);
		assertEquals('b', chars4[0]);
		assertEquals('a', chars4[1]);
		assertEquals('c', chars4[2]);
		assertEquals('d', chars4[3]);
		ArrayUtil.swap(chars4, 1, 2);
		assertEquals('b', chars4[0]);
		assertEquals('c', chars4[1]);
		assertEquals('a', chars4[2]);
		assertEquals('d', chars4[3]);
		ArrayUtil.swap(chars4, 2, 3);
		assertEquals('b', chars4[0]);
		assertEquals('c', chars4[1]);
		assertEquals('d', chars4[2]);
		assertEquals('a', chars4[3]);
		ArrayUtil.swap(chars4, 3, 0);
		assertEquals('a', chars4[0]);
		assertEquals('c', chars4[1]);
		assertEquals('d', chars4[2]);
		assertEquals('b', chars4[3]);
		ArrayUtil.swap(chars4, 3, 1);
		assertEquals('a', chars4[0]);
		assertEquals('b', chars4[1]);
		assertEquals('d', chars4[2]);
		assertEquals('c', chars4[3]);
		ArrayUtil.swap(chars4, 2, 0);
		assertEquals('d', chars4[0]);
		assertEquals('b', chars4[1]);
		assertEquals('a', chars4[2]);
		assertEquals('c', chars4[3]);
		ArrayUtil.swap(chars4, 3, 2);
		assertEquals('d', chars4[0]);
		assertEquals('b', chars4[1]);
		assertEquals('c', chars4[2]);
		assertEquals('a', chars4[3]);
		ArrayUtil.swap(chars4, 0, 3);
		assertEquals('a', chars4[0]);
		assertEquals('b', chars4[1]);
		assertEquals('c', chars4[2]);
		assertEquals('d', chars4[3]);

		double[] doubles1 = { 0.0 };
		ArrayUtil.swap(doubles1, 0, 0);
		assertEquals(0.0, doubles1[0]);

		double[] doubles2 = { Math.PI, Math.E };
		ArrayUtil.swap(doubles2, 0, 1);
		assertEquals(Math.E, doubles2[0]);
		assertEquals(Math.PI, doubles2[1]);
		ArrayUtil.swap(doubles2, 1, 0);
		assertEquals(Math.PI, doubles2[0]);
		assertEquals(Math.E, doubles2[1]);

		double[] doubles3 = { -1D, 0D, 1D };
		ArrayUtil.swap(doubles3, 0, 1);
		assertEquals(0D, doubles3[0]);
		assertEquals(-1D, doubles3[1]);
		assertEquals(1D, doubles3[2]);
		ArrayUtil.swap(doubles3, 1, 2);
		assertEquals(0D, doubles3[0]);
		assertEquals(1D, doubles3[1]);
		assertEquals(-1D, doubles3[2]);
		ArrayUtil.swap(doubles3, 2, 0);
		assertEquals(-1D, doubles3[0]);
		assertEquals(1D, doubles3[1]);
		assertEquals(0D, doubles3[2]);
		ArrayUtil.swap(doubles3, 2, 1);
		assertEquals(-1D, doubles3[0]);
		assertEquals(0D, doubles3[1]);
		assertEquals(1D, doubles3[2]);

		double[] doubles4 = { Math.PI, 4.0, 0.0625, 1E99D };
		ArrayUtil.swap(doubles4, 0, 1);
		assertEquals(4.0, doubles4[0]);
		assertEquals(Math.PI, doubles4[1]);
		assertEquals(0.0625, doubles4[2]);
		assertEquals(1E99D, doubles4[3]);
		ArrayUtil.swap(doubles4, 1, 2);
		assertEquals(4.0, doubles4[0]);
		assertEquals(0.0625, doubles4[1]);
		assertEquals(Math.PI, doubles4[2]);
		assertEquals(1E99D, doubles4[3]);
		ArrayUtil.swap(doubles4, 2, 3);
		assertEquals(4.0, doubles4[0]);
		assertEquals(0.0625, doubles4[1]);
		assertEquals(1E99D, doubles4[2]);
		assertEquals(Math.PI, doubles4[3]);
		ArrayUtil.swap(doubles4, 3, 0);
		assertEquals(Math.PI, doubles4[0]);
		assertEquals(0.0625, doubles4[1]);
		assertEquals(1E99D, doubles4[2]);
		assertEquals(4.0, doubles4[3]);
		ArrayUtil.swap(doubles4, 3, 1);
		assertEquals(Math.PI, doubles4[0]);
		assertEquals(4.0, doubles4[1]);
		assertEquals(1E99D, doubles4[2]);
		assertEquals(0.0625, doubles4[3]);
		ArrayUtil.swap(doubles4, 2, 0);
		assertEquals(1E99D, doubles4[0]);
		assertEquals(4.0, doubles4[1]);
		assertEquals(Math.PI, doubles4[2]);
		assertEquals(0.0625, doubles4[3]);
		ArrayUtil.swap(doubles4, 3, 2);
		assertEquals(1E99D, doubles4[0]);
		assertEquals(4.0, doubles4[1]);
		assertEquals(0.0625, doubles4[2]);
		assertEquals(Math.PI, doubles4[3]);
		ArrayUtil.swap(doubles4, 0, 3);
		assertEquals(Math.PI, doubles4[0]);
		assertEquals(4.0, doubles4[1]);
		assertEquals(0.0625, doubles4[2]);
		assertEquals(1E99D, doubles4[3]);

		int[] ints1 = { 0 };
		ArrayUtil.swap(ints1, 0, 0);
		assertEquals(0, ints1[0]);

		int[] ints2 = { -1, 1 };
		ArrayUtil.swap(ints2, 0, 1);
		assertEquals(1, ints2[0]);
		assertEquals(-1, ints2[1]);
		ArrayUtil.swap(ints2, 1, 0);
		assertEquals(-1, ints2[0]);
		assertEquals(1, ints2[1]);

		int[] ints3 = { 1000, 1000000, 1000000000 };
		ArrayUtil.swap(ints3, 0, 1);
		assertEquals(1000000, ints3[0]);
		assertEquals(1000, ints3[1]);
		assertEquals(1000000000, ints3[2]);
		ArrayUtil.swap(ints3, 1, 2);
		assertEquals(1000000, ints3[0]);
		assertEquals(1000000000, ints3[1]);
		assertEquals(1000, ints3[2]);
		ArrayUtil.swap(ints3, 2, 0);
		assertEquals(1000, ints3[0]);
		assertEquals(1000000000, ints3[1]);
		assertEquals(1000000, ints3[2]);
		ArrayUtil.swap(ints3, 2, 1);
		assertEquals(1000, ints3[0]);
		assertEquals(1000000, ints3[1]);
		assertEquals(1000000000, ints3[2]);

		int[] ints4 = { -1, -2, -3, -4 };
		ArrayUtil.swap(ints4, 0, 1);
		assertEquals(-2, ints4[0]);
		assertEquals(-1, ints4[1]);
		assertEquals(-3, ints4[2]);
		assertEquals(-4, ints4[3]);
		ArrayUtil.swap(ints4, 1, 2);
		assertEquals(-2, ints4[0]);
		assertEquals(-3, ints4[1]);
		assertEquals(-1, ints4[2]);
		assertEquals(-4, ints4[3]);
		ArrayUtil.swap(ints4, 2, 3);
		assertEquals(-2, ints4[0]);
		assertEquals(-3, ints4[1]);
		assertEquals(-4, ints4[2]);
		assertEquals(-1, ints4[3]);
		ArrayUtil.swap(ints4, 3, 0);
		assertEquals(-1, ints4[0]);
		assertEquals(-3, ints4[1]);
		assertEquals(-4, ints4[2]);
		assertEquals(-2, ints4[3]);
		ArrayUtil.swap(ints4, 3, 1);
		assertEquals(-1, ints4[0]);
		assertEquals(-2, ints4[1]);
		assertEquals(-4, ints4[2]);
		assertEquals(-3, ints4[3]);
		ArrayUtil.swap(ints4, 2, 0);
		assertEquals(-4, ints4[0]);
		assertEquals(-2, ints4[1]);
		assertEquals(-1, ints4[2]);
		assertEquals(-3, ints4[3]);
		ArrayUtil.swap(ints4, 3, 2);
		assertEquals(-4, ints4[0]);
		assertEquals(-2, ints4[1]);
		assertEquals(-3, ints4[2]);
		assertEquals(-1, ints4[3]);
		ArrayUtil.swap(ints4, 0, 3);
		assertEquals(-1, ints4[0]);
		assertEquals(-2, ints4[1]);
		assertEquals(-3, ints4[2]);
		assertEquals(-4, ints4[3]);

		float[] floats1 = { Float.MAX_VALUE };
		ArrayUtil.swap(floats1, 0, 0);
		assertEquals(Float.MAX_VALUE, floats1[0]);

		float[] floats2 = { Float.MIN_VALUE, Float.MAX_VALUE };
		ArrayUtil.swap(floats2, 0, 1);
		assertEquals(Float.MAX_VALUE, floats2[0]);
		assertEquals(Float.MIN_VALUE, floats2[1]);
		ArrayUtil.swap(floats2, 1, 0);
		assertEquals(Float.MIN_VALUE, floats2[0]);
		assertEquals(Float.MAX_VALUE, floats2[1]);

		float[] floats3 = { 1E10F, 1E5F, 1E1F };
		ArrayUtil.swap(floats3, 0, 1);
		assertEquals(1E5F, floats3[0]);
		assertEquals(1E10F, floats3[1]);
		assertEquals(1E1F, floats3[2]);
		ArrayUtil.swap(floats3, 1, 2);
		assertEquals(1E5F, floats3[0]);
		assertEquals(1E1F, floats3[1]);
		assertEquals(1E10F, floats3[2]);
		ArrayUtil.swap(floats3, 2, 0);
		assertEquals(1E10F, floats3[0]);
		assertEquals(1E1F, floats3[1]);
		assertEquals(1E5F, floats3[2]);
		ArrayUtil.swap(floats3, 2, 1);
		assertEquals(1E10F, floats3[0]);
		assertEquals(1E5F, floats3[1]);
		assertEquals(1E1F, floats3[2]);

		float[] floats4 = { Float.MIN_VALUE, 1F, -1F, Float.MAX_VALUE };
		ArrayUtil.swap(floats4, 0, 1);
		assertEquals(1F, floats4[0]);
		assertEquals(Float.MIN_VALUE, floats4[1]);
		assertEquals(-1F, floats4[2]);
		assertEquals(Float.MAX_VALUE, floats4[3]);
		ArrayUtil.swap(floats4, 1, 2);
		assertEquals(1F, floats4[0]);
		assertEquals(-1F, floats4[1]);
		assertEquals(Float.MIN_VALUE, floats4[2]);
		assertEquals(Float.MAX_VALUE, floats4[3]);
		ArrayUtil.swap(floats4, 2, 3);
		assertEquals(1F, floats4[0]);
		assertEquals(-1F, floats4[1]);
		assertEquals(Float.MAX_VALUE, floats4[2]);
		assertEquals(Float.MIN_VALUE, floats4[3]);
		ArrayUtil.swap(floats4, 3, 0);
		assertEquals(Float.MIN_VALUE, floats4[0]);
		assertEquals(-1F, floats4[1]);
		assertEquals(Float.MAX_VALUE, floats4[2]);
		assertEquals(1F, floats4[3]);
		ArrayUtil.swap(floats4, 3, 1);
		assertEquals(Float.MIN_VALUE, floats4[0]);
		assertEquals(1F, floats4[1]);
		assertEquals(Float.MAX_VALUE, floats4[2]);
		assertEquals(-1F, floats4[3]);
		ArrayUtil.swap(floats4, 2, 0);
		assertEquals(Float.MAX_VALUE, floats4[0]);
		assertEquals(1F, floats4[1]);
		assertEquals(Float.MIN_VALUE, floats4[2]);
		assertEquals(-1F, floats4[3]);
		ArrayUtil.swap(floats4, 3, 2);
		assertEquals(Float.MAX_VALUE, floats4[0]);
		assertEquals(1F, floats4[1]);
		assertEquals(-1F, floats4[2]);
		assertEquals(Float.MIN_VALUE, floats4[3]);
		ArrayUtil.swap(floats4, 0, 3);
		assertEquals(Float.MIN_VALUE, floats4[0]);
		assertEquals(1F, floats4[1]);
		assertEquals(-1F, floats4[2]);
		assertEquals(Float.MAX_VALUE, floats4[3]);
	}

	public void testToObjLongArray()
	{
		Long[] ls;

		ls = ArrayUtil.toObjLongArray(new long[0]);

		assertEquals(0, ls.length);

		ls = ArrayUtil.toObjLongArray(new long[] { 1 });

		assertEquals(1, ls.length);
		assertEquals(Long.valueOf(1L), ls[0]);

		ls = ArrayUtil.toObjLongArray(new long[] { -1 });

		assertEquals(1, ls.length);
		assertEquals(Long.valueOf(-1L), ls[0]);

		ls = ArrayUtil.toObjLongArray(new long[] { 1, 1 });

		assertEquals(2, ls.length);
		assertEquals(Long.valueOf(1L), ls[0]);
		assertEquals(Long.valueOf(1L), ls[1]);

		ls = ArrayUtil.toObjLongArray(new long[] { -1, 1 });

		assertEquals(2, ls.length);
		assertEquals(Long.valueOf(-1L), ls[0]);
		assertEquals(Long.valueOf(1L), ls[1]);

		ls = ArrayUtil.toObjLongArray(new long[] { 1, -1 });

		assertEquals(2, ls.length);
		assertEquals(Long.valueOf(1L), ls[0]);
		assertEquals(Long.valueOf(-1L), ls[1]);

		ls = ArrayUtil.toObjLongArray(new long[] { -1, -1 });

		assertEquals(2, ls.length);
		assertEquals(Long.valueOf(-1L), ls[0]);
		assertEquals(Long.valueOf(-1L), ls[1]);
	}

	public void testShuffleArray()
	{
		Random rng = new Random(1846005705852173447L);

		byte[] byteArr = new byte[1024];
		Set<Byte> byteSet1 = new HashSet<>();
		Set<Byte> byteSet2 = new HashSet<>();
		rng.nextBytes(byteArr);
		for(byte b : byteArr)
			byteSet1.add(b);

		assertSame(byteArr, ArrayUtil.shuffleArray(byteArr));

		for(byte b : byteArr)
			byteSet2.add(b);

		assertEquals(byteSet1, byteSet2);

		int bound = 1;
		for(int n = 0; n < 6; n++)
		{
			int[] intArr = new int[rng.nextInt(bound) + 1];
			Set<Integer> intSet1 = new HashSet<>();
			Set<Integer> intSet2 = new HashSet<>();

			for(int i = 0; i < intArr.length; i++)
			{
				int r = rng.nextInt();
				intArr[i] = r;
				intSet1.add(r);
			}

			assertSame(intArr, ArrayUtil.shuffleArray(intArr, rng));

			for (int j : intArr)
			{
				intSet2.add(j);
			}

			assertEquals(intSet1, intSet2);

			float[] floatArr = new float[rng.nextInt(bound) + 1];
			Set<Float> floatSet1 = new HashSet<>();
			Set<Float> floatSet2 = new HashSet<>();

			for(int i = 0; i < floatArr.length; i++)
			{
				float r = rng.nextFloat();
				floatArr[i] = r;
				floatSet1.add(r);
			}

			assertSame(floatArr, ArrayUtil.shuffleArray(floatArr, rng));

			for (float value : floatArr)
			{
				floatSet2.add(value);
			}

			assertEquals(floatSet1, floatSet2);

			double[] doubleArr = new double[rng.nextInt(bound) + 1];
			Set<Double> doubleSet1 = new HashSet<>();
			Set<Double> doubleSet2 = new HashSet<>();

			for(int i = 0; i < doubleArr.length; i++)
			{
				double r = rng.nextDouble();
				doubleArr[i] = r;
				doubleSet1.add(r);
			}

			assertSame(doubleArr, ArrayUtil.shuffleArray(doubleArr, rng));

			for (double v : doubleArr)
			{
				doubleSet2.add(v);
			}

			assertEquals(doubleSet1, doubleSet2);

			long[] longArr = new long[rng.nextInt(bound) + 1];
			Set<Long> longSet1 = new HashSet<>();
			Set<Long> longSet2 = new HashSet<>();

			for(int i = 0; i < longArr.length; i++)
			{
				long r = rng.nextLong();
				longArr[i] = r;
				longSet1.add(r);
			}

			assertSame(longArr, ArrayUtil.shuffleArray(longArr, rng));

			for (long l : longArr)
			{
				longSet2.add(l);
			}

			assertEquals(longSet1, longSet2);

			String[] strArr = new String[rng.nextInt(bound) + 1];
			Set<String> strSet1 = new HashSet<>();
			Set<String> strSet2 = new HashSet<>();

			for(int i = 0; i < strArr.length; i++)
			{
				char c = (char) (rng.nextInt(Character.MAX_RADIX - Character.MIN_RADIX) + Character.MIN_RADIX);
				String r = String.valueOf(c);
				r += r + r;
				strArr[i] = r;
				strSet1.add(r);
			}

			assertSame(strArr, ArrayUtil.shuffleArray(strArr, rng));

			Collections.addAll(strSet2, strArr);

			assertEquals(strSet1, strSet2);

			bound *= 10;
		}
	}

	public void testToObjByteArray()
	{
		Byte[] bs;

		bs = ArrayUtil.toObjByteArray(new byte[0]);

		assertEquals(0, bs.length);

		bs = ArrayUtil.toObjByteArray(new byte[] { 1 });

		assertEquals(1, bs.length);
		assertEquals(Byte.valueOf((byte) 1), bs[0]);

		bs = ArrayUtil.toObjByteArray(new byte[] { -1 });

		assertEquals(1, bs.length);
		assertEquals(Byte.valueOf((byte) -1), bs[0]);

		bs = ArrayUtil.toObjByteArray(new byte[] { 1, 1 });

		assertEquals(2, bs.length);
		assertEquals(Byte.valueOf((byte) 1), bs[0]);
		assertEquals(Byte.valueOf((byte) 1), bs[1]);

		bs = ArrayUtil.toObjByteArray(new byte[] { -1, 1 });

		assertEquals(2, bs.length);
		assertEquals(Byte.valueOf((byte) -1), bs[0]);
		assertEquals(Byte.valueOf((byte) 1), bs[1]);

		bs = ArrayUtil.toObjByteArray(new byte[] { 1, -1 });

		assertEquals(2, bs.length);
		assertEquals(Byte.valueOf((byte) 1), bs[0]);
		assertEquals(Byte.valueOf((byte) -1), bs[1]);

		bs = ArrayUtil.toObjByteArray(new byte[] { -1, -1 });

		assertEquals(2, bs.length);
		assertEquals(Byte.valueOf((byte) -1), bs[0]);
		assertEquals(Byte.valueOf((byte) -1), bs[1]);
	}

	public void testToFloatArray()
	{
		float[] fs;

		fs = ArrayUtil.toFloatArray(Collections.<Float>emptyList());

		assertEquals(0, fs.length);

		fs = ArrayUtil.toFloatArray(Collections.singletonList(1F));

		assertEquals(1, fs.length);
		assertEquals(1F, fs[0]);

		fs = ArrayUtil.toFloatArray(Collections.singletonList(-1F));

		assertEquals(1, fs.length);
		assertEquals(-1F, fs[0]);

		fs = ArrayUtil.toFloatArray(Arrays.asList(1F, 1F));

		assertEquals(2, fs.length);
		assertEquals(1F, fs[0]);
		assertEquals(1F, fs[1]);

		fs = ArrayUtil.toFloatArray(Arrays.asList(-1F, 1F));

		assertEquals(2, fs.length);
		assertEquals(-1F, fs[0]);
		assertEquals(1F, fs[1]);

		fs = ArrayUtil.toFloatArray(Arrays.asList(1F, -1F));

		assertEquals(2, fs.length);
		assertEquals(1F, fs[0]);
		assertEquals(-1F, fs[1]);

		fs = ArrayUtil.toFloatArray(Arrays.asList(-1F, -1F));

		assertEquals(2, fs.length);
		assertEquals(-1F, fs[0]);
		assertEquals(-1F, fs[1]);

		fs = ArrayUtil.toFloatArray();

		assertEquals(0, fs.length);

		fs = ArrayUtil.toFloatArray(1F);

		assertEquals(1, fs.length);
		assertEquals(1F, fs[0]);

		fs = ArrayUtil.toFloatArray(-1F);

		assertEquals(1, fs.length);
		assertEquals(-1F, fs[0]);

		fs = ArrayUtil.toFloatArray(1F, 1F);

		assertEquals(2, fs.length);
		assertEquals(1F, fs[0]);
		assertEquals(1F, fs[1]);

		fs = ArrayUtil.toFloatArray(-1F, 1F);

		assertEquals(2, fs.length);
		assertEquals(-1F, fs[0]);
		assertEquals(1F, fs[1]);

		fs = ArrayUtil.toFloatArray(1F, -1F);

		assertEquals(2, fs.length);
		assertEquals(1F, fs[0]);
		assertEquals(-1F, fs[1]);

		fs = ArrayUtil.toFloatArray(-1F, -1F);

		assertEquals(2, fs.length);
		assertEquals(-1F, fs[0]);
		assertEquals(-1F, fs[1]);
	}

	public void testToShortArray()
	{
		short[] ss;

		ss = ArrayUtil.toShortArray(Collections.<Short>emptyList());

		assertEquals(0, ss.length);

		ss = ArrayUtil.toShortArray(Collections.singletonList((short) 1));

		assertEquals(1, ss.length);
		assertEquals((short) 1, ss[0]);

		ss = ArrayUtil.toShortArray(Collections.singletonList((short) -1));

		assertEquals(1, ss.length);
		assertEquals((short) -1, ss[0]);

		ss = ArrayUtil.toShortArray(Arrays.asList((short) 1, (short) 1));

		assertEquals(2, ss.length);
		assertEquals((short) 1, ss[0]);
		assertEquals((short) 1, ss[1]);

		ss = ArrayUtil.toShortArray(Arrays.asList((short) -1, (short) 1));

		assertEquals(2, ss.length);
		assertEquals((short) -1, ss[0]);
		assertEquals((short) 1, ss[1]);

		ss = ArrayUtil.toShortArray(Arrays.asList((short) 1, (short) -1));

		assertEquals(2, ss.length);
		assertEquals((short) 1, ss[0]);
		assertEquals((short) -1, ss[1]);

		ss = ArrayUtil.toShortArray(Arrays.asList((short) -1, (short) -1));

		assertEquals(2, ss.length);
		assertEquals((short) -1, ss[0]);
		assertEquals((short) -1, ss[1]);

		ss = ArrayUtil.toShortArray();

		assertEquals(0, ss.length);

		ss = ArrayUtil.toShortArray((short) 1);

		assertEquals(1, ss.length);
		assertEquals((short) 1, ss[0]);

		ss = ArrayUtil.toShortArray((short) -1);

		assertEquals(1, ss.length);
		assertEquals((short) -1, ss[0]);

		ss = ArrayUtil.toShortArray((short) 1, (short) 1);

		assertEquals(2, ss.length);
		assertEquals((short) 1, ss[0]);
		assertEquals((short) 1, ss[1]);

		ss = ArrayUtil.toShortArray((short) -1, (short) 1);

		assertEquals(2, ss.length);
		assertEquals((short) -1, ss[0]);
		assertEquals((short) 1, ss[1]);

		ss = ArrayUtil.toShortArray((short) 1, (short) -1);

		assertEquals(2, ss.length);
		assertEquals((short) 1, ss[0]);
		assertEquals((short) -1, ss[1]);

		ss = ArrayUtil.toShortArray((short) -1, (short) -1);

		assertEquals(2, ss.length);
		assertEquals((short) -1, ss[0]);
		assertEquals((short) -1, ss[1]);
	}

	public void testImmutableArrayOf()
	{
		ImmutableArray<String> strArr1 = ArrayUtil.immutableArrayOf(new String[0]);
		assertNotNull(strArr1);
		assertEquals(0, strArr1.length);

		ImmutableArray<String> strArr2 = ArrayUtil.immutableArrayOf(new String[1]);
		assertNotNull(strArr2);
		assertEquals(1, strArr2.length);

		ImmutableArray<Boolean> boolArr1 = ArrayUtil.immutableArrayOf(new boolean[0]);
		assertNotNull(boolArr1);
		assertEquals(0, boolArr1.length);

		ImmutableArray<Boolean> boolArr2 = ArrayUtil.immutableArrayOf(new boolean[1]);
		assertNotNull(boolArr2);
		assertEquals(1, boolArr2.length);

		ImmutableArray<Long> longArr1 = ArrayUtil.immutableArrayOf(new long[0]);
		assertNotNull(longArr1);
		assertEquals(0, longArr1.length);

		ImmutableArray<Long> longArr2 = ArrayUtil.immutableArrayOf(new long[1]);
		assertNotNull(longArr2);
		assertEquals(1, longArr2.length);

		ImmutableArray<Short> shortArr1 = ArrayUtil.immutableArrayOf(new short[0]);
		assertNotNull(shortArr1);
		assertEquals(0, shortArr1.length);

		ImmutableArray<Short> shortArr2 = ArrayUtil.immutableArrayOf(new short[1]);
		assertNotNull(shortArr2);
		assertEquals(1, shortArr2.length);

		ImmutableArray<Byte> byteArr1 = ArrayUtil.immutableArrayOf(new byte[0]);
		assertNotNull(byteArr1);
		assertEquals(0, byteArr1.length);

		ImmutableArray<Byte> byteArr2 = ArrayUtil.immutableArrayOf(new byte[1]);
		assertNotNull(byteArr2);
		assertEquals(1, byteArr2.length);

		ImmutableArray<Character> charArr1 = ArrayUtil.immutableArrayOf(new char[0]);
		assertNotNull(charArr1);
		assertEquals(0, charArr1.length);

		ImmutableArray<Character> charArr2 = ArrayUtil.immutableArrayOf(new char[1]);
		assertNotNull(charArr2);
		assertEquals(1, charArr2.length);

		ImmutableArray<Double> doubleArr1 = ArrayUtil.immutableArrayOf(new double[0]);
		assertNotNull(doubleArr1);
		assertEquals(0, doubleArr1.length);

		ImmutableArray<Double> doubleArr2 = ArrayUtil.immutableArrayOf(new double[1]);
		assertNotNull(doubleArr2);
		assertEquals(1, doubleArr2.length);

		ImmutableArray<Integer> intArr1 = ArrayUtil.immutableArrayOf(new int[0]);
		assertNotNull(intArr1);
		assertEquals(0, intArr1.length);

		ImmutableArray<Integer> intArr2 = ArrayUtil.immutableArrayOf(new int[1]);
		assertNotNull(intArr2);
		assertEquals(1, intArr2.length);

		ImmutableArray<Float> floatArr1 = ArrayUtil.immutableArrayOf(new float[0]);
		assertNotNull(floatArr1);
		assertEquals(0, floatArr1.length);

		ImmutableArray<Float> floatArr2 = ArrayUtil.immutableArrayOf(new float[1]);
		assertNotNull(floatArr2);
		assertEquals(1, floatArr2.length);
	}

	public void testToObjDoubleArray()
	{
		Double[] ds;

		ds = ArrayUtil.toObjDoubleArray(new double[0]);

		assertEquals(0, ds.length);

		ds = ArrayUtil.toObjDoubleArray(new double[] { 1 });

		assertEquals(1, ds.length);
		assertEquals(1D, ds[0]);

		ds = ArrayUtil.toObjDoubleArray(new double[] { -1 });

		assertEquals(1, ds.length);
		assertEquals(-1D, ds[0]);

		ds = ArrayUtil.toObjDoubleArray(new double[] { 1, 1 });

		assertEquals(2, ds.length);
		assertEquals(1D, ds[0]);
		assertEquals(1D, ds[1]);

		ds = ArrayUtil.toObjDoubleArray(new double[] { -1, 1 });

		assertEquals(2, ds.length);
		assertEquals(-1D, ds[0]);
		assertEquals(1D, ds[1]);

		ds = ArrayUtil.toObjDoubleArray(new double[] { 1, -1 });

		assertEquals(2, ds.length);
		assertEquals(1D, ds[0]);
		assertEquals(-1D, ds[1]);

		ds = ArrayUtil.toObjDoubleArray(new double[] { -1, -1 });

		assertEquals(2, ds.length);
		assertEquals(-1D, ds[0]);
		assertEquals(-1D, ds[1]);
	}

	public void testToObjCharacterArray()
	{
		Character[] cs;

		cs = ArrayUtil.toObjCharacterArray(new char[0]);

		assertEquals(0, cs.length);

		cs = ArrayUtil.toObjCharacterArray(new char[] { 'X' });

		assertEquals(1, cs.length);
		assertEquals(new Character('X'), cs[0]);

		cs = ArrayUtil.toObjCharacterArray(new char[] { 'Y' });

		assertEquals(1, cs.length);
		assertEquals(new Character('Y'), cs[0]);

		cs = ArrayUtil.toObjCharacterArray(new char[] { 'X', 'X' });

		assertEquals(2, cs.length);
		assertEquals(new Character('X'), cs[0]);
		assertEquals(new Character('X'), cs[1]);

		cs = ArrayUtil.toObjCharacterArray(new char[] { 'Y', 'X' });

		assertEquals(2, cs.length);
		assertEquals(new Character('Y'), cs[0]);
		assertEquals(new Character('X'), cs[1]);

		cs = ArrayUtil.toObjCharacterArray(new char[] { 'X', 'Y' });

		assertEquals(2, cs.length);
		assertEquals(new Character('X'), cs[0]);
		assertEquals(new Character('Y'), cs[1]);

		cs = ArrayUtil.toObjCharacterArray(new char[] { 'Y', 'Y' });

		assertEquals(2, cs.length);
		assertEquals(new Character('Y'), cs[0]);
		assertEquals(new Character('Y'), cs[1]);
	}

	public void testToDoubleArray()
	{
		double[] ds;

		ds = ArrayUtil.toDoubleArray(Collections.<Double>emptyList());

		assertEquals(0, ds.length);

		ds = ArrayUtil.toDoubleArray(Collections.singletonList(1D));

		assertEquals(1, ds.length);
		assertEquals(1D, ds[0]);

		ds = ArrayUtil.toDoubleArray(Collections.singletonList(-1D));

		assertEquals(1, ds.length);
		assertEquals(-1D, ds[0]);

		ds = ArrayUtil.toDoubleArray(Arrays.asList(1D, 1D));

		assertEquals(2, ds.length);
		assertEquals(1D, ds[0]);
		assertEquals(1D, ds[1]);

		ds = ArrayUtil.toDoubleArray(Arrays.asList(-1D, 1D));

		assertEquals(2, ds.length);
		assertEquals(-1D, ds[0]);
		assertEquals(1D, ds[1]);

		ds = ArrayUtil.toDoubleArray(Arrays.asList(1D, -1D));

		assertEquals(2, ds.length);
		assertEquals(1D, ds[0]);
		assertEquals(-1D, ds[1]);

		ds = ArrayUtil.toDoubleArray(Arrays.asList(-1D, -1D));

		assertEquals(2, ds.length);
		assertEquals(-1D, ds[0]);
		assertEquals(-1D, ds[1]);

		ds = ArrayUtil.toDoubleArray();

		assertEquals(0, ds.length);

		ds = ArrayUtil.toDoubleArray(1D);

		assertEquals(1, ds.length);
		assertEquals(1D, ds[0]);

		ds = ArrayUtil.toDoubleArray(-1D);

		assertEquals(1, ds.length);
		assertEquals(-1D, ds[0]);

		ds = ArrayUtil.toDoubleArray(1D, 1D);

		assertEquals(2, ds.length);
		assertEquals(1D, ds[0]);
		assertEquals(1D, ds[1]);

		ds = ArrayUtil.toDoubleArray(-1D, 1D);

		assertEquals(2, ds.length);
		assertEquals(-1D, ds[0]);
		assertEquals(1D, ds[1]);

		ds = ArrayUtil.toDoubleArray(1D, -1D);

		assertEquals(2, ds.length);
		assertEquals(1D, ds[0]);
		assertEquals(-1D, ds[1]);

		ds = ArrayUtil.toDoubleArray(-1D, -1D);

		assertEquals(2, ds.length);
		assertEquals(-1D, ds[0]);
		assertEquals(-1D, ds[1]);
	}

	public void testToByteArray()
	{
		byte[] bs;

		bs = ArrayUtil.toByteArray(Collections.<Byte>emptyList());

		assertEquals(0, bs.length);

		bs = ArrayUtil.toByteArray(Collections.singletonList((byte) 1));

		assertEquals(1, bs.length);
		assertEquals((byte) 1, bs[0]);

		bs = ArrayUtil.toByteArray(Collections.singletonList((byte) -1));

		assertEquals(1, bs.length);
		assertEquals((byte) -1, bs[0]);

		bs = ArrayUtil.toByteArray(Arrays.asList((byte) 1, (byte) 1));

		assertEquals(2, bs.length);
		assertEquals((byte) 1, bs[0]);
		assertEquals((byte) 1, bs[1]);

		bs = ArrayUtil.toByteArray(Arrays.asList((byte) -1, (byte) 1));

		assertEquals(2, bs.length);
		assertEquals((byte) -1, bs[0]);
		assertEquals((byte) 1, bs[1]);

		bs = ArrayUtil.toByteArray(Arrays.asList((byte) 1, (byte) -1));

		assertEquals(2, bs.length);
		assertEquals((byte) 1, bs[0]);
		assertEquals((byte) -1, bs[1]);

		bs = ArrayUtil.toByteArray(Arrays.asList((byte) -1, (byte) -1));

		assertEquals(2, bs.length);
		assertEquals((byte) -1, bs[0]);
		assertEquals((byte) -1, bs[1]);

		bs = ArrayUtil.toByteArray();

		assertEquals(0, bs.length);

		bs = ArrayUtil.toByteArray((byte) 1);

		assertEquals(1, bs.length);
		assertEquals((byte) 1, bs[0]);

		bs = ArrayUtil.toByteArray((byte) -1);

		assertEquals(1, bs.length);
		assertEquals((byte) -1, bs[0]);

		bs = ArrayUtil.toByteArray((byte) 1, (byte) 1);

		assertEquals(2, bs.length);
		assertEquals((byte) 1, bs[0]);
		assertEquals((byte) 1, bs[1]);

		bs = ArrayUtil.toByteArray((byte) -1, (byte) 1);

		assertEquals(2, bs.length);
		assertEquals((byte) -1, bs[0]);
		assertEquals((byte) 1, bs[1]);

		bs = ArrayUtil.toByteArray((byte) 1, (byte) -1);

		assertEquals(2, bs.length);
		assertEquals((byte) 1, bs[0]);
		assertEquals((byte) -1, bs[1]);

		bs = ArrayUtil.toByteArray((byte) -1, (byte) -1);

		assertEquals(2, bs.length);
		assertEquals((byte) -1, bs[0]);
		assertEquals((byte) -1, bs[1]);
	}

	public void testToIntArray()
	{
		int[] is;

		is = ArrayUtil.toIntArray(Collections.<Integer>emptyList());

		assertEquals(0, is.length);

		is = ArrayUtil.toIntArray(Collections.singletonList(1));

		assertEquals(1, is.length);
		assertEquals(1, is[0]);

		is = ArrayUtil.toIntArray(Collections.singletonList(-1));

		assertEquals(1, is.length);
		assertEquals(-1, is[0]);

		is = ArrayUtil.toIntArray(Arrays.asList(1, 1));

		assertEquals(2, is.length);
		assertEquals(1, is[0]);
		assertEquals(1, is[1]);

		is = ArrayUtil.toIntArray(Arrays.asList(-1, 1));

		assertEquals(2, is.length);
		assertEquals(-1, is[0]);
		assertEquals(1, is[1]);

		is = ArrayUtil.toIntArray(Arrays.asList(1, -1));

		assertEquals(2, is.length);
		assertEquals(1, is[0]);
		assertEquals(-1, is[1]);

		is = ArrayUtil.toIntArray(Arrays.asList(-1, -1));

		assertEquals(2, is.length);
		assertEquals(-1, is[0]);
		assertEquals(-1, is[1]);

		is = ArrayUtil.toIntArray();

		assertEquals(0, is.length);

		is = ArrayUtil.toIntArray(1);

		assertEquals(1, is.length);
		assertEquals(1, is[0]);

		is = ArrayUtil.toIntArray(-1);

		assertEquals(1, is.length);
		assertEquals(-1, is[0]);

		is = ArrayUtil.toIntArray(1, 1);

		assertEquals(2, is.length);
		assertEquals(1, is[0]);
		assertEquals(1, is[1]);

		is = ArrayUtil.toIntArray(-1, 1);

		assertEquals(2, is.length);
		assertEquals(-1, is[0]);
		assertEquals(1, is[1]);

		is = ArrayUtil.toIntArray(1, -1);

		assertEquals(2, is.length);
		assertEquals(1, is[0]);
		assertEquals(-1, is[1]);

		is = ArrayUtil.toIntArray(-1, -1);

		assertEquals(2, is.length);
		assertEquals(-1, is[0]);
		assertEquals(-1, is[1]);
	}

	public void testToArray()
	{
		String[] ss;

		ss = ArrayUtil.toArray();

		assertEquals(0, ss.length);

		ss = ArrayUtil.toArray("come with me");

		assertEquals(1, ss.length);
		assertEquals("come with me", ss[0]);

		ss = ArrayUtil.toArray("abc123");

		assertEquals(1, ss.length);
		assertEquals("abc123", ss[0]);

		ss = ArrayUtil.toArray("come with me", "come with me");

		assertEquals(2, ss.length);
		assertEquals("come with me", ss[0]);
		assertEquals("come with me", ss[1]);

		ss = ArrayUtil.toArray("abc123", "come with me");

		assertEquals(2, ss.length);
		assertEquals("abc123", ss[0]);
		assertEquals("come with me", ss[1]);

		ss = ArrayUtil.toArray("come with me", "abc123");

		assertEquals(2, ss.length);
		assertEquals("come with me", ss[0]);
		assertEquals("abc123", ss[1]);

		ss = ArrayUtil.toArray("abc123", "abc123");

		assertEquals(2, ss.length);
		assertEquals("abc123", ss[0]);
		assertEquals("abc123", ss[1]);
	}

	public void testToObjBooleanArray()
	{
		Boolean[] cs;

		cs = ArrayUtil.toObjBooleanArray(new boolean[0]);

		assertEquals(0, cs.length);

		cs = ArrayUtil.toObjBooleanArray(new boolean[] { false });

		assertEquals(1, cs.length);
		assertEquals(Boolean.FALSE, cs[0]);

		cs = ArrayUtil.toObjBooleanArray(new boolean[] { true });

		assertEquals(1, cs.length);
		assertEquals(Boolean.TRUE, cs[0]);

		cs = ArrayUtil.toObjBooleanArray(new boolean[] { false, false });

		assertEquals(2, cs.length);
		assertEquals(Boolean.FALSE, cs[0]);
		assertEquals(Boolean.FALSE, cs[1]);

		cs = ArrayUtil.toObjBooleanArray(new boolean[] { true, false });

		assertEquals(2, cs.length);
		assertEquals(Boolean.TRUE, cs[0]);
		assertEquals(Boolean.FALSE, cs[1]);

		cs = ArrayUtil.toObjBooleanArray(new boolean[] { false, true });

		assertEquals(2, cs.length);
		assertEquals(Boolean.FALSE, cs[0]);
		assertEquals(Boolean.TRUE, cs[1]);

		cs = ArrayUtil.toObjBooleanArray(new boolean[] { true, true });

		assertEquals(2, cs.length);
		assertEquals(Boolean.TRUE, cs[0]);
		assertEquals(Boolean.TRUE, cs[1]);
	}

	public void testGrowArray()
	{
		String[] strCopy;
		String[] strArr1 = { };
		strCopy = ArrayUtil.growArray(strArr1);

		assertNotSame(strArr1, strCopy);
		assertEquals(strArr1.length + 1, strCopy.length);

		String[] strArr2 = { "ab", "cd", "ef" };
		strCopy = ArrayUtil.growArray(strArr2);

		assertNotSame(strArr2, strCopy);
		assertEquals(strArr2.length + 1, strCopy.length);
		for(int i = 0; i < strArr2.length; i++)
			assertEquals(strArr2[i], strCopy[i]);

		boolean[] boolCopy;
		boolean[] boolArr1 = { };
		boolCopy = ArrayUtil.growArray(boolArr1);

		assertNotSame(boolArr1, boolCopy);
		assertEquals(boolArr1.length + 1, boolCopy.length);

		boolean[] boolArr2 = { true, true, true };
		boolCopy = ArrayUtil.growArray(boolArr2);

		assertNotSame(boolArr2, boolCopy);
		assertEquals(boolArr2.length + 1, boolCopy.length);
		for(int i = 0; i < boolArr2.length; i++)
			assertEquals(boolArr2[i], boolCopy[i]);

		long[] longCopy;
		long[] longArr1 = { };
		longCopy = ArrayUtil.growArray(longArr1);

		assertNotSame(longArr1, longCopy);
		assertEquals(longArr1.length + 1, longCopy.length);

		long[] longArr2 = { -1L, 0L, 1L };
		longCopy = ArrayUtil.growArray(longArr2);

		assertNotSame(longArr2, longCopy);
		assertEquals(longArr2.length + 1, longCopy.length);
		for(int i = 0; i < longArr2.length; i++)
			assertEquals(longArr2[i], longCopy[i]);

		short[] shortCopy;
		short[] shortArr1 = { };
		shortCopy = ArrayUtil.growArray(shortArr1);

		assertNotSame(shortArr1, shortCopy);
		assertEquals(shortArr1.length + 1, shortCopy.length);

		short[] shortArr2 = { Short.MIN_VALUE, 0, Short.MAX_VALUE };
		shortCopy = ArrayUtil.growArray(shortArr2);

		assertNotSame(shortArr2, shortCopy);
		assertEquals(shortArr2.length + 1, shortCopy.length);
		for(int i = 0; i < shortArr2.length; i++)
			assertEquals(shortArr2[i], shortCopy[i]);

		byte[] byteCopy;
		byte[] byteArr1 = { };
		byteCopy = ArrayUtil.growArray(byteArr1);

		assertNotSame(byteArr1, byteCopy);
		assertEquals(byteArr1.length + 1, byteCopy.length);

		byte[] byteArr2 = { Byte.MIN_VALUE, 0, Byte.MAX_VALUE };
		byteCopy = ArrayUtil.growArray(byteArr2);

		assertNotSame(byteArr2, byteCopy);
		assertEquals(byteArr2.length + 1, byteCopy.length);
		for(int i = 0; i < byteArr2.length; i++)
			assertEquals(byteArr2[i], byteCopy[i]);

		char[] charCopy;
		char[] charArr1 = { };
		charCopy = ArrayUtil.growArray(charArr1);

		assertNotSame(charArr1, charCopy);
		assertEquals(charArr1.length + 1, charCopy.length);

		char[] charArr2 = { 'a', 'b', '1', '2' };
		charCopy = ArrayUtil.growArray(charArr2);

		assertNotSame(charArr2, charCopy);
		assertEquals(charArr2.length + 1, charCopy.length);
		for(int i = 0; i < charArr2.length; i++)
			assertEquals(charArr2[i], charCopy[i]);

		double[] doubleCopy;
		double[] doubleArr1 = { };
		doubleCopy = ArrayUtil.growArray(doubleArr1);

		assertNotSame(doubleArr1, doubleCopy);
		assertEquals(doubleArr1.length + 1, doubleCopy.length);

		double[] doubleArr2 = { Double.MIN_VALUE, 0, Double.MAX_VALUE };
		doubleCopy = ArrayUtil.growArray(doubleArr2);

		assertNotSame(doubleArr2, doubleCopy);
		assertEquals(doubleArr2.length + 1, doubleCopy.length);
		for(int i = 0; i < doubleArr2.length; i++)
			assertEquals(doubleArr2[i], doubleCopy[i]);

		int[] intCopy;
		int[] intArr1 = { };
		intCopy = ArrayUtil.growArray(intArr1);

		assertNotSame(intArr1, intCopy);
		assertEquals(intArr1.length + 1, intCopy.length);

		int[] intArr2 = { Integer.MIN_VALUE, 0, Integer.MAX_VALUE };
		intCopy = ArrayUtil.growArray(intArr2);

		assertNotSame(intArr2, intCopy);
		assertEquals(intArr2.length + 1, intCopy.length);
		for(int i = 0; i < intArr2.length; i++)
			assertEquals(intArr2[i], intCopy[i]);

		float[] floatCopy;
		float[] floatArr1 = { };
		floatCopy = ArrayUtil.growArray(floatArr1);

		assertNotSame(floatArr1, floatCopy);
		assertEquals(floatArr1.length + 1, floatCopy.length);

		float[] floatArr2 = { Float.MIN_VALUE, 0, Float.MAX_VALUE };
		floatCopy = ArrayUtil.growArray(floatArr2);

		assertNotSame(floatArr2, floatCopy);
		assertEquals(floatArr2.length + 1, floatCopy.length);
		for(int i = 0; i < floatArr2.length; i++)
			assertEquals(floatArr2[i], floatCopy[i]);
	}

//	public void testGrowArrayAt()
//	{
//		// TODO: ArrayUtil.growArrayAt(int[], int)
//
//		// TODO: ArrayUtil.growArrayAt(Object[], int)
//	}

	public void testToObjFloatArray()
	{
		Float[] cs;

		cs = ArrayUtil.toObjFloatArray(new float[0]);

		assertEquals(0, cs.length);

		cs = ArrayUtil.toObjFloatArray(new float[] { Float.MIN_VALUE });

		assertEquals(1, cs.length);
		assertEquals(Float.MIN_VALUE, cs[0]);

		cs = ArrayUtil.toObjFloatArray(new float[] { Float.MAX_VALUE });

		assertEquals(1, cs.length);
		assertEquals(Float.MAX_VALUE, cs[0]);

		cs = ArrayUtil.toObjFloatArray(new float[] { Float.MIN_VALUE, Float.MIN_VALUE });

		assertEquals(2, cs.length);
		assertEquals(Float.MIN_VALUE, cs[0]);
		assertEquals(Float.MIN_VALUE, cs[1]);

		cs = ArrayUtil.toObjFloatArray(new float[] { Float.MAX_VALUE, Float.MIN_VALUE });

		assertEquals(2, cs.length);
		assertEquals(Float.MAX_VALUE, cs[0]);
		assertEquals(Float.MIN_VALUE, cs[1]);

		cs = ArrayUtil.toObjFloatArray(new float[] { Float.MIN_VALUE, Float.MAX_VALUE });

		assertEquals(2, cs.length);
		assertEquals(Float.MIN_VALUE, cs[0]);
		assertEquals(Float.MAX_VALUE, cs[1]);

		cs = ArrayUtil.toObjFloatArray(new float[] { Float.MAX_VALUE, Float.MAX_VALUE });

		assertEquals(2, cs.length);
		assertEquals(Float.MAX_VALUE, cs[0]);
		assertEquals(Float.MAX_VALUE, cs[1]);
	}

	public void testToObjIntegerArray()
	{
		Integer[] cs;

		cs = ArrayUtil.toObjIntegerArray(new int[0]);

		assertEquals(0, cs.length);

		cs = ArrayUtil.toObjIntegerArray(new int[] { Integer.MIN_VALUE });

		assertEquals(1, cs.length);
		assertEquals(Integer.valueOf(Integer.MIN_VALUE), cs[0]);

		cs = ArrayUtil.toObjIntegerArray(new int[] { Integer.MAX_VALUE });

		assertEquals(1, cs.length);
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), cs[0]);

		cs = ArrayUtil.toObjIntegerArray(new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE });

		assertEquals(2, cs.length);
		assertEquals(Integer.valueOf(Integer.MIN_VALUE), cs[0]);
		assertEquals(Integer.valueOf(Integer.MIN_VALUE), cs[1]);

		cs = ArrayUtil.toObjIntegerArray(new int[] { Integer.MAX_VALUE, Integer.MIN_VALUE });

		assertEquals(2, cs.length);
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), cs[0]);
		assertEquals(Integer.valueOf(Integer.MIN_VALUE), cs[1]);

		cs = ArrayUtil.toObjIntegerArray(new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE });

		assertEquals(2, cs.length);
		assertEquals(Integer.valueOf(Integer.MIN_VALUE), cs[0]);
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), cs[1]);

		cs = ArrayUtil.toObjIntegerArray(new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE });

		assertEquals(2, cs.length);
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), cs[0]);
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), cs[1]);
	}
}