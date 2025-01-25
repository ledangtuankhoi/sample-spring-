package com.example.book.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

// lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
// JPA
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    // CREATE TABLE employee (
    // id VARCHAR(255) NOT NULL,
    // firstName VARCHAR(255) NOT NULL,
    // lastName VARCHAR(255),
    // KIN VARCHAR(255),
    // isDiscipilined BOOLEAN,
    // PRIMARY KEY (id)
    // );

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(nullable = false, name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "KIN")
    private String KIN;

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
        String KIN,
        Boolean isDiscipilined
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.KIN = KIN;
        this.isDiscipilined = isDiscipilined;
    }
}
