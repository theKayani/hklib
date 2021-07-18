package com.hk.math;

import java.io.Serializable;

public class Bits implements Cloneable, Serializable
{
	private long val;
	private final int size;
	
	public Bits()
	{
		this(0);
	}

	public Bits(long val)
	{
		this(val, 16);
	}

	public Bits(long val, int size)
	{
		this.size = MathUtil.between(1, size, 64);
		
		setVal(val);
	}

	public Bits setBit(int index, boolean b)
	{
		if (index < 0 || index >= size)
			throw new OutOfBoundsException(index, size);

		long i = 1L << index;
		if (b) val |= i;
		else val &= ~i;

		return this;
	}

	public boolean getBit(int index)
	{
		if (index < 0 || index >= size)
			throw new OutOfBoundsException(index, size);

		return (val & (1L << index)) != 0;
	}

	public Bits toggleBit(int index)
	{
		return setBit(index, !getBit(index));
	}
	
	public Bits setAll(boolean b)
	{
		return setVal(b ? -1 : 0);
	}
	
	public long getVal()
	{
		long v = 0;
		for(int i = 0; i < size; i++)
			v |= 1L << i;
		return val & v;
	}
	
	public Bits setVal(long val)
	{
		this.val = val;
		return this;
	}

	@Override
	public Bits clone()
	{
		return new Bits(val, size);
	}

	@Override
	public String toString()
	{
		char[] cs = new char[size];
		for(int i = 0; i < size; i++)
			cs[i] = (val & (1L << (size - i - 1))) == 0 ? '0' : '1';
		return new String(cs);
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof Bits && ((Bits) o).val == val && ((Bits) o).size == size;
	}

	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = 19 + hash * (int) (val & 0xFFFFFF);
		hash = 19 + hash * (int) (val >> 32 & 0xFFFFFF);
		hash = 19 + hash * size;
		return hash;
	}

	private static class OutOfBoundsException extends RuntimeException
	{
		private OutOfBoundsException(int index, int size)
		{
			super("The index must be between 0 and " + size + ". It is " + index + ".");
		}

		private static final long serialVersionUID = -7155366593715852887L;
	}

	private static final long serialVersionUID = -2318678507295309447L;
}
