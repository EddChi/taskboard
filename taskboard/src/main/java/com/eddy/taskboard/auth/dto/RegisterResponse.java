package com.eddy.taskboard.auth.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

// the output from the /auth/register endpoint
public record RegisterResponse(
        UUID id,
        String email,
        String fullName,
        OffsetDateTime createdAt
) {}
