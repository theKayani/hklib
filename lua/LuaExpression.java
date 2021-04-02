package com.hk.lua;

import java.util.EmptyStackException;
import java.util.Stack;

class LuaExpression extends Lua.LuaValue
{
	protected final LuaInterpreter interp;
	private final String source;
	protected Object root;
	
	LuaExpression(LuaInterpreter interp)
	{
		this.interp = interp;
		
		source = interp.currSource;
	}
	
	LuaExpression(LuaInterpreter interp, Lua.LuaValue value)
	{
		this.interp = interp;
		this.root = value;

		source = interp.currSource;
	}
	
	LuaExpression collect(Object[] values)
	{		
		if(root != null)
			throw new Error();
		
		Stack<Object> stack = new Stack<>();
		Object value;
		int line;
		
		for(int i = 0; i < values.length; i += 2)
		{
			value = values[i];
			line = (Integer) values[i + 1];

			try
			{
				if(value instanceof Integer)
				{
					switch((Integer) value)
					{
					case T_AND:
						stack.push(new AndNode(stack.pop(), stack.pop(), line));
						break;
					case T_OR:
						stack.push(new OrNode(stack.pop(), stack.pop(), line));
						break;
					case T_NOT:
						stack.push(new NotNode(stack.pop(), line));
						break;
					case T_NEGATE:
						stack.push(new NegateNode(stack.pop(), line));
						break;
					case T_POUND:
						stack.push(new LengthNode(stack.pop(), line));
						break;
					case T_UBNOT:
						stack.push(new UnaryNotNode(stack.pop(), line));
						break;
					case T_VARARGS:
						stack.push(new VarargsNode(line));
						break;
					default:
						stack.push(new Node(stack.pop(), stack.pop(), (Integer) value, line));
					}
				}
				else
					stack.push((Lua.LuaValue) value);
			}
			catch(EmptyStackException e)
			{
				throw new Error(e);
//				throw new LuaException(source, line, "Unexpected");
			}
		}
		
		root = stack.pop();
		return this;
	}

	@Override
	LuaObject evaluate()
	{
		return ((Lua.LuaValue) root).evaluate();
	}
	
	boolean isCall()
	{
		return root instanceof LuaLocation && ((LuaLocation) root).isCall() || root instanceof VarargsNode;
	}
	
	private class VarargsNode extends Node
	{
		private VarargsNode(int line)
		{
			super(null, null, T_VARARGS, line);
		}

		@Override
		protected LuaObject evaluate()
		{
			return interp.env.varargs;
		}
	}
	
	private class AndNode extends Node
	{
		private AndNode(Object op2, Object op1, int line)
		{
			super(op2, op1, T_AND, line);
		}

		@Override
		protected LuaObject evaluate()
		{
			try
			{
				LuaObject v1 = getOp(op1);
				if(v1.getBoolean())
					return getOp(op2);
				else
					return v1;
			}
			catch(LuaException e)
			{
				throw new LuaException(source, line, e.getLocalizedMessage()).internal();
			}
		}
	}
	
	private class OrNode extends Node
	{
		private OrNode(Object op2, Object op1, int line)
		{
			super(op2, op1, T_OR, line);
		}

		@Override
		protected LuaObject evaluate()
		{
			try
			{
				LuaObject v1 = getOp(op1);
				if(v1.getBoolean())
					return v1;
				else
					return getOp(op2);
			}
			catch(LuaException e)
			{
				throw new LuaException(source, line, e.getLocalizedMessage()).internal();
			}
		}
	}
	
	private class UnaryNotNode extends Node
	{
		private UnaryNotNode(Object op1, int line)
		{
			super(null, op1, T_UBNOT, line);
		}

		@Override
		protected LuaObject evaluate()
		{
			try
			{
				return getOp(op1).doBNOT();
			}
			catch(LuaException e)
			{
				throw new LuaException(source, line, e.getLocalizedMessage()).internal();
			}
		}
	}
	
	private class NotNode extends Node
	{
		private NotNode(Object op1, int line)
		{
			super(null, op1, T_NOT, line);
		}

		@Override
		protected LuaObject evaluate()
		{
			try
			{
				return LuaBoolean.valueOf(!getOp(op1).getBoolean());
			}
			catch(LuaException e)
			{
				throw new LuaException(source, line, e.getLocalizedMessage()).internal();
			}
		}
	}
	
	private class NegateNode extends Node
	{
		private NegateNode(Object op1, int line)
		{
			super(null, op1, T_NEGATE, line);
		}
		
		@Override
		protected LuaObject evaluate()
		{
			try
			{
				return getOp(op1).doUnm();
			}
			catch(LuaException e)
			{
				throw new LuaException(source, line, e.getLocalizedMessage()).internal();
			}
		}
	}
	
	private class LengthNode extends Node
	{
		private LengthNode(Object op1, int line)
		{
			super(null, op1, T_POUND, line);
		}
		
		@Override
		protected LuaObject evaluate()
		{
			try
			{
				return getOp(op1).doLen();
			}
			catch(LuaException e)
			{
				throw new LuaException(source, line, e.getLocalizedMessage()).internal();
			}
		}
	}
	
	private class Node extends Lua.LuaValue
	{
		protected final Object op1, op2;
		protected final int type, line;
		
		private Node(Object op2, Object op1, int type, int line)
		{
			this.op2 = op2;
			this.op1 = op1;
			this.type = type;
			this.line = line;
		}
		
		protected LuaObject getOp(Object op)
		{
			if(op instanceof Lua.LuaValue)
				return ((Lua.LuaValue) op).evaluate();
			else
				throw new Error();
		}
		
		@Override
		protected LuaObject evaluate()
		{
			LuaObject v1 = getOp(op1);
			LuaObject v2 = getOp(op2);
			try
			{
				switch(type)
				{
				case T_GREQ_THAN:
					return v2.doLE(v1);
				case T_LSEQ_THAN:
					return v1.doLE(v2);
				case T_GRTR_THAN:
					return v2.doLT(v1);
				case T_LESS_THAN:
					return v1.doLT(v2);
				case T_NEQUALS:
					return v1.doEQ(v2).not();
				case T_EEQUALS:
					return v1.doEQ(v2);
				case T_CONCAT:
					return v1.doConcat(v2);
				case T_MINUS:
					return v1.doSub(v2);
				case T_PLUS:
					return v1.doAdd(v2);
				case T_TIMES:
					return v1.doMul(v2);
				case T_DIVIDE:
					return v1.doDiv(v2);
				case T_FLR_DIVIDE:
					return v1.doIDiv(v2);
				case T_MODULO:
					return v1.doMod(v2);
				case T_POW:
					return v1.doPow(v2);
				case T_BAND:
					return v1.doBAND(v2);
				case T_BOR:
					return v1.doBOR(v2);
				case T_BXOR:
					return v1.doBXOR(v2);
				case T_SHR:
					return v1.doSHR(v2);
				case T_SHL:
					return v1.doSHL(v2);
				default:
					throw new Error();
				}
			}
			catch(LuaException e)
			{
				throw new LuaException(source, line, e.getLocalizedMessage()).internal();
			}
		}
	}
}
