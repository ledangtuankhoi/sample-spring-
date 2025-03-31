package com.example.borrowing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.borrowing.Controller.BorrowingController;
import com.example.borrowing.Event.BorrowingUpdateEvent;
import com.example.borrowing.Model.BorrowingEntity;
import com.example.borrowing.Model.BorrowingEntity.Status;
import com.example.borrowing.Service.BorrowingKafkaService;
import com.example.borrowing.Service.BorrowingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, topics = { "borrowing-topic" })
@Execution(ExecutionMode.CONCURRENT)
@EnableKafka
@TestPropertySource(locations = "classpath:application.test.properties")
public class BorrowingControllerConcurrencyTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingService borrowingService;

    @MockBean
    private BorrowingKafkaService borrowingKafkaService;

    @InjectMocks
    private BorrowingController borrowingController;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Kiểm thử yêu cầu mượn sách đồng thời
    @Test
    public void testConcurrentBorrowingRequests() throws Exception {
        when(borrowingService.isBook(anyString())).thenReturn(true);
        when(borrowingService.isEmpl(anyString())).thenReturn(true);
        when(
            borrowingService.findByBookIdAndEmployeeId(anyString(), anyString())
        ).thenReturn(null);

        // Gửi hai yêu cầu mượn sách đồng thời
        ResultActions result1 = mockMvc.perform(
            post("/kafka")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"bookId\": \"123\", \"employeeId\": \"emp001\"}")
        );

        ResultActions result2 = mockMvc.perform(
            post("/kafka")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"bookId\": \"123\", \"employeeId\": \"emp002\"}")
        );

        // Kiểm tra kết quả trả về của cả hai yêu cầu
        result1
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("$.message").value("request send success with ID: 1")
            );

        result2
            .andExpect(status().isBadRequest())
            .andExpect(
                content()
                    .string(
                        "Book with ID 123 and Employee with ID emp002 already borrowed"
                    )
            );
    }

    // Kiểm thử trường hợp sách không tồn tại
    @Test
    public void testBookNotFound() throws Exception {
        when(borrowingService.isBook(anyString())).thenReturn(false);

        mockMvc
            .perform(
                post("/kafka")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\"bookId\": \"999\", \"employeeId\": \"emp001\"}"
                    )
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Book with ID 999 not found"));
    }

    // Kiểm thử trường hợp nhân viên không tồn tại
    @Test
    public void testEmployeeNotFound() throws Exception {
        when(borrowingService.isEmpl(anyString())).thenReturn(false);

        mockMvc
            .perform(
                post("/kafka")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\"bookId\": \"123\", \"employeeId\": \"emp999\"}"
                    )
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Employee with ID emp999 not found"));
    }

    // Kiểm thử gửi và tiêu thụ sự kiện Kafka
    @Test
    public void testBorrowingUpdateKafka() throws Exception {
        // Giả lập dữ liệu cần thiết cho event
        BorrowingUpdateEvent event = new BorrowingUpdateEvent(
            "123",
            "emp001",
            Status.BORROWED
        );
        String eventMessage = new ObjectMapper().writeValueAsString(event);

        // Gửi sự kiện Kafka
        kafkaTemplate.send("BORROWING_UPDATE", eventMessage);

        // Giả lập hành vi và kiểm tra tiêu thụ sự kiện
        verify(borrowingService, times(1)).save(any(BorrowingEntity.class)); // Kiểm tra xem borrowingService.save được gọi
    }
}
