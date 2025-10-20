package com.eddy.taskboard.config;

// imports
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import com.eddy.taskboard.user.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // BCrypt for password hashing and verification
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DAO auth provider that pulls users from PostgreSQL
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    // expose AuthenticationManager built from our provider
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    // HTTP security rules and login
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
            // enable form login (works with Swagger’s Authorise and browser)
            .formLogin(Customizer.withDefaults())

            // Also keep HTTP Basic for quick curl testing
            .httpBasic(Customizer.withDefaults())

            // calling DAO provider
            .authenticationProvider(daoAuthenticationProvider(passwordEncoder()));

        return http.build(); // return the configured security filter chain
    }
}