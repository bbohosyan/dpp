package com.falcon.dpp.message.service;

import com.falcon.dpp.message.MessageRepository;
import com.falcon.dpp.message.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService{

    private final String TOPIC = "messages";

    private final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    private final SimpMessagingTemplate template;

    private final MessageRepository messageRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MessageServiceImpl(SimpMessagingTemplate template, MessageRepository messageRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.template = template;
        this.messageRepository = messageRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishMessage(String message){
        kafkaTemplate.send(TOPIC, message);

        this.template.convertAndSend("/topic/messages", message);

        LOGGER.info(message);
    }

    @Override
    public List<String> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(Message::getMessage).collect(Collectors.toList());
    }
}
