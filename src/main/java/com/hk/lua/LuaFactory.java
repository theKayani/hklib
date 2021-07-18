package com.hk.lua;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import com.hk.func.Consumer;

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
	
	public void addLibrary(LuaLibrary<?> lib)
	{
		if(!libs.contains(lib))
			libs.add(lib);
	}
	
	public void addHandler(Consumer<LuaInterpreter> handler)
	{
		if(!handlers.contains(handler))
			handlers.add(handler);
	}
	
	public void removeLibrary(LuaLibrary<?> lib)
	{
		libs.remove(lib);
	}
	
	public void removeHandler(Consumer<LuaInterpreter> handler)
	{
		handlers.remove(handler);
	}
	
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
