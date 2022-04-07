package com.hk.math.vector;

import junit.framework.TestCase;

public class Color3FTest extends TestCase
{
	@Override
	public void setUp()
	{
		// TODO: create or delete
	}

	public void testToHexString()
	{
		assertEquals("FFFFFFFF", Color3F.WHITE.toHexString());
		assertEquals("FF000000", Color3F.BLACK.toHexString());
		assertEquals("FFFF0000", Color3F.RED.toHexString());
		assertEquals("FF00FF00", Color3F.GREEN.toHexString());
		assertEquals("FF0000FF", Color3F.BLUE.toHexString());
		assertEquals("FFFFFF00", Color3F.YELLOW.toHexString());
		assertEquals("FF00FFFF", Color3F.CYAN.toHexString());
		assertEquals("FFFF00FF", Color3F.PURPLE.toHexString());
	}

	@Override
	public void tearDown()
	{
		// TODO: create or delete
	}
}