# Data Processing Pipeline

# Falcon.io

Technology stack : Kafka, Postgresql, WebSocket, Stomp.js

# Installations
Before you can build this project, you must install and configure the following dependencies on your machine:

Docker
Java 11
MongoDB
Kafka 2.12

# Run
docker-compose up

Run the application from the IDE

# Endpoints

GET - /messages/publish - Sending the message to kafka and sending message to the frontend subscriber
GET - /messages - Retrieving all the messages from MongoDB
