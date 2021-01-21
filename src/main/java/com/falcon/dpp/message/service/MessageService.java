package com.falcon.dpp.message.service;

import java.util.List;

public interface MessageService {

    void publishMessage(String message);

    List<String> getAllMessages();

}
