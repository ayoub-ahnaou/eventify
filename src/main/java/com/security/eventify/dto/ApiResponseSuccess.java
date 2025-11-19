package com.security.eventify.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ApiResponseSuccess<T> extends ApiResponse {
    private T data;

    public ApiResponseSuccess(Integer status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}
