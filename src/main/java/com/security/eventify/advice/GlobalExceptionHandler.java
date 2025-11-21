package com.security.eventify.advice;

import com.security.eventify.advice.exeption.*;
import com.security.eventify.dto.ApiResponse;
import com.security.eventify.dto.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ApiResponseError resp = new ApiResponseError(400, "Validation failed", errors);
        return ResponseEntity.badRequest().body(resp);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add("Resource requested not found.");

        ApiResponseError apiResponseError = new ApiResponseError(404, ex.getMessage(), errors);
        return ResponseEntity.status(apiResponseError.getStatus()).body(apiResponseError);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ApiResponse> handleEmailConflict(EmailAlreadyUsedException ex) {
        ApiResponseError resp = new ApiResponseError(409, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        ApiResponseError resp = new ApiResponseError(401, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDenied(AccessDeniedException ex) {
        ApiResponseError resp = new ApiResponseError(403, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse> handleForbidden(ForbiddenException ex) {
        ApiResponseError resp = new ApiResponseError(403, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
    }
}
