package com.hk.jinja;

import org.jetbrains.annotations.NotNull;

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

/**
 * <p>Jinja class.</p>
 *
 * @author theKayani
 */
public class Jinja
{	
	/**
	 * <p>reader.</p>
	 *
	 * @param rdr a {@link java.io.Reader} object
	 * @return a {@link com.hk.jinja.TemplateReader} object
	 */
	@NotNull
	public static TemplateReader reader(@NotNull Reader rdr)
	{
		return new TemplateReader(rdr);
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @return a {@link com.hk.jinja.TemplateReader} object
	 */
	@NotNull
	public static TemplateReader reader(@NotNull String str)
	{
		return new TemplateReader(new StringReader(str));
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param array an array of {@link char} objects
	 * @return a {@link com.hk.jinja.TemplateReader} object
	 */
	@NotNull
	public static TemplateReader reader(char[] array)
	{
		return new TemplateReader(new CharArrayReader(array));
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link com.hk.jinja.TemplateReader} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	@NotNull
	public static TemplateReader reader(@NotNull File file) throws FileNotFoundException
	{
		return new TemplateReader(new FileReader(file));
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.jinja.TemplateReader} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	@NotNull
	public static TemplateReader reader(@NotNull File file, @NotNull Charset charset) throws FileNotFoundException
	{
		return new TemplateReader(new InputStreamReader(new FileInputStream(file), charset));
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param input a {@link java.io.InputStream} object
	 * @return a {@link com.hk.jinja.TemplateReader} object
	 */
	@NotNull
	public static TemplateReader reader(@NotNull InputStream input)
	{
		return new TemplateReader(new InputStreamReader(input));
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param input a {@link java.io.InputStream} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.jinja.TemplateReader} object
	 */
	@NotNull
	public static TemplateReader reader(@NotNull InputStream input, @NotNull Charset charset)
	{
		return new TemplateReader(new InputStreamReader(input, charset));
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param url a {@link java.net.URL} object
	 * @return a {@link com.hk.jinja.TemplateReader} object
	 * @throws java.io.IOException if any.
	 */
	@NotNull
	public static TemplateReader reader(@NotNull URL url) throws IOException
	{
		return new TemplateReader(new InputStreamReader(url.openStream()));
	}
}
