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
	public boolean test(X x);
}
