package com.hk.io.mqtt;

import com.hk.math.MathUtil;
import junit.framework.TestCase;

public class PacketTypeTest extends TestCase
{
	public void test()
	{
		byte b;
		PacketType type;

		b = 0b00010000;
		type = PacketType.getFromHeader(b);
		assertEquals(PacketType.CONNECT, type);

		b = 0b00010001;
		type = PacketType.getFromHeader(b);
		assertNull(type);

		b = 0b00100000;
		type = PacketType.getFromHeader(b);
		assertEquals(PacketType.CONNACK, type);

		b = (byte) 0b10000010;
		type = PacketType.getFromHeader(b);
		assertEquals(PacketType.SUBSCRIBE, type);

		for (PacketType packetType : PacketType.values())
		{
			b = (byte) (packetType.getType() << 4);
			for (int i = 0; i < 16; i++)
			{
				type = PacketType.getFromHeader((byte) (b + i));

				boolean expectNull;
				if(packetType == PacketType.PUBREL || packetType == PacketType.SUBSCRIBE || packetType == PacketType.UNSUBSCRIBE)
					expectNull = i != 2;
				else if(packetType != PacketType.PUBLISH)
					expectNull = i != 0;
				else
					expectNull = (i & 0x6) == 0x6;

				if(expectNull)
					assertNull(type);
				else
					assertEquals(packetType, type);
			}
		}
	}
}