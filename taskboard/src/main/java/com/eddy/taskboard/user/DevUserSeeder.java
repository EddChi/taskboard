package com.eddy.taskboard.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * seeds a development user at startup if none exists
 * email: eddy@example.com
 * password: eddy123 (BCrypt-hashed)
 */
@Configuration
public class DevUserSeeder {

    @Bean
    CommandLineRunner seedUser(UserRepository users) {
        return args -> {
            String email = "eddy@example.com";
            if (users.existsByEmailIgnoreCase(email)) return;

            var encoder = new BCryptPasswordEncoder();
            var hash = encoder.encode("eddy123");

            var user = new User(
                    UUID.randomUUID(),
                    email,
                    hash,
                    "Eddy",
                    OffsetDateTime.now(ZoneOffset.UTC)
            );
            users.save(user);
        };
    }
}
