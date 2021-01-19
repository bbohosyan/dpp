package com.falcon.dpp.message.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    private static final String TOPIC = "message";

    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/publish/{message}")
    public ResponseEntity<String> post(@PathVariable("message") final String message) {

        logger.info("Starting pusblish message endpoint...");

        kafkaTemplate.send(TOPIC, message);

        this.template.convertAndSend("/topic/messages", message);
        logger.info(message);

        logger.info("Publish message endpoint ended successfully");
        return ResponseEntity.ok(message);
    }
}
