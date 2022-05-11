package com.hk.math;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * <p>Bits class.</p>
 *
 * @author theKayani
 */
public class Bits implements Cloneable, Serializable
{
	private long val;
	private final int size;

	/**
	 * <p>Constructor for Bits.</p>
	 */
	public Bits()
	{
		this(0);
	}

	/**
	 * <p>Constructor for Bits.</p>
	 *
	 * @param val a long
	 */
	public Bits(long val)
	{
		this(val, 16);
	}

	/**
	 * <p>Constructor for Bits.</p>
	 *
	 * @param val a long
	 * @param size a int
	 */
	public Bits(long val, int size)
	{
		this.size = MathUtil.between(1, size, 64);

		setVal(val);
	}

	/**
	 * <p>setBit.</p>
	 *
	 * @param index a int
	 * @param b a boolean
	 * @return a {@link com.hk.math.Bits} object
	 */
	@NotNull
	public Bits setBit(int index, boolean b)
	{
		if (index < 0 || index >= size)
			throw new OutOfBoundsException(index, size);

		long i = 1L << index;
		if (b) val |= i;
		else val &= ~i;

		return this;
	}

	/**
	 * <p>getBit.</p>
	 *
	 * @param index a int
	 * @return a boolean
	 */
	public boolean getBit(int index)
	{
		if (index < 0 || index >= size)
			throw new OutOfBoundsException(index, size);

		return (val & (1L << index)) != 0;
	}

	/**
	 * <p>toggleBit.</p>
	 *
	 * @param index a int
	 * @return a {@link com.hk.math.Bits} object
	 */
	@NotNull
	public Bits toggleBit(int index)
	{
		return setBit(index, !getBit(index));
	}

	/**
	 * <p>setAll.</p>
	 *
	 * @param b a boolean
	 * @return a {@link com.hk.math.Bits} object
	 */
	@NotNull
	public Bits setAll(boolean b)
	{
		return setVal(b ? -1 : 0);
	}

	/**
	 * <p>Getter for the field <code>val</code>.</p>
	 *
	 * @return a long
	 */
	public long getVal()
	{
		long v = 0;
		for(int i = 0; i < size; i++)
			v |= 1L << i;
		return val & v;
	}

	/**
	 * <p>Setter for the field <code>val</code>.</p>
	 *
	 * @param val a long
	 * @return a {@link com.hk.math.Bits} object
	 */
	@NotNull
	public Bits setVal(long val)
	{
		this.val = val;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	@NotNull
	public Bits clone()
	{
		try
		{
			return (Bits) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		char[] cs = new char[size];
		for(int i = 0; i < size; i++)
			cs[i] = (val & (1L << (size - i - 1))) == 0 ? '0' : '1';
		return new String(cs);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Bits && ((Bits) o).val == val && ((Bits) o).size == size;
	}

	/** {@inheritDoc} */
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