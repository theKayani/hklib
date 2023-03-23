package com.hk.io.mqtt;

import com.hk.Assets;
import com.hk.args.Arguments;
import com.hk.math.MathUtil;
import com.hk.math.Rand;
import com.hk.str.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class TestClient
{
    public static void main(String[] args2)
    {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %4$s]: %5$s%6$s%n");

        // publish abc 123 --qos 0
        // publish def 456 --qos 1
        // publish ghi 789 --qos 2

        Client client;
//        client = new Client("localhost", 21999);
        client = new Client("192.168.0.216", 21999);
//        client = new Client("broker.hivemq.com", 1883);
//        client = new Client("broker.emqx.io", 1883);
//        client = new Client("test.mosquitto.org", 1883);
//        client = new Client("mqtt.flespi.io", 1883);
//        client = new Client("mqtt.dioty.co", 1883);
//        client = new Client("mqtt.fluux.io", 1883);
//        client = new Client("mqtt.fluux.io", 1883);
        client.setLogLevel(Level.ALL);
        client.setDefaultExceptionHandler();
        Map<String, Integer> publishedTopics = Collections.synchronizedMap(new TreeMap<>());
        client.setMessageConsumer(message -> publishedTopics.compute(message.getTopic(), (s, integer) -> integer == null ? 1 : integer + 1));

        String cmd;
        Scanner in = new Scanner(System.in);
        boolean connected = false;
        while(!connected || client.isConnected())
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
                client.setCleanSession(true);
                if(args.flag("persist"))
                    client.setCleanSession(false);

                System.out.println("Connecting!");
                client.connect();
                connected = true;
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
            else if(args.getArg(0).equalsIgnoreCase("log-level"))
            {
                client.setLogLevel(Level.parse(args.getArg(1).toUpperCase(Locale.ROOT)));
                System.out.println("Set Log Level: " + client.getLogger().getLevel());
            }
            else if(args.getArg(0).equalsIgnoreCase("max-volatile-message-size"))
            {
                client.options.maxVolatileMessageSize = Integer.parseInt(args.getArg(1));
                System.out.println("Set Max Volatile Message Size: " + client.options.maxVolatileMessageSize);
            }
            else if(args.getArg(0).equalsIgnoreCase("will"))
            {
                String topic = args.option("topic");
                String message = args.option("message");
                Objects.requireNonNull(topic);
                Objects.requireNonNull(message);
                Message will = new Message(topic, message, 0, false);
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
            else if(args.getArg(0).equalsIgnoreCase("published-topics-count"))
            {
                System.out.println("Published Topics Count: " + StringUtil.commaFormat(publishedTopics.size()));
            }
            else if(args.getArg(0).equalsIgnoreCase("published-topics-list"))
            {
                synchronized (publishedTopics)
                {
                    System.out.println("Listing " + publishedTopics.size() + " Published Topics:");
                    for (Map.Entry<String, Integer> entry : publishedTopics.entrySet())
                        System.out.printf("\t'%s' = %d%n", entry.getKey(), entry.getValue());
                }
            }
            else if(args.getArg(0).equalsIgnoreCase("published-topics-export"))
            {
                File file = Assets.get("published-topics-export-" + MathUtil.longHex(Rand.nextLong()) + ".txt");
                try
                {
                    System.out.println("Listing " + publishedTopics.size() + " topics to " + file);
                    FileWriter wtr = new FileWriter(file);
                    synchronized (publishedTopics)
                    {
                        wtr.write("Listing " + publishedTopics.size() + " topics");
                        for (Map.Entry<String, Integer> entry : publishedTopics.entrySet())
                            wtr.write(String.format("%n\t'%s' = %d", entry.getKey(), entry.getValue()));
                    }
                    wtr.close();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
            else if(args.getArg(0).equalsIgnoreCase("publish"))
            {
                int qos = 0;
                String qosOption = args.option("qos");
                if(qosOption != null)
                    qos = Integer.parseInt(qosOption);
                boolean retain = args.flag("retain");
                String repOption = args.option("rep");
                int repeats = repOption == null ? 1 : Integer.parseInt(repOption);

                for (int i = 0; i < repeats; i++)
                {
                    boolean published = client.publish(args.getArg(1), args.getArg(2), qos, retain);

                    if(published)
                        System.out.println("Published!");
                    else
                        System.out.println("Not published!");
                }
            }
            else if(args.getArg(0).equalsIgnoreCase("subscribe"))
            {
                int qos = 2;
                if(args.count() > 2)
                    qos = Integer.parseInt(args.getArg(2));
                String topicFilter = args.getArg(1);

                System.out.println("Subscribe result: 0x" + MathUtil.byteHex(client.subscribe(topicFilter, qos)));
            }
            else if(args.getArg(0).equalsIgnoreCase("unsubscribe"))
            {
                String topicFilter = args.getArg(1);
                client.unsubscribe(topicFilter);

                System.out.println("Unsubscribed!");
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
                    } while (client.getStatus() != Client.Status.DISCONNECTED);
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
