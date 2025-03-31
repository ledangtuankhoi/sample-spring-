package com.example.book.repository;

import com.example.book.model.BookEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<BookEntity, String> {
    @Query(
        "SELECT b FROM BookEntity b LEFT JOIN BorrowingEntity br ON br.`bookId` = b.id WHERE br.`employeeId` = :employeeId"
    )
    List<BookEntity> findBooksToBorrowingByEmployeeId(String employeeId);

    @Query(
        "SELECT b FROM BookEntity b JOIN BorrowingEntity br ON br.`bookId` = b.id WHERE br.`employeeId` = :employeeId AND br.status = 'BORROWED'"
    )
    List<BookEntity> findBooksToBorrowedByEmployeeId(String employeeId);

    BookEntity findByIdAndName(String id, String name);

    Optional<BookEntity> findByName(String name);

    Optional<BookEntity> findByAuthor(String author);

    Optional<BookEntity> findByNameAndAuthor(String name, String author);
}
