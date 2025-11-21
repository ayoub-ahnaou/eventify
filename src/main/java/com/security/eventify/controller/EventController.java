package com.security.eventify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.eventify.dto.event.request.EventRequestDTO;
import com.security.eventify.dto.event.response.EventResponseDTO;
import com.security.eventify.service.EventService;

@RestController
@RequestMapping("/api")
public  class EventController{  


    EventService eventService;

    EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @PostMapping("/organizer/events")
    public EventResponseDTO creatEvent(@RequestBody EventRequestDTO eventRequestDTO) {
        return eventService.createEvent(eventRequestDTO);
    }

    @GetMapping("/organizer/events/{id}")
    public EventResponseDTO getEvent(@PathVariable("id") Long id) {
        return eventService.getEvent(id);
    }

    @PutMapping("/organizer/events/{id}")
    public EventResponseDTO updateEvent(@PathVariable("id") Long id , @RequestBody EventRequestDTO eventRequestDTO) {
        return eventService.updateEvent(id, eventRequestDTO);
    }

    @DeleteMapping("/organizer/events/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().body("event deleted successfully");
    }
}