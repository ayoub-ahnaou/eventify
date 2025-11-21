package com.security.eventify.controller;

import com.security.eventify.advice.exeption.ForbiddenException;
import com.security.eventify.dto.ApiResponse;
import com.security.eventify.dto.ApiResponseError;
import com.security.eventify.dto.ApiResponseSuccess;
import com.security.eventify.dto.user.response.UserResponse;
import com.security.eventify.mapper.UserMapper;
import com.security.eventify.model.entity.User;
import com.security.eventify.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private final TokenService tokenService;
    private final UserMapper userMapper;

    public UserController(TokenService tokenService, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer "))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseError(400, "Missing or invalid Authorization header", null));

        String raw = auth.substring(7);
        User user = tokenService.getUserFromToken(raw).orElse(null);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponseError(401, "invalid token", null));

        UserResponse resp = userMapper.toDto(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseSuccess<>(200, "Profile successful", resp));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new ForbiddenException("Missing or invalid Authorization header");

        String token = authHeader.substring(7);
        tokenService.deleteToken(token);

        return ResponseEntity.ok(new ApiResponseSuccess<>(200, "Logout successful", null));
    }
}
