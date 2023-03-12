package com.hk.io.mqtt.engine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class Message implements Cloneable
{
	@NotNull
	private final String topic;
	private final byte @NotNull [] message;
	private byte qos;
	private boolean retain;

	/**
	 * default will with a QOS of 1 and to retain flag off
	 *
	 * @param topic   the will topic to publish
	 * @param message the will message to publish
	 */
	public Message(@NotNull String topic, @NotNull String message) {
		this(topic, message, 1, false);
	}

	/**
	 * default will with a QOS of 1 and to retain flag off
	 *
	 * @param topic   the will topic to publish
	 * @param message the will message to publish
	 */
	public Message(@NotNull String topic, byte @NotNull [] message) {
		this(topic, message, 1, false);
	}

	public Message(@NotNull String topic, @NotNull String message, @Range(from = 0, to = 2) int qos, boolean retain) {
		this.topic = Objects.requireNonNull(topic);
		this.message = message.getBytes(StandardCharsets.UTF_8);
		setQos(qos).setRetain(retain);
	}

	public Message(@NotNull String topic, byte @NotNull [] message, @Range(from = 0, to = 2) int qos, boolean retain) {
		this.topic = Objects.requireNonNull(topic);
		this.message = Arrays.copyOf(message, message.length);
		setQos(qos).setRetain(retain);
	}

	@Range(from = 0, to = 2)
	public int getQos() {
		return qos;
	}

	public Message setNoQos() {
		return setQos(0);
	}

	public Message setQos(@Range(from = 0, to = 2) int qos) {
		if (qos < 0 || qos > 2)
			throw new IllegalArgumentException("qos must be between 0 and 2");
		this.qos = (byte) qos;
		return this;
	}

	public boolean isRetain() {
		return retain;
	}

	public Message setToRetain() {
		return setRetain(true);
	}

	public Message setNotRetain() {
		return setRetain(false);
	}

	public Message setRetain(boolean retain) {
		this.retain = retain;
		return this;
	}

	@NotNull
	public String getTopic() {
		return topic;
	}

	public byte @NotNull [] getRawMessage()
	{
		return message;
	}

	@NotNull
	public String getMessage() {
		return new String(message, StandardCharsets.UTF_8);
	}

	@NotNull
	@Override
	public Message clone()
	{
		return new Message(topic, message, qos, retain);
	}
}
