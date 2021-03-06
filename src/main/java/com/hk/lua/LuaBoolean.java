package com.hk.lua;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class LuaBoolean extends LuaObject
{
	static final LuaBoolean TRUE = new LuaBoolean(true);
	static final LuaBoolean FALSE = new LuaBoolean(false);

	private final boolean value;

	private LuaBoolean(boolean value)
	{
		this.value = value;
	}

	/**
	 * <p>not.</p>
	 *
	 * @return a {@link com.hk.lua.LuaBoolean} object
	 */
	public LuaBoolean not()
	{
		return value ? FALSE : TRUE;
	}

	/** {@inheritDoc}
	 * @param o*/
	@Override
	public boolean rawEqual(@NotNull LuaObject o)
	{
		return o.isBoolean() && value == o.getBoolean();
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaObject rawLen()
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaObject rawGet(@NotNull LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	/** {@inheritDoc}
	 * @param key
	 * @param value*/
	@Override
	public void rawSet(@NotNull LuaObject key, @NotNull LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	/** {@inheritDoc}
	 * @param interp
	 * @return*/
	@Override
	public @NotNull String getString(@Nullable LuaInterpreter interp)
	{
		return value ? "true" : "false";
	}

	/** {@inheritDoc} */
	@Override
	public boolean getBoolean()
	{
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public double getDouble()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public long getLong()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNil()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isBoolean()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isString()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNumber()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInteger()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isTable()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isFunction()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isUserdata()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isThread()
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVarargs()
	{
		return false;
	}

	@Override
	LuaBoolean doLE(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.code() == T_FUNCTION)
		{
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
		throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
	}

	@Override
	LuaBoolean doLT(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.code() == T_FUNCTION)
		{
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
		throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
	}

	@Override
	LuaBoolean doEQ(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaBoolean.valueOf(o.isBoolean() && value == o.getBoolean());
	}

	@Override
	LuaObject doConcat(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_CONCATENATE.create(name());
	}

	@Override
	LuaObject doAdd(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSub(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMul(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doDiv(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doIDiv(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMod(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doPow(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBAND(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBXOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHL(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBNOT(@Nullable LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doUnm(@Nullable LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doLen(@Nullable LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	@Override
	LuaObject doIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	void doNewIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key, @NotNull LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
	{
		throw LuaErrors.INVALID_CALL.create(name());
	}

	@Override
	int code()
	{
		return T_BOOLEAN;
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaType type()
	{
		return LuaType.BOOLEAN;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return value ? 1231 : 1237;
	}

	static LuaBoolean valueOf(boolean value)
	{
		return value ? TRUE : FALSE;
	}
}