package com.hk.math.vector;

import org.jetbrains.annotations.NotNull;

/**
 * <p>Point2F class.</p>
 * TODO: finish
 *
 * @author theKayani
 */
public class Point2F
{
	public float x, y;

	/**
	 * <p>Constructor for Point2F.</p>
	 */
	public Point2F()
	{
		x = y = 0;
	}

	/**
	 * <p>Constructor for Point2F.</p>
	 *
	 * @param val a float
	 */
	public Point2F(float val)
	{
		x = y = val;
	}

	/**
	 * <p>Constructor for Point2F.</p>
	 *
	 * @param x a float
	 * @param y a float
	 */
	public Point2F(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * <p>Constructor for Point2F.</p>
	 *
	 * @param p a {@link com.hk.math.vector.Point2F} object
	 */
	public Point2F(@NotNull Point2F p)
	{
		x = p.x;
		y = p.y;
	}

	/**
	 * <p>Getter for the field <code>x</code>.</p>
	 *
	 * @return a float
	 */
	public float getX()
	{
		return x;
	}

	/**
	 * <p>Getter for the field <code>y</code>.</p>
	 *
	 * @return a float
	 */
	public float getY()
	{
		return y;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @param off a int
	 * @return an array of {@link float} objects
	 */
	public float @NotNull [] get(float @NotNull [] arr, int off)
	{
		arr[off++] = x;
		arr[off] = y;
		return arr;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return an array of {@link float} objects
	 */
	public float @NotNull [] get(float @NotNull [] arr)
	{
		return get(arr, 0);
	}

	/**
	 * <p>get.</p>
	 *
	 * @return an array of {@link float} objects
	 */
	public float @NotNull [] get()
	{
		return get(new float[2]);
	}

	/**
	 * <p>Setter for the field <code>x</code>.</p>
	 *
	 * @param x a float
	 * @return a {@link com.hk.math.vector.Point2F} object
	 */
	@NotNull
	public Point2F setX(float x)
	{
		this.x = x;
		return this;
	}

	/**
	 * <p>Setter for the field <code>y</code>.</p>
	 *
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Point2F} object
	 */
	@NotNull
	public Point2F setY(float y)
	{
		this.y = y;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Point2F} object
	 */
	@NotNull
	public Point2F set(float val)
	{
		x = y = val;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Point2F} object
	 */
	@NotNull
	public Point2F set(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param p a {@link com.hk.math.vector.Point2F} object
	 * @return a {@link com.hk.math.vector.Point2F} object
	 */
	@NotNull
	public Point2F set(@NotNull Point2F p)
	{
		x = p.x;
		y = p.y;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @param off a int
	 * @return a {@link com.hk.math.vector.Point2F} object
	 */
	@NotNull
	public Point2F set(float @NotNull [] arr, int off)
	{
		x = arr[off++];
		y = arr[off];
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return a {@link com.hk.math.vector.Point2F} object
	 */
	@NotNull
	public Point2F set(float @NotNull [] arr)
	{
		return set(arr, 0);
	}
}