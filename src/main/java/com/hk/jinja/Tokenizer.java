package com.hk.jinja;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * <p>Tokenizer class.</p>
 *
 * @author theKayani
 */
public class Tokenizer
{
	private final StringBuilder sb;
	private final LineNumberReader rdr;
	private char c;
	
	boolean inStatement, inExpression, inComment/*, inLineStatement */;
	
	private String prevToken, token;
	private Object prevValue, value;
	private int prevType, type;
	
	Tokenizer(Reader rdr)
	{
		this.rdr = new LineNumberReader(rdr);
		sb = new StringBuilder(256);
	}
	
	int read() throws IOException
	{
		return rdr.read();
	}
	
	boolean prev()
	{
		if(token == null)
			return false;
		
		prevToken = token;
		prevValue = value;
		prevType = type;
		return true;
	}
	
	boolean next() throws IOException
	{
		if(prevToken != null)
		{
			token = prevToken;
			value = prevValue;
			type = prevType;
			return true;
		}
		
		token = null;
		value = null;
		type = 0;
		if(inComment)
		{
			throw new RuntimeException("TODO");
		}
		else if(inExpression)
		{
			c = (char) rdr.read();

			if((short) c == -1)
				return false;

			if(c == '"' || c == '\'')
			{
				char q = c;
				
				while(true)
				{
					c = (char) rdr.read();

					if((short) c == -1)
					{
						type = T_INC_STRING;
						break;
					}
					else if(c == q)
					{
						type = T_STRING;
						break;
					}
					else
						sb.append(c);
				}
				value = token = sb.toString();
				sb.setLength(0);
			}
			else if(c >= '0' && c <= '9')
			{
				
			}
			else
			{
				token = String.valueOf(c);
				type = T_NULL;
			}
			return true;
		}
		else if(inStatement)
		{
			throw new RuntimeException("TODO");
		}
		else
		{
			while(true)
			{
				rdr.mark(1);
				c = (char) rdr.read();
				
				if((short) c == -1)
				{
					type = T_NULL;
					break;
				}
				else if(c == '{')
				{
					if(sb.length() > 0)
					{
						rdr.reset();
						type = T_NULL;
					}
					else
					{
						sb.append('{');
						type = T_OPEN_BRACE;
					}
					break;
				}
				else if(c == '%')
				{
					if(sb.length() > 0)
					{
						rdr.reset();
						type = T_NULL;
					}
					else
					{
						sb.append('%');
						type = T_MODULO;
					}
					break;
				}
				else if(c == '#')
				{
					if(sb.length() > 0)
					{
						rdr.reset();
						type = T_NULL;
					}
					else
					{
						sb.append('#');
						type = T_POUND;
					}
					break;
				}
				else
					sb.append(c);
			}
			
			if(sb.length() <= 0)
				return false;
			
			token = sb.toString();
			sb.setLength(0);
			
			return true;
		}
	}
	
	String token()
	{
		return token;
	}
	
	int type()
	{
		return type;
	}
	
//	private static boolean isIdentifierStart(char c)
//	{
//		return c == '_' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
//	}
	
//	private static boolean isIdentifierPart(char c)
//	{
//		return isIdentifierStart(c) || c >= '0' && c <= '9';
//	}
	
	static final int T_NULL = 0;
	static final int T_OPEN_BRACE = 1;
	static final int T_MODULO = 2;
	static final int T_POUND = 3;
	static final int T_STRING = 4;
	static final int T_INC_STRING = 5;
}
