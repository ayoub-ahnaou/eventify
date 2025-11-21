package com.security.eventify.service;

import java.util.List;

import com.security.eventify.dto.registration.response.RegistrationResponseDTO;

public interface RegistrationService {
    
    public void createRegistration(Long eventId);

    public RegistrationResponseDTO getRegistrtion(Long id);
    
    public void deleteRegistration(Long id);

    public List<RegistrationResponseDTO> getAllRegistrationsOfUser(Long id);
}
