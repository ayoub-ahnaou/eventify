package com.security.eventify.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.eventify.model.entity.User;
import com.security.eventify.model.enums.Role;
import com.security.eventify.repository.TokenRepository;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.implementation.TokenServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class UserControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenServiceImpl tokenService;

    @Autowired
    ObjectMapper objectMapper;

    private User savedUser;
    private String rawToken;

    @BeforeEach
    void setUp() {
        tokenRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setEmail("email@gmail.com");
        user.setName("ismail");
        user.setPassword(passwordEncoder.encode("pass1234"));
        user.setRole(Role.ROLE_USER);

        savedUser = userRepository.save(user);
        rawToken = tokenService.generateToken(user);
    }

    @Test
    @DisplayName("GET /api/user/profile - success")
    void getProfile_Success() throws Exception {
        mockMvc.perform(get("/api/user/profile")
                .header("Authorization", "Bearer " + rawToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.email").value(savedUser.getEmail()))
                .andExpect(jsonPath("$.data.name").value(savedUser.getName()));
    }

    @Test
    @DisplayName("GET /api/user/profile - missing header -> 400")
    void getProfile_missingHeader() throws Exception {
        mockMvc.perform(get("/api/user/profile"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Missing or invalid Authorization header"));
    }

    @Test
    @DisplayName("GET /api/user/profile - invalid token -> 401")
    void getProfile_invalidToken() throws Exception {
        mockMvc.perform(get("/api/user/profile")
                .header("Authorization", "Bearer invalidRawToken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("invalid token"));
    }

    @Test
    @DisplayName("POST /api/user/logout - success deletes token")
    void logout_success() throws Exception {

        Assertions.assertFalse(tokenRepository.findAll().isEmpty());

        mockMvc.perform(post("/api/user/logout")
                .header("Authorization", "Bearer " + rawToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logout successful"));

        Assertions.assertFalse(tokenRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("POST /api/user/logout - missing header -> Forbidden")
    void logout_missingHeader_forbidden() throws Exception {
        mockMvc.perform(post("/api/user/logout"))
                .andExpect(status().isForbidden());
    }
}
