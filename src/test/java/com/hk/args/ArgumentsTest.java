package com.hk.args;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.function.Consumer;

public class ArgumentsTest extends TestCase
{
	public void test()
	{
		Arguments arg;
		String[] args;

		try
		{
			new Arguments(new String[] { null });
			fail("expected NullPointerException");
		}
		catch (NullPointerException ignored)
		{}
		try
		{
			new Arguments((String[]) null);
			fail("expected NullPointerException");
		}
		catch (NullPointerException ignored)
		{}
		try
		{
			new Arguments("args", "cannot have", null, "value");
			fail("expected NullPointerException");
		}
		catch (NullPointerException ignored)
		{}

		arg = new Arguments();
		testEqual(arg);
		assertEquals(0, arg.count());
		try
		{
			arg.getArg(-1);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		try
		{
			arg.getArg(0);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		try
		{
			arg.getArg(1);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		args = arg.getArgs();
		assertEquals(0, args.length);
		arg = arg.sub(0, 0);
		testEqual(arg);
		assertEquals(0, arg.count());

		arg = new Arguments("sacrifice", "myself");
		testEqual(arg);
		assertEquals(2, arg.count());
		assertEquals("sacrifice", arg.getArg(0));
		assertEquals("myself", arg.getArg(1));
		try
		{
			arg.getArg(-1);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		try
		{
			arg.getArg(2);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		args = arg.getArgs();
		assertEquals(2, args.length);
		assertEquals("sacrifice", args[0]);
		assertEquals("myself", args[1]);
		assertEquals("[sacrifice, myself]", arg.toString());
		try
		{
			arg.sub(-1);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		try
		{
			arg.sub(0, 3);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		try
		{
			arg.sub(2, 1);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		try
		{
			arg.sub(2, 5);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		try
		{
			arg.sub(5, 10);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		arg = arg.sub(1, 2);
		testEqual(arg);
		assertEquals(1, arg.count());
		assertEquals("myself", arg.getArg(0));

		arg = new Arguments("workin", "on", "the", "weekend", "like usual");
		testEqual(arg);
		assertEquals(5, arg.count());
		assertEquals("workin", arg.getArg(0));
		assertEquals("on", arg.getArg(1));
		assertEquals("the", arg.getArg(2));
		assertEquals("weekend", arg.getArg(3));
		assertEquals("like usual", arg.getArg(4));
		try
		{
			arg.getArg(-1);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		try
		{
			arg.getArg(5);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		args = arg.getArgs();
		assertEquals(5, args.length);
		assertEquals("workin", args[0]);
		assertEquals("on", args[1]);
		assertEquals("the", args[2]);
		assertEquals("weekend", args[3]);
		assertEquals("like usual", args[4]);
		assertEquals("[workin, on, the, weekend, like usual]", arg.toString());
		arg = arg.sub(1, 4);
		testEqual(arg);
		assertEquals(3, arg.count());
		assertEquals("on", arg.getArg(0));
		assertEquals("the", arg.getArg(1));
		assertEquals("weekend", arg.getArg(2));

		args = new String[3];
		args[0] = "123";
		args[1] = "!\t\n^&*\n\"'$\"%'\r\n";
		args[2] = "abc";
		arg = new Arguments(args);
		testEqual(arg);
		assertEquals(3, arg.count());
		assertEquals("123", arg.getArg(0));
		assertEquals("!\t\n^&*\n\"'$\"%'\r\n", arg.getArg(1));
		assertEquals("abc", arg.getArg(2));
		try
		{
			arg.getArg(-1);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		try
		{
			arg.getArg(3);
			fail("expected IndexOutOfBoundsException");
		}
		catch(IndexOutOfBoundsException ignored)
		{}
		args = arg.getArgs();
		assertEquals(3, args.length);
		assertEquals("123", args[0]);
		assertEquals("!\t\n^&*\n\"'$\"%'\r\n", args[1]);
		assertEquals("abc", args[2]);
		assertEquals("[123, !\t\n^&*\n\"'$\"%'\r\n, abc]", arg.toString());
		arg = arg.sub(2);
		testEqual(arg);
		assertEquals(1, arg.count());
		assertEquals("abc", arg.getArg(0));
	}

	public void testParseLine()
	{
		Arguments arg;
		String[] args;

		arg = Arguments.parseLine("copy --input file1.txt --output file2.txt");
		testEqual(arg);
		args = arg.getArgs();
		assertEquals(5, arg.count());
		assertEquals(5, args.length);
		assertEquals("copy", args[0]);
		assertEquals("--input", args[1]);
		assertEquals("file1.txt", args[2]);
		assertEquals("--output", args[3]);
		assertEquals("file2.txt", args[4]);

		arg = Arguments.parseLine("copy --input \"file one.txt\" --output \"file two.txt\"");
		testEqual(arg);
		args = arg.getArgs();
		assertEquals(5, arg.count());
		assertEquals(5, args.length);
		assertEquals("copy", args[0]);
		assertEquals("--input", args[1]);
		assertEquals("file one.txt", args[2]);
		assertEquals("--output", args[3]);
		assertEquals("file two.txt", args[4]);

		arg = Arguments.parseLine("json a=1 b=2 c=3");
		testEqual(arg);
		args = arg.getArgs();
		assertEquals(4, arg.count());
		assertEquals(4, args.length);
		assertEquals("json", args[0]);
		assertEquals("a=1", args[1]);
		assertEquals("b=2", args[2]);
		assertEquals("c=3", args[3]);

		Consumer<Arguments> tester = arg2 -> {
			testEqual(arg2);
			String[] args2 = arg2.getArgs();
			assertEquals(3, arg2.count());
			assertEquals(3, args2.length);
			assertEquals("json", args2[0]);
			assertEquals("[]", args2[1]);
			assertEquals("{ \"hello\": \"world\" }", args2[2]);
		};

		tester.accept(Arguments.parseLine("json '[]' '{ \"hello\": \"world\" }'"));
		tester.accept(Arguments.parseLine("json \"[]\" \"{ \\\"hello\\\": \\\"world\\\" }\""));
		tester.accept(Arguments.parseLine("json `[]` `{ \"hello\": \"world\" }`"));

		arg = Arguments.parseLine("copy-file\n" +
				"--input \"C:\\\\Program Files\\\\Git\" -d\n" +
				"--output C:\\Users\\John\\Desktop\n" +
				"--buffer 2048");
		testEqual(arg);
		args = arg.getArgs();
		assertEquals(8, arg.count());
		assertEquals(8, args.length);
		assertEquals("copy-file", args[0]);
		assertEquals("--input", args[1]);
		assertEquals("C:\\Program Files\\Git", args[2]);
		assertEquals("-d", args[3]);
		assertEquals("--output", args[4]);
		assertEquals("C:\\Users\\John\\Desktop", args[5]);
		assertEquals("--buffer", args[6]);
		assertEquals("2048", args[7]);
	}

	public void testFlagsAndOptions()
	{
		Arguments arg;

		arg = new Arguments("calculator", "--a", "2", "--b", "3", "-add");
		assertTrue(arg.flag("add"));
		assertEquals("2", arg.option("a"));
		assertEquals("3", arg.option("b"));

		arg = new Arguments("cmd", "-these", "-are", "-all", "-flags");
		assertFalse(arg.flag("cmd"));
		assertTrue(arg.flag("these"));
		assertTrue(arg.flag("are"));
		assertTrue(arg.flag("all"));
		assertTrue(arg.flag("flags"));

		arg = new Arguments("cmd", "--these", "A", "--are", "B", "--all", "C", "--options", ":D");
		assertNull(arg.option("cmd"));
		assertEquals("A", arg.option("these"));
		assertEquals("B", arg.option("are"));
		assertEquals("C", arg.option("all"));
		assertEquals(":D", arg.option("options"));

		arg = new Arguments("cmd", "-flags", "--and", "options", "--can", "be", "-used-together");
		assertFalse(arg.flag("cmd"));
		assertNull(arg.option("cmd"));
		assertTrue(arg.flag("flags"));
		assertEquals("options", arg.option("and"));
		assertEquals("be", arg.option("can"));
		assertTrue(arg.flag("used-together"));

		arg = new Arguments("cmd", "--ending");
		assertFalse(arg.flag("cmd"));
		assertEquals("", arg.option("ending"));

		arg = new Arguments("copy-file",
				"--input", "C:\\Program Files\\Git", "-d",
				"--output", "C:\\Users\\John\\Desktop",
				"--buffer", "2048");
		assertEquals("C:\\Program Files\\Git", arg.option("input"));
		assertTrue(arg.flag("d"));
		assertEquals("C:\\Users\\John\\Desktop", arg.option("output"));
		assertEquals("2048", arg.option("buffer"));
	}

	private static void testEqual(Arguments arg)
	{
		String[] args = arg.getArgs();

		assertEquals(args.length, arg.count());
		for(int i = 0; i < arg.count(); i++)
			assertEquals(args[i], arg.getArg(i));
	}
}