package com.eddy.taskboard.workspace;

import com.eddy.taskboard.workspace.dto.CreateWorkspaceRequest;
import com.eddy.taskboard.workspace.dto.WorkspaceResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * RestController exposing API endpoints for workspaces
 * handles HTTP routing and delegates logic to WorkspaceService
 */
@RestController
@RequestMapping("/api/v1/workspaces")
public class WorkspaceController {

    private final WorkspaceService service;

    public WorkspaceController(WorkspaceService service) {
        this.service = service;
    }

    // creates a new workspace
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkspaceResponse create(@Valid @RequestBody CreateWorkspaceRequest body,
                                    Authentication auth) {
        // temporary user mapping, fake UUID until user accounts exist
        UUID creatorId = UUID.nameUUIDFromBytes(auth.getName().getBytes());
        return service.create(body, creatorId);
    }

    // lists all workspaces in the system
    @GetMapping
    public List<WorkspaceResponse> list() {
        return service.list();
    }
}
