package com.example.employee.Model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "users")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    // @Column(nullable = false)
    // private String fullName;

    @Column(nullable = false, name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String emplId;

    // @CreationTimestamp
    // @Column(updatable = false, name = "created_at")
    // private Date createdAt;

    // @UpdateTimestamp
    // @Column(name = "updated_at")
    // private Date updatedAt;

    @Column(name = "createdAt")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Instant updatedAt;

    // ----------------- Constructors -----------------
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getEmplId() {
        return this.emplId;
    }

    public User setEmplId(String emplId) {
        this.emplId = emplId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
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

    @Override
    public String toString() {
        return (
            "{" +
            " id='" +
            getId() +
            "'" +
            ", firstName='" +
            getFirstName() +
            "'" +
            ", lastName='" +
            getLastName() +
            "'" +
            ", email='" +
            getEmail() +
            "'" +
            ", password='" +
            getPassword() +
            "'" +
            ", emplId='" +
            getEmplId() +
            "'" +
            ", createdAt='" +
            getCreatedAt() +
            "'" +
            ", updatedAt='" +
            getUpdatedAt() +
            "'" +
            "}"
        );
    }
}
