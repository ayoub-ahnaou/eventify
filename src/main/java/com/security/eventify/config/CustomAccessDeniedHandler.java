package com.security.eventify.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.eventify.advice.exeption.AccessDeniedException;
import com.security.eventify.dto.ApiResponseError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiResponseError error = new ApiResponseError(
                403,
                "Access denied: " + accessDeniedException.getMessage(),
                null
        );

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getWriter(), error);
    }
}
