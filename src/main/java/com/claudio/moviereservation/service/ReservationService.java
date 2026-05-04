package com.claudio.moviereservation.service;

import com.claudio.moviereservation.dto.ReservationRequest;
import com.claudio.moviereservation.model.Reservation;
import com.claudio.moviereservation.model.Showtime;
import com.claudio.moviereservation.model.User;
import com.claudio.moviereservation.repository.ReservationRepository;
import com.claudio.moviereservation.repository.ShowtimeRepository;
import com.claudio.moviereservation.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ShowtimeRepository showtimeRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, ShowtimeRepository showtimeRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.showtimeRepository = showtimeRepository;
    }

    public List<Reservation> getMyReservations (String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return reservationRepository.findByUserId(user.getId());
    }

    public Reservation createReservation(ReservationRequest request, String username) {
        Showtime showtime = showtimeRepository.findById(request.getShowtimeId()).orElseThrow();
        if (showtime.getAvailableSeats() >= 1) {
            User user = userRepository.findByUsername(username).orElseThrow();
            Reservation reservation = new Reservation();
            reservation.setSeatNumber(request.getSeatNumber());
            reservation.setShowtime(showtime);
            reservation.setUser(user);
            reservation.setStatus("CONFIRMED");
            reservation.setCreatedAt(LocalDateTime.now());
            showtime.setAvailableSeats(showtime.getAvailableSeats() - 1);
            showtimeRepository.save(showtime);
            return reservationRepository.save(reservation);
        }else {throw new RuntimeException("No seats available");}
    }

    public void cancelReservation(Long id, String username){
        Reservation reservationCancelled = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        User user = userRepository.findByUsername(username).orElseThrow();
        Showtime showtime = reservationCancelled.getShowtime();
        if (reservationCancelled.getUser().getId().equals(user.getId())) {
            reservationCancelled.setStatus("CANCELLED");
            showtime.setAvailableSeats(showtime.getAvailableSeats() + 1);
            showtimeRepository.save(showtime);
            reservationRepository.save(reservationCancelled);
        }else {
            throw new RuntimeException("Unauthorized");
        }
    }
}
