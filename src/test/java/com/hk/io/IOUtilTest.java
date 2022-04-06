package com.hk.io;

import junit.framework.TestCase;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SuppressWarnings("ALL")
public class IOUtilTest extends TestCase
{
	final String str = "Lorem ipsum dolor sit amet, consectetur adipiscing " +
			"elit. Vivamus velit lectus, porttitor ac tempus id, ornare " +
			"in lorem. Morbi tristique ex nec interdum blandit. Vestibulum " +
			"pharetra ornare tortor, nec accumsan lorem lacinia sit " +
			"amet. Nullam eget quam et lorem ultrices tempor et sed " +
			"justo. Ut pretium, diam facilisis molestie laoreet, justo " +
			"lorem convallis ligula, at pharetra tortor lacus vel " +
			"elit. Aenean nulla eros, commodo malesuada molestie cursus, " +
			"vestibulum vel risus. Donec accumsan pretium diam sit " +
			"amet pellentesque.\n" +
			"Nullam nec sem in massa interdum vehicula. " +
			"Duis lobortis, ante ut ornare iaculis, quam nunc aliquam " +
			"metus, eget pharetra massa purus in felis. Aliquam efficitur " +
			"vestibulum consequat. Duis efficitur lacus sed porttitor " +
			"tincidunt. Nulla eget varius nibh, vel ultricies dolor. " +
			"Duis rhoncus dui quis sapien viverra, sed fringilla turpis " +
			"ultricies. Quisque in justo maximus, consequat sapien " +
			"in, pulvinar massa. Morbi diam ipsum, venenatis ac dignissim " +
			"vel, vehicula vel purus. Integer sed risus sed dolor " +
			"ullamcorper pretium et feugiat arcu. Vivamus dignissim " +
			"ut leo at volutpat. Praesent luctus ut tortor vel feugiat. " +
			"Donec nibh nulla, convallis vitae viverra id, hendrerit " +
			"non quam. Integer pellentesque varius ante. Nulla vitae " +
			"dolor ac ante tincidunt vestibulum. Nam at quam quis " +
			"felis placerat hendrerit dictum et tellus. Etiam venenatis, " +
			"leo mattis tempor malesuada, lorem libero semper tortor, " +
			"nec fermentum nunc nibh vitae elit.\n" +
			"Ut sit amet nisl sed metus auctor ullamcorper. Suspendisse " +
			"potenti. Nulla scelerisque massa eget risus dictum, sit " +
			"amet dignissim risus condimentum. Duis blandit urna eget " +
			"rutrum gravida. Cras posuere tellus vel felis faucibus, " +
			"non tristique metus luctus. Sed iaculis egestas nisi " +
			"ut suscipit. Vivamus maximus leo et tellus ultricies " +
			"faucibus. Morbi a interdum nulla. Pellentesque elementum " +
			"ex enim, vel mattis sem fermentum vel.";

	public void testCopyToStream()
	{
		final int[] wrote = { 0 };
		final int[] read = { 0 };
		final boolean[] inClosed = { false };
		final boolean[] outClosed = { false };
		InputStream in = new InputStream() {
			int count = 0;
			@Override
			public int read()
			{
				if(count > 255)
					return -1;

				read[0]++;
				return count++;
			}

			@Override
			public void close()
			{
				inClosed[0] = true;
			}
		};
		OutputStream out = new OutputStream() {
			int count = 0;
			@Override
			public void write(int b)
			{
				wrote[0]++;
				assertTrue(read[0] >= wrote[0]);
				assertEquals((byte) (count++), b);
			}

			@Override
			public void close()
			{
				outClosed[0] = true;
			}
		};
		IOUtil.copyTo(in, out);
		assertEquals(256, read[0]);
		assertEquals(256, wrote[0]);
		assertFalse(inClosed[0]);
		assertFalse(outClosed[0]);
	}

	public void testCopyToWriter()
	{
		final int[] wrote = { 0 };
		final int[] read = { 0 };
		final boolean[] inClosed = { false };
		final boolean[] outClosed = { false };
		final char[] cs = str.toCharArray();

		Reader in = new Reader() {
			int count = 0;
			@Override
			public int read(char[] cbuf, int off, int len)
			{
				if(count >= cs.length)
					return -1;

				len = Math.min(cs.length - count, len);
				for(int i = 0; i < len; i++)
				{
					read[0]++;
					cbuf[i + off] = cs[count + i];
				}
				count += len;

				return len;
			}

			@Override
			public void close()
			{
				inClosed[0] = true;
			}
		};
		Writer out = new Writer() {
			int count = 0;
			@Override
			public void write(char[] cbuf, int off, int len)
			{
				if(count >= cs.length)
					return;

				for(int i = 0; i < len; i++)
				{
					wrote[0]++;
					assertEquals(cs[count + i], cbuf[i + off]);
				}
				count += len;
				assertTrue(read[0] >= wrote[0]);
			}

			@Override
			public void flush()
			{

			}

			@Override
			public void close()
			{
				outClosed[0] = true;
			}
		};
		IOUtil.copyTo(in, out);
		assertEquals(cs.length, read[0]);
		assertEquals(cs.length, wrote[0]);
		assertFalse(inClosed[0]);
		assertFalse(outClosed[0]);
	}

	public void testCloseWithRE()
	{
		try
		{
			IOUtil.closeWithRE(new ErrorStream());
			fail("expected RuntimeException");
		}
		catch(RuntimeException ex)
		{
			assertTrue(ex.getCause() instanceof IOException);
			assertEquals("closeWithRE", ex.getCause().getLocalizedMessage());
		}
	}

	public void testReadString()
	{
		byte[] bs1 = str.getBytes(StandardCharsets.UTF_8);
		char[] cs1 = str.toCharArray();
		final boolean[] closed = { false };

		InputStream bin = new ByteArrayInputStream(bs1) {
			@Override
			public void close()
			{
				closed[0] = true;
			}
		};
		assertEquals(str, IOUtil.readString(bin, StandardCharsets.UTF_8));
		assertFalse(closed[0]);
		Reader rin = new CharArrayReader(cs1) {
			@Override
			public void close()
			{
				closed[0] = true;
			}
		};
		assertEquals(str, IOUtil.readString(rin));
		assertFalse(closed[0]);
	}

	public void testCloseQuietly()
	{
		IOUtil.closeQuietly(new ErrorStream());
	}

	public void testReadAll()
	{
		byte[] bs1 = str.getBytes(StandardCharsets.UTF_8);
		char[] cs1 = str.toCharArray();
		final boolean[] closed = { false };

		InputStream bin = new ByteArrayInputStream(bs1) {
			@Override
			public void close()
			{
				closed[0] = true;
			}
		};
		assertTrue(Arrays.equals(bs1, IOUtil.readAll(bin)));
		assertFalse(closed[0]);
		Reader rin = new CharArrayReader(cs1) {
			@Override
			public void close()
			{
				closed[0] = true;
			}
		};
		assertTrue(Arrays.equals(cs1, IOUtil.readAll(rin)));
		assertFalse(closed[0]);
	}

	public void testEmptyReader()
	{
		assertNotNull(IOUtil.emptyReader());

		Reader reader = IOUtil.emptyReader();

		assertNotNull(reader);
		assertSame(reader, IOUtil.emptyReader());
		try
		{
			assertEquals(-1, reader.read());
			assertEquals(-1, reader.read());
			reader.close();
			reader.close();
			assertEquals(-1, reader.read());
			assertEquals(-1, reader.read(new char[1]));
			assertEquals(-1, reader.read(new char[2]));
			assertEquals(-1, reader.read(new char[3]));
			assertEquals(-1, reader.read(new char[4]));
			reader.close();
			assertEquals(-1, reader.read(new char[8]));
			assertEquals(-1, reader.read(new char[128]));
			reader.close();
			assertEquals(-1, reader.read(new char[1024]));
			reader.close();
			assertEquals(-1, reader.read(new char[8192]));
		}
		catch (IOException e)
		{
			fail("not possible");
		}

		assertNotNull(IOUtil.nowhereWriter());
		assertSame(IOUtil.nowhereWriter(), IOUtil.nowhereWriter());
	}

	public void testEmptyStream()
	{
		assertNotNull(IOUtil.emptyStream());

		InputStream stream = IOUtil.emptyStream();

		assertNotNull(stream);
		assertSame(stream, IOUtil.emptyStream());
		try
		{
			assertEquals(-1, stream.read());
			assertEquals(-1, stream.read());
			stream.close();
			stream.close();
			assertEquals(-1, stream.read());
			assertEquals(-1, stream.read(new byte[1]));
			assertEquals(-1, stream.read(new byte[2]));
			assertEquals(-1, stream.read(new byte[3]));
			assertEquals(-1, stream.read(new byte[4]));
			stream.close();
			assertEquals(-1, stream.read(new byte[8]));
			assertEquals(-1, stream.read(new byte[128]));
			stream.close();
			assertEquals(-1, stream.read(new byte[1024]));
			stream.close();
			assertEquals(-1, stream.read(new byte[8192]));
		}
		catch (IOException e)
		{
			fail("not possible");
		}

		assertNotNull(IOUtil.nowhereStream());
		assertSame(IOUtil.nowhereStream(), IOUtil.nowhereStream());
	}

	private static class ErrorStream extends InputStream
	{
		@Override
		public int read()
		{
			return -1;
		}

		@Override
		public void close() throws IOException
		{
			throw new IOException("closeWithRE");
		}
	}
}