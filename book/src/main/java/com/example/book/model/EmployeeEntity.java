package com.example.book.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

// lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
// JPA
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "id")
    private String id;

    @Column(nullable = false, name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "KIN")
    private String kin;

    @Column(name = "isDiscipilined")
    private Boolean isDiscipilined;

    @Column(name = "createdAt")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Instant updatedAt;

    public EmployeeEntity(
        String id,
        String firstName,
        String lastName,
        String kin,
        Boolean isDiscipilined
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.kin = kin;
        this.isDiscipilined = isDiscipilined;
    }
}
