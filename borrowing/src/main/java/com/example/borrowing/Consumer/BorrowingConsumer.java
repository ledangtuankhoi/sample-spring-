package com.example.borrowing.Consumer;

import com.example.borrowing.Constant.KafkaConstants;
import com.example.borrowing.Event.BorrowingEvent;
import com.example.borrowing.Event.BorrowingUpdateEvent;
import com.example.borrowing.Event.BorrowingValidated;
import com.example.borrowing.Model.BookEntity;
import com.example.borrowing.Model.BorrowingEntity;
import com.example.borrowing.Model.BorrowingEntity.Status;
import com.example.borrowing.Producer.BorrowingProducer;
import com.example.borrowing.Repository.BorrowingRepository;
import com.example.borrowing.Service.BookService;
import com.example.borrowing.Service.BorrowingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BorrowingConsumer {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowingProducer borrowingProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BorrowingRepository borrowingRepository;

    private final Map<String, BorrowingValidated> validationResults =
        new ConcurrentHashMap<>();

    @KafkaListener(
        topics = KafkaConstants.BORROWING_VALIDATION_TOPIC,
        groupId = KafkaConstants.BORROWING_GROUP
    )
    public void listenBookValidate(String event) {
        try {
            BorrowingValidated response = objectMapper.readValue(
                event,
                BorrowingValidated.class
            );
            String requestId = response.getRequestId();
            System.out.println(
                "listen from" +
                KafkaConstants.BOOK_VALIDATION_TOPIC +
                " with " +
                response
            );

            validationResults.compute(requestId, (key, existingResult) -> {
                if (existingResult == null) {
                    existingResult = new BorrowingValidated(
                        requestId,
                        response.getBookId(),
                        response.getEmployeeId(),
                        false,
                        false
                    );
                }
                if (response.isBookValid()) {
                    existingResult.setBookValid(true);
                }
                if (response.isEmplValid()) {
                    existingResult.setEmplValid(true);
                }
                return existingResult;
            });
            // Kiểm tra nếu cả hai service đều hợp lệ thì tạo BorrowingEntity
            BorrowingValidated result = validationResults.get(
                response.getRequestId()
            );

            if (result.isBookValid() && result.isEmplValid()) {
                BorrowingEntity borrowing = new BorrowingEntity(
                    response.getBookId(),
                    response.getEmployeeId()
                );
                borrowing.setStatus(Status.BORROWED);
                borrowing.setId(requestId);

                borrowingService.save(borrowing);
                validationResults.remove(response.getRequestId());

                System.out.println("✅ Borrowing created: " + borrowing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(
        topics = KafkaConstants.BORROWING_UPDATE,
        groupId = KafkaConstants.BORROWING_GROUP,
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void ListenUpdateBorrowing(String event) {
        try {
            BorrowingUpdateEvent responce = objectMapper.readValue(
                event,
                BorrowingUpdateEvent.class
            );
            BorrowingEntity entity = borrowingService.findByBookIdAndEmployeeId(
                responce.getBookId(),
                responce.getEmployeeId()
            );

            System.out.println(
                "listen from " +
                KafkaConstants.BORROWING_UPDATE +
                " with " +
                responce.toString()
            );

            entity.setStatus(responce.getStatus());
            borrowingService.save(entity);

            // send notification to book service and employee service
            borrowingProducer.sendNotification(
                responce.getBookId(),
                responce.getEmployeeId(),
                responce.getStatus()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
