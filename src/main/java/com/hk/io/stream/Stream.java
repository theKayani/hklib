package com.hk.io.stream;

import java.io.Closeable;
import java.io.Serializable;

/**
 * <p>Stream interface.</p>
 *
 * @author theKayani
 */
public interface Stream extends Closeable
{
	/**
	 * <p>writeBoolean.</p>
	 *
	 * @param o a boolean
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeBoolean(boolean o) throws StreamException;

	/**
	 * <p>writeByte.</p>
	 *
	 * @param o a byte
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeByte(byte o) throws StreamException;

	/**
	 * <p>writeShort.</p>
	 *
	 * @param o a short
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeShort(short o) throws StreamException;

	/**
	 * <p>writeInt.</p>
	 *
	 * @param o a int
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeInt(int o) throws StreamException;

	/**
	 * <p>writeFloat.</p>
	 *
	 * @param o a float
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeFloat(float o) throws StreamException;

	/**
	 * <p>writeCharacter.</p>
	 *
	 * @param o a char
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeCharacter(char o) throws StreamException;

	/**
	 * <p>writeLong.</p>
	 *
	 * @param o a long
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeLong(long o) throws StreamException;

	/**
	 * <p>writeDouble.</p>
	 *
	 * @param o a double
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeDouble(double o) throws StreamException;

	/**
	 * <p>writeUTFString.</p>
	 *
	 * @param o a {@link java.lang.String} object
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeUTFString(String o) throws StreamException;
	
	/**
	 * <p>writeRawString.</p>
	 *
	 * @param o a {@link java.lang.String} object
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeRawString(String o) throws StreamException;

	/**
	 * <p>writeSerializable.</p>
	 *
	 * @param o a {@link java.io.Serializable} object
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeSerializable(Serializable o) throws StreamException;

	/**
	 * <p>writeBytes.</p>
	 *
	 * @param arr an array of {@link byte} objects
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	void writeBytes(byte[] arr) throws StreamException;

	/**
	 * <p>readBoolean.</p>
	 *
	 * @return a boolean
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	boolean readBoolean() throws StreamException;

	/**
	 * <p>readByte.</p>
	 *
	 * @return a byte
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	byte readByte() throws StreamException;

	/**
	 * <p>readShort.</p>
	 *
	 * @return a short
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	short readShort() throws StreamException;

	/**
	 * <p>readInt.</p>
	 *
	 * @return a int
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	int readInt() throws StreamException;

	/**
	 * <p>readFloat.</p>
	 *
	 * @return a float
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	float readFloat() throws StreamException;

	/**
	 * <p>readCharacter.</p>
	 *
	 * @return a char
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	char readCharacter() throws StreamException;

	/**
	 * <p>readLong.</p>
	 *
	 * @return a long
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	long readLong() throws StreamException;

	/**
	 * <p>readDouble.</p>
	 *
	 * @return a double
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	double readDouble() throws StreamException;

	/**
	 * <p>readUTFString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	String readUTFString() throws StreamException;
	
	/**
	 * <p>readRawString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	String readRawString() throws StreamException;

	/**
	 * <p>readSerializable.</p>
	 *
	 * @param cls a {@link java.lang.Class} object
	 * @param <T> a T class
	 * @return a T object
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	<T> T readSerializable(Class<T> cls) throws StreamException;

	/**
	 * <p>readBytes.</p>
	 *
	 * @return an array of {@link byte} objects
	 * @throws com.hk.io.stream.StreamException if any.
	 */
	byte[] readBytes() throws StreamException;

	/** {@inheritDoc} */
	@Override
	void close() throws StreamException;

	/** Constant <code>TYPE_BOOLEAN=0</code> */
	byte TYPE_BOOLEAN = 0;
	/** Constant <code>TYPE_BYTE=1</code> */
	byte TYPE_BYTE = 1;
	/** Constant <code>TYPE_SHORT=2</code> */
	byte TYPE_SHORT = 2;
	/** Constant <code>TYPE_INT=3</code> */
	byte TYPE_INT = 3;
	/** Constant <code>TYPE_FLOAT=4</code> */
	byte TYPE_FLOAT = 4;
	/** Constant <code>TYPE_CHAR=5</code> */
	byte TYPE_CHAR = 5;
	/** Constant <code>TYPE_LONG=6</code> */
	byte TYPE_LONG = 6;
	/** Constant <code>TYPE_DOUBLE=7</code> */
	byte TYPE_DOUBLE = 7;
	/** Constant <code>TYPE_UTF_STRING=8</code> */
	byte TYPE_UTF_STRING = 8;
	/** Constant <code>TYPE_RAW_STRING=9</code> */
	byte TYPE_RAW_STRING = 9;
	/** Constant <code>TYPE_SERIALIZABLE=10</code> */
	byte TYPE_SERIALIZABLE = 10;
	/** Constant <code>TYPE_BYTES=11</code> */
	byte TYPE_BYTES = 11;
}
