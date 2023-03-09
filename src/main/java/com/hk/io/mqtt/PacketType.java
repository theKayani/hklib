package com.hk.io.mqtt;

enum PacketType
{
	CONNECT,
	CONNACK,
	PUBLISH,
	PUBACK,
	PUBREC,
	PUBREL,
	PUBCOMP,
	SUBSCRIBE,
	SUBACK,
	UNSUBSCRIBE,
	UNSUBACK,
	PINGREQ,
	PINGRESP,
	DISCONNECT;

	int getType()
	{
		return ordinal() + 1;
	}

	static PacketType getFromHeader(byte b)
	{
		byte b2 = (byte) ((b & 0xF0) >> 4);
		b = (byte) (b & 0x0F);

		if(b2 == 0 || b2 == 15)
			return null;

		PacketType type = values()[b2 - 1];

		if(type == PUBREL || type == SUBSCRIBE || type == UNSUBSCRIBE)
		{
			if(b != 2)
				return null;
		}
		else if (type != PUBLISH && b != 0)
			return null;
		else if(type == PUBLISH && (b & 0x6) == 0x6)
			return null;

		return type;
	}
}
