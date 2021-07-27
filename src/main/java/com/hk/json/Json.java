package com.hk.json;

import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.hk.collections.lists.SortedList;
import com.hk.io.StringBuilderWriter;

/**
 * <p>Json class.</p>
 *
 * @author theKayani
 */
public class Json
{
	static final List<JsonAdapter<?>> globalAdapters = new SortedList<>();
	
	/**
	 * <p>registerAdapter.</p>
	 *
	 * @param adapter a T object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T extends JsonAdapter<?>> T registerAdapter(T adapter)
	{
		globalAdapters.add(adapter);
		return adapter;
	}

	/**
	 * <p>unregisterAdapter.</p>
	 *
	 * @param adapter a T object
	 * @param <T> a T class
	 * @return a T object
	 */
	public static <T extends JsonAdapter<?>> T unregisterAdapter(T adapter)
	{
		globalAdapters.remove(adapter);
		return adapter;
	}

	/**
	 * <p>Getter for the field <code>globalAdapters</code>.</p>
	 *
	 * @return a {@link java.util.Set} object
	 */
	public static Set<JsonAdapter<?>> getGlobalAdapters()
	{
		return new HashSet<>(globalAdapters);
	}
	
	/**
	 * <p>read.</p>
	 *
	 * @param rdr a {@link java.io.Reader} object
	 * @return a {@link com.hk.json.JsonValue} object
	 * @throws com.hk.json.JsonFormatException if any.
	 */
	public static JsonValue read(Reader rdr) throws JsonFormatException
	{
		JsonReader jr = reader(rdr);
		JsonValue val = jr.get();
		jr.close();
		return val;
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param rdr a {@link java.io.Reader} object
	 * @return a {@link com.hk.json.JsonReader} object
	 */
	public static JsonReader reader(Reader rdr)
	{
		return new JsonReader(rdr);
	}

	/**
	 * <p>read.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @return a {@link com.hk.json.JsonValue} object
	 * @throws com.hk.json.JsonFormatException if any.
	 */
	public static JsonValue read(String str) throws JsonFormatException
	{
		JsonReader jr = reader(str);
		JsonValue val = jr.get();
		jr.close();
		return val;
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @return a {@link com.hk.json.JsonReader} object
	 */
	public static JsonReader reader(String str)
	{
		return new JsonReader(new StringReader(str));
	}

	/**
	 * <p>read.</p>
	 *
	 * @param array an array of {@link char} objects
	 * @return a {@link com.hk.json.JsonValue} object
	 * @throws com.hk.json.JsonFormatException if any.
	 */
	public static JsonValue read(char[] array) throws JsonFormatException
	{
		JsonReader jr = reader(array);
		JsonValue val = jr.get();
		jr.close();
		return val;
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param array an array of {@link char} objects
	 * @return a {@link com.hk.json.JsonReader} object
	 */
	public static JsonReader reader(char[] array)
	{
		return new JsonReader(new CharArrayReader(array));
	}

	/**
	 * <p>read.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link com.hk.json.JsonValue} object
	 * @throws com.hk.json.JsonFormatException if any.
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static JsonValue read(File file) throws JsonFormatException, FileNotFoundException
	{
		JsonReader jr = reader(file);
		JsonValue val = jr.get();
		jr.close();
		return val;
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link com.hk.json.JsonReader} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static JsonReader reader(File file) throws FileNotFoundException
	{
		return new JsonReader(new FileReader(file));
	}

	/**
	 * <p>read.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.json.JsonValue} object
	 * @throws com.hk.json.JsonFormatException if any.
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static JsonValue read(File file, Charset charset) throws JsonFormatException, FileNotFoundException
	{
		JsonReader jr = reader(file, charset);
		JsonValue val = jr.get();
		jr.close();
		return val;
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.json.JsonReader} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static JsonReader reader(File file, Charset charset) throws FileNotFoundException
	{
		return new JsonReader(new InputStreamReader(new FileInputStream(file), charset));
	}

	/**
	 * <p>read.</p>
	 *
	 * @param input a {@link java.io.InputStream} object
	 * @return a {@link com.hk.json.JsonValue} object
	 * @throws com.hk.json.JsonFormatException if any.
	 */
	public static JsonValue read(InputStream input) throws JsonFormatException
	{
		JsonReader jr = reader(input);
		JsonValue val = jr.get();
		jr.close();
		return val;
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param input a {@link java.io.InputStream} object
	 * @return a {@link com.hk.json.JsonReader} object
	 */
	public static JsonReader reader(InputStream input)
	{
		return new JsonReader(new InputStreamReader(input));
	}

	/**
	 * <p>read.</p>
	 *
	 * @param input a {@link java.io.InputStream} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.json.JsonValue} object
	 * @throws com.hk.json.JsonFormatException if any.
	 */
	public static JsonValue read(InputStream input, Charset charset) throws JsonFormatException
	{
		JsonReader jr = reader(input, charset);
		JsonValue val = jr.get();
		jr.close();
		return val;
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param input a {@link java.io.InputStream} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.json.JsonReader} object
	 */
	public static JsonReader reader(InputStream input, Charset charset)
	{
		return new JsonReader(new InputStreamReader(input, charset));
	}

	/**
	 * <p>read.</p>
	 *
	 * @param url a {@link java.net.URL} object
	 * @return a {@link com.hk.json.JsonValue} object
	 * @throws com.hk.json.JsonFormatException if any.
	 * @throws java.io.IOException if any.
	 */
	public static JsonValue read(URL url) throws JsonFormatException, IOException
	{
		JsonReader jr = reader(url);
		JsonValue val = jr.get();
		jr.close();
		return val;
	}

	/**
	 * <p>reader.</p>
	 *
	 * @param url a {@link java.net.URL} object
	 * @return a {@link com.hk.json.JsonReader} object
	 * @throws java.io.IOException if any.
	 */
	public static JsonReader reader(URL url) throws IOException
	{
		return new JsonReader(new InputStreamReader(url.openStream()));
	}
	
	/**
	 * <p>write.</p>
	 *
	 * @param wtr a {@link java.io.Writer} object
	 * @param value a {@link com.hk.json.JsonValue} object
	 */
	public static void write(Writer wtr, JsonValue value)
	{
		JsonWriter jw = writer(wtr);
		jw.put(value);
		jw.close();
	}
	
	/**
	 * <p>writer.</p>
	 *
	 * @param wtr a {@link java.io.Writer} object
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public static JsonWriter writer(Writer wtr)
	{
		return new JsonWriter(wtr);
	}

	/**
	 * <p>write.</p>
	 *
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @return a {@link java.lang.String} object
	 */
	public static String write(JsonValue value)
	{
		return write(new StringBuilder(), value).toString();
	}
	
	/**
	 * <p>writePretty.</p>
	 *
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @return a {@link java.lang.String} object
	 */
	public static String writePretty(JsonValue value)
	{
		StringBuilder sb = new StringBuilder();
		writer(sb).setPrettyPrint().put(value).close();
		return sb.toString();
	}
	
	/**
	 * <p>write.</p>
	 *
	 * @param sb a {@link java.lang.StringBuilder} object
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @return a {@link java.lang.StringBuilder} object
	 */
	public static StringBuilder write(StringBuilder sb, JsonValue value)
	{
		JsonWriter jw = writer(sb);
		jw.put(value);
		jw.close();
		return sb;
	}
	
	/**
	 * <p>writer.</p>
	 *
	 * @param sb a {@link java.lang.StringBuilder} object
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public static JsonWriter writer(StringBuilder sb)
	{
		return new JsonWriter(new StringBuilderWriter(sb));
	}
	
	/**
	 * <p>write.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static void write(File file, JsonValue value) throws FileNotFoundException
	{
		JsonWriter jw = writer(file);
		jw.put(value);
		jw.close();
	}
	
	/**
	 * <p>writer.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link com.hk.json.JsonWriter} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static JsonWriter writer(File file) throws FileNotFoundException
	{
		return new JsonWriter(new OutputStreamWriter(new FileOutputStream(file)));
	}
	
	/**
	 * <p>write.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static void write(File file, Charset charset, JsonValue value) throws FileNotFoundException
	{
		JsonWriter jw = writer(file, charset);
		jw.put(value);
		jw.close();
	}
	
	/**
	 * <p>writer.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.json.JsonWriter} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static JsonWriter writer(File file, Charset charset) throws FileNotFoundException
	{
		return new JsonWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
	}
	
	/**
	 * <p>write.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static void write(OutputStream out, JsonValue value) throws FileNotFoundException
	{
		JsonWriter jw = writer(out);
		jw.put(value);
		jw.close();
	}
	
	/**
	 * <p>writer.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public static JsonWriter writer(OutputStream out)
	{
		return new JsonWriter(new OutputStreamWriter(out));
	}
	
	/**
	 * <p>write.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @param value a {@link com.hk.json.JsonValue} object
	 * @throws java.io.FileNotFoundException if any.
	 */
	public static void write(OutputStream out, Charset charset, JsonValue value) throws FileNotFoundException
	{
		JsonWriter jw = writer(out, charset);
		jw.put(value);
		jw.close();
	}
	
	/**
	 * <p>writer.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object
	 * @param charset a {@link java.nio.charset.Charset} object
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public static JsonWriter writer(OutputStream out, Charset charset)
	{
		return new JsonWriter(new OutputStreamWriter(out, charset));
	}
	
	//	@SuppressWarnings("unchecked")
	/**
	 * <p>fromJson.</p>
	 *
	 * @param val a {@link com.hk.json.JsonValue} object
	 * @return a {@link java.lang.Object} object
	 */
	public static Object fromJson(JsonValue val)
	{
		if(val == null || val.isNull())
			return null;
		else if(val.isBoolean())
			return val.getBoolean();
		else if(val.isString())
			return val.getString();
		else if(val.isNumber())
			return val.getNumber();
		else if(val.isArray())
			return val.getArray().list;
		else if(val.isObject())
			return val.getObject().map;
	
		throw new JsonAdaptationException();
	}

	/**
	 * <p>toJson.</p>
	 *
	 * @param obj a {@link java.lang.Object} object
	 * @return a {@link com.hk.json.JsonValue} object
	 */
	@SuppressWarnings("unchecked")
	public static JsonValue toJson(Object obj)
	{
		if(obj == null)
		{
			return JsonNull.NULL;
		}
		else if(obj instanceof JsonValue)
		{
			return (JsonValue) obj;
		}
		else if(obj instanceof Number)
		{
			if(obj instanceof Long || obj instanceof Integer || obj instanceof Short || obj instanceof Byte)
			{
				return new JsonNumber(((Number) obj).longValue());
			}
			else
			{
				return new JsonNumber(((Number) obj).doubleValue());
			}
		}
		else if(obj instanceof CharSequence || obj instanceof Character)
		{
			return new JsonString(obj.toString());
		}
		else if(obj instanceof Boolean)
		{
			return JsonBoolean.valueOf((Boolean) obj);
		}
		else if(obj instanceof Iterable<?>)
		{
			JsonArray arr = new JsonArray();
			Iterator<Object> itr = ((Iterable<Object>) obj).iterator();
			while(itr.hasNext())
				arr.add(itr.next());

			return arr;
		}
		else if(obj instanceof Map<?, ?>)
		{
			JsonObject o = new JsonObject();
			Map<Object, Object> mp = (Map<Object, Object>) obj;
			Iterator<Map.Entry<Object, Object>> itr = mp.entrySet().iterator();
			Map.Entry<Object, Object> ent;
			while(itr.hasNext())
			{
				ent = itr.next();
				o.put(Objects.toString(ent.getKey()), ent.getValue());
			}
			return o;
		}

		for(JsonAdapter<? extends Object> adapter : globalAdapters)
		{
			if(adapter.getObjClass().isAssignableFrom(obj.getClass()))
				return adapter.tryTo(obj);
		}
		throw new JsonAdaptationException("No adapter for " + obj.getClass().getName());
	}
}