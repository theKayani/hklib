package com.hk.abs;

/**
 * <p>Lockable interface.</p>
 *
 * @author theKayani
 */
public interface Lockable
{
	/**
	 * <p>isLocked.</p>
	 *
	 * @return a boolean
	 */
	public boolean isLocked();
	
	/**
	 * <p>lock.</p>
	 */
	public void lock();
}
