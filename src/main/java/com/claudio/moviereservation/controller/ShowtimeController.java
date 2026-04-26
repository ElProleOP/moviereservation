package com.claudio.moviereservation.controller;

import com.claudio.moviereservation.dto.ShowtimeRequest;
import com.claudio.moviereservation.model.Showtime;
import com.claudio.moviereservation.service.ShowtimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService){ this.showtimeService = showtimeService;}

    @GetMapping("/showtimes")
    public ResponseEntity<List<Showtime>> getAllShowtimes() {
        return ResponseEntity.ok(showtimeService.getAllShowtimes());
    }

    @PostMapping("/showtimes")
    public ResponseEntity<Showtime> createShowtime(@RequestBody ShowtimeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(showtimeService.createShowtime(request));
    }

    @GetMapping("/showtimes/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id) {
        return ResponseEntity.ok(showtimeService.getShowtimeById(id));
    }

    @GetMapping("/showtimes/movie/{id}")
    public ResponseEntity<List<Showtime>> getShowtimesByMovieId(@PathVariable Long movieId){
        return ResponseEntity.ok(showtimeService.getShowtimesByMovie(movieId));
    }

    @DeleteMapping("/showtimes/{id}")
    public ResponseEntity<Void> deleteShowtime (@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.noContent().build();
    }
}
