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
    public WorkspaceResponse create(CreateWorkspaceRequest req, UUID creatorId) {
        var entity = new Workspace(
                UUID.randomUUID(),  // generate unique ID
                req.name().trim(),  // trim name to avoid trailing spaces
                creatorId,  // Assign creator
                OffsetDateTime.now(ZoneOffset.UTC)  // uses UTC timestamp
        );

        var saved = repo.save(entity);
        return toResponse(saved);
    }

    // returns all existing workspaces
    public List<WorkspaceResponse> list() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // helper to convert from entity to DTO
    private WorkspaceResponse toResponse(Workspace w) {
        return new WorkspaceResponse(w.getId(), w.getName(), w.getCreatedBy(), w.getCreatedAt());
    }
}

