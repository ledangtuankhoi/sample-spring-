package com.example.book.service;

import com.example.book.dtos.BookDTO;
import com.example.book.exception.BookNotFoundException;
import com.example.book.mappers.BookMapper;
import com.example.book.model.BookEntity;
import com.example.book.model.BookModelAssembler;
import com.example.book.repository.BookRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private BookModelAssembler assembler;

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
                .collect(Collectors.toList())
        );
    }

    public Optional<BookEntity> findById(String id) {
        if (repository.findById(id).isPresent() == false) {
            throw new BookNotFoundException(id);
        }
        return repository.findById(id);
    }

    public Optional<BookEntity> save(BookDTO entityDto) {
        BookEntity entity = mapper.toEntity(entityDto);
        // repository.findByName(entity).count()
        if (
            repository
                .findByNameAndAuthor(entity.getName(), entity.getAuthor())
                .isPresent() ==
            false
        ) {
            return Optional.of(repository.save(entity));
        } else {
            throw new RuntimeException("duplicate entity");
        }
    }

    public Optional<BookEntity> updateBook(String id, BookEntity request) {
        BookEntity entity = this.findById(id).get();
        if (request.getIsReady() != null) {
            entity.setIsReady(request.getIsReady());
        }

        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        if (request.getAuthor() != null) {
            entity.setAuthor(request.getAuthor());
        }
        return Optional.of(repository.save(entity));
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
