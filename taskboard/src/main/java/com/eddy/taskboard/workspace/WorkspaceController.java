package com.eddy.taskboard.workspace;

import com.eddy.taskboard.workspace.dto.CreateWorkspaceRequest;
import com.eddy.taskboard.workspace.dto.WorkspaceResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.eddy.taskboard.user.AuthUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

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

    @Operation(
            summary = "Create a new workspace",
            description = "Automatically sets `createdBy` to the authenticated user's ID.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )

    // creates a new workspace
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkspaceResponse create(@Valid @RequestBody CreateWorkspaceRequest body,
                                    @AuthenticationPrincipal AuthUserDetails user) {
        return service.create(body, user.getId());
    }

    // lists all workspaces in the system
    @GetMapping
    public List<WorkspaceResponse> list() {
        return service.list();
    }
}
