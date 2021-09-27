package com.example.cleaningcompanyplanner;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        List<ValidationErrorInfo> violations = ex.getFieldErrors().stream()
                .map(fieldError -> new ValidationErrorInfo(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @Value
    public static class ValidationErrorResponse {
        List<ValidationErrorInfo> violations;
    }

    @Value
    public static class ValidationErrorInfo {
        String field;
        String message;
    }
}
