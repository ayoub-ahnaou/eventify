package com.security.eventify.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.security.eventify.dto.EventRequestDTO;
import com.security.eventify.dto.EventResponseDTO;
import com.security.eventify.mapper.EventMapper;
import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.User;
import com.security.eventify.repository.EventRepository;

@Service
public class EventService {

    EventRepository eventRepository;
    EventMapper eventMapper;
    
    public EventService(EventRepository eventRepository , EventMapper eventMapper){
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }


    // this is the method of event creation
    public EventResponseDTO createEvent(EventRequestDTO eventDto) {
        Event event = eventMapper.dtoToEvent(eventDto);
        event.setDateTime(LocalDateTime.now());
        User user = new User(); // just mocke user untill i implement the login method so i can have the userDetails 
        event.setOrganizer(user);
        EventResponseDTO response = eventMapper.eventToDto(eventRepository.save(event));
        return response;
    }

    // this is the method of getting just specifique event
    public EventResponseDTO getEvent(Long id) {
        return eventMapper.eventToDto(eventRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("there is no event with this id")));
    }

    // this is the methoid of update specifique event
    public EventResponseDTO updateEvent(Long id , EventRequestDTO requestDTO) {
        Event Event = eventRepository.findById(id).orElseThrow(()->new IllegalArgumentException("there is no event with this id")); 
        Event.setTitle(requestDTO.getTitle());
        Event.setLocation(requestDTO.getLocation());
        Event.setCapacity(requestDTO.getCapacity());

        return eventMapper.eventToDto(eventRepository.save(Event));
    }

    // this is the method of delete specifique event 
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
    
    // this is the method of get all events
    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream().map((a)->eventMapper.eventToDto(a)).toList();
    }
}