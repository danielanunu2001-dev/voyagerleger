package com.voyageconnect.config;

import com.voyageconnect.user.User;
import com.voyageconnect.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * This runner checks for the admin user on startup and updates the password
 * if the placeholder is still present, using an environment variable for security.
 */
@Component
public class AdminUserInitializer implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${ADMIN_PASSWORD:}") // Reads from environment variable, defaults to empty
    private String adminPassword;

    @Value("${CLIENT_PASSWORD:}")
    private String clientPassword;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        PasswordEncoder passwordEncoder = applicationContext.getBean(PasswordEncoder.class);

        updateUserPassword(userRepository, passwordEncoder, "admin", adminPassword, "$2a$10$d1fG.gH6h.jK9l8m7n6o5.pQ4r3s2t1u0.vW9x8y7z.A6B5C4D3");
        updateUserPassword(userRepository, passwordEncoder, "client", clientPassword, "$2a$10$9e8g3.yX/O5.L0.B/8z1u.bH8G9L9b6D5A/8s2z1e.k9a/C2c4e");
    }

    private void updateUserPassword(UserRepository userRepository, PasswordEncoder passwordEncoder, String username, String newPassword, String placeholderPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            System.out.println("WARN: " + username.toUpperCase() + "_PASSWORD environment variable not set. " + username + " password will not be updated.");
            return;
        }

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Check if the password is the placeholder from init.sql
            if (user.getPasswordHash().equals(placeholderPassword) || !passwordEncoder.matches(newPassword, user.getPasswordHash())) {
                 user.setPasswordHash(passwordEncoder.encode(newPassword));
                 userRepository.save(user);
                 System.out.println("INFO: " + username + " user password has been updated from environment variable.");
            }
        }
    }
}
