package com.security.eventify.service;

import java.util.List;

import com.security.eventify.dto.event.request.EventRequestDTO;
import com.security.eventify.dto.event.response.EventResponseDTO;


public interface EventService {
    public EventResponseDTO createEvent(EventRequestDTO eventRequestDTO);

    public EventResponseDTO getEvent(Long id);

    public EventResponseDTO updateEvent(Long id , EventRequestDTO rEventRequestDTO);

    public void deleteEvent(Long id);

    public List<EventResponseDTO> getAllEvents();
}
