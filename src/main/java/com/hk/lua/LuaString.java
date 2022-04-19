package com.hk.lua;

class LuaString extends LuaMetatable
{
	private final String value;

	LuaString(CharSequence value)
	{
		this.value = value.toString();
		metatable = LuaLibraryString.stringMetatable;
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject rawLen()
	{
		return LuaInteger.valueOf(value.length());
	}

	/** {@inheritDoc} */
	@Override
	public boolean rawEqual(LuaObject o)
	{
		return o.isString() && value.equals(o.getString());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject rawGet(LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public void rawSet(LuaObject key, LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public boolean getBoolean()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public String getString(LuaInterpreter interp)
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
	LuaBoolean doLE(LuaInterpreter interp, LuaObject o)
	{
		if (o.code() == T_STRING) {
			return LuaBoolean.valueOf(value.compareTo(o.getString()) <= 0);
		}
		throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doLT(LuaInterpreter interp, LuaObject o)
	{
		if (o.code() == T_STRING) {
			return LuaBoolean.valueOf(value.compareTo(o.getString()) < 0);
		}
		throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doEQ(LuaInterpreter interp, LuaObject o)
	{
		return LuaBoolean.valueOf(o.isString() && value.equals(o.getString()));
	}

	@Override
	LuaObject doConcat(LuaInterpreter interp, LuaObject o)
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
	LuaObject doAdd(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(getDouble() + o.getDouble());
	}

	@Override
	LuaObject doSub(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(getDouble() - o.getDouble());
	}

	@Override
	LuaObject doMul(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(getDouble() * o.getDouble());
	}

	@Override
	LuaObject doDiv(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(getDouble() / o.getDouble());
	}

	@Override
	LuaObject doIDiv(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getLong() / o.getLong());
	}

	@Override
	LuaObject doMod(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(getDouble() % o.getDouble());
	}

	@Override
	LuaObject doPow(LuaInterpreter interp, LuaObject o)
	{
		return new LuaFloat(Math.pow(getDouble(), o.getDouble()));
	}

	@Override
	LuaObject doBAND(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getLong() & o.getLong());
	}

	@Override
	LuaObject doBOR(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getLong() | o.getLong());
	}

	@Override
	LuaObject doBXOR(LuaInterpreter interp, LuaObject o)
	{
		return LuaInteger.valueOf(getLong() ^ o.getLong());
	}

	@Override
	LuaObject doSHL(LuaInterpreter interp, LuaObject o)
	{
		if (o.getInt() == 64) {
			return LuaInteger.ZERO;
		}
		return LuaInteger.valueOf(getLong() << o.getLong());
	}

	@Override
	LuaObject doSHR(LuaInterpreter interp, LuaObject o)
	{
		if (o.getInt() == 64) {
			return LuaInteger.ZERO;
		}
		return LuaInteger.valueOf(getLong() >> o.getLong());
	}

	@Override
	LuaObject doBNOT(LuaInterpreter interp)
	{
		return LuaInteger.valueOf(~getLong());
	}

	@Override
	LuaObject doUnm(LuaInterpreter interp)
	{
		return new LuaFloat(-getDouble());
	}

	@Override
	LuaObject doLen(LuaInterpreter interp)
	{
		return rawLen();
	}

	@Override
	LuaObject doIndex(LuaInterpreter interp, LuaObject key)
	{
		return metatable.doIndex(interp, key);
	}

	@Override
	void doNewIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
	{
		throw LuaErrors.INVALID_CALL.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public void setMetatable(LuaObject metatable)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	int code()
	{
		return T_STRING;
	}

	/** {@inheritDoc} */
	@Override
	public LuaType type()
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