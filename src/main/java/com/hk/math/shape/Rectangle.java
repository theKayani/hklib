package com.hk.math.shape;

import com.hk.math.vector.Vector2F;

/**
 * <p>Rectangle class.</p>
 *
 * @author theKayani
 */
public class Rectangle extends Shape
{
	public float x, y, width, height;

	/**
	 * <p>Constructor for Rectangle.</p>
	 */
	public Rectangle()
	{
		x = y = width = height = 0F;
	}

	/**
	 * <p>Constructor for Rectangle.</p>
	 *
	 * @param width a float
	 * @param height a float
	 */
	public Rectangle(float width, float height)
	{
		set(width, height);
	}

	/**
	 * <p>Constructor for Rectangle.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param width a float
	 * @param height a float
	 */
	public Rectangle(float x, float y, float width, float height)
	{
		set(x, y, width, height);
	}

	/**
	 * <p>Constructor for Rectangle.</p>
	 *
	 * @param rect a {@link com.hk.math.shape.Rectangle} object
	 */
	public Rectangle(Rectangle rect)
	{
		set(rect);
	}

	/**
	 * <p>set.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param width a float
	 * @param height a float
	 * @return a {@link com.hk.math.shape.Rectangle} object
	 */
	public Rectangle set(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param width a float
	 * @param height a float
	 * @return a {@link com.hk.math.shape.Rectangle} object
	 */
	public Rectangle set(float width, float height)
	{
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param rectangle a {@link com.hk.math.shape.Rectangle} object
	 * @return a {@link com.hk.math.shape.Rectangle} object
	 */
	public Rectangle set(Rectangle rectangle)
	{
		x = rectangle.x;
		y = rectangle.y;
		width = rectangle.width;
		height = rectangle.height;
		return this;
	}

	/**
	 * <p>getArea.</p>
	 *
	 * @return a float
	 */
	public float getArea()
	{
		return width * height;
	}

	/**
	 * <p>getPerimiter.</p>
	 *
	 * @return a float
	 */
	public float getPerimiter()
	{
		return width + width + height + height;
	}

	/**
	 * <p>getCenter.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	public Vector2F getCenter()
	{
		return new Vector2F(x + width / 2F, y + height / 2F);
	}

	/**
	 * <p>getCenterX.</p>
	 *
	 * @return a float
	 */
	public float getCenterX()
	{
		return x + width / 2F;
	}

	/**
	 * <p>getCenterY.</p>
	 *
	 * @return a float
	 */
	public float getCenterY()
	{
		return y + height / 2F;
	}

	/**
	 * <p>getMinX.</p>
	 *
	 * @return a float
	 */
	public float getMinX()
	{
		return x;
	}

	/**
	 * <p>getMaxX.</p>
	 *
	 * @return a float
	 */
	public float getMaxX()
	{
		return x + width;
	}

	/**
	 * <p>getMinY.</p>
	 *
	 * @return a float
	 */
	public float getMinY()
	{
		return y;
	}

	/**
	 * <p>getMaxY.</p>
	 *
	 * @return a float
	 */
	public float getMaxY()
	{
		return y + height;
	}
	
	/**
	 * <p>center.</p>
	 */
	public void center()
	{
		x -= width / 2;
		y -= height / 2;
	}

	/**
	 * <p>grow.</p>
	 *
	 * @param x a float
	 * @param y a float
	 */
	public void grow(float x, float y)
	{
		this.x -= x;
		this.y -= y;
		width += x + x;
		height += y + y;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(float x, float y)
	{
		return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(float x, float y, float w, float h)
	{
		return contains(x, y) && contains(x + w, y + h);
	}

	/** {@inheritDoc} */
	@Override
	public Rectangle clone()
	{
		return new Rectangle(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Rectangle)
		{
			Rectangle r = (Rectangle) o;
			return r.x == x && r.y == y && r.width == width && r.height == height;
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = hash * 31 + Float.floatToIntBits(x);
		hash = hash * 31 + Float.floatToIntBits(y);
		hash = hash * 31 + Float.floatToIntBits(width);
		hash = hash * 31 + Float.floatToIntBits(height);
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ", " + width + ", " + height + ")";
	}

	private static final long serialVersionUID = 3999762594357009498L;

}
