package com.hk.io.mqtt;

import junit.framework.TestCase;

import java.util.logging.Level;

public class Test1 extends TestCase
{
	private Broker broker;

	@Override
	protected void setUp() throws Exception
	{
		broker = new Broker("localhost", 21999);
		broker.setLogLevel(Level.OFF);
		broker.runAsync();

		Thread.sleep(1000);
	}

	public void testClient() throws Exception
	{
		assertNotNull(broker);

		Client client = new Client("localhost", 21999);
		client.setLogLevel(Level.OFF);
		client.connect();

		Thread.sleep(3000);

		assertTrue(broker.clientIDThreadMap.containsKey(client.clientID));
		assertNotNull(broker.clientIDThreadMap.get(client.clientID));

		client.disconnect(false);

		Thread.sleep(3000);

		assertFalse(broker.clientIDThreadMap.containsKey(client.clientID));
		assertNull(broker.clientIDThreadMap.get(client.clientID));
	}

	@Override
	protected void tearDown() throws Exception
	{
		broker.stop();
	}
}