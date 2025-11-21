package com.security.eventify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/organizer/events")
@PreAuthorize("hasRole('Organizer')")
public  class EventController{  


    EventService eventService;

    EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @PostMapping
    public EventResponseDTO creatEvent(@RequestBody EventRequestDTO eventRequestDTO) {
        return eventService.createEvent(eventRequestDTO);
    }

    @GetMapping("/{id}")
    public EventResponseDTO getEvent(@PathVariable("id") Long id) {
        return eventService.getEvent(id);
    }

    @PutMapping("/{id}")
    public EventResponseDTO updateEvent(@PathVariable("id") Long id , @RequestBody EventRequestDTO eventRequestDTO) {
        return eventService.updateEvent(id, eventRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().body("event deleted successfully");
    }
}