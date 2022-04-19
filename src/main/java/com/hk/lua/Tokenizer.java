package com.hk.lua;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Stack;

class Tokenizer implements Tokens
{
	private final StringBuilder sb;
	private final LineNumberReader rdr;
	private final Stack<Object[]> next;
	private final LinkedList<Object[]> prev;
	private char c;
	private String token;
	private Object value;
	private int type, line;

	Tokenizer(Reader rdr)
	{
		this.rdr = new LineNumberReader(rdr);
		next = new Stack<>();
		prev = new LinkedList<>();
		sb = new StringBuilder(256);
	}

	void prev()
	{
		if(token == null)
			return;

		next.push(new Object[] { token, value, type, line });
		if(prev.isEmpty())
		{
			token = null;
			value = null;
			type = line = -1;
		}
		else
		{
			Object[] arr = prev.removeLast();
			token = (String) arr[0];
			value = arr[1];
			type = (int) arr[2];
			line = (int) arr[3];
		}
	}

	boolean next() throws IOException
	{
		if(token != null)
		{
			prev.add(new Object[] { token, value, type, line });
			if(prev.size() > 16)
				prev.removeFirst();	
		}
		if(!next.isEmpty())
		{
			Object[] arr = next.pop();
			token = (String) arr[0];
			value = arr[1];
			type = (int) arr[2];
			line = (int) arr[3];
			return true;
		}

		token = null;
		value = null;
		type = 0;

		ws();
		line = rdr.getLineNumber();
		c = (char) rdr.read();

		if((short) c == -1)
			return false;

		if(isIdentifierStart(c))
		{
			sb.append(c);

			while(true)
			{
				rdr.mark(1);
				c = (char) rdr.read();

				if(!isIdentifierPart(c))
				{
					rdr.reset();
					break;
				}

				sb.append(c);
			}
			value = token = sb.toString();
			sb.setLength(0);
			switch(token)
			{
			case "true":
			case "false":
				value = token.equals("true");
				type = T_BOOLEAN;
				break;
			case "and":
				type = T_AND;
				break;
			case "break":
				type = T_BREAK;
				break;
			case "do":
				type = T_DO;
				break;
			case "else":
				type = T_ELSE;
				break;
			case "elseif":
				type = T_ELSEIF;
				break;
			case "end":
				type = T_END;
				break;
			case "for":
				type = T_FOR;
				break;
			case "function":
				type = T_FUNCTION;
				break;
			case "goto":
				type = T_GOTO;
				break;
			case "if":
				type = T_IF;
				break;
			case "in":
				type = T_IN;
				break;
			case "local":
				type = T_LOCAL;
				break;
			case "nil":
				type = T_NIL;
				break;
			case "not":
				type = T_NOT;
				break;
			case "or":
				type = T_OR;
				break;
			case "repeat":
				type = T_REPEAT;
				break;
			case "return":
				type = T_RETURN;
				break;
			case "then":
				type = T_THEN;
				break;
			case "until":
				type = T_UNTIL;
				break;
			case "while":
				type = T_WHILE;
				break;
			default:
				type = T_IDENTIFIER;
			}
		}
		else
		{
			token = String.valueOf(c);
			switch(c)
			{
			case '"':
			case '\'':
				if(readString(sb))
					type = T_STRING;
				else
					type = T_INC_STRING;
				value = token = sb.toString();
				sb.setLength(0);
				break;
			case '0':
				rdr.mark(1);
				c = (char) rdr.read();
				if(c == 'x' || c == 'X')
				{
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
				type = T_NUMBER;
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
				type = T_NUMBER;
				break;
			case '%':
				type = T_MODULO;
				break;
			case ':':
				rdr.mark(1);
				c = (char) rdr.read();
				if(c == ':')
				{
					type = T_DOUBLECOLON;
				}
				else
				{
					rdr.reset();
					type = T_COLON;
				}
				break;
			case '#':
				type = T_POUND;
				break;
			case '+':
				type = T_PLUS;
				break;
			case '-':
				type = T_MINUS;
				break;
			case '*':
				type = T_TIMES;
				break;
			case '/':
				rdr.mark(1);
				c = (char) rdr.read();
				if(c == '/')
					type = T_FLR_DIVIDE;
				else
				{
					rdr.reset();
					type = T_DIVIDE;
				}
				break;
			case '&':
				type = T_BAND;
				break;
			case '|':
				type = T_BOR;
				break;
			case ';':
				type = T_SEMIC;
				break;
			case ',':
				type = T_COMMA;
				break;
			case '^':
				type = T_POW;
				break;
			case ')':
				type = T_CLSE_PTS;
				break;
			case '(':
				type = T_OPEN_PTS;
				break;
			case '}':
				type = T_CLSE_BRC;
				break;
			case '{':
				type = T_OPEN_BRC;
				break;
			case ']':
				type = T_CLSE_BKT;
				break;
			case '[':
				rdr.mark(1);
				c = (char) rdr.read();
				if(c == '[' || c == '=')
				{
					int amt = 0;
					while(c == '=')
					{
						amt++;
						c = (char) rdr.read();
					}
					if(c == '[')
					{
						boolean first = true;
						while(true)
						{
							c = (char) rdr.read();

							if(c == ']')
							{
								c = (char) rdr.read();
								int amt2 = 0;
								while(c == '=' && amt2 < amt)
								{
									amt2++;
									c = (char) rdr.read();
								}

								if(c != ']')
								{
									sb.append(']');
									while(amt2-- > 0)
										sb.append('=');
									sb.append(c);
								}
								else
									break;
							}
							else if(!first || c != '\n')
								sb.append(c);

							first = false;
						}
						value = token = sb.toString();
						sb.setLength(0);
						type = T_STRING;
					}
					else
						type = T_INC_STRING;
				}
				else
				{
					rdr.reset();
					type = T_OPEN_BKT;
				}
				break;
			case '.':
				rdr.mark(1);
				c = (char) rdr.read();
				if(c == '.')
				{
					token += '.';
					rdr.mark(1);
					c = (char) rdr.read();
					if(c == '.')
					{
						type = T_VARARGS;
						token += '.';
					}
					else
					{
						rdr.reset();
						type = T_CONCAT;
					}
				}
				else if('0' <= c && c <= '9')
				{
					c = '.';
					rdr.reset();
					value = readNumber(sb);
					token = sb.toString();
					sb.setLength(0);
					type = T_NUMBER;
				}
				else
				{
					rdr.reset();
					type = T_PERIOD;
				}
				break;
			case '=':
			case '~':
			case '>':
			case '<':
				char q = c;
				rdr.mark(1);
				c = (char) rdr.read();
				if(c == '>' && q == '>')
				{
					token += q;
					type = T_SHR;
				}
				else if(c == '<' && q == '<')
				{
					token += q;
					type = T_SHL;
				}
				else if(c == '=')
				{
					token += '=';
					switch(q)
					{
					case '=':
						type = T_EEQUALS;
						break;
					case '~':
						type = T_NEQUALS;
						break;
					case '>':
						type = T_GREQ_THAN;
						break;
					case '<':
						type = T_LSEQ_THAN;
						break;
					}
				}
				else
				{
					rdr.reset();
					switch(q)
					{
					case '=':
						type = T_EQUALS;
						break;
					case '~':
						type = T_BXOR;
						break;
					case '>':
						type = T_GRTR_THAN;
						break;
					case '<':
						type = T_LESS_THAN;
						break;
					}
				}
				break;
			default:
				type = T_NULL;
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
				type = T_STRING;
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

	int type()
	{
		return type;
	}

	int line()
	{
		return line;
	}

	<T> T value(Class<T> cls)
	{
		return cls.cast(value);
	}

	private void ws() throws IOException
	{
		while(true)
		{
			rdr.mark(2);
			c = (char) rdr.read();
			if(c == '-')
			{
				if((c = (char) rdr.read()) == '-')
				{
					int level = -1;

					c = (char) rdr.read();
					if(c == '[')
					{
						int nl = 0;
						while((c = (char) rdr.read()) == '=')
							nl++;

						if(c == '[')
						{
							level = nl;
						}
					}

					do
					{
						if((short) c == -1)
							break;

						if(level < 0 && c == '\n')
						{
							break;
						}
						else if(level >= 0 && c == ']')
						{
							int nl = 0;
							while((c = (char) rdr.read()) == '=')
								nl++;

							if(nl == level && c == ']')
								break;
						}

						c = (char) rdr.read();
					} while(true);
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

	private static boolean isIdentifierStart(char c)
	{
		return c == '_' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
	}

	private static boolean isIdentifierPart(char c)
	{
		return isIdentifierStart(c) || c >= '0' && c <= '9';
	}

	static int prec(int type)
	{
		switch(type)
		{
		case T_OR:
			return 1;
		case T_AND:
			return 2;
		case T_GREQ_THAN:
		case T_LSEQ_THAN:
		case T_GRTR_THAN:
		case T_LESS_THAN:
		case T_NEQUALS:
		case T_EEQUALS:
			return 3;
		case T_BOR:
			return 4;
		case T_BXOR:
			return 5;
		case T_BAND:
			return 6;
		case T_SHR:
		case T_SHL:
			return 7;
		case T_CONCAT:
			return 8;
		case T_MINUS:
		case T_PLUS:
			return 9;
		case T_TIMES:
		case T_DIVIDE:
		case T_FLR_DIVIDE:
		case T_MODULO:
			return 10;
		case T_POW:
			return 11;
		case T_NOT:
		case T_NEGATE:
		case T_POUND:
		case T_UBNOT:
			return 12;
		default:
			return 0;
		}
	}

	static String label(int type)
	{
		switch(type)
		{
		case T_AND:                     return "T_AND";
		case T_BAND:                    return "T_BAND";
		case T_BOOLEAN:                 return "T_BOOLEAN";
		case T_BOR:                     return "T_BOR";
		case T_BREAK:                   return "T_BREAK";
		case T_CLSE_BKT:                return "T_CLSE_BKT";
		case T_CLSE_BRC:                return "T_CLSE_BRC";
		case T_CLSE_PTS:                return "T_CLSE_PTS";
		case T_COLON:                   return "T_COLON";
		case T_COMMA:                   return "T_COMMA";
		case T_CONCAT:                  return "T_CONCAT";
		case T_DIVIDE:                  return "T_DIVIDE";
		case T_DO:                      return "T_DO";
		case T_EEQUALS:                 return "T_EEQUALS";
		case T_ELSE:                    return "T_ELSE";
		case T_ELSEIF:                  return "T_ELSEIF";
		case T_END:                     return "T_END";
		case T_EQUALS:                  return "T_EQUALS";
		case T_FLR_DIVIDE:              return "T_FLR_DIVIDE";
		case T_FOR:                     return "T_FOR";
		case T_FUNCTION:                return "T_FUNCTION";
		case T_GREQ_THAN:               return "T_GREQ_THAN";
		case T_GRTR_THAN:               return "T_GRTR_THAN";
		case T_IDENTIFIER:              return "T_IDENTIFIER";
		case T_IF:                      return "T_IF";
		case T_IN:                      return "T_IN";
		case T_INC_NUMBER:              return "T_INC_NUMBER";
		case T_INC_STRING:              return "T_INC_STRING";
		case T_LESS_THAN:               return "T_LESS_THAN";
		case T_LOCAL:                   return "T_LOCAL";
		case T_LSEQ_THAN:               return "T_LSEQ_THAN";
		case T_MINUS:                   return "T_MINUS";
		case T_MODULO:                  return "T_MODULO";
		case T_NEGATE:                  return "T_NEGATE";
		case T_NEQUALS:                 return "T_NEQUALS";
		case T_NIL:                     return "T_NIL";
		case T_NOT:                     return "T_NOT";
		case T_NULL:                    return "T_NULL";
		case T_NUMBER:                  return "T_NUMBER";
		case T_OPEN_BKT:                return "T_OPEN_BKT";
		case T_OPEN_BRC:                return "T_OPEN_BRC";
		case T_OPEN_PTS:                return "T_OPEN_PTS";
		case T_OR:                      return "T_OR";
		case T_PERIOD:                  return "T_PERIOD";
		case T_PLUS:                    return "T_PLUS";
		case T_POUND:                   return "T_POUND";
		case T_POW:                     return "T_POW";
		case T_REPEAT:                  return "T_REPEAT";
		case T_RETURN:                  return "T_RETURN";
		case T_SEMIC:                   return "T_SEMIC";
		case T_SHL:                     return "T_SHL";
		case T_SHR:                     return "T_SHR";
		case T_STRING:                  return "T_STRING";
		case T_TABLE:                   return "T_TABLE";
		case T_THEN:                    return "T_THEN";
		case T_BXOR:                    return "T_BXOR";
		case T_TIMES:                   return "T_TIMES";
		case T_UNTIL:                   return "T_UNTIL";
		case T_VARARGS:                 return "T_VARARGS";
		case T_WHILE:                   return "T_WHILE";
		default:                        return null;
		}
	}
}