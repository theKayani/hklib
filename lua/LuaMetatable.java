package com.hk.lua;

abstract class LuaMetatable extends LuaObject
{
	protected LuaObject metatable;
	
	LuaMetatable()
	{
		metatable = LuaNil.NIL;
	}

	LuaBoolean doEQ(LuaObject o)
	{
		if(o == this)
			return LuaBoolean.TRUE;
	
		if(code() == o.code())
		{
			LuaObject mm1 = metatable == null ? null : metatable.rawGet(new LuaString("__eq"));
			LuaObject mm2 = !o.getMetatable().isNil() ? ((LuaTable) o.getMetatable()).rawGet(new LuaString("__eq")) : null;
			LuaObject mm = mm1 == mm2 ? mm1 : null;

			if(mm != null)
			{
				LuaObject res = mm.doCall(new LuaObject[] { this, o });
				res = res.evaluate();
				return LuaBoolean.valueOf(((LuaObject) res).getBoolean());
			}
		}
		return LuaBoolean.FALSE;
	}
	
	public void setMetatable(LuaObject metatable)
	{
		this.metatable = metatable == null ? LuaNil.NIL : metatable;
	}
	
	public LuaObject getMetatable()
	{
		return metatable == null ? LuaNil.NIL : metatable;
	}
	
	protected LuaObject event(String event, LuaObject other, LuaObject... args)
	{
		LuaObject func = getHandler(event, other);
		if(func != null && func.isFunction())
			return func.doCall(args.length == 0 ? new LuaObject[] { this, other } : args).evaluate();
		else
			return null;
	}
	
	protected LuaObject getHandler(String event, LuaObject other)
	{
		LuaString ls = new LuaString("__" + event);
		LuaObject mm = getMetatable().isNil() ? LuaNil.NIL : getMetatable().rawGet(ls);
		if(!mm.isNil())
			return mm;

		return !other.getMetatable().isNil() ? ((LuaTable) other.getMetatable()).rawGet(ls) : null;
	}
}