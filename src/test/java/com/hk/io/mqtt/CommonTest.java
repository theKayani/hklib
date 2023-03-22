package com.hk.io.mqtt;

import com.hk.str.StringUtil;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class CommonTest extends TestCase
{
	public void testRemainingField() throws IOException
	{
		int n;
		ByteArrayOutputStream bout = new ByteArrayOutputStream(5);
		byte[] bs;
		assertEquals(0, bout.size());

		n = 0;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(0, bs[0] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 1;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(1, bs[0] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 5;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(5, bs[0] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 64;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(64, bs[0] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 120;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(120, bs[0] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 127;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(127, bs[0] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 128;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(2, bs.length);
		assertEquals(0x80, bs[0] & 0xFF);
		assertEquals(0x01, bs[1] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 16383;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(2, bs.length);
		assertEquals(0xFF, bs[0] & 0xFF);
		assertEquals(0x7F, bs[1] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 16384;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(3, bs.length);
		assertEquals(0x80, bs[0] & 0xFF);
		assertEquals(0x80, bs[1] & 0xFF);
		assertEquals(0x01, bs[2] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 2097151;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(3, bs.length);
		assertEquals(0xFF, bs[0] & 0xFF);
		assertEquals(0xFF, bs[1] & 0xFF);
		assertEquals(0x7F, bs[2] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 2097152;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(4, bs.length);
		assertEquals(0x80, bs[0] & 0xFF);
		assertEquals(0x80, bs[1] & 0xFF);
		assertEquals(0x80, bs[2] & 0xFF);
		assertEquals(0x01, bs[3] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 268435455;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(4, bs.length);
		assertEquals(0xFF, bs[0] & 0xFF);
		assertEquals(0xFF, bs[1] & 0xFF);
		assertEquals(0xFF, bs[2] & 0xFF);
		assertEquals(0x7F, bs[3] & 0xFF);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		try
		{
			bs = new byte[] { (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80 };
			Common.readRemainingField(new ByteArrayInputStream(bs));
			fail("expected IOException");
		}
		catch (IOException ignored)
		{}

		try
		{
			bs = new byte[] { (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80 };
			Common.readRemainingField(new ByteArrayInputStream(bs));
			fail("expected IOException");
		}
		catch (IOException ignored)
		{}

		try
		{
			bs = new byte[] { (byte) 0x80 };
			Common.readRemainingField(new ByteArrayInputStream(bs));
			fail("expected IOException");
		}
		catch (IOException ignored)
		{}
	}

	public void testUTFString() throws IOException
	{
		String s;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		s = "hello!";
		Common.writeUTFString(bout, s);
		assertTrue(bout.size() > 0);
		assertEquals(s, Common.readUTFString(new ByteArrayInputStream(bout.toByteArray()), null));
		bout.reset();

		s = "";
		Common.writeUTFString(bout, s);
		assertTrue(bout.size() > 0);
		assertEquals(s, Common.readUTFString(new ByteArrayInputStream(bout.toByteArray()), null));
		bout.reset();

		s = "string";
		Common.writeUTFString(bout, s);
		assertTrue(bout.size() > 0);
		assertEquals(s, Common.readUTFString(new ByteArrayInputStream(bout.toByteArray()), null));
		bout.reset();

		s = "this is my string, there are many like it but this one is mine";
		Common.writeUTFString(bout, s);
		assertTrue(bout.size() > 0);
		assertEquals(s, Common.readUTFString(new ByteArrayInputStream(bout.toByteArray()), null));
		bout.reset();

		s = StringUtil.repeat("0", 65535);
		Common.writeUTFString(bout, s);
		assertTrue(bout.size() > 0);
		assertEquals(s, Common.readUTFString(new ByteArrayInputStream(bout.toByteArray()), null));
		bout.reset();

		try
		{
			s = StringUtil.repeat("0", 65536);
			Common.writeUTFString(bout, s);
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException ignored)
		{}
	}

	public void testClientIDs()
	{
		String id;
		for (int i = 0; i < 1000; i++)
		{
			id = Client.randomClientID();
			assertNotNull(id);
			assertTrue(id.length() <= 23);
			assertTrue(id.length() > 0);
			assertTrue(Broker.isValidClientID(id));
		}
	}

	public void testInvalidTopics()
	{
		String[] invalidTopics = {
				"", "#", "+", "+/#",
				"home/+", "my/topic/+",
				"this/is/my/topic/there/are/many/like/+/but/this/one/is/mine",
		};
		for (String invalidTopic : invalidTopics)
			assertFalse(invalidTopic, Session.isValidTopic(invalidTopic));

		String[] validTopics = {
				"abc", "123", "abc123", "come with me",
				"my/topic", "i/just/wanna/be/pure", "Accounts payable",
				"/is this valid?", "//why//yes/itis// /",
				"this/is/my/topic/there/are/many/like/it/but/this/one/is/mine",
		};
		for (String validTopic : validTopics)
			assertTrue(validTopic, Session.isValidTopic(validTopic));

		String[] invalidTopicFilters = {
				"##", "++", "#+", "+#", "+/+#", "#/",
				"sport/tennis#", "sport/tennis/#/ranking",
				"sport+",
		};
		for (String invalidTopicFilter : invalidTopicFilters)
			assertFalse(invalidTopicFilter, Session.isValidTopicFilter(invalidTopicFilter));

		String[] validTopicFilters = {
				"#", "+/+", "/#", "+/#", "+/+/#", "//+///+/+//+/+/#",
				"sport/#", "sport/tennis/#", "sport/tennis/player1/#",
				"this/is/my/topic/there/are/many/like/+/but/this/one/is/mine",
		};
		for (String validTopicFilter : validTopicFilters)
			assertTrue(validTopicFilter, Session.isValidTopicFilter(validTopicFilter));
	}

	public void testTopicFilters()
	{
		String[][] simpleTopicFilterPairs = {
				{ "abc", "abc" },
				{ "abc123", "abc123" },
				{ "my/topic", "my/topic" },
				{ "/", "/" },
				{ "///", "///" },
				{ "///  / /// / / / ", "///  / /// / / / " },
				{ "/random", "/random" },
				{ "we/st//phili/delp/hia//born//and//raised", "we/st//phili/delp/hia//born//and//raised" },
		};

		for (String[] pair : simpleTopicFilterPairs)
		{
			assertTrue(Arrays.toString(pair), Session.matches(pair[0], pair[1]));
			// should match everything
			assertTrue(pair[0], Session.matches(pair[0], "#"));
		}

		String[][] wildcardTopicFilterPairs = {
				{ "sport/tennis/player1", "sport/tennis/player1/#" },
				{ "sport/tennis/player1/ranking", "sport/tennis/player1/#" },
				{ "sport/tennis/player1/score/wimbledon", "sport/tennis/player1/#" },
				{ "sport", "sport/#" },
				{ "/finance", "+/+" },
				{ "/finance", "/+" },
				{ "home/sensor/abc", "home/sensor/+" },
				{ "home/sensor/1/value", "home/sensor/+/value" },
				{ "home/sensor/1/value", "home/#" },
		};

		for (String[] pair : wildcardTopicFilterPairs)
		{
			assertTrue(Arrays.toString(pair), Session.matches(pair[0], pair[1]));
			// should match everything
			assertTrue(pair[0], Session.matches(pair[0], "#"));
		}

		String[][] systemTopicFilterPairs = {
				{ "$my/topic", "$my/topic" },
				{ "$SYS/", "$SYS/#" },
				{ "$SYS/monitor/clients", "$SYS/monitor/+" },
		};

		for (String[] pair : systemTopicFilterPairs)
		{
			assertTrue(Arrays.toString(pair), Session.matches(pair[0], pair[1]));
			// should not match topics beginning with $
			assertFalse(pair[0], Session.matches(pair[0], "#"));
		}

		String[][] falseTopicFilterPairs = {
				{ "/finance", "+" },
				{ "/finance", "finance" },
				{ "finance", "/finance" },
				{ "Accounts", "ACCOUNTS" },
				{ "accounts", "ACCOUNTS" },
				{ "ACCOUNTS", "Accounts" },
				{ "ACCOUNTS", "accounts" },
				{ "a/very/long/path/indeed", "+/long/path/indeed" },
				{ "$SYS/not/a/match", "#" },
				{ "$SYS/monitor/clients", "+/monitor/clients" },
				{ "$not/a/match", "not/a/match" },
				{ "not/a/match", "$not/a/match" },
				{ "$no", "no" },
				{ "no", "$no" },
		};
		for (String[] pair : falseTopicFilterPairs)
		{
			assertFalse(Arrays.toString(pair), Session.matches(pair[0], pair[1]));
		}
	}
}
