package com.voyageconnect.web.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {

    @NotNull(message = "Voyage ID cannot be null.")
    private Long voyageId;

    @NotEmpty(message = "Payment token cannot be empty.")
    private String paymentToken;

    public Long getVoyageId() {
        return voyageId;
    }

    public void setVoyageId(Long voyageId) {
        this.voyageId = voyageId;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }
}
