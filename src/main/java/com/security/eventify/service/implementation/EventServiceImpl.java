package com.security.eventify.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.security.eventify.dto.event.request.EventRequestDTO;
import com.security.eventify.dto.event.response.EventResponseDTO;
import com.security.eventify.mapper.EventMapper;
import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.User;
import com.security.eventify.repository.*;
import com.security.eventify.service.EventService;

@Service
public class EventServiceImpl implements EventService {


    EventRepository eventRepository;
    UserRepository userRepository;
    EventMapper eventMapper;
    
    public EventServiceImpl(EventRepository eventRepository , EventMapper eventMapper,UserRepository UserRepository, UserRepository userRepository){
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.userRepository = userRepository;
    }


    // this is the method of event creation
    @Override
    public EventResponseDTO createEvent(EventRequestDTO eventDto) {
        Event event = eventMapper.toEntity(eventDto);
        LocalDateTime dateTime = LocalDateTime.now();
        event.setDateTime(dateTime);
        User user = userRepository.findById(1L).orElse(null) ;// we gonna just use this until we do the login and get the orginazer from the authontication
        event.setOrganizer(user);
        EventResponseDTO response = eventMapper.toDto(eventRepository.save(event));
        return response;
    }

    // this is the method of getting just specifique event
    @Override
    public EventResponseDTO getEvent(Long id) {
        return eventMapper.toDto(eventRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("there is no event with this id")));
    }

    // this is the methoid of update specifique event
    @Override
    public EventResponseDTO updateEvent(Long id , EventRequestDTO requestDTO) {
        Event Event = eventRepository.findById(id).orElseThrow(()->new IllegalArgumentException("there is no event with this id")); 
        Event.setTitle(requestDTO.getTitle());
        Event.setLocation(requestDTO.getLocation());
        Event.setCapacity(requestDTO.getCapacity());

        return eventMapper.toDto(eventRepository.save(Event));
    }

    // this is the method of delete specifique event 
    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
    
    // this is the method of get all events
    @Override
    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream().map((a)->eventMapper.toDto(a)).toList();
    }
}