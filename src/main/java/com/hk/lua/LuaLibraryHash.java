package com.hk.lua;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.hk.io.IOUtil;
import com.hk.lua.Lua.LuaMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>LuaLibraryHash class.</p>
 *
 * @author theKayani
 */
public enum LuaLibraryHash implements BiConsumer<Environment, LuaObject>, LuaMethod
{
	funcs() {
		@Override
		public void accept(Environment env, LuaObject table)
		{
			LuaTable funcs = new LuaTable();

			Set<String> algorithms = Security.getAlgorithms("MessageDigest");
			for (final String algorithm : algorithms)
			{
				final String alg = algorithm.toLowerCase(Locale.ROOT)
						.replaceAll("[^a-zA-Z0-9]", "_");

				LuaObject func = new LuaFunction() {
					@Override
					LuaObject doCall(@Nullable LuaInterpreter interp, @NotNull LuaObject[] args)
					{
						Lua.checkArgs(alg, args, LuaType.STRING);
						return hash(algorithm, args[0].getString());
					}
				};

				funcs.rawSet(alg, func);
				table.rawSet(alg, func);
			}

			table.rawSet("funcs", funcs);
		}
	},
	zip() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING, LuaType.STRING);
			boolean overwrite = args.length > 2 && args[2].getBoolean();
			File dir = new File(args[0].getString());
			if(!dir.exists() || !dir.isDirectory())
				return Lua.wrapErr("expected directory at '" + dir + "'");

			File zip = new File(args[1].getString());
			if(zip.exists() && !overwrite)
				return Lua.wrapErr("zip file at '" + zip + "' already exists");
			if(!zip.getParentFile().exists())
				return Lua.wrapErr("cannot create zip file at '" + zip.getParent());

			try
			{
				if(overwrite && zip.exists() && !zip.delete())
					throw new IOException("couldn't overwrite file at '" + zip + "'");

				ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip));

				Path root = dir.toPath();
				Stack<File> toCheck = new Stack<>();
				toCheck.push(dir);

				boolean first = true;
				ZipEntry entry;
				while(!toCheck.isEmpty())
				{
					File f = toCheck.pop();

					if(!first)
					{
						String pth = root.relativize(f.toPath()).toString();
						if(f.isDirectory())
							pth += "/";
						entry = new ZipEntry(pth);
						zout.putNextEntry(entry);
					}

					if(f.isDirectory())
					{
						File[] files = f.listFiles();

						if(files != null)
						{
							for (File file : files)
								toCheck.push(file);
						}
					}
					else
					{
						FileInputStream fin = new FileInputStream(f);
						IOUtil.copyTo(fin, zout);
						fin.close();
					}

					if(!first)
						zout.closeEntry();

					first = false;
				}

				zout.close();
			}
			catch (IOException e)
			{
				return Lua.wrapErr(e);
			}

			return LuaBoolean.TRUE;
		}
	},
	unzip() {
		@Override
		public LuaObject call(LuaInterpreter interp, LuaObject[] args)
		{
			Lua.checkArgs(toString(), args, LuaType.STRING, LuaType.STRING);
			boolean overwrite = args.length > 2 && args[2].getBoolean();
			File zip = new File(args[0].getString());
			if(!zip.exists() || !zip.isFile())
				return Lua.wrapErr("expected zip file at '" + zip + "'");

			File dir = new File(args[1].getString());
			if(dir.exists() && !overwrite)
				return Lua.wrapErr("directory at '" + dir + "' already exists");
			if(!dir.getParentFile().exists())
				return Lua.wrapErr("cannot create directory at '" + dir.getParent());

			try
			{
				Path root = dir.toPath();
				Files.createDirectory(root);
				ZipInputStream zin = new ZipInputStream(new FileInputStream(zip));

				ZipEntry entry;

				while((entry = zin.getNextEntry()) != null)
				{
					Path pth = root.resolve(entry.getName());

					if (!entry.isDirectory())
					{
						if(Files.exists(pth))
						{
							if(overwrite)
								Files.delete(pth);
							else
								return Lua.wrapErr("zip entry already exists at '" + pth);
						}
						Files.createFile(pth);
						OutputStream fout = Files.newOutputStream(pth);
						IOUtil.copyTo(zin, fout);
						fout.close();
					}
					else
						Files.createDirectories(pth);

					zin.closeEntry();
				}

				zin.close();
			}
			catch (IOException e)
			{
				return Lua.wrapErr(e);
			}

			return LuaBoolean.TRUE;
		}
	};

	/** {@inheritDoc} */
	@Override
	public LuaObject call(LuaInterpreter interp, LuaObject[] args)
	{
		throw new Error();
	}

	/** {@inheritDoc} */
	@Override
	public void accept(Environment env, LuaObject table)
	{
		String name = toString();
		if(name != null && !name.trim().isEmpty())
			table.rawSet(new LuaString(name), Lua.newMethod(this));
	}

	/**
	 * <p>hash.</p>
	 *
	 * @param alg a {@link java.lang.String} object
	 * @param input a {@link java.lang.String} object
	 * @return a {@link com.hk.lua.LuaObject} object
	 */
	public static LuaObject hash(String alg, String input)
	{
		try
		{
			MessageDigest dg = MessageDigest.getInstance(alg);
			dg.update(input.getBytes());
			byte[] out = dg.digest();

			char[] cs = new char[out.length * 2];
			for(int i = 0; i < out.length; i++)
			{
				cs[i * 2 + 1] = hex[out[i] & 0xF];
				cs[i * 2] = hex[(out[i] >> 4) & 0xF];
			}
			return new LuaString(new String(cs));
		}
		catch (NoSuchAlgorithmException e)
		{
			return Lua.wrapErr(e);
		}
	}

	private static final char[] hex = "0123456789abcdef".toCharArray();
}