package com.eddy.taskboard.workspace.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * response DTO returned by API endpoints
 * keeps API responses clean and decoupled from internal entities
 */

public record WorkspaceResponse(
        UUID id,
        String name,
        UUID createdBy,
        OffsetDateTime createdAt
) {}

