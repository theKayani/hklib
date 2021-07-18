package com.hk;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hk.file.FileUtil;
import com.hk.str.HTMLText;

public class ClassGen
{
	private static final String dir = System.getProperty("user.dir");
	
	public static void main(String[] args) throws Exception
	{
		File p = new File(dir, "src");
		list(new File(dir, "test"), p.toPath(), p);
	}
	
	private static void list(File dest, Path parent, File file) throws Exception
	{
		String path, name, code;
		for(File f : file.listFiles())
		{
			if(f.isDirectory())
			{
				list(dest, parent, f);
			}
			else if(f.getName().endsWith(".java"))
			{
				path = parent.relativize(f.toPath()).toString();
				path = path.substring(0, path.length() - 5);
				name = path.replace('/', '.').replace('\\', '.');
				path = path.concat("Test.java");

				File clss = dest.toPath().resolve(path).toFile();

				Class<?> clazz = Class.forName(name);
				code = setupClass(clazz, clss);

				if(clss.exists())
					clss.delete();
				if(code != null)
				{
					clss.getParentFile().mkdirs();
					FileUtil.resetFile(clss, code);
				}
			}
		}
	}
	
	private static String setupClass(Class<?> clazz, File dest)
	{
		List<Method> lst;
		HTMLText txt = new HTMLText();
		txt.pr("package ").wr(clazz.getPackage().getName()).wrln(";");
		txt.ln();
		txt.prln("import junit.framework.TestCase;");
		txt.ln();
		txt.pr("public class ").wr(clazz.getSimpleName()).wrln("Test extends TestCase");
		txt.openBrace();
		txt.prln("@Override");
		txt.prln("public void setUp()");
		txt.openBrace();
		txt.prln("// TODO: create or delete");
		txt.closeBrace();
		txt.ln();
		
		Method[] mths = clazz.getDeclaredMethods();
		Map<String, List<Method>> map = new HashMap<>();
		
		for(Method mth : mths)
		{
			int mods = mth.getModifiers();
			if(Modifier.isPublic(mods) && Modifier.isStatic(mods))
			{
				lst = map.get(mth.getName());
				
				if(lst == null)
					map.put(mth.getName(), lst = new LinkedList<>());
				
				lst.add(mth);
			}
		}
		
		if(map.isEmpty())
		{
			List<Constructor<?>> cons = new ArrayList<>();
			for(Constructor<?> con : clazz.getDeclaredConstructors())
			{
				if(Modifier.isPublic(con.getModifiers()))
					cons.add(con);
			}
			
			if(cons.isEmpty())
			{
				System.out.println("No public stuff in class " + clazz.getName());
				return null;
			}
			
			txt.pr("public void test").wr(clazz.getSimpleName()).wrln("()");
			txt.openBrace();
			for(int j = 0; j < cons.size(); j++)
			{
				Constructor<?> con = cons.get(j);
				txt.pr("// TODO: ").wr(clazz.getSimpleName()).wr("(");
				writeParams(txt, con.getParameters());
				txt.wrln(")");
				
				if(j < cons.size() - 1)
					txt.ln();
			}
			txt.closeBrace();
			txt.ln();
			
			for(Method mth : mths)
			{
				int mods = mth.getModifiers();
				if(Modifier.isPublic(mods))
				{
					lst = map.get(mth.getName());
					
					if(lst == null)
						map.put(mth.getName(), lst = new LinkedList<>());
					
					lst.add(mth);
				}
			}
		}

		String name;
		for(Map.Entry<String, List<Method>> ent : map.entrySet())
		{
			name = ent.getKey();
			txt.pr("public void test").wr(name.substring(0, 1).toUpperCase()).wr(name.substring(1));
			txt.wrln("()");

			txt.openBrace();
			lst = ent.getValue();
			for(int j = 0; j < lst.size(); j++)
			{
				Method mth = lst.get(j);
				txt.pr("// TODO: ").wr(clazz.getSimpleName()).wr(".").wr(name).wr("(");
				writeParams(txt, mth.getParameters());
				txt.wrln(")");
				
				if(j < lst.size() - 1)
					txt.ln();
			}
			txt.closeBrace();
			txt.ln();
		}

		txt.prln("@Override");
		txt.prln("public void tearDown()");
		txt.openBrace();
		txt.prln("// TODO: create or delete");
		txt.closeBrace();
		txt.closeBrace();
		
		return txt.create();
	}

	private static void writeParams(HTMLText txt, Parameter[] params)
	{
		for(int i = 0; i < params.length; i++)
		{
			Parameter param = params[i];
			Class<?> type = param.getType();
			
			if(type.getTypeName().matches("java\\.lang\\..+"))
				txt.wr(type.getTypeName().substring(10));
			else
				txt.wr(type.getTypeName());
				
			
			if(i < params.length - 1)
				txt.wr(", ");
		}
	}
}
/*
	No public stuff in class com.hk.abs.Childing
	No public stuff in class com.hk.abs.Lockable
	No public stuff in class com.hk.abs.Nameable
	No public stuff in class com.hk.abs.Named
	No public stuff in class com.hk.abs.Unlockable
	No public stuff in class com.hk.annotations.Named
	No public stuff in class com.hk.collections.CollectionUtil
	No public stuff in class com.hk.func.BiConsumer
	No public stuff in class com.hk.func.BiFunction
	No public stuff in class com.hk.func.BiPredicate
	No public stuff in class com.hk.func.Consumer
	No public stuff in class com.hk.func.Function
	No public stuff in class com.hk.func.Predicate
	No public stuff in class com.hk.func.Supplier
	No public stuff in class com.hk.io.stream.Stream
	No public stuff in class com.hk.jinja.Template
	No public stuff in class com.hk.jinja.TemplateReader
	No public stuff in class com.hk.jinja.Tokenizer
	No public stuff in class com.hk.jinja.Value
	No public stuff in class com.hk.json.JsonFormatException
	No public stuff in class com.hk.json.JsonNull
	No public stuff in class com.hk.json.JsonReader
	No public stuff in class com.hk.json.JsonWriter
	No public stuff in class com.hk.lua.*
	No public stuff in class com.hk.math.expression.ExpressionFormatException
	No public stuff in class com.hk.math.StorageUtils
*/