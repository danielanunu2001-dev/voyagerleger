package com.voyageconnect.booking;

import com.voyageconnect.user.User;
import com.voyageconnect.user.UserRepository;
import com.voyageconnect.voyage.Voyage;
import com.voyageconnect.voyage.VoyageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BookingConcurrencyIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private VoyageRepository voyageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingService bookingService;

    private Voyage testVoyage;
    private User testUser1;
    private User testUser2;

    @BeforeEach
    void setUp() {
        // Clear previous data
        voyageRepository.deleteAll();
        userRepository.deleteAll();

        // Create a voyage with only one seat left
        testVoyage = new Voyage();
        testVoyage.setTitle("Last Seat Voyage");
        testVoyage.setPrice(BigDecimal.TEN);
        testVoyage.setSeatsAvailable(1);
        testVoyage = voyageRepository.save(testVoyage);

        // Create two users to compete for the seat
        testUser1 = new User();
        testUser1.setUsername("user1");
        testUser1.setEmail("user1@test.com");
        testUser1.setPasswordHash("hash1");
        testUser1 = userRepository.save(testUser1);

        testUser2 = new User();
        testUser2.setUsername("user2");
        testUser2.setEmail("user2@test.com");
        testUser2.setPasswordHash("hash2");
        testUser2 = userRepository.save(testUser2);
    }

    @Test
    void testConcurrentBookingForLastSeat() throws InterruptedException {
        final int numberOfThreads = 2;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger optimisticLockCount = new AtomicInteger(0);

        // Task for User 1
        executor.submit(() -> {
            try {
                bookingService.createReservation(testVoyage.getId(), testUser1, "valid_token");
                successCount.incrementAndGet();
            } catch (Exception e) {
                if (e.getCause() instanceof OptimisticLockingFailureException || e.getMessage().contains("just booked")) {
                    optimisticLockCount.incrementAndGet();
                }
            } finally {
                latch.countDown();
            }
        });

        // Task for User 2
        executor.submit(() -> {
            try {
                bookingService.createReservation(testVoyage.getId(), testUser2, "valid_token");
                successCount.incrementAndGet();
            } catch (Exception e) {
                if (e.getCause() instanceof OptimisticLockingFailureException || e.getMessage().contains("just booked")) {
                    optimisticLockCount.incrementAndGet();
                }
            } finally {
                latch.countDown();
            }
        });

        latch.await(); // Wait for both threads to complete
        executor.shutdown();

        // Assertions
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(optimisticLockCount.get()).isEqualTo(1);

        // Verify the final state of the voyage
        Optional<Voyage> finalVoyageOpt = voyageRepository.findById(testVoyage.getId());
        assertThat(finalVoyageOpt).isPresent();
        assertThat(finalVoyageOpt.get().getSeatsAvailable()).isEqualTo(0);
    }

    @AfterEach
    void tearDown() {
        voyageRepository.deleteAll();
        userRepository.deleteAll();
    }
}
