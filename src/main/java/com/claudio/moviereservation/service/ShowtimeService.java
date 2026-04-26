package com.claudio.moviereservation.service;

import com.claudio.moviereservation.dto.ShowtimeRequest;
import com.claudio.moviereservation.exception.MovieNotFoundException;
import com.claudio.moviereservation.exception.ShowtimeNotFoundException;
import com.claudio.moviereservation.model.Movie;
import com.claudio.moviereservation.model.Showtime;
import com.claudio.moviereservation.repository.MovieRepository;
import com.claudio.moviereservation.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }
    public List<Showtime> getAllShowtimes() {return showtimeRepository.findAll();}
    public Showtime getShowtimeById(Long id) {return showtimeRepository.findById(id).orElseThrow(() -> new ShowtimeNotFoundException(id));}
    public List<Showtime> getShowtimesByMovie(Long movieId) {return showtimeRepository.findByMovieId(movieId);}
    public Showtime createShowtime(ShowtimeRequest request) {
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new MovieNotFoundException(request.getMovieId()));

        Showtime showtime = new Showtime();
        showtime.setDateTime(request.getDateTime());
        showtime.setRoom(request.getRoom());
        showtime.setTotalSeats(request.getTotalSeats());
        showtime.setAvailableSeats(request.getAvailableSeats());
        showtime.setMovie(movie);

        return showtimeRepository.save(showtime);
    }
    public void deleteShowtime(Long id) {
        getShowtimeById(id);
        showtimeRepository.deleteById(id);
    }
}
