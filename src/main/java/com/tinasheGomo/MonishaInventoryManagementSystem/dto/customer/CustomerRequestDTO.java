package com.tinasheGomo.MonishaInventoryManagementSystem.dto.customer;
import jakarta.validation.constraints.NotBlank;
public record CustomerRequestDTO(
    @NotBlank(message = "Customer name is required") String customerName,
    @NotBlank(message = "Phone number is required") String phoneNumber
) {}
