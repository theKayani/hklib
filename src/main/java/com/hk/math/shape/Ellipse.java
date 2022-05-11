package com.hk.math.shape;

import com.hk.math.FloatMath;

/**
 * <p>Ellipse class.</p>
 *
 * @author theKayani
 */
public class Ellipse extends Shape
{
	public float x, y, width, height;

	/**
	 * <p>Constructor for Ellipse.</p>
	 */
	public Ellipse()
	{
		x = y = width = height = 0F;
	}

	/**
	 * <p>Constructor for Ellipse.</p>
	 *
	 * @param width a float
	 * @param height a float
	 */
	public Ellipse(float width, float height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * <p>Constructor for Ellipse.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param width a float
	 * @param height a float
	 */
	public Ellipse(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * <p>getArea.</p>
	 *
	 * @return a float
	 */
	public float getArea()
	{
		return FloatMath.PI * width * height;
	}

	/**
	 * <p>getCircumference.</p>
	 * TODO: finish
	 *
	 * @return a float
	 */
	public float getCircumference()
	{
		return Float.NaN;
	}

	/**
	 * <p>getEccentricity.</p>
	 *
	 * @return a float
	 */
	public float getEccentricity()
	{
		float a = Math.max(width, height);
		float b = Math.min(width, height);
		return FloatMath.sqrt(1F - b * b / (a * a));
	}

	/**
	 * <p>getSemiMajorAxis.</p>
	 *
	 * @return a float
	 */
	public float getSemiMajorAxis()
	{
		return Math.max(width, height);
	}

	/**
	 * <p>getSemiMinorAxis.</p>
	 *
	 * @return a float
	 */
	public float getSemiMinorAxis()
	{
		return Math.min(width, height);
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(float x, float y)
	{
		x -= this.x;
		y -= this.y;
		y *= width / height;
		return x * x + y * y <= width * 0.5F * (width * 0.5F);
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(float x, float y, float w, float h)
	{
		// TODO: Actually add code here...
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Ellipse clone()
	{
		return new Ellipse(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Ellipse)
		{
			Ellipse e = (Ellipse) o;
			return e.x == x && e.y == y && e.width == width && e.height == height;
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		int hash = 31;
		hash = hash * 13 + Float.floatToIntBits(x);
		hash = hash * 13 + Float.floatToIntBits(y);
		hash = hash * 13 + Float.floatToIntBits(width);
		hash = hash * 13 + Float.floatToIntBits(height);
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ", " + width + ", " + height + ")";
	}

	private static final long serialVersionUID = -6041225136062166164L;
}
