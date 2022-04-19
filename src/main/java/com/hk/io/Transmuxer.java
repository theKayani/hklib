package com.hk.io;

import java.util.Random;

import com.hk.array.ArrayUtil;

/**
 * <p>Transmuxer class.</p>
 *
 * @author theKayani
 */
public class Transmuxer
{
	private final byte key;
	private final int offset, length;
	private final byte[] data;
	private Boolean encoded;

	private Transmuxer(byte key, byte[] data, int offset, int length)
	{
		this.key = key;
		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * <p>encoded.</p>
	 *
	 * @return a boolean
	 */
	public boolean encoded()
	{
		return encoded != null && encoded;
	}

	/**
	 * <p>doEncode.</p>
	 */
	public void doEncode()
	{
		if(encoded != null)
			throw new IllegalStateException("already used");

		process(false);
	}

	/**
	 * <p>decoded.</p>
	 *
	 * @return a boolean
	 */
	public boolean decoded()
	{
		return encoded != null && !encoded;
	}

	/**
	 * <p>doDecode.</p>
	 */
	public void doDecode()
	{
		if(encoded != null)
			throw new IllegalStateException("already used");

		process(true);
	}

	private void process(boolean flip)
	{
		long seed = 0;
		for(int i = 0; i < 4; i++)
			seed |= (key * key) << (long) (i * 16);

		Muxer[] muxers = ArrayUtil.shuffleArray(Muxer.values(), new Random(seed));
		if(flip)
		{
			for(int i = muxers.length - 1; i >= 0; i--)
			{
				if(!muxers[i].ignore)
					muxers[i].demux(key, data, offset, length);
			}
		}
		else
		{
			for (Muxer muxer : muxers)
			{
				if (!muxer.ignore)
					muxer.mux(key, data, offset, length);
			}
		}
	}

	/**
	 * <p>make.</p>
	 *
	 * @param key a byte
	 * @param data an array of {@link byte} objects
	 * @param offset a int
	 * @param length a int
	 * @return a {@link com.hk.io.Transmuxer} object
	 */
	public static Transmuxer make(byte key, byte[] data, int offset, int length)
	{
		if(data == null)
			throw new NullPointerException("data");
		if(offset < 0 || offset >= length || offset >= data.length)
			throw new IllegalArgumentException("offset out of range");
		if(length > data.length - offset)
			throw new IllegalArgumentException("length out of range");

		return new Transmuxer(key, data, offset, length);
	}

	/**
	 * <p>make.</p>
	 *
	 * @param key a byte
	 * @param data an array of {@link byte} objects
	 * @return a {@link com.hk.io.Transmuxer} object
	 */
	public static Transmuxer make(byte key, byte[] data)
	{
		return make(key, data, 0, data.length);
	}

	/**
	 * <p>make.</p>
	 *
	 * @param data an array of {@link byte} objects
	 * @param offset a int
	 * @param length a int
	 * @return a {@link com.hk.io.Transmuxer} object
	 */
	public static Transmuxer make(byte[] data, int offset, int length)
	{
		return make(data[offset], data, offset + 1, length - 1);
	}

	/**
	 * <p>make.</p>
	 *
	 * @param data an array of {@link byte} objects
	 * @return a {@link com.hk.io.Transmuxer} object
	 */
	public static Transmuxer make(byte[] data)
	{
		return make(data, 0, data.length);
	}

	enum Muxer
	{
		FLIP
		{
			@Override
			void process(byte key, byte[] data, int offset, int length)
			{
				for(int i = offset; i < offset + length; i++)
				{
					data[i] = (byte) (~data[i] & 0xFF);
				}
			}
		},
		REVERSE
		{
			@Override
			void process(byte key, byte[] data, int offset, int length)
			{
				for(int i = offset; i < (offset + length) / 2; i++)
				{
					byte n = data[i];
					data[i] = data[offset + length - i - 1];
					data[offset + length - i - 1] = n;
				}
			}
		},
		SUBTRACT
		{
			void mux(byte key, byte[] data, int offset, int length)
			{
				byte b = data[offset];
				for(int i = offset + 1; i < offset + length; i++)
				{
					int n = data[i] & 0xFF;
					int diff = n - (b & 0xFF);
					b = (byte) n;
					data[i] = (byte) diff;
				}
			}

			void demux(byte key, byte[] data, int offset, int length)
			{
				for(int i = offset + 1; i < offset + length; i++)
				{
					int n = data[i] & 0xFF;
					int sum = (data[i - 1] & 0xFF) + n;
					data[i] = (byte) sum;
				}
			}
		},
		XOR
		{
			void process(byte key, byte[] data, int offset, int length)
			{
				for(int i = offset; i < offset + length; i++)
				{
					data[i] = (byte) (data[i] ^ key);
				}
			}
		};

		private final boolean ignore;

		Muxer()
		{
			this(false);
		}

		Muxer(boolean ignore)
		{
			this.ignore = ignore;
		}

		void process(byte key, byte[] data, int offset, int length)
		{}

		void mux(byte key, byte[] data, int offset, int length)
		{
			process(key, data, offset, length);
		}

		void demux(byte key, byte[] data, int offset, int length)
		{
			process(key, data, offset, length);
		}
	}
}