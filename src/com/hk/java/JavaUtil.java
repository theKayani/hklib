package com.hk.java;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import com.hk.array.ArrayUtil;
import com.hk.file.FileUtil;

public class JavaUtil
{
	@SuppressWarnings("resource")
	public static Class<?> getClass(File parent, File file)
	{
		if (file.getName().endsWith(".class"))
		{
			try
			{
				URL url = parent.toURI().toURL();

				ClassLoader cl = new URLClassLoader(ArrayUtil.toArray(url));
				String pckg = parent.toPath().relativize(file.toPath()).toString().replace("\\\\", "/");
				pckg = pckg.replace("\\", "/");
				pckg = pckg.replace("/", ".");
				pckg = pckg.substring(0, pckg.length() - 6);
				return cl.loadClass(pckg);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	@SuppressWarnings("resource")
	public static Class<?>[] getAllClasses(File dir)
	{
		try
		{
			List<Class<?>> clzs = new ArrayList<>();
			ClassLoader cl = new URLClassLoader(ArrayUtil.toArray(dir.toURI().toURL()));
			for (File file : FileUtil.getAllFiles(dir))
			{
				if (file.getName().endsWith(".class"))
				{
					String pckg = dir.toPath().relativize(file.toPath()).toString().replace("\\\\", "/");
					pckg = pckg.replace("\\", "/");
					pckg = pckg.replace("/", ".");
					pckg = pckg.substring(0, pckg.length() - 6);
					clzs.add(cl.loadClass(pckg));
				}
			}
			return clzs.toArray(new Class<?>[clzs.size()]);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static Object getFieldValue(Object obj, String fieldName)
	{
		try
		{
			Class<?> clz = obj.getClass();
			Field field = clz.getField(fieldName);
			return field.get(obj);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static Object getStaticFieldValue(Class<?> clz, String fieldName)
	{
		try
		{
			return clz.getField(fieldName).get(null);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
