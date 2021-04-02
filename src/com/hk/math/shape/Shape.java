package com.hk.math.shape;

import java.io.Serializable;
import com.hk.math.vector.Vector2F;

public abstract class Shape implements Serializable, Cloneable
{
	public boolean contains(Vector2F v)
	{
		return contains(v.x, v.y);
	}

	public abstract boolean contains(float x, float y);

	public abstract boolean contains(float x, float y, float w, float h);

	@Override
	public abstract Shape clone();

	@Override
	public abstract boolean equals(Object o);

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();

	private static final long serialVersionUID = -4363845090169684027L;
}
