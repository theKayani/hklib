package com.hk.jinja;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TemplateReader class.</p>
 *
 * @author theKayani
 */
public class TemplateReader
{
	private final Tokenizer tkz;

	TemplateReader(Reader rdr)
	{
		this.tkz = new Tokenizer(rdr);
	}

	/**
	 * <p>get.</p>
	 *
	 * @return a {@link com.hk.jinja.Template} object
	 * @throws java.io.UncheckedIOException if any.
	 */
	public Template get() throws UncheckedIOException
	{
		try
		{
			return compileTemplate();
		}
		catch(IOException ex)
		{
			throw new UncheckedIOException(ex);
		}
	}

	private Template compileTemplate() throws IOException
	{
		List<Object> lst = new ArrayList<>();

		while(tkz.next())
		{
			if(tkz.type() == Tokenizer.T_OPEN_BRACE && tkz.next())
			{
				if(tkz.type() == Tokenizer.T_OPEN_BRACE)
					tkz.inExpression = true;
				else
					tkz.prev();
			}
		}
		return new Template(lst.toArray());
	}

//	private Template compileTemplate() throws IOException
//	{
//		List<Object> lst = new ArrayList<>();
//		
//		StringBuilder sb = new StringBuilder();
//		while((short) (c = (char) rdr.read()) >= 0)
//		{
//			if(c == '{')
//			{
//				int state = 0;
//				rdr.mark(1);
//				c = (char) rdr.read();
//				// If we see {{
//				if(c == '{')
//					state = 1;
//				else if(c == '%')
//					state = 2;
//				
//				if(state == 0)
//				{
//					sb.append('{');
//					rdr.reset();
//				}
//				else
//				{
//					if(sb.length() > 0)
//					{
//						char[] cs = sb.toString().toCharArray();
//						System.out.println(cs);
//						System.out.println("\n-------------\n-------------\n-------------");
//						lst.add(cs);
//						sb.setLength(0);
//					}
//					
//					switch(state)
//					{
//					case 1:
//						lst.add(readValue());
//						break;
//					case 2:
//						break;
//					}
//				}
//			}
//			else
//			{
//				sb.append(c);
//			}
//		}
//
//		if(sb.length() > 0)
//			lst.add(sb.toString().toCharArray());
//		return new Template(lst.toArray());
//	}
//
//	private Value readValue() throws IOException
//	{
//		Stack<Object> stack = new Stack<>();
//		LinkedList<Object> postfix = new LinkedList<>();
//		boolean readLiteral = false;
//		
//		while(true)
//		{
//			ws();
//			c = (char) rdr.read();
//			if((short) c == -1)
//				break;
//			
//			switch(c)
//			{
//			case '"':
//			case '\'':
//				break;
//			}
//		}
//		return new Value(postfix);
//	}

//	private String readString() throws IOException
//	{
//		if(c != '"')
//			throw unexpected();
//		StringBuilder sb = new StringBuilder();
//
//		int i;
//		while(true)
//		{
//			i = rdr.read();
//			if(i == -1)
//				throw eof("string");
//			
//			c = (char) i;
//			if(c == '\\')
//			{
//				i = rdr.read();
//				if(i == -1)
//					throw eof("string");
//
//				c = (char) i;
//
//				if(c == '"')
//					sb.append('"');
//				else if(c == '\\')
//					sb.append('\\');
//				else if(c == '/')
//					sb.append('/');
//				else if(c == 'b')
//					sb.append('\b');
//				else if(c == 'f')
//					sb.append('\f');
//				else if(c == 'n')
//					sb.append('\n');
//				else if(c == 'r')
//					sb.append('\r');
//				else if(c == 't')
//					sb.append('\t');
//				else if(c == 'u')
//				{
//					char[] cs = new char[4];
//					for(int j = 0; j < 4; j++)
//					{
//						i = rdr.read();
//						if(i == -1)
//							throw eof("string");
//						cs[j] = testHex(i);
//					}
//					
//					sb.append((char) Integer.parseInt(new String(cs), 16));
//				}
//				else
//					throw unexpected("character escape");
//			}
//			else if(c != '"')
//				sb.append(c);
//			else
//				break;
//		}
//		
//		return sb.toString();
//	}

//	private void ws() throws IOException
//	{
//		while(true)
//		{
//			rdr.mark(1);
//			c = (char) rdr.read();
//			if(!Character.isWhitespace(c))
//			{
//				rdr.reset();
//				break;
//			}
//		}
//	}
}