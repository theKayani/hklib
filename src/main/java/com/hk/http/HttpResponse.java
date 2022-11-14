package com.hk.http;

import com.hk.io.IOUtil;
import com.hk.json.Json;
import com.hk.json.JsonFormatException;
import com.hk.json.JsonValue;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public class HttpResponse implements Closeable
{
	private final HttpMethod method;
	private final URL url;
	final HttpURLConnection conn;
	private final int responseCode;

	HttpResponse(HttpRequest request) throws IOException
	{
		this.method = request.method;
		this.url = request.url;
		this.conn = request.conn;

		conn.connect();

		responseCode = conn.getResponseCode();
	}

	/**
	 * Get the response header for a given key.
	 *
	 * @param key the header key to search for
	 * @return the value that belongs to this key
	 */
	public String getHeader(String key)
	{
		return conn.getHeaderField(key);
	}

	/**
	 * Get an input stream of bytes from this HTTP response.
	 *
	 * @return an input stream that when closed, also closes the response
	 * @throws IOException if the underlying connection causes an
	 * 						exception
	 */
	public InputStream toInputStream() throws IOException
	{
		return wrap(conn.getInputStream());
	}

	/**
	 * Get an array of bytes from this HTTP response.
	 *
	 * @return an array of bytes
	 * @throws UncheckedIOException if the underlying connection
	 * 								causes an exception
	 */
	public byte[] toBytes()
	{
		try
		{
			InputStream in = toInputStream();
			byte[] bytes = IOUtil.readAll(in);
			in.close();
			return bytes;
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Get a reader of characters from this HTTP response.
	 *
	 * @return a reader that when closed, also closes the response
	 * @throws IOException if the underlying connection causes an
 	 * 						exception or the charset is unknown
	 */
	public Reader toReader() throws IOException
	{
		String charset = conn.getContentEncoding();
		if(charset == null)
			throw new IOException("Unknown connection character encoding");

		return wrap(new InputStreamReader(conn.getInputStream(), charset));
	}

	/**
	 * Get a reader of characters given a specific character encoding
	 * from this HTTP response.
	 *
	 * @return a reader that when closed, also closes the response
	 * @throws IOException if the underlying connection causes an
	 * 						exception
	 */
	public Reader toReader(Charset charset) throws IOException
	{
		return wrap(new InputStreamReader(conn.getInputStream(), charset));
	}

	/**
	 * Get a reader of characters, using the UTF-8 charset from this
	 * HTTP response.
	 *
	 * @return a reader that when closed, also closes the response
	 * @throws IOException if the underlying connection causes an
	 * 						exception
	 */
	public Reader toUTF8Reader() throws IOException
	{
		return toReader(StandardCharsets.UTF_8);
	}

	/**
	 * Get a string from this HTTP response.
	 *
	 * Convert the contents of this HTTP response into a character
	 * array given the specific charset.
	 *
	 * This also closes the connection.
	 *
	 * @throws UncheckedIOException if the underlying connection
	 * 								causes an exception
	 */
	public char[] toCharArray(Charset charset)
	{
		try
		{
			Reader rdr = toReader(charset);
			char[] cs = IOUtil.readAll(rdr);
			rdr.close();
			return cs;
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Get an array of characters from this HTTP response.
	 *
	 * Convert the contents of this HTTP response into a character
	 * array.
	 *
	 * This also closes the connection.
	 *
	 * @throws UncheckedIOException if the underlying connection
	 * 								causes an exception
	 */
	public char[] toCharArray()
	{
		try
		{
			Reader rdr = toReader();
			char[] cs = IOUtil.readAll(rdr);
			rdr.close();
			return cs;
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Get a string from this HTTP response.
	 *
	 * Convert the contents of this HTTP response into a string.
	 *
	 * This also closes the connection.
	 *
	 * @throws UncheckedIOException if the underlying connection
	 * 								causes an exception
	 */
	public String toString()
	{
		return new String(toCharArray());
	}

	/**
	 * Get a string from this HTTP response.
	 *
	 * Convert the contents of this HTTP response into a string given
	 * the specific charset.
	 *
	 * This also closes the connection.
	 *
	 * @throws UncheckedIOException if the underlying connection
	 * 								causes an exception
	 */
	public String toString(Charset charset)
	{
		return new String(toCharArray(charset));
	}

	/**
	 * Write the contents of this response to a given file.
	 *
	 * This also closes the connection.
	 *
	 * @param f the file to write to
	 * @throws UncheckedIOException if the underlying connection or
	 * 								file causes an exception
	 */
	public void toFile(File f)
	{
		try
		{
			InputStream in = toInputStream();
			OutputStream out = new FileOutputStream(f);
			IOUtil.copyTo(in, out);
			out.close();
			in.close();
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Write the contents of this response to a given path.
	 *
	 * This also closes the connection.
	 *
	 * @param p the path to write to
	 * @throws UncheckedIOException if the underlying connection
	 * 								or path causes an exception
	 */
	public void toFile(Path p)
	{
		try
		{
			InputStream in = toInputStream();
			OutputStream out = Files.newOutputStream(p);
			IOUtil.copyTo(in, out);
			out.close();
			in.close();
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Get the HTTP response as a JSON object given a certain charset.
	 *
	 * This also closes the connection.
	 *
	 * @throws UncheckedIOException if the underlying connection causes an
	 * 						exception
	 */
	public JsonValue toJson(Charset charset)
	{
		try
		{
			Reader rdr = toReader(charset);
			JsonValue value = Json.read(rdr);
			rdr.close();
			return value;
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
		catch (JsonFormatException e)
		{
			throw new RuntimeException("error parsing response data into json", e);
		}
	}

	/**
	 * Get the HTTP response as a JSON value.
	 *
	 * This also closes the connection.
	 *
	 * @throws UncheckedIOException if the underlying connection causes an
	 * 						exception
	 */
	public JsonValue toJson()
	{
		Charset charset;
		String encoding = conn.getContentEncoding();
		if(encoding == null)
			charset = StandardCharsets.UTF_8;
		else
			charset = Charset.forName(encoding);

		return toJson(charset);
	}

	/**
	 * Use the provided function to convert the input stream to
	 * another medium.
	 *
	 * This might close the connection depending on the function.
	 *
	 * @throws UncheckedIOException if the underlying connection
	 * 								or function causes an exception
	 */
	public <T> T streamTo(Function<InputStream, T> mapper)
	{
		try
		{
			return mapper.apply(toInputStream());
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Use the provided function to convert the reader to
	 * another medium.
	 *
	 * This might close the connection depending on the function.
	 *
	 * @throws UncheckedIOException if the underlying connection
	 * 								or function causes an exception
	 */
	public <T> T readerTo(Function<Reader, T> mapper)
	{
		try
		{
			return mapper.apply(toReader());
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Get the method used to connect.
	 *
	 * @return the method used to connect
	 */
	public HttpMethod getMethod()
	{
		return method;
	}

	/**
	 * Get the URL the connection was made to.
	 *
	 * @return the URL
	 */
	public URL getUrl()
	{
		return url;
	}

	/**
	 * Get the HTTP response code sent from the server.
	 *
	 * @return the HTTP response code
	 */
	public int getResponseCode()
	{
		return responseCode;
	}

	private InputStream wrap(InputStream input)
	{
		return new FilterInputStream(input) {
			@Override
			public void close() throws IOException
			{
				super.close();
				HttpResponse.this.close();
			}
		};
	}

	private Reader wrap(Reader input)
	{
		return new FilterReader(input) {
			@Override
			public void close() throws IOException
			{
				super.close();
				HttpResponse.this.close();
			}
		};
	}

	/**
	 * Closes the connection of this response
	 */
	@Override
	public void close()
	{
		conn.disconnect();
	}
}
