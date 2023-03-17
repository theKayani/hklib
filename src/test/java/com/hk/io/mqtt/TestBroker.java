package com.hk.io.mqtt;

import com.hk.args.Arguments;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;

public class TestBroker
{
    public static void main(String[] args2)
    {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %4$s]: %5$s%6$s%n");

//        Broker broker = new Broker("localhost", 21999);
        Broker broker = new Broker("192.168.0.238", 21999);
        broker.setLogLevel(Level.ALL);
        broker.setDefaultExceptionHandler();

        broker.runAsync(thread -> {
            thread.setUncaughtExceptionHandler((t, e) -> broker.getLogger().log(Level.WARNING, "unexpected", e));
            thread.setDaemon(true);
        });

        String cmd;
        Scanner in = new Scanner(System.in);
        do
        {
            System.out.print("\ncmd: ");
            cmd = in.nextLine();
            if(cmd.trim().isEmpty())
                continue;

            Arguments args = Arguments.parseLine(cmd);
            if(args.count() == 0)
                continue;

            if(args.getArg(0).equalsIgnoreCase("exit"))
            {
                if(broker.getStatus() != Broker.Status.NOT_BOUND)
                {
                    broker.stop();
                    System.out.print("Waiting.");
                    do
                    {
                        System.out.print('.');
                    } while (broker.getStatus() != Broker.Status.STOPPED);
                    System.out.println("!");
                }
                break;
            }
            else
                System.err.println("Unknown command: " + Arrays.toString(args.getArgs()));
        } while(broker.isRunning());
        in.close();
        System.out.println("Exiting...");
    }
}
