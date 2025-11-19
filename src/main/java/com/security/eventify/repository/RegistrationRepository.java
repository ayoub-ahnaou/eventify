package com.security.eventify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.eventify.dto.registration.response.RegistrationResponseDTO;
import com.security.eventify.model.entity.Registration;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<RegistrationResponseDTO> findRegistrationsByUserId(Long id);
}
