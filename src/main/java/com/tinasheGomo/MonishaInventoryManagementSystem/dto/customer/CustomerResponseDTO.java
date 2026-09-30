package com.tinasheGomo.MonishaInventoryManagementSystem.dto.customer;
import java.time.LocalDateTime; import java.util.UUID;
public record CustomerResponseDTO(UUID customerId, String customerName, String phoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {}
