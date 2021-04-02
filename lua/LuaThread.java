package com.hk.lua;

import java.util.concurrent.atomic.AtomicBoolean;

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
	
	public LuaObject resume(LuaObject[] args)
	{
		if(completed.get())
			return new LuaArgs(LuaNil.NIL, new LuaString("cannot resume dead coroutine"));

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
	
	public LuaObject yield(LuaObject[] args)
	{
		if(completed.get())
			return new LuaArgs(LuaNil.NIL, new LuaString("cannot yield dead coroutine"));

		if(thread == null)
			return new LuaArgs(LuaNil.NIL, new LuaString("cannot yield, hasn't resumed yet"));
		
		interp.threads.pop();
		thread.result = args;
		thread.done.set(true);

		do {}
		while(thread.done.get());
		
		return new LuaArgs(thread.result);
	}
	
	public String status()
	{
		return completed.get() ? "dead" : "suspended";
	}

	@Override
	public LuaBoolean rawEqual(LuaObject o)
	{
		return LuaBoolean.valueOf(o == this);
	}

	@Override
	public LuaObject rawLen()
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
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

	@Override
	public boolean getBoolean()
	{
		return true;
	}

	@Override
	public String getString()
	{
		return "thread: 0x" + Integer.toHexString(hashCode());
	}

	@Override
	public double getFloat()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public long getInteger()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public boolean isNil()
	{
		return false;
	}

	@Override
	public boolean isBoolean()
	{
		return false;
	}

	@Override
	public boolean isString()
	{
		return false;
	}

	@Override
	public boolean isNumber()
	{
		return false;
	}

	@Override
	public boolean isInteger()
	{
		return false;
	}

	@Override
	public boolean isTable()
	{
		return false;
	}

	@Override
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
		return true;
	}

	@Override
	public final LuaType type()
	{
		return LuaType.THREAD;
	}

	@Override
	public LuaBoolean doLE(LuaObject o)
	{
		if(name().equals(o.name()))
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	public LuaBoolean doLT(LuaObject o)
	{
		if(name().equals(o.name()))
			throw LuaErrors.INVALID_DUAL_COMPARISON.create(name());
		else
			throw LuaErrors.INVALID_COMPARISON.create(name(), o.name());
	}

	@Override
	LuaBoolean doEQ(LuaObject o)
	{
		return rawEqual(o);
	}

	@Override
	public LuaObject doConcat(LuaObject o)
	{
		throw LuaErrors.INVALID_CONCATENATE.create(name(), o.name());
	}

	@Override
	public LuaObject doAdd(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doSub(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doMul(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doDiv(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doIDiv(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doMod(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doPow(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBAND(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBOR(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBXOR(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doSHL(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doSHR(LuaObject o)
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doBNOT()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doUnm()
	{
		throw LuaErrors.INVALID_ARITHMETIC.create(name());
	}

	@Override
	public LuaObject doLen()
	{
		throw LuaErrors.INVALID_LENGTH.create(name());
	}

	@Override
	public LuaObject doIndex(LuaObject key)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	public void doNewIndex(LuaObject key, LuaObject value)
	{
		throw LuaErrors.INVALID_INDEX.create(name());
	}

	@Override
	public LuaObject doCall(LuaObject[] args)
	{
		throw LuaErrors.INVALID_CALL.create(name());
	}
	
	public LuaThread getAsThread()
	{
		return this;
	}

	@Override
	int code()
	{
		return T_THREAD;
	}

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
				LuaObject obj = function.doCall(args);
	
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
