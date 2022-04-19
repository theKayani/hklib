package com.hk.math;

import java.lang.reflect.Array;
import java.util.Objects;

import junit.framework.TestCase;

@SuppressWarnings({"ResultOfMethodCallIgnored", "RedundantArrayCreation"})
public class PrimitiveUtilTest extends TestCase
{
	public void testLongToBytes()
	{
		byte[] bs;

		// PrimitiveUtil.longToBytes(long)

		bs = PrimitiveUtil.longToBytes(0x00000000_00000000L);
		assertEqualArrays(bs, new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });

		bs = PrimitiveUtil.longToBytes(0x00000000_00000001L);
		assertEqualArrays(bs, new byte[] { 1, 0, 0, 0, 0, 0, 0, 0 });

		bs = PrimitiveUtil.longToBytes(0x10000000_00000000L);
		assertEqualArrays(bs, new byte[] { 0, 0, 0, 0, 0, 0, 0, 16 });

		bs = PrimitiveUtil.longToBytes(0x01000000_00000000L);
		assertEqualArrays(bs, new byte[] { 0, 0, 0, 0, 0, 0, 0, 1 });

		bs = PrimitiveUtil.longToBytes(0x00000000_FFFFFFFFL);
		assertEqualArrays(bs, new byte[] { -1, -1, -1, -1, 0, 0, 0, 0 });

		bs = PrimitiveUtil.longToBytes(0x08070605_04030201L);
		assertEqualArrays(bs, new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 });

		bs = PrimitiveUtil.longToBytes(0xFF00FF0000FF00FFL);
		assertEqualArrays(bs, new byte[] { -1, 0, -1, 0, 0, -1, 0, -1 });

		// PrimitiveUtil.longToBytes(long, byte[], int)

		bs = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.longToBytes(0x0000000000000000L, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });

		bs = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 0 };
		PrimitiveUtil.longToBytes(0x0000000000000000L, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });

		bs = new byte[] { 127, 1, 2, 3, 4, 5, 6, 7, 8, -128 };
		PrimitiveUtil.longToBytes(0x0000000000000000L, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, 0, 0, 0, 0, 0, 0, -128 });

		bs = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.longToBytes(0xFFFFFFFFFFFFFFFFL, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, -1, -1, -1, -1, -1, -1, -1, -1, 0 });

		bs = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 0 };
		PrimitiveUtil.longToBytes(0xFFFFFFFFFFFFFFFFL, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, -1, -1, -1, -1, -1, -1, -1, -1, 0 });

		bs = new byte[] { 127, 1, 2, 3, 4, 5, 6, 7, 8, -128 };
		PrimitiveUtil.longToBytes(0xFFFFFFFFFFFFFFFFL, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, -1, -1, -1, -1, -1, -1, -1, -1, -128 });

		bs = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.longToBytes(0x00000000FFFFFFFFL, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, -1, -1, -1, -1, 0, 0, 0, 0, 0 });

		bs = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 0 };
		PrimitiveUtil.longToBytes(0x00000000FFFFFFFFL, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, -1, -1, -1, -1, 0, 0, 0, 0, 0 });

		bs = new byte[] { 127, 1, 2, 3, 4, 5, 6, 7, 8, -128 };
		PrimitiveUtil.longToBytes(0x00000000FFFFFFFFL, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, -1, -1, -1, -1, 0, 0, 0, 0, -128 });

		bs = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.longToBytes(0x08070605_04030201L, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 0 });

		bs = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 0 };
		PrimitiveUtil.longToBytes(0x08070605_04030201L, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 0 });

		bs = new byte[] { 127, 1, 2, 3, 4, 5, 6, 7, 8, -128 };
		PrimitiveUtil.longToBytes(0x08070605_04030201L, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 1, 2, 3, 4, 5, 6, 7, 8, -128 });

		bs = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.longToBytes(0xFF00FF0000FF00FFL, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, -1, 0, -1, 0, 0, -1, 0, -1, 0 });

		bs = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 0 };
		PrimitiveUtil.longToBytes(0xFF00FF0000FF00FFL, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, -1, 0, -1, 0, 0, -1, 0, -1, 0 });

		bs = new byte[] { 127, 1, 2, 3, 4, 5, 6, 7, 8, -128 };
		PrimitiveUtil.longToBytes(0xFF00FF0000FF00FFL, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, -1, 0, -1, 0, 0, -1, 0, -1, -128 });

		bs = new byte[] { 127, -1, -1, -1, -1, -1, -1, -1, -1, -128 };
		PrimitiveUtil.longToBytes(0x0000000000000000L, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, 0, 0, 0, 0, 0, 0, -128 });

		bs = new byte[] { 127, 0, 0, 0, 0, 0, 0, 0, 0, -128 };
		PrimitiveUtil.longToBytes(0x0000000000000000L, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, 0, 0, 0, 0, 0, 0, -128 });

		bs = new byte[] { 127, 10, 20, 30, 40, 50, 60, 70, 80, -128 };
		PrimitiveUtil.longToBytes(0x0000000000000000L, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, 0, 0, 0, 0, 0, 0, -128 });

		bs = new byte[] { 127, -1, -1, -1, -1, -1, -1, -1, -1, -128 };
		PrimitiveUtil.longToBytes(0x0000000000000000L, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, 0, 0, 0, 0, 0, 0, -128 });

		bs = new byte[] { 127, 0, 0, 0, 0, 0, 0, 0, 0, -128 };
		PrimitiveUtil.longToBytes(0x0000000000000000L, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, 0, 0, 0, 0, 0, 0, -128 });

		bs = new byte[] { 127, 10, 20, 30, 40, 50, 60, 70, 80, -128 };
		PrimitiveUtil.longToBytes(0xB00BB00BB000000BL, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 11, 0, 0, -80, 11, -80, 11, -80, -128 });

		try
		{
			PrimitiveUtil.longToBytes(0L, new byte[0], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.longToBytes(0L, new byte[7], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.longToBytes(0L, new byte[8], 1);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testBytesToLong()
	{
		byte[] bs;

		bs = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		assertEquals(PrimitiveUtil.bytesToLong(0, bs), 0x0000000000000000L);

		bs = new byte[] { -128, 0, 0, 0, 0, 0, 0, 0, 0, 127 };
		assertEquals(PrimitiveUtil.bytesToLong(1, bs), 0x0000000000000000L);

		bs = new byte[] { 1, 0, 0, 0, 0, 0, 0, 0 };
		assertEquals(PrimitiveUtil.bytesToLong(0, bs), 0x0000000000000001L);

		bs = new byte[] { -128, 1, 0, 0, 0, 0, 0, 0, 0, 127 };
		assertEquals(PrimitiveUtil.bytesToLong(1, bs), 0x0000000000000001L);

		bs = new byte[] { 1, 0, 0, 0, 0, 0, 0, 0 };
		assertEquals(PrimitiveUtil.bytesToLong(0, bs), 0x00000000_00000001L);

		bs = new byte[] { -128, 1, 0, 0, 0, 0, 0, 0, 0, 127 };
		assertEquals(PrimitiveUtil.bytesToLong(1, bs), 0x00000000_00000001L);

		bs = new byte[] { 0, 0, 0, 0, 0, 0, 0, 16 };
		assertEquals(PrimitiveUtil.bytesToLong(0, bs), 0x10000000_00000000L);

		bs = new byte[] { -128, 0, 0, 0, 0, 0, 0, 0, 16, 127 };
		assertEquals(PrimitiveUtil.bytesToLong(1, bs), 0x10000000_00000000L);

		bs = new byte[] { 0, 0, 0, 0, 0, 0, 0, 1 };
		assertEquals(PrimitiveUtil.bytesToLong(0, bs), 0x01000000_00000000L);

		bs = new byte[] { -128, 0, 0, 0, 0, 0, 0, 0, 1, 127 };
		assertEquals(PrimitiveUtil.bytesToLong(1, bs), 0x01000000_00000000L);

		bs = new byte[] { -1, -1, -1, -1, 0, 0, 0, 0 };
		assertEquals(PrimitiveUtil.bytesToLong(0, bs), 0x00000000_FFFFFFFFL);

		bs = new byte[] { -128, -1, -1, -1, -1, 0, 0, 0, 0, 127 };
		assertEquals(PrimitiveUtil.bytesToLong(1, bs), 0x00000000_FFFFFFFFL);

		bs = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		assertEquals(PrimitiveUtil.bytesToLong(0, bs), 0x08070605_04030201L);

		bs = new byte[] { -128, 1, 2, 3, 4, 5, 6, 7, 8, 127 };
		assertEquals(PrimitiveUtil.bytesToLong(1, bs), 0x08070605_04030201L);

		bs = new byte[] { -1, 0, -1, 0, 0, -1, 0, -1 };
		assertEquals(PrimitiveUtil.bytesToLong(0, bs), 0xFF00FF0000FF00FFL);

		bs = new byte[] { -128, -1, 0, -1, 0, 0, -1, 0, -1, 127 };
		assertEquals(PrimitiveUtil.bytesToLong(1, bs), 0xFF00FF0000FF00FFL);

		try
		{
			PrimitiveUtil.bytesToLong(0, new byte[0]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.bytesToLong(0, new byte[7]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.bytesToLong(1, new byte[8]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testIntToShorts()
	{
		short[] ss;

		// PrimitiveUtil.intToShorts(int)

		ss = PrimitiveUtil.intToShorts(0x00000000);
		assertEqualArrays(ss, new short[] { 0, 0 });

		ss = PrimitiveUtil.intToShorts(0x00000001);
		assertEqualArrays(ss, new short[] { 1, 0 });

		ss = PrimitiveUtil.intToShorts(0x10000000);
		assertEqualArrays(ss, new short[] { 0, 4096 });

		ss = PrimitiveUtil.intToShorts(0x00010000);
		assertEqualArrays(ss, new short[] { 0, 1 });

		ss = PrimitiveUtil.intToShorts(0x0000FFFF);
		assertEqualArrays(ss, new short[] { -1, 0 });

		ss = PrimitiveUtil.intToShorts(0x00020001);
		assertEqualArrays(ss, new short[] { 1, 2 });

		ss = PrimitiveUtil.intToShorts(0xF0F00F0F);
		assertEqualArrays(ss, new short[] { 3855, -3856 });

		// PrimitiveUtil.intToShorts(int, short[], int)

		ss = new short[] { 0, 0, 0, 0 };
		PrimitiveUtil.intToShorts(0x00000000, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 0, 0, 0 });

		ss = new short[] { 0, 1, 2, 0 };
		PrimitiveUtil.intToShorts(0x00000000, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 0, 0, 0 });

		ss = new short[] { 127, 1, 2, -128 };
		PrimitiveUtil.intToShorts(0x00000000, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, -128 });

		ss = new short[] { 0, 0, 0, 0 };
		PrimitiveUtil.intToShorts(0xFFFFFFFF, ss, 1);
		assertEqualArrays(ss, new short[] { 0, -1, -1, 0 });

		ss = new short[] { 0, 1, 2, 0 };
		PrimitiveUtil.intToShorts(0xFFFFFFFF, ss, 1);
		assertEqualArrays(ss, new short[] { 0, -1, -1, 0 });

		ss = new short[] { 127, 1, 2, -128 };
		PrimitiveUtil.intToShorts(0xFFFFFFFF, ss, 1);
		assertEqualArrays(ss, new short[] { 127, -1, -1, -128 });

		ss = new short[] { 0, 0, 0, 0 };
		PrimitiveUtil.intToShorts(0x0000FFFF, ss, 1);
		assertEqualArrays(ss, new short[] { 0, -1, 0, 0 });

		ss = new short[] { 0, 1, 2, 0 };
		PrimitiveUtil.intToShorts(0x0000FFFF, ss, 1);
		assertEqualArrays(ss, new short[] { 0, -1, 0, 0 });

		ss = new short[] { 127, 1, 2, -128 };
		PrimitiveUtil.intToShorts(0x0000FFFF, ss, 1);
		assertEqualArrays(ss, new short[] { 127, -1, 0, -128 });

		ss = new short[] { 0, 0, 0, 0 };
		PrimitiveUtil.intToShorts(0x00020001, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 1, 2, 0 });

		ss = new short[] { 0, 1, 2, 0 };
		PrimitiveUtil.intToShorts(0x00020001, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 1, 2, 0 });

		ss = new short[] { 127, 1, 2, -128 };
		PrimitiveUtil.intToShorts(0x00020001, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 1, 2, -128 });

		ss = new short[] { 0, 0, 0, 0 };
		PrimitiveUtil.intToShorts(0xF0F00F0F, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 3855, -3856, 0 });

		ss = new short[] { 0, 1, 2, 0 };
		PrimitiveUtil.intToShorts(0xF0F00F0F, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 3855, -3856, 0 });

		ss = new short[] { 127, 1, 2, -128 };
		PrimitiveUtil.intToShorts(0xF0F00F0F, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 3855, -3856, -128 });

		ss = new short[] { 127, -1, -1, -128 };
		PrimitiveUtil.intToShorts(0x00000000, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, -128 });

		ss = new short[] { 127, 0, 0, -128 };
		PrimitiveUtil.intToShorts(0x00000000, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, -128 });

		ss = new short[] { 127, 10, 20, -128 };
		PrimitiveUtil.intToShorts(0x00000000, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, -128 });

		ss = new short[] { 127, -1, -1, -128 };
		PrimitiveUtil.intToShorts(0x00000000, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, -128 });

		ss = new short[] { 127, 0, 0, -128 };
		PrimitiveUtil.intToShorts(0x00000000, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, -128 });

		ss = new short[] { 127, 10, 20, -128 };
		PrimitiveUtil.intToShorts(0xB00B000B, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 11, -20469, -128 });

		try
		{
			PrimitiveUtil.intToShorts(0, new short[0], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.intToShorts(0, new short[1], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.intToShorts(0, new short[2], 1);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testLongToShorts()
	{
		short[] ss;

		// PrimitiveUtil.longToShorts(long)

		ss = PrimitiveUtil.longToShorts(0x00000000_00000000L);
		assertEqualArrays(ss, new short[] { 0, 0, 0, 0 });

		ss = PrimitiveUtil.longToShorts(0x00000000_00000001L);
		assertEqualArrays(ss, new short[] { 1, 0, 0, 0 });

		ss = PrimitiveUtil.longToShorts(0x10000000_00000000L);
		assertEqualArrays(ss, new short[] { 0, 0, 0, 4096 });

		ss = PrimitiveUtil.longToShorts(0x00010000_00000000L);
		assertEqualArrays(ss, new short[] { 0, 0, 0, 1 });

		ss = PrimitiveUtil.longToShorts(0x00000000_FFFFFFFFL);
		assertEqualArrays(ss, new short[] { -1, -1, 0, 0 });

		ss = PrimitiveUtil.longToShorts(0x00040003_00020001L);
		assertEqualArrays(ss, new short[] { 1, 2, 3, 4 });

		ss = PrimitiveUtil.longToShorts(0xFF00FF0000FF00FFL);
		assertEqualArrays(ss, new short[] { 255, 255, -256, -256 });

		// PrimitiveUtil.longToShorts(long, short[], int)

		ss = new short[] { 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.longToShorts(0x0000000000000000L, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 0, 0, 0, 0, 0 });

		ss = new short[] { 0, 1, 2, 3, 4, 0 };
		PrimitiveUtil.longToShorts(0x0000000000000000L, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 0, 0, 0, 0, 0 });

		ss = new short[] { 127, 1, 2, 3, 4, -128 };
		PrimitiveUtil.longToShorts(0x0000000000000000L, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, 0, 0, -128 });

		ss = new short[] { 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.longToShorts(0xFFFFFFFFFFFFFFFFL, ss, 1);
		assertEqualArrays(ss, new short[] { 0, -1, -1, -1, -1, 0 });

		ss = new short[] { 0, 1, 2, 3, 4, 0 };
		PrimitiveUtil.longToShorts(0xFFFFFFFFFFFFFFFFL, ss, 1);
		assertEqualArrays(ss, new short[] { 0, -1, -1, -1, -1, 0 });

		ss = new short[] { 127, 1, 2, 3, 4, -128 };
		PrimitiveUtil.longToShorts(0xFFFFFFFFFFFFFFFFL, ss, 1);
		assertEqualArrays(ss, new short[] { 127, -1, -1, -1, -1, -128 });

		ss = new short[] { 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.longToShorts(0x00000000FFFFFFFFL, ss, 1);
		assertEqualArrays(ss, new short[] { 0, -1, -1, 0, 0, 0 });

		ss = new short[] { 0, 1, 2, 3, 4, 0 };
		PrimitiveUtil.longToShorts(0x00000000FFFFFFFFL, ss, 1);
		assertEqualArrays(ss, new short[] { 0, -1, -1, 0, 0, 0 });

		ss = new short[] { 127, 1, 2, 3, 4, -128 };
		PrimitiveUtil.longToShorts(0x00000000FFFFFFFFL, ss, 1);
		assertEqualArrays(ss, new short[] { 127, -1, -1, 0, 0, -128 });

		ss = new short[] { 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.longToShorts(0x0004000300020001L, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 1, 2, 3, 4, 0 });

		ss = new short[] { 0, 1, 2, 3, 4, 0 };
		PrimitiveUtil.longToShorts(0x0004000300020001L, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 1, 2, 3, 4, 0 });

		ss = new short[] { 127, 1, 2, 3, 4, -128 };
		PrimitiveUtil.longToShorts(0x0004000300020001L, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 1, 2, 3, 4, -128 });

		ss = new short[] { 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.longToShorts(0xFF00FF0000FF00FFL, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 255, 255, -256, -256, 0 });

		ss = new short[] { 0, 1, 2, 3, 4, 0 };
		PrimitiveUtil.longToShorts(0xFF00FF0000FF00FFL, ss, 1);
		assertEqualArrays(ss, new short[] { 0, 255, 255, -256, -256, 0 });

		ss = new short[] { 127, 1, 2, 3, 4, -128 };
		PrimitiveUtil.longToShorts(0xFF00FF0000FF00FFL, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 255, 255, -256, -256, -128 });

		ss = new short[] { 127, -1, -1, -1, -1, -128 };
		PrimitiveUtil.longToShorts(0x0000000000000000L, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, 0, 0, -128 });

		ss = new short[] { 127, 0, 0, 0, 0, -128 };
		PrimitiveUtil.longToShorts(0x0000000000000000L, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, 0, 0, -128 });

		ss = new short[] { 127, 10, 20, 30, 40, -128 };
		PrimitiveUtil.longToShorts(0x0000000000000000L, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, 0, 0, -128 });

		ss = new short[] { 127, -1, -1, -1, -1, -128 };
		PrimitiveUtil.longToShorts(0x0000000000000000L, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, 0, 0, -128 });

		ss = new short[] { 127, 0, 0, 0, 0, -128 };
		PrimitiveUtil.longToShorts(0x0000000000000000L, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 0, 0, 0, 0, -128 });

		ss = new short[] { 127, 10, 20, 30, 40, -128 };
		PrimitiveUtil.longToShorts(0xB00BB00BB000000BL, ss, 1);
		assertEqualArrays(ss, new short[] { 127, 11, -20480, -20469, -20469, -128 });

		try
		{
			PrimitiveUtil.longToShorts(0, new short[0], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.longToShorts(0, new short[3], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.longToShorts(0, new short[4], 1);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testIntsToLong()
	{
		int[] is;

		is = new int[] { 0, 0 };
		assertEquals(PrimitiveUtil.intsToLong(0, is), 0x0000000000000000L);

		is = new int[] { -128, 0, 0, 127 };
		assertEquals(PrimitiveUtil.intsToLong(1, is), 0x0000000000000000L);

		is = new int[] { 1, 0 };
		assertEquals(PrimitiveUtil.intsToLong(0, is), 0x0000000000000001L);

		is = new int[] { -128, 1, 0, 127 };
		assertEquals(PrimitiveUtil.intsToLong(1, is), 0x0000000000000001L);

//		is = new int[] { 1, 0 };
//		assertEquals(PrimitiveUtil.intsToLong(0, is), 0x0000000000000001L);
//
//		is = new int[] { -128, 1, 0, 127 };
//		assertEquals(PrimitiveUtil.intsToLong(1, is), 0x0000000000000001L);

		is = new int[] { 0, 268435456 };
		assertEquals(PrimitiveUtil.intsToLong(0, is), 0x10000000_00000000L);

		is = new int[] { -128, 0, 268435456, 127 };
		assertEquals(PrimitiveUtil.intsToLong(1, is), 0x10000000_00000000L);

		is = new int[] { 0, 1 };
		assertEquals(PrimitiveUtil.intsToLong(0, is), 0x00000001_00000000L);

		is = new int[] { -128, 0, 1, 127 };
		assertEquals(PrimitiveUtil.intsToLong(1, is), 0x00000001_00000000L);

		is = new int[] { -1, 0 };
		assertEquals(PrimitiveUtil.intsToLong(0, is), 0x00000000_FFFFFFFFL);

		is = new int[] { -128, -1, 0, 127 };
		assertEquals(PrimitiveUtil.intsToLong(1, is), 0x00000000_FFFFFFFFL);

		is = new int[] { 1, 2 };
		assertEquals(PrimitiveUtil.intsToLong(0, is), 0x00000002_00000001L);

		is = new int[] { -128, 1, 2, 127 };
		assertEquals(PrimitiveUtil.intsToLong(1, is), 0x00000002_00000001L);

		is = new int[] { 0, -1 };
		assertEquals(PrimitiveUtil.intsToLong(0, is), 0xFFFFFFFF_00000000L);

		is = new int[] { -128, 0, -1, 127 };
		assertEquals(PrimitiveUtil.intsToLong(1, is), 0xFFFFFFFF_00000000L);

		try
		{
			PrimitiveUtil.intsToLong(0, new int[0]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.intsToLong(0, new int[1]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.intsToLong(1, new int[2]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testBytesToShort()
	{
		byte[] bs;

		bs = new byte[] { 0, 0 };
		assertEquals(PrimitiveUtil.bytesToShort(0, bs), 0x0000);

		bs = new byte[] { -128, 0, 0, 127 };
		assertEquals(PrimitiveUtil.bytesToShort(1, bs), 0x0000);

		bs = new byte[] { 1, 0 };
		assertEquals(PrimitiveUtil.bytesToShort(0, bs), 0x0001);

		bs = new byte[] { -128, 1, 0, 127 };
		assertEquals(PrimitiveUtil.bytesToShort(1, bs), 0x0001);

//		bs = new byte[] { 1, 0 };
//		assertEquals(PrimitiveUtil.bytesToShort(0, bs), 0x0001);
//
//		bs = new byte[] { -128, 1, 0, 127 };
//		assertEquals(PrimitiveUtil.bytesToShort(1, bs), 0x0001);

		bs = new byte[] { 0, 16 };
		assertEquals(PrimitiveUtil.bytesToShort(0, bs), 0x1000);

		bs = new byte[] { -128, 0, 16, 127 };
		assertEquals(PrimitiveUtil.bytesToShort(1, bs), 0x1000);

		bs = new byte[] { 0, 1 };
		assertEquals(PrimitiveUtil.bytesToShort(0, bs), 0x0100);

		bs = new byte[] { -128, 0, 1, 127 };
		assertEquals(PrimitiveUtil.bytesToShort(1, bs), 0x0100);

		bs = new byte[] { -1, 0 };
		assertEquals(PrimitiveUtil.bytesToShort(0, bs), 0x00FF);

		bs = new byte[] { -128, -1, 0, 127 };
		assertEquals(PrimitiveUtil.bytesToShort(1, bs), 0x00FF);

		bs = new byte[] { 1, 2 };
		assertEquals(PrimitiveUtil.bytesToShort(0, bs), 0x0201);

		bs = new byte[] { -128, 1, 2, 127 };
		assertEquals(PrimitiveUtil.bytesToShort(1, bs), 0x0201);

		bs = new byte[] { 0, -1 };
		assertEquals(PrimitiveUtil.bytesToShort(0, bs), (short) 0xFF00);

		bs = new byte[] { -128, 0, -1, 127 };
		assertEquals(PrimitiveUtil.bytesToShort(1, bs), (short) 0xFF00);

		try
		{
			PrimitiveUtil.bytesToShort(0, new byte[0]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.bytesToShort(0, new byte[1]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.bytesToShort(1, new byte[2]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testLongToInts()
	{
		int[] is;

		// PrimitiveUtil.longToInts(long)

		is = PrimitiveUtil.longToInts(0x00000000_00000000L);
		assertEqualArrays(is, new int[] { 0, 0 });

		is = PrimitiveUtil.longToInts(0x00000000_00000001L);
		assertEqualArrays(is, new int[] { 1, 0 });

		is = PrimitiveUtil.longToInts(0x10000000_00000000L);
		assertEqualArrays(is, new int[] { 0, 268435456 });

		is = PrimitiveUtil.longToInts(0x00000001_00000000L);
		assertEqualArrays(is, new int[] { 0, 1 });

		is = PrimitiveUtil.longToInts(0x00000000_FFFFFFFFL);
		assertEqualArrays(is, new int[] { -1, 0 });

		is = PrimitiveUtil.longToInts(0x0000000200000001L);
		assertEqualArrays(is, new int[] { 1, 2 });

		is = PrimitiveUtil.longToInts(0xF0F0F0F00F0F0F0FL);
		assertEqualArrays(is, new int[] { 252645135, -252645136 });

		// PrimitiveUtil.longToInts(long, int[], int)

		is = new int[] { 0, 0, 0, 0 };
		PrimitiveUtil.longToInts(0x00000000_00000000L, is, 1);
		assertEqualArrays(is, new int[] { 0, 0, 0, 0 });

		is = new int[] { 0, 1, 2, 0 };
		PrimitiveUtil.longToInts(0x00000000_00000000L, is, 1);
		assertEqualArrays(is, new int[] { 0, 0, 0, 0 });

		is = new int[] { 127, 1, 2, -128 };
		PrimitiveUtil.longToInts(0x00000000_00000000L, is, 1);
		assertEqualArrays(is, new int[] { 127, 0, 0, -128 });

		is = new int[] { 0, 0, 0, 0 };
		PrimitiveUtil.longToInts(0xFFFFFFFF_FFFFFFFFL, is, 1);
		assertEqualArrays(is, new int[] { 0, -1, -1, 0 });

		is = new int[] { 0, 1, 2, 0 };
		PrimitiveUtil.longToInts(0xFFFFFFFF_FFFFFFFFL, is, 1);
		assertEqualArrays(is, new int[] { 0, -1, -1, 0 });

		is = new int[] { 127, 1, 2, -128 };
		PrimitiveUtil.longToInts(0xFFFFFFFF_FFFFFFFFL, is, 1);
		assertEqualArrays(is, new int[] { 127, -1, -1, -128 });

		is = new int[] { 0, 0, 0, 0 };
		PrimitiveUtil.longToInts(0x00000000_FFFFFFFFL, is, 1);
		assertEqualArrays(is, new int[] { 0, -1, 0, 0 });

		is = new int[] { 0, 1, 2, 0 };
		PrimitiveUtil.longToInts(0x00000000_FFFFFFFFL, is, 1);
		assertEqualArrays(is, new int[] { 0, -1, 0, 0 });

		is = new int[] { 127, 1, 2, -128 };
		PrimitiveUtil.longToInts(0x00000000_FFFFFFFFL, is, 1);
		assertEqualArrays(is, new int[] { 127, -1, 0, -128 });

		is = new int[] { 0, 0, 0, 0 };
		PrimitiveUtil.longToInts(0x00000002_00000001L, is, 1);
		assertEqualArrays(is, new int[] { 0, 1, 2, 0 });

		is = new int[] { 0, 1, 2, 0 };
		PrimitiveUtil.longToInts(0x00000002_00000001L, is, 1);
		assertEqualArrays(is, new int[] { 0, 1, 2, 0 });

		is = new int[] { 127, 1, 2, -128 };
		PrimitiveUtil.longToInts(0x00000002_00000001L, is, 1);
		assertEqualArrays(is, new int[] { 127, 1, 2, -128 });

		is = new int[] { 0, 0, 0, 0 };
		PrimitiveUtil.longToInts(0xF0F0F0F00F0F0F0FL, is, 1);
		assertEqualArrays(is, new int[] { 0, 252645135, -252645136, 0 });

		is = new int[] { 0, 1, 2, 0 };
		PrimitiveUtil.longToInts(0xF0F0F0F00F0F0F0FL, is, 1);
		assertEqualArrays(is, new int[] { 0, 252645135, -252645136, 0 });

		is = new int[] { 127, 1, 2, -128 };
		PrimitiveUtil.longToInts(0xF0F0F0F00F0F0F0FL, is, 1);
		assertEqualArrays(is, new int[] { 127, 252645135, -252645136, -128 });

		is = new int[] { 127, -1, -1, -128 };
		PrimitiveUtil.longToInts(0x00000000_00000000L, is, 1);
		assertEqualArrays(is, new int[] { 127, 0, 0, -128 });

		is = new int[] { 127, 0, 0, -128 };
		PrimitiveUtil.longToInts(0x00000000_00000000L, is, 1);
		assertEqualArrays(is, new int[] { 127, 0, 0, -128 });

		is = new int[] { 127, 10, 20, -128 };
		PrimitiveUtil.longToInts(0x00000000_00000000L, is, 1);
		assertEqualArrays(is, new int[] { 127, 0, 0, -128 });

		is = new int[] { 127, -1, -1, -128 };
		PrimitiveUtil.longToInts(0x00000000_00000000L, is, 1);
		assertEqualArrays(is, new int[] { 127, 0, 0, -128 });

		is = new int[] { 127, 0, 0, -128 };
		PrimitiveUtil.longToInts(0x00000000_00000000L, is, 1);
		assertEqualArrays(is, new int[] { 127, 0, 0, -128 });

		is = new int[] { 127, 10, 20, -128 };
		PrimitiveUtil.longToInts(0xB00BB00BB000000BL, is, 1);
		assertEqualArrays(is, new int[] { 127, -1342177269, -1341411317, -128 });

		try
		{
			PrimitiveUtil.longToInts(0, new int[0], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.longToInts(0, new int[1], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.longToInts(0, new int[2], 1);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testShortsToInt()
	{
		short[] ss;

		ss = new short[] { 0, 0 };
		assertEquals(PrimitiveUtil.shortsToInt(0, ss), 0x00000000);

		ss = new short[] { -128, 0, 0, 127 };
		assertEquals(PrimitiveUtil.shortsToInt(1, ss), 0x00000000);

		ss = new short[] { 1, 0 };
		assertEquals(PrimitiveUtil.shortsToInt(0, ss), 0x00000001);

		ss = new short[] { -128, 1, 0, 127 };
		assertEquals(PrimitiveUtil.shortsToInt(1, ss), 0x00000001);

//		ss = new short[] { 1, 0 };
//		assertEquals(PrimitiveUtil.shortsToInt(0, ss), 0x00000001);
//
//		ss = new short[] { -128, 1, 0, 127 };
//		assertEquals(PrimitiveUtil.shortsToInt(1, ss), 0x00000001);

		ss = new short[] { 0, 4096 };
		assertEquals(PrimitiveUtil.shortsToInt(0, ss), 0x10000000);

		ss = new short[] { -128, 0, 4096, 127 };
		assertEquals(PrimitiveUtil.shortsToInt(1, ss), 0x10000000);

		ss = new short[] { 0, 1 };
		assertEquals(PrimitiveUtil.shortsToInt(0, ss), 0x00010000);

		ss = new short[] { -128, 0, 1, 127 };
		assertEquals(PrimitiveUtil.shortsToInt(1, ss), 0x00010000);

		ss = new short[] { -1, 0 };
		assertEquals(PrimitiveUtil.shortsToInt(0, ss), 0x0000FFFF);

		ss = new short[] { -128, -1, 0, 127 };
		assertEquals(PrimitiveUtil.shortsToInt(1, ss), 0x0000FFFF);

		ss = new short[] { 1, 2 };
		assertEquals(PrimitiveUtil.shortsToInt(0, ss), 0x00020001);

		ss = new short[] { -128, 1, 2, 127 };
		assertEquals(PrimitiveUtil.shortsToInt(1, ss), 0x00020001);

		ss = new short[] { 0, -1 };
		assertEquals(PrimitiveUtil.shortsToInt(0, ss), 0xFFFF0000);

		ss = new short[] { -128, 0, -1, 127 };
		assertEquals(PrimitiveUtil.shortsToInt(1, ss), 0xFFFF0000);

		try
		{
			PrimitiveUtil.shortsToInt(0, new short[0]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.shortsToInt(0, new short[1]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.shortsToInt(1, new short[2]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testShortToBytes()
	{
		byte[] bs;

		// PrimitiveUtil.shortToBytes(long)

		bs = PrimitiveUtil.shortToBytes((short) 0x0000);
		assertEqualArrays(bs, new byte[] { 0, 0 });

		bs = PrimitiveUtil.shortToBytes((short) 0x0001);
		assertEqualArrays(bs, new byte[] { 1, 0 });

		bs = PrimitiveUtil.shortToBytes((short) 0x1000);
		assertEqualArrays(bs, new byte[] { 0, 16 });

		bs = PrimitiveUtil.shortToBytes((short) 0x0100);
		assertEqualArrays(bs, new byte[] { 0, 1 });

		bs = PrimitiveUtil.shortToBytes((short) 0x00FF);
		assertEqualArrays(bs, new byte[] { -1, 0 });

		bs = PrimitiveUtil.shortToBytes((short) 0x0201);
		assertEqualArrays(bs, new byte[] { 1, 2 });

		bs = PrimitiveUtil.shortToBytes((short) 0xF0F0);
		assertEqualArrays(bs, new byte[] { -16, -16 });

		// PrimitiveUtil.shortToBytes(long, byte[], byte)

		bs = new byte[] { 0, 0, 0, 0 };
		PrimitiveUtil.shortToBytes((short) 0x0000, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, 0, 0, 0 });

		bs = new byte[] { 0, 1, 2, 0 };
		PrimitiveUtil.shortToBytes((short) 0x0000, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, 0, 0, 0 });

		bs = new byte[] { 127, 1, 2, -128 };
		PrimitiveUtil.shortToBytes((short) 0x0000, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, -128 });

		bs = new byte[] { 0, 0, 0, 0 };
		PrimitiveUtil.shortToBytes((short) 0xFFFF, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, -1, -1, 0 });

		bs = new byte[] { 0, 1, 2, 0 };
		PrimitiveUtil.shortToBytes((short) 0xFFFF, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, -1, -1, 0 });

		bs = new byte[] { 127, 1, 2, -128 };
		PrimitiveUtil.shortToBytes((short) 0xFFFF, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, -1, -1, -128 });

		bs = new byte[] { 0, 0, 0, 0 };
		PrimitiveUtil.shortToBytes((short) 0x00FF, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, -1, 0, 0 });

		bs = new byte[] { 0, 1, 2, 0 };
		PrimitiveUtil.shortToBytes((short) 0x00FF, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, -1, 0, 0 });

		bs = new byte[] { 127, 1, 2, -128 };
		PrimitiveUtil.shortToBytes((short) 0x00FF, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, -1, 0, -128 });

		bs = new byte[] { 0, 0, 0, 0 };
		PrimitiveUtil.shortToBytes((short) 0x0201, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, 1, 2, 0 });

		bs = new byte[] { 0, 1, 2, 0 };
		PrimitiveUtil.shortToBytes((short) 0x0201, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, 1, 2, 0 });

		bs = new byte[] { 127, 1, 2, -128 };
		PrimitiveUtil.shortToBytes((short) 0x0201, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 1, 2, -128 });

		bs = new byte[] { 0, 0, 0, 0 };
		PrimitiveUtil.shortToBytes((short) 0xF00F, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, 15, -16, 0 });

		bs = new byte[] { 0, 1, 2, 0 };
		PrimitiveUtil.shortToBytes((short) 0xF00F, bs, 1);
		assertEqualArrays(bs, new byte[] { 0, 15, -16, 0 });

		bs = new byte[] { 127, 1, 2, -128 };
		PrimitiveUtil.shortToBytes((short) 0xF00F, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 15, -16, -128 });

		bs = new byte[] { 127, -1, -1, -128 };
		PrimitiveUtil.shortToBytes((short) 0x0000, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, -128 });

		bs = new byte[] { 127, 0, 0, -128 };
		PrimitiveUtil.shortToBytes((short) 0x0000, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, -128 });

		bs = new byte[] { 127, 10, 20, -128 };
		PrimitiveUtil.shortToBytes((short) 0x0000, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, -128 });

		bs = new byte[] { 127, -1, -1, -128 };
		PrimitiveUtil.shortToBytes((short) 0x0000, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, -128 });

		bs = new byte[] { 127, 0, 0, -128 };
		PrimitiveUtil.shortToBytes((short) 0x0000, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 0, 0, -128 });

		bs = new byte[] { 127, 10, 20, -128 };
		PrimitiveUtil.shortToBytes((short) 0xB00B, bs, 1);
		assertEqualArrays(bs, new byte[] { 127, 11, -80, -128 });

		try
		{
			PrimitiveUtil.shortToBytes((short) 0, new byte[0], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.shortToBytes((short) 0, new byte[1], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.shortToBytes((short) 0, new byte[2], 1);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testShortsToLong()
	{
		short[] ss;

		ss = new short[] { 0, 0, 0, 0 };
		assertEquals(PrimitiveUtil.shortsToLong(0, ss), 0x0000000000000000L);

		ss = new short[] { -128, 0, 0, 0, 0, 127 };
		assertEquals(PrimitiveUtil.shortsToLong(1, ss), 0x0000000000000000L);

		ss = new short[] { 1, 0, 0, 0 };
		assertEquals(PrimitiveUtil.shortsToLong(0, ss), 0x0000000000000001L);

		ss = new short[] { -128, 1, 0, 0, 0, 127 };
		assertEquals(PrimitiveUtil.shortsToLong(1, ss), 0x0000000000000001L);

//		is = new short[] { 1, 0 };
//		assertEquals(PrimitiveUtil.shortsToLong(0, is), 0x0000000000000001L);
//
//		is = new short[] { -128, 1, 0, 127 };
//		assertEquals(PrimitiveUtil.shortsToLong(1, is), 0x0000000000000001L);

		ss = new short[] { 0, 0, 0, 4096 };
		assertEquals(PrimitiveUtil.shortsToLong(0, ss), 0x10000000_00000000L);

		ss = new short[] { -128, 0, 0, 0, 4096, 127 };
		assertEquals(PrimitiveUtil.shortsToLong(1, ss), 0x10000000_00000000L);

		ss = new short[] { 0, 0, 0, 1 };
		assertEquals(PrimitiveUtil.shortsToLong(0, ss), 0x00010000_00000000L);

		ss = new short[] { -128, 0, 0, 0, 1, 127 };
		assertEquals(PrimitiveUtil.shortsToLong(1, ss), 0x00010000_00000000L);

		ss = new short[] { -1, -1, 0, 0 };
		assertEquals(PrimitiveUtil.shortsToLong(0, ss), 0x00000000_FFFFFFFFL);

		ss = new short[] { -128, -1, -1, 0, 0, 127 };
		assertEquals(PrimitiveUtil.shortsToLong(1, ss), 0x00000000_FFFFFFFFL);

		ss = new short[] { 1, 2, 3, 4 };
		assertEquals(PrimitiveUtil.shortsToLong(0, ss), 0x00040003_00020001L);

		ss = new short[] { -128, 1, 2, 3, 4, 127 };
		assertEquals(PrimitiveUtil.shortsToLong(1, ss), 0x00040003_00020001L);

		ss = new short[] { 0, 0, -1, -1 };
		assertEquals(PrimitiveUtil.shortsToLong(0, ss), 0xFFFFFFFF_00000000L);

		ss = new short[] { -128, 0, 0, -1, -1, 127 };
		assertEquals(PrimitiveUtil.shortsToLong(1, ss), 0xFFFFFFFF_00000000L);

		try
		{
			PrimitiveUtil.shortsToLong(0, new short[0]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.shortsToLong(0, new short[3]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.shortsToLong(1, new short[4]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testBytesToInt()
	{
		byte[] ss;

		ss = new byte[] { 0, 0, 0, 0 };
		assertEquals(PrimitiveUtil.bytesToInt(0, ss), 0x00000000);

		ss = new byte[] { -128, 0, 0, 0, 0, 127 };
		assertEquals(PrimitiveUtil.bytesToInt(1, ss), 0x00000000);

		ss = new byte[] { 1, 0, 0, 0 };
		assertEquals(PrimitiveUtil.bytesToInt(0, ss), 0x00000001);

		ss = new byte[] { -128, 1, 0, 0, 0, 127 };
		assertEquals(PrimitiveUtil.bytesToInt(1, ss), 0x00000001);

//		ss = new byte[] { 1, 0, 0, 0 };
//		assertEquals(PrimitiveUtil.bytesToInt(0, ss), 0x00000001);
//
//		ss = new byte[] { -128, 1, 0, 0, 0, 127 };
//		assertEquals(PrimitiveUtil.bytesToInt(1, ss), 0x00000001);

		ss = new byte[] { 0, 0, 0, 16 };
		assertEquals(PrimitiveUtil.bytesToInt(0, ss), 0x10000000);

		ss = new byte[] { -128, 0, 0, 0, 16, 127 };
		assertEquals(PrimitiveUtil.bytesToInt(1, ss), 0x10000000);

		ss = new byte[] { 0, 0, 0, 1 };
		assertEquals(PrimitiveUtil.bytesToInt(0, ss), 0x01000000);

		ss = new byte[] { -128, 0, 0, 0, 1, 127 };
		assertEquals(PrimitiveUtil.bytesToInt(1, ss), 0x01000000);

		ss = new byte[] { -1, -1, 0, 0 };
		assertEquals(PrimitiveUtil.bytesToInt(0, ss), 0x0000FFFF);

		ss = new byte[] { -128, -1, -1, 0, 0, 127 };
		assertEquals(PrimitiveUtil.bytesToInt(1, ss), 0x0000FFFF);

		ss = new byte[] { 1, 2, 3, 4 };
		assertEquals(PrimitiveUtil.bytesToInt(0, ss), 0x04030201);

		ss = new byte[] { -128, 1, 2, 3, 4, 127 };
		assertEquals(PrimitiveUtil.bytesToInt(1, ss), 0x04030201);

		ss = new byte[] { 0, 0, -1, -1 };
		assertEquals(PrimitiveUtil.bytesToInt(0, ss), 0xFFFF0000);

		ss = new byte[] { -128, 0, 0, -1, -1, 127 };
		assertEquals(PrimitiveUtil.bytesToInt(1, ss), 0xFFFF0000);

		try
		{
			PrimitiveUtil.bytesToInt(0, new byte[0]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.bytesToInt(0, new byte[3]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.bytesToInt(1, new byte[4]);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	public void testIntToBytes()
	{
		byte[] ss;

		// PrimitiveUtil.intToBytes(long)

		ss = PrimitiveUtil.intToBytes(0x00000000);
		assertEqualArrays(ss, new byte[] { 0, 0, 0, 0 });

		ss = PrimitiveUtil.intToBytes(0x00000001);
		assertEqualArrays(ss, new byte[] { 1, 0, 0, 0 });

		ss = PrimitiveUtil.intToBytes(0x10000000);
		assertEqualArrays(ss, new byte[] { 0, 0, 0, 16 });

		ss = PrimitiveUtil.intToBytes(0x01000000);
		assertEqualArrays(ss, new byte[] { 0, 0, 0, 1 });

		ss = PrimitiveUtil.intToBytes(0x0000FFFF);
		assertEqualArrays(ss, new byte[] { -1, -1, 0, 0 });

		ss = PrimitiveUtil.intToBytes(0x04030201);
		assertEqualArrays(ss, new byte[] { 1, 2, 3, 4 });

		ss = PrimitiveUtil.intToBytes(0xF0F00F0F);
		assertEqualArrays(ss, new byte[] { 15, 15, -16, -16 });

		// PrimitiveUtil.intToBytes(int, byte[], int)

		ss = new byte[] { 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.intToBytes(0x00000000, ss, 1);
		assertEqualArrays(ss, new byte[] { 0, 0, 0, 0, 0, 0 });

		ss = new byte[] { 0, 1, 2, 3, 4, 0 };
		PrimitiveUtil.intToBytes(0x00000000, ss, 1);
		assertEqualArrays(ss, new byte[] { 0, 0, 0, 0, 0, 0 });

		ss = new byte[] { 127, 1, 2, 3, 4, -128 };
		PrimitiveUtil.intToBytes(0x00000000, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, 0, 0, 0, 0, -128 });

		ss = new byte[] { 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.intToBytes(0xFFFFFFFF, ss, 1);
		assertEqualArrays(ss, new byte[] { 0, -1, -1, -1, -1, 0 });

		ss = new byte[] { 0, 1, 2, 3, 4, 0 };
		PrimitiveUtil.intToBytes(0xFFFFFFFF, ss, 1);
		assertEqualArrays(ss, new byte[] { 0, -1, -1, -1, -1, 0 });

		ss = new byte[] { 127, 1, 2, 3, 4, -128 };
		PrimitiveUtil.intToBytes(0xFFFFFFFF, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, -1, -1, -1, -1, -128 });

		ss = new byte[] { 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.intToBytes(0x0000FFFF, ss, 1);
		assertEqualArrays(ss, new byte[] { 0, -1, -1, 0, 0, 0 });

		ss = new byte[] { 0, 1, 2, 3, 4, 0 };
		PrimitiveUtil.intToBytes(0x0000FFFF, ss, 1);
		assertEqualArrays(ss, new byte[] { 0, -1, -1, 0, 0, 0 });

		ss = new byte[] { 127, 1, 2, 3, 4, -128 };
		PrimitiveUtil.intToBytes(0x0000FFFF, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, -1, -1, 0, 0, -128 });

		ss = new byte[] { 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.intToBytes(0x04030201, ss, 1);
		assertEqualArrays(ss, new byte[] { 0, 1, 2, 3, 4, 0 });

		ss = new byte[] { 0, 1, 2, 3, 4, 0 };
		PrimitiveUtil.intToBytes(0x04030201, ss, 1);
		assertEqualArrays(ss, new byte[] { 0, 1, 2, 3, 4, 0 });

		ss = new byte[] { 127, 1, 2, 3, 4, -128 };
		PrimitiveUtil.intToBytes(0x04030201, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, 1, 2, 3, 4, -128 });

		ss = new byte[] { 0, 0, 0, 0, 0, 0 };
		PrimitiveUtil.intToBytes(0xF0F00F0F, ss, 1);
		assertEqualArrays(ss, new byte[] { 0, 15, 15, -16, -16, 0 });

		ss = new byte[] { 0, 1, 2, 3, 4, 0 };
		PrimitiveUtil.intToBytes(0xF0F00F0F, ss, 1);
		assertEqualArrays(ss, new byte[] { 0, 15, 15, -16, -16, 0 });

		ss = new byte[] { 127, 1, 2, 3, 4, -128 };
		PrimitiveUtil.intToBytes(0xF0F00F0F, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, 15, 15, -16, -16, -128 });

		ss = new byte[] { 127, -1, -1, -1, -1, -128 };
		PrimitiveUtil.intToBytes(0x00000000, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, 0, 0, 0, 0, -128 });

		ss = new byte[] { 127, 0, 0, 0, 0, -128 };
		PrimitiveUtil.intToBytes(0x00000000, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, 0, 0, 0, 0, -128 });

		ss = new byte[] { 127, 10, 20, 30, 40, -128 };
		PrimitiveUtil.intToBytes(0x00000000, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, 0, 0, 0, 0, -128 });

		ss = new byte[] { 127, -1, -1, -1, -1, -128 };
		PrimitiveUtil.intToBytes(0x00000000, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, 0, 0, 0, 0, -128 });

		ss = new byte[] { 127, 0, 0, 0, 0, -128 };
		PrimitiveUtil.intToBytes(0x00000000, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, 0, 0, 0, 0, -128 });

		ss = new byte[] { 127, 10, 20, 30, 40, -128 };
		PrimitiveUtil.intToBytes(0xB00BB00B, ss, 1);
		assertEqualArrays(ss, new byte[] { 127, 11, -80, 11, -80, -128 });

		try
		{
			PrimitiveUtil.intToBytes(0, new byte[0], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.intToBytes(0, new byte[3], 0);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}

		try
		{
			PrimitiveUtil.intToBytes(0, new byte[4], 1);
			fail();
		} catch(ArrayIndexOutOfBoundsException ignored) {}
	}

	private void assertEqualArrays(Object o1, Object o2)
	{
		if(o1 == null && o2 == null)
			return;

		if(o1 != null && o2 != null && o1.getClass().isArray() && o1.getClass().equals(o2.getClass()))
		{
			int len = Array.getLength(o1);

			if(len == Array.getLength(o2))
			{
				for(int i = 0; i < len; i++)
				{
					Object a = Array.get(o1, i);
					Object b = Array.get(o2, i);
					if(!Objects.deepEquals(a, b))
						fail("expected index <" + i + "> to be <" + b + ">, not <" + a + ">");
				}
				return;
			}
		}
		fail("Not arrays or not equal classes");
	}
}