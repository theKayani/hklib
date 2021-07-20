package com.hk.math.vector;

import java.io.Serializable;

/**
 * <p>Point class.</p>
 *
 * @author theKayani
 */
public final class Point implements Serializable, Cloneable
{
	public int x, y;

	/**
	 * <p>Constructor for Point.</p>
	 */
	public Point()
	{
		x = y = 0;
	}

	/**
	 * <p>Constructor for Point.</p>
	 *
	 * @param val a int
	 */
	public Point(int val)
	{
		x = y = val;
	}

	/**
	 * <p>Constructor for Point.</p>
	 *
	 * @param x a int
	 * @param y a int
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * <p>Constructor for Point.</p>
	 *
	 * @param p a {@link com.hk.math.vector.Point} object
	 */
	public Point(Point p)
	{
		x = p.x;
		y = p.y;
	}

	/**
	 * <p>Getter for the field <code>x</code>.</p>
	 *
	 * @return a int
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * <p>Getter for the field <code>y</code>.</p>
	 *
	 * @return a int
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * <p>get.</p>
	 *
	 * @param arr an array of {@link int} objects
	 * @param off a int
	 * @return an array of {@link int} objects
	 */
	public int[] get(int[] arr, int off)
	{
		arr[off++] = x;
		arr[off] = y;
		return arr;
	}
	
	/**
	 * <p>get.</p>
	 *
	 * @param arr an array of {@link int} objects
	 * @return an array of {@link int} objects
	 */
	public int[] get(int[] arr)
	{
		return get(arr, 0);
	}

	/**
	 * <p>get.</p>
	 *
	 * @return an array of {@link int} objects
	 */
	public int[] get()
	{
		return get(new int[2]);
	}
	
	/**
	 * <p>Setter for the field <code>x</code>.</p>
	 *
	 * @param x a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point setX(int x)
	{
		this.x = x;
		return this;
	}

	/**
	 * <p>Setter for the field <code>y</code>.</p>
	 *
	 * @param y a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point setY(int y)
	{
		this.y = y;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param val a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point set(int val)
	{
		x = y = val;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param x a int
	 * @param y a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point set(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param p a {@link com.hk.math.vector.Point} object
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point set(Point p)
	{
		x = p.x;
		y = p.y;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arr an array of {@link int} objects
	 * @param off a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point set(int[] arr, int off)
	{
		x = arr[off++];
		y = arr[off];
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arr an array of {@link int} objects
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point set(int[] arr)
	{
		return set(arr, 0);
	}
	
	/**
	 * <p>add.</p>
	 *
	 * @param x a int
	 * @param y a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point add(int x, int y)
	{
		this.x += x;
		this.y += y;
		return this;
	}
	
	/**
	 * <p>add.</p>
	 *
	 * @param val a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point add(int val)
	{
		x += val;
		y += val;
		return this;
	}
	
	/**
	 * <p>add.</p>
	 *
	 * @param p a {@link com.hk.math.vector.Point} object
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point add(Point p)
	{
		x += p.x;
		y += p.y;
		return this;
	}
	
	/**
	 * <p>sub.</p>
	 *
	 * @param x a int
	 * @param y a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point sub(int x, int y)
	{
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	/**
	 * <p>sub.</p>
	 *
	 * @param val a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point sub(int val)
	{
		x -= val;
		y -= val;
		return this;
	}
	
	/**
	 * <p>sub.</p>
	 *
	 * @param p a {@link com.hk.math.vector.Point} object
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point sub(Point p)
	{
		x -= p.x;
		y -= p.y;
		return this;
	}
	
	/**
	 * <p>mult.</p>
	 *
	 * @param x a int
	 * @param y a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point mult(int x, int y)
	{
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	/**
	 * <p>mult.</p>
	 *
	 * @param val a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point mult(int val)
	{
		x *= val;
		y *= val;
		return this;
	}
	
	/**
	 * <p>mult.</p>
	 *
	 * @param p a {@link com.hk.math.vector.Point} object
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point mult(Point p)
	{
		x *= p.x;
		y *= p.y;
		return this;
	}
	
	/**
	 * <p>div.</p>
	 *
	 * @param x a int
	 * @param y a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point div(int x, int y)
	{
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	/**
	 * <p>div.</p>
	 *
	 * @param val a int
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point div(int val)
	{
		x /= val;
		y /= val;
		return this;
	}
	
	/**
	 * <p>div.</p>
	 *
	 * @param p a {@link com.hk.math.vector.Point} object
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point div(Point p)
	{
		x /= p.x;
		y /= p.y;
		return this;
	}
	
	/**
	 * <p>negate.</p>
	 *
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point negate()
	{
		x = -x;
		y = -y;
		return this;
	}
	
	/**
	 * <p>negative.</p>
	 *
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point negative()
	{
		return abs().negate();
	}
	
	/**
	 * <p>abs.</p>
	 *
	 * @return a {@link com.hk.math.vector.Point} object
	 */
	public Point abs()
	{
		x = Math.abs(x);
		y = Math.abs(y);
		return this;
	}
	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
	/** {@inheritDoc} */
	public boolean equals(Object o)
	{
		return o instanceof Point && ((Point) o).x == x && ((Point) o).y == y;
	}
	
	/**
	 * <p>hashCode.</p>
	 *
	 * @return a int
	 */
	public int hashCode()
	{
		int hash = 31;
		hash = 17 * hash + x;
		hash = 17 * hash + y;
		return hash;
	}
	
	/** {@inheritDoc} */
	@Override
	public Point clone()
	{
		return new Point(this);
	}

	private static final long serialVersionUID = -2476514875223896181L;
}
