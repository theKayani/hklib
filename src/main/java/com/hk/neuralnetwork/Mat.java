package com.hk.neuralnetwork;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Mat implements Serializable, Cloneable
{
	public final int rows, cols;
	public final double[][] data;
	
	public Mat(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		this.data = new double[rows][cols];
	}
	
	public Mat(double[][] data)
	{
		this.rows = data.length;
		this.cols = data[0].length;
		this.data = new double[rows][cols];
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < cols; c++)
			{
				this.data[r][c] = data[r][c];
			}
		}
	}
	
	public Mat randomize()
	{
		return randomize(ThreadLocalRandom.current());
	}
	
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
	
	public double[] toArray()
	{
		double[] arr = new double[rows * cols];
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < cols; c++)
			{
				arr[c + r * cols] = data[r][c];
			}
		}
		return arr;
	}
	
	public double[] getColumn(int col)
	{
		double[] column = new double[rows];
		for(int i = 0; i < rows; i++)
		{
			column[i] = data[i][col];
		}
		return column;
	}

	public Mat clone()
	{
		return new Mat(data);
	}
	
	public String toArrayString()
	{
		return Arrays.deepToString(data);
	}
	
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
		public double perform(double val, int r, int c);
	}
	
	public static MatFunc SIGMOID = new MatFunc()
	{
		@Override
		public double perform(double val, int r, int c)
		{
			return 1 / (1 + Math.exp(-val));
		}
	};
	
	public static MatFunc SIGMOID_DERIVATIVE = new MatFunc()
	{
		@Override
		public double perform(double val, int r, int c)
		{
			return val * (1 - val);
		}
	};
	
	public static MatFunc TANH = new MatFunc()
	{
		@Override
		public double perform(double val, int r, int c)
		{
			return Math.tanh(val);
		}
	};
	
	public static MatFunc TANH_DERIVATIVE = new MatFunc()
	{
		@Override
		public double perform(double val, int r, int c)
		{
			return 1 - val * val;
		}
	};
	
	private static final long serialVersionUID = 3107367440033528127L;
}
