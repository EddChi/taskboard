package com.eddy.taskboard.workspace;

// imports
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * represents a workspace within the Taskboard system
 * each workspace can contain projects, tasks, and members
 */

@Entity
@Table(name = "workspaces")
public class Workspace {

    // primary key (universally unique identifier)
    @Id
    private UUID userId;

    @Column
    private String name;

    // the user who created the workspace will be
    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    // time the work space was created
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    // constructor
    protected Workspace() {}

    // argument constructor
    public Workspace(UUID userId, String name, UUID createdBy, OffsetDateTime createdAt) {
        this.userId = userId;
        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    // getters
    public UUID getuserId() { return userId; }
    public String getName() { return name; }
    public UUID getCreatedBy() { return createdBy; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
