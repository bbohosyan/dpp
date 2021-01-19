package com.falcon.dpp.message.message;

import com.falcon.dpp.message.message.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private static final String TOPIC = "message";

    private final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/publish/{message}")
    public ResponseEntity<String> post(@PathVariable("message") final String message) {

        LOGGER.info("Starting pusblish message endpoint...");

        kafkaTemplate.send(TOPIC, message);

        this.template.convertAndSend("/topic/messages", message);
        LOGGER.info(message);

        LOGGER.info("Publish message endpoint ended successfully");
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllMessages(){

        LOGGER.info("Starting pusblish message endpoint...");

        List<String> getAllMessages = messageRepository.findAll().stream()
                .map(Message::getMessage).collect(Collectors.toList());

        return ResponseEntity.ok(getAllMessages);

    }
}
