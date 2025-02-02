package com.example.borrowing.Controller;

import com.example.borrowing.DTO.BorrowingDTO;
import com.example.borrowing.Mappers.BorrowingMapper;
import com.example.borrowing.Model.BorrowingEntity;
import com.example.borrowing.Model.BorrowingModelAssembler;
import com.example.borrowing.Repository.BorrowingRepository;
import com.example.borrowing.Service.BorrowingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrowing/api/v1")
public class BorrowingController {

    @Autowired
    private BorrowingRepository repository;

    // private BorrowingService borrowingService;
    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BorrowingModelAssembler borrowingModelAssembler;

    @Autowired
    private BorrowingMapper mapper;

    public BorrowingController() {}

    // --------------------
    @GetMapping("/all2")
    @Operation(
        summary = "danh sach da muon sach",
        description = "lay tat ca danh sach da muon"
    )
    public @ResponseBody CollectionModel<
        EntityModel<BorrowingEntity>
    > GetAll() {
        return CollectionModel.of(
            repository
                .findAll()
                .stream()
                .map(borrowingModelAssembler::toModel)
                .toList()
        );
    }

    @GetMapping("/employee/{employeeId}")
    public List<BorrowingDTO> getBorrowingByEmplId(
        @PathVariable String employeeId
    ) {
        System.out.println("employeeId: " + employeeId);
        return borrowingService
            .getByEmplId(employeeId)
            .stream()
            .map(e -> mapper.toDto(e))
            .toList();
    }

    // --------------------
    @GetMapping("/all")
    @Operation(
        summary = "danh sach da muon sach",
        description = "lay tat ca danh sach da muon"
    )
    public @ResponseBody List<BorrowingEntity> GetAll2() {
        return repository.findAll();
    }

    @GetMapping("/{borrowingId}")
    public BorrowingEntity getById(String borrowingId) {
        return repository
            .findById(borrowingId)
            .orElseThrow(() -> new RuntimeException("not found"));
    }
    // // Get book borrowing by employee	GET	/api/v1/borrowing/{employeeId}
    // @GetMapping("/{employeeId}")
    // @Operation(summary = "Get book borrowing by employee from manager ")
    // public CollectionModel<EntityModel<BookDTO>> getBookBorrowing(
    //     @PathVariable String employeeId
    // ) {
    //     List<BookEntity> listBook = bookService.getBookBorrowingByEmployeeId(
    //         employeeId
    //     );

    //     // return listBook;
    //     return CollectionModel.of(
    //         listBook.stream().map(borrowingModelAssembler::toBooks).toList()
    //     );
    // }
    // // Add a new borrowing POST /api/v1/borrowing
    // @PostMapping("")
    // @Operation(
    //     summary = "muon sach",
    //     description = "muon sach voi id cua book, id cua employee"
    // )
    // BorrowingEntity newEntity(
    //     @RequestBody BorrowingWithBookIdsDTO borrowingWithBookIdsDTO
    // ) {
    //     BookEntity bookEntity = bookRepository
    //         .findById(borrowingWithBookIdsDTO.getBookId())
    //         .orElse(null);
    //     EmployeeEntity employeeEntity = employeeRepository
    //         .findById(borrowingWithBookIdsDTO.getEmployeeId())
    //         .orElse(null);

    //     // TODO: handle exception with null, return to message
    //     if (bookEntity == null && employeeEntity == null) return null;

    //     BorrowingEntity borrowingEntity = new BorrowingEntity();
    //     borrowingEntity.setBookId(bookEntity.getId());
    //     borrowingEntity.setEmployeeId(employeeEntity.getId());

    //     return repository.save(borrowingEntity);
    // }

    // //    Update a book return PUT	/api/v1/borrowing/{employeeId}/{bookId}
    // @PutMapping("/{employeeId}/{bookId}")
    // public ResponseEntity<BorrowingEntity> edit(
    //     @PathVariable String employeeId,
    //     @PathVariable String bookId,
    //     @RequestBody BorrowingEntity entity
    // ) {
    //     //TODO: process PUT request
    //     BorrowingEntity borrowingEntity =
    //         borrowingServiceImp.updateStatusToBorrowing(
    //             employeeId,
    //             bookId,
    //             entity
    //         );
    //     return new ResponseEntity<>(borrowingEntity, HttpStatus.OK);
    // }
}
