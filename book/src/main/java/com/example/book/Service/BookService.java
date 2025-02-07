package com.example.book.Service;

import com.example.book.DTO.BookDTO;
import com.example.book.Mappers.BookMapper;
import com.example.book.Model.BookEntity;
import com.example.book.Repository.BookRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    // @Autowired
    // private KafkaTemplate<String, String> kafkaTemplate;

    // @Autowired
    private BookMapper mapper;

    public BookService(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<BookEntity> getBookBorrowingByEmployeeId(String employeeId) {
        return repository.findBooksToBorrowingByEmployeeId(employeeId);
    }

    public List<BookEntity> getBookBorrowedByEmployeeId(String employeeId) {
        return repository.findBooksToBorrowedByEmployeeId(employeeId);
    }

    public BookEntity toEntityWithTimelamp(BookDTO dto) {
        BookEntity entity = repository.findByIdAndName(
            dto.getId(),
            dto.getName()
        );
        return entity;
    }

    public BookDTO getById(String id) {
        BookEntity book = repository.findById(id).orElse(null);
        return mapper.toDto(book);
    }

    public List<BookDTO> getAll() {
        List<BookEntity> books = repository.findAll();

        return books
            .stream()
            .peek(System.out::println)
            .map(book -> mapper.toDto(book))
            .peek(System.out::println)
            .toList();
    }
}
