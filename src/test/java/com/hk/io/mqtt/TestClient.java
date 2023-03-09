package com.hk.io.mqtt;

import java.util.logging.Level;

public class TestClient
{
    public static void main(String[] args)
    {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %4$s]: %5$s%6$s%n");

        Client client = new Client("localhost", 21999);
        client.setLogLevel(Level.ALL);
        client.setUsername("myuser");
        client.setLastWill(new Client.Will("test/client", "Hello world!"));
        client.setDefaultExceptionHandler();

        client.connect();
    }
}
