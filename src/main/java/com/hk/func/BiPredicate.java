package com.hk.func;

/**
 * <p>BiPredicate interface.</p>
 *
 * @author theKayani
 */
public interface BiPredicate<X, Y>
{
	/**
	 * <p>test.</p>
	 *
	 * @param x a X object
	 * @param y a Y object
	 * @return a boolean
	 */
	boolean test(X x, Y y);
}
