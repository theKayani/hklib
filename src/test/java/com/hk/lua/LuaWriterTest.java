package com.hk.lua;

import com.hk.Assets;
import com.hk.io.IOUtil;
import junit.framework.TestCase;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;

public class LuaWriterTest extends TestCase
{
	private LuaInterpreter interp;

	@Override
	protected void setUp() throws Exception
	{
		interp = Lua.reader(Assets.get("lua/test_stdout.lua"));

		Lua.importStandard(interp);

		interp.execute();
	}

	public void testClose()
	{
		interp.setExtra(LuaLibraryIO.EXKEY_STDOUT, new LuaWriter(IOUtil.nowhereWriter()));

		LuaObject obj = interp.getGlobals().getVar("testClose").call(interp);
		assertTrue(obj.getBoolean());
	}

	public void testFlush()
	{
		LuaObject obj;

		CharArrayWriter wtr = new CharArrayWriter();
		interp.setExtra(LuaLibraryIO.EXKEY_STDOUT, new LuaWriter(new BufferedWriter(wtr)));

		obj = interp.getGlobals().getVar("testFlushBegin").call(interp);
		assertTrue(obj.getBoolean());

		assertEquals(0, wtr.size());

		obj = interp.getGlobals().getVar("testFlushEnd").call(interp);
		assertTrue(obj.getBoolean());

		assertEquals(12, wtr.size());
	}

}