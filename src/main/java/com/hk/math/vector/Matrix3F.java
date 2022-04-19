package com.hk.math.vector;

import java.io.Serializable;

// https://developer.apple.com/library/content/documentation/Cocoa/Conceptual/CocoaDrawingGuide/Transforms/Transforms.html
/**
 * <p>Matrix3F class.</p>
 *
 * @author theKayani
 */
public final class Matrix3F implements Serializable, Cloneable
{
	public float m00, m01, m02, m10, m11, m12, m20, m21, m22;

	/**
	 * <p>Constructor for Matrix3F.</p>
	 */
	public Matrix3F()
	{
		identity();
	}

	/**
	 * <p>Constructor for Matrix3F.</p>
	 *
	 * @param copy a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F(Matrix3F copy)
	{
		set(copy);
	}

	/**
	 * <p>Constructor for Matrix3F.</p>
	 *
	 * @param arr an array of {@link float} objects
	 */
	public Matrix3F(float[] arr)
	{
		set(arr);
	}

	/**
	 * <p>Constructor for Matrix3F.</p>
	 *
	 * @param arr an array of {@link float} objects
	 */
	public Matrix3F(float[][] arr)
	{
		set(arr);
	}

	/**
	 * <p>Constructor for Matrix3F.</p>
	 *
	 * @param m00 a float
	 * @param m01 a float
	 * @param m02 a float
	 * @param m10 a float
	 * @param m11 a float
	 * @param m12 a float
	 * @param m20 a float
	 * @param m21 a float
	 * @param m22 a float
	 */
	public Matrix3F(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
	{
		set(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}

	/**
	 * <p>set.</p>
	 *
	 * @param copy a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F set(Matrix3F copy)
	{
		m00 = copy.m00;
		m01 = copy.m01;
		m02 = copy.m02;
		m10 = copy.m10;
		m11 = copy.m11;
		m12 = copy.m12;
		m20 = copy.m20;
		m21 = copy.m21;
		m22 = copy.m22;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param m00 a float
	 * @param m01 a float
	 * @param m02 a float
	 * @param m10 a float
	 * @param m11 a float
	 * @param m12 a float
	 * @param m20 a float
	 * @param m21 a float
	 * @param m22 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F set(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
	{
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F set(float[] arr)
	{
		m00 = arr[0];
		m01 = arr[1];
		m02 = arr[2];
		m10 = arr[3];
		m11 = arr[4];
		m12 = arr[5];
		m20 = arr[6];
		m21 = arr[7];
		m22 = arr[8];
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arr an array of {@link float} objects
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F set(float[][] arr)
	{
		m00 = arr[0][0];
		m01 = arr[0][1];
		m02 = arr[0][2];
		m10 = arr[1][0];
		m11 = arr[1][1];
		m12 = arr[1][2];
		m20 = arr[2][0];
		m21 = arr[2][1];
		m22 = arr[2][2];
		return this;
	}

	/**
	 * <p>setRow.</p>
	 *
	 * @param row a int
	 * @param a a float
	 * @param b a float
	 * @param c a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setRow(int row, float a, float b, float c)
	{
		switch (row)
		{
			case 0:
				m00 = a;
				m01 = b;
				m02 = c;
				break;
			case 1:
				m10 = a;
				m11 = b;
				m12 = c;
				break;
			case 2:
				m20 = a;
				m21 = b;
				m22 = c;
				break;
			default:
				throw new IllegalArgumentException("column both be 0 to 2 (inclusive)");
		}
		return this;
	}

	/**
	 * <p>setRow.</p>
	 *
	 * @param row a int
	 * @param arr an array of {@link float} objects
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setRow(int row, float[] arr)
	{
		setRow(row, arr[0], arr[1], arr[2]);
		return this;
	}

	/**
	 * <p>setColumn.</p>
	 *
	 * @param column a int
	 * @param a a float
	 * @param b a float
	 * @param c a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setColumn(int column, float a, float b, float c)
	{
		switch (column)
		{
			case 0:
				m00 = a;
				m10 = b;
				m20 = c;
				break;
			case 1:
				m01 = a;
				m11 = b;
				m11 = c;
				break;
			case 2:
				m02 = a;
				m12 = b;
				m22 = c;
				break;
			default:
				throw new IllegalArgumentException("row must be 0 to 2 (inclusive)");
		}
		return this;
	}

	/**
	 * <p>setColumn.</p>
	 *
	 * @param column a int
	 * @param arr an array of {@link float} objects
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setColumn(int column, float[] arr)
	{
		setColumn(column, arr[0], arr[1], arr[2]);
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param row a int
	 * @param column a int
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F set(int row, int column, float val)
	{
		switch (row)
		{
			case 0:
				switch (column)
				{
					case 0:
						m00 = val;
						break;
					case 1:
						m01 = val;
						break;
					case 2:
						m02 = val;
						break;
					default:
						throw new IllegalArgumentException("row and column must both be 0 to 2 (inclusive)");
				}
				break;
			case 1:
				switch (column)
				{
					case 0:
						m10 = val;
						break;
					case 1:
						m11 = val;
						break;
					case 2:
						m12 = val;
						break;
					default:
						throw new IllegalArgumentException("row and column must both be 0 to 2 (inclusive)");
				}
				break;
			case 2:
				switch (column)
				{
					case 0:
						m20 = val;
						break;
					case 1:
						m21 = val;
						break;
					case 2:
						m22 = val;
						break;
					default:
						throw new IllegalArgumentException("row and column must both be 0 to 2 (inclusive)");
				}
				break;
			default:
				throw new IllegalArgumentException("row and column must both be 0 to 2 (inclusive)");
		}
		return this;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param row a int
	 * @param column a int
	 * @return a float
	 */
	public float get(int row, int column)
	{
		switch (row)
		{
			case 0:
				switch (column)
				{
					case 0:
						return m00;
					case 1:
						return m01;
					case 2:
						return m02;
				}
				break;
			case 1:
				switch (column)
				{
					case 0:
						return m10;
					case 1:
						return m11;
					case 2:
						return m12;
				}
				break;
			case 2:
				switch (column)
				{
					case 0:
						return m20;
					case 1:
						return m21;
					case 2:
						return m22;
				}
		}
		throw new IllegalArgumentException("row and column must both be 0 to 2 (inclusive)");
	}

	/**
	 * <p>getColumn.</p>
	 *
	 * @param column a int
	 * @return an array of {@link float} objects
	 */
	public float[] getColumn(int column)
	{
		switch (column)
		{
			case 0:
				return new float[] {
						m00, m10, m20
				};
			case 1:
				return new float[] {
						m01, m11, m21
				};
			case 2:
				return new float[] {
						m02, m12, m22
				};
			default:
				throw new IllegalArgumentException("row must be 0 to 2 (inclusive)");
		}
	}

	/**
	 * <p>getRow.</p>
	 *
	 * @param row a int
	 * @return an array of {@link float} objects
	 */
	public float[] getRow(int row)
	{
		switch (row)
		{
			case 0:
				return new float[] {
						m00, m01, m02
				};
			case 1:
				return new float[] {
						m10, m11, m12
				};
			case 2:
				return new float[] {
						m20, m21, m22
				};
			default:
				throw new IllegalArgumentException("row must be 0 to 2 (inclusive)");
		}
	}

	/**
	 * <p>getArrays.</p>
	 *
	 * @return an array of {@link float} objects
	 */
	public float[][] getArrays()
	{
		return new float[][] {
				{
						m00, m01, m02
				}, {
						m10, m11, m12
				}, {
						m20, m21, m22

				}
		};
	}

	/**
	 * <p>getArray.</p>
	 *
	 * @return an array of {@link float} objects
	 */
	public float[] getArray()
	{
		return new float[] {
				m00, m01, m02, m10, m11, m12, m20, m21, m22
		};
	}

	/**
	 * <p>Getter for the field <code>m00</code>.</p>
	 *
	 * @return a float
	 */
	public float getM00()
	{
		return m00;
	}

	/**
	 * <p>Setter for the field <code>m00</code>.</p>
	 *
	 * @param m00 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setM00(float m00)
	{
		this.m00 = m00;
		return this;
	}

	/**
	 * <p>Getter for the field <code>m01</code>.</p>
	 *
	 * @return a float
	 */
	public float getM01()
	{
		return m01;
	}

	/**
	 * <p>Setter for the field <code>m01</code>.</p>
	 *
	 * @param m01 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setM01(float m01)
	{
		this.m01 = m01;
		return this;
	}

	/**
	 * <p>Getter for the field <code>m02</code>.</p>
	 *
	 * @return a float
	 */
	public float getM02()
	{
		return m02;
	}

	/**
	 * <p>Setter for the field <code>m02</code>.</p>
	 *
	 * @param m02 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setM02(float m02)
	{
		this.m02 = m02;
		return this;
	}

	/**
	 * <p>Getter for the field <code>m10</code>.</p>
	 *
	 * @return a float
	 */
	public float getM10()
	{
		return m10;
	}

	/**
	 * <p>Setter for the field <code>m10</code>.</p>
	 *
	 * @param m10 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setM10(float m10)
	{
		this.m10 = m10;
		return this;
	}

	/**
	 * <p>Getter for the field <code>m11</code>.</p>
	 *
	 * @return a float
	 */
	public float getM11()
	{
		return m11;
	}

	/**
	 * <p>Setter for the field <code>m11</code>.</p>
	 *
	 * @param m11 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setM11(float m11)
	{
		this.m11 = m11;
		return this;
	}

	/**
	 * <p>Getter for the field <code>m12</code>.</p>
	 *
	 * @return a float
	 */
	public float getM12()
	{
		return m12;
	}

	/**
	 * <p>Setter for the field <code>m12</code>.</p>
	 *
	 * @param m12 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setM12(float m12)
	{
		this.m12 = m12;
		return this;
	}

	/**
	 * <p>Getter for the field <code>m20</code>.</p>
	 *
	 * @return a float
	 */
	public float getM20()
	{
		return m20;
	}

	/**
	 * <p>Setter for the field <code>m20</code>.</p>
	 *
	 * @param m20 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setM20(float m20)
	{
		this.m20 = m20;
		return this;
	}

	/**
	 * <p>Getter for the field <code>m21</code>.</p>
	 *
	 * @return a float
	 */
	public float getM21()
	{
		return m21;
	}

	/**
	 * <p>Setter for the field <code>m21</code>.</p>
	 *
	 * @param m21 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setM21(float m21)
	{
		this.m21 = m21;
		return this;
	}

	/**
	 * <p>Getter for the field <code>m22</code>.</p>
	 *
	 * @return a float
	 */
	public float getM22()
	{
		return m22;
	}

	/**
	 * <p>Setter for the field <code>m22</code>.</p>
	 *
	 * @param m22 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F setM22(float m22)
	{
		this.m22 = m22;
		return this;
	}

	/**
	 * <p>identity.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F identity()
	{
		m00 = m11 = m22 = 1F;
		m01 = m10 = m20 = m02 = m12 = m21 = 0F;
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F add(Matrix3F mat)
	{
		return new Matrix3F(m00 + mat.m00, m01 + mat.m01, m02 + mat.m02, m10 + mat.m10, m11 + mat.m11, m12 + mat.m12, m20 + mat.m20, m21 + mat.m21, m22 + mat.m22);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param m00 a float
	 * @param m01 a float
	 * @param m02 a float
	 * @param m10 a float
	 * @param m11 a float
	 * @param m12 a float
	 * @param m20 a float
	 * @param m21 a float
	 * @param m22 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F add(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
	{
		return new Matrix3F(this.m00 + m00, this.m01 + m01, this.m02 + m02, this.m10 + m10, this.m11 + m11, this.m12 + m12, this.m20 + m20, this.m21 + m21, this.m22 + m22);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F add(float val)
	{
		return add(val, val, val, val, val, val, val, val, val);
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F addLocal(Matrix3F mat)
	{
		m00 += mat.m00;
		m01 += mat.m01;
		m02 += mat.m02;
		m10 += mat.m10;
		m11 += mat.m11;
		m12 += mat.m12;
		m20 += mat.m20;
		m21 += mat.m21;
		m22 += mat.m22;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param m00 a float
	 * @param m01 a float
	 * @param m02 a float
	 * @param m10 a float
	 * @param m11 a float
	 * @param m12 a float
	 * @param m20 a float
	 * @param m21 a float
	 * @param m22 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F addLocal(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
	{
		this.m00 += m00;
		this.m01 += m01;
		this.m02 += m02;
		this.m10 += m10;
		this.m11 += m11;
		this.m12 += m12;
		this.m20 += m20;
		this.m21 += m21;
		this.m22 += m22;
		return this;
	}

	/**
	 * <p>addLocal.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F addLocal(float val)
	{
		return addLocal(val, val, val, val, val, val, val, val, val);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F subtract(Matrix3F mat)
	{
		return new Matrix3F(m00 - mat.m00, m01 - mat.m01, m02 - mat.m02, m10 - mat.m10, m11 - mat.m11, m12 - mat.m12, m20 - mat.m20, m21 - mat.m21, m22 - mat.m22);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param m00 a float
	 * @param m01 a float
	 * @param m02 a float
	 * @param m10 a float
	 * @param m11 a float
	 * @param m12 a float
	 * @param m20 a float
	 * @param m21 a float
	 * @param m22 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F subtract(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
	{
		return new Matrix3F(this.m00 - m00, this.m01 - m01, this.m02 - m02, this.m10 - m10, this.m11 - m11, this.m12 - m12, this.m20 - m20, this.m21 - m21, this.m22 - m22);
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F subtract(float val)
	{
		return subtract(val, val, val, val, val, val, val, val, val);
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F subtractLocal(Matrix3F mat)
	{
		m00 -= mat.m00;
		m01 -= mat.m01;
		m02 -= mat.m02;
		m10 -= mat.m10;
		m11 -= mat.m11;
		m12 -= mat.m12;
		m20 -= mat.m20;
		m21 -= mat.m21;
		m22 -= mat.m22;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param m00 a float
	 * @param m01 a float
	 * @param m02 a float
	 * @param m10 a float
	 * @param m11 a float
	 * @param m12 a float
	 * @param m20 a float
	 * @param m21 a float
	 * @param m22 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F subtractLocal(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
	{
		this.m00 -= m00;
		this.m01 -= m01;
		this.m02 -= m02;
		this.m10 -= m10;
		this.m11 -= m11;
		this.m12 -= m12;
		this.m20 -= m20;
		this.m21 -= m21;
		this.m22 -= m22;
		return this;
	}

	/**
	 * <p>subtractLocal.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F subtractLocal(float val)
	{
		return subtractLocal(val, val, val, val, val, val, val, val, val);
	}

	/**
	 * <p>scale.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F scale(Matrix3F mat)
	{
		return new Matrix3F(m00 * mat.m00, m01 * mat.m01, m02 * mat.m02, m10 * mat.m10, m11 * mat.m11, m12 * mat.m12, m20 * mat.m20, m21 * mat.m21, m22 * mat.m22);
	}

	/**
	 * <p>scale.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F scale(float val)
	{
		return scale(val, val, val, val, val, val, val, val, val);
	}

	/**
	 * <p>scale.</p>
	 *
	 * @param m00 a float
	 * @param m01 a float
	 * @param m02 a float
	 * @param m10 a float
	 * @param m11 a float
	 * @param m12 a float
	 * @param m20 a float
	 * @param m21 a float
	 * @param m22 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F scale(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
	{
		return new Matrix3F(this.m00 * m00, this.m01 * m01, this.m02 * m02, this.m10 * m10, this.m11 * m11, this.m12 * m12, this.m20 * m20, this.m21 * m21, this.m22 * m22);
	}

	/**
	 * <p>scaleLocal.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F scaleLocal(Matrix3F mat)
	{
		m00 *= mat.m00;
		m01 *= mat.m01;
		m02 *= mat.m02;
		m10 *= mat.m10;
		m11 *= mat.m11;
		m12 *= mat.m12;
		m20 *= mat.m20;
		m21 *= mat.m21;
		m22 *= mat.m22;
		return this;
	}

	/**
	 * <p>scaleLocal.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F scaleLocal(float val)
	{
		m00 *= val;
		m01 *= val;
		m02 *= val;
		m10 *= val;
		m11 *= val;
		m12 *= val;
		m20 *= val;
		m21 *= val;
		m22 *= val;
		return this;
	}

	/**
	 * <p>scaleLocal.</p>
	 *
	 * @param m00 a float
	 * @param m01 a float
	 * @param m02 a float
	 * @param m10 a float
	 * @param m11 a float
	 * @param m12 a float
	 * @param m20 a float
	 * @param m21 a float
	 * @param m22 a float
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F scaleLocal(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
	{
		this.m00 *= m00;
		this.m01 *= m01;
		this.m02 *= m02;
		this.m10 *= m10;
		this.m11 *= m11;
		this.m12 *= m12;
		this.m20 *= m20;
		this.m21 *= m21;
		this.m22 *= m22;
		return this;
	}

	/**
	 * <p>mult.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F mult(Matrix3F mat)
	{
		return new Matrix3F(this).multLocal(mat);
	}

	/**
	 * <p>multLocal.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F multLocal(Matrix3F mat)
	{
		float temp00 = m00 * mat.m00 + m01 * mat.m10 + m02 * mat.m20;
		float temp01 = m00 * mat.m01 + m01 * mat.m11 + m02 * mat.m21;
		float temp02 = m00 * mat.m02 + m01 * mat.m12 + m02 * mat.m22;
		float temp10 = m10 * mat.m00 + m11 * mat.m10 + m12 * mat.m20;
		float temp11 = m10 * mat.m01 + m11 * mat.m11 + m12 * mat.m21;
		float temp12 = m10 * mat.m02 + m11 * mat.m12 + m12 * mat.m22;
		float temp20 = m20 * mat.m00 + m21 * mat.m10 + m22 * mat.m20;
		float temp21 = m20 * mat.m01 + m21 * mat.m11 + m22 * mat.m21;
		float temp22 = m20 * mat.m02 + m21 * mat.m12 + m22 * mat.m22;

		m00 = temp00;
		m01 = temp01;
		m02 = temp02;
		m10 = temp10;
		m11 = temp11;
		m12 = temp12;
		m20 = temp20;
		m21 = temp21;
		m22 = temp22;

		return this;
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F divide(Matrix3F mat)
	{
		return mult(mat.flip());
	}

	/**
	 * <p>divideLocal.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix3F} object
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F divideLocal(Matrix3F mat)
	{
		return multLocal(mat.flip());
	}

	/**
	 * <p>negate.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F negate()
	{
		return scale(-1);
	}

	/**
	 * <p>negateLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F negateLocal()
	{
		return scaleLocal(-1);
	}

	/**
	 * <p>abs.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F abs()
	{
		return new Matrix3F(Math.abs(m00), Math.abs(m01), Math.abs(m02), Math.abs(m10), Math.abs(m11), Math.abs(m12), Math.abs(m20), Math.abs(m21), Math.abs(m22));
	}

	/**
	 * <p>absLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F absLocal()
	{
		m00 = Math.abs(m00);
		m01 = Math.abs(m01);
		m02 = Math.abs(m02);
		m10 = Math.abs(m10);
		m11 = Math.abs(m11);
		m12 = Math.abs(m12);
		m20 = Math.abs(m20);
		m21 = Math.abs(m21);
		m22 = Math.abs(m22);
		return this;
	}

	/**
	 * <p>negative.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F negative()
	{
		return abs().negateLocal();
	}

	/**
	 * <p>negativeLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F negativeLocal()
	{
		return absLocal().negateLocal();
	}

	/**
	 * <p>transpose.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F transpose()
	{
		return new Matrix3F(m00, m10, m20, m01, m11, m21, m02, m12, m22);
	}

	/**
	 * <p>transposeLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F transposeLocal()
	{
		float h01 = m01;
		float h02 = m02;
		float h10 = m10;
		float h12 = m12;
		float h20 = m20;
		float h21 = m21;

		m01 = h10;
		m02 = h20;
		m10 = h01;
		m12 = h21;
		m20 = h02;
		m21 = h12;
		return this;
	}

	/**
	 * <p>flip.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F flip()
	{
		return new Matrix3F(1F / m00, 1F / m01, 1F / m02, 1F / m10, 1F / m11, 1F / m12, 1F / m20, 1F / m21, 1F / m22);
	}

	/**
	 * <p>flipLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F flipLocal()
	{
		m00 = 1F / m00;
		m01 = 1F / m01;
		m02 = 1F / m02;
		m10 = 1F / m10;
		m11 = 1F / m11;
		m12 = 1F / m12;
		m20 = 1F / m20;
		m21 = 1F / m21;
		m22 = 1F / m22;
		return this;
	}

	/**
	 * <p>determinant.</p>
	 *
	 * @return a float
	 */
	public float determinant()
	{
		float f = m00 * (m11 * m22 - m12 * m21);
		f -= m01 * (m10 * m22 - m12 * m20);
		f += m02 * (m10 * m21 - m11 * m20);
		return f;
	}

	/**
	 * <p>isIdentity.</p>
	 *
	 * @return a boolean
	 */
	public boolean isIdentity()
	{
		return m00 == 1 && m01 == 0 && m02 == 0 && m10 == 0 && m11 == 1 && m12 == 0 && m20 == 0 && m21 == 0 && m22 == 1;
	}

	/**
	 * <p>inverseLocal.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F inverseLocal()
	{
		float indet = 1F / determinant();
		float a = m11 * m22 - m12 * m21;
		float b = -(m10 * m22 - m12 * m20);
		float c = m10 * m21 - m11 * m20;
		float d = -(m01 * m22 - m02 * m21);
		float e = m00 * m22 - m02 * m20;
		float f = -(m00 * m21 - m01 * m20);
		float g = m01 * m12 - m02 * m11;
		float h = -(m00 * m12 - m02 * m10);
		float i = m00 * m11 - m01 * m10;
		m00 = a * indet;
		m10 = b * indet;
		m20 = c * indet;
		m01 = d * indet;
		m11 = e * indet;
		m21 = f * indet;
		m02 = g * indet;
		m12 = h * indet;
		m22 = i * indet;
		return this;
	}

	/**
	 * <p>inverse.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix3F} object
	 */
	public Matrix3F inverse()
	{
		Matrix3F m = new Matrix3F();
		float indet = 1F / determinant();
		float a = m11 * m22 - m12 * m21;
		float b = -(m10 * m22 - m12 * m20);
		float c = m10 * m21 - m11 * m20;
		float d = -(m01 * m22 - m02 * m21);
		float e = m00 * m22 - m02 * m20;
		float f = -(m00 * m21 - m01 * m20);
		float g = m01 * m12 - m02 * m11;
		float h = -(m00 * m12 - m02 * m10);
		float i = m00 * m11 - m01 * m10;
		m.m00 = a * indet;
		m.m10 = b * indet;
		m.m20 = c * indet;
		m.m01 = d * indet;
		m.m11 = e * indet;
		m.m21 = f * indet;
		m.m02 = g * indet;
		m.m12 = h * indet;
		m.m22 = i * indet;
		return m;
	}

	/**
	 * <p>apply.</p>
	 *
	 * @param def a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	public Vector2F apply(Vector2F def)
	{
		if(def == null) def = new Vector2F(0, 1);

		return def;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		// (m00, m01, m02)
		// (m10, m11, m12)
		// (m20, m21, m22)

		// (  a,   b,   c)
		// (  d,   e,   f)
		// (  g,   h,   i)
		return "(" + m00 + ", " + m01 + ", " + m02 + ")\n" + "(" + m10 + ", " + m11 + ", " + m12 + ")\n" + "(" + m20 + ", " + m21 + ", " + m22 + ")";
	}

	/** {@inheritDoc} */
	@Override
	public Matrix3F clone()
	{
		try
		{
			return (Matrix3F) super.clone();
		}
		catch (CloneNotSupportedException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 89 * hash + Float.floatToIntBits(m00);
		hash = 89 * hash + Float.floatToIntBits(m01);
		hash = 89 * hash + Float.floatToIntBits(m02);
		hash = 89 * hash + Float.floatToIntBits(m10);
		hash = 89 * hash + Float.floatToIntBits(m11);
		hash = 89 * hash + Float.floatToIntBits(m12);
		hash = 89 * hash + Float.floatToIntBits(m20);
		hash = 89 * hash + Float.floatToIntBits(m21);
		hash = 89 * hash + Float.floatToIntBits(m22);
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Matrix3F)) return false;
		final Matrix3F other = (Matrix3F) obj;
		return Float.floatToIntBits(m00) == Float.floatToIntBits(other.m00) && Float.floatToIntBits(m01) == Float.floatToIntBits(other.m01) && Float.floatToIntBits(m02) == Float.floatToIntBits(other.m02) && Float.floatToIntBits(m10) == Float.floatToIntBits(other.m10) && Float.floatToIntBits(m11) == Float.floatToIntBits(other.m11) && Float.floatToIntBits(m12) == Float.floatToIntBits(other.m12) && Float.floatToIntBits(m20) == Float.floatToIntBits(other.m20) && Float.floatToIntBits(m21) == Float.floatToIntBits(other.m21) && Float.floatToIntBits(m22) == Float.floatToIntBits(other.m22);
	}

	private static final long serialVersionUID = 6892904438255608380L;
}