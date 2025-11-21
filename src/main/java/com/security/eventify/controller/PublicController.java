package com.security.eventify.controller;

import com.security.eventify.advice.exeption.InvalidCredentialsException;
import com.security.eventify.advice.exeption.ResourceNotFoundException;
import com.security.eventify.dto.ApiResponse;
import com.security.eventify.dto.ApiResponseSuccess;
import com.security.eventify.dto.user.request.CreateUserRequest;
import com.security.eventify.dto.user.request.LoginRequest;
import com.security.eventify.dto.user.response.UserResponse;
import com.security.eventify.model.entity.User;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.TokenService;
import com.security.eventify.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public PublicController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody CreateUserRequest dto) {
        UserResponse user = this.userService.create(dto);
        ApiResponseSuccess<UserResponse> apiResponse = new ApiResponseSuccess<>(201, "User registered successfully", user);

        return ResponseEntity.status(201).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElse(null);

        if(user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException("Invalid credentials, email or password incorrect.");

        String token = tokenService.generateToken(user);
        return ResponseEntity.status(200).body(new ApiResponseSuccess<>(200, "Token generated successfully", token));
    }
}
