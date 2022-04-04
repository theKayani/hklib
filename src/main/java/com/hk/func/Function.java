package com.hk.func;

/**
 * <p>Function interface.</p>
 *
 * @author theKayani
 */
public interface Function<X, Y>
{
	/**
	 * <p>apply.</p>
	 *
	 * @param x a X object
	 * @return a Y object
	 */
	Y apply(X x);
}
