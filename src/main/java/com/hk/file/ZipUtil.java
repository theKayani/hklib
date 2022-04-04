package com.hk.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import com.hk.io.IOUtil;

/**
 * <p>ZipUtil class.</p>
 *
 * @author theKayani
 */
public class ZipUtil
{
	/**
	 * <p>zipFile.</p>
	 *
	 * @param directory a {@link java.io.File} object
	 * @param zipFile a {@link java.io.File} object
	 */
	public static void zipFile(File directory, File zipFile)
	{
		try
		{
			List<File> files = FileUtil.getAllFiles(directory);
			ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));
			long time = System.currentTimeMillis();
			for (File file : files)
			{
				ZipEntry e = new ZipEntry(directory.toPath().relativize(file.toPath()).toString().replaceAll("\\\\", "/"));
				e.setTime(time);
				zout.putNextEntry(e);
				FileInputStream fin = new FileInputStream(file);
				IOUtil.copyTo(fin, zout);
				fin.close();
				zout.closeEntry();
			}

			zout.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>unzipFile.</p>
	 *
	 * @param zipFile a {@link java.io.File} object
	 * @param directory a {@link java.io.File} object
	 */
	public static void unzipFile(File zipFile, File directory)
	{
		try
		{
			ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));

			ZipEntry entry;
			while ((entry = zin.getNextEntry()) != null)
			{
				if (entry.getName().endsWith("/")) continue;
				File f = new File(directory, entry.getName().replace("/", File.separator));
				f.getParentFile().mkdirs();
				f.delete();
				f.createNewFile();
				FileOutputStream fout = new FileOutputStream(f);
				IOUtil.copyTo(zin, fout);
				fout.close();
			}

			zin.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>jarFile.</p>
	 *
	 * @param directory a {@link java.io.File} object
	 * @param jarFile a {@link java.io.File} object
	 */
	public static void jarFile(File directory, File jarFile)
	{
		try
		{
			List<File> files = FileUtil.getAllFiles(directory);
			JarOutputStream jout = new JarOutputStream(new FileOutputStream(jarFile));
			long time = System.currentTimeMillis();
			for (File file : files)
			{
				JarEntry e = new JarEntry(directory.toPath().relativize(file.toPath()).toString().replaceAll("\\\\", "/"));
				e.setTime(time);
				jout.putNextEntry(e);
				jout.write(IOUtil.readAll(new FileInputStream(file)));
				jout.closeEntry();
			}

			jout.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>unjarFile.</p>
	 *
	 * @param jarFile a {@link java.io.File} object
	 * @param directory a {@link java.io.File} object
	 */
	public static void unjarFile(File jarFile, File directory)
	{
		try
		{
			JarInputStream jin = new JarInputStream(new FileInputStream(jarFile));

			JarEntry entry;
			while ((entry = jin.getNextJarEntry()) != null)
			{
				if (entry.getName().endsWith("/")) continue;
				File f = new File(directory, entry.getName().replace("/", File.separator));
				f.getParentFile().mkdirs();
				f.delete();
				f.createNewFile();
				FileOutputStream fout = new FileOutputStream(f);
				IOUtil.copyTo(jin, fout);
				fout.close();
			}

			jin.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private ZipUtil()
	{}
}
