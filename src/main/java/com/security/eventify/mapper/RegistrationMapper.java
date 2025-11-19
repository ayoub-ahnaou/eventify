package com.security.eventify.mapper;

import org.mapstruct.Mapper;

import com.security.eventify.dto.RegistrationRequestDTO;
import com.security.eventify.dto.RegistrationResponseDTO;
import com.security.eventify.model.entity.Registration;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    Registration dtoToRegistration(RegistrationRequestDTO registrationRequestDTO);
    RegistrationResponseDTO registrationToDto(Registration registration);
}