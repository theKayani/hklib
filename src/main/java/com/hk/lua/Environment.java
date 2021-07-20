package com.hk.lua;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Environment class.</p>
 *
 * @author theKayani
 */
public class Environment
{
	public final LuaInterpreter interp;
	final LuaTable lua_G;
	LuaObject varargs;
	private final Map<String, LuaObject[]> globals, locals;
	private final Map<LuaObject, LuaObject> globalMap;
	
	Environment(LuaInterpreter interp, Environment parent, boolean onlyGlobals)
	{
		this.interp = interp;
		if(parent == null)
		{
			globals = new HashMap<>();
			lua_G = new LuaGTable(globalMap = new HashMap<LuaObject, LuaObject>());
			globals.put("_G", new LuaObject[] { lua_G });
			locals = new HashMap<>();
			varargs = LuaNil.NIL;
		}
		else
		{
			globals = parent.globals;
			if(onlyGlobals)
				locals = new HashMap<>();
			else
				locals = new HashMap<>(parent.locals);
			varargs = parent.varargs;
			globalMap = parent.globalMap;
			lua_G = parent.lua_G;
		}
	}
	
	/**
	 * <p>setVar.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @param value a {@link com.hk.lua.LuaObject} object
	 */
	public void setVar(String name, LuaObject value)
	{
		if(locals.containsKey(name))
			locals.get(name)[0] = value;
		else
		{
			if(value.isNil())
			{
				globals.remove(name);
				globalMap.remove(new LuaString(name));
			}
			else if(globals.containsKey(name))
			{
				globals.get(name)[0] = value;
				globalMap.put(new LuaString(name), value);
			}
			else
			{
				globals.put(name, new LuaObject[] { value });
				globalMap.put(new LuaString(name), value);
			}
		}
	}
	
	void setLocal(String name, LuaObject value)
	{
		if(locals.containsKey(name))
			locals.get(name)[0] = value;
		else
			locals.put(name, new LuaObject[] { value });
	}
	
	/**
	 * <p>getVar.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject getVar(String name)
	{
		LuaObject[] obj = locals.get(name);
		if(obj == null)
		{
			obj = globals.get(name);
			return obj == null ? LuaNil.NIL : obj[0];
		}

		return obj[0];
	}
	
	/**
	 * <p>isLocal.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isLocal(String name)
	{
		return locals.containsKey(name);
	}
	
	private class LuaGTable extends LuaTable
	{
		private LuaGTable(Map<LuaObject, LuaObject> map)
		{
			super(map);
		}

		@Override
		public void rawSet(LuaObject key, LuaObject value)
		{
//			super.rawSet(key, value);
			if(key.isString())
				setVar(key.getString(), value);
		}
		
		@Override
		public void doNewIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
		{
//			super.doNewIndex(key, value);
			if(key.isString())
				setVar(key.getString(), value);
		}
	}
}
