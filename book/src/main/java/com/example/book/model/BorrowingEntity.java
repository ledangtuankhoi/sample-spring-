package com.example.book.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

// lombok
@Data
@NoArgsConstructor
// JPA
@Entity
@Table(name = "borrowing")
public class BorrowingEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    private Instant returnBorrowing;

    @Column(length = 32, columnDefinition = "varchar(32) default 'BORROWED'")
    @Enumerated(value = EnumType.STRING)
    @Size(max = 32)
    private Status status = Status.BORROWED;

    @NotNull
    private String bookId;

    @NotNull
    private String employeeId;

    public enum Status {
        BORROWED,
        CANCELED,
        RETURNED,
    }

    @Column(name = "createdAt")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Instant updatedAt;

    public BorrowingEntity(
        String id,
        Instant returnBorrowing,
        Status status,
        String bookId,
        String employeeId
    ) {
        this.id = id;
        this.returnBorrowing = returnBorrowing;
        this.status = status;
        this.bookId = bookId;
        this.employeeId = employeeId;
    }
}
