package com.tinasheGomo.MonishaInventoryManagementSystem.dto.user;
import com.tinasheGomo.MonishaInventoryManagementSystem.enums.UserRole;
import java.time.LocalDateTime; import java.util.UUID;
public record UserResponseDTO(UUID userId, String userName, String userEmail, UserRole userRole, String userPhoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {}
