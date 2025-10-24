package com.eddy.taskboard.workspace;

// imports
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

/**
 * data access layer for Workspaces
 * extends JpaRepository to automatically provide CRUD operations
 */

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    List<Workspace> findAllByCreatedBy(UUID createdBy);

    Optional<Workspace> findByIdAndCreatedBy(UUID userId, UUID createdBy);
}
