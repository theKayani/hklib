package com.hk.math.vector;

public class Point2F
{
	public float x, y;

	public Point2F()
	{
		x = y = 0;
	}

	public Point2F(float val)
	{
		x = y = val;
	}

	public Point2F(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Point2F(Point2F p)
	{
		x = p.x;
		y = p.y;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}
	
	public float[] get(float[] arr, int off)
	{
		arr[off++] = x;
		arr[off] = y;
		return arr;
	}
	
	public float[] get(float[] arr)
	{
		return get(arr, 0);
	}

	public float[] get()
	{
		return get(new float[2]);
	}
	
	public Point2F setX(float x)
	{
		this.x = x;
		return this;
	}

	public Point2F setY(float y)
	{
		this.y = y;
		return this;
	}

	public Point2F set(float val)
	{
		x = y = val;
		return this;
	}

	public Point2F set(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public Point2F set(Point2F p)
	{
		x = p.x;
		y = p.y;
		return this;
	}

	public Point2F set(float[] arr, int off)
	{
		x = arr[off++];
		y = arr[off];
		return this;
	}

	public Point2F set(float[] arr)
	{
		return set(arr, 0);
	}
}
