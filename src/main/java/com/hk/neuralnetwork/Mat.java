package com.hk.neuralnetwork;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>Mat class.</p>
 *
 * @author theKayani
 */
public class Mat implements Serializable, Cloneable
{
	public final int rows, cols;
	public final double[][] data;
	
	/**
	 * <p>Constructor for Mat.</p>
	 *
	 * @param rows a int
	 * @param cols a int
	 */
	public Mat(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		this.data = new double[rows][cols];
	}
	
	/**
	 * <p>Constructor for Mat.</p>
	 *
	 * @param data an array of {@link double} objects
	 */
	public Mat(double[][] data)
	{
		this.rows = data.length;
		this.cols = data[0].length;
		this.data = new double[rows][cols];
		for(int r = 0; r < rows; r++)
		{
			System.arraycopy(data[r], 0, this.data[r], 0, cols);
		}
	}
	
	/**
	 * <p>randomize.</p>
	 *
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat randomize()
	{
		return randomize(ThreadLocalRandom.current());
	}
	
	/**
	 * <p>randomize.</p>
	 *
	 * @param rand a {@link java.util.Random} object
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat randomize(final Random rand)
	{
		return map(new MatFunc()
		{
			@Override
			public double perform(double val, int r, int c)
			{
				return rand.nextDouble() * 2 - 1;
			}
		});
	}
	
	/**
	 * <p>add.</p>
	 *
	 * @param mat a {@link com.hk.neuralnetwork.Mat} object
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat add(final Mat mat)
	{
		return new Mat(data).map(new MatFunc()
		{
			@Override
			public double perform(double val, int r, int c)
			{
				return val + mat.data[r][c];
			}
		});
	}
	
	/**
	 * <p>add.</p>
	 *
	 * @param scl a double
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat add(final double scl)
	{
		return new Mat(data).map(new MatFunc()
		{
			@Override
			public double perform(double val, int r, int c)
			{
				return val + scl;
			}
		});
	}
	
	/**
	 * <p>subtract.</p>
	 *
	 * @param mat a {@link com.hk.neuralnetwork.Mat} object
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat subtract(final Mat mat)
	{
		return new Mat(data).map(new MatFunc()
		{
			@Override
			public double perform(double val, int r, int c)
			{
				return val - mat.data[r][c];
			}
		});
	}
	
	/**
	 * <p>subtract.</p>
	 *
	 * @param scl a double
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat subtract(final double scl)
	{
		return new Mat(data).map(new MatFunc()
		{
			@Override
			public double perform(double val, int r, int c)
			{
				return val - scl;
			}
		});
	}
	
	/**
	 * <p>transpose.</p>
	 *
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat transpose()
	{
		return new Mat(cols, rows).map(new MatFunc()
		{
			@Override
			public double perform(double val, int r, int c)
			{
				return data[c][r];
			}
		});
	}
	
	/**
	 * <p>mult.</p>
	 *
	 * @param scl a double
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat mult(final double scl)
	{
		return new Mat(data).map(new MatFunc()
		{
			@Override
			public double perform(double val, int r, int c)
			{
				return val * scl;
			}
		});
	}
	
	/**
	 * <p>elementMult.</p>
	 *
	 * @param mat a {@link com.hk.neuralnetwork.Mat} object
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat elementMult(final Mat mat)
	{
		return new Mat(data).map(new MatFunc()
		{
			@Override
			public double perform(double val, int r, int c)
			{
				return val * mat.data[r][c];
			}
		});
	}
	
	/**
	 * <p>mult.</p>
	 *
	 * @param mat a {@link com.hk.neuralnetwork.Mat} object
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat mult(final Mat mat)
	{
		if(cols != mat.rows) throw new RuntimeException("Rows don't match columns");
		
		return new Mat(rows, mat.cols).map(new MatFunc()
		{
			@Override
			public double perform(double val, int r, int c)
			{
				double sum = 0;
				for(int i = 0; i < cols; i++)
				{
					sum += data[r][i] * mat.data[i][c];
				}
				return sum;
			}
		});
	}
	
	/**
	 * <p>map.</p>
	 *
	 * @param func a {@link com.hk.neuralnetwork.Mat.MatFunc} object
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat map(MatFunc func)
	{
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < cols; c++)
			{
				data[r][c] = func.perform(data[r][c], r, c);
			}
		}
		return this;
	}
	
	/**
	 * <p>toArray.</p>
	 *
	 * @return an array of {@link double} objects
	 */
	public double[] toArray()
	{
		double[] arr = new double[rows * cols];
		for(int r = 0; r < rows; r++)
		{
			if (cols >= 0) System.arraycopy(data[r], 0, arr, r * cols, cols);
		}
		return arr;
	}
	
	/**
	 * <p>getColumn.</p>
	 *
	 * @param col a int
	 * @return an array of {@link double} objects
	 */
	public double[] getColumn(int col)
	{
		double[] column = new double[rows];
		for(int i = 0; i < rows; i++)
		{
			column[i] = data[i][col];
		}
		return column;
	}

	/**
	 * <p>clone.</p>
	 *
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public Mat clone()
	{
		return new Mat(data);
	}
	
	/**
	 * <p>toArrayString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toArrayString()
	{
		return Arrays.deepToString(data);
	}
	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString()
	{		
		StringBuilder sb = new StringBuilder();
		for(int r = 0; r < rows; r++)
		{
			sb.append("[");
			for(int c = 0; c < cols; c++)
			{
				sb.append(data[r][c]);

				if(c < cols - 1) sb.append(", ");
			}
			
			sb.append(']');
			if(r < rows - 1) sb.append('\n');
		}
		return sb.toString();
	}
	
	/**
	 * <p>fromArray.</p>
	 *
	 * @param arr an array of {@link double} objects
	 * @return a {@link com.hk.neuralnetwork.Mat} object
	 */
	public static Mat fromArray(double[] arr)
	{
		Mat mat = new Mat(arr.length, 1);
		for(int i = 0; i < arr.length; i++)
		{
			mat.data[i][0] = arr[i];
		}
		return mat;
	}
	
	public interface MatFunc
	{
		double perform(double val, int r, int c);
	}
	
	/** Constant <code>SIGMOID</code> */
	public static final MatFunc SIGMOID = new MatFunc()
	{
		@Override
		public double perform(double val, int r, int c)
		{
			return 1 / (1 + Math.exp(-val));
		}
	};
	
	/** Constant <code>SIGMOID_DERIVATIVE</code> */
	public static final MatFunc SIGMOID_DERIVATIVE = new MatFunc()
	{
		@Override
		public double perform(double val, int r, int c)
		{
			return val * (1 - val);
		}
	};
	
	/** Constant <code>TANH</code> */
	public static final MatFunc TANH = new MatFunc()
	{
		@Override
		public double perform(double val, int r, int c)
		{
			return Math.tanh(val);
		}
	};
	
	/** Constant <code>TANH_DERIVATIVE</code> */
	public static final MatFunc TANH_DERIVATIVE = new MatFunc()
	{
		@Override
		public double perform(double val, int r, int c)
		{
			return 1 - val * val;
		}
	};
	
	private static final long serialVersionUID = 3107367440033528127L;
}
