package com.example.book.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

// lombok
// JPA
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "book")
@Schema(hidden = true) // from swagger UI
public class BookEntity {

    @Id
    @UuidGenerator
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator = "uuid")
    @Column(name = "id")
    private String id;

    @Column(nullable = false)
    @Size(min = 1, max = 60)
    private String name;

    @Size(min = 1, max = 60)
    private String author;

    @Column(name = "isReady")
    private Boolean isReady = false;

    // @Column(name = "created_at")
    @Column(name = "createdAt")
    @CreationTimestamp
    private Instant createdAt;

    // @Column(name = "updated_at")
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Instant updatedAt;

    public BookEntity(String id, String name, String author, Boolean isReady) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isReady = isReady;
    }
}
