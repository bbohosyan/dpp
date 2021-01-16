package com.falcon.dpp.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "message";

    public MessageController(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/publish/{message}")
    public ResponseEntity<String> post(@PathVariable("message") final String message){
        kafkaTemplate.send(TOPIC, message);

        logger.info(message);

        return ResponseEntity.ok(message);
    }
}
