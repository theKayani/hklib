package com.hk.lua;

class LuaTableExpression extends LuaExpression
{
	LuaTableExpression(String source)
	{
		super(source);
	}

	@Override
	LuaTableExpression collect(Object[] array)
	{
		Lua.LuaValue[] vl = new Lua.LuaValue[array.length];
		for(int i = 0; i < array.length; i++)
			vl[i] = (Lua.LuaValue) array[i];

		root = vl;
		return this;
	}

	LuaObject evaluate(LuaInterpreter interp)
	{
		LuaTable tbl = new LuaTable();
		Lua.LuaValue[] array = (Lua.LuaValue[]) root;
		int index = 1;
		for(int i = 0; i < array.length; i += 2)
		{
			LuaObject obj = array[i + 1].evaluate(interp);
			if(array[i] == null)
			{
				if(i == array.length - 2 && obj instanceof LuaArgs)
				{
					LuaObject[] os = ((LuaArgs) obj).objs;
					for(LuaObject o : os)
						tbl.rawSet(LuaInteger.valueOf(index++), o);

				}
				else
					tbl.rawSet(LuaInteger.valueOf(index++), obj);
			}
			else
				tbl.rawSet(array[i].evaluate(interp), obj);
		}
		return tbl;
	}

	boolean isCall()
	{
		return false;
	}
}