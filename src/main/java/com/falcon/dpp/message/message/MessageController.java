package com.falcon.dpp.message.message;

import com.falcon.dpp.message.message.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public void handleException() {
        System.out.println("Message is not valid JSON");
    }

    @GetMapping("/publish")
    public ResponseEntity<Object> publishMessage(@RequestBody final Object message) {
            LOGGER.info("Starting pusblish message endpoint...");

            messageService.publishMessage(message.toString());

            LOGGER.info("Publishing message endpoint ended successfully");
            return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllMessages(){

        LOGGER.info("Starting publish message endpoint...");

        List<String> getAllMessages = messageService.getAllMessages();

        LOGGER.info("Publishing message endpoint ended successfully");
        return ResponseEntity.ok(getAllMessages);

    }
}
