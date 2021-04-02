package com.hk.lua;

import java.security.SecureRandom;
import java.util.Random;

import com.hk.func.BiConsumer;
import com.hk.lua.Lua.LuaMethod;
import com.hk.math.MathUtil;
import com.hk.math.Rand;

enum LuaLibraryMath implements BiConsumer<Environment, LuaObject>, LuaMethod
{	
	abs() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			if(args[0].isInteger())
				return LuaInteger.valueOf(Math.abs(args[0].getInteger()));
			else
				return new LuaFloat(Math.abs(args[0].getFloat()));
		}
	},
	acos() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.acos(args[0].getFloat()));
		}
	},
	asin() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.asin(args[0].getFloat()));
		}
	},
	atan() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			if(args.length > 1)
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
				return new LuaFloat(Math.atan2(args[0].getFloat(), args[1].getFloat()));
			}
			else
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER);
				return new LuaFloat(Math.atan(args[0].getFloat()));
			}
		}
	},
	ceil() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return LuaInteger.valueOf((long) (args[0].getFloat() < 0 ? Math.floor(args[0].getFloat()) : Math.ceil(args[0].getFloat())));
		}
	},
	cos() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.cos(args[0].getFloat()));
		}
	},
	deg() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.toDegrees(args[0].getFloat()));
		}
	},
	exp() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.exp(args[0].getFloat()));
		}
	},
	floor() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return LuaInteger.valueOf((long) (args[0].getFloat() < 0 ? Math.ceil(args[0].getFloat()) : Math.floor(args[0].getFloat())));
		}
	},
	fmod() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
			if(args[0].isInteger() && args[1].isInteger())
				return LuaInteger.valueOf(Math.floorMod(args[0].getInteger(), args[1].getInteger()));
			else
				return new LuaFloat(Math.floorMod(args[0].getInteger(), args[1].getInteger()));
		}
	},
	huge() {
		public void accept(Environment env, LuaObject table)
		{
			table.rawSet(new LuaString(name()), LuaFloat.HUGE);
		}
	},
	log() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			if(args.length > 1)
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
				return new LuaFloat(Math.log(args[0].getFloat()) / Math.log(args[1].getFloat()));
			}
			else
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER);
				return new LuaFloat(Math.log(args[0].getFloat()));
			}
		}
	},
	max() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			LuaObject max = args[0];
			for(int i = 1; i < args.length; i++)
			{
				if(max.doLT(args[i]).getBoolean())
					max = args[i];
			}
			return max;
		}
	},
	maxinteger() {
		public void accept(Environment env, LuaObject table)
		{
			table.rawSet(new LuaString(name()), LuaInteger.valueOf(Long.MAX_VALUE));
		}
	},
	min() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			LuaObject min = args[0];
			for(int i = 1; i < args.length; i++)
			{
				if(args[i].doLT(min).getBoolean())
					min = args[i];
			}
			return min;
		}
	},
	mininteger() {		
		public void accept(Environment env, LuaObject table)
		{
			table.rawSet(new LuaString(name()), LuaInteger.valueOf(Long.MIN_VALUE));
		}
	},
	modf() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.FLOAT);
			double d = args[0].getFloat();
			if(Double.isNaN(d))
				return new LuaArgs(LuaFloat.NaN, LuaFloat.NaN);
			else if(Double.isInfinite(d))
				return new LuaArgs(new LuaFloat(d), LuaInteger.ZERO);
			else if((double) (long) d == d)
				return new LuaArgs(LuaInteger.valueOf((long) d), new LuaFloat(d % 1D));
			else
				return new LuaArgs(new LuaFloat(d < 0 ? Math.ceil(d) : Math.floor(d)), new LuaFloat(d % 1D));
		}
	},
	pi() {
		public void accept(Environment env, LuaObject table)
		{
			table.rawSet(new LuaString(name()), LuaFloat.PI);
		}
	},
	rad() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.toRadians(args[0].getFloat()));
		}
	},
	random() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			if(rng == null)
				rng = new Random();
			
			if(args.length > 1)
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
				double d = rng.nextDouble() * (args[1].getFloat() - args[0].getFloat() + 1) + args[0].getFloat();
				if(args[0].isInteger() && args[1].isInteger())
					return LuaInteger.valueOf((long) d);
				else
					return new LuaFloat(d);
			}
			else if(args.length == 1)
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER);
				if(args[0].isInteger())
					return LuaInteger.valueOf(args[0].getInteger() == 0L ? rng.nextLong() : (long) (rng.nextDouble() * ((int) args[0].getInteger() + 1)));
				else
					return new LuaFloat(rng.nextDouble() * args[0].getFloat() + 1);
			}
			else
			{
				return new LuaFloat(rng.nextDouble());
			}
		}
	},
	randomseed() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			byte[] seed = new byte[16];
			if(args.length >= 1)
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER);
				MathUtil.longToBytes(Double.doubleToRawLongBits(args[0].getFloat()), seed, 0);
				if(args.length > 1)
				{
					Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
					MathUtil.longToBytes(Double.doubleToRawLongBits(args[1].getFloat()), seed, 8);
				}
			}
			else
			{
				MathUtil.longToBytes(Rand.nextLong(), seed, 0);
				MathUtil.longToBytes(Rand.nextLong(), seed, 8);
			}
			rng = new SecureRandom(seed);

			return new LuaArgs(LuaInteger.valueOf(MathUtil.bytesToLong(0, seed)), LuaInteger.valueOf(MathUtil.bytesToLong(8, seed)));
		}
	},
	sin() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.sin(args[0].getFloat()));
		}
	},
	sqrt() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.sqrt(args[0].getFloat()));
		}
	},
	tan() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.tan(args[0].getFloat()));
		}
	},
	tointeger() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			
			if(args[0].isInteger())
				return args[0];
			else if(args[0].isString())
			{
				Number n = LuaString.getNumber(args[0].getString());

				if(n != null && n.doubleValue() == n.longValue())
					return LuaInteger.valueOf(n.longValue());
			}
			return LuaNil.NIL;
		}
	},
	type() {
		@Override
		public LuaObject call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.ANY);
			switch(args[0].type())
			{
			case FLOAT:
				return new LuaString("float");
			case INTEGER:
				return new LuaString("integer");
			default:
				return LuaNil.NIL;
			}
		}
	},
	ult() {
		@Override
		public LuaBoolean call(LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
			return LuaBoolean.valueOf(args[0].isInteger() && args[1].isInteger() && args[0].getInteger() < args[1].getInteger());
		}
	};

	@Override
	public LuaObject call(LuaObject[] args)
	{
		return null;
	}

	@Override
	public void accept(Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), Lua.newFunc(this));
	}
	
	private static Random rng;
}