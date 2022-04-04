package com.hk.lua;

import java.security.SecureRandom;
import java.util.Random;

import com.hk.func.BiConsumer;
import com.hk.lua.Lua.LuaMethod;
import com.hk.math.PrimitiveUtil;
import com.hk.math.Rand;

/**
 * <p>LuaLibraryMath class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryMath implements BiConsumer<Environment, LuaObject>, LuaMethod
{	
	abs() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			if(args[0].isInteger())
				return LuaInteger.valueOf(Math.abs(args[0].getLong()));
			else
				return new LuaFloat(Math.abs(args[0].getDouble()));
		}
	},
	acos() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.acos(args[0].getDouble()));
		}
	},
	asin() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.asin(args[0].getDouble()));
		}
	},
	atan() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			if(args.length > 1)
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
				return new LuaFloat(Math.atan2(args[0].getDouble(), args[1].getDouble()));
			}
			else
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER);
				return new LuaFloat(Math.atan(args[0].getDouble()));
			}
		}
	},
	ceil() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return LuaInteger.valueOf((long) (args[0].getDouble() < 0 ? Math.floor(args[0].getDouble()) : Math.ceil(args[0].getDouble())));
		}
	},
	cos() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.cos(args[0].getDouble()));
		}
	},
	deg() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.toDegrees(args[0].getDouble()));
		}
	},
	exp() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.exp(args[0].getDouble()));
		}
	},
	floor() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return LuaInteger.valueOf((long) (args[0].getDouble() < 0 ? Math.ceil(args[0].getDouble()) : Math.floor(args[0].getDouble())));
		}
	},
	fmod() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
			long x = args[0].getLong();
			long y = args[1].getLong();
			long r = x - (x / y) * y;

			if(args[0].isInteger() && args[1].isInteger())
				return LuaInteger.valueOf(r);
			else
				return new LuaFloat(r);
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
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			if(args.length > 1)
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
				return new LuaFloat(Math.log(args[0].getDouble()) / Math.log(args[1].getDouble()));
			}
			else
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER);
				return new LuaFloat(Math.log(args[0].getDouble()));
			}
		}
	},
	max() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			LuaObject max = args[0];
			for(int i = 1; i < args.length; i++)
			{
				if(max.doLT(interp, args[i]).getBoolean())
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
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			LuaObject min = args[0];
			for(int i = 1; i < args.length; i++)
			{
				if(args[i].doLT(interp, min).getBoolean())
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
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.FLOAT);
			double d = args[0].getDouble();
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
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.toRadians(args[0].getDouble()));
		}
	},
	random() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Random rng = interp.getExtra(EXKEY_MATH_RNG, Random.class);
			if(rng == null)
			{
				rng = new Random();
				interp.setExtra(EXKEY_MATH_RNG, rng);
			}
			
			if(args.length > 1)
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
				double d = rng.nextDouble() * (args[1].getDouble() - args[0].getDouble() + 1) + args[0].getDouble();
				if(args[0].isInteger() && args[1].isInteger())
					return LuaInteger.valueOf((long) d);
				else
					return new LuaFloat(d);
			}
			else if(args.length == 1)
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER);
				if(args[0].isInteger())
					return LuaInteger.valueOf(args[0].getLong() == 0L ? rng.nextLong() : (long) (rng.nextDouble() * ((int) args[0].getLong() + 1)));
				else
					return new LuaFloat(rng.nextDouble() * args[0].getDouble() + 1);
			}
			else
			{
				return new LuaFloat(rng.nextDouble());
			}
		}
	},
	randomseed() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			byte[] seed = new byte[16];
			if(args.length >= 1)
			{
				Lua.checkArgs(name(), args, LuaType.NUMBER);
				PrimitiveUtil.longToBytes(Double.doubleToRawLongBits(args[0].getDouble()), seed, 0);
				if(args.length > 1)
				{
					Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
					PrimitiveUtil.longToBytes(Double.doubleToRawLongBits(args[1].getDouble()), seed, 8);
				}
			}
			else
			{
				PrimitiveUtil.longToBytes(Rand.nextLong(), seed, 0);
				PrimitiveUtil.longToBytes(Rand.nextLong(), seed, 8);
			}
			interp.setExtra(EXKEY_MATH_RNG, new SecureRandom(seed));

			return new LuaArgs(LuaInteger.valueOf(PrimitiveUtil.bytesToLong(0, seed)), LuaInteger.valueOf(PrimitiveUtil.bytesToLong(8, seed)));
		}
	},
	sin() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.sin(args[0].getDouble()));
		}
	},
	sqrt() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.sqrt(args[0].getDouble()));
		}
	},
	tan() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER);
			return new LuaFloat(Math.tan(args[0].getDouble()));
		}
	},
	tointeger() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
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
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
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
		public LuaBoolean call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.NUMBER, LuaType.NUMBER);
			return LuaBoolean.valueOf(args[0].isInteger() && args[1].isInteger() && args[0].getLong() < args[1].getLong());
		}
	};

	/** {@inheritDoc} */
	@Override
	public LuaObject call(LuaInterpreter interp, LuaObject[] args)
	{
		throw new Error();
	}

	/** {@inheritDoc} */
	@Override
	public void accept(Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), Lua.newFunc(this));
	}
	
	/** Constant <code>EXKEY_MATH_RNG="math.rng"</code> */
	public static final String EXKEY_MATH_RNG = "math.rng";
}
