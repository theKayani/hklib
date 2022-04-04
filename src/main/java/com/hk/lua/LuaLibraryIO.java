package com.hk.lua;

import com.hk.file.FileUtil;
import com.hk.func.BiConsumer;
import com.hk.io.IOUtil;
import com.hk.lua.Lua.LuaMethod;

import java.io.*;
import java.nio.file.Paths;

/**
 * <p>This class is to replicate the <em>io</em> library from Lua.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryIO implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	close() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			try
			{
				LuaObject obj;
				String err;
				if(args.length > 0)
				{
					obj = args[0];
					err = "bad argument #1 to 'close' (expected closeable)";
				}
				else
				{
					obj = interp.getExtraLua(EXKEY_STDOUT);
					err = "stdout expected to be closeable";
				}
				if (obj instanceof LuaIOUserdata)
					return ((LuaIOUserdata) obj).close();
				else if (obj instanceof Closeable)
				{
					((Closeable) obj).close();
					return LuaBoolean.TRUE;
				}
				else
					throw new LuaException(err);
			}
			catch (IOException ex)
			{
				throw new LuaException(ex.getLocalizedMessage());
			}
		}
	},
	cwd() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Object obj = interp.getExtra(EXKEY_CWD);
			File f;
			if(obj instanceof String)
				f = Paths.get((String) obj).toFile();
			else
				f = FileUtil.getUserDirectory();

			return new LuaString(f.getAbsolutePath());
		}
	},
	flush() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			LuaObject obj = interp.getExtraLua(EXKEY_STDOUT);

			if(!(obj instanceof LuaIOUserdata))
				return new LuaArgs(LuaNil.NIL, new LuaString("expected output FILE* object"));

			try
			{
				return ((LuaIOUserdata) obj).flush();
			}
			catch (IOException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
			}
		}
	},
	input() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			if(args.length > 0)
			{
				if(!(args[0] instanceof LuaIOUserdata || args[0].isString()))
					throw new LuaException("bad argument #1 to '" + name() + "' (expected FILE* object or string)");

				if(args[0].isString())
				{
					String file = args[0].getString();
					File f;

					if(interp.hasExtra(EXKEY_CWD))
						f = new File(interp.getExtra(EXKEY_CWD, String.class), file);
					else
						f = new File(file);
					try
					{
						args[0] = new LuaReader(new FileReader(f));
					}
					catch (FileNotFoundException e)
					{
						return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
					}
				}
				interp.setExtra(EXKEY_STDIN, args[0]);
				return LuaBoolean.TRUE;
			}
			else
				return interp.getExtraLua(EXKEY_STDIN);
		}
	},
	lines() {
		@Override
		public LuaObject call(LuaInterpreter interp, final LuaObject[] args)
		{
			LuaIOUserdata in;
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

				if (!(obj instanceof LuaIOUserdata))
					return new LuaArgs(LuaNil.NIL, new LuaString("expected input FILE* object"));

				fmts = getFormats(args, 0);
				in = (LuaIOUserdata) obj;
			}

			final LuaIOUserdata input = in;
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
			Lua.checkArgs(name(), args, LuaType.STRING, LuaType.STRING);

			String file = args[0].getString();
			String mode = args[1].getString();

			boolean binary = mode.endsWith("b");

			if(binary)
				mode = mode.substring(0, mode.length() - 1);

			boolean update = mode.endsWith("+");

			if(update)
				throw new LuaException("Update mode unsupported for this version of Lua (JVM)");
//				mode = mode.substring(0, mode.length() - 1);

			File f;

			if(interp.hasExtra(EXKEY_CWD))
				f = new File(interp.getExtra(EXKEY_CWD, String.class), file);
			else
				f = new File(file);

			try
			{
				switch(mode)
				{
					case "r":
						if(!f.exists())
							return new LuaArgs(LuaNil.NIL, new LuaString("file not found"));
						return new LuaReader(new FileReader(f));
					case "w":
						return new LuaWriter(new FileWriter(f, false));
					case "a":
						return new LuaWriter(new FileWriter(f, true));
				}
				throw new LuaException("invalid mode");
			}
			catch (IOException e)
			{
				throw new LuaException(e.getLocalizedMessage());
			}
		}
	},
	output() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			if(args.length > 0)
			{
				if(!(args[0] instanceof LuaIOUserdata || args[0].isString()))
					throw new LuaException("bad argument #1 to '" + name() + "' (expected FILE* object or string)");

				if(args[0].isString())
				{
					String file = args[0].getString();
					File f;

					if(interp.hasExtra(EXKEY_CWD))
						f = new File(interp.getExtra(EXKEY_CWD, String.class), file);
					else
						f = new File(file);
					try
					{
						args[0] = new LuaWriter(new FileWriter(f));
					}
					catch (IOException e)
					{
						return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
					}
				}
				interp.setExtra(EXKEY_STDOUT, args[0]);
				return LuaBoolean.TRUE;
			}
			else
				return interp.getExtraLua(EXKEY_STDOUT);
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

			if(!(obj instanceof LuaIOUserdata))
				return new LuaArgs(LuaNil.NIL, new LuaString("expected input FILE* object"));

			int[] formats = getFormats(args, 0);
			try
			{
				return new LuaArgs(((LuaIOUserdata) obj).read(formats));
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
			throw new UnsupportedOperationException("Not in this version of Lua 5.3 (JVM)");
		}
	},
	type() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			if(args[0] instanceof LuaIOUserdata)
				return new LuaString(((LuaIOUserdata) args[0]).isClosed() ? "closed file" : "file");
			return LuaNil.NIL;
		}
	},
	write() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			LuaObject obj = interp.getExtraLua(EXKEY_STDOUT);

			if(!(obj instanceof LuaIOUserdata))
				return new LuaArgs(LuaNil.NIL, new LuaString("expected output FILE* object"));

			try
			{
				return ((LuaIOUserdata) obj).write(args);
			}
			catch (IOException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
			}
		}
	},
	stdin() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			LuaObject stdin = env.interp.getExtraLua(EXKEY_STDIN);
			if(stdin.isNil())
			{
				stdin = new LuaReader(IOUtil.emptyReader()) {
					@Override
					public LuaObject close()
					{
						return LuaNil.NIL;
					}
				};
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
				stdout = new LuaWriter(IOUtil.nowhereWriter()) {
					@Override
					public LuaObject close()
					{
						return LuaNil.NIL;
					}
				};
				env.interp.setExtra(EXKEY_STDOUT, stdout);
			}
			table.rawSet(new LuaString(name()), stdout);
		}
	},
	stderr() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			LuaObject stderr = env.interp.getExtraLua(EXKEY_STDERR);
			if(stderr.isNil())
			{
				stderr = new LuaWriter(IOUtil.nowhereWriter()) {
					@Override
					public LuaObject close()
					{
						return LuaNil.NIL;
					}
				};
				env.interp.setExtra(EXKEY_STDERR, stderr);
			}
			table.rawSet(new LuaString(name()), stderr);
		}
	},
	sep() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			table.rawSet(new LuaString(name()), new LuaString(File.separator));
		}
	},
	psep() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			table.rawSet(new LuaString(name()), new LuaString(File.pathSeparator));
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

				if(!(args[0] instanceof LuaIOUserdata))
					return new LuaArgs(LuaNil.NIL, new LuaString("bad file descriptor"));

				try
				{
					return new LuaArgs(((LuaIOUserdata) args[0]).read(getFormats(args, 1)));
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
					throw new LuaException("bad argument #1 to 'lines' (expected FILE*)");

				if(!(args[0] instanceof LuaIOUserdata))
					return new LuaArgs(LuaNil.NIL, new LuaString("bad file descriptor"));

				final int[] formats = getFormats(args, 1);
				final LuaIOUserdata input = (LuaIOUserdata) args[0];

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
		ioMetatable.rawSet("close", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{
				try
				{
					if (args.length < 1)
						throw new LuaException("bad argument #1 to 'close' (expected FILE*)");

					if (args[0] instanceof LuaIOUserdata)
						return ((LuaIOUserdata) args[0]).close();
					else if (args[0] instanceof Closeable)
					{
						((Closeable) args[0]).close();
						return LuaBoolean.TRUE;
					}
					return new LuaArgs(LuaNil.NIL, new LuaString("bad file descriptor"));
				}
				catch (IOException ex)
				{
					throw new LuaException(ex.getLocalizedMessage());
				}
			}
		});
		ioMetatable.rawSet("flush", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{
				try
				{
					if (args.length < 1)
						throw new LuaException("bad argument #1 to 'flush' (expected FILE*)");

					if (args[0] instanceof LuaIOUserdata)
						return ((LuaIOUserdata) args[0]).flush();

					return new LuaArgs(LuaNil.NIL, new LuaString("bad file descriptor"));
				}
				catch (IOException ex)
				{
					throw new LuaException(ex.getLocalizedMessage());
				}
			}
		});
		ioMetatable.rawSet("setvbuf", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{
				try
				{
					if (args.length < 1 || !(args[0] instanceof LuaIOUserdata))
						throw new LuaException("bad argument #1 to 'setvbuf' (expected FILE*)");
					if(args.length < 2 || !args[1].isString())
						throw new LuaException("bad argument #2 to 'setvbuf' (expected string)");
					int mode = 0, size = 4096;

					switch (args[1].getString())
					{
						case "no":
							mode = SETVBUFMODE_NO;
							break;
						case "full":
							mode = SETVBUFMODE_FULL;
						case "line":
							if("line".equals(args[1].getString()))
								mode = SETVBUFMODE_LINE;

							if(args.length > 2)
							{
								if(args[2].isInteger())
									size = args[2].getInt();
								else
									throw new LuaException("bad argument #3 to 'setvbuf' (expected integer)");
							}
							break;
						default:
							throw new LuaException("invalid mode");
					}

					return ((LuaIOUserdata) args[0]).setvbuf(mode, size);
				}
				catch (IOException ex)
				{
					throw new LuaException(ex.getLocalizedMessage());
				}
			}
		});
		ioMetatable.rawSet("write", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{
				if (args.length < 1 || !(args[0] instanceof LuaIOUserdata))
					throw new LuaException("bad argument #1 to 'write' (expected FILE*)");

				try
				{
					LuaObject[] tmp = new LuaObject[args.length - 1];
					System.arraycopy(args, 1, tmp, 0, tmp.length);
					return ((LuaIOUserdata) args[0]).write(tmp);
				}
				catch (IOException e)
				{
					throw new LuaException(e.getLocalizedMessage());
				}
			}
		});
		ioMetatable.rawSet("seek", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{
				if (args.length < 1 || !(args[0] instanceof LuaIOUserdata))
					throw new LuaException("bad argument #1 to 'seek' (expected FILE*)");

				int mode = SEEKMODE_CUR;
				long offset = 0;

				if(args.length > 1)
				{
					if(!args[1].isString())
						throw new LuaException("bad argument #2 to 'seek' (expected string)");

					switch(args[1].getString())
					{
						case "set":
							mode = SEEKMODE_SET;
							break;
						case "cur":
							break;
						case "end":
							mode = SEEKMODE_END;
							break;
						default:
							throw new LuaException("bad argument #2 to 'seek' (invalid mode)");
					}
					if(args.length > 2)
					{
						if(!args[2].isInteger())
							throw new LuaException("bad argument #3 to 'seek' (expected integer)");

						offset = args[2].getLong();
					}
				}

				try
				{
					offset = ((LuaIOUserdata) args[0]).seek(mode, offset);
				}
				catch (IOException e)
				{
					return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
				}
				return LuaInteger.valueOf(offset);
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
						case "*number":
						case "*n":
						case "n":
							formats[i - offset] = READMODE_N;
							break;
						case "*all":
						case "*a":
						case "a":
							formats[i - offset] = READMODE_A;
							break;
						case "*line":
						case "*l":
						case "l":
							formats[i - offset] = READMODE_LOW_L;
							break;
						case "*L":
						case "L":
							formats[i - offset] = READMODE_L;
							break;
						default:
							throw new LuaException("bad argument #" + (i + 1) + " to 'read' (invalid mode)");
					}
				} else if (args[i].isInteger() && args[i].getLong() >= 0)
					formats[i - offset] = args[i].getInt();
				else
					throw new LuaException("bad argument #" + (i + 1) + " to 'read' (expected string or positive integer");
			}
		}
		else
			formats = new int[] { READMODE_LOW_L };

		return formats;
	}

	public static abstract class LuaIOUserdata extends LuaUserdata
	{
		public LuaIOUserdata()
		{
			metatable = ioMetatable;
		}

		public abstract boolean isClosed();

		public abstract LuaObject close() throws IOException;

		public abstract LuaObject flush() throws IOException;

		/*
		 * formats are either a positive integer or from
		 * READMODE_N, READMODE_A, READMODE_L, and READMODE_LOW_L
		 */
		public abstract LuaObject[] read(int[] formats) throws IOException;

		public abstract long seek(int mode, long offset) throws IOException;

		public abstract LuaObject setvbuf(int mode, int size) throws IOException;

		public abstract LuaObject write(LuaObject[] values) throws IOException;

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

	/** Constant <code>EXKEY_CWD="io.cwd"</code> */
	public static final String EXKEY_CWD = "io.cwd";

	public static final int READMODE_N = -4;
	public static final int READMODE_A = -3;
	public static final int READMODE_L = -2;
	public static final int READMODE_LOW_L = -1;

	public static final int SETVBUFMODE_NO = 1;
	public static final int SETVBUFMODE_FULL = 2;
	public static final int SETVBUFMODE_LINE = 3;

	public static final int SEEKMODE_SET = 1;
	public static final int SEEKMODE_CUR = 2;
	public static final int SEEKMODE_END = 3;
}
