package com.hk.lua;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class LuaString extends LuaMetatable
{
	private final String value;

	LuaString(CharSequence value)
	{
		this.value = value.toString();
		metatable = LuaLibraryString.stringMetatable;
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaObject rawLen()
	{
		return LuaInteger.valueOf(value.length());
	}

	/** {@inheritDoc}
	 * @param o*/
	@Override
	public boolean rawEqual(@NotNull LuaObject o)
	{
		return o.isString() && value.equals(o.getString());
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

	/** {@inheritDoc} */
	@Override
	public boolean getBoolean()
	{
		return true;
	}

	/** {@inheritDoc}
	 * @param interp
	 * @return*/
	@Override
	public @NotNull String getString(@Nullable LuaInterpreter interp)
	{
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public double getDouble()
	{
		Number num = getNumber(value);
		if(num == null)
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
		else
			return num.doubleValue();
	}

	/** {@inheritDoc} */
	@Override
	public long getLong()
	{
		Number num = getNumber(value);
		if(!(num instanceof Long))
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
		else
			return num.longValue();
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
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isString()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNumber()
	{
		return getNumber(value) != null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInteger()
	{
//		return getNumber(value) instanceof Long;
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

	@Override
	LuaBoolean doLE(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.code() == T_STRING) {
			return LuaBoolean.valueOf(value.compareTo(o.getString()) <= 0);
		}
		throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doLT(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.code() == T_STRING) {
			return LuaBoolean.valueOf(value.compareTo(o.getString()) < 0);
		}
		throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doEQ(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaBoolean.valueOf(o.isString() && value.equals(o.getString()));
	}

	@Override
	LuaObject doConcat(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		switch(o.code())
		{
		case T_STRING:
		case T_NUMBER:
			return new LuaString(getString() + o.getString());
		default:
			throw LuaErrors.INVALID_CONCATENATE.create(o.name());
		}
	}

	@Override
	LuaObject doAdd(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return new LuaFloat(getDouble() + o.getDouble());
	}

	@Override
	LuaObject doSub(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return new LuaFloat(getDouble() - o.getDouble());
	}

	@Override
	LuaObject doMul(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return new LuaFloat(getDouble() * o.getDouble());
	}

	@Override
	LuaObject doDiv(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return new LuaFloat(getDouble() / o.getDouble());
	}

	@Override
	LuaObject doIDiv(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(getLong() / o.getLong());
	}

	@Override
	LuaObject doMod(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return new LuaFloat(getDouble() % o.getDouble());
	}

	@Override
	LuaObject doPow(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return new LuaFloat(Math.pow(getDouble(), o.getDouble()));
	}

	@Override
	LuaObject doBAND(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(getLong() & o.getLong());
	}

	@Override
	LuaObject doBOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(getLong() | o.getLong());
	}

	@Override
	LuaObject doBXOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		return LuaInteger.valueOf(getLong() ^ o.getLong());
	}

	@Override
	LuaObject doSHL(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.getInt() == 64) {
			return LuaInteger.ZERO;
		}
		return LuaInteger.valueOf(getLong() << o.getLong());
	}

	@Override
	LuaObject doSHR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		if (o.getInt() == 64) {
			return LuaInteger.ZERO;
		}
		return LuaInteger.valueOf(getLong() >> o.getLong());
	}

	@Override
	LuaObject doBNOT(@Nullable LuaInterpreter interp)
	{
		return LuaInteger.valueOf(~getLong());
	}

	@Override
	LuaObject doUnm(@Nullable LuaInterpreter interp)
	{
		return new LuaFloat(-getDouble());
	}

	@Override
	LuaObject doLen(@Nullable LuaInterpreter interp)
	{
		return rawLen();
	}

	@Override
	LuaObject doIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key)
	{
		return metatable.doIndex(interp, key);
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

	/** {@inheritDoc}
	 * @param metatable*/
	@Override
	public void setMetatable(@NotNull LuaObject metatable)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	int code()
	{
		return T_STRING;
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaType type()
	{
		return LuaType.STRING;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	static Number getNumber(String value)
	{
		value = value.trim();

		boolean isFloat = false;
		int index = 0, n;
		double decimal = 0;
		boolean negative = false;

		if(index < value.length() && (value.charAt(index) == '+' || value.charAt(index) == '-'))
		{
			negative = value.charAt(index) == '-';
			value = value.substring(1);
		}

		if(value.startsWith("0x") || value.startsWith("0X"))
		{
			index = 2;

			while(index < value.length())
			{
				if((n = Tokenizer.num(value.charAt(index))) >= 16)
					break;

				decimal *= 16;
				decimal += n;

				index++;
			}

			if(index < value.length() && value.charAt(index) == '.')
			{
				double fraction = 0;
				double place = 16;
				while(index < value.length())
				{
					if((n = Tokenizer.num(value.charAt(index))) >= 16)
						break;

					fraction += n / place;
					place *= 16;
					index++;
				}

				decimal += fraction;
				isFloat = true;
			}

			if(index < value.length() && (value.charAt(index) == 'p' || value.charAt(index) == 'P'))
			{
				index++;
				boolean neg = false;

				if(index < value.length() && (value.charAt(index) == '-' || value.charAt(index) == '+'))
				{
					neg = value.charAt(index) == '-';

					index++;
				}

				double exponent = 0;
				while(index < value.length())
				{
					if((n = Tokenizer.num(value.charAt(index))) >= 10)
						break;

					exponent *= 10;
					exponent += n;

					index++;
				}

				decimal *= Math.pow(2, neg ? -exponent : exponent);
				isFloat = true;
			}
		}
		else
		{
			while(index < value.length())
			{
				if((n = Tokenizer.num(value.charAt(index))) >= 10)
					break;

				decimal *= 10;
				decimal += n;

				index++;
			}

			if(index < value.length() && value.charAt(index) == '.')
			{
				index++;
				double fraction = 0;
				double place = 10;
				while(index < value.length())
				{
					if((n = Tokenizer.num(value.charAt(index))) >= 10)
						break;

					fraction += n / place;
					place *= 10;

					index++;
				}

				decimal += fraction;
				isFloat = true;
			}

			if(index < value.length() && (value.charAt(index) == 'e' || value.charAt(index) == 'E'))
			{
				index++;
				boolean neg = false;

				if(index < value.length() && (value.charAt(index) == '-' || value.charAt(index) == '+'))
				{
					neg = value.charAt(index) == '-';

					index++;
				}

				double exponent = 0;
				while(index < value.length())
				{
					if((n = Tokenizer.num(value.charAt(index))) >= 10)
						break;

					exponent *= 10;
					exponent += n;

					index++;
				}

				decimal *= Math.pow(10, neg ? -exponent : exponent);
				isFloat = true;
			}
		}

		if(negative)
			decimal = -decimal;

		if(index != value.length())
			return null;
		else if(!isFloat && decimal == (double) (long) decimal)
			return (long) decimal;
		else
			return decimal;
	}
}