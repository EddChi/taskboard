package com.eddy.taskboard.workspace.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * represents input data when creating a new workspace
 * uses @NotBlank to ensure the workspace name isn't empty
 */

public record CreateWorkspaceRequest(
        @NotBlank String name
) {}

