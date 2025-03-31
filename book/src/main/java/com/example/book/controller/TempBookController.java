package com.example.book.controller;

import com.example.book.dtos.BookDTO;
import com.example.book.exception.BookNotFoundException;
import com.example.book.model.BookModelAssembler;
import com.example.book.model.EmployeeEntity;
import com.example.book.service.BookService;
import com.example.book.service.EmployeeService;
import com.example.book.service.MessageProducer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(info = @Info(title = "Book API", version = "1.0"))
@RestController
@RequestMapping("test/book/api/v1")
@Tag(name = "Temp-Books", description = "API cho quản lý sách")
public class TempBookController {

    private BookModelAssembler assembler;
    private BookService service;
    private EmployeeService employeeService;
    private MessageProducer messageProducer;
    Logger logger = LoggerFactory.getLogger(TempBookController.class);

    public TempBookController(
        BookModelAssembler assembler,
        BookService service,
        EmployeeService employeeService,
        MessageProducer messageProducer
    ) {
        this.assembler = assembler;
        this.service = service;
        this.employeeService = employeeService;
        this.messageProducer = messageProducer;
    }

    // -=======================
    @Operation(
        summary = "Send a Kafka message asd",
        description = "Publishes a message to a Kafka topic. This method is used for sending messages to the Kafka topic for processing."
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
        description = "Fetches employee details through an API gateway using their unique ID."
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
        description = "Fetches a list of all books available in the system."
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
        description = "Fetches a list of all books available, with additional transformation using an assembler."
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
        summary = "Delete a book",
        description = "Deletes a book based on its ID."
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
