package com.voyageconnect.booking;

import com.voyageconnect.booking.exception.BookingException;
import com.voyageconnect.user.User;

/**
 * Service interface for handling the booking process.
 */
public interface BookingService {

    /**
     * Creates a reservation for a given voyage and user.
     * This method is transactional and handles optimistic locking.
     *
     * @param voyageId the ID of the voyage to book
     * @param user the user making the reservation
     * @param paymentToken a token for payment processing
     * @return the created Reservation
     * @throws BookingException if the booking fails (e.g., no seats, payment issue)
     */
    Reservation createReservation(Long voyageId, User user, String paymentToken) throws BookingException;
}
