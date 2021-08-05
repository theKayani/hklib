package com.hk.abs;

/**
 * This interface is a utility to represent an object which has a name.
 * Mostly to be used with the Nameable interface in the case that it
 * have a mutable name.
 *
 * @see Nameable
 *
 * @author theKayani
 */
public interface Named
{
	/**
	 * Get the assigned name of this object. Can possibly be null.
	 *
	 * @return a {@link java.lang.String} object, or null.
	 */
	String getName();
}
