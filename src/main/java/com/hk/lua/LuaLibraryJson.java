package com.hk.lua;

import java.io.*;
import java.net.URL;
import java.util.Map;

import com.hk.func.BiConsumer;
import com.hk.json.*;
import com.hk.lua.Lua.LuaMethod;

/**
 * <p>LuaLibraryJson class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryJson implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	read() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);

			try
			{
				return toLua(Json.read(args[0].getString()));
			}
			catch (JsonFormatException e)
			{
				throw new LuaException(e.getLocalizedMessage());
			}
		}
	},
	readFile() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			
			try
			{
				return toLua(Json.read(new File(args[0].getString())));
			}
			catch (FileNotFoundException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
			}
			catch (JsonFormatException e)
			{
				throw new LuaException(e.getLocalizedMessage());
			}
		}
	},
	readURL() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			
			try
			{
				return toLua(Json.read(new URL(args[0].getString())));
			}
			catch (IOException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
			}
			catch (JsonFormatException e)
			{
				throw new LuaException(e.getLocalizedMessage());
			}
		}
	},
	readFrom() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			if(args.length < 1 || !(args[0] instanceof LuaReader))
				throw new LuaException("bad argument #1 to 'readFrom' (reader expected)");

			Reader rdr = args[0].getUserdata(Reader.class);

			try
			{
				return toLua(Json.reader(rdr).unsetReadFully().get());
			}
			catch (JsonFormatException e)
			{
				throw new LuaException(e.getLocalizedMessage());
			}
		}
	},
	write() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			
			return new LuaString(Json.write(toJson(args[0])));
		}
	},
	writePretty() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			
			StringBuilder sb = new StringBuilder();
			JsonWriter writer = Json.writer(sb);
			writer.setPrettyPrint();
			writer.put(toJson(args[0]));
			writer.close();
			return new LuaString(sb);
		}
	},
	writeFile() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING, LuaType.ANY);

			try
			{
				Json.write(new File(args[0].getString()), toJson(args[1]));
				return LuaBoolean.TRUE;
			}
			catch (FileNotFoundException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
			}
		}
	},
	writeTo() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			if(args.length < 1 || !(args[0] instanceof LuaWriter))
				throw new LuaException("bad argument #1 to 'writeTo' (writer expected)");
			Writer wtr = args[0].getUserdata(Writer.class);

			Json.writer(wtr).put(toJson(args[1]));
			return LuaBoolean.TRUE;
		}
	};

	/** {@inheritDoc} */
	@Override
	public LuaObject call(LuaInterpreter interp, LuaObject[] args)
	{
		throw new Error();
	}

	/** {@inheritDoc} */
	@Override
	public void accept(Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), Lua.newFunc(this));
	}

	/**
	 * Convert {@link LuaObject}s to {@link JsonValue}s
	 *
	 * @param obj to convert
	 * @return a {@link JsonValue}
	 */
	public static JsonValue toJson(LuaObject obj)
	{
		if(obj == null || obj.isNil())
			return JsonNull.NULL;
		else if(obj.isBoolean())
			return JsonBoolean.valueOf(obj.getBoolean());
		else if(obj.isString() || obj.isFunction() || obj.isUserdata() || obj.isThread())
			return new JsonString(obj.getString());
		else if(obj.isInteger())
			return new JsonNumber(obj.getLong());
		else if(obj.isNumber())
			return new JsonNumber(obj.getDouble());
		else if(obj.isTable())
		{
			long len = obj.getLength();
			if(len > 0)
			{
				JsonArray arr = new JsonArray();
				
				for(long i = 1; i <= len; i++)
					arr.add(toJson(obj.rawGet(i)));
				
				return arr;
			}
			else
			{
				JsonObject o = new JsonObject();
				
				for(LuaObject indx : obj.getIndicies())
					o.put(indx.getString(), toJson(obj.rawGet(indx)));
				
				return o;
			}
		}
		return JsonNull.NULL;
	}

	/**
	 * Convert {@link JsonValue}s to {@link LuaObject}s
	 *
	 * @param val to convert
	 * @return a {@link LuaObject}
	 */
	public static LuaObject toLua(JsonValue val)
	{
		if(val == null || val.isNull())
			return LuaNil.NIL;
		else if(val.isString())
			return new LuaString(val.getString());
		else if(val.isBoolean())
			return LuaBoolean.valueOf(val.getBoolean());
		else if(val.isNumber())
			return val.getNumber() instanceof Long ? LuaInteger.valueOf(val.getNumber().longValue()) : new LuaFloat(val.getNumber().doubleValue());
		else if(val.isObject())
		{
			LuaTable tbl = new LuaTable();
			for(Map.Entry<String, JsonValue> ent : val.getObject())
				tbl.rawSet(new LuaString(ent.getKey()), toLua(ent.getValue()));

			return tbl;
		}
		else if(val.isArray())
		{
			LuaTable tbl = new LuaTable();

			long i = 1;
			for(JsonValue val2 : val.getArray())
				tbl.rawSet(LuaInteger.valueOf(i++), toLua(val2));
				
			return tbl;
		}
		return LuaNil.NIL;
	}
}
