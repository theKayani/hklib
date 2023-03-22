package com.hk.io.mqtt;

import com.hk.json.Json;
import com.hk.json.JsonValue;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MessageInput
{
    private final Message message;

    public MessageInput(Message message)
    {
        if(message.maxReads == 0)
            throw new IllegalStateException("cannot read message");

        this.message = message;
    }

    public void toFile(File file) throws IOException
    {
        FileOutputStream out = new FileOutputStream(file);
        message.writeTo(out);
        out.close();
    }

    public void toPath(Path path) throws IOException
    {
        OutputStream out = Files.newOutputStream(path);
        message.writeTo(out);
        out.close();
    }

    public String toString()
    {
        try
        {
            return toString(StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    public String toString(Charset charset) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        message.writeTo(out);
        out.close();
        return out.toString();
    }

    public void toStream(OutputStream out) throws IOException
    {
        message.writeTo(out);
    }

    public void toWriter(Writer writer, Charset charset) throws IOException
    {
        writer.write(toString(charset));
    }

    public JsonValue toJson()
    {
        return Json.read(toString());
    }

    public byte[] toByteArray() throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        message.writeTo(out);
        out.close();
        return out.toByteArray();
    }

    public String getTopic()
    {
        return message.getTopic();
    }

    public int getSize()
    {
        return message.getSize();
    }

    public Message getMessage()
    {
        return message;
    }

    public boolean wasRetained()
    {
        return message.isRetain();
    }

    public boolean canRead()
    {
        return message.maxReads != 0;
    }
}
