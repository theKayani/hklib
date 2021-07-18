package com.hk.math.vector;

import java.io.Serializable;

import com.hk.array.ArrayUtil;

public class TexCoord4F implements Cloneable, Serializable
{
	public float ux, uy, vx, vy;
	
	public TexCoord4F()
	{
		set(0);
	}
	
	public TexCoord4F(TexCoord4F tc)
	{
		set(tc);
	}
	
	public TexCoord4F(float val)
	{
		set(val);
	}
	
	public TexCoord4F(float ux, float uy, float vx, float vy)
	{
		set(ux, uy, vx, vy);
	}
	
	public TexCoord4F add(float ux, float uy, float vx, float vy)
	{
		this.ux += ux;
		this.uy += uy;
		this.vx += vx;
		this.vy += vy;
		return this;
	}

	public TexCoord4F add(float val)
	{
		this.ux += val;
		this.uy += val;
		this.vx += val;
		this.vy += val;
		return this;
	}

	public TexCoord4F add(TexCoord4F tc)
	{
		this.ux += tc.ux;
		this.uy += tc.uy;
		this.vx += tc.vx;
		this.vy += tc.vy;
		return this;
	}
	
	public TexCoord4F subtract(float ux, float uy, float vx, float vy)
	{
		this.ux -= ux;
		this.uy -= uy;
		this.vx -= vx;
		this.vy -= vy;
		return this;
	}

	public TexCoord4F subtract(float val)
	{
		this.ux -= val;
		this.uy -= val;
		this.vx -= val;
		this.vy -= val;
		return this;
	}

	public TexCoord4F subtract(TexCoord4F tc)
	{
		this.ux -= tc.ux;
		this.uy -= tc.uy;
		this.vx -= tc.vx;
		this.vy -= tc.vy;
		return this;
	}
	
	public TexCoord4F multiply(float ux, float uy, float vx, float vy)
	{
		this.ux *= ux;
		this.uy *= uy;
		this.vx *= vx;
		this.vy *= vy;
		return this;
	}

	public TexCoord4F multiply(float val)
	{
		this.ux *= val;
		this.uy *= val;
		this.vx *= val;
		this.vy *= val;
		return this;
	}

	public TexCoord4F multiply(TexCoord4F tc)
	{
		this.ux *= tc.ux;
		this.uy *= tc.uy;
		this.vx *= tc.vx;
		this.vy *= tc.vy;
		return this;
	}
	
	public TexCoord4F divide(float ux, float uy, float vx, float vy)
	{
		this.ux /= ux;
		this.uy /= uy;
		this.vx /= vx;
		this.vy /= vy;
		return this;
	}

	public TexCoord4F divide(float val)
	{
		this.ux /= val;
		this.uy /= val;
		this.vx /= val;
		this.vy /= val;
		return this;
	}

	public TexCoord4F divide(TexCoord4F tc)
	{
		this.ux /= tc.ux;
		this.uy /= tc.uy;
		this.vx /= tc.vx;
		this.vy /= tc.vy;
		return this;
	}

	
	public TexCoord4F interpolate(float ux, float uy, float vx, float vy, float amt)
	{
		this.ux = (1F - amt) * this.ux + amt * ux;
		this.uy = (1F - amt) * this.uy + amt * uy;
		this.vx = (1F - amt) * this.vx + amt * vx;
		this.vy = (1F - amt) * this.vy + amt * vy;
		return this;
	}

	public TexCoord4F interpolate(float val, float amt)
	{
		this.ux = (1F - amt) * this.ux + amt * val;
		this.uy = (1F - amt) * this.uy + amt * val;
		this.vx = (1F - amt) * this.vx + amt * val;
		this.vy = (1F - amt) * this.vy + amt * val;
		return this;
	}

	public TexCoord4F interpolate(TexCoord4F tc, float amt)
	{
		this.ux = (1F - amt) * this.ux + amt * tc.ux;
		this.uy = (1F - amt) * this.uy + amt * tc.uy;
		this.vx = (1F - amt) * this.vx + amt * tc.vx;
		this.vy = (1F - amt) * this.vy + amt * tc.vy;
		return this;
	}
	
	public TexCoord4F zero()
	{
		ux = uy = vx = vy = 0;
		return this;
	}
	
	public TexCoord4F identity()
	{
		ux = uy = 0;
		vx = vy = 1;
		return this;
	}
	
	public TexCoord4F set(float ux, float uy, float vx, float vy)
	{
		this.ux = ux;
		this.uy = uy;
		this.vx = vx;
		this.vy = vy;
		return this;
	}

	public TexCoord4F set(float val)
	{
		this.ux = val;
		this.uy = val;
		this.vx = val;
		this.vy = val;
		return this;
	}

	public TexCoord4F set(TexCoord4F tc)
	{
		this.ux = tc.ux;
		this.uy = tc.uy;
		this.vx = tc.vx;
		this.vy = tc.vy;
		return this;
	}

	public TexCoord4F setUX(float ux)
	{
		this.ux = ux;
		return this;
	}

	public TexCoord4F setUY(float uy)
	{
		this.uy = uy;
		return this;
	}

	public TexCoord4F setVX(float vx)
	{
		this.vx = vx;
		return this;
	}

	public TexCoord4F setVY(float vy)
	{
		this.vy = vy;
		return this;
	}

	public TexCoord4F set(float[] arr)
	{
		return set(arr, 0);
	}

	public TexCoord4F set(float[] arr, int off)
	{
		this.ux = arr[0 + off];
		this.uy = arr[1 + off];
		this.vx = arr[2 + off];
		this.vy = arr[3 + off];
		return this;
	}

	public float getUX()
	{
		return ux;
	}
	
	public float getUY()
	{
		return uy;
	}
	
	public float getVX()
	{
		return vx;
	}
	
	public float getVY()
	{
		return vy;
	}
	
	public float[] get()
	{
		return ArrayUtil.toFloatArray(ux, uy, vx, vy);
	}
	
	public float[] get(float[] arr)
	{
		return get(arr, 0);
	}
	
	public float[] get(float[] arr, int off)
	{
		arr[0 + off] = ux;
		arr[1 + off] = uy;
		arr[2 + off] = vx;
		arr[3 + off] = vy;
		return arr;
	}
	
	public TexCoord4F abs()
	{
		this.ux = Math.abs(ux);
		this.uy = Math.abs(uy);
		this.vx = Math.abs(vx);
		this.vy = Math.abs(vy);
		return this;
	}
	
	public TexCoord4F negate()
	{
		this.ux = -ux;
		this.uy = -uy;
		this.vx = -vx;
		this.vy = -vy;
		return this;
	}
	
	public TexCoord4F negative()
	{
		this.ux = -Math.abs(ux);
		this.uy = -Math.abs(uy);
		this.vx = -Math.abs(vx);
		this.vy = -Math.abs(vy);
		return this;
	}

	@Override
	public TexCoord4F clone()
	{
		return new TexCoord4F(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof TexCoord4F)
		{
			TexCoord4F v = (TexCoord4F) o;
			return Float.floatToIntBits(ux) == Float.floatToIntBits(v.ux) && Float.floatToIntBits(uy) == Float.floatToIntBits(v.uy) && Float.floatToIntBits(vx) == Float.floatToIntBits(v.vy);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 19;
		hash = hash * 31 + Float.floatToIntBits(ux);
		hash = hash * 31 + Float.floatToIntBits(uy);
		hash = hash * 31 + Float.floatToIntBits(vx);
		hash = hash * 31 + Float.floatToIntBits(vy);
		return hash;
	}

	@Override
	public String toString()
	{
		return "(" + ux + ", " + uy + ", " + vx + ", " + vy + ")";
	}

	private static final long serialVersionUID = 7896908543175472292L;
}
