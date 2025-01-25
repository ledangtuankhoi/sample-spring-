package com.example.employee.Service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.employee.Model.BookEntity;
import com.example.employee.Repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private BorrowingService borrowingService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private EmployeeService employeeService;

    public EmployeeServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void testGetBooksBorrowed() throws Exception {
        // // Mock data
        // String employeeId = "123";
        // List<BookEntity> mockBooks = Arrays.asList(
        //     new BookEntity("book1", "Book 1", true),
        //     new BookEntity("book2", "Book 2", true)
        // );

        // // Mock behavior of service
        // when(employeeService.getBooksBorrowedByEmplId(employeeId)).thenReturn(
        //     mockBooks
        // );

        // // Perform GET request and verify response
        // mockMvc
        //     .perform(
        //         get(
        //             "/api/v1/employees/{employeeId}/books",
        //             employeeId
        //         ).contentType(MediaType.APPLICATION_JSON)
        //     )
        //     .andExpect(status().isOk())
        //     .andExpect(jsonPath("$.length()").value(2))
        //     .andExpect(jsonPath("$[0].name").value("Book 1"))
        //     .andExpect(jsonPath("$[1].name").value("Book 2"));

        // // Verify service interaction
        // verify(employeeService, times(1)).getBooksBorrowedByEmplId(employeeId);
    }
}
