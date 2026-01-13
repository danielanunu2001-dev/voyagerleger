package com.voyageconnect.booking;

import com.voyageconnect.booking.exception.BookingException;
import com.voyageconnect.common.exception.PaymentException;
import com.voyageconnect.common.exception.ResourceNotFoundException;
import com.voyageconnect.user.User;
import com.voyageconnect.voyage.Voyage;
import com.voyageconnect.voyage.VoyageRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the BookingService.
 */
@Service
public class BookingServiceImpl implements BookingService {

    private final VoyageRepository voyageRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;

    public BookingServiceImpl(VoyageRepository voyageRepository,
                              ReservationRepository reservationRepository,
                              PaymentService paymentService) {
        this.voyageRepository = voyageRepository;
        this.reservationRepository = reservationRepository;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional(rollbackFor = BookingException.class)
    public Reservation createReservation(Long voyageId, User user, String paymentToken) throws BookingException {
        try {
            Voyage voyage = voyageRepository.findById(voyageId)
                    .orElseThrow(() -> new ResourceNotFoundException("Voyage", "id", voyageId));

            if (voyage.getSeatsAvailable() <= 0) {
                throw new BookingException("No seats available for voyage: " + voyage.getTitle());
            }

            // Process payment before confirming reservation
            try {
                paymentService.processPayment(voyage.getPrice(), paymentToken);
            } catch (PaymentException e) {
                throw new BookingException("Payment failed: " + e.getMessage(), e);
            }

            voyage.setSeatsAvailable(voyage.getSeatsAvailable() - 1);
            voyageRepository.save(voyage);

            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setVoyage(voyage);
            reservation.setAmount(voyage.getPrice());
            reservation.setStatus("CONFIRMED");

            return reservationRepository.save(reservation);

        } catch (OptimisticLockingFailureException e) {
            // This occurs if another transaction updated the voyage (e.g., booked the last seat)
            // between reading and writing. The transaction is rolled back automatically.
            throw new BookingException("The selected voyage was just booked by another user. Please try again.");
        }
    }
}
