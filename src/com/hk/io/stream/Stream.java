package com.hk.io.stream;

import java.io.Closeable;
import java.io.Serializable;

public interface Stream extends Closeable
{
	public void writeBoolean(boolean o) throws StreamException;

	public void writeByte(byte o) throws StreamException;

	public void writeShort(short o) throws StreamException;

	public void writeInt(int o) throws StreamException;

	public void writeFloat(float o) throws StreamException;

	public void writeCharacter(char o) throws StreamException;

	public void writeLong(long o) throws StreamException;

	public void writeDouble(double o) throws StreamException;

	public void writeUTFString(String o) throws StreamException;
	
	public void writeRawString(String o) throws StreamException;

	public void writeSerializable(Serializable o) throws StreamException;

	public void writeBytes(byte[] arr) throws StreamException;

	public boolean readBoolean() throws StreamException;

	public byte readByte() throws StreamException;

	public short readShort() throws StreamException;

	public int readInt() throws StreamException;

	public float readFloat() throws StreamException;

	public char readCharacter() throws StreamException;

	public long readLong() throws StreamException;

	public double readDouble() throws StreamException;

	public String readUTFString() throws StreamException;
	
	public String readRawString() throws StreamException;

	public <T> T readSerializable(Class<T> cls) throws StreamException;

	public byte[] readBytes() throws StreamException;

	@Override
	public void close() throws StreamException;

	static final byte TYPE_BOOLEAN = 0;
	static final byte TYPE_BYTE = 1;
	static final byte TYPE_SHORT = 2;
	static final byte TYPE_INT = 3;
	static final byte TYPE_FLOAT = 4;
	static final byte TYPE_CHAR = 5;
	static final byte TYPE_LONG = 6;
	static final byte TYPE_DOUBLE = 7;
	static final byte TYPE_UTF_STRING = 8;
	static final byte TYPE_RAW_STRING = 9;
	static final byte TYPE_SERIALIZABLE = 10;
	static final byte TYPE_BYTES = 11;
}
