package com.claudio.moviereservation.dto;

import lombok.Data;

@Data
public class ReservationRequest {
    private Long showtimeId;
    private Integer seatNumber;
}