package com.example.employee.Service;

import com.example.employee.DTO.EmployeeDTO;
import com.example.employee.Mappers.EmployeeMapper;
import com.example.employee.Model.BookEntity;
import com.example.employee.Model.BorrowingEntity;
import com.example.employee.Model.EmployeeEntity;
import com.example.employee.Repository.EmployeeRepository;
import com.example.employee.Repository.EmployeeRepository;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    // @Autowired
    // private KafkaTemplate<String, String> kafkaTemplate;

    // @Autowired
    private EmployeeMapper mapper;
    private BorrowingService borrowingService;
    private BookService bookService;

    public EmployeeService(
        EmployeeRepository repository,
        EmployeeMapper mapper,
        BorrowingService borrowingService,
        BookService bookService
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.borrowingService = borrowingService;
        this.bookService = bookService;
    }
 
    public EmployeeDTO getById(String id) {
        return repository
            .findById(id)
            .map(book -> mapper.toDto(book))
            .orElse(null);
    }

    public List<EmployeeDTO> getAll() {
        List<EmployeeEntity> empl = repository.findAll();

        return empl
            .stream()
            .peek(System.out::println)
            .map(book -> mapper.toDto(book))
            .peek(System.out::println)
            .toList();
    }

    public List<BookEntity> getBooksBorrowedByEmplId(String id) {
        List<BorrowingEntity> borrowingEntities =
            borrowingService.getBorrowingByEmplId(id);
        List<BookEntity> bookEntities = borrowingEntities
            .stream()
            .map(e -> e.getBookId())
            .map(bookid -> bookService.getBookById(bookid))
            .toList();
        return bookEntities;
    }
}
