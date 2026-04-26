package com.claudio.moviereservation.service;

import com.claudio.moviereservation.exception.MovieNotFoundException;
import com.claudio.moviereservation.model.Movie;
import com.claudio.moviereservation.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) { this.movieRepository = movieRepository;}

    public List<Movie> getAllMovies() {return movieRepository.findAll();}
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }
    public Movie createMovie(Movie movie) {return movieRepository.save(movie);}
    public void deleteMovie(Long id) {
        getMovieById(id);
        movieRepository.deleteById(id);
    }
}
