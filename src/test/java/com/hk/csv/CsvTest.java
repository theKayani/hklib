package com.hk.csv;

import com.hk.io.StringBuilderReader;
import com.hk.io.StringBuilderWriter;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringReader;

public class CsvTest extends TestCase
{
	public void testWriteRead() throws IOException
	{
		StringBuilder sb = new StringBuilder();
		CsvWriter wtr = new CsvWriter(new StringBuilderWriter(sb));
		wtr.writeLine('a', 'b', 'c', '\n');
		wtr.writeLine(1, 2, 3);
		wtr.writeLine("4, 5, 6");
		wtr.close();

		String[] line;
		CsvReader rdr = new CsvReader(new StringReader(sb.toString()));
		line = rdr.readLine();
		assertNotNull(line);
		assertEquals(4, line.length);
		assertEquals("a", line[0]);
		assertEquals("b", line[1]);
		assertEquals("c", line[2]);
		assertEquals("\n", line[3]);
		line = rdr.readLine();
		assertNotNull(line);
		assertEquals(3, line.length);
		assertEquals("1", line[0]);
		assertEquals("2", line[1]);
		assertEquals("3", line[2]);
		line = rdr.readLine();
		assertNotNull(line);
		assertEquals(1, line.length);
		assertEquals("4, 5, 6", line[0]);
		rdr.close();
	}
}
