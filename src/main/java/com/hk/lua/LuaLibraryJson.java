package com.hk.lua;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.hk.func.BiConsumer;
import com.hk.json.Json;
import com.hk.json.JsonArray;
import com.hk.json.JsonBoolean;
import com.hk.json.JsonNull;
import com.hk.json.JsonNumber;
import com.hk.json.JsonObject;
import com.hk.json.JsonString;
import com.hk.json.JsonValue;
import com.hk.json.JsonWriter;
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
			
			return toLua(interp, Json.read(args[0].getString()));
		}
	},
	readFile() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);
			
			try
			{
				return toLua(interp, Json.read(new File(args[0].getString())));
			}
			catch (FileNotFoundException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
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
				return toLua(interp, Json.read(new URL(args[0].getString())));
			}
			catch (IOException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
			}
		}
	},
	write() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			
			return new LuaString(Json.write(toJson(interp, args[0])));
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
			writer.put(toJson(interp, args[0]));
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
				Json.write(new File(args[0].getString()), toJson(interp, args[1]));
				return LuaBoolean.TRUE;
			}
			catch (FileNotFoundException e)
			{
				return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
			}
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
	
	private static JsonValue toJson(LuaInterpreter interp, LuaObject obj)
	{
		if(obj == null || obj.isNil())
			return JsonNull.NULL;
		else if(obj.isBoolean())
			return JsonBoolean.valueOf(obj.getBoolean());
		else if(obj.isString() || obj.isFunction() || obj.isUserdata() || obj.isThread())
			return new JsonString(obj.getString());
		else if(obj.isInteger())
			return new JsonNumber(obj.getInteger());
		else if(obj.isNumber())
			return new JsonNumber(obj.getFloat());
		else if(obj.isTable())
		{
			long len = obj.getLength();
			if(len > 0)
			{
				JsonArray arr = new JsonArray();
				
				for(long i = 1; i <= len; i++)
					arr.add(toJson(interp, obj.getIndex(interp, i)));
				
				return arr;
			}
			else
			{
				JsonObject o = new JsonObject();
				
				for(LuaObject indx : obj.getIndicies())
					o.put(indx.getString(), toJson(interp, obj.getIndex(interp, indx)));
				
				return o;
			}
		}
		return JsonNull.NULL;
	}
	
	private static LuaObject toLua(LuaInterpreter interp, JsonValue val)
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
				tbl.rawSet(new LuaString(ent.getKey()), toLua(interp, ent.getValue()));

			return tbl;
		}
		else if(val.isArray())
		{
			LuaTable tbl = new LuaTable();
			
			long i = 1;
			for(JsonValue val2 : val.getArray())
				tbl.setIndex(interp, i++, toLua(interp, val2));
				
			return tbl;
		}
		return LuaNil.NIL;
	}
}
