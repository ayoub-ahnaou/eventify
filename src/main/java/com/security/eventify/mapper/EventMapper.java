package com.security.eventify.mapper;

import org.mapstruct.Mapper;

import com.security.eventify.dto.event.EventRequestDTO;
import com.security.eventify.dto.event.EventResponseDTO;
import com.security.eventify.model.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event dtoToEvent(EventRequestDTO requestDTO);
    EventResponseDTO eventToDto(Event event);
}
