package com.security.eventify.service;

import java.util.List;

import com.security.eventify.dto.registration.request.RegistrationRequestDTO;
import com.security.eventify.dto.registration.response.RegistrationResponseDTO;

public interface RegistrationService {
    
    public void createRegistration(RegistrationRequestDTO registrationRequestDTO);

    public RegistrationResponseDTO getRegistrtion(Long id);
    
    public void updateRegistration(Long id , String status);

    public void deleteRegistration(Long id);

    public List<RegistrationResponseDTO> getAllRegistrationsOfUser(Long id);
}
