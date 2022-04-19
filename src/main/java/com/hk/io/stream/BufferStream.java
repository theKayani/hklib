package com.hk.io.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.hk.ex.OutOfBoundsException;

/**
 * <p>BufferStream class.</p>
 *
 * @author theKayani
 */
public class BufferStream implements Stream
{
	private final byte[] buf;
	public final int off, end;
	private boolean littleEndian;
	private int pos;

	/**
	 * <p>Constructor for BufferStream.</p>
	 *
	 * @param size a int
	 */
	public BufferStream(int size)
	{
		this(new byte[size]);
	}

	/**
	 * <p>Constructor for BufferStream.</p>
	 *
	 * @param buf an array of {@link byte} objects
	 */
	public BufferStream(byte[] buf)
	{
		this(buf, 0, buf.length);
	}

	/**
	 * <p>Constructor for BufferStream.</p>
	 *
	 * @param buf an array of {@link byte} objects
	 * @param off a int
	 * @param end a int
	 */
	public BufferStream(byte[] buf, int off, int end)
	{
		this.buf = buf;
		this.off = pos = off;
		this.end = end;
		littleEndian = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
	}

	/** {@inheritDoc} */
	@Override
	public void writeBoolean(boolean o)
	{
		ensure(1);
		buf[pos++] = (byte) (o ? 1231 : 1237);
	}

	/** {@inheritDoc} */
	@Override
	public void writeByte(byte o)
	{
		ensure(1);
		buf[pos++] = o;
	}

	/** {@inheritDoc} */
	@Override
	public void writeShort(short o)
	{
		ensure(2);
		for(int i = 0; i < 2; i++)
		{
			buf[pos + (littleEndian ? i : 1 - i)] = (byte) ((o >> (i * 8)) & 0xFF);
		}
		pos += 2;
	}

	/** {@inheritDoc} */
	@Override
	public void writeInt(int o)
	{
		ensure(4);
		for(int i = 0; i < 4; i++)
		{
			buf[pos + (littleEndian ? i : 3 - i)] = (byte) ((o >> (i * 8)) & 0xFF);
		}
		pos += 4;
	}

	/** {@inheritDoc} */
	@Override
	public void writeFloat(float o)
	{
		writeInt(Float.floatToIntBits(o));
	}

	/** {@inheritDoc} */
	@Override
	public void writeCharacter(char o)
	{
		writeShort((short) ((int) o & 0xFFFF));
	}

	/** {@inheritDoc} */
	@Override
	public void writeLong(long o)
	{
		ensure(8);
		for(int i = 0; i < 8; i++)
		{
			buf[pos + (littleEndian ? i : 7 - i)] = (byte) ((o >> (i * 8)) & 0xFF);
		}
		pos += 8;
	}

	/** {@inheritDoc} */
	@Override
	public void writeDouble(double o)
	{
		writeLong(Double.doubleToLongBits(o));
	}

	/** {@inheritDoc} */
	@Override
	public void writeUTFString(String o)
	{
		wr(o, StandardCharsets.UTF_8);
	}

	/** {@inheritDoc} */
	@Override
	public void writeRawString(String o)
	{
		wr(o, StandardCharsets.UTF_16);
	}

	private void wr(String s, Charset cs)
	{
		writeInt(s.length());
		byte[] bs = s.getBytes(cs);
		ensure(bs.length);
		System.arraycopy(bs, 0, buf, pos, bs.length);
		pos += bs.length;
	}

	/** {@inheritDoc} */
	@Override
	public void writeSerializable(Serializable o)
	{
		try
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(bout);
			oout.writeObject(o);
			oout.close();

			int len = bout.size();
			writeInt(len);
			ensure(len);
			bout.writeTo(new OutputStream()
			{
				@Override
				public void write(int b)
				{}

				@Override
				public void write(byte[] b, int off, int len)
				{
					System.arraycopy(b, off, buf, pos, len);
				}
			});
			pos += len;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void writeBytes(byte[] arr)
	{
		writeInt(arr.length);
		ensure(arr.length);
		System.arraycopy(arr, 0, buf, pos, arr.length);
		pos += arr.length;
	}

	/** {@inheritDoc} */
	@Override
	public boolean readBoolean()
	{
		ensure(1);
		return (buf[pos++] & 0xFF) == 207;
	}

	/** {@inheritDoc} */
	@Override
	public byte readByte()
	{
		ensure(1);
		return buf[pos++];
	}

	/** {@inheritDoc} */
	@Override
	public short readShort()
	{
		ensure(2);
		short o = 0;
		for(int i = 0; i < 2; i++)
		{
			o |= buf[pos + (littleEndian ? i : 1 - i)] << (i * 8);
		}
		pos += 2;
		return o;
	}

	/** {@inheritDoc} */
	@Override
	public int readInt()
	{
		ensure(4);
		int o = 0;
		for(int i = 0; i < 4; i++)
		{
			o |= buf[pos + (littleEndian ? i : 3 - i)] << (i * 8);
		}
		pos += 4;

		return o;
	}

	/** {@inheritDoc} */
	@Override
	public float readFloat()
	{
		return Float.intBitsToFloat(readInt());
	}

	/** {@inheritDoc} */
	@Override
	public char readCharacter()
	{
		return (char) (readShort() & 0xFFFF);
	}

	/** {@inheritDoc} */
	@Override
	public long readLong()
	{
		ensure(8);
		long o = 0;
		for(int i = 0; i < 8; i++)
		{
			o |= (long) buf[pos + (littleEndian ? i : 7 - i)] << (i * 8);
		}
		pos += 8;
		return o;
	}

	/** {@inheritDoc} */
	@Override
	public double readDouble()
	{
		return Double.longBitsToDouble(readLong());
	}

	/** {@inheritDoc} */
	@Override
	public String readUTFString()
	{
		return rd(StandardCharsets.UTF_8);
	}

	/** {@inheritDoc} */
	@Override
	public String readRawString()
	{
		return rd(StandardCharsets.UTF_16);
	}

	private String rd(Charset cs)
	{
		int len = readInt();
		ensure(len);
		pos += len;
		return new String(buf, pos - len, len, cs);
	}

	/** {@inheritDoc} */
	@Override
	public <T> T readSerializable(Class<T> cls) throws StreamException
	{
		try
		{
			int len = readInt();
			ensure(len);
			ByteArrayInputStream bin = new ByteArrayInputStream(buf, pos, len);
			ObjectInputStream oin = new ObjectInputStream(bin);
			Object o = oin.readObject();
			oin.close();
			pos += len;
			return cls.cast(o);
		}
		catch(Exception e)
		{
			throw new StreamException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public byte[] readBytes()
	{
		int len = readInt();
		byte[] arr = Arrays.copyOfRange(buf, pos, len);
		pos += len;
		return arr;
	}

	private void ensure(int bytes)
	{
		if(pos + bytes > end)
			throw new OutOfBoundsException("Exceeded by " + (pos + bytes - end));
	}

	/**
	 * <p>getPosition.</p>
	 *
	 * @return a int
	 */
	public int getPosition()
	{
		return pos;
	}

	/**
	 * <p>available.</p>
	 *
	 * @return a int
	 */
	public int available()
	{
		return end - pos;
	}

	/**
	 * <p>reset.</p>
	 */
	public void reset()
	{
		pos = off;
	}

	/**
	 * <p>setBigEndian.</p>
	 *
	 * @param bigEndian a boolean
	 */
	public void setBigEndian(boolean bigEndian)
	{
		this.littleEndian = !bigEndian;
	}

	/**
	 * <p>isBigEndian.</p>
	 *
	 * @return a boolean
	 */
	public boolean isBigEndian()
	{
		return !littleEndian;
	}

	/**
	 * <p>shift.</p>
	 *
	 * @param a a int
	 */
	public void shift(int a)
	{
		if(a > 0)
		{
			ensure(a);
			pos += a;
		}
		else if(a < 0)
		{
			if(pos < a)
				throw new OutOfBoundsException();
			pos -= a;
		}
	}

	/**
	 * <p>get.</p>
	 *
	 * @return an array of {@link byte} objects
	 */
	public byte[] get()
	{
		return Arrays.copyOf(buf, buf.length);
	}

	/** {@inheritDoc} */
	@Override
	public void close()
	{}
}