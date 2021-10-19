package com.hk.lua;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>This class encapsulates a Lua environment. A Lua environment
 * consists of global and local variables which are all accessible
 * through this utility.</p>
 *
 * @author theKayani
 */
public class Environment
{
	/**
	 * The interpreter this environment is currently running under.
	 */
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
			lua_G = new LuaGTable(globalMap = new HashMap<>());
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
	 * <p>This method sets a value to a variable. If the variable is
	 * local, this will reassign the local variable. If the variable
	 * is global or doesn't exist yet, this will assign the global
	 * variable.</p>
	 *
	 * @param name name of the variable
	 * @param value value of the variable
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

	/**
	 * <p>This method explicitly sets the local variable with the
	 * given name to the given value. If the local variable already
	 * exists, it will reassign the local variable. If the local
	 * variable doesn't exist, it will assign the value to the local
	 * variable.</p>
	 * <p>If there is a global variable with this name, it is left
	 * untouched. This method does not affect the global space.</p>
	 *
	 * @param name name of the variable to assign
	 * @param value value of the variable to assign
	 */
	public void setLocal(String name, LuaObject value)
	{
		if(locals.containsKey(name))
			locals.get(name)[0] = value;
		else
			locals.put(name, new LuaObject[] { value });
	}
	
	/**
	 * <p>Retrieve the value of the variable under the specified
	 * name. If this variable exists in the local space, this method
	 * will return the local value. Otherwise it will return the
	 * global value if one exists. If one does not exist in the
	 * global space, this will return <code>nil</code>.</p>
	 *
	 * @param name name of the variable to retrieve
	 * @return value of the variable specified
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
	 * <p>This checks whether the variable exists in the local space.</p>
	 *
	 * @param name name of the variable to check
	 * @return whether the specific variable exists in the local space
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
		void doNewIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
		{
//			super.doNewIndex(key, value);
			if(key.isString())
				setVar(key.getString(), value);
		}
	}
}
