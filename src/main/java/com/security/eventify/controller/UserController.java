package com.security.eventify.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.eventify.dto.registration.response.RegistrationResponseDTO;
import com.security.eventify.service.RegistrationService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/user")
// @PreAuthorize("hasRole('USER')")
public class UserController {


    // UserService UserService;
    RegistrationService registrationService;

    UserController(
        // UserService userService,
        RegistrationService registrationService
    ) {
        // this.userService = userService;
        this.registrationService = registrationService;
    }

    
    @GetMapping("/profile/{id}")
    public String profile(@PathVariable("id") Long id) {
        return new String();
    }

    @GetMapping("/registrations/{id}")
    public List<RegistrationResponseDTO> userRegistrations(@PathVariable("id") Long id) {
        return registrationService.getAllRegistrationsOfUser(id);
    }
    
    
}
