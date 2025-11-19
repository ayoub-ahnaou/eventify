package com.security.eventify.service.interfaces;

import java.util.List;

import com.security.eventify.dto.registration.RegistrationRequestDTO;
import com.security.eventify.dto.registration.RegistrationResponseDTO;

public interface RegistrationService {
    
    public void createRegistration(RegistrationRequestDTO registrationRequestDTO);

    public RegistrationResponseDTO getRegistrtion(Long id);
    
    public void updateRegistration(Long id , String status);

    public void deleteRegistration(Long id);

    public List<RegistrationResponseDTO> getAllRegistrationsOfUser(Long id);
}
