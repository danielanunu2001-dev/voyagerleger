package com.voyageconnect.booking;

import com.voyageconnect.booking.exception.BookingException;
import com.voyageconnect.common.exception.PaymentException;
import com.voyageconnect.common.exception.ResourceNotFoundException;
import com.voyageconnect.user.User;
import com.voyageconnect.voyage.Voyage;
import com.voyageconnect.voyage.VoyageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private VoyageRepository voyageRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Voyage voyage;
    private User user;

    @BeforeEach
    void setUp() {
        voyage = new Voyage();
        voyage.setId(1L);
        voyage.setTitle("Test Voyage");
        voyage.setPrice(new BigDecimal("1000"));
        voyage.setSeatsAvailable(1);
        voyage.setVersion(0L);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    @Test
    void createReservation_Success() throws BookingException, PaymentException {
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));
        when(paymentService.processPayment(any(), any())).thenReturn("txn_123");
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reservation reservation = bookingService.createReservation(1L, user, "tok_valid");

        assertNotNull(reservation);
        assertEquals(0, voyage.getSeatsAvailable());
        assertEquals("CONFIRMED", reservation.getStatus());

        verify(voyageRepository, times(1)).save(voyage);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(paymentService, times(1)).processPayment(voyage.getPrice(), "tok_valid");
    }

    @Test
    void createReservation_NoSeatsAvailable_ThrowsBookingException() {
        voyage.setSeatsAvailable(0);
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));

        assertThrows(BookingException.class, () -> {
            bookingService.createReservation(1L, user, "tok_valid");
        });

        verify(voyageRepository, never()).save(any());
    }

    @Test
    void createReservation_PaymentFails_ThrowsBookingException() throws PaymentException {
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));
        when(paymentService.processPayment(any(), any())).thenThrow(new PaymentException("Payment provider error"));

        assertThrows(BookingException.class, () -> {
            bookingService.createReservation(1L, user, "tok_invalid");
        });

        assertEquals(1, voyage.getSeatsAvailable()); // Seats should not be decremented
        verify(voyageRepository, never()).save(any());
    }

    @Test
    void createReservation_VoyageNotFound_ThrowsResourceNotFoundException() {
        when(voyageRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.createReservation(2L, user, "tok_valid");
        });
    }

    @Test
    void createReservation_OptimisticLockingFailure_ThrowsBookingException() {
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));
        when(voyageRepository.save(any(Voyage.class))).thenThrow(new OptimisticLockingFailureException("..."));

        assertThrows(BookingException.class, () -> {
            bookingService.createReservation(1L, user, "tok_valid");
        });
    }
}
