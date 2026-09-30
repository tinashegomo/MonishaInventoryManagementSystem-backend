package com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.response;
import java.time.LocalDateTime; import java.util.UUID;
public record ProductRestockHistoryResponseDTO(UUID restockId, UUID productId, String size, Integer quantityAdded, String restockedBy, LocalDateTime restockedAt) {}
