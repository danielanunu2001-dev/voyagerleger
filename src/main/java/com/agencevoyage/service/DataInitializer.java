package com.agencevoyage.service;

import com.agencevoyage.entity.User;
import com.agencevoyage.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@example.com");
            // Remplacer par une gestion de rôles si nécessaire
            // admin.setRoles(Set.of("ROLE_ADMIN", "ROLE_CLIENT"));
            userRepository.save(admin);
        }
    }
}
