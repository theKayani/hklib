package com.hk.math;

import com.hk.util.Requirements;

/**
 * <p>MathUtil class.</p>
 *
 * @author theKayani
 */
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
	/** Constant <code>deg2rad=Math.PI / 180</code> */
	public static final double deg2rad = Math.PI / 180;
	/** Constant <code>rad2deg=180 / Math.PI</code> */
	public static final double rad2deg = 180 / Math.PI;
	/** Constant <code>deg2radF=(float) deg2rad</code> */
	public static final float deg2radF = (float) deg2rad;
	/** Constant <code>rad2degF=(float) rad2deg</code> */
	public static final float rad2degF = (float) rad2deg;
	
	/**
	 * <p>cube.</p>
	 *
	 * @param a a double
	 * @return a double
	 */
	public static double cube(double a)
	{
		return a * a * a;
	}

	/**
	 * <p>cube.</p>
	 *
	 * @param a a float
	 * @return a float
	 */
	public static float cube(float a)
	{
		return a * a * a;
	}

	/**
	 * <p>cube.</p>
	 *
	 * @param a a int
	 * @return a int
	 */
	public static int cube(int a)
	{
		return a * a * a;
	}

	/**
	 * <p>cube.</p>
	 *
	 * @param a a long
	 * @return a long
	 */
	public static long cube(long a)
	{
		return a * a * a;
	}

	/**
	 * <p>determinant.</p>
	 *
	 * @param m00 a double
	 * @param m01 a double
	 * @param m10 a double
	 * @param m11 a double
	 * @return a double
	 */
	public static double determinant(double m00, double m01, double m10, double m11)
	{
		return m00 * m11 - m10 * m01;
	}

	/**
	 * <p>determinant.</p>
	 *
	 * @param m00 a float
	 * @param m01 a float
	 * @param m10 a float
	 * @param m11 a float
	 * @return a float
	 */
	public static float determinant(float m00, float m01, float m10, float m11)
	{
		return m00 * m11 - m10 * m01;
	}

	/**
	 * <p>hypot.</p>
	 *
	 * @param a a double
	 * @param b a double
	 * @return a double
	 */
	public static double hypot(double a, double b)
	{
		return Math.sqrt(a * a + b * b);
	}

	/**
	 * <p>hypotSq.</p>
	 *
	 * @param a a double
	 * @param b a double
	 * @return a double
	 */
	public static double hypotSq(double a, double b)
	{
		return a * a + b * b;
	}

	/**
	 * <p>hypot.</p>
	 *
	 * @param a a float
	 * @param b a float
	 * @return a float
	 */
	public static float hypot(float a, float b)
	{
		return (float) Math.sqrt(a * a + b * b);
	}

	/**
	 * <p>hypot.</p>
	 *
	 * @param a a float
	 * @param b a float
	 * @return a float
	 */
	public static float hypotSq(float a, float b)
	{
		return a * a + b * b;
	}

	/**
	 * <p>log.</p>
	 *
	 * @param a a double
	 * @param base a double
	 * @return a double
	 */
	public static double log(double a, double base)
	{
		return Math.log10(a) / Math.log10(base);
	}

	/**
	 * <p>log.</p>
	 *
	 * @param a a float
	 * @param base a float
	 * @return a float
	 */
	public static float log(float a, float base)
	{
		return (float) (Math.log10(a) / Math.log10(base));
	}

	/**
	 * <p>square.</p>
	 *
	 * @param a a double
	 * @return a double
	 */
	public static double square(double a)
	{
		return a * a;
	}

	/**
	 * <p>square.</p>
	 *
	 * @param a a float
	 * @return a float
	 */
	public static float square(float a)
	{
		return a * a;
	}

	/**
	 * <p>square.</p>
	 *
	 * @param a a int
	 * @return a int
	 */
	public static int square(int a)
	{
		return a * a;
	}

	/**
	 * <p>square.</p>
	 *
	 * @param a a long
	 * @return a long
	 */
	public static long square(long a)
	{
		return a * a;
	}

	/**
	 * <p>sign.</p>
	 *
	 * @param a a double
	 * @return a double
	 */
	public static double sign(double a)
	{
		return a < 0D ? -1 : a > 0D ? 1 : 0D;
	}

	/**
	 * <p>sign.</p>
	 *
	 * @param a a float
	 * @return a float
	 */
	public static float sign(float a)
	{
		return a < 0F ? -1 : a > 0F ? 1 : 0F;
	}

	/**
	 * <p>sign.</p>
	 *
	 * @param a a int
	 * @return a int
	 */
	public static int sign(int a)
	{
		return Integer.compare(a, 0);
	}

	/**
	 * <p>sign.</p>
	 *
	 * @param a a long
	 * @return a long
	 */
	public static long sign(long a)
	{
		return a < 0L ? -1L : a > 0L ? 1L : 0L;
	}

	/**
	 * <p>sign.</p>
	 *
	 * @param a a short
	 * @return a short
	 */
	public static short sign(short a)
	{
		return (short) (a < 0 ? -1 : a > 0 ? 1 : 0);
	}

	/**
	 * <p>sign.</p>
	 *
	 * @param a a byte
	 * @return a byte
	 */
	public static byte sign(byte a)
	{
		return (byte) (a < 0 ? -1 : a > 0 ? 1 : 0);
	}
	
	/**
	 * <p>between.</p>
	 *
	 * @param min a double
	 * @param val a double
	 * @param max a double
	 * @return a double
	 */
	public static double between(double min, double val, double max)
	{
		return val > max ? max : Math.max(val, min);
	}
	
	/**
	 * <p>between.</p>
	 *
	 * @param min a float
	 * @param val a float
	 * @param max a float
	 * @return a float
	 */
	public static float between(float min, float val, float max)
	{
		return val > max ? max : Math.max(val, min);
	}
	
	/**
	 * <p>between.</p>
	 *
	 * @param min a int
	 * @param val a int
	 * @param max a int
	 * @return a int
	 */
	public static int between(int min, int val, int max)
	{
		return val > max ? max : Math.max(val, min);
	}
	
	/**
	 * <p>between.</p>
	 *
	 * @param min a long
	 * @param val a long
	 * @param max a long
	 * @return a long
	 */
	public static long between(long min, long val, long max)
	{
		return val > max ? max : Math.max(val, min);
	}
	
	/**
	 * <p>between.</p>
	 *
	 * @param min a short
	 * @param val a short
	 * @param max a short
	 * @return a short
	 */
	public static short between(short min, short val, short max)
	{
		return val > max ? max : val < min ? min : val;
	}
	
	/**
	 * <p>between.</p>
	 *
	 * @param min a byte
	 * @param val a byte
	 * @param max a byte
	 * @return a byte
	 */
	public static byte between(byte min, byte val, byte max)
	{
		return val > max ? max : val < min ? min : val;
	}

	/**
	 * <p>gcd.</p>
	 *
	 * @param a a long
	 * @param b a long
	 * @return a long
	 */
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

	/**
	 * <p>gcd.</p>
	 *
	 * @param a a int
	 * @param b a int
	 * @return a int
	 */
	public static int gcd(int a, int b)
	{
		return (int) gcd(a, (long) b);
	}

	/**
	 * <p>gcd.</p>
	 *
	 * @param a a short
	 * @param b a short
	 * @return a short
	 */
	public static short gcd(short a, short b)
	{
		return (short) gcd(a, (long) b);
	}

	/**
	 * <p>gcd.</p>
	 *
	 * @param a a byte
	 * @param b a byte
	 * @return a byte
	 */
	public static byte gcd(byte a, byte b)
	{
		return (byte) gcd(a, (long) b);
	}

	/**
	 * <p>lcm.</p>
	 *
	 * @param a a long
	 * @param b a long
	 * @return a long
	 */
	public static long lcm(long a, long b)
	{
		return a * b / gcd(a, b);
	}

	/**
	 * <p>lcm.</p>
	 *
	 * @param a a int
	 * @param b a int
	 * @return a int
	 */
	public static int lcm(int a, int b)
	{
		return (int) lcm(a, (long) b);
	}

	/**
	 * <p>lcm.</p>
	 *
	 * @param a a short
	 * @param b a short
	 * @return a short
	 */
	public static short lcm(short a, short b)
	{
		return (short) lcm(a, (long) b);
	}

	/**
	 * <p>lcm.</p>
	 *
	 * @param a a byte
	 * @param b a byte
	 * @return a byte
	 */
	public static byte lcm(byte a, byte b)
	{
		return (byte) lcm(a, (long) b);
	}

	/**
	 * <p>lerp.</p>
	 *
	 * @param a a double
	 * @param b a double
	 * @param amt a double
	 * @return a double
	 */
	public static double lerp(double a, double b, double amt)
	{
		amt = between(0, amt, 1);
		return a * amt + b * (1D - amt);
	}

	/**
	 * <p>lerp.</p>
	 *
	 * @param a a float
	 * @param b a float
	 * @param amt a float
	 * @return a float
	 */
	public static float lerp(float a, float b, float amt)
	{
		amt = between(0, amt, 1);
		return a * amt + b * (1F - amt);
	}
	
	/**
	 * <p>byteHex.</p>
	 *
	 * @param num a int
	 * @return a {@link java.lang.String} object
	 */
	public static String byteHex(int num)
	{
		return longHex(num, 2);
	}
	
	/**
	 * <p>shortHex.</p>
	 *
	 * @param num a int
	 * @return a {@link java.lang.String} object
	 */
	public static String shortHex(int num)
	{
		return longHex(num, 4);
	}
	
	/**
	 * <p>intHex.</p>
	 *
	 * @param num a int
	 * @return a {@link java.lang.String} object
	 */
	public static String intHex(int num)
	{
		return longHex(num, 8);
	}
	
	/**
	 * <p>longHex.</p>
	 *
	 * @param num a long
	 * @return a {@link java.lang.String} object
	 */
	public static String longHex(long num)
	{
		return longHex(num, 16);
	}
	
	/**
	 * <p>longHex.</p>
	 *
	 * @param num a long
	 * @param amt a int
	 * @return a {@link java.lang.String} object
	 */
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
	
	/**
	 * <p>byteBin.</p>
	 *
	 * @param num a int
	 * @return a {@link java.lang.String} object
	 */
	public static String byteBin(int num)
	{
		return longBin(num, 8);
	}
	
	/**
	 * <p>shortBin.</p>
	 *
	 * @param num a int
	 * @return a {@link java.lang.String} object
	 */
	public static String shortBin(int num)
	{
		return longBin(num, 16);
	}
	
	/**
	 * <p>intBin.</p>
	 *
	 * @param num a int
	 * @return a {@link java.lang.String} object
	 */
	public static String intBin(int num)
	{
		return longBin(num, 32);
	}
	
	/**
	 * <p>longBin.</p>
	 *
	 * @param num a long
	 * @return a {@link java.lang.String} object
	 */
	public static String longBin(long num)
	{
		return longBin(num, 64);
	}
	
	/**
	 * <p>longBin.</p>
	 *
	 * @param num a long
	 * @param amt a int
	 * @return a {@link java.lang.String} object
	 */
	public static String longBin(long num, int amt)
	{
		char[] cs = new char[Requirements.requireInBounds(0, amt, 64)];
		for(int i = 0; i < amt; i++)
		{
			cs[amt - i - 1] = ((num >> i) & 1) == 0 ? '0' : '1';
		}
		return new String(cs);
	}

	/**
	 * <p>max.</p>
	 *
	 * @param fs a float
	 * @return a float
	 */
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

	/**
	 * <p>min.</p>
	 *
	 * @param fs a float
	 * @return a float
	 */
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

	/**
	 * <p>max.</p>
	 *
	 * @param ds a double
	 * @return a double
	 */
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

	/**
	 * <p>min.</p>
	 *
	 * @param ds a double
	 * @return a double
	 */
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

	/**
	 * <p>sigmoid.</p>
	 *
	 * @param t a double
	 * @return a double
	 */
	public static double sigmoid(double t)
	{
		return 1D / (1D + Math.exp(-t));
	}

	/**
	 * <p>sigmoid.</p>
	 *
	 * @param t a float
	 * @return a float
	 */
	public static float sigmoid(float t)
	{
		return 1F / (1F + FloatMath.exp(-t));
	}
	
	/**
	 * <p>map.</p>
	 *
	 * @param val a float
	 * @param srcMin a float
	 * @param srcMax a float
	 * @param dstMin a float
	 * @param dstMax a float
	 * @return a float
	 */
	public static float map(float val, float srcMin, float srcMax, float dstMin, float dstMax)
	{
		return (val - srcMin) / (srcMax - srcMin) * (dstMax - dstMin) + dstMin;
	}
	
	/**
	 * <p>map.</p>
	 *
	 * @param val a double
	 * @param srcMin a double
	 * @param srcMax a double
	 * @param dstMin a double
	 * @param dstMax a double
	 * @return a double
	 */
	public static double map(double val, double srcMin, double srcMax, double dstMin, double dstMax)
	{
		return (val - srcMin) / (srcMax - srcMin) * (dstMax - dstMin) + dstMin;
	}
	
	/**
	 * <p>map.</p>
	 *
	 * @param val a int
	 * @param srcMin a int
	 * @param srcMax a int
	 * @param dstMin a int
	 * @param dstMax a int
	 * @return a int
	 */
	public static int map(int val, int srcMin, int srcMax, int dstMin, int dstMax)
	{
		return (int) map((float) val, srcMin, srcMax, dstMin, dstMax);
	}

	private MathUtil()
	{}
}
