package com.example.employee.Repository;

import com.example.employee.Model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
    extends JpaRepository<EmployeeEntity, String> {
    // Get borrowed books for employee GET /api /v1/employees/{employeeId}/books
    // @Query("select u from User u where u.emailAddress = ?1")
    // User findByEmailAddress(String emailAddress);
    // @Query("SELECT b FROM book b LEFT JOIN borrorwing br ON br.`bookId` = b.id
    // WHERE br.`employeeId` = ?1")
    // public List<BookEntity> findBorrowedBooksByEmployeeId(String id);
}
