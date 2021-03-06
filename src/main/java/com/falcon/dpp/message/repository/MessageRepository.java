package com.falcon.dpp.message.repository;

import com.falcon.dpp.message.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
