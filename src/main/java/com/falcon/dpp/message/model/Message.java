package com.falcon.dpp.message.model;

import com.falcon.dpp.message.model.base.Model;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class Message extends Model {

    private String message;

    public Message(String message){
        setMessage(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
