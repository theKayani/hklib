package com.hk.abs;

/**
 * <p>This interface is described to represent objects can have an access
 * period in which they can be used. Otherwise, they will/should be
 * locked.</p>
 *
 * <p>A thing to do is to implement a return from the lock method so
 * that when an object is locked, it must return a key that should be
 * used to unlock the object.</p>
 *
 * @see Unlockable
 * @see com.hk.collections.lists.LockableList
 * @see java.lang.IllegalStateException
 *
 * @author theKayani
 */
public interface Lockable
{
	/**
	 * Check whether this object is currently locked.
	 *
	 * @return if this object is locked or not
	 */
	boolean isLocked();

	/**
	 * Lock the current object, given it can be locked by the current
	 * call. This might mean the resource is inaccessible or
	 * immutable until unlocked.

	 * @throws java.lang.IllegalStateException if the implementation does not
	 * allow at this period of time, or with this configuration.
	 */
	void lock() throws IllegalStateException;
}