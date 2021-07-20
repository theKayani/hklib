package com.hk.math;

import com.hk.util.Requirements;

/**
 * <p>FPSCounter class.</p>
 *
 * @author theKayani
 */
public class FPSCounter
{
	private double fps;
	private final int maxFrames;
	private int frames;
	private long start = -1;

	/**
	 * <p>Constructor for FPSCounter.</p>
	 *
	 * @param maxFrames a int
	 */
	public FPSCounter(int maxFrames)
	{
		this.maxFrames = Requirements.requireInBounds(1, maxFrames, Integer.MAX_VALUE);
	}

	/**
	 * <p>calc.</p>
	 */
	public void calc()
	{
		frames++;

		long time = System.currentTimeMillis();
		if (frames == maxFrames)
		{
			long elapsed = time - start;
			start = time;

			fps = 1D / (elapsed / 1000D / maxFrames);

			frames = 0;
		}
	}

	/**
	 * <p>getFPS.</p>
	 *
	 * @return a double
	 */
	public double getFPS()
	{
		return fps;
	}
}
