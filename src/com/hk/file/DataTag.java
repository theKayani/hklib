package com.hk.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DataTag implements Serializable
{
	private final Map<String, Serializable> objs;

	public DataTag()
	{
		objs = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name)
	{
		return (T) objs.get(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T getDefault(String name, T defValue)
	{
		return (T) (objs.containsKey(name) ? objs.get(name) : defValue);
	}

	public void set(String name, Serializable obj)
	{
		objs.put(name, obj);
	}

	public void setAll(Map<? extends String, ? extends Serializable> objs)
	{
		this.objs.putAll(objs);
	}

	DataTag load(File f) throws Exception
	{
		return load(new FileInputStream(f));
	}

	@SuppressWarnings("unchecked")
	DataTag load(InputStream in) throws Exception
	{
		GZIPInputStream gin = new GZIPInputStream(in);
		ObjectInputStream oin = new ObjectInputStream(gin);
		objs.putAll((Map<? extends String, ? extends Serializable>) oin.readObject());
		oin.close();
		in.close();
		return this;
	}

	void save(File f) throws Exception
	{
		save(new FileOutputStream(f));
	}

	void save(OutputStream out) throws Exception
	{
		GZIPOutputStream gout = new GZIPOutputStream(out);
		ObjectOutputStream oout = new ObjectOutputStream(gout);
		oout.writeObject(objs);
		oout.close();
		gout.finish();
		gout.close();
		out.close();
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof DataTag && Objects.deepEquals(objs, ((DataTag) obj).objs);
	}

	@Override
	public int hashCode()
	{
		return 201 + Objects.hashCode(objs);
	}

	private static final long serialVersionUID = 2972010173854168060L;
}
