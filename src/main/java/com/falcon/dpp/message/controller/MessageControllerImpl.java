package com.falcon.dpp.message.controller;

import com.falcon.dpp.message.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageControllerImpl implements MessageController {

    private static final String EMPTY_JSON = "{}";

    private final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    @Autowired
    public MessageControllerImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Endpoint for sending the message to kafka
     * @param message - every valid JSON
     * @return
     */
    @Override
    @GetMapping("/publish")
    public ResponseEntity<Object> publishMessage(@RequestBody final Object message) {

            LOGGER.info("Starting pusblish message endpoint...");

            if (message.toString().equals(EMPTY_JSON)){
                LOGGER.warn("Message is empty JSON");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            messageService.publishMessage(message.toString());

            LOGGER.info("Publishing message endpoint ended successfully");
            return ResponseEntity.ok(message);
    }

    /**
     * Endpoint for retrieving all message persisted in the MongoDB
     * @return
     */
    @Override
    @GetMapping
    public ResponseEntity<List<String>> getAllMessages(){

        LOGGER.info("Starting publish message endpoint...");

        List<String> getAllMessages = messageService.getAllMessages();

        LOGGER.info("Publishing message endpoint ended successfully");
        return ResponseEntity.ok(getAllMessages);

    }
}
