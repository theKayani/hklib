package com.hk.str;


import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * This class contains utility methods for handling strings.
 *
 * @author theKayani
 */
public class StringUtil
{
	/**
	 * <p>Capitalize first letters of words</p>
	 *
	 * @param string a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	@Contract("null -> null; !null -> !null")
	public static String properCapitalize(String string)
	{
		if(string == null || string.trim().isEmpty()) return string;
		StringBuilder r = new StringBuilder();
		for(int i = string.length() - 1; i >= 0; i--)
		{
			char a = string.charAt(i);

			if(i == 0 || !Character.isAlphabetic(string.charAt(i - 1)))
				a = Character.toUpperCase(a);
			else
				a = Character.toLowerCase(a);

			r.insert(0, a);
		}
		return r.toString();
	}

	/**
	 * <p>Turns any long int into a readable string in the US and UK
	 * locale, ex <code>1,234</code>. This can also properly format
	 * negative numbers.</p>
	 * <p>Here are some example of what this would produce:</p>
	 * <ul>
	 *     <li><code>commaFormat(1) -&gt; "1"</code></li>
	 *     <li><code>commaFormat(-1) -&gt; "-1"</code></li>
	 *     <li><code>commaFormat(12345L) -&gt; "12,345"</code></li>
	 *     <li><code>commaFormat(-6789) -&gt; "-6,789"</code></li>
	 *     <li><code>commaFormat(-1000000000000000000L) -&gt; "-1,000,000,000,000,000,000"</code></li>
	 *     <li><code>commaFormat(Long.MIN_VALUE) -&gt; "-9,223,372,036,854,775,808"</code></li>
	 * </ul>
	 *
	 * @param num the long int to format
	 * @return a string with thousands grouped by commas
	 */
	@NotNull
	public static String commaFormat(long num)
	{
		if(num == 0)
			return "0";
		if(num == Long.MIN_VALUE)
			return "-9,223,372,036,854,775,808";

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
	 * <p>Pad a base string with another to match up to a specified
	 * length on the <b>left</b> side of the base string.</p>
	 *
	 * @param length specify the length that the resulting string
	 *               should match or exceed
	 * @param string the base string to pad <i>ONLY</i> on the left side
	 * @param padding the string to pad the base string with
	 * @return a string with a length equal to or greater than the
	 *          specified length.
	 * @throws IllegalArgumentException if padding is empty or length
	 * is zero or a negative number.
	 */
	public static String atLeast(int length, String string, String padding)
	{
		return atLeast(length, string, padding, true);
	}

	/**
	 * <p>Pad a base string with another to match up to a specified
	 * length on either side of the base string.</p>
	 *
	 * @param length specify the length that the resulting string
	 *               should match or exceed
	 * @param string the base string to pad on the left or right side
	 * @param padding the string to pad the base string with
	 * @param leftSide if true, the string will be padded from the
	 *                 left, if false, the string will be padded from
	 *                 the right
	 * @return a string with a length equal to or greater than the
	 *          specified length.
	 * @throws IllegalArgumentException if padding is empty or length
	 * is zero or a negative number.
	 */
	@NotNull
	public static String atLeast(int length, @NotNull String string, @NotNull String padding, boolean leftSide)
	{
		if(padding.isEmpty())
			throw new IllegalArgumentException("padding should not be empty");
		if(length <= 0)
			throw new IllegalArgumentException("length should be a non-zero positive integer");

		StringBuilder sb = new StringBuilder(string);
		while(sb.length() < length)
		{
			if(leftSide)
				sb.insert(0, padding);
			else
				sb.append(padding);
		}
		return sb.toString();
	}

	/**
	 * <p>Returns a string copy of the specified string repeated the
	 * specified amount of times. <b>Note: This can easily overflow
	 * memory.</b></p>
	 * <p>If the string is empty or amount is zero, then an empty
	 * string is returned.</p>
	 *
	 * @param string to repeat
	 * @param amount of times to repeat given string
	 * @return a string with length equal to the length of the given
	 *          string multiplied by the amount.
	 * @throws IllegalArgumentException if amount is a negative number
	 */
	@NotNull
	public static String repeat(@NotNull String string, int amount)
	{
		if(amount < 0)
			throw new IllegalArgumentException("amount should be a positive integer");
		if(string.isEmpty() || amount == 0)
			return "";

		StringBuilder sb = new StringBuilder(string.length() * amount);
		for(; amount > 0; amount--)
			sb.append(string);

		return sb.toString();
	}

	/**
	 * <p>isNumeric.</p>
	 *
	 * @param cs a {@link java.lang.CharSequence} object
	 * @return a boolean
	 */
	public static boolean isNumeric(@NotNull CharSequence cs)
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
	public static boolean isNumeric(@NotNull CharSequence cs, int base)
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
	public static boolean isDecimal(@NotNull String s)
	{
		int i = s.indexOf('.');
		if(i >= 0)
			return isNumeric(s.substring(0, i)) && isNumeric(s.substring(i + 1));
		return isNumeric(s);
	}

	/**
	 * <p>Gets the captures within the input string using the
	 * specified pattern. Here's an example:</p>
	 * <code>capturesOf("2F-0392-EE", "(.{2})-(.{4})-(.{2})")</code>
	 * <p>would return ["2F", "0392", "EE"]. This is because the pattern has only
	 * one capture and the pattern full matches the input. If the
	 * pattern has no captures, an <code>IllegalArgumentException</code>
	 * is thrown.</p>
	 *
	 * @param input the input to match with
	 * @param regex the pattern to use
	 * @return the single captured value in the input when matched
	 *          against the pattern or null if no match was found
	 * @throws PatternSyntaxException if the regex doesn't compile
	 */
	@Nullable
	public static String[] capturesOf(@NotNull String input, @NotNull @RegExp String regex) throws PatternSyntaxException
	{
		return capturesOf(input, Pattern.compile(regex));
	}

	/**
	 * <p>Gets the captures within the input string using the
	 * specified pattern. See {@link #capturesOf(String, String)}</p>
	 *
	 * @see #capturesOf(String, String)
	 * @param input the input to match with
	 * @param pattern the pattern to use
	 * @return the single captured value in the input when matched
	 *          against the pattern or null if no match was found
	 */
	@Nullable
	public static String[] capturesOf(@NotNull String input, @NotNull Pattern pattern)
	{
		Matcher matcher = pattern.matcher(input);

		if(!matcher.matches())
			return null;
		if(matcher.groupCount() == 0)
			throw new IllegalArgumentException("no captures in this pattern");

		String[] captures = new String[matcher.groupCount()];
		for (int i = 0; i < matcher.groupCount(); i++)
			captures[i] = matcher.group(i + 1);

		return captures;
	}

	/**
	 * <p>Get the single capture within the input string using the
	 * specified pattern. Here's an example:</p>
	 * <code>captureOf("2F-0392-EE", ".{2}-(.{4})-.{2}")</code>
	 * <p>would return "0392". This is because the pattern has only
	 * one capture and the pattern full matches the input. If the
	 * pattern had more than one capture, or none, an <code>IllegalArgumentException</code>
	 * is thrown. If the input doesn't match the pattern, an <code>IllegalStateException</code>
	 * is thrown.</p>
	 *
	 * @param input the input to match with
	 * @param regex the pattern to use
	 * @return the single captured value in the input when matched
	 *          against the pattern or null if no match was found
	 * @throws PatternSyntaxException if the regex doesn't compile
	 */
	@Nullable
	public static String captureOf(@NotNull String input, @NotNull @RegExp String regex) throws PatternSyntaxException
	{
		return captureOf(input, Pattern.compile(regex));
	}


	/**
	 * <p>Get the single capture within the input string using the
	 * specified pattern. See {@link #captureOf(String, String)}</p>
	 *
	 * @see #captureOf(String, String)
	 * @param input the input to match with
	 * @param pattern the pattern to use
	 * @return the single captured value in the input when matched
	 *          against the pattern or null if no match was found
	 */
	@Nullable
	public static String captureOf(@NotNull String input, @NotNull Pattern pattern)
	{
		String[] strs = capturesOf(input, pattern);

		if(strs == null)
			return null;
		else if(strs.length == 1)
			return strs[0];
		else
			throw new IllegalArgumentException("multiple captures in pattern");
	}

	/**
	 * <p>Find all the captures in the input string that match the
	 * pattern provided. Here's an example:</p>
	 * <code>findAllCapturesOf("this is my sentence", "(\\w)(\\w+)")</code>
	 * <p>The resulting list would look something like this:</p>
	 * <code>[ ["t", "his"], ["i", "s"], ["m", "y"], ["s", "entence"] ]</code>
	 * <p>If the pattern has no captures, an <code>IllegalArgumentException</code>
	 * is thrown.</p>
	 *
	 * @param input the input to match with
	 * @param regex the pattern to use
	 * @return a list of all the captures found.
	 * @throws PatternSyntaxException if the regex doesn't compile
	 */
	@NotNull
	public static List<String[]> findAllCapturesOf(@NotNull String input, @NotNull @RegExp String regex) throws PatternSyntaxException
	{
		return findAllCapturesOf(input, Pattern.compile(regex));
	}

	/**
	 * <p>Find all the captures in the input string that match the
	 * pattern provided. See {@link #findAllCapturesOf(String, String)}</p>
	 *
	 * @param input the input to match with
	 * @param pattern the pattern to use
	 * @return a list of all the captures found.
	 */
	@NotNull
	public static List<String[]> findAllCapturesOf(@NotNull String input, @NotNull Pattern pattern)
	{
		Matcher matcher = pattern.matcher(input);

		List<String[]> list = new ArrayList<>();
		while(matcher.find())
		{
			if(matcher.groupCount() == 0)
				throw new IllegalArgumentException("no captures in this pattern");

			String[] captures = new String[matcher.groupCount()];
			for (int i = 0; i < matcher.groupCount(); i++)
				captures[i] = matcher.group(i + 1);
			list.add(captures);
		}

		return list;
	}

	/**
	 * <p>Find each capture in the input string that match the
	 * pattern provided. Here's an example:</p>
	 * <code>findCapturesOf("this is my sentence", "(\\w+)")</code>
	 * <p>The resulting array would look something like this:</p>
	 * <code>["this", "is", "my", "sentence"]</code>
	 * <p>If the pattern has no captures, an <code>IllegalArgumentException</code>
	 * is thrown.</p>
	 * <p>If no matches were found, null is returned.</p>
	 *
	 * @param input the input to match with
	 * @param regex the pattern to use
	 * @return a list of all the captures found or null if no matches
	 *          were found.
	 * @throws PatternSyntaxException if the regex doesn't compile
	 */
	@Nullable
	public static String[] findCapturesOf(@NotNull String input, @NotNull @RegExp String regex) throws PatternSyntaxException
	{
		return findCapturesOf(input, Pattern.compile(regex));
	}

	/**
	 * <p>Find each capture in the input string that match the
	 * pattern provided. See {@link #findCapturesOf(String, String)}</p>
	 *
	 * @param input the input to match with
	 * @param pattern the pattern to use
	 * @return a list of all the captures found or null if no matches
	 *          were found.
	 */
	@Nullable
	public static String[] findCapturesOf(@NotNull String input, @NotNull Pattern pattern)
	{
		List<String[]> strs = findAllCapturesOf(input, pattern);
		if(strs.isEmpty())
			return null;

		String[] arr = new String[strs.size()];
		String[] arr2;
		for (int i = 0; i < arr.length; i++)
		{
			arr2 = strs.get(i);
			if(arr2.length > 1)
				throw new IllegalArgumentException("multiple captures in pattern");
			arr[i] = arr2[0];
		}
		return arr;
	}

	/**
	 * <p>Find single capture in the input string that match the
	 * pattern provided. Here's an example:</p>
	 * <code>findCaptureOf("this is my sentence", "my (.+)")</code>
	 * <p>The resulting string would look something like this:</p>
	 * <code>"sentence"</code>
	 * <p>If the pattern has no captures, an <code>IllegalArgumentException</code>
	 * is thrown.</p>
	 * <p>If the pattern has more than one capture, an <code>IllegalArgumentException</code>
	 * is thrown.</p>
	 * <p>If no matches were found, null is returned.</p>
	 *
	 * @param input the input to match with
	 * @param regex the pattern to use
	 * @return a list of all the captures found or null if no matches
	 *          were found.
	 * @throws PatternSyntaxException if the regex doesn't compile
	 */
	@Nullable
	public static String findCaptureOf(@NotNull String input, @NotNull @RegExp String regex) throws PatternSyntaxException
	{
		return findCaptureOf(input, Pattern.compile(regex));
	}

	/**
	 * <p>Find single capture in the input string that match the
	 * pattern provided. See {@link #findCaptureOf(String, String)}</p>
	 *
	 * @param input the input to match with
	 * @param pattern the pattern to use
	 * @return a list of all the captures found or null if no matches
	 *          were found.
	 */
	@Nullable
	public static String findCaptureOf(@NotNull String input, @NotNull Pattern pattern)
	{
		List<String[]> strs = findAllCapturesOf(input, pattern);
		if(strs.isEmpty())
			return null;
		String[] arr = strs.get(0);
		if(arr.length > 1)
			throw new IllegalArgumentException("multiple captures in pattern");
		return arr[0];
	}

	private StringUtil() {}
}