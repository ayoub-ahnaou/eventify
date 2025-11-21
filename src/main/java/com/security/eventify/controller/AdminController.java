package com.security.eventify.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.eventify.dto.event.response.EventResponseDTO;
import com.security.eventify.dto.user.request.UpdateRoleRequest;
import com.security.eventify.dto.user.response.UserResponse;
import com.security.eventify.service.EventService;
import com.security.eventify.service.UserService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('Admin')")
public class AdminController {
    

    private final UserService userService;
    private final EventService eventService;

    AdminController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);    
    }


    @PutMapping("/users/{id}/role")
    public UserResponse UpdateRole(@PathVariable Long id, @RequestBody UpdateRoleRequest roleRequest) {
        return userService.updateRole(id , roleRequest);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
         eventService.deleteEvent(id);
         return ResponseEntity.ok().body("event deleted successfully");
    }
}
