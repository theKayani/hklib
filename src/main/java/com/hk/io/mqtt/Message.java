package com.hk.io.mqtt;

import com.hk.io.IOUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public final class Message implements Cloneable
{
	@NotNull
	private final String topic;
	private final Datatype type;
	private byte qos;
	private boolean retain;
	// datatypes
	private final byte[] byteArrMessage;
	private final InputStream streamMessage;
	private final int streamLength;
	private final Path pathMessage;
	int maxReads = -1;

	public Message(@NotNull String topic, @NotNull String message, @Range(from=0, to=2) int qos, boolean retain)
	{
		this(topic, message.getBytes(StandardCharsets.UTF_8), qos, retain);
	}

	public Message(@NotNull String topic, byte @NotNull [] message, @Range(from=0, to=2) int qos, boolean retain)
	{
		this.topic = Objects.requireNonNull(topic);
		type = Datatype.BYTE_ARRAY;
		byteArrMessage = Arrays.copyOf(message, message.length);
		streamMessage = null;
		streamLength = -1;
		pathMessage = null;
		setQos(qos).setRetain(retain);
	}

	public Message(@NotNull String topic, @NotNull InputStream message, @Range(from=0, to=2) int qos, boolean retain)
	{
		this.topic = Objects.requireNonNull(topic);
		type = Datatype.STREAM;
		byteArrMessage = null;
		streamMessage = message;
		streamLength = -1;
		pathMessage = null;
		setQos(qos).setRetain(retain);
	}

	public Message(@NotNull String topic, @NotNull InputStream message, @Range(from=0, to=Integer.MAX_VALUE) int size, @Range(from=0, to=2) int qos, boolean retain)
	{
		this.topic = Objects.requireNonNull(topic);
		type = Datatype.STREAM;
		byteArrMessage = null;
		streamMessage = message;
		streamLength = size;
		pathMessage = null;
		setQos(qos).setRetain(retain);
	}

	Message(@NotNull String topic, @NotNull File message, int maxReads, @Range(from=0, to=2) int qos, boolean retain) throws FileNotFoundException
	{
		this(topic, message.toPath(), qos, retain);
		this.maxReads = maxReads;
	}

	public Message(@NotNull String topic, @NotNull File message, @Range(from=0, to=2) int qos, boolean retain) throws FileNotFoundException
	{
		this(topic, message.toPath(), qos, retain);
	}

	public Message(@NotNull String topic, @NotNull Path message, @Range(from=0, to=2) int qos, boolean retain) throws FileNotFoundException
	{
		this.topic = Objects.requireNonNull(topic);
		type = Datatype.PATH;
		byteArrMessage = null;
		streamMessage = null;
		streamLength = -1;
		pathMessage = message.toAbsolutePath();
		setQos(qos).setRetain(retain);

		checkPathMessage();
	}

	@Range(from = 0, to = 2)
	public int getQos()
	{
		return qos;
	}

	@Contract("-> this")
	public Message setNoQos()
	{
		return setQos(0);
	}

	@Contract("_ -> this")
	public Message setQos(@Range(from=0, to=2) int qos)
	{
		if (qos < 0 || qos > 2)
			throw new IllegalArgumentException("qos must be between 0 and 2");
		this.qos = (byte) qos;
		return this;
	}

	public boolean isRetain()
	{
		return retain;
	}

	@Contract("-> this")
	public Message setToRetain()
	{
		return setRetain(true);
	}

	@Contract("-> this")
	public Message setNotRetain()
	{
		return setRetain(false);
	}

	@Contract("_ -> this")
	public Message setRetain(boolean retain)
	{
		this.retain = retain;
		return this;
	}

	@NotNull
	public String getTopic()
	{
		return topic;
	}

	int getSize()
	{
		switch (type)
		{
			case BYTE_ARRAY:
				return byteArrMessage.length;
			case STREAM:
				return streamLength;
			case PATH:
				try
				{
					checkPathMessage();
					return Math.toIntExact(Files.size(pathMessage));
				}
				catch (FileNotFoundException e)
				{
					throw new RuntimeException(e);
				}
				catch (IOException e)
				{
					throw new UncheckedIOException(e);
				}
			default:
				throw new AssertionError("unexpected");
		}
	}

	public MessageInput toInput()
	{
		return new MessageInput(this);
	}

	void writeTo(OutputStream out) throws IOException
	{
		switch (type)
		{
			case BYTE_ARRAY:
				out.write(byteArrMessage);
				break;
			case STREAM:
				IOUtil.copyTo(streamMessage, out, 256);
				break;
			case PATH:
				checkPathMessage();
				InputStream in = Files.newInputStream(pathMessage);
				IOUtil.copyTo(in, out, 256);
				in.close();
				if(maxReads > 0)
				{
					maxReads--;
					if(maxReads == 0)
						Files.delete(pathMessage);
				}
				break;
			default:
				throw new AssertionError("unexpected");
		}
	}

	private void checkPathMessage() throws FileNotFoundException
	{
		if(!Files.exists(pathMessage))
			throw new FileNotFoundException("file not found for message: " + pathMessage);
		if(Files.isDirectory(pathMessage))
			throw new FileNotFoundException("cannot read directory for message: " + pathMessage);
	}

	@NotNull
	@Override
	@Contract("-> new")
	public Message clone()
	{
		switch (type)
		{
			case BYTE_ARRAY:
				return new Message(topic, byteArrMessage, qos, retain);
			case STREAM:
				return new Message(topic, streamMessage, streamLength, qos, retain);
			case PATH:
				try
				{
					return new Message(topic, pathMessage, qos, retain);
				}
				catch (FileNotFoundException e)
				{
					throw new RuntimeException(e);
				}
			default:
				throw new AssertionError("unexpected");
		}
	}

	@Override
	public String toString()
	{
		int size = getSize();
		String s =  "Message{" +
				"topic='" + topic + '\'' +
				", qos=" + qos +
				", retain=" + retain +
				", size=" + (size == -1 ? "'unknown'" : size);

		switch (type)
		{
			case BYTE_ARRAY:
				s += ", messageBytes=" + Arrays.toString(byteArrMessage);
				break;
			case STREAM:
				s += ", messageStream=" + streamMessage;
				break;
			case PATH:
				s += ", messageFile=" + pathMessage;
				break;
		}
		s += '}';
		return s;
	}

	enum Datatype
	{
		BYTE_ARRAY, STREAM, PATH
	}
}
