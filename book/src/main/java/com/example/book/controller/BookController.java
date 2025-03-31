package com.example.book.controller;

import com.example.book.dtos.BookDTO;
import com.example.book.model.BookEntity;
import com.example.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book/api/v1")
@Tag(name = "Books", description = "API cho quản lý sách")
@AllArgsConstructor
@Validated
public class BookController {

    private BookService service;

    @Operation(
        summary = "Get all books",
        description = "Fetches a list of all books available in the system.",
        tags = { "Books" }
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
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Operation(
        summary = "Retrieve book details",
        description = "Fetches details of a book based on its ID.",
        tags = { "Books" }
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
    public ResponseEntity<BookEntity> getBook(@Valid @PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
        summary = "Add a new book",
        description = "Creates and stores a new book record in the system.",
        tags = { "Books" }
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
    public ResponseEntity<?> createBook(@RequestBody BookDTO request) {
        return ResponseEntity.ok(service.save(request));
    }

    @Operation(
        summary = "Update an existing book",
        description = "Updates the details of an existing book based on its ID.",
        tags = { "Books" }
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
    public ResponseEntity<BookEntity> updateBook(
        @RequestBody BookEntity request,
        @PathVariable String id
    ) {
        return ResponseEntity.ok(service.updateBook(id, request));
    }

    @Operation(
        summary = "Delete a book",
        description = "Deletes a book based on its ID.",
        tags = { "Books" }
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
    public ResponseEntity<Void> deleteBook(
        @Parameter(
            name = "id",
            description = "The ID of the book",
            example = "1"
        ) @PathVariable String id
    ) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
