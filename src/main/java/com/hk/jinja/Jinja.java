package com.hk.jinja;

import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;

public class Jinja
{	
	public static TemplateReader reader(Reader rdr)
	{
		return new TemplateReader(rdr);
	}

	public static TemplateReader reader(String str)
	{
		return new TemplateReader(new StringReader(str));
	}

	public static TemplateReader reader(char[] array)
	{
		return new TemplateReader(new CharArrayReader(array));
	}

	public static TemplateReader reader(File file) throws FileNotFoundException
	{
		return new TemplateReader(new FileReader(file));
	}

	public static TemplateReader reader(File file, Charset charset) throws FileNotFoundException
	{
		return new TemplateReader(new InputStreamReader(new FileInputStream(file), charset));
	}

	public static TemplateReader reader(InputStream input)
	{
		return new TemplateReader(new InputStreamReader(input));
	}

	public static TemplateReader reader(InputStream input, Charset charset)
	{
		return new TemplateReader(new InputStreamReader(input, charset));
	}

	public static TemplateReader reader(URL url) throws IOException
	{
		return new TemplateReader(new InputStreamReader(url.openStream()));
	}
}
