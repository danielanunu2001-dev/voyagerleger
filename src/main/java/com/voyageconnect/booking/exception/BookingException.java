package com.voyageconnect.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for handling booking-related errors, such as no available seats.
 * Results in a 400 Bad Request HTTP status.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingException extends RuntimeException {

    public BookingException(String message) {
        super(message);
    }

    public BookingException(String message, Throwable cause) {
        super(message, cause);
    }
}
