package com.falcon.dpp.message.message;

import com.falcon.dpp.message.message.model.Message;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
