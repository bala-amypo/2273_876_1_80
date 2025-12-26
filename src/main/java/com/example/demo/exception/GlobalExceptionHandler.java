package com.example.demo.exception;

import com.example.demo.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.demo.dto.ApiResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiResponse(ex.getMessage(), false),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(
                new ApiResponse(ex.getMessage(), false),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(
                new ApiResponse(ex.getMessage(), false),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
