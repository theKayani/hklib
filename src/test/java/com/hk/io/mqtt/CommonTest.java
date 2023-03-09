package com.hk.io.mqtt;

import com.hk.math.MathUtil;
import com.hk.str.StringUtil;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
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
		printBinary(bs);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 1;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(1, bs[0] & 0xFF);
		printBinary(bs);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 5;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(5, bs[0] & 0xFF);
		printBinary(bs);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 64;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(64, bs[0] & 0xFF);
		printBinary(bs);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 120;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(120, bs[0] & 0xFF);
		printBinary(bs);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 127;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(1, bs.length);
		assertEquals(127, bs[0] & 0xFF);
		printBinary(bs);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 128;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(2, bs.length);
		assertEquals(0x80, bs[0] & 0xFF);
		assertEquals(0x01, bs[1] & 0xFF);
		printBinary(bs);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 16383;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(2, bs.length);
		assertEquals(0xFF, bs[0] & 0xFF);
		assertEquals(0x7F, bs[1] & 0xFF);
		printBinary(bs);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 16384;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(3, bs.length);
		assertEquals(0x80, bs[0] & 0xFF);
		assertEquals(0x80, bs[1] & 0xFF);
		assertEquals(0x01, bs[2] & 0xFF);
		printBinary(bs);
		bout.reset();
		assertEquals(n, Common.readRemainingField(new ByteArrayInputStream(bs)));

		n = 2097151;
		Common.writeRemainingField(bout, n);
		bs = bout.toByteArray();
		assertEquals(3, bs.length);
		assertEquals(0xFF, bs[0] & 0xFF);
		assertEquals(0xFF, bs[1] & 0xFF);
		assertEquals(0x7F, bs[2] & 0xFF);
		printBinary(bs);
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
		printBinary(bs);
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
		printBinary(bs);
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
		assertEquals(s, Common.readUTFString(new ByteArrayInputStream(bout.toByteArray())));
		bout.reset();

		s = "";
		Common.writeUTFString(bout, s);
		assertTrue(bout.size() > 0);
		assertEquals(s, Common.readUTFString(new ByteArrayInputStream(bout.toByteArray())));
		bout.reset();

		s = "string";
		Common.writeUTFString(bout, s);
		assertTrue(bout.size() > 0);
		assertEquals(s, Common.readUTFString(new ByteArrayInputStream(bout.toByteArray())));
		bout.reset();

		s = "this is my string, there are many like it but this one is mine";
		Common.writeUTFString(bout, s);
		assertTrue(bout.size() > 0);
		assertEquals(s, Common.readUTFString(new ByteArrayInputStream(bout.toByteArray())));
		bout.reset();

		s = StringUtil.repeat("0", 65535);
		Common.writeUTFString(bout, s);
		assertTrue(bout.size() > 0);
		assertEquals(s, Common.readUTFString(new ByteArrayInputStream(bout.toByteArray())));
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

	private void printBinary(byte[] bs)
	{
//		StringBuilder sb = new StringBuilder();
//		sb.append('[');
//		for (int i = 0; i < bs.length; i++)
//		{
//			sb.append(MathUtil.byteBin(bs[i] & 0xFF));
//			if(i < bs.length - 1)
//				sb.append(", ");
//		}
//		sb.append(']');
//		System.out.println(sb);
	}
}
