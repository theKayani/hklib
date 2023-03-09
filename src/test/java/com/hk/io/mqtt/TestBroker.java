package com.hk.io.mqtt;

import java.util.logging.Level;

public class TestBroker
{
    public static void main(String[] args)
    {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %4$s]: %5$s%6$s%n");

        Broker broker = new Broker("localhost", 21999);
        broker.setLogLevel(Level.ALL);
        broker.setDefaultExceptionHandler();

        broker.run();
    }
}
