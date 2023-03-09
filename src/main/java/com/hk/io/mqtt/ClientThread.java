package com.hk.io.mqtt;

import com.hk.math.MathUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

class ClientThread extends Thread
{
	private final Client client;
	private final Socket socket;
	private final Consumer<IOException> exceptionHandler;
	private final AtomicBoolean globalStop;

	ClientThread(Client client, Socket socket)
	{
		this.client = client;
		this.socket = socket;
		this.exceptionHandler = client.exceptionHandler;
		globalStop = client.globalStop;
	}

	@Override
	public void run()
	{
		try
		{
			InputStream in = socket.getInputStream();
			byte b;
			while(!globalStop.get())
			{
				// fixed header
				PacketType type = PacketType.getFromHeader(Common.read(in));

				System.out.println("RECEIVED PACKET: " + type);
				if(type == null)
					socket.close();

				int remLen = Common.readRemainingField(in);
				switch (type)
				{
					case CONNACK:
						handleConnackPacket(in, remLen);
						break;
					default:
						throw new Error("TODO");
				}
			}
		}
		catch (IOException e)
		{
			if(exceptionHandler != null)
				exceptionHandler.accept(e);
		}
	}
	private void handleConnackPacket(InputStream in, int remLen) throws IOException
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
