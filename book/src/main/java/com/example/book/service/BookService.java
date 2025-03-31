package com.example.book.service;

import com.example.book.dtos.BookDTO;
import com.example.book.exception.BookAlreadyExistsException;
import com.example.book.exception.BookNotFoundException;
import com.example.book.mappers.BookMapper;
import com.example.book.model.BookEntity;
import com.example.book.model.BookModelAssembler;
import com.example.book.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookModelAssembler assembler;

    @Autowired
    private BookMapper mapper;
 

    public List<BookEntity> getBookBorrowingByEmployeeId(String employeeId) {
        return repository.findBooksToBorrowingByEmployeeId(employeeId);
    }

    public List<BookEntity> getBookBorrowedByEmployeeId(String employeeId) {
        return repository.findBooksToBorrowedByEmployeeId(employeeId);
    }

    public BookDTO getById(String id) {
        BookEntity book = repository.findById(id).orElse(null);
        return mapper.toDto(book);
    }

    public List<BookDTO> getAll() {
        List<BookEntity> books = repository.findAll();

        return books.stream().map(book -> mapper.toDto(book)).toList();
    }

    public List<BookDTO> findAll() {
        return repository.findAll().stream().map(e -> mapper.toDto(e)).toList();
    }

    public CollectionModel<EntityModel<BookDTO>> findAllWithAssembler() {
        return CollectionModel.of(
            repository
                .findAll()
                .stream()
                .map(book -> mapper.toDto(book))
                .map(e -> assembler.toModel(e))
                .toList()
        );
    }

    public BookEntity findById(String id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookEntity save(BookDTO entityDto) {
        // Chuyển đổi từ DTO sang Entity
        BookEntity entity = mapper.toEntity(entityDto);

        // Kiểm tra các điều kiện cần thiết trước khi lưu
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new IllegalArgumentException(
                "Book name cannot be null or empty"
            );
        }

        if (entity.getAuthor() == null || entity.getAuthor().isEmpty()) {
            throw new IllegalArgumentException(
                "Book author cannot be null or empty"
            );
        }

        if (entity.getIsReady() == null) {
            throw new IllegalArgumentException(
                "Book readiness status cannot be null"
            );
        }

        try {
            // Kiểm tra xem sách đã tồn tại với cùng tên và tác giả chưa
            if (
                repository
                    .findByNameAndAuthor(entity.getName(), entity.getAuthor())
                    .isPresent()
            ) {
                throw new BookAlreadyExistsException(
                    "A book with the same name and author already exists"
                );
            }

            // Lưu sách vào cơ sở dữ liệu
            return repository.save(entity);
        } catch (BookAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving book", e);
        }
    }

    public BookEntity updateBook(String id, BookEntity request) {
        BookEntity entity = this.findById(id);
        if (request.getIsReady() != null) {
            entity.setIsReady(request.getIsReady());
        }

        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        if (request.getAuthor() != null) {
            entity.setAuthor(request.getAuthor());
        }
        return repository.save(entity);
    }

    public void deleteBook(String id) {
        Optional<BookEntity> book = repository.findById(id);
        if (book.isPresent()) {
            repository.delete(book.get());
        } else {
            throw new BookNotFoundException(
                "Book with ID " + id + " not found"
            );
        }
    }
}
