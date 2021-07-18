package com.hk.lua;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.hk.func.BiConsumer;
import com.hk.lua.Lua.LuaMethod;

public enum LuaLibraryHash implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	md5() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			return hash("MD5", args[0].getString(), false);
		}
	},
	sha1() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			return hash("SHA-1", args[0].getString(), false);
		}
	},
	sha256() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			return hash("SHA-256", args[0].getString(), false);
		}
	},
	sha512() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			return hash("SHA-512", args[0].getString(), false);
		}
	},
	rawmd5() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			return hash("MD5", args[0].getString(), true);
		}
	},
	rawsha1() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			return hash("SHA-1", args[0].getString(), true);
		}
	},
	rawsha256() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			return hash("SHA-256", args[0].getString(), true);
		}
	},
	rawsha512() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING);
			return hash("SHA-512", args[0].getString(), true);
		}
	};

	@Override
	public void accept(Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), Lua.newFunc(this));
	}
	
	public static LuaObject hash(String alg, String input, boolean raw)
	{
		try
		{
			MessageDigest dg = MessageDigest.getInstance(alg);
			dg.update(input.getBytes());
			byte[] out = dg.digest();
			
			if(raw)
				return new LuaString(new String(out));
			
			char[] cs = new char[out.length * 2];
			for(int i = 0; i < out.length; i++)
			{
				cs[i * 2 + 1] = hex[out[i] & 0xF];
				cs[i * 2] = hex[(out[i] >> 4) & 0xF];
			}
			return new LuaString(new String(cs));
		}
		catch (NoSuchAlgorithmException e)
		{
			return new LuaArgs(LuaNil.NIL, new LuaString(e.getLocalizedMessage()));
		}
	}
	
	private static final char[] hex = "0123456789ABCDEF".toCharArray();
}
