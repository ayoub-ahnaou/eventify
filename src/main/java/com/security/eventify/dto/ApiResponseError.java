package com.security.eventify.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ApiResponseError extends ApiResponse {
    private List<String> errors;

    public ApiResponseError(Integer status, String message, List<String> errors) {
        super(status, message);
        this.errors = errors;
    }
}
