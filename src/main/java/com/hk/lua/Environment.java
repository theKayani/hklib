package com.hk.lua;

import org.jetbrains.annotations.NotNull;

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
	LuaObject lua_G;
	LuaObject varargs;
	private final Map<String, LuaObject[]> locals;

	Environment(LuaInterpreter interp, Environment parent, boolean onlyGlobals)
	{
		this.interp = interp;
		if(parent == null)
		{
			lua_G = new LuaTable();
			lua_G.rawSet("_G", lua_G);
			lua_G.rawSet("_ENV", lua_G);
			locals = new HashMap<>();
			varargs = LuaNil.NIL;
		}
		else
		{
			if(onlyGlobals)
				locals = new HashMap<>();
			else
				locals = new HashMap<>(parent.locals);
			varargs = parent.varargs;
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
	public void setVar(@NotNull String name, @NotNull LuaObject value)
	{
		setVar(new LuaString(name), value);
	}

	/**
	 * <p>This method sets a value to a variable. If the variable is
	 * local, this will reassign the local variable. If the variable
	 * is global or doesn't exist yet, this will assign the global
	 * variable.</p>
	 *
	 * @param key the key of the variable
	 * @param value value of the variable
	 */
	public void setVar(@NotNull LuaObject key, @NotNull LuaObject value)
	{
		boolean isStr = key.isString();
		if(isStr && locals.containsKey(key.getString()))
		{
			if(key.getString().equals("_ENV"))
			{
				Environment newEnv = new Environment(interp, interp.env, false);
				newEnv.lua_G = value;
				interp.env = newEnv;
			}
			else
				locals.get(key.getString())[0] = value;
		}
		else
		{
			if(isStr && key.getString().equals("_ENV"))
			{
				Environment newEnv = new Environment(interp, interp.env, false);
				newEnv.lua_G = value;
				interp.global = interp.env = newEnv;
			}
			else
				lua_G.doNewIndex(interp, key, value);
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
	public void setLocal(@NotNull String name, @NotNull LuaObject value)
	{
		if(name.equals("_ENV"))
		{
			Environment newEnv = new Environment(interp, interp.env, false);
			newEnv.lua_G = value;
			interp.env = newEnv;
		}
		else if(locals.containsKey(name))
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
	@NotNull
	public LuaObject getVar(@NotNull String name)
	{
		return getVar(new LuaString(name));
	}

	/**
	 * <p>Retrieve the value of the variable under the specified
	 * key. If this variable exists in the local space, this method
	 * will return the local value. Otherwise it will return the
	 * global value if one exists. If one does not exist in the
	 * global space, this will return <code>nil</code>.</p>
	 *
	 * @param key the key of the variable to retrieve
	 * @return value of the variable specified
	 */
	@NotNull
	public LuaObject getVar(@NotNull LuaObject key)
	{
		LuaObject[] obj = key.isString() ? locals.get(key.getString()) : null;
		if(obj == null)
			return lua_G.doIndex(interp, key);
		else
			return obj[0];
	}

	/**
	 * <p>This checks whether the variable exists in the local space.</p>
	 *
	 * @param name name of the variable to check
	 * @return whether the specific variable exists in the local space
	 */
	public boolean isLocal(@NotNull String name)
	{
		return locals.containsKey(name);
	}
}