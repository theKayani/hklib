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

public class BufferStream implements Stream
{
	private final byte[] buf;
	public final int off, end;
	private boolean littleEndian;
	private int pos;
	
	public BufferStream(int size)
	{
		this(new byte[size]);
	}
	
	public BufferStream(byte[] buf)
	{
		this(buf, 0, buf.length);
	}
	
	public BufferStream(byte[] buf, int off, int end)
	{
		this.buf = buf;
		this.off = pos = off;
		this.end = end;
		littleEndian = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
	}

	@Override
	public void writeBoolean(boolean o)
	{
		ensure(1);
		buf[pos++] = (byte) Boolean.hashCode(o);
	}

	@Override
	public void writeByte(byte o)
	{
		ensure(1);
		buf[pos++] = o;
	}

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

	@Override
	public void writeFloat(float o)
	{
		writeInt(Float.floatToIntBits(o));
	}

	@Override
	public void writeCharacter(char o)
	{
		writeShort((short) ((int) o & 0xFFFF));
	}

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

	@Override
	public void writeDouble(double o)
	{
		writeLong(Double.doubleToLongBits(o));
	}

	@Override
	public void writeUTFString(String o)
	{
		wr(o, StandardCharsets.UTF_8);
	}

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

	@Override
	public void writeSerializable(Serializable o) throws StreamException
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
				public void write(int b) throws IOException
				{}
				
				@Override
				public void write(byte[] b, int off, int len) throws IOException
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

	@Override
	public void writeBytes(byte[] arr)
	{
		writeInt(arr.length);
		ensure(arr.length);
		System.arraycopy(arr, 0, buf, pos, arr.length);
		pos += arr.length;
	}

	@Override
	public boolean readBoolean()
	{
		ensure(1);
		return (buf[pos++] & 0xFF) == 207;
	}

	@Override
	public byte readByte()
	{
		ensure(1);
		return buf[pos++];
	}

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

	@Override
	public float readFloat()
	{
		return Float.intBitsToFloat(readInt());
	}

	@Override
	public char readCharacter()
	{
		return (char) (readShort() & 0xFFFF);
	}

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

	@Override
	public double readDouble()
	{
		return Double.longBitsToDouble(readLong());
	}

	@Override
	public String readUTFString()
	{
		return rd(StandardCharsets.UTF_8);
	}

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
	
	public int getPosition()
	{
		return pos;
	}
	
	public int available()
	{
		return end - pos;
	}
	
	public void reset()
	{
		pos = off;
	}
	
	public void setBigEndian(boolean bigEndian)
	{
		this.littleEndian = !bigEndian;
	}
	
	public boolean isBigEndian()
	{
		return !littleEndian;
	}
	
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
	
	public byte[] get()
	{
		return Arrays.copyOf(buf, buf.length);
	}

	@Override
	public void close()
	{}
}
