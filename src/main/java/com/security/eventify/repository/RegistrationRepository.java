package com.security.eventify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.eventify.model.entity.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    
}