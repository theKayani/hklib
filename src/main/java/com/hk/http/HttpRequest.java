package com.hk.http;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class HttpRequest
{
	final HttpMethod method;
	final URL url;
	final HttpURLConnection conn;

	public HttpRequest(@NotNull HttpMethod method, @NotNull URL url)
	{
		String prot = url.getProtocol().toLowerCase(Locale.ROOT);
		if(!prot.equals("http") && !prot.equals("https"))
			throw new IllegalArgumentException("expected HTTP/S not a '" + prot + "' url");

		this.method = method;
		this.url = url;

		try
		{
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method.name());
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	public HttpRequest setHeader(String key, String value)
	{
		conn.setRequestProperty(key, value);
		return this;
	}

	public HttpResponse make()
	{
		try
		{
			return new HttpResponse(this);
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	public HttpMethod getMethod()
	{
		return method;
	}

	public URL getUrl()
	{
		return url;
	}
}
