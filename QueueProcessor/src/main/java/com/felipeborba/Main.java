package com.felipeborba;

public class Main {


    public static void main(String[] argv) throws Exception {
        Queue queue = new Queue();

        queue.onMessageReceived(message -> {
            //TODO logica para ver se o usuário está registrado e pode ancaminhar a mensaggem para a fila de saida
            queue.sendMessageToExitQueue(message);
        });

        while (true) {
            Thread.sleep(100);
        }
    }
}
