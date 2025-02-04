package com.example.borrowing.Producer;

import com.example.borrowing.Constant.KafkaConstants;
import com.example.borrowing.Event.BorrowingEvent;
import com.example.borrowing.Model.BorrowingEntity;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BorrowingProducer {

    private static final Logger logger = LoggerFactory.getLogger(
        BorrowingProducer.class
    );

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendBorrowingEvent(BorrowingEntity entity) {
        BorrowingEvent event = new BorrowingEvent(
            entity.getStatus(),
            entity.getBookId(),
            entity.getEmployeeId(),
            "Borrow a book",
            "borrowed"
        );

        List<String> topics = Arrays.asList(
            KafkaConstants.BOOK_TOPIC,
            KafkaConstants.EMP_TOPIC
        );

        for (String topic : topics) {
            kafkaTemplate
                .send(topic, event.toJson())
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Sent event to {}: {}", topic, event);
                    } else {
                        logger.error(
                            "Failed to send event to {}: {}",
                            topic,
                            ex.getMessage()
                        );
                    }
                });
        }
    }
}
