package com.hk.math.shape;

import java.io.Serializable;
import com.hk.math.vector.Vector2F;

/**
 * <p>Abstract Shape class.</p>
 *
 * @author theKayani
 */
public abstract class Shape implements Serializable, Cloneable
{
	/**
	 * <p>contains.</p>
	 *
	 * @param v a {@link com.hk.math.vector.Vector2F} object
	 * @return a boolean
	 */
	public boolean contains(Vector2F v)
	{
		return contains(v.x, v.y);
	}

	/**
	 * <p>contains.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a boolean
	 */
	public abstract boolean contains(float x, float y);

	/**
	 * <p>contains.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param w a float
	 * @param h a float
	 * @return a boolean
	 */
	public abstract boolean contains(float x, float y, float w, float h);

	/** {@inheritDoc} */
	@Override
	public abstract Shape clone();

	/** {@inheritDoc} */
	@Override
	public abstract boolean equals(Object o);

	/** {@inheritDoc} */
	@Override
	public abstract int hashCode();

	/** {@inheritDoc} */
	@Override
	public abstract String toString();

	private static final long serialVersionUID = -4363845090169684027L;
}
