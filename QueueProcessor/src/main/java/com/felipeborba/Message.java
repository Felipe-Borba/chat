package com.felipeborba;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Message implements Serializable {
    private String content;
    private String sender;
    private ZonedDateTime date;

    // Getters e Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", date=" + date +
                '}';
    }
}
