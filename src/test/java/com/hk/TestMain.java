package com.hk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TestMain
{
	public static void main(String[] args) throws ClassNotFoundException, IOException
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
