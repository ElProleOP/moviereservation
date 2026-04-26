package com.claudio.moviereservation.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShowtimeRequest {
    private LocalDateTime dateTime;
    private Integer room;
    private Integer totalSeats;
    private Integer availableSeats;
    private Long movieId;
}