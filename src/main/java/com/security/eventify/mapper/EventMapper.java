package com.security.eventify.mapper;

import org.mapstruct.Mapper;

import com.security.eventify.dto.EventRequestDTO;
import com.security.eventify.dto.EventResponseDTO;
import com.security.eventify.model.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event dtoToEvent(EventRequestDTO requestDTO);
    EventResponseDTO eventToDto(Event event);
}
