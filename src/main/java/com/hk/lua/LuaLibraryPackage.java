package com.hk.lua;

import com.hk.lua.Lua.LuaMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * <p>LuaLibraryPackage class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryPackage implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	require() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(name(), args, LuaType.STRING);

			String name = args[0].getString();
			LuaObject extra = interp.getExtraLua(EXKEY_PRELOAD_PREFIX + name);
			if(!extra.isNil())
				return extra;

			String split = interp.getExtraProp(EXKEY_TEMPLATE_SEP);
			String mark = interp.getExtraProp(EXKEY_TEMPLATE_MARK);
			String path = interp.getExtraProp(EXKEY_PATH);

			List<String> missed = new ArrayList<>();
			path = findPath(path.split(split), mark, name, missed);

			if(path == null)
			{
				StringBuilder sb = new StringBuilder();
				sb.append("no module '").append(name).append("' found:");
				for (String miss : missed) {
					sb.append("\nno file: '").append(miss).append('\'');
				}

				throw new LuaException(sb.toString());
			}

			try
			{
				BufferedReader rdr = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
				return interp.require(name, rdr);
			}
			catch (IOException e)
			{
				throw new LuaException(e.getLocalizedMessage());
			}
		}

		@Override
		public void accept(Environment env, LuaObject table)
		{
			Environment globals = env.interp.getGlobals();

			if(!globals.getVar(name()).getBoolean())
				globals.setVar(name(), Lua.newMethod(this));
		}
	},
	config() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			String str = File.separator + "\n";
			if (!env.interp.hasExtra(EXKEY_TEMPLATE_SEP))
			{
				env.interp.setExtra(EXKEY_TEMPLATE_SEP, ";");
				str += ";";
			}
			else
				str += env.interp.getExtraProp(EXKEY_TEMPLATE_SEP);

			str += '\n';
			if (!env.interp.hasExtra(EXKEY_TEMPLATE_MARK))
			{
				env.interp.setExtra(EXKEY_TEMPLATE_MARK, "?");
				str += "?";
			}
			else
				str += env.interp.getExtraProp(EXKEY_TEMPLATE_MARK);

			str += "\n!\n-\n";

			table.rawSet(new LuaString(name()), new LuaString(str));
		}
	},
	loaded() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			table.rawSet(new LuaString(name()), new LuaTable(env.interp.required) {
				@Override
				public void rawSet(@NotNull LuaObject key, @NotNull LuaObject value) {}

				@Override
				void doNewIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key, @NotNull LuaObject value) {}
			});
		}
	},
	path() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			String str;
			if (!env.interp.hasExtra(EXKEY_PATH))
			{
				List<String> paths = new ArrayList<>();
				paths.add("./?.lua");
				paths.add("./?/init.lua");

				if(env.interp.hasExtra(LuaLibraryIO.EXKEY_CWD))
				{
					String cwd = env.interp.getExtraProp(LuaLibraryIO.EXKEY_CWD);
					paths.add(cwd + "/?.lua");
					paths.add(cwd + "/?/init.lua");
				}

				String path = System.getProperty(EXKEY_PATH);
				if(path != null)
				{
					paths.add(path + "/?.lua");
					paths.add(path + "/?/init.lua");
				}

				path = System.getProperty("user.home");
				if(path != null)
				{
					paths.add(path + "/?.lua");
					paths.add(path + "/?/init.lua");
				}

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < paths.size(); i++)
				{
					sb.append(paths.get(i));

					if(i < paths.size() - 1)
						sb.append(';');
				}
				str = sb.toString();
				env.interp.setExtra(EXKEY_PATH, str);
			}
			else
				str = env.interp.getExtraProp(EXKEY_PATH);

			table.rawSet(new LuaString(name()), new LuaString(str));
		}
	},
	preload() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			table.rawSet(new LuaString(name()), new LuaTable(null) {
				@Override
				public @NotNull LuaObject rawGet(@NotNull LuaObject key)
				{
					return LuaNil.NIL;
				}

				@Override
				LuaObject doIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key)
				{
					if(key.isString())
						return interp.getExtraLua(EXKEY_PRELOAD_PREFIX + key.getString());

					return LuaNil.NIL;
				}

				@Override
				public void rawSet(@NotNull LuaObject key, @NotNull LuaObject value) {}

				@Override
				void doNewIndex(@Nullable LuaInterpreter interp, @NotNull LuaObject key, @NotNull LuaObject value) {}
			});
		}
	},
	searchpath() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING, LuaType.STRING);

			String split = interp.getExtraProp(EXKEY_TEMPLATE_SEP);
			String mark = interp.getExtraProp(EXKEY_TEMPLATE_MARK);
			String name = args[0].getString();
			String path = args[1].getString();
			String sep = ".", rep = File.separator;

			for (int i = 0; i < 2; i++)
			{
				if(args.length > i + 2)
				{
					if(args[i + 2].isString())
					{
						if(i == 0)
							sep = args[i + 2].getString();
						else
							rep = args[i + 2].getString();
					}
					else
						throw Lua.badArgument(i + 2, name(), "string expected");
				}
			}
			name = name.replace(sep, rep);

			List<String> missed = new LinkedList<>();
			String pth = findPath(path.split(split), mark, name, missed);
			if (pth != null)
				return new LuaString(pth);

			StringBuilder sb = new StringBuilder();
			for (String s : missed)
				sb.append("\n\tno file: '").append(s).append('\'');

			return Lua.wrapErr(sb.toString());
		}
	};

	static String findPath(final String[] paths, final String mark, final String name, final List<String> missed)
	{
		for (String pth : paths)
		{
			pth = pth.replace(mark, name);
			Path p = Paths.get(pth)
					.normalize()
					.toAbsolutePath();
			pth = p.toString();

			if(Files.exists(p))
				return pth;

			missed.add(pth);
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public LuaObject call(LuaInterpreter interp, LuaObject[] args)
	{
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void accept(Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), Lua.newMethod(this));
	}

	/** Constant <code>EXKEY_TEMPLATE_SEP="package.template.sep"</code> */
	public static final String EXKEY_TEMPLATE_SEP = "package.template.sep";
	/** Constant <code>EXKEY_TEMPLATE_MARK="package.template.mark"</code> */
	public static final String EXKEY_TEMPLATE_MARK = "package.template.mark";
	/** Constant <code>EXKEY_PATH="package.path"</code> */
	public static final String EXKEY_PATH = "package.path";
	/** Constant <code>EXKEY_PRELOAD_PREFIX="package.preload."</code> */
	public static final String EXKEY_PRELOAD_PREFIX = "package.preload.";
}
