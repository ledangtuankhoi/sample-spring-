package com.example.employee.Controller;

import com.example.employee.DTO.EmployeeDTO;
import com.example.employee.Model.BookEntity;
import com.example.employee.Model.EmployeeEntity;
import com.example.employee.Model.EmployeeModelAssembler;
// import com.example.employee.Model.EmployeeModelAssembler;
import com.example.employee.Repository.EmployeeRepository;
import com.example.employee.Service.BookService;
import com.example.employee.Service.BorrowingService;
import com.example.employee.Service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/api/v1")
public class EmployeeController {

    // @Autowired
    private EmployeeRepository repository;
    private EmployeeService employeeService;
    private BorrowingService borrowingService;
    private EmployeeModelAssembler assembler;

    @Autowired
    public EmployeeController(
        EmployeeRepository repository,
        EmployeeService employeeService,
        EmployeeModelAssembler assembler,
        BorrowingService borrowingService
    ) {
        this.repository = repository;
        this.assembler = assembler;
        this.employeeService = employeeService;
        this.borrowingService = borrowingService;
    }

    // Get employee details GET /api /v1/employees/{employeeId}
    @Operation(
        summary = "Get employee details",
        description = "Retrieve details of an employee by ID."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved employee details",
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
    @GetMapping("/{id}")
    public Optional<EmployeeEntity> get(@PathVariable String id) {
        return repository.findById(id);
    }

    @KafkaListener(topics = "my-topic", groupId = "book-group")
    @Operation(
        summary = "Get all employees",
        description = "Retrieve a list of all employees."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved list of employees",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeEntity.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/all2")
    public @ResponseBody List<EmployeeEntity> getAll12() {
        // return repository.findAll();
        return repository.findAll();
    }

    // ---------------------------------

    // Get borrowed books for employee GET /api /v1/employees/{employeeId}/books
    @Operation(
        summary = "Get borrowed books for an employee",
        description = "Retrieve a list of books borrowed by a specific employee using their employeeId. Returns 404 if the employee is not found."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved the list of borrowed books",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookEntity.class)
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
    @GetMapping("/{employeeId}/books-sample")
    public List<BookEntity> getBooksBorrowed_sample(
        @RequestParam String employeeId
    ) {
        // employeeService.getById(employeeId);
        return employeeService.getBooksBorrowedByEmplId(employeeId);
    }

    // Get borrowed books for employee GET /api/v1/employees/{employeeId}/books
    @Operation(
        summary = "Get borrowed books for an employee",
        description = "Retrieve a list of books borrowed by a specific employee using their employeeId. Returns 404 if the employee is not found."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved the list of borrowed books",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookEntity.class)
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
    @GetMapping("/{employeeId}/books")
    public ResponseEntity<?> getBooksBorrowed(@PathVariable String employeeId) {
        // Kiểm tra xem employeeId có tồn tại không
        EmployeeDTO employee = employeeService.getById(employeeId);
        if (employee == null) {
            // Trả về lỗi 404 nếu không tìm thấy nhân viên
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                "Employee with ID " + employeeId + " not found."
            );
        }

        // Lấy danh sách sách đã mượn hoặc đang mượn bởi nhân viên
        List<BookEntity> borrowedBooks =
            employeeService.getBooksBorrowedByEmplId(employeeId);

        // Nếu không có sách nào được mượn, trả về danh sách trống
        if (borrowedBooks.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Trả về danh sách sách đã mượn
        return ResponseEntity.ok(borrowedBooks);
    }

    // ---------------------------------

    // TODO: create mapper
    @GetMapping("/")
    public List<EmployeeEntity> getAll() {
        // return new String();
        return repository.findAll();
    }

    // Add new employee POST /api/v1/employees
    @Operation(
        summary = "Add new employee",
        description = "Create a new employee record."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Successfully created new employee",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeEntity.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @PostMapping("")
    EmployeeEntity newEntity(@RequestBody EmployeeEntity entity) {
        // return entity.getName();
        return repository.save(entity);
    }

    // Update book PUT /api/v1/books/{bookId}
    @Operation(
        summary = "Update employee details",
        description = "Update the details of an existing employee."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully updated employee details",
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
    @PutMapping("/{id}")
    EmployeeEntity editEntity(
        @RequestBody EmployeeEntity request,
        @PathVariable String id
    ) {
        // BookEntity entity = new BookEntity();
        // entity.
        return repository
            .findById(id)
            .map(book -> {
                book.setFirstName(request.getFirstName());
                book.setLastName(request.getLastName());
                book.setKIN(request.getKIN());
                book.setIsDiscipilined(request.getIsDiscipilined());
                return repository.save(book);
            })
            .orElseGet(() -> {
                return repository.save(request);
            });
    }

    // Remove employee DELETE /api/v1/employees/{employeeId}
    @Operation(
        summary = "Delete employee",
        description = "Delete an employee record by ID."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Successfully deleted employee"
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
    @DeleteMapping("/{id}")
    void deleteEntity(@PathVariable String id) {
        repository.deleteById(id);
    }
}
