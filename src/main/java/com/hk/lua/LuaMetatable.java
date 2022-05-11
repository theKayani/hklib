package com.hk.lua;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

abstract class LuaMetatable extends LuaObject
{
	protected LuaObject metatable;

	LuaMetatable()
	{
		metatable = LuaNil.NIL;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVarargs()
	{
		return false;
	}

	LuaBoolean doEQ(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if(rawEqual(o))
			return LuaBoolean.TRUE;

		if(code() == o.code())
		{
			LuaObject mm1 = metatable == null || metatable.isNil() ? null : metatable.rawGet(new LuaString("__eq"));
			LuaObject mm2 = o.getMetatable().isNil() ? null : o.getMetatable().rawGet(new LuaString("__eq"));
			LuaObject mm = mm1 == mm2 ? mm1 : null;

			if(mm != null && !mm.isNil())
			{
				Objects.requireNonNull(interp);
				LuaObject res = mm.doCall(interp, new LuaObject[] { this, o });
				res = res.evaluate(interp);
				return LuaBoolean.valueOf(res.getBoolean());
			}
		}
		return LuaBoolean.FALSE;
	}

	/** {@inheritDoc}
	 * @param metatable*/
	public void setMetatable(@NotNull LuaObject metatable)
	{
		this.metatable = metatable;
	}

	/**
	 * <p>Getter for the field <code>metatable</code>.</p>
	 *
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public @NotNull LuaObject getMetatable()
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

		return !other.getMetatable().isNil() ? other.getMetatable().rawGet(ls) : null;
	}
}