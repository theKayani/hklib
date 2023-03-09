package com.hk.io.mqtt;

import com.hk.math.MathUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

class BrokerClientThread extends Thread
{
	private final Broker broker;
	private final Socket client;
	private final AtomicBoolean globalStop;

	BrokerClientThread(Broker broker, Socket client)
	{
		this.broker = broker;
		this.client = client;
		globalStop = broker.globalStop;
	}

	@Override
	public void run()
	{
		try
		{
			InputStream in = client.getInputStream();
			byte b;
			while(!globalStop.get())
			{
				// fixed header
				b = Common.read(in);
				PacketType type = PacketType.getFromHeader(b);

				if(type == null)
				{
					if(broker.getLogger().isLoggable(Level.WARNING))
						broker.getLogger().warning("[" + client.getRemoteSocketAddress() + "] Unknown packet header: " + MathUtil.byteBin(b & 0xFF));
					client.close();
					break;
				}

				int remLen = Common.readRemainingField(in);
				if(broker.getLogger().isLoggable(Level.FINER))
					broker.getLogger().finer("[" + client.getRemoteSocketAddress() + "] Received packet: " + type + ", remaining length: " + remLen);
				switch (type)
				{
					case CONNECT:
						handleConnectPacket(in, remLen);
						break;
					case PUBLISH:
//						handlePublishPacket(in, remLen, b);
						throw new Error("TODO PUBLISH");
					case PUBACK:
						throw new Error("TODO PUBACK");
					case PUBREC:
						throw new Error("TODO PUBREC");
					case PUBREL:
						throw new Error("TODO PUBREL");
					case PUBCOMP:
						throw new Error("TODO PUBCOMP");
					case SUBSCRIBE:
						throw new Error("TODO SUBSCRIBE");
					case UNSUBSCRIBE:
						throw new Error("TODO UNSUBSCRIBE");
					case PINGREQ:
						throw new Error("TODO PINGREQ");
					case DISCONNECT:
						throw new Error("TODO DISCONNECT");
					case CONNACK:
					case SUBACK:
					case UNSUBACK:
					case PINGRESP:
						throw new IOException("unexpected packet type: " + type);
				}
			}
		}
		catch (IOException e)
		{
			// TODO: send last will testament
			if(broker.exceptionHandler != null)
				broker.exceptionHandler.accept(e);
		}
	}

	private void handleConnectPacket(InputStream in, int remLen) throws IOException
	{
		// variable header
		byte b;
		for (int i = 0; i < remLen; i++)
		{
			b = Common.read(in);

			System.out.println(MathUtil.byteBin(b & 0xFF));
		}

		// payload
	}
}
