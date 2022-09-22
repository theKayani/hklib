package com.hk.str;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtilTest extends TestCase
{
	public void testProperCapitalize()
	{
		String str;

		str = StringUtil.properCapitalize(null);
		assertNull(str);

		str = StringUtil.properCapitalize("");
		assertEquals("", str);

		str = StringUtil.properCapitalize("my project name");
		assertEquals("My Project Name", str);

		str = StringUtil.properCapitalize("MY PROJECT NAME");
		assertEquals("My Project Name", str);

		str = StringUtil.properCapitalize("TNT");
		assertEquals("Tnt", str);

		str = StringUtil.properCapitalize("tnt");
		assertEquals("Tnt", str);

		str = StringUtil.properCapitalize("USER");
		assertEquals("User", str);

		str = StringUtil.properCapitalize("JDoe");
		assertEquals("Jdoe", str);

		str = StringUtil.properCapitalize("thisVariable");
		assertEquals("Thisvariable", str);

		str = StringUtil.properCapitalize("this_is_my_variable");
		assertEquals("This_Is_My_Variable", str);

		str = StringUtil.properCapitalize("https://google.com/user/my-slug-newspaper-article");
		assertEquals("Https://Google.Com/User/My-Slug-Newspaper-Article", str);

		str = StringUtil.properCapitalize("A B C D");
		assertEquals("A B C D", str);

		str = StringUtil.properCapitalize("a B C d");
		assertEquals("A B C D", str);

		str = StringUtil.properCapitalize("A b c D");
		assertEquals("A B C D", str);

		str = StringUtil.properCapitalize(" a b c d ");
		assertEquals(" A B C D ", str);

		str = StringUtil.properCapitalize("{[\\|\"'-=;:,./<>?!@#$%^&*()\n\t`~]}");
		assertEquals("{[\\|\"'-=;:,./<>?!@#$%^&*()\n\t`~]}", str);
	}

	public void testCommaFormat()
	{
		String str;

		str = StringUtil.commaFormat(0);
		assertEquals("0", str);

		str = StringUtil.commaFormat(1);
		assertEquals("1", str);

		str = StringUtil.commaFormat(-1);
		assertEquals("-1", str);

		str = StringUtil.commaFormat(123);
		assertEquals("123", str);

		str = StringUtil.commaFormat(-123);
		assertEquals("-123", str);

		str = StringUtil.commaFormat(1234);
		assertEquals("1,234", str);

		str = StringUtil.commaFormat(-1234);
		assertEquals("-1,234", str);

		str = StringUtil.commaFormat(Long.MAX_VALUE);
		assertEquals("9,223,372,036,854,775,807", str);

		str = StringUtil.commaFormat(1000000000000000000L);
		assertEquals("1,000,000,000,000,000,000", str);
		str = StringUtil.commaFormat(100000000000000000L);
		assertEquals("100,000,000,000,000,000", str);
		str = StringUtil.commaFormat(10000000000000000L);
		assertEquals("10,000,000,000,000,000", str);

		str = StringUtil.commaFormat(1000000000000000L);
		assertEquals("1,000,000,000,000,000", str);
		str = StringUtil.commaFormat(100000000000000L);
		assertEquals("100,000,000,000,000", str);
		str = StringUtil.commaFormat(10000000000000L);
		assertEquals("10,000,000,000,000", str);

		str = StringUtil.commaFormat(1000000000000L);
		assertEquals("1,000,000,000,000", str);
		str = StringUtil.commaFormat(100000000000L);
		assertEquals("100,000,000,000", str);
		str = StringUtil.commaFormat(10000000000L);
		assertEquals("10,000,000,000", str);

		str = StringUtil.commaFormat(1000000000L);
		assertEquals("1,000,000,000", str);
		str = StringUtil.commaFormat(100000000L);
		assertEquals("100,000,000", str);
		str = StringUtil.commaFormat(10000000L);
		assertEquals("10,000,000", str);

		str = StringUtil.commaFormat(1000000L);
		assertEquals("1,000,000", str);
		str = StringUtil.commaFormat(100000L);
		assertEquals("100,000", str);
		str = StringUtil.commaFormat(10000L);
		assertEquals("10,000", str);

		str = StringUtil.commaFormat(1000L);
		assertEquals("1,000", str);
		str = StringUtil.commaFormat(100L);
		assertEquals("100", str);
		str = StringUtil.commaFormat(10L);
		assertEquals("10", str);

		str = StringUtil.commaFormat(1L);
		assertEquals("1", str);

		// should not trigger any processing, just return
		str = StringUtil.commaFormat(Long.MIN_VALUE);
		assertEquals("-9,223,372,036,854,775,808", str);

		str = StringUtil.commaFormat(-Long.MAX_VALUE);
		assertEquals("-9,223,372,036,854,775,807", str);

		str = StringUtil.commaFormat(-1000000000000000000L);
		assertEquals("-1,000,000,000,000,000,000", str);
		str = StringUtil.commaFormat(-100000000000000000L);
		assertEquals("-100,000,000,000,000,000", str);
		str = StringUtil.commaFormat(-10000000000000000L);
		assertEquals("-10,000,000,000,000,000", str);

		str = StringUtil.commaFormat(-1000000000000000L);
		assertEquals("-1,000,000,000,000,000", str);
		str = StringUtil.commaFormat(-100000000000000L);
		assertEquals("-100,000,000,000,000", str);
		str = StringUtil.commaFormat(-10000000000000L);
		assertEquals("-10,000,000,000,000", str);

		str = StringUtil.commaFormat(-1000000000000L);
		assertEquals("-1,000,000,000,000", str);
		str = StringUtil.commaFormat(-100000000000L);
		assertEquals("-100,000,000,000", str);
		str = StringUtil.commaFormat(-10000000000L);
		assertEquals("-10,000,000,000", str);

		str = StringUtil.commaFormat(-1000000000L);
		assertEquals("-1,000,000,000", str);
		str = StringUtil.commaFormat(-100000000L);
		assertEquals("-100,000,000", str);
		str = StringUtil.commaFormat(-10000000L);
		assertEquals("-10,000,000", str);

		str = StringUtil.commaFormat(-1000000L);
		assertEquals("-1,000,000", str);
		str = StringUtil.commaFormat(-100000L);
		assertEquals("-100,000", str);
		str = StringUtil.commaFormat(-10000L);
		assertEquals("-10,000", str);

		str = StringUtil.commaFormat(-1000L);
		assertEquals("-1,000", str);
		str = StringUtil.commaFormat(-100L);
		assertEquals("-100", str);
		str = StringUtil.commaFormat(-10L);
		assertEquals("-10", str);

		str = StringUtil.commaFormat(-1L);
		assertEquals("-1", str);
	}

	public void testAtLeast()
	{
		try
		{
			StringUtil.atLeast(2, "1", "");
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException e)
		{
			assertTrue(e.getLocalizedMessage().contains("should not be empty"));
		}
		try
		{
			StringUtil.atLeast(0, "1", "1");
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException e)
		{
			assertTrue(e.getLocalizedMessage().contains("non-zero positive integer"));
		}
		try
		{
			StringUtil.atLeast(-1, "1", "1");
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException e)
		{
			assertTrue(e.getLocalizedMessage().contains("non-zero positive integer"));
		}

		String str;

		str = StringUtil.atLeast(4, "00000000", "1");
		assertEquals("00000000", str);

		str = StringUtil.atLeast(4, "00000000", "1", true);
		assertEquals("00000000", str);

		str = StringUtil.atLeast(4, "00000000", "1", false);
		assertEquals("00000000", str);

		str = StringUtil.atLeast(8, "00000000", "1");
		assertEquals("00000000", str);

		str = StringUtil.atLeast(8, "00000000", "1", true);
		assertEquals("00000000", str);

		str = StringUtil.atLeast(8, "00000000", "1", false);
		assertEquals("00000000", str);

		str = StringUtil.atLeast(8, "0000000", "1");
		assertEquals("10000000", str);

		str = StringUtil.atLeast(8, "0000000", "1", true);
		assertEquals("10000000", str);

		str = StringUtil.atLeast(8, "0000000", "1", false);
		assertEquals("00000001", str);

		str = StringUtil.atLeast(8, "0000", "1");
		assertEquals("11110000", str);

		str = StringUtil.atLeast(8, "0000", "1", true);
		assertEquals("11110000", str);

		str = StringUtil.atLeast(8, "0000", "1", false);
		assertEquals("00001111", str);

		str = StringUtil.atLeast(8, "0", "1");
		assertEquals("11111110", str);

		str = StringUtil.atLeast(8, "0", "1", true);
		assertEquals("11111110", str);

		str = StringUtil.atLeast(8, "0", "1", false);
		assertEquals("01111111", str);

		str = StringUtil.atLeast(4, "aaaaaaaa", "b");
		assertEquals("aaaaaaaa", str);

		str = StringUtil.atLeast(4, "aaaaaaaa", "b", true);
		assertEquals("aaaaaaaa", str);

		str = StringUtil.atLeast(4, "aaaaaaaa", "b", false);
		assertEquals("aaaaaaaa", str);

		str = StringUtil.atLeast(8, "aaaaaaaa", "b");
		assertEquals("aaaaaaaa", str);

		str = StringUtil.atLeast(8, "aaaaaaaa", "b", true);
		assertEquals("aaaaaaaa", str);

		str = StringUtil.atLeast(8, "aaaaaaaa", "b", false);
		assertEquals("aaaaaaaa", str);

		str = StringUtil.atLeast(8, "aaaaaaa", "b");
		assertEquals("baaaaaaa", str);

		str = StringUtil.atLeast(8, "aaaaaaa", "b", true);
		assertEquals("baaaaaaa", str);

		str = StringUtil.atLeast(8, "aaaaaaa", "b", false);
		assertEquals("aaaaaaab", str);

		str = StringUtil.atLeast(8, "aaaa", "b");
		assertEquals("bbbbaaaa", str);

		str = StringUtil.atLeast(8, "aaaa", "b", true);
		assertEquals("bbbbaaaa", str);

		str = StringUtil.atLeast(8, "aaaa", "b", false);
		assertEquals("aaaabbbb", str);

		str = StringUtil.atLeast(8, "a", "b");
		assertEquals("bbbbbbba", str);

		str = StringUtil.atLeast(8, "a", "b", true);
		assertEquals("bbbbbbba", str);

		str = StringUtil.atLeast(8, "a", "b", false);
		assertEquals("abbbbbbb", str);

		str = StringUtil.atLeast(8, "a", "b", false);
		assertEquals("abbbbbbb", str);

		str = StringUtil.atLeast(15, str, "b");
		assertEquals("bbbbbbbabbbbbbb", str);

		str = "0";
		while(str.length() < 32)
			str = StringUtil.atLeast(str.length() + 1, str, String.valueOf(str.length() % 2), str.length() % 2 == 0);

		assertEquals("00000000000000001111111111111111", str);

		str = "0";
		while(str.length() < 32)
			str = StringUtil.atLeast(str.length() + 1, str, String.valueOf(str.length() % 2));

		assertEquals("10101010101010101010101010101010", str);
	}

	public void testRepeat()
	{
		try
		{
			StringUtil.repeat("1", -1);
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException e)
		{
			assertTrue(e.getLocalizedMessage().contains("should be a positive integer"));
		}

		String str;

		// should not trigger loop, should be instantaneous
		str = StringUtil.repeat("", 0);
		assertEquals("", str);

		// should not trigger loop, should be instantaneous
		str = StringUtil.repeat("", 1);
		assertEquals("", str);

		// should not trigger loop, should be instantaneous
		str = StringUtil.repeat("", 10);
		assertEquals("", str);

		// should not trigger loop, should be instantaneous
		str = StringUtil.repeat("", Integer.MAX_VALUE);
		assertEquals("", str);

		// should not trigger loop, should be instantaneous
		char[] cs =  new char[1024 * 1024]; // 2MB or RAM?
		Arrays.fill(cs, 'a');
		str = StringUtil.repeat(new String(cs), 0);
		assertEquals("", str);

		str = StringUtil.repeat("1", 2);
		assertEquals("11", str);

		str = StringUtil.repeat("1", 8);
		assertEquals("11111111", str);

		str = StringUtil.repeat("a", 8);
		assertEquals("aaaaaaaa", str);

		str = StringUtil.repeat("10", 8);
		assertEquals("1010101010101010", str);

		str = StringUtil.repeat("01", 8);
		assertEquals("0101010101010101", str);

		str = StringUtil.repeat("10", 16);
		assertEquals("10101010101010101010101010101010", str);

		str = StringUtil.repeat("01", 16);
		assertEquals("01010101010101010101010101010101", str);

		str = StringUtil.repeat("ba", 16);
		assertEquals("babababababababababababababababa", str);

		str = "a";
		for (int i = 0; i < 20; i++)
			str = StringUtil.repeat(str, 2);

		assertEquals(new String(cs), str);
	}

	public void testCapturesOf()
	{
		try
		{
			StringUtil.capturesOf("abc", "[a-zA-Z]+");
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains("no captures"));
		}

		Pattern ptn;
		String[] strs;
		String str;

		strs = StringUtil.capturesOf("123", "[a-zA-Z]+");
		assertNull(strs);

		strs = StringUtil.capturesOf("abc", "([a-zA-Z]+)");
		assertEquals(1, strs.length);
		assertEquals("abc", strs[0]);

		strs = StringUtil.capturesOf("555-678-5555", "(\\d{3})-(\\d{3})-(\\d{4})");
		assertEquals(3, strs.length);
		assertEquals("555-678-5555", String.join("-", strs));

		ptn = Pattern.compile("(https://[\\w.]+.com)([\\w-_/]*?)\\?(.*)");
		strs = StringUtil.capturesOf("https://google.com/stuff/and/things?q=random+dancing", ptn);

		assertEquals(3, strs.length);
		assertEquals("https://google.com", strs[0]);
		assertEquals("/stuff/and/things", strs[1]);
		assertEquals("q=random+dancing", strs[2]);
		strs = StringUtil.capturesOf("https://subdomain.example.com/random/dancing?stuff=things", ptn);

		assertEquals(3, strs.length);
		assertEquals("https://subdomain.example.com", strs[0]);
		assertEquals("/random/dancing", strs[1]);
		assertEquals("stuff=things", strs[2]);
	}

	public void testCaptureOf()
	{
		Pattern ptn;
		String str;

		try
		{
			StringUtil.captureOf("abc-dev", "([a-z]+)-([a-z]+)");
			fail("expected IllegalArgumentException");
		}
		catch(IllegalArgumentException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains("multiple captures"));
		}

		str = StringUtil.captureOf("abc", "([a-zA-Z]+)");
		assertEquals("abc", str);

		str = StringUtil.captureOf("John: /command", "(.+?): /command");
		assertEquals("John", str);

		str = StringUtil.captureOf("my favorite food is spaghetti.", "my favorite food is (.*?).");
		assertEquals("spaghetti", str);
	}

	public void testFindCapturesOf()
	{
		String[] strs;

		strs = StringUtil.findCapturesOf("123", "([a-z])");
		assertNull(strs);

		strs = StringUtil.findCapturesOf("123", "(\\d)");
		assertNotNull(strs);
		assertEquals(3, strs.length);
		assertEquals("1", strs[0]);
		assertEquals("2", strs[1]);
		assertEquals("3", strs[2]);

		strs = StringUtil.findCapturesOf("123456", "(\\d{3})");
		assertNotNull(strs);
		assertEquals(2, strs.length);
		assertEquals("123", strs[0]);
		assertEquals("456", strs[1]);

		strs = StringUtil.findCapturesOf("abc456", "(\\w\\w)");
		assertNotNull(strs);
		assertEquals(3, strs.length);
		assertEquals("ab", strs[0]);
		assertEquals("c4", strs[1]);
		assertEquals("56", strs[2]);

		strs = StringUtil.findCapturesOf("abcdef", "(\\d{3})");
		assertNull(strs);
	}

	public void testFindCaptureOf()
	{
		String str;

		try
		{
			StringUtil.findCaptureOf("abc-dev-def-ved", "([a-z]+)-([a-z]+)");
			fail("expected IllegalArgumentException");
		}
		catch(IllegalArgumentException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains("multiple captures"));
		}

		str = StringUtil.findCaptureOf("123", "([a-z])");
		assertNull(str);

		str = StringUtil.findCaptureOf("123", "(\\d)");
		assertEquals("1", str);

		String html = "<!DOCTYPE html>\n" +
				"<a href=\"https://google.com\">\n" +
				"<a href=\"http://example.com/example?hello=hi\">";
		str = StringUtil.findCaptureOf(html, "(https?://[\\w.]+\\.com[\\w/]*\\??[\\w=]*)\"");
		assertEquals("https://google.com", str);
	}

	public void testFindAllCapturesOf()
	{
		List<String[]> list;
		String[] strs;

		try
		{
			StringUtil.findAllCapturesOf("123", ".+");
			fail("expected IllegalArgumentException");
		}
		catch(IllegalArgumentException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains("no captures"));
		}

		list = StringUtil.findAllCapturesOf("this isnt even mine!", "(\\w{4})(.)");
		assertEquals(4, list.size());
		strs = list.get(0);
		assertEquals(2, strs.length);
		assertEquals("this", strs[0]);
		assertEquals(" ", strs[1]);
		strs = list.get(1);
		assertEquals(2, strs.length);
		assertEquals("isnt", strs[0]);
		assertEquals(" ", strs[1]);
		strs = list.get(2);
		assertEquals(2, strs.length);
		assertEquals("even", strs[0]);
		assertEquals(" ", strs[1]);
		strs = list.get(3);
		assertEquals(2, strs.length);
		assertEquals("mine", strs[0]);
		assertEquals("!", strs[1]);

		list = StringUtil.findAllCapturesOf("abcdef123456", "(.)(.)"); // now a that's quality uni-tit-est
		assertEquals(6, list.size());
		strs = list.get(0);
		assertEquals(2, strs.length);
		assertEquals("a", strs[0]);
		assertEquals("b", strs[1]);
		strs = list.get(1);
		assertEquals(2, strs.length);
		assertEquals("c", strs[0]);
		assertEquals("d", strs[1]);
		strs = list.get(2);
		assertEquals(2, strs.length);
		assertEquals("e", strs[0]);
		assertEquals("f", strs[1]);
		strs = list.get(3);
		assertEquals(2, strs.length);
		assertEquals("1", strs[0]);
		assertEquals("2", strs[1]);
		strs = list.get(4);
		assertEquals(2, strs.length);
		assertEquals("3", strs[0]);
		assertEquals("4", strs[1]);
		strs = list.get(5);
		assertEquals(2, strs.length);
		assertEquals("5", strs[0]);
		assertEquals("6", strs[1]);

		String html = "<!DOCTYPE html>\n" +
				"<a href=\"https://google.com\">\n" +
				"<a href=\"http://example.com/example?hello=hi\">";
		list = StringUtil.findAllCapturesOf(html, "(https?://[\\w.]+\\.com)([\\w/]*)\\??([\\w=]*)\"");
		assertEquals(2, list.size());
		strs = list.get(0);
		assertEquals(3, strs.length);
		assertEquals("https://google.com", strs[0]);
		assertEquals("", strs[1]);
		assertEquals("", strs[2]);
		strs = list.get(1);
		assertEquals(3, strs.length);
		assertEquals("http://example.com", strs[0]);
		assertEquals("/example", strs[1]);
		assertEquals("hello=hi", strs[2]);
	}
}