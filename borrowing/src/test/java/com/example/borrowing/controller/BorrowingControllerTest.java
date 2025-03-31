package com.example.borrowing.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.borrowing.Controller.BorrowingController;
import com.example.borrowing.DTO.BorrowingRequestDTO;
import com.example.borrowing.Exception.BookServiceException;
import com.example.borrowing.Mappers.BorrowingMapper;
import com.example.borrowing.Model.BookEntity;
import com.example.borrowing.Model.BorrowingEntity;
import com.example.borrowing.Model.BorrowingModelAssembler;
import com.example.borrowing.Producer.BorrowingProducer;
import com.example.borrowing.Repository.BorrowingRepository;
import com.example.borrowing.Service.BorrowingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class) // Dùng Mockito với JUnit 5
@WebMvcTest(BorrowingController.class) // Chỉ test Controller
public class BorrowingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BorrowingService borrowingService; // Chỉ mock Service, không mock Repo

    @MockBean
    private BorrowingRepository borrowingRepository;

    @MockBean
    private BorrowingProducer borrowingProducer;

    @MockBean
    private BorrowingModelAssembler borrowingModelAssembler;

    @MockBean
    private BorrowingMapper borrowingMapper;

    @InjectMocks
    private BorrowingController borrowingController;

    @Test
    void BorrowingBook_BookIdAndEmpId_Success() throws Exception {
        // Dữ liệu giả lập
        BorrowingRequestDTO borrowingRequestDTO = new BorrowingRequestDTO(
            "1",
            "2"
        );

        // Tạo bản ghi mượn sách
        BorrowingEntity borrowing = new BorrowingEntity();
        borrowing.setBookId("1");
        borrowing.setEmployeeId("2");
        borrowing.setStatus(BorrowingEntity.Status.BORROWED);
        borrowing.setCreatedAt(Instant.now());
        borrowing.setUpdatedAt(Instant.now());

        // gia lap hanh vi
        when(borrowingService.isBook("1")).thenReturn(true);
        when(borrowingService.isEmpl("2")).thenReturn(true);
        when(borrowingService.save(borrowing)).thenReturn(borrowing);

        System.out.println("Test 1");
        System.out.println(
            objectMapper.writeValueAsString(borrowingRequestDTO)
        );

        // Gửi request đến API và kiểm tra phản hồi
        mockMvc
            .perform(
                post("/borrowing/api/v1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(borrowingRequestDTO)
                    )
            )
            // .andDo(print())
            .andDo(result -> {
                // Ghi log response body
                System.out.println(
                    "request body: " +
                    // result.getResponse().getContentAsString()
                    result.getRequest().getContentAsString()
                );
                System.out.println(
                    "Response body: " +
                    result.getResponse().getContentAsString()
                );
            })
            .andExpect(status().isCreated()) // Kiểm tra status 200
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.bookId").value("1")) // Kiểm tra giá trị trả về
            .andExpect(jsonPath("$.employeeId").value("2"));
        // .andExpect(jsonPath(null, null))

        verify(borrowingService).isBook("1");
        verify(borrowingService).isEmpl("2");
        verify(borrowingService).save(any());
    }

    @Test
    void BorrowingBook_BookId_NotFound() throws Exception {
        String bookId = "12";
        String emplId = "2";
        // Dữ liệu giả lập
        BorrowingRequestDTO borrowingRequestDTO = new BorrowingRequestDTO(
            bookId,
            emplId
        );

        // Tạo bản ghi mượn sách
        BorrowingEntity borrowing = new BorrowingEntity();
        borrowing.setBookId(bookId);
        borrowing.setEmployeeId(emplId);
        borrowing.setStatus(BorrowingEntity.Status.BORROWED);
        borrowing.setCreatedAt(Instant.now());
        borrowing.setUpdatedAt(Instant.now());

        // gia lap hanh vi
        when(borrowingService.isBook(bookId)).thenReturn(false);
        // Gửi request đến API và kiểm tra phản hồi
        mockMvc
            .perform(
                post("/borrowing/api/v1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(borrowingRequestDTO)
                    )
            )
            .andDo(print())
            // Kiểm tra status 200
            .andExpect(status().isNotFound())
            .andExpect(
                content().string("Book with ID " + bookId + " not found")
            );

        verify(borrowingService).isBook(bookId);
    }

    @Test
    void BorrowingBook_EmplId_NotFound() throws Exception {
        // Dữ liệu giả lập
        String bookId = "1";
        String emplId = "22";
        BorrowingRequestDTO borrowingRequestDTO = new BorrowingRequestDTO(
            bookId,
            emplId
        );

        // Tạo bản ghi mượn sách
        BorrowingEntity borrowing = new BorrowingEntity();
        borrowing.setBookId(bookId);
        borrowing.setEmployeeId(emplId);
        borrowing.setStatus(BorrowingEntity.Status.BORROWED);
        borrowing.setCreatedAt(Instant.now());
        borrowing.setUpdatedAt(Instant.now());

        // gia lap hanh vi
        when(borrowingService.isBook(bookId)).thenReturn(true);
        when(borrowingService.isEmpl(bookId)).thenReturn(false);

        // Gửi request đến API và kiểm tra phản hồi
        mockMvc
            .perform(
                post("/borrowing/api/v1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(borrowingRequestDTO)
                    )
            )
            // Kiểm tra statu
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(
                content().string("Employee with ID " + emplId + " not found")
            );

        verify(borrowingService).isBook(bookId);
        verify(borrowingService).isEmpl(emplId);
    }

    @Test
    void BorrowingBook_Save_Erorr() throws Exception {
        // Dữ liệu giả lập
        String bookId = "1";
        String emplId = "2";
        BorrowingRequestDTO borrowingRequestDTO = new BorrowingRequestDTO(
            bookId,
            emplId
        );

        // gia lap hanh vi
        when(borrowingService.isBook(bookId)).thenReturn(true);
        when(borrowingService.isEmpl(emplId)).thenReturn(true);
        when(borrowingService.save(any(BorrowingEntity.class))).thenThrow(
            new RuntimeException("Database error")
        );

        // Gửi request đến API và kiểm tra phản hồi
        mockMvc
            .perform(
                post("/borrowing/api/v1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(borrowingRequestDTO)
                    )
            )
            // Kiểm tra statu
            .andDo(print())
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("something went wrong"));

        verify(borrowingService).isBook(bookId);
        verify(borrowingService).isEmpl(emplId);
    }

    @Test
    void BorrowingBook_Filed_Missing() throws Exception {
        // Dữ liệu giả lập
        String bookId = "1";
        String emplId = null;
        BorrowingRequestDTO borrowingRequestDTO = new BorrowingRequestDTO(
            bookId,
            emplId
        );

        // Gửi request đến API và kiểm tra phản hồi
        mockMvc
            .perform(
                post("/borrowing/api/v1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(borrowingRequestDTO)
                    )
            )
            // Kiểm tra statu
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
