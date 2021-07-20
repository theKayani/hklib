package com.hk.lua;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import com.hk.func.Consumer;

/**
 * <p>LuaFactory class.</p>
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
	 * <p>addLibrary.</p>
	 *
	 * @param lib a {@link com.hk.lua.LuaLibrary} object
	 */
	public void addLibrary(LuaLibrary<?> lib)
	{
		if(!libs.contains(lib))
			libs.add(lib);
	}
	
	/**
	 * <p>addHandler.</p>
	 *
	 * @param handler a {@link com.hk.func.Consumer} object
	 */
	public void addHandler(Consumer<LuaInterpreter> handler)
	{
		if(!handlers.contains(handler))
			handlers.add(handler);
	}
	
	/**
	 * <p>removeLibrary.</p>
	 *
	 * @param lib a {@link com.hk.lua.LuaLibrary} object
	 */
	public void removeLibrary(LuaLibrary<?> lib)
	{
		libs.remove(lib);
	}
	
	/**
	 * <p>removeHandler.</p>
	 *
	 * @param handler a {@link com.hk.func.Consumer} object
	 */
	public void removeHandler(Consumer<LuaInterpreter> handler)
	{
		handlers.remove(handler);
	}
	
	/**
	 * <p>compile.</p>
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
		}
	}
	
	/**
	 * <p>build.</p>
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
