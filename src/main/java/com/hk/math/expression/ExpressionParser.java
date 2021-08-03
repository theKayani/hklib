package com.hk.math.expression;

import com.hk.ex.UncheckedIOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

class ExpressionParser
{
	private final BufferedReader rdr;
	private char c;
	String token;
	Object value;
	byte type;
	boolean hasSpace;

	ExpressionParser(Reader rdr)
	{
		this.rdr = new BufferedReader(rdr, 64);
	}

	boolean next() throws ExpressionFormatException
	{
		try
		{
			value = token = null;
			type = 0;

			hasSpace = ws();
			int i = rdr.read();

			if(i == -1)
				return false;

			c = (char) i;

			if(isIdentifierStart(c))
			{
				StringBuilder sb = new StringBuilder();
				sb.append(c);

				while(true)
				{
					rdr.mark(1);

					i = rdr.read();

					if(i == -1)
						break;

					c = (char) i;

					if(isIdentifierPart(c))
						sb.append(c);
					else
					{
						rdr.reset();
						break;
					}
				}

				value = token = sb.toString();
				type = T_VARIABLE;
				return true;
			}

			switch(c)
			{
				case '+':
					value = c;
					token = String.valueOf(c);
					type = T_OP_ADD;
					return true;
				case '-':
					value = c;
					token = String.valueOf(c);
					type = T_OP_SUBTRACT;
					return true;
				case '*':
					value = c;
					token = String.valueOf(c);
					type = T_OP_MULTIPLY;
					return true;
				case '/':
					value = c;
					token = String.valueOf(c);
					type = T_OP_DIVIDE;
					return true;
				case '^':
					value = c;
					token = String.valueOf(c);
					type = T_OP_POW;
					return true;
				case '%':
					value = c;
					token = String.valueOf(c);
					type = T_OP_MOD;
					return true;
				case '(':
					value = token = String.valueOf(c);
					type = T_OPEN_PAR;
					return true;
				case ')':
					value = token = String.valueOf(c);
					type = T_CLOSE_PAR;
					return true;
				case '.':
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					StringBuilder sb = new StringBuilder();
					value = readNumber(sb);
					token = sb.toString();
					type = T_NUMBER;
					return true;
				default:
					throw new ExpressionFormatException("Unexpected token: " + c);
			}
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	private Double readNumber(StringBuilder sb) throws IOException, ExpressionFormatException
	{
		sb.append(c);
		double result = 0;
		double fraction = 0;
		int decimal = 0;

		if(c == '.')
			decimal = 1;
		else
			result = num(c);

		while(true)
		{
			rdr.mark(1);

			int i = rdr.read();

			if(i == -1)
				break;

			c = (char) i;

			if(c == '.')
			{
				if(decimal > 0)
					throw new ExpressionFormatException("Unexpected repeated period");

				sb.append('.');
				decimal = 1;
				continue;
			}

			i = num(c);

			if(i == -1)
			{
				rdr.reset();
				break;
			}

			sb.append(c);
			if(decimal > 0)
			{
				fraction += i / Math.pow(10, decimal);
				decimal++;
			}
			else
			{
				result *= 10;
				result += i;
			}
		}

		return result + fraction;
	}

	private boolean ws() throws IOException
	{
		boolean hasSpace = false;
		int i;
		while(true)
		{
			rdr.mark(1);

			i = rdr.read();

			if(i == -1 || !Character.isWhitespace(i))
			{
				rdr.reset();
				break;
			}
			hasSpace = true;
		}

		return hasSpace;
	}

	void close()
	{
		try
		{
			rdr.close();
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	private static boolean isIdentifierStart(char c)
	{
		return c == '_' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
	}

	private static boolean isIdentifierPart(char c)
	{
		return isIdentifierStart(c) || c >= '0' && c <= '9';
	}

	private static int num(char c)
	{
		switch(c)
		{
			case '0': return 0;
			case '1': return 1;
			case '2': return 2;
			case '3': return 3;
			case '4': return 4;
			case '5': return 5;
			case '6': return 6;
			case '7': return 7;
			case '8': return 8;
			case '9': return 9;
			default: return -1;
		}
	}

	static final byte T_VARIABLE = 1;
	static final byte T_NUMBER = 2;
	static final byte T_OPEN_PAR = 4;
	static final byte T_CLOSE_PAR = 5;

	static final byte T_OPERATOR = 16;
	static final byte T_OP_ADD = 16;
	static final byte T_OP_SUBTRACT = 17;
	static final byte T_OP_MULTIPLY = 18;
	static final byte T_OP_DIVIDE = 19;
	static final byte T_OP_POW = 20;
	static final byte T_OP_MOD = 21;
	static final byte T_OP_NEGATE = 22;
}
