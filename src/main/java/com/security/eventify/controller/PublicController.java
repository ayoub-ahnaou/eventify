package com.security.eventify.controller;

import com.security.eventify.dto.ApiResponse;
import com.security.eventify.dto.ApiResponseSuccess;
import com.security.eventify.dto.user.request.CreateUserRequest;
import com.security.eventify.dto.user.response.UserResponse;
import com.security.eventify.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody CreateUserRequest dto) {
        UserResponse user = this.userService.create(dto);
        ApiResponseSuccess<UserResponse> apiResponse = new ApiResponseSuccess<>(201, "User registered successfully", user);

        return ResponseEntity.status(201).body(apiResponse);
    }
}
