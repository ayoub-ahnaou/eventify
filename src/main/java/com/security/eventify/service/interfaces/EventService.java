package com.security.eventify.service.interfaces;

import java.util.List;

import com.security.eventify.dto.event.EventRequestDTO;
import com.security.eventify.dto.event.EventResponseDTO;


public interface EventService {
    public EventResponseDTO createEvent(EventRequestDTO eventRequestDTO);

    public EventResponseDTO getEvent(Long id);

    public EventResponseDTO updateEvent(Long id , EventRequestDTO rEventRequestDTO);

    public void deleteEvent(Long id);

    public List<EventResponseDTO> getAllEvents();
}
