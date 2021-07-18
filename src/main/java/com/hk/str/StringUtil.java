package com.hk.str;


public class StringUtil
{
	public static String properCapitalize(String s)
	{
		if(s == null || s.trim().isEmpty()) return s;
		String r = "";
		for(int i = s.length() - 1; i >= 0; i--)
		{
			char a = s.charAt(i);
			
			if(i == 0 || Character.isAlphabetic(s.charAt(i - 1)))
			{
				a = Character.toLowerCase(a);
			}
			
			r = a + r;
		}
		return r;
	}
	
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
	
	public static String atLeast(int length, String s, String padding)
	{
		return atLeast(length, s, padding, true);
	}
	
	public static String atLeast(int length, String s, String padding, boolean leftSide)
	{
		while(s.length() < length)
		{
			s = leftSide ? padding + s : s + padding;
		}
		return s;
	}

	public static String repeat(String string, int amount)
	{
		StringBuilder sb = new StringBuilder();
		for(; amount > 0; amount--)
		{
			sb.append(string);
		}
		return sb.toString();
	}

	public static boolean isNumeric(CharSequence cs)
	{
		return isNumeric(cs, 10);
	}

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
	
	public static boolean isDecimal(String s)
	{
		int i = s.indexOf('.');
		if(i >= 0)
			return isNumeric(s.substring(0, i)) && isNumeric(s.substring(i + 1));
		return isNumeric(s);
	}
	
	private StringUtil() {}
}
