package com.eddy.taskboard.system;

// imports
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthController
 * ----------------
 * A simple REST controller that exposes a health check endpoint.
 * Used by developers or monitoring tools to verify that the app is running.
 */

@RestController
public class HealthController {

    /**
     * GET /api/v1/health
     * -------------------
     * Returns a simple "OK" message to confirm the service is up.
     */
    @GetMapping("/api/v1/health")
    public String healthCheck() {
        return "OK";
    }
}
