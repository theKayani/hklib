package com.hk;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Assets
{
	public static Path get()
	{
		return Paths.get("src/test/resources/assets").toAbsolutePath();
	}

	public static File get(String path)
	{
		return get().resolve(path).toFile();
	}
}