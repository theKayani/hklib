package com.hk.lua;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>Lua factories can be used to create various
 * {@link LuaInterpreter} objects without the need of recompiling.
 * This can be created using any of the {@link Lua#factory} methods.</p>
 * <p>Once the factory is created, {@link #compile()} can be used to
 * parse the reader and close it. Once compiled, the {@link #build()}
 * method retrieves a new LuaInterpreter every time.</p>
 * <p>Prior to building any interpreters, handlers can be attached
 * which are called before the interpreter is returned by
 * {@link #build()}. Libraries can also be added which would be
 * injected into each interpreter built.</p>
 *
 * @author theKayani
 */
public class LuaFactory
{
	private final String source;
	private final List<LuaLibrary<?>> libs;
	private final List<Consumer<LuaInterpreter>> handlers;
	private Reader reader;
	private LuaStatement[] sts;

	LuaFactory(Reader reader, String source)
	{
		this.reader = reader;
		this.source = source;

		libs = new LinkedList<>();
		handlers = new LinkedList<>();
	}

	/**
	 * <p>Add a library to be injected into each built interpreter.
	 * This means that the retrieved interpreter does not need this
	 * library added to it.</p>
	 *
	 * @param lib a {@link com.hk.lua.LuaLibrary}
	 */
	public void addLibrary(LuaLibrary<?> lib)
	{
		if(!libs.contains(lib))
			libs.add(lib);
	}

	/**
	 * <p>Handlers are called on each interpreter built before they
	 * are returned. This happens after libraries are injected.</p>
	 *
	 * @param handler a {@link java.util.function.Consumer} object
	 */
	public void addHandler(Consumer<LuaInterpreter> handler)
	{
		if(!handlers.contains(handler))
			handlers.add(handler);
	}

	/**
	 * <p>Remove a specific library if it was previously added using
	 * the {@link #addLibrary(LuaLibrary)} method. If the given
	 * library doesn't exist, this doesn't do anything.</p>
	 *
	 * @param lib a {@link com.hk.lua.LuaLibrary} object
	 */
	public void removeLibrary(LuaLibrary<?> lib)
	{
		libs.remove(lib);
	}

	/**
	 * <p>Remove a handler from this factory. The handler would only
	 * be removed if it was previously added using the {@link #addHandler(Consumer)}
	 * method.</p>
	 *
	 * @param handler a {@link java.util.function.Consumer} object
	 */
	public void removeHandler(Consumer<LuaInterpreter> handler)
	{
		handlers.remove(handler);
	}

	/**
	 * <p>Fully read the reader and attempt to compile the Lua source
	 * into an executable chunk. If there are any issues during
	 * compilation, this method will throw them.</p>
	 * <p>This must be called before {@link #build()} can be called.</p>
	 *
	 * @throws java.io.IOException if any.
	 */
	public void compile() throws IOException
	{
		if(sts != null)
			return;

		Tokenizer tkz = new Tokenizer(reader);
		try
		{
			sts = LuaInterpreter.readStatements(tkz, source, 0);
			if(tkz.next())
				throw LuaInterpreter.unexpected(tkz, source);
		}
		catch(LuaException e)
		{
			if(!e.primary)
				throw e;
			else
				throw new LuaException(source, tkz.line(), e.getLocalizedMessage());
		}
		finally
		{
			reader.close();
			reader = null;
		}
	}

	/**
	 * <p>Build and return a new {@link LuaInterpreter} object
	 * containing the given libraries as well as executing the
	 * handlers that were added previously.</p>
	 *
	 * @return a {@link com.hk.lua.LuaInterpreter} object
	 */
	public LuaInterpreter build()
	{
		if(sts == null)
			throw new IllegalStateException("compile() must be called before build()");

		LuaInterpreter interp = new LuaInterpreter(sts, source);

		for(LuaLibrary<?> lib : libs)
			interp.importLib(lib);

		for(Consumer<LuaInterpreter> handler : handlers)
			handler.accept(interp);

		return interp;
	}
}