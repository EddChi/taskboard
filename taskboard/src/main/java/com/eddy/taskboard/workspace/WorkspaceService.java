package com.eddy.taskboard.workspace;

import com.eddy.taskboard.workspace.dto.CreateWorkspaceRequest;
import com.eddy.taskboard.workspace.dto.WorkspaceResponse;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

/**
 * business logic for workspace management
 * handles validation, entity creation, and transformation
 */

@Service
public class WorkspaceService {

    private final WorkspaceRepository repo;

    public WorkspaceService(WorkspaceRepository repo) {
        this.repo = repo;
    }

    // creates a new workspace
    public WorkspaceResponse create(CreateWorkspaceRequest req, UUID userId) {
        var entity = new Workspace(
                UUID.randomUUID(),  // generate unique ID
                req.name().trim(),  // trim name to avoid trailing spaces
                userId,  // Assign creator
                OffsetDateTime.now(ZoneOffset.UTC)  // uses UTC timestamp
        );

        var saved = repo.save(entity);
        return toResponse(saved);
    }

    // returns all existing workspaces
    @Deprecated
    public List<WorkspaceResponse> list() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // list only the caller's workspaces
    public List<WorkspaceResponse> listUserWorkspaces(UUID userId) {
        return repo.findAllByCreatedBy(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    // get a single workspace owned by caller, else 404-equivalent via exception
    public WorkspaceResponse getUserWorkspace(UUID workspaceId, UUID userId) {
        var ws = repo.findByIdAndCreatedBy(workspaceId, userId)
                .orElseThrow(() -> new WorkspaceNotFoundException(workspaceId));
        return toResponse(ws);
    }

    // helper to convert from entity to DTO
    private WorkspaceResponse toResponse(Workspace w) {
        return new WorkspaceResponse(
                w.getuserId(),
                w.getName(),
                w.getCreatedBy(),
                w.getCreatedAt());
    }
}

