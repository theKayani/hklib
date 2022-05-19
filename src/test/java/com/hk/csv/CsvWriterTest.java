package com.hk.csv;

import com.hk.io.StringBuilderWriter;
import com.hk.json.JsonArray;
import com.hk.json.JsonObject;
import com.hk.math.vector.Vector2F;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvWriterTest extends TestCase
{
	public void testSimpleCSV() throws IOException
	{
		CsvWriter wtr;
		StringBuilder sb = new StringBuilder();

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.close();
		assertEquals(0, sb.length());

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine();
		wtr.close();
		assertEquals(0, sb.length());

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine("");
		wtr.close();
		assertEquals(0, sb.length());

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine();
		wtr.writeLine();
		wtr.close();
		assertEquals("\n", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine();
		wtr.writeLine();
		wtr.writeLine();
		wtr.close();
		assertEquals("\n\n", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		assertSame(wtr, wtr.setSkipEmptyLines());
		wtr.writeLine();
		wtr.writeLine();
		wtr.writeLine();
		wtr.close();
		assertEquals("", sb.toString());

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine(" ");
		wtr.close();
		assertEquals(" ", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine("", "");
		wtr.close();
		assertEquals(",", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine("");
		wtr.writeLine("");
		wtr.close();
		assertEquals("\n", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine("");
		wtr.writeLine("");
		wtr.close();
		assertEquals("\n", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine('1');
		wtr.close();
		assertEquals("1", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine(1);
		wtr.close();
		assertEquals("1", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine((Object) null);
		wtr.close();
		assertEquals("", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine(null, null);
		wtr.close();
		assertEquals(",", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine(null, null);
		wtr.writeLine(null, null);
		wtr.close();
		assertEquals(",\n,", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.setNullString("0");
		wtr.writeLine(null, null);
		wtr.writeLine(null, null);
		wtr.close();
		assertEquals("0,0\n0,0", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.setNullString("\",\"");
		wtr.writeLine(null, null);
		wtr.writeLine(null, null);
		wtr.close();
		assertEquals("\"\\\",\\\"\",\"\\\",\\\"\"\n\"\\\",\\\"\",\"\\\",\\\"\"", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine(true);
		wtr.close();
		assertEquals("true", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine(1, 2, 3);
		wtr.close();
		assertEquals("1,2,3", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine(1, 2, 3);
		wtr.writeLine(4, 5, 6);
		wtr.close();
		assertEquals("1,2,3\n4,5,6", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine(1, 2, 3);
		wtr.writeLine();
		wtr.writeLine(4, 5, 6);
		wtr.writeLine();
		wtr.close();
		assertEquals("1,2,3\n\n4,5,6\n", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		assertSame(wtr, wtr.setSkipEmptyLines());
		wtr.writeLine(1, 2, 3);
		wtr.writeLine();
		wtr.writeLine(4, 5, 6);
		wtr.writeLine();
		wtr.close();
		assertEquals("1,2,3\n4,5,6", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		assertSame(wtr, wtr.setQuoteStrings());
		wtr.writeLine(1, 2, 3);
		wtr.writeLine(4, 5, 6);
		wtr.close();
		assertEquals("1,2,3\n4,5,6", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine(1, "2", 3);
		wtr.writeLine("4", 5, "6");
		wtr.close();
		assertEquals("1,2,3\n4,5,6", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine('1', "2", '3');
		wtr.writeLine("4", '5', "6");
		wtr.close();
		assertEquals("1,2,3\n4,5,6", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine("1", '2', "3");
		wtr.writeLine('4', "5", '6');
		wtr.close();
		assertEquals("1,2,3\n4,5,6", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		assertSame(wtr, wtr.setQuoteAll());
		wtr.writeLine(1);
		wtr.close();
		assertEquals("\"1\"", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		assertSame(wtr, wtr.setQuoteAll());
		wtr.writeLine(1, 2, 3);
		wtr.writeLine(4, 5, 6);
		wtr.close();
		assertEquals("\"1\",\"2\",\"3\"\n\"4\",\"5\",\"6\"", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		assertSame(wtr, wtr.setQuoteStrings());
		wtr.writeLine(1, "2", 3);
		wtr.writeLine("4", 5, "6");
		wtr.close();
		assertEquals("1,\"2\",3\n\"4\",5,\"6\"", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine("1,2,3", "4,5,6");
		wtr.close();
		assertEquals("\"1,2,3\",\"4,5,6\"", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine("1,2,3", "4,5,6", "789");
		wtr.close();
		assertEquals("\"1,2,3\",\"4,5,6\",789", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		assertSame(wtr, wtr.setQuoteStrings());
		wtr.writeLine("1,2,3", "4,5,6", "789");
		wtr.close();
		assertEquals("\"1,2,3\",\"4,5,6\",\"789\"", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		assertSame(wtr, wtr.setQuoteNone());
		wtr.writeLine("1,2,3", "4,5,6", "7,8,9");
		wtr.close();
		assertEquals("1,2,3,4,5,6,7,8,9", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.setValuePrefix(" ");
		wtr.writeLine(1, 2, 3);
		wtr.close();
		assertEquals(" 1, 2, 3", sb.toString());
		sb.setLength(0);


		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.setValuePostfix(",");
		wtr.writeLine(1, 2, 3);
		wtr.close();
		assertEquals("1,,2,,3,", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		assertSame(wtr, wtr.setQuoteNone());
		wtr.setValuePrefix("\"");
		wtr.setValuePostfix("\"");
		wtr.writeLine(1, 2, 3);
		wtr.writeLine(4, 5, 6);
		wtr.close();
		assertEquals("\"1\",\"2\",\"3\"\n\"4\",\"5\",\"6\"", sb.toString());
		sb.setLength(0);

		JsonArray arr;
		arr = new JsonArray();
		arr.add(1);
		arr.add("2");
		arr.add(3);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine(new JsonObject(), arr);
		wtr.writeLine(new Vector2F(3, 4), new Vector2F(10, 12.99F));
		wtr.close();
		assertEquals("{ },\"[\\n\t1,\\n\t\\\"2\\\",\\n\t3\\n]\"\n\"(3.0, 4.0)\",\"(10.0, 12.99)\"", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.setQuoteNone();
		wtr.writeLine(new JsonObject(), arr);
		wtr.writeLine(new Vector2F(3, 4), new Vector2F(10, 12.99F));
		wtr.close();
		assertEquals("{ },[\n\t1,\n\t\"2\",\n\t3\n]\n(3.0, 4.0),(10.0, 12.99)", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.setOnlyPrimitive();
		try
		{
			wtr.writeLine(arr);
			fail("expected unsupported operation exception");
		}
		catch (UnsupportedOperationException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains("only prim"));
		}
		wtr.close();
		assertEquals("", sb.toString());
		sb.setLength(0);

		wtr = new CsvWriter(new StringBuilderWriter(sb));
		for (int i = 1; i < 10; i++)
		{
			Object[] objs = new Object[i];
			for (int j = 0; j < i; j++)
				objs[j] = i;

			wtr.writeLine(objs);
		}
		wtr.close();
		String[] sp = sb.toString().split("\n");
		assertEquals(9, sp.length);
		for (int i = 1; i < 10; i++)
		{
			sb.setLength(0);
			for (int j = 0; j < i; j++)
			{
				sb.append(i);
				if(j < i - 1)
					sb.append(',');
			}

			assertEquals(sb.toString(), sp[i - 1]);
		}
		sb.setLength(0);
	}

	public void testStreamCSV() throws IOException
	{
		List<Object[]> objs = new ArrayList<>();
		objs.add(new Object[] { 1, 2, 3 });
		objs.add(new Object[] { 4, 5, 6 });
		objs.add(new Object[] { 7, 8, 9 });

		StringBuilder sb = new StringBuilder();
		new CsvWriter(new StringBuilderWriter(sb))
			.accept(objs.stream())
			.close();
		assertEquals("1,2,3\n4,5,6\n7,8,9", sb.toString());
	}
}