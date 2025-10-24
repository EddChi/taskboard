package com.eddy.taskboard.workspace;

import com.eddy.taskboard.workspace.dto.CreateWorkspaceRequest;
import com.eddy.taskboard.workspace.dto.WorkspaceResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        return service.create(body, user.getuserId());
    }

    @Operation(
            summary = "List my workspaces",
            description = "Returns workspaces owned by the authenticated user.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )

    // lists all workspaces in the system
    @GetMapping
    public List<WorkspaceResponse> list(@AuthenticationPrincipal AuthUserDetails user) {
        return service.listUserWorkspaces(user.getuserId());
    }

    @Operation(
            summary = "Get a workspace I own",
            description = "Returns a workspace if owned by the authenticated user; otherwise 404.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )

    @GetMapping("/{userId}")
    public WorkspaceResponse get(
            @PathVariable UUID userId,
            @AuthenticationPrincipal AuthUserDetails user) {
        return service.getUserWorkspace(userId, user.getuserId());
    }
}
