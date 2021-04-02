package com.hk.math.vector;

import java.io.Serializable;
import com.hk.array.ArrayUtil;
import com.hk.math.FloatMath;

public final class Vector3F implements Cloneable, Serializable
{
	public float x, y, z;

	public Vector3F()
	{
		set(0F);
	}

	public Vector3F(Vector3F vec)
	{
		set(vec);
	}

	public Vector3F(float val)
	{
		set(val);
	}

	public Vector3F(float x, float y, float z)
	{
		set(x, y, z);
	}

	public Vector3F add(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Vector3F add(float val)
	{
		x += val;
		y += val;
		z += val;
		return this;
	}

	public Vector3F add(Vector3F vec)
	{
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}

	public Vector3F subtract(float x, float y, float z)
	{
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public Vector3F subtract(float val)
	{
		x -= val;
		y -= val;
		z -= val;
		return this;
	}

	public Vector3F subtract(Vector3F vec)
	{
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}

	public Vector3F multiply(float x, float y, float z)
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	public Vector3F multiply(float val)
	{
		x *= val;
		y *= val;
		z *= val;
		return this;
	}

	public Vector3F multiply(Vector3F vec)
	{
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	public Vector3F divide(float x, float y, float z)
	{
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}

	public Vector3F divide(float val)
	{
		x /= val;
		y /= val;
		z /= val;
		return this;
	}

	public Vector3F divide(Vector3F vec)
	{
		x /= vec.x;
		y /= vec.y;
		z /= vec.z;
		return this;
	}

	public Vector3F interpolate(float x, float y, float z, float amt)
	{
		this.x = (1F - amt) * this.x + amt * x;
		this.y = (1F - amt) * this.y + amt * y;
		this.z = (1F - amt) * this.z + amt * z;
		return this;
	}

	public Vector3F interpolate(float val, float amt)
	{
		x = (1F - amt) * x + amt * val;
		y = (1F - amt) * y + amt * val;
		z = (1F - amt) * z + amt * val;
		return this;
	}

	public Vector3F interpolate(Vector3F vec, float amt)
	{
		x = (1F - amt) * x + amt * vec.x;
		y = (1F - amt) * y + amt * vec.y;
		z = (1F - amt) * z + amt * vec.z;
		return this;
	}

	public Vector3F zero()
	{
		x = y = z = 0F;
		return this;
	}

	public Vector3F set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3F set(float val)
	{
		x = val;
		y = val;
		z = val;
		return this;
	}

	public Vector3F set(Vector3F vec)
	{
		x = vec.x;
		y = vec.y;
		z = vec.z;
		return this;
	}

	public Vector3F setX(float x)
	{
		this.x = x;
		return this;
	}

	public Vector3F setY(float y)
	{
		this.y = y;
		return this;
	}

	public Vector3F setZ(float z)
	{
		this.z = z;
		return this;
	}

	public Vector3F set(float[] arr)
	{
		return set(arr, 0);
	}

	public Vector3F set(float[] arr, int off)
	{
		x = arr[off + 0];
		y = arr[off + 1];
		z = arr[off + 2];
		return this;
	}
	
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

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getZ()
	{
		return z;
	}

	public float[] get()
	{
		return ArrayUtil.toFloatArray(x, y, z);
	}

	public float[] get(float[] arr)
	{
		return get(arr, 0);
	}

	public float[] get(float[] arr, int off)
	{
		arr[off + 0] = x;
		arr[off + 1] = y;
		arr[off + 2] = z;
		return arr;
	}
	
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

	public Vector3F normalize()
	{
		float l = length();
		if (l == 0F)
		{
			return this;
		}
		return divide(l);
	}

	public Vector3F abs()
	{
		set(Math.abs(x), Math.abs(y), Math.abs(z));
		return this;
	}

	public Vector3F negate()
	{
		set(-x, -y, -z);
		return this;
	}

	public Vector3F negative()
	{
		set(-Math.abs(x), -Math.abs(y), -Math.abs(z));
		return this;
	}

	public float length()
	{
		return FloatMath.sqrt(lengthSquared());
	}

	public float lengthSquared()
	{
		return x * x + y * y + z * z;
	}

	public float distance(float x, float y, float z)
	{
		return FloatMath.sqrt(distanceSquared(x, y, z));
	}

	public float distance(float val)
	{
		return FloatMath.sqrt(distanceSquared(val));
	}

	public float distance(Vector3F vec)
	{
		return FloatMath.sqrt(distanceSquared(vec));
	}

	public float dot(float x, float y, float z)
	{
		return this.x * x + this.y * y + this.z * z;
	}

	public float dot(float val)
	{
		return x * val + y * val + z * val;
	}

	public float dot(Vector3F vec)
	{
		return x * vec.x + y * vec.y + z * vec.z;
	}

	public Vector3F cross(float x, float y, float z)
	{
		float x1 = this.y * z - this.z * y;
		float y1 = this.z * x - this.x * z;
		float z1 = this.x * y - this.y * x;
		set(x1, y1, z1);
		return this;
	}

	public Vector3F cross(float val)
	{
		float x1 = y * val - z * val;
		float y1 = z * val - x * val;
		float z1 = x * val - y * val;
		set(x1, y1, z1);
		return this;
	}

	public Vector3F cross(Vector3F vec)
	{
		float x1 = y * vec.z - z * vec.y;
		float y1 = z * vec.x - x * vec.z;
		float z1 = x * vec.y - y * vec.x;
		set(x1, y1, z1);
		return this;
	}

	public float distanceSquared(Vector3F vec)
	{
		float x1 = x - vec.x;
		float y1 = y - vec.y;
		float z1 = z - vec.z;

		return x1 * x1 + y1 * y1 + z1 * z1;
	}

	public float distanceSquared(float val)
	{
		float x1 = x - val;
		float y1 = y - val;
		float z1 = z - val;

		return x1 * x1 + y1 * y1 + z1 * z1;
	}

	public float distanceSquared(float x, float y, float z)
	{
		float x1 = this.x - x;
		float y1 = this.y - y;
		float z1 = this.z - z;

		return x1 * x1 + y1 * y1 + z1 * z1;
	}

	public float getAngleToXAxis()
	{
		return FloatMath.atan2(FloatMath.sqrt(y * y + z * z), x);
	}

	public float getAngleToYAxis()
	{
		return FloatMath.atan2(FloatMath.sqrt(x * x + z * z), y);
	}

	public float getAngleToZAxis()
	{
		return FloatMath.atan2(FloatMath.sqrt(x * x + y * y), z);
	}

	public boolean isValidVector()
	{
		return isValidVector(this);
	}

	public boolean isUnitVector()
	{
		return isUnitVector(this);
	}

	public static boolean isValidVector(Vector3F vec)
	{
		return vec == null ? false : !(Float.isNaN(vec.x) || Float.isNaN(vec.y) || Float.isInfinite(vec.x) || Float.isInfinite(vec.y) || Float.isInfinite(vec.z) || Float.isInfinite(vec.z));
	}

	public static boolean isUnitVector(Vector3F vec)
	{
		return vec == null ? false : vec.x > 0.99F && vec.y > 0.99F && vec.z > 0.99F && vec.x < 1.01F && vec.y < 1.01F && vec.z < 1.01F;
	}

	@Override
	public Vector3F clone()
	{
		return new Vector3F(this);
	}

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

	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = hash * 23 + Float.floatToIntBits(x);
		hash = hash * 23 + Float.floatToIntBits(y);
		hash = hash * 23 + Float.floatToIntBits(z);
		return hash;
	}

	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}

	private static final long serialVersionUID = 7671650628557118354L;
}