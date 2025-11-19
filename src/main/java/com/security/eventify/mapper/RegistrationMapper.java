package com.security.eventify.mapper;

import org.mapstruct.Mapper;

import com.security.eventify.dto.registration.request.RegistrationRequestDTO;
import com.security.eventify.dto.registration.response.RegistrationResponseDTO;
import com.security.eventify.model.entity.Registration;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    Registration toEntity(RegistrationRequestDTO registrationRequestDTO);
    RegistrationResponseDTO toDto(Registration registration);
}