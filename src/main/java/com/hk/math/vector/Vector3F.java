package com.hk.math.vector;

import java.io.Serializable;
import com.hk.array.ArrayUtil;
import com.hk.math.FloatMath;

/**
 * <p>Vector3F class.</p>
 *
 * @author theKayani
 */
public final class Vector3F implements Cloneable, Serializable
{
	public float x, y, z;

	/**
	 * <p>Constructor for Vector3F.</p>
	 */
	public Vector3F()
	{
		set(0F);
	}

	/**
	 * <p>Constructor for Vector3F.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F(Vector3F vec)
	{
		set(vec);
	}

	/**
	 * <p>Constructor for Vector3F.</p>
	 *
	 * @param val a float
	 */
	public Vector3F(float val)
	{
		set(val);
	}

	/**
	 * <p>Constructor for Vector3F.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 */
	public Vector3F(float x, float y, float z)
	{
		set(x, y, z);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F add(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F add(float val)
	{
		x += val;
		y += val;
		z += val;
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F add(Vector3F vec)
	{
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F subtract(float x, float y, float z)
	{
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F subtract(float val)
	{
		x -= val;
		y -= val;
		z -= val;
		return this;
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F subtract(Vector3F vec)
	{
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}

	/**
	 * <p>multiply.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F multiply(float x, float y, float z)
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	/**
	 * <p>multiply.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F multiply(float val)
	{
		x *= val;
		y *= val;
		z *= val;
		return this;
	}

	/**
	 * <p>multiply.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F multiply(Vector3F vec)
	{
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F divide(float x, float y, float z)
	{
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F divide(float val)
	{
		x /= val;
		y /= val;
		z /= val;
		return this;
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F divide(Vector3F vec)
	{
		x /= vec.x;
		y /= vec.y;
		z /= vec.z;
		return this;
	}

	/**
	 * <p>interpolate.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @param amt a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F interpolate(float x, float y, float z, float amt)
	{
		this.x = (1F - amt) * this.x + amt * x;
		this.y = (1F - amt) * this.y + amt * y;
		this.z = (1F - amt) * this.z + amt * z;
		return this;
	}

	/**
	 * <p>interpolate.</p>
	 *
	 * @param val a float
	 * @param amt a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F interpolate(float val, float amt)
	{
		x = (1F - amt) * x + amt * val;
		y = (1F - amt) * y + amt * val;
		z = (1F - amt) * z + amt * val;
		return this;
	}

	/**
	 * <p>interpolate.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @param amt a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F interpolate(Vector3F vec, float amt)
	{
		x = (1F - amt) * x + amt * vec.x;
		y = (1F - amt) * y + amt * vec.y;
		z = (1F - amt) * z + amt * vec.z;
		return this;
	}

	/**
	 * <p>zero.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F zero()
	{
		x = y = z = 0F;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F set(float val)
	{
		x = val;
		y = val;
		z = val;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F set(Vector3F vec)
	{
		x = vec.x;
		y = vec.y;
		z = vec.z;
		return this;
	}

	/**
	 * <p>Setter for the field <code>x</code>.</p>
	 *
	 * @param x a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F setX(float x)
	{
		this.x = x;
		return this;
	}

	/**
	 * <p>Setter for the field <code>y</code>.</p>
	 *
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F setY(float y)
	{
		this.y = y;
		return this;
	}

	/**
	 * <p>Setter for the field <code>z</code>.</p>
	 *
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F setZ(float z)
	{
		this.z = z;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F set(float[] arr)
	{
		return set(arr, 0);
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @param off a int
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F set(float[] arr, int off)
	{
		x = arr[off];
		y = arr[off + 1];
		z = arr[off + 2];
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param index a int
	 * @param val a float
	 */
	public void set(int index, float val)
	{
		switch(index)
		{
			case 0: x = val; return;
			case 1: y = val; return;
			case 2: z = val; return;
		}
		throw new IllegalArgumentException(index + " must be 0, 1, or 2.");
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
	 * <p>Getter for the field <code>z</code>.</p>
	 *
	 * @return a float
	 */
	public float getZ()
	{
		return z;
	}

	/**
	 * <p>get.</p>
	 *
	 * @return an array of {@link float} objects
	 */
	public float[] get()
	{
		return ArrayUtil.toFloatArray(x, y, z);
	}

	/**
	 * <p>get.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return an array of {@link float} objects
	 */
	public float[] get(float[] arr)
	{
		return get(arr, 0);
	}

	/**
	 * <p>get.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @param off a int
	 * @return an array of {@link float} objects
	 */
	public float[] get(float[] arr, int off)
	{
		arr[off] = x;
		arr[off + 1] = y;
		arr[off + 2] = z;
		return arr;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param index a int
	 * @return a float
	 */
	public float get(int index)
	{
		switch(index)
		{
		case 0: return x;
		case 1: return y;
		case 2: return z;
		}
		throw new IllegalArgumentException(index + " must be 0, 1, or 2.");
	}

	/**
	 * <p>normalize.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F normalize()
	{
		float l = length();
		if (l == 0F)
		{
			return this;
		}
		return divide(l);
	}

	/**
	 * <p>abs.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F abs()
	{
		set(Math.abs(x), Math.abs(y), Math.abs(z));
		return this;
	}

	/**
	 * <p>negate.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F negate()
	{
		set(-x, -y, -z);
		return this;
	}

	/**
	 * <p>negative.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F negative()
	{
		set(-Math.abs(x), -Math.abs(y), -Math.abs(z));
		return this;
	}

	/**
	 * <p>length.</p>
	 *
	 * @return a float
	 */
	public float length()
	{
		return FloatMath.sqrt(lengthSquared());
	}

	/**
	 * <p>lengthSquared.</p>
	 *
	 * @return a float
	 */
	public float lengthSquared()
	{
		return x * x + y * y + z * z;
	}

	/**
	 * <p>distance.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a float
	 */
	public float distance(float x, float y, float z)
	{
		return FloatMath.sqrt(distanceSquared(x, y, z));
	}

	/**
	 * <p>distance.</p>
	 *
	 * @param val a float
	 * @return a float
	 */
	public float distance(float val)
	{
		return FloatMath.sqrt(distanceSquared(val));
	}

	/**
	 * <p>distance.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a float
	 */
	public float distance(Vector3F vec)
	{
		return FloatMath.sqrt(distanceSquared(vec));
	}

	/**
	 * <p>dot.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a float
	 */
	public float dot(float x, float y, float z)
	{
		return this.x * x + this.y * y + this.z * z;
	}

	/**
	 * <p>dot.</p>
	 *
	 * @param val a float
	 * @return a float
	 */
	public float dot(float val)
	{
		return x * val + y * val + z * val;
	}

	/**
	 * <p>dot.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a float
	 */
	public float dot(Vector3F vec)
	{
		return x * vec.x + y * vec.y + z * vec.z;
	}

	/**
	 * <p>cross.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F cross(float x, float y, float z)
	{
		float x1 = this.y * z - this.z * y;
		float y1 = this.z * x - this.x * z;
		float z1 = this.x * y - this.y * x;
		set(x1, y1, z1);
		return this;
	}

	/**
	 * <p>cross.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F cross(float val)
	{
		float x1 = y * val - z * val;
		float y1 = z * val - x * val;
		float z1 = x * val - y * val;
		set(x1, y1, z1);
		return this;
	}

	/**
	 * <p>cross.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a {@link com.hk.math.vector.Vector3F} object
	 */
	public Vector3F cross(Vector3F vec)
	{
		float x1 = y * vec.z - z * vec.y;
		float y1 = z * vec.x - x * vec.z;
		float z1 = x * vec.y - y * vec.x;
		set(x1, y1, z1);
		return this;
	}

	/**
	 * <p>distanceSquared.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a float
	 */
	public float distanceSquared(Vector3F vec)
	{
		float x1 = x - vec.x;
		float y1 = y - vec.y;
		float z1 = z - vec.z;

		return x1 * x1 + y1 * y1 + z1 * z1;
	}

	/**
	 * <p>distanceSquared.</p>
	 *
	 * @param val a float
	 * @return a float
	 */
	public float distanceSquared(float val)
	{
		float x1 = x - val;
		float y1 = y - val;
		float z1 = z - val;

		return x1 * x1 + y1 * y1 + z1 * z1;
	}

	/**
	 * <p>distanceSquared.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a float
	 */
	public float distanceSquared(float x, float y, float z)
	{
		float x1 = this.x - x;
		float y1 = this.y - y;
		float z1 = this.z - z;

		return x1 * x1 + y1 * y1 + z1 * z1;
	}

	/**
	 * <p>getAngleToXAxis.</p>
	 *
	 * @return a float
	 */
	public float getAngleToXAxis()
	{
		return FloatMath.atan2(FloatMath.sqrt(y * y + z * z), x);
	}

	/**
	 * <p>getAngleToYAxis.</p>
	 *
	 * @return a float
	 */
	public float getAngleToYAxis()
	{
		return FloatMath.atan2(FloatMath.sqrt(x * x + z * z), y);
	}

	/**
	 * <p>getAngleToZAxis.</p>
	 *
	 * @return a float
	 */
	public float getAngleToZAxis()
	{
		return FloatMath.atan2(FloatMath.sqrt(x * x + y * y), z);
	}

	/**
	 * <p>isValidVector.</p>
	 *
	 * @return a boolean
	 */
	public boolean isValidVector()
	{
		return isValidVector(this);
	}

	/**
	 * <p>isUnitVector.</p>
	 *
	 * @return a boolean
	 */
	public boolean isUnitVector()
	{
		return isUnitVector(this);
	}

	/**
	 * <p>isValidVector.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a boolean
	 */
	public static boolean isValidVector(Vector3F vec)
	{
		return vec != null && !(Float.isNaN(vec.x) || Float.isNaN(vec.y) || Float.isNaN(vec.z) || Float.isInfinite(vec.x) || Float.isInfinite(vec.y) || Float.isInfinite(vec.z));
	}

	/**
	 * <p>isUnitVector.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector3F} object
	 * @return a boolean
	 */
	public static boolean isUnitVector(Vector3F vec)
	{
		return vec != null && vec.x > 0.99F && vec.y > 0.99F && vec.z > 0.99F && vec.x < 1.01F && vec.y < 1.01F && vec.z < 1.01F;
	}

	/** {@inheritDoc} */
	@Override
	public Vector3F clone()
	{
		return new Vector3F(this);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Vector3F)
		{
			Vector3F v = (Vector3F) o;
			return Float.floatToIntBits(x) == Float.floatToIntBits(v.x) && Float.floatToIntBits(y) == Float.floatToIntBits(v.y) && Float.floatToIntBits(z) == Float.floatToIntBits(v.z);
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = hash * 23 + Float.floatToIntBits(x);
		hash = hash * 23 + Float.floatToIntBits(y);
		hash = hash * 23 + Float.floatToIntBits(z);
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}

	private static final long serialVersionUID = 7671650628557118354L;
}