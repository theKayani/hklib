package com.hk.io.mqtt;

import com.hk.args.Arguments;
import com.hk.io.mqtt.engine.Message;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;

public class TestClient
{
    public static void main(String[] args2)
    {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %4$s]: %5$s%6$s%n");

        Client client;
        client = new Client("localhost", 21999);
        client.setLogLevel(Level.ALL);
        client.setDefaultExceptionHandler();

        String cmd;
        Scanner in = new Scanner(System.in);
        while(true)
        {
            System.out.print("\ncmd: ");
            cmd = in.nextLine();
            if(cmd.trim().isEmpty())
                continue;

            Arguments args = Arguments.parseLine(cmd);
            if(args.count() == 0)
                continue;

            if(args.getArg(0).equalsIgnoreCase("connect"))
            {
                if(args.flag("clean"))
                    client.setCleanSession(true);

                System.out.println("Connecting!");
                client.connect();
            }
            else if(args.getArg(0).equalsIgnoreCase("id"))
            {
                client.setID(args.getArg(1));
                System.out.println("Set Client ID: " + client.getID());
            }
            else if(args.getArg(0).equalsIgnoreCase("user"))
            {
                client.setUsername(args.getArg(1));
                System.out.println("Set Username: " + client.getUsername());
            }
            else if(args.getArg(0).equalsIgnoreCase("password"))
            {
                client.setPassword(args.getArg(1));
                System.out.println("Set Super Secret Password!");
            }
            else if(args.getArg(0).equalsIgnoreCase("will"))
            {
                String topic = args.option("topic");
                String message = args.option("message");
                Objects.requireNonNull(topic);
                Objects.requireNonNull(message);
                Message will = new Message(topic, message);
                String qos = args.option("qos");
                if (qos != null)
                    will.setQos(Integer.parseInt(qos));
                will.setRetain(args.flag("retain"));

                client.setLastWill(will);

                System.out.println("Set Will: " + message + " (" + topic + ")");
            }
            else if(args.getArg(0).equalsIgnoreCase("keep-alive"))
            {
                client.setKeepAlive(Integer.parseInt(args.getArg(1)));
                System.out.println("Set Keep Alive: " + client.getKeepAlive());
            }
            else if(args.getArg(0).equalsIgnoreCase("ping"))
            {
                double ping = client.ping();
                if(ping >= 0)
                    System.out.println("Ping: " + ping + "ms!");
                else
                    System.err.println("Hmm...");
            }
            else if(args.getArg(0).equalsIgnoreCase("exit"))
            {
                if(client.getStatus() != Client.Status.NOT_CONNECTED)
                {
                    client.disconnect(args.flag("hard"));
                    System.out.print("Waiting.");
                    do
                    {
                        System.out.print('.');
                    } while (client.getStatus() == Client.Status.DISCONNECTED);
                    System.out.println("!");
                }
                break;
            }
            else
                System.err.println("Unknown command: " + Arrays.toString(args.getArgs()));
        }
        in.close();
        System.out.println("Exiting...");
    }
}
