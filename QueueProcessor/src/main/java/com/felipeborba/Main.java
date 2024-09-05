package com.felipeborba;

public class Main {


    public static void main(String[] argv) throws InterruptedException {
        Queue queue = new Queue();

        queue.onMessageReceived(message -> {
            System.out.println(message);
        });

        while (true) {
            Thread.sleep(100);
        }
    }
}
