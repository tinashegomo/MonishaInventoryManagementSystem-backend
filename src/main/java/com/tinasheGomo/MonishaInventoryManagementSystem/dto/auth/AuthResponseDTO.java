package com.tinasheGomo.MonishaInventoryManagementSystem.dto.auth;
import java.util.UUID; import com.tinasheGomo.MonishaInventoryManagementSystem.enums.UserRole;
public record AuthResponseDTO(String token, UUID userId, String userName, String userEmail, UserRole userRole) {}
