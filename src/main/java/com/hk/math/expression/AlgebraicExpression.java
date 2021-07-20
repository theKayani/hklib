package com.hk.math.expression;

/**
 * <p>AlgebraicExpression class.</p>
 *
 * @author theKayani
 */
public class AlgebraicExpression
{
	public Double result = null;

	/**
	 * <p>Constructor for AlgebraicExpression.</p>
	 *
	 * @param txt a {@link java.lang.String} object
	 * @throws com.hk.math.expression.ExpressionFormatException if any.
	 */
	public AlgebraicExpression(String txt) throws ExpressionFormatException
	{
		try
		{
			result = Double.parseDouble(txt);
		}
		catch (NumberFormatException e)
		{
			result = parse(txt.replaceAll("\\s", ""));
		}
		//System.out.println(txt + ", " + result);
	}

	private Double parse(String s) throws ExpressionFormatException
	{
		boolean hasA = false;
		double a = 0D;
		int op = -1;
		boolean hasB = false;
		double b = 0D;
		String currNum = null;

		for (int i = 0; i < s.length(); i++)
		{
			int prevOp = op;
			char c = s.charAt(i);
			//System.out.println("[" + c + "], [" + hasA + "|" + a + "], [" + hasB + "|" + b + "], " + currNum);

			if (c == '(' || c == '[' || c == '{')
			{
				char opening = c;
				char closing = c == '(' ? ')' : c == '[' ? ']' : '}';

				int amt = 1;
				int start = ++i;
				int end = -1;
				for (; i < s.length(); i++)
				{
					c = s.charAt(i);
					if (c == opening)
					{
						amt++;
					}
					else if (c == closing)
					{
						amt--;

						if (amt == 0)
						{
							end = i;
							break;
						}
					}
				}

				if (end != -1)
				{
					String txt = s.substring(start, end);
					AlgebraicExpression ae = new AlgebraicExpression(txt);
					if (!hasA)
					{
						hasA = true;
						a = ae.result;
					}
					else if (!hasB)
					{
						hasB = true;
						b = ae.result;

						if (op != -1)
						{
							switch (op)
							{
								case 1:
									a = result = a + b;
									hasB = false;
									break;
								case 2:
									a = result = a - b;
									hasB = false;
									break;
								case 3:
									a = result = a * b;
									hasB = false;
									break;
								case 4:
									a = result = a / b;
									hasB = false;
									break;
							}
						}
					}
					else
					{
						throw new ExpressionFormatException("No need for " + txt + " = " + ae.result);
					}
				}
			}
			else if (Character.isDigit(c))
			{
				currNum = currNum == null ? String.valueOf(c) : currNum + c;
			}
			else if (c == 'E')
			{
				if (currNum != null)
				{
					currNum = currNum + c;
				}
				else
				{
					throw new ExpressionFormatException("Invalid letter");
				}
			}
			else if (c == '.')
			{
				if (currNum.contains("."))
				{
					throw new ExpressionFormatException("Invalid period");
				}
				else
				{
					currNum += c;
				}
			}
			else if (c == '+')
			{
				op = 1;
			}
			else if (c == '-')
			{
				if (currNum == null)
				{
					currNum = "-";
				}
				else
				{
					op = 2;
				}
			}
			else if (c == '*')
			{
				op = 3;
			}
			else if (c == '/')
			{
				op = 4;
			}
			else if (c == '^')
			{

			}

			if (prevOp != op && currNum != null)
			{
				if (!hasA)
				{
					hasA = true;
					a = Double.parseDouble(currNum);
					currNum = null;
				}
				else if (!hasB)
				{
					hasB = true;
					b = Double.parseDouble(currNum);
					currNum = null;

					if (op != -1)
					{
						switch (op)
						{
							case 1:
								a = result = a + b;
								hasB = false;
								break;
							case 2:
								a = result = a - b;
								hasB = false;
								break;
							case 3:
								a = result = a * b;
								hasB = false;
								break;
							case 4:
								a = result = a / b;
								hasB = false;
								break;
						}
					}
				}
				else
				{
					throw new ExpressionFormatException("No need for " + currNum);
				}
			}
		}
		if (!hasA && currNum != null)
		{
			hasA = true;
			a = Double.parseDouble(currNum);
			currNum = null;
		}
		else if (!hasB && currNum != null)
		{
			hasB = true;
			b = Double.parseDouble(currNum);
			currNum = null;

			if (op != -1)
			{
				switch (op)
				{
					case 1:
						a = result = a + b;
						hasB = false;
						break;
					case 2:
						a = result = a - b;
						hasB = false;
						break;
					case 3:
						a = result = a * b;
						hasB = false;
						break;
					case 4:
						a = result = a / b;
						hasB = false;
						break;
				}
			}
		}
		else if (currNum != null)
		{
			throw new ExpressionFormatException("No need for " + currNum);
		}
		//System.out.println("[ᖿ], [" + hasA + "|" + a + "], [" + hasB + "|" + b + "], " + currNum);
		return result;
	}
}
