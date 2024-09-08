package com.felipeborba;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] argv) throws Exception {
        List<String> users = new ArrayList<>();
        users.add("Felipe");
        users.add("Andre");
        users.add("Leonardo");
        users.add("Jonatas");

        Queue queue = new Queue(users);
        queue.onMessageReceived(message -> {
            if (users.contains(message.getSender())) {
                queue.sendMessageToExitQueue(message);
            }
        });

        while (true) {
            Thread.sleep(100);
        }
    }
}
