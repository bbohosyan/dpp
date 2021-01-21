package com.falcon.dpp.message.service;

import com.falcon.dpp.message.repository.MessageRepository;
import com.falcon.dpp.message.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final MessageRepository messageRepository;

    @Autowired
    public MessageConsumer(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @KafkaListener(topics = "messages", groupId = "dpp")
    public void receive(String message)  {
        LOGGER.info("received message: " + message);

        messageRepository.save(new Message(message));
    }
}
