package com.felipeborba;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Queue {
    private final static String ENTRY_QUEUE = "Entry";
    private final static String EXIT_QUEUE = "Exit";
    private static final String EXCHANGE_NAME = "logs";
    private final Channel channel;
    private final Serializer serializer;

    Queue() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        serializer = new Serializer();
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDelete(ENTRY_QUEUE);
        channel.queueDeclare(ENTRY_QUEUE, true, false, false, null);

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        channel.queueDelete(EXIT_QUEUE);
        channel.queueDeclare(EXIT_QUEUE, true, false, false, null);
        channel.queueBind(EXIT_QUEUE, EXCHANGE_NAME, "");
        clearExitQueue();
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
//        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
//                .expiration("1000")
//                .build();

        channel.basicPublish(EXCHANGE_NAME, "", null, serializer.serialize(message));
    }

    void clearExitQueue() {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // Start the thread
            thread.start();
        };

        try {
            channel.basicConsume(EXIT_QUEUE, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            System.out.println("Error to consume Exit queue");
        }
    }
}
