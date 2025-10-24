package com.eddy.taskboard.config;

import com.eddy.taskboard.workspace.WorkspaceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WorkspaceNotFoundException.class)
    public ErrorBody handleWorkspaceNotFound(WorkspaceNotFoundException ex) {
        return new ErrorBody("NOT_FOUND", ex.getMessage());
    }

    public record ErrorBody(String code, String message) {}
}
