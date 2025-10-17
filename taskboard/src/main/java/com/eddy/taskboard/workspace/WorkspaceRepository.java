package com.eddy.taskboard.workspace;

// imports
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * data access layer for Workspaces
 * extends JpaRepository to automatically provide CRUD operations
 */

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {
}
