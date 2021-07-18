package com.hk.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hk.io.IOUtil;

public class FileUtil
{
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

	public static boolean createFile(String file)
	{
		return createFile(new File(file));
	}

	public static boolean createDirectory(File file)
	{
		return file.mkdirs();
	}

	public static boolean createDirectory(String file)
	{
		return createDirectory(new File(file));
	}

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

	public static boolean deleteFile(String file)
	{
		return deleteFile(new File(file));
	}

	public static boolean deleteDirectory(File file)
	{
		File[] list = file.listFiles();
		boolean done = true;
		for (File f : list)
		{
			done &= deleteFile(f);
		}
		if(done) file.delete();
		return done;
	}

	public static boolean deleteDirectory(String file)
	{
		return deleteDirectory(new File(file));
	}

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

	public static void resetFile(String file)
	{
		resetFile(new File(file));
	}

	public static void resetFile(File file, String... lines)
	{
		resetFile(file);
		for (String s : lines)
		{
			writeToFile(file, s);
		}
	}
	
	public static void resetFile(String file, byte[] data)
	{
		resetFile(file);
		writeToFile(file, data);
	}
	
	public static void resetFile(File file, byte[] data)
	{
		resetFile(file);
		writeToFile(file, data);
	}

	public static void resetFile(String file, String... lines)
	{
		resetFile(new File(file));
	}

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

	public static String getFileContents(String file)
	{
		return getFileContents(new File(file));
	}

	public static String[] getFileLines(File file)
	{
		return getFileContents(file).split("\n");
	}

	public static String[] getFileLines(String file)
	{
		return getFileContents(file).split("\n");
	}

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

	public static void writeToFile(String file, byte[] bytes)
	{
		writeToFile(new File(file), bytes);
	}

	public static void writeToFile(File file, String line)
	{
		writeToFile(file, line.getBytes());
	}

	public static void writeToFile(String file, String line)
	{
		writeToFile(file, line.getBytes());
	}

	public static File getUserDirectory()
	{
		return new File(System.getProperty("user.dir"));
	}

	public static File getHomeDirectory()
	{
		return new File(System.getProperty("user.home"));
	}

	public static List<File> getAllFiles(String dir)
	{
		return getAllFiles(new File(dir));
	}

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

	public static String getFileExtension(String file)
	{
		return getFileExtension(new File(file));
	}

	public static String getFileExtension(File file)
	{
		String s = file.toString();
		return s.substring(s.lastIndexOf(".") + 1, s.length());
	}

	public static byte[] readAll(String file)
	{
		return readAll(new File(file));
	}

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
