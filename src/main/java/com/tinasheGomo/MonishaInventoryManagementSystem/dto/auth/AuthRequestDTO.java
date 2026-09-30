package com.tinasheGomo.MonishaInventoryManagementSystem.dto.auth;
import jakarta.validation.constraints.Email; import jakarta.validation.constraints.NotBlank;
public record AuthRequestDTO(
    @Email(message = "Valid email is required") @NotBlank(message = "Email is required") String email,
    @NotBlank(message = "Password is required") String password
) {}
