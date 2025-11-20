package com.security.eventify.dto.registration.response;

import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {
    private String status;
    private User user;
    private Event event;
}
