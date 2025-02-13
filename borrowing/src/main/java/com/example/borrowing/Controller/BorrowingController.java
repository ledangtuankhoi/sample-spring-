package com.example.borrowing.Controller;

import com.example.borrowing.DTO.BorrowingDTO;
import com.example.borrowing.DTO.BorrowingRequestDTO;
import com.example.borrowing.Exception.BookServiceException;
import com.example.borrowing.Mappers.BorrowingMapper;
import com.example.borrowing.Model.BorrowingEntity;
import com.example.borrowing.Model.BorrowingEntity.Status;
import com.example.borrowing.Model.BorrowingModelAssembler;
import com.example.borrowing.Producer.BorrowingProducer;
import com.example.borrowing.Repository.BorrowingRepository;
import com.example.borrowing.Service.BorrowingKafkaService;
import com.example.borrowing.Service.BorrowingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrowing/api/v1")
@Validated
public class BorrowingController {

    // @Autowired
    // private BorrowingRepository repository;

    // private BorrowingService borrowingService;
    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BorrowingKafkaService borrowingKafkaService;

    @Autowired
    private BorrowingModelAssembler borrowingModelAssembler;

    @Autowired
    private BorrowingMapper mapper;

    @Autowired
    private BorrowingProducer borrowingProducer;

    public BorrowingController() {}

    // ii. Book Borrowing Service API
    // Functionality Method Path
    // Get book borrowing by employee GET /api /v1/borrowing/{employeeId}
    // Add a new borrowing POST /api/v1/borrowing
    // Update a book return PUT /api/v1/borrowing/{employeeId}/{bookId}
    // --------------------
    @Operation(
        summary = "Borrow a book via Kafka",
        description = "Creates a borrowing record for an employee and sends a Kafka message. Returns 200 OK if successful, or 404 Not Found if book/employee does not exist.",
        tags = { "Borrowing-homework" },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully borrowed book via Kafka"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Book or employee not found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @PostMapping("/kafka")
    public ResponseEntity<?> borrowingBookKafka(
        @Valid @RequestBody BorrowingRequestDTO request
    ) {
        // Lấy bookId và employeeId từ yêu cầu
        String bookId = request.getBookId();
        String emplId = request.getEmployeeId();

        // In ra thông tin để debug
        System.err.println("book: " + bookId + " empl: " + emplId);
        System.err.println(request);

        try {
            // Kiểm tra xem sách đã được mượn bởi nhân viên này chưa
            if (
                borrowingService.findByBookIdAndEmployeeId(bookId, emplId) !=
                null
            ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Book with ID " +
                    bookId +
                    " and Employee with ID " +
                    emplId +
                    " already borrowed"
                );
            }

            // Kiểm tra sự tồn tại của sách
            if (!borrowingService.isBook(bookId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Book with ID " + bookId + " not found"
                );
            }

            // Kiểm tra sự tồn tại của nhân viên
            if (!borrowingService.isEmpl(emplId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Employee with ID " + emplId + " not found"
                );
            }

            // Tạo một thực thể BorrowingEntity mới với trạng thái PENDING
            BorrowingEntity borrowing = new BorrowingEntity(bookId, emplId);
            borrowing.setStatus(Status.PENDING);
            borrowing = borrowingService.save(borrowing);

            // Gửi thông tin cập nhật trạng thái mượn sách qua Kafka
            borrowingKafkaService.sendBorrowingUpdateStatus(bookId, emplId);

            // Trả về phản hồi thành công với ID của borrowing
            return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("request send success with ID: " + borrowing.getId());
        } catch (BookServiceException e) {
            // Xử lý ngoại lệ liên quan đến dịch vụ sách
            System.err.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                "An error occurred: " + e.getMessage()
            );
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "something went wrong"
            );
        }
    }

    @Operation(
        summary = "Get borrowings by employee ID",
        description = "Retrieve a list of borrowings based on the employee ID. Returns a 200 OK status if found, or 404 if no borrowings exist for the given employee ID.",
        tags = { "Borrowing-homework" },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved borrowings"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Employee not found or no borrowings found for the given employee ID"
            ),
        }
    )
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<BorrowingDTO>> getBorrowingByEmplId(
        @PathVariable String employeeId
    ) {
        // Debug log to see incoming employeeId
        System.out.println("employeeId: " + employeeId);

        // Fetch borrowings from the service layer
        List<BorrowingDTO> borrowings = borrowingService
            .getByEmplId(employeeId)
            .stream()
            .map(e -> mapper.toDto(e))
            .toList();

        // Check if borrowings are found, return HTTP status accordingly
        if (borrowings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 if no borrowings found
        }

        return new ResponseEntity<>(borrowings, HttpStatus.OK); // 200 OK with data if borrowings are found
    }

    // Add a new borrowing POST /api/v1/borrowing
    @Operation(
        summary = "Borrow a book",
        description = "Creates a borrowing record for an employee. Returns 201 Created if successful, or 404 Not Found if book/employee does not exist.",
        tags = { "Borrowing-homework" },
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Successfully borrowed book"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Book or employee not found"
            ),
        }
    )
    @PostMapping("")
    public ResponseEntity<?> borrowingBook(
        @Valid @RequestBody BorrowingRequestDTO request
    ) {
        String bookId = request.getBookId();
        String emplId = request.getEmployeeId();

        System.err.println("book" + bookId + " empl" + emplId);
        System.err.println(request);

        try {
            // Kiểm tra sách có tồn tại không
            if (!borrowingService.isBook(bookId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Book with ID " + bookId + " not found"
                );
            }

            // Kiểm tra nhân viên có tồn tại không
            if (!borrowingService.isEmpl(emplId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Employee with ID " + emplId + " not found"
                );
            }

            // Tạo bản ghi mượn sách
            BorrowingEntity borrowing = new BorrowingEntity();
            borrowing.setBookId(bookId);
            borrowing.setEmployeeId(emplId);
            borrowing.setStatus(BorrowingEntity.Status.BORROWED);

            borrowingService.save(borrowing);

            // Gửi sự kiện Kafka để thông báo cho các service khác
            borrowingProducer.sendBorrowingEvent(borrowing);

            return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(borrowing);
            // .body(new BorrowingEntity(bookId, emplId));
        } catch (BookServiceException e) {
            System.err.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                "An error occurred: " + e.getMessage()
            );
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "something went wrong"
            );
        }
    }

    // --------------------

    @Operation(
        summary = "Get all employees",
        description = "Retrieve a list of all employees.",
        // tags = { "Employee" },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved list of employees"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/all2")
    public @ResponseBody CollectionModel<
        EntityModel<BorrowingEntity>
    > GetAll() {
        return CollectionModel.of(
            borrowingService
                .findAll()
                .stream()
                .map(borrowingModelAssembler::toModel)
                .toList()
        );
    }

    // --------------------

    @Operation(
        summary = "Get all employees",
        description = "Retrieve a list of all employees.",
        tags = { "Employee" },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved list of employees"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/all")
    public @ResponseBody List<BorrowingEntity> GetAll2() {
        return borrowingService.findAll();
    }

    @Operation(
        summary = "Get all employees",
        description = "Retrieve a list of all employees.",
        tags = { "Borrowing-homework" },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved list of employees"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/{borrowingId}")
    public BorrowingEntity getById(String borrowingId) {
        return borrowingService
            .findById(borrowingId)
            .orElseThrow(() -> new RuntimeException("not found"));
    }
}
