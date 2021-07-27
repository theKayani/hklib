package com.hk.lua;

abstract class LuaMetatable extends LuaObject
{
	protected LuaObject metatable;
	
	LuaMetatable()
	{
		metatable = LuaNil.NIL;
	}

	LuaBoolean doEQ(LuaInterpreter interp, LuaObject o)
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
				LuaObject res = mm.doCall(interp, new LuaObject[] { this, o });
				res = res.evaluate(interp);
				return LuaBoolean.valueOf(((LuaObject) res).getBoolean());
			}
		}
		return LuaBoolean.FALSE;
	}
	
	/** {@inheritDoc} */
	public void setMetatable(LuaObject metatable)
	{
		this.metatable = metatable == null ? LuaNil.NIL : metatable;
	}
	
	/**
	 * <p>Getter for the field <code>metatable</code>.</p>
	 *
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject getMetatable()
	{
		return metatable == null ? LuaNil.NIL : metatable;
	}
	
	/**
	 * <p>event.</p>
	 *
	 * @param interp a {@link com.hk.lua.LuaInterpreter} object
	 * @param event a {@link java.lang.String} object
	 * @param other a {@link com.hk.lua.LuaObject} object
	 * @param args a {@link com.hk.lua.LuaObject} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	protected LuaObject event(LuaInterpreter interp, String event, LuaObject other, LuaObject... args)
	{
		LuaObject func = getHandler(event, other);
		if(func != null && func.isFunction())
			return func.doCall(interp, args.length == 0 ? new LuaObject[] { this, other } : args).evaluate(interp);
		else
			return null;
	}
	
	/**
	 * <p>getHandler.</p>
	 *
	 * @param event a {@link java.lang.String} object
	 * @param other a {@link com.hk.lua.LuaObject} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	protected LuaObject getHandler(String event, LuaObject other)
	{
		LuaString ls = new LuaString("__" + event);
		LuaObject mm = getMetatable().isNil() ? LuaNil.NIL : getMetatable().rawGet(ls);
		if(!mm.isNil())
			return mm;

		return !other.getMetatable().isNil() ? ((LuaTable) other.getMetatable()).rawGet(ls) : null;
	}
}