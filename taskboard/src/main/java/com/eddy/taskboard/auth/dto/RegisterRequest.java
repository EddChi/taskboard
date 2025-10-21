package com.eddy.taskboard.auth.dto;

// imports
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// input for the /auth/register endpoint
public record RegisterRequest(
        @Email @NotBlank String email,
        @NotBlank @Size(min = 2, max = 80) String fullName,
        @NotBlank @Size(min = 6, max = 128) String password
) {}