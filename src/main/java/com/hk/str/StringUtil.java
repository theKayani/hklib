package com.hk.str;


/**
 * <p>StringUtil class.</p>
 *
 * @author theKayani
 */
public class StringUtil
{
	/**
	 * <p>properCapitalize.</p>
	 *
	 * @param s a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String properCapitalize(String s)
	{
		if(s == null || s.trim().isEmpty()) return s;
		StringBuilder r = new StringBuilder();
		for(int i = s.length() - 1; i >= 0; i--)
		{
			char a = s.charAt(i);

			if(i == 0 || Character.isAlphabetic(s.charAt(i - 1)))
				a = Character.toLowerCase(a);

			r.insert(0, a);
		}
		return r.toString();
	}

	/**
	 * <p>commaFormat.</p>
	 *
	 * @param num a long
	 * @return a {@link java.lang.String} object
	 */
	public static String commaFormat(long num)
	{
		if(num == 0) return "0";

		boolean neg = num < 0;
		num = neg ? -num : num;
		int amt = 0;
		StringBuilder sb = new StringBuilder();
		while(num > 0)
		{
			if(amt > 0 && amt % 3 == 0) sb.insert(0, ",");

			amt++;
			sb.insert(0, num % 10);
			num /= 10;
		}
		if(neg) sb.insert(0, '-');

		return sb.toString();
	}

	/**
	 * <p>atLeast.</p>
	 *
	 * @param length a int
	 * @param s a {@link java.lang.String} object
	 * @param padding a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String atLeast(int length, String s, String padding)
	{
		return atLeast(length, s, padding, true);
	}

	/**
	 * <p>atLeast.</p>
	 *
	 * @param length a int
	 * @param s a {@link java.lang.String} object
	 * @param padding a {@link java.lang.String} object
	 * @param leftSide a boolean
	 * @return a {@link java.lang.String} object
	 */
	public static String atLeast(int length, String s, String padding, boolean leftSide)
	{
		while(s.length() < length)
		{
			s = leftSide ? padding + s : s + padding;
		}
		return s;
	}

	/**
	 * <p>repeat.</p>
	 *
	 * @param string a {@link java.lang.String} object
	 * @param amount a int
	 * @return a {@link java.lang.String} object
	 */
	public static String repeat(String string, int amount)
	{
		StringBuilder sb = new StringBuilder();
		for(; amount > 0; amount--)
		{
			sb.append(string);
		}
		return sb.toString();
	}

	/**
	 * <p>isNumeric.</p>
	 *
	 * @param cs a {@link java.lang.CharSequence} object
	 * @return a boolean
	 */
	public static boolean isNumeric(CharSequence cs)
	{
		return isNumeric(cs, 10);
	}

	/**
	 * <p>isNumeric.</p>
	 *
	 * @param cs a {@link java.lang.CharSequence} object
	 * @param base a int
	 * @return a boolean
	 */
	public static boolean isNumeric(CharSequence cs, int base)
	{
		String bss = "0123456789ABCDEF";
		for(int i = 0; i < cs.length(); i++)
		{
			int indx = bss.indexOf(Character.toUpperCase(cs.charAt(i)));
			if(indx == -1 || indx >= base)
			{
				return false;
			}
		}
		return cs.length() > 0;
	}

	/**
	 * <p>isDecimal.</p>
	 *
	 * @param s a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean isDecimal(String s)
	{
		int i = s.indexOf('.');
		if(i >= 0)
			return isNumeric(s.substring(0, i)) && isNumeric(s.substring(i + 1));
		return isNumeric(s);
	}

	private StringUtil() {}
}