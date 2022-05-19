package com.hk.csv;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * <p>This class can be used to write comma-separated values to a Java
 * writer. This class also contains some methods/flags that can change
 * the behavior of the writer.</p>
 *
 * @see CsvReader
 * @see <a href="https://en.wikipedia.org/wiki/Comma-separated_values">https://en.wikipedia.org/wiki/Comma-separated_values</a>
 */
public class CsvWriter implements Closeable, Consumer<Object[]>
{
	private final Writer wtr;
	private int line = 0;
	private boolean quoteAll, quoteStrings, noQuote,
					onlyPrims, skipEmptyLines;
	private String nullString = "", valPrefix, valPostfix;

	/**
	 * <p>Create a new CSV writer from a file. This doesn't write
	 * anything until the methods are called.</p>
	 *
	 * @param f a {@link java.io.File} object
	 * @throws FileNotFoundException if the parent-file does not exist
	 */
	public CsvWriter(@NotNull File f) throws IOException
	{
		this(new FileWriter(f));
	}

	/**
	 * <p>Create a new CSV writer from a file using the given charset.
	 * This doesn't write anything until the methods are called.</p>
	 *
	 * @param f a {@link java.io.File} object
	 * @param charset charset to use
	 * @throws IOException if the underlying stream throws an exception
	 */
	public CsvWriter(@NotNull File f, @NotNull Charset charset) throws IOException
	{
		this(new FileOutputStream(f), charset);
	}

	/**
	 * <p>Create a new CSV writer from a Java writer. This doesn't
	 * write anything until the methods are called.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object
	 */
	public CsvWriter(@NotNull OutputStream out)
	{
		this(new OutputStreamWriter(out));
	}

	/**
	 * <p>Create a new CSV writer from a Java writer using the given
	 * charset. This doesn't write anything until the methods are
	 * called.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object
	 * @param charset charset to use
	 */
	public CsvWriter(@NotNull OutputStream out, @NotNull Charset charset)
	{
		this(new OutputStreamWriter(out, charset));
	}

	/**
	 * <p>Create a new CSV writer from a Java writer. This doesn't
	 * write anything until the methods are called.</p>
	 *
	 * @param wtr a {@link java.io.Writer} object
	 */
	public CsvWriter(@NotNull Writer wtr)
	{
		this.wtr = Objects.requireNonNull(wtr);
	}

	/**
	 * <p>Set the flag to only write primitive values, which are</p>
	 * <ul>
	 *     <li>{@link CharSequence}</li>
	 *     <li>{@link Character}</li>
	 *     <li>{@link Byte}</li>
	 *     <li>{@link Short}</li>
	 *     <li>{@link Integer}</li>
	 *     <li>{@link Long}</li>
	 *     <li>{@link Float}</li>
	 *     <li>{@link Double}</li>
	 *     <li>{@link Boolean}</li>
	 * </ul>
	 * <p>Any other values will throw an {@link UnsupportedOperationException}.
	 * Also, this cannot be unset as to prevent improper use.</p>
	 * @return this
	 */
	public CsvWriter setOnlyPrimitive()
	{
		onlyPrims = true;
		return this;
	}

	/**
	 * Set the flag to quote all values regardless of their type or
	 * whether they contain commas or new line characters.
	 *
	 * @return this
	 */
	public CsvWriter setQuoteAll()
	{
		quoteAll = true;
		return this;
	}

	/**
	 * Unset the flag to quote all values regardless of their type or
	 * whether they contain commas or new line characters.
	 *
	 * @see #setQuoteAll()
	 * @return this
	 */
	public CsvWriter unsetQuoteAll()
	{
		quoteAll = false;
		return this;
	}

	/**
	 * Set the flag to quote all string types that are passed to the
	 * write function. This won't quote primitive types like integers,
	 * but will quote the {@link Character} class.
	 *
	 * @return this
	 */
	public CsvWriter setQuoteStrings()
	{
		quoteStrings = true;
		return this;
	}

	/**
	 * Unset the flag to quote all string types that are passed to the
	 * write function. This won't quote primitive types like integers,
	 * but will quote the {@link Character} class.
	 *
	 * @see #setQuoteStrings()
	 * @return this
	 */
	public CsvWriter unsetQuoteStrings()
	{
		quoteStrings = false;
		return this;
	}

	/**
	 * Set the flag to ignore all quoting when writing values. This
	 * can lead to issues as strings containing commas or new lines
	 * aren't properly stored within the columns resulting is
	 * potential loss of data.
	 *
	 * @return this
	 */
	public CsvWriter setQuoteNone()
	{
		noQuote = true;
		return this;
	}

	/**
	 * Unset the flag to ignore all quoting when writing values. This
	 * can lead to issues as strings containing commas or new lines
	 * aren't properly stored within the columns resulting in
	 * potential loss of data.
	 *
	 * @see #setQuoteNone()
	 * @return this
	 */
	public CsvWriter unsetQuoteNone()
	{
		noQuote = false;
		return this;
	}

	/**
	 * <p>Set the flag to ignore any blank lines that appear instead
	 * of writing them on.</p>
	 *
	 * @return this
	 */
	@NotNull
	public CsvWriter setSkipEmptyLines()
	{
		this.skipEmptyLines = true;
		return this;
	}

	/**
	 * <p>Unset the flag to ignore any blank lines that appear instead
	 * of writing them on.</p>
	 *
	 * @return this
	 */
	@NotNull
	public CsvWriter unsetSkipEmptyLines()
	{
		this.skipEmptyLines = false;
		return this;
	}

	/**
	 * Set the string to use when a null value is passed into the
	 * writer. This is an empty string by default.
	 *
	 * @param nullString The string to use when a null value is encountered
	 * @return this
	 */
	public CsvWriter setNullString(@NotNull String nullString)
	{
		this.nullString = Objects.requireNonNull(nullString);
		return this;
	}

	/**
	 * Get the string to use for null-supplied values
	 *
	 * @return null string to use for this writer
	 */
	@NotNull
	public String getNullString()
	{
		return nullString;
	}

	/**
	 * Set the value prefix. The value prefix is a string that should
	 * be written before each value is written. This would go at the
	 * beginning of the line or after the comma, if there is one.
	 *
	 * @param valPrefix the string to write before each value
	 * @return this
	 */
	public CsvWriter setValuePrefix(@Nullable String valPrefix)
	{
		this.valPrefix = valPrefix;
		return this;
	}

	/**
	 * Get the value prefix. The value prefix is a string that should
	 * be written before each value is written. This would go at the
	 * beginning of the line or after the comma, if there is one.
	 *
	 * @see #setValuePrefix(String)
	 * @return the string to write before each value
	 */
	@Nullable
	public String getValuePrefix()
	{
		return valPrefix;
	}

	/**
	 * Set the value postfix. The value postfix is a string that should
	 * be written after each value is written. This would go at the
	 * end of the line or before the comma, if there is one.
	 *
	 * @param valPostfix the string to write after each value
	 * @return this
	 */
	public CsvWriter setValuePostfix(@Nullable String valPostfix)
	{
		this.valPostfix = valPostfix;
		return this;
	}

	/**
	 * Get the value postfix. The value postfix is a string that should
	 * be written after each value is written. This would go at the
	 * end of the line or before the comma, if there is one.
	 *
	 * @see #setValuePostfix(String)
	 * @return the string to write after each value
	 */
	@Nullable
	public String getValuePostfix()
	{
		return valPostfix;
	}

	/**
	 * Return the amount of lines that have been written by this object.
	 *
	 * @return total number of lines written
	 */
	public int getLineNumber()
	{
		return line;
	}

	/** {@inheritDoc} */
	@Override
	public void accept(Object[] objects)
	{
		try
		{
			writeLine(objects);
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Write a list of comma-separated values to the underlying writer.
	 * This will perform the proper quoting that needs to be done to
	 * properly write the values in a comma-separated line. Using the
	 * flags specified by the other methods can be used to alter the
	 * value-writing behavior. By default, if a value contains a comma
	 * or a new line character, the string is properly quoted and written.
	 *
	 * @param values The list of values to write
	 * @throws IOException if the underlying writer throws an exception
	 */
	public void writeLine(Object @NotNull ... values) throws IOException
	{
		Objects.requireNonNull(values);
		StringBuilder sb = new StringBuilder();
		boolean nl = false;

		for (int i = 0; i < values.length; i++)
		{
			boolean isPrim = values[i] == null || prims.contains(values[i].getClass());
			if(onlyPrims && !isPrim && !(values[i] instanceof Character || values[i] instanceof CharSequence))
				throw new UnsupportedOperationException("only primitive values can be written to this writer");

			String value = values[i] == null ? nullString : values[i].toString();

			if (!noQuote && (quoteAll || (quoteStrings && !isPrim) || value.contains(",") || value.contains("\n")))
			{
				sb.ensureCapacity(value.length() + 2);
				sb.append('"');
				for (int j = 0; j < value.length(); j++)
				{
					switch (value.charAt(j))
					{
						case '"':
							sb.append("\\\"");
							break;
						case '\\':
							sb.append("\\\\");
							break;
						case '\n':
							sb.append("\\n");
							break;
						default:
							sb.append(value.charAt(j));
					}
				}
				sb.append('"');
				value = sb.toString();
				sb.setLength(0);
			}

			if(line > 0 && !nl)
			{
				wtr.write('\n');
				nl = true;
			}

			if(valPrefix != null)
				wtr.write(valPrefix);
			wtr.write(value);
			if(valPostfix != null)
				wtr.write(valPostfix);
			if(i < values.length - 1)
				wtr.write(',');
		}
		if(line > 0 && !nl && !skipEmptyLines)
			wtr.write('\n');
		line++;
	}

	/**
	 * Process a supplier that will provide the values to separate.
	 * This will keep executing until the supplier returns a null
	 * value. Even empty arrays will be written as empty lines.
	 *
	 * @param supplier supplier to feed this writer
	 * @throws IOException if the underlying writer throws an exception
	 * @return this
	 */
	public CsvWriter process(@NotNull Supplier<? extends Object[]> supplier) throws IOException
	{
		Object[] next;

		while((next = supplier.get()) != null)
			accept(next);
		return this;
	}

	/**
	 * Collect arrays of values from a stream and feed them into this
	 * writer.
	 *
	 * @param stream to read from
	 * @return this
	 */
	public CsvWriter accept(@NotNull Stream<? extends Object[]> stream)
	{
		stream.forEach(this);
		return this;
	}

	/**
	 * Collect arrays of values from an iterator stream and feed them
	 * into this writer.
	 *
	 * @param iterator to read from
	 * @return this
	 */
	public CsvWriter accept(@NotNull Iterator<? extends Object[]> iterator)
	{
		iterator.forEachRemaining(this);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws IOException
	{
		wtr.close();
	}

	private static final Set<Class<?>> prims;

	static
	{
		prims = new HashSet<>();

		prims.add(byte.class);
		prims.add(Byte.class);
		prims.add(short.class);
		prims.add(Short.class);
		prims.add(int.class);
		prims.add(Integer.class);
		prims.add(float.class);
		prims.add(Float.class);
		prims.add(double.class);
		prims.add(Double.class);
		prims.add(long.class);
		prims.add(Long.class);
		prims.add(boolean.class);
		prims.add(Boolean.class);
	}
}