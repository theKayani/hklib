package com.hk.math.vector;

import java.io.Serializable;

public final class Point implements Serializable, Cloneable
{
	public int x, y;

	public Point()
	{
		x = y = 0;
	}

	public Point(int val)
	{
		x = y = val;
	}

	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Point(Point p)
	{
		x = p.x;
		y = p.y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	
	public int[] get(int[] arr, int off)
	{
		arr[off++] = x;
		arr[off] = y;
		return arr;
	}
	
	public int[] get(int[] arr)
	{
		return get(arr, 0);
	}

	public int[] get()
	{
		return get(new int[2]);
	}
	
	public Point setX(int x)
	{
		this.x = x;
		return this;
	}

	public Point setY(int y)
	{
		this.y = y;
		return this;
	}

	public Point set(int val)
	{
		x = y = val;
		return this;
	}

	public Point set(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public Point set(Point p)
	{
		x = p.x;
		y = p.y;
		return this;
	}

	public Point set(int[] arr, int off)
	{
		x = arr[off++];
		y = arr[off];
		return this;
	}

	public Point set(int[] arr)
	{
		return set(arr, 0);
	}
	
	public Point add(int x, int y)
	{
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Point add(int val)
	{
		x += val;
		y += val;
		return this;
	}
	
	public Point add(Point p)
	{
		x += p.x;
		y += p.y;
		return this;
	}
	
	public Point sub(int x, int y)
	{
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Point sub(int val)
	{
		x -= val;
		y -= val;
		return this;
	}
	
	public Point sub(Point p)
	{
		x -= p.x;
		y -= p.y;
		return this;
	}
	
	public Point mult(int x, int y)
	{
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public Point mult(int val)
	{
		x *= val;
		y *= val;
		return this;
	}
	
	public Point mult(Point p)
	{
		x *= p.x;
		y *= p.y;
		return this;
	}
	
	public Point div(int x, int y)
	{
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public Point div(int val)
	{
		x /= val;
		y /= val;
		return this;
	}
	
	public Point div(Point p)
	{
		x /= p.x;
		y /= p.y;
		return this;
	}
	
	public Point negate()
	{
		x = -x;
		y = -y;
		return this;
	}
	
	public Point negative()
	{
		return abs().negate();
	}
	
	public Point abs()
	{
		x = Math.abs(x);
		y = Math.abs(y);
		return this;
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
	public boolean equals(Object o)
	{
		return o instanceof Point && ((Point) o).x == x && ((Point) o).y == y;
	}
	
	public int hashCode()
	{
		int hash = 31;
		hash = 17 * hash + x;
		hash = 17 * hash + y;
		return hash;
	}
	
	@Override
	public Point clone()
	{
		return new Point(this);
	}

	private static final long serialVersionUID = -2476514875223896181L;
}
