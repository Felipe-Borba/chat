package com.felipeborba;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class Queue {
    private final static String ENTRY_QUEUE = "Entry";
    private final static String EXIT_QUEUE = "Exit";
    private Channel channel;
    private final Serializer serializer;

    Queue() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        serializer = new Serializer();

        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(ENTRY_QUEUE, true, false, false, null);
            channel.queueDeclare(EXIT_QUEUE, true, false, false, null);
        } catch (Exception e) {
            System.out.println("Error to setup queue");
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

    void sendMessageToExitQueue(Message message) {

    }
}
