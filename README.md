# sample-spring-
Project Overview: Microservices Architecture with Spring Boot, Kafka, Zookeeper, and Eureka


## Project Overview: Microservices Architecture with Spring Boot, Kafka, Zookeeper, and Eureka

This project demonstrates a **microservices architecture** using **Spring Boot** for service development, **Kafka** for message brokering, **Zookeeper** for managing Kafka, and **Eureka Server** for service discovery. The application includes multiple services that interact with one another while using **Docker Compose** for container orchestration.

---

### Features
- **Gateway Service**: Acts as an API gateway to route requests to respective microservices.
- **Eureka Server**: Handles service discovery, enabling dynamic scaling and communication between microservices.
- **Kafka & Zookeeper**: Manages event-driven communication between microservices with a reliable message queue.
- **Microservices**:
  - **Book Service**: Manages book-related functionalities.
  - **Employee (Empl) Service**: Handles employee data.
  - **Borrowing Service**: Manages borrowing records.
- **MySQL Databases**: Each service has its own isolated database instance.

---

### Architecture
1. **Gateway Service**:
   - Port: `8080`
   - Features:
     - Centralized entry point for all client requests.
     - Service routing based on Eureka registry.
   - Hot-reload enabled for fast development.

2. **Eureka Server**:
   - Port: `8761`
   - Features:
     - Dynamic service discovery.
     - Microservices automatically register and deregister with Eureka.

3. **Kafka & Zookeeper**:
   - Kafka:
     - Port: `9092`
     - Features: Message broker for microservices communication.
   - Zookeeper:
     - Port: `2181`
     - Manages Kafka brokers.

4. **Book, Employee, and Borrowing Services**:
   - Individual services exposed at ports:
     - Book: `8081`
     - Employee: `8082`
     - Borrowing: `8083`
   - Hot-reload enabled via Maven (`mvn spring-boot:run`).
   - Each service connects to its own **MySQL database**.

5. **MySQL Databases**:
   - Each microservice has an independent MySQL instance to ensure database isolation.

---

### How to Run
1. Clone this repository:
   ```bash
   git clone <repository_url>
   cd <repository_folder>
   ```

2. Start the application:
   ```bash
   docker-compose up --build
   ```

3. Access the services:
   - **Gateway**: `http://localhost:8080`
   - **Eureka Dashboard**: `http://localhost:8761`
   - Other services:
     - Book: `http://localhost:8081`
     - Employee: `http://localhost:8082`
     - Borrowing: `http://localhost:8083`

4. Stop the application:
   ```bash
   docker-compose down
   ```

---

### Environment Variables
Each service supports environment variables for configuration. Example:
- **Gateway Service**:
  - `SPRING_PROFILES_ACTIVE`: Profile settings (`docker`).
  - `JAVA_OPTS`: JVM options.

- **Database Configuration**:
  - `MYSQL_DATABASE`: Database name.
  - `MYSQL_USER`: Database user.
  - `MYSQL_PASSWORD`: User password.
  - `MYSQL_ROOT_PASSWORD`: Root password.

---

### Development Setup
- **Code Synchronization**:
  - Volumes are mounted to sync local files (`src` and `pom.xml`) with the running containers.
  - Modify code locally for hot-reloading within containers.

- **Logging**:
  - JSON logging with size-limited log files for efficient debugging.

---

### Dependencies
- **Docker** & **Docker Compose**.
- Spring Boot, Maven, Kafka, MySQL, and Zookeeper images.

---

### Key Notes
- Ensure the `share-network` exists before running the application:
  ```bash
  docker network create share-network
  ```
- Services use the `mvn spring-boot:run` command, enabling live development with Maven.

---

Feel free to customize and extend this setup to fit your use case! 🎉
