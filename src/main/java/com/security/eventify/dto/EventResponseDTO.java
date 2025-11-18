package com.security.eventify.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
    private String title;
    private String location;
    private LocalDateTime dateTime;
    private Integer capacity;
}
