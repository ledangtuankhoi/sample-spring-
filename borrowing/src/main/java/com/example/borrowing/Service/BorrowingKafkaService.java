package com.example.borrowing.Service;

import com.example.borrowing.Exception.BookServiceException;
import com.example.borrowing.Model.BookEntity;
import com.example.borrowing.Model.BorrowingEntity;
import com.example.borrowing.Model.BorrowingEntity.Status;
import com.example.borrowing.Model.EmployeeEntity;
import com.example.borrowing.Producer.BorrowingProducer;
import com.example.borrowing.Repository.BorrowingRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// @AllArgsConstructor
// @NoArgsConstructor
public class BorrowingKafkaService {

    @Autowired
    private BorrowingProducer borrowingProducer;

    @Autowired
    private BorrowingRepository borrowingRepository;

    private BookService bookService;
    private EmployeeService employeeService;

    public BorrowingKafkaService(
        BorrowingRepository borrowingRepository,
        BookService bookService,
        EmployeeService employeeService
    ) {
        this.borrowingRepository = borrowingRepository;
        this.bookService = bookService;
        this.employeeService = employeeService;
    }

    public void sendRequestBorrowBook(
        String requestId,
        String bookId,
        String emplId
    ) {
        borrowingProducer.sendValidate(requestId, bookId, emplId);
    }

    public void sendBorrowingUpdateStatus(String bookId, String emplId) {
        // check book not borrowing with empl
        BorrowingEntity entity = borrowingRepository.findByBookIdAndEmployeeId(
            bookId,
            emplId
        );
        if (entity.getStatus() != Status.BORROWED) {
            borrowingProducer.sendUpdateStatus(
                entity.getId(),
                bookId,
                emplId,
                Status.BORROWED
            );
        }
    }
}
