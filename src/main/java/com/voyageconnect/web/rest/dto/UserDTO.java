package com.voyageconnect.web.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * DTO for user registration.
 */
public class UserDTO {

    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters.")
    private String username;

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
