package com.hk.util;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * <p>Version class.</p>
 *
 * @author theKayani
 */
public final class Version implements Comparable<Version>, Serializable, Cloneable
{
	public final int major, minor, revision;

	/**
	 * <p>Constructor for Version.</p>
	 *
	 * @param major a int
	 * @param minor a int
	 * @param revision a int
	 */
	public Version(int major, int minor, int revision)
	{
		this.major = Requirements.requireInBounds(0, major, Integer.MAX_VALUE);
		this.minor = Requirements.requireInBounds(0, minor, Integer.MAX_VALUE);
		this.revision = Requirements.requireInBounds(0, revision, Integer.MAX_VALUE);
	}

	/**
	 * <p>Constructor for Version.</p>
	 *
	 * @param versionString a {@link java.lang.String} object
	 */
	public Version(@NotNull String versionString)
	{
		String[] sp = versionString.split("\\.");
		if(sp.length != 3)
		{
			throw new IllegalArgumentException("Invalid version input for string. Ex. (x.x.x)");
		}
		try
		{
			major = Requirements.requireInBounds(0, Integer.parseInt(sp[0]), Integer.MAX_VALUE);
			minor = Requirements.requireInBounds(0, Integer.parseInt(sp[1]), Integer.MAX_VALUE);
			revision = Requirements.requireInBounds(0, Integer.parseInt(sp[2]), Integer.MAX_VALUE);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException("Invalid number found: " + versionString);
		}
	}

	/**
	 * <p>clone.</p>
	 *
	 * @return a {@link com.hk.util.Version} object
	 */
	public Version clone()
	{
		try
		{
			return (Version) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(@NotNull Version e)
	{
		int mjc = Integer.compare(major, e.major);
		if(mjc == 0)
		{
			int mc = Integer.compare(minor, e.minor);
			if(mc == 0)
			{
				return Integer.compare(revision, e.revision);
			}
		}
		return mjc;
	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	@NotNull
	public String toString()
	{
		return major + "." + minor + "." + revision;
	}

	/**
	 * <p>hashCode.</p>
	 *
	 * @return a int
	 */
	public int hashCode()
	{
		int hash = 17;
		hash = hash * 31 + major;
		hash = hash * 31 + minor;
		hash = hash * 31 + revision;
		return hash;
	}

	/** {@inheritDoc} */
	public boolean equals(Object o)
	{
		return o instanceof Version && ((Version) o).major == major && ((Version) o).minor == minor && ((Version) o).revision == revision;
	}

	private static final long serialVersionUID = 2773312361385008226L;
}