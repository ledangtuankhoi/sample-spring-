---

# Microservices Architecture with Spring Boot, Kafka, Zookeeper, and Eureka

## Project Overview

This project demonstrates a **microservices architecture** using **Spring Boot** for service development, **Kafka** for message brokering, **Zookeeper** for managing Kafka, and **Eureka Server** for service discovery. The application includes multiple services that interact with one another while using **Docker Compose** for container orchestration. Kafka serves as the backbone for asynchronous communication between the microservices, promoting scalability and decoupling.

### Technologies Utilized

- **Spring Boot**: For building Java-based microservices.
- **Kafka**: For reliable, event-driven messaging between services.
- **Zookeeper**: To manage Kafka brokers and ensure high availability.
- **Eureka Server**: For service discovery to allow seamless communication among services.
- **Docker Compose**: For managing Docker containers for each microservice and infrastructure component.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Architecture](#architecture)
    1. [Gateway Service](#gateway-service)
    2. [Eureka Server](#eureka-server)
    3. [Kafka & Zookeeper](#kafka--zookeeper)
    4. [Microservices](#microservices)
4. [Debugging Configuration](#debugging-configuration)
5. [Technologies Used](#technologies-used)
6. [Getting Started](#getting-started)
7. [Kafka Usage](#kafka-usage)
8. [Additional Information](#additional-information)
9. [Troubleshooting](#troubleshooting)

## Features

- **Gateway Service**: API Gateway that routes requests to appropriate microservices.
- **Eureka Server**: Manages service discovery, allowing dynamic microservice scaling.
- **Kafka & Zookeeper**: Facilitates event-driven communication between services with Kafka as a message broker and Zookeeper for Kafka management.
- **Microservices**:
    - **Book Service**: Manages CRUD operations for books.
    - **Employee Service**: Manages employee-related data.
    - **Borrowing Service**: Handles borrowing records, leveraging Kafka for inter-service communication.
- **MySQL Databases**: Each microservice uses a separate MySQL instance to ensure data isolation.

## Architecture

### 1. Gateway Service

- **Port**: `8080`
- **Role**: Acts as the centralized entry point for all client requests, routing them to the appropriate microservice via **Eureka**. This service abstracts the communication between clients and the backend services, making the architecture cleaner and easier to manage.

### 2. Eureka Server

- **Port**: `8761`
- **Role**: Ensures service discovery, allowing microservices to dynamically register themselves and communicate with each other. Eureka Server helps facilitate load balancing and fault tolerance in the microservice environment.

### 3. Kafka & Zookeeper

- **Kafka**:
    - **Role**: A message broker that enables reliable, event-driven communication between microservices. Kafka decouples services by allowing them to communicate asynchronously, increasing scalability and fault tolerance.
    - **Benefit**: By leveraging Kafka, the system is able to handle high-throughput events and large volumes of data with ease.
- **Zookeeper**:
    - **Role**: Zookeeper manages Kafka brokers and coordinates Kafka's partitioning and replication. It ensures Kafka’s high availability and reliability, which is crucial in a distributed system.
    - **Benefit**: Zookeeper handles coordination for Kafka to maintain the health and synchronization of services.

### 4. Microservices

- **Book Service**: Manages CRUD operations related to books in the library system. This service interacts with the database to perform operations such as creating, updating, and retrieving books.
- **Employee Service**: Manages employee data. It stores employee information such as personal details and borrowing history. This service integrates with other services for cross-service communication.

- **Borrowing Service**: Handles borrowing records and communicates events (e.g., a successful borrowing) to Kafka, enabling other services to react to these events. This service is tightly coupled with Kafka for event-driven communication.

## Getting Started

### Prerequisites

- **Java 11** or higher
- **Maven**
- **Docker** and **Docker Compose**
- **Apache Kafka** setup

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/sample-spring.git
    ```
2. Navigate to the project directory:
    ```sh
    cd sample-spring
    ```
3. Build the project:
    ```sh
    mvn clean install
    ```

### Running the Application

1. Start Kafka and Zookeeper using Docker Compose:
    ```sh
    docker-compose up -d
    ```
2. Run the Spring Boot application:
    ```sh
    mvn spring-boot:run
    ```

## Debugging Configuration

### Steps to Debug Spring Boot in Docker

- Configure Docker Compose and VS Code for remote debugging.
- Ensure that debug ports (e.g., `5005`, `5006`, `5007`) are exposed for each microservice.
- Use the `launch.json` configuration in VS Code to connect to running Docker containers and debug effectively.

---

## Kafka Usage in the Project

### Kafka as a Message Broker

Kafka is integral to this project as it acts as the message broker facilitating asynchronous communication between the **Borrowing Service** and other services. Kafka enables event-driven architecture, allowing microservices to communicate without direct dependencies. This decoupling improves scalability, fault tolerance, and flexibility in communication.

### Kafka in the Borrowing Service

When a book is successfully borrowed, the **Borrowing Service** publishes an event to Kafka. Other services, such as the **Notification Service**, can consume this event to take action (e.g., sending a notification about the borrowing). Kafka allows these actions to occur asynchronously and decouples the services involved.

Example of Kafka Integration in **BorrowingService**:

```java
// Location: src/main/java/com/example/borrowing/Controller/BorrowingController.java
public class BorrowingController {

    // Method triggered when a book is borrowed
    public ResponseEntity<String> borrowingBook(
        BorrowingRequest borrowingRequest
    ) {
        // Logic for borrowing a book

        // Publish event to Kafka after successful borrowing
        borrowingProducer.sendBorrowingEvent(borrowingRequest);
        return ResponseEntity.ok("Book borrowed successfully!");
    }
}

```

### Common Kafka Commands

To interact with Kafka during development or troubleshooting, here are some useful Kafka commands:

| **Command Description**                                     | **Command**                                                                                                             |
| ----------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------- |
| **View messages in a topic**                                | `kafka-console-consumer --bootstrap-server localhost:9092 --topic borrowing-topic --from-beginning`                     |
| **List all Consumer Groups**                                | `kafka-consumer-groups --bootstrap-server localhost:9092 --list`                                                        |
| **Describe a Consumer Group (services consuming messages)** | `kafka-consumer-groups --bootstrap-server localhost:9092 --group borrowing-group --describe`                            |
| **Create a new Kafka topic**                                | `kafka-topics --create --bootstrap-server localhost:9092 --topic borrowing-topic --partitions 1 --replication-factor 1` |
| **List all Kafka topics**                                   | `kafka-topics --list --bootstrap-server localhost:9092`                                                                 |
| **Delete a Kafka topic**                                    | `kafka-topics --delete --bootstrap-server localhost:9092 --topic borrowing-topic`                                       |

### Common MYSQL Commands

-import database to mysqldb
docker exec -i one-book-mysqldb-1 mysql -uroot -ppassword book < book/db-sql/book.sql

-export database to mysqldb
docker exec -i one-book-mysqldb-1 mysqldump -uroot -ppassword book > book/db-sql/book.sql



### Key Kafka-Related Files and Their Role

1. **`BorrowingProducer.java`**:

    - **Purpose**: Responsible for sending (producing) Kafka messages to a topic when a borrowing event occurs (e.g., a book is borrowed).
    - **Role**: The producer serializes the borrowing event and sends it to a Kafka topic for other services to consume.

2. **`BorrowingConsumer.java`**:

    - **Purpose**: Responsible for consuming (receiving) Kafka messages from a Kafka topic, processing the borrowing event, and performing necessary actions in the system.
    - **Role**: The consumer listens to Kafka topics and triggers processing in response to events, enabling real-time actions such as updating records or triggering notifications.

3. **`KafkaConstants.java`**:

    - **Purpose**: Stores constants related to Kafka, such as topic names.
    - **Role**: Centralizes Kafka-related configuration, ensuring consistency across the project when referring to Kafka topics.

4. **`BorrowingEvent.java`**:

    - **Purpose**: Defines the structure of the Kafka message, i.e., the event object that is sent and received via Kafka.
    - **Role**: Provides a standardized format for the event data, ensuring that producers and consumers can serialize/deserialize the event correctly.

5. **`KafkaConfig.java`**:
    - **Purpose**: Configures the Kafka producer and consumer, including the necessary serializers, deserializers, and KafkaTemplate.
    - **Role**: Provides essential configuration for integrating Kafka with Spring Boot, ensuring that messages are produced and consumed effectively, and managing the communication setup between microservices.

---

### Conclusion

Kafka empowers this project with event-driven architecture, decoupling microservices and enabling real-time communication through message queuing. Each Kafka-related file has a clear purpose, from producing messages to consuming them, and the configuration ensures smooth communication across services. By utilizing Kafka, the project maintains scalability and flexibility, which are crucial for a robust microservices architecture.

---
