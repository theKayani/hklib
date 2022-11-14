package com.hk.blok;

import java.io.Closeable;
import java.io.Reader;
import java.io.LineNumberReader;
import java.io.IOException;

public class Tokenizer implements Closeable
{
	private final LineNumberReader rdr;
	private final StringBuilder sb;
	private int ci;
	private char c;
	private String token;
	private Object value;
	private Tokens type;

	Tokenizer(Reader rdr)
	{
		this.rdr = new LineNumberReader(rdr);
		sb = new StringBuilder(256);
	}

	boolean next() throws IOException
	{
		token = null;
		value = null;
		type = null;

		ws();

		ci = rdr.read();

		if(ci == -1)
			return false;

		c = (char) ci;
		if(Character.isJavaIdentifierStart(c))
		{
			sb.append(c);

			while(true)
			{
				rdr.mark(1);
				c = (char) rdr.read();

				if(!Character.isJavaIdentifierPart(c))
				{
					rdr.reset();
					break;
				}

				sb.append(c);
			}
			value = token = sb.toString();
			sb.setLength(0);
			type = Tokens.T_IDENTIFIER;
		}
		else
		{
			token = String.valueOf(c);
			switch(c)
			{
				case '[':
					rdr.mark(1);
					c = (char) rdr.read();
					if(c == ']')
					{
						type = Tokens.T_BOX;
					}
					else
					{
						type = Tokens.T_OPEN_BKT;
						rdr.reset();
					}
					break;
				case ']':
					type = Tokens.T_CLSE_BKT;
					break;
				case '"':
				case '\'':
					if(readString(sb))
						type = Tokens.T_STRING;
					else
						type = Tokens.T_INC_STRING;
					value = token = sb.toString();
					sb.setLength(0);
					break;
				case '.':
					rdr.mark(1);
					c = (char) rdr.read();
					if('0' <= c && c <= '9')
					{
						c = '.';
						rdr.reset();
						value = readNumber(sb);
						token = sb.toString();
						sb.setLength(0);
						type = Tokens.T_NUMBER;
					}
					else
					{
						rdr.reset();
						type = Tokens.T_PERIOD;
					}
					break;
				case ':':
					type = Tokens.T_COLON;
					break;
				case ';':
					type = Tokens.T_SEMIC;
					break;
				case ',':
					type = Tokens.T_COMMA;
					break;
				case ')':
					type = Tokens.T_CLSE_PTS;
					break;
				case '(':
					type = Tokens.T_OPEN_PTS;
					break;
				case '}':
					type = Tokens.T_CLSE_BRC;
					break;
				case '{':
					type = Tokens.T_OPEN_BRC;
					break;
				case '0':
					rdr.mark(1);
					c = (char) rdr.read();
					if(c == 'x' || c == 'X')
					{
						sb.append('0').append(c);
						value = readHex(sb);
					}
					else
					{
						rdr.reset();
						c = '0';
						value = readNumber(sb);
					}
					token = sb.toString();
					sb.setLength(0);
					type = Tokens.T_NUMBER;
					break;
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					value = readNumber(sb);
					token = sb.toString();
					sb.setLength(0);
					type = Tokens.T_NUMBER;
					break;
				case '=':
				case '!':
				case '>':
				case '<':
					char q = c;
					rdr.mark(1);
					c = (char) rdr.read();
					if(c == '>' && q == '>')
					{
						token += q;
						type = Tokens.T_SHR;
					}
					else if(c == '<' && q == '<')
					{
						token += q;
						type = Tokens.T_SHL;
					}
					else if(c == '=')
					{
						token += '=';
						switch(q)
						{
							case '=':
								type = Tokens.T_EEQUALS;
								break;
							case '!':
								type = Tokens.T_NEQUALS;
								break;
							case '>':
								type = Tokens.T_GREQ_THAN;
								break;
							case '<':
								type = Tokens.T_LSEQ_THAN;
								break;
						}
					}
					else
					{
						rdr.reset();
						switch(q)
						{
							case '=':
								type = Tokens.T_EQUALS;
								break;
							case '!':
								type = Tokens.T_NOT;
								break;
							case '>':
								type = Tokens.T_GRTR_THAN;
								break;
							case '<':
								type = Tokens.T_LESS_THAN;
								break;
						}
					}
					break;
			}
			if(value == null)
				value = token;
		}

		return true;
	}

	private boolean readString(StringBuilder sb) throws IOException
	{
		char q = c;

		while(true)
		{
			c = (char) rdr.read();

			if((short) c == -1)
				return false;
			else if(c == q)
			{
				type = Tokens.T_STRING;
				break;
			}
			else if(c == '\\')
			{
				c = (char) rdr.read();
				if(c >= '0' && c <= '9')
				{
					StringBuilder s = new StringBuilder(String.valueOf(c));
					for(int i = 0; i < 2; i++)
					{
						rdr.mark(1);
						c = (char) rdr.read();
						if(c >= '0' && c <= '9')
							s.append(c);
						else
							rdr.reset();
					}
					sb.append((char) Integer.parseInt(s.toString()));
				}
				else
				{
					switch(c)
					{
						case 'v':
							sb.append('\u000B');
						case 'a':
							sb.append('\u0007');
						case 'b':
							sb.append('\b');
							break;
						case 'r':
							sb.append('\r');
							break;
						case 'n':
							sb.append('\n');
							break;
						case 't':
							sb.append('\t');
							break;
						case 'f':
							sb.append('\f');
							break;
						case '[': case ']':
						case '\\': case '\'': case '"':
						sb.append(c);
						break;
						default:
							return false;
					}
				}
			}
			else
			{
				sb.append(c);
			}
		}
		return true;
	}

	private Number readNumber(StringBuilder sb) throws IOException
	{
		boolean isFloat = false;
		int n;
		double decimal = 0;
		long integer = 0;

		while((n = num(c)) < 10)
		{
			sb.append(c);

			decimal *= 10;
			integer *= 10;
			decimal += n;
			integer += n;
			if(integer < 0)
				isFloat = true;

			rdr.mark(1);
			c = (char) rdr.read();
		}

		if(c == '.')
		{
			sb.append('.');
			double fraction = 0;
			double place = 10;
			while(true)
			{
				rdr.mark(1);
				c = (char) rdr.read();

				if((n = num(c)) >= 10)
					break;

				sb.append(c);
				fraction += n / place;
				place *= 10;
			}

			decimal += fraction;
			isFloat = true;
		}

		if(c == 'e' || c == 'E')
		{
			rdr.mark(1);
			c = (char) rdr.read();

			boolean neg = false;

			if(c == '-' || c == '+')
			{
				sb.append('.');
				neg = c == '-';
			}
			else
				rdr.reset();

			double exponent = 0;
			while(true)
			{
				rdr.mark(1);
				c = (char) rdr.read();

				if((n = num(c)) >= 10)
					break;

				sb.append(c);
				exponent *= 10;
				exponent += n;
			}

			exponent = Math.pow(10, neg ? -exponent : exponent);
			decimal *= exponent;

			isFloat = true;
		}

		rdr.reset();

		if(isFloat)
			return decimal;
		else
			return integer;
	}

	private Number readHex(StringBuilder sb) throws IOException
	{
		boolean isFloat = false;
		int n;
		double decimal = 0;

		while(true)
		{
			rdr.mark(1);
			c = (char) rdr.read();

			if((n = num(c)) >= 16)
				break;

			sb.append(c);

			decimal *= 16;
			decimal += n;
		}

		if(c == '.')
		{
			sb.append('.');
			double fraction = 0;
			double place = 16;
			while(true)
			{
				rdr.mark(1);
				c = (char) rdr.read();

				if((n = num(c)) >= 16)
					break;

				sb.append(c);
				fraction += n / place;
				place *= 16;
			}

			decimal += fraction;
			isFloat = true;
		}

		if(c == 'p' || c == 'P')
		{
			rdr.mark(1);
			c = (char) rdr.read();

			boolean neg = false;

			if(c == '-' || c == '+')
			{
				sb.append('.');
				neg = c == '-';
			}
			else
				rdr.reset();

			double exponent = 0;
			while(true)
			{
				rdr.mark(1);
				c = (char) rdr.read();

				if((n = num(c)) >= 10)
					break;

				sb.append(c);
				exponent *= 10;
				exponent += n;
			}

			decimal *= Math.pow(2, neg ? -exponent : exponent);
			isFloat = true;
		}
		rdr.reset();

		if(!isFloat && decimal == (double) (long) decimal)
			return (long) decimal;
		else
			return decimal;
	}

	static int num(char c)
	{
		switch(c)
		{
			case '0':
				return 0;
			case '1':
				return 1;
			case '2':
				return 2;
			case '3':
				return 3;
			case '4':
				return 4;
			case '5':
				return 5;
			case '6':
				return 6;
			case '7':
				return 7;
			case '8':
				return 8;
			case '9':
				return 9;
			case 'a':
			case 'A':
				return 10;
			case 'b':
			case 'B':
				return 11;
			case 'c':
			case 'C':
				return 12;
			case 'd':
			case 'D':
				return 13;
			case 'e':
			case 'E':
				return 14;
			case 'f':
			case 'F':
				return 15;
			default:
				return Integer.MAX_VALUE;
		}
	}

	String token()
	{
		return token;
	}

	Object value()
	{
		return value;
	}

	Tokens type()
	{
		return type;
	}

	private void ws() throws IOException
	{
		while(true)
		{
			rdr.mark(2);
			c = (char) rdr.read();
			if(c == '/')
			{
				if((c = (char) rdr.read()) == '*')
				{
					while(true)
					{
						ci = rdr.read();
						if(ci == -1)
							break;
						c = (char) ci;
						if(c !=  '*')
							continue;

						rdr.mark(1);
						ci = rdr.read();
						if(ci == -1)
							break;
						c = (char) ci;
						if(c == '/')
							break;
						else
							rdr.reset();
					}
				}
				else
				{
					rdr.reset();
					break;
				}
			}
			else if(!Character.isWhitespace(c))
			{
				rdr.reset();
				break;
			}
		}
	}

	@Override
	public void close() throws IOException
	{
		rdr.close();
	}

	enum Tokens
	{
		T_OPEN_BKT, T_OPEN_PTS, T_OPEN_BRC,
		T_CLSE_BKT, T_CLSE_PTS, T_CLSE_BRC,
		T_IDENTIFIER, T_STRING, T_INC_STRING, T_NUMBER,
		T_BOX, T_PERIOD, T_SEMIC, T_COMMA, T_COLON,
		T_NOT, T_SHR, T_SHL,
		T_EQUALS, T_EEQUALS, T_NEQUALS,
		T_GREQ_THAN, T_LSEQ_THAN,
		T_GRTR_THAN, T_LESS_THAN;

		static final int F_GLOBAL = 1;
	}

//	static class Tokens
//	{
//		static final int T_OPEN_BKT = 1;
//		static final int T_CLSE_BKT = 2;
//		static final int T_IDENTIFIER = 3;
//		static final int T_STRING = 4;
//		static final int T_INC_STRING = 5;
//		static final int T_NUMBER = 6;
//		static final int T_PERIOD = 7;
//		static final int T_SEMIC = 8;
//		static final int T_COMMA = 9;
//		static final int T_OPEN_PTS = 10;
//		static final int T_CLSE_PTS = 11;
//		static final int T_OPEN_BRC = 12;
//		static final int T_CLSE_BRC = 13;
//		static final int T_COLON = 14;
//
//		static final int F_GLOBAL = 1;
//	}
}
