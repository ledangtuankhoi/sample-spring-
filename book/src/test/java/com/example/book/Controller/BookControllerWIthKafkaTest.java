package com.example.book.Controller;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(
    partitions = 1,
    topics = { "my-topic" },
    brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" }
)
public class BookControllerWIthKafkaTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private BlockingQueue<ConsumerRecord<String, String>> records;

    @BeforeEach
    void setup() {
        records = new LinkedBlockingQueue<>();
    }

    @AfterEach
    void tearDown() {
        records.clear();
    }

    @KafkaListener(topics = "my-topic")
    public void listen(ConsumerRecord<String, String> record) {
        records.add(record);
    }

    @Test
    void testKafkaSendAndReceive() throws InterruptedException {
        String testMessage = "Integration test message";

        // Send message
        kafkaTemplate.send("my-topic", testMessage);

        // Verify message received
        ConsumerRecord<String, String> record = records.take(); // Blocking until message received
        assertEquals(testMessage, record.value());
    }
}
