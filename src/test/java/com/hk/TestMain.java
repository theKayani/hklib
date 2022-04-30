package com.hk;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import com.hk.ex.UncheckedIOException;
import com.hk.file.FileUtil;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TestMain
{
	private static int walk(File f)
	{
		int totalLines = 0;
		if(f.getPath().endsWith(".java"))
		{
			int blankLines = 0;
			try
			{
				List<String> lines = new ArrayList<>();
				BufferedReader rdr = new BufferedReader(new FileReader(f));
				String line;

				while((line = rdr.readLine()) != null)
				{
					totalLines++;
					if(!line.isEmpty() && line.trim().isEmpty())
					{
						blankLines++;
						lines.add("");
					}
					else
						lines.add(line);
				}

				rdr.close();

				if(blankLines > 0)
				{
					System.out.println(f + " has " + blankLines + " blank line" + (blankLines == 1 ? "" : "s"));
					FileWriter wtr = new FileWriter(f);
					int max = lines.size();
					for (int i = lines.size() - 1; i >= 0; i--)
					{
						if(lines.get(i).isEmpty())
							max--;
						else
							break;
					}
					for (int i = 0; i < max; i++)
					{
						wtr.write(lines.get(i));

						if(i < lines.size() - 1)
							wtr.write("\n");
					}
					wtr.close();
				}
			}
			catch (IOException ex)
			{
				throw new UncheckedIOException(ex);
			}
		}

		if(f.isDirectory())
		{
			File[] files = f.listFiles();
			if(files != null)
			{
				for (File file : files)
					totalLines += walk(file);
			}
		}

		return totalLines;
	}

	public static void main(String[] args) throws IOException
	{
		File f = new File("C:\\Users\\kayan\\Desktop\\Eclipse\\hklib\\src");

		int totalLines = walk(f);
		System.out.println(totalLines);
	}

	public static void main2(String[] args) throws ClassNotFoundException, IOException
	{
		TestSuite suite = new TestSuite();

		File argfile = new File("argfile.txt");
		BufferedReader rdr = new BufferedReader(new FileReader(argfile));
		String line;

		while((line = rdr.readLine()) != null)
		{
			if(line.startsWith("test/") && line.endsWith("Test.java"))
			{
				line = line.substring(5, line.length() - 5).replace('/', '.');
				suite.addTestSuite(Class.forName(line).asSubclass(TestCase.class));
			}
		}

		rdr.close();

		TestRunner.run(suite);
	}
	
	/*
		public static void main(String[] args) throws IOException
	{
		boolean doGen = JsonBoolean.TRUE.getBoolean();

		if(doGen)
		{
			File csv = new File("C:\\Users\\kayan\\Downloads\\USP_business_map.csv");
			File lua = new File("src/test/resources/assets/lua/main.lua");

			BufferedReader rdr = new BufferedReader(new FileReader(csv));
			String line;
			String[] sp;
			rdr.readLine();

			System.out.println("Category, Type of Business, Business, Address, Phone Number, Latitude, Longitude");
			int row = 1, counted = 0;
			Map<String, String> literals = new TreeMap<>();
			List<String[]> entries = new ArrayList<>();
			Pattern phoneNoPtn = Pattern.compile("(\\d{3}).(\\d{3}).(\\d{4})");
			while((line = rdr.readLine()) != null)
			{
				row++;
				sp = line.split(",");
				String category = sp[0];
				String typeOfBusiness = sp[1].replace("||", ",");
				String business = sp[2];
				String address = sp[3];
				String phoneNumber = sp.length > 6 ? sp[6].trim() : null;
				if(phoneNumber != null && !phoneNumber.isEmpty())
				{
					Matcher matcher = phoneNoPtn.matcher(phoneNumber);
					if(matcher.matches())
						phoneNumber = matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3);
					else
						phoneNumber = null;
				}
				else
					phoneNumber = null;

				String latitude = sp.length > 8 ? sp[8] : null;
				String longitude = sp.length > 9 ? sp[9] : null;
				if(longitude != null && !longitude.startsWith("-73"))
					throw new RuntimeException("Row " + row);

				counted++;

				String categoryVar = category.toUpperCase().replaceAll("\\s+", "_");
				literals.put(categoryVar, category);
				String businessVar = typeOfBusiness.toUpperCase().replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", "_");
				literals.put(businessVar, typeOfBusiness);

				entries.add(new String[] { categoryVar, businessVar, business, address, phoneNumber, latitude, longitude });
			}
			System.out.println("counted = " + counted);

			rdr.close();

			PrintWriter wtr = new PrintWriter(new FileWriter(lua));

			wtr.println("-- CONSTANTS");
			for (Map.Entry<String, String> entry : literals.entrySet())
			{
				wtr.append("local ").append(entry.getKey()).append(" = '").append(entry.getValue()).append("'").println();
			}

			wtr.println();
			wtr.println("local records = {}");
			wtr.println("local function addRecord(category, typeOfBusiness, business, address, phoneNumber, latitude, longitude)\n" +
					"\ttable.insert(records, {category=category, typeOfBusiness=typeOfBusiness, business=business, address=address, phoneNumber=phoneNumber, latitude=latitude, longitude=longitude})\n" +
					"end");

			wtr.println();
			for (String[] entry : entries)
			{
				String categoryVar = entry[0];
				String businessVar = entry[1];
				String business = entry[2];
				String address = entry[3];
				String phoneNumber = entry[4];
				String latitude = entry[5];
				String longitude = entry[6];
				wtr.append("addRecord(");
				wtr.append(categoryVar);
				wtr.append(", ");
				wtr.append(businessVar);
				wtr.append(", ");
				if(business.contains("'"))
					wtr.append("\"").append(business).append("\"");
				else
					wtr.append("'").append(business).append("'");
				wtr.append(", '");
				wtr.append(address);
				wtr.append("', ");
				if(phoneNumber == null)
					wtr.append("nil");
				else
					wtr.append("'").append(phoneNumber).append("'");
				wtr.append(", ");
				if(latitude == null)
					wtr.append("nil");
				else
					wtr.append("'").append(latitude).append("'");
				wtr.append(", ");
				if(longitude == null)
					wtr.append("nil");
				else
					wtr.append("'").append(longitude).append("'");
				wtr.append(")");
				wtr.println();
			}
			wtr.println();
			wtr.println("return #records");
			wtr.close();
		}

		boolean doRun = JsonBoolean.TRUE.getBoolean();

		if(doRun)
		{
			File mainFile = Assets.get("lua/main.lua");

			if(!mainFile.exists())
				return;

			long start;
			final LuaInterpreter interp = Lua.reader(mainFile);

			start = System.nanoTime();
			interp.compile();
			double compileTime = (System.nanoTime() - start) / 1E6D;

			System.setProperty(LuaLibraryPackage.EXKEY_PATH, "./src/test/resources/assets/lua");

			Lua.importStandard(interp);
			interp.importLib(LuaLibrary.PACKAGE);
//			interp.importLib(LuaLibrary.REFLECT);

			Object obj;
			try
			{
				start = System.nanoTime();
				obj = interp.execute();
				double executeTime = (System.nanoTime() - start) / 1E6D;
				System.out.println("(time) " + compileTime + ", " + executeTime);
			}
			catch(LuaException e)
			{
				System.out.println("(time) " + compileTime);
				throw e;
			}

			System.out.println(obj);
		}
	}
	*/
}
