package com.hk.io.mqtt;

import com.hk.math.MathUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

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
				PacketType type = PacketType.getFromHeader(Common.read(in));

				System.out.println("RECEIVED PACKET: " + type);
				if(type == null)
				{
					client.close();
					break;
				}

				int remLen = Common.readRemainingField(in);
				switch (type)
				{
					case CONNECT:
						handleConnectPacket(in, remLen);
						break;
					default:
						throw new Error("TODO");
				}
			}
		}
		catch (IOException e)
		{
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
