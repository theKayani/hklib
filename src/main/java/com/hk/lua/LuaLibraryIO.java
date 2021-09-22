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
			LuaObject obj = interp.getExtraLua(EXKEY_STDIN);

			if(!(obj instanceof LuaInput))
				return new LuaArgs(LuaNil.NIL, new LuaString("bad file descriptor"));

			long[] formats = new long[args.length];
			for(int i = 0; i < args.length; i++)
			{
				if(args[i].isString())
				{
					switch(args[i].getString())
					{
						case "n":
							formats[i] = READMODE_N;
							break;
						case "a":
							formats[i] = READMODE_A;
							break;
						case "l":
							formats[i] = READMODE_LOW_L;
							break;
						case "L":
							formats[i] = READMODE_L;
							break;
						default:
							throw new LuaException("bad argument #" + (i + 1) + " to 'read' (invalid mode)");
					}
				}
				else if(args[i].isInteger() && args[i].getInteger() >= 0)
				{
					formats[i] = args[i].getInteger();
				}
				else
					throw new LuaException("bad argument #" + (i + 1) + " to 'read' (expected string or positive integer");
			}

			LuaInput input = (LuaInput) obj;

			LuaObject[] read;
			try
			{
				read = input.read(formats);
			}
			catch (IOException e)
			{
				throw new LuaException(e.getLocalizedMessage());
			}


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
			LuaObject stdin = env.interp.getExtraLua(EXKEY_STDIN);
			if(stdin.isNil())
			{
				String str = "\"álo\"{a}\nsecond line\nthird line \nçfourth_line\n\n\t\t  3450\n";

				stdin = new LuaReader(new StringReader(str));
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

		ioMetatable.rawSet("read", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{

				return LuaNil.NIL;
			}
		});
	}

	public static abstract class LuaInput extends LuaUserdata
	{
		public abstract boolean isClosed();

		public abstract LuaObject close() throws IOException;

		public abstract LuaObject[] read(long[] formats) throws IOException;

		public abstract Iterator<LuaObject> lines(long[] formats) throws IOException;

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

		public abstract LuaObject close() throws IOException;

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

	public static final int READMODE_N = -4;
	public static final int READMODE_A = -3;
	public static final int READMODE_L = -2;
	public static final int READMODE_LOW_L = -1;
}
