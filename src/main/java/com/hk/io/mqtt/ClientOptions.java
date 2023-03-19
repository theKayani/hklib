package com.hk.io.mqtt;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Range;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientOptions
{
	// using SSLSocket or just Socket
	boolean useSSL;
	// "reasonable" amount of millis to wait before disconnecting a
	// client that doesn't receive a CONNACK packet
	int connectWaitTimeout;
	// the default quality of service level to use when publishing messages
	// using the client publish methods. does not affect the Message constructor
	final AtomicInteger defaultQos = new AtomicInteger(0);
	// the default retain flag to use when publishing messages
	// using the client publish methods. does not affect the Message constructor
	final AtomicBoolean defaultRetain = new AtomicBoolean(false);
	// set to true to not wait for authorization (CONNACK) before publishing messages
	boolean exploitFreedom;
	// amount of millis to wait for an ACK response for a packet of QoS > 0
	int qosAckTimeout;
	// true if publish methods will block until packets are delivered/received
	// false if everything is treated like fire-and-forget
	// not receiving ack will still result in the connection being closed
	boolean waitForPubAck;
	// similar to waitForPubAck but for SUBSCRIBE and UNSUBSCRIBE packets
	boolean waitForSubAck;

	public ClientOptions()
	{
		useSSL = false;
		connectWaitTimeout = 10000;
		exploitFreedom = false;
		qosAckTimeout = 15000;
		waitForPubAck = true;
		waitForSubAck = true;
	}

	public ClientOptions(ClientOptions options)
	{
		set(options);
	}

	public boolean isUsingSSL()
	{
		return useSSL;
	}

	public int getConnectWaitTimeout()
	{
		return connectWaitTimeout;
	}

	public int getDefaultQos()
	{
		return defaultQos.get();
	}

	public boolean isDefaultRetain()
	{
		return defaultRetain.get();
	}

	public boolean willExploitFreedom()
	{
		return exploitFreedom;
	}

	public int getQosAckTimeout()
	{
		return qosAckTimeout;
	}

	public boolean willWaitForPubAck()
	{
		return waitForPubAck;
	}

	public boolean willWaitForSubAck()
	{
		return waitForSubAck;
	}

	@Contract("_ -> this")
	public ClientOptions setUseSSL(boolean useSSL)
	{
		this.useSSL = useSSL;
		return this;
	}

	@Contract("_ -> this")
	public ClientOptions setConnectWaitTimeout(int connectWaitTimeout)
	{
		this.connectWaitTimeout = connectWaitTimeout;
		return this;
	}

	@Contract("_ -> this")
	public ClientOptions setDefaultQos(@Range(from=0, to=2) int qos)
	{
		if (qos < 0 || qos > 2)
			throw new IllegalArgumentException("qos must be between 0 and 2");
		defaultQos.set(qos);
		return this;
	}

	@Contract("-> this")
	public ClientOptions setToDefaultRetain()
	{
		return setDefaultRetain(true);
	}

	@Contract("-> this")
	public ClientOptions setNotDefaultRetain()
	{
		return setDefaultRetain(false);
	}

	@Contract("_ -> this")
	public ClientOptions setDefaultRetain(boolean retain)
	{
		this.defaultRetain.set(retain);
		return this;
	}

	@Contract("_ -> this")
	public ClientOptions setExploitFreedom(boolean exploitFreedom)
	{
		this.exploitFreedom = exploitFreedom;
		return this;
	}

	@Contract("_ -> this")
	public ClientOptions setQosAckTimeout(int qosAckTimeout)
	{
		this.qosAckTimeout = qosAckTimeout;
		return this;
	}

	@Contract("_ -> this")
	public ClientOptions setWaitForPubAck(boolean waitForPubAck)
	{
		this.waitForPubAck = waitForPubAck;
		return this;
	}

	@Contract("_ -> this")
	public ClientOptions setWaitForSubAck(boolean waitForSubAck)
	{
		this.waitForSubAck = waitForSubAck;
		return this;
	}

	@Contract("_ -> this")
	public ClientOptions set(ClientOptions options)
	{
		useSSL = options.useSSL;
		connectWaitTimeout = options.connectWaitTimeout;
		defaultQos.set(options.defaultQos.get());
		defaultRetain.set(options.defaultRetain.get());
		exploitFreedom = options.exploitFreedom;
		qosAckTimeout = options.qosAckTimeout;
		waitForPubAck = options.waitForPubAck;
		waitForSubAck = options.waitForSubAck;
		return this;
	}
}
