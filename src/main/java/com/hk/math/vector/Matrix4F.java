package com.hk.math.vector;

import java.io.Serializable;

/**
 * <p>Matrix4F class.</p>
 *
 * @author theKayani
 */
public class Matrix4F implements Serializable
{
	public float m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33;

	/**
	 * <p>identity.</p>
	 *
	 * @return a {@link com.hk.math.vector.Matrix4F} object
	 */
	public Matrix4F identity()
	{
		m00 = m11 = m22 = m33 = 1F;
		m01 = m02 = m03 = m10 = m12 = m13 = m20 = m21 = m23 = m30 = m31 = m32;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param mat a {@link com.hk.math.vector.Matrix4F} object
	 * @return a {@link com.hk.math.vector.Matrix4F} object
	 */
	public Matrix4F set(Matrix4F mat)
	{
		m00 = mat.m00;
		m01 = mat.m01;
		m02 = mat.m02;
		m03 = mat.m03;
		m10 = mat.m10;
		m11 = mat.m11;
		m12 = mat.m12;
		m13 = mat.m13;
		m20 = mat.m20;
		m21 = mat.m21;
		m22 = mat.m22;
		m23 = mat.m23;
		m30 = mat.m30;
		m31 = mat.m31;
		m32 = mat.m32;
		m33 = mat.m33;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param m00 a float
	 * @param m01 a float
	 * @param m02 a float
	 * @param m03 a float
	 * @param m10 a float
	 * @param m11 a float
	 * @param m12 a float
	 * @param m13 a float
	 * @param m20 a float
	 * @param m21 a float
	 * @param m22 a float
	 * @param m23 a float
	 * @param m30 a float
	 * @param m31 a float
	 * @param m32 a float
	 * @param m33 a float
	 * @return a {@link com.hk.math.vector.Matrix4F} object
	 */
	public Matrix4F set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33)
	{
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @param val a float
	 * @return a {@link com.hk.math.vector.Matrix4F} object
	 */
	public Matrix4F set(float val)
	{
		this.m00 = val;
		this.m01 = val;
		this.m02 = val;
		this.m03 = val;
		this.m10 = val;
		this.m11 = val;
		this.m12 = val;
		this.m13 = val;
		this.m20 = val;
		this.m21 = val;
		this.m22 = val;
		this.m23 = val;
		this.m30 = val;
		this.m31 = val;
		this.m32 = val;
		this.m33 = val;
		return this;
	}

	private static final long serialVersionUID = -8448067512979362709L;
}
