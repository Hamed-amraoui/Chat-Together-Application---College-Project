package com.example.chattogetherapp;

public class Message {
    private String Sender;
    private String Receiver;
    private String Content;

    public Message(){

    }

    public Message(String sender, String receiver, String content) {
        Sender = sender;
        Receiver = receiver;
        Content = content;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
