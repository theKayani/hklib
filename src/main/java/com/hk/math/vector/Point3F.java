package com.hk.math.vector;

import java.io.Serializable;
import com.hk.array.ArrayUtil;

public class Point3F implements Cloneable, Serializable
{
	public float x, y, z;

	public Point3F()
	{
		set(0F);
	}

	public Point3F(Point3F vec)
	{
		set(vec);
	}

	public Point3F(float val)
	{
		set(val);
	}

	public Point3F(float x, float y, float z)
	{
		set(x, y, z);
	}

	public Point3F addLocal(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Point3F addLocal(float val)
	{
		x += val;
		y += val;
		z += val;
		return this;
	}

	public Point3F addLocal(Point3F vec)
	{
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}

	public Point3F subtractLocal(float x, float y, float z)
	{
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public Point3F subtractLocal(float val)
	{
		x -= val;
		y -= val;
		z -= val;
		return this;
	}

	public Point3F subtractLocal(Point3F vec)
	{
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}

	public Point3F multiplyLocal(float x, float y, float z)
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	public Point3F multiplyLocal(float val)
	{
		x *= val;
		y *= val;
		z *= val;
		return this;
	}

	public Point3F multiplyLocal(Point3F vec)
	{
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	public Point3F divideLocal(float x, float y, float z)
	{
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}

	public Point3F divideLocal(float val)
	{
		x /= val;
		y /= val;
		z /= val;
		return this;
	}

	public Point3F divideLocal(Point3F vec)
	{
		x /= vec.x;
		y /= vec.y;
		z /= vec.z;
		return this;
	}

	public Point3F interpolateLocal(float x, float y, float z, float amt)
	{
		x = (1F - amt) * this.x + amt * x;
		y = (1F - amt) * this.y + amt * y;
		z = (1F - amt) * this.z + amt * z;
		return this;
	}

	public Point3F interpolateLocal(float val, float amt)
	{
		x = (1F - amt) * x + amt * val;
		y = (1F - amt) * y + amt * val;
		z = (1F - amt) * z + amt * val;
		return this;
	}

	public Point3F interpolateLocal(Point3F vec, float amt)
	{
		x = (1F - amt) * x + amt * vec.x;
		y = (1F - amt) * y + amt * vec.y;
		z = (1F - amt) * z + amt * vec.z;
		return this;
	}

	public Point3F add(float x, float y, float z)
	{
		return clone().addLocal(x, y, z);
	}

	public Point3F add(float val)
	{
		return clone().addLocal(val);
	}

	public Point3F add(Point3F vec)
	{
		return clone().addLocal(vec);
	}

	public Point3F subtract(float x, float y, float z)
	{
		return clone().subtractLocal(x, y, z);
	}

	public Point3F subtract(float val)
	{
		return clone().subtractLocal(val);
	}

	public Point3F subtract(Point3F vec)
	{
		return clone().subtractLocal(vec);
	}

	public Point3F multiply(float x, float y, float z)
	{
		return clone().multiplyLocal(x, y, z);
	}

	public Point3F multiply(float val)
	{
		return clone().multiplyLocal(val);
	}

	public Point3F multiply(Point3F vec)
	{
		return clone().multiplyLocal(vec);
	}

	public Point3F divide(float x, float y, float z)
	{
		return clone().divideLocal(x, y, z);
	}

	public Point3F divide(float val)
	{
		return clone().divideLocal(val);
	}

	public Point3F divide(Point3F vec)
	{
		return clone().divideLocal(vec);
	}

	public Point3F interpolate(float x, float y, float z, float amt)
	{
		return clone().interpolateLocal(x, y, z, amt);
	}

	public Point3F interpolate(float val, float amt)
	{
		return clone().interpolateLocal(val, amt);
	}

	public Point3F interpolate(Point3F vec, float amt)
	{
		return clone().interpolateLocal(vec, amt);
	}

	public Point3F zero()
	{
		x = y = z = 0F;
		return this;
	}

	public Point3F set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Point3F set(float val)
	{
		x = val;
		y = val;
		z = val;
		return this;
	}

	public Point3F set(Point3F vec)
	{
		x = vec.x;
		y = vec.y;
		z = vec.z;
		return this;
	}

	public Point3F setX(float x)
	{
		this.x = x;
		return this;
	}

	public Point3F setY(float y)
	{
		this.y = y;
		return this;
	}

	public Point3F setZ(float z)
	{
		this.z = z;
		return this;
	}

	public Point3F set(float[] arr)
	{
		x = arr[0];
		y = arr[1];
		z = arr[2];
		return this;
	}

	public Point3F set(float[] arr, int off)
	{
		x = arr[off + 0];
		y = arr[off + 1];
		z = arr[off + 2];
		return this;
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

	public Point3F absLocal()
	{
		set(Math.abs(x), Math.abs(y), Math.abs(z));
		return this;
	}

	public Point3F negateLocal()
	{
		set(-x, -y, -z);
		return this;
	}

	public Point3F negativeLocal()
	{
		set(-Math.abs(x), -Math.abs(y), -Math.abs(z));
		return this;
	}

	public Point3F abs()
	{
		return clone().absLocal();
	}

	public Point3F negate()
	{
		return clone().negateLocal();
	}

	public Point3F negative()
	{
		return clone().negative();
	}

	@Override
	public Point3F clone()
	{
		return new Point3F(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Point3F)
		{
			Point3F v = (Point3F) o;
			return Float.floatToIntBits(x) == Float.floatToIntBits(v.x) && Float.floatToIntBits(y) == Float.floatToIntBits(v.y) && Float.floatToIntBits(z) == Float.floatToIntBits(v.z);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 47;
		hash = hash * 19 + Float.floatToIntBits(x);
		hash = hash * 19 + Float.floatToIntBits(y);
		hash = hash * 19 + Float.floatToIntBits(z);
		return hash;
	}

	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}

	private static final long serialVersionUID = -5534417407287157643L;
}