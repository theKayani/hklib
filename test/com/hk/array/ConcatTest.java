package com.hk.array;

import junit.framework.TestCase;

/*
 * I fuckin flipped the parameters of the assert statement
 * 
 * FML
 */
public class ConcatTest extends TestCase
{
	public void testSumOfShorts()
	{
		short sum;

		sum = Concat.sumOfShorts();
		assertEquals(sum, 0);

		sum = Concat.sumOfShorts(new short[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfShorts(new short[0], new short[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfShorts(new short[0], new short[0], new short[0], new short[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfShorts(new short[10], new short[10]);
		assertEquals(sum, 0);

		sum = Concat.sumOfShorts(new short[1], new short[10], new short[100], new short[1000]);
		assertEquals(sum, 0);

		sum = Concat.sumOfShorts(new short[] { 1 });
		assertEquals(sum, 1);

		sum = Concat.sumOfShorts(new short[] { 1, 1 });
		assertEquals(sum, 2);

		sum = Concat.sumOfShorts(new short[] { 1 }, new short[] { 1 });
		assertEquals(sum, 2);

		sum = Concat.sumOfShorts(new short[] { 1, 1 }, new short[] { 1, 1 });
		assertEquals(sum, 4);

		sum = Concat.sumOfShorts(new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 });
		assertEquals(sum, 9);

		sum = Concat.sumOfShorts(new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 });
		assertEquals(sum, -9);

		sum = Concat.sumOfShorts(
			new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 },
			new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 },
			new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 },
			new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 },
			new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 },
			new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 },
			new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 },
			new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 }
		);
		assertEquals(sum, 0);
	}

	public void testSumOfBytes()
	{
		byte sum;

		sum = Concat.sumOfBytes();
		assertEquals(sum, 0);

		sum = Concat.sumOfBytes(new byte[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfBytes(new byte[0], new byte[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfBytes(new byte[0], new byte[0], new byte[0], new byte[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfBytes(new byte[10], new byte[10]);
		assertEquals(sum, 0);

		sum = Concat.sumOfBytes(new byte[1], new byte[10], new byte[100], new byte[1000]);
		assertEquals(sum, 0);

		sum = Concat.sumOfBytes(new byte[] { 1 });
		assertEquals(sum, 1);

		sum = Concat.sumOfBytes(new byte[] { 1, 1 });
		assertEquals(sum, 2);

		sum = Concat.sumOfBytes(new byte[] { 1 }, new byte[] { 1 });
		assertEquals(sum, 2);

		sum = Concat.sumOfBytes(new byte[] { 1, 1 }, new byte[] { 1, 1 });
		assertEquals(sum, 4);

		sum = Concat.sumOfBytes(new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 });
		assertEquals(sum, 9);

		sum = Concat.sumOfBytes(new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 });
		assertEquals(sum, -9);

		sum = Concat.sumOfBytes(
			new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 },
			new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 },
			new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 },
			new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 },
			new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 },
			new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 },
			new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 },
			new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 }
		);
		assertEquals(sum, 0);
	}

	public void testSumOfLongs()
	{
		long sum;

		sum = Concat.sumOfLongs();
		assertEquals(sum, 0);

		sum = Concat.sumOfLongs(new long[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfLongs(new long[0], new long[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfLongs(new long[0], new long[0], new long[0], new long[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfLongs(new long[10], new long[10]);
		assertEquals(sum, 0);

		sum = Concat.sumOfLongs(new long[1], new long[10], new long[100], new long[1000]);
		assertEquals(sum, 0);

		sum = Concat.sumOfLongs(new long[] { 1 });
		assertEquals(sum, 1);

		sum = Concat.sumOfLongs(new long[] { 1, 1 });
		assertEquals(sum, 2);

		sum = Concat.sumOfLongs(new long[] { 1 }, new long[] { 1 });
		assertEquals(sum, 2);

		sum = Concat.sumOfLongs(new long[] { 1, 1 }, new long[] { 1, 1 });
		assertEquals(sum, 4);

		sum = Concat.sumOfLongs(new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 });
		assertEquals(sum, 9);

		sum = Concat.sumOfLongs(new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 });
		assertEquals(sum, -9);

		sum = Concat.sumOfLongs(
			new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 },
			new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 },
			new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 },
			new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 },
			new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 },
			new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 },
			new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 },
			new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 }
		);
		assertEquals(sum, 0);
	}

	public void testSumOfFloats()
	{
		float sum;

		sum = Concat.sumOfFloats();
		assertEquals(sum, 0F);

		sum = Concat.sumOfFloats(new float[0]);
		assertEquals(sum, 0F);

		sum = Concat.sumOfFloats(new float[0], new float[0]);
		assertEquals(sum, 0F);

		sum = Concat.sumOfFloats(new float[0], new float[0], new float[0], new float[0]);
		assertEquals(sum, 0F);

		sum = Concat.sumOfFloats(new float[10], new float[10]);
		assertEquals(sum, 0F);

		sum = Concat.sumOfFloats(new float[1], new float[10], new float[100], new float[1000]);
		assertEquals(sum, 0F);

		sum = Concat.sumOfFloats(new float[] { 1 });
		assertEquals(sum, 1F);

		sum = Concat.sumOfFloats(new float[] { 1, 1 });
		assertEquals(sum, 2F);

		sum = Concat.sumOfFloats(new float[] { 1 }, new float[] { 1 });
		assertEquals(sum, 2F);

		sum = Concat.sumOfFloats(new float[] { 1, 1 }, new float[] { 1, 1 });
		assertEquals(sum, 4F);

		sum = Concat.sumOfFloats(new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 });
		assertEquals(sum, 9F);

		sum = Concat.sumOfFloats(new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 });
		assertEquals(sum, -9F);

		sum = Concat.sumOfFloats(
			new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 },
			new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 },
			new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 },
			new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 },
			new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 },
			new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 },
			new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 },
			new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 }
		);
		assertEquals(sum, 0F);
	}

	public void testConcat()
	{
		assertNull(Concat.concat(new Object[0][]));
		
		char[] charSum;

		charSum = Concat.concat(new char[0]);
		assertEquals(charSum.length, 0);

		charSum = Concat.concat(new char[0], new char[0]);
		assertEquals(charSum.length, 0);

		charSum = Concat.concat(new char[0], new char[0], new char[0], new char[0]);
		assertEquals(charSum.length, 0);

		charSum = Concat.concat(new char[10], new char[10]);
		assertEquals(charSum.length, 20);
		for(char l : charSum)
			assertEquals('\u0000', l);

		charSum = Concat.concat(new char[1], new char[10], new char[100], new char[1000]);
		assertEquals(charSum.length, 1111);
		for(char l : charSum)
			assertEquals('\u0000', l);

		charSum = Concat.concat(new char[] { 'a' });
		assertEquals(charSum.length, 1);
		for(char l : charSum)
			assertEquals('a', l);

		charSum = Concat.concat(new char[] { 'a', 'a' });
		assertEquals(charSum.length, 2);
		for(char l : charSum)
			assertEquals('a', l);

		charSum = Concat.concat(new char[] { 'a' }, new char[] { 'a' });
		assertEquals(charSum.length, 2);
		for(char l : charSum)
			assertEquals('a', l);

		charSum = Concat.concat(new char[] { 'a', 'a' }, new char[] { 'a', 'a' });
		assertEquals(charSum.length, 4);
		for(char l : charSum)
			assertEquals('a', l);

		charSum = Concat.concat(new char[] { 'a', 'a' , 'a' }, new char[] { 'a', 'a' , 'a' }, new char[] { 'a', 'a' , 'a' });
		assertEquals(charSum.length, 9);
		for(char l : charSum)
			assertEquals('a', l);

		charSum = Concat.concat(new char[] { 'A', 'A' , 'A' }, new char[] { 'A', 'A' , 'A' }, new char[] { 'A', 'A' , 'A' });
		assertEquals(charSum.length, 9);
		for(char l : charSum)
			assertEquals('A', l);

		charSum = Concat.concat(
			new char[] { 'A', 'A', 'A' }, new char[] { 'A', 'A', 'A' },
			new char[] { 'a', 'a', 'a' }, new char[] { 'a', 'a', 'a' },
			new char[] { 'A', 'A', 'A' }, new char[] { 'A', 'A', 'A' },
			new char[] { 'a', 'a', 'a' }, new char[] { 'a', 'a', 'a' },
			new char[] { 'A', 'A', 'A' }, new char[] { 'A', 'A', 'A' },
			new char[] { 'a', 'a', 'a' }, new char[] { 'a', 'a', 'a' },
			new char[] { 'A', 'A', 'A' }, new char[] { 'A', 'A', 'A' },
			new char[] { 'a', 'a', 'a' }, new char[] { 'a', 'a', 'a' }
		);
		assertEquals(charSum.length, 48);
		for(int i = 0; i < 8; i++)
		{
			char n = i % 2 == 0 ? 'A' : 'a';

			for(int j = 0; j < 6; j++)
				assertEquals(n, charSum[i * 6 + j]);
		}

		long[] longSum;

		longSum = Concat.concat(new long[0]);
		assertEquals(longSum.length, 0);

		longSum = Concat.concat(new long[0], new long[0]);
		assertEquals(longSum.length, 0);

		longSum = Concat.concat(new long[0], new long[0], new long[0], new long[0]);
		assertEquals(longSum.length, 0);

		longSum = Concat.concat(new long[10], new long[10]);
		assertEquals(longSum.length, 20);
		for(long l : longSum)
			assertEquals(0, l);

		longSum = Concat.concat(new long[1], new long[10], new long[100], new long[1000]);
		assertEquals(longSum.length, 1111);
		for(long l : longSum)
			assertEquals(0, l);

		longSum = Concat.concat(new long[] { 1 });
		assertEquals(longSum.length, 1);
		for(long l : longSum)
			assertEquals(1, l);

		longSum = Concat.concat(new long[] { 1, 1 });
		assertEquals(longSum.length, 2);
		for(long l : longSum)
			assertEquals(1, l);

		longSum = Concat.concat(new long[] { 1 }, new long[] { 1 });
		assertEquals(longSum.length, 2);
		for(long l : longSum)
			assertEquals(1, l);

		longSum = Concat.concat(new long[] { 1, 1 }, new long[] { 1, 1 });
		assertEquals(longSum.length, 4);
		for(long l : longSum)
			assertEquals(1, l);

		longSum = Concat.concat(new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 });
		assertEquals(longSum.length, 9);
		for(long l : longSum)
			assertEquals(1, l);

		longSum = Concat.concat(new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 });
		assertEquals(longSum.length, 9);
		for(long l : longSum)
			assertEquals(-1, l);

		longSum = Concat.concat(
			new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 },
			new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 },
			new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 },
			new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 },
			new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 },
			new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 },
			new long[] { -1, -1, -1 }, new long[] { -1, -1, -1 },
			new long[] { 1, 1, 1 }, new long[] { 1, 1, 1 }
		);
		assertEquals(longSum.length, 48);
		for(int i = 0; i < 8; i++)
		{
			int n = i % 2 == 0 ? -1 : 1;

			for(int j = 0; j < 6; j++)
				assertEquals(n, longSum[i * 6 + j]);
		}

		short[] shortSum;

		shortSum = Concat.concat(new short[0]);
		assertEquals(shortSum.length, 0);

		shortSum = Concat.concat(new short[0], new short[0]);
		assertEquals(shortSum.length, 0);

		shortSum = Concat.concat(new short[0], new short[0], new short[0], new short[0]);
		assertEquals(shortSum.length, 0);

		shortSum = Concat.concat(new short[10], new short[10]);
		assertEquals(shortSum.length, 20);
		for(short l : shortSum)
			assertEquals(0, l);

		shortSum = Concat.concat(new short[1], new short[10], new short[100], new short[1000]);
		assertEquals(shortSum.length, 1111);
		for(short l : shortSum)
			assertEquals(0, l);

		shortSum = Concat.concat(new short[] { 1 });
		assertEquals(shortSum.length, 1);
		for(short l : shortSum)
			assertEquals(1, l);

		shortSum = Concat.concat(new short[] { 1, 1 });
		assertEquals(shortSum.length, 2);
		for(short l : shortSum)
			assertEquals(1, l);

		shortSum = Concat.concat(new short[] { 1 }, new short[] { 1 });
		assertEquals(shortSum.length, 2);
		for(short l : shortSum)
			assertEquals(1, l);

		shortSum = Concat.concat(new short[] { 1, 1 }, new short[] { 1, 1 });
		assertEquals(shortSum.length, 4);
		for(short l : shortSum)
			assertEquals(1, l);

		shortSum = Concat.concat(new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 });
		assertEquals(shortSum.length, 9);
		for(short l : shortSum)
			assertEquals(1, l);

		shortSum = Concat.concat(new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 });
		assertEquals(shortSum.length, 9);
		for(short l : shortSum)
			assertEquals(-1, l);

		shortSum = Concat.concat(
			new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 },
			new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 },
			new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 },
			new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 },
			new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 },
			new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 },
			new short[] { -1, -1, -1 }, new short[] { -1, -1, -1 },
			new short[] { 1, 1, 1 }, new short[] { 1, 1, 1 }
		);
		assertEquals(shortSum.length, 48);
		for(int i = 0; i < 8; i++)
		{
			int n = i % 2 == 0 ? -1 : 1;

			for(int j = 0; j < 6; j++)
				assertEquals(n, shortSum[i * 6 + j]);
		}

		boolean[] boolSum;

		boolSum = Concat.concat(new boolean[0]);
		assertEquals(boolSum.length, 0);

		boolSum = Concat.concat(new boolean[0], new boolean[0]);
		assertEquals(boolSum.length, 0);

		boolSum = Concat.concat(new boolean[0], new boolean[0], new boolean[0], new boolean[0]);
		assertEquals(boolSum.length, 0);

		boolSum = Concat.concat(new boolean[10], new boolean[10]);
		assertEquals(boolSum.length, 20);
		for(boolean l : boolSum)
			assertEquals(false, l);

		boolSum = Concat.concat(new boolean[1], new boolean[10], new boolean[100], new boolean[1000]);
		assertEquals(boolSum.length, 1111);
		for(boolean l : boolSum)
			assertEquals(false, l);

		boolSum = Concat.concat(new boolean[] { true });
		assertEquals(boolSum.length, 1);
		for(boolean l : boolSum)
			assertEquals(true, l);

		boolSum = Concat.concat(new boolean[] { true, true });
		assertEquals(boolSum.length, 2);
		for(boolean l : boolSum)
			assertEquals(true, l);

		boolSum = Concat.concat(new boolean[] { true }, new boolean[] { true });
		assertEquals(boolSum.length, 2);
		for(boolean l : boolSum)
			assertEquals(true, l);

		boolSum = Concat.concat(new boolean[] { true, true }, new boolean[] { true, true });
		assertEquals(boolSum.length, 4);
		for(boolean l : boolSum)
			assertEquals(true, l);

		boolSum = Concat.concat(new boolean[] { true, true , true }, new boolean[] { true, true , true }, new boolean[] { true, true , true });
		assertEquals(boolSum.length, 9);
		for(boolean l : boolSum)
			assertEquals(true, l);

		boolSum = Concat.concat(new boolean[] { false, false, false }, new boolean[] { false, false, false }, new boolean[] { false, false, false });
		assertEquals(boolSum.length, 9);
		for(boolean l : boolSum)
			assertEquals(false, l);

		boolSum = Concat.concat(
			new boolean[] { false, false, false }, new boolean[] { false, false, false },
			new boolean[] { true, true , true }, new boolean[] { true, true , true },
			new boolean[] { false, false, false }, new boolean[] { false, false, false },
			new boolean[] { true, true , true }, new boolean[] { true, true , true },
			new boolean[] { false, false, false }, new boolean[] { false, false, false },
			new boolean[] { true, true , true }, new boolean[] { true, true , true },
			new boolean[] { false, false, false }, new boolean[] { false, false, false },
			new boolean[] { true, true , true }, new boolean[] { true, true , true }
		);
		assertEquals(boolSum.length, 48);
		for(int i = 0; i < 8; i++)
		{
			boolean n = i % 2 != 0;

			for(int j = 0; j < 6; j++)
				assertEquals(n, boolSum[i * 6 + j]);
		}

		String[] strSum;

		strSum = Concat.concat(new String[0]);
		assertEquals(strSum.length, 0);

		strSum = Concat.concat(new String[0], new String[0]);
		assertEquals(strSum.length, 0);

		strSum = Concat.concat(new String[0], new String[0], new String[0], new String[0]);
		assertEquals(strSum.length, 0);

		strSum = Concat.concat(new String[10], new String[10]);
		assertEquals(strSum.length, 20);
		for(String l : strSum)
			assertEquals(null, l);

		strSum = Concat.concat(new String[1], new String[10], new String[100], new String[1000]);
		assertEquals(strSum.length, 1111);
		for(String l : strSum)
			assertEquals(null, l);

		strSum = Concat.concat(new String[] { "hello" });
		assertEquals(strSum.length, 1);
		for(String l : strSum)
			assertEquals("hello", l);

		strSum = Concat.concat(new String[] { "hello", "hello" });
		assertEquals(strSum.length, 2);
		for(String l : strSum)
			assertEquals("hello", l);

		strSum = Concat.concat(new String[] { "hello" }, new String[] { "hello" });
		assertEquals(strSum.length, 2);
		for(String l : strSum)
			assertEquals("hello", l);

		strSum = Concat.concat(new String[] { "hello", "hello" }, new String[] { "hello", "hello" });
		assertEquals(strSum.length, 4);
		for(String l : strSum)
			assertEquals("hello", l);

		strSum = Concat.concat(new String[] { "hello", "hello" , "hello" }, new String[] { "hello", "hello" , "hello" }, new String[] { "hello", "hello" , "hello" });
		assertEquals(strSum.length, 9);
		for(String l : strSum)
			assertEquals("hello", l);

		strSum = Concat.concat(new String[] { "bye", "bye", "bye" }, new String[] { "bye", "bye", "bye" }, new String[] { "bye", "bye", "bye" });
		assertEquals(strSum.length, 9);
		for(String l : strSum)
			assertEquals("bye", l);

		strSum = Concat.concat(
			new String[] { "bye", "bye", "bye" }, new String[] { "bye", "bye", "bye" },
			new String[] { "hello", "hello" , "hello" }, new String[] { "hello", "hello" , "hello" },
			new String[] { "bye", "bye", "bye" }, new String[] { "bye", "bye", "bye" },
			new String[] { "hello", "hello" , "hello" }, new String[] { "hello", "hello" , "hello" },
			new String[] { "bye", "bye", "bye" }, new String[] { "bye", "bye", "bye" },
			new String[] { "hello", "hello" , "hello" }, new String[] { "hello", "hello" , "hello" },
			new String[] { "bye", "bye", "bye" }, new String[] { "bye", "bye", "bye" },
			new String[] { "hello", "hello" , "hello" }, new String[] { "hello", "hello" , "hello" }
		);
		assertEquals(strSum.length, 48);
		for(int i = 0; i < 8; i++)
		{
			String n = i % 2 == 0 ? "bye" : "hello";

			for(int j = 0; j < 6; j++)
				assertEquals(n, strSum[i * 6 + j]);
		}

		int[] intSum;

		intSum = Concat.concat(new int[0]);
		assertEquals(intSum.length, 0);

		intSum = Concat.concat(new int[0], new int[0]);
		assertEquals(intSum.length, 0);

		intSum = Concat.concat(new int[0], new int[0], new int[0], new int[0]);
		assertEquals(intSum.length, 0);

		intSum = Concat.concat(new int[10], new int[10]);
		assertEquals(intSum.length, 20);
		for(int l : intSum)
			assertEquals(0, l);

		intSum = Concat.concat(new int[1], new int[10], new int[100], new int[1000]);
		assertEquals(intSum.length, 1111);
		for(int l : intSum)
			assertEquals(0, l);

		intSum = Concat.concat(new int[] { Integer.MAX_VALUE });
		assertEquals(intSum.length, 1);
		for(int l : intSum)
			assertEquals(Integer.MAX_VALUE, l);

		intSum = Concat.concat(new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE });
		assertEquals(intSum.length, 2);
		for(int l : intSum)
			assertEquals(Integer.MAX_VALUE, l);

		intSum = Concat.concat(new int[] { Integer.MAX_VALUE }, new int[] { Integer.MAX_VALUE });
		assertEquals(intSum.length, 2);
		for(int l : intSum)
			assertEquals(Integer.MAX_VALUE, l);

		intSum = Concat.concat(new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE }, new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE });
		assertEquals(intSum.length, 4);
		for(int l : intSum)
			assertEquals(Integer.MAX_VALUE, l);

		intSum = Concat.concat(new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE }, new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE }, new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE });
		assertEquals(intSum.length, 9);
		for(int l : intSum)
			assertEquals(Integer.MAX_VALUE, l);

		intSum = Concat.concat(new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE }, new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE }, new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE });
		assertEquals(intSum.length, 9);
		for(int l : intSum)
			assertEquals(Integer.MIN_VALUE, l);

		intSum = Concat.concat(
			new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE }, new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE },
			new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE }, new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE },
			new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE }, new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE },
			new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE }, new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE },
			new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE }, new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE },
			new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE }, new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE },
			new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE }, new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE },
			new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE }, new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE , Integer.MAX_VALUE }
		);
		assertEquals(intSum.length, 48);
		for(int i = 0; i < 8; i++)
		{
			int n = i % 2 == 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;

			for(int j = 0; j < 6; j++)
				assertEquals(n, intSum[i * 6 + j]);
		}

		double[] doubleSum;

		doubleSum = Concat.concat(new double[0]);
		assertEquals(doubleSum.length, 0);

		doubleSum = Concat.concat(new double[0], new double[0]);
		assertEquals(doubleSum.length, 0);

		doubleSum = Concat.concat(new double[0], new double[0], new double[0], new double[0]);
		assertEquals(doubleSum.length, 0);

		doubleSum = Concat.concat(new double[10], new double[10]);
		assertEquals(doubleSum.length, 20);
		for(double l : doubleSum)
			assertEquals(0D, l);

		doubleSum = Concat.concat(new double[1], new double[10], new double[100], new double[1000]);
		assertEquals(doubleSum.length, 1111);
		for(double l : doubleSum)
			assertEquals(0D, l);

		doubleSum = Concat.concat(new double[] { 1 });
		assertEquals(doubleSum.length, 1);
		for(double l : doubleSum)
			assertEquals(1D, l);

		doubleSum = Concat.concat(new double[] { 1, 1 });
		assertEquals(doubleSum.length, 2);
		for(double l : doubleSum)
			assertEquals(1D, l);

		doubleSum = Concat.concat(new double[] { 1 }, new double[] { 1 });
		assertEquals(doubleSum.length, 2);
		for(double l : doubleSum)
			assertEquals(1D, l);

		doubleSum = Concat.concat(new double[] { 1, 1 }, new double[] { 1, 1 });
		assertEquals(doubleSum.length, 4);
		for(double l : doubleSum)
			assertEquals(1D, l);

		doubleSum = Concat.concat(new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 });
		assertEquals(doubleSum.length, 9);
		for(double l : doubleSum)
			assertEquals(1D, l);

		doubleSum = Concat.concat(new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 });
		assertEquals(doubleSum.length, 9);
		for(double l : doubleSum)
			assertEquals(-1D, l);

		doubleSum = Concat.concat(
			new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 },
			new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 },
			new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 },
			new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 },
			new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 },
			new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 },
			new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 },
			new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 }
		);
		assertEquals(doubleSum.length, 48);
		for(int i = 0; i < 8; i++)
		{
			double n = i % 2 == 0 ? -1D : 1D;

			for(int j = 0; j < 6; j++)
				assertEquals(n, doubleSum[i * 6 + j]);
		}

		float[] floatSum;

		floatSum = Concat.concat(new float[0]);
		assertEquals(floatSum.length, 0);

		floatSum = Concat.concat(new float[0], new float[0]);
		assertEquals(floatSum.length, 0);

		floatSum = Concat.concat(new float[0], new float[0], new float[0], new float[0]);
		assertEquals(floatSum.length, 0);

		floatSum = Concat.concat(new float[10], new float[10]);
		assertEquals(floatSum.length, 20);
		for(float l : floatSum)
			assertEquals(0F, l);

		floatSum = Concat.concat(new float[1], new float[10], new float[100], new float[1000]);
		assertEquals(floatSum.length, 1111);
		for(float l : floatSum)
			assertEquals(0F, l);

		floatSum = Concat.concat(new float[] { 1 });
		assertEquals(floatSum.length, 1);
		for(float l : floatSum)
			assertEquals(1F, l);

		floatSum = Concat.concat(new float[] { 1, 1 });
		assertEquals(floatSum.length, 2);
		for(float l : floatSum)
			assertEquals(1F, l);

		floatSum = Concat.concat(new float[] { 1 }, new float[] { 1 });
		assertEquals(floatSum.length, 2);
		for(float l : floatSum)
			assertEquals(1F, l);

		floatSum = Concat.concat(new float[] { 1, 1 }, new float[] { 1, 1 });
		assertEquals(floatSum.length, 4);
		for(float l : floatSum)
			assertEquals(1F, l);

		floatSum = Concat.concat(new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 });
		assertEquals(floatSum.length, 9);
		for(float l : floatSum)
			assertEquals(1F, l);

		floatSum = Concat.concat(new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 });
		assertEquals(floatSum.length, 9);
		for(float l : floatSum)
			assertEquals(-1F, l);

		floatSum = Concat.concat(
			new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 },
			new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 },
			new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 },
			new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 },
			new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 },
			new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 },
			new float[] { -1, -1, -1 }, new float[] { -1, -1, -1 },
			new float[] { 1, 1, 1 }, new float[] { 1, 1, 1 }
		);
		assertEquals(floatSum.length, 48);
		for(int i = 0; i < 8; i++)
		{
			float n = i % 2 == 0 ? -1F : 1F;

			for(int j = 0; j < 6; j++)
				assertEquals(n, floatSum[i * 6 + j]);
		}

		byte[] byteSum;

		byteSum = Concat.concat(new byte[0]);
		assertEquals(byteSum.length, 0);

		byteSum = Concat.concat(new byte[0], new byte[0]);
		assertEquals(byteSum.length, 0);

		byteSum = Concat.concat(new byte[0], new byte[0], new byte[0], new byte[0]);
		assertEquals(byteSum.length, 0);

		byteSum = Concat.concat(new byte[10], new byte[10]);
		assertEquals(byteSum.length, 20);
		for(byte l : byteSum)
			assertEquals((byte) 0, l);

		byteSum = Concat.concat(new byte[1], new byte[10], new byte[100], new byte[1000]);
		assertEquals(byteSum.length, 1111);
		for(byte l : byteSum)
			assertEquals((byte) 0, l);

		byteSum = Concat.concat(new byte[] { 1 });
		assertEquals(byteSum.length, 1);
		for(byte l : byteSum)
			assertEquals((byte) 1, l);

		byteSum = Concat.concat(new byte[] { 1, 1 });
		assertEquals(byteSum.length, 2);
		for(byte l : byteSum)
			assertEquals((byte) 1, l);

		byteSum = Concat.concat(new byte[] { 1 }, new byte[] { 1 });
		assertEquals(byteSum.length, 2);
		for(byte l : byteSum)
			assertEquals((byte) 1, l);

		byteSum = Concat.concat(new byte[] { 1, 1 }, new byte[] { 1, 1 });
		assertEquals(byteSum.length, 4);
		for(byte l : byteSum)
			assertEquals((byte) 1, l);

		byteSum = Concat.concat(new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 });
		assertEquals(byteSum.length, 9);
		for(byte l : byteSum)
			assertEquals((byte) 1, l);

		byteSum = Concat.concat(new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 });
		assertEquals(byteSum.length, 9);
		for(byte l : byteSum)
			assertEquals((byte) -1, l);

		byteSum = Concat.concat(
			new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 },
			new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 },
			new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 },
			new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 },
			new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 },
			new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 },
			new byte[] { -1, -1, -1 }, new byte[] { -1, -1, -1 },
			new byte[] { 1, 1, 1 }, new byte[] { 1, 1, 1 }
		);
		assertEquals(byteSum.length, 48);
		for(int i = 0; i < 8; i++)
		{
			byte n = (byte) (i % 2 == 0 ? -1 : 1);

			for(int j = 0; j < 6; j++)
				assertEquals(n, byteSum[i * 6 + j]);
		}
	}

	public void testSumOfInts()
	{
		int sum;

		sum = Concat.sumOfInts();
		assertEquals(sum, 0);

		sum = Concat.sumOfInts(new int[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfInts(new int[0], new int[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfInts(new int[0], new int[0], new int[0], new int[0]);
		assertEquals(sum, 0);

		sum = Concat.sumOfInts(new int[10], new int[10]);
		assertEquals(sum, 0);

		sum = Concat.sumOfInts(new int[1], new int[10], new int[100], new int[1000]);
		assertEquals(sum, 0);

		sum = Concat.sumOfInts(new int[] { 1 });
		assertEquals(sum, 1);

		sum = Concat.sumOfInts(new int[] { 1, 1 });
		assertEquals(sum, 2);

		sum = Concat.sumOfInts(new int[] { 1 }, new int[] { 1 });
		assertEquals(sum, 2);

		sum = Concat.sumOfInts(new int[] { 1, 1 }, new int[] { 1, 1 });
		assertEquals(sum, 4);

		sum = Concat.sumOfInts(new int[] { 1, 1, 1 }, new int[] { 1, 1, 1 }, new int[] { 1, 1, 1 });
		assertEquals(sum, 9);

		sum = Concat.sumOfInts(new int[] { -1, -1, -1 }, new int[] { -1, -1, -1 }, new int[] { -1, -1, -1 });
		assertEquals(sum, -9);

		sum = Concat.sumOfInts(
			new int[] { -1, -1, -1 }, new int[] { -1, -1, -1 },
			new int[] { 1, 1, 1 }, new int[] { 1, 1, 1 },
			new int[] { -1, -1, -1 }, new int[] { -1, -1, -1 },
			new int[] { 1, 1, 1 }, new int[] { 1, 1, 1 },
			new int[] { -1, -1, -1 }, new int[] { -1, -1, -1 },
			new int[] { 1, 1, 1 }, new int[] { 1, 1, 1 },
			new int[] { -1, -1, -1 }, new int[] { -1, -1, -1 },
			new int[] { 1, 1, 1 }, new int[] { 1, 1, 1 }
		);
		assertEquals(sum, 0);
	}

	public void testSumOfChars()
	{
		// TODO: Concat.sumOfChars(char[][])
	}

	public void testConcatStrings()
	{
		String total;

		total = Concat.concatStrings();
		assertEquals(total, "");

		total = Concat.concatStrings(new String[0]);
		assertEquals(total, "");

		total = Concat.concatStrings(new String[0], new String[0]);
		assertEquals(total, "");

		total = Concat.concatStrings(new String[0], new String[0], new String[0], new String[0]);
		assertEquals(total, "");

		total = Concat.concatStrings(new String[10], new String[10]);
		assertEquals(total, "");

		total = Concat.concatStrings(new String[1], new String[10], new String[100], new String[1000]);
		assertEquals(total, "");

		total = Concat.concatStrings(new String[] { "a" });
		assertEquals(total, "a");

		total = Concat.concatStrings(new String[] { "ab", "cd" });
		assertEquals(total, "abcd");

		total = Concat.concatStrings(new String[] { "a" }, new String[] { "b" });
		assertEquals(total, "ab");

		total = Concat.concatStrings(new String[] { "a", "b" }, new String[] { "cd", "ef" });
		assertEquals(total, "abcdef");

		total = Concat.concatStrings(new String[] { "1", "1", "1" }, new String[] { "1", "1", "1" }, new String[] { "1", "1", "1" });
		assertEquals(total, "111111111");

		total = Concat.concatStrings(new String[] { "02", "28", "2020" }, new String[] { "03", "14", "2021" }, new String[] { "01", "01", "1970" });
		assertEquals(total, "022820200314202101011970");

		total = Concat.concatStrings(
			new String[] { "0", "0", "0" }, new String[] { "0", "0", "0" },
			new String[] { "1", "1", "1" }, new String[] { "1", "1", "1" },
			new String[] { "0", "0", "0" }, new String[] { "0", "0", "0" },
			new String[] { "1", "1", "1" }, new String[] { "1", "1", "1" },
			new String[] { "0", "0", "0" }, new String[] { "0", "0", "0" },
			new String[] { "1", "1", "1" }, new String[] { "1", "1", "1" },
			new String[] { "0", "0", "0" }, new String[] { "0", "0", "0" },
			new String[] { "1", "1", "1" }, new String[] { "1", "1", "1" }
		);
		assertEquals(total, "000000111111000000111111000000111111000000111111");
	}

	public void testSumOfDoubles()
	{
		double sum;

		sum = Concat.sumOfDoubles();
		assertEquals(sum, 0D);

		sum = Concat.sumOfDoubles(new double[0]);
		assertEquals(sum, 0D);

		sum = Concat.sumOfDoubles(new double[0], new double[0]);
		assertEquals(sum, 0D);

		sum = Concat.sumOfDoubles(new double[0], new double[0], new double[0], new double[0]);
		assertEquals(sum, 0D);

		sum = Concat.sumOfDoubles(new double[10], new double[10]);
		assertEquals(sum, 0D);

		sum = Concat.sumOfDoubles(new double[1], new double[10], new double[100], new double[1000]);
		assertEquals(sum, 0D);

		sum = Concat.sumOfDoubles(new double[] { 1 });
		assertEquals(sum, 1D);

		sum = Concat.sumOfDoubles(new double[] { 1, 1 });
		assertEquals(sum, 2D);

		sum = Concat.sumOfDoubles(new double[] { 1 }, new double[] { 1 });
		assertEquals(sum, 2D);

		sum = Concat.sumOfDoubles(new double[] { 1, 1 }, new double[] { 1, 1 });
		assertEquals(sum, 4D);

		sum = Concat.sumOfDoubles(new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 });
		assertEquals(sum, 9D);

		sum = Concat.sumOfDoubles(new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 });
		assertEquals(sum, -9D);

		sum = Concat.sumOfDoubles(
			new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 },
			new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 },
			new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 },
			new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 },
			new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 },
			new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 },
			new double[] { -1, -1, -1 }, new double[] { -1, -1, -1 },
			new double[] { 1, 1, 1 }, new double[] { 1, 1, 1 }
		);
		assertEquals(sum, 0D);
	}
}