package com.security.eventify.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.eventify.dto.event.request.EventRequestDTO;
import com.security.eventify.dto.user.request.CreateUserRequest;
import com.security.eventify.dto.user.request.LoginRequest;
import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.User;
import com.security.eventify.model.enums.Role;
import com.security.eventify.repository.EventRepository;
import com.security.eventify.repository.TokenRepository;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.EventService;
import com.security.eventify.service.TokenService;
import com.security.eventify.service.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
public class PublicControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventService eventService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TokenService TokenService;


    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        tokenRepository.deleteAll();
        eventRepository.deleteAll();
    }

    @Test
    void register_success() throws Exception{
        CreateUserRequest user = new CreateUserRequest();
        user.setEmail("register@gmail.com");
        user.setName("ismail");
        user.setPassword(passwordEncoder.encode("user1234"));
        String json = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/api/public/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(user.getEmail()));
    }

    @Test
    void getAllEvents_success() throws Exception{
        Event eventReq1 = new Event();
        eventReq1.setTitle("ismail1");
        eventReq1.setLocation("lmghrib");
        eventReq1.setCapacity(20);

        Event eventReq2 = new Event();
        eventReq2.setTitle("ismail2");
        eventReq2.setLocation("lmghrib");
        eventReq2.setCapacity(20);

        Event savedEvent1 = eventRepository.save(eventReq1);
        Event savedEvent2 = eventRepository.save(eventReq2);

        mockMvc.perform(get("/api/public/events")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(savedEvent1.getTitle()))
                .andExpect(jsonPath("$[1].title").value(savedEvent2.getTitle()));
    }
    

    @Test
    void login_success() throws Exception{
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setPassword(passwordEncoder.encode("pass1234"));
        user.setName("ismail");
        user.setRole(Role.ROLE_USER);

        User savedUser = userRepository.save(user);
        

        LoginRequest login = new LoginRequest();
        login.setEmail("email@gmail.com");
        login.setPassword("pass1234");

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(post("/api/public/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}
