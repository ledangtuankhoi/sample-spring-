package com.example.book.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.example.book.dtos.BookDTO;
import com.example.book.exception.BookAlreadyExistsException;
import com.example.book.exception.BookNotFoundException;
import com.example.book.mappers.BookMapper;
import com.example.book.model.BookEntity;
import com.example.book.model.BookModelAssembler;
import com.example.book.repository.BookRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookModelAssembler assembler;

    @Mock
    private BookMapper mapper;

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    private BookEntity bookEntity;
    private BookDTO bookDTO;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        bookEntity = new BookEntity();
        bookEntity.setId("1");
        bookEntity.setName("Test Book");
        bookEntity.setAuthor("Test Author");

        bookDTO = new BookDTO();
        bookDTO.setId("1");
        bookDTO.setName("Test Author");
    }

    @Test
    void getBookBorrowingByEmployeeId() {
        when(repository.findBooksToBorrowingByEmployeeId("1")).thenReturn(
            Arrays.asList(bookEntity)
        );
        List<BookEntity> books = service.getBookBorrowingByEmployeeId("1");
        assertEquals(1, books.size());
        verify(repository, times(1)).findBooksToBorrowingByEmployeeId("1");
    }

    @Test
    void getBookBorrowedByEmployeeId() {
        when(repository.findBooksToBorrowedByEmployeeId("1")).thenReturn(
            Arrays.asList(bookEntity)
        );
        List<BookEntity> books = service.getBookBorrowedByEmployeeId("1");
        assertEquals(1, books.size());
        verify(repository, times(1)).findBooksToBorrowedByEmployeeId("1");
    }

    @Test
    void getById() {
        when(repository.findById("1")).thenReturn(Optional.of(bookEntity));
        when(mapper.toDto(bookEntity)).thenReturn(bookDTO);
        BookDTO dto = service.getById("1");
        assertNotNull(dto);
        assertEquals("1", dto.getId());
        verify(repository, times(1)).findById("1");
        verify(mapper, times(1)).toDto(bookEntity);
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(bookEntity));
        when(mapper.toDto(bookEntity)).thenReturn(bookDTO);
        List<BookDTO> dtos = service.getAll();
        assertEquals(1, dtos.size());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDto(bookEntity);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(bookEntity));
        when(mapper.toDto(bookEntity)).thenReturn(bookDTO);
        List<BookDTO> dtos = service.findAll();
        assertEquals(1, dtos.size());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDto(bookEntity);
    }

    @Test
    void findAllWithAssembler() {
        when(repository.findAll()).thenReturn(Arrays.asList(bookEntity));
        when(mapper.toDto(bookEntity)).thenReturn(bookDTO);
        when(assembler.toModel(bookDTO)).thenReturn(EntityModel.of(bookDTO));
        CollectionModel<EntityModel<BookDTO>> collection =
            service.findAllWithAssembler();
        assertNotNull(collection);
        assertEquals(1, collection.getContent().size());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDto(bookEntity);
        verify(assembler, times(1)).toModel(bookDTO);
    }

    @Test
    void findById() {
        when(repository.findById("1")).thenReturn(Optional.of(bookEntity));
        BookEntity entity = service.findById("1");
        assertNotNull(entity);
        assertEquals("1", entity.getId());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void save_Success() {
        // Thiết lập hành vi của mapper và repository
        when(mapper.toEntity(bookDTO)).thenReturn(bookEntity);
        when(
            repository.findByNameAndAuthor("Test Book", "Test Author")
        ).thenReturn(Optional.empty());
        when(repository.save(bookEntity)).thenReturn(bookEntity);

        // Gọi phương thức save và kiểm tra kết quả
        BookEntity savedEntity = service.save(bookDTO);
        assertNotNull(savedEntity); // Kiểm tra kết quả không null
        assertEquals("1", savedEntity.getId()); // Kiểm tra ID của kết quả

        // Kiểm tra các phương thức của repository và mapper được gọi đúng số lần
        verify(mapper, times(1)).toEntity(bookDTO);
        verify(repository, times(1)).findByNameAndAuthor(
            "Test Book",
            "Test Author"
        );
        verify(repository, times(1)).save(bookEntity);
    }

    @Test
    void save_DuplicateEntity() {
        // Thiết lập hành vi của mapper và repository
        when(mapper.toEntity(bookDTO)).thenReturn(bookEntity);
        when(
            repository.findByNameAndAuthor("Test Book", "Test Author")
        ).thenReturn(Optional.of(bookEntity));

        // Gọi phương thức save và kiểm tra ngoại lệ
        BookAlreadyExistsException exception = assertThrows(
            BookAlreadyExistsException.class,
            () -> service.save(bookDTO)
        );
        assertEquals(
            "A book with the same name and author already exists",
            exception.getMessage()
        ); // Kiểm tra thông điệp ngoại lệ

        // Kiểm tra các phương thức của repository và mapper được gọi đúng số lần
        verify(mapper, times(1)).toEntity(bookDTO);
        verify(repository, times(1)).findByNameAndAuthor(
            "Test Book",
            "Test Author"
        );
        verify(repository, never()).save(bookEntity); // Phương thức save không được gọi
    }

    @Test
    void save_InvalidBookName() {
        // Thiết lập bookDTO với tên null
        bookDTO.setName(null);
        bookEntity.setName(null);
        when(mapper.toEntity(bookDTO)).thenReturn(bookEntity);

        // Gọi phương thức save và kiểm tra ngoại lệ
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.save(bookDTO)
        );
        assertEquals(
            "Book name cannot be null or empty",
            exception.getMessage()
        ); // Kiểm tra thông điệp ngoại lệ

        // Kiểm tra các phương thức của repository và mapper được gọi đúng số lần
        verify(mapper, times(1)).toEntity(bookDTO);
        verify(repository, never()).findByNameAndAuthor(
            anyString(),
            anyString()
        ); // Phương thức findByNameAndAuthor không được gọi
        verify(repository, never()).save(bookEntity); // Phương thức save không được gọi
    }

    @Test
    void save_InvalidBookAuthor() {
        // Thiết lập bookDTO với tác giả null
        bookDTO.setAuthor(null);
        bookEntity.setAuthor(null);
        when(mapper.toEntity(bookDTO)).thenReturn(bookEntity);

        // Gọi phương thức save và kiểm tra ngoại lệ
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.save(bookDTO)
        );
        assertEquals(
            "Book author cannot be null or empty",
            exception.getMessage()
        ); // Kiểm tra thông điệp ngoại lệ

        // Kiểm tra các phương thức của repository và mapper được gọi đúng số lần
        verify(mapper, times(1)).toEntity(bookDTO);
        verify(repository, never()).findByNameAndAuthor(
            anyString(),
            anyString()
        ); // Phương thức findByNameAndAuthor không được gọi
        verify(repository, never()).save(bookEntity); // Phương thức save không được gọi
    }

    @Test
    void save_InvalidBookIsReady() {
        // Thiết lập bookDTO với isReady null
        bookEntity.setIsReady(null);
        when(mapper.toEntity(bookDTO)).thenReturn(bookEntity);

        // Gọi phương thức save và kiểm tra ngoại lệ
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.save(bookDTO)
        );
        assertEquals(
            "Book readiness status cannot be null",
            exception.getMessage()
        ); // Kiểm tra thông điệp ngoại lệ

        // Kiểm tra các phương thức của repository và mapper được gọi đúng số lần
        verify(mapper, times(1)).toEntity(bookDTO);
        verify(repository, never()).findByNameAndAuthor(
            anyString(),
            anyString()
        ); // Phương thức findByNameAndAuthor không được gọi
        verify(repository, never()).save(bookEntity); // Phương thức save không được gọi
    }

    @Test
    void updateBook_Success() {
        // Thiết lập hành vi của repository
        when(repository.findById("1")).thenReturn(Optional.of(bookEntity));
        when(repository.save(bookEntity)).thenReturn(bookEntity);

        // Tạo đối tượng request để cập nhật
        BookEntity request = new BookEntity();
        request.setIsReady(true);
        request.setName("Updated Book");
        request.setAuthor("Updated Author");

        // Gọi phương thức updateBook và kiểm tra kết quả
        BookEntity updatedEntity = service.updateBook("1", request);
        assertNotNull(updatedEntity); // Kiểm tra kết quả không null
        assertEquals("1", updatedEntity.getId()); // Kiểm tra ID của kết quả
        assertEquals(true, updatedEntity.getIsReady()); // Kiểm tra giá trị isReady
        assertEquals("Updated Book", updatedEntity.getName()); // Kiểm tra tên sách
        assertEquals("Updated Author", updatedEntity.getAuthor()); // Kiểm tra tác giả

        // Kiểm tra các phương thức của repository được gọi đúng số lần
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(bookEntity);
    }

    @Test
    void updateBook_NotFound() {
        // Thiết lập hành vi của repository
        when(repository.findById("1")).thenReturn(Optional.empty());

        // Tạo đối tượng request để cập nhật
        BookEntity request = new BookEntity();
        request.setIsReady(true);
        request.setName("Updated Book");
        request.setAuthor("Updated Author");

        // Gọi phương thức updateBook và kiểm tra ngoại lệ
        assertThrows(BookNotFoundException.class, () ->
            service.updateBook("1", request)
        );

        // Kiểm tra các phương thức của repository được gọi đúng số lần
        verify(repository, times(1)).findById("1");
        verify(repository, never()).save(any(BookEntity.class)); // Phương thức save không được gọi
    }

    @Test
    void updateBook() {
        when(repository.findById("1")).thenReturn(Optional.of(bookEntity));
        when(repository.save(bookEntity)).thenReturn(bookEntity);
        BookEntity updatedEntity = service.updateBook("1", bookEntity);
        assertNotNull(updatedEntity);
        assertEquals("1", updatedEntity.getId());
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(bookEntity);
    }

    @Test
    void deleteBook() {
        when(repository.findById("1")).thenReturn(Optional.of(bookEntity));
        doNothing().when(repository).delete(bookEntity);
        service.deleteBook("1");
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).delete(bookEntity);
    }

    @Test
    void deleteBook_NotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> service.deleteBook("1")
        );
        verify(repository, times(1)).findById("1");
    }
}
