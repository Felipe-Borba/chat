package com.felipeborba;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Main {

    private final static String QUEUE_NAME = "minha_fila";

    public static void main(String[] argv) throws Exception {
        // Configurar a fábrica de conexões
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Alterar para o endereço do seu servidor RabbitMQ
        factory.setUsername("guest"); // Alterar para seu usuário RabbitMQ
        factory.setPassword("guest"); // Alterar para sua senha RabbitMQ

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Criar uma fila
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello, RabbitMQ!";

            // Enviar uma mensagem
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            // Consumir uma mensagem
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String receivedMessage = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + receivedMessage + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        }
    }
}
