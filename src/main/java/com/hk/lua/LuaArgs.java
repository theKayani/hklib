package com.hk.lua;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class LuaArgs extends LuaObject
{
	final LuaObject obj;
	final LuaObject[] objs;

	LuaArgs(LuaObject... res)
	{
		objs = res;
		obj = objs[0];
	}

	/** {@inheritDoc}
	 * @param o*/
	@Override
	public boolean rawEqual(@NotNull LuaObject o)
	{
		return obj.rawEqual(o);
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaObject rawLen()
	{
		return obj.rawLen();
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaObject rawGet(@NotNull LuaObject key)
	{
		return obj.rawGet(key);
	}

	/** {@inheritDoc}
	 * @param key
	 * @param value*/
	@Override
	public void rawSet(@NotNull LuaObject key, @NotNull LuaObject value)
	{
		obj.rawSet(key, value);
	}

	/** {@inheritDoc} */
	@Override
	public boolean getBoolean()
	{
		return obj.getBoolean();
	}

	/** {@inheritDoc}
	 * @param interp
	 * @return*/
	@Override
	public @NotNull String getString(@Nullable LuaInterpreter interp)
	{
		return obj.getString();
	}

	/** {@inheritDoc} */
	@Override
	public double getDouble()
	{
		return obj.getDouble();
	}

	/** {@inheritDoc} */
	@Override
	public long getLong()
	{
		return obj.getLong();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNil()
	{
		return obj.isNil();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isBoolean()
	{
		return obj.isBoolean();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isString()
	{
		return obj.isString();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNumber()
	{
		return obj.isNumber();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInteger()
	{
		return obj.isInteger();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isTable()
	{
		return obj.isTable();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isFunction()
	{
		return obj.isFunction();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isUserdata()
	{
		return obj.isUserdata();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isThread()
	{
		return obj.isThread();
	}

	@Override
	LuaBoolean doLE(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doLE(interp, o);
	}

	@Override
	LuaBoolean doLT(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doLT(interp, o);
	}

	@Override
	LuaBoolean doEQ(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doEQ(interp, o);
	}

	@Override
	LuaObject doConcat(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doConcat(interp, o);
	}

	@Override
	LuaObject doAdd(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doAdd(interp, o);
	}

	@Override
	LuaObject doSub(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doSub(interp, o);
	}

	@Override
	LuaObject doMul(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doMul(interp, o);
	}

	@Override
	LuaObject doDiv(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doDiv(interp, o);
	}

	@Override
	LuaObject doIDiv(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doIDiv(interp, o);
	}

	@Override
	LuaObject doMod(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doMod(interp, o);
	}

	@Override
	LuaObject doPow(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doPow(interp, o);
	}

	@Override
	LuaObject doBAND(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doBAND(interp, o);
	}

	@Override
	LuaObject doBOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doBOR(interp, o);
	}

	@Override
	LuaObject doBXOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doBXOR(interp, o);
	}

	@Override
	LuaObject doSHL(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doSHL(interp, o);
	}

	@Override
	LuaObject doSHR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return obj.doSHR(interp, o);
	}

	@Override
	LuaObject doBNOT(@Nullable LuaInterpreter interp)
	{
		return obj.doBNOT(interp);
	}

	@Override
	LuaObject doUnm(@Nullable LuaInterpreter interp)
	{
		return obj.doUnm(interp);
	}

	@Override
	LuaObject doLen(@Nullable LuaInterpreter interp)
	{
		return obj.doLen(interp);
	}

	@Override
	LuaObject doIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key)
	{
		return obj.doIndex(interp, key);
	}

	@Override
	void doNewIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key, @NotNull LuaObject value)
	{
		obj.doNewIndex(interp, key, value);
	}

	@Override
	LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
	{
		return obj.doCall(interp, args);
	}

	@NotNull
	@Override
	LuaObject evaluate(@NotNull LuaInterpreter interp)
	{
		return obj;
	}

	@Override
	int code()
	{
		return obj.code();
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaType type()
	{
		return obj.type();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return obj.hashCode();
	}
}