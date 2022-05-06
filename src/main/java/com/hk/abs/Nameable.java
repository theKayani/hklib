package com.hk.abs;

import org.jetbrains.annotations.Contract;

/**
 * This interface represents an object that has a mutable name. Which
 * means, it can be renamed given some type of parameters or object
 * state.
 *
 * @see Named
 *
 * @author theKayani
 */
public interface Nameable extends Named
{
	/**
	 * Assign a new name to this object. Can possibly be null to
	 * reassign the name, if the implementation allows it.
	 *
	 * @param name a name to assign to this object
	 */
	void setName(String name);
}
