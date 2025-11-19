package com.security.eventify.advice;

import com.security.eventify.advice.exeption.ResourceNotFoundException;
import com.security.eventify.dto.ApiResponseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
//        List<String> errors = new ArrayList<>();
//        errors.add("Resource requested not found.");
//
//        ApiResponseError apiResponseError = new ApiResponseError(404, ex.getMessage(), errors);
//        return ResponseEntity.status(apiResponseError.getStatus()).body(apiResponseError);
//    }
}
