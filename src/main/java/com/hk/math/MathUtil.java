package com.hk.math;

import com.hk.util.Requirements;

public class MathUtil
{
	//	private static Point2D getCross(Line2D lineA, Line2D lineB)
	//	{
	//		double x1 = lineA.getX1();
	//		double x2 = lineA.getX2();
	//		double y1 = lineA.getY1();
	//		double y2 = lineA.getY2();
	//		double x3 = lineB.getX1();
	//		double x4 = lineB.getX2();
	//		double y3 = lineB.getY1();
	//		double y4 = lineB.getY2();
	//		double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
	//		if (d != 0)
	//		{
	//			double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
	//			double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;
	//			return new Point2D.Double(xi, yi);
	//		}
	//		return null;
	//	}
	public static double deg2rad = Math.PI / 180;
	public static double rad2deg = 180 / Math.PI;
	public static float deg2radF = (float) deg2rad;
	public static float rad2degF = (float) rad2deg;
	
	public static double cube(double a)
	{
		return a * a * a;
	}

	public static float cube(float a)
	{
		return a * a * a;
	}

	public static int cube(int a)
	{
		return a * a * a;
	}

	public static long cube(long a)
	{
		return a * a * a;
	}

	public static double determinant(double m00, double m01, double m10, double m11)
	{
		return m00 * m11 - m10 * m01;
	}

	public static float determinant(float m00, float m01, float m10, float m11)
	{
		return m00 * m11 - m10 * m01;
	}

	public static double hypot(double a, double b)
	{
		return Math.sqrt(a * a + b * b);
	}

	public static float hypot(float a, float b)
	{
		return (float) Math.sqrt(a * a + b * b);
	}

	public static double log(double a, double base)
	{
		return Math.log10(a) / Math.log10(base);
	}

	public static float log(float a, float base)
	{
		return (float) (Math.log10(a) / Math.log10(base));
	}

	public static double square(double a)
	{
		return a * a;
	}

	public static float square(float a)
	{
		return a * a;
	}

	public static int square(int a)
	{
		return a * a;
	}

	public static long square(long a)
	{
		return a * a;
	}

	public static double sign(double a)
	{
		return a < 0D ? -1 : a > 0D ? 1 : 0D;
	}

	public static float sign(float a)
	{
		return a < 0F ? -1 : a > 0F ? 1 : 0F;
	}

	public static int sign(int a)
	{
		return a < 0 ? -1 : a > 0 ? 1 : 0;
	}

	public static long sign(long a)
	{
		return a < 0L ? -1L : a > 0L ? 1L : 0L;
	}

	public static short sign(short a)
	{
		return (short) (a < 0 ? -1 : a > 0 ? 1 : 0);
	}

	public static byte sign(byte a)
	{
		return (byte) (a < 0 ? -1 : a > 0 ? 1 : 0);
	}
	
	public static double between(double min, double val, double max)
	{
		return val > max ? max : val < min ? min : val;
	}
	
	public static float between(float min, float val, float max)
	{
		return val > max ? max : val < min ? min : val;
	}
	
	public static int between(int min, int val, int max)
	{
		return val > max ? max : val < min ? min : val;
	}
	
	public static long between(long min, long val, long max)
	{
		return val > max ? max : val < min ? min : val;
	}
	
	public static short between(short min, short val, short max)
	{
		return val > max ? max : val < min ? min : val;
	}
	
	public static byte between(byte min, byte val, byte max)
	{
		return val > max ? max : val < min ? min : val;
	}

	public static long gcd(long a, long b)
	{
		long r = a;
		a = Math.max(a, b);
		b = Math.min(r, b);

		r = b;
		while (a % b != 0)
		{
			r = a % b;
			a = b;
			b = r;
		}
		return r;
	}

	public static int gcd(int a, int b)
	{
		return (int) gcd((long) a, (long) b);
	}

	public static short gcd(short a, short b)
	{
		return (short) gcd((long) a, (long) b);
	}

	public static byte gcd(byte a, byte b)
	{
		return (byte) gcd((long) a, (long) b);
	}

	public static long lcm(long a, long b)
	{
		return a * b / gcd(a, b);
	}

	public static int lcm(int a, int b)
	{
		return (int) lcm((long) a, (long) b);
	}

	public static short lcm(short a, short b)
	{
		return (short) lcm((long) a, (long) b);
	}

	public static byte lcm(byte a, byte b)
	{
		return (byte) lcm((long) a, (long) b);
	}

	public static double lerp(double a, double b, double amt)
	{
		amt = between(0, amt, 1);
		return a * amt + b * (1D - amt);
	}

	public static float lerp(float a, float b, float amt)
	{
		amt = between(0, amt, 1);
		return a * amt + b * (1F - amt);
	}
	
	public static String byteHex(int num)
	{
		return longHex(num | 0L, 2);
	}
	
	public static String shortHex(int num)
	{
		return longHex(num | 0L, 4);
	}
	
	public static String intHex(int num)
	{
		return longHex(num | 0L, 8);
	}
	
	public static String longHex(long num)
	{
		return longHex(num, 16);
	}
	
	public static String longHex(long num, int amt)
	{
		char[] ns = "0123456789ABCDEF".toCharArray();
		char[] cs = new char[Requirements.requireInBounds(0, amt, 16)];
		for(int i = 0; i < amt; i++)
		{
			cs[amt - i - 1] = ns[(int) ((num >> 4L * i) & 15)];
		}
		return new String(cs);
	}
	
	public static String byteBin(int num)
	{
		return longBin(num | 0L, 8);
	}
	
	public static String shortBin(int num)
	{
		return longBin(num | 0L, 16);
	}
	
	public static String intBin(int num)
	{
		return longBin(num | 0L, 32);
	}
	
	public static String longBin(long num)
	{
		return longBin(num, 64);
	}
	
	public static String longBin(long num, int amt)
	{
		char[] cs = new char[Requirements.requireInBounds(0, amt, 64)];
		for(int i = 0; i < amt; i++)
		{
			cs[amt - i - 1] = ((num >> i) & 1) == 0 ? '0' : '1';
		}
		return new String(cs);
	}

	public static float max(float... fs)
	{
		float max = 0;
		if(fs.length > 0)
		{
			max = fs[0];
			for(float f : fs)
			{
				if(f > max)
					max = f;
			}
		}
		return max;
	}

	public static float min(float... fs)
	{
		float min = 0;
		if(fs.length > 0)
		{
			min = fs[0];
			for(float f : fs)
			{
				if(f < min)
					min = f;
			}
		}
		return min;
	}

	public static double max(double... ds)
	{
		double max = 0;
		if(ds.length > 0)
		{
			max = ds[0];
			for(double d : ds)
			{
				if(d > max)
					max = d;
			}
		}
		return max;
	}

	public static double min(double... ds)
	{
		double min = 0;
		if(ds.length > 0)
		{
			min = ds[0];
			for(double d : ds)
			{
				if(d < min)
					min = d;
			}
		}
		return min;
	}

	public static double sigmoid(double t)
	{
		return 1D / (1D + Math.exp(-t));
	}

	public static float sigmoid(float t)
	{
		return 1F / (1F + FloatMath.exp(-t));
	}
	
	public static float map(float val, float srcMin, float srcMax, float dstMin, float dstMax)
	{
		return (val - srcMin) / (srcMax - srcMin) * (dstMax - dstMin) + dstMin;
	}
	
	public static double map(double val, double srcMin, double srcMax, double dstMin, double dstMax)
	{
		return (val - srcMin) / (srcMax - srcMin) * (dstMax - dstMin) + dstMin;
	}
	
	public static int map(int val, int srcMin, int srcMax, int dstMin, int dstMax)
	{
		return (int) map((float) val, srcMin, srcMax, dstMin, dstMax);
	}

	private MathUtil()
	{}
}
