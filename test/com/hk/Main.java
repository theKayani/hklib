package com.hk;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class Main
{
	public static void main(String[] args) throws ClassNotFoundException
	{
		TestSuite suite = new TestSuite();
		
		File dir = new File(System.getProperty("user.dir"), "testbin");
		
		Path path = dir.toPath();
		List<String> clss = new ArrayList<>();
		Stack<File> dirs = new Stack<>();
		dirs.push(dir);
		
		while(!dirs.isEmpty())
		{
			File[] fs = dirs.pop().listFiles();
			
			for(File f : fs)
			{
				if(f.isDirectory())
				{
					dirs.push(f);
				}
				else if(f.getName().endsWith("Test.class"))
				{
					String s = path.relativize(f.toPath()).toString();
					s = s.substring(0, s.length() - 6);
					s = s.replace(File.separator, ".");
					clss.add(s);
				}
			}
		
		}
		
		for(String cls : clss)
			suite.addTestSuite(Class.forName(cls).asSubclass(TestCase.class));

		TestRunner.run(suite);
	}
}
