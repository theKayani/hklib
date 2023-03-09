package com.hk.io.mqtt;

import junit.framework.TestCase;

public class Test1 extends TestCase
{
	private Broker broker;

	@Override
	protected void setUp() throws Exception
	{
		broker = new Broker("localhost", 21999);

		broker.runAsync();

		Thread.sleep(1000);
	}

	public void testClient() throws Exception
	{
		assertNotNull(broker);

		Client client = new Client("localhost", 21999);
		client.connect();

		Thread.sleep(3000);

		client.disconnect(false);
	}

	@Override
	protected void tearDown() throws Exception
	{
		broker.stop(true);
	}
}