package com.example.book.Controller;

import com.example.book.DTO.BookDTO;
import com.example.book.Exception.BookNotFoundException;
import com.example.book.Mappers.BookMapper;
import com.example.book.Model.BookEntity;
import com.example.book.Model.BookModelAssembler;
import com.example.book.Model.EmployeeEntity;
import com.example.book.Repository.BookRepository;
import com.example.book.Service.BookService;
import com.example.book.Service.EmployeeService;
import com.example.book.Service.MessageProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.Get;
import org.hibernate.sql.Delete;
import org.mapstruct.control.MappingControl.Use;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("book/api/v1")
@Tag(name = "Books", description = "API cho quản lý sách")
public class BookController {

    // @Autowired

    // @Autowired
    private BookModelAssembler assembler;

    // @Autowired
    private BookService service;

    // @Autowired
    private BookMapper mapper;

    private EmployeeService employeeService;
    // private EurekaClient eurekaClient;
    private DiscoveryClient discoveryClient;
    private RestClient restClient;
    private MessageProducer messageProducer;

    @Autowired
    public BookController(
        BookModelAssembler assembler,
        BookService service,
        BookMapper mapper,
        // EurekaClient eurekaClient,
        DiscoveryClient discoveryClient,
        EmployeeService employeeService,
        RestClient.Builder restClientBuilder,
        MessageProducer messageProducer
    ) {
        this.assembler = assembler;
        this.service = service;
        this.mapper = mapper;
        // this.eurekaClient = eurekaClient;
        this.discoveryClient = discoveryClient;
        this.restClient = restClientBuilder.build();
        this.employeeService = employeeService;
        this.messageProducer = messageProducer;

        System.out.println(
            "Controller Loaded: BookController with path /book/api/v1"
        );
    }

    // -=======================

    // i. Book Service API
    // Functionality Method Path
    // Get book details GET /api /v1/books/{bookId}
    // Add book POST /api/v1/books
    // Update book PUT /api/v1/books/{bookId}
    // Delete book DELETE /api/v1/books/{bookId}

    // ii. Book Borrowing Service API
    // Functionality Method Path
    // Get book borrowing by employee GET /api /v1/borrowing/{employeeId}
    // Add a new borrowing POST /api/v1/borrowing
    // Update a book return PUT /api/v1/borrowing/{employeeId}/{bookId}

    // iii. Employee Service API
    // Functionality Method Path
    // Get employee details GET /api /v1/employees/{employeeId}
    // Get borrowed books for employee GET /api /v1/employees/{employeeId}/books
    // Add new employee POST /api/v1/employees
    // Remove employee DELETE /api/v1/employees/{employeeId}

    // - Create a comprehensive sample application integrating all learned technologies.
    // - Use Spring Boot / OpenAPI as the core framework.
    // - Implement database operations with JPA and Azure SQL.
    // - Manage database changes with LiquiBase.
    // - Use Docker for containerization.
    // - Implement object mapping with MapStruct.
    // - Document APIs using OpenAPI.
    // - Integrate business rules with Drools.
    // - Use Kafka for messaging.
    // - Implement webhooks for external event handling.
    // - Manage APIs using APIM.

    // -=======================
    @Operation(
        summary = "Send a Kafka message asd",
        description = "Publishes a message to a Kafka topic. This method is used for sending messages to the Kafka topic for processing.",
        tags = { "Messaging" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Message successfully sent to Kafka topic",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/senMessageKafka/{message}")
    public void sendMessageKafka(
        @Parameter(
            name = "message",
            description = "The message to send to Kafka",
            example = "Hello Kafka!"
        ) @PathVariable String message
    ) {
        messageProducer.sendMessage("my-topic", message);
    }

    @Operation(
        summary = "Retrieve employee details",
        description = "Fetches the details of an employee by their unique employee ID."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Employee details successfully retrieved",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeEntity.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Employee not found",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/employee/{id}")
    public EmployeeEntity getEmployee(
        @Parameter(
            name = "id",
            description = "The ID of the employee",
            example = "1"
        ) @PathVariable long id
    ) {
        return employeeService.getEmployeeDetails(id);
    }

    @Operation(
        summary = "Retrieve employee details via API Gateway",
        description = "Fetches employee details through an API gateway using their unique ID.",
        tags = { "Employee" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Employee details successfully retrieved",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeEntity.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Employee not found",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/employeewithapigetway/{id}")
    public EmployeeEntity getEmployeeWithApiGateway(
        @Parameter(
            name = "id",
            description = "The ID of the employee",
            example = "2"
        ) @PathVariable long id
    ) {
        return employeeService.getEmployeeDetailsWithApigetway(id);
    }

    @Operation(
        summary = "Get all books",
        description = "Fetches a list of all books available in the system.",
        tags = { "Books", "Books-homework" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved the list of books",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/all")
    public List<BookDTO> getAllBooks() {
        return service.findAll();
    }

    @Operation(
        summary = "Get all books with Assembler",
        description = "Fetches a list of all books available, with additional transformation using an assembler.",
        tags = { "Books" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved the list of books with assembler",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/allWithAssembler")
    public CollectionModel<EntityModel<BookDTO>> getAllBooksWithAssembler() {
        return service.findAllWithAssembler();
    }

    @Operation(
        summary = "Retrieve book details",
        description = "Fetches details of a book based on its ID.",
        tags = { "Books", "Books-homework" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved the book details",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookEntity.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable String id) {
        Optional<BookEntity> book = service.findById(id);

        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                "Book with ID " + id + " not found"
            );
        }
        // return repository
        //     .findById(id)
        //     .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Operation(
        summary = "Add a new book",
        description = "Creates and stores a new book record in the system.",
        tags = { "Books", "Books-homework" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Book successfully created",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookEntity.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @PostMapping("")
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDTO request) {
        try {
            Optional<BookEntity> entity = service.save(request);
            if (entity.isPresent()) {
                return ResponseEntity.ok(entity.get());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Not create book"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                e.getMessage()
            );
        }
    }

    @Operation(
        summary = "Update an existing book",
        description = "Updates the details of an existing book based on its ID.",
        tags = { "Books", "Books-homework" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Book successfully updated",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookEntity.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(
        @RequestBody BookEntity request,
        @PathVariable String id
    ) {
        try {
            Optional<BookEntity> updatedBook = service.updateBook(id, request);
            if (updatedBook.isPresent()) {
                return ResponseEntity.ok(updatedBook.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Book with ID " + id + " not found"
                );
            }
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                e.getMessage()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "An error occurred while updating the book: " + e.getMessage()
            );
        }
    }

    @Operation(
        summary = "Delete a book",
        description = "Deletes a book based on its ID.",
        tags = { "Books", "Books-homework" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Book successfully deleted"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(
        @Parameter(
            name = "id",
            description = "The ID of the book",
            example = "1"
        ) @PathVariable String id
    ) {
        try {
            service.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                e.getMessage()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "An error occurred while deleting the book: " + e.getMessage()
            );
        }
    }

    @GetMapping("/all2")
    public CollectionModel<EntityModel<BookDTO>> all2() {
        return CollectionModel.of(
            service
                .getAll()
                .stream()
                .map(book -> assembler.toModel(book))
                .toList()
        );
    }
}
