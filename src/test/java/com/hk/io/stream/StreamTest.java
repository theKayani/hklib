package com.hk.io.stream;

import com.hk.math.Rand;
import com.hk.math.vector.Vector3F;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StreamTest extends TestCase
{
	public void testStream() throws IOException
	{
		ByteArrayOutputStream bout;
		ByteArrayInputStream bin;
		Stream out, in;

		bout = new ByteArrayOutputStream();
		out = new OutStream(bout);
		Section section = new Section(0xFFF);
		section.output(out);
		System.out.println("1 -> " + bout.size());
		bin = new ByteArrayInputStream(bout.toByteArray());
		in = new InStream(bin);
		section.input(in);

		bout = new ByteArrayOutputStream();
		out = new OutStream(bout);
		List<Section> sections = new ArrayList<>();
		sections.add(new Section(0xFFF));
		sections.add(new Section(0));
		for (int i = 0; i < Rand.nextInt(100, 200); i++)
			sections.add(new Section(Rand.nextInt() & 0xFFF));
		sections.add(new Section(0xFFF));

		for (Section s : sections)
			s.output(out);

		System.out.println(sections.size() + " -> " + bout.size());
		bin = new ByteArrayInputStream(bout.toByteArray());
		in = new InStream(bin);
		for (Section s : sections)
			s.input(in);
	}

	private static class Section
	{
		private boolean output = false;
		private final int key;
		private boolean[] booleans;
		private byte[] bytes;
		private short[] shorts;
		private int[] ints;
		private float[] floats;
		private char[] chars;
		private long[] longs;
		private double[] doubles;
		private String[] utfStrings;
		private String[] rawStrings;
		private byte[][] byteArrs;

		private Section(int key)
		{
			this.key = key;
		}

		public void output(Stream out) throws StreamException
		{
			if(output)
				throw new IllegalArgumentException("already output");
			output = true;

			out.writeShort((short) key);

			if((key & 1) != 0)
			{
				out.writeBoolean(true);
				out.writeBoolean(true);
				out.writeBoolean(false);
				out.writeBoolean(false);
				out.writeBoolean(true);
				booleans = Rand.nextBooleans(Rand.nextInt(100, 200));
				for (boolean b : booleans)
					out.writeBoolean(b);
			}

			if((key & 1 << 1) != 0)
			{
				out.writeByte((byte) 3);
				out.writeByte((byte) 12);
				out.writeByte((byte) 39);
				out.writeByte((byte) 127);
				out.writeByte((byte) -34);
				out.writeByte((byte) -60);
				out.writeByte((byte) -128);
				bytes = Rand.nextBytes(Rand.nextInt(100, 200));
				for (byte b : bytes)
					out.writeByte(b);
			}

			if((key & 1 << 2) != 0)
			{
				out.writeShort((short) 19);
				out.writeShort((short) 100);
				out.writeShort((short) 1234);
				out.writeShort((short) 23142);
				out.writeShort((short) 50000);
				out.writeShort((short) 12000);
				out.writeShort((short) 65535);
				out.writeShort((short) -65536);
				out.writeShort((short) -12000);
				out.writeShort((short) -43274);
				out.writeShort((short) -31);
				shorts = Rand.nextShorts(Rand.nextInt(100, 200));
				for (short s : shorts)
					out.writeShort(s);
			}

			if((key & 1 << 3) != 0)
			{
				out.writeInt(9);
				out.writeInt(21000);
				out.writeInt(763);
				out.writeInt(70000);
				out.writeInt(525600);
				out.writeInt(7528222);
				out.writeInt(1200000);
				out.writeInt(2481030);
				out.writeInt(987827368);
				out.writeInt(2147483647);
				out.writeInt(-2147483648);
				out.writeInt(-140000000);
				out.writeInt(-91871782);
				out.writeInt(-1991818);
				out.writeInt(-1872);
				out.writeInt(-18);
				ints = Rand.nextInts(Rand.nextInt(100, 200));
				for (int i : ints)
					out.writeInt(i);
			}

			if((key & 1 << 4) != 0)
			{
				out.writeFloat(0.1F);
				out.writeFloat(0.01F);
				out.writeFloat(0.001F);
				out.writeFloat(2837.2744F);
				out.writeFloat(-0.0000372F);
				out.writeFloat(84763278.272F);
				floats = Rand.nextFloats(Rand.nextInt(100, 200));
				for (float f : floats)
					out.writeFloat(f);
			}

			if((key & 1 << 5) != 0)
			{
				out.writeCharacter('z');
				out.writeCharacter('A');
				out.writeCharacter('c');
				out.writeCharacter('A');
				out.writeCharacter('B');
				out.writeCharacter('8');
				out.writeCharacter('1');
				out.writeCharacter(' ');
				out.writeCharacter('\t');
				out.writeCharacter('*');
				out.writeCharacter('!');
				out.writeCharacter('~');
				chars = Rand.nextString(Rand.nextInt(100, 200)).toCharArray();
				for (char c : chars)
					out.writeCharacter(c);
			}

			if((key & 1 << 6) != 0)
			{
				out.writeLong(200L);
				out.writeLong(2000L);
				out.writeLong(200000L);
				out.writeLong(200000000L);
				out.writeLong(20000000000L);
				out.writeLong(200000000000000L);
				out.writeLong(-200000000000000L);
				out.writeLong(-20000000000L);
				out.writeLong(-200000000L);
				out.writeLong(-200000L);
				out.writeLong(-2000L);
				out.writeLong(-200L);
				out.writeLong(67901872893L);
				out.writeLong(473265637489L);
				out.writeLong(981672839432L);
				out.writeLong(74372895874389177L);
				out.writeLong(8456437829574839237L);
				out.writeLong(-8347389161L);
				out.writeLong(-123456954872L);
				out.writeLong(-516783947328L);
				out.writeLong(-84516783947328L);
				out.writeLong(-321845753261784832L);
				longs = Rand.nextLongs(Rand.nextInt(100, 200));
				for (long l : longs)
					out.writeLong(l);
			}

			if((key & 1 << 7) != 0)
			{
				out.writeDouble(Math.PI);
				out.writeDouble(Math.E);
				out.writeDouble(Math.sqrt(2));
				out.writeDouble(Math.pow(26.4372, 3.543));
				out.writeDouble(10030000000070.0);
				out.writeDouble(10300006000.0);
				out.writeDouble(10043600.0);
				out.writeDouble(10346.0);
				out.writeDouble(10.3);
				out.writeDouble(0);
				out.writeDouble(0.41);
				out.writeDouble(0.00001);
				out.writeDouble(0.044777001);
				out.writeDouble(0.00000037400001);
				out.writeDouble(0.0005607002360000001);
				out.writeDouble(0.000023603600236002360231);
				doubles = Rand.nextDoubles(Rand.nextInt(100, 200));
				for (double d : doubles)
					out.writeDouble(d);
			}

			if((key & 1 << 8) != 0)
			{
				out.writeUTFString("hello world");
				out.writeUTFString("i wish there was a way to" +
						"know you're in thegood times before they're" +
						"really gone");
				utfStrings = new String[Rand.nextInt(100, 200)];
				for (int i = 0; i < utfStrings.length; i++)
				{
					String s = utfStrings[i] = Rand.nextString(Rand.nextInt(100, 200));
					out.writeUTFString(s);
				}
			}

			if((key & 1 << 9) != 0)
			{
				out.writeRawString("hello world");
				out.writeRawString("i wish there was a way to" +
						"know you're in thegood times before it's" +
						"really gone");
				rawStrings = new String[Rand.nextInt(100, 200)];
				for (int i = 0; i < rawStrings.length; i++)
				{
					String s = rawStrings[i] = Rand.nextString(Rand.nextInt(100, 200));
					out.writeRawString(s);
				}
			}

			if((key & 1 << 10) != 0)
			{
				out.writeSerializable(new Date(1754430735457L));
				out.writeSerializable(new Vector3F(12, -0.01F, 36));
			}

			if((key & 1 << 11) != 0)
			{
				out.writeBytes(new byte[] { (byte) 1, (byte) 2, (byte) 3 });
				out.writeBytes(new byte[] { (byte) 0 });
				out.writeBytes(new byte[] { (byte) 66, (byte) 34, (byte) 12, (byte) 56, (byte) 122, (byte) -7, (byte) -18, (byte) -23, });
				byteArrs = new byte[Rand.nextInt(100, 200)][];
				for (int i = 0; i < byteArrs.length; i++)
				{
					byte[] bs = byteArrs[i] = Rand.nextBytes(Rand.nextInt(100, 200));
					out.writeBytes(bs);
				}
			}
		}

		public void input(Stream in) throws StreamException
		{
			assertEquals((short) key, in.readShort());

			if((key & 1) != 0)
			{
				assertTrue(in.readBoolean());
				assertTrue(in.readBoolean());
				assertFalse(in.readBoolean());
				assertFalse(in.readBoolean());
				assertTrue(in.readBoolean());
				for (boolean b : booleans)
					assertEquals(in.readBoolean(), b);
			}

			if((key & 1 << 1) != 0)
			{
				assertEquals(in.readByte(), (byte) 3);
				assertEquals(in.readByte(), (byte) 12);
				assertEquals(in.readByte(), (byte) 39);
				assertEquals(in.readByte(), (byte) 127);
				assertEquals(in.readByte(), (byte) -34);
				assertEquals(in.readByte(), (byte) -60);
				assertEquals(in.readByte(), (byte) -128);
				for (byte b : bytes)
					assertEquals(in.readByte(), b);
			}

			if((key & 1 << 2) != 0)
			{
				assertEquals(in.readShort(), (short) 19);
				assertEquals(in.readShort(), (short) 100);
				assertEquals(in.readShort(), (short) 1234);
				assertEquals(in.readShort(), (short) 23142);
				assertEquals(in.readShort(), (short) 50000);
				assertEquals(in.readShort(), (short) 12000);
				assertEquals(in.readShort(), (short) 65535);
				assertEquals(in.readShort(), (short) -65536);
				assertEquals(in.readShort(), (short) -12000);
				assertEquals(in.readShort(), (short) -43274);
				assertEquals(in.readShort(), (short) -31);
				for (short s : shorts)
					assertEquals(in.readShort(), s);
			}

			if((key & 1 << 3) != 0)
			{
				assertEquals(in.readInt(), 9);
				assertEquals(in.readInt(), 21000);
				assertEquals(in.readInt(), 763);
				assertEquals(in.readInt(), 70000);
				assertEquals(in.readInt(), 525600);
				assertEquals(in.readInt(), 7528222);
				assertEquals(in.readInt(), 1200000);
				assertEquals(in.readInt(), 2481030);
				assertEquals(in.readInt(), 987827368);
				assertEquals(in.readInt(), 2147483647);
				assertEquals(in.readInt(), -2147483648);
				assertEquals(in.readInt(), -140000000);
				assertEquals(in.readInt(), -91871782);
				assertEquals(in.readInt(), -1991818);
				assertEquals(in.readInt(), -1872);
				assertEquals(in.readInt(), -18);
				for (int i : ints)
					assertEquals(in.readInt(), i);
			}

			if((key & 1 << 4) != 0)
			{
				assertEquals(in.readFloat(), 0.1F);
				assertEquals(in.readFloat(), 0.01F);
				assertEquals(in.readFloat(), 0.001F);
				assertEquals(in.readFloat(), 2837.2744F);
				assertEquals(in.readFloat(), -0.0000372F);
				assertEquals(in.readFloat(), 84763278.272F);
				for (float f : floats)
					assertEquals(in.readFloat(), f);
			}

			if((key & 1 << 5) != 0)
			{
				assertEquals(in.readCharacter(), 'z');
				assertEquals(in.readCharacter(), 'A');
				assertEquals(in.readCharacter(), 'c');
				assertEquals(in.readCharacter(), 'A');
				assertEquals(in.readCharacter(), 'B');
				assertEquals(in.readCharacter(), '8');
				assertEquals(in.readCharacter(), '1');
				assertEquals(in.readCharacter(), ' ');
				assertEquals(in.readCharacter(), '\t');
				assertEquals(in.readCharacter(), '*');
				assertEquals(in.readCharacter(), '!');
				assertEquals(in.readCharacter(), '~');
				for (char c : chars)
					assertEquals(in.readCharacter(), c);
			}

			if((key & 1 << 6) != 0)
			{
				assertEquals(in.readLong(), 200L);
				assertEquals(in.readLong(), 2000L);
				assertEquals(in.readLong(), 200000L);
				assertEquals(in.readLong(), 200000000L);
				assertEquals(in.readLong(), 20000000000L);
				assertEquals(in.readLong(), 200000000000000L);
				assertEquals(in.readLong(), -200000000000000L);
				assertEquals(in.readLong(), -20000000000L);
				assertEquals(in.readLong(), -200000000L);
				assertEquals(in.readLong(), -200000L);
				assertEquals(in.readLong(), -2000L);
				assertEquals(in.readLong(), -200L);
				assertEquals(in.readLong(), 67901872893L);
				assertEquals(in.readLong(), 473265637489L);
				assertEquals(in.readLong(), 981672839432L);
				assertEquals(in.readLong(), 74372895874389177L);
				assertEquals(in.readLong(), 8456437829574839237L);
				assertEquals(in.readLong(), -8347389161L);
				assertEquals(in.readLong(), -123456954872L);
				assertEquals(in.readLong(), -516783947328L);
				assertEquals(in.readLong(), -84516783947328L);
				assertEquals(in.readLong(), -321845753261784832L);
				for (long l : longs)
					assertEquals(in.readLong(), l);
			}

			if((key & 1 << 7) != 0)
			{
				assertEquals(in.readDouble(), Math.PI);
				assertEquals(in.readDouble(), Math.E);
				assertEquals(in.readDouble(), Math.sqrt(2));
				assertEquals(in.readDouble(), Math.pow(26.4372, 3.543));
				assertEquals(in.readDouble(), 10030000000070.0);
				assertEquals(in.readDouble(), 10300006000.0);
				assertEquals(in.readDouble(), 10043600.0);
				assertEquals(in.readDouble(), 10346.0);
				assertEquals(in.readDouble(), 10.3);
				assertEquals(in.readDouble(), 0.0);
				assertEquals(in.readDouble(), 0.41);
				assertEquals(in.readDouble(), 0.00001);
				assertEquals(in.readDouble(), 0.044777001);
				assertEquals(in.readDouble(), 0.00000037400001);
				assertEquals(in.readDouble(), 0.0005607002360000001);
				assertEquals(in.readDouble(), 0.000023603600236002360231);
				for (double d : doubles)
					assertEquals(in.readDouble(), d);
			}

			if((key & 1 << 8) != 0)
			{
				assertEquals(in.readUTFString(), "hello world");
				assertEquals(in.readUTFString(), "i wish there was a way to" +
						"know you're in thegood times before they're" +
						"really gone");
				for (String s : utfStrings)
					assertEquals(in.readUTFString(), s);
			}

			if((key & 1 << 9) != 0)
			{
				assertEquals(in.readRawString(), "hello world");
				assertEquals(in.readRawString(), "i wish there was a way to" +
						"know you're in thegood times before it's" +
						"really gone");
				for(String s : rawStrings)
					assertEquals(in.readRawString(), s);
			}

			if((key & 1 << 10) != 0)
			{
				assertEquals(in.readSerializable(Date.class), new Date(1754430735457L));
				assertEquals(in.readSerializable(Vector3F.class), new Vector3F(12, -0.01F, 36));
			}

			if((key & 1 << 11) != 0)
			{
				assertTrue(Arrays.equals(in.readBytes(), new byte[] { (byte) 1, (byte) 2, (byte) 3 }));
				assertTrue(Arrays.equals(in.readBytes(), new byte[] { (byte) 0 }));
				assertTrue(Arrays.equals(in.readBytes(), new byte[] { (byte) 66, (byte) 34, (byte) 12, (byte) 56, (byte) 122, (byte) -7, (byte) -18, (byte) -23, }));
				for (byte[] bs : byteArrs)
					assertTrue(Arrays.equals(in.readBytes(), bs));
			}
		}
	}
}