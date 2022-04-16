package com.hk.lua;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>LuaThread class.</p>
 *
 * @author theKayani
 */
public class LuaThread extends LuaObject
{
	private final LuaInterpreter interp;
	private final LuaFunction function;
	private final AtomicBoolean completed;
	private LuaRunnable thread;

	LuaThread(LuaInterpreter interp, LuaFunction function)
	{
		this.interp = interp;
		this.function = function;
		completed = new AtomicBoolean();
	}
	
	/**
	 * <p>resume.</p>
	 *
	 * @param args an array of {@link com.hk.lua.LuaObject} objects
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject resume(LuaObject[] args)
	{
		if(completed.get())
			return Lua.wrapErr("cannot resume dead coroutine");

		if(thread == null)
		{
			thread = new LuaRunnable(args);
			thread.start();
		}
		else
		{
			thread.result = args;
			thread.done.set(false);
		}
		interp.threads.push(this);
		
		do {}
		while(!thread.done.get());
		
		LuaObject[] tmp = new LuaObject[thread.result.length + 1];
		tmp[0] = LuaBoolean.TRUE;
		System.arraycopy(thread.result, 0, tmp, 1, thread.result.length);
		
		return new LuaArgs(tmp);
	}
	
	/**
	 * <p>yield.</p>
	 *
	 * @param args an array of {@link com.hk.lua.LuaObject} objects
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public LuaObject yield(LuaObject[] args)
	{
		if(completed.get())
			return Lua.wrapErr("cannot yield dead coroutine");

		if(thread == null)
			return Lua.wrapErr("cannot yield, hasn't resumed yet");

		interp.threads.pop();
		thread.result = args;
		thread.done.set(true);

		do {}
		while(thread.done.get());
		
		return new LuaArgs(thread.result);
	}
	
	/**
	 * <p>status.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String status()
	{
		return completed.get() ? "dead" : "suspended";
	}

	/** {@inheritDoc} */
	@Override
	public boolean rawEqual(LuaObject o)
	{
		return o == this;
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject rawLen()
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
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
		return "thread: 0x" + Integer.toHexString(hashCode());
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
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public final LuaType type()
	{
		return LuaType.THREAD;
	}

	/** {@inheritDoc} */
	@Override
	public LuaBoolean doLE(LuaInterpreter interp, LuaObject o)
	{
		if(name().equals(o.name()))
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaBoolean doLT(LuaInterpreter interp, LuaObject o)
	{
		if(name().equals(o.name()))
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doEQ(LuaInterpreter interp, LuaObject o)
	{
		return LuaBoolean.valueOf(rawEqual(o));
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doConcat(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_CONCATENATE.create(name(), o.name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doAdd(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doSub(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doMul(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doDiv(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doIDiv(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doMod(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doPow(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doBAND(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doBOR(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doBXOR(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doSHL(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doSHR(LuaInterpreter interp, LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doBNOT(LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doUnm(LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doLen(LuaInterpreter interp)
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doIndex(LuaInterpreter interp, LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public void doNewIndex(LuaInterpreter interp, LuaObject key, LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject doCall(LuaInterpreter interp, LuaObject[] args)
	{
		throw LuaErrors.INVALID_CALL.create(name());
	}
	
	/**
	 * <p>getAsThread.</p>
	 *
	 * @return a {@link com.hk.lua.LuaThread} object
	 */
	public LuaThread getAsThread()
	{
		return this;
	}

	@Override
	int code()
	{
		return T_THREAD;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return System.identityHashCode(this);
	}

	private class LuaRunnable extends Thread
	{
		private final LuaObject[] args;
		private final AtomicBoolean done;
		private LuaObject[] result;

		LuaRunnable(LuaObject[] args)
		{
			this.args = args;
			setDaemon(true);
			done = new AtomicBoolean();
		}

		@Override
		public void run()
		{
			try
			{
				LuaObject obj = function.doCall(interp, args);
	
				if(obj.isNil())
					result = new LuaObject[] { LuaNil.NIL };
				else if(obj instanceof LuaArgs)
					result = ((LuaArgs) obj).objs;
				else
					result = new LuaObject[] { obj };
			}
			catch(LuaException ex)
			{
				result = new LuaObject[] { LuaNil.NIL, new LuaString(ex.getLocalizedMessage()) };
			}

			interp.threads.pop();
			LuaThread.this.completed.set(true);
			done.set(true);
		}
	}
}
