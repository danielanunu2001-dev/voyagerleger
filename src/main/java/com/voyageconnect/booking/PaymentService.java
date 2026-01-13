package com.voyageconnect.booking;

import com.voyageconnect.common.exception.PaymentException;
import java.math.BigDecimal;

/**
 * Service interface for payment processing.
 */
public interface PaymentService {

    /**
     * Processes a payment for a given amount.
     * @param amount the amount to charge
     * @param paymentToken a token representing the payment method (e.g., from Stripe.js)
     * @return a transaction ID if successful
     * @throws PaymentException if the payment fails
     */
    String processPayment(BigDecimal amount, String paymentToken) throws PaymentException;
}
