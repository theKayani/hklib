package com.hk.ini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.nio.charset.Charset;

public class IniUtil
{
	private static String escape(String str)
	{
		StringBuilder sb = new StringBuilder(str.length());
		char c;
		for(int i = 0; i < str.length(); i++)
		{
			c = str.charAt(i);
			if(((int) c & 0xFF00) != 0)
			{
				String s = Integer.toHexString((int) c).toUpperCase();
				sb.append("\\x");
				for(int j = s.length(); j < 4; j++)
				{
					sb.append('0');
				}
				sb.append(s);
			}
			else if(c == '=')
			{
				sb.append("\\=");
			}
			else if(c == '\'')
			{
				sb.append("\\'");
			}
			else if(c == '\\')
			{
				sb.append("\\\\");
			}
			else if(c == '"')
			{
				sb.append("\\\"");
			}
			else if(c == '\0')
			{
				sb.append("\\0");
			}
			else if(c == '\r')
			{
				sb.append("\\r");
			}
			else if(c == '\b')
			{
				sb.append("\\b");
			}
			else if(c == '\t')
			{
				sb.append("\\t");
			}
			else if(c == '\n')
			{
				sb.append("\\n");
			}
			else
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	private static String unescape(String str)
	{
		if(str.length() > 1 &&
		  (str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"' ||
		   str.charAt(0) == '\'' && str.charAt(str.length() - 1) == '\''))
		{
			str = str.substring(1, str.length() - 1);
		}

		StringBuilder sb = new StringBuilder(str.length());
		char c;
		for(int i = 0; i < str.length(); i++)
		{
			c = str.charAt(i);
			if(c != '\\' || i == str.length() - 1)
			{
				sb.append(c);
				continue;
			}
			c = str.charAt(++i);
			
			if(c == '\\')
				sb.append('\\');
			else if(c == '\'')
				sb.append('\'');
			else if(c == '"')
				sb.append('"');
			else if(c == '0')
				sb.append('\0');
			else if(c == 'b')
				sb.append('\b');
			else if(c == 't')
				sb.append('\t');
			else if(c == 'r')
				sb.append('\r');
			else if(c == 'n')
				sb.append('\n');
			else if(c == ';')
				sb.append(';');
			else if(c == '#')
				sb.append('#');
			else if(c == '=')
				sb.append('=');
			else if(c == ':')
				sb.append(':');
			else
			{
				boolean hasUni = false;
				if(c == 'x' && i < str.length() - 4)
				{
					String hex = "0123456789abcdefABCDEF";
					for(int j = i + 1; j < i + 5; j++)
					{
						if(hex.indexOf(str.charAt(j)) == -1)
						{
							hex = null;
							break;
						}
					}
					if(hex != null)
					{
						hasUni = true;
						sb.append((char) Integer.parseInt(str.substring(i + 1, i + 5), 16));
						i += 4;
					}
				}
				
				if(!hasUni)
				{
					sb.append('\\').append(c);
				}
			}
		}
		return sb.toString();
	}
	
	private static void load(Ini ini, BufferedReader rd) throws IOException
	{
		String line;
		
		StringBuilder comment = new StringBuilder();
		String section = null;
		int eqIndx;
		while((line = rd.readLine()) != null)
		{
			line = line.trim();
			eqIndx = -1;
			for(int i = 0; i < line.length(); i++)
			{
				if(i < line.length() - 1 && line.charAt(i) == '\\')
				{
					i++;
					continue;
				}
				else if(line.charAt(i) == '=')
				{
					eqIndx = i;
					break;
				}
			}
			
			if(line.isEmpty())
			{
				continue;
			}
			else if(line.charAt(0) == ';')
			{
				if(comment.length() > 0)
					comment.append('\n');
				comment.append(line.substring(1));
			}
			else if(line.charAt(0) == '[' && line.charAt(line.length() - 1) == ']')
			{
				section = unescape(line.substring(1, line.length() - 1));
				if(comment.length() > 0)
					ini.setComment(section, comment.toString());
				comment.setLength(0);
			}
			else if(eqIndx >= 0)
			{
				comment.setLength(0);
				String name = "";
				if(eqIndx > 0)
					name = unescape(line.substring(0, eqIndx).trim());

				String val = "";
				if(eqIndx < line.length() - 1)
					val = unescape(line.substring(eqIndx + 1).trim());

				ini.add(section, name, val);
			}
		}
	}
	
	public static Ini loadFrom(CharSequence data)
	{
		Ini ini = new Ini();
		
		String s = data == null ? null : data.toString();
		if(s == null || s.trim().isEmpty())
			return ini;
		
		try
		{
			BufferedReader rd = new BufferedReader(new StringReader(s));
			load(ini, rd);
			rd.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		return ini;
	}
	
	public static Ini loadFrom(File file)
	{
		return loadFrom(file, Charset.defaultCharset());
	}
	
	public static Ini loadFrom(File file, Charset charset)
	{
		if(!file.exists())
			return null;
		
		try
		{
			Ini ini = new Ini();
			BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
			load(ini, rd);
			rd.close();
			return ini;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static void save(Ini ini, File file)
	{
		save(ini, file, Charset.defaultCharset());
	}
	
	public static void save(Ini ini, File file, Charset charset)
	{
		String s = save(ini, new StringBuilder()).toString();
		
		try
		{
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file), charset);
			fw.write(s);
			fw.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static String save(Ini ini)
	{
		return save(ini, new StringBuilder()).toString();
	}
	
	public static StringBuilder save(Ini ini, StringBuilder sb)
	{
		String[] secs = ini.getSections();
		for(int i = 0; i < secs.length; i++)
		{
			String sec = secs[i];
			if(i != 0)
				sb.append('\n');
			String cmt = ini.getComment(sec);
			if(cmt != null)
			{
				String[] sp = cmt.split("\n");
				for(String s : sp)
				{
					sb.append(";");
					sb.append(s);
					sb.append('\n');
				}
			}
			if(!sec.isEmpty())
				sb.append('[').append(IniUtil.escape(sec)).append("]\n");
			String[] keys = ini.getKeys(sec);
			for(int j = 0; j < keys.length; j++)
			{
				String key = keys[j];
				Object[] vals;
				Object o = ini.get(sec, key);
				if(o instanceof Object[])
					vals = (Object[]) o;
				else
					vals = new Object[] { o };
				
				for(int k = 0; k < vals.length; k++)
				{
					sb.append(IniUtil.escape(key));
					sb.append('=');
					sb.append(IniUtil.escape(vals[k].toString()));
					sb.append('\n');
				}
			}
		}
		return sb;
	}
}
