package com.hk.math.shape;

import com.hk.math.FloatMath;

/**
 * <p>Circle class.</p>
 *
 * @author theKayani
 */
public class Circle extends Shape
{
	public float x, y, radius;

	/**
	 * <p>Constructor for Circle.</p>
	 */
	public Circle()
	{
		x = y = radius = 0F;
	}

	/**
	 * <p>Constructor for Circle.</p>
	 *
	 * @param radius a float
	 */
	public Circle(float radius)
	{
		this.radius = radius;
	}

	/**
	 * <p>Constructor for Circle.</p>
	 *
	 * @param x a float
	 * @param y a float
	 */
	public Circle(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * <p>Constructor for Circle.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param radius a float
	 */
	public Circle(float x, float y, float radius)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	/**
	 * <p>getCircumference.</p>
	 *
	 * @return a float
	 */
	public float getCircumference()
	{
		return 2 * FloatMath.PI * radius;
	}

	/**
	 * <p>getArea.</p>
	 *
	 * @return a float
	 */
	public float getArea()
	{
		return FloatMath.PI * (radius * radius);
	}

	/**
	 * <p>getDiameter.</p>
	 *
	 * @return a float
	 */
	public float getDiameter()
	{
		return radius + radius;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(float x, float y)
	{
		x -= this.x;
		y -= this.y;
		return x * x + y * y <= radius * radius;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(float x, float y, float w, float h)
	{
		return contains(x, y) && contains(x + w, y + h);
	}

	/** {@inheritDoc} */
	@Override
	public Circle clone()
	{
		return new Circle(x, y, radius);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Circle)
		{
			Circle c = (Circle) o;
			return c.x == x && c.y == y && c.radius == radius;
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		int hash = 29;
		hash = hash * 41 + Float.floatToIntBits(x);
		hash = hash * 41 + Float.floatToIntBits(y);
		hash = hash * 41 + Float.floatToIntBits(radius);
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ", " + radius + ")";
	}

	private static final long serialVersionUID = 4235769867295883802L;
}
