package com.felipeborba;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Queue {
    private final static String ENTRY_QUEUE = "Entry";
    private static final String EXCHANGE_NAME = "MessageDistribution";
    private final Channel channel;
    private final Serializer serializer;

    Queue(List<String> users) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        serializer = new Serializer();
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDelete(ENTRY_QUEUE);
        channel.queueDeclare(ENTRY_QUEUE, false, false, true, null);

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        for (String user : users) {
            channel.queueDelete(user);
            Map<String, Object> argsMap = new HashMap<>();
            argsMap.put("x-message-ttl", 60000);
            argsMap.put("x-expires", 3600000);
            channel.queueDeclare(user, false, false, true, argsMap);
            channel.queueBind(user, EXCHANGE_NAME, "");
        }
    }

    void onMessageReceived(OnMessage callback) {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            if (delivery.getBody() == null) return;
            callback.onReceived(serializer.read(delivery.getBody()));
        };

        try {
            channel.basicConsume(ENTRY_QUEUE, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            System.out.println("Error to read Entry queue");
        }
    }

    void sendMessageToExitQueue(Message message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, "", null, serializer.serialize(message));
    }
}
