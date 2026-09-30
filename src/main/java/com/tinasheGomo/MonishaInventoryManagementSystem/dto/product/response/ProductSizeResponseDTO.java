package com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.response;
import java.time.LocalDateTime; import java.util.UUID;
public record ProductSizeResponseDTO(UUID productSizeId, String size, Integer quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {}
