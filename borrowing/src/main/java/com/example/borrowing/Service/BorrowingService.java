package com.example.borrowing.Service;

import com.example.borrowing.Model.BookEntity;
import com.example.borrowing.Model.BorrowingEntity;
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

    public BorrowingService(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    public List<BorrowingEntity> getByEmplId(String id) {
        System.err.println("id: " + id);
        return borrowingRepository.findByEmployeeId(id);
    }
    // @Override
    // public BorrowingEntity updateBorrowing(String employeeId, String bookId) {
    //     EmployeeEntity employeeEntity = employeeRepository
    //         .findById(employeeId)
    //         .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

    //     BookEntity bookEntity = bookRepository
    //         .findById(bookId)
    //         .orElseThrow(() -> new BookNotFoundException(bookId));
    //     BorrowingEntity borrowingEntity = borrowingRepository.findByBookId(
    //         bookId
    //     );

    //     // if (borrowingEntity.getBook().getId().equals(bookEntity.getId())) {
    //     //   System.err.println("\"borrorwing does not belongs to book\"");
    //     // }

    //     if (borrowingEntity.getStatus() != BorrowingEntity.Status.BORROWED) {
    //         BorrowingEntity old = borrowingEntity;
    //         old.setStatus(BorrowingEntity.Status.RETURNED);
    //         borrowingRepository.save(old);
    //     }
    //     BorrowingEntity newEn = borrowingEntity;
    //     newEn.setStatus(BorrowingEntity.Status.BORROWED);
    //     newEn.setEmployeeId(employeeEntity.getId());
    //     borrowingRepository.save(newEn);

    //     return newEn;
    // }

    // public BorrowingEntity updateStatusToBorrowing(
    //     String employeeId,
    //     String bookId,
    //     BorrowingEntity entity
    // ) {
    //     employeeRepository
    //         .findById(employeeId)
    //         .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

    //     bookRepository
    //         .findById(bookId)
    //         .orElseThrow(() -> new BookNotFoundException(bookId));

    //     BorrowingEntity borrowingEntity =
    //         borrowingRepository.findByBookIdAndEmployeeId(bookId, employeeId);
    //     if (borrowingEntity.getId() == null) {
    //         throw new EntityNotFoundException(
    //             "No borrowing record found for employee " +
    //             employeeId +
    //             " and book " +
    //             bookId
    //         );
    //     }
    //     borrowingEntity.setStatus(entity.getStatus());
    //     borrowingEntity.setReturnBorrowing(entity.getReturnBorrowing());
    //     return borrowingRepository.save(borrowingEntity);
    // }
}
