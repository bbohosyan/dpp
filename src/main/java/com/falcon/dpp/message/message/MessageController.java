package com.falcon.dpp.message.message;

import com.falcon.dpp.message.message.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/publish/{message}")
    public ResponseEntity<String> publishMessage(@PathVariable("message") final String message) {

        LOGGER.info("Starting pusblish message endpoint...");

        messageService.publishMessage(message);

        LOGGER.info("Publishing message endpoint ended successfully");
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllMessages(){

        LOGGER.info("Starting pusblish message endpoint...");

        List<String> getAllMessages = messageService.getAllMessages();

        LOGGER.info("Pusblishing message endpoint ended successfully");
        return ResponseEntity.ok(getAllMessages);

    }
}
