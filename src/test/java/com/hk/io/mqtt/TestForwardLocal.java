package com.hk.io.mqtt;

import com.hk.args.Arguments;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;

public class TestForwardLocal
{
    public static void main(String[] args2)
    {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %4$s]: %5$s%6$s%n");
        Client client1, client2;
//        client1 = new Client("localhost", 21999);
        client1 = new Client("192.168.0.101", 21999);
        client1.setLogLevel(Level.INFO);
        client1.setDefaultExceptionHandler();
        client2 = new Client("broker.emqx.io", 1883);
        client2.setLogLevel(Level.INFO);
        client2.setDefaultExceptionHandler();

        client2.setMessageConsumer(client1::publish);
        client1.setMessageConsumer(client2::publish);

        String cmd;
        Scanner in = new Scanner(System.in);
        boolean connected = false;
        String lastFilter = null;
        while(true)
        {
            System.out.print("\ncmd: ");
            cmd = in.nextLine();
            if (cmd.trim().isEmpty())
                continue;

            Arguments args = Arguments.parseLine(cmd);
            if (args.count() == 0)
                continue;

            if (args.getArg(0).equalsIgnoreCase("connect"))
            {
                client1.connect();
                client2.connect();

                do {
                } while (client1.getStatus() != Client.Status.AUTHORIZED || client2.getStatus() != Client.Status.AUTHORIZED);

                System.out.println("client2.publish(\"abc\", \"123\") = " + client2.publish("abc", "123"));
                System.out.println("Client 1 subscribe all result = " + client1.subscribe("#", 2));
            }
            else if(args.getArg(0).equalsIgnoreCase("pipe"))
            {
                String filter = args.getArg(1);
                if(lastFilter != null)
                    client2.unsubscribe(lastFilter);
                System.out.println("Client 2 subscribe to '" + filter + "', result = " + client2.subscribe(filter, 2));
                client1.setTopicPredicate(topic -> !Session.matches(topic, filter));
                lastFilter = filter;
            }
            else if(args.getArg(0).equalsIgnoreCase("pause"))
            {
                if(lastFilter != null)
                    client2.unsubscribe(lastFilter);
                client1.setTopicPredicate(null);
            }
            else if(args.getArg(0).equalsIgnoreCase("log-level"))
            {
                Level level = Level.parse(args.getArg(1).toUpperCase(Locale.ROOT));
                client1.setLogLevel(level);
                client2.setLogLevel(level);
                System.out.println("Set Log Level: " + level);
            }
            else if(args.getArg(0).equalsIgnoreCase("ping"))
            {
                double ping = client1.ping();
                if(ping >= 0)
                    System.out.println("Client 1 Ping: " + ping + "ms!");
                else
                    System.err.println("Hmm...");
                ping = client2.ping();
                if(ping >= 0)
                    System.out.println("Client 2 Ping: " + ping + "ms!");
                else
                    System.err.println("Hmm...");
            }
            else if(args.getArg(0).equalsIgnoreCase("exit"))
            {
                client1.disconnect(false);
                client2.disconnect(false);
                break;
            }
            else
                System.err.println("Unknown command: " + Arrays.toString(args.getArgs()));
        }

        System.out.println("Stopping...");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Stopped!");
    }
}