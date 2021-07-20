package com.hk.func;

/**
 * <p>Consumer interface.</p>
 *
 * @author theKayani
 */
public interface Consumer<X>
{
	/**
	 * <p>accept.</p>
	 *
	 * @param x a X object
	 */
	public void accept(X x);
}
