package com.docsehr.flowerhub.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Global exception handler for all exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> errorAttributes = new HashMap<>();

        // Populate error details
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorAttributes.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorAttributes.put("path", request.getDescription(false).replace("uri=", ""));
        errorAttributes.put("message", ex.getMessage());

        return new ResponseEntity<>(errorAttributes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

