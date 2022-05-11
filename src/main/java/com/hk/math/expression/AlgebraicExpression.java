package com.hk.math.expression;

import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import static com.hk.math.expression.ExpressionParser.*;

/**
 * This utility class is used for converting algebraic expressions
 * into solvable and pluggable solutions. Here are some examples of
 * algebraic expressions that are accepted:
 * <ul>
 *     <li><code>3.14</code></li>
 *     <li><code>1 + 2 + 3</code></li>
 *     <li><code>2 + 2 / 2</code></li>
 *     <li><code>4^3^2</code></li>
 *     <li><code>11 % 3</code> <i>(modulo)</i></li>
 *     <li><code>5 + 3(8 + 2)</code></li>
 * </ul>
 * Along with non-variable expressions, variable expressions are also
 * acceptable. The only requirement is that variables should be
 * plugged in. Here are some examples of variable expressions:
 * <ul>
 *     <li><code>a + b * c</code></li>
 *     <li><code>5x + 2</code></li>
 *     <li><code>x^2 + 2x + 1</code></li>
 *     <li><code>7x(5y + 10z)</code></li>
 *     <li><code>grade1 * 0.2 + grade2 * 0.2 + grade3 * 0.2 + final * 0.4</code></li>
 * </ul>
 *
 * @author theKayani
 */
public final class AlgebraicExpression implements DoubleSupplier
{
	/**
	 * This variable holds whether this parsed algebraic expression
	 * contains any variables that must be plugged in before calculation.
	 */
	public final boolean hasVariables;
	private final List<Object> resultList;
	private final Double result;
	private Map<String, Object> variables;

	public AlgebraicExpression(@NotNull String text) throws ExpressionFormatException
	{
		this(new StringReader(text));
	}

	/**
	 * Create a new algebraic expression from a reader.
	 *
	 * The reader is continuously read until finished or on error.
	 *
	 * @param reader a {@link java.io.Reader} object
	 * @throws com.hk.math.expression.ExpressionFormatException if any.
	 */
	public AlgebraicExpression(@NotNull Reader reader) throws ExpressionFormatException
	{
		this.resultList = parse(reader);
//		System.out.println("resultList = " + resultList);

		boolean hasVars = false;

		for(Object r : resultList)
		{
			if(r instanceof String)
			{
				hasVars = true;
				break;
			}
		}

		if(!hasVars)
			result = getResult();
		else
			result = null;

		hasVariables = hasVars;
		variables = null;
	}

	/**
	 * Assign a value to a variable to be plugged in the expression
	 * when acquiring the result.
	 *
	 * @param variable the name of the mathematical variable, Ex: <code>x</code>, <code>grade</code>
	 * @param value the numerical value to assign to the variable
	 * @return this
	 */
	@NotNull
	public AlgebraicExpression set(@NotNull String variable, double value)
	{
		if(variables == null)
			variables = new HashMap<>();

		variables.put(variable, value);
		return this;
	}

	/**
	 * Assign a value to a variable to be called anytime the variable
	 * is used in the expression when acquiring the result.
	 *
	 * For example, using the following expression, <code>a + a * a</code>,
	 * if we were to provide a supplier for the a variable as follows:
	 * <pre>
	 *     Supplier&lt;Double&gt; supp = new Supplier&lt;&gt;() {
	 *         int i = 1;
	 *         public Double get()
	 *         {
	 *             return (double) i++;
	 *         }
	 *     };</pre>
	 * Then set this supplier using this method, and the <code>a</code> variable:
	 * <pre>
	 *     expression.set("a", supp);</pre>
	 * Upon calling the {@link #getResult()} method will calculate
	 * the value of the expression using <b>order of operations</b>, the supplier
	 * will be called three times. First for the multiplication
	 * operation, and then for the addition operation. The actual
	 * expression, plugged in, would look a little something like:
	 * <code>3 + 1 * 2</code>, which will result in <code>5</code>.
	 *
	 * @param variable the name of the mathematical variable, Ex: <code>x</code>, <code>grade</code>
	 * @param supplier the supplier to provide a value to use
	 * @return this
	 */
	@NotNull
	public AlgebraicExpression set(@NotNull String variable, @NotNull Supplier<? extends Number> supplier)
	{
		if(variables == null)
			variables = new HashMap<>();

		variables.put(variable, supplier);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getAsDouble()
	{
		return getResult();
	}

	/**
	 * This method will attempt to calculate the result with
	 * predetermined variables set using the <code>set</code> methods.
	 *
	 * If the expression has no variables, a pre-calculated value is
	 * returned. You can also use the {@link #getResult(Map)} method,
	 * which can accept a map of variables and their assigned values.
	 *
	 * @return the calculated result of this algebraic expression
	 */
	public double getResult()
	{
		return getResult(variables == null ? Collections.emptyMap() : variables);
	}

	/**
	 * This method will attempt to calculate the result and provide
	 * the caller with said result. There is one important thing to
	 * note which is, if this expression has no variables, then this
	 * method returns a pre-calculated value.
	 *
	 * @param variables The map of variables and their numerical
	 *                     equivalents to be used in the calculation
	 * @return the calculated result of this algebraic expression
	 */
	public double getResult(@NotNull Map<String, ?> variables)
	{
		if(result != null)
			return result;

		List<Object> resultList = new ArrayList<>(this.resultList);
		byte type;
		double a, b, c;
		for(int i = 0; i < resultList.size(); i++)
		{
			if(resultList.get(i) instanceof Byte)
			{
				type = (byte) resultList.get(i);

				if(type == T_OP_NEGATE)
				{
					c = getDouble(variables, resultList.remove(i - 1));
					resultList.set(i - 1, -c);
					i--;
				}
				else
				{
					Object o1 = resultList.remove(i - 2);
					Object o2 = resultList.remove(i - 2);
					a = getDouble(variables, o1);
					b = getDouble(variables, o2);

					switch (type)
					{
						case T_OP_ADD:
							c = a + b;
							break;
						case T_OP_SUBTRACT:
							c = a - b;
							break;
						case T_OP_MULTIPLY:
							c = a * b;
							break;
						case T_OP_DIVIDE:
							c = a / b;
							break;
						case T_OP_MOD:
							c = a % b;
							break;
						case T_OP_POW:
							c = Math.pow(a, b);
							break;
						default:
							throw new ExpressionFormatException("Unexpected value: " + type);
					}
					resultList.set(i - 2, c);

					i -= 2;
				}
			}
		}

		return getDouble(variables, resultList.get(0));
	}

	private double getDouble(Map<String, ?> variables, Object variable)
	{
		if(variable instanceof Number)
			return ((Number) variable).doubleValue();
		else if(variable instanceof String)
		{
			Object o = variables.get(variable);

			if (o instanceof Number)
				return ((Number) o).doubleValue();
			else if (o instanceof Supplier)
			{
				o = ((Supplier<?>) o).get();

				if (o instanceof Number)
					return ((Number) o).doubleValue();
			}

			String s = variables.isEmpty() ? "" : " in map " + variables;
			throw new ExpressionFormatException("No value for variable '" + variable + "'" + s);
		}

		throw new AssertionError("not possible");
	}

	private List<Object> parse(Reader reader)
	{
		ExpressionParser parser = new ExpressionParser(reader);
		Stack<Byte> ops = new Stack<>();
		List<Object> res = new ArrayList<>();
		boolean hasValue = false, ra;
		byte lastVal = 0;

		while(parser.next())
		{
			if(hasValue && (parser.type & T_OPERATOR) != 0)
			{
				hasValue = false;
				ra = parser.type == T_OP_POW;
				while(!ops.isEmpty() && (!ra && precedence(parser.type) <= precedence(ops.peek()) || ra && precedence(parser.type) < precedence(ops.peek())))
					res.add(ops.pop());

				ops.add(parser.type);
			}
			else
			{
				byte type;
				switch(parser.type)
				{
					case T_OP_SUBTRACT:
						parser.type = T_OP_NEGATE;

						while(!ops.isEmpty())
						{
							type = ops.peek();
							ra = type == T_OP_POW;
							if(!ra && precedence(parser.type) <= precedence(type) || ra && precedence(parser.type) == precedence(type))
								res.add(ops.pop());
							else
								break;
						}
						ops.push(parser.type);
						break;
					case T_OPEN_PAR:
						if(hasValue)
							ops.add(T_OP_MULTIPLY);

						lastVal = 0;
						ops.add(parser.type);

						hasValue = false;
						break;
					case T_CLOSE_PAR:
						boolean broken = false;
						while(!ops.isEmpty())
						{
							type = ops.pop();

							if(type == T_OPEN_PAR)
							{
								broken = true;
								break;
							}
							res.add(type);
						}

						if(!broken)
							throw new ExpressionFormatException("Expected matching open parenthesis");
						hasValue = true;

						lastVal = T_CLOSE_PAR;
						break;
					case T_VARIABLE:
					case T_NUMBER:
						if(hasValue)
						{
							if(parser.type == lastVal ||
								parser.type == T_NUMBER && lastVal == T_VARIABLE)
								throw new ExpressionFormatException("Unexpected token: " + parser.token);

							ops.push(T_OP_MULTIPLY);
						}

						res.add(parser.value);
						lastVal = parser.type;
						hasValue = true;
						break;
					default:
						throw new ExpressionFormatException("Unexpected token: " + parser.token);
				}
			}
		}
		parser.close();

		byte type;
		while(!ops.isEmpty())
		{
			type = ops.pop();
			if(type == T_OPEN_PAR)
				throw new ExpressionFormatException("Expected matching closing parenthesis");
			res.add(type);
		}

		if(res.isEmpty())
			throw new ExpressionFormatException("No expression found from text");

		return res;
	}

	private static int precedence(byte type)
	{
		switch(type)
		{
			case T_OP_ADD:
			case T_OP_SUBTRACT:
				return 1;
			case T_OP_MULTIPLY:
			case T_OP_DIVIDE:
			case T_OP_MOD:
				return 2;
			case T_OP_NEGATE:
				return 3;
			case T_OP_POW:
				return 4;
			default:
				return 0;
		}
	}
}
