package com.security.eventify.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.eventify.dto.event.request.EventRequestDTO;
import com.security.eventify.dto.event.response.EventResponseDTO;
import com.security.eventify.healper.AuthenticatedUser;
import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.User;
import com.security.eventify.repository.EventRepository;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.EventService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
public class EventControllerIT {

    @Autowired
    EventService eventService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockitoBean
    AuthenticatedUser authenticatedUser;

    private User savedUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        eventRepository.deleteAll();

        User user = new User();
        user.setEmail("email@gmail.com");
        user.setName("ismail");
        user.setPassword(passwordEncoder.encode("pass1234"));

        savedUser = userRepository.save(user);
        when(authenticatedUser.get()).thenReturn(savedUser);        
    }


    @Test
    void createEvent_success() throws Exception{
        EventRequestDTO eventReq = new EventRequestDTO();
        eventReq.setTitle("ismail");
        eventReq.setLocation("lmghrib");
        eventReq.setCapacity(20);

        String json = objectMapper.writeValueAsString(eventReq);
        mockMvc.perform(post("/api/organizer/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(eventReq.getTitle()));       
    }

    @Test
    void getEvent_success() throws Exception{
        Event eventReq = new Event();
        eventReq.setTitle("Test Event");
        eventReq.setLocation("Test Location");
        eventReq.setCapacity(25);

        Event event = eventRepository.save(eventReq);
        Long id = event.getId();

        mockMvc.perform(get("/api/organizer/events/" + id )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(eventReq.getTitle()));
                
    }

    @Test
    void updateEvent_success() throws Exception{
        Event event = new Event();
        event.setTitle("Test Event");
        event.setLocation("Test Location");
        event.setCapacity(25);
        Event savedEvent = eventRepository.save(event);
        Long id = savedEvent.getId();

        EventRequestDTO eventReq = new EventRequestDTO();
        eventReq.setTitle("test updated");

        String json  = objectMapper.writeValueAsString(eventReq);
        mockMvc.perform(put("/api/organizer/events/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(eventReq.getTitle()));
    }


    @Test
    void deleteEvent_success() throws Exception{
        Event event = new Event();
        event.setTitle("Test Event");
        event.setLocation("Test Location");
        event.setCapacity(25);
        Event savedEvent = eventRepository.save(event);
        Long id = savedEvent.getId();
        
        mockMvc.perform(delete("/api/organizer/events/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(0, eventRepository.findAll().size());
    }
}
