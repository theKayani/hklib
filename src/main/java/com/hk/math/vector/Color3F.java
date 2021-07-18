package com.hk.math.vector;

import java.io.Serializable;

import com.hk.array.ArrayUtil;
import com.hk.math.MathUtil;
import com.hk.math.Rand;

public final class Color3F implements Serializable, Cloneable
{
	public float r, g, b, a;

	public Color3F()
	{
		a = 1F;
	}
	
	public Color3F(int rgb)
	{
		set(rgb);
	}
	
	public Color3F(int argb, boolean alpha)
	{
		set(argb, alpha);
	}

	public Color3F(int r, int g, int b)
	{
		set(r, g, b);
	}

	public Color3F(int r, int g, int b, int a)
	{
		set(r, g, b, a);
	}

	public Color3F(float r, float g, float b)
	{
		set(r, g, b, 1F);
	}

	public Color3F(float r, float g, float b, float a)
	{
		set(r, g, b, a);
	}

	public Color3F(float value)
	{
		set(value);
	}

	public Color3F(float[] array)
	{
		fromArray(array);
	}

	public Color3F(Color3F copy)
	{
		set(copy);
	}

	public float getRed()
	{
		return r;
	}

	public float getGreen()
	{
		return g;
	}

	public float getBlue()
	{
		return b;
	}

	public float getAlpha()
	{
		return a;
	}

	public int getRedInt()
	{
		return (int) (r * 255F);
	}

	public int getGreenInt()
	{
		return (int) (g * 255F);
	}

	public int getBlueInt()
	{
		return (int) (b * 255F);
	}

	public int getAlphaInt()
	{
		return (int) (a * 255F);
	}

	public Color3F setR(float r)
	{
		this.r = r;
		return this;
	}

	public Color3F setG(float g)
	{
		this.g = g;
		return this;
	}

	public Color3F setB(float b)
	{
		this.b = b;
		return this;
	}

	public Color3F setA(float a)
	{
		this.a = a;
		return this;
	}
	
	public Color3F set(int rgb)
	{
		return set(rgb, false);
	}
	
	public Color3F set(int argb, boolean alpha)
	{
		r = ((argb >> 16) & 0xFF) / 255F;
		g = ((argb >> 8) & 0xFF) / 255F;
		b = (argb & 0xFF) / 255F;
		a = alpha ? ((argb >> 24) & 0xFF) / 255F : 1F;
		return this;
	}

	public Color3F set(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		return this;
	}

	public Color3F set(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		return this;
	}

	public Color3F set(float value)
	{
		r = g = b = a = value;
		return this;
	}

	public Color3F set(Color3F vec)
	{
		r = vec.r;
		g = vec.g;
		b = vec.b;
		a = vec.a;
		return this;
	}

	public Color3F addLocal(Color3F vec)
	{
		r += vec.r;
		g += vec.g;
		b += vec.b;
		a += vec.a;
		return this;
	}

	public Color3F addLocal(float value)
	{
		r += value;
		g += value;
		b += value;
		a += value;
		return this;
	}

	public Color3F addLocal(float r, float g, float b)
	{
		this.r += r;
		this.g += g;
		this.b += b;
		return this;
	}

	public Color3F addLocal(float r, float g, float b, float a)
	{
		this.r += r;
		this.g += g;
		this.b += b;
		this.a += a;
		return this;
	}

	public Color3F subtractLocal(Color3F vec)
	{
		r -= vec.r;
		g -= vec.g;
		b -= vec.b;
		a -= vec.a;
		return this;
	}

	public Color3F subtractLocal(float value)
	{
		r -= value;
		g -= value;
		b -= value;
		a -= value;
		return this;
	}

	public Color3F subtractLocal(float r, float g, float b)
	{
		this.r -= r;
		this.g -= g;
		this.b -= b;
		return this;
	}

	public Color3F subtractLocal(float r, float g, float b, float a)
	{
		this.r -= r;
		this.g -= g;
		this.b -= b;
		this.a -= a;
		return this;
	}

	public Color3F multLocal(Color3F vec)
	{
		r *= vec.r;
		g *= vec.g;
		b *= vec.b;
		a *= vec.a;
		return this;
	}

	public Color3F multLocal(float value)
	{
		r *= value;
		g *= value;
		b *= value;
		a *= value;
		return this;
	}

	public Color3F multLocal(float r, float g, float b)
	{
		this.r *= r;
		this.g *= g;
		this.b *= b;
		return this;
	}

	public Color3F multLocal(float r, float g, float b, float a)
	{
		this.r *= r;
		this.g *= g;
		this.b *= b;
		this.a *= a;
		return this;
	}

	public Color3F divideLocal(Color3F vec)
	{
		r /= vec.r;
		g /= vec.g;
		b /= vec.b;
		a /= vec.a;
		return this;
	}

	public Color3F divideLocal(float value)
	{
		r /= value;
		g /= value;
		b /= value;
		a /= value;
		return this;
	}

	public Color3F divideLocal(float r, float g, float b)
	{
		this.r /= r;
		this.g /= g;
		this.b /= b;
		return this;
	}

	public Color3F divideLocal(float r, float g, float b, float a)
	{
		this.r /= r;
		this.g /= g;
		this.b /= b;
		this.a /= a;
		return this;
	}

	public Color3F add(Color3F vec)
	{
		return clone().addLocal(vec);
	}

	public Color3F add(float value)
	{
		return clone().addLocal(value);
	}

	public Color3F add(float r, float g, float b)
	{
		return clone().addLocal(r, g, b);
	}

	public Color3F add(float r, float g, float b, float a)
	{
		return clone().addLocal(r, g, b, a);
	}

	public Color3F subtract(Color3F vec)
	{
		return clone().subtractLocal(vec);
	}

	public Color3F subtract(float value)
	{
		return clone().subtractLocal(value);
	}

	public Color3F subtract(float r, float g, float b)
	{
		return clone().subtractLocal(r, g, b);
	}

	public Color3F subtract(float r, float g, float b, float a)
	{
		return clone().subtractLocal(r, g, b, a);
	}

	public Color3F mult(Color3F vec)
	{
		return clone().multLocal(vec);
	}

	public Color3F mult(float value)
	{
		return clone().multLocal(value);
	}

	public Color3F mult(float r, float g, float b)
	{
		return clone().multLocal(r, g, b);
	}

	public Color3F mult(float r, float g, float b, float a)
	{
		return clone().multLocal(r, g, b, a);
	}

	public Color3F divide(Color3F vec)
	{
		return clone().divideLocal(vec);
	}

	public Color3F divide(float value)
	{
		return clone().divideLocal(value);
	}

	public Color3F divide(float r, float g, float b)
	{
		return clone().divideLocal(r, g, b);
	}

	public Color3F divide(float r, float g, float b, float a)
	{
		return clone().divideLocal(r, g, b, a);
	}

	public float[] toArray(float[] arr)
	{
		arr[0] = r;
		arr[1] = g;
		arr[2] = b;
		arr[3] = a;
		return arr;
	}

	public float[] toArray()
	{
		return ArrayUtil.toFloatArray(r, g, b, a);
	}

	public Color3F fromArray(float[] arr)
	{
		return fromArray(arr, 0);
	}

	public Color3F fromArray(float[] arr, int off)
	{
		r = arr[off + 0];
		g = arr[off + 1];
		b = arr[off + 2];
		a = arr[off + 3];
		return this;
	}

	public Color3F interpolate(Color3F finalVec, float changeAmnt)
	{
		return clone().interpolateLocal(finalVec, changeAmnt);
	}

	public Color3F interpolateLocal(Color3F finalVec, float changeAmnt)
	{
		r = (1 - changeAmnt) * r + changeAmnt * finalVec.r;
		g = (1 - changeAmnt) * g + changeAmnt * finalVec.g;
		b = (1 - changeAmnt) * b + changeAmnt * finalVec.b;
		a = (1 - changeAmnt) * a + changeAmnt * finalVec.a;
		return this;
	}

	public int toRGBA()
	{
		int rgba = 0;
		rgba |= getRedInt() << 24;
		rgba |= getGreenInt() << 16;
		rgba |= getBlueInt() << 8;
		rgba |= getAlphaInt();
		return rgba;
	}

	public int toARGB()
	{
		int argb = 0;
		argb |= getRedInt() << 16;
		argb |= getGreenInt() << 8;
		argb |= getBlueInt();
		argb |= getAlphaInt() << 24;
		return argb;
	}

	public int toRGB()
	{
		int rgb = 0;
		rgb |= getRedInt() << 16;
		rgb |= getGreenInt() << 8;
		rgb |= getBlueInt();
		return rgb | 0xFF000000;
	}

	public String toHexString()
	{
		String s = Integer.toHexString(toARGB());
		while (s.length() < 8)
		{
			s = '0' + s;
		}
		return s.toUpperCase();
	}

	public Color3F clamp()
	{
		r = MathUtil.between(0F, r, 1F);
		g = MathUtil.between(0F, g, 1F);
		b = MathUtil.between(0F, b, 1F);
		a = MathUtil.between(0F, a, 1F);
		return this;
	}

	@Override
	public Color3F clone()
	{
		return new Color3F(this);
	}

	@Override
	public int hashCode()
	{
		int hash = 11;
		hash = 41 * hash + Float.floatToIntBits(r);
		hash = 41 * hash + Float.floatToIntBits(g);
		hash = 41 * hash + Float.floatToIntBits(b);
		hash = 41 * hash + Float.floatToIntBits(a);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Color3F && Float.floatToIntBits(r) == Float.floatToIntBits(((Color3F) obj).r) && Float.floatToIntBits(g) == Float.floatToIntBits(((Color3F) obj).g) && Float.floatToIntBits(b) == Float.floatToIntBits(((Color3F) obj).b) && Float.floatToIntBits(a) == Float.floatToIntBits(((Color3F) obj).a);
	}

	@Override
	public String toString()
	{
		return "(" + r + ", " + g + ", " + b + ", " + a + ")";
	}
	
	public static Color3F randColor()
	{
		return new Color3F(Rand.nextFloat(), Rand.nextFloat(), Rand.nextFloat());
	}

	public static final Color3F WHITE = new Color3F(0xFFFFFF);
	public static final Color3F BLACK = new Color3F(0x000000);
	public static final Color3F RED = new Color3F(0xFF0000);
	public static final Color3F GREEN = new Color3F(0x00FF00);
	public static final Color3F BLUE = new Color3F(0x0000FF);
	public static final Color3F YELLOW = new Color3F(0xFFFF00);
	public static final Color3F CYAN = new Color3F(0x00FFFF);
	public static final Color3F PURPLE = new Color3F(0xFF00FF);
	private static final long serialVersionUID = -3172866587807242195L;
}
