package com.example.book.Repository;

import com.example.book.Model.BookEntity;
// import org.hibernate.mapping.List;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<BookEntity, String> {
    @Query(
        // "SELECT b FROM BookEntity b JOIN b.borrowings br WHERE br.employee.id = :employeeId"
        "SELECT b FROM BookEntity b LEFT JOIN BorrowingEntity br ON br.`bookId` = b.id WHERE br.`employeeId` = :employeeId"
    )
    List<BookEntity> findBooksToBorrowingByEmployeeId(String employeeId);

    @Query(
        // "SELECT b FROM BookEntity b JOIN b.borrowings br WHERE br.employee.id = :employeeId"
        "SELECT b FROM BookEntity b JOIN BorrowingEntity br ON br.`bookId` = b.id WHERE br.`employeeId` = :employeeId AND br.status = 'BORROWED'"
    )
    List<BookEntity> findBooksToBorrowedByEmployeeId(String employeeId);

    BookEntity findByIdAndName(String id, String name);
    // Option findByIdAndName(String id, String name);
}
