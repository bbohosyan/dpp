package com.falcon.dpp.message.message.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "messages")
public class Message {

    @Id
    private String id;

    private String message;

    public Message(){

    }

    public Message(String message){
        setId(UUID.randomUUID().toString());
        setMessage(message);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
