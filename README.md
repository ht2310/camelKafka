**Kafka Message Aggregation Project for assesment**

This project demonstrates a data pipeline that produces Kafka messages, consumes them via Apache Camel, aggregates the messages by a common identifier, and stores the aggregated result as JSON. The UI application provides a way to visualize or interact with the processed data.


**Features**

Kafka message production
Apache Camel integration for message consumption
Message aggregation by ID (limit: 10 per group)
Aggregated output in JSON format
UI to view or interact with the results

**Technologies Used**

Apache Kafka (running in Docker)
Apache Camel
Java + Maven
Angular for the frontend

**Prerequisites**

Make sure the following are installed:
Docker & Docker Compose
Java 17+ and Maven
Node.js and npm (for UI)


**STEPS**

docker-compose up -d
mvn clean install
cd kafka-camel-ui
npm install
ng serve
This will start the Angular development server. Visit http://localhost:4200 to access the UI.
