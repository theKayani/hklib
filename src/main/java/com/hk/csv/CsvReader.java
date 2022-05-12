package com.hk.csv;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

/**
 * <p>This class can be used to read comma-separated values from a Java
 * reader. This class also contains some methods/flags that can change
 * the behavior of the parser.</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Comma-separated_values">https://en.wikipedia.org/wiki/Comma-separated_values</a>
 */
public class CsvReader implements Closeable
{
	private final LineNumberReader rdr;
	private boolean ignoreHeader, ignoredHeader, whitespaceTrim,
					skipEmptyLines, ignoreQuotes;

	/**
	 * <p>Create a new CSV reader from a file. This doesn't read
	 * anything until the methods are called.</p>
	 *
	 * @param f a {@link java.io.File} object
	 */
	public CsvReader(@NotNull File f) throws FileNotFoundException
	{
		this(new FileReader(f));
	}

	/**
	 * <p>Create a new CSV reader from a file with a given charset.
	 * This doesn't read anything until the methods are called.</p>
	 *
	 * @param f a {@link java.io.File} object
	 * @param charset charset to use
	 */
	public CsvReader(@NotNull File f, @NotNull Charset charset) throws IOException
	{
		this(new FileInputStream(f), charset);
	}

	/**
	 * <p>Create a new CSV reader from a Java input stream. This doesn't
	 * read anything until the methods are called.</p>
	 *
	 * @param in a {@link java.io.InputStream} object
	 */
	public CsvReader(@NotNull InputStream in)
	{
		this(new InputStreamReader(in));
	}

	/**
	 * <p>Create a new CSV reader from a Java input stream with a
	 * given charset. This doesn't read anything until the methods are
	 * called.</p>
	 *
	 * @param in a {@link java.io.InputStream} object
	 * @param charset charset to use
	 */
	public CsvReader(@NotNull InputStream in, @NotNull Charset charset)
	{
		this(new InputStreamReader(in, charset));
	}

	/**
	 * <p>Create a new CSV reader from a Java reader. This doesn't
	 * read anything until the methods are called.</p>
	 *
	 * @param rdr a {@link java.io.Reader} object
	 */
	public CsvReader(@NotNull Reader rdr)
	{
		this.rdr = new LineNumberReader(Objects.requireNonNull(rdr));
	}

	/**
	 * <p>Set the flag to ignore the first header line. This has no
	 * effect if the first line has already been read.</p>
	 *
	 * @return this
	 */
	@NotNull
	public CsvReader setIgnoreHeader()
	{
		this.ignoreHeader = true;
		return this;
	}

	/**
	 * <p>Unset the flag to ignore the first header line. This has no
	 * effect if the first line has already been read.</p>
	 *
	 * @return this
	 */
	@NotNull
	public CsvReader unsetIgnoreHeader()
	{
		this.ignoreHeader = false;
		return this;
	}

	/**
	 * <p>Set the flag to trim the whitespace off the edges of the
	 * values. If a value is quoted, this will not remove the
	 * whitespace between the quotes.</p>
	 *
	 * @return this
	 */
	@NotNull
	public CsvReader setWhitespaceTrim()
	{
		this.whitespaceTrim = true;
		return this;
	}

	/**
	 * <p>Unset the flag to trim the whitespace off the edges of the
	 * values.</p>
	 *
	 * @see #setWhitespaceTrim()
	 * @return this
	 */
	@NotNull
	public CsvReader unsetWhitespaceTrim()
	{
		this.whitespaceTrim = false;
		return this;
	}

	/**
	 * <p>Set the flag to ignore any blank lines that appear instead
	 * of passing them on.</p>
	 *
	 * @return this
	 */
	@NotNull
	public CsvReader setSkipEmptyLines()
	{
		this.skipEmptyLines = true;
		return this;
	}

	/**
	 * <p>Unset the flag to ignore any blank lines that appear instead
	 * of passing them on.</p>
	 *
	 * @return this
	 */
	@NotNull
	public CsvReader unsetSkipEmptyLines()
	{
		this.skipEmptyLines = false;
		return this;
	}

	/**
	 * <p>Get the line number that this reader is on. <b>NOTE:</b> This
	 * is NOT the row number, but the amount of lines that were read
	 * from this reader so far. This does not move the reader or read
	 * anything from the stream.</p>
	 *
	 * @return the current line number
	 */
	public int getLineNumber()
	{
		return rdr.getLineNumber();
	}

	/**
	 * <p>Reads the next comma-separated values that are retrieved from
	 * the reader. If there are no more characters to read from the
	 * stream, this will return a null array.</p>
	 *
	 * <p>This method can be used to loop through the values. Like so:</p>
	 * <code>
	 * String[] strings;<br>
	 * while((strings = rdr.readLine()) != null)<br>
	 * {<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;// process strings<br>
	 * }
	 * </code>
	 * @return a string array containing the comma-separated values
	 * 			that were read from the supplied reader, or null if
	 * 			there are no more lines to read
	 * @throws IOException if the underlying reader throws an exception
	 */
	@Nullable
	public String[] readLine() throws IOException
	{
		List<String> lst = new ArrayList<>();
		if(readLineInto(lst))
			return lst.toArray(new String[0]);
		return null;
	}

	/**
	 * <p>Collects the comma-separated values and individually stream
	 * them to this consumer. The collected string arrays will never
	 * be null.</p>
	 *
	 * @param consumer a {@link java.util.function.Consumer} object.
	 * @throws IOException if the underlying reader throws an exception
	 */
	public void process(@NotNull Consumer<String[]> consumer) throws IOException
	{
		List<String> lst = new ArrayList<>();

		while(readLineInto(lst))
		{
			consumer.accept(lst.toArray(new String[0]));
			lst.clear();
		}
	}

	/**
	 * <p>Collects the comma-separated values and individually stream
	 * them to this consumer <b>along with the offset-row number</b>.
	 * This means that this method will always start from row 0, even
	 * if rows have already been read. The collected string arrays
	 * will never be null.</p>
	 *
	 * @see #process(Consumer)
	 * @param consumer a {@link java.util.function.ObjIntConsumer} object.
	 * @throws IOException if the underlying reader throws an exception
	 */
	public void process(@NotNull ObjIntConsumer<String[]> consumer) throws IOException
	{
		int row = 0;
		List<String> lst = new ArrayList<>();

		while(readLineInto(lst))
		{
			consumer.accept(lst.toArray(new String[0]), row);
			lst.clear();
			row++;
		}
	}

	private boolean readLineInto(@NotNull List<String> lst) throws IOException
	{
		if(!ignoredHeader)
		{
			ignoredHeader = true;
			if(ignoreHeader && !readLineInto(new ArrayList<>()))
				return false;
		}
		StringBuilder sb = new StringBuilder();
		boolean hasValue = false;
		int i;
		char c;

		do
		{
			rdr.mark(1);
			i = rdr.read();
			if (i == -1)
				return false;
			c = (char) i;
		} while(skipEmptyLines && c == '\n');

		rdr.reset();
		do {
			skipWhitespace();
			i = rdr.read();
			if(i == -1)
			{
				hasValue = true;
				break;
			}

			c = (char) i;
			if(!ignoreQuotes && c == '"')
			{
				while(true)
				{
					i = rdr.read();

					if(i == -1)
						break;

					c = (char) i;

					if (c == '"' || c == '\n')
						break;
					else if (c == '\\')
					{
						i = rdr.read();

						if (i == -1)
							break;
						c = (char) i;

						switch (c)
						{
							case '\\':
							case '"':
								break;
							default:
								sb.append('\\');
						}
					}

					sb.append(c);
				}

				lst.add(sb.toString());
				sb.setLength(0);

				skipWhitespace();
				i = rdr.read();
				c = (char) i;
			}
			else if(c == ',' || c == '\n')
			{
				lst.add("");
			}
			else
			{
				sb.append(c);
				while(true)
				{
					i = rdr.read();

					if(i == -1)
						break;

					c = (char) i;
					if (c != ',' && c != '\n')
						sb.append(c);
					else
						break;
				}
				lst.add(whitespaceTrim ? sb.toString().trim() : sb.toString());
				sb.setLength(0);
			}
		} while(c == ',');

		if(hasValue)
		{
			lst.add(whitespaceTrim ? sb.toString().trim() : sb.toString());
			sb.setLength(0);
		}

		return true;
	}

	private void skipWhitespace() throws IOException
	{
		int i;
		char c;
		if(whitespaceTrim)
		{
			do
			{
				rdr.mark(1);
				i = rdr.read();
				c = (char) i;
			} while(i != -1 && c != '\n' && Character.isWhitespace(c));
			rdr.reset();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws IOException
	{
		rdr.close();
	}
}
