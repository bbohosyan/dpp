package com.falcon.dpp.message.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MessageController {

    ResponseEntity<Object> publishMessage(@RequestBody final Object message);
    ResponseEntity<List<String>> getAllMessages();

}
