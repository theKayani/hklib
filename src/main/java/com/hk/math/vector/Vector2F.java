package com.hk.math.vector;

import java.io.Serializable;
import com.hk.array.ArrayUtil;
import com.hk.math.FloatMath;
import com.hk.math.MathUtil;
import com.hk.math.Rand;

public final class Vector2F implements Serializable, Cloneable
{
	public float x, y;

	public Vector2F()
	{}

	public Vector2F(float x, float y)
	{
		set(x, y);
	}

	public Vector2F(float value)
	{
		set(value);
	}

	public Vector2F(float[] array)
	{
		fromArray(array);
	}

	public Vector2F(Vector2F copy)
	{
		set(copy);
	}
	
	public float getIndx(int indx)
	{
		switch(indx)
		{
			case 0: return x;
			case 1: return y;
			default: throw new IllegalArgumentException("Out of bounds [0, 1]: " + indx);
		}
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}
	
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

	public Vector2F setX(float x)
	{
		this.x = x;
		return this;
	}

	public Vector2F setY(float y)
	{
		this.y = y;
		return this;
	}

	public Vector2F set(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2F set(float value)
	{
		x = y = value;
		return this;
	}

	public Vector2F set(Vector2F vec)
	{
		x = vec.x;
		y = vec.y;
		return this;
	}

	public Vector2F addLocal(Vector2F vec)
	{
		x += vec.x;
		y += vec.y;
		return this;
	}

	public Vector2F addLocal(float value)
	{
		x += value;
		y += value;
		return this;
	}

	public Vector2F addLocal(float x, float y)
	{
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2F subtractLocal(Vector2F vec)
	{
		x -= vec.x;
		y -= vec.y;
		return this;
	}

	public Vector2F subtractLocal(float value)
	{
		x -= value;
		y -= value;
		return this;
	}

	public Vector2F subtractLocal(float x, float y)
	{
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector2F multLocal(Vector2F vec)
	{
		x *= vec.x;
		y *= vec.y;
		return this;
	}

	public Vector2F multLocal(float value)
	{
		x *= value;
		y *= value;
		return this;
	}

	public Vector2F multLocal(float x, float y)
	{
		this.x *= x;
		this.y *= y;
		return this;
	}

	public Vector2F divideLocal(Vector2F vec)
	{
		x /= vec.x;
		y /= vec.y;
		return this;
	}

	public Vector2F divideLocal(float value)
	{
		x /= value;
		y /= value;
		return this;
	}

	public Vector2F divideLocal(float x, float y)
	{
		this.x /= x;
		this.y /= y;
		return this;
	}

	public Vector2F add(Vector2F vec)
	{
		return new Vector2F(x + vec.x, y + vec.y);
	}

	public Vector2F add(float value)
	{
		return new Vector2F(x + value, y + value);
	}

	public Vector2F add(float x, float y)
	{
		return new Vector2F(this.x + x, this.y + y);
	}

	public Vector2F subtract(Vector2F vec)
	{
		return new Vector2F(x - vec.x, y - vec.y);
	}

	public Vector2F subtract(float value)
	{
		return new Vector2F(x - value, y - value);
	}

	public Vector2F subtract(float x, float y)
	{
		return new Vector2F(this.x - x, this.y - y);
	}

	public Vector2F mult(Vector2F vec)
	{
		return new Vector2F(x * vec.x, y * vec.y);
	}

	public Vector2F mult(float value)
	{
		return new Vector2F(x * value, y * value);
	}

	public Vector2F mult(float x, float y)
	{
		return new Vector2F(this.x * x, this.y * y);
	}

	public Vector2F divide(Vector2F vec)
	{
		return new Vector2F(x / vec.x, y / vec.y);
	}

	public Vector2F divide(float value)
	{
		return new Vector2F(x / value, y / value);
	}

	public Vector2F divide(float x, float y)
	{
		return new Vector2F(this.x / x, this.y / y);
	}

	public Vector2F normalize()
	{
		return new Vector2F(this).normalizeLocal();
	}

	public Vector2F normalizeLocal()
	{
		float l = length();
		return l != 0 ? divideLocal(l) : divideLocal(1);
	}

	public float length()
	{
		return MathUtil.hypot(x, y);
	}

	public float lengthSquared()
	{
		return x * x + y * y;
	}

	public float getAngle()
	{
		return FloatMath.atan2(x, y);
	}

	public float[] toArray(float[] arr)
	{
		arr[0] = x;
		arr[1] = y;
		return arr;
	}

	public float[] toArray()
	{
		return ArrayUtil.toFloatArray(x, y);
	}

	public Vector2F fromArray(float[] arr)
	{
		x = arr[0];
		y = arr[1];
		return this;
	}

	public Vector2F negate()
	{
		return new Vector2F(-x, -y);
	}

	public Vector2F negateLocal()
	{
		set(-x, -y);
		return this;
	}

	public Vector2F abs()
	{
		return new Vector2F(Math.abs(x), Math.abs(y));
	}

	public Vector2F absLocal()
	{
		set(Math.abs(x), Math.abs(y));
		return this;
	}

	public Vector2F negative()
	{
		return new Vector2F(-Math.abs(x), -Math.abs(y));
	}

	public Vector2F negativeLocal()
	{
		set(-Math.abs(x), -Math.abs(y));
		return this;
	}

	public float distanceSquared(Vector2F vec)
	{
		return MathUtil.square(x - vec.x) + MathUtil.square(y - vec.y);
	}

	public float distanceSquared(float x, float y)
	{
		return MathUtil.square(this.x - x) + MathUtil.square(this.y - y);
	}

	public float distance(Vector2F vec)
	{
		return MathUtil.hypot(x - vec.x, y - vec.y);
	}

	public float distance(float x, float y)
	{
		return MathUtil.hypot(this.x - x, this.y - y);
	}

	public float angleBetween(Vector2F vec)
	{
		return vec.getAngle() - getAngle();
	}

	public float smallestAngleBetween(Vector2F vec)
	{
		return FloatMath.acos(dot(vec) / (length() * vec.length()));
	}

	public Vector2F interpolate(Vector2F finalVec, float changeAmnt)
	{
		return new Vector2F((1 - changeAmnt) * x + changeAmnt * finalVec.x, (1 - changeAmnt) * y + changeAmnt * finalVec.y);
	}

	public Vector2F interpolateLocal(Vector2F finalVec, float changeAmnt)
	{
		x = (1 - changeAmnt) * x + changeAmnt * finalVec.x;
		y = (1 - changeAmnt) * y + changeAmnt * finalVec.y;
		return this;
	}

	public float dot(Vector2F vec)
	{
		return x * vec.x + y * vec.y;
	}

	public float determinant(Vector2F v)
	{
		return x * v.y - y * v.x;
	}

	public Vector2F zero()
	{
		set(0);
		return this;
	}

	public boolean isValidVector()
	{
		return isValidVector(this);
	}

	public boolean isUnitVector()
	{
		return isUnitVector(this);
	}

	public Vector2F rotateAround(float angle, boolean cw)
	{
		angle = cw ? angle : -angle;
		float newX = FloatMath.cos(angle) * x - FloatMath.sin(angle) * y;
		float newY = FloatMath.sin(angle) * x + FloatMath.cos(angle) * y;
		x = newX;
		y = newY;
		return this;
	}

	@Override
	public Vector2F clone()
	{
		return new Vector2F(this);
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 47 * hash + Float.floatToIntBits(x);
		hash = 47 * hash + Float.floatToIntBits(y);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Vector2F ? Float.floatToIntBits(x) == Float.floatToIntBits(((Vector2F) obj).x) && Float.floatToIntBits(y) == Float.floatToIntBits(((Vector2F) obj).y) : false;
	}

	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

	public static boolean isValidVector(Vector2F vec)
	{
		return vec == null ? false : !(Float.isNaN(vec.x) || Float.isNaN(vec.y) || Float.isInfinite(vec.x) || Float.isInfinite(vec.y));
	}

	public static boolean isUnitVector(Vector2F vec)
	{
		return vec == null ? false : vec.x > 0.99F && vec.y > 0.99F && vec.x < 1.01F && vec.y < 1.01F;
	}

	public static Vector2F randUnitVector()
	{
		float ang = Rand.nextFloat(2F * FloatMath.PI);
		return new Vector2F(FloatMath.cos(ang), FloatMath.sin(ang));
	}

	private static final long serialVersionUID = 5989148081015523353L;
}
