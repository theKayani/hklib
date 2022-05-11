package com.hk.lua;

import com.hk.Assets;
import com.hk.file.FileUtil;
import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LuaLibraryHashTest extends TestCase
{
	@Override
	public void setUp() throws Exception
	{
		Files.deleteIfExists(Paths.get("src/test/resources/assets/tozip.zip"));
		FileUtil.deleteDirectory("src/test/resources/assets/unzipped");
	}

	public void test() throws IOException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/library_hash.lua"));

		Lua.importStandard(interp);

		LuaObject obj = interp.execute();

		assertNotNull(obj);
		assertTrue(obj.getBoolean());
	}

	@Override
	public void tearDown() throws Exception
	{
		Files.deleteIfExists(Paths.get("src/test/resources/assets/tozip.zip"));
		FileUtil.deleteDirectory("src/test/resources/assets/unzipped");
	}
}