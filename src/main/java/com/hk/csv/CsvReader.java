package com.hk.csv;

import java.io.Closeable;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CsvReader implements Closeable
{
	private final LineNumberReader rdr;
	private StringBuilder sb;

	public CsvReader(Reader rdr)
	{
		this.rdr = new LineNumberReader(rdr);
	}

	public String[] readLine() throws IOException
	{
		List<String> lst = new ArrayList<>();
		if(readLineInto(lst))
			return lst.toArray(new String[0]);
		return null;
	}

	public void process(Consumer<String[]> consumer) throws IOException
	{
		List<String> lst = new ArrayList<>();

		while(readLineInto(lst))
		{
			consumer.accept(lst.toArray(new String[0]));
			lst.clear();
		}
	}

	private boolean readLineInto(List<String> lst) throws IOException
	{
		if(sb == null)
			sb = new StringBuilder();

		boolean done = false;
		int i;
		char c;
		lbl:
		while(true)
		{
			i = rdr.read();
			if (i == -1)
			{
				done = true;
				break;
			}
			else
			{
				c = (char) i;

				switch (c)
				{
					case ',':
						lst.add(sb.toString());
						break;
					case '\n':
						break;
					default:
						sb.append(c);
				}
			}
		}

		return done;
	}

	@Override
	public void close() throws IOException
	{
		rdr.close();
	}
}
