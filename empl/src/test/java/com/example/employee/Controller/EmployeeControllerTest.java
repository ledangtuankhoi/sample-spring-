package com.example.employee.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.employee.Model.BookEntity;
import com.example.employee.Model.BorrowingEntity;
import com.example.employee.Model.BorrowingEntity.Status;
import com.example.employee.Model.EmployeeEntity;
import com.example.employee.Repository.EmployeeRepository;
import com.example.employee.Service.EmployeeService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void testGetBooksBorrowed() {
        // String emplid = "2";

        // BookEntity bookEntity = new BookEntity("asdf", "aasdfasd", true);

        // EmployeeEntity employeeEntity = new EmployeeEntity(
        //     "13",
        //     "123",
        //     "sdfa",
        //     true
        // );

        // // BorrowingEntity borrowingEntity = new BorrowingEntity("2", );
        // LocalDateTime localDateTime = LocalDateTime.now();
        // Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        // BorrowingEntity borrowingEntity = new BorrowingEntity(
        //     instant,
        //     Status.BORROWED,
        //     "2",
        //     "2"
        // );

        // List<BookEntity> result = employeeController.getBooksBorrowed(emplid);

        // assertNotNull(result);
        // assertEquals("2", result.get(0).getId());
    }
}
