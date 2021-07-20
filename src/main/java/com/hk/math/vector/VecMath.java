package com.hk.math.vector;

/**
 * <p>VecMath class.</p>
 *
 * @author theKayani
 */
public class VecMath
{
	/**
	 * <p>distanceToSegment.</p>
	 *
	 * @param v1 a {@link com.hk.math.vector.Vector2F} object
	 * @param v2 a {@link com.hk.math.vector.Vector2F} object
	 * @param point a {@link com.hk.math.vector.Vector2F} object
	 * @return a float
	 */
	public static float distanceToSegment(Vector2F v1, Vector2F v2, Vector2F point)
	{
		return closestToSegment(v1, v2, point).distance(point);
	}
	
	/**
	 * <p>closestToSegment.</p>
	 *
	 * @param v1 a {@link com.hk.math.vector.Vector2F} object
	 * @param v2 a {@link com.hk.math.vector.Vector2F} object
	 * @param point a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	public static Vector2F closestToSegment(Vector2F v1, Vector2F v2, Vector2F point)
	{
		float xDelta = v2.getX() - v1.getX();
		float yDelta = v2.getY() - v1.getY();

		if ((xDelta == 0) && (yDelta == 0))
		{
		    throw new IllegalArgumentException("p1 and p2 cannot be the same point");
		}

		float u = ((point.getX() - v1.getX()) * xDelta + (point.getY() - v1.getY()) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

		final Vector2F closestPoint;
		if (u < 0)
		{
		    closestPoint = v1;
		}
		else if (u > 1)
		{
		    closestPoint = v2;
		}
		else
		{
		    closestPoint = new Vector2F(v1.getX() + u * xDelta, v1.getY() + u * yDelta);
		}

		return closestPoint;
	}
	
	private VecMath()
	{}
}
