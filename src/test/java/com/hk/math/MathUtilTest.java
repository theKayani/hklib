package com.hk.math;

import java.util.Random;

import junit.framework.TestCase;

public class MathUtilTest extends TestCase
{
	private Random rng;
	
	@Override
	public void setUp()
	{
		rng = new Random(-391019036797665643L);
	}

	public void testLongBin()
	{
		long l;
		
		l = 0b1111111111111111111111111111111111111111111111111111111111111000L;
		assertEquals(MathUtil.longBin(l), "1111111111111111111111111111111111111111111111111111111111111000");

		l = 0b1111111111111111111111111111111111111111111111111111111111111100L;
		assertEquals(MathUtil.longBin(l), "1111111111111111111111111111111111111111111111111111111111111100");

		l = 0b1111111111111111111111111111111111111111111111111111111111111110L;
		assertEquals(MathUtil.longBin(l), "1111111111111111111111111111111111111111111111111111111111111110");

		l = 0b1111111111111111111111111111111111111111111111111111111111111111L;
		assertEquals(MathUtil.longBin(l), "1111111111111111111111111111111111111111111111111111111111111111");

		l = 0b0L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000000000000000000000000000000000000");

		l = 0b1L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000000000000000000000000000000000001");

		l = 0b10L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000000000000000000000000000000000010");

		l = 0b100L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000000000000000000000000000000000100");

		l = 0b1000L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000000000000000000000000000000001000");

		l = 0b1111111111111111111111111111111111111111111111111111111110011000L;
		assertEquals(MathUtil.longBin(l), "1111111111111111111111111111111111111111111111111111111110011000");

		l = 0b1111111111111111111111111111111111111111111111111111111111001001L;
		assertEquals(MathUtil.longBin(l), "1111111111111111111111111111111111111111111111111111111111001001");

		l = 0b1111111111111111111111111111111111111111111111111111111110101010L;
		assertEquals(MathUtil.longBin(l), "1111111111111111111111111111111111111111111111111111111110101010");

		l = 0b1100010L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000000000000000000000000000001100010");

		l = 0b110011111100001L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000000000000000000000110011111100001");

		l = 0b111011000111001L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000000000000000000000111011000111001");

		l = 0b1111111111111111111111111111111111111111111111111111100111110101L;
		assertEquals(MathUtil.longBin(l), "1111111111111111111111111111111111111111111111111111100111110101");

		l = 0b1111111111111111111111111111111111111111111111111110001110111101L;
		assertEquals(MathUtil.longBin(l), "1111111111111111111111111111111111111111111111111110001110111101");

		l = 0b1111111111111111111111111111111110111011100100001110011101000101L;
		assertEquals(MathUtil.longBin(l), "1111111111111111111111111111111110111011100100001110011101000101");

		l = 0b1000000000011011001000000011111L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000001000000000011011001000000011111");

		l = 0b10111100101000011011111010001L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000000010111100101000011011111010001");

		l = 0b1111001011001001000000110000L;
		assertEquals(MathUtil.longBin(l), "0000000000000000000000000000000000001111001011001001000000110000");

		l = 0b1111110011100111100010010011010001111111101011010000110001110101L;
		assertEquals(MathUtil.longBin(l), "1111110011100111100010010011010001111111101011010000110001110101");

		l = 0b1111001110000010010100001010101000010001101010110000001000100100L;
		assertEquals(MathUtil.longBin(l), "1111001110000010010100001010101000010001101010110000001000100100");

		l = 0b1100001010001101111100010111001011011111011111001001100000010101L;
		assertEquals(MathUtil.longBin(l), "1100001010001101111100010111001011011111011111001001100000010101");

		l = 0b1011010001000011111110011111011010000111001111101101110000010101L;
		assertEquals(MathUtil.longBin(l), "1011010001000011111110011111011010000111001111101101110000010101");

		l = 0b1011011101010100111011101111001111100110101000001010100100111111L;
		assertEquals(MathUtil.longBin(l), "1011011101010100111011101111001111100110101000001010100100111111");

		l = 0b10000111101000010111011110101100101101001101001001101011110L;
		assertEquals(MathUtil.longBin(l), "0000010000111101000010111011110101100101101001101001001101011110");

		l = 0b100101100100101110101110000100110101000101001000110011010101100L;
		assertEquals(MathUtil.longBin(l), "0100101100100101110101110000100110101000101001000110011010101100");

		l = 0b110101100111000001111011111110000010010110110111101000001100110L;
		assertEquals(MathUtil.longBin(l), "0110101100111000001111011111110000010010110110111101000001100110");

		l = 0b111111111111111111111111111111111111111111111111111111111111111L;
		assertEquals(MathUtil.longBin(l), "0111111111111111111111111111111111111111111111111111111111111111");

		l = 0b111111111111111111111111111111111111111111111111111111111111110L;
		assertEquals(MathUtil.longBin(l), "0111111111111111111111111111111111111111111111111111111111111110");

		l = 0b111111111111111111111111111111111111111111111111111111111111101L;
		assertEquals(MathUtil.longBin(l), "0111111111111111111111111111111111111111111111111111111111111101");

		l = 0b111111111111111111111111111111111111111111111111111111111111100L;
		assertEquals(MathUtil.longBin(l), "0111111111111111111111111111111111111111111111111111111111111100");

		l = 0b111111111111111111111111111111111111111111111111111111111111011L;
		assertEquals(MathUtil.longBin(l), "0111111111111111111111111111111111111111111111111111111111111011");

		l = 0b111111111111111111111111111111111111111111111111111111111111010L;
		assertEquals(MathUtil.longBin(l), "0111111111111111111111111111111111111111111111111111111111111010");

		l = 0b111111111111111111111111111111111111111111111111111111111111001L;
		assertEquals(MathUtil.longBin(l), "0111111111111111111111111111111111111111111111111111111111111001");

		l = 0b111111111111111111111111111111111111111111111111111111111111000L;
		assertEquals(MathUtil.longBin(l), "0111111111111111111111111111111111111111111111111111111111111000");

		l = 0b1000000000000000000000000000000000000000000000000000000000000000L;
		assertEquals(MathUtil.longBin(l), "1000000000000000000000000000000000000000000000000000000000000000");

		l = 0b1000000000000000000000000000000000000000000000000000000000000001L;
		assertEquals(MathUtil.longBin(l), "1000000000000000000000000000000000000000000000000000000000000001");

		l = 0b1000000000000000000000000000000000000000000000000000000000000010L;
		assertEquals(MathUtil.longBin(l), "1000000000000000000000000000000000000000000000000000000000000010");

		l = 0b1000000000000000000000000000000000000000000000000000000000000011L;
		assertEquals(MathUtil.longBin(l), "1000000000000000000000000000000000000000000000000000000000000011");

		l = 0b1000000000000000000000000000000000000000000000000000000000000100L;
		assertEquals(MathUtil.longBin(l), "1000000000000000000000000000000000000000000000000000000000000100");

		l = 0b1000000000000000000000000000000000000000000000000000000000000101L;
		assertEquals(MathUtil.longBin(l), "1000000000000000000000000000000000000000000000000000000000000101");

		l = 0b1000000000000000000000000000000000000000000000000000000000000110L;
		assertEquals(MathUtil.longBin(l), "1000000000000000000000000000000000000000000000000000000000000110");

		l = 0b1000000000000000000000000000000000000000000000000000000000000111L;
		assertEquals(MathUtil.longBin(l), "1000000000000000000000000000000000000000000000000000000000000111");
	}

	public void testLog()
	{
		assertEquals(MathUtil.log(100D, 10D), 2D);
		assertEquals(MathUtil.log(100F, 10F), 2F);
		assertEquals(MathUtil.log(10D, 100D), 0.5D);
		assertEquals(MathUtil.log(10F, 100F), 0.5F);
		assertEquals(MathUtil.log(64D, 2D), 6D);
		assertEquals(MathUtil.log(64F, 2F), 6F);
		assertEquals(MathUtil.log(9D, 3D), 2D);
		assertEquals(MathUtil.log(9F, 3F), 2F);
		
//		for(int i = 0; i < 1000; i++)
//		{
//			int n = rng.nextInt(73) + 1;
//			int r = rng.nextInt(5) + 1;
////			if(n == 0 || r == 0)
////			{
////				i--;
////				continue;
////			}
//			double l = Math.pow(n, r);
//			
//			
//			System.out.println(n + "^" + r + "=" + l);
//			assertEquals(MathUtil.log(l, n), r, 0.0000000001);
//		}
	}

	public void testMax()
	{
		double d;
		float f;
		
		d = MathUtil.max(1D, 2D, 3D);
		assertEquals(d, 3D);
		
		d = MathUtil.max(0D, 1D, 2D, 3D);
		assertEquals(d, 3D);
		
		d = MathUtil.max(3D, 2D, 1D);
		assertEquals(d, 3D);
		
		d = MathUtil.max(3D, 2D, 1D, 0D);
		assertEquals(d, 3D);
		
		d = MathUtil.max(-1D, -2D, -3D);
		assertEquals(d, -1D);
		
		d = MathUtil.max(-0D, -1D, -2D, -3D);
		assertEquals(d, -0D);
		
		d = MathUtil.max(-3D, -2D, -1D);
		assertEquals(d, -1D);
		
		d = MathUtil.max(-3D, -2D, -1D, -0D);
		assertEquals(d, -0D);
		
		f = MathUtil.max(1F, 2F, 3F);
		assertEquals(f, 3F);
		
		f = MathUtil.max(0F, 1F, 2F, 3F);
		assertEquals(f, 3F);
		
		f = MathUtil.max(3F, 2F, 1F);
		assertEquals(f, 3F);
		
		f = MathUtil.max(3F, 2F, 1F, 0F);
		assertEquals(f, 3F);
		
		f = MathUtil.max(-1F, -2F, -3F);
		assertEquals(f, -1F);
		
		f = MathUtil.max(-0F, -1F, -2F, -3F);
		assertEquals(f, -0F);
		
		f = MathUtil.max(-3F, -2F, -1F);
		assertEquals(f, -1F);
		
		f = MathUtil.max(-3F, -2F, -1F, -0F);
		assertEquals(f, -0F);
		
		int[] sizes = { 5, 10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000 };
		for(int size : sizes)
		{
			String label = "with size <" + size + ">";
			double[] ds = new double[size];
			
			for(int i = 0; i < ds.length; i++)
				ds[i] = i + 1;
			
			assertEquals(label, MathUtil.max(ds), (double) size);
			
			for(int i = 0; i < ds.length; i++)
				ds[i] = size + 1 - (i + 1);
			
			assertEquals(label, MathUtil.max(ds), (double) size);
			
			for(int i = 0; i < ds.length; i++)
				ds[i] = -i;
			
			assertEquals(label, MathUtil.max(ds), 0D);
			
			for(int i = 0; i < ds.length; i++)
				ds[i] = i - size + 1;
			
			assertEquals(label, MathUtil.max(ds), 0D);
			
			for(int i = 0; i < ds.length; i++)
				ds[i] = i + 1;
	
			for(int i = ds.length - 1; i > 0; i--)
			{
				int j = rng.nextInt(i);
				double tmp = ds[i];
				ds[i] = ds[j];
				ds[j] = tmp;
			}
			assertEquals(label, MathUtil.max(ds), (double) size);
		
			float[] fs = new float[size];
			
			for(int i = 0; i < fs.length; i++)
				fs[i] = i + 1;
			
			assertEquals(label, MathUtil.max(fs), (float) size);
			
			for(int i = 0; i < fs.length; i++)
				fs[i] = size + 1 - (i + 1);
			
			assertEquals(label, MathUtil.max(fs), (float) size);
			
			for(int i = 0; i < fs.length; i++)
				fs[i] = -i;
			
			assertEquals(label, MathUtil.max(fs), 0F);
			
			for(int i = 0; i < fs.length; i++)
				fs[i] = i - size + 1;
			
			assertEquals(label, MathUtil.max(fs), 0F);
			
			for(int i = 0; i < fs.length; i++)
				fs[i] = i + 1;
	
			for(int i = fs.length - 1; i > 0; i--)
			{
				int j = rng.nextInt(i);
				float tmp = fs[i];
				fs[i] = fs[j];
				fs[j] = tmp;
			}
			assertEquals(label, MathUtil.max(fs), (float) size);
		}
	}

	public void testMin()
	{
		double d;
		float f;
		
		d = MathUtil.min(1D, 2D, 3D);
		assertEquals(d, 1D);
		
		d = MathUtil.min(0D, 1D, 2D, 3D);
		assertEquals(d, 0D);
		
		d = MathUtil.min(3D, 2D, 1D);
		assertEquals(d, 1D);
		
		d = MathUtil.min(3D, 2D, 1D, 0D);
		assertEquals(d, 0D);
		
		d = MathUtil.min(-1D, -2D, -3D);
		assertEquals(d, -3D);
		
		d = MathUtil.min(-0D, -1D, -2D, -3D);
		assertEquals(d, -3D);
		
		d = MathUtil.min(-3D, -2D, -1D);
		assertEquals(d, -3D);
		
		d = MathUtil.min(-3D, -2D, -1D, -0D);
		assertEquals(d, -3D);
		
		f = MathUtil.min(1F, 2F, 3F);
		assertEquals(f, 1F);
		
		f = MathUtil.min(0F, 1F, 2F, 3F);
		assertEquals(f, 0F);
		
		f = MathUtil.min(3F, 2F, 1F);
		assertEquals(f, 1F);
		
		f = MathUtil.min(3F, 2F, 1F, 0F);
		assertEquals(f, 0F);
		
		f = MathUtil.min(-1F, -2F, -3F);
		assertEquals(f, -3F);
		
		f = MathUtil.min(-0F, -1F, -2F, -3F);
		assertEquals(f, -3F);
		
		f = MathUtil.min(-3F, -2F, -1F);
		assertEquals(f, -3F);
		
		f = MathUtil.min(-3F, -2F, -1F, -0F);
		assertEquals(f, -3F);
		
		int[] sizes = { 5, 10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000 };
		for(int size : sizes)
		{
			String label = "with size <" + size + ">";
			double[] ds = new double[size];
			
			for(int i = 0; i < ds.length; i++)
				ds[i] = i + 1;
			
			assertEquals(label, MathUtil.min(ds), 1D);
			
			for(int i = 0; i < ds.length; i++)
				ds[i] = size + 1 - (i + 1);
			
			assertEquals(label, MathUtil.min(ds), 1D);
			
			for(int i = 0; i < ds.length; i++)
				ds[i] = -i;
			
			assertEquals(label, MathUtil.min(ds), (double) -(size - 1));
			
			for(int i = 0; i < ds.length; i++)
				ds[i] = i - size + 1;
			
			assertEquals(label, MathUtil.min(ds), (double) -(size - 1));
			
			for(int i = 0; i < ds.length; i++)
				ds[i] = i + 1;
	
			for(int i = ds.length - 1; i > 0; i--)
			{
				int j = rng.nextInt(i);
				double tmp = ds[i];
				ds[i] = ds[j];
				ds[j] = tmp;
			}
			assertEquals(label, MathUtil.min(ds), 1D);
		
			float[] fs = new float[size];
			
			for(int i = 0; i < fs.length; i++)
				fs[i] = i + 1;
			
			assertEquals(label, MathUtil.min(fs), 1F);
			
			for(int i = 0; i < fs.length; i++)
				fs[i] = size + 1 - (i + 1);
			
			assertEquals(label, MathUtil.min(fs), 1F);
			
			for(int i = 0; i < fs.length; i++)
				fs[i] = -i;
			
			assertEquals(label, MathUtil.min(fs), (float) -(size - 1));
			
			for(int i = 0; i < fs.length; i++)
				fs[i] = i - size + 1;
			
			assertEquals(label, MathUtil.min(fs), (float) -(size - 1));
			
			for(int i = 0; i < fs.length; i++)
				fs[i] = i + 1;
	
			for(int i = fs.length - 1; i > 0; i--)
			{
				int j = rng.nextInt(i);
				float tmp = fs[i];
				fs[i] = fs[j];
				fs[j] = tmp;
			}
			assertEquals(label, MathUtil.min(fs), 1F);
		}
	}

	public void testLerp()
	{
		assertEquals(MathUtil.lerp(0D, 1D, 0.5D), 0.5D);
		assertEquals(MathUtil.lerp(0D, 2D, 0.5D), 1D);
		assertEquals(MathUtil.lerp(1D, 2D, 0.5D), 1.5D);
		assertEquals(MathUtil.lerp(-1D, 1D, 0.5D), 0D);
		assertEquals(MathUtil.lerp(0D, 100D, 0.5D), 50D);
		
		
		double d;
		for(int i = 0; i < 1000; i++)
		{
			d = rng.nextDouble();
			assertEquals(MathUtil.lerp(0D, 1D, d), 1D - d);
			assertEquals(MathUtil.lerp(0D, 1D, 1D - d), d);
			assertEquals(MathUtil.lerp(1D, 0D, d), d);
			assertEquals(MathUtil.lerp(1D, 0D, 1D - d), 1D - d);
			assertEquals(MathUtil.lerp(0D, 100D, d), (1D - d) * 100D);
		}
	}

	public void testSign()
	{
		assertEquals((byte) 0, MathUtil.sign((byte) 0));
		assertEquals((short) 0, MathUtil.sign((short) 0));
		assertEquals(0, MathUtil.sign(0));
		assertEquals(0L, MathUtil.sign(0L));

		assertEquals(0F, MathUtil.sign(0F));
		assertEquals(0D, MathUtil.sign(0D));

		assertEquals((byte) 1, MathUtil.sign((byte) 1));
		assertEquals((short) 1, MathUtil.sign((short) 1));
		assertEquals(1, MathUtil.sign(1));
		assertEquals(1L, MathUtil.sign(1L));

		assertEquals(1F, MathUtil.sign(1F));
		assertEquals(1D, MathUtil.sign(1D));

		assertEquals((byte) -1, MathUtil.sign((byte) -1));
		assertEquals((short) -1, MathUtil.sign((short) -1));
		assertEquals(-1, MathUtil.sign(-1));
		assertEquals(-1L, MathUtil.sign(-1L));

		assertEquals(-1F, MathUtil.sign(-1F));
		assertEquals(-1D, MathUtil.sign(-1D));

		assertEquals((byte) 1, MathUtil.sign((byte) 100));
		assertEquals((short) 1, MathUtil.sign((short) 100));
		assertEquals(1, MathUtil.sign(100));
		assertEquals(1L, MathUtil.sign(100L));

		assertEquals(1F, MathUtil.sign(100F));
		assertEquals(1D, MathUtil.sign(100D));

		assertEquals((byte) -1, MathUtil.sign((byte) -100));
		assertEquals((short) -1, MathUtil.sign((short) -100));
		assertEquals(-1, MathUtil.sign(-100));
		assertEquals(-1L, MathUtil.sign(-100L));

		assertEquals(-1F, MathUtil.sign(-100F));
		assertEquals(-1D, MathUtil.sign(-100D));
		
		for(int i = 0; i < 1000; i++)
		{
			assertEquals((byte) 1, MathUtil.sign((byte) (rng.nextInt(Byte.MAX_VALUE - 1) + 1)));
			assertEquals((short) 1, MathUtil.sign((short) (rng.nextInt(Short.MAX_VALUE - 1) + 1)));
			assertEquals(1, MathUtil.sign(rng.nextInt(Integer.MAX_VALUE - 1) + 1));
			
			long l = rng.nextInt();
			l = ((long) (rng.nextInt(Integer.MAX_VALUE - 1) + 1) << 32L) + l;
			assertEquals(1L, MathUtil.sign(l));
		}
		
		for(int i = 0; i < 1000; i++)
		{
			assertEquals((byte) -1, MathUtil.sign((byte) (-rng.nextInt(-(Byte.MIN_VALUE + 1)) - 1)));
			assertEquals((short) -1, MathUtil.sign((short) (-rng.nextInt(-(Short.MIN_VALUE + 1)) - 1)));
			assertEquals(-1, MathUtil.sign(-rng.nextInt(-(Integer.MIN_VALUE + 1)) - 1));

			long l = rng.nextInt();
			l = ((long) (-rng.nextInt(-(Integer.MIN_VALUE + 1)) - 1) << 32L) | l;
			assertEquals(-1L, MathUtil.sign(l));
		}
	}

	public void testIntBin()
	{
		int i;

		// -5
		i = 0b11111111111111111111111111111011;
		assertEquals("11111111111111111111111111111011", MathUtil.intBin(i));

		// -4
		i = 0b11111111111111111111111111111100;
		assertEquals("11111111111111111111111111111100", MathUtil.intBin(i));

		// -3
		i = 0b11111111111111111111111111111101;
		assertEquals("11111111111111111111111111111101", MathUtil.intBin(i));

		// -2
		i = 0b11111111111111111111111111111110;
		assertEquals("11111111111111111111111111111110", MathUtil.intBin(i));

		// -1
		i = 0b11111111111111111111111111111111;
		assertEquals("11111111111111111111111111111111", MathUtil.intBin(i));

		// 0
		i = 0b0;
		assertEquals("00000000000000000000000000000000", MathUtil.intBin(i));

		// 1
		i = 0b1;
		assertEquals("00000000000000000000000000000001", MathUtil.intBin(i));

		// 2
		i = 0b10;
		assertEquals("00000000000000000000000000000010", MathUtil.intBin(i));

		// 3
		i = 0b11;
		assertEquals("00000000000000000000000000000011", MathUtil.intBin(i));

		// 4
		i = 0b100;
		assertEquals("00000000000000000000000000000100", MathUtil.intBin(i));

		// 5
		i = 0b101;
		assertEquals("00000000000000000000000000000101", MathUtil.intBin(i));

		// 2147483646
		i = 0b1111111111111111111111111111110;
		assertEquals("01111111111111111111111111111110", MathUtil.intBin(i));

		// -2147483647
		i = 0b10000000000000000000000000000001;
		assertEquals("10000000000000000000000000000001", MathUtil.intBin(i));

		// 2147483645
		i = 0b1111111111111111111111111111101;
		assertEquals("01111111111111111111111111111101", MathUtil.intBin(i));

		// -2147483646
		i = 0b10000000000000000000000000000010;
		assertEquals("10000000000000000000000000000010", MathUtil.intBin(i));

		// 2147483644
		i = 0b1111111111111111111111111111100;
		assertEquals("01111111111111111111111111111100", MathUtil.intBin(i));

		// -2147483645
		i = 0b10000000000000000000000000000011;
		assertEquals("10000000000000000000000000000011", MathUtil.intBin(i));

		// 2147483643
		i = 0b1111111111111111111111111111011;
		assertEquals("01111111111111111111111111111011", MathUtil.intBin(i));

		// -2147483644
		i = 0b10000000000000000000000000000100;
		assertEquals("10000000000000000000000000000100", MathUtil.intBin(i));

		// 2147483642
		i = 0b1111111111111111111111111111010;
		assertEquals("01111111111111111111111111111010", MathUtil.intBin(i));

		// -2147483643
		i = 0b10000000000000000000000000000101;
		assertEquals("10000000000000000000000000000101", MathUtil.intBin(i));

		// 27
		i = 0b11011;
		assertEquals("00000000000000000000000000011011", MathUtil.intBin(i));

		// -27
		i = 0b11111111111111111111111111100101;
		assertEquals("11111111111111111111111111100101", MathUtil.intBin(i));

		// 19
		i = 0b10011;
		assertEquals("00000000000000000000000000010011", MathUtil.intBin(i));

		// -19
		i = 0b11111111111111111111111111101101;
		assertEquals("11111111111111111111111111101101", MathUtil.intBin(i));

		// 60
		i = 0b111100;
		assertEquals("00000000000000000000000000111100", MathUtil.intBin(i));

		// -60
		i = 0b11111111111111111111111111000100;
		assertEquals("11111111111111111111111111000100", MathUtil.intBin(i));

		// 57
		i = 0b111001;
		assertEquals("00000000000000000000000000111001", MathUtil.intBin(i));

		// -57
		i = 0b11111111111111111111111111000111;
		assertEquals("11111111111111111111111111000111", MathUtil.intBin(i));

		// 69
		i = 0b1000101;
		assertEquals("00000000000000000000000001000101", MathUtil.intBin(i));

		// -69
		i = 0b11111111111111111111111110111011;
		assertEquals("11111111111111111111111110111011", MathUtil.intBin(i));

		// 335
		i = 0b101001111;
		assertEquals("00000000000000000000000101001111", MathUtil.intBin(i));

		// -335
		i = 0b11111111111111111111111010110001;
		assertEquals("11111111111111111111111010110001", MathUtil.intBin(i));

		// 543
		i = 0b1000011111;
		assertEquals("00000000000000000000001000011111", MathUtil.intBin(i));

		// -543
		i = 0b11111111111111111111110111100001;
		assertEquals("11111111111111111111110111100001", MathUtil.intBin(i));

		// 484
		i = 0b111100100;
		assertEquals("00000000000000000000000111100100", MathUtil.intBin(i));

		// -484
		i = 0b11111111111111111111111000011100;
		assertEquals("11111111111111111111111000011100", MathUtil.intBin(i));

		// 2578
		i = 0b101000010010;
		assertEquals("00000000000000000000101000010010", MathUtil.intBin(i));

		// -2578
		i = 0b11111111111111111111010111101110;
		assertEquals("11111111111111111111010111101110", MathUtil.intBin(i));

		// 1412
		i = 0b10110000100;
		assertEquals("00000000000000000000010110000100", MathUtil.intBin(i));

		// -1412
		i = 0b11111111111111111111101001111100;
		assertEquals("11111111111111111111101001111100", MathUtil.intBin(i));

		// 9790
		i = 0b10011000111110;
		assertEquals("00000000000000000010011000111110", MathUtil.intBin(i));

		// -9790
		i = 0b11111111111111111101100111000010;
		assertEquals("11111111111111111101100111000010", MathUtil.intBin(i));

		// 5004
		i = 0b1001110001100;
		assertEquals("00000000000000000001001110001100", MathUtil.intBin(i));

		// -5004
		i = 0b11111111111111111110110001110100;
		assertEquals("11111111111111111110110001110100", MathUtil.intBin(i));

		// 21618
		i = 0b101010001110010;
		assertEquals("00000000000000000101010001110010", MathUtil.intBin(i));

		// -21618
		i = 0b11111111111111111010101110001110;
		assertEquals("11111111111111111010101110001110", MathUtil.intBin(i));

		// 328738
		i = 0b1010000010000100010;
		assertEquals("00000000000001010000010000100010", MathUtil.intBin(i));

		// -328738
		i = 0b11111111111110101111101111011110;
		assertEquals("11111111111110101111101111011110", MathUtil.intBin(i));

		// 722715
		i = 0b10110000011100011011;
		assertEquals("00000000000010110000011100011011", MathUtil.intBin(i));

		// -722715
		i = 0b11111111111101001111100011100101;
		assertEquals("11111111111101001111100011100101", MathUtil.intBin(i));

		// 712629
		i = 0b10101101111110110101;
		assertEquals("00000000000010101101111110110101", MathUtil.intBin(i));

		// -712629
		i = 0b11111111111101010010000001001011;
		assertEquals("11111111111101010010000001001011", MathUtil.intBin(i));
	}

	public void testGcd()
	{
		byte b1, b2, b0;
		short s1, s2, s0;
		int i1, i2, i0;
		long l1, l2, l0;
		long[][] arrs = {
			{ 9L, 6L, 3L },
			{ 4L, 8L, 4L },
			{ 3L, 4L, 1L },
			{ 32L, 12L, 4L },
			{ 20L, 8L, 4L },
			{ 20L, 10L, 10L },
			{ 10L, 20L, 10L },
			{ 10L, 10L, 10L },
			null,
			{ 2000L, 1500L, 500L }
		};
		
		int state = 3;
		for(long[] arr : arrs)
		{
			if(arr == null)
			{
				state--;
				continue;
			}
			else
			{
				if(state > 2)
				{
					b1 = (byte) arr[0];
					b2 = (byte) arr[1];
					b0 = (byte) arr[2];
					assertEquals(b0, MathUtil.gcd(b1, b2));
				}
				if(state > 1)
				{
					s1 = (short) arr[0];
					s2 = (short) arr[1];
					s0 = (short) arr[2];
					assertEquals(s0, MathUtil.gcd(s1, s2));
				}
				if(state > 0)
				{
					i1 = (int) arr[0];
					i2 = (int) arr[1];
					i0 = (int) arr[2];
					assertEquals(i0, MathUtil.gcd(i1, i2));
				}

				l1 = arr[0];
				l2 = arr[1];
				l0 = arr[2];
				assertEquals(l0, MathUtil.gcd(l1, l2));
			}
		}
	}

	public void testShortHex()
	{
		// TODO: MathUtil.shortHex(int)
	}

	public void testSquare()
	{
		float f;
		
		for(int i = 0; i < 1000; i++)
		{
			f = rng.nextFloat() * Integer.MAX_VALUE;
			assertEquals(f * f, MathUtil.square(f));
		}

		double d;
		
		for(int i = 0; i < 1000; i++)
		{
			d = rng.nextDouble() * Long.MAX_VALUE;
			assertEquals(d * d, MathUtil.square(d));
		}

		int n;
		
		for(int i = 0; i < 1000; i++)
		{
			n = rng.nextInt();
			assertEquals(n * n, MathUtil.square(n));
		}

		long l;
		
		for(int i = 0; i < 1000; i++)
		{
			l = rng.nextLong();
			assertEquals(l * l, MathUtil.square(l));
		}
	}

	public void testByteBin()
	{
		// TODO: MathUtil.byteBin(int)
	}

	public void testHypot()
	{
		int[] as = {
			3, 6, 5, 9, 8, 12, 15, 7, 10, 20, 18,
			16, 21, 12, 15, 24, 9, 27, 30, 14,
			24, 20, 28, 33, 40, 36, 11, 39, 33,
			25, 16, 32, 42, 48, 24, 45, 21, 30,
			48, 18, 51, 40, 36, 13, 60, 39, 54,
			35, 57, 65, 60, 28, 20, 48, 40, 63,
			56, 60, 66, 36, 15, 69, 80, 45, 56, 72
		};
		int[] bs = {
			4, 8, 12, 12, 15, 16, 20, 24, 24, 21,
			24, 30, 28, 35, 36, 32, 40, 36, 40,
			48, 45, 48, 45, 44, 42, 48, 60, 52,
			56, 60, 63, 60, 56, 55, 70, 60, 72,
			72, 64, 80, 68, 75, 77, 84, 63, 80,
			72, 84, 76, 72, 80, 96, 99, 90, 96,
			84, 90, 91, 88, 105, 112, 92, 84,
			108, 105, 96
		};
		int[] cs = {
			5, 10, 13, 15, 17, 20, 25, 25, 26,
			29, 30, 34, 35, 37, 39, 40, 41, 45,
			50, 50, 51, 52, 53, 55, 58, 60, 61,
			65, 65, 65, 65, 68, 70, 73, 74, 75,
			75, 78, 80, 82, 85, 85, 85, 85, 87,
			89, 90, 91, 95, 97, 100, 100, 101,
			102, 104, 105, 106, 109, 110, 111,
			113, 115, 116, 117, 119, 120
		};
		int size = 66;

		for(int i = 0; i < size; i++)
		{
			assertEquals((float) cs[i], MathUtil.hypot((float) as[i], (float) bs[i]));
			assertEquals((double) cs[i], MathUtil.hypot((double) as[i], (double) bs[i]));
		}
	}

	public void testSigmoid()
	{
		assertEquals(0.5D, MathUtil.sigmoid(0D));
		assertEquals(0.5F, MathUtil.sigmoid(0F));
	}

	public void testLcm()
	{
		byte b1, b2, b0;
		short s1, s2, s0;
		int i1, i2, i0;
		long l1, l2, l0;
		long[][] arrs = {
			{ 6L, 7L, 42L },
			{ 4L, 6L, 12L },
			{ 12L, 30L, 60L },
			{ 3L, 5L, 15L },
			{ 4L, 5L, 20L },
			{ 6L, 15L, 30L },
			null,
			{ 24L, 300L, 600L },
		};
		
		int state = 3;
		for(long[] arr : arrs)
		{
			if(arr == null)
			{
				state--;
				continue;
			}
			else
			{
				if(state > 2)
				{
					b1 = (byte) arr[0];
					b2 = (byte) arr[1];
					b0 = (byte) arr[2];
					assertEquals(b0, MathUtil.lcm(b1, b2));
				}
				if(state > 1)
				{
					s1 = (short) arr[0];
					s2 = (short) arr[1];
					s0 = (short) arr[2];
					assertEquals(s0, MathUtil.lcm(s1, s2));
				}
				if(state > 0)
				{
					i1 = (int) arr[0];
					i2 = (int) arr[1];
					i0 = (int) arr[2];
					assertEquals(i0, MathUtil.lcm(i1, i2));
				}

				l1 = arr[0];
				l2 = arr[1];
				l0 = arr[2];
				assertEquals(l0, MathUtil.lcm(l1, l2));
			}
		}
	}

	public void testLongHex()
	{
		// TODO: MathUtil.longHex(long, int)

		// TODO: MathUtil.longHex(long)
	}

	public void testCube()
	{
		float f;
		
		for(int i = 0; i < 1000; i++)
		{
			f = rng.nextFloat() * Integer.MAX_VALUE;
			assertEquals(f * f * f, MathUtil.cube(f));
		}

		double d;
		
		for(int i = 0; i < 1000; i++)
		{
			d = rng.nextDouble() * Long.MAX_VALUE;
			assertEquals(d * d * d, MathUtil.cube(d));
		}

		int n;
		
		for(int i = 0; i < 1000; i++)
		{
			n = rng.nextInt();
			assertEquals(n * n * n, MathUtil.cube(n));
		}

		long l;
		
		for(int i = 0; i < 1000; i++)
		{
			l = rng.nextLong();
			assertEquals(l * l * l, MathUtil.cube(l));
		}
	}

	public void testIntHex()
	{
		// TODO: MathUtil.intHex(int)
	}

	public void testMap()
	{
		assertEquals(1D, MathUtil.map(0D, 0D, 1D, 1D, 2D));
		assertEquals(2D, MathUtil.map(1D, 0D, 1D, 1D, 2D));
		assertEquals(1.5D, MathUtil.map(0.5D, 0D, 1D, 1D, 2D));
		// TODO: MathUtil.map(double, double, double, double, double)

		// TODO: MathUtil.map(float, float, float, float, float)

		// TODO: MathUtil.map(int, int, int, int, int)
		
		assertEquals(50, MathUtil.map(6, 3, 9, 25, 75));
	}

	public void testByteHex()
	{
		// TODO: MathUtil.byteHex(int)
	}

	public void testShortBin()
	{
		// TODO: MathUtil.shortBin(int)
	}

	public void testBetween()
	{
		// TODO: MathUtil.between(double, double, double)

		// TODO: MathUtil.between(float, float, float)

		// TODO: MathUtil.between(int, int, int)

		// TODO: MathUtil.between(long, long, long)

		// TODO: MathUtil.between(byte, byte, byte)

		// TODO: MathUtil.between(short, short, short)
	}

	public void testDeterminant()
	{
		// TODO: MathUtil.determinant(double, double, double, double)

		// TODO: MathUtil.determinant(float, float, float, float)
	}

	@Override
	public void tearDown()
	{
		// TODO: create or delete
	}
}