package com.security.eventify.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.eventify.service.RegistrationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/user/events")
@PreAuthorize("hasRole('User')")
public class RegistrationController {

    private final RegistrationService registrationService;
    
    RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/{id}/register")
    public ResponseEntity<String> createRegistration(@PathVariable("id") Long eventId) {
        registrationService.createRegistration(eventId);
        return ResponseEntity.ok().body("registration created succefullt");
    }
    
}