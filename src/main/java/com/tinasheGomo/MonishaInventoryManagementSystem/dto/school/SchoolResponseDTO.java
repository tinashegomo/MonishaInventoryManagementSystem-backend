package com.tinasheGomo.MonishaInventoryManagementSystem.dto.school;
import java.time.LocalDateTime; import java.util.UUID;
public record SchoolResponseDTO(UUID schoolId, String schoolName, LocalDateTime createdAt, LocalDateTime updatedAt) {}
