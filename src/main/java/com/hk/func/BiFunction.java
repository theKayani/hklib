package com.hk.func;

/**
 * <p>BiFunction interface.</p>
 *
 * @author theKayani
 */
public interface BiFunction<X, Y, Z>
{
	/**
	 * <p>apply.</p>
	 *
	 * @param x a X object
	 * @param y a Y object
	 * @return a Z object
	 */
	Z apply(X x, Y y);
}
