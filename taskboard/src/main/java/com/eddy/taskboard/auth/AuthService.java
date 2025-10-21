package com.eddy.taskboard.auth;

import com.eddy.taskboard.auth.dto.RegisterRequest;
import com.eddy.taskboard.auth.dto.RegisterResponse;
import com.eddy.taskboard.user.User;
import com.eddy.taskboard.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

// handles user registration and authentication helpers
@Service
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest req) {
        if (users.existsByEmailIgnoreCase(req.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        var user = new User(
                UUID.randomUUID(),
                req.email().trim(),
                passwordEncoder.encode(req.password()),
                req.fullName().trim(),
                OffsetDateTime.now(ZoneOffset.UTC)
        );

        var saved = users.save(user);

        return new RegisterResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getFullName(),
                saved.getCreatedAt()
        );
    }
}
