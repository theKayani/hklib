package com.hk.math.shape;

import com.hk.math.FloatMath;

public class Ellipse extends Shape
{
	public float x, y, width, height;

	public Ellipse()
	{
		x = y = width = height = 0F;
	}

	public Ellipse(float width, float height)
	{
		this.width = width;
		this.height = height;
	}

	public Ellipse(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public float getArea()
	{
		return FloatMath.PI * width * height;
	}

	public float getCircumference()
	{
		return Float.NaN; // TODO: Shouldn't return NaN...
	}

	public float getEccentricity()
	{
		float a = Math.max(width, height);
		float b = Math.min(width, height);
		return FloatMath.sqrt(1F - b * b / (a * a));
	}

	public float getSemiMajorAxis()
	{
		return Math.max(width, height);
	}

	public float getSemiMinorAxis()
	{
		return Math.min(width, height);
	}

	@Override
	public boolean contains(float x, float y)
	{
		x -= this.x;
		y -= this.y;
		y *= width / height;
		return x * x + y * y <= width * 0.5F * (width * 0.5F);
	}

	@Override
	public boolean contains(float x, float y, float w, float h)
	{
		// TODO: Actually add code here...
		return false;
	}

	@Override
	public Ellipse clone()
	{
		return new Ellipse(x, y, width, height);
	}

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

	@Override
	public int hashCode()
	{
		int hash = 31;
		hash = hash * 13 + Float.floatToIntBits(x);
		hash = hash * 13 + Float.floatToIntBits(y);
		hash = hash * 13 + Float.floatToIntBits(width);
		hash = hash * 13 + Float.floatToIntBits(height);
		return 0;
	}

	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ", " + width + ", " + height + ")";
	}

	private static final long serialVersionUID = -6041225136062166164L;
}
