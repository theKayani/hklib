package com.hk.lua;

import com.hk.Assets;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;

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

		LuaLibrary.importStandard(interp);

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
					return Lua.newVarargs(Lua.nil(), Lua.newString(e.getLocalizedMessage()));
				}

				return interp.require(arg, rdr);
			}
		}));
		globals.setVar("print", Lua.newFunc(new Lua.LuaMethod() {
			@Override
			public LuaObject call(LuaInterpreter interp, LuaObject[] args)
			{
				return Lua.nil();
			}
		}));

		assertNull(interp.execute());
		assertFalse("Lua exited with code (" + interp.getExtra(Lua.EXIT_CODE) + ")",
				interp.hasExtra(Lua.EXIT_CODE));
	}

	@Override
	public void tearDown()
	{
		// TODO: create or delete
	}
}