package com.hk.math.shape;

import com.hk.math.FloatMath;

public class Circle extends Shape
{
	public float x, y, radius;

	public Circle()
	{
		x = y = radius = 0F;
	}

	public Circle(float radius)
	{
		this.radius = radius;
	}

	public Circle(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Circle(float x, float y, float radius)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public float getCircumference()
	{
		return 2 * FloatMath.PI * radius;
	}

	public float getArea()
	{
		return FloatMath.PI * (radius * radius);
	}

	public float getDiameter()
	{
		return radius + radius;
	}

	@Override
	public boolean contains(float x, float y)
	{
		x -= this.x;
		y -= this.y;
		return x * x + y * y <= radius * radius;
	}

	@Override
	public boolean contains(float x, float y, float w, float h)
	{
		return contains(x, y) && contains(x + w, y + h);
	}

	@Override
	public Circle clone()
	{
		return new Circle(x, y, radius);
	}

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

	@Override
	public int hashCode()
	{
		int hash = 29;
		hash = hash * 41 + Float.floatToIntBits(x);
		hash = hash * 41 + Float.floatToIntBits(y);
		hash = hash * 41 + Float.floatToIntBits(radius);
		return 0;
	}

	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ", " + radius + ")";
	}

	private static final long serialVersionUID = 4235769867295883802L;
}
