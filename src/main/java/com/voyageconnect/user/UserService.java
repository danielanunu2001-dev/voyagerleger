package com.voyageconnect.user;

import java.util.Optional;

/**
 * Service interface for user-related operations.
 */
public interface UserService {

    /**
     * Creates a new user.
     * @param username the username
     * @param email the user's email
     * @param rawPassword the user's raw password
     * @return the created User entity
     */
    User createUser(String username, String email, String rawPassword);

    /**
     * Finds a user by their username.
     * @param username the username to search for
     * @return an Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
}
