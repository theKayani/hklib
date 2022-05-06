package com.hk.lua;

import com.hk.Assets;
import com.hk.math.MathUtil;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.util.Objects;

public class LuaLibraryReflectTest extends TestCase
{
	public void test() throws FileNotFoundException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/library_reflect.lua"));

		Lua.importStandard(interp);
		interp.importLib(LuaLibrary.REFLECT);

		Object obj = interp.execute();

		assertTrue(obj instanceof LuaObject);
		assertTrue(((LuaObject) obj).getBoolean());
	}

	public static class Point
	{
		public static long magic = 4321234;
		public static final Point ZERO = new Point(0, 0);
		public double x;
		public double y;

		public Point()
		{
			x = y = 0;
		}

		public Point(double x, double y)
		{
			this.x = x;
			this.y = y;
		}

		public Point(Point p)
		{
			this.x = p.x;
			this.y = p.y;
		}

		public double getX()
		{
			return x;
		}

		public double getY()
		{
			return y;
		}

		public Point set(double x, double y)
		{
			this.x = x;
			this.y = y;
			return this;
		}

		public Point set(Point p)
		{
			this.x = p.x;
			this.y = p.y;
			return this;
		}

		public Point setX(double x)
		{
			this.x = x;
			return this;
		}

		public void setY(double y)
		{
			this.y = y;
		}

		public int overloaded(String str)
		{
			Objects.requireNonNull(str);
			return 1;
		}

		public int overloaded(double d)
		{
			return 0;
		}

		public int overloaded(Point pt)
		{
			Objects.requireNonNull(pt);
			return -1;
		}

		public static int[] numbers(int n)
		{
			int[] ds = new int[n];

			for (int i = 0; i < n; i++)
				ds[i] = n - i;

			return ds;
		}

		public static double distance(double x1, double y1, double x2, double y2)
		{
			return MathUtil.hypot(x2 - x1, y2 - y1);
		}

		public static double distanceSq(double x1, double y1, double x2, double y2)
		{
			return MathUtil.hypotSq(x2 - x1, y2 - y1);
		}

		@Override
		public boolean equals(Object o)
		{
			return o instanceof Point && ((Point) o).x == x && ((Point) o).y == y;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(x, y);
		}

		@Override
		public String toString()
		{
			return String.format("Point{x=%s, y=%s}", x, y);
		}
	}
}