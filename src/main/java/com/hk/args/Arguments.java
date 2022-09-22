package com.hk.args;

import com.hk.str.StrReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * <p>This class handles an array of command-line arguments and breaks it
 * down into different datatypes and properties. Here are some
 * examples...</p>
 *
 * <p><code>Arguments.parseLine("delfiles file1.txt stuff.dat \"Calculus II.pdf\" unused.zip data.json")</code><br>
 * can be parsed and broken into 6 arguments: <code>["delfiles", "file1.txt", "stuff.dat", "Calculus II.pdf", "unused.zip", "data.json"]</code>
 * which can be counted using {@link #count()} and then retrieved
 * using {@link #getArg(int)}</p>
 *
 * <p><code>Arguments.parseLine("copy-file --in /home/john/Desktop/file.txt --out /home/jane/Desktop/file.txt")</code><br>
 * can be broken down using the {@link #option(String)} method to retrieve
 * the <kbd>in</kbd> and <kbd>out</kbd> parameters.</p>
 *
 * <p><code>Arguments.parseLine("run-socket --host localhost --port 8080 -debug")</code><br>
 * similarly using the {@link #option(String)} method to get the
 * options, you can use the {@link #flag(String)} method to check if
 * a certain flag exists. In this case, the <kbd>debug</kbd> flag.</p>
 */
public class Arguments
{
	private final String[] args;
//	private Path workingDirectory;

	/**
	 * Create a new Arguments object based off an array of strings
	 * which are assumed to be the arguments.
	 *
	 * @param args the non-null array of strings
	 */
	public Arguments(String... args)
	{
		this.args = Arrays.copyOf(args, args.length);
		for(String arg : args)
		{
			if(arg == null)
				throw new NullPointerException("args contains null string");
		}
//		workingDirectory = Paths.get(System.getProperty("user.dir"));
	}

	/**
	 * Get the amount of arguments.
	 *
	 * @return The total amount of arguments in this object.
	 */
	public int count()
	{
		return args.length;
	}

	/**
	 * Gets the argument at the index provided.
	 *
	 * @param index The index of the argument to retrieve
	 * @throws IndexOutOfBoundsException if the index is not within
	 *          <code>0</code> and <code>{@link #count()} - 1</code>
	 * @return The specific non-null argument at the specified position.
	 */
	@NotNull
	public String getArg(int index)
	{
		if(index < 0)
			throw new IndexOutOfBoundsException("too low");
		else if(index >= count())
			throw new IndexOutOfBoundsException("too high");
		else
			return args[index];
	}

	/**
	 * Check whether the provided string was one of the arguments
	 * within this arguments object.
	 *
	 * @param str The argument to check for in this array
	 * @return true if the string is
	 */
	public boolean contains(@NotNull String str)
	{
		return index(str) != -1;
	}

	/**
	 * Check the index of a provided string as one of the arguments in
	 * this array. If the object exists, it's index is returned,
	 * otherwise <code>-1</code> is returned.
	 *
	 * @param str The string to match for within this array
	 * @return the index of the string, <code>-1</code> if the string
	 *          isn't an argument.
	 */
	public int index(@NotNull String str)
	{
		return index(str, false);
	}

	/**
	 * Check the index of a provided string as one of the arguments in
	 * this array. If the object exists, it's index is returned,
	 * otherwise <code>-1</code> is returned. If <code>ignoreCase</code>
	 * is true then the string case is ignored.
	 *
	 * @param str The string to match for within this array
	 * @param ignoreCase If true, then finds a match that is not
	 *                      case-sensitive, otherwise finds an exact match.
	 * @return the index of the string, <code>-1</code> if the string
	 *          isn't an argument.
	 */
	public int index(@NotNull String str, boolean ignoreCase)
	{
		if(str == null)
			throw new NullPointerException();
		return find(ignoreCase ? str::equalsIgnoreCase : str::equals);
	}

	/**
	 * <p>Retrieves the value of the option provided within the
	 * arguments.</p>
	 *
	 * <p>For example, if the array looked like: <code>
	 *     ['list', '--host', 'localhost', '--port', '10213']
	 * </code> Then you can use this method to retrieve the values for
	 * the <code>host</code> and <code>port</code> options by doing...<br>
	 * <code>
	 *     String host = arguments.option("host");<br>
	 *     String port = arguments.option("port");
	 * </code></p>
	 * <p>If the option isn't present in the array, returns null.</p>
	 * <p>If there aren't any arguments after an option, then an empty
	 * string is returned.</p>
	 *
	 * @param name The name of the option to test for and retrieve
	 * @return the value of the option provided. If it doesn't exist,
	 *          returns null, if there isn't a value for the option,
	 *          an empty string is returned.
	 */
	@Nullable
	public String option(@NotNull String name)
	{
		int idx = index("--" + name, true);
		if(idx == count() - 1)
			return "";
		else if(idx == -1)
			return null;
		else
			return getArg(idx + 1);
	}


	/**
	 * <p>Checks the existence of the flag provided within the
	 * arguments.</p>
	 *
	 * <p>For example, if the array looked like: <code>
	 *     ['shutdown', '-now', '-do-not-save']
	 * </code> Then you can use this method to check the
	 * <code>now</code> and <code>do-not-save</code> flags by doing...<br>
	 * <code>
	 *     boolean now = arguments.flag("now");<br>
	 *     boolean doNotSave = arguments.option("do-not-save");
	 * </code></p>
	 * <p>These two are true but if the flag isn't present in the
	 * array, returns false.</p>
	 *
	 * @param name The name of the flag to check
	 * @return true if the flag exists in the arguments, otherwise false.
	 */
	public boolean flag(@NotNull String name)
	{
		return contains("-" + name);
	}

	/**
	 * Get a copy of the array of argument strings.
	 *
	 * @return an array of arguments.
	 */
	@NotNull
	public String[] getArgs()
	{
		return Arrays.copyOf(args, args.length);
	}

	/**
	 * Splice this object into a smaller piece of itself.
	 *
	 * @param start a positive value that marks the beginning of the
	 *              section
	 * @return a new <code>Arguments</code> object that is a
	 *          sub-section of this object.
	 */
	@NotNull
	public Arguments sub(int start)
	{
		return sub(start, count());
	}

	/**
	 * Splice this object into a smaller piece of itself.
	 *
	 * @param start a positive value that marks the beginning of the
	 *              section
	 * @param end a positive value, greater than <code>start</code>, but less than
	 *               or equal to {@link #count()} that marks the end
	 *               of the section
	 * @return a new <code>Arguments</code> object that is a
	 *          sub-section of this object.
	 */
	@NotNull
	public Arguments sub(int start, int end)
	{
		if(start < 0)
			throw new IndexOutOfBoundsException("start is too low");
		else if(end > count())
			throw new IndexOutOfBoundsException("end is too high");
		else if(start > end)
			throw new IndexOutOfBoundsException("start cannot be greater than end");
		else
			return new Arguments(Arrays.copyOfRange(args, start, end));
	}

	/**
	 * Test the arguments in order according to a certain predicate.
	 * If when tested against the predicate, if true, then the index
	 * is returned. Otherwise, if false, next argument is tested. If
	 * no argument tested true against the predicate, <code>-1</code>
	 * is returned.
	 *
	 * @param predicate the predicate to test arguments against
	 * @return The first index that tested true when passed to the
	 *          provided predicate. Otherwise, if none is tested true,
	 *          then -1 is returned.
	 */
	public int find(@NotNull Predicate<String> predicate)
	{
		for(int i = 0; i < count(); i++)
		{
			if(predicate.test(getArg(i)))
				return i;
		}
		return -1;
	}

	/**
	 * Collect all the arguments in order using a Java 8
	 * <code>Consumer</code> function.
	 *
	 * @param consumer The function to pass the values in order to.
	 */
	public void forEach(Consumer<String> consumer)
	{
		for(int i = 0; i < count(); i++)
			consumer.accept(args[i]);
	}

	/**
	 * Collect all the arguments in order using a Java 8
	 * <code>Consumer</code> function. This function also passes the
	 * index along with the argument.
	 *
	 * @param consumer The function to pass the values in order to.
	 */
	public void forEach(BiConsumer<Integer, String> consumer)
	{
		for(int i = 0; i < count(); i++)
			consumer.accept(i, args[i]);
	}

	/**
	 * Collect the arguments as a Java 8 stream!
	 *
	 * @return a stream of arguments
	 */
	public Stream<String> stream()
	{
		return Arrays.stream(args);
	}

	/**
	 * <p>Collect the arguments in simple list fashion:</p>
	 * <code>
	 *     [arg1, arg2, arg3, ... , argN]
	 * </code>
	 * @return a string representation of the argument strings
	 */
	@Override
	public String toString()
	{
		return Arrays.toString(args);
	}

	/**
	 * <p>Parses a string of characters into an array of arguments,
	 * similar to a command line entry. Here are some examples of how
	 * this function splits arguments.</p>
	 * <ul>
	 *     <li><code><u>add 1 2</u></code> <b>-&gt;</b> <code>["add", "1", "2"]</code></li>
	 *     <li><code><u>mult 5.5 -1 pi</u></code> <b>-&gt;</b> <code>["mult", "5.5", "-1", "pi"]</code></li>
	 *     <li><code><u>search-for Mockingbird</u></code> <b>-&gt;</b> <code>["search-for", "Mockingbird"]</code></li>
	 *     <li><code><u>search-for "Easy Way"</u></code> <b>-&gt;</b> <code>["search-for", "Easy Way"]</code></li>
	 *     <li><code><u>'single' 'quotes' 'are' also 'quoted' ':D'</u></code> <b>-&gt;</b> <code>["these", "strings", "are", "also", "quoted", ":D"]</code></li>
	 *     <li><code><u>`grave` `accents` can also `be used` for `quoted strings`</u></code> <b>-&gt;</b> <code>["grave", "accents", "can", "also", "be used", "for", "quoted strings"]</code></li>
	 *     <li><code><u>`and` 'can be' `escaped \`` 'escaped \'' "escaped \"" , "mixed" "and" `matched`</u></code> <b>-&gt;</b> <code>["and", "can be", "escaped `", "escaped '", 'escaped "', ",", "mixed", "and", "matched"]</code></li>
	 *     <li><code><u>copy-file "C:\\Program Files\\Photoshop\\image.png" C:\Users\John\Desktop</u></code> <b>-&gt;</b> <code>["copy-file", "C:\Program Files\Photoshop\image.png", "C:\Users\John\Desktop"]</code></li>
	 * </ul>
	 * <p>The whitespace between the arguments can be spaces, tabs, or
	 * newlines. Arguments can be on separate lines and still properly
	 * parsed</p>
	 *
	 * @param line the string to be parsed
	 * @return a new <code>Arguments</code> object.
	 */
	@NotNull
	public static Arguments parseLine(@NotNull String line)
	{
		List<String> args = new ArrayList<>();

		StrReader rdr = new StrReader(line);
		char c;
		while(rdr.hasNext())
		{
			c = rdr.next();

			if(c == '"' || c == '\'' || c == '`')
				args.add(readQuote(rdr));
			else if(!Character.isWhitespace(c))
				args.add(readString(rdr));
		}

		return new Arguments(args.toArray(new String[0]));
	}

	private static String readQuote(StrReader rdr)
	{
		StringBuilder sb = new StringBuilder();
		char quoteChar = rdr.current();
		char c;
		while(rdr.hasNext())
		{
			c = rdr.next();

			if(c == '\\')
			{
				if(rdr.hasNext())
				{
					c = rdr.next();

					if(c == quoteChar)
						sb.append(quoteChar);
					else if(c == '\\')
						sb.append('\\');
					else
					{
						sb.append('\\');
						rdr.prev();
					}
				}
				else
					sb.append('\\');
			}
			else if(c != quoteChar)
				sb.append(c);
			else
				break;
		}

		return sb.toString();
	}

	private static String readString(StrReader rdr)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(rdr.current());

		char c;
		while(rdr.hasNext())
		{
			c = rdr.next();

			if(Character.isWhitespace(c))
				break;
			else
				sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Helper class for building an array of arguments.
	 */
	public static class Builder
	{
		private final List<String> list;

		/**
		 * Creates an empty list of argument string that can be
		 * utilized to build a new <code>Arguments</code> object.
		 */
		public Builder()
		{
			list = new ArrayList<>();
		}

		@NotNull
		public Builder add(@NotNull String arg)
		{
			list.add(arg);
			return this;
		}

		@NotNull
		public Builder addAll(@NotNull String[] args)
		{
			Collections.addAll(list, args);
			return this;
		}

		@NotNull
		public Builder addAll(@NotNull Collection<String> args)
		{
			list.addAll(args);
			return this;
		}

		/**
		 * Collect all the provided argument strings in the
		 * <b>reverse</b> order into a new <code>Arguments</code>
		 * object.
		 *
		 * @return a new <code>Arguments</code> object with the collected arguments.
		 */
		@NotNull
		public Arguments buildReversed()
		{
			String[] arr = new String[list.size()];
			for(int i = 0; i < arr.length; i++)
				arr[i] = list.get(arr.length - i - 1);

			return new Arguments(arr);
		}

		/**
		 * Collect all the provided argument strings into a new
		 * <code>Arguments</code> object.
		 *
		 * @return a new <code>Arguments</code> object with the collected arguments.
		 */
		@NotNull
		public Arguments build()
		{
			return new Arguments(list.toArray(new String[0]));
		}
	}
}
