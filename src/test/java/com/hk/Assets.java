package com.hk;

import java.io.File;

public class Assets
{
	public static File get(String path)
	{
		return new File(("src/test/resources/assets/" + path).replace("/", File.separator)).getAbsoluteFile();
	}
}