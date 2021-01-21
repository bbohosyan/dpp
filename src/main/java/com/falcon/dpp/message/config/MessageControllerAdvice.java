package com.falcon.dpp.message.config;

import com.falcon.dpp.message.controller.MessageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = {"com.falcon.dpp.message.controller"} )
public class MessageControllerAdvice {

    private final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    /**
     * Handler is logging and returning 400 status code
     * when JSON is not valid
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public void handleBadRequest() {
        LOGGER.info("Message is not valid JSON");
    }
}
