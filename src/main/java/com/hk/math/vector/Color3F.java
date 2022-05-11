package com.hk.math.vector;

import java.io.Serializable;
import java.util.Locale;

import com.hk.array.ArrayUtil;
import com.hk.math.MathUtil;
import com.hk.math.Rand;
import com.hk.str.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * <p>Color3F class.</p>
 *
 * @author theKayani
 */
public final class Color3F implements Serializable, Cloneable
{
	@Range(from=0, to=1)
	public float r, g, b, a;

	/**
	 * <p>Constructor for Color3F.</p>
	 */
	public Color3F()
	{
		a = 1F;
	}

	/**
	 * <p>Constructor for Color3F.</p>
	 *
	 * @param rgb a int
	 */
	public Color3F(int rgb)
	{
		set(rgb);
	}

	/**
	 * <p>Constructor for Color3F.</p>
	 *
	 * @param argb a int
	 * @param alpha a boolean
	 */
	public Color3F(int argb, boolean alpha)
	{
		set(argb, alpha);
	}

	/**
	 * <p>Constructor for Color3F.</p>
	 *
	 * @param r a int
	 * @param g a int
	 * @param b a int
	 */
	public Color3F(int r, int g, int b)
	{
		set(r, g, b);
	}

	/**
	 * <p>Constructor for Color3F.</p>
	 *
	 * @param r a int
	 * @param g a int
	 * @param b a int
	 * @param a a int
	 */
	public Color3F(int r, int g, int b, int a)
	{
		set(r, g, b, a);
	}

	/**
	 * <p>Constructor for Color3F.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 */
	public Color3F(float r, float g, float b)
	{
		set(r, g, b, 1F);
	}

	/**
	 * <p>Constructor for Color3F.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @param a a float
	 */
	public Color3F(float r, float g, float b, float a)
	{
		set(r, g, b, a);
	}

	/**
	 * <p>Constructor for Color3F.</p>
	 *
	 * @param value a float
	 */
	public Color3F(float value)
	{
		set(value);
	}

	/**
	 * <p>Constructor for Color3F.</p>
	 *
	 * @param array an array of {@link float} objects
	 */
	public Color3F(float @NotNull [] array)
	{
		fromArray(array);
	}

	/**
	 * <p>Constructor for Color3F.</p>
	 *
	 * @param copy a {@link com.hk.math.vector.Color3F} object
	 */
	public Color3F(@NotNull Color3F copy)
	{
		set(copy);
	}

	/**
	 * <p>getRed.</p>
	 *
	 * @return a float
	 */
	public float getRed()
	{
		return r;
	}

	/**
	 * <p>getGreen.</p>
	 *
	 * @return a float
	 */
	public float getGreen()
	{
		return g;
	}

	/**
	 * <p>getBlue.</p>
	 *
	 * @return a float
	 */
	public float getBlue()
	{
		return b;
	}

	/**
	 * <p>getAlpha.</p>
	 *
	 * @return a float
	 */
	public float getAlpha()
	{
		return a;
	}

	/**
	 * <p>getRedInt.</p>
	 *
	 * @return a int
	 */
	public int getRedInt()
	{
		return (int) (r * 255F);
	}

	/**
	 * <p>getGreenInt.</p>
	 *
	 * @return a int
	 */
	public int getGreenInt()
	{
		return (int) (g * 255F);
	}

	/**
	 * <p>getBlueInt.</p>
	 *
	 * @return a int
	 */
	public int getBlueInt()
	{
		return (int) (b * 255F);
	}

	/**
	 * <p>getAlphaInt.</p>
	 *
	 * @return a int
	 */
	public int getAlphaInt()
	{
		return (int) (a * 255F);
	}

	/**
	 * <p>Setter for the field <code>r</code>.</p>
	 *
	 * @param r a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F setR(float r)
	{
		this.r = r;
		return this;
	}

	/**
	 * <p>Setter for the field <code>g</code>.</p>
	 *
	 * @param g a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F setG(float g)
	{
		this.g = g;
		return this;
	}

	/**
	 * <p>Setter for the field <code>b</code>.</p>
	 *
	 * @param b a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F setB(float b)
	{
		this.b = b;
		return this;
	}

	/**
	 * <p>Setter for the field <code>a</code>.</p>
	 *
	 * @param a a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F setA(float a)
	{
		this.a = a;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param rgb a int
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F set(int rgb)
	{
		return set(rgb, false);
	}

	/**
	 * <p>set.</p>
	 *
	 * @param argb a int
	 * @param alpha a boolean
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F set(int argb, boolean alpha)
	{
		r = ((argb >> 16) & 0xFF) / 255F;
		g = ((argb >> 8) & 0xFF) / 255F;
		b = (argb & 0xFF) / 255F;
		a = alpha ? ((argb >> 24) & 0xFF) / 255F : 1F;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F set(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @param a a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F set(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F set(float value)
	{
		r = g = b = a = value;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Color3F} object
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F set(@NotNull Color3F vec)
	{
		r = vec.r;
		g = vec.g;
		b = vec.b;
		a = vec.a;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Color3F} object
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F addLocal(@NotNull Color3F vec)
	{
		r += vec.r;
		g += vec.g;
		b += vec.b;
		a += vec.a;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F addLocal(float value)
	{
		r += value;
		g += value;
		b += value;
		a += value;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F addLocal(float r, float g, float b)
	{
		this.r += r;
		this.g += g;
		this.b += b;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @param a a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F addLocal(float r, float g, float b, float a)
	{
		this.r += r;
		this.g += g;
		this.b += b;
		this.a += a;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Color3F} object
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F subtractLocal(@NotNull Color3F vec)
	{
		r -= vec.r;
		g -= vec.g;
		b -= vec.b;
		a -= vec.a;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F subtractLocal(float value)
	{
		r -= value;
		g -= value;
		b -= value;
		a -= value;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F subtractLocal(float r, float g, float b)
	{
		this.r -= r;
		this.g -= g;
		this.b -= b;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @param a a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F subtractLocal(float r, float g, float b, float a)
	{
		this.r -= r;
		this.g -= g;
		this.b -= b;
		this.a -= a;
		return this;
	}

	/**
	 * <p>multLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Color3F} object
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F multLocal(@NotNull Color3F vec)
	{
		r *= vec.r;
		g *= vec.g;
		b *= vec.b;
		a *= vec.a;
		return this;
	}

	/**
	 * <p>multLocal.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F multLocal(float value)
	{
		r *= value;
		g *= value;
		b *= value;
		a *= value;
		return this;
	}

	/**
	 * <p>multLocal.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F multLocal(float r, float g, float b)
	{
		this.r *= r;
		this.g *= g;
		this.b *= b;
		return this;
	}

	/**
	 * <p>multLocal.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @param a a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F multLocal(float r, float g, float b, float a)
	{
		this.r *= r;
		this.g *= g;
		this.b *= b;
		this.a *= a;
		return this;
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Color3F} object
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F divideLocal(@NotNull Color3F vec)
	{
		r /= vec.r;
		g /= vec.g;
		b /= vec.b;
		a /= vec.a;
		return this;
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F divideLocal(float value)
	{
		r /= value;
		g /= value;
		b /= value;
		a /= value;
		return this;
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F divideLocal(float r, float g, float b)
	{
		this.r /= r;
		this.g /= g;
		this.b /= b;
		return this;
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @param a a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F divideLocal(float r, float g, float b, float a)
	{
		this.r /= r;
		this.g /= g;
		this.b /= b;
		this.a /= a;
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Color3F} object
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F add(@NotNull Color3F vec)
	{
		return clone().addLocal(vec);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F add(float value)
	{
		return clone().addLocal(value);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F add(float r, float g, float b)
	{
		return clone().addLocal(r, g, b);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @param a a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F add(float r, float g, float b, float a)
	{
		return clone().addLocal(r, g, b, a);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Color3F} object
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F subtract(Color3F vec)
	{
		return clone().subtractLocal(vec);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F subtract(float value)
	{
		return clone().subtractLocal(value);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F subtract(float r, float g, float b)
	{
		return clone().subtractLocal(r, g, b);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @param a a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F subtract(float r, float g, float b, float a)
	{
		return clone().subtractLocal(r, g, b, a);
	}

	/**
	 * <p>mult.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Color3F} object
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F mult(@NotNull Color3F vec)
	{
		return clone().multLocal(vec);
	}

	/**
	 * <p>mult.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F mult(float value)
	{
		return clone().multLocal(value);
	}

	/**
	 * <p>mult.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F mult(float r, float g, float b)
	{
		return clone().multLocal(r, g, b);
	}

	/**
	 * <p>mult.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @param a a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F mult(float r, float g, float b, float a)
	{
		return clone().multLocal(r, g, b, a);
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param vec a {@link com.hk.math.vector.Color3F} object
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F divide(Color3F vec)
	{
		return clone().divideLocal(vec);
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param value a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F divide(float value)
	{
		return clone().divideLocal(value);
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F divide(float r, float g, float b)
	{
		return clone().divideLocal(r, g, b);
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param r a float
	 * @param g a float
	 * @param b a float
	 * @param a a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F divide(float r, float g, float b, float a)
	{
		return clone().divideLocal(r, g, b, a);
	}

	/**
	 * <p>toArray.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return an array of {@link float} objects
	 */
	public float @NotNull [] toArray(float @NotNull [] arr)
	{
		arr[0] = r;
		arr[1] = g;
		arr[2] = b;
		arr[3] = a;
		return arr;
	}

	/**
	 * <p>toArray.</p>
	 *
	 * @return an array of {@link float} objects
	 */
	public float @NotNull [] toArray()
	{
		return ArrayUtil.toFloatArray(r, g, b, a);
	}

	/**
	 * <p>fromArray.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F fromArray(float @NotNull [] arr)
	{
		return fromArray(arr, 0);
	}

	/**
	 * <p>fromArray.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @param off a int
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F fromArray(float @NotNull [] arr, int off)
	{
		r = arr[off    ];
		g = arr[off + 1];
		b = arr[off + 2];
		a = arr[off + 3];
		return this;
	}

	/**
	 * <p>interpolate.</p>
	 *
	 * @param finalVec a {@link com.hk.math.vector.Color3F} object
	 * @param changeAmnt a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F interpolate(@NotNull Color3F finalVec, float changeAmnt)
	{
		return clone().interpolateLocal(finalVec, changeAmnt);
	}

	/**
	 * <p>interpolateLocal.</p>
	 *
	 * @param finalVec a {@link com.hk.math.vector.Color3F} object
	 * @param changeAmnt a float
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F interpolateLocal(@NotNull Color3F finalVec, float changeAmnt)
	{
		r = (1 - changeAmnt) * r + changeAmnt * finalVec.r;
		g = (1 - changeAmnt) * g + changeAmnt * finalVec.g;
		b = (1 - changeAmnt) * b + changeAmnt * finalVec.b;
		a = (1 - changeAmnt) * a + changeAmnt * finalVec.a;
		return this;
	}

	/**
	 * <p>toRGBA.</p>
	 *
	 * @return a int
	 */
	public int toRGBA()
	{
		int rgba = 0;
		rgba |= getRedInt() << 24;
		rgba |= getGreenInt() << 16;
		rgba |= getBlueInt() << 8;
		rgba |= getAlphaInt();
		return rgba;
	}

	/**
	 * <p>toARGB.</p>
	 *
	 * @return a int
	 */
	public int toARGB()
	{
		int argb = 0;
		argb |= getRedInt() << 16;
		argb |= getGreenInt() << 8;
		argb |= getBlueInt();
		argb |= getAlphaInt() << 24;
		return argb;
	}

	/**
	 * <p>toRGB.</p>
	 *
	 * @return a int
	 */
	public int toRGB()
	{
		int rgb = 0;
		rgb |= getRedInt() << 16;
		rgb |= getGreenInt() << 8;
		rgb |= getBlueInt();
		return rgb | 0xFF000000;
	}

	/**
	 * <p>toHexString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	@NotNull
	public String toHexString()
	{
		String s = Integer.toHexString(toARGB()).toUpperCase(Locale.ROOT);
		return StringUtil.repeat("0", 8 - s.length()) + s;
	}

	/**
	 * <p>clamp.</p>
	 *
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public Color3F clamp()
	{
		r = MathUtil.between(0F, r, 1F);
		g = MathUtil.between(0F, g, 1F);
		b = MathUtil.between(0F, b, 1F);
		a = MathUtil.between(0F, a, 1F);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public Color3F clone()
	{
		try
		{
			return (Color3F) super.clone();
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
		int hash = 11;
		hash = 41 * hash + Float.floatToIntBits(r);
		hash = 41 * hash + Float.floatToIntBits(g);
		hash = 41 * hash + Float.floatToIntBits(b);
		hash = 41 * hash + Float.floatToIntBits(a);
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(@Nullable Object obj)
	{
		return obj instanceof Color3F && Float.floatToIntBits(r) == Float.floatToIntBits(((Color3F) obj).r) && Float.floatToIntBits(g) == Float.floatToIntBits(((Color3F) obj).g) && Float.floatToIntBits(b) == Float.floatToIntBits(((Color3F) obj).b) && Float.floatToIntBits(a) == Float.floatToIntBits(((Color3F) obj).a);
	}

	/** {@inheritDoc} */
	@Override
	@NotNull
	public String toString()
	{
		return "(" + r + ", " + g + ", " + b + ", " + a + ")";
	}

	/**
	 * <p>randColor.</p>
	 *
	 * @return a {@link com.hk.math.vector.Color3F} object
	 */
	@NotNull
	public static Color3F randColor()
	{
		return new Color3F(Rand.nextFloat(), Rand.nextFloat(), Rand.nextFloat());
	}

	/** Constant <code>WHITE</code> */
	public static final Color3F WHITE = new Color3F(0xFFFFFF);
	/** Constant <code>BLACK</code> */
	public static final Color3F BLACK = new Color3F(0x000000);
	/** Constant <code>RED</code> */
	public static final Color3F RED = new Color3F(0xFF0000);
	/** Constant <code>GREEN</code> */
	public static final Color3F GREEN = new Color3F(0x00FF00);
	/** Constant <code>BLUE</code> */
	public static final Color3F BLUE = new Color3F(0x0000FF);
	/** Constant <code>YELLOW</code> */
	public static final Color3F YELLOW = new Color3F(0xFFFF00);
	/** Constant <code>CYAN</code> */
	public static final Color3F CYAN = new Color3F(0x00FFFF);
	/** Constant <code>PURPLE</code> */
	public static final Color3F PURPLE = new Color3F(0xFF00FF);
	private static final long serialVersionUID = -3172866587807242195L;
}