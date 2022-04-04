package com.hk.func;

/**
 * <p>Predicate interface.</p>
 *
 * @author theKayani
 */
public interface Predicate<X>
{
	/**
	 * <p>test.</p>
	 *
	 * @param x a X object
	 * @return a boolean
	 */
	boolean test(X x);
}
