package com.hk.json;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import com.hk.ex.UncheckedIOException;

/**
 * <p>JsonReader class.</p>
 *
 * @author theKayani
 */
public class JsonReader
{
	private final LineNumberReader rdr;
	private char c;
	private boolean readFully;
	
	JsonReader(Reader rdr)
	{
		this.rdr = new LineNumberReader(rdr);
		
		setReadFully();
	}
	
	/**
	 * <p>unsetReadFully.</p>
	 *
	 * @return a {@link com.hk.json.JsonReader} object
	 */
	public JsonReader unsetReadFully()
	{
		this.readFully = false;
		return this;
	}
	
	/**
	 * <p>Setter for the field <code>readFully</code>.</p>
	 *
	 * @return a {@link com.hk.json.JsonReader} object
	 */
	public JsonReader setReadFully()
	{
		this.readFully = true;
		return this;
	}
	
	/**
	 * <p>get.</p>
	 *
	 * @return a {@link com.hk.json.JsonValue} object
	 * @throws com.hk.json.JsonFormatException if any.
	 */
	public JsonValue get() throws JsonFormatException
	{
		try
		{
			JsonValue val = parseValue();
			if(readFully)
			{
				int i = rdr.read();
				c = (char) i;
				if(i != -1)
					throw unexpected();
			}
			return val;
		}
		catch(IOException ex)
		{
			throw new UncheckedIOException(ex);
		}
	}
	
	/**
	 * <p>close.</p>
	 */
	public void close()
	{
		try
		{
			rdr.close();
		}
		catch (IOException ex)
		{
			throw new UncheckedIOException(ex);
		}
	}
	
	private JsonValue parseValue() throws IOException
	{
		ws();
		int i = rdr.read();
		if(i == -1)
			throw eof("value");
		
		JsonValue val;
		c = (char) i;
		if(c == '{')
		{
			val = parseObject();
		}
		else if(c == '[')
		{
			val = parseArray();
		}
		else if(c == '"')
		{
			val = parseString();
		}
		else if(c == '-' || c >= '0' && c <= '9')
		{
			val = parseNumber();
		}
		else if(c == 't' || c == 'f')
		{
			val = parseBoolean();
		}
		else if(c == 'n')
		{
			val = parseNull();
		}
		else
			throw unexpected();
		
		ws();
		return val;
	}

	private JsonObject parseObject() throws IOException
	{
		if(c != '{')
			throw unexpected();
		ws();
		int i = rdr.read();
		if(i == -1)
			throw eof("object");
		
		JsonObject obj = new JsonObject();
		String name;
		JsonValue val;
		c = (char) i;
		if(c != '}')
		{
			do
			{
				name = readString();
				ws();
				i = rdr.read();
				if(i == -1)
					throw eof("object");
				
				c = (char) i;
				if(c != ':')
					throw unexpected();
				
				val = parseValue();
				obj.put(name, val);

				ws();
				i = rdr.read();
				if(i == -1)
					throw eof("object");
				
				c = (char) i;
				if(c != ',')
					break;
				
				ws();
				i = rdr.read();
				if(i == -1)
					throw eof("object");
				c = (char) i;
			} while(true);

			if(c != '}')
				throw unexpected();
		}
		return obj;
	}

	private JsonArray parseArray() throws IOException
	{
		if(c != '[')
			throw unexpected();
		ws();
		rdr.mark(1);
		int i = rdr.read();
		if(i == -1)
			throw eof("array");
		
		JsonArray arr = new JsonArray();
		JsonValue val;
		c = (char) i;
		if(c != ']')
		{
			rdr.reset();
			do
			{
				val = parseValue();
				arr.add(val);

				ws();
				i = rdr.read();
				if(i == -1)
					throw eof("array");
				
				c = (char) i;
				if(c != ',')
					break;
				ws();
			} while(true);

			if(c != ']')
				throw unexpected();
		}
		return arr;
	}

	private JsonString parseString() throws IOException
	{
		return new JsonString(readString());
	}

	private JsonNumber parseNumber() throws IOException
	{
		int i;
		boolean decimal = false;
		StringBuilder sb = new StringBuilder();
		
		if(c == '-')
		{
			sb.append('-');

			i = rdr.read();
			if(i == -1)
				throw eof("number");
			
			c = (char) i;
		}
		
		if(c >= '0' && c <= '9')
		{
			sb.append(c);
			
			if(c != '0')
			{
				readDigits(sb);
			}
		}
		else
			throw unexpected();
		
		rdr.mark(1);
		
		i = rdr.read();
		c = (char) i;
		
		if(c == '.')
		{
			decimal = true;
			sb.append('.');
			if(!readDigits(sb))
				throw unexpected();
		}
		else
			rdr.reset();
		
		rdr.mark(1);

		i = rdr.read();
		c = (char) i;

		if(c == 'e' || c == 'E')
		{
			rdr.mark(1);

			i = rdr.read();
			c = (char) i;
			
			StringBuilder sb2 = new StringBuilder();
			if(c == '-' || c == '+')
				sb2.append(c);
			else
				rdr.reset();
			
			if(!readDigits(sb2))
				throw unexpected();

			
			try
			{
				double val = Double.parseDouble(sb.toString());
				val *= Math.pow(10, Long.parseLong(sb2.toString()));
				return new JsonNumber(val);
			}
			catch(NumberFormatException ex)
			{
				throw new JsonFormatException(ex);
			}
		}
		else
		{
			rdr.reset();

			if(decimal)
				return new JsonNumber(Double.parseDouble(sb.toString()));
			else
				return new JsonNumber(Long.parseLong(sb.toString()));
		}
	}

	private JsonBoolean parseBoolean() throws IOException
	{
		if(c == 't' &&
			(c = (char) rdr.read()) == 'r' &&
			(c = (char) rdr.read()) == 'u' &&
			(c = (char) rdr.read()) == 'e')
			return JsonBoolean.TRUE;
		else if(c == 'f' &&
				(c = (char) rdr.read()) == 'a' &&
				(c = (char) rdr.read()) == 'l' &&
				(c = (char) rdr.read()) == 's' &&
				(c = (char) rdr.read()) == 'e')
			return JsonBoolean.FALSE;
		
		throw unexpected();
	}

	private JsonNull parseNull() throws IOException
	{
		if(c == 'n' &&
			(c = (char) rdr.read()) == 'u' &&
			(c = (char) rdr.read()) == 'l' &&
			(c = (char) rdr.read()) == 'l')
			return JsonNull.NULL;

		throw unexpected();
	}
	
	private boolean readDigits(StringBuilder sb) throws IOException
	{
		boolean read = false;
		int i;
		while(true)
		{
			rdr.mark(1);
			
			i = rdr.read();
			if(i == -1)
				break;
			c = (char) i;
			
			if(c >= '0' && c <= '9')
			{
				read = true;
				sb.append(c);
			}
			else
			{
				rdr.reset();
				break;
			}
		}
		
		return read;
	}
	
	private String readString() throws IOException
	{
		if(c != '"')
			throw unexpected();
		StringBuilder sb = new StringBuilder();

		int i;
		while(true)
		{
			i = rdr.read();
			if(i == -1)
				throw eof("string");
			
			c = (char) i;
			if(c == '\\')
			{
				i = rdr.read();
				if(i == -1)
					throw eof("string");

				c = (char) i;

				if(c == '"')
					sb.append('"');
				else if(c == '\\')
					sb.append('\\');
				else if(c == '/')
					sb.append('/');
				else if(c == 'b')
					sb.append('\b');
				else if(c == 'f')
					sb.append('\f');
				else if(c == 'n')
					sb.append('\n');
				else if(c == 'r')
					sb.append('\r');
				else if(c == 't')
					sb.append('\t');
				else if(c == 'u')
				{
					char[] cs = new char[4];
					for(int j = 0; j < 4; j++)
					{
						i = rdr.read();
						if(i == -1)
							throw eof("string");
						cs[j] = testHex(i);
					}
					
					sb.append((char) Integer.parseInt(new String(cs), 16));
				}
				else
					throw unexpected("character escape");
			}
			else if(c != '"')
				sb.append(c);
			else
				break;
		}
		
		return sb.toString();
	}
	
	private void ws() throws IOException
	{
		while(true)
		{
			rdr.mark(1);
			c = (char) rdr.read();
			if(!Character.isWhitespace(c))
			{
				rdr.reset();
				break;
			}
		}
	}
	
	private char testHex(int i)
	{
		char c = (char) i;
		switch(c)
		{
			case '0': case '1': case '2': case '3':
	        case '4': case '5': case '6': case '7':
	        case '8': case '9': case 'a': case 'b':
	        case 'c': case 'd': case 'e': case 'f':
	        case 'A': case 'B': case 'C': case 'D':
	        case 'E': case 'F':
	            return c;
		}
		
		throw unexpected("unicode escape");
	}
	
	private JsonFormatException eof(String section)
	{
		return new JsonFormatException("EOF while reading " + section + " (Line " + (rdr.getLineNumber() + 1) + ")");
	}
	
	private JsonFormatException unexpected()
	{
		return unexpected(null);
	}
	
	private JsonFormatException unexpected(String section)
	{
		String s = "Unexpected char '";
		s += c;
		s += '\'';
		if(section != null)
		{
			s += " reading ";
			s += section;
			s += ' ';
		}
		s += "(Line ";
		s += (rdr.getLineNumber() + 1);
		s += ')';
		return new JsonFormatException(s);
	}
}
