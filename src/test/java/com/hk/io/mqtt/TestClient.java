package com.hk.io.mqtt;

import com.hk.args.Arguments;
import com.hk.io.mqtt.engine.Message;

import java.util.Arrays;
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

        client.setUsername("myuser");
        client.setLastWill(new Message("test/client", "Hello world!"));

        String cmd;
        Scanner in = new Scanner(System.in);
        while(true)
        {
            System.out.print("cmd: ");
            cmd = in.nextLine();
            if(cmd.trim().isEmpty())
                continue;

            Arguments args = new Arguments(cmd);
            if(args.count() == 0)
                continue;

            if(args.getArg(0).equalsIgnoreCase("connect"))
            {
                client.connect();
            }
            else if(args.getArg(0).equalsIgnoreCase("exit"))
            {
                if(client.getStatus() != Client.Status.NOT_CONNECTED)
                {
                    client.disconnect(args.flag("hard"));
                    do
                    {} while (client.getStatus() == Client.Status.DISCONNECTED);
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
