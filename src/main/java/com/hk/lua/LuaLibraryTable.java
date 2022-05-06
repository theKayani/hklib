package com.hk.lua;

import com.hk.lua.Lua.LuaMethod;

import java.util.function.BiConsumer;

/**
 * <p>LuaLibraryTable class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryTable implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	concat() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.TABLE);
			LuaObject sep = args.length > 1 ? args[1] : new LuaString("");
			LuaObject i = args.length > 2 ? args[2] : LuaInteger.ONE;
			LuaObject j = args.length > 3 ? args[3] : args[0].rawLen();
			StringBuilder sb = new StringBuilder();

			while(i.doLE(interp, j).getBoolean())
			{
				sb.append(args[0].rawGet(i));

				if(i.doLT(interp, j).getBoolean())
					sb.append(sep.getString());

				i = i.doAdd(interp, LuaInteger.ONE);
			}

			return new LuaString(sb.toString());
		}
	},
	insert() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.TABLE, LuaType.ANY);
			LuaObject len = args[0].rawLen();
			LuaObject pos = args.length > 2 ? args[1] : len.doAdd(interp, LuaInteger.ONE);
			LuaObject value = args.length > 2 ? args[2] : args[1];

			while(pos.doLE(interp, len).getBoolean())
			{
				args[0].rawSet(len.doAdd(interp, LuaInteger.ONE), args[0].rawGet(len));
				len = len.doSub(interp, LuaInteger.ONE);
			}

			args[0].rawSet(pos, value);
			return LuaNil.NIL;
		}
	},
	move() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.TABLE, LuaType.NUMBER, LuaType.NUMBER, LuaType.NUMBER);
			LuaObject a1 = args[0];
			LuaObject f = args[1];
			LuaObject e = args[2];
			LuaObject t = args[3];
			LuaObject a2 = args.length > 4 ? args[4] : a1;

			for(long l = f.getLong(); l <= e.getLong(); l++)
			{
				a2.rawSet(t, a1.rawGet(LuaInteger.valueOf(l)));

				t = t.doAdd(interp, LuaInteger.ONE);
			}
			return a2;
		}
	},
	pack() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			LuaTable tbl = new LuaTable();
			long len = 1;
			for(LuaObject arg : args)
				tbl.rawSet(LuaInteger.valueOf(len++), arg);

			tbl.rawSet(new LuaString("n"), LuaInteger.valueOf(args.length));
			return tbl;
		}
	},
	remove() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.TABLE);
			LuaObject len = args[0].rawLen();
			LuaObject pos = args.length > 1 ? args[1] : len;
			LuaObject curr = args[0].rawGet(pos);

			while(pos.doLE(interp, len).getBoolean())
			{
				pos = pos.doAdd(interp, LuaInteger.ONE);
				args[0].rawSet(pos.doSub(interp, LuaInteger.ONE), args[0].rawGet(pos));
			}
			return curr;
		}
	},
	sort() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.TABLE);
			LuaObject tbl = args[0];
			LuaObject comp = args.length > 1 ? args[1] : null;

			long len = tbl.rawLen().getLong();
			boolean swap;
			for(long i = 1; i < len; i++)
			{
				for(long j = i; j <= len; j++)
				{
					if(comp == null)
					{
						swap = !tbl.rawGet(LuaInteger.valueOf(i)).doLT(interp, tbl.rawGet(LuaInteger.valueOf(j))).getBoolean();
					}
					else
					{
						LuaObject o = comp.doCall(interp, new LuaObject[] { tbl.rawGet(LuaInteger.valueOf(i)), tbl.rawGet(LuaInteger.valueOf(j)) });

						swap = !o.evaluate(interp).getBoolean();
					}

					if(swap)
					{
						LuaObject tmp = tbl.rawGet(LuaInteger.valueOf(i));
						tbl.rawSet(LuaInteger.valueOf(i), tbl.rawGet(LuaInteger.valueOf(j)));
						tbl.rawSet(LuaInteger.valueOf(j), tmp);
					}
				}	
			}
			return LuaNil.NIL;
		}
	},
	unpack() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.TABLE);
			LuaObject list = args[0];
			LuaObject i = args.length > 1 ? args[1] : LuaInteger.ONE;
			LuaObject j = args.length > 2 ? args[2] : list.rawLen();
			LuaObject[] res = new LuaObject[j.doSub(interp, i).getInt() + 1];
			for(int k = 0; k < res.length; k++)
			{
				res[k] = list.rawGet(i);
				i = i.doAdd(interp, LuaInteger.ONE);
			}
			return new LuaArgs(res);
		}
	};

	/** {@inheritDoc} */
	@Override
	public void accept(Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), Lua.newMethod(this));
	}
}