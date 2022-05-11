package com.hk.math.vector;

import java.io.Serializable;
import com.hk.array.ArrayUtil;
import org.jetbrains.annotations.NotNull;

/**
 * <p>Point3F class.</p>
 *
 * @author theKayani
 */
public class Point3F implements Cloneable, Serializable
{
	public float x, y, z;

	/**
	 * <p>Constructor for Point3F.</p>
	 */
	public Point3F()
	{
		set(0F);
	}

	/**
	 * <p>Constructor for Point3F.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 */
	public Point3F(@NotNull Point3F vec)
	{
		set(vec);
	}

	/**
	 * <p>Constructor for Point3F.</p>
	 *
	 * @param val a float
	 */
	public Point3F(float val)
	{
		set(val);
	}

	/**
	 * <p>Constructor for Point3F.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 */
	public Point3F(float x, float y, float z)
	{
		set(x, y, z);
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F addLocal(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F addLocal(float val)
	{
		x += val;
		y += val;
		z += val;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F addLocal(@NotNull Point3F vec)
	{
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F subtractLocal(float x, float y, float z)
	{
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F subtractLocal(float val)
	{
		x -= val;
		y -= val;
		z -= val;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F subtractLocal(@NotNull Point3F vec)
	{
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}

	/**
	 * <p>multiplyLocal.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F multiplyLocal(float x, float y, float z)
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	/**
	 * <p>multiplyLocal.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F multiplyLocal(float val)
	{
		x *= val;
		y *= val;
		z *= val;
		return this;
	}

	/**
	 * <p>multiplyLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F multiplyLocal(@NotNull Point3F vec)
	{
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F divideLocal(float x, float y, float z)
	{
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F divideLocal(float val)
	{
		x /= val;
		y /= val;
		z /= val;
		return this;
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F divideLocal(@NotNull Point3F vec)
	{
		x /= vec.x;
		y /= vec.y;
		z /= vec.z;
		return this;
	}

	/**
	 * <p>interpolateLocal.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @param amt a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F interpolateLocal(float x, float y, float z, float amt)
	{
		this.x = (1F - amt) * this.x + amt * x;
		this.y = (1F - amt) * this.y + amt * y;
		this.z = (1F - amt) * this.z + amt * z;
		return this;
	}

	/**
	 * <p>interpolateLocal.</p>
	 *
	 * @param val a float
	 * @param amt a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F interpolateLocal(float val, float amt)
	{
		x = (1F - amt) * x + amt * val;
		y = (1F - amt) * y + amt * val;
		z = (1F - amt) * z + amt * val;
		return this;
	}

	/**
	 * <p>interpolateLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @param amt a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F interpolateLocal(@NotNull Point3F vec, float amt)
	{
		x = (1F - amt) * x + amt * vec.x;
		y = (1F - amt) * y + amt * vec.y;
		z = (1F - amt) * z + amt * vec.z;
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F add(float x, float y, float z)
	{
		return clone().addLocal(x, y, z);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F add(float val)
	{
		return clone().addLocal(val);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F add(@NotNull Point3F vec)
	{
		return clone().addLocal(vec);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F subtract(float x, float y, float z)
	{
		return clone().subtractLocal(x, y, z);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F subtract(float val)
	{
		return clone().subtractLocal(val);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F subtract(@NotNull Point3F vec)
	{
		return clone().subtractLocal(vec);
	}

	/**
	 * <p>multiply.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F multiply(float x, float y, float z)
	{
		return clone().multiplyLocal(x, y, z);
	}

	/**
	 * <p>multiply.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F multiply(float val)
	{
		return clone().multiplyLocal(val);
	}

	/**
	 * <p>multiply.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F multiply(@NotNull Point3F vec)
	{
		return clone().multiplyLocal(vec);
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F divide(float x, float y, float z)
	{
		return clone().divideLocal(x, y, z);
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F divide(float val)
	{
		return clone().divideLocal(val);
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F divide(@NotNull Point3F vec)
	{
		return clone().divideLocal(vec);
	}

	/**
	 * <p>interpolate.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @param z a float
	 * @param amt a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F interpolate(float x, float y, float z, float amt)
	{
		return clone().interpolateLocal(x, y, z, amt);
	}

	/**
	 * <p>interpolate.</p>
	 *
	 * @param val a float
	 * @param amt a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F interpolate(float val, float amt)
	{
		return clone().interpolateLocal(val, amt);
	}

	/**
	 * <p>interpolate.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @param amt a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F interpolate(@NotNull Point3F vec, float amt)
	{
		return clone().interpolateLocal(vec, amt);
	}

	/**
	 * <p>zero.</p>
	 *
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F zero()
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
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F set(float x, float y, float z)
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
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F set(float val)
	{
		x = val;
		y = val;
		z = val;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Point3F} object
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F set(@NotNull Point3F vec)
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
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F setX(float x)
	{
		this.x = x;
		return this;
	}

	/**
	 * <p>Setter for the field <code>y</code>.</p>
	 *
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F setY(float y)
	{
		this.y = y;
		return this;
	}

	/**
	 * <p>Setter for the field <code>z</code>.</p>
	 *
	 * @param z a float
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F setZ(float z)
	{
		this.z = z;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F set(float @NotNull [] arr)
	{
		x = arr[0];
		y = arr[1];
		z = arr[2];
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @param off a int
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F set(float @NotNull [] arr, int off)
	{
		x = arr[off];
		y = arr[off + 1];
		z = arr[off + 2];
		return this;
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
	public float @NotNull [] get()
	{
		return ArrayUtil.toFloatArray(x, y, z);
	}

	/**
	 * <p>absLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F absLocal()
	{
		set(Math.abs(x), Math.abs(y), Math.abs(z));
		return this;
	}

	/**
	 * <p>negateLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F negateLocal()
	{
		set(-x, -y, -z);
		return this;
	}

	/**
	 * <p>negativeLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F negativeLocal()
	{
		set(-Math.abs(x), -Math.abs(y), -Math.abs(z));
		return this;
	}

	/**
	 * <p>abs.</p>
	 *
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F abs()
	{
		return clone().absLocal();
	}

	/**
	 * <p>negate.</p>
	 *
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F negate()
	{
		return clone().negateLocal();
	}

	/**
	 * <p>negative.</p>
	 *
	 * @return a {@link com.hk.math.vector.Point3F} object
	 */
	@NotNull
	public Point3F negative()
	{
		return clone().negative();
	}

	/** {@inheritDoc} */
	@Override
	@NotNull
	public Point3F clone()
	{
		try
		{
			return (Point3F) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		int hash = 47;
		hash = hash * 19 + Float.floatToIntBits(x);
		hash = hash * 19 + Float.floatToIntBits(y);
		hash = hash * 19 + Float.floatToIntBits(z);
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	@NotNull
	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}

	private static final long serialVersionUID = -5534417407287157643L;
}
