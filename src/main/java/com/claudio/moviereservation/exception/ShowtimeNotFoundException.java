package com.claudio.moviereservation.exception;

public class ShowtimeNotFoundException extends RuntimeException{
    public ShowtimeNotFoundException(Long id) {
        super("Showtime with id " + id + " not found.");
    }
}
