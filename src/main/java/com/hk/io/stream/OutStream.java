package com.hk.io.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * <p>OutStream class.</p>
 *
 * @author theKayani
 */
public class OutStream implements Stream
{
	private final boolean errorCheck;
	private final OutputStream out;
	private boolean closed;

	/**
	 * <p>Constructor for OutStream.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object
	 */
	public OutStream(OutputStream out)
	{
		this(out, true);
	}

	/**
	 * <p>Constructor for OutStream.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object
	 * @param errorCheck a boolean
	 */
	public OutStream(OutputStream out, boolean errorCheck)
	{
		this.out = out;
		this.errorCheck = errorCheck;
	}

	/** {@inheritDoc} */
	@Override
	public void writeBoolean(boolean o) throws StreamException
	{
		if(errorCheck) write(TYPE_BOOLEAN);

		byte b = (byte) (o ? 37 : 66);
		write(b);
	}

	/** {@inheritDoc} */
	@Override
	public void writeByte(byte o) throws StreamException
	{
		if(errorCheck) write(TYPE_BYTE);
		write(o);
	}

	/** {@inheritDoc} */
	@Override
	public void writeShort(short o) throws StreamException
	{
		if(errorCheck) write(TYPE_SHORT);
		write((byte) (o >> 8 & 0xFF));
		write((byte) (o & 0xFF));
	}

	/** {@inheritDoc} */
	@Override
	public void writeInt(int o) throws StreamException
	{
		if(errorCheck) write(TYPE_INT);
		write((byte) (o >> 24 & 0xFF));
		write((byte) (o >> 16 & 0xFF));
		write((byte) (o >>  8 & 0xFF));
		write((byte) (o & 0xFF));
	}

	/** {@inheritDoc} */
	@Override
	public void writeFloat(float o) throws StreamException
	{
		if(errorCheck) write(TYPE_FLOAT);
		int o2 = Float.floatToIntBits(o);
		write((byte) (o2 >> 24 & 0xFF));
		write((byte) (o2 >> 16 & 0xFF));
		write((byte) (o2 >>  8 & 0xFF));
		write((byte) (o2 & 0xFF));
	}

	/** {@inheritDoc} */
	@Override
	public void writeCharacter(char o) throws StreamException
	{
		if(errorCheck) write(TYPE_CHAR);
		write((byte) ((int) o >> 8 & 0xFF));
		write((byte) ((int) o & 0xFF));
	}

	/** {@inheritDoc} */
	@Override
	public void writeLong(long o) throws StreamException
	{
		if(errorCheck) write(TYPE_LONG);
		write((byte) (o >> 56 & 0xFF));
		write((byte) (o >> 48 & 0xFF));
		write((byte) (o >> 40 & 0xFF));
		write((byte) (o >> 32 & 0xFF));
		write((byte) (o >> 24 & 0xFF));
		write((byte) (o >> 16 & 0xFF));
		write((byte) (o >>  8 & 0xFF));
		write((byte) (o & 0xFF));
	}

	/** {@inheritDoc} */
	@Override
	public void writeDouble(double o) throws StreamException
	{
		if(errorCheck) write(TYPE_DOUBLE);
		long o2 = Double.doubleToLongBits(o);
		write((byte) (o2 >> 56 & 0xFF));
		write((byte) (o2 >> 48 & 0xFF));
		write((byte) (o2 >> 40 & 0xFF));
		write((byte) (o2 >> 32 & 0xFF));
		write((byte) (o2 >> 24 & 0xFF));
		write((byte) (o2 >> 16 & 0xFF));
		write((byte) (o2 >>  8 & 0xFF));
		write((byte) (o2 & 0xFF));
	}

	/** {@inheritDoc} */
	@Override
	public void writeUTFString(String o) throws StreamException
	{
		if(errorCheck) write(TYPE_UTF_STRING);
		byte[] bs = o.getBytes(StandardCharsets.UTF_8);
		int len = bs.length;
		write((byte) (len >> 24 & 0xFF));
		write((byte) (len >> 16 & 0xFF));
		write((byte) (len >> 8 & 0xFF));
		write((byte) (len & 0xFF));
		for (byte b : bs)
			write((byte) (~b & 0xFF));
	}

	/** {@inheritDoc} */
	@Override
	public void writeRawString(String o) throws StreamException
	{
		if(errorCheck) write(TYPE_RAW_STRING);
		int len = o.length();
		write((byte) (len >> 24 & 0xFF));
		write((byte) (len >> 16 & 0xFF));
		write((byte) (len >> 8 & 0xFF));
		write((byte) (len & 0xFF));
		for(int i = 0; i < len; i++)
		{
			int o2 = o.charAt(i);
			write((byte) (o2 >> 8 & 0xFF));
			write((byte) (o2 & 0xFF));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void writeSerializable(Serializable o) throws StreamException
	{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try
		{
			ObjectOutputStream oout = new ObjectOutputStream(bout);
			oout.writeObject(o);
			oout.close();
			bout.close();
		}
		catch(IOException e)
		{
			throw new StreamException(e);
		}
		byte[] arr = bout.toByteArray();
		int len = arr.length;
		write((byte) (len >> 24 & 0xFF));
		write((byte) (len >> 16 & 0xFF));
		write((byte) (len >> 8 & 0xFF));
		write((byte) (len & 0xFF));
		for (byte b : arr)
			write((byte) (~b & 0xFF));
	}

	/** {@inheritDoc} */
	@Override
	public void writeBytes(byte[] arr) throws StreamException
	{
		if(errorCheck) write(TYPE_BYTES);
		int len = arr.length;
		write((byte) (len >> 24 & 0xFF));
		write((byte) (len >> 16 & 0xFF));
		write((byte) (len >> 8 & 0xFF));
		write((byte) (len & 0xFF));
		for (byte b : arr)
			write(b);
	}

	/** {@inheritDoc} */
	@Override
	public boolean readBoolean() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public byte readByte() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public short readShort() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public int readInt() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public float readFloat() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public char readCharacter() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public long readLong() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public double readDouble() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public String readUTFString() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public String readRawString() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public <T> T readSerializable(Class<T> cls) throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public byte[] readBytes() throws StreamException
	{
		throw new StreamException("Can't read from this stream");
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws StreamException
	{
		try
		{
			out.close();
		}
		catch (IOException e)
		{
			throw new StreamException(e);
		}
		closed = true;
	}

	private void write(byte b) throws StreamException
	{
		if (closed)
		{
			throw new StreamException("Stream was Closed");
		}
		try
		{
			out.write(b);
		}
		catch (IOException e)
		{
			throw new StreamException(e);
		}
	}
}