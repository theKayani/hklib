package com.hk.io.mqtt;

import com.hk.math.MathUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Level;

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
				b = Common.read(in);
				PacketType type = PacketType.getFromHeader(b);

				System.out.println("RECEIVED PACKET: " + type);
				if(type == null)
				{
					client.getLogger().warning("Unknown packet header: " + MathUtil.byteBin(b & 0xFF));
					socket.close();
					break;
				}

				int remLen = Common.readRemainingField(in);
				if(client.getLogger().isLoggable(Level.FINER))
					client.getLogger().finer("Received packet: " + type + ", remaining length: " + remLen);
				switch (type)
				{
					case CONNACK:
						handleConnackPacket(in, new AtomicInteger(remLen));
						break;
					case PUBLISH:
						throw new Error("TODO PUBLISH");
					case PUBACK:
						throw new Error("TODO PUBACK");
					case PUBREC:
						throw new Error("TODO PUBREC");
					case PUBREL:
						throw new Error("TODO PUBREL");
					case PUBCOMP:
						throw new Error("TODO PUBCOMP");
					case SUBACK:
						throw new Error("TODO SUBACK");
					case UNSUBACK:
						throw new Error("TODO UNSUBACK");
					case PINGRESP:
						throw new Error("TODO PINGRESP");
					case DISCONNECT:
						throw new Error("TODO DISCONNECT");
					case CONNECT:
					case SUBSCRIBE:
					case UNSUBSCRIBE:
					case PINGREQ:
						throw new IOException("unexpected packet type: " + type);
				}
			}
		}
		catch (IOException e)
		{
			if(exceptionHandler != null)
				exceptionHandler.accept(e);
		}
	}
	private void handleConnackPacket(InputStream in, AtomicInteger remLen) throws IOException
	{
		// variable header
		byte b;
		for (int i = 0; i < remLen.get(); i++)
		{
			b = Common.read(in, remLen);

			System.out.println(MathUtil.byteBin(b & 0xFF));
		}

		// payload
	}
}
