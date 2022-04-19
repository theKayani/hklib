package com.hk;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import com.hk.ex.UncheckedIOException;
import com.hk.file.FileUtil;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TestMain
{
	private static int walk(File f)
	{
		int totalLines = 0;
		if(f.getPath().endsWith(".java"))
		{
			int blankLines = 0;
			try
			{
				List<String> lines = new ArrayList<>();
				BufferedReader rdr = new BufferedReader(new FileReader(f));
				String line;

				while((line = rdr.readLine()) != null)
				{
					totalLines++;
					if(!line.isEmpty() && line.trim().isEmpty())
					{
						blankLines++;
						lines.add("");
					}
					else
						lines.add(line);
				}

				rdr.close();

				if(blankLines > 0)
				{
					System.out.println(f + " has " + blankLines + " blank line" + (blankLines == 1 ? "" : "s"));
					FileWriter wtr = new FileWriter(f);
					int max = lines.size();
					for (int i = lines.size() - 1; i >= 0; i--)
					{
						if(lines.get(i).isEmpty())
							max--;
						else
							break;
					}
					for (int i = 0; i < max; i++)
					{
						wtr.write(lines.get(i));

						if(i < lines.size() - 1)
							wtr.write("\n");
					}
					wtr.close();
				}
			}
			catch (IOException ex)
			{
				throw new UncheckedIOException(ex);
			}
		}

		if(f.isDirectory())
		{
			File[] files = f.listFiles();
			if(files != null)
			{
				for (File file : files)
					totalLines += walk(file);
			}
		}

		return totalLines;
	}

	public static void main(String[] args) throws IOException
	{
		File f = new File("C:\\Users\\kayan\\Desktop\\Eclipse\\hklib\\src");

		int totalLines = walk(f);
		System.out.println(totalLines);
	}

	public static void main2(String[] args) throws ClassNotFoundException, IOException
	{
		TestSuite suite = new TestSuite();

		File argfile = new File("argfile.txt");
		BufferedReader rdr = new BufferedReader(new FileReader(argfile));
		String line;

		while((line = rdr.readLine()) != null)
		{
			if(line.startsWith("test/") && line.endsWith("Test.java"))
			{
				line = line.substring(5, line.length() - 5).replace('/', '.');
				suite.addTestSuite(Class.forName(line).asSubclass(TestCase.class));
			}
		}

		rdr.close();

		TestRunner.run(suite);
	}
}
