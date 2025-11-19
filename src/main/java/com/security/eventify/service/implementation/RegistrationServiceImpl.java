package com.security.eventify.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.security.eventify.dto.registration.RegistrationRequestDTO;
import com.security.eventify.dto.registration.RegistrationResponseDTO;
import com.security.eventify.mapper.RegistrationMapper;
import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.Registration;
import com.security.eventify.model.entity.User;
import com.security.eventify.repository.EventRepository;
import com.security.eventify.repository.RegistrationRepository;
import com.security.eventify.service.interfaces.RegistrationService;


@Service
public class RegistrationServiceImpl implements RegistrationService {

    RegistrationRepository registrationRepository;
    RegistrationMapper registrationMapper;
    EventRepository eventRepository;

    RegistrationServiceImpl(RegistrationRepository registrationRepository, RegistrationMapper registrationMapper, EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
        this.eventRepository = eventRepository;
    }
    


    // this is the method of create registration
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

    // this is the method of get specifique registration by id
    public RegistrationResponseDTO getRegistrtion(Long id) {
        return registrationMapper.registrationToDto(registrationRepository.findById(id).orElseThrow(()->new RuntimeException("there is no registration with the id")));
    }

    // this is the method of update registration
    public void updateRegistration(Long id , String status) {
        Registration registration = registrationRepository.findById(id).orElseThrow(()->new RuntimeException("there is no registration with the id"));
        registration.setStatus(status);
        registrationRepository.save(registration);
    }

    // this is the method of delete registration
    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

    // this the method of get all the registrations
    public List<RegistrationResponseDTO> getAllRegistrationsOfUser(Long userId) {
        return registrationRepository.findRegistrationsByUserId(userId);
    }
}
