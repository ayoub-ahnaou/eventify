package com.security.eventify.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.security.eventify.dto.event.request.EventRequestDTO;
import com.security.eventify.dto.event.response.EventResponseDTO;
import com.security.eventify.healper.AuthenticatedUser;
import com.security.eventify.mapper.EventMapper;
import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.User;
import com.security.eventify.model.enums.Role;
import com.security.eventify.repository.EventRepository;
import com.security.eventify.service.implementation.EventServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    
    @Mock
    EventRepository eventRepository;

    @Mock
    AuthenticatedUser authenticatedUser;

    @Mock
    EventMapper eventMapper;

    @InjectMocks
    EventServiceImpl eventService;


    @Test
    @DisplayName("create event test method")
    void itShouldReturnTheEventCreated() {
        // given
        //eventResponseDto , eventRequestDto , Event , User
        EventRequestDTO eventRequestDTO = new EventRequestDTO();
        eventRequestDTO.setCapacity(20);
        eventRequestDTO.setTitle("title1");
        eventRequestDTO.setLocation("morocco");

        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        eventResponseDTO.setCapacity(20);
        eventResponseDTO.setDateTime(LocalDateTime.now());
        eventResponseDTO.setLocation("morocco");
        eventResponseDTO.setTitle("title1");
        
        User organizer = new User();
        organizer.setId(1L);
        organizer.setRole(Role.ROLE_ORGANIZER);

        Event event = new Event();
        event.setId(1L);
        event.setOrganizer(organizer);
        event.setCapacity(20);
        event.setTitle("title1");
        event.setLocation("morocco");
        event.setDateTime(LocalDateTime.now());

        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(eventMapper.toDto(any(Event.class))).thenReturn(eventResponseDTO);
        when(eventMapper.toEntity(eventRequestDTO)).thenReturn(event);
        when(authenticatedUser.get()).thenReturn(organizer);

        // when
        EventResponseDTO eventResResult = eventService.createEvent(eventRequestDTO);

        // then

        assertNotNull(eventResResult);
        assertEquals(eventResResult.getTitle() , event.getTitle());
        
        verify(eventRepository).save(any(Event.class));
        verify(eventMapper).toDto(any(Event.class));
        verify(eventMapper).toEntity(eventRequestDTO);
    }


    @Test
    @DisplayName("update event test method")
    void itShouldReturnEventAfterFindIt() {
        // given
        // Event , EventResponseDTO
        User organizer = new User();
        organizer.setId(1L);
        organizer.setRole(Role.ROLE_ORGANIZER);

        Event event = new Event();
        event.setId(1L);
        event.setOrganizer(organizer);
        event.setCapacity(20);
        event.setTitle("title1");
        event.setLocation("morocco");
        event.setDateTime(LocalDateTime.now());

        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        eventResponseDTO.setCapacity(20);
        eventResponseDTO.setDateTime(LocalDateTime.now());
        eventResponseDTO.setLocation("morocco");
        eventResponseDTO.setTitle("title1");
        
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventMapper.toDto(any(Event.class))).thenReturn(eventResponseDTO);
        
        // when
        EventResponseDTO eventResResult = eventService.getEvent(1L);
        // then

        assertNotNull(eventResResult);
        assertEquals(eventResponseDTO.getTitle(), eventResResult.getTitle());
        
        verify(eventRepository).findById(1l);
        verify(eventMapper).toDto(any(Event.class));

    }


    @Test
    @DisplayName("event update test method")
    void itShouldReturnTheEventAfterUpdateIt() {
        // given
        User organizer = new User();
        organizer.setId(1L);
        organizer.setRole(Role.ROLE_ORGANIZER);

        Event event = new Event();
        event.setId(1L);
        event.setOrganizer(organizer);
        event.setCapacity(20);
        event.setTitle("title1");
        event.setLocation("morocco");
        event.setDateTime(LocalDateTime.now());

        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        eventResponseDTO.setCapacity(20);
        eventResponseDTO.setDateTime(LocalDateTime.now());
        eventResponseDTO.setLocation("morocco");
        eventResponseDTO.setTitle("title1");
        
        EventRequestDTO eventRequestDTO = new EventRequestDTO();
        eventRequestDTO.setCapacity(20);
        eventRequestDTO.setTitle("title1");
        eventRequestDTO.setLocation("morocco");

        when(eventRepository.findById(1l)).thenReturn(Optional.of(event));
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.toDto(event)).thenReturn(eventResponseDTO);
        // when
        EventResponseDTO eventResResult = eventService.updateEvent(1l, eventRequestDTO);
        // then 

        assertNotNull(eventResResult);
        assertEquals(eventRequestDTO.getTitle(), eventResResult.getTitle());

        verify(eventRepository).save(any(Event.class));
        verify(eventMapper).toDto(any(Event.class));
    }

    @Test
    @DisplayName("event delete test method")
    void itShouldDeleteEvents() {
        // given
        Long id = 1L;

        // when
        eventService.deleteEvent(id);

        // then
        verify(eventRepository).deleteById(id);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    @DisplayName("get all events test method")
    void itShouldGetAllEventsIfSuccess() {
        // given
        User organizer = new User();
        organizer.setId(1L);
        organizer.setRole(Role.ROLE_ORGANIZER);

        Event event1 = new Event();
        event1.setId(1L);
        event1.setOrganizer(organizer);
        event1.setCapacity(20);
        event1.setTitle("title1");
        event1.setLocation("morocco");
        event1.setDateTime(LocalDateTime.now());

        Event event2 = new Event();
        event2.setId(2L);
        event2.setOrganizer(organizer);
        event2.setCapacity(10);
        event2.setTitle("title2");
        event2.setLocation("morocco");
        event2.setDateTime(LocalDateTime.now());


        EventResponseDTO eventResponseDTO1 = new EventResponseDTO();
        eventResponseDTO1.setCapacity(20);
        eventResponseDTO1.setDateTime(LocalDateTime.now());
        eventResponseDTO1.setLocation("morocco");
        eventResponseDTO1.setTitle("title1");

        EventResponseDTO eventResponseDTO2 = new EventResponseDTO();
        eventResponseDTO2.setCapacity(10);
        eventResponseDTO2.setDateTime(LocalDateTime.now());
        eventResponseDTO2.setLocation("morocco");
        eventResponseDTO2.setTitle("title2");

        when(eventRepository.findAll()).thenReturn(List.of(event1,event2));
        when(eventMapper.toDto(any(Event.class))).thenAnswer(invocation -> {
            Event e = invocation.getArgument(0);
            EventResponseDTO event = new EventResponseDTO();
            event.setCapacity(e.getCapacity());
            event.setDateTime(e.getDateTime());
            event.setLocation(e.getLocation());
            event.setTitle(e.getTitle());
            return event;
        });
        // when
        List<EventResponseDTO> eventsResult = eventService.getAllEvents();
        // then

        assertNotNull(eventsResult);
        assertEquals(2, eventsResult.size());
        assertEquals("title1", eventsResult.get(0).getTitle());
        assertEquals("title2", eventsResult.get(1).getTitle());

        verify(eventRepository).findAll();
        verify(eventMapper, times(2)).toDto(any(Event.class));
    }
}
