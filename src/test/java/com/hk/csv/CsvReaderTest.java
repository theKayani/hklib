package com.hk.csv;

import com.hk.Assets;
import com.hk.str.StringUtil;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class CsvReaderTest extends TestCase
{
	public void testSimpleCSV() throws IOException
	{
		AtomicInteger row = new AtomicInteger(0);
		CsvReader rdr;
		String[] line;

		rdr = new CsvReader(new StringReader(""));
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader(" "));
		line = rdr.readLine();
		assertEquals(1, line.length);
		assertEquals(" ", line[0]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("1"));
		line = rdr.readLine();
		assertEquals(1, line.length);
		assertEquals("1", line[0]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("header row"));
		assertSame(rdr, rdr.setIgnoreHeader());
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader(","));
		line = rdr.readLine();
		assertEquals(2, line.length);
		assertEquals("", line[0]);
		assertEquals("", line[1]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader(StringUtil.repeat("\n,", 100).substring(1)));
		for (int i = 0; i < 100; i++)
		{
			line = rdr.readLine();
			assertEquals(2, line.length);
			assertEquals("", line[0]);
			assertEquals("", line[1]);
		}
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("header row\n,"));
		assertSame(rdr, rdr.setIgnoreHeader());

		line = rdr.readLine();
		assertEquals(2, line.length);
		assertEquals("", line[0]);
		assertEquals("", line[1]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader(",,"));
		line = rdr.readLine();
		assertEquals(3, line.length);
		assertEquals("", line[0]);
		assertEquals("", line[1]);
		assertEquals("", line[2]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());

		rdr = new CsvReader(new StringReader(",,\n,,\n,,\n,,"));
		for (int i = 0; i < 4; i++)
		{
			line = rdr.readLine();
			assertEquals(3, line.length);
			assertEquals("", line[0]);
			assertEquals("", line[1]);
			assertEquals("", line[2]);
		}
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("header row\n,,\n,,\n,,\n,,"));
		assertSame(rdr, rdr.setIgnoreHeader());
		for (int i = 0; i < 4; i++)
		{
			line = rdr.readLine();
			assertEquals(3, line.length);
			assertEquals("", line[0]);
			assertEquals("", line[1]);
			assertEquals("", line[2]);
		}
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		Consumer<String[]> counter = (vals) -> {
			assertEquals(1, vals.length);
			assertEquals(row.incrementAndGet(), Integer.parseInt(vals[0]));
		};

		rdr = new CsvReader(new StringReader("header row\n1"));
		assertSame(rdr, rdr.setIgnoreHeader());
		rdr.process(counter);
		rdr.close();
		row.set(0);

		rdr = new CsvReader(new StringReader("header row\n1\n2\n3"));
		assertSame(rdr, rdr.setIgnoreHeader());
		String[] vals;
		while((vals = rdr.readLine()) != null)
			counter.accept(vals);
		rdr.close();
		row.set(0);

		rdr = new CsvReader(new StringReader("header row\n1\n2\n3"));
		String[] header = rdr.readLine();
		assertEquals(1, header.length);
		assertEquals("header row", header[0]);
		rdr.process(counter);
		rdr.close();
		row.set(0);

		rdr = new CsvReader(new StringReader("header row\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10"));
		assertSame(rdr, rdr.setIgnoreHeader());
		rdr.process(counter);
		rdr.close();
		row.set(0);

		rdr = new CsvReader(new StringReader("header row\n\n1\n2\n3\n\n4\n5\n\n\n6\n7\n8\n9\n10\n"));
		assertSame(rdr, rdr.setIgnoreHeader());
		assertSame(rdr, rdr.setSkipEmptyLines());
		rdr.process(counter);
		rdr.close();
		row.set(0);

		StringBuilder sb = new StringBuilder();
		sb.append("header row\n");
		for (int i = 1; i <= 1000; i++)
		{
			sb.append(i);
			if(i < 1000)
				sb.append('\n');
		}

		rdr = new CsvReader(new StringReader(sb.toString()));
		assertSame(rdr, rdr.setIgnoreHeader());
		rdr.process(counter);
		rdr.close();
		row.set(0);

		File f = Assets.get("csv/times_table.csv");
		rdr = new CsvReader(new FileReader(f));
		assertSame(rdr, rdr.setIgnoreHeader());

		rdr.process(strings -> {
			assertEquals(3, strings.length);

			int x = Integer.parseInt(strings[0]);
			int y = Integer.parseInt(strings[1]);
			int xy = Integer.parseInt(strings[2]);

			assertTrue(x > 0 && x <= 12);
			assertTrue(y > 0 && y <= 12);
			assertEquals(xy, x * y);

			row.getAndIncrement();
		});

		rdr.close();

		assertEquals(144, row.getAndSet(0));
	}

	public void testQuotedCSV() throws IOException
	{
		AtomicInteger row = new AtomicInteger(0);
		CsvReader rdr;
		String[] line;

		rdr = new CsvReader(new StringReader("\"\""));
		line = rdr.readLine();
		assertEquals(1, line.length);
		assertEquals("", line[0]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("\"value\""));
		line = rdr.readLine();
		assertEquals(1, line.length);
		assertEquals("value", line[0]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("\",\""));
		line = rdr.readLine();
		assertEquals(1, line.length);
		assertEquals(",", line[0]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("1,\",\""));
		line = rdr.readLine();
		assertEquals(2, line.length);
		assertEquals("1", line[0]);
		assertEquals(",", line[1]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("\",\",1"));
		line = rdr.readLine();
		assertEquals(2, line.length);
		assertEquals(",", line[0]);
		assertEquals("1", line[1]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader(",\",\""));
		line = rdr.readLine();
		assertEquals(2, line.length);
		assertEquals("", line[0]);
		assertEquals(",", line[1]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("\",\","));
		line = rdr.readLine();
		assertEquals(2, line.length);
		assertEquals(",", line[0]);
		assertEquals("", line[1]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("1,\"\""));
		line = rdr.readLine();
		assertEquals(2, line.length);
		assertEquals("1", line[0]);
		assertEquals("", line[1]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("\"\",1"));
		line = rdr.readLine();
		assertEquals(2, line.length);
		assertEquals("", line[0]);
		assertEquals("1", line[1]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("\"\\\\\",\\"));
		line = rdr.readLine();
		assertEquals(2, line.length);
		assertEquals("\\", line[0]);
		assertEquals("\\", line[1]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new StringReader("\"\\\"\",'"));
		line = rdr.readLine();
		assertEquals(2, line.length);
		assertEquals("\"", line[0]);
		assertEquals("'", line[1]);
		assertNull(rdr.readLine());
		assertNull(rdr.readLine());
		rdr.close();

		rdr = new CsvReader(new FileReader(Assets.get("csv/records.csv")));

		String[] header = rdr.readLine();

		assertEquals(2, header.length);
		assertEquals("Location", header[0]);
		assertEquals("Latitude / Longitude", header[1]);
		double[][] lls = new double[][] {
				{40.70555556, -73.99638889},
				{-21.04805556, -43.21055556},
				{43.6426, -79.3871},
				{41.89, 12.49222222},
				{40.74833333, -73.98555556},
				{37.81972222, -122.4786111},
				{36.1, -112.1},
				{29.97916667, 31.13416667},
				{40.67693, 117.23193},
				{41.008548, 28.979938},
				{43.72305556, 10.39638889},
				{-12.83666667, -72.54555556},
				{27.98805556, 86.92527778},
				{9.08, -79.68},
				{51.17861111, -1.826111111},
				{27.175, 78.04194444}
		};

		Pattern locationPtn = Pattern.compile("[\\w\\s]+");
		rdr.process(strings -> {
			assertEquals(2, strings.length);

			assertTrue(locationPtn.matcher(strings[0]).matches());

			String[] sp = strings[1].split(",");
			double latitude = Double.parseDouble(sp[0]);
			double longitude = Double.parseDouble(sp[1]);

			assertEquals(lls[row.get()][0], latitude);
			assertEquals(lls[row.get()][1], longitude);

			row.getAndIncrement();
		});

		rdr.close();

		assertEquals(16, row.get());
	}

	public void testFileCSV() throws IOException
	{
		CsvReader rdr = new CsvReader(Assets.get("csv/grades.csv"), StandardCharsets.UTF_8);
		assertSame(rdr, rdr.setWhitespaceTrim());
		assertSame(rdr, rdr.setSkipEmptyLines());
		assertSame(rdr, rdr.setIgnoreHeader());

		String[] line;
		int row = 0;
		while((line = rdr.readLine()) != null)
		{
			row++;
			assertEquals(9, line.length);

			assertTrue(line[0].matches("\\w+"));
			assertTrue(line[1].matches("\\w+"));
			assertTrue(line[2].matches("\\d{3}-\\d{2}-\\d{4}"));
			assertTrue(Double.isFinite(Double.parseDouble(line[3])));
			assertTrue(Double.isFinite(Double.parseDouble(line[4])));
			assertTrue(Double.isFinite(Double.parseDouble(line[5])));
			assertTrue(Double.isFinite(Double.parseDouble(line[6])));
			assertTrue(Double.isFinite(Double.parseDouble(line[7])));
			assertTrue(line[8].matches("[ABCDEF][-+]*"));
		}

		rdr.close();
		assertEquals(16, row);
	}
}