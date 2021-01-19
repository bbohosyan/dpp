package com.falcon.dpp.message.message;

import com.falcon.dpp.message.message.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "message", groupId = "group")
    public void receive(String message)  {
        LOGGER.info("received message {} ", message);

        messageRepository.save(new Message(message));
    }
}
