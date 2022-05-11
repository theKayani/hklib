package com.hk.lua;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

class LuaTable extends LuaMetatable
{
	protected final Map<LuaObject, LuaObject> map;

	LuaTable()
	{
//		this(new java.util.TreeMap<>());
//		this(new java.util.HashMap<>());
		this(new java.util.LinkedHashMap<>());
	}

	LuaTable(Map<LuaObject, LuaObject> map)
	{
		this.map = map;
	}

	/** {@inheritDoc}
	 * @param o*/
	@Override
	public boolean rawEqual(@NotNull LuaObject o)
	{
		return o == this;
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaObject rawLen()
	{
		LuaObject a, b;
		int count = 0;

		while(true)
		{
			a = rawGet(LuaInteger.valueOf(count + 1));
			b = rawGet(LuaInteger.valueOf(count + 2));
			if(a.isNil() && b.isNil())
				break;

			count++;
		}

		return LuaInteger.valueOf(count);
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaObject rawGet(@NotNull LuaObject key)
	{
		LuaObject obj = map.get(key);
		return obj == null ? LuaNil.NIL : obj;
	}

	/** {@inheritDoc}
	 * @param key
	 * @param value*/
	@Override
	public void rawSet(@NotNull LuaObject key, @NotNull LuaObject value)
	{
		if(key.isNil() || (key.isNumber() && Double.isNaN(key.getDouble())))
			return;

		if(value.isNil())
			map.remove(key);
		else
			map.put(key, value);
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
		if(interp != null)
		{
			LuaObject res = event(interp, "tostring", LuaNil.NIL);
			if (res != null)
				return res.getString();
		}

		return "table: 0x" + Integer.toHexString(hashCode()) + Integer.toHexString(System.identityHashCode(map));
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
		return true;
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
		LuaObject res = event(interp, "le", o);
		if(res != null)
			return LuaBoolean.valueOf(res.getBoolean());
		else
		{
			res = event(interp, "lt", o, o, this);
			if(res != null)
				return LuaBoolean.valueOf(!res.getBoolean());
			else
				throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
		}
	}

	@Override
	LuaBoolean doLT(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "lt", o);
		if(res != null)
			return LuaBoolean.valueOf(res.getBoolean());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaObject doConcat(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "concat", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_CONCATENATE.create(name());
	}

	@Override
	LuaObject doAdd(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "add", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSub(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "sub", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMul(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "mul", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doDiv(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "div", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doIDiv(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "idiv", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doMod(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "mod", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doPow(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "pow", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBAND(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "band", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "bor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBXOR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "bxor", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHL(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "shl", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doSHR(@Nullable LuaInterpreter interp, @NotNull LuaObject o)
	{
		LuaObject res = event(interp, "shr", o);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doBNOT(@Nullable LuaInterpreter interp)
	{
		LuaObject res = event(interp, "bnot", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doUnm(@Nullable LuaInterpreter interp)
	{
		LuaObject res = event(interp, "unm", LuaNil.NIL);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	LuaObject doLen(@Nullable LuaInterpreter interp)
	{
		LuaObject res = event(interp, "len", LuaNil.NIL);
		if(res != null)
			return res;
		else
			return rawLen();
	}

	@Override
	LuaObject doIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key)
	{
		if(map.containsKey(key))
		{
			LuaObject ky = map.get(key);
			if(ky != null && !ky.isNil())
				return ky;
		}

		LuaObject func = getHandler("index", LuaNil.NIL);
		if(func != null)
		{
			Objects.requireNonNull(interp);
			if(func.isFunction())
				return func.doCall(interp, new LuaObject[] { this, key }).evaluate(interp);
			else
				return func.doIndex(interp, key);
		}
		else
			return LuaNil.NIL;
	}

	@Override
	void doNewIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key, @NotNull LuaObject value)
	{
		LuaObject v = map.get(key);
		if(v == null || v.isNil())
		{
			LuaObject func = getHandler("newindex", LuaNil.NIL);
			if(func != null)
			{
				if(func.isFunction())
					func.doCall(interp, new LuaObject[] { this, key, value });
				else
					func.doNewIndex(interp, key, value);
			}
		}
		map.put(key, value);
	}

	@Override
	LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
	{
		LuaObject[] tmp = new LuaObject[args.length + 1];
		tmp[0] = this;
		System.arraycopy(args, 0, tmp, 1, args.length);

		LuaObject res = event(interp, "call", LuaNil.NIL, tmp);
		if(res != null)
			return res;
		else
			throw LuaErrors.INVALID_CALL.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public Set<LuaObject> getIndicies()
	{
		return map.keySet();
	}

	/** {@inheritDoc} */
	@Override
	public Set<Map.Entry<LuaObject, LuaObject>> getEntries()
	{
		return map.entrySet();
	}

	@Override
	int code()
	{
		return T_TABLE;
	}

	/** {@inheritDoc}
	 * @return*/
	@Override
	public @NotNull LuaType type()
	{
		return LuaType.TABLE;
	}

	/** {@inheritDoc} */
	@NotNull
	@Override
	public String toString()
	{
		return map.toString();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return System.identityHashCode(this);
	}
}