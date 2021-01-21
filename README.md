# Data Processing Pipeline

# Falcon.io

Technology stack : Spring Boot, Kafka, MongoDB, Docker, Kafka

# Installations

Docker

Java 11

MongoDB

Kafka 2.12

# Run

docker-compose up

Run the application from the IDE

# Endpoints

GET - /messages/publish - Sending the message to kafka and sending message to the frontend subscriber

Possible return status codes: 

400 - when JSON is not valid

204 - when JSON is empty

200 - when JSON is valid and is not empty

GET - /messages - Retrieving all the messages from MongoDB

Possible return status codes:

200 return all the messages from the MongoDB
