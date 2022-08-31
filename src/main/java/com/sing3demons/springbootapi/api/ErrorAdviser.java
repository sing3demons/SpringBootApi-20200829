package com.sing3demons.springbootapi.api;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.ProductException;
import com.sing3demons.springbootapi.exception.UserException;

import lombok.Data;

@RestControllerAdvice
public class ErrorAdviser {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        ErrorResponse response = new ErrorResponse();
        response.setError(e.getMessage());
        response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorResponse> handleProductException(ProductException e) {
        ErrorResponse response = new ErrorResponse();
        response.setError(e.getMessage());
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        ErrorResponse response = new ErrorResponse();
        response.setError(e.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Data
    public static class ErrorResponse {
        private LocalDateTime timestamp = LocalDateTime.now();
        private int status;
        private String error;
    }

}
