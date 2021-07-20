package com.hk.json;

import java.io.BufferedWriter;
import java.io.IOException;
import com.hk.ex.UncheckedIOException;
import java.io.Writer;
import java.util.Map;

import com.hk.math.MathUtil;
import com.hk.str.StringUtil;

/**
 * <p>JsonWriter class.</p>
 *
 * @author theKayani
 */
public class JsonWriter
{
	private final BufferedWriter wtr;
	@SuppressWarnings("unused")
	private boolean prettyPrint, ignoreNulls, unicodeEscape, slashEscape, singleQuoteEscape;
	private int tabs;
	
	JsonWriter(Writer wtr)
	{
		this.wtr = new BufferedWriter(wtr);
	}
	
	/**
	 * <p>unsetPrettyPrint.</p>
	 *
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter unsetPrettyPrint()
	{
		this.prettyPrint = false;
		return this;
	}
	
	/**
	 * <p>unsetIgnoreNulls.</p>
	 *
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter unsetIgnoreNulls()
	{
		this.ignoreNulls = false;
		return this;
	}
	
	/**
	 * <p>unsetUnicodeEscape.</p>
	 *
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter unsetUnicodeEscape()
	{
		this.unicodeEscape = false;
		return this;
	}
	
	/**
	 * <p>unsetSlashEscape.</p>
	 *
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter unsetSlashEscape()
	{
		this.slashEscape = false;
		return this;
	}
	
	/**
	 * <p>unsetSingleQuoteEscape.</p>
	 *
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter unsetSingleQuoteEscape()
	{
		this.singleQuoteEscape = false;
		return this;
	}
	
	/**
	 * <p>Setter for the field <code>singleQuoteEscape</code>.</p>
	 *
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter setSingleQuoteEscape()
	{
		this.singleQuoteEscape = true;
		return this;
	}
	
	/**
	 * <p>Setter for the field <code>slashEscape</code>.</p>
	 *
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter setSlashEscape()
	{
		this.slashEscape = true;
		return this;
	}
	
	/**
	 * <p>Setter for the field <code>unicodeEscape</code>.</p>
	 *
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter setUnicodeEscape()
	{
		this.unicodeEscape = true;
		return this;
	}
	
	/**
	 * <p>Setter for the field <code>ignoreNulls</code>.</p>
	 *
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter setIgnoreNulls()
	{
		this.ignoreNulls = true;
		return this;
	}
	
	/**
	 * <p>Setter for the field <code>prettyPrint</code>.</p>
	 *
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter setPrettyPrint()
	{
		this.prettyPrint = true;
		return this;
	}
	
	/**
	 * <p>put.</p>
	 *
	 * @param val a {@link com.hk.json.JsonValue} object
	 * @return a {@link com.hk.json.JsonWriter} object
	 */
	public JsonWriter put(JsonValue val)
	{
		try
		{
			tabs = 0;
			writeValue(val);
		}
		catch(IOException ex)
		{
			throw new UncheckedIOException(ex);
		}
		return this;
	}
	
	/**
	 * <p>close.</p>
	 */
	public void close()
	{
		try
		{
			wtr.close();
		}
		catch (IOException ex)
		{
			throw new UncheckedIOException(ex);
		}
	}

	private void writeValue(JsonValue val) throws IOException
	{
		if(val == null || val.isNull())
			writeNull();
		else if(val.isObject())
			writeObject(val.getObject());
		else if(val.isArray())
			writeArray(val.getArray());
		else if(val.isString())
			writeString(val.getString());
		else if(val.isNumber())
			writeNumber(val.getNumber());
		else if(val.isBoolean())
			writeBoolean(val.getBoolean());
		wtr.flush();
	}

	private void writeObject(JsonObject obj) throws IOException
	{
		if(obj.isEmpty())
		{
			wtr.write(prettyPrint ? "{ }" : "{}");
		}
		else
		{
			tabs++;
			wtr.write('{');
			if(prettyPrint)
				wtr.write('\n');

			int i = 0;
			int size = obj.size();
			for(Map.Entry<String, JsonValue> entry : obj)
			{
				if(prettyPrint)
					wtr.write(StringUtil.repeat("\t", tabs));
				
				writeString(entry.getKey());
				wtr.write(':');
				if(prettyPrint)
					wtr.write(' ');
				writeValue(entry.getValue());
				
				if(i++ < size - 1)
					wtr.write(',');
				
				if(prettyPrint)
					wtr.write('\n');
			}
			tabs--;
			if(prettyPrint)
				wtr.write(StringUtil.repeat("\t", tabs));
			wtr.write('}');
		}
	}

	private void writeArray(JsonArray arr) throws IOException
	{
		if(arr.isEmpty())
		{
			wtr.write(prettyPrint ? "[ ]" : "[]");
		}
		else
		{
			tabs++;
			wtr.write('[');
			if(prettyPrint)
				wtr.write('\n');

			int i = 0;
			int size = arr.size();
			for(JsonValue val : arr)
			{
				if(prettyPrint)
					wtr.write(StringUtil.repeat("\t", tabs));
				writeValue(val);

				if(i++ < size - 1)
					wtr.write(',');

				if(prettyPrint)
					wtr.write('\n');
			}
			tabs--;
			if(prettyPrint)
				wtr.write(StringUtil.repeat("\t", tabs));
			wtr.write(']');
		}
	}

	private void writeString(String str) throws IOException
	{
		wtr.write('"');
		char c;
		for(int i = 0; i < str.length(); i++)
		{
			c = str.charAt(i);
			
			if(c == '"' || c == '\\' || (c == '/' && slashEscape) || (c == '\'' && singleQuoteEscape))
			{
				wtr.write('\\');
				wtr.write(c);
			}
			else if(c == '\b')
			{
				wtr.write("\\b");
			}
			else if(c == '\f')
			{
				wtr.write("\\f");
			}
			else if(c == '\n')
			{
				wtr.write("\\n");
			}
			else if(c == '\r')
			{
				wtr.write("\\r");
			}
			else if(c == '\t')
			{
				wtr.write("\\t");
			}
			else if((int) c >= 127)
			{
				wtr.write("\\u");
				wtr.write(MathUtil.shortHex(c));
			}
			else
				wtr.write(c);
		}
		wtr.write('"');
	}

	private void writeNumber(Number num) throws IOException
	{
		if(num instanceof Float || num instanceof Double)
			wtr.write(Double.toString(num.doubleValue()));
		else
			wtr.write(Long.toString(num.longValue()));
	}

	private void writeBoolean(boolean val) throws IOException
	{
		wtr.write(val ? "true" : "false");
	}

	private void writeNull() throws IOException
	{
		wtr.write("null");
	}
}
