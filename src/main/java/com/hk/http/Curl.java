package com.hk.http;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class Curl
{
	public static HttpRequest get(URL url)
	{
		return new HttpRequest(HttpMethod.GET, url);
	}

	public static HttpRequest get(String url)
	{
		try
		{
			return get(new URL(url));
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static HttpRequest get(String url, Map<String, String> queryParams)
	{
		if(url.indexOf('?') != -1)
			throw new IllegalArgumentException("URL might already contain query string");

		return get(url + toQuery(queryParams));
	}

	public static HttpRequest post(URL url)
	{
		return new HttpRequest(HttpMethod.POST, url);
	}

	public static HttpRequest post(String url)
	{
		try
		{
			return post(new URL(url));
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static HttpRequest post(String url, Map<String, String> queryParams)
	{
		if(url.indexOf('?') != -1)
			throw new IllegalArgumentException("URL might already contain query string");

		return post(url + toQuery(queryParams));
	}

	public static String toQuery(Map<String, String> queryParams)
	{
		if (queryParams.isEmpty())
			return "";

		StringBuilder query = new StringBuilder("?");

		Iterator<Map.Entry<String, String>> itr = queryParams.entrySet().iterator();
		Map.Entry<String, String> entry;

		while(itr.hasNext())
		{
			entry = itr.next();
			String key = entry.getKey() != null ? entry.getKey() : "";
			String value = entry.getValue() != null ? entry.getValue() : "";

			try
			{
				key = URLEncoder.encode(key, "UTF-8");
				value = URLEncoder.encode(value, "UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				throw new RuntimeException("very unexpected", e);
			}

			query.ensureCapacity(key.length() + value.length() + 2);
			query.append(key).append('=').append(value);

			if(itr.hasNext())
				query.append("&");
		}

		return query.toString();
	}
}
