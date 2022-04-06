package com.hk.lua;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.hk.func.BiConsumer;
import com.hk.lua.Lua.LuaMethod;

/**
 * <p>LuaLibraryString class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryString implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	_byte() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			String str = args[0].getString();
			LuaObject i = args.length > 1 ? args[1] : LuaInteger.ONE;
			LuaObject j = args.length > 2 ? args[2] : i;
			
			LuaObject[] res = new LuaObject[j.doSub(interp, i).getInt() + 1];
			for(int k = 0; k < res.length; k++)
			{
				res[k] = LuaInteger.valueOf((int) str.charAt(i.getInt() - 1));
				i = i.doAdd(interp, LuaInteger.ONE);
			}
			return new LuaArgs(res);
		}
		
		public String toString()
		{
			return "byte";
		}
	},
	_char() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			char[] cs = new char[args.length];
			for(int i = 0; i < args.length; i++)
			{
				if(!args[i].isInteger())
					throw Lua.badArgument(i, "char", "integer expected");
				
				cs[i] = (char) args[i].getInt();
			}
			return new LuaString(new String(cs));
		}

		public String toString()
		{
			return "char";
		}
	},
	dump() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new UnsupportedOperationException("This is not supported in this version (Lua 5.3 JVM)");
		}
	},
	find() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING, LuaType.STRING);
			String str = args[0].getString();
			String pattern = args[1].getString();
			int init = args.length > 2 ? args[2].getInt() - 1 : 0;
			while(init < 0)
				init += str.length();
			boolean plain = args.length > 3 && args[3].getBoolean();
			
			if(plain)
			{
				int indx = str.indexOf(pattern, init);
				if(indx >= 0)
					return new LuaArgs(LuaInteger.valueOf(indx + 1), LuaInteger.valueOf(indx + pattern.length()));
			}
			else
			{
				Pattern ptn = toJavaPattern(pattern);
				Matcher mtr = ptn.matcher(str);
				if(mtr.find(init))
				{
					LuaObject[] arr = new LuaObject[mtr.groupCount() + 2];
					arr[0] = LuaInteger.valueOf(mtr.start() + 1);
					arr[1] = LuaInteger.valueOf(mtr.end());
					for(int i = 1; i <= mtr.groupCount(); i++)
					{
						String g = mtr.group(i);
						if(g == null)
							arr[i + 1] = LuaNil.NIL;
						else
							arr[i + 1] = new LuaString(g);
					}
					
					return new LuaArgs(arr);
				}
			}
			return LuaNil.NIL;
		}
	},
	format() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			String str = args[0].getString();

			Object arg;
			Object[] newargs = new Object[args.length - 1];

			for(int i = 1; i < args.length; i++)
			{
				switch (args[i].type())
				{
					case NIL:
						arg = null;
						break;
					case BOOLEAN:
						arg = args[i].getBoolean();
						break;
					case FLOAT:
						arg = args[i].getDouble();
						break;
					case INTEGER:
						arg = args[i].getLong();
						break;
					case USERDATA:
						if(args[i] instanceof LuaLibraryDate.LuaDateUserdata)
							arg = args[i].getUserdata();
						else
							arg = args[i].getString();
						break;
					default:
						arg = args[i].getString();
						break;
				}

				newargs[i - 1] = arg;
			}

			return new LuaString(String.format(str, newargs));
		}
	},
	gmatch() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING, LuaType.STRING);
			String str = args[0].getString();
			String pattern = args[1].getString();

			Pattern ptn = toJavaPattern(pattern);
			final Matcher mtr = ptn.matcher(str);
			return new LuaFunction() {
				@Override
				LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
				{
					if(mtr.find())
					{
						if(mtr.groupCount() > 0)
						{
							LuaObject[] arr = new LuaObject[mtr.groupCount()];
							for(int i = 1; i <= mtr.groupCount(); i++)
							{
								String g = mtr.group(i);
								if(g == null)
									arr[i - 1] = LuaNil.NIL;
								else
									arr[i - 1] = new LuaString(g);
							}
							
							return new LuaArgs(arr);
						}
						else
						{
							return new LuaString(mtr.group());
						}
					}
					return LuaNil.NIL;
				}				
			};
		}
	},
	gsub() {
		@Override
		@SuppressWarnings("incomplete-switch")
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING, LuaType.STRING, LuaType.ANY);
			String str = args[0].getString();
			String pattern = args[1].getString();
			Object repl;
			LuaType type = args[2].type();
			switch(type)
			{
			case STRING:
				String s = args[2].getString();
				StringBuilder sb = new StringBuilder(s.length());
				char c;
				for(int i = 0; i < s.length(); i++)
				{
					c = s.charAt(i);
					if(c == '%' && i < s.length() - 1)
					{
						c = s.charAt(i + 1);
						if(c == '%')
							sb.append('%');
						else if('0' <= c && c <= '9')
							sb.append("$").append(c);
						i++;
					}
					else if(c == '$')
						sb.append("\\$");
					else
						sb.append(c);
				}
				repl = sb.toString();
				break;
			case TABLE:
			case FUNCTION:
				repl = args[2];
				break;
			default:
				throw Lua.badArgument(2, "gsub", "string, table, or function expected");
			}
			int n = args.length > 3 ? args[3].getInt() : Integer.MAX_VALUE;
			int total = 0;

			StringBuffer res = new StringBuffer();
			Pattern ptn = toJavaPattern(pattern);
			Matcher mtr = ptn.matcher(str);
			LuaObject obj;
			String r;
			while(mtr.find() && total++ < n)
			{
				switch(type)
				{
				case STRING:
					try
					{
						mtr.appendReplacement(res, ((String) repl).replace("\\", "\\\\"));
					}
					catch(IllegalArgumentException | IndexOutOfBoundsException ex)
					{
						throw new LuaException(ex.getLocalizedMessage());
					}
					break;
				case TABLE:
					obj = ((LuaObject) repl).doIndex(interp, new LuaString(mtr.groupCount() > 0 ? mtr.group(1) : mtr.group()));
					
					if(obj == null || obj.isNil())
						r = mtr.group();
					else
						r = obj.getString();
					mtr.appendReplacement(res, r.replace("\\", "\\\\").replace("$", "\\$"));
					break;
				case FUNCTION:
					LuaObject[] ags;
					
					if(mtr.groupCount() > 0)
					{
						ags = new LuaObject[mtr.groupCount()];
						for(int i = 0; i < mtr.groupCount(); i++)
							ags[i] = new LuaString(mtr.group(i + 1));
					}
					else
						ags = new LuaObject[] { new LuaString(mtr.group()) };

					obj = ((LuaObject) repl).doCall(interp, ags);
					if(obj == null || obj.isNil())
						r = mtr.group();
					else
						r = obj.getString();
					mtr.appendReplacement(res, r.replace("\\", "\\\\").replace("$", "\\$"));
					break;
				}
			}

			if(mtr.hitEnd())
				mtr.appendTail(res);

			return new LuaArgs(new LuaString(res.toString()), LuaInteger.valueOf(total));
		}
	},
	len() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			return args[0].rawLen();
		}
	},
	lower() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			return new LuaString(args[0].getString().toLowerCase());
		}
	},
	match() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING, LuaType.STRING);
			String str = args[0].getString();
			String pattern = args[1].getString();
			int init = args.length > 2 ? args[2].getInt() - 1 : 0;
			while(init < 0)
				init += str.length();
			
			Pattern ptn = toJavaPattern(pattern);
			Matcher mtr = ptn.matcher(str);
			if(mtr.find(init))
			{
				List<LuaObject> lst = new ArrayList<>();
				if(mtr.groupCount() > 0)
				{
					for(int i = 1; i <= mtr.groupCount(); i++)
					{
						String g = mtr.group(i);
						if(g == null)
							lst.add(LuaNil.NIL);
						else
							lst.add(new LuaString(g));
					}
				}
				else
				{
					lst.add(new LuaString(mtr.group()));
				}
				
				return new LuaArgs(lst.toArray(new LuaObject[0]));
			}

			return LuaNil.NIL;
		}
	},
	pack() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	packsize() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	rep() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING, LuaType.NUMBER);
			String str = args[0].getString();
			LuaObject n = args[1];
			LuaObject sep = args.length > 2 ? args[2] : new LuaString("");
			
			StringBuilder sb = new StringBuilder(str.length() * n.getInt() + sep.getString().length() * (n.getInt() - 1));
			for(int i = 0; i < n.getInt(); i++)
			{
				sb.append(str);

				if(i < n.getInt() - 1)
					sb.append(sep.getString());
			}
			return new LuaString(sb.toString());
		}
	},
	reverse() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			return new LuaString(new StringBuilder(args[0].getString()).reverse().toString());
		}
	},
	sub() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			try
			{
				String str = args[0].getString();
				int i = args.length > 1 ? args[1].getInt() : 1;
				int j = args.length > 2 ? args[2].getInt() : i;

				char[] res = new char[Math.min(j - i, str.length() - i) + 1];
				for (int k = 0; k < res.length; k++)
				{
					res[k] = str.charAt(i - 1);
					i++;
				}
				return new LuaString(new String(res));
			}
			catch (StringIndexOutOfBoundsException ex)
			{
				throw new LuaException(ex.getLocalizedMessage());
			}
		}
	},
	unpack() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			throw new Error();
		}
	},
	upper() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			return new LuaString(args[0].getString().toUpperCase());
		}
	};
	
	final LuaObject func;
	
	LuaLibraryString()
	{
		func = Lua.newFunc(this);
	}

	/** {@inheritDoc} */
	@Override
	public void accept(Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), func);
	}

	private static Pattern toJavaPattern(String pattern)
	{
		StringBuilder sb = new StringBuilder(pattern.length());
		for(int i = 0; i < pattern.length(); i++)
			sb.append(pattern.charAt(i));

		try
		{
			return Pattern.compile(sb.toString());
		}
		catch(PatternSyntaxException ex)
		{
			throw new LuaException(ex.getLocalizedMessage());
		}
	}

	static final LuaTable stringMetatable;
	
	static
	{
		Map<LuaObject, LuaObject> map = new LinkedHashMap<>();
		for(LuaLibraryString f : values())
		{
			String name = f.toString();
			if(name != null && !name.trim().isEmpty())
				map.put(new LuaString(name), f.func);
		}
		stringMetatable = new LuaTable(Collections.unmodifiableMap(map));
	}
}
