package com.voyageconnect.user;

import com.voyageconnect.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private Role clientRole;

    @BeforeEach
    void setUp() {
        clientRole = new Role("ROLE_CLIENT");
    }

    @Test
    void createUser_Success() {
        when(roleRepository.findByName("ROLE_CLIENT")).thenReturn(Optional.of(clientRole));
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = userService.createUser("testuser", "test@test.com", "password123");

        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("hashedPassword", user.getPasswordHash());
        assertTrue(user.getRoles().contains(clientRole));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_RoleNotFound_ThrowsException() {
        when(roleRepository.findByName("ROLE_CLIENT")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.createUser("testuser", "test@test.com", "password123");
        });

        verify(userRepository, never()).save(any(User.class));
    }
}
