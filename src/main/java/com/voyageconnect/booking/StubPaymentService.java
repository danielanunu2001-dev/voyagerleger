package com.voyageconnect.booking;

import com.voyageconnect.common.exception.PaymentException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * A stub implementation of the PaymentService for local development and testing.
 * This is the default service activated when 'payment.provider=stub' or is not specified.
 */
@Service
@ConditionalOnProperty(name = "payment.provider", havingValue = "stub", matchIfMissing = true)
public class StubPaymentService implements PaymentService {

    @Override
    public String processPayment(BigDecimal amount, String paymentToken) throws PaymentException {
        System.out.println("Processing STUB payment for " + amount);
        // Simulate a successful payment and return a mock transaction ID.
        if ("fail".equalsIgnoreCase(paymentToken)) {
            throw new PaymentException("Simulated payment failure.");
        }
        return "stub_txn_" + System.currentTimeMillis();
    }
}
