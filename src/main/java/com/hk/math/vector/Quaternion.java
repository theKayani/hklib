package com.hk.math.vector;

import java.io.Serializable;
import com.hk.math.FloatMath;

// http://paulbourke.net/fractals/quatjulia
// http://www.astro.rug.nl/software/kapteyn/_downloads/attitude.pdf
// https://www.mathworks.com/help/aeroblks/quaterniondivision.html?s_tid=gn_loc_drop

public final class Quaternion implements Cloneable, Serializable
{
	public float i, j, k, w;

	public Quaternion()
	{
		identity();
	}

	public Quaternion(Quaternion quat)
	{
		set(quat);
	}

	public Quaternion(float i, float j, float k, float w)
	{
		set(i, j, k, w);
	}

	public Quaternion add(Quaternion quat)
	{
		i += quat.i;
		j += quat.j;
		k += quat.k;
		w += quat.w;
		return this;
	}

	public Quaternion multiply(Quaternion quat)
	{
		float i1 = w * quat.i + quat.w * i + j * quat.k - k * quat.j;
		float j1 = w * quat.j + quat.w * j + k * quat.i - i * quat.k;
		float k1 = w * quat.k + quat.w * k + i * quat.j - j * quat.i;
		float w1 = w * quat.w - i * quat.i - j * quat.j - k * quat.k;
		set(i1, j1, k1, w1);
		return this;
	}

	public Quaternion multiply(float i, float j, float k, float w)
	{
		float i1 = this.w * i + w * this.i + this.j * k - this.k * j;
		float j1 = this.w * j + w * this.j + this.k * i - this.i * k;
		float k1 = this.w * k + w * this.k + this.i * j - this.j * i;
		float w1 = this.w * w - this.i * i - this.j * j - this.k * k;
		set(i1, j1, k1, w1);
		return this;
	}

	public Quaternion divide(Quaternion quat)
	{
		return this;
	}

	public Quaternion interpolate(float i, float j, float k, float w, float amt)
	{
		this.i = (1F - amt) * this.i + amt * i;
		this.j = (1F - amt) * this.j + amt * j;
		this.k = (1F - amt) * this.k + amt * k;
		this.w = (1F - amt) * this.w + amt * w;
		return this;
	}

	public Quaternion interpolate(Quaternion quat, float amt)
	{
		i = (1F - amt) * i + amt * quat.i;
		j = (1F - amt) * j + amt * quat.j;
		k = (1F - amt) * k + amt * quat.k;
		w = (1F - amt) * w + amt * quat.w;
		return this;
	}

	public Quaternion set(float i, float j, float k, float w)
	{
		this.i = i;
		this.j = j;
		this.k = k;
		this.w = w;
		return this;
	}

	public Quaternion set(Quaternion quat)
	{
		i = quat.i;
		j = quat.j;
		k = quat.k;
		w = quat.w;
		return this;
	}

	public Quaternion conjugate()
	{
		i = -i;
		j = -j;
		k = -k;
		return this;
	}

	public Quaternion inverse()
	{
		float l2 = lengthSquared();
		i = -i / l2;
		j = -j / l2;
		k = -k / l2;
		return this;
	}

	public Quaternion identity()
	{
		i = j = k = 0F;
		w = 1F;
		return this;
	}

	public Quaternion normalize()
	{
		float l = length();
		if (l != 0)
		{
			i /= l;
			j /= l;
			k /= l;
			w /= l;
		}
		return this;
	}

	public float length()
	{
		return FloatMath.sqrt(lengthSquared());
	}

	public float lengthSquared()
	{
		return i * i + j * j + k * k + w * w;
	}

	public Matrix3F toMatrix3F()
	{
		Matrix3F mat = new Matrix3F();
		mat.m00 = 1 - 2 * j * j - 2 * k * k;
		mat.m01 = 2 * i * j - 2 * w * k;
		mat.m02 = 2 * i * k + 2 * w * j;

		mat.m10 = 2 * i * j + 2 * w * k;
		mat.m11 = 1 - 2 * i * i - 2 * k * k;
		mat.m12 = 2 * j * k + 2 * w * k;

		mat.m20 = 2 * i * k - 2 * w * j;
		mat.m21 = 2 * j * k - 2 * w * k;
		mat.m22 = 1 - 2 * i * i - 2 * j * j;
		return mat;
	}

	@Override
	public Quaternion clone()
	{
		return new Quaternion(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Quaternion)
		{
			Quaternion q = (Quaternion) o;
			return Float.floatToIntBits(i) == Float.floatToIntBits(q.i) && Float.floatToIntBits(j) == Float.floatToIntBits(q.j) && Float.floatToIntBits(k) == Float.floatToIntBits(q.k) && Float.floatToIntBits(w) == Float.floatToIntBits(q.w);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 29;
		hash = hash * 31 + Float.floatToIntBits(i);
		hash = hash * 31 + Float.floatToIntBits(j);
		hash = hash * 31 + Float.floatToIntBits(k);
		hash = hash * 31 + Float.floatToIntBits(w);
		return hash;
	}

	@Override
	public String toString()
	{
		return "(" + i + ", " + j + ", " + k + ", " + w + ")";
	}

	private static final long serialVersionUID = 4013666988127924831L;
}