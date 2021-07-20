package com.hk.ini;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hk.collections.lists.ListUtil;

/**
 * <p>Ini class.</p>
 *
 * @author theKayani
 */
public class Ini
{
	private final Map<String, Map<String, Object>> data;
	
	/**
	 * <p>Constructor for Ini.</p>
	 */
	public Ini()
	{
		data = new LinkedHashMap<>();
	}
	
	/**
	 * <p>remove.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @return a {@link com.hk.ini.Ini} object
	 */
	public Ini remove(String section, String key)
	{
		section = section == null ? "" : section;
		key = key == null ? "" : key;
		Map<String, Object> map = data.get(section);
		if(map != null)
			map.remove(key);
		return this;
	}
	
	/**
	 * <p>removeSection.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @return a {@link com.hk.ini.Ini} object
	 */
	public Ini removeSection(String section)
	{
		data.remove(section == null ? "" : section);
		return this;
	}
	
	/**
	 * <p>set.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @param value a {@link java.lang.Object} object
	 * @return a {@link com.hk.ini.Ini} object
	 */
	public Ini set(String section, String key, Object value)
	{
		section = section == null ? "" : section;
		key = key == null ? "" : key;
		Map<String, Object> map = data.get(section);
		if(map == null)
			data.put(section, map = new LinkedHashMap<>());
		
		if(value instanceof Collection)
		{
			value = new ArrayList<>((Collection<?>) value);
		}
		else if(value instanceof Object[])
		{
			List<Object> lst = new ArrayList<>();
			ListUtil.addElements(lst, (Object[]) value);
			value = lst;
		}
		else
			value = value == null ? "" : value.toString();
		
		map.put(key, value);
		return this;
	}
	
	/**
	 * <p>add.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @param value a {@link java.lang.Object} object
	 * @return a {@link com.hk.ini.Ini} object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Ini add(String section, String key, Object value)
	{
		section = section == null ? "" : section;
		key = key == null ? "" : key;
		Map<String, Object> map = data.get(section);
		Object o;
		if(map == null)
		{
			data.put(section, map = new LinkedHashMap<>());
			o = null;
		}
		else
		{
			o = map.get(key);
		}

		if(value instanceof Collection)
			value = new ArrayList<Object>((Collection<?>) value);
		else
			value = value == null ? "" : value.toString();

		if(o == null)
		{
			map.put(key, value);
		}
		else if(o instanceof List)
		{
			if(value instanceof List)
				((List) o).addAll((List) value);
			else
				((List) o).add(value);
		}
		else
		{
			List<Object> lst = new ArrayList<>();
			lst.add(o);
			if(value instanceof List)
				lst.addAll((List) value);
			else
				lst.add(value);
			map.put(key, lst);
		}
		return this;
	}
	
	/**
	 * <p>get.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @return a {@link java.lang.Object} object
	 */
	public Object get(String section, String key)
	{
		return get(section, key, null);
	}
	
	/**
	 * <p>getString.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @param def a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public String getString(String section, String key, String def)
	{
		section = section == null ? "" : section;
		key = key == null ? "" : key;
		Map<String, Object> map = data.get(section);
		if(map == null)
			return def;
		
		Object o = map.get(key);
		
		return o instanceof String ? (String) o : def;
	}
	
	/**
	 * <p>getInt.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @return a int
	 */
	public int getInt(String section, String key)
	{
		return getInt(section, key, 0);
	}
	
	/**
	 * <p>getInt.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @param def a int
	 * @return a int
	 */
	public int getInt(String section, String key, int def)
	{
		return (int) getLong(section, key, def);
	}
	
	/**
	 * <p>getLong.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @return a long
	 */
	public long getLong(String section, String key)
	{
		return getLong(section, key, 0);
	}
	
	/**
	 * <p>getLong.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @param def a long
	 * @return a long
	 */
	public long getLong(String section, String key, long def)
	{
		section = section == null ? "" : section;
		key = key == null ? "" : key;
		Map<String, Object> map = data.get(section);
		if(map == null)
			return def;
		
		Object o = map.get(key);
		if(o instanceof String)
		{
			try
			{
				return Long.parseLong((String) o);
			}
			catch(NumberFormatException e)
			{}
		}
		return def;
	}
	
	/**
	 * <p>getFloat.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @return a float
	 */
	public float getFloat(String section, String key)
	{
		return getFloat(section, key, 0);
	}
	
	/**
	 * <p>getFloat.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @param def a float
	 * @return a float
	 */
	public float getFloat(String section, String key, float def)
	{
		return (float) getDouble(section, key, def);
	}
	
	/**
	 * <p>getDouble.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @return a double
	 */
	public double getDouble(String section, String key)
	{
		return getDouble(section, key, 0);
	}
	
	/**
	 * <p>getDouble.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @param def a double
	 * @return a double
	 */
	public double getDouble(String section, String key, double def)
	{
		section = section == null ? "" : section;
		key = key == null ? "" : key;
		Map<String, Object> map = data.get(section);
		if(map == null)
			return def;
		
		Object o = map.get(key);
		if(o instanceof String)
		{
			try
			{
				return Double.parseDouble((String) o);
			}
			catch(NumberFormatException e)
			{}
		}
		return def;
	}
	
	/**
	 * <p>get.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @param def a {@link java.lang.Object} object
	 * @return a {@link java.lang.Object} object
	 */
	@SuppressWarnings("rawtypes")
	public Object get(String section, String key, Object def)
	{
		section = section == null ? "" : section;
		key = key == null ? "" : key;
		Map<String, Object> map = data.get(section);
		if(map == null)
			return def;
		
		Object o = map.get(key);
		
		return o instanceof List ? ((List) o).toArray() : (o == null ? def : o);
	}
	
	/**
	 * <p>getArray.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @return an array of {@link java.lang.Object} objects
	 */
	public Object[] getArray(String section, String key)
	{
		return getArray(section, key, null);
	}
	
	/**
	 * <p>getArray.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param key a {@link java.lang.String} object
	 * @param def an array of {@link java.lang.Object} objects
	 * @return an array of {@link java.lang.Object} objects
	 */
	public Object[] getArray(String section, String key, Object[] def)
	{
		Object o = get(section, key, def);
		return o instanceof Object[] ? (Object[]) o : def;
	}
	
	/**
	 * <p>getSections.</p>
	 *
	 * @return an array of {@link java.lang.String} objects
	 */
	public String[] getSections()
	{
		return data.keySet().toArray(new String[0]);
	}
	
	/**
	 * <p>getKeys.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @return an array of {@link java.lang.String} objects
	 */
	public String[] getKeys(String section)
	{
		Map<String, Object> map = data.get(section == null ? "" : section);
		if(map == null)
			return null;
		
		List<String> keys = new ArrayList<>();
		for(String s : map.keySet())
		{
			if(s != null)
				keys.add(s);
		}
		return keys.toArray(new String[0]);
	}
	
	/**
	 * <p>getComment.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public String getComment(String section)
	{
		Map<String, Object> map = data.get(section == null ? "" : section);
		return map == null ? null : (String) map.get(null);
	}
	
	/**
	 * <p>setComment.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @param comment a {@link java.lang.String} object
	 * @return a {@link com.hk.ini.Ini} object
	 */
	public Ini setComment(String section, String comment)
	{
		section = section == null ? "" : section;
		Map<String, Object> map = data.get(section);
		if(map == null)
			data.put(section, map = new LinkedHashMap<>());
		
		if(comment == null)
			map.remove(null);
		else
			map.put(null, comment);
		return this;
	}
	
	/** {@inheritDoc} */
	public boolean equals(Object o)
	{
		return o instanceof Ini && data.equals(((Ini) o).data);
	}
	
	/**
	 * <p>hashCode.</p>
	 *
	 * @return a int
	 */
	public int hashCode()
	{
		return ~data.hashCode();
	}
	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString()
	{
		return IniUtil.save(this);
	}
}
