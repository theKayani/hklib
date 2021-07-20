package com.hk.abs;

/**
 * <p>Childing interface.</p>
 *
 * <code>
 *     String s = "ello";
 * </code>
 *
 * @author theKayani
 */
public interface Childing<T extends Childing<T>>
{
	/**
	 * <p>getParent.</p>
	 *
	 * @return a T object
	 */
	public T getParent();

	/**
	 * <p>getChildren.</p>
	 *
	 * @return an array of T[] objects
	 */
	public T[] getChildren();
}
