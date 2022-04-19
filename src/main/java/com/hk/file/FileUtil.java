package com.hk.file;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.hk.ex.UncheckedIOException;
import com.hk.io.IOUtil;

/**
 * <p>FileUtil class.</p>
 *
 * @author theKayani
 */
public class FileUtil
{
	/**
	 * <p>contentEquals.</p>
	 *
	 * @param file1 a {@link java.io.File} object
	 * @param file2 a {@link java.io.File} object
	 * @return a boolean
	 */
	public static boolean contentEquals(File file1, File file2)
	{
		if(file1 == null && file2 == null || file1.equals(file2) || !file1.exists() && !file2.exists())
			return true;

		if(!file1.exists() || !file2.exists() || file1.length() != file2.length())
			return false;

		try
		{
			FileInputStream fs1 = new FileInputStream(file1);
			FileInputStream fs2 = new FileInputStream(file1);
			byte[] b1 = new byte[1024];
			byte[] b2 = new byte[1024];
			int len;
			while(true)
			{
				len = fs1.read(b1);
				if(len != fs2.read(b2))
				{
					len = 0;
					break;
				}
				if(len < 0)
					break;

				if(!Arrays.equals(b1, b2))
				{
					len = 0;
					break;
				}
			}
			fs1.close();
			fs2.close();
			return len == -1;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <p>createFile.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a boolean
	 */
	public static boolean createFile(File file)
	{
		try
		{
			return file.createNewFile();
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * <p>createFile.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean createFile(String file)
	{
		return createFile(new File(file));
	}

	/**
	 * <p>createDirectory.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a boolean
	 */
	public static boolean createDirectory(File file)
	{
		return file.mkdirs();
	}

	/**
	 * <p>createDirectory.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean createDirectory(String file)
	{
		return createDirectory(new File(file));
	}

	/**
	 * <p>deleteFile.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a boolean
	 */
	public static boolean deleteFile(File file)
	{
		if (file.isDirectory())
		{
			return deleteDirectory(file);
		}
		else
		{
			return file.delete();
		}
	}

	/**
	 * <p>deleteFile.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean deleteFile(String file)
	{
		return deleteFile(new File(file));
	}

	/**
	 * <p>deleteDirectory.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a boolean
	 */
	public static boolean deleteDirectory(File file)
	{
		File[] list = file.listFiles();
		boolean done = true;
		if(list != null)
		{
			for (File f : list)
			{
				done &= deleteFile(f);
			}
		}

		return done && file.delete();
	}

	/**
	 * <p>deleteDirectory.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean deleteDirectory(String file)
	{
		return deleteDirectory(new File(file));
	}

	/**
	 * <p>resetFile.</p>
	 *
	 * @param file a {@link java.io.File} object
	 */
	public static void resetFile(File file)
	{
		try
		{
			file.delete();
			file.createNewFile();
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * <p>resetFile.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 */
	public static void resetFile(String file)
	{
		resetFile(new File(file));
	}

	/**
	 * <p>resetFile.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param lines a {@link java.lang.String} object
	 */
	public static void resetFile(File file, String... lines)
	{
		resetFile(file);
		for (String s : lines)
		{
			writeToFile(file, s);
		}
	}

	/**
	 * <p>resetFile.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @param data an array of {@link byte} objects
	 */
	public static void resetFile(String file, byte[] data)
	{
		resetFile(file);
		writeToFile(file, data);
	}

	/**
	 * <p>resetFile.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param data an array of {@link byte} objects
	 */
	public static void resetFile(File file, byte[] data)
	{
		resetFile(file);
		writeToFile(file, data);
	}

	/**
	 * <p>resetFile.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @param lines a {@link java.lang.String} object
	 */
	public static void resetFile(String file, String... lines)
	{
		resetFile(new File(file));
	}

	/**
	 * <p>getFileContents.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link java.lang.String} object
	 */
	public static String getFileContents(File file)
	{
		if(!file.exists()) return null;
		try
		{
			FileInputStream in = new FileInputStream(file);
			String s = IOUtil.readString(in);
			in.close();
			return s;
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * <p>getFileContents.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String getFileContents(String file)
	{
		return getFileContents(new File(file));
	}

	/**
	 * <p>getFileLines.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return an array of {@link java.lang.String} objects
	 */
	public static String[] getFileLines(File file)
	{
		return getFileContents(file).split("\n");
	}

	/**
	 * <p>getFileLines.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @return an array of {@link java.lang.String} objects
	 */
	public static String[] getFileLines(String file)
	{
		return getFileContents(file).split("\n");
	}

	/**
	 * <p>writeToFile.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param bytes an array of {@link byte} objects
	 */
	public static void writeToFile(File file, byte[] bytes)
	{
		try
		{
			FileOutputStream fout = new FileOutputStream(file, true);
			fout.write(bytes);
			fout.close();
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * <p>writeToFile.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @param bytes an array of {@link byte} objects
	 */
	public static void writeToFile(String file, byte[] bytes)
	{
		writeToFile(new File(file), bytes);
	}

	/**
	 * <p>writeToFile.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param line a {@link java.lang.String} object
	 */
	public static void writeToFile(File file, String line)
	{
		writeToFile(file, line.getBytes());
	}

	/**
	 * <p>writeToFile.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @param line a {@link java.lang.String} object
	 */
	public static void writeToFile(String file, String line)
	{
		writeToFile(file, line.getBytes());
	}

	/**
	 * <p>getUserDirectory.</p>
	 *
	 * @return a {@link java.io.File} object
	 */
	public static File getUserDirectory()
	{
		return new File(System.getProperty("user.dir"));
	}

	/**
	 * <p>getHomeDirectory.</p>
	 *
	 * @return a {@link java.io.File} object
	 */
	public static File getHomeDirectory()
	{
		return new File(System.getProperty("user.home"));
	}

	/**
	 * <p>getAllFiles.</p>
	 *
	 * @param dir a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	public static List<File> getAllFiles(String dir)
	{
		return getAllFiles(new File(dir));
	}

	/**
	 * <p>getAllFiles.</p>
	 *
	 * @param dir a {@link java.io.File} object
	 * @return a {@link java.util.List} object
	 */
	public static List<File> getAllFiles(File dir)
	{
		if (!dir.isDirectory())
		{
			throw new IllegalArgumentException("dir isn't a directory");
		}
		List<File> files = new ArrayList<>();

		for (File f : dir.listFiles())
		{
			if (f.isDirectory())
			{
				files.addAll(getAllFiles(f));
			}
			else
			{
				files.add(f);
			}
		}
		return files;
	}

	/**
	 * <p>getFileExtension.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String getFileExtension(String file)
	{
		return getFileExtension(new File(file));
	}

	/**
	 * <p>getFileExtension.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link java.lang.String} object
	 */
	public static String getFileExtension(File file)
	{
		String s = file.toString();
		return s.substring(s.lastIndexOf(".") + 1);
	}

	/**
	 * <p>readAll.</p>
	 *
	 * @param file a {@link java.lang.String} object
	 * @return an array of {@link byte} objects
	 */
	public static byte[] readAll(String file)
	{
		return readAll(new File(file));
	}

	/**
	 * <p>readAll.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return an array of {@link byte} objects
	 */
	public static byte[] readAll(File file)
	{
		try
		{
			FileInputStream fin = new FileInputStream(file);
			byte[] arr = IOUtil.readAll(fin);
			fin.close();
			return arr;
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>loadFrom.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link com.hk.file.DataTag} object
	 */
	public static DataTag loadFrom(File file)
	{
		try
		{
			return new DataTag().load(file);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * <p>loadFromStream.</p>
	 *
	 * @param stream a {@link java.io.InputStream} object
	 * @return a {@link com.hk.file.DataTag} object
	 */
	public static DataTag loadFromStream(InputStream stream)
	{
		try
		{
			return new DataTag().load(stream);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * <p>saveTo.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param tag a {@link com.hk.file.DataTag} object
	 */
	public static void saveTo(File file, DataTag tag)
	{
		try
		{
			FileUtil.deleteFile(file);
			tag.save(file);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * <p>saveToStream.</p>
	 *
	 * @param file a {@link java.io.OutputStream} object
	 * @param tag a {@link com.hk.file.DataTag} object
	 */
	public static void saveToStream(OutputStream file, DataTag tag)
	{
		try
		{
			tag.save(file);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private FileUtil()
	{}
}