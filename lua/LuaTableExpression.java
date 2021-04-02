package com.hk.lua;

class LuaTableExpression extends LuaExpression
{
	LuaTableExpression(LuaInterpreter interp)
	{
		super(interp);
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
	
	LuaObject evaluate()
	{
		LuaTable tbl = new LuaTable();
		Lua.LuaValue[] array = (Lua.LuaValue[]) root;
		int index = 1;
		for(int i = 0; i < array.length; i += 2)
		{
			LuaObject obj = array[i + 1].evaluate();
			if(array[i] == null)
			{
				if(i == array.length - 2 && obj instanceof LuaArgs)
				{
					LuaObject[] os = ((LuaArgs) obj).objs;
					for(LuaObject o : os)
						tbl.doNewIndex(LuaInteger.valueOf(index++), o);
					
				}
				else
					tbl.doNewIndex(LuaInteger.valueOf(index++), obj);
			}
			else
			{
				tbl.doNewIndex(array[i].evaluate(), obj);
			}
		}
		return tbl;
	}
	
	boolean isCall()
	{
		return false;
	}
}
