package com.hk.file;

import com.hk.math.Rand;
import com.hk.math.vector.Point;
import com.hk.math.vector.Vector3F;
import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class DataTagTest extends TestCase
{
	public void testSaveAndLoad() throws Exception
	{
		DataTag orig, tag1, tag2, tag3;

		orig = new DataTag();
		orig = doSaveAndLoad(orig);
		assertTrue(orig.isEmpty());
		assertEquals(orig.size(), 0);

		orig = new DataTag();
		orig.set("hello", "world");
		orig = doSaveAndLoad(orig);
		assertFalse(orig.isEmpty());
		assertEquals(orig.size(), 1);
		assertEquals(orig.get("hello"), "world");

		orig = new DataTag();
		orig.set("a", 1);
		orig.set("b", 2);
		orig.set("c", 3);
		orig = doSaveAndLoad(orig);
		assertFalse(orig.isEmpty());
		assertEquals(orig.size(), 3);
		assertEquals(orig.get("a"), (Integer) 1);
		assertEquals(orig.get("b"), (Integer) 2);
		assertEquals(orig.get("c"), (Integer) 3);

		orig = new DataTag();
		orig.set("my number", 12356);
		orig.set("my string", "this is my string");
		orig.set("my boolean", true);
		orig = doSaveAndLoad(orig);
		assertFalse(orig.isEmpty());
		assertEquals(orig.size(), 3);
		assertEquals(orig.get("my number"), (Integer) 12356);
		assertEquals(orig.get("my string"), "this is my string");
		assertEquals(orig.get("my boolean"), (Boolean) true);

		orig = new DataTag();
		for (int i = 0; i < 256; i++)
		{
			for (int j = 0; j < 256; j++)
			{
				orig.set(i + "-" + j, i * j);
			}
		}
		orig = doSaveAndLoad(orig);
		assertFalse(orig.isEmpty());
		assertEquals(orig.size(), 65536);
		for (int i = 0; i < 256; i++)
		{
			for (int j = 0; j < 256; j++)
			{
				assertEquals(orig.get(i + "-" + j), (Integer) (i * j));
			}
		}

		orig = new DataTag();

		orig.set("some time in space", new Date(1754414008542L));
		orig.set("my decimal", new BigDecimal("111222333444555666777888999.111222333444555666777888999"));
		orig.set("math3fvector", new Vector3F(-10.99F, 22.5F, 3201F));
		Set<String> set = new HashSet<>();
		for (int i = 0; i < Rand.nextInt(10, 100); i++)
			set.add(Rand.nextString(Rand.nextInt(16, 64)));
		orig.set("string set", (Serializable) set);
		tag1 = new DataTag();
		tag1.set("a", "x");
		tag1.set("b", "y");
		tag1.set("c", "z");
		orig.set("othertag", tag1);

		orig = doSaveAndLoad(orig);
		assertFalse(orig.isEmpty());
		assertEquals(orig.size(), 5);
		assertEquals(orig.get("some time in space"), new Date(1754414008542L));
		assertEquals(orig.get("my decimal"), new BigDecimal("111222333444555666777888999.111222333444555666777888999"));
		assertEquals(orig.get("math3fvector"), new Vector3F(-10.99F, 22.5F, 3201F));
		assertEquals(set, orig.get("string set"));
		tag1 = orig.get("othertag");
		assertNotNull(tag1);
		assertFalse(tag1.isEmpty());
		assertEquals(tag1.size(), 3);
		assertEquals(tag1.get("a"), "x");
		assertEquals(tag1.get("b"), "y");
		assertEquals(tag1.get("c"), "z");

		orig = new DataTag();

		for (int i = 0; i < 256; i++)
		{
			for (int j = 0; j < 256; j++)
			{
				tag1 = new DataTag();
				tag1.set("x", i * 2 + 1000);
				tag1.set("y", 1000 - j * 2);
				tag1.set("z", (i / 2) * (j / 2));
				orig.set(i + "," + j, tag1);
			}
		}

		orig = doSaveAndLoad(orig);
		assertFalse(orig.isEmpty());
		assertEquals(orig.size(), 65536);

		for (int i = 0; i < 256; i++)
		{
			for (int j = 0; j < 256; j++)
			{
				tag1 = orig.get(i + "," + j);
				assertFalse(tag1.isEmpty());
				assertEquals(tag1.size(), 3);
				assertEquals(tag1.get("x"), (Integer) (i * 2 + 1000));
				assertEquals(tag1.get("y"), (Integer) (1000 - j * 2));
				assertEquals(tag1.get("z"), (Integer) ((i / 2) * (j / 2)));
			}
		}
	}

	private DataTag doSaveAndLoad(DataTag tag) throws Exception
	{
		assertNotNull(tag);

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		tag.save(bout);
		byte[] arr = bout.toByteArray();
		assertTrue(arr.length != 0);

		ByteArrayInputStream bin = new ByteArrayInputStream(arr);
		DataTag copy = new DataTag();
		assertSame(copy, copy.load(bin));
		assertNotNull(copy);
		assertEquals(tag, copy);

		return copy;
	}
}