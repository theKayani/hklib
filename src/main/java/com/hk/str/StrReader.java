package com.hk.str;

import java.io.Serializable;
import java.util.Objects;

import com.hk.util.Requirements;

/**
 * <p>StrReader class.</p>
 *
 * @author theKayani
 */
public class StrReader implements CharSequence, Serializable, Cloneable
{
	public final String str;
	private int position;

	/**
	 * <p>Constructor for StrReader.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 */
	public StrReader(String str)
	{
		this(str, 0);
	}

	/**
	 * <p>Constructor for StrReader.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @param position a int
	 */
	public StrReader(String str, int position)
	{
		this.str = Requirements.requireNotNull(str);
		this.position = position;
	}

	/**
	 * <p>indx.</p>
	 *
	 * @return a int
	 */
	public int indx()
	{
		return position;
	}

	/**
	 * <p>hasNext.</p>
	 *
	 * @return a boolean
	 */
	public boolean hasNext()
	{
		return position < length();
	}

	/**
	 * <p>hasNext.</p>
	 *
	 * @param amt a int
	 * @return a boolean
	 */
	public boolean hasNext(int amt)
	{
		return position + amt < length();
	}

	/**
	 * <p>hasPrev.</p>
	 *
	 * @return a boolean
	 */
	public boolean hasPrev()
	{
		return position > 1;
	}

	/**
	 * <p>hasPrev.</p>
	 *
	 * @param amt a int
	 * @return a boolean
	 */
	public boolean hasPrev(int amt)
	{
		return position - amt > 1;
	}

	/**
	 * <p>next.</p>
	 *
	 * @return a char
	 */
	public char next()
	{
		return inBound(position) ? str.charAt(position++) : '\u0000';
	}

	/**
	 * <p>peek.</p>
	 *
	 * @return a char
	 */
	public char peek()
	{
		return inBound(position) ? str.charAt(position) : '\u0000';
	}

	/**
	 * <p>current.</p>
	 *
	 * @return a char
	 */
	public char current()
	{
		return inBound(position - 1) ? str.charAt(position - 1) : '\u0000';
	}

	/**
	 * <p>prev.</p>
	 *
	 * @return a char
	 */
	public char prev()
	{
		return inBound(position - 2) ? str.charAt(position -= 2) : '\u0000';
	}

	/**
	 * <p>poll.</p>
	 *
	 * @return a char
	 */
	public char poll()
	{
		return inBound(position - 2) ? str.charAt(position - 2) : '\u0000';
	}

	/**
	 * <p>nextString.</p>
	 *
	 * @param amt a int
	 * @return a {@link java.lang.String} object
	 */
	public String nextString(int amt)
	{
		String s = peekString(amt);
		position += Math.max(0, amt);
		return s;
	}

	/**
	 * <p>peekString.</p>
	 *
	 * @param amt a int
	 * @return a {@link java.lang.String} object
	 */
	public String peekString(int amt)
	{
		amt = Math.max(0, amt);
		StringBuilder s = new StringBuilder();
		for(int i = position; i < position + amt; i++)
		{
			if(!inBound(i)) break;
			s.append(str.charAt(i));
		}
		return s.toString();
	}

	/**
	 * <p>prevString.</p>
	 *
	 * @param amt a int
	 * @return a {@link java.lang.String} object
	 */
	public String prevString(int amt)
	{
		String s = pollString(amt);
		position -= Math.max(0, amt);
		return s;
	}

	/**
	 * <p>pollString.</p>
	 *
	 * @param amt a int
	 * @return a {@link java.lang.String} object
	 */
	public String pollString(int amt)
	{
		amt = Math.max(0, amt);
		StringBuilder s = new StringBuilder();
		for(int i = position - amt - 1; i < position - 1; i++)
		{
			if(!inBound(i)) break;
			s.append(str.charAt(i));
		}
		return s.toString();
	}

	private boolean inBound(int i)
	{
		return i >= 0 && i < str.length();
	}

	/**
	 * <p>skipWhitespace.</p>
	 *
	 * @return a boolean
	 */
	public boolean skipWhitespace()
	{
		int pos = position;
		while(hasNext() && Character.isWhitespace(peek()))
		{
			next();
		}
		return pos != position;
	}

	/**
	 * <p>getCurrentLine.</p>
	 *
	 * @return a int
	 */
	public int getCurrentLine()
	{
		int line = 0;
		for(int i = 0; i < position; i++)
		{
			if(str.charAt(i) == '\n')
			{
				line++;
			}
		}
		return line;
	}

	/** {@inheritDoc} */
	@Override
	public char charAt(int indx)
	{
		return str.charAt(indx);
	}

	/** {@inheritDoc} */
	@Override
	public int length()
	{
		return str.length();
	}

	/**
	 * <p>charsLeft.</p>
	 *
	 * @return a int
	 */
	public int charsLeft()
	{
		return length() - position;
	}

	/** {@inheritDoc} */
	@Override
	public StrReader subSequence(int indx1, int indx2)
	{
		return new StrReader(str.substring(indx1, indx2));
	}

	/**
	 * <p>clone.</p>
	 *
	 * @return a {@link com.hk.str.StrReader} object
	 */
	public StrReader clone()
	{
		return new StrReader(str, position);
	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString()
	{
		return str;
	}

	/**
	 * <p>hashCode.</p>
	 *
	 * @return a int
	 */
	public int hashCode()
	{
		int hash = 19;
		hash = hash * 31 + Objects.hashCode(str);
		hash = hash * 31 + position;
		return hash;
	}

	/** {@inheritDoc} */
	public boolean equals(Object obj)
	{
		return obj instanceof StrReader && ((StrReader) obj).str.equals(str) && ((StrReader) obj).position == position;
	}

	private static final long serialVersionUID = -4642985468639148226L;
}