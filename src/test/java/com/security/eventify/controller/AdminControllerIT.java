package com.security.eventify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.eventify.dto.user.request.UpdateRoleRequest;
import com.security.eventify.dto.user.response.UserResponse;
import com.security.eventify.model.entity.User;
import com.security.eventify.model.enums.Role;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.EventService;
import com.security.eventify.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) 
@ActiveProfiles("test")
public class AdminControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    ObjectMapper objectMapper;

    private User savedUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user = new User();
        user.setEmail("user@test.com");
        user.setName("Admin Test User");
        user.setPassword("pass1234");
        user.setRole(Role.ROLE_USER);

        savedUser = userRepository.save(user);
    }

    @Test
    @DisplayName("GET /api/admin/users - success")
    void testGetUsers() throws Exception {
        mockMvc.perform(get("/api/admin/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    List<UserResponse> users = objectMapper.readValue(json,
                            objectMapper.getTypeFactory().constructCollectionType(List.class, UserResponse.class));
                    assertEquals(1, users.size());
                    assertEquals(savedUser.getEmail(), users.get(0).getEmail());
                });
    }

    @Test
    @DisplayName("PUT /api/admin/users/{id}/role - update role success")
    void testUpdateRole() throws Exception {
        UpdateRoleRequest roleRequest = new UpdateRoleRequest();
        roleRequest.setRole(Role.ROLE_ADMIN);

        String jsonRequest = objectMapper.writeValueAsString(roleRequest);

        mockMvc.perform(put("/api/admin/users/" + savedUser.getId() + "/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    UserResponse response = objectMapper.readValue(json, UserResponse.class);
                    assertEquals(Role.ROLE_ADMIN, response.getRole());
                });
    }

    @Test
    @DisplayName("DELETE /api/admin/events/{id} - delete event success")
    void testDeleteEvent() throws Exception {
        Long eventId = 1L; 

        mockMvc.perform(delete("/api/admin/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String message = result.getResponse().getContentAsString();
                    assertTrue(message.contains("event deleted successfully"));
                });
    }
}
