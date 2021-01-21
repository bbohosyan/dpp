package com.falcon.dpp.message;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.falcon.dpp.message.repository.MessageRepository;
import com.falcon.dpp.message.service.MessageConsumer;
import com.falcon.dpp.message.service.MessageService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
public class MessageConsumerAndSenderTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void setUp(){
        messageRepository.deleteAll();
    }

    @Test
    public void consumeMessage_messageIsValid_messageIsConsumedSuccessfully() {

        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker));
        Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new StringDeserializer()).createConsumer();
        consumer.subscribe(singleton("messages"));
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Logger logger = (Logger) LoggerFactory.getLogger(MessageConsumer.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        //produce
        messageService.publishMessage("message");

        //consume
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "messages");
        assertThat(singleRecord).isNotNull();

        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("received message: message", logsList.get(0)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(0)
                .getLevel());
        assertEquals(messageService.getAllMessages().size(), 1);
        assertEquals(messageService.getAllMessages().get(0), "message");
    }
}
