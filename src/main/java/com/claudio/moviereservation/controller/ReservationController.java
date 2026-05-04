package com.claudio.moviereservation.controller;

import com.claudio.moviereservation.dto.ReservationRequest;
import com.claudio.moviereservation.model.Reservation;
import com.claudio.moviereservation.service.ReservationService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {this.reservationService = reservationService;}

    @GetMapping("/reservations/my")
    public ResponseEntity<List<Reservation>> getMyReservations(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(reservationService.getMyReservations(username));
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(Authentication authentication, @RequestBody ReservationRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(request, authentication.getName()));
    }

    @PutMapping("/reservations/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id, Authentication authentication) {
        reservationService.cancelReservation(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
