package com.hk.lua;

class LuaString extends LuaMetatable
{
	private final String value;

	LuaString(CharSequence value)
	{
		this.value = value.toString();
		metatable = LuaLibraryString.stringMetatable;
	}

	public LuaObject rawLen()
	{
		return doLen();
	}

	@Override
	public LuaBoolean rawEqual(LuaObject o)
	{
		return LuaBoolean.valueOf(o.isString() && value.equals(o.getString()));
	}

	@Override
	public LuaObject rawGet(LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	public void rawSet(LuaObject key, LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}
	
	public boolean getBoolean()
	{
		return true;
	}
	
	public String getString()
	{
		return value;
	}
	
	public double getFloat()
	{
		Number num = getNumber(value);
		if(num == null)
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
		else
			return num.doubleValue();
	}
	
	public long getInteger()
	{
		Number num = getNumber(value);
		if(num == null || !(num instanceof Long))
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
		else
			return num.longValue();
	}
	
	public boolean isNil()
	{
		return false;
	}
	
	public boolean isBoolean()
	{
		return false;
	}
	
	public boolean isString()
	{
		return true;
	}
	
	public boolean isNumber()
	{
		return getNumber(value) != null;
	}
	
	public boolean isInteger()
	{
//		return getNumber(value) instanceof Long;
		return false;
	}
	
	public boolean isTable()
	{
		return false;
	}
	
	public boolean isFunction()
	{
		return false;
	}

	@Override
	public boolean isUserdata()
	{
		return false;
	}

	@Override
	public boolean isThread()
	{
		return false;
	}

	@Override
	LuaBoolean doLE(LuaObject o)
	{
		switch(o.code())
		{
		case T_STRING:
			return LuaBoolean.valueOf(value.compareTo(o.getString()) <= 0);
		default:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	@Override
	LuaBoolean doLT(LuaObject o)
	{
		switch(o.code())
		{
		case T_STRING:
			return LuaBoolean.valueOf(value.compareTo(o.getString()) < 0);
		default:
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	LuaBoolean doEQ(LuaObject o)
	{
		return LuaBoolean.valueOf(o.isString() && value.equals(o.getString()));
	}

	@Override
	LuaObject doConcat(LuaObject o)
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
	
	LuaObject doAdd(LuaObject o)
	{
		return new LuaFloat(getFloat() + o.getFloat());
	}

	LuaObject doSub(LuaObject o)
	{
		return new LuaFloat(getFloat() - o.getFloat());
	}

	LuaObject doMul(LuaObject o)
	{
		return new LuaFloat(getFloat() * o.getFloat());
	}

	LuaObject doDiv(LuaObject o)
	{
		return new LuaFloat(getFloat() / o.getFloat());
	}

	@Override
	LuaObject doIDiv(LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() / o.getInteger());
	}

	@Override
	LuaObject doMod(LuaObject o)
	{
		return new LuaFloat(getFloat() % o.getFloat());
	}

	LuaObject doPow(LuaObject o)
	{
		return new LuaFloat(Math.pow(getFloat(), o.getFloat()));
	}

	@Override
	LuaObject doBAND(LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() & o.getInteger());
	}

	@Override
	LuaObject doBOR(LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() | o.getInteger());
	}

	@Override
	LuaObject doBXOR(LuaObject o)
	{
		return LuaInteger.valueOf(getInteger() ^ o.getInteger());
	}

	@Override
	LuaObject doSHL(LuaObject o)
	{
		switch((int) o.getInteger())
		{
		case 64:
			return LuaInteger.ZERO;
		default:
			return LuaInteger.valueOf(getInteger() << o.getInteger());
		}
	}

	@Override
	LuaObject doSHR(LuaObject o)
	{
		switch((int) o.getInteger())
		{
		case 64:
			return LuaInteger.ZERO;
		default:
			return LuaInteger.valueOf(getInteger() >> o.getInteger());
		}
	}

	@Override
	LuaObject doBNOT()
	{
		return LuaInteger.valueOf(~getInteger());
	}

	LuaObject doUnm()
	{
		return new LuaFloat(-getFloat());
	}

	@Override
	LuaObject doLen()
	{
		return LuaInteger.valueOf(value.length());
	}

	@Override
	LuaObject doIndex(LuaObject key)
	{
		return metatable.doIndex(key);
	}

	@Override
	void doNewIndex(LuaObject key, LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	LuaObject doCall(LuaObject[] args)
	{
		throw LuaErrors.INVALID_CALL.create(name());
	}
	
	public void setMetatable(LuaObject metatable)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	int code()
	{
		return T_STRING;
	}
	
	public LuaType type()
	{
		return LuaType.STRING;
	}
	
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
					
					fraction += n / (double) place;
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
					
					fraction += n / (double) place;
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
