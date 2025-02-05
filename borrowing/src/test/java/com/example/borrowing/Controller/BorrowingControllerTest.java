package com.example.borrowing.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.borrowing.DTO.BorrowingRequestDTO;
import com.example.borrowing.Mappers.BorrowingMapper;
import com.example.borrowing.Model.BorrowingEntity;
import com.example.borrowing.Model.BorrowingModelAssembler;
import com.example.borrowing.Producer.BorrowingProducer;
import com.example.borrowing.Repository.BorrowingRepository;
import com.example.borrowing.Service.BorrowingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import javax.print.attribute.standard.Media;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

    @Test
    void muonSachThanhCong() throws Exception {
        // Dữ liệu giả lập
        BorrowingRequestDTO borrowingRequestDTO = new BorrowingRequestDTO(
            "1",
            "22"
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
}
