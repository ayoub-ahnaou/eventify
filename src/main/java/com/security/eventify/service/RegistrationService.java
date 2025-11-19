package com.security.eventify.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.security.eventify.dto.RegistrationRequestDTO;
import com.security.eventify.dto.RegistrationResponseDTO;
import com.security.eventify.mapper.RegistrationMapper;
import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.Registration;
import com.security.eventify.model.entity.User;
import com.security.eventify.repository.EventRepository;
import com.security.eventify.repository.RegistrationRepository;


@Service
public class RegistrationService {

    RegistrationRepository registrationRepository;
    RegistrationMapper registrationMapper;
    EventRepository eventRepository;

    RegistrationService(RegistrationRepository registrationRepository, RegistrationMapper registrationMapper, EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
        this.eventRepository = eventRepository;
    }
    


    public void createRegistration(RegistrationRequestDTO registrationRequestDTO) {
        User user = new User();
        Event event = eventRepository.findById(registrationRequestDTO.getEventId()).orElseThrow(()-> new RuntimeException("there is no registration with this id"));
        Registration registration = new Registration();
        registration.setEvent(event);
        registration.setUser(user);
        registration.setRegisterAt(LocalDateTime.now());
        registration.setStatus("waiting");
        registrationRepository.save(registration); 
    }

    public Registration getRegistrtion(Long id) {
        return registrationRepository.findById(id).orElseThrow(()->new RuntimeException("there is no registration with the id"));
    }

    public void updateRegistration(Long id , String status) {
        Registration registration = registrationRepository.findById(id).orElseThrow(()->new RuntimeException("there is no registration with the id"));
        registration.setStatus(status);
        registrationRepository.save(registration);
    }

    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

    public List<RegistrationResponseDTO> getAllRegistrationsOfUser(Long userId) {
        return registrationRepository.findRegistrationsByUserId(userId);
    }
}
