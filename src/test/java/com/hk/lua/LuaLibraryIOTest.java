package com.hk.lua;

import com.hk.Assets;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class LuaLibraryIOTest extends TestCase
{
	@Override
	public void setUp()
	{
		// TODO: create or delete
	}

	public void test() throws FileNotFoundException
	{
		File src = Assets.get("lua/library_io.lua");

		LuaInterpreter interp = Lua.reader(src);

		Lua.importStandard(interp);

		Environment globals = interp.getGlobals();
		globals.setVar("require", Lua.newFunc(new Lua.LuaMethod() {
			@Override
			public LuaObject call(LuaInterpreter interp, LuaObject[] args)
			{
				Lua.checkArgs("require", args, LuaType.STRING);

				String arg = args[0].getString();
				String path = arg.replace(".", File.separator);

				Reader rdr;
				try
				{
					rdr = new FileReader(Assets.get("lua/" + path + ".lua"));
				}
				catch (FileNotFoundException e)
				{
					return Lua.newVarargs(Lua.NIL, Lua.newString(e.getLocalizedMessage()));
				}

				return interp.require(arg, rdr);
			}
		}));
		globals.setVar("print", Lua.newFunc(new Lua.LuaMethod() {
			@Override
			public LuaObject call(LuaInterpreter interp, LuaObject[] args)
			{
				return Lua.NIL;
			}
		}));

		assertNull(interp.execute());
		assertFalse("Lua exited with code (" + interp.getExtra(Lua.EXIT_CODE) + ")",
				interp.hasExtra(Lua.EXIT_CODE));
	}

	public void testCWD() throws FileNotFoundException
	{
		LuaInterpreter interp = Lua.reader(Assets.get("lua/test_cwd.lua"));
		Lua.importStandard(interp);
		interp.execute();

		LuaObject obj;

		String dir = System.getProperty("user.dir");

		interp.setExtra(LuaLibraryIO.EXKEY_CWD, dir);
		obj = interp.getGlobals().getVar("butNotTheDeputy").call(interp);
		assertTrue(obj.getBoolean());

		interp.setExtra(LuaLibraryIO.EXKEY_CWD, dir + "/src/test/resources");
		obj = interp.getGlobals().getVar("butISwearItWasSelfDefense").call(interp);
		assertTrue(obj.getBoolean());

		interp.setExtra(LuaLibraryIO.EXKEY_CWD, dir + "/src/test/resources/assets/lua");
		obj = interp.getGlobals().getVar("andTheySayItIsACapitalOffense").call(interp);
		assertTrue(obj.getBoolean());
	}

	@Override
	public void tearDown()
	{
		// TODO: create or delete
	}
}