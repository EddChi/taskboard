package com.eddy.taskboard.config;

// imports
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // disabling csrf for simplicity in a stateless API
            .csrf(AbstractHttpConfigurer::disable)
            // defining which requests are allowed without authentication
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/v1/health",   // Health check endpoint
                    "/swagger-ui.html", // Swagger UI main page
                    "/swagger-ui/**",   // any other Swagger UI pages
                    "/v3/api-docs/**"   // OpenAPI JSON
                ).permitAll()   // public access to all the above
                .anyRequest().authenticated()   // everything else requires a login
            )
            .httpBasic(Customizer.withDefaults()); // temporary usage; will replace with JWT
        return http.build(); // return the configured security filter chain
    }
}