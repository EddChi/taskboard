package com.eddy.taskboard.user;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * domain user for authentication/authorisation
 * password is stored as a BCrypt hash (never plain text)
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, columnDefinition = "citext")
    private String email;   // unique login identifier

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;    // BCrypt-hashed password

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    protected User() { }

    public User(UUID id, String email, String passwordHash, String fullName, OffsetDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getFullName() { return fullName; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}

