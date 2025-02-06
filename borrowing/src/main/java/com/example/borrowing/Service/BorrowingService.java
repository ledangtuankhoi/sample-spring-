package com.example.borrowing.Service;

import com.example.borrowing.Exception.BookServiceException;
import com.example.borrowing.Model.BookEntity;
import com.example.borrowing.Model.BorrowingEntity;
import com.example.borrowing.Model.BorrowingEntity.Status;
import com.example.borrowing.Model.EmployeeEntity;
import com.example.borrowing.Repository.BorrowingRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    private BookService bookService;
    private EmployeeService employeeService;

    public BorrowingService(
        BorrowingRepository borrowingRepository,
        BookService bookService,
        EmployeeService employeeService
    ) {
        this.borrowingRepository = borrowingRepository;
        this.bookService = bookService;
        this.employeeService = employeeService;
    }

    public List<BorrowingEntity> getByEmplId(String id) {
        System.err.println("id: " + id);
        return borrowingRepository.findByEmployeeId(id);
    }

    public List<BorrowingEntity> getByBookId(String id) {
        System.err.println("id: " + id);
        return borrowingRepository.findByBookId(id);
    }

    public boolean isBook(String bookId) {
        if (bookService.getBookById(bookId).getId().isEmpty() == false) {
            return true;
        }
        return false;
    }

    public boolean isEmpl(String emplId) {
        try {
            if (employeeService.getById(emplId).getId().isEmpty() == false) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public BorrowingEntity save(BorrowingEntity borrowing) {
        BorrowingEntity entityOld =
            borrowingRepository.findByBookIdAndEmployeeId(
                borrowing.getBookId(),
                borrowing.getEmployeeId()
            );

        // if (entityOld == null) {
        //     return borrowingRepository.save(borrowing);
        // }
        if (entityOld == null) {
            return borrowingRepository.save(borrowing);
        }
        // So sánh nếu có sự khác biệt
        if (!entityOld.equals(borrowing)) {
            entityOld.setStatus(borrowing.getStatus());
            entityOld.setBookId(borrowing.getBookId());
            entityOld.setEmployeeId(borrowing.getEmployeeId());
            entityOld.setReturnBorrowing(borrowing.getReturnBorrowing());

            return borrowingRepository.save(entityOld);
        }
        return entityOld;
    }

    public void updateStatus(String borrwingId, Status status) {
        borrowingRepository.updateStatusById(borrwingId, status);
    }

    public BorrowingEntity findByBookIdAndEmployeeId(
        String bookId,
        String employeeId
    ) {
        return borrowingRepository.findByBookIdAndEmployeeId(
            bookId,
            employeeId
        );
    }
}
