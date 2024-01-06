package com.todolist.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicateTaskException.class)
    public ResponseEntity<Object> handleDuplicateTaskException(DuplicateTaskException ex, WebRequest request) {
        return ResponseEntity.badRequest().body("Erro: " + ex.getMessage());
    }
}
