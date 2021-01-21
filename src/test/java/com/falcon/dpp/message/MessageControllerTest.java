package com.falcon.dpp.message;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.falcon.dpp.message.controller.MessageController;
import com.falcon.dpp.message.model.Message;
import com.falcon.dpp.message.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedKafka
public class MessageControllerTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ObjectWriter objectWriter;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    public void setUp(){
        messageRepository.deleteAll();
        
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        Logger logger = (Logger) LoggerFactory.getLogger(MessageController.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    public void publishMessage_validRequest_returnsOK() throws Exception {
        String requestJson=objectWriter.writeValueAsString("{'message':'testmessage'}");
        this.mockMvc.perform(get("/messages/publish")
                .contentType(APPLICATION_JSON_VALUE)
                            .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void publishMessage_invalidMessageJSON_returnsBadRequest() throws Exception {

        this.mockMvc.perform(get("/messages/publish")
                .contentType(APPLICATION_JSON)
                .content("'message':'test message'}}}}}"))
                .andDo(print()).andExpect(status().isBadRequest());

        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Message is not valid JSON", logsList.get(0)
                .getMessage());
    }

    @Test
    public void publishMessage_emptyMessageJSON_returnsBadRequest() throws Exception {

        String requestJson=objectWriter.writeValueAsString("{}");

        this.mockMvc.perform(get("/messages/publish")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isNoContent());

        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Message is empty JSON", logsList.get(1)
                .getMessage());
    }

    @Test
    public void getAllMessages_oneMessageInMongoDBreturnsOk() throws Exception {

        messageRepository.save(new Message("message"));

        String result = this.mockMvc.perform(get("/messages")
                .contentType(APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<String> response = objectMapper.readValue(result, List.class);

        assertEquals(response.size(), 1);
        assertEquals(response.get(0), "message");
    }
}
