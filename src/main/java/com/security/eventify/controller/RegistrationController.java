package com.security.eventify.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.eventify.dto.registration.response.RegistrationResponseDTO;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/user/events")
public class RegistrationController {
    
    RegistrationController() {

    }

    @PostMapping("/{id}/register")
    public void register(@PathVariable Long id) {
    }
    
}
