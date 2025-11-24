package com.security.eventify.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.security.eventify.healper.AuthenticatedUser;
import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.User;
import com.security.eventify.model.enums.Role;
import com.security.eventify.repository.EventRepository;
import com.security.eventify.repository.RegistrationRepository;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.EventService;
import com.security.eventify.service.RegistrationService;
import com.security.eventify.service.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
public class RegistrationControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventService eventService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired 
    AuthenticatedUser authenticatedUser;

    private User savedUser;
    private Event savedEvent;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        userRepository.deleteAll();
        registrationRepository.deleteAll();

        User user = new User();
        user.setEmail("email@gmail.com");
        user.setName("ismail");
        user.setPassword(passwordEncoder.encode("pass1234"));
        user.setRole(Role.ROLE_ORGANIZER);

        savedUser = userRepository.save(user);
        
        
        Event event = new Event();
        event.setTitle("event1");
        event.setLocation("local");
        event.setOrganizer(savedUser);
        event.setCapacity(10);


        savedEvent = eventRepository.save(event);
    }

    @Test
    @WithMockUser(username = "email@gmail.com", roles = "USER")
    void createRegistration_success() throws Exception{

        mockMvc.perform(post("/api/user/events/"+ savedEvent.getId() + "/register")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
