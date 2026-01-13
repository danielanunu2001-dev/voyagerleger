package com.voyageconnect.web.rest;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.voyageconnect.booking.StripeEvent;
import com.voyageconnect.booking.StripeEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webhooks")
public class StripeWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final StripeEventRepository eventRepository;

    public StripeWebhookController(StripeEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        if (webhookSecret == null || webhookSecret.isBlank()) {
            logger.error("Stripe webhook secret is not configured.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook secret not configured.");
        }

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            logger.warn("Invalid Stripe signature received.");
            return ResponseEntity.badRequest().body("Invalid signature.");
        }

        // Idempotency check: Has this event been processed before?
        if (eventRepository.existsById(event.getId())) {
            logger.info("Received duplicate Stripe event with ID: {}", event.getId());
            return ResponseEntity.ok("Event already processed.");
        }

        // Process the event
        switch (event.getType()) {
            case "payment_intent.succeeded":
                // Fulfill the purchase (e.g., update reservation status, send confirmation email)
                logger.info("PaymentIntent succeeded for event: {}", event.getId());
                // TODO: Implement business logic here.
                break;
            case "payment_intent.payment_failed":
                logger.warn("PaymentIntent failed for event: {}", event.getId());
                // TODO: Notify user or handle the failure.
                break;
            default:
                logger.warn("Unhandled event type: {}", event.getType());
        }

        // Save the event ID to prevent reprocessing
        eventRepository.save(new StripeEvent(event.getId()));

        return ResponseEntity.ok("Webhook received.");
    }
}
