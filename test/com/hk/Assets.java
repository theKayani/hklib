package com.hk;

import java.io.File;

public class Assets
{
	public static File get(String path)
	{
		return new File("test" + File.separator + "assets", path.replace("/", File.separator)).getAbsoluteFile();
	}
}