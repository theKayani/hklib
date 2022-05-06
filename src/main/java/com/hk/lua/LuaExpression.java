package com.hk.lua;

import java.util.EmptyStackException;
import java.util.Stack;

class LuaExpression extends Lua.LuaValue
{
	private final String source;
	protected Object root;

	LuaExpression(String source)
	{
		this.source = source;
	}

	LuaExpression(String source, Lua.LuaValue value)
	{
		this.root = value;
		this.source = source;
	}

	LuaExpression collect(Object[] values)
	{
//		StringBuilder sb = new StringBuilder();
//		sb.append("[");
//		for(int i = 0; i < values.length; i += 2)
//		{
//			Object o = values[i];
//			if(o instanceof Integer)
//			{
//				String label = Tokenizer.label((Integer) o);
//				if(label != null)
//					sb.append(label);
//				else
//					sb.append(o);
//			}
//			else
//				sb.append(o);
//
//			if(i < values.length - 2)
//				sb.append(", ");
//		}
//		sb.append("]");
//		String str = sb.toString();
//		System.out.println(str);

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
					stack.push(value);
			}
			catch(EmptyStackException e)
			{
				throw new Error(e);
//				e.printStackTrace();
//				throw new LuaException(source, line, "Unexpected");
			}
		}

		root = stack.pop();
		return this;
	}

	@Override
	LuaObject evaluate(LuaInterpreter interp)
	{
		return ((Lua.LuaValue) root).evaluate(interp);
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
		protected LuaObject evaluate(LuaInterpreter interp)
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
		protected LuaObject evaluate(LuaInterpreter interp)
		{
			try
			{
				LuaObject v1 = getOp(interp, op1).evaluate(interp);
				if(v1.getBoolean())
					return getOp(interp, op2).evaluate(interp);
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
		protected LuaObject evaluate(LuaInterpreter interp)
		{
			try
			{
				LuaObject v1 = getOp(interp, op1).evaluate(interp);
				if(v1.getBoolean())
					return v1;
				else
					return getOp(interp, op2).evaluate(interp);
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
		protected LuaObject evaluate(LuaInterpreter interp)
		{
			try
			{
				return getOp(interp, op1).doBNOT(interp);
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
		protected LuaObject evaluate(LuaInterpreter interp)
		{
			try
			{
				return LuaBoolean.valueOf(!getOp(interp, op1).getBoolean());
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
		protected LuaObject evaluate(LuaInterpreter interp)
		{
			try
			{
				return getOp(interp, op1).doUnm(interp);
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
		protected LuaObject evaluate(LuaInterpreter interp)
		{
			try
			{
				return getOp(interp, op1).doLen(interp);
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

		protected LuaObject getOp(LuaInterpreter interp, Object op)
		{
			if(op instanceof Lua.LuaValue)
				return ((Lua.LuaValue) op).evaluate(interp);
			else
				throw new Error();
		}

		@Override
		protected LuaObject evaluate(LuaInterpreter interp)
		{
			LuaObject v1 = getOp(interp, op1);
			LuaObject v2 = getOp(interp, op2);
			try
			{
				switch(type)
				{
				case T_GREQ_THAN:
					return v2.doLE(interp, v1);
				case T_LSEQ_THAN:
					return v1.doLE(interp, v2);
				case T_GRTR_THAN:
					return v2.doLT(interp, v1);
				case T_LESS_THAN:
					return v1.doLT(interp, v2);
				case T_NEQUALS:
					return v1.doEQ(interp, v2).not();
				case T_EEQUALS:
					return v1.doEQ(interp, v2);
				case T_CONCAT:
					return v1.doConcat(interp, v2);
				case T_MINUS:
					return v1.doSub(interp, v2);
				case T_PLUS:
					return v1.doAdd(interp, v2);
				case T_TIMES:
					return v1.doMul(interp, v2);
				case T_DIVIDE:
					return v1.doDiv(interp, v2);
				case T_FLR_DIVIDE:
					return v1.doIDiv(interp, v2);
				case T_MODULO:
					return v1.doMod(interp, v2);
				case T_POW:
					return v1.doPow(interp, v2);
				case T_BAND:
					return v1.doBAND(interp, v2);
				case T_BOR:
					return v1.doBOR(interp, v2);
				case T_BXOR:
					return v1.doBXOR(interp, v2);
				case T_SHR:
					return v1.doSHR(interp, v2);
				case T_SHL:
					return v1.doSHL(interp, v2);
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