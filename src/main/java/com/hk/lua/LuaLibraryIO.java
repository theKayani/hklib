package com.hk.lua;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hk.func.BiConsumer;
import com.hk.lua.Lua.LuaMethod;

/**
 * <p>LuaLibraryIO class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryIO implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	close() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	flush() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	input() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	lines() {
		@Override
		public LuaObject call(LuaInterpreter interp, final LuaObject[] args)
		{
			throw new Error();
		}
	},
	open() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	output() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	popen() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	read() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	tmpfile() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	type() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			if(args[0] instanceof LuaInput || args[0] instanceof LuaOutput)
			{
				boolean isClosed;
				if (args[0] instanceof LuaInput)
					isClosed = ((LuaInput) args[0]).isClosed();
				else
					isClosed = ((LuaOutput) args[0]).isClosed();

				return new LuaString(isClosed ? "closed file" : "file");
			}
			return LuaNil.NIL;
		}
	},
	write() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	stdin() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			if(env.interp.getExtra(EXKEY_STDIN) == null)
			{
				String str = "public static void main(String[] args)\n" +
						"{\n" +
						"\tSystem.out.println(9 + 10);\n" +
						"}";

				LuaObject stdin = new LuaReader(new StringReader(str));
				env.interp.setExtra(EXKEY_STDIN, stdin);
			}

			table.rawSet(new LuaString(name()), stdin);
		}
	},
	stdout() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
//			Writer wtr = new CharArrayWriter();
//
//			LuaObject stdout = new LuaWriter(wtr);
//			env.interp.setExtra(EXKEY_STDOUT, stdout);
//
//			table.rawSet(new LuaString(name()), stdout);
		}
	};

	@Override
	public LuaObject call(LuaInterpreter interp, LuaObject[] args)
	{
		throw new Error();
	}

	/** {@inheritDoc} */
	@Override
	public void accept(final Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), Lua.newFunc(this));
	}
	
	static final LuaObject ioMetatable = new LuaTable();
	
	static
	{
		ioMetatable.rawSet("__name", new LuaString("FILE*"));
		ioMetatable.rawSet("__index", ioMetatable);


	}

	public static abstract class LuaInput extends LuaUserdata
	{
		public abstract boolean isClosed();

		public abstract Integer close() throws IOException;

		public abstract LuaObject[] read(int[] formats) throws IOException;

		public abstract Iterator<LuaObject> lines(int[] formats) throws IOException;

		public abstract long seek(int mode, long offset) throws IOException;

		@Override
		public String name()
		{
			return "FILE*";
		}

		@Override
		public String getString(LuaInterpreter interp)
		{
			return "file (0x" + Integer.toHexString(hashCode()) + ")";
		}
	}

	public static abstract class LuaOutput extends LuaUserdata
	{
		public abstract boolean isClosed();

		public abstract Integer close() throws IOException;

		public abstract void flush() throws IOException;

		public abstract long seek(int mode, long offset) throws IOException;

		public abstract void setvbuf(int mode, int size) throws IOException;

		public abstract void write(LuaObject[] values) throws IOException;

		@Override
		public String name()
		{
			return "FILE*";
		}

		@Override
		public String getString(LuaInterpreter interp)
		{
			return "file (0x" + Integer.toHexString(hashCode()) + ")";
		}
	}

	/** Constant <code>EXKEY_STDIN="io.stdin"</code> */
	public static final String EXKEY_STDIN = "io.stdin";

	/** Constant <code>EXKEY_STDOUT="io.stdout"</code> */
	public static final String EXKEY_STDOUT = "io.stdout";

	/** Constant <code>EXKEY_STDERR="io.stderr"</code> */
	public static final String EXKEY_STDERR = "io.stderr";
}
