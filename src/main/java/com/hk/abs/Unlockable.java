package com.hk.abs;

/**
 * Paired up with the {@link Lockable} interface, it is used to unlock
 * a previously locked object. Whether that be a resource, data
 * structure, or something else. This interface allows that object to
 * be unlocked for future usage.
 *
 * @see java.lang.IllegalStateException
 *
 * @author theKayani
 */
public interface Unlockable extends Lockable
{
	/**
	 * Unlock this object and allow it to be used, once again. Or even
	 * throw an error if the implementation does not allow this object
	 * to be unlocked. Potentially regarding the given object state
	 * or another parameter.
	 *
	 * @throws java.lang.IllegalStateException if the implementation does not
	 * allow at this period of time, or with this configuration.
	 */
	void unlock() throws IllegalStateException;
}
