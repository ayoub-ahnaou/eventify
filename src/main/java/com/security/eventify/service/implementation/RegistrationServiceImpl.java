package com.security.eventify.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.security.eventify.dto.registration.response.RegistrationResponseDTO;
import com.security.eventify.mapper.RegistrationMapper;
import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.Registration;
import com.security.eventify.model.entity.User;
import com.security.eventify.repository.EventRepository;
import com.security.eventify.repository.RegistrationRepository;
import com.security.eventify.repository.UserRepository;
import com.security.eventify.service.RegistrationService;


@Service
public class RegistrationServiceImpl implements RegistrationService {

    RegistrationRepository registrationRepository;
    RegistrationMapper registrationMapper;
    EventRepository eventRepository;
    UserRepository userRepository;

    RegistrationServiceImpl(RegistrationRepository registrationRepository, RegistrationMapper registrationMapper, EventRepository eventRepository, UserRepository userRepository) {
        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }
    


    // this is the method of create registration
    @Override
    public void createRegistration(Long eventId) {
        User user = userRepository.findById(1L).orElse(null);
        Event event = eventRepository.findById(eventId).orElseThrow(()-> new RuntimeException("there is no registration with this id"));
        Registration registration = new Registration();
        registration.setEvent(event);
        registration.setUser(user);
        registration.setRegisterAt(LocalDateTime.now());
        registration.setStatus("waiting");
        registrationRepository.save(registration);
    }

    // this is the method of get specifique registration by id
    @Override
    public RegistrationResponseDTO getRegistrtion(Long id) {
        return registrationMapper.toDto(registrationRepository.findById(id).orElseThrow(()->new RuntimeException("there is no registration with the id")));
    }

    // this is the method of delete registration
    @Override
    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

    // this the method of get all the registrations
    @Override
    public List<RegistrationResponseDTO> getAllRegistrationsOfUser(Long userId) {
        return registrationRepository.findByUserId(userId).stream().map(r -> registrationMapper.toDto(r)).toList();
    }
}
