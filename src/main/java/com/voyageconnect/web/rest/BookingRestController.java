package com.voyageconnect.web.rest;

import com.voyageconnect.booking.BookingService;
import com.voyageconnect.booking.exception.BookingException;
import com.voyageconnect.user.User;
import com.voyageconnect.web.rest.dto.BookingRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
public class BookingRestController {

    private final BookingService bookingService;

    public BookingRestController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody BookingRequestDTO bookingRequest,
                                      @AuthenticationPrincipal User user) {
        if (user == null) {
            return new ResponseEntity<>("User must be authenticated to book.", HttpStatus.UNAUTHORIZED);
        }
        try {
            bookingService.createReservation(
                    bookingRequest.getVoyageId(),
                    user,
                    bookingRequest.getPaymentToken()
            );
            return ResponseEntity.ok().body("Reservation created successfully.");
        } catch (BookingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
