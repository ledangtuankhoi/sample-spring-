package com.example.borrowing.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

// lombok
@Entity
@Table(name = "borrowing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingEntity {

    // // DROP TABLE borrowing;
    // CREATE TABLE borrowing (
    //  id VARCHAR(255) NOT NULL,
    // bookId VARCHAR(255) NOT NULL,
    // employeeId VARCHAR(255) NOT NULL,
    // borrowingDate DATE,
    // returnBorrowing DATE,
    // status ENUM('BORROWED', 'CANCELED', 'RETURNED') NOT NULL)

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    private Instant returnBorrowing;

    @Column(length = 32, columnDefinition = "varchar(32) default 'CANCELED'")
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.BORROWED;

    @NotNull
    @JsonProperty("bookId")
    private String bookId;

    @NotNull
    @JsonProperty("employeeId")
    private String employeeId;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "bookId", nullable = false)
    // @JsonBackReference
    // private BookEntity book;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "employeeId", nullable = false)
    // @JsonBackReference
    // private EmployeeEntity employee;

    public enum Status {
        BORROWED,
        CANCELED,
        RETURNED,
        PENDING,
    }

    @Column(name = "createdAt")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Instant updatedAt;

    public BorrowingEntity(
        Instant returnBorrowing,
        Status status,
        String bookId,
        String employeeId
    ) {
        this.returnBorrowing = returnBorrowing;
        this.status = status;
        this.bookId = bookId;
        this.employeeId = employeeId;
    }

    public BorrowingEntity(String bookId, String employeeId) {
        this.bookId = bookId;
        this.employeeId = employeeId;

        this.status = Status.CANCELED;
        this.returnBorrowing = Instant.now();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getReturnBorrowing() {
        return this.returnBorrowing;
    }

    public void setReturnBorrowing(Instant returnBorrowing) {
        this.returnBorrowing = returnBorrowing;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
