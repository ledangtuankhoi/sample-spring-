package com.example.borrowing.Repository;

import com.example.borrowing.Model.BorrowingEntity;
import com.example.borrowing.Model.BorrowingEntity.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BorrowingRepository
    extends JpaRepository<BorrowingEntity, String> {
    //    BorrowingEntity save()
    // @Query(
    //   // value = "SELECT b FROM BookEntity b LEFT JOIN BorrowingEntity br ON br.book.id = b.id
    // WHERE br.employee.id = ?1"
    //   value = "UPDATE borrorwing br SET br.employeeId = 12312 WHERE br.bookId = 1    AND br.status
    // like `BORROWE` ORDER BY br.borrowingDate LIMIT 1"
    // )
    // BookEntity updateEmpByBookId(String BookId);
    // List<Tag> findTagsByTutorialsId(Long tutorialId);

    List<BorrowingEntity> findByBookId(String BookId);
    List<BorrowingEntity> findByEmployeeId(String employeeId);
    BorrowingEntity findByBookIdAndEmployeeId(String bookId, String employeeId);

    @Modifying
    @Query(
        "update BorrowingEntity b set b.status = :status where b.id = :borrowingId"
    )
    void updateStatusById(
        @Param("borrowingId") String borrowingId,
        @Param("status") Status status
    );
}
