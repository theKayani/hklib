package com.hk.math.expression;

import org.jetbrains.annotations.NotNull;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * <p>Expression class.</p>
 *
 * @author theKayani
 */
public class Expression
{
	public final boolean result;

	/**
	 * <p>Constructor for Expression.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @throws com.hk.math.expression.ExpressionFormatException if any.
	 */
	public Expression(@NotNull String str) throws ExpressionFormatException
	{
		str = str.replaceAll("\\s", "");

		if (str.length() == 1)
		{
			if (str.equals("0"))
			{
				result = false;
			}
			else if (str.equals("1"))
			{
				result = true;
			}
			else
			{
				throw new ExpressionFormatException("Invalid character: " + str);
			}
		}
		else
		{
			result = parse(str, new StringCharacterIterator("0" + str));
		}
	}

	private boolean parse(String s, StringCharacterIterator itr) throws ExpressionFormatException
	{
		int curr = -1;
		int op = -1;

		while (itr.next() != CharacterIterator.DONE)
		{
			char c = itr.current();

			switch (c)
			{
				case '(':
					int start = itr.getIndex();
					int end = -1;
					int amt = 1;
					while (itr.next() != CharacterIterator.DONE)
					{
						if (itr.current() == '(')
						{
							amt++;
						}
						else if (itr.current() == ')')
						{
							amt--;

							if (amt == 0)
							{
								end = itr.getIndex();
								break;
							}
						}
					}

					if (end != -1)
					{
						boolean val = new Expression(s.substring(start, end - 1)).result;
						val = (s.charAt(start - 1) == '!') != val;
						int v = val ? 1 : 0;

						if (curr == -1)
						{
							curr = v;
						}
						else
						{
							if (op == 0)
							{
								curr &= v;
							}
							else if (op == 1)
							{
								curr |= v;
							}
							else if (op == 2)
							{
								curr ^= v;
							}
							else
							{
								throw new ExpressionFormatException("Unexpected Value. Expected Operation.");
							}
						}
					}
					else
					{
						throw new ExpressionFormatException("Unclosed Parenthesis at " + start);
					}
					break;
				case '0':
				case '1':
					boolean val = c == '1';
					// i have no idea what this is...
					if (itr.getIndex() > 0)
					{
						val = (s.charAt(itr.getIndex() - 1) == '!') != val;
					}
					int v = c == '0' ? 0 : 1;

					if (curr == -1)
					{
						curr = v;
					}
					else
					{
						if (op == 0)
						{
							curr &= v;
						}
						else if (op == 1)
						{
							curr |= v;
						}
						else if (op == 2)
						{
							curr ^= v;
						}
						else
						{
							System.err.println(curr);
							throw new ExpressionFormatException("Unexpected Value at index (" + itr.getIndex() + "): '" + c + "', Expected Operation");
						}
					}
					break;
				case '!':
					break;
				case '&':
					if (curr != -1)
					{
						op = 0;
					}
					else
					{
						throw new ExpressionFormatException("Unexpected '&', no set value");
					}
					break;
				case '|':
					if (curr != -1)
					{
						op = 1;
					}
					else
					{
						throw new ExpressionFormatException("Unexpected '|', no set value");
					}
					break;
				case '^':
					if (curr != -1)
					{
						op = 2;
					}
					else
					{
						throw new ExpressionFormatException("Unexpected '^', no set value");
					}
					break;
				default:
					throw new ExpressionFormatException("Unexpected Value at " + itr.getIndex() + ": '" + c + "'");
			}
		}

		return curr != 0;
	}

	public static class Builder
	{
		private String s = "";

		@NotNull
		public Builder pf()
		{
			s += "0";
			return this;
		}

		@NotNull
		public Builder pt()
		{
			s += "1";
			return this;
		}

		@NotNull
		public Builder p(boolean value)
		{
			s += value ? "1" : "0";
			return this;
		}

		@NotNull
		public Builder or()
		{
			s += " | ";
			return this;
		}

		@NotNull
		public Builder and()
		{
			s += " & ";
			return this;
		}

		@NotNull
		public Builder not()
		{
			s += "!";
			return this;
		}

		@NotNull
		public Builder xor()
		{
			s += " ^ ";
			return this;
		}

		@NotNull
		public Builder op()
		{
			s += "(";
			return this;
		}

		@NotNull
		public Builder cp()
		{
			s += ")";
			return this;
		}

		@NotNull
		public String get()
		{
			return s;
		}

		@NotNull
		public String build()
		{
			String str = s;
			s = "";
			return str;
		}
	}
}
