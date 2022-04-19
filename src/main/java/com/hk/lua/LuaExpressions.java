package com.hk.lua;

import java.util.ArrayList;
import java.util.List;

import com.hk.collections.lists.ListUtil;

class LuaExpressions
{
	private final LuaExpression[] exps;
	private final boolean fixed;

	LuaExpressions(LuaExpression exp)
	{
		exps = new LuaExpression[] { exp };
		fixed = exp.isCall();
	}

	LuaExpressions(LuaExpression[] exps)
	{
		this.exps = exps;
		fixed = !exps[exps.length - 1].isCall();
	}

	LuaObject[] collect(LuaInterpreter interp, LuaObject... first)
	{
		if(fixed)
		{
			LuaObject[] vals = new LuaObject[first.length + exps.length];
			System.arraycopy(first, 0, vals, 0, first.length);

			for(int i = 0; i < exps.length; i++)
				vals[first.length + i] = exps[i].evaluate(interp);

			return vals;
		}
		else
		{
			List<LuaObject> vals = new ArrayList<>(exps.length + 1);
			int i;
			ListUtil.addAll(vals, first);

			for(i = 0; i < exps.length - 1; i++)
				vals.add(exps[i].evaluate(interp));

			LuaObject e = exps[i].evaluate(interp);
			if(e instanceof LuaArgs)
				ListUtil.addAll(vals, ((LuaArgs) e).objs);
			else
				vals.add(e);

			return vals.toArray(new LuaObject[0]);
		}
	}
}