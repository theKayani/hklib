package com.hk.lua;

import java.util.Arrays;

import com.hk.func.BiConsumer;
import com.hk.func.BiFunction;
import com.hk.lua.Lua.LuaMethod;

enum LuaLibraryCoroutine implements BiConsumer<Environment, LuaObject>, BiFunction<LuaInterpreter, LuaObject[], LuaObject>
{
	create() {
		@Override
		public LuaObject apply(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.FUNCTION);
			return new LuaThread(interp, (LuaFunction) args[0]);
		}
	},
	isyieldable() {
		@Override
		public LuaObject apply(LuaInterpreter interp, LuaObject[] args)
		{
			return LuaBoolean.valueOf(!interp.threads.isEmpty());
		}
	},
	resume() {
		@Override
		public LuaObject apply(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.THREAD);
			return args[0].getAsThread().resume(Arrays.copyOfRange(args, 1, args.length));
		}
	},
	running() {
		@Override
		public LuaObject apply(LuaInterpreter interp, LuaObject[] args)
		{
			if(interp.threads.isEmpty())
				return LuaNil.NIL;
			else
				return new LuaArgs(interp.threads.peek(), LuaBoolean.TRUE);
		}
	},
	status() {
		@Override
		public LuaObject apply(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.THREAD);
			return new LuaString(args[0].getAsThread().status());
		}
	},
	wrap() {
		@Override
		public LuaObject apply(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.FUNCTION);
			final LuaThread thread = new LuaThread(interp, (LuaFunction) args[0]);
			return new LuaFunction() {
				@Override
				LuaObject doCall(LuaObject[] args)
				{
					return thread.resume(args);
				}
			};
		}
	},
	yield() {
		@Override
		public LuaObject apply(LuaInterpreter interp, LuaObject[] args)
		{
			if(interp.threads.isEmpty())
				throw new LuaException("cannot yield, not in a thread");
			else
				return interp.threads.peek().yield(args);
		}
	};

	@Override
	public void accept(final Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
		{
			table.rawSet(new LuaString(name), Lua.newFunc(new LuaMethod() {
				@Override
				public LuaObject call(LuaObject[] args)
				{
					return apply(env.interp, args);
				}
			}));
		}
	}
}
