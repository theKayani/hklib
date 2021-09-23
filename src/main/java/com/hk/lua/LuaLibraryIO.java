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
			LuaInput in;
			int[] fmts;
			if(args.length > 0)
			{
				try
				{
					in = new LuaReader(new FileReader(args[0].getString()));
					fmts = getFormats(args, 1);
				}
				catch (FileNotFoundException e)
				{
					throw new LuaException(e.getLocalizedMessage());
				}
			}
			else
			{
				LuaObject obj = interp.getExtraLua(EXKEY_STDIN);

				if (!(obj instanceof LuaInput))
					return new LuaArgs(LuaNil.NIL, new LuaString("expected input FILE* object"));

				fmts = getFormats(args, 0);
				in = (LuaInput) obj;
			}

			final LuaInput input = in;
			final int[] formats = fmts;
			return new LuaFunction() {
				@Override
				LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
				{
					try
					{
						return new LuaArgs(input.read(formats));
					}
					catch (IOException e)
					{
						throw new LuaException(e.getLocalizedMessage());
					}
				}
			};
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
				return new LuaArgs(LuaNil.NIL, new LuaString("expected input FILE* object"));

			int[] formats = getFormats(args, 0);
			try
			{
				return new LuaArgs(((LuaInput) obj).read(formats));
			}
			catch (IOException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
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
				stdin = new LuaReader(new CharArrayReader(new char[0]));
				env.interp.setExtra(EXKEY_STDIN, stdin);
			}
			table.rawSet(new LuaString(name()), stdin);
		}
	},
	stdout() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			LuaObject stdout = env.interp.getExtraLua(EXKEY_STDOUT);
			if(stdout.isNil())
			{
//				stdout = new LuaWriter(new CharArrayWriter());
				env.interp.setExtra(EXKEY_STDOUT, stdout);
			}
			table.rawSet(new LuaString(name()), stdout);
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
				if(args.length < 1)
					throw new LuaException("bad argument #1 to 'read' (expected FILE*)");

				if(!(args[0] instanceof LuaInput))
					return new LuaArgs(LuaNil.NIL, new LuaString("bad file descriptor"));

				try
				{
					return new LuaArgs(((LuaInput) args[0]).read(getFormats(args, 1)));
				}
				catch (IOException e)
				{
					return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
				}
			}
		});

		ioMetatable.rawSet("lines", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args) {
				if(args.length < 1)
					throw new LuaException("bad argument #1 to 'read' (expected FILE*)");

				if(!(args[0] instanceof LuaInput))
					return new LuaArgs(LuaNil.NIL, new LuaString("bad file descriptor"));

				final int[] formats = getFormats(args, 1);
				final LuaInput input = (LuaInput) args[0];

				return new LuaFunction() {
					@Override
					LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
					{
						try
						{
							return new LuaArgs(input.read(formats));
						}
						catch (IOException e)
						{
							throw new LuaException(e.getLocalizedMessage());
						}
					}
				};
			}
		});
	}

	private static int[] getFormats(LuaObject[] args, int offset)
	{
		int[] formats;
		if(args.length > offset)
		{
			formats = new int[args.length - offset];
			for (int i = offset; i < args.length; i++)
			{
				if (args[i].isString())
				{
					switch (args[i].getString())
					{
						case "n":
							formats[i - offset] = READMODE_N;
							break;
						case "a":
							formats[i - offset] = READMODE_A;
							break;
						case "l":
							formats[i - offset] = READMODE_LOW_L;
							break;
						case "L":
							formats[i - offset] = READMODE_L;
							break;
						default:
							throw new LuaException("bad argument #" + (i + 1) + " to 'read' (invalid mode)");
					}
				} else if (args[i].isInteger() && args[i].getInteger() >= 0)
					formats[i - offset] = (int) args[i].getInteger();
				else
					throw new LuaException("bad argument #" + (i + 1) + " to 'read' (expected string or positive integer");
			}
		}
		else
			formats = new int[] { READMODE_LOW_L };

		return formats;
	}

	public static abstract class LuaInput extends LuaUserdata
	{
		public LuaInput()
		{
			metatable = ioMetatable;
		}

		public abstract boolean isClosed();

		public abstract LuaObject close() throws IOException;

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
		public LuaOutput()
		{
			metatable = ioMetatable;
		}

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
