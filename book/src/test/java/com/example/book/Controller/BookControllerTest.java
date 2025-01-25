package com.example.book.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.book.Controller.BookController;
import com.example.book.Mappers.BookMapper;
import com.example.book.Model.BookEntity;
import com.example.book.Model.BookModelAssembler;
import com.example.book.Repository.BookRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class) // Chỉ test controller
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc; // Gửi request HTTP mô phỏng

    @MockBean
    private BookRepository repository; // Giả lập repository

    @MockBean
    private BookModelAssembler assembler; // Giả lập assembler

    @MockBean
    private BookMapper bookMapper;

    Logger logger = LoggerFactory.getLogger(BookControllerTest.class);
    // @Test
    // void testGetBook_Success() throws Exception {
    //     // Dữ liệu giả lập
    //     String bookId = "1";
    //     BookEntity mockBook = new BookEntity(
    //         "1",
    //         "Búp sen xanh",
    //         "son tung",
    //         true
    //     );
    //     logger.info(
    //         "Testing Book - ID:  {}, {}, {}, {}",
    //         mockBook.getId(),
    //         mockBook.getName(),
    //         mockBook.getAuthor(),
    //         mockBook.getIsReady()
    //     );

    //     EntityModel<BookEntity> mockModel = EntityModel.of(mockBook);

    //     // Giả lập hành vi repository và assembler
    //     when(repository.findById(bookId)).thenReturn(
    //         java.util.Optional.of(mockBook)
    //     );
    //     when(mockModel).thenReturn(mockModel);

    //     // Gửi yêu cầu GET và kiểm tra phản hồi
    //     mockMvc
    //         .perform(
    //             get("/api/v1/books/{id}", bookId).contentType(
    //                 MediaType.APPLICATION_JSON
    //             )
    //         )
    //         .andExpect(status().isOk()) // Kiểm tra mã trạng thái HTTP
    //         .andExpect(jsonPath("$.id").value(bookId)) // Kiểm tra trường "id" trong JSON
    //         .andExpect(jsonPath("$.name").value("Búp sen xanh")); // Kiểm tra trường "name" trong JSON
    // }

    // // @Test
    // // void testGetBook_NotFound() throws Exception {
    // //     // Giả lập trường hợp sách không tồn tại
    // //     String bookId = "99";
    // //     when(repository.findById(bookId)).thenReturn(
    // //         java.util.Optional.empty()
    // //     );

    // //     // Gửi yêu cầu GET và kiểm tra phản hồi
    // //     mockMvc
    // //         .perform(
    // //             get("/api/v1/books/{id}", bookId).contentType(
    // //                 MediaType.APPLICATION_JSON
    // //             )
    // //         )
    // //         .andExpect(status().isNotFound()); // Kiểm tra mã trạng thái HTTP
    // // }

    // @ParameterizedTest
    // @CsvSource(
    //     {
    //         "1, Búp sen xanh, son tung, true",
    //         "3, Tuổi thơ dữ dội - Tập 1, Phùng Quán, false",
    //         "4,Astérix - Astérix và cái vạc,René Goscinny,false",
    //         "5,Momo- Hoàng tử bé xóm Cúc Lam,Yaël Hassan,false",
    //         "6,Khi 'trai' đẹp hẹn hò - Tập 2 'Tặng Set 2 Bookmark Ghép Hình',Nana Aokawa,false",
    //         "7, Dế mèn phiêu lưu ký, Tô Hoài, false",
    //         "8,Doraemon Movie  màu - Nobita và bản giao hưởng Địa Cầu,Fujiko F Fujio,false",
    //     }
    // )
    // void testGetBookParameterized(
    //     String id,
    //     String name,
    //     String author,
    //     Boolean isReady
    // ) throws Exception {
    //     logger.info(
    //         "Testing Book - ID:  {}, {}, {}, {}",
    //         id,
    //         name,
    //         author,
    //         isReady
    //     );

    //     // System.out.println(
    //     //     "=====================" +
    //     //     String.format(
    //     //         "Testing Book - ID:  %s, %s, %s, %s",
    //     //         id,
    //     //         name,
    //     //         author,
    //     //         isReady
    //     //     )
    //     // );

    //     BookEntity mockBook = new BookEntity(id, name, author, isReady);
    //     when(repository.findById(id)).thenReturn(Optional.of(mockBook));
    //     when(mockBook).thenReturn(mockBook);

    //     var result = mockMvc
    //         .perform(
    //             get("/api/v1/books/{id}", id).contentType(
    //                 MediaType.APPLICATION_JSON
    //             )
    //         )
    //         .andExpect(status().isOk())
    //         .andExpect(jsonPath("$.id").value(id))
    //         .andExpect(jsonPath("$.name").value(name))
    //         .andExpect(jsonPath("$.author").value(author))
    //         .andReturn();

    //     logger.info(
    //         "Test for Book ID {} completed successfully. Response: {}",
    //         id,
    //         result.getResponse().getContentAsString()
    //     );
    // }
}
