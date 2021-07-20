package com.hk.lua;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
			LuaFileUserdata fud;
			if(args.length > 0)
			{
				if(args[0] instanceof LuaFileUserdata)
					fud = (LuaFileUserdata) args[0];
				else
					throw new LuaException("bad argument #1 to 'close' (expected FILE*, got " + args[0].name() + ")");
			}
			else
				fud = interp.getExtra("io.output", LuaFileUserdata.class);

			try
			{
				fud.close();
			}
			catch (IOException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
			}
			return LuaBoolean.TRUE;
		}
	},
	flush() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			LuaFileUserdata fud = interp.getExtra("io.output", LuaFileUserdata.class);			
			try
			{
				fud.flush();
			}
			catch (IOException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
			}
			return LuaBoolean.TRUE;
		}
	},
	input() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			if(args.length > 0)
			{
				LuaObject fud;
				if(args[0] instanceof LuaFileUserdata)
					fud = args[0];
				else
					fud = open.call(interp, new LuaObject[] { args[0], new LuaString("r") });

				interp.setExtra("io.input", fud);
				return LuaBoolean.TRUE;
			}
			else
				return interp.getExtraLua("io.input");
		}
	},
	lines() {
		@Override
		public LuaObject call(LuaInterpreter interp, final LuaObject[] args)
		{
			if(args.length > 0)
			{
				final LuaFileUserdata fud = (LuaFileUserdata) open.call(interp, new LuaObject[] { args[0], new LuaString("r") });
			
				return new LuaFunction()
				{
					@Override
					LuaObject doCall(LuaInterpreter interp, LuaObject[] args2)
					{
						LuaObject obj = fud.lines(args, 1).doCall(interp, new LuaObject[0]);
						if(obj.isNil())
						{
							try
							{
								fud.close();
							}
							catch (IOException e)
							{
								throw new LuaException(e.getLocalizedMessage());
							}
						}
						return obj;
					}
				};
			}
			else
			{
				return ((LuaFileUserdata) interp.getExtraLua("io.input")).lines(new LuaObject[0], 0);
			}
		}
	},
	open() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			if(args.length > 1)
				Lua.checkArgs(toString(), args, LuaType.STRING, LuaType.STRING);
			else
				Lua.checkArgs(toString(), args, LuaType.STRING);
			
			File file = new File(args[0].getString());
			String mode = args.length > 1 ? args[1].getString() : "r";
			if(mode.isEmpty())
				throw new LuaException("bad argument #2 to 'open' (invalid mode)");
			
			boolean binary = false;
			if(mode.endsWith("b"))
			{
				binary = true;
				mode = mode.substring(0, mode.length() - 1);
			}
			try
			{
				switch(mode.replace("*", ""))
				{
				case "r":
				case "r+":
					if(binary)
						return new LuaFileInputUserdata(file);
					else
						return new LuaFileReaderUserdata(file);
				case "w":
				case "w+":
					if(binary)
						return new LuaFileOutputUserdata(file, false);
					else
						return new LuaFileWriterUserdata(file, false);
				case "a":
				case "a+":
					if(binary)
						return new LuaFileOutputUserdata(file, true);
					else
						return new LuaFileWriterUserdata(file, true);
				default:
					throw new LuaException("bad argument #2 to 'open' (invalid mode)");
				}
			}
//			catch(FileNotFoundException ex)
//			{
//				throw new Error();
//			}
			catch(IOException ex)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(ex.getLocalizedMessage()));
			}
		}
	},
	output() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			if(args.length > 0)
			{
				LuaObject fud;
				if(args[0] instanceof LuaFileUserdata)
					fud = args[0];
				else
					fud = open.call(interp, new LuaObject[] { args[0], new LuaString("w") });

				interp.setExtra("io.output", fud);
				return LuaBoolean.TRUE;
			}
			else
				return interp.getExtraLua("io.output");
		}
	},
//	popen() {
//		@Override
//		public LuaObject apply(LuaInterpreter interp, LuaObject[] args)
//		{
//			throw new Error();
//		}
//	},
	read() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			try
			{
				LuaFileUserdata fud = interp.getExtra("io.input", LuaFileUserdata.class);			
				LuaObject[] res;
				if(args.length != 0)
				{
					Object[] arr = new Object[args.length];
					for(int i = 0; i < args.length; i++)
					{
						if(args[i].isString())
							arr[i] = args[i].getString();
						else if(args[i].isNumber())
							arr[i] = (int) args[i].getInteger();
						else
							throw new LuaException("bad argument #" + (i + 1) + " to 'read' (expected string, got " + args[i].name() + ")");
					}
					
					res = fud.read(arr);
				}
				else
					res = fud.read("l");

				return new LuaArgs(res);
			}
			catch(IOException ex)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(ex.getLocalizedMessage()));
			}
		}
	},
	tmpfile() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			try
			{
				return new LuaFileWriterUserdata(Files.createTempFile("luajvmtmp", ".tmp").toFile(), false);
			}
			catch(IOException ex)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(ex.getLocalizedMessage()));
			}
		}
	},
	type() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.USERDATA);
			if(args[0] instanceof LuaFileUserdata)
				return new LuaString(((LuaFileUserdata) args[0]).closed ? "closed file" : "file");
			else
				return LuaNil.NIL;
		}
	},
	write() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			try
			{
				LuaFileUserdata fud = interp.getExtra("io.output", LuaFileUserdata.class);			

				String[] arr = new String[args.length];
				for(int i = 0; i < args.length; i++)
				{
					if(args[i].isString() || args[i].isNumber())
						arr[i] = args[i].getString();
					else
						throw new LuaException("bad argument #" + (i + 1) + " to 'write' (expected string or number, got " + args[i].name() + ")");
				}

				fud.write(arr);
				return fud;
			}
			catch(IOException ex)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(ex.getLocalizedMessage()));
			}
		}
	};

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

		ioMetatable.rawSet("close", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{
				Lua.checkArgs(toString(), args, LuaType.USERDATA);
				if(args[0] instanceof LuaFileUserdata)
				{
					try
					{
						((LuaFileUserdata) args[0]).close();
					}
					catch (IOException e)
					{
						return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
					}
					return LuaBoolean.TRUE;
				}
				throw new LuaException("bad argument #1 to 'close' (expected FILE*, got " + args[0].name() + ")");
			}
		});
		ioMetatable.rawSet("flush", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{
				Lua.checkArgs(toString(), args, LuaType.USERDATA);
				if(args[0] instanceof LuaFileUserdata)
				{
					try
					{
						((LuaFileUserdata) args[0]).flush();
					}
					catch (IOException e)
					{
						return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
					}
					return LuaBoolean.TRUE;
				}
				throw new LuaException("bad argument #1 to 'flush' (expected FILE*, got " + args[0].name() + ")");
			}
		});
		ioMetatable.rawSet("read", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{
				Lua.checkArgs(toString(), args, LuaType.USERDATA);
				try
				{
					if(args[0] instanceof LuaFileUserdata)
					{
						LuaObject[] res;
						if(args.length != 1)
						{
							Object[] arr = new Object[args.length - 1];
							for(int i = 1; i < args.length; i++)
							{
								if(args[i].isString())
									arr[i - 1] = args[i].getString();
								else if(args[i].isNumber())
									arr[i - 1] = (int) args[i].getInteger();
								else
									throw new LuaException("bad argument #" + (i + 1) + " to 'read' (expected string, got " + args[i].name() + ")");
							}
							
							res = ((LuaFileUserdata) args[0]).read(arr);
						}
						else
							res = ((LuaFileUserdata) args[0]).read("l");

						return new LuaArgs(res);
					}
					throw new LuaException("bad argument #1 to 'read' (expected FILE*, got " + args[0].name() + ")");
				}
				catch(IOException ex)
				{
					return new LuaArgs(LuaNil.NIL, new LuaString(ex.getLocalizedMessage()));
				}
			}
		});
		ioMetatable.rawSet("write", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
			{
				Lua.checkArgs(toString(), args, LuaType.USERDATA);
				try
				{
					if(args[0] instanceof LuaFileUserdata)
					{
						String[] arr = new String[args.length - 1];
						for(int i = 1; i < args.length; i++)
						{
							if(args[i].isString() || args[i].isNumber())
								arr[i - 1] = args[i].getString();
							else
								throw new LuaException("bad argument #" + (i + 1) + " to 'write' (expected string or number, got " + args[i].name() + ")");
						}

						((LuaFileUserdata) args[0]).write(arr);
						return args[0];
					}
					throw new LuaException("bad argument #1 to 'write' (expected FILE*, got " + args[0].name() + ")");
				}
				catch(IOException ex)
				{
					return new LuaArgs(LuaNil.NIL, new LuaString(ex.getLocalizedMessage()));
				}
			}
		});
		ioMetatable.rawSet("lines", new LuaFunction() {
			@Override
			LuaObject doCall(LuaInterpreter interp, final LuaObject[] args)
			{
				Lua.checkArgs(toString(), args, LuaType.USERDATA);
				try
				{
					if(args[0] instanceof LuaFileUserdata)
						return ((LuaFileUserdata) args[0]).lines(args, 1);
					else
						throw new LuaException("bad argument #1 to 'lines' (expected FILE*, got " + args[0].name() + ")");
				}
				catch(Exception ex)
				{
					throw new LuaException(ex.getLocalizedMessage());
				}
			}
		});
//		ioMetatable.setIndex("seek", new LuaFunction() {
//			@Override
//			LuaObject doCall(final LuaObject[] args)
//			{
//				Lua.checkArgs(toString(), args, LuaType.USERDATA);
//				try
//				{
//					if(args[0] instanceof LuaFileUserdata)
//					{
//						return ((LuaFileUserdata) args[0]).seek(whence, offset);
//					}
//					throw new LuaException("bad argument #1 to 'lines' (expected FILE*, got " + args[0].name() + ")");
//				}
//				catch(Exception ex)
//				{
//					throw new LuaException(ex.getLocalizedMessage());
//				}
//			}
//		});
	}
	
	static class LuaFileReaderUserdata extends LuaFileUserdata
	{
		private final FileReader reader;
		
		private LuaFileReaderUserdata(File file) throws IOException
		{
			super(file);
			this.reader = new FileReader(file);
		}

		@Override
		void write(String... strs) throws IOException {}

		@Override
		LuaObject[] read(Object... formats) throws IOException
		{
			List<LuaObject> lst = new ArrayList<>();
			for(Object format : formats)
			{
				if("l".equals(format) || "L".equals(format))
				{
					StringBuilder sb = new StringBuilder();
					int i = -1;
					char c;
					while((i = reader.read()) >= 0)
					{
						c = (char) i;
						
						if(c != '\n')
						{
							sb.append(c);
						}
						else
						{
							if("L".equals(format))
								sb.append(c);
							break;
						}
					}
					if(i == -1 && sb.length() == 0)
						lst.add(LuaNil.NIL);
					else
						lst.add(new LuaString(sb.toString().replace("\r", "")));
				}
				else if("a".equals(format))
				{
					StringBuilder sb = new StringBuilder();
					int i;
					while((i = reader.read()) >= 0)
						sb.append((char) i);

					lst.add(new LuaString(sb.toString()));
				}
				else if(format instanceof Integer)
				{
					int amt = (Integer) format;
					if(amt > 0)
					{
						StringBuilder sb = new StringBuilder();
						int i = -1;
						while((i = reader.read()) >= 0)
						{
							sb.append((char) i);
							amt--;
							
							if(amt == 0)
								break;
						}
	
						if(i == -1 && sb.length() == 0)
							lst.add(LuaNil.NIL);
						else
							lst.add(new LuaString(sb.toString()));
					}
					else
						lst.add(new LuaString(""));
				}
				else
				{
					lst.add(LuaNil.NIL);
					break;
//					throw new Error();
				}
			}
			return lst.toArray(new LuaObject[0]);
		}
		
		LuaObject lines(LuaObject[] args, int offset)
		{
			final Object[] arr;
			if(offset < args.length)
			{
				arr = new Object[args.length - offset];
				for(int i = offset; i < args.length; i++)
				{
					if(args[i].isString())
						arr[i - offset] = args[i].getString();
					else if(args[i].isNumber())
						arr[i - offset] = (int) args[i].getInteger();
					else
						throw new LuaException("bad argument #" + (i + 1) + " to 'lines' (expected string, got " + args[i].name() + ")");
				}
			}
			else
				arr = new Object[] { "l" };
			
			return new LuaFunction() {
				@Override
				LuaObject doCall(LuaInterpreter interp, LuaObject[] args2)
				{
					try
					{
						return new LuaArgs(read(arr));
					}
					catch (IOException e)
					{
						throw new LuaException(e.getLocalizedMessage());
					}
				}
			};
		}
		
		@Override
		void flush() throws IOException {}

		@Override
		void close() throws IOException
		{
			super.close();
			reader.close();
		}
	}
	
	static class LuaFileWriterUserdata extends LuaFileUserdata
	{
		private final FileWriter writer;
		
		LuaFileWriterUserdata(File file, boolean append) throws IOException
		{
			super(file);
			writer = new FileWriter(file, append);
		}

		@Override
		void write(String... strs) throws IOException
		{
			for(String str : strs)
				writer.write(str);
		}

		@Override
		LuaObject[] read(Object... formats) throws IOException
		{
			return new LuaObject[] { LuaNil.NIL };
		}
		
		LuaObject lines(LuaObject[] args, int offset)
		{
			return LuaNil.NIL;
		}

		@Override
		void flush() throws IOException
		{
			writer.flush();
		}

		@Override
		void close() throws IOException
		{
			super.close();
			writer.close();
		}
	}
	
	static class LuaFileInputUserdata extends LuaFileUserdata
	{
		private final FileInputStream input;
		
		private LuaFileInputUserdata(File file) throws IOException
		{
			super(file);
			this.input = new FileInputStream(file);
		}

		@Override
		void write(String... strs) throws IOException
		{
			
		}

		@Override
		LuaObject[] read(Object... formats) throws IOException
		{

			return new LuaObject[] { LuaNil.NIL };
		}
		
		@Override
		LuaObject lines(LuaObject[] args, int offset)
		{
			return LuaNil.NIL;
		}

		@Override
		void flush() throws IOException {}

		@Override
		void close() throws IOException
		{
			super.close();
			input.close();
		}
	}
	
	static class LuaFileOutputUserdata extends LuaFileUserdata
	{
		private final FileOutputStream output;
		
		LuaFileOutputUserdata(File file, boolean append) throws IOException
		{
			super(file);
			output = new FileOutputStream(file, append);
		}

		@Override
		void write(String... strs) throws IOException
		{
			
		}

		@Override
		LuaObject[] read(Object... formats) throws IOException
		{
			return new LuaObject[] { LuaNil.NIL };
		}
		
		@Override
		LuaObject lines(LuaObject[] args, int offset)
		{
			return LuaNil.NIL;
		}

		@Override
		void flush() throws IOException
		{
			output.flush();
		}

		@Override
		void close() throws IOException
		{
			super.close();
			output.close();
		}
	}
	
	abstract static class LuaFileUserdata extends LuaUserdata
	{
		private final File file;
		private boolean closed;
		
		LuaFileUserdata(File file)
		{
			this.file = file;
			this.metatable = ioMetatable;
		}
		
		abstract LuaObject[] read(Object... formats) throws IOException;
		
		abstract void write(String... strs) throws IOException;

		abstract void flush() throws IOException;
		
		abstract LuaObject lines(LuaObject[] args, int offset);
		
//		abstract long seek(String whence, int offset) throws IOException;

		void close() throws IOException
		{
			closed = true;
		}
		
		@Override
		public String name()
		{
			return "FILE*";
		}

		@Override
		public Object getUserdata()
		{
			return file;
		}

		@Override
		public String getString(LuaInterpreter interp)
		{
			return "file (0x" + Integer.toHexString(file.hashCode()) + ")";
		}
	}
}
