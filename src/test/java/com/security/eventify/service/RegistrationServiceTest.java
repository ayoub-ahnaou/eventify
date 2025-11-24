package com.security.eventify.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;

import com.security.eventify.dto.registration.response.RegistrationResponseDTO;
import com.security.eventify.healper.AuthenticatedUser;
import com.security.eventify.mapper.RegistrationMapper;
import com.security.eventify.model.entity.Event;
import com.security.eventify.model.entity.Registration;
import com.security.eventify.model.entity.User;
import com.security.eventify.repository.EventRepository;
import com.security.eventify.repository.RegistrationRepository;
import com.security.eventify.service.implementation.RegistrationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    
    @Mock
    RegistrationRepository registrationRepository;

    @Mock
    RegistrationMapper registrationMapper;

    @Mock
    EventRepository eventRepository;

    @Mock
    AuthenticatedUser authenticatedUser;

    @InjectMocks
    RegistrationServiceImpl registrationService;


    @Test
    @DisplayName("create registration test method")
    void itShouldCreateRegistration() {
        // given
        // user , event , registration
        User user = new User();
        user.setId(1L);
        user.setEmail("email@gmail.com");

        Event event = new Event();
        event.setId(1L);
        event.setTitle("title1");

        Registration registration = new Registration();
        registration.setId(1L);
        registration.setStatus("waiting");
        registration.setEvent(event);
        registration.setUser(user);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(authenticatedUser.get()).thenReturn(user);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        // when
        registrationService.createRegistration(1L);
        
        // then
        verify(registrationRepository).save(any(Registration.class));
        verify(eventRepository).findById(1L);
        verify(authenticatedUser).get();
        verifyNoMoreInteractions(registrationRepository);
    }

    @Test
    @DisplayName("get registration test method")
    void itShouldReturnRegistrationAfterFinfIt()
    {
        // given
        //registrationresponsedto , registration , event , user , 

        User user = new User();
        user.setId(1L);

        Event event = new Event();
        event.setId(1L);

        Registration registration = new Registration();
        registration.setId(1L);
        registration.setStatus("waiting");
        registration.setRegisterAt( LocalDateTime.now());
        registration.setEvent(event);
        registration.setUser(user);
        
        RegistrationResponseDTO registrationResponseDTO = new RegistrationResponseDTO();
        registrationResponseDTO.setStatus("waiting");
        registrationResponseDTO.setEvent(event);
        registrationResponseDTO.setUser(user);

        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));
        when(registrationMapper.toDto(any(Registration.class))).thenReturn(registrationResponseDTO);
        // when
        RegistrationResponseDTO registrationRes = registrationService.getRegistrtion(1L);
        // then

        assertNotNull(registrationRes);
        assertEquals(registrationResponseDTO.getEvent().getId(), registrationRes.getUser().getId());
        assertEquals(registrationResponseDTO.getUser().getId(), registrationRes.getUser().getId());

        verify(registrationRepository).findById(anyLong());
        verify(registrationMapper).toDto(any(Registration.class));
    }

    @Test
    @DisplayName("delete registration test method")
    void itShouldDeleteRegistration() {
        // given
        Long id = 1L;
        // when
        registrationService.deleteRegistration(id);
        // then
        verify(registrationRepository).deleteById(id);
        verifyNoMoreInteractions(registrationRepository);
    }

    @Test
    @DisplayName("get all registrations of user test method")
    void itShouldGetAllRegistrationsOfUser() {
        // given

        Registration registration1 = new Registration();
        registration1.setId(1L);
        registration1.setStatus("waiting");

        Registration registration2 = new Registration();
        registration2.setId(2L);
        registration2.setStatus("finished");

        when(registrationRepository.findByUserId(1L)).thenReturn(List.of(registration1, registration2));
        when(registrationMapper.toDto(any(Registration.class))).thenAnswer(invocation -> {
            Registration r = invocation.getArgument(0);
            RegistrationResponseDTO responseDTO = new RegistrationResponseDTO();
            responseDTO.setEvent(r.getEvent());
            responseDTO.setStatus(r.getStatus());
            responseDTO.setUser(r.getUser());
            return responseDTO;
        });

        // when
        List<RegistrationResponseDTO> registrations = registrationService.getAllRegistrationsOfUser(1L);
        

        // then
        assertEquals(2, registrations.size());
        assertEquals(registration2.getStatus(), registrations.get(1).getStatus());
        
        verify(registrationRepository).findByUserId(1L);
        verify(registrationMapper, times(2)).toDto(any(Registration.class));
    }
}

