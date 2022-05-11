package com.hk.math.vector;

import java.io.Serializable;
import com.hk.array.ArrayUtil;
import com.hk.math.FloatMath;
import com.hk.math.MathUtil;
import com.hk.math.Rand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>Vector2F class.</p>
 *
 * @author theKayani
 */
public final class Vector2F implements Serializable, Cloneable
{
	public float x, y;

	/**
	 * <p>Constructor for Vector2F.</p>
	 */
	public Vector2F()
	{}

	/**
	 * <p>Constructor for Vector2F.</p>
	 *
	 * @param x a float
	 * @param y a float
	 */
	public Vector2F(float x, float y)
	{
		set(x, y);
	}

	/**
	 * <p>Constructor for Vector2F.</p>
	 *
	 * @param value a float
	 */
	public Vector2F(float value)
	{
		set(value);
	}

	/**
	 * <p>Constructor for Vector2F.</p>
	 *
	 * @param array an array of {@link float} objects
	 */
	public Vector2F(float @NotNull [] array)
	{
		fromArray(array);
	}

	/**
	 * <p>Constructor for Vector2F.</p>
	 *
	 * @param copy a {@link com.hk.math.vector.Vector2F} object
	 */
	public Vector2F(@NotNull Vector2F copy)
	{
		set(copy);
	}

	/**
	 * <p>getIndx.</p>
	 *
	 * @param indx a int
	 * @return a float
	 */
	public float getIndx(int indx)
	{
		switch(indx)
		{
			case 0: return x;
			case 1: return y;
			default: throw new IllegalArgumentException("Out of bounds [0, 1]: " + indx);
		}
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
	 * <p>setIndx.</p>
	 *
	 * @param indx a int
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F setIndx(int indx, float val)
	{
		switch(indx)
		{
		case 0: x = val; return this;
		case 1: y = val; return this;
		default:
			throw new IllegalArgumentException("Out of bounds [0, 1]: " + indx);
		}
	}

	/**
	 * <p>Setter for the field <code>x</code>.</p>
	 *
	 * @param x a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F setX(float x)
	{
		this.x = x;
		return this;
	}

	/**
	 * <p>Setter for the field <code>y</code>.</p>
	 *
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F setY(float y)
	{
		this.y = y;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F set(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F set(float value)
	{
		x = y = value;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F set(@NotNull Vector2F vec)
	{
		x = vec.x;
		y = vec.y;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F addLocal(@NotNull Vector2F vec)
	{
		x += vec.x;
		y += vec.y;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F addLocal(float value)
	{
		x += value;
		y += value;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F addLocal(float x, float y)
	{
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F subtractLocal(@NotNull Vector2F vec)
	{
		x -= vec.x;
		y -= vec.y;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F subtractLocal(float value)
	{
		x -= value;
		y -= value;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F subtractLocal(float x, float y)
	{
		this.x -= x;
		this.y -= y;
		return this;
	}

	/**
	 * <p>multLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F multLocal(@NotNull Vector2F vec)
	{
		x *= vec.x;
		y *= vec.y;
		return this;
	}

	/**
	 * <p>multLocal.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F multLocal(float value)
	{
		x *= value;
		y *= value;
		return this;
	}

	/**
	 * <p>multLocal.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F multLocal(float x, float y)
	{
		this.x *= x;
		this.y *= y;
		return this;
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F divideLocal(@NotNull Vector2F vec)
	{
		x /= vec.x;
		y /= vec.y;
		return this;
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F divideLocal(float value)
	{
		x /= value;
		y /= value;
		return this;
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F divideLocal(float x, float y)
	{
		this.x /= x;
		this.y /= y;
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F add(@NotNull Vector2F vec)
	{
		return new Vector2F(x + vec.x, y + vec.y);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F add(float value)
	{
		return new Vector2F(x + value, y + value);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F add(float x, float y)
	{
		return new Vector2F(this.x + x, this.y + y);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F subtract(@NotNull Vector2F vec)
	{
		return new Vector2F(x - vec.x, y - vec.y);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F subtract(float value)
	{
		return new Vector2F(x - value, y - value);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F subtract(float x, float y)
	{
		return new Vector2F(this.x - x, this.y - y);
	}

	/**
	 * <p>mult.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F mult(@NotNull Vector2F vec)
	{
		return new Vector2F(x * vec.x, y * vec.y);
	}

	/**
	 * <p>mult.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F mult(float value)
	{
		return new Vector2F(x * value, y * value);
	}

	/**
	 * <p>mult.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F mult(float x, float y)
	{
		return new Vector2F(this.x * x, this.y * y);
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F divide(@NotNull Vector2F vec)
	{
		return new Vector2F(x / vec.x, y / vec.y);
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F divide(float value)
	{
		return new Vector2F(x / value, y / value);
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F divide(float x, float y)
	{
		return new Vector2F(this.x / x, this.y / y);
	}

	/**
	 * <p>normalize.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F normalize()
	{
		return new Vector2F(this).normalizeLocal();
	}

	/**
	 * <p>normalizeLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F normalizeLocal()
	{
		float l = length();
		return l != 0 ? divideLocal(l) : divideLocal(1);
	}

	/**
	 * <p>length.</p>
	 *
	 * @return a float
	 */
	public float length()
	{
		return MathUtil.hypot(x, y);
	}

	/**
	 * <p>lengthSquared.</p>
	 *
	 * @return a float
	 */
	public float lengthSquared()
	{
		return x * x + y * y;
	}

	/**
	 * <p>getAngle.</p>
	 *
	 * @return a float
	 */
	public float getAngle()
	{
		return FloatMath.atan2(x, y);
	}

	/**
	 * <p>toArray.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return an array of {@link float} objects
	 */
	public float @NotNull [] toArray(float @NotNull [] arr)
	{
		arr[0] = x;
		arr[1] = y;
		return arr;
	}

	/**
	 * <p>toArray.</p>
	 *
	 * @return an array of {@link float} objects
	 */
	public float @NotNull [] toArray()
	{
		return ArrayUtil.toFloatArray(x, y);
	}

	/**
	 * <p>fromArray.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F fromArray(float @NotNull [] arr)
	{
		x = arr[0];
		y = arr[1];
		return this;
	}

	/**
	 * <p>negate.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F negate()
	{
		return new Vector2F(-x, -y);
	}

	/**
	 * <p>negateLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F negateLocal()
	{
		set(-x, -y);
		return this;
	}

	/**
	 * <p>abs.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F abs()
	{
		return new Vector2F(Math.abs(x), Math.abs(y));
	}

	/**
	 * <p>absLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F absLocal()
	{
		set(Math.abs(x), Math.abs(y));
		return this;
	}

	/**
	 * <p>negative.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F negative()
	{
		return new Vector2F(-Math.abs(x), -Math.abs(y));
	}

	/**
	 * <p>negativeLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F negativeLocal()
	{
		set(-Math.abs(x), -Math.abs(y));
		return this;
	}

	/**
	 * <p>distanceSquared.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a float
	 */
	public float distanceSquared(@NotNull Vector2F vec)
	{
		return MathUtil.square(x - vec.x) + MathUtil.square(y - vec.y);
	}

	/**
	 * <p>distanceSquared.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a float
	 */
	public float distanceSquared(float x, float y)
	{
		return MathUtil.square(this.x - x) + MathUtil.square(this.y - y);
	}

	/**
	 * <p>distance.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a float
	 */
	public float distance(@NotNull Vector2F vec)
	{
		return MathUtil.hypot(x - vec.x, y - vec.y);
	}

	/**
	 * <p>distance.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a float
	 */
	public float distance(float x, float y)
	{
		return MathUtil.hypot(this.x - x, this.y - y);
	}

	/**
	 * <p>angleBetween.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a float
	 */
	public float angleBetween(@NotNull Vector2F vec)
	{
		return vec.getAngle() - getAngle();
	}

	/**
	 * <p>smallestAngleBetween.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a float
	 */
	public float smallestAngleBetween(@NotNull Vector2F vec)
	{
		return FloatMath.acos(dot(vec) / (length() * vec.length()));
	}

	/**
	 * <p>interpolate.</p>
	 *
	 * @param finalVec a {@link com.hk.math.vector.Vector2F} object
	 * @param changeAmnt a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F interpolate(@NotNull Vector2F finalVec, float changeAmnt)
	{
		return new Vector2F((1 - changeAmnt) * x + changeAmnt * finalVec.x, (1 - changeAmnt) * y + changeAmnt * finalVec.y);
	}

	/**
	 * <p>interpolateLocal.</p>
	 *
	 * @param finalVec a {@link com.hk.math.vector.Vector2F} object
	 * @param changeAmnt a float
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F interpolateLocal(@NotNull Vector2F finalVec, float changeAmnt)
	{
		x = (1 - changeAmnt) * x + changeAmnt * finalVec.x;
		y = (1 - changeAmnt) * y + changeAmnt * finalVec.y;
		return this;
	}

	/**
	 * <p>dot.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a float
	 */
	public float dot(@NotNull Vector2F vec)
	{
		return x * vec.x + y * vec.y;
	}

	/**
	 * <p>determinant.</p>
	 *
	 * @param v a {@link com.hk.math.vector.Vector2F} object
	 * @return a float
	 */
	public float determinant(@NotNull Vector2F v)
	{
		return x * v.y - y * v.x;
	}

	/**
	 * <p>zero.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F zero()
	{
		set(0);
		return this;
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
	 * <p>rotateAround.</p>
	 *
	 * @param angle a float
	 * @param cw a boolean
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public Vector2F rotateAround(float angle, boolean cw)
	{
		angle = cw ? angle : -angle;
		float newX = FloatMath.cos(angle) * x - FloatMath.sin(angle) * y;
		float newY = FloatMath.sin(angle) * x + FloatMath.cos(angle) * y;
		x = newX;
		y = newY;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public Vector2F clone()
	{
		try
		{
			return (Vector2F) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 47 * hash + Float.floatToIntBits(x);
		hash = 47 * hash + Float.floatToIntBits(y);
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Vector2F && Float.floatToIntBits(x) == Float.floatToIntBits(((Vector2F) obj).x) && Float.floatToIntBits(y) == Float.floatToIntBits(((Vector2F) obj).y);
	}

	/** {@inheritDoc} */
	@Override
	@NotNull
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

	/**
	 * <p>isValidVector.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a boolean
	 */
	public static boolean isValidVector(@Nullable Vector2F vec)
	{
		return vec != null && !(Float.isNaN(vec.x) || Float.isNaN(vec.y) || Float.isInfinite(vec.x) || Float.isInfinite(vec.y));
	}

	/**
	 * <p>isUnitVector.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Vector2F} object
	 * @return a boolean
	 */
	public static boolean isUnitVector(@NotNull Vector2F vec)
	{
		return vec.x > 0.99F && vec.y > 0.99F && vec.x < 1.01F && vec.y < 1.01F;
	}

	/**
	 * <p>randUnitVector.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	@NotNull
	public static Vector2F randUnitVector()
	{
		float ang = Rand.nextFloat(2F * FloatMath.PI);
		return new Vector2F(FloatMath.cos(ang), FloatMath.sin(ang));
	}

	private static final long serialVersionUID = 5989148081015523353L;
}