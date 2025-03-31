package com.example.book.configuation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class KafkaConfigTest {

    @InjectMocks
    private KafkaConfig kafkaConfig;

    @BeforeEach
    void setUp() {
        // Thiết lập giá trị cho các trường bootstrapAddress và consumerGroupId
        // Sử dụng ReflectionTestUtils để thiết lập các giá trị private trong KafkaConfig
        ReflectionTestUtils.setField(
            kafkaConfig,
            "bootstrapAddress",
            "localhost:9092"
        );
        ReflectionTestUtils.setField(
            kafkaConfig,
            "consumerGroupId",
            "test-group"
        );
    }

    @Test
    void producerFactory() {
        // Kiểm tra phương thức producerFactory
        // Phương thức này tạo một ProducerFactory với các cấu hình cần thiết
        ProducerFactory<String, String> producerFactory =
            kafkaConfig.producerFactory();
        assertNotNull(producerFactory); // Kiểm tra producerFactory không null

        // Lấy cấu hình của producer và kiểm tra các giá trị
        Map<String, Object> configProps =
            ((DefaultKafkaProducerFactory<
                        String,
                        String
                    >) producerFactory).getConfigurationProperties();
        System.out.println("Producer Config: " + configProps); // In log cấu hình producer

        // Kiểm tra các giá trị cấu hình
        assertEquals(
            "localhost:9092",
            configProps.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)
        ); // Kiểm tra địa chỉ bootstrap
        assertEquals(
            StringSerializer.class,
            configProps.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)
        ); // Kiểm tra serializer cho key
        assertEquals(
            StringSerializer.class,
            configProps.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)
        ); // Kiểm tra serializer cho value
        assertEquals("all", configProps.get(ProducerConfig.ACKS_CONFIG)); // Kiểm tra cấu hình ACK
        assertEquals(3, configProps.get(ProducerConfig.RETRIES_CONFIG)); // Kiểm tra số lần thử lại
        assertEquals(
            30000,
            configProps.get(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG)
        ); // Kiểm tra thời gian chờ yêu cầu
    }

    @Test
    void kafkaTemplate() {
        // Kiểm tra phương thức kafkaTemplate
        // Phương thức này tạo một KafkaTemplate sử dụng producerFactory
        KafkaTemplate<String, String> kafkaTemplate =
            kafkaConfig.kafkaTemplate();
        assertNotNull(kafkaTemplate); // Kiểm tra kafkaTemplate không null
        assertNotNull(kafkaTemplate.getProducerFactory()); // Kiểm tra producerFactory không null
    }

    @Test
    void topic1() {
        // Kiểm tra phương thức topic1
        // Phương thức này tạo một topic mới với tên "my-topic", 1 partition và replication factor là 1
        NewTopic topic = kafkaConfig.topic1();
        assertNotNull(topic); // Kiểm tra topic không null
        assertEquals("my-topic", topic.name()); // Kiểm tra tên topic
        assertEquals(1, topic.numPartitions()); // Kiểm tra số partition
        assertEquals(1, topic.replicationFactor()); // Kiểm tra replication factor
    }

    @Test
    void consumerFactory() {
        // Kiểm tra phương thức consumerFactory
        // Phương thức này tạo một ConsumerFactory với các cấu hình cần thiết
        ConsumerFactory<String, String> consumerFactory =
            kafkaConfig.consumerFactory();
        assertNotNull(consumerFactory); // Kiểm tra consumerFactory không null

        // Lấy cấu hình của consumer và kiểm tra các giá trị
        Map<String, Object> configProps =
            ((DefaultKafkaConsumerFactory<
                        String,
                        String
                    >) consumerFactory).getConfigurationProperties();
        System.out.println("Consumer Config: " + configProps); // In log cấu hình consumer

        // Kiểm tra các giá trị cấu hình
        assertEquals(
            "localhost:9092",
            configProps.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG)
        ); // Kiểm tra địa chỉ bootstrap
        assertEquals(
            "test-group",
            configProps.get(ConsumerConfig.GROUP_ID_CONFIG)
        ); // Kiểm tra group ID
        assertEquals(
            StringDeserializer.class,
            configProps.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG)
        ); // Kiểm tra deserializer cho key
        assertEquals(
            StringDeserializer.class,
            configProps.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG)
        ); // Kiểm tra deserializer cho value
    }

    @Test
    void kafkaListenerContainerFactory() {
        // Kiểm tra phương thức kafkaListenerContainerFactory
        // Phương thức này tạo một ConcurrentKafkaListenerContainerFactory sử dụng consumerFactory
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            kafkaConfig.kafkaListenerContainerFactory();
        assertNotNull(factory); // Kiểm tra factory không null
        assertNotNull(factory.getConsumerFactory()); // Kiểm tra consumerFactory không null
    }
}
